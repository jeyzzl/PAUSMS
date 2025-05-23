// Bean del Catalogo de Grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupoBorra{
	private String cursoCargaId;
	private String folio;
	private String fecha;
	private String usuario;
	private String ip;
	private String numEst;
	private String numAct;
	
	public CargaGrupoBorra(){
		cursoCargaId	= "";
		folio			= "0";		
		fecha			= "";
		usuario			= "";
		ip				= "";
		numEst			= "";
		numAct			= "";		
	}	
	
	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
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

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the numEst
	 */
	public String getNumEst() {
		return numEst;
	}

	/**
	 * @param numEst the numEst to set
	 */
	public void setNumEst(String numEst) {
		this.numEst = numEst;
	}

	/**
	 * @return the numAct
	 */
	public String getNumAct() {
		return numAct;
	}

	/**
	 * @param numAct the numAct to set
	 */
	public void setNumAct(String numAct) {
		this.numAct = numAct;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		folio 			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		usuario	 		= rs.getString("USUARIO");
		ip				= rs.getString("IP");
		numEst			= rs.getString("NUMEST");
		numAct			= rs.getString("NUMACT");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String folio ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CURSO_CARGA_ID,"+
				" FOLIO,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"+
				" USUARIO,"+
				" IP,"+
				" NUMEST,"+
				" NUMACT"+			
				" FROM ENOC.CARGA_GRUPO_BORRA " + 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
	
}