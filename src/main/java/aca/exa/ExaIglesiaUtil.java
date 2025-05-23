package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaIglesiaUtil {
	
	public boolean insertReg(Connection conn, ExaIglesia exaIglesia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_IGLESIA(IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEGRESADOIGLESIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaIglesia.getIglesiaId());
			ps.setString(2, exaIglesia.getMatricula());
			ps.setString(3, exaIglesia.getIglesia());
			ps.setString(4, exaIglesia.getFuncion());
			ps.setString(5, exaIglesia.getFechaAct());
			ps.setString(6, exaIglesia.getEliminado());
			ps.setString(7, exaIglesia.getIdEgresadoIglesia());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String iglesiaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_IGLESIA"+ 
				" SET ELIMINADO = '1' " +
				" WHERE IGLESIA_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, iglesiaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaIglesia mapeaRegIdIglesia( Connection conn, String iglesiaId) throws SQLException{
		ExaIglesia exaIglesia = new ExaIglesia();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "+
				"FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,iglesiaId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaIglesia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaIglesia;
	}
	
	public ExaIglesia mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaIglesia exaIglesia = new ExaIglesia();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "+
				"FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaIglesia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaIglesia;
	}
	
	public boolean existeReg(Connection conn, String iglesiaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1, iglesiaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|existeAlumno|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IGLESIA_ID) AS MAXIMO FROM ENOC.EXA_IGLESIA WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IGLESIA_ID)+1 AS MAXIMO FROM ENOC.EXA_IGLESIA");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaIglesia> getIglesia(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaIglesia> list		= new ArrayList<ExaIglesia>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA FROM ENOC.EXA_IGLESIA" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaIglesia obj = new ExaIglesia();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|getIglesia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
