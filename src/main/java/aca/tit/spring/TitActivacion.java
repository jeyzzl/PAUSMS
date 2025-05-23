package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitActivacion {

	@XmlAttribute(name = "institucion")
	protected String institucion;
	@XmlAttribute(name = "inicio")
	protected String inicio;
	@XmlAttribute(name = "fin")
	protected String fin;
	@XmlAttribute(name = "selloActivacion")
	protected String selloActivacion;
	
	public String getInstitucion() {
		return institucion;
	}
	
	public void setInstitucion(String value) {
		this.institucion = value;
	}
	
	public String getInicio() {
		return inicio;
	}
	
	public void setInicio(String value) {
		this.inicio = value;
	}
	
	public String getFin() {
		return fin;
	}
	
	public void setFin(String value) {
		this.fin = value;
	}
	
	public String getSelloActivacion() {
		return selloActivacion;
	}
	
	public void setSelloActivacion(String value) {
		this.selloActivacion = value;
	}
}
