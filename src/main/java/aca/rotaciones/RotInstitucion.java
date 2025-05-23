package aca.rotaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RotInstitucion {

	private String institucionId;
	private String institucionNombre;
	
	public RotInstitucion(){		
		institucionId		= "";
		institucionNombre	= "";
	}
	
	public String getInstitucionId() {
		return institucionId;
	}

	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}

	public String getInstitucionNombre() {
		return institucionNombre;
	}

	public void setInstitucionNombre(String institucionNombre) {
		this.institucionNombre = institucionNombre;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROT_INSTITUCION(INSTITUCION_ID, INSTITUCION_NOMBRE) " +
					" VALUES(?,?)");
			ps.setString(1,institucionId);
			ps.setString(2,institucionNombre);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROT_INSTITUCION SET INSTITUCION_NOMBRE = ? " +
					" WHERE INSTITUCION_ID = ?");			
			
			ps.setString(1,institucionNombre);
			ps.setString(2,institucionId);	
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ?"); 
			ps.setString(1,institucionId);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		institucionId 		= rs.getString("INSTITUCION_ID");
		institucionNombre	= rs.getString("INSTITUCION_NOMBRE");
	}
	
	public void mapeaRegId(Connection con, String institucionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT INSTITUCION_ID, INSTITUCION_NOMBRE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ? "); 
			ps.setString(1,institucionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = ? "); 
			ps.setString(1, institucionId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
	
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(INSTITUCION_ID)+1 MAXIMO FROM ENOC.ROT_INSTITUCION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombre(Connection conn, String institucionId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT INSTITUCION_NOMBRE FROM ENOC.ROT_INSTITUCION WHERE INSTITUCION_ID = '"+institucionId+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("INSTITUCION_NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotInstitucion|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}
