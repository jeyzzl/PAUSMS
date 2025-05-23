package aca.util;

/*
 *  Nombres de los dias en letra para el sistema de los certificados
 * */

public class NumberToDay {		
	public String letra;	
	
	public NumberToDay(){
		letra = "";		
	}
	
	public NumberToDay(int n){
		letra = "";				
	}
	
	private String dia(int numero){
		
		switch (numero){
		case 1 : letra	= "primer"; break;
		case 2 : letra 	= "dos"; break;
		case 3 : letra 	= "tres"; break;
		case 4 : letra 	= "cuatro"; break;
		case 5 : letra 	= "cinco"; break;
		case 6 : letra 	= "seis"; break;
		case 7 : letra 	= "siete"; break;
		case 8 : letra 	= "ocho"; break;
		case 9 : letra 	= "nueve"; break;
		case 10: letra 	= "diez"; break;
		case 11: letra 	= "once"; break;
		case 12: letra 	= "doce"; break;
		case 13: letra 	= "trece"; break;
		case 14: letra 	= "catorce"; break;		
		case 15: letra 	= "quince"; break;
		case 16: letra 	= "diecis&eacute;is"; break;
		case 17: letra 	= "diecisiete"; break;
		case 18: letra 	= "dieciocho"; break;
		case 19: letra 	= "diecinueve"; break;
		case 20: letra 	= "veinte"; break;
		case 21: letra 	= "veinti&uacute;n"; break;
		case 22: letra 	= "veintid&oacute;s"; break;
		case 23: letra 	= "veintitr&eacute;s"; break;
		case 24: letra 	= "veinticuatro"; break;
		case 25: letra 	= "veinticinco"; break;
		case 26: letra 	= "veintiseis"; break;
		case 27: letra 	= "veintisiete"; break;
		case 28: letra 	= "veintiocho"; break;
		case 29: letra 	= "veintinueve"; break;
		case 30: letra 	= "treinta"; break;
		case 31: letra 	= "treinta y un"; break;
		}
		return letra;
	}	
	
	public static String convertirLetras(int numero){
		NumberToDay n2d = new NumberToDay();
		String num_letras = n2d.dia(numero);
		if(numero == 0)
			num_letras = "cero";
		return num_letras;
	} 	
}