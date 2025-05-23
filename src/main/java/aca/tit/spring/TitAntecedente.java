package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;


/*
@XmlRootElement(name = "Antecedente")
@XmlType(propOrder = {"institucion", "estudioId", "estudio", "entidadId", "entidad", "fechaInicio", "fechaTerminacion", "cedula"})
public class TitAntecedente {
	
    String folio;
    String institucion;
    String estudioId;
    String estudio;
    String entidadId;
    String entidad;
    String fechaInicio;
    String fechaTerminacion;
    String cedula;

    public TitAntecedente() {
    	
    	folio				= "";
    	institucion 		= "";
    	estudioId 	 		= "";
    	estudio				= "";
    	entidadId 	 		= "";
    	entidad				= "";
    	fechaInicio			= "";
    	fechaTerminacion	= "";
    	cedula				= "";
    }

	public String getFolio() {
		return folio;
	}

	@XmlTransient
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getInstitucion() {
		return institucion;
	}

	@XmlElement(name = "institucionProcedencia")
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getEstudioId() {
		return estudioId;
	}

	@XmlElement(name = "idTipoEstudioAntecedente")
	public void setEstudioId(String estudioId) {
		this.estudioId = estudioId;
	}

	public String getEstudio() {
		return estudio;
	}

	@XmlElement(name = "tipoEstudioAntecedente")
	public void setEstudio(String estudio) {
		this.estudio = estudio;
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

	public String getCedula() {
		return cedula;
	}

	@XmlElement(name = "noCedula")
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
    
}
*/

@XmlRootElement(name = "Antecedente")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitAntecedente {
	
	@XmlTransient
    String folio;
	@XmlAttribute(name = "institucionProcedencia", required = true)
    String institucion;
	@XmlAttribute(name = "idTipoEstudioAntecedente", required = true)
    String estudioId;
	@XmlAttribute(name = "tipoEstudioAntecedente", required = true)
    String estudio;
	@XmlAttribute(name = "idEntidadFederativa", required = true)
    String entidadId;
	@XmlAttribute(name = "entidadFederativa")
    String entidad;
	@XmlAttribute(name = "fechaInicio")
    @XmlSchemaType(name = "date")
    String fechaInicio;
	@XmlAttribute(name = "fechaTerminacion", required = true)
    @XmlSchemaType(name = "date")
    String fechaTerminacion;
	@XmlAttribute(name = "noCedula")
    String cedula;

    public TitAntecedente() {
    	
    	folio				= "";
    	institucion 		= "";
    	estudioId 	 		= "";
    	estudio				= "";
    	entidadId 	 		= "0";
    	entidad				= "";
    	fechaInicio			= "";
    	fechaTerminacion	= "";
    	cedula				= "";
    }

	public String getFolio() {
		return folio;
	}

	
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getEstudioId() {
		return estudioId;
	}

	public void setEstudioId(String estudioId) {
		this.estudioId = estudioId;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
    
}