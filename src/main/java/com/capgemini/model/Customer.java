package com.capgemini.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customer")
@Scope(scopeName = "prototype")
@Entity
@Table(name = "CUSTOMER_MASTER")
public class Customer implements Serializable {

	@Id
	@Column(name = "CUSTOMER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;

	@Column(name = "FIRST_NAME", length = 30, nullable = false)
	@NotEmpty(message = "Please enter first name")
	@Size(min = 2, message = "customer firstname should have atleast 2 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Please enter valid name")
	private String firstName;

	@Column(name = "LAST_NAME", length = 30, nullable = false)
	@NotEmpty(message = "Please enter last name")
	@Size(min = 2, message = "customer lastname should have atleast 2 characters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Please enter valid name")
	private String lastName;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "GENDER", length = 10, nullable = false)
	@NotEmpty
	private String gender;

	@Column(name = "ADDRESS")
	@NotEmpty
	@Size(min = 2, message = "Address should have at least 2 characters")
	private String address;

	@Column(name = "MOBILE_NO", length = 10, unique = true)
	private long mobileNo;

	@Column(name = "EMAIL", unique = true)
	@NotEmpty
	@Email
	private String email;

	@Column(name = "PASSWORD")
	@NotEmpty
	@Size(min = 8, message = "password should have at least 8 characters")
	private String password;

	public Customer() {

	}

	public Customer(int customerId, String firstName, String lastName, LocalDate dateOfBirth, String gender,
			String address, long mobileNo, String email, String password) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.mobileNo = mobileNo;
		this.email = email;
		this.password = password;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", address=" + address + ", mobileNo="
				+ mobileNo + ", email=" + email + ", password=" + password + "]";
	}

}