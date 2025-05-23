package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class HorarioTraspaso {

	public static void main(String[] args) {
		String comando 		= "";
		try {			
			int cont = 0;
			int error = 0;			
			
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection( "jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc", "caminacondios");
			PreparedStatement ps 	= null;
			ResultSet rs 			= null;
			ResultSet rsExiste		= null;
			
			java.util.HashMap<String, String> mapHorario = aca.catalogo.CatHorarioUtil.mapHorario(Conn);

			ps = Conn.prepareStatement(" SELECT" +
					" CURSO_CARGA_ID, SALON_ID," +
					" (SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) AS FACULTAD_ID," +
					" SUBSTR(HORARIO,1,70) AS MATUTINO, SUBSTR(HORARIO,71,70) AS VESPERTINO, SUBSTR(HORARIO,141,70) AS NOCTURNO" +
					" FROM ENOC.CARGA_GRUPO_HORARIO CGH WHERE CURSO_CARGA_ID LIKE '0506%'" +
					" AND (SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) IS NOT NULL");
			rs = ps.executeQuery();
			
			Statement st1 = Conn.createStatement();
			Statement st2 = Conn.createStatement();

			while (rs.next()) {
				cont++;
				int dia=0;
				int periodo = 0;
				String cursoCargaId	= rs.getString("CURSO_CARGA_ID");
				String salon		= rs.getString("SALON_ID");
				String facultad		= rs.getString("FACULTAD_ID");
				String horarioMat 	= rs.getString("MATUTINO");
				String horarioVes 	= rs.getString("VESPERTINO");
				String horarioNoc 	= rs.getString("NOCTURNO");
				
				String horarioId	= "";				
				String hora = "";
				
				
				// Periodos del horario Matutino
				for(int i=0; i<horarioMat.length();i++){
					hora = String.valueOf(horarioMat.charAt(i));
					if (hora.equals("1")){
						// Dia del  
						dia = (i % 7);
						if (dia==0) dia = 7;
						
						if (i>=0 && i<=6) 	periodo = 1;
						if (i>=7 && i<=13)	periodo = 2;
						if (i>=14 && i<=20) periodo = 3;
						if (i>=21 && i<=27) periodo = 4;
						if (i>=28 && i<=34) periodo = 5;
						if (i>=35 && i<=41) periodo = 6;
						if (i>=42 && i<=48) periodo = 7;
						if (i>=49 && i<=55) periodo = 8;
						if (i>=56 && i<=62) periodo = 9;
						if (i>=63 && i<=70) periodo = 10;
						
						if (mapHorario.containsKey(facultad)){
							horarioId = mapHorario.get(facultad);
							comando = "SELECT * FROM ENOC.CARGA_GRUPO_HORA " +
									" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
									" AND SALON_ID = '"+salon+"'" +
									" AND DIA = "+dia+
									" AND HORARIO_ID = "+horarioId+
									" AND PERIODO = "+periodo;
							rsExiste = st2.executeQuery(comando);
							if (!rsExiste.next()){
								
								comando = "INSERT INTO ENOC.CARGA_GRUPO_HORA(CURSO_CARGA_ID, SALON_ID, DIA, HORARIO_ID, PERIODO)" +
										" VALUES('"+cursoCargaId+"','"+salon+"',"+dia+","+horarioId+","+periodo+")";
								if(st1.executeUpdate(comando)==1){
									Conn.commit();
								}else 
									error++;
							}else{
								System.out.println("Horario duplicado: "+comando);
							}
						}else{
							System.out.println("No existe el horario de "+facultad);
						}	
						
						//System.out.println("Matutino:"+cursoCargaId+":"+salon+":"+facultad+":"+horarioId+":"+(i+1)+":"+dia+":"+periodo);
					}
				}
				
				// Periodos del horario Vespertino
				for(int i=0; i<horarioVes.length();i++){
					hora = String.valueOf(horarioVes.charAt(i));
					
					if (hora.equals("1")){
						// Dia
						dia = ((i+1) % 7);
						if (dia==0) dia = 7;
						
						if (i>=0 && i<=6) periodo = 11;
						if (i>=7 && i<=13) periodo = 12;
						if (i>=14 && i<=20) periodo = 13;
						if (i>=21 && i<=27) periodo = 14;
						if (i>=28 && i<=34) periodo = 15;
						if (i>=35 && i<=41) periodo = 16;
						if (i>=42 && i<=48) periodo = 17;
						if (i>=49 && i<=55) periodo = 18;
						if (i>=56 && i<=62) periodo = 19;
						if (i>=63 && i<=70) periodo = 20;
						
						if (mapHorario.containsKey(facultad)){ 
							horarioId = mapHorario.get(facultad);
							
							comando = "SELECT * FROM ENOC.CARGA_GRUPO_HORA " +
									" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
									" AND SALON_ID = '"+salon+"'" +
									" AND DIA = "+dia+
									" AND HORARIO_ID = "+horarioId+
									" AND PERIODO = "+periodo;
							rsExiste = st2.executeQuery(comando);
							if (!rsExiste.next()){
								
								comando = "INSERT INTO ENOC.CARGA_GRUPO_HORA(CURSO_CARGA_ID, SALON_ID, DIA, HORARIO_ID, PERIODO)" +
										" VALUES('"+cursoCargaId+"','"+salon+"',"+dia+","+horarioId+","+periodo+")";
								if(st1.executeUpdate(comando)==1){
									Conn.commit();
								}else 
									error++;
							}else{
								System.out.println("Horario duplicado: "+comando);
							}
							
						}else{
							System.out.println("No existe el horario de "+facultad);
						}	
						
						//System.out.println("Vespertino:"+cursoCargaId+":"+salon+":"+facultad+":"+horarioId+":"+(i+1)+":"+dia+":"+periodo);
					}
				}
				
				// Periodos del horario Nocturno
				for(int i=0; i<horarioNoc.length();i++){
					hora = String.valueOf(horarioNoc.charAt(i));
					if (hora.equals("1")){
						// Dia del  
						dia = (i % 7);
						if (dia==0) dia = 7;
						
						if (i>=0 && i<=6) periodo = 21;
						if (i>=7 && i<=13) periodo = 22;
						if (i>=14 && i<=20) periodo = 23;
						if (i>=21 && i<=27) periodo = 24;
						if (i>=28 && i<=34) periodo = 25;
						if (i>=35 && i<=41) periodo = 26;
						if (i>=42 && i<=48) periodo = 27;
						if (i>=49 && i<=55) periodo = 28;
						if (i>=56 && i<=62) periodo = 29;
						if (i>=63 && i<=70) periodo = 30;
						
						if (mapHorario.containsKey(facultad)){
							horarioId = mapHorario.get(facultad);
							
							comando = "SELECT * FROM ENOC.CARGA_GRUPO_HORA " +
									" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
									" AND SALON_ID = '"+salon+"'" +
									" AND DIA = "+dia+
									" AND HORARIO_ID = "+horarioId+
									" AND PERIODO = "+periodo;
							rsExiste = st2.executeQuery(comando);
							if (!rsExiste.next()){
								
								comando = "INSERT INTO ENOC.CARGA_GRUPO_HORA(CURSO_CARGA_ID, SALON_ID, DIA, HORARIO_ID, PERIODO)" +
										" VALUES('"+cursoCargaId+"','"+salon+"',"+dia+","+horarioId+","+periodo+")";
								if(st1.executeUpdate(comando)==1){
									Conn.commit();
								}else 
									error++;
							}else{
								System.out.println("Horario duplicado: "+comando);
							}
							
						}else{
							System.out.println("No existe el horario de "+facultad);
						}	
					
						//System.out.println("Nocturno:"+cursoCargaId+":"+salon+":"+facultad+":"+horarioId+":"+(i+1)+":"+dia+":"+periodo);
					}
				}
				if ((cont%200)==0) System.out.print("200-");
				
			}
			
			System.out.println("Errores: "+error);		
			
			if (Conn != null) Conn.close();

		} catch (Exception e) {
			System.out.println(e+":"+comando);
		}	
		
	}

}
