package aca.residencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResColonia {

	private String coloniaId;	
	private String coloniaNombre;
	
	public ResColonia(){
		coloniaId 		= "";
		coloniaNombre	= "";
	}
	
	
	/**
	 * @return the coloniaId
	 */
	public String getColoniaId() {
		return coloniaId;
	}


	/**
	 * @param coloniaId the coloniaId to set
	 */
	public void setColoniaId(String coloniaId) {
		this.coloniaId = coloniaId;
	}


	/**
	 * @return the coloniaNombre
	 */
	public String getColoniaNombre() {
		return coloniaNombre;
	}

	/**
	 * @param coloniaNombre the coloniaNombre to set
	 */
	public void setColoniaNombre(String coloniaNombre) {
		this.coloniaNombre = coloniaNombre;
	}




	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"RES_COLONIA(COLONIA_ID, COLONIA_NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1,coloniaId);
			ps.setString(2, coloniaNombre);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.RES_COLONIA "+ 
				"SET COLONIA_NOMBRE = ? "+
				"WHERE COLONIA_ID = TO_NUMBER(?,'999')");
			ps.setString(1, coloniaNombre);
			ps.setString(2, coloniaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.RES_COLONIA "+ 
				"WHERE COLONIA_ID = TO_NUMBER(?,'999')");
			ps.setString(1, coloniaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		coloniaId 		= rs.getString("COLONIA_ID");
		coloniaNombre 	= rs.getString("COLONIA_NOMBRE");		
	}
	
	public void mapeaRegId( Connection conn, String religionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT COLONIA_ID, COLONIA_NOMBRE "+
				"FROM ENOC.RES_COLONIA WHERE COLONIA_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,religionId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.RES_COLONIA WHERE COLONIA_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,coloniaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT COALESCE(MAX(COLONIA_ID)+1,1) MAXIMO FROM ENOC.RES_COLONIA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreReligion(Connection conn, String religionId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT COLONIA_NOMBRE FROM ENOC.RES_COLONIA WHERE COLONIA_ID = ?"); 
			ps.setString(1, religionId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("COLONIA_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResColonia|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

}