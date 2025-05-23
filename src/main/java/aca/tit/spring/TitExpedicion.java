package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/*
@XmlRootElement(name = "Expedicion")
@XmlType(propOrder = {"fechaExpedicion", "modalidadId", "modalidad", "fechaExamen", "fechaExencion", "servicio", "fundamentoId", "fundamento", "entidadId", "entidad"})
public class TitExpedicion {
	
    String folio;
    String fechaExpedicion;
    String modalidadId;
    String modalidad;
    String fechaExamen;
    String fechaExencion;
    String servicio;
    String fundamentoId;
    String fundamento;
    String entidadId;
    String entidad;

    public TitExpedicion() {
    	
    	folio 	 		= "";
    	fechaExpedicion	= "";
    	modalidadId 	= "";
    	modalidad		= "";
    	fechaExamen 	= "";
    	fechaExencion	= "";
    	servicio 		= "";
    	fundamentoId	= "";
    	fundamento		= "";
    	entidadId 		= "";
    	entidad 		= "";
    }

	public String getFolio() {
		return folio;
	}
	
	@XmlTransient
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaExpedicion() {
		return fechaExpedicion;
	}

	@XmlElement
	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getModalidadId() {
		return modalidadId;
	}
	
	@XmlElement(name = "idModalidadTitulacion")
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getModalidad() {
		return modalidad;
	}

	@XmlElement(name = "modalidadTitulacion")
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getFechaExamen() {
		return fechaExamen;
	}

	@XmlElement(name = "fechaExamenProfesional")
	public void setFechaExamen(String fechaExamen) {
		this.fechaExamen = fechaExamen;
	}

	public String getFechaExencion() {
		return fechaExencion;
	}
	
	@XmlElement(name = "fechaExencionExamenProfesional")
	public void setFechaExencion(String fechaExencion) {
		this.fechaExencion = fechaExencion;
	}

	public String getServicio() {
		return servicio;
	}
	
	@XmlElement(name = "cumplioServicioSocial")
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getFundamentoId() {
		return fundamentoId;
	}

	@XmlElement(name = "idFundamentoLegalServicioSocial")
	public void setFundamentoId(String fundamentoId) {
		this.fundamentoId = fundamentoId;
	}

	public String getFundamento() {
		return fundamento;
	}

	@XmlElement(name = "fundamentoLegalServicioSocial")
	public void setFundamento(String fundamento) {
		this.fundamento = fundamento;
	}

	public String getEntidadId() {
		return entidadId;
	}

	@XmlElement(name = "idEntidadFederativa")
	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	public String getEntidad() {
		return entidad;
	}

	@XmlElement(name = "entidadFederativa")
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
}
*/

@XmlRootElement(name = "Expedicion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitExpedicion {
	
	@XmlTransient
    String folio;
    @XmlAttribute(name = "fechaExpedicion", required = true)
    @XmlSchemaType(name = "date")
    String fechaExpedicion;
    @XmlAttribute(name = "idModalidadTitulacion", required = true)
    String modalidadId;
    @XmlAttribute(name = "modalidadTitulacion", required = true)
    String modalidad;
    @XmlAttribute(name = "fechaExamenProfesional")
    @XmlSchemaType(name = "date")
    String fechaExamen;
    @XmlAttribute(name = "fechaExencionExamenProfesional")
    @XmlSchemaType(name = "date")
    String fechaExencion;
    @XmlAttribute(name = "cumplioServicioSocial", required = true)
    String servicio;
    @XmlAttribute(name = "idFundamentoLegalServicioSocial", required = true)
    String fundamentoId;
    @XmlAttribute(name = "fundamentoLegalServicioSocial", required = true)
    String fundamento;
    @XmlAttribute(name = "idEntidadFederativa", required = true)
    String entidadId;
    @XmlAttribute(name = "entidadFederativa", required = true)
    String entidad;

    public TitExpedicion() {
    	
    	folio 	 		= "";
    	fechaExpedicion	= "0";
    	modalidadId 	= "";
    	modalidad		= "";
    	fechaExamen 	= "";
    	fechaExencion	= "";
    	servicio 		= "";
    	fundamentoId	= "2";
    	fundamento		= "";
    	entidadId 		= "0";
    	entidad 		= "";
    }

	public String getFolio() {
		return folio;
	}
	
	
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getModalidadId() {
		return modalidadId;
	}
	
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getFechaExamen() {
		return fechaExamen;
	}

	public void setFechaExamen(String fechaExamen) {
		this.fechaExamen = fechaExamen;
	}

	public String getFechaExencion() {
		return fechaExencion;
	}
	
	public void setFechaExencion(String fechaExencion) {
		this.fechaExencion = fechaExencion;
	}

	public String getServicio() {
		return servicio;
	}
	
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getFundamentoId() {
		return fundamentoId;
	}

	public void setFundamentoId(String fundamentoId) {
		this.fundamentoId = fundamentoId;
	}

	public String getFundamento() {
		return fundamento;
	}

	public void setFundamento(String fundamento) {
		this.fundamento = fundamento;
	}

	public String getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(String entidadId) {
		this.entidadId = entidadId;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
}