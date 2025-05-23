package aca.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import javax.swing.*;


@SuppressWarnings({ "removal", "serial" })
public class Ubicacion extends JApplet {
 
	URL paginalocalizacion;
	BufferedReader entrada;
	String codigo,c;
	String ciudad,pais;

	public void init(){
		try {
			setBackground(Color.WHITE);
			   
			JPanel parriba=new JPanel();
			parriba.add(new JLabel("Ubicacion Geografica"));
			parriba.setBackground(Color.WHITE);
			add(parriba,BorderLayout.NORTH);
			   
			JPanel pdatos=new JPanel(new GridLayout(2,1));
			pdatos.setBackground(Color.WHITE);
			paginalocalizacion=new URL("http://whatismyipaddress.com");
			entrada=new BufferedReader(new InputStreamReader(paginalocalizacion.openStream()));
			c=entrada.readLine();
			while(c!=null){
				codigo=codigo+c;
				c=entrada.readLine();
			}
			ciudad=buscarCiudad();
			pais=buscarPais();
			pdatos.add(new JLabel("Ciudad:"));
			pdatos.add(new JLabel(""+ciudad+""));
			pdatos.add(new JLabel("Pais:"));
			pdatos.add(new JLabel(""+pais+""));
			JPanel paux=new JPanel();
			paux.setBackground(Color.WHITE);
			paux.add(pdatos);
			add(paux);
			JPanel pderecha=new JPanel();
			pderecha.setBackground(Color.WHITE);
			add(pderecha,BorderLayout.SOUTH);
		} catch (MalformedURLException e) {
		   // TODO Auto-generated catch block
		e.printStackTrace();
		} catch (IOException e) {
		   // TODO Auto-generated catch block
		e.printStackTrace();
		}
	  
	}

	private String buscarPais() {
		int inicio=0;
		int fin=0;
		for(int i=0;i<codigo.length();i++){
			if(codigo.charAt(i)=='C'){
				if(codigo.charAt(i+1)=='o'){
					if(codigo.charAt(i+2)=='u'){
						if(codigo.charAt(i+3)=='n'){
							if(codigo.charAt(i+4)=='t'){
								if(codigo.charAt(i+5)=='r'){
									if(codigo.charAt(i+6)=='y'){
										if(codigo.charAt(i+7)==':'){
											inicio=i+17;
											int x=inicio;
											while(codigo.charAt(x)!='<'){
												x++;
											}
											fin=x;
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return codigo.substring(inicio, fin);
	}

	private String buscarCiudad() {
		int inicio=0;
		int fin=0;
		for(int i=0;i<codigo.length();i++){
			if(codigo.charAt(i)=='C'){
				if(codigo.charAt(i+1)=='i'){
					if(codigo.charAt(i+2)=='t'){
						if(codigo.charAt(i+3)=='y'){
							if(codigo.charAt(i+4)==':'){
								inicio=i+14;
								int x=inicio;
								while(codigo.charAt(x)!='<'){
									x++;
								}
								fin=x;
								break;
							}
						}
					}
				}
			}
		}
		return codigo.substring(inicio, fin);
	}
 
}
