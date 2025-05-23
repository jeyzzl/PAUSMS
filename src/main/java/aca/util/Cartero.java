package aca.util;

import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;


public class Cartero {
	
	public boolean enviarMensaje(){
		boolean enviar = false; 
		try{
			String host = "smtp.gmail.com";
		    String from = "etorres@um.edu.mx";
		    String pass = "teritos";
		    Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", "true"); // added this line
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", from);
		    props.put("mail.smtp.password", pass);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
		 
		    String[] to = {"erytorres@hotmail.com"}; // added this line
		 
		    Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		 
		    InternetAddress[] toAddress = new InternetAddress[to.length];
		 
		    // To get the array of addresses
		    for( int i=0; i < to.length; i++ ) { // changed from a while loop
		        toAddress[i] = new InternetAddress(to[i]);
		    }
		    System.out.println(Message.RecipientType.TO);
		 
		    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
		        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		    }
		    message.setSubject("sending in a group");
		    message.setText("Welcome to JavaMail");
		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		    
		    enviar = true;
		    
	    }catch(Exception ex){	    	
	    	System.out.println("Error:aca.util.Cartero"+ex);
	    	enviar = false;
	    }
	    
	    return enviar;
	}
	
	public static void main(String[] args){		
		try{
			System.out.println("Inicio de envio...");
			Cartero mail = new Cartero();
			if (mail.enviarMensaje()){
				System.out.println("Se envio");
			}else{
				System.out.println("No se envió");
			}
		}catch(Exception ex){
	    	System.out.println("Error:aca.util.Cartero"+ex);
	    }
	}
}
