package aca.alerta;
import java.sql.*;



public class AlertaPeriodo{
	
	private String periodoId		= "";
	private String periodoNombre	= "";
	private String fechaIni			= "";
	private String fechaFin			= "";
	private String modalidades		= "";
	private String estado			= "";
	private String excepto			= "";
	
	
	public AlertaPeriodo(){
		
		periodoId		= "";
		periodoNombre	= "";
		fechaIni		= "";
		fechaFin		= "";
		modalidades		= "";
		estado			= "";
		excepto			= "";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getPeriodoNombre() {
		return periodoNombre;
	}

	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getModalidades() {
		return modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getExcepto() {
		return excepto;
	}

	public void setExcepto(String excepto) {
		this.excepto = excepto;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId		= rs.getString("PERIODO_ID");
		periodoNombre	= rs.getString("PERIODO_NOMBRE");
		fechaIni		= rs.getString("FECHA_INI");
		fechaFin		= rs.getString("FECHA_FIN");
		modalidades		= rs.getString("MODALIDADES");
		estado			= rs.getString("ESTADO");
		excepto			= rs.getString("EXCEPTO");
	}
	
public void mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
				"MODALIDADES, ESTADO FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = ? "); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}