package com.capgemini.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.model.Customer;
import com.capgemini.repository.CustomerRepository;
import com.sun.el.stream.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	private JavaMailSender javaMailSender;

	@Override

	@Transactional // container manage transaction
	public Customer registration(Customer customer) {
		System.out.println(customer);
		Customer result = null;
		if (customer != null)
			result = repository.save(customer);
		return result;
	}

	@Override
	public Customer findCustomerById(int customerId) throws NoSuchCustomerException {

		// if( repository.existsById(customerId)) { return
		return repository.findById(customerId).get(); // } //throw new

	}

	@Override
	public List<Customer> findAllCustomers() {

		return repository.findAll();
	}

	@Override
	public List<Customer> findAllCustomerByName(String name) {
		return repository.realAllName(name);
	}

	@Override
	public boolean removeCustomerById(int customerId) throws NoSuchCustomerException {
		if (repository.existsById(customerId)) {
			repository.deleteById(customerId);
			return true;
		}
		return false;

	}

	@Override
	public Customer modifyCustomer(int customerId) {

		return null;
	}

	@Override
	public Customer loginCustomer(String email, String password) {
		return repository.readByEmailAndpassword(email, password);
	}

	@Autowired
	@Override
	public void MailService(JavaMailSender javaMailSender) {
		// TODO Auto-generated method stub
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(Customer customer) throws MailException, MessagingException {

		// TODO Auto-generated method stub

		// SimpleMailMessage mail = new SimpleMailMessage();
		String result1 = customer.getPassword();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(customer.getEmail());
		helper.setSubject(" Automation Travel Agency");
		helper.setText("hello");
		helper.setText(result1);

		javaMailSender.send(mimeMessage);
	}

	@Override
	public Customer findCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.readByEmail(email);
	}

}
