package aca.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServAlumnoUtil {
	
	public ArrayList<ServAlumno> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<ServAlumno> lisAlumno	= new ArrayList<ServAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT" +
					" EVENTO_ID, CODIGO_PERSONAL," +
					" SEG_MEDICO," +
					" SEG_ACCIDENTE," +
					" DORMITORIO," +
					" COMEDOR," +
					" USUARIO" +
					" FROM ENOC.SERV_ALUMNO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				ServAlumno evento = new ServAlumno();
				evento.mapeaReg(rs);
				lisAlumno.add(evento);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisAlumno;
	}
	
	public ArrayList<String> getListServ(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisAlumno	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL, " +
					" ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD," +
					" (SELECT NOMBRE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ENOC.SERV_ALUMNO.CODIGO_PERSONAL) AS NOMBRE," +
					" (SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ENOC.SERV_ALUMNO.CODIGO_PERSONAL) AS APELLIDOS," +
					" ENOC.ALUM_CARRERA(CODIGO_PERSONAL) AS CARRERA," +
					" TO_CHAR( (SELECT FECHA_INICIO FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = ENOC.SERV_ALUMNO.EVENTO_ID), 'DD/MM/YYYY')  AS FECHA_INICIO, " +
					" TO_CHAR( (SELECT FECHA_FINAL FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = ENOC.SERV_ALUMNO.EVENTO_ID), 'DD/MM/YYYY') AS FECHA_FINAL," +
					" SEG_MEDICO, " +
					" SEG_ACCIDENTE, " +
					" ALUM_TIPOALUMNO(CODIGO_PERSONAL) AS TIPOALUMNO" +
					" FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.SERV_EVENTO WHERE now() BETWEEN FECHA_INICIO AND FECHA_FINAL) "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
					String codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
					String nombre 			= rs.getString("NOMBRE");
					String apellidos 		= rs.getString("APELLIDOS");
					String modalidad 		= rs.getString("MODALIDAD");
					String fInicio  		= rs.getString("FECHA_INICIO");
					String fFin  			= rs.getString("FECHA_FINAL");
					String nombreCarrera  	= rs.getString("CARRERA");
					String seguroMedico  	= rs.getString("SEG_MEDICO");
					String seguroAccidente  = rs.getString("SEG_ACCIDENTE");
					String tipoAlumno  		= rs.getString("TIPOALUMNO");
				
					lisAlumno.add(codigoPersonal+"@"+nombre+"@"+apellidos+"@"+modalidad+"@"+fInicio+"@"+fFin+"@"+nombreCarrera+"@"+seguroMedico+"@"+seguroAccidente+"@"+tipoAlumno);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisAlumno;
	}
	
	public HashMap<String,ServAlumno> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ServAlumno> map = new HashMap<String,ServAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT " +
					" EVENTO_ID, CODIGO_PERSONAL," +
					" SEG_MEDICO," +
					" SEG_ACCIDENTE," +
					" DORMITORIO" +		
					" COMEDOR" +
					" USUARIO" +
					" FROM ENOC.SERV_ALUMNO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ServAlumno obj = new ServAlumno();
				obj.mapeaReg(rs);
				llave = obj.getEventoId();
				llave += obj.getCodigoPersonal();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumnoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public ArrayList<ServAlumno> getListEvento(Connection conn, String eventoId, String orden ) throws SQLException{
		
		ArrayList<ServAlumno> lisAlumno	= new ArrayList<ServAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT" +
					" EVENTO_ID, CODIGO_PERSONAL," +
					" SEG_MEDICO," +
					" SEG_ACCIDENTE," +
					" DORMITORIO," +
					" COMEDOR," +
					" USUARIO" +
					" FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID =  "+eventoId+" "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				ServAlumno evento = new ServAlumno();
				evento.mapeaReg(rs);
				lisAlumno.add(evento);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumnoUtil|getListEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisAlumno;
	}
	
}

