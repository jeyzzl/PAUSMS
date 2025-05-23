package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatActividad {
	private String actividadId;	
	private String descripcion;
	
	public CatActividad(){
		actividadId     = "";
		descripcion     = "";
	}
	

	public String getActividadId() {
		return actividadId;
	}


	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_ACTIVIDAD(ACTIVIDAD_ID, DESCRIPCION ) "+ 
				"VALUES(?, ?) ");
			ps.setString(1, actividadId);
			ps.setString(2, descripcion);

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAtividad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ACTIVIDAD "+ 
				" SET DESCRIPCION = ? " +
				" WHERE ACTIVIDAD_ID = ? ");
			ps.setString(1, descripcion);
			ps.setString(2, actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAtividad|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ACTIVIDAD "+ 
				" WHERE ACTIVIDAD_ID = ? ");
			ps.setString(1, actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEje|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		actividadId     = rs.getString("ACTIVIDAD_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId( Connection conn, String actividadId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_ID, DESCRIPCION "+
				"FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ?");		 
			ps.setString(1,actividadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatActividad|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ? "); 
			ps.setString(1,actividadId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAtividad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getDescripcion(Connection conn, String actividadId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String descripcion 		= "x";
		
		try{
			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.CAT_ACTIVIDAD WHERE ACTIVIDAD_ID = ? "); 
			ps.setString(1,actividadId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				descripcion = rs.getString("DESCRIPCION");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAtividad|getDescripcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return descripcion;
	}
	
}