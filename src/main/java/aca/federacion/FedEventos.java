// Clase para la tabla de Modulo
package aca.federacion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FedEventos{	
	private String eventoId;
	private String eventoNombre;
	private String eventoDescripcion;
	private String fechaIni;
	private String fechaFin;
	private String tipo;
	
	public FedEventos(){
		eventoId			= "";
		eventoNombre		= "";
		fechaIni			= "";
		fechaFin			= "";		
		eventoDescripcion	= "";
		tipo				= "F";
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

	public String getEventoDescripcion() {
		return eventoDescripcion;
	}

	public void setEventoDescripcion(String eventoDescripcion) {
		this.eventoDescripcion = eventoDescripcion;
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

	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		eventoNombre		= rs.getString("EVENTO_NOMBRE");
 		eventoDescripcion	= rs.getString("EVENTO_DESCRIPCION");
 		fechaIni	 		= rs.getString("FECHA_INI");
 		fechaFin			= rs.getString("FECHA_FIN"); 
 		tipo				= rs.getString("TIPO");
 	} 	
}