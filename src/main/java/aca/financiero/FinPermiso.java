/**
 * 
 */
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class FinPermiso {
	private String codigoPersonal;
	private String folio;
	private String fInicio;
	private String fLimite;
	private String usuario;
	private String comentario;
	
	public FinPermiso(){
		codigoPersonal	= "";
		folio			= "";
		fInicio			= "";
		fLimite			= "";
		usuario			= "";
		comentario		= "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}

	/**
	 * @param inicio the fInicio to set
	 */
	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	/**
	 * @return the fLimite
	 */
	public String getFLimite() {
		return fLimite;
	}

	/**
	 * @param limite the fLimite to set
	 */
	public void setFLimite(String limite) {
		fLimite = limite;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		fInicio			= rs.getString("F_INICIO");
		fLimite			= rs.getString("F_LIMITE");
		usuario			= rs.getString("USUARIO");
		comentario		= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{	
	
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_LIMITE, 'DD/MM/YYYY') AS F_LIMITE," +
					" USUARIO, COMENTARIO"+				
					" FROM ENOC.FIN_PERMISO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(?, 'DD/MM/YYYY')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinPermiso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}