// Bean de datos personales del alumno 
package  aca.alumno.spring;

import org.springframework.beans.factory.annotation.Autowired;

import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;

public class AlumPersonal{
 	private String codigoPersonal;
 	private String nombre;
 	private String apellidoPaterno;
 	private String apellidoMaterno;
 	private String nombreLegal;
 	private String fNacimiento;
 	private String sexo;
 	private String estadoCivil;
 	private String religionId;
 	private String bautizado;
 	private String paisId;
 	private String estadoId;
 	private String ciudadId;
 	private String nacionalidad;
 	private String email;
 	private String curp;
 	private String estado;
 	private String cotejado;
 	private String telefono;
 	private String direccion;
 	private String codigoSe;
 	private String fCreado;
 	private String usAlta;
  	private String NombreArch;
  	private String credencial;
	private String num_oferta;
  	private String fBautizado;
  	private String culturalId;
  	private String regionId;
	private String resPaisId;
	private String resEstadoId;
	private String resCiudadId;
	private String sync;

 	public AlumPersonal(){
 		codigoPersonal		= "0";
 		nombre				= "-";
 		apellidoPaterno		= "-";
 		apellidoMaterno		= "-";
 		nombreLegal			= "-";
 		fNacimiento			= "01/01/2000";
 		sexo				= "-";
 		estadoCivil			= "-";
 		religionId			= "1";
 		bautizado			= "-";
 		paisId				= "153";
 		estadoId			= "1";
 		ciudadId			= "1";
 		nacionalidad 		= "153";
 		email				= "-";
 		curp				= "-";
 		estado				= "-";
 		cotejado			= "N";
 		codigoSe			= "0";
 		fCreado				= "01/01/2000";
 		usAlta				= "-";
 		telefono			= "-";
 		direccion			= "-";
 		credencial			= "-";
 		num_oferta			= "0";
 		fBautizado			= "01/01/2000";
 		culturalId			= "";
 		regionId			= "";
		resPaisId 			= "153";
		resEstadoId 		= "1";
		resCiudadId 		= "1";
		sync 				= "S";
 	}

	public String getCodigoPersonal(){
 		return codigoPersonal;
 	}
 	
 	public void setCodigoPersonal( String Codigo_personal){
 		this.codigoPersonal = Codigo_personal;
 	}	
 	
 	public String getNombre(){
 		return nombre;
 	}
 	
 	public void setNombre( String Nombre){
 		this.nombre = Nombre;
 	}	
 	
 	public String getApellidoPaterno(){
 		return apellidoPaterno;
 	}
 	
 	public void setApellidoPaterno( String Apellido_paterno){
 		this.apellidoPaterno = Apellido_paterno;
 	}
 	
 	public String getApellidoMaterno(){
 		return apellidoMaterno;
 	}
 	
 	public void setApellidoMaterno( String Apellido_materno){
 		this.apellidoMaterno = Apellido_materno;
 	}
 	
 	public String getNombreLegal(){
 		return nombreLegal;
 	}
 	
 	public void setNombreLegal( String Nombre_legal){
 		this.nombreLegal = Nombre_legal;
 	}
 	
 	public String getFNacimiento(){
 		return fNacimiento;
 	}
 	
 	public void setFNacimiento( String F_nacimiento){
 		this.fNacimiento = F_nacimiento;
 	}
 	
 	public String getSexo(){
 		return sexo;
 	}
 	
 	public void setSexo( String Sexo){
 		this.sexo = Sexo;
 	}
 	
 	public String getEstadoCivil(){
 		return estadoCivil;
 	}
 	
 	public void setEstadoCivil( String Estado_civil){
 		this.estadoCivil = Estado_civil;
 	}
 	
 	public String getReligionId(){
 		return religionId;
 	}
 	
 	public void setReligionId( String Religion_id){
 		this.religionId = Religion_id;
 	}
 	
 	public String getBautizado(){
 		return bautizado;
 	}
 	
 	public void setBautizado( String Bautizado){
 		this.bautizado = Bautizado;
 	}
 	
 	public String getPaisId(){
 		return paisId;
 	}
 	
 	public void setPaisId( String Pais_id){
 		this.paisId = Pais_id;
 	}
 	
 	public String getEstadoId(){
 		return estadoId;
 	}
 	
 	public void setEstadoId( String Estado_id){
 		this.estadoId = Estado_id;
 	}
 	
 	public String getCiudadId(){
 		return ciudadId;
 	}
 	
 	public void setCiudadId( String Ciudad_id){
 		this.ciudadId = Ciudad_id;
 	}
 	
 	public String getNacionalidad(){
 		return nacionalidad;
 	}
 	
 	public void setNacionalidad( String Nacionalidad){
 		this.nacionalidad = Nacionalidad; 		
 	}
 	
 	public String getEmail(){
 		return email;
 	}
 	
 	public void setEmail( String Email){
 		this.email = Email;
 	}
 	
 	public String getCurp(){
 		return curp;
 	}
 	
 	public void setCurp( String Curp){
 		this.curp = Curp;
 	}
 	
 	public String getEstado(){
 		return estado;
 	}
 	
 	public void setEstado( String Estado){
 		this.estado = Estado;
 	}
 	
 	public String getCotejado(){
 		return cotejado;
 	}
 	
 	public void setCotejado( String Cotejado){
 		this.cotejado = Cotejado;
 	}
 	
 	public String getFcreado() {
		return fCreado;
	}

	public void setFCreado(String fCreado) {
		this.fCreado = fCreado;
	}
	
	public String getUsAlta() {
		return usAlta;
	}
	
	public void setUsAlta(String usAlta) {
		this.usAlta = usAlta;
	}

	public void setNombreArch(String nombreArch) {
 		NombreArch = nombreArch;
 	}

 	public String getNombreArch() { 		
 		return NombreArch; 		
 	}
 	
	public String getCodigoSe() {
		return codigoSe;
	}

	public void setCodigoSe(String codigoSe) {
		this.codigoSe = codigoSe;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getDireccion() {
		return direccion;
	} 
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCredencial() {
		return credencial;
	}
	public void setCredencial(String credencial) {
		this.credencial = credencial;
	}	

	public String getNum_oferta() {
		return num_oferta;
	}
	public void setNum_oferta(String num_oferta) {
		this.num_oferta = num_oferta;
	}

	public String getfBautizado() {
		return fBautizado;
	}
	public void setfBautizado(String fBautizado) {
		this.fBautizado = fBautizado;
	}

	public String getCulturalId() {
		return culturalId;
	}
	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getResPaisId() {
		return resPaisId;
	}
	public void setResPaisId(String resPaisId) {
		this.resPaisId = resPaisId;
	}

	public String getResEstadoId() {
		return resEstadoId;
	}
	public void setResEstadoId(String resEstadoId) {
		this.resEstadoId = resEstadoId;
	}

	public String getResCiudadId() {
		return resCiudadId;
	}
	public void setResCiudadId(String resCiudadId) {
		this.resCiudadId = resCiudadId;
	}

	public String getSync() {
		return sync;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
 	
 }