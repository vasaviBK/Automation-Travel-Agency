package com.capgemini.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoCapacityInVehicleException;
import com.capgemini.exceptions.NoSuchBookingIdFoundException;
import com.capgemini.exceptions.NoSuchRouteException;
import com.capgemini.exceptions.VehicleAlreadyDepartedException;
import com.capgemini.model.Booking;
import com.capgemini.model.Vehicle;
import com.capgemini.repository.BookingRepository;
import com.capgemini.repository.CustomerRepository;
import com.capgemini.repository.VehicleRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	private JavaMailSender javaMailSender;

	@Autowired
	private BookingServiceImpl bookingServiceImpl;

	@Override
	@Autowired
	public void MailService(JavaMailSender javaMailSender) {

		this.javaMailSender = javaMailSender;
	}

	@Override
	@Transactional
	public Booking bookVehicle(Booking booking)
			throws NoCapacityInVehicleException, MailException, VehicleAlreadyDepartedException, MessagingException {
		LocalDateTime bookingDateTime = LocalDateTime.now(ZoneId.systemDefault());
		Booking booking2 = null;

		// Setting the customer and vehicle object
		if (booking.getVehicle().getVehicleId() != vehicleRepository.findById(booking.getVehicle().getVehicleId()).get()
				.getVehicleId()) {
			throw new NoSuchElementException("Vehicle ID is Invalid");
		} else
			booking.setVehicle(vehicleRepository.findById(booking.getVehicle().getVehicleId()).get());

		// Throwing exception if customerId is not present in database
		if (booking.getCustomer().getCustomerId() != customerRepository.findById(booking.getCustomer().getCustomerId())
				.get().getCustomerId()) {
			throw new NoSuchElementException("Customer ID is Invalid");
		} else
			booking.setCustomer(customerRepository.findById(booking.getCustomer().getCustomerId()).get());

		// Calculating Fare by (fare_per_km * distance) * noOfPassengers
		double distance = booking.getVehicle().getRoute().getDistance();
		int farePerKm = booking.getVehicle().getFarePerKm();
		int noOfPassenger = booking.getNoOfPassengers();

		int cap = booking.getVehicle().getSittingCapacity();
		double fare = (distance * farePerKm) * noOfPassenger;
		booking.setFare(fare);

		// Setting Dates from LocalDate and journeyDate from Vehicle Entity
		booking.setBookingDateTime(bookingDateTime);
		booking.setJourneyDate(booking.getVehicle().getJourneyDate());

		// Retrieving vehicle records
		int vehicleId = booking.getVehicle().getVehicleId();
		List<Booking> list = bookingRepository.findByVehicleId(vehicleId);
		int bookedSit = 0;

		// Checking already booked sit for particular vehicle selected by Customer
		for (Booking b : list) {
			bookedSit += b.getNoOfPassengers();
		}

		// Check for vehicles with date which is already passed
		LocalDate journeyDate = booking.getVehicle().getJourneyDate();
		int compareDate = journeyDate.compareTo(bookingDateTime.toLocalDate());

		int compareTime = booking.getVehicle().getDepartTime().compareTo(bookingDateTime.toLocalTime());
		if (compareTime < 0) {
			throw new VehicleAlreadyDepartedException("Sorry " + booking.getVehicle().getVehicleName()
					+ " is already is departed from your place at " + booking.getVehicle().getDepartTime());
		}

		/*
		 * Checking (Already booked sit + user trying to book sit) is less than vehicle
		 * sitting capacity Also Checking if the vehicle selected by user has valid
		 * journeyDate
		 */
		if ((bookedSit + noOfPassenger) <= cap && compareDate >= 0) {
			booking.setStatus(true);
			booking2 = bookingRepository.save(booking);

			//Send Mail to Customer
			bookingServiceImpl.successfulBookingMail(booking);
			return booking2;

		} else {
			throw new NoCapacityInVehicleException("Sorry your request of " + noOfPassenger
					+ " sits can not be booked. Currently " + bookedSit + "/" + cap + " sits are already booked.");

		}
	}

	@Override
	public void successfulBookingMail(Booking booking) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(booking.getCustomer().getEmail());
		helper.setSubject("Automation Travel Agency");
		helper.setText("Thank you for Booking a sit! " + booking.getCustomer().getFirstName() + " "
				+ booking.getCustomer().getLastName() + "\nYour Booking Id : " + booking.getBookingId()
				+ "\nFor Number of Sits : " + booking.getNoOfPassengers() + "\nCharged with : " + booking.getFare()
				+ "\nYour Source city : " + booking.getVehicle().getRoute().getSource() + "\nYour Destination city : "
				+ booking.getVehicle().getRoute().getDestination() + "\nYou will be picked from : "
				+ booking.getBoardingPoint() + "\nYour Drop Point is : " + booking.getDropPoint()
				+ "\nYour sit are booked for : " + booking.getJourneyDate() + "\nYour Vehicle Number : "
				+ booking.getVehicle().getVehicleNo() + "\nYour Vehicle Name : "
				+ booking.getVehicle().getVehicleName());

		javaMailSender.send(mimeMessage);
	}

	@Override
	public List<Vehicle> searchByLocation(String source, String destination, LocalDate journeyDate) {
		return vehicleRepository.searchByCustomerRequest(source, destination, journeyDate);
	}

	@Override
	public int cancelBooking(int bookingId) throws NoSuchBookingIdFoundException {
		if (bookingRepository.existsById(bookingId)) {
			return bookingRepository.updateBookingStatus(bookingId);
		}
		throw new NoSuchBookingIdFoundException("Booking id " + bookingId + " does not exist");
	}

	// Show Customer Booking History
	@Override
	public List<Booking> viewBookingStatus(int customerId) {
		return bookingRepository.findByCustomerId(customerId);
	}

	@Override
	public List<Booking> viewAllBooking() {
		return bookingRepository.findAll();
	}

}
