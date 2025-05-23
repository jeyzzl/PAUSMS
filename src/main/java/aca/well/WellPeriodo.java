package aca.well;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WellPeriodo {

	private String periodoId;
	private String periodoNombre;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	public WellPeriodo(){
		periodoId 		= "";
		periodoNombre 	= "";
		fechaIni	 	= "";
		fechaFin 		= "";
		estado 			= "";
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		periodoId			= rs.getString("PERIODO_ID");
		periodoNombre		= rs.getString("PERIODO_NOMBRE");
		fechaIni			= rs.getString("FECHA_INI");
	    fechaFin			= rs.getString("FECHA_FIN");
		estado				= rs.getString("ESTADO");
 	}
	
}
