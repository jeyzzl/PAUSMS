package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitTramiteAlumno {
	
	private String folio;
	private String codigoPersonal;
	private String tramiteId;
	private String fechaInicio;
	private String fechaFinal;
	private String estado;
	private String areaId;
	private String areaOrigen;
	private String folioOrigen;
	private String usuario;
	private String comentario;
	
	public BitTramiteAlumno(){
		folio 				= "0";
		codigoPersonal		= "0";
		tramiteId			= "0";
		fechaInicio			= "";
		fechaFinal			= "";
		estado				= "";
		areaId				= "0";
		areaOrigen			= "0";
		folioOrigen			= "-";
		usuario 			= "-";
		comentario 			= "-";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaOrigen() {
		return areaOrigen;
	}

	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		folio 				= rs.getString("FOLIO");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		tramiteId 			= rs.getString("TRAMITE_ID");
		fechaInicio 		= rs.getString("FECHA_INICIO");
		fechaFinal 			= rs.getString("FECHA_FINAL");
		estado 				= rs.getString("ESTADO");
		areaId 				= rs.getString("AREA_ID");
		areaOrigen 			= rs.getString("AREA_ORIGEN");
		folioOrigen 		= rs.getString("FOLIO_ORIGEN");
		usuario 			= rs.getString("USUARIO");
		comentario 			= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId( Connection conn, String folio) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " ESTADO, AREA_ID, AREA_ORIGEN, FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO = '"+folio+"'");
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}
