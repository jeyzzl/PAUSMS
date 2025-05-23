package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmUbicacion{
	private String folio;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String calle;
	private String numero;
	private String codigoPostal;	
	private String telefono;
	private String colonia;
		
	public AdmUbicacion(){
		folio 			= "";
		paisId 			= "";
		estadoId 		= "";
		ciudadId		= "";
		calle 			= "";
		numero 			= "";
		codigoPostal 	= "";		
		telefono		= "";
		colonia			= "";
	}
	
	
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}





	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}





	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}



	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}



	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}



	/**
	 * @return the paisId
	 */
	public String getPaisId() {
		return paisId;
	}



	/**
	 * @param paisId the paisId to set
	 */
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}



	/**
	 * @return the estadoId
	 */
	public String getEstadoId() {
		return estadoId;
	}



	/**
	 * @param estadoId the estadoId to set
	 */
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}



	/**
	 * @return the ciudadId
	 */
	public String getCiudadId() {
		return ciudadId;
	}



	/**
	 * @param ciudadId the ciudadId to set
	 */
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}



	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}



	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}



	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}



	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}



	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}



	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}	

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_UBICACION"+ 
				"(FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE, NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), " +
				" ?,?,?,?, ?)");
			ps.setString(1, folio);
			ps.setString(2, paisId);
			ps.setString(3, estadoId);
			ps.setString(4, ciudadId);
			ps.setString(5, calle);
			ps.setString(6, numero);
			ps.setString(7, codigoPostal);			
			ps.setString(8, telefono);
			ps.setString(9, colonia);

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacion|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_UBICACION " + 
					" SET PAIS_ID = TO_NUMBER(?,'999'), " +
					" ESTADO_ID = TO_NUMBER(?,'999'), " +
					" CIUDAD_ID = TO_NUMBER(?,'999'), " +
					" CALLE = ?, " +
					" NUMERO = ?, " +
					" CODIGO_POSTAL = ?," +					
					" TELEFONO = ?," +
					" COLONIA = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			
			ps.setString(1,  paisId);
			ps.setString(2,  estadoId);
			ps.setString(3,  ciudadId);
			ps.setString(4,  calle);
			ps.setString(5,  numero);
			ps.setString(6,  codigoPostal);			
			ps.setString(7,  telefono);
			ps.setString(8,  colonia);
			ps.setString(9,  folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacion|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		paisId			= rs.getString("PAIS_ID");
		estadoId 		= rs.getString("ESTADO_ID");
		ciudadId		= rs.getString("CIUDAD_ID");
		calle			= rs.getString("CALLE");
		numero	 		= rs.getString("NUMERO");
		codigoPostal	= rs.getString("CODIGO_POSTAL");
		telefono		= rs.getString("TELEFONO");
		colonia			= rs.getString("COLONIA");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE," +
				" NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA"+
				" FROM SALOMON.ADM_UBICACION "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
						
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
	
}