package aca.util;

public class Utilerias {
	public static String quitarLetrasEspeciales(String str){
		String texto = str;
		
		// Cambiar acentos en las minusculas
		texto = texto.replace("á", "a");
		texto = texto.replace("é", "e");
		texto = texto.replace("í", "i");
		texto = texto.replace("ó", "o");
		texto = texto.replace("ú", "u");
		// Cambiar acentos en las mayusculas
		texto = texto.replace("Á", "A");
		texto = texto.replace("É", "E");
		texto = texto.replace("Í", "I");		
		texto = texto.replace("Ó", "O");
		texto = texto.replace("Ú", "U");
		
		// otros
		texto = texto.replace("´", "");
		texto = texto.replace("À", "A");
		texto = texto.replace("Ì", "I");
		texto = texto.replace("Ò", "O");
		
		return texto;
	}
}