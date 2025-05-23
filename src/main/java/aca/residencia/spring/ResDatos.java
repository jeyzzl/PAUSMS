//Beans de la Res_Datos

package aca.residencia.spring;


public class ResDatos {
	private String matricula;
	private String periodoId;
	private String calle;
	private String colonia;
	private String mpio;
	private String telArea;
	private String telNum;
	private String nombreTut;
	private String apellidoTut;
	private String razon;
	private String usuario;
	private String fecha;
	private String numero;
	
	public ResDatos(){
		matricula 	= "0";
		periodoId 	= "0";
		calle 		= "";
		colonia 	= "";
		mpio 		= "";
		telArea 	= "";
		telNum 		= "";
		nombreTut 	= "";
		apellidoTut = "";
		razon 		= "0";
		usuario 	= "0";
		fecha 		= aca.util.Fecha.getHoy();
		numero 		= "0";
	}

	
	public String getMatricula() {
		return matricula;
	}


	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	public String getPeriodoId() {
		return periodoId;
	}


	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getColonia() {
		return colonia;
	}


	public void setColonia(String colonia) {
		this.colonia = colonia;
	}


	public String getMpio() {
		return mpio;
	}


	public void setMpio(String mpio) {
		this.mpio = mpio;
	}


	public String getTelArea() {
		return telArea;
	}


	public void setTelArea(String telArea) {
		this.telArea = telArea;
	}


	public String getTelNum() {
		return telNum;
	}


	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}


	public String getNombreTut() {
		return nombreTut;
	}


	public void setNombreTut(String nombreTut) {
		this.nombreTut = nombreTut;
	}


	public String getApellidoTut() {
		return apellidoTut;
	}


	public void setApellidoTut(String apellidoTut) {
		this.apellidoTut = apellidoTut;
	}


	public String getRazon() {
		return razon;
	}


	public void setRazon(String razon) {
		this.razon = razon;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}