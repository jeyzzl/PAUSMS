package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaRedUtil {
	
	public boolean insertReg(Connection conn, ExaRed exaRed ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_REDSOCIAL(REDSOCIAL_ID, MATRICULA, RED, FECHAACTUALIZACION, " +
				"ELIMINADO, IDREDSOCIAL) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaRed.getRedSocialId());
			ps.setString(2, exaRed.getMatricula());
			ps.setString(3, exaRed.getRed());
			ps.setString(4, exaRed.getFechaAct());
			ps.setString(5, exaRed.getEliminado());
			ps.setString(6, exaRed.getIdRedSocial());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String redSocialId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_REDSOCIAL"+ 
				" SET ELIMINADO = '1' " +
				" WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, redSocialId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaRed mapeaRegIdEstudio( Connection conn, String redSocialId) throws SQLException{
		ExaRed exaRed = new ExaRed();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED, " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,redSocialId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaRed.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaRed;
	}
	
	public ExaRed mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaRed exaRed = new ExaRed();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaRed.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaRed;
	}
	
	public boolean existeReg(Connection conn, String redSocialId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_REDSOCIAL WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1, redSocialId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAlumno(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_REDSOCIAL WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|existeAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(REDSOCIAL_ID)+1 AS MAXIMO FROM EXA_REDSOCIAL WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(REDSOCIAL_ID)+1 AS MAXIMO FROM EXA_REDSOCIAL");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo==null?"1":maximo;
	}
	
	public ArrayList<ExaRed> getRed(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaRed> list		= new ArrayList<ExaRed>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT REDSOCIAL_ID, MATRICULA, RED, " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL FROM ENOC.EXA_REDSOCIAL" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaRed obj = new ExaRed();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|getRed|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
