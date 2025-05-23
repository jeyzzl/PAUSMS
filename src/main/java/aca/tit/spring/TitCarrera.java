package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/*
@XmlRootElement(name = "Carrera")
@XmlType(propOrder = {"cveCarrera", "nombreCarrera", "fechaInicio", "fechaTerminacion", "idAutorizacion", "autorizacion", "numeroRvoe"})
public class TitCarrera {
	
    String cveCarrera;
    String nombreCarrera;
    String fechaInicio;
    String fechaTerminacion;
    String idAutorizacion;
    String autorizacion;
    String numeroRvoe;
    String folio;

    public TitCarrera() {
    	
    	cveCarrera 	 		= "";
    	nombreCarrera		= "";
    	fechaInicio 	 	= "";
    	fechaTerminacion	= "";
    	idAutorizacion 	 	= "";
    	autorizacion		= "";
    	numeroRvoe 	 		= "";
    	folio	 	 		= "";
    }

	public String getFolio() {
		return folio;
	}
	
	@XmlTransient
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCveCarrera() {
		return cveCarrera;
	}

	@XmlElement
	public void setCveCarrera(String cveCarrera) {
		this.cveCarrera = cveCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	@XmlElement
	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	@XmlElement
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	@XmlElement
	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public String getIdAutorizacion() {
		return idAutorizacion;
	}

	@XmlElement(name = "idAutorizacionReconocimiento")
	public void setIdAutorizacion(String idAutorizacion) {
		this.idAutorizacion = idAutorizacion;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	@XmlElement(name = "autorizacionReconocimiento")
	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getNumeroRvoe() {
		return numeroRvoe;
	}

	@XmlElement
	public void setNumeroRvoe(String numeroRvoe) {
		this.numeroRvoe = numeroRvoe;
	}   	
}
*/

@XmlRootElement(name = "Carrera")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitCarrera {
	
	@XmlAttribute(name = "cveCarrera", required = true)
    String cveCarrera;
	@XmlAttribute(name = "nombreCarrera", required = true)
    String nombreCarrera;
	@XmlAttribute(name = "fechaInicio")
    @XmlSchemaType(name = "date")
    String fechaInicio;
	@XmlAttribute(name = "fechaTerminacion", required = true)
    @XmlSchemaType(name = "date")
    String fechaTerminacion;
	@XmlAttribute(name = "idAutorizacionReconocimiento", required = true)
    String idAutorizacion;
	@XmlAttribute(name = "autorizacionReconocimiento", required = true)
    String autorizacion;
	@XmlAttribute(name = "numeroRvoe")
    String numeroRvoe;
    @XmlTransient
    String folio;

    public TitCarrera() {    	
    	cveCarrera 	 		= "-";
    	nombreCarrera		= "-";
    	fechaInicio 	 	= "";
    	fechaTerminacion	= "";
    	idAutorizacion 	 	= "0";
    	autorizacion		= "";
    	numeroRvoe 	 		= "-";
    	folio	 	 		= "0";
    }

	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCveCarrera() {
		return cveCarrera;
	}

	public void setCveCarrera(String cveCarrera) {
		this.cveCarrera = cveCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public String getIdAutorizacion() {
		return idAutorizacion;
	}

	public void setIdAutorizacion(String idAutorizacion) {
		this.idAutorizacion = idAutorizacion;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getNumeroRvoe() {
		return numeroRvoe;
	}

	public void setNumeroRvoe(String numeroRvoe) {
		this.numeroRvoe = numeroRvoe;
	}
}
