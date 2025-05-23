// Bean del Catalogo de Ciudades
package  adm.catalogo;

import java.sql.*;

public class CatCiudad{
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String nombreCiudad;
	
	public CatCiudad(){
		paisId 		= "";
		estadoId 		= "";
		ciudadId		= "";
		nombreCiudad	= "";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public void setCiudadId( String ciudadId){
		this.ciudadId = ciudadId;
	}	
	
	public String getNombreCiudad(){
		return nombreCiudad;
	}
	
	public void setNombreCiudad( String nombreCiudad){
		this.nombreCiudad = nombreCiudad;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_CIUDAD"+
				"(PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD) "+
				"VALUES( TO_NUMBER (?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			ps.setString(4, nombreCiudad);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_CIUDAD "+
				"SET NOMBRE_CIUDAD = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombreCiudad);
			ps.setString(2, paisId);
			ps.setString(3, estadoId);
			ps.setString(4, ciudadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_CIUDAD "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 		= rs.getString("PAIS_ID");
		estadoId	 	= rs.getString("ESTADO_ID");
		ciudadId	 	= rs.getString("CIUDAD_ID");
		nombreCiudad 	= rs.getString("NOMBRE_CIUDAD");
	}
	
	public void mapeaRegId( Connection conn, String paisId, String estadoId, String ciudadId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD "+
			"FROM ENOC.CAT_CIUDAD "+
			"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
			"AND ESTADO_ID = TO_NUMBER(?,'999') "+
			"AND CIUDAD_ID = TO_NUMBER(?,'999')");
		ps.setString(1, paisId);
		ps.setString(2, estadoId);
		ps.setString(3, ciudadId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_CIUDAD "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			ps.setString(3,ciudadId);
			
			rs = ps.executeQuery();
				if (rs.next()){
				ok = true;
			}else{
			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String paisId, String estadoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "001";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CIUDAD_ID)+1 AS MAXIMO FROM ENOC.CAT_CIUDAD "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreCiudad(Connection conn, String paisId, String estadoId, String ciudadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = ? AND ESTADO_ID = ? AND CIUDAD_ID = ?");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CIUDAD");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|getNombreCiudad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}