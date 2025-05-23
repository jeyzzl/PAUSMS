package aca.sep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class SepAlumnoUtil {
		
	public void mapeaRegId( Connection conn, String codigoPersonal, String fecha) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PLANTEL, PLAN_SEP, CICLO, CURP, NOMBRE, PATERNO, MATERNO, FECHA, CODIGO_PERSONAL, PLAN_UM, GENERO, EDAD, GRADO," + 
					" PAIS_ID, ESTADO_ID, PREPA_LUGAR, USADO FROM ENOC.SEP_ALUMNO WHERE CODIGO_PERSONAL = "+codigoPersonal+" AND FECHA TO_DATE("+fecha+",'DD/MM/YYYY')"); 
			
			rs = ps.executeQuery();
			if (rs.next()){
				SepAlumno sepAlumno = new SepAlumno();
				sepAlumno.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public ArrayList<SepAlumno> listAlumno(Connection conn, String codigoAlumno ) throws SQLException{
		
		ArrayList<SepAlumno> lista	= new ArrayList<SepAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.SEP_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' ORDER BY FECHA DESC";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				SepAlumno sepAlumno = new SepAlumno();
				sepAlumno.mapeaReg(rs);
				lista.add(sepAlumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|listFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<String> listFechas(Connection conn) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT DISTINCT(TO_CHAR(FECHA,'YYYY/MM/DD')) AS FECHA FROM ENOC.SEP_ALUMNO ORDER BY FECHA DESC";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("FECHA"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|listFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	/* Total de alumnos */
	public int getTotal( Connection conn, String fecha, String planSep, String planUm) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		int total				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)");			
			ps.setString(1, fecha);			
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getInt("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|getTotal|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por genero */
	public String getGenero( Connection conn, String genero, String fecha, String planSep, String planUm) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String total			= "0";
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND GENERO = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)"
					+ " AND CICLO = 1"); 
			
			ps.setString(1, fecha);
			ps.setString(2, genero);
			ps.setString(3, planSep);
			ps.setString(4, planUm);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|getGenero|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por estudios de prepa*/
	public ArrayList<String> lisPrepaLugar( Connection conn, String fecha, String planSep, String planUm ) throws SQLException{
		
		ArrayList<String> lista		= new ArrayList<String>();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT SEP_LUGAR_NOMBRE(PREPA_LUGAR) AS LUGAR, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND CICLO = 1"
					+ " GROUP BY SEP_LUGAR_NOMBRE(PREPA_LUGAR) ORDER BY 1");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
						
			rs = ps.executeQuery();
			while (rs.next()){
				lista.add(rs.getString("LUGAR")+"="+rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|lisPrepaLugar|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	/* Primer ingreso por pais y estado*/
	public String getPorEstado( Connection conn, String fecha, String planSep, String planUm, String pais, String estado) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String total			= "0";
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM SEP_ALUMNO" 
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)"
					+ " AND CICLO = 1"
					+ " AND PAIS_ID = ?"
					+ " AND ESTADO_ID = ?"); 
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setString(4, pais);
			ps.setString(5, estado);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|getPorEstado|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por pais menos un estado*/
	public String getPorPais( Connection conn, String fecha, String planSep, String planUm, String pais, String estado) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String total			= "0";
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM SEP_ALUMNO" 
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)"
					+ " AND CICLO = 1"
					+ " AND PAIS_ID = ?"
					+ " AND ESTADO_ID != ?");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setString(4, pais);
			ps.setString(5, estado);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|getPorPais|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por pais menos el estado de nuevo leon*/
	public ArrayList<String> getOtrosPaises( Connection conn, String fecha, String planSep, String planUm, String paises) throws SQLException{
		
		ArrayList<String> lista		= new ArrayList<String>();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PAIS(PAIS_ID) AS PAIS, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND CICLO = 1"
					+ " AND PAIS_ID NOT IN ("+paises+")"
					+ " GROUP BY NOMBRE_PAIS(PAIS_ID)");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
						
			rs = ps.executeQuery();
			while (rs.next()){
				lista.add(rs.getString("PAIS")+"="+rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|getOtrosPaises|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapGrado(Connection conn, String fecha, String planSep, String planUm) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement(" SELECT GRADO, GENERO, COUNT(*) AS TOTAL FROM SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " GROUP BY GRADO, GENERO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("GRADO")+rs.getString("GENERO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapGrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	/* Total de alumnos por pais y estado*/
	public String totalPorEstado( Connection conn, String fecha, String planSep, String planUm, String pais, String estado) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String total			= "0";
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM SEP_ALUMNO" 
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)"
					+ " AND PAIS_ID = ?"
					+ " AND ESTADO_ID = ?"); 
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setString(4, pais);
			ps.setString(5, estado);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|totalPorEstado|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por pais menos un estado*/
	public String totalPorPais( Connection conn, String fecha, String planSep, String planUm, String pais, String estado) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String total			= "0";
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO" 
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND PAIS_ID = ?"
					+ " AND ESTADO_ID != ?");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setString(4, pais);
			ps.setString(5, estado);
			rs = ps.executeQuery();
			if (rs.next()){
				total = rs.getString("TOTAL");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|totalPorPais|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	/* Primer ingreso por pais menos el estado de nuevo leon*/
	public ArrayList<String> totalOtrosPaises( Connection conn, String fecha, String planSep, String planUm, String paises) throws SQLException{
		
		ArrayList<String> lista		= new ArrayList<String>();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PAIS(PAIS_ID) AS PAIS, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"					
					+ " AND PAIS_ID NOT IN ("+paises+")"
					+ " GROUP BY NOMBRE_PAIS(PAIS_ID)");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
						
			rs = ps.executeQuery();
			while (rs.next()){
				lista.add(rs.getString("PAIS")+"="+rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|totalOtrosPaises|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapEdadyGrado(Connection conn, String fecha, String planSep, String planUm) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement("SELECT EDAD,GRADO, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ? "					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"					
					+ " GROUP BY EDAD, GRADO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);		
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("EDAD")+rs.getString("GRADO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapEdadyGrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapEdadGradoyGenero(Connection conn, String fecha, String planSep, String planUm) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement("SELECT EDAD,GRADO,GENERO,COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ? "					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"			
					+ " GROUP BY EDAD, GRADO, GENERO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);		
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("EDAD")+rs.getString("GRADO")+rs.getString("GENERO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapEdadGradoyGenero|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapEdadyGradoRango(Connection conn, String fecha, String planSep, String planUm, int inicio, int fin) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement("SELECT GRADO,GENERO, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ? "					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND EDAD >= ? AND EDAD <= ?"
					+ " GROUP BY GRADO, GENERO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setInt(4, inicio);
			ps.setInt(5, fin);
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("GRADO")+rs.getString("GENERO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapEdadyGrado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	/* Minimo de edad de los alumnos de un plan*/
	public String minEdad( Connection conn, String fecha, String planSep, String planUm) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String minimo			= "0";
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MIN(EDAD),0) AS MINIMO FROM ENOC.SEP_ALUMNO" 
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ?"
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM)>0)");
			
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);			
			rs = ps.executeQuery();
			if (rs.next()){
				minimo = rs.getString("MINIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|minEdad|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return minimo;
	}
	
	public HashMap<String, String> mapEdadyGenero(Connection conn, String fecha, String planSep, String planUm, String ciclos) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement("SELECT EDAD,GENERO, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ? "					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND CICLO IN ("+ciclos+")"
					+ " GROUP BY EDAD,GENERO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("EDAD")+rs.getString("GENERO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapEdadyGenero|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, String> mapEdadyGeneroRango(Connection conn, String fecha, String planSep, String planUm, int inicio, int fin, String ciclos) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		PreparedStatement ps 			= null;
		ResultSet rs		 			= null;		
				
		try{
			ps = conn.prepareStatement("SELECT GENERO, COUNT(*) AS TOTAL FROM ENOC.SEP_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'YYYY/MM/DD') = ? "					
					+ " AND (INSTR(?, PLAN_SEP) > 0 OR INSTR(?, PLAN_UM) > 0)"
					+ " AND EDAD >= ? AND EDAD <= ?"
					+ " AND CICLO IN ("+ciclos+")"
					+ " GROUP BY GENERO");
			ps.setString(1, fecha);
			ps.setString(2, planSep);
			ps.setString(3, planUm);
			ps.setInt(4, inicio);
			ps.setInt(5, fin);
			
			rs = ps.executeQuery();
			while (rs.next()){				
				map.put(rs.getString("GENERO"),rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepAlumnoUtil|mapEdadyGeneroRango|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}
