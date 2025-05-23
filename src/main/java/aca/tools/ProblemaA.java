package aca.tools;
import java.util.*;
/**
3
HELL1235670WO1234591561580
PROGRAMMING037124670C123567123567156
AND MORE037124903735790278134573712467045612356735792781245612467278
*/
public class ProblemaA{
    public static void main(String args[]){
       
        Scanner teclado = new Scanner(System.in);
        System.out.print('\u000C');
        ArrayList<String> listaOriginal = new ArrayList<String>();
        ArrayList<String> listaCorrecta = new ArrayList<String>();
        
        int numCasos = Integer.parseInt(teclado.nextLine());     
        String fraseTemp = "";       
        int row = 0;
        do{
        	fraseTemp = teclado.nextLine();
        	listaOriginal.add(fraseTemp);
        	row++;        	
        }while(row < numCasos);
        
        for(String frase : listaOriginal){
            frase = frase.replace("1234567", "B");
            frase = frase.replace("123457", "A");
            frase = frase.replace("123567", "O");
            frase = frase.replace("123459", "R");
            frase = frase.replace("135790", "W");
            frase = frase.replace("12357", "M");
            frase = frase.replace("12456", "E");
            frase = frase.replace("12569", "G");
            frase = frase.replace("13459", "K");
            frase = frase.replace("13457", "H");
            frase = frase.replace("12347", "Q");
            frase = frase.replace("13567", "U");
            frase = frase.replace("23456", "Z");
            frase = frase.replace("12467", "S");
            frase = frase.replace("1580", "D");
            frase = frase.replace("1249", "F");
            frase = frase.replace("3567", "J");
            frase = frase.replace("3579", "N");
            frase = frase.replace("1458", "P");
            frase = frase.replace("1379", "V");
            frase = frase.replace("1347", "Y");
            frase = frase.replace("456", "C");
            frase = frase.replace("156", "L");
            frase = frase.replace("278", "T");
            frase = frase.replace("37", "I");
            frase = frase.replace("90", "X");
            frase = frase.replace("0", " ");
            listaCorrecta.add(frase);
            //System.out.println("Frase " + i + ":" + frase);            
        }       
        row=0;
        for (String fraseCorrecta :listaCorrecta) {
        	row++;
        	System.out.println("Frase "+row+" "+fraseCorrecta);
        }
        teclado.close();
    }
}
