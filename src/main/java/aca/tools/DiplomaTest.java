package aca.tools;

public class DiplomaTest {
	
	public static void main(String[] args) {
		try{
			String clave= aca.util.Encriptar.sha256ConBase64("250674"+"shareDiploma");
			System.out.println(clave);		}catch(Exception e){
			System.out.println(e);			
		}		
	}
	
}