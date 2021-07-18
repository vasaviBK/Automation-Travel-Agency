package com.capgemini.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.model.Customer;

public interface CustomerService {
	public Customer registration(Customer customer);

	public Customer findCustomerById(int customerId) throws NoSuchCustomerException;

	public List<Customer> findAllCustomers();

	public List<Customer> findAllCustomerByName(String name);

	public Customer modifyCustomer(int customerId);

	public boolean removeCustomerById(int customerId) throws NoSuchCustomerException;

	public Customer loginCustomer(String email, String password);

	public void MailService(JavaMailSender javaMailSender);

	public void sendEmail(Customer customer) throws MailException, MessagingException;

	public Customer findCustomerByEmail(String email);

}