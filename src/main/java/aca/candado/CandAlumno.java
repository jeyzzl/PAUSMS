// Clase para la tabla de Modulo
package aca.candado;
import java.sql.*;

public class CandAlumno{ 
	private String codigoPersonal;
	private String folio;
	private String candadoId;
	private String fCreado;
	private String fBorrado;
	private String usAlta;
	private String usBaja;
	private String comentario;
	private String estado;
	
	// Constructor
	public CandAlumno(){
		codigoPersonal	= "";
		folio			= "";
		candadoId		= "";
		fCreado			= "";
		fBorrado		= "";
		usAlta			= "";
		usBaja			= "";
		comentario		= "";
		estado			= "";				
	}	
	
	/**
	 * @return Returns the candadoId.
	 */
	public String getCandadoId() {
		return candadoId;
	}
	/**
	 * @param candadoId The candadoId to set.
	 */
	public void setCandadoId(String candadoId) {
		this.candadoId = candadoId;
	}
	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	/**
	 * @return Returns the comentario.
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @param comentario The comentario to set.
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return Returns the fBorrado.
	 */
	public String getFBorrado() {
		return fBorrado;
	}
	/**
	 * @param borrado The fBorrado to set.
	 */
	public void setFBorrado(String borrado) {
		fBorrado = borrado;
	}
	/**
	 * @return Returns the fCreado.
	 */
	public String getFCreado() {
		return fCreado;
	}
	/**
	 * @param creado The fCreado to set.
	 */
	public void setFCreado(String creado) {
		fCreado = creado;
	}	
	/**
	 * @return Returns the folio.
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio The folio to set.
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	/**
	 * @return Returns the usAlta.
	 */
	public String getUsAlta() {
		return usAlta;
	}
	/**
	 * @param usAlta The usAlta to set.
	 */
	public void setUsAlta(String usAlta) {
		this.usAlta = usAlta;
	}
	/**
	 * @return Returns the usBaja.
	 */
	public String getUsBaja() {
		return usBaja;
	}
	/**
	 * @param usBaja The usBaja to set.
	 */
	public void setUsBaja(String usBaja) {
		this.usBaja = usBaja;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		candadoId  		= rs.getString("CANDADO_ID");
		fCreado			= rs.getString("F_CREADO");
		fBorrado		= rs.getString("F_BORRADO");
		usAlta			= rs.getString("US_ALTA");
		usBaja			= rs.getString("US_BAJA");
		comentario		= rs.getString("COMENTARIO");
		estado			= rs.getString("ESTADO");
	}

	public static String maximoReg(Connection conn, String codigoPersonal2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{		
				
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
				"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
				"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
				"US_ALTA, "+
				"US_BAJA, "+
				"COMENTARIO, "+
				"ESTADO "+
				"FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'999') ");
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
}