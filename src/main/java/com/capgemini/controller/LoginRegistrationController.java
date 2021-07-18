package com.capgemini.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.exceptions.NoSuchCustomerException;
import com.capgemini.model.Admin;
import com.capgemini.model.Customer;
import com.capgemini.service.AdminService;
import com.capgemini.service.CustomerService;

@RestController
@RequestMapping(path = "loginreg")
public class LoginRegistrationController {

	@Autowired
	private AdminService service;

	@Autowired
	private Admin admin;

	// http://localhost:9090/user-api/loginreg/admin/ - Post

	@PostMapping(path = "/admin/")
	public ResponseEntity<String> saveAdmin(@Valid @RequestBody Admin admin) throws NoSuchAdminException {
		ResponseEntity<String> response = null;
		Admin result = service.registration(admin);
		if (result != null) {
			response = new ResponseEntity<String>("Admin with id" + admin.getAdminId() + "is added",
					HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<String>("Admin with id" + admin.getAdminId() + "is not added",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}

	// http://localhost:9090/user-api/loginreg/admin/1 - Get
	@GetMapping(path = "/admin/{adminId}")
	public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") int adminId) throws NoSuchAdminException {
		ResponseEntity<Admin> response = null;
		Admin admin = service.findAdminById(adminId);
		response = new ResponseEntity<Admin>(admin, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/loginreg/admin/ - Get
	@GetMapping(path = "/admin/")
	public ResponseEntity<List<Admin>> getAllAdmins() {
		ResponseEntity<List<Admin>> response = null;
		List<Admin> list = service.findAllAdmins();
		response = new ResponseEntity<List<Admin>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/loginreg/admin/byName/xyz
	@GetMapping(path = "/admin/byName/{firstName}")
	public ResponseEntity<List<Admin>> getAllAdminByName(@PathVariable("firstName") String firstName) {
		ResponseEntity<List<Admin>> response = null;
		List<Admin> list = service.findAllAdminByName(firstName);
		response = new ResponseEntity<List<Admin>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/loginreg/admin/delete/2
	@DeleteMapping("/delete/{adminId}")
	public String deleteAdmin(@PathVariable(value = "adminId") int adminId) {

		service.deleteAdmin(adminId);

		return "Deleted Successfully id= " + adminId;
	}

	// http://localhost:9090/user-api/loginreg/admin/login/lalit/5678
	@GetMapping(path = "login/admin/{email1}/{password1}")
	public ResponseEntity<Admin> getAdminByEmailAndPassword(@PathVariable("email1") String email1,
			@PathVariable("password1") String password1) {
		ResponseEntity<Admin> response = null;
		Admin admin = service.login(email1, password1);
		response = new ResponseEntity<Admin>(admin, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/loginreg/send-mail/ybiradar10@gmail.com
	/*
	 * @RequestMapping(path="/send-mail/") public String send() {
	 * 
	 * 
	 * //String email=email1; admin.setEmail("ybiradar10@gmail.com"); //Receiver's
	 * email address //admin.setEmail(email); try { service.sendEmail(admin); }
	 * catch (MailException mailException) { System.out.println(mailException); }
	 * return "Congratulations! Your mail has been send to the user."; }
	 */
//	http://localhost:9090/user-api/loginreg/admin/byemail/ybiradar10@gmail.com
	@GetMapping(path = "/admin/byEmail/{email}")
	public String forgotAdminPassword(@PathVariable("email") String email) throws MailException, MessagingException {

		Admin result = service.findAdminByEmail(email);
		String email1 = result.getEmail();
		String password1 = result.getPassword();
		admin.setEmail(email1);
		admin.setPassword(password1);

		service.sendEmail(admin);
		return "Congratulations! Your mail has been send to the user.";
	}

	// -------------------------------------------------------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Customer customer;

	// http://localhost:9090/user-api/loginreg/customer/byEmail/
	@GetMapping(path = "/customer/byEmail/{email}")
	public String forgotCustomerPassword(@PathVariable("email") String email) throws MailException, MessagingException {

		Customer result = customerService.findCustomerByEmail(email);
		String email1 = result.getEmail();
		String password1 = result.getPassword();
		customer.setEmail(email1);
		customer.setPassword(password1);

		customerService.sendEmail(customer);
		return "Congratulations! Your mail has been send to the user.";
	}

   //http://localhost:9090/user-api/loginreg/customer/ - post
	@PostMapping(path = "/customer/")	
	public ResponseEntity<String> saveCustomer(@Valid @RequestBody Customer customer) throws NoSuchCustomerException {
		ResponseEntity<String> response = null;
		Customer result = customerService.registration(customer);
		if (result != null)
			response = new ResponseEntity<String>("Customer with id " + customer.getCustomerId() + " is added.",
					HttpStatus.CREATED);
		else
			throw new NoSuchCustomerException("Invalid Data");
		return response;
	}

	// http://localhost:9090/user-api/loginreg/searchById/ -Get
	@GetMapping(path = "/searchById/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId)
			throws NoSuchCustomerException {
		ResponseEntity<Customer> response = null;
		Customer customer = customerService.findCustomerById(customerId);
		response = new ResponseEntity<Customer>(customer, HttpStatus.OK);
		return response;
	}

	@GetMapping(path = "/getAllCustomer/")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		ResponseEntity<List<Customer>> response = null;
		List<Customer> list = customerService.findAllCustomers();
		response = new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/user-api/loginreg/searchByName/
	@GetMapping(path = "/searchByName/{firstName}")
	public ResponseEntity<List<Customer>> getAllCustomerByName(@PathVariable("customerName") String firstName) {
		ResponseEntity<List<Customer>> response = null;
		List<Customer> list = customerService.findAllCustomerByName(firstName);
		response = new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
		return response;
	}

	// 	/lalit@gmail/5678
	@GetMapping(path = "/login/customer/{email}/{password}")
	public ResponseEntity<Customer> getCustomerByEmailAndPassword(@PathVariable("email") String email,
			@PathVariable("password") String password) {
		ResponseEntity<Customer> response = null;
		Customer customer = customerService.loginCustomer(email, password);
		response = new ResponseEntity<Customer>(customer, HttpStatus.OK);
		return response;
	}

}
