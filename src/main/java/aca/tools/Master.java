package aca.tools;
import java.sql.*;

import aca.alumno.AlumPersonal;

public class Master {
	
	public static void main(String[] args) {
		
		try{			
			Connection Conn = null;				
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
			System.out.println("CONECTADO..xx");
			
			PreparedStatement ps	= null;
			PreparedStatement ps2	= null;
			ResultSet rs 			= null;
			ResultSet rs2 			= null;
			String planes 			= "";
			String inscrito 		= "";
			
			// 
			ps = Conn.prepareStatement("SELECT " +
					"CODIGO_PERSONAL, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL) AS NOMBRE " +
					"FROM ENOC.ALUM_PLAN " + 
					"WHERE ENOC.CARRERA(PLAN_ID) IN " +
						"(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE NIVEL_ID = '3') "+ 
					"INTERSECT " +
					"SELECT " +
					"CODIGO_PERSONAL, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL) AS NOMBRE " +
					"FROM ENOC.ALUM_PLAN " + 
					"WHERE PLAN_ID IN ('TEO2000*','RELI1995','TEO1995*','RELI1970','TEOL1970')");
			rs = ps.executeQuery();			
			ps2 = Conn.prepareStatement("SELECT PLAN_ID, ESTADO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?"); 
			while (rs.next()){ planes = "";				
				
				ps2.setString(1,rs.getString("CODIGO_PERSONAL"));
				rs2 = ps2.executeQuery();
				while (rs2.next()){
					if (rs2.getString("ESTADO").equals("1")){
						planes += "["+rs2.getString("PLAN_ID")+"]-";
					}else{
						planes += rs2.getString("PLAN_ID")+"-";
					}	
				}
				if (aca.alumno.AlumUtil.esInscrito(Conn,rs.getString("CODIGO_PERSONAL"))) inscrito = "INSCRITO"; else inscrito = "INACTIVO";
				
				System.out.println(rs.getString("CODIGO_PERSONAL")+"~"+rs.getString("NOMBRE")+"~"+planes+"~"+inscrito);
			}
			
			System.out.println("ALUMNOS INSCRITOS EN MAESTRIA SIN REFERENCIA DE LICENCIATURA");
			ps = Conn.prepareStatement("SELECT " +
					"CODIGO_PERSONAL, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL) AS NOMBRE " +
					"FROM ENOC.ALUM_PLAN " + 
					"WHERE ESTADO = '1' " +
					"AND ENOC.CARRERA(PLAN_ID) IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE NIVEL_ID = '3') " + 
					"AND CODIGO_PERSONAL NOT IN " +
					"(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE ENOC.CARRERA(PLAN_ID) IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE NIVEL_ID = '2')) " + 
					"AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)");
			rs = ps.executeQuery();			
			ps2 = Conn.prepareStatement("SELECT PLAN_ID, ESTADO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?"); 
			while (rs.next()){ planes = "";				
				
				ps2.setString(1,rs.getString("CODIGO_PERSONAL"));
				rs2 = ps2.executeQuery();
				while (rs2.next()){
					if (rs2.getString("ESTADO").equals("1")){
						planes += "["+rs2.getString("PLAN_ID")+"]-";
					}else{
						planes += rs2.getString("PLAN_ID")+"-";
					}	
				}
				if (aca.alumno.AlumUtil.esInscrito(Conn,rs.getString("CODIGO_PERSONAL"))) inscrito = "INSCRITO"; else inscrito = "INACTIVO";
				
				System.out.println(rs.getString("CODIGO_PERSONAL")+"~"+rs.getString("NOMBRE")+"~"+planes+"~"+inscrito);
			}
			
			if (ps !=null) ps.close();
			if (ps2!=null) ps2.close();
			if (rs !=null) rs.close();
			if (rs2!=null) rs2.close();
			
			Conn.commit();
			Conn.close();
			
			System.out.println("Fin");
			
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}