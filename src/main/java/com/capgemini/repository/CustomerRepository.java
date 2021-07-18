package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	  public List<Customer> readCustomersByFirstName(String firstName);
	  
	  @Query(value = "select s from Customer s Where s.firstName = :name")
	  public List<Customer> realAllName(@Param("name") String firstName);
	  
	  //@Query(value = "Delete s From Customer s Where s.customerId = :customerId")
	  //public boolean deleteById(@Param("customerId") int customerId);
	  
	  Customer findByEmailAndPassword(String email, String Password);
	  
	  @Query(value ="Select s from Customer s Where s.email = :email1 AND s.password = :password1 ") 
	  public Customer readByEmailAndpassword(@Param("email1") String email1, @Param("password1") String password1);

	  @Query(value = "Select a From Customer a where a.email=:email")
		public Customer readByEmail(@Param("email") String email);
}
