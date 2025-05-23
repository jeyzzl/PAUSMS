package aca.admision.spring;

/**
 * @author 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AdmPeriodo {
    private String eventoId;
    private String dia;
    private String periodoId;
    private String horaInicio;
    private String minInicio;
    private String horaFin;
    private String minFin;
    private String cupo;
    
    public AdmPeriodo() {    	
    	eventoId	 	= "0";
    	dia				= "0";
    	periodoId		= "A";
    	horaInicio		="";
    	minInicio		="";
    	horaFin			="";
    	minFin			="";
    	cupo			="";
    }

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getMinInicio() {
		return minInicio;
	}

	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getMinFin() {
		return minFin;
	}

	public void setMinFin(String minFin) {
		this.minFin = minFin;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}
    
}