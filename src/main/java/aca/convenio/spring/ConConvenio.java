package aca.convenio.spring;

public class ConConvenio{
	
	private String id;
	private String nombre;
	private String fechaFirma;
	private String fechaVigencia;
	private String programa;	
	private String objetivo;
	private String estado;
	private String tipoId;
			
	public ConConvenio(){
		id 					= "0";
		nombre				= "-";
		fechaFirma			= aca.util.Fecha.getHoyReversa();
		fechaVigencia		= aca.util.Fecha.getHoyReversa();
		programa			= "-";
		objetivo			= "-";
		estado				= "1";
		tipoId				= "1";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(String fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public String getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
}