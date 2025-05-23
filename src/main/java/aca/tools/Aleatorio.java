package aca.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aleatorio {
	
	public static void main(String[] args){

		Random random = new Random();
		List<Integer> lisNumeros = new ArrayList<Integer>();
		for (int i=0; i<100; i++) {
			lisNumeros.add(random.nextInt(10));			
		}
		
	}

}
