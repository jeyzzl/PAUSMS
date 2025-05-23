package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Horario {

	public static void main(String[] args) {
		try {
			String horario = "";
			int cont = 0;
			int error = 0;
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc",
					"caminacondios");
			PreparedStatement ps = null;
			ResultSet rs = null;

			ps = Conn.prepareStatement(" SELECT * FROM ENOC.CARGA_GRUPO");
			rs = ps.executeQuery();
			
			Statement sta = Conn.createStatement();

			while (rs.next()) {
				cont++;
				horario = rs.getString("HORARIO");
				String tmpHorario = "";

				while(horario.length()>0){
					tmpHorario += horario.substring(0, 49) + "XXXXXXXXXXXXXXXXXXXXX";
					horario = horario.substring(49);
				}

				if(sta.executeUpdate("UPDATE ENOC.CARGA_GRUPO SET HORARIO = '"+tmpHorario+"' WHERE CURSO_CARGA_ID = '"+rs.getString("CURSO_CARGA_ID")+"'")==1){
					System.out.println(cont+" de 39169");
				}
				else error++;
			}
			
			System.out.println("Errores: "+error);

			System.out.println("YA TERMINO ERY!!");
			
			if (Conn != null)
				Conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			String horario = "";
			int cont = 0;
			int error = 0;
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@172.16.254.20:1521:ora1", "enoc",
					"caminacondios");
			PreparedStatement ps = null;
			ResultSet rs = null;

			ps = Conn.prepareStatement(" SELECT * FROM ENOC.CARGA_GRUPO_HORARIO");
			rs = ps.executeQuery();
			
			Statement sta = Conn.createStatement();

			while (rs.next()) {
				cont++;
				horario = rs.getString("HORARIO");
				String tmpHorario = "";

				while(horario.length()>0){
					tmpHorario += horario.substring(0, 49) + "XXXXXXXXXXXXXXXXXXXXX";
					horario = horario.substring(49);
				}

				if(sta.executeUpdate("UPDATE ENOC.CARGA_GRUPO_HORARIO SET HORARIO = '"+tmpHorario+"' WHERE CURSO_CARGA_ID = '"+rs.getString("CURSO_CARGA_ID")+"' AND SALON_ID='"+rs.getString("SALON_ID")+"'")==1){
					System.out.println(cont+" de 24468");
				}
				else error++;
			}
			
			System.out.println("Errores: "+error);

			System.out.println("YA TERMINO ERY!!");
			
			if (Conn != null)
				Conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
