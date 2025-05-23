package aca.util;
import java.util.Scanner;

public class Fahrenheit{
	
	public static void main(String[] arg){
		Scanner teclado = new Scanner(System.in);
		//System.out.print('\u000C');
		int celsius;
		double fahrenheit;
		System.out.print("Temperatura en Celsius(°C): ");
		celsius = teclado.nextInt();
		fahrenheit = (1.8 * celsius) + 32;
		System.out.println("Temperatura en Fahrenheit (°F): "+fahrenheit);
		}
	}
