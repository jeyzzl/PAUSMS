package aca.util;

public class ModuloDiez {
 
	public static String invertirTexto(String matricula){
		StringBuilder textoInvertido = new StringBuilder();
		if (matricula.length()>0){
			for (int i=matricula.length()-1;i>=0;i--){
				textoInvertido.append(matricula.charAt(i));
			}
		}else{
			textoInvertido.append("");
		}
		
		return textoInvertido.toString();
	}
	
	public static String generarReferenciaSantander(String matricula){
		
		String matInversa = invertirTexto(matricula);
		StringBuilder resultado = new StringBuilder();
		
		// Resultado de multiplicaciones
		for (int i=0; i < matInversa.length();i++){
			
			// Si es par multiplica por 2 e impar multiplica por 1  
			if ((i % 2)== 0) 
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 2));
			else
				resultado.append( String.valueOf(Integer.parseInt(matInversa.substring(i, i+1)) * 1));
		}		
		
		// Suma los nxmeros del resultado
		int suma = 0;
		for (int i=0; i < resultado.toString().length(); i++){
			suma += Integer.parseInt( resultado.toString().substring(i, i+1));
		}
		
		// Obtiene el digito verificador
		int digito = 10 - (suma % 10);
		if (digito == 10) digito = 0;
		
		return String.valueOf(digito);
	}
	
 
    public static void main(String args[]) throws Exception {
    	ModuloDiez a = new ModuloDiez();
        System.out.println(a.generarReferenciaSantander("0740012"));
    }
}

