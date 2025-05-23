package aca.util;

public class ConvertirASCII {
	
	public static String convertir(String txt){
		
		String txtFinal = "";
		for(int j=0; j<txt.length(); j++){
			int dec = (int)txt.charAt(j);
			
			if(dec >= 48 && dec<=57){
				txtFinal += txt.charAt(j);
			}else if(dec >= 65 && dec<=90){
				txtFinal += txt.charAt(j);
			}else if(dec >= 97 && dec<=122){
				txtFinal += txt.charAt(j);
			}else{
				txtFinal += "&#"+dec+";";
			}

		}
		
		return txtFinal;
	}
	
	
}
