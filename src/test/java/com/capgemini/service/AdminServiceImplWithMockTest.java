package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.model.Admin;
import com.capgemini.repository.AdminRepository;
@SpringBootTest
class AdminServiceImplWithMockTest {

	@Autowired
	private AdminService service;
	
	@MockBean
	private AdminRepository repository;
	
	
	@Test
	public void saveAdminTest() throws NoSuchAdminException
	{
		Admin admin=new Admin(1,"yogesh","biradar",LocalDate.of(1998, 04, 04),"Mumbai",986760,"ybiradar10@gmail.com","12345678");
		when(repository.save(admin)).thenReturn(admin);
		assertEquals(admin,service.registration(admin));
		
	}

	
	
	
}
