//Clase para la tabla de Modulo
package aca.alumno;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumEgresoUtil{	
	
	public boolean insertReg(Connection conn, AlumEgreso alumEgreso ) throws Exception{
		PreparedStatement ps 	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_EGRESO"
 					+ " (EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, PROMEDIO, AVANCE,"
 					+ " TITULADO, FECHA, USUARIO)"
 					+ " VALUES( TO_NUMBER(?, '999'), ?, ?, ?, TO_NUMBER(?, '999.99'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?)");			
 			ps.setString(1, alumEgreso.getEventoId());
 			ps.setString(2, alumEgreso.getCodigoPersonal());
 			ps.setString(3, alumEgreso.getCarreraId());
 			ps.setString(4, alumEgreso.getPlanId());		
 			ps.setString(5, alumEgreso.getPromedio());
 			ps.setString(6, alumEgreso.getAvance());
 			ps.setString(7, alumEgreso.getTitulado());
 			ps.setString(8, alumEgreso.getFecha()); 			
 			ps.setString(9, alumEgreso.getUsuario());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn, AlumEgreso alumEgreso ) throws Exception{ 		
 		PreparedStatement ps 	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ALUM_EGRESO SET"
 					+ " CARRERA_ID = ?,"
 					+ " PLAN_ID = ?,"
 					+ " PROMEDIO = TO_NUMBER(?, '999.99'),"
 					+ " AVANCE = ?,"
 					+ " TITULADO = ?,"
 					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
 					+ " USUARIO = ?"
 					+ " WHERE EVENTO_ID = TO_NUMBER(?, '999')"
 					+ " AND CODIGO_PERSONAL = ?");
 				
 			ps.setString(1, alumEgreso.getCarreraId());
 			ps.setString(2, alumEgreso.getPlanId()); 			
 			ps.setString(3, alumEgreso.getPromedio());
 			ps.setString(4, alumEgreso.getAvance());
 			ps.setString(5, alumEgreso.getTitulado());
 			ps.setString(6, alumEgreso.getFecha()); 			
 			ps.setString(7, alumEgreso.getUsuario());
 			ps.setString(8, alumEgreso.getEventoId()); 			
 			ps.setString(9, alumEgreso.getCodigoPersonal());
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false; 			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|updateReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	 	
 	public boolean deleteReg(Connection conn, String eventoId, String codigoPersonal ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE EVENTO_ID = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? ");
 			ps.setString(1, eventoId);
 			ps.setString(2, codigoPersonal);			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public AlumEgreso mapeaRegId( Connection conn, String eventoId, String codigoPersonal ) throws SQLException, IOException{
 		AlumEgreso alumEgreso = new AlumEgreso();
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO"
	 				+ " FROM ENOC.ALUM_EGRESO"
	 				+ " WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?");
	 		ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			alumEgreso.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return alumEgreso;
 	}
	
 	public boolean existeReg(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = ?");
 			ps.setString(1, codigoPersonal);
			ps.setString(2, eventoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|existeReg|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean existeAlumno(Connection conn, String codigoPersonal) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|existeReg|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public boolean existeAlumnoPlan(Connection conn, String codigoPersonal, String planId) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO"+ 
 				" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PLAN_ID = '"+planId+"' ");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|existeAlumnoPlan|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static String getPais( Connection conn, String codigoPersonal) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String pais		= "";
		
		try{
			ps = conn.prepareStatement("SELECT ENOC.NOMBRE_PAIS(ALUM_PAIS('"+codigoPersonal+"')) AS PAIS"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next()){
				pais = rs.getString("PAIS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return pais;
	} 
 	
 	public static String getEstado( Connection conn, String codigoPersonal) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String estado		= "";
		
		try{
			ps = conn.prepareStatement("SELECT ALUMNO_ESTADO('"+codigoPersonal+"') AS ESTADO " +
					"FROM ENOC.ALUM_EGRESO " + 
					"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				estado = rs.getString("ESTADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	} 
 	
 	public static int getNumGenero( Connection conn, String eventoId, String genero ) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numAlumnos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS GENERO" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE SEXO = ?)"); 
			ps.setString(1, eventoId);
			ps.setString(2, genero);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numAlumnos = rs.getInt("GENERO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumGenero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlumnos;
	}
 	
 	public static int getNumMexicanos( Connection conn, String eventoId ) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numAlumnos		= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS MEXICANOS" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE NACIONALIDAD = 91)"); 
			ps.setString(1, eventoId);			
			
			rs = ps.executeQuery();
			if (rs.next()){
				numAlumnos = rs.getInt("MEXICANOS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumMexicanos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlumnos;
	}
 	
 	public static int getNumNacidosPais( Connection conn, String eventoId, String paisId ) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numAlumnos		= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE PAIS_ID = "+paisId+" )");
			ps.setString(1, eventoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numAlumnos = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumNacidosPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlumnos;
	}
 	
 	public static int getNumResidencia( Connection conn, String eventoId, String residencia ) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numAlumnos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS RESIDENCIA" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE RESIDENCIA_ID = ?)"); 
			ps.setString(1, eventoId);
			ps.setString(2, residencia);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numAlumnos = rs.getInt("RESIDENCIA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumResidencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlumnos;
	}
 	
 	public static int getNumEdoCivil( Connection conn, String eventoId, String estado ) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numAlumnos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS ESTADO" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE ESTADO_CIVIL = ?)"); 
			ps.setString(1, eventoId);
			ps.setString(2, estado);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numAlumnos = rs.getInt("ESTADO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumEdoCivil|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlumnos;
	}
 	
 	public static int getNumGraduandosPorPais(Connection conn, String pais, String evento) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numGraduandos		= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS NUMERO FROM ENOC.ALUM_EGRESO " + 
					" WHERE EVENTO_ID = '"+evento+"' " +
					" AND ALUM_NACIONALIDAD(CODIGO_PERSONAL) = ? ");
			ps.setString(1, pais);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numGraduandos = rs.getInt("NUMERO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumGraduandosPorPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numGraduandos;
	}
 	
 	public static int getNumGraduandosPorEstado(Connection conn, String NombreEstado, String evento) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		int numGraduandos		= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS NUMERO FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = '"+evento+"' " + 
					" AND ALUM_NACIONALIDAD(CODIGO_PERSONAL) = 91 " +
					" AND ALUMNO_ESTADO(CODIGO_PERSONAL) = ? ");
			ps.setString(1, NombreEstado);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numGraduandos = rs.getInt("NUMERO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getNumGraduandosPorEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numGraduandos;
	}
 	
 	public static String getEvento(Connection conn, String codigoPersonal, String planId) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String eventoId			= "";
		
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " + 
					" AND PLAN_ID = '"+planId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next()){
				eventoId = rs.getString("EVENTO_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return eventoId;
	}
 	
 	public static boolean esAlumnoGraduando(Connection conn, String codigoPersonal, String eventoId) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO"
 					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
 					+ " AND EVENTO_ID = '"+eventoId+"'");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|esAlumnoGraduando|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
	
	public static ArrayList<AlumEgreso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisAlumno	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, PROMEDIO," +
 				" AVANCE, TITULADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO " +
 				" FROM ENOC.ALUM_EGRESO "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEgreso alumno = new AlumEgreso();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public ArrayList<AlumEgreso> getListaEvento(Connection conn, String Evento_Id, String Orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisAlumno	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, PROMEDIO, AVANCE, TITULADO, FECHA, USUARIO"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = "+Evento_Id+" "+Orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEgreso Alumno = new AlumEgreso();
				Alumno.mapeaReg(rs);
				lisAlumno.add(Alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListaEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	public ArrayList<AlumEgreso> getListAlum(Connection conn, String Codigo_Personal, String Orden ) throws SQLException{
		
		ArrayList<AlumEgreso> lisPersonal	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA, TITULADO, USUARIO FROM ENOC.ALUM_EGRESO"+ 
			" WHERE CODIGO_PERSONAL = '"+Codigo_Personal+"' "+Orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumEgreso Alumno = new AlumEgreso();
				Alumno.mapeaReg(rs);
				lisPersonal.add(Alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisPersonal;
	}
	
	public ArrayList<String[]> getListPaisesGraduandos(Connection conn, String evento) throws SQLException{
		
		ArrayList<String[]> lisPaises	= new ArrayList<String[]>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT ENOC.NOMBRE_PAIS(B.PAIS_ID) AS NOMBRE_PAIS " +
					" ,COUNT(A.CODIGO_PERSONAL) AS NUMERO " +
					" FROM ENOC.ALUM_EGRESO A, ENOC.ALUM_PERSONAL B" +
					" WHERE A.EVENTO_ID = "+evento+" " +
					" AND B.CODIGO_PERSONAL = A.CODIGO_PERSONAL" +
					" GROUP BY B.PAIS_ID" +
					" ORDER BY B.PAIS_ID ";			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				String [] arreglo = new String[2];
				arreglo[0]= rs.getString("NOMBRE_PAIS");
				arreglo[1]= rs.getString("NUMERO");
				lisPaises.add(arreglo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListPaisesGraduandos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisPaises;
	}
	
	public ArrayList<String[]> getListEstadosGraduandos(Connection conn, String evento, String paisId) throws SQLException{
		
		ArrayList<String[]> lisEstados	= new ArrayList<String[]>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "	SELECT ENOC.NOMBRE_ESTADO(B.PAIS_ID,B.ESTADO_ID) AS NOMBRE_ESTADO, " +
					"		COUNT(A.CODIGO_PERSONAL) AS CANTIDAD FROM ENOC.ALUM_EGRESO A, ENOC.ALUM_PERSONAL B" +
					"	WHERE A.EVENTO_ID = '"+evento+"'" +
					"		AND B.PAIS_ID = '"+paisId+"'" +
					"		AND B.CODIGO_PERSONAL = A.CODIGO_PERSONAL" +
					"	GROUP BY B.PAIS_ID, B.ESTADO_ID" +
					"	ORDER BY CANTIDAD DESC, NOMBRE_ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				String [] arr = new String[2];
				arr[0] = rs.getString("NOMBRE_ESTADO");
				arr[1] = rs.getString("CANTIDAD");
				lisEstados.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListEstadosGraduandos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisEstados;
	}
	
	public static ArrayList<String> getListGradMat(Connection conn, int eventoId, String orden) throws SQLException{
		
		ArrayList<String> lisAlumno	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL " +
 				" FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = "+eventoId+" "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisAlumno.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListGradMat|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	
	public static ArrayList<AlumEgreso> getListFechaIngresoUm(Connection conn, String orden, String eventoId ) throws SQLException{
		
		ArrayList<AlumEgreso> lisAlumno	= new ArrayList<AlumEgreso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, PROMEDIO, AVANCE, TITULADO, COALESCE((SELECT TO_CHAR(MIN(FECHA),'DD/MM/YYYY')"
					+ " FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = ENOC.ALUM_EGRESO.CODIGO_PERSONAL"
					+ " AND PLAN_ID = ENOC.ALUM_EGRESO.PLAN_ID"
					+ " AND INSCRITO = 'S'),'01/01/2000') AS FECHA"
					+ " FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = '"+eventoId+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumEgreso alumno = new AlumEgreso();
				alumno.mapeaReg(rs);
				lisAlumno.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|getListFechaIngresoUm|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisAlumno;
	}
	
	/* Map de alumnos graduandos por cada pais*/ 
	public static HashMap<String,String> mapGraduaPorPais(Connection conn, String evento ) throws SQLException{
		
		HashMap<String,String> mapPais = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.ALUM_PAIS(CODIGO_PERSONAL) AS PAIS_ID, COUNT(CODIGO_PERSONAL) AS TOTAL"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = '"+evento+"'"
					+ " GROUP BY ENOC.ALUM_PAIS(CODIGO_PERSONAL)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				llave = rs.getString("PAIS_ID");
				mapPais.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEgresoUtil|mapGraduaPorPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapPais;
	}
	
}