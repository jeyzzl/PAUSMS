package aca.saii.spring;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailSender {
	
	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	
	public void sendEmails() throws AddressException,
			MessagingException {
		
			setMailServerProperties();
			createEmailMessage();
			sendEmail();
		}


	public void setMailServerProperties() {

		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");

	}

	public void createEmailMessage() throws AddressException,
			MessagingException {
		String[]toEmails = { "1150520@alumno.um.edu.mx", "1140196@alumno.um.edu.mx" };
		String emailSubject = "Test Email Efectividad";
		String emailBody = "This is an email sent by JavaMail api.";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");//for a html email
		emailMessage.setText(emailBody);// for a text email

	}

	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "danibalderas@gmail.com";//just the id alone without @gmail.com
		String fromUserEmailPassword = "12345678";

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}

}