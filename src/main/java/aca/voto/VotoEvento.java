package aca.voto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotoEvento {
	private String eventoId;	
	private String eventoNombre;
	private String fechaIni;
	private String fechaFin;
	private String tipo;
	private String poblacion;
	
	public VotoEvento(){
		eventoId		= "0";	
		eventoNombre	= "-";
		fechaIni		= "";
		fechaFin		= "";
		tipo			= "F";
		poblacion 		= "-"; 
	}

	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	
	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
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
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		eventoNombre		= rs.getString("EVENTO_NOMBRE");
 		fechaIni	 		= rs.getString("FECHA_INI");
 		fechaFin			= rs.getString("FECHA_FIN"); 
 		tipo				= rs.getString("TIPO");
 		poblacion			= rs.getString("POBLACION");
 	}
	
}