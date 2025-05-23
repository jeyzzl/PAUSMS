/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

public class TitAlumno {
	
    private String folio;
    private String codigoPersonal;
    private String planId;
    private String fecha;
    private String estado;
    private String institucion;
    private String xml;
    private String respuesta;
    private byte[] xmlSep;
    private String folioFisico;
    private String fechaRes;
    private String folioControl;
    
    public TitAlumno() {    	
    	folio 	 		= "";
    	codigoPersonal	= "";
    	planId 	 		= "";
    	fecha			= "";
    	estado 	 		= "";
    	institucion 	= "UM";
    	xml				= "XML";
    	respuesta 		= "X";
    	xmlSep			= null;
    	folioFisico 	= "X";
    	fechaRes		= "";
    	folioControl	= "0";
    }

	public String getFolioFisico() {
		return folioFisico;
	}
	public void setFolioFisico(String folioFisico) {
		this.folioFisico = folioFisico;
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
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public byte[] getXmlSep() {
		return xmlSep;
	}
	public void setXmlSep(byte[] xmlSep) {
		this.xmlSep = xmlSep;
	}

	public String getFechaRes() {
		return fechaRes;
	}
	public void setFechaRes(String fechaRes) {
		this.fechaRes = fechaRes;
	}

	public String getFolioControl() {
		return folioControl;
	}
	public void setFolioControl(String folioControl){
		this.folioControl = folioControl;
	}	
}