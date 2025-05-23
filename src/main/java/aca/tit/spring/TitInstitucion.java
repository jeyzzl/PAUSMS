package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/*
@XmlRootElement(name = "Institucion")
public class TitInstitucion {
    String cveInstitucion	 		= "";
    String nombreinstitucion 		= "";
	
    
    public String getCveInstitucion() {
		return cveInstitucion;
	}
    
    @XmlElement
	public void setCveInstitucion(String cveInstitucion) {
		this.cveInstitucion = cveInstitucion;
	}
    
	public String getNombreInstitucion() {
		return nombreinstitucion;
	}
	
	@XmlElement
	public void setNombreInstitucion(String nombreinstitucion) {
		this.nombreinstitucion = nombreinstitucion;
	}
}
*/

@XmlRootElement(name = "Institucion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitInstitucion {
	
	@XmlAttribute(name = "cveInstitucion", required = true)
    String cveInstitucion	 		= "";
	@XmlAttribute(name = "nombreInstitucion", required = true)
    String nombreinstitucion 		= "";	
	@XmlTransient
    String institucion 		= "";
	
    public String getCveInstitucion() {
		return cveInstitucion;
	}
    
	public void setCveInstitucion(String cveInstitucion) {
		this.cveInstitucion = cveInstitucion;
	}
    
	public String getNombreInstitucion() {
		return nombreinstitucion;
	}
	
	public void setNombreInstitucion(String nombreinstitucion) {
		this.nombreinstitucion = nombreinstitucion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
}
