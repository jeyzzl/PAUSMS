package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/*
@XmlRootElement(name = "FirmaResponable")
@XmlType(propOrder = {"nombre", "primerApellido", "segundoApellido", "curp", "idCargo", "cargo", "abrTitulo", "sello", "certificado", "numeroCertificado"})
public class TitFirma {
	
    String codigoPersonal;
    String nombre;
    String primerApellido;
    String segundoApellido;
    String curp;
    String idCargo;
    String cargo;
    String abrTitulo;
    String sello;
    String certificado;
    String numeroCertificado;

    public TitFirma() {
    	
    	codigoPersonal		= "";
    	nombre				= "";
    	primerApellido 		= "";
    	segundoApellido		= "";
    	curp 	 			= "";
    	idCargo 			= "";
    	cargo				= "";
    	abrTitulo			= "";
    	sello				= "";
    	certificado			= "";
    	numeroCertificado	= "";
    }

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	@XmlTransient
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(String idCargo) {
		this.idCargo = idCargo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getAbrTitulo() {
		return abrTitulo;
	}

	public void setAbrTitulo(String abrTitulo) {
		this.abrTitulo = abrTitulo;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getCertificado() {
		return certificado;
	}

	@XmlElement(name = "certificadoResponsable")
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getNumeroCertificado() {
		return numeroCertificado;
	}

	@XmlElement(name = "noCertificadoResponsable")
	public void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
	}
}
*/

@XmlRootElement(name = "FirmaResponable")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "")
@XmlType(name = "", propOrder = {"nombre", "primerApellido", "segundoApellido", "curp", "idCargo", "cargo", "abrTitulo", "sello", "certificado", "numeroCertificado"})
public class TitFirma {
	
	@XmlTransient
    String codigoPersonal;
	@XmlTransient
	String institucion;
	@XmlTransient
	byte[] cer;	
	@XmlAttribute(name = "nombre", required = true)
    String nombre;
	@XmlAttribute(name = "primerApellido", required = true)
    String primerApellido;
	@XmlAttribute(name = "segundoApellido")
    String segundoApellido;
	@XmlAttribute(name = "curp", required = true)
    String curp;
	@XmlAttribute(name = "idCargo", required = true)
    String idCargo;
	@XmlAttribute(name = "cargo", required = true)
    String cargo;
	@XmlAttribute(name = "abrTitulo")
    String abrTitulo;
	@XmlAttribute(name = "sello", required = true)
    String sello;
	@XmlAttribute(name = "certificadoResponsable", required = true)
    String certificado;
	@XmlAttribute(name = "noCertificadoResponsable", required = true)
    String numeroCertificado;
	
	
    public TitFirma() {
    	
    	codigoPersonal		= "";
    	institucion 		= ""; 
    	nombre				= "";
    	primerApellido 		= "";
    	segundoApellido		= "";
    	curp 	 			= "";
    	idCargo 			= "";
    	cargo				= "";
    	abrTitulo			= "";
    	sello				= "";
    	certificado			= "";
    	numeroCertificado	= "";
    	cer					= null;
    }

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(String idCargo) {
		this.idCargo = idCargo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getAbrTitulo() {
		return abrTitulo;
	}

	public void setAbrTitulo(String abrTitulo) {
		this.abrTitulo = abrTitulo;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getNumeroCertificado() {
		return numeroCertificado;
	}

	public void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
	}

	public byte[] getCer() {
		return cer;
	}

	public void setCer(byte[] cer) {
		this.cer = cer;
	}
	
}