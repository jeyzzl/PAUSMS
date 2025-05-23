package aca.mail;

import java.io.IOException;

import jakarta.mail.MessagingException;

public interface MailService {	
	public void sendMesageSimple(String para, String subject, String mensaje) throws MessagingException, IOException;
	public void sendMessageWithAttachment(String para, String subject, String mensaje, String pathToAttachment) throws MessagingException, IOException;
}
