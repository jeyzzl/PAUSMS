package adm.util;
import java.util.Scanner;

public class Farenheit {
	
	public static void main (String [] ar){
		int celsius = 0;
		double farenheit = 0;
	
	
		Scanner teclado = new Scanner(System.in);
	
		System.out.println("Ingrese la temperatura en °C");
		celsius = teclado.nextInt();
		
		farenheit = (celsius * 1.8) + 32; 
		
		System.out.println("La temperatura es " + farenheit + " °F");
	
	}
}
