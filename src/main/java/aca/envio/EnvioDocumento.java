package aca.envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvioDocumento {
	private String folio;
	private String matricula;
	private String destinatario;
	private String servicioId;
	private String fecha;
	private String documentos;
	private String numguia;
	private String paisId;
	private String estadoId;
	private String direccion;
	private String pago;
	
	public EnvioDocumento(){
		folio			= "";
		matricula		= "";
		destinatario	= "";
		servicioId		= "";
		fecha			= "";
		documentos		= "";
		numguia			= "";
		paisId			= "";
		estadoId		= "";
		direccion		= "";
		pago			= "";
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
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the servicioId
	 */
	public String getServicioId() {
		return servicioId;
	}

	/**
	 * @param servicioId the servicioId to set
	 */
	public void setServicioId(String servicioId) {
		this.servicioId = servicioId;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the documentos
	 */
	public String getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return the numguia
	 */
	public String getNumguia() {
		return numguia;
	}

	/**
	 * @param numguia the numguia to set
	 */
	public void setNumguia(String numguia) {
		this.numguia = numguia;
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
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the pago
	 */
	public String getPago() {
		return pago;
	}

	/**
	 * @param pago the pago to set
	 */
	public void setPago(String pago) {
		this.pago = pago;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		matricula		= rs.getString("MATRICULA");
		destinatario	= rs.getString("DESTINATARIO");
		servicioId		= rs.getString("SERVICIO_ID");
		fecha			= rs.getString("FECHA");
		documentos		= rs.getString("DOCUMENTOS");
		numguia			= rs.getString("NUMGUIA");
		paisId 			= rs.getString("PAIS_ID");
		estadoId		= rs.getString("ESTADO_ID");
		direccion		= rs.getString("DIRECCION");
		pago			= rs.getString("PAGO");
	}
	
	public void mapeaRegId(Connection con, String idEmpleado, String folio) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT FOLIO, MATRICULA, DESTINATARIO, SERVICIO_ID," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, DOCUMENTOS, NUMGUIA, PAIS_ID, ESTADO_ID," +
					" DIRECCION, PAGO FROM ENVIO_DOCUMENTO " +
					" WHERE FOLIO = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	}
	
}