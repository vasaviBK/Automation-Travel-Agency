package com.capgemini.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.model.Admin;

public interface AdminService {
	public Admin registration(Admin admin)throws NoSuchAdminException;

	public Admin findAdminById(int adminId)throws NoSuchAdminException;

	public List<Admin> findAllAdmins();

	public List<Admin> findAllAdminByName(String firstName);

	public void deleteAdmin(int adminId);

	public Admin login(String email, String password);
	
	public void MailService(JavaMailSender javaMailSender);
	
	public void sendEmail(Admin admin)throws MailException, MessagingException;
	
	
	public Admin findAdminByEmail(String email);

}
