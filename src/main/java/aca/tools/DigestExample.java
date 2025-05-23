package aca.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class DigestExample {
	
	public String EjemploSHA256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		    
		for(byte b : hash) {        
			sb.append(String.format("%02x", b));
		}
		    
		return sb.toString();
	}
	
	public static void main(String[] args) {    		
		
		MessageDigest md = null;
		String password = "MiClave";
		String password2 = "OtraClave";		
	        try {
	            //SHA-512
	            md= MessageDigest.getInstance("SHA-512");
	            md.update(password.getBytes());
	            byte[] mb = md.digest();
	            System.out.println(java.util.Base64.getEncoder().encodeToString(mb));
	            //System.out.println(Hex.encodeHex(mb));
	            
	            //SHA-256
	            md= MessageDigest.getInstance("SHA-512");
	            md.update(password2.getBytes());
	            mb = md.digest();
	            System.out.println(java.util.Base64.getEncoder().encodeToString(mb));
	            //System.out.println(Hex.encodeHex(mb));               
	           
	            
	            //MD5
	            md= MessageDigest.getInstance("MD5");
	            md.update(password.getBytes());
	            mb = md.digest();
	            System.out.println(java.util.Base64.getEncoder().encodeToString(mb));
	            
	            md= MessageDigest.getInstance("MD5");
	            md.update(password2.getBytes());
	            mb = md.digest();
	            System.out.println(java.util.Base64.getEncoder().encodeToString(mb));            
	            
	            
	        } catch (NoSuchAlgorithmException e) {
	            //Error
	        }
	    }
}