package com.capgemini.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capgemini.exceptions.NoSuchAdminException;
import com.capgemini.model.Admin;
import com.capgemini.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository repository;
	
	private JavaMailSender javaMailSender;

	@Override
	public Admin registration(Admin admin)throws NoSuchAdminException
	{
		Admin result = null;
		
		if(admin==null )
		{
			throw new NoSuchAdminException("Admin not found");
		
		}	
		else
		{
			result = repository.save(admin);
			//result=true;
		}
		return result;

	}
	
	
	/*@Override
	public boolean registration(Admin admin) {
		boolean result = false;
		if(AdminValidator.isAdminFirstNameValid(admin.getFirstName()) && AdminValidator.isAdminLastNameValid(admin.getLastName()) )
		{
			admin = repository.save(admin);
			result=true;
		}
		
		return result;

	}*/
	
	
	
	/*@Override
	public boolean registration(Admin admin) {
		boolean result = false;
		admin = repository.save(admin);
		if (admin.getAdminId() > 0)
			result = true;
		return result;

	}*/

	@Override
	public Admin findAdminById(int adminId)throws NoSuchAdminException 
{
		if (repository.existsById(adminId)) {
			return repository.findById(adminId).get();
		}
		throw new NoSuchAdminException("Admin with id"+adminId+"not found");

	}

	@Override
	public List<Admin> findAllAdmins() {

		return repository.findAll();
	}

	@Override
	public List<Admin> findAllAdminByName(String firstName) {
		
		return repository.readAllName(firstName);
	}

	@Override
	public Admin login(String email, String password) {
		
		
		return repository.readByEmailAndPassword(email, password);
	}
	

	@Override
	public void deleteAdmin(int adminId) {
	repository.deleteById(adminId);

	}

	@Override
	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
	
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(Admin admin) throws MailException, MessagingException
	{
		
		//SimpleMailMessage mail = new SimpleMailMessage();
		String result1=admin.getPassword();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		helper.setTo(admin.getEmail());
		helper.setSubject(" Automation Travel Agency");
		helper.setText("heeloodsd");
		helper.setText(result1);
		
		javaMailSender.send(mimeMessage);
	}

	@Override
	public Admin findAdminByEmail(String email) {
		
		return repository.readByEmail(email);
	}

/*	@Override
	public void sendEmail(Admin admin) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(admin.getEmail());
		mail.setSubject(" Automation Travel Agency");
		mail.setText("heeloodsd");

		
		javaMailSender.send(mail);
		
	}*/
}
