package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Prueba {
	
	public static void main(String[] args){
		String fechaData = "29/02/2021";
		try {
			java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
			Date fecha	 	= formatoFecha.parse(fechaData);
			Date hoy 		= formatoFecha.parse(aca.util.Fecha.getHoy());
			
			System.out.println(aca.util.Fecha.getDiasEntreFechas(aca.util.Fecha.getHoy(), fechaData));
		}catch(Exception ex) {
			
		}		
	}	
}