package com.capgemini.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	@Query(value = "SELECT b FROM Booking b WHERE b.vehicle.vehicleId = :vehicleId")
	public List<Booking> findByVehicleId(@Param("vehicleId") int vehicleId);
	
	@Query(value = "SELECT b FROM Booking b WHERE b.customer.customerId = :customerId")
	public List<Booking> findByCustomerId(@Param("customerId") int customerId);
	
	@Modifying
	@Transactional
	@Query(value = "Update Booking b Set b.status= false where b.bookingId = :bookingId")
	int updateBookingStatus(@Param("bookingId") int bookingId);

}
