package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Curp {
	
	public static String getCurp( String nom, String pat, String mat, String gen, String date, int paisId, int estadoId){
		String curp			= "X";
		
		try{
			
			String nombreOri	= nom;
			String paternoOri	= pat;
			String maternoOri	= mat;			
			String fecha		= date;
			String genero 		= gen;
			int pais 			= paisId;
			int estado 			= estadoId;
			
			String nombre 		= nombreOri;
			String paterno 		= paternoOri;
			String materno		= maternoOri;			
			String vocales		= "AEIOU";
			String letra		= "";
			
			int largo 			= 0;
			int i 				= 0;			
			boolean ok			= false;			
			
			/*
			 * QUITAR TODOS LOS ACENTOS
			 * */
			nombre = nombre.replaceAll("Á", "A");			nombre = nombre.replaceAll("É", "E");
			nombre = nombre.replaceAll("Í", "I");			nombre = nombre.replaceAll("Ó", "O");
			nombre = nombre.replaceAll("Ú", "U");			nombre = nombre.replaceAll(" ", "X");
			
			/*
			 * QUITAR LAS PALABRAS QUE SE OMITEN EN EL NOMBRE 
			 * */
			nombre = nombre.replaceAll("MARIA ", " ");		nombre = nombre.replaceAll("MA ", " ");
			nombre = nombre.replaceAll("MA. ", " ");		nombre = nombre.replaceAll("JOSE ", " ");	
			nombre = nombre.replaceAll("J ", " ");			nombre = nombre.replaceAll("J. ", " ");
			nombre = nombre.replaceAll("MC "," ");			nombre = nombre.replaceAll("MC. "," ");
			nombre = nombre.trim();
			if (nombre.length()==0) nombre = nombreOri;
			
			/*
			 * QUITAR LAS PREPOSICIONES DEL APELLIDO PATERNO
			 * */
			paterno = paterno.replaceAll("DA ", " ");		paterno = paterno.replaceAll("DAS ", " ");
			paterno = paterno.replaceAll("DE ", " ");		paterno = paterno.replaceAll("DEL ", " ");
			paterno = paterno.replaceAll("DER ", " ");		paterno = paterno.replaceAll("DI ", " ");
			paterno = paterno.replaceAll("DIE ", " ");		paterno = paterno.replaceAll("DD ", " ");
			paterno = paterno.replaceAll("EL ", " ");		paterno = paterno.replaceAll("LA ", " ");
			paterno = paterno.replaceAll("LOS ", " ");		paterno = paterno.replaceAll("LAS ", " ");
			paterno = paterno.replaceAll("LE ", " ");		paterno = paterno.replaceAll("LES ", " "); 
			paterno = paterno.replaceAll("MAC ", " ");		paterno = paterno.replaceAll("VAN ", " ");
			paterno = paterno.replaceAll("VON ", " ");		paterno = paterno.replaceAll("Y ", " ");
			paterno = paterno.replaceAll("Ü", "U");			paterno = paterno.replaceAll(" ", "X");
			paterno = paterno.trim();
			if (paterno.length()==0) paterno = paternoOri;
			
			/*
			 * QUITAR LAS PREPOSICIONES DEL APELLIDO MATERNO
			 * */
			materno = materno.replaceAll("DA ", " ");		materno = materno.replaceAll("DAS ", " ");			
			materno = materno.replaceAll("DE ", " ");		materno = materno.replaceAll("DEL ", " ");
			materno = materno.replaceAll("DER ", " ");		materno = materno.replaceAll("DI ", " ");
			materno = materno.replaceAll("DIE ", " ");		materno = materno.replaceAll("DD ", " ");
			materno = materno.replaceAll("EL ", " ");		materno = materno.replaceAll("LA ", " ");
			materno = materno.replaceAll("LOS ", " ");		materno = materno.replaceAll("LAS ", " ");
			materno = materno.replaceAll("LE ", " ");		materno = materno.replaceAll("LES ", " ");
			materno = materno.replaceAll("MAC ", " ");		materno = materno.replaceAll("VAN ", " ");
			materno = materno.replaceAll("VON ", " ");		materno = materno.replaceAll("Y ", " ");
			materno = materno.replaceAll("Ü", "U");			materno = materno.replaceAll(" ", "X");
			materno = materno.trim();	
			if (materno.length()==0) materno = maternoOri;
			
			/*
			 * OBTIENE LOS PRIMEROS CUATRO CARACTERES
			 * */
			curp = paterno.substring(0, 1);
			
			largo = paterno.length(); i=1;
			while (i<largo && ok==false ){
				letra 	= paterno.substring(i, i+1);
				if (vocales.indexOf(letra)>=0){ ok = true; curp += letra; }
				i++;
			}
			if (ok==false) curp += "X";
				
			if ( materno.length()>0 ){
				if (materno.substring(0, 1).equals(" "))
					curp += "X"; 
				else
					curp += materno.substring(0, 1);
				
			}else{
				curp += "X";
			}			
			curp += nombre.substring(0, 1);
			
			/*
			 * OBTENER LOS CARACTERES 5-10
			 * */
			curp += fecha.substring(8, 10)+fecha.substring(3, 5)+fecha.substring(0, 2);
			
			if (genero.equals("M")) genero = "H"; else genero= "M";
			curp += genero;
			
			if (pais!=91){
				curp+= "NE";
			}else{
				switch(estado){
					case  1:{ curp +=  "AS"; break; }
					case  2:{ curp +=  "BC"; break; }
					case  3:{ curp +=  "BS"; break; }
					case  4:{ curp +=  "CC"; break; }
					case  5:{ curp +=  "CL"; break; }
					case  6:{ curp +=  "CM"; break; }
					case  7:{ curp +=  "CS"; break; }
					case  8:{ curp +=  "CH"; break; }
					case  9:{ curp +=  "DF"; break; }
					case 10:{ curp +=  "DG"; break; }
					case 11:{ curp +=  "GT"; break; }
					case 12:{ curp +=  "GR"; break; }
					case 13:{ curp +=  "HG"; break; }
					case 14:{ curp +=  "JC"; break; }
					case 15:{ curp +=  "MC"; break; }
					case 16:{ curp +=  "MN"; break; }
					case 17:{ curp +=  "MS"; break; }
					case 18:{ curp +=  "NT"; break; }
					case 19:{ curp +=  "NL"; break; }
					case 20:{ curp +=  "OC"; break; }		
					case 21:{ curp +=  "PL"; break; }
					case 22:{ curp +=  "QT"; break; }
					case 23:{ curp +=  "QR"; break; }
					case 24:{ curp +=  "SP"; break; }
					case 25:{ curp +=  "SL"; break; }
					case 26:{ curp +=  "SR"; break; }
					case 27:{ curp +=  "TC"; break; }
					case 28:{ curp +=  "TS"; break; }
					case 29:{ curp +=  "TL"; break; }
					case 30:{ curp +=  "VZ"; break; }
					case 31:{ curp +=  "YN"; break; }
					case 32:{ curp +=  "ZS"; break; }
				}
			}
			
			/*
			 * BUSCA LA CONSONANTE INTERNA DEL APELLIDO PATERNO
			 * */
			ok = false; i=1;
			largo = paterno.length(); 
			while (i<largo && ok==false ){
				letra 	= paterno.substring(i, i+1);
				if (vocales.indexOf(letra) == -1){ ok = true; curp += letra; }
				i++;
			}
			if ( ok==false) curp += "X";
			
			/*
			 * BUSCA LA CONSONANTE INTERNA DEL APELLIDO MATERNO
			 * */
			if ( materno.length()>0 ){
				ok = false; i=1;
				largo = materno.length();
				while (i<largo && ok==false ){
					letra 	= materno.substring(i, i+1);
					if (vocales.indexOf(letra) == -1){ ok = true; curp +=  letra; }
					i++;
				}
				if (ok==false) curp += "X";
			}else{
				curp += "X";
			}
			
			/*
			 * BUSCA LA CONSONANTE INTERNA DEL APELLIDO MATERNO
			 * */
			if ( nombre.length()>0 ){
				ok = false; i=1;
				largo = nombre.length();
				while (i<largo && ok==false ){
					letra 	= nombre.substring(i, i+1);
					if (vocales.indexOf(letra) == -1){ ok = true; curp +=  letra; }
					i++;
				}
				if (ok==false) curp += "X";
			}else{
				curp += "X";
			}
			/*
			 * AGREGAR DIGITO 17(HOMONIMIA) Y 18(DIGITO VERIFICADOR).
			 * */
			curp += "00";			
			
		}catch(Exception e){
			System.out.println(e);
		}	
		
		return curp.toUpperCase();
	}
	
	public static void main(String[] args) {
		
		try{
			String curp = "x"; 
			int cont 	= 0;
			int error 	= 0;
			
			Connection Conn = null;						
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps =null;
			PreparedStatement ps2 =null;
			ResultSet rs = null;
			
			ps = Conn.prepareStatement("SELECT "+
					"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO, SEXO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS FECHA, PAIS_ID, ESTADO_ID "+
					"FROM ENOC.ALUM_PERSONAL WHERE LENGTH(TRIM(CURP)) <= 15"); 
			rs = ps.executeQuery();			
			ps2 = Conn.prepareStatement("UPDATE ENOC.ALUM_PERSONAL SET CURP = ? WHERE CODIGO_PERSONAL = ?"); 
			
			while (rs.next()){ cont++;
				curp = getCurp(rs.getString("NOMBRE"), rs.getString("APELLIDO_PATERNO"), rs.getString("APELLIDO_MATERNO"),rs.getString("SEXO"),rs.getString("FECHA"),rs.getInt("PAIS_ID"),rs.getInt("ESTADO_ID"));
				ps2.setString(1, curp);
				ps2.setString(2, rs.getString("CODIGO_PERSONAL"));
				if (ps2.executeUpdate()==1){
					System.out.println("Row:"+cont+":"+rs.getString("NOMBRE")+":"+ rs.getString("APELLIDO_PATERNO")+":"+rs.getString("APELLIDO_MATERNO")+":"+curp);
					Conn.commit();
				}else{
					error++;
				}				
			}
			System.out.println("Cont:"+cont);
			if (Conn!=null) Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}
}