package aca.mail;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender emailSender;
	
	@Override	
	public void sendMesageSimple(String para, String subject, String mensaje) throws MessagingException, IOException{
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setTo(para); 
		message.setFrom("inscripcion@um.edu.mx");
		message.setSubject(subject); 
		message.setText(mensaje);
		message.setSentDate(new Date());
		emailSender.send(message);		
	}
	
	@Override
	public void sendMessageWithAttachment( String to, String subject, String text, String pathToAttachment){	    
	    try { 
	    	MimeMessage message = emailSender.createMimeMessage();
	      
	    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	     
	    	helper.setTo(to);
	    	helper.setSubject(subject);
	    	helper.setText(text);
	         
	    	FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
	    	helper.addAttachment("Invoice", file);
	    	emailSender.send(message);
	    }catch(Exception ex){
	    	System.out.println("Error:"+ex);
	    }    
	}
}
