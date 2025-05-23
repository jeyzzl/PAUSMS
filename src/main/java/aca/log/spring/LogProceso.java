package aca.log.spring;

public class LogProceso {
	
	public String folio;
	public String codigoPersonal;
	public String modulo;
	public String fecha;
	public String evento;
	
	public LogProceso() {
		folio 			= "0";
		codigoPersonal 	= "X";
		modulo 			= "-";
		fecha 			= "";
		evento 			= "-";
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

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}
}
