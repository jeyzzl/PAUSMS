package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumGraduaMatUtil {
	
	public boolean insertReg(Connection conn, AlumGraduaMat alumGradMat ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_GRADUA_MAT"+ 
					"(CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO) "+
					"VALUES( ?, ?, ?,?)");
			
				ps.setString(1, alumGradMat.getCodigoPersonal());
				ps.setString(2, alumGradMat.getCursoId());
				ps.setString(3, alumGradMat.getProgramada());
				ps.setString(4, alumGradMat.getComentario());
							
				if (ps.executeUpdate()== 1)
					ok = true;				
				else
					ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
		
	public boolean updateReg(Connection conn, AlumGraduaMat alumGradMat ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_GRADUA_MAT"+ 
				" SET "+
				" PROGRAMADA =?,"+
				" COMENTARIO = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_ID = ?");	
			
			ps.setString(1, alumGradMat.getProgramada());
			ps.setString(2, alumGradMat.getComentario());
			ps.setString(3, alumGradMat.getCodigoPersonal());			
			ps.setString(4, alumGradMat.getCursoId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteMateriasDelPlan(Connection conn, String codigoPersonal, String planId ) throws SQLException{			
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_GRADUA_MAT WHERE CODIGO_PERSONAL = ? AND SUBSTR(CURSO_ID,1,8) = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);			
			if (ps.executeUpdate() >= 1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|deleteMateriasDelPlan|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumGraduaMat mapeaRegId( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		AlumGraduaMat alumGradMat = new AlumGraduaMat();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO FROM ENOC.ALUM_GRADUA_MAT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_ID = ?");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumGradMat.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumGradMat;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cursoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean tieneReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_GRADUA_MAT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_ID,1,8) = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|tieneReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static int numCredFaltan(Connection conn, String codigoPersonal, String planId) throws SQLException{			
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		int creditos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT SUM(ENOC.CREDITOS(CURSO_ID)) AS CRED FROM ENOC.ALUM_GRADUA_MAT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_ID,1,8) = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				creditos = rs.getInt("CRED");				
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|numCredFaltan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return creditos;
	}
	
	public ArrayList<AlumGraduaMat> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumGraduaMat> lisGraduaMat		= new ArrayList<AlumGraduaMat>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO "+
				"FROM ENOC.ALUM_GRADUA_MAT "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumGraduaMat actividad = new AlumGraduaMat();
				actividad.mapeaReg(rs);
				lisGraduaMat.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGraduaMat;
	}
	
	public ArrayList<AlumGraduaMat> getLista(Connection conn, String codigoPersonal, String cursoId, String orden ) throws SQLException{
		
		ArrayList<AlumGraduaMat> lisActiv	= new ArrayList<AlumGraduaMat>();
		Statement st 			    		= conn.createStatement();
		ResultSet rs 				    	= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_ID, PROGRAMADA, COMENTARIO "+
					"FROM ENOC.ALUM_GRADUA_MAT "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					"AND CURSO_ID = '"+cursoId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumGraduaMat actividad = new AlumGraduaMat();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}

}