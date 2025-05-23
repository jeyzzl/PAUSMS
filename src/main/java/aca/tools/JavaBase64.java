package aca.tools;

import java.sql.SQLException;
import java.util.Base64;

public class JavaBase64 {
	
	public static void main(String[] args) throws SQLException {		
		try {
			byte[] bytes = "eryjr".getBytes("UTF-8");
			String encoded = Base64.getEncoder().encodeToString(bytes);
			System.out.println(encoded);
			byte[] decoded = Base64.getDecoder().decode(encoded);			
			System.out.println(decoded);
			
		}catch(Exception e){
			System.out.println(e);
		}
	}	

}
