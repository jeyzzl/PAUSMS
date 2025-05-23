package aca.admision.spring;

public class AdmCartaSubir {
	
	private String folio;
	private String nombre;
	private byte[] carta;
	private String fecha;
	
	public AdmCartaSubir() {
		folio 		= "0";
		nombre		= "0";
		carta		= null;
		fecha 		= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getCarta() {
		return carta;
	}

	public void setCarta(byte[] carta) {
		this.carta = carta;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
