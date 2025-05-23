// Clase para el Reporte Hoja de Ruta

package  aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class HojaRutaUtil {
	
	public ArrayList<String> getListAlum (Connection conn, String codigo ) throws SQLException{
		
		ArrayList<String> lisHojaRuta		= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String datos			= "";
		
		try{
			comando = "SELECT "+  			
					"TO_CHAR(now(),'DD/MM/YYYY') AS FECHA, "+ 
					"TO_CHAR(now(),'HH24:MM:SS') AS HORA, "+
					"CASE A.SEXO WHEN 'M' THEN 'Masculino' ELSE 'Femenino' END AS SEXO, "+ 
					"TO_CHAR(A.F_NACIMIENTO,'DD/MM/YYYY') AS NACIMIENTO, "+ 
					"CASE A.ESTADO_CIVIL WHEN 'D' THEN 'Divorciado' WHEN 'C' THEN 'Casado' WHEN 'V' THEN 'Viudo' ELSE 'Soltero' END AS CIVIL, "+
					"COALESCE(A.CURP,'No Encontro') AS CURP, "+
					"ENOC.NOMBRE_RELIGION(A.RELIGION_ID) AS RELIGION, "+
					"CASE A.RESIDENCIA_ID WHEN 'I' THEN 'Interno' ELSE 'Externo' END AS RESIDENCIA, "+ 
					"ENOC.ALUM_TIPOALUMNO(A.CODIGO_PERSONAL) AS TIPO, "+
					"ENOC.MODALIDAD_NOMBRE(A.MODALIDAD_ID) AS MODALIDAD, "+
					"C.NACIONALIDAD, C.NOMBRE_PAIS,	ENOC.NOMBRE_CARRERA(A.CARRERA_ID) AS CARRERA "+
					"FROM ENOC.ALUMNO A, ENOC.CAT_PAIS C "+ 
					"WHERE A.PAIS_ID = C.PAIS_ID "+
					"AND A.CODIGO_PERSONAL = '"+codigo+"' ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				datos = rs.getString("FECHA")+"~"+rs.getString("HORA")+"~"+
						rs.getString("SEXO")+"~"+rs.getString("NACIMIENTO")+"~"+
						rs.getString("CIVIL")+"~"+rs.getString("CURP")+"~"+
						rs.getString("RELIGION")+"~"+rs.getString("RESIDENCIA")+"~"+
						rs.getString("TIPO")+"~"+rs.getString("MODALIDAD")+"~"+
						rs.getString("NACIONALIDAD")+"~"+rs.getString("NOMBRE_PAIS")+"~"+
						rs.getString("CARRERA");
				lisHojaRuta.add(datos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.HojaRutaUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHojaRuta;
	}

public String getTutor (Connection conn, String codigo ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String datos			= "";
		
		try{
			comando = "SELECT "+ 
				"CASE P.DIRECCION WHEN 'XXX' THEN 'No Encontro' ELSE P.DIRECCION END DIR, "+
				"COALESCE(P.EMAIL, 'No Encontro') EMAIL, "+
				" CASE P.BAUTIZADO WHEN 'N' THEN 'NO' ELSE 'SI' END AS BAUTIZADO, "+
				"COALESCE(P.TELEFONO, 'No Encontro') TEL, "+
				"ALUM_PLAN_ID(P.CODIGO_PERSONAL) PLAN, "+
				"U.T_NOMBRE, CASE U.T_TELEFONO WHEN 'X' THEN 'No Encontro' ELSE U.T_TELEFONO END AS T_TEL, "+ 
				"CASE U.T_EMAIL WHEN 'X' THEN 'No Encontro' ELSE U.T_EMAIL END AS T_EMAIL, "+
				"U.T_DIRECCION, U.T_COLONIA, "+
				"NOMBRE_CIUDAD(U.T_PAIS, U.T_ESTADO, U.T_CIUDAD) T_CIUDAD, "+
				"ENOC.NOMBRE_ESTADO(U.T_PAIS, U.T_ESTADO, U.T_CIUDAD) T_ESTADO, "+
				"ENOC.NOMBRE_PAIS(U.T_PAIS) T_PAIS, "+
				"CASE U.T_CODIGO WHEN 'X' THEN 'No Encontro' ELSE U.T_CODIGO END AS CP, "+ 
				"CASE U.T_APARTADO WHJEN 'X' THEN 'No Encontro' ELSE U.T_APARTADO END AS AP "+
				"FROM ENOC.ALUM_PERSONAL P, ENOC.ALUM_ACADEMICO A, ENOC.ALUM_UBICACION U  "+ 
				"WHERE P.CODIGO_PERSONAL = A.CODIGO_PERSONAL "+
				"AND P.CODIGO_PERSONAL = U.CODIGO_PERSONAL "+
				"AND P.CODIGO_PERSONAL = '"+codigo+"' ";

			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				datos = rs.getString("DIR")+"~"+rs.getString("EMAIL")+"~"+
						rs.getString("BAUTIZADO")+"~"+rs.getString("TEL")+"~"+
						rs.getString("PLAN")+"~"+rs.getString("T_NOMBRE")+"~"+
						rs.getString("T_TEL")+"~"+rs.getString("T_EMAIL")+"~"+
						rs.getString("T_DIRECCION")+"~"+rs.getString("T_COLONIA")+"~"+
						rs.getString("T_CIUDAD")+"~"+rs.getString("T_ESTADO")+"~"+
						rs.getString("T_PAIS")+"~"+	rs.getString("CP")+"~"+rs.getString("AP");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.HojaRutaUtil|getTutor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return datos;
	}
}