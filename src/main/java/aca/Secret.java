package aca;

import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Secret implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		String claveEncriptada = "";
        try {        	
        	MessageDigest md = MessageDigest.getInstance("SHA-512");
        	md.update(rawPassword.toString().getBytes("UTF-8"));
        	byte[] mb = md.digest();
        	claveEncriptada = java.util.Base64.getEncoder().encodeToString(mb);     	    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
		return claveEncriptada;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {		
		// TODO Auto-generated method stub
		if (encodedPassword!=null && encodedPassword.length() != 0) {
			String claveEncriptada = encode(rawPassword);
			System.out.println(rawPassword.toString()+" : "+claveEncriptada+" : "+encodedPassword);
			if (claveEncriptada.equals(encodedPassword)) {
				return true;
			}
		}
		return false;
	}	

}