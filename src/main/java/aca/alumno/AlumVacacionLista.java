package aca.alumno;
import java.sql.*;
import java.util.ArrayList;

public class AlumVacacionLista {
	
	public boolean insertReg(Connection conn, AlumVacacion alumVacacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_VACACION " + 
				"(NIVEL_ID, MODALIDAD_ID, F_EXAMEN, F_INICIO, F_FINAL) " +
				"VALUES(TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), " +
				"TO_DATE(?,'DD/MM/YYYY'), " +
				"TO_DATE(?,'DD/MM/YYYY'), " +
				"TO_DATE(?,'DD/MM/YYYY'))");	
			
			ps.setString(1, alumVacacion.getNivelId());
			ps.setString(2, alumVacacion.getModalidadId());
			ps.setString(3, alumVacacion.getFExamen());
			ps.setString(4, alumVacacion.getFInicio());
			ps.setString(5, alumVacacion.getFFinal());					
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumVacacion alumVacacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_VACACION SET " + 
					"F_EXAMEN = TO_DATE(?,'DD/MM/YYYY'), " +
					"F_INICIO = TO_DATE(?,'DD/MM/YYYY'), " +
					"F_FINAL = TO_DATE(?,'DD/MM/YYYY') " +
					"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99')");
		
			ps.setString(1, alumVacacion.getFExamen());
			ps.setString(2, alumVacacion.getFInicio());
			ps.setString(3, alumVacacion.getFFinal());
			ps.setString(4, alumVacacion.getNivelId());
			ps.setString(5, alumVacacion.getModalidadId());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String nivelId, String modalidadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_VACACION "+ 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
				"AND MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.AlumVacacionLista|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumVacacion mapeaRegId( Connection conn, String nivelId, String modalidadId ) throws SQLException{
		
		AlumVacacion vacacion = new AlumVacacion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"NIVEL_ID, MODALIDAD_ID, "+
				"TO_CHAR(F_EXAMEN,'DD/MM/YYYY') AS F_EXAMEN, " +
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL " +
				"FROM ENOC.ALUM_VACACION " + 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
				"AND MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				vacacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return vacacion;
	}

	public boolean existeReg(Connection conn, String nivelId, String modalidadId ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getFechaExamen(Connection conn, String nivelId, String modalidadId) throws SQLException{
		Statement st	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		String examen	= "X";
		
		try{
			comando = "SELECT COALESCE(TO_CHAR(F_EXAMEN,'DD/MM/YYYY'),'01/01/2010') AS F_EXAMEN " +
					"FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = '"+nivelId+"' "+
					"AND MODALIDAD_ID = '"+modalidadId+"'";
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				examen = rs.getString("F_EXAMEN");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|getFechaExamen|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}		
		return examen;
	}	
	
	public static String getFechaInicio(Connection conn, String nivelId, String modalidadId) throws SQLException{
		Statement st	= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String inicio	= "X";
		
		try{
			comando = "SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO " +
					"FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = '"+nivelId+"' " +
					"AND MODALIDAD_ID = '"+modalidadId+"'";
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				inicio = rs.getString("F_INICIO");				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|getFechaInicio|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}		
		return inicio;
	}	
	
	public static String getFechaFinal(Connection conn, String nivelId, String modalidadId) throws SQLException{
		Statement st	= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String end	= "X";
		
		try{
			comando = "SELECT TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL " +
				"FROM ENOC.ALUM_VACACION " + 
				"WHERE NIVEL_ID = '"+nivelId+"' " +
				"AND MODALIDAD_ID = '"+modalidadId+"'";;
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				end = rs.getString("F_FINAL");				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|getFechaFinal|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		return end;
	}	

	public ArrayList<AlumVacacion> getListAll(Connection conn, String orden ) throws SQLException{		
		ArrayList<AlumVacacion> lisVacacion= new ArrayList<AlumVacacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT NIVEL_ID, MODALIDAD_ID, " +
					"TO_CHAR(F_EXAMEN,'DD/MM/YYYY') AS F_EXAMEN, " +
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL "+
					"FROM ENOC.ALUM_VACACION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumVacacion vacacion = new AlumVacacion();
				vacacion.mapeaReg(rs);
				lisVacacion.add(vacacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisVacacion;
	}
}