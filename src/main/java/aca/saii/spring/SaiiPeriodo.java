package aca.saii.spring;
import java.sql.*;

public class SaiiPeriodo{
	
	private String periodoId		= "";
	private String periodoNombre	= "";
	private String estado			= "";
	private String fecha			= "";	
	
	public SaiiPeriodo(){
		
		periodoId		= "0";
		periodoNombre	= "-";
		estado			= "A";
		fecha			= aca.util.Fecha.getHoy();
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId		= rs.getString("PERIODO_ID");
		periodoNombre	= rs.getString("PERIODO_NOMBRE");
		estado			= rs.getString("ESTADO");
		fecha			= rs.getString("FECHA");
	}
		
}
