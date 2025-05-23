// Bean del Catalogo de Estados
package  adm.catalogo;

import java.sql.*;

public class CatEstado{
	private String paisId;
	private String estadoId;
	private String nombreEstado;
	
	public CatEstado(){
		paisId 		= "";
		estadoId 		= "";
		nombreEstado	= "";
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
	
	public String getNombreEstado(){
		return nombreEstado;
	}
	
	public void setNombreEstado( String nombreEstado){
		this.nombreEstado = nombreEstado;
	}
	
	public boolean insertReg(Connection Conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO ENOC.CAT_ESTADO"+
				"(PAIS_ID, ESTADO_ID, NOMBRE_ESTADO) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, nombreEstado);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
			
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ESTADO "+
				"SET NOMBRE_ESTADO = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombreEstado);
			ps.setString(2, paisId);
			ps.setString(3, estadoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ESTADO "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 		= rs.getString("PAIS_ID");
		estadoId	 	= rs.getString("ESTADO_ID");
		nombreEstado 	= rs.getString("NOMBRE_ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String paisId, String estadoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO "+
			"FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '999')");
		ps.setString(1,paisId);
		ps.setString(2,estadoId);
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ESTADO "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String paisId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ESTADO_ID)+1 AS MAXIMO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreEstado(Connection conn, String paisId, String estadoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = ? AND ESTADO_ID = ?");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_ESTADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstado|getNombreEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}