package aca.util;

public class Certificados {
	public static boolean isFstFsp(String planId){
		if(planId!=null){
			if(!planId.contains("10") && !planId.contains("11")){
				return true;
			}
		}
		return false;
	}
}
