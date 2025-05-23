//Bean del Portafolio
package aca.por;

import java.sql.*;

public class PorRequisito {
	
	private String requisitoId;
	private String descripcion;
	private String categoriaId;
	private String seccionId;
	private String valor;
	private String tipo;

	
	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(String requisitoId) {
		this.requisitoId = requisitoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSeccionId() {
		return seccionId;
	}

	public void setSeccionId(String seccionId) {
		this.seccionId = seccionId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.POR_REQUISITO( REQUISITO_ID, DESCRIPCION, CATEGORIA_ID, VALOR, SECCION_ID, TIPO)"					
					+ " VALUES( TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999.99'), ?, ?)");
			ps.setString(1, requisitoId);
			ps.setString(2, descripcion);
			ps.setString(3, categoriaId);
			ps.setString(4, valor);
			ps.setString(5, seccionId);
			ps.setString(6, tipo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_REQUISITO"
					+ " SET DESCRIPCION = ?,"
					+ " CATEGORIA_ID = TO_NUMBER(?,'99'),"
					+ " VALOR = TO_NUMBER(?,'999.99'),"
					+ " SECCION_ID = ?,"
					+ " TIPO = ?"
					+ " WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, descripcion);	
			ps.setString(2, categoriaId);
			ps.setString(3, valor);
			ps.setString(4, seccionId);
			ps.setString(5, tipo);
			ps.setString(6, requisitoId);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, requisitoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		requisitoId 		= rs.getString("REQUISITO_ID");
		descripcion 		= rs.getString("DESCRIPCION");		
		valor 				= rs.getString("VALOR");
		categoriaId 		= rs.getString("CATEGORIA_ID");
		seccionId 			= rs.getString("SECCION_ID");
		tipo				= rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_ID, DESCRIPCION, CATEGORIA_ID, VALOR, SECCION_ID, TIPO"
					+ " FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId( Connection conn) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT REQUISITO_ID, DESCRIPCION, CATEGORIA_ID, VALOR, SECCION_ID, TIPO"
					+ " FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		boolean 		ok 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1,requisitoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getDescripcion(Connection conn, String requisitoId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1,requisitoId);
			rs = ps.executeQuery();
			if (rs.next()){			
				nombre = rs.getString("DESCRIPCION");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|getDescripcion|:"+ex);
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
			ps = conn.prepareStatement("SELECT (MAX(REQUISITO_ID)+1) AS MAXIMO FROM ENOC.POR_REQUISITO");
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if(maximo == null)
				maximo = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisito|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
}