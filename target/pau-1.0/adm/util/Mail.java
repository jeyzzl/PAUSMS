package adm.util;

import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;


public class Mail {
	
	public boolean SendMsgGmail( String from, String pass, String destino, String asunto, String texto){
		boolean enviar = false;
		
		try{
			// Configuración del correo de gmail
			String host = "smtp.gmail.com";
			
		    Properties props = System.getProperties();
		    props.put("mail.smtp.starttls.enable", "true"); // added this line
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", from);
		    props.put("mail.smtp.password", pass);
		    props.put("mail.smtp.port", "587"); //465 o 587
		    props.put("mail.smtp.auth", "true");
		    
		    // Quitar espacios en blanco			
			destino = destino.trim();
		    String[] to = destino.split(",");
		 
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
		    message.setSubject(asunto);
		    message.setText(texto);
		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		    enviar = true;
		    
	    }catch(Exception ex){
	    	System.out.println("Error:adm.util.Mail|"+ex);
	    	enviar = false;
	    }
	    return enviar;
	}
	

	public static void main(String[] args){
		try{
			System.out.println("Inicio de envio...");
			Mail mail = new Mail();
			String texto = "Gracias por aceptar ayudarnos en el procesod e admisión de Elier Obed Espinoza Zepeda."+
				"\n\nPor favor evalxe al aspirante seleccionando, para cada cualidad, la alternativa que mejor describa su realidad."+
				"\n\nSi considera que no tiene el conocimiento suficiente del candidato en alguna de las caracterxsticas mencionadas, elija la opcixn: No dispongo de informacixn suficiente"+
				"\n\nAgradecemos su colaboracixn en contestar y enviar esta informacixn lo antes posible. Si no lo puede hacer en este momento, por favor vuelva a esta pxgina haciendo clic en la liga del mensaje del correo."+
				"https://admision.um.edu.mx/admision/recomendacion?folio=3"+
				"\n\nEsta informacixn tendrx carxcter de reservada, anónima, y para uso exclusivo en el proceso de admisixn."+
				"\n\nMuchas gracias por su colaboracixn."+
				"\nOficina de Admisixn \nUniversidad de Montemorelos";
				
			
			if (mail.SendMsgGmail("admisionenlinea@um.edu.mx","ingresoum","erytorres@hotmail.com.mx,etorres@um.edu.mx", "Recomendacixn de Admisiones U.M.", texto)){
				System.out.println("Se envio");
			}else{
				System.out.println("No se envix");
			}
		}catch(Exception ex){
	    	System.out.println("Error: adm.util.Mail"+ex);
	    }
	}
	
}