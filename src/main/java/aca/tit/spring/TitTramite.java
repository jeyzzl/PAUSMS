package aca.tit.spring;

public class TitTramite {

	String tramiteId;
    String fecha;
    String descripcion;
    String estado;
    String institucion;
    String recibo;

    
    public TitTramite() {
    	tramiteId 	= "";
    	fecha		= "";
    	descripcion = "";
    	estado		= "";
    	institucion = "UM";
    	recibo 		= "0";
    }
    
	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}
    
}
