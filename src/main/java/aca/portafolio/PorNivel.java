//Bean del Catalogo de Peridos

package aca.portafolio;

import java.sql.*;

public class PorNivel {

	private String nivelId;
	private String nivelNombre;	
	private String archivo;
	private String documento_id;
	
	public String getDocumento_id() {
		return documento_id;
	}

	public void setDocumento_id(String documento_id) {
		this.documento_id = documento_id;
	}

	public PorNivel(){
		nivelId 		= "";
		nivelNombre		= "";		
		archivo			= "";
		documento_id 	= "";
	}
	
	/**
	 * @return the nivelId
	 */
	public String getNivelId() {
		return nivelId;
	}

	/**
	 * @param nivelId the nivelId to set
	 */
	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	/**
	 * @return the nivelNombre
	 */
	public String getNivelNombre() {
		return nivelNombre;
	}

	/**
	 * @param nivelNombre the nivelNombre to set
	 */
	public void setNivelNombre(String nivelNombre) {
		this.nivelNombre = nivelNombre;
	}

	/**
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		nivelId 		= rs.getString("NIVEL_ID");
		nivelNombre 	= rs.getString("NIVEL_NOMBRE");		
		archivo 		= rs.getString("ARCHIVO");
		documento_id 	= rs.getString("documento_id");
	}
	
	public void mapeaRegId( Connection conn, String nivelId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = ?"); 
			ps.setString(1,nivelId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = ?"); 
			ps.setString(1,nivelId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombre(Connection conn, String nivel) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT NIVEL_NOMBRE FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = '"+nivel+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NIVEL_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
}