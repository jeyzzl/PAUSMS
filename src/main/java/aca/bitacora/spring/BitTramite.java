package aca.bitacora.spring;

public class BitTramite {

	private String tramiteId;
	private String nombre;
	private String areaId;
	private String minimo;
	private String maximo;
	private String promedio;
	private String requisitos;
	private String tipo;
	private String solAlumno;
	private String costo;

	private String importe;
	
	public BitTramite(){
		tramiteId 	= "";
		nombre 		= "";
		areaId		= "";
		minimo 		= "0";
		maximo 		= "0";
		promedio 	= "0";
		requisitos	= "";
		tipo 		= "";
		solAlumno	= "N";
		costo		= " ";
	}

	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMinimo() {
		return minimo;
	}

	public void setMinimo(String minimo) {
		this.minimo = minimo;
	}

	public String getMaximo() {
		return maximo;
	}

	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}
	
	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSolAlumno() {
		return solAlumno;
	}

	public void setSolAlumno(String solAlumno) {
		this.solAlumno = solAlumno;
	}
	
	public String getCosto() {
		return costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public String getImporte() {return importe;
	}

	public void setImporte(String importe) {this.importe = importe;
	}
}
