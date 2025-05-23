package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/*
@XmlRootElement(name = "Profesionista")
@XmlType(propOrder = {"curp", "nombre", "primerApellido", "segundoApellido", "correoElectronico"})
public class TitProfesional {
	
    String folio;
    String curp;
    String nombre;
    String primerApellido;
    String segundoApellido;
    String correoElectronico;
    
    public TitProfesional() {
    	
    	folio 				= "";
    	curp 				= "";
    	nombre 				= "";
    	primerApellido		= "";
    	segundoApellido 	= "";
    	correoElectronico 	= "";
    }

	public String getFolio() {
		return folio;
	}

	@XmlTransient
	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCurp() {
		return curp;
	}

	@XmlElement
	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	@XmlElement
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	@XmlElement
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	@XmlElement
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	@XmlElement
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}
*/

@XmlRootElement(name = "Profesionista")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class TitProfesional {
	
	@XmlTransient
    String folio;
	@XmlAttribute(name = "curp", required = true)
    String curp;
	@XmlAttribute(name = "nombre", required = true)
    String nombre;
	@XmlAttribute(name = "primerApellido", required = true)
    String primerApellido;
	@XmlAttribute(name = "segundoApellido")
    String segundoApellido;
	@XmlAttribute(name = "correoElectronico", required = true)
    String correoElectronico;
    
    public TitProfesional() {    	
    	folio 				= "";
    	curp 				= "";
    	nombre 				= "";
    	primerApellido		= "";
    	segundoApellido 	= "";
    	correoElectronico 	= "";
    }

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
}