package aca.tools;


import javax.swing.*;


public class Curp1 {

	public static void main(String[] args) {
	
	    String nombre="",curp,curps,apellido;
	    String [] opc1={"NOMBRE","APELLIDO PATERNO","APELLIDO MATERNO","SEXO","DIA DE NACIMIENTO /DD","MES /MM","AÑO /AAAA"};
	    int i,res,a=0,b=1;	
	    i=0;
	    
		do{
			curp =JOptionPane.showInputDialog(opc1[i]);	
		
			opc1[i]= curp.substring(a,b);
			curps=opc1[1]+opc1[2]+opc1[0]+opc1[6]+opc1[4]+opc1[5]+opc1[3];
		
		
			if(i==0){ b=2; }
			if(i==1){ b--; }
			if(i==3){ b=2; a=0; }
			if(i==5){ a=2; b=4;	}
			i++;
		}while(i<7);
		
		i=0;
		JOptionPane.showMessageDialog(null,curps,"CURP",JOptionPane.INFORMATION_MESSAGE);	
	
	}
	
}