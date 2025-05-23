package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusiInstitucion {

	private String institucionId;
 	private String institucionNombre;
 	
 	public MusiInstitucion(){
 		institucionId			= "";
 		institucionNombre		= ""; 	
 	}
 	
	
	/**
	 * @return the institucionId
	 */
	public String getInstitucionId() {
		return institucionId;
	}


	/**
	 * @param institucionId the institucionId to set
	 */
	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}


	/**
	 * @return the institucionNombre
	 */
	public String getInstitucionNombre() {
		return institucionNombre;
	}


	/**
	 * @param institucionNombre the institucionNombre to set
	 */
	public void setInstitucionNombre(String institucionNombre) {
		this.institucionNombre = institucionNombre;
	}


	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_INSTITUCION"+ 
 				"(INSTITUCION_ID, INSTITUCION_NOMBRE)"+
 				" VALUES(TO_NUMBER(?,'99'),?)");
 			ps.setString(1, institucionId);
 			ps.setString(2, institucionNombre);
 			
 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstitucion|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.MUSI_INSTITUCION "+ 
 				"SET INSTITUCION_NOMBRE = ? "+				
 				"WHERE INSTITUCION_ID = TO_NUMBER(?,'99')");
 				
 			ps.setString(1, institucionNombre);
 			ps.setString(2, institucionId);
 			
 					
 			 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstitucion|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_INSTITUCION "+ 
 				"WHERE INSTITUCION_ID = TO_NUMBER(?,'99') ");
 			ps.setString(1, institucionId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstitucion|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		institucionId 			= rs.getString("INSTITUCION_ID");
 		institucionNombre 		= rs.getString("INSTITUCION_NOMBRE"); 		
 	}
  	
 	public void mapeaRegId( Connection conn, String Codigo_personal ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			"INSTITUCION_ID, INSTITUCION_NOMBRE "+
	 			"FROM ENOC.MUSI_INSTITUCION WHERE INSTITUCION_ID = TO_NUMBER(?,'99') "); 
	 		ps.setString(1, institucionId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstitucion|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_INSTITUCION "+ 
 				"WHERE INSTITUCION_ID = TO_NUMBER(?,'99')");
 			ps.setString(1, institucionId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstitucion|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
 	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(INSTITUCION_ID)+1 MAXIMO FROM ENOC.MUSI_INSTITUCION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstitucion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreInstitucion(Connection conn, String institucionId) throws SQLException{
 		
 		PreparedStatement ps	= null; 		
 		ResultSet 		rs		= null;
 		String institucion 		= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT INSTITUCION_NOMBRE FROM ENOC.MUSI_INSTITUCION"+ 
 				" WHERE INSTITUCION_ID = TO_NUMBER(?,'99')");
 			ps.setString(1, institucionId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				institucion = rs.getString("INSTITUCION_NOMBRE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiInstitucion|getNombreInstitucion|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return institucion;
 	}
	
}