package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatAsignacionUtil {
	
	public boolean insertReg(Connection conn, CatAsignacion asignacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_ASIGNACION"+ 
				"(ASIGNACION_ID, ASIGNACION_NOMBRE, DIRECCION, TELEFONO)"+
				" VALUES( ?, ?, ?, ? ) ");
			ps.setString(1, asignacion.getAsignacionId());
			ps.setString(2, asignacion.getAsignacionNombre());
			ps.setString(3, asignacion.getDireccion());
			ps.setString(4, asignacion.getTelefono());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsignacionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatAsignacion asignacion  ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ASIGNACION"+ 
				" SET ASIGNACION_NOMBRE = ?," +
				" DIRECCION = ?," +
				" TELEFONO = ?"+
				" WHERE ASIGNACION_ID = ?");
			ps.setString(1, asignacion.getAsignacionNombre());			
			ps.setString(2, asignacion.getDireccion());
			ps.setString(3, asignacion.getTelefono());
			ps.setString(4, asignacion.getAsignacionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsignacionUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String asignacionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ASIGNACION"+ 
				" WHERE ASIGNACION_ID = ?");
			ps.setString(1, asignacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsignacionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatAsignacion mapeaRegId( Connection conn, String asignacionId ) throws SQLException{
		ResultSet rs 				= null;
		PreparedStatement ps 		= null; 
		CatAsignacion asignacion 	= new CatAsignacion();
		
		try{
			ps = conn.prepareStatement("SELECT ASIGNACION_ID, ASIGNACION_NOMBRE," +
					" DIRECCION, TELEFONO"+
					" FROM ENOC.CAT_ASIGNACION WHERE ASIGNACION_ID = ?"); 
			ps.setString(1,asignacionId);
			rs = ps.executeQuery();
			if (rs.next()){
				asignacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsignacionUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return asignacion;
	}
	
	public boolean existeReg(Connection conn, String asignacionId ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ASIGNACION WHERE ASIGNACION_ID = ? "); 
			ps.setString(1, asignacionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsignacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

}
