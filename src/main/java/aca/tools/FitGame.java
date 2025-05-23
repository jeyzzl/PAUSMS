package aca.tools;

import java.util.Scanner;
/**
HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
HHTTTHHTTTHTHHTHHTTHTTTHHHTHTTHTTHTTTHTH
HTHTHHHTHHHTHTHHHHTTTHTTTTTHHTTTTHTHHHHT
 */
public class FitGame{
    
    public static void main(String args[]){
        
        Scanner teclado = new Scanner(System.in);
        System.out.print('\u000C');
        
        String arreglo[] = {"TTT","TTH","THT","THH","HTT","HTH","HHT","HHH"}; 
        int cont[]= {0,0,0,0,0,0,0,0};
        
        int casos = teclado.nextInt();
        String codigoA[] = new String[casos];
        if(casos>=1 && casos<=1000){
            for(int z=0; z<casos; z++){
                codigoA[z] = teclado.nextLine();
            }
            System.out.println(" ");
            for(int h=0; h<casos; h++){
                for(int i=0; i<=codigoA[h].length()-3; i++){
                    String res = codigoA[h].substring(i,i+3);
                    for(int j=0; j<=7; j++){
                        if(res.equals(arreglo[j])){
                            cont[j] = cont[j] + 1;
                        }
                    }
                } 
                System.out.print((h+1) + " ");
                for(int j=0; j<=7; j++){
                   System.out.print(cont[j] + " ");
                   cont[j] = 0;
                }
                System.out.println(" ");
            }
        }
    }
}

