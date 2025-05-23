package  aca.calcula.spring;

public class CalCosto{
	
	private String costoId;
	private String conceptoId;
	private String cargaId;
	private String bloqueId;
	private String tipo;
	private String importe;
	private String comentario;	
		
	public CalCosto(){
		costoId			= "";
		conceptoId		= "";
		cargaId			= "";
		bloqueId		= "";
		tipo			= "";
		importe			= "";
		comentario		= "-";	
	}

	public String getCostoId() {
		return costoId;
	}

	public void setCostoId(String costoId) {
		this.costoId = costoId;
	}

	public String getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(String conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloqueId() {
		return bloqueId;
	}

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}