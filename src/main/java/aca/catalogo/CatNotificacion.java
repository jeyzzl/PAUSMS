// Bean del Catalogo de Religiones
package aca.catalogo;

import java.sql.*;

public class CatNotificacion{
	private String notificacionId;	
	private String notificacionNombre;
	
	public CatNotificacion(){
		notificacionId 		= "";		
		notificacionNombre	= "";
	}
	
	/**
	 * @return the notificacionId
	 */
	public String getNotificacionId() {
		return notificacionId;
	}

	/**
	 * @param notificacionId the notificacionId to set
	 */
	public void setNotificacionId(String notificacionId) {
		this.notificacionId = notificacionId;
	}

	/**
	 * @return the notificacionNombre
	 */
	public String getNotificacionNombre() {
		return notificacionNombre;
	}

	/**
	 * @param notificacionNombre the notificacionNombre to set
	 */
	public void setNotificacionNombre(String notificacionNombre) {
		this.notificacionNombre = notificacionNombre;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_NOTIFICACION"+ 
				"(NOTIFICACION_ID, NOTIFICACION_NOMBRE) "+
				"VALUES( ?, ? ) ");
			ps.setString(1, notificacionId);
			ps.setString(2, notificacionNombre);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_NOTIFICACION "+ 
				"SET NOTIFICACION_NOMBRE = ? "+
				"WHERE NOTIFICACION_ID 	= ?");
			ps.setString(1, notificacionNombre);			
			ps.setString(2, notificacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_NOTIFICACION "+ 
				"WHERE NOTIFICACION_ID = ?");
			ps.setString(1, notificacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		notificacionId 		= rs.getString("NOTIFICACION_ID");
		notificacionNombre 	= rs.getString("NOTIFICACION_NOMBRE");		
	}
	
	public void mapeaRegId( Connection conn, String notificacionId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE "+
				"FROM ENOC.CAT_NOTIFICACION WHERE NOTIFICACION_ID = ?"); 
			ps.setString(1,notificacionId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_NOTIFICACION WHERE NOTIFICACION_ID = ? "); 
			ps.setString(1,notificacionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreEstrategia(Connection conn, String notificacionId) throws SQLException{
		ResultSet rs = null;
		String nombre = "";
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE "+
				"FROM ENOC.CAT_NOTIFICACION WHERE NOTIFICACION_ID = ?"); 
			
			ps.setString(1, notificacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("NOMBRE_NOTIFICACION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(NOTIFICAION_ID)+1 MAXIMO FROM ENOC.CAT_NOTIFICACION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}