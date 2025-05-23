/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AlumnoUtil {
	
	public boolean insertReg(Connection conn, Alumno alumno ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		if (!existeReg(conn, alumno.getCodigoPersonal())){
			try{
				ps = conn.prepareStatement("INSERT INTO ENOC.INT_ALUMNO(DORMITORIO_ID, CUARTO_ID, CODIGO_PERSONAL) VALUES(?,?,?)"); 
				ps.setString(1, alumno.getDormitorioId());
				ps.setString(2, alumno.getCuartoId());
				ps.setString(3, alumno.getCodigoPersonal());
				
				if ( ps.executeUpdate()== 1){
					ok = true;
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.AlumnoUtil|insertReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
		}
		return ok;
	}
	public boolean updateReg(Connection conn, Alumno alumno ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_ALUMNO SET CUARTO_ID = ? WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?");			 
			ps.setString(1, alumno.getCuartoId());
			ps.setString(2, alumno.getDormitorioId());
			ps.setString(3, alumno.getCodigoPersonal());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String dormitorioId, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?"); 
			ps.setString(1,dormitorioId);			
			ps.setString(2,codigoPersonal);			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Alumno mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		Alumno alumno = new Alumno();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();		
			if(rs.next()){
				alumno.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumno;
	}
	public boolean existeReg(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();		
			if(rs.next()){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean existeReg(Connection con, String codigoPersonal, String dormitorioId) throws SQLException{
		PreparedStatement ps = null;
		boolean ok = false;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND DORMITORIO_ID = TO_NUMBER(?, '99')");
			ps.setString(1,codigoPersonal);
			ps.setString(2, dormitorioId);
			rs = ps.executeQuery();		
			if(rs.next()){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
 	public static boolean esInterno(Connection conn, String codigoPersonal) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = 'I' ");				 
 			ps.setString(1,codigoPersonal);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				ok = true;
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.AlumnoUtil|esInterno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static boolean tieneAlumnos(Connection conn, String cuartoId, String dormitorioId) throws SQLException{
 		
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		boolean ok 				= false;
 		
 		try{
 			ps = conn.prepareStatement("select codigo_personal from ENOC.int_alumno where cuarto_id = ? and dormitorio_id = ? ");				 
 			ps.setString(1,cuartoId);
 			ps.setString(2,dormitorioId);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				ok = true;
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.AlumnoUtil|esInterno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
 	public static int numAlumRegistrados(Connection conn, String dormitorioId, String estado) throws SQLException{
 		Statement st	= conn.createStatement();
 		ResultSet rs	= null;
 		String comando 	= "";
 		int total 		= 0;
 		
 		try{
 			comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = '"+dormitorioId+"' AND ESTADO IN ("+estado+")"; 			
 			rs = st.executeQuery(comando);
 			if (rs.next()){
 				total = rs.getInt("TOTAL");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.AlumnoUtil|numAlumRegistrados|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return total;
 	}
 	
 	public static int numAlumRegInscritos(Connection conn, String dormitorioId, String estado) throws SQLException{
 		Statement st	= conn.createStatement();
 		ResultSet rs	= null;
 		String comando 	= "";
 		int total 		= 0;
 		
 		try{
 			comando = " SELECT COUNT(*) AS TOTAL FROM ENOC.INT_ALUMNO "
 					+ " WHERE DORMITORIO_ID = '"+dormitorioId+"'"
 					+ " AND ESTADO IN ("+estado+")"
 					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
 			rs = st.executeQuery(comando);
 			if (rs.next()){
 				total = rs.getInt("TOTAL");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.AlumnoUtil|numAlumRegInscritos|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return total;
 	}

}