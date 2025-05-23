 // Bean de datos personales del alumno 
 package  aca.alumno;
// import java.io.File;
 import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
// import java.io.InputStream;
// import java.sql.Blob; 
//import java.sql.DriverManager; 
 import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;

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
 	//private Blob blob;
 	private String codigoSe;
 	private String fCreado;
 	private String usAlta;
  	private String NombreArch;
  	private String credencial;
  	
 	//private InputStream blobStream;
 	//private String dirArch = "d:/academico/fotodb/img/";
  	//private String dirArch = "/var/tomcat4/webapps/academico/fotodb/img/";
    //private File Comp;
 	
 	public AlumPersonal(){
 		codigoPersonal		= "";
 		nombre				= "";
 		apellidoPaterno		= "";
 		apellidoMaterno		= "";
 		nombreLegal			= "";
 		fNacimiento			= "";
 		sexo				= "";
 		estadoCivil			= "";
 		religionId			= "";
 		bautizado			= "";
 		paisId				= "91";
 		estadoId			= "19";
 		ciudadId			= "";
 		nacionalidad 		= "91";
 		email				= "";
 		curp				= "";
 		estado				= "";
 		cotejado			= "N";
 		codigoSe			= "";
 		fCreado				= "";
 		usAlta				= "";
 		telefono			= "";
 		credencial			= "";
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
 	
 	/*public Blob getBlob(){
 		return blob;
 	}
 	
 	public void setBlob(Blob Blob){
 		this.blob = Blob;
 	}*/

 	/**
	 * @return the fCreado
	 */
	public String getFcreado() {
		return fCreado;
	}

	/**
	 * @param f_creado the f_creado to set
	 */
	public void setFCreado(String fCreado) {
		this.fCreado = fCreado;
	}

	/**
	 * @return the usAlta
	 */
	public String getUsAlta() {
		return usAlta;
	}

	/**
	 * @param us_alta the usAlta to set
	 */
	public void setUsAlta(String usAlta) {
		this.usAlta = usAlta;
	}

	public void setNombreArch(String nombreArch) {
 		NombreArch = nombreArch;
 	}

 	public String getNombreArch() { 		
 		return NombreArch; 		
 	}
 	
 	
 	/**
	 * @return the codigoSe
	 */
	public String getCodigoSe() {
		return codigoSe;
	}

	/**
	 * @param codigoSe the codigoSe to set
	 */
	public void setCodigoSe(String codigoSe) {
		this.codigoSe = codigoSe;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCredencial() {
		return credencial;
	}

	public void setCredencial(String credencial) {
		this.credencial = credencial;
	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		nombre 				= rs.getString("NOMBRE");
 		apellidoPaterno 	= rs.getString("APELLIDO_PATERNO");
 		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
 		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
 		fNacimiento			= rs.getString("F_NACIMIENTO");
 		sexo 				= rs.getString("SEXO");
 		estadoCivil 		= rs.getString("ESTADO_CIVIL");
 		religionId 			= rs.getString("RELIGION_ID");
 		bautizado			= rs.getString("BAUTIZADO");
 		paisId 				= rs.getString("PAIS_ID");
 		estadoId			= rs.getString("ESTADO_ID");
 		ciudadId			= rs.getString("CIUDAD_ID");
 		nacionalidad		= rs.getString("NACIONALIDAD");
 		email				= rs.getString("EMAIL");
 		curp				= rs.getString("CURP");
 		estado				= rs.getString("ESTADO");
 		cotejado			= rs.getString("COTEJADO");
 		codigoSe			= rs.getString("CODIGO_SE");
 		telefono			= rs.getString("TELEFONO");
 		credencial			= rs.getString("CREDENCIAL");
 		//fCreado			= rs.getString("F_CREADO");
 		//usAlta				= rs.getString("US_ALTA");
 	} 	

 	public void mapeaRegCompleto(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		nombre 				= rs.getString("NOMBRE");
 		apellidoPaterno 	= rs.getString("APELLIDO_PATERNO");
 		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
 		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
 		fNacimiento			= rs.getString("F_NACIMIENTO");
 		sexo 				= rs.getString("SEXO");
 		estadoCivil 		= rs.getString("ESTADO_CIVIL");
 		religionId 			= rs.getString("RELIGION_ID");
 		bautizado			= rs.getString("BAUTIZADO");
 		paisId 				= rs.getString("PAIS_ID");
 		estadoId			= rs.getString("ESTADO_ID");
 		ciudadId			= rs.getString("CIUDAD_ID");
 		nacionalidad		= rs.getString("NACIONALIDAD");
 		email				= rs.getString("EMAIL");
 		curp				= rs.getString("CURP");
 		estado				= rs.getString("ESTADO");
 		cotejado			= rs.getString("COTEJADO");
 		codigoSe			= rs.getString("CODIGO_SE");
 		fCreado				= rs.getString("F_CREADO");
 		telefono			= rs.getString("TELEFONO");
 		credencial			= rs.getString("CREDENCIAL");
 		usAlta				= rs.getString("US_ALTA");
 	}
 	
 	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		
 		PreparedStatement ps 	= null;
 		ResultSet rs 			= null;
 		
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
	 			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
	 			" COALESCE(SEXO, 'M') SEXO, COALESCE(ESTADO_CIVIL, 'S') ESTADO_CIVIL,"+
	 			" COALESCE(RELIGION_ID, 1) RELIGION_ID, COALESCE(BAUTIZADO,'S') BAUTIZADO,"+
	 			" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"+
	 			" EMAIL, CURP, COALESCE(ESTADO,'A') ESTADO, COALESCE(COTEJADO,'N') COTEJADO,"+
	 			" CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, CREDENCIAL"+
	 			" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}		
 		
 	}
 	
 	public void mapeaRegIdCredencial(Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		
 		PreparedStatement ps 	= null;
 		ResultSet rs 			= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT "+
 					" CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
 					" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
 					" COALESCE(SEXO, 'M') SEXO, COALESCE(ESTADO_CIVIL, 'S') ESTADO_CIVIL,"+
 					" COALESCE(RELIGION_ID, 1) RELIGION_ID, COALESCE(BAUTIZADO,'S') BAUTIZADO,"+
 					" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"+
 					" EMAIL, CURP, COALESCE(ESTADO,'A') ESTADO, COALESCE(COTEJADO,'N') COTEJADO,"+
 					" CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, CREDENCIAL"+
 					" FROM ENOC.ALUM_PERSONAL WHERE CREDENCIAL = ?"); 
 			ps.setString(1, codigoPersonal);	
 			rs = ps.executeQuery();
 			if (rs.next()){
 				mapeaReg(rs);
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 			try { ps.close(); } catch (Exception ignore) { }
 		}		
 		
 	}
 	
 }