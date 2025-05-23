package aca.musica.spring;

public class MusiHorario {
	
	private String maestroId;
 	private String horaInicio;
 	private String horaFinal;
 	private String cupo;
 	private String valor;
 	private String dia;
 	private String estado;
 	private String minInicio;
 	private String minFinal;
 	private String cargaId;
 	private String folio;
 	
 	public MusiHorario(){
 		maestroId	= "0";
 		horaInicio	= "0";
 		horaFinal	= "0";
 		cupo		= "0";
 		valor		= "1";
 		dia			= "0";
 		estado		= "-";
 		minInicio	= "0";
 		minFinal	= "0";
 		cargaId		= "0";
 		folio		= "0";
 	}

	public String getMaestroId() {
		return maestroId;
	}

	public void setMaestroId(String maestroId) {
		this.maestroId = maestroId;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}
	
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMinInicio() {
		return minInicio;
	}

	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}

	public String getMinFinal() {
		return minFinal;
	}

	public void setMinFinal(String minFinal) {
		this.minFinal = minFinal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
 	
}
