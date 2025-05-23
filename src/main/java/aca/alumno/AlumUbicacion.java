// Bean de datos personales del alumno
package  aca.alumno;

import java.sql.*;

public class AlumUbicacion{
	private String codigoPersonal;
	private String pNombre;
	private String pReligion;
	private String pNacionalidad;
	private String mNombre;
	private String mReligion;
	private String mNacionalidad;
	private String tNombre;
	private String tDireccion;
	private String tColonia;
	private String tCodigo;
	private String tApartado;
	private String tTelefono;
	private String tEmail;
	private String tPais;
	private String tEstado;
	private String tCiudad;
	private String tCelular;
	private String tComunica;
	private String codigoPadre;
	private String codigoMadre;
	private String fecha;
	
	public AlumUbicacion(){
		codigoPersonal	= "";
		pNombre			= "";
		pReligion		= "";
		pNacionalidad	= "";
		mNombre			= "";
		mReligion		= "";
		mNacionalidad	= "";
		tNombre 		= "";
		tDireccion		= "";
		tColonia		= "";
		tCodigo			= "";
		tApartado		= "";
		tTelefono		= "";
		tEmail			= "";
		tPais			= "0";
		tEstado			= "0";
		tCiudad			= "0";
		tCelular		= "";
		tComunica		= "";
		codigoPadre		= "";
		codigoMadre		= "";
		fecha			= "";
		
	}
		
	public String gettComunica() {
		return tComunica;
	}

	public void settComunica(String tComunica) {
		this.tComunica = tComunica;
	}

	public String gettCelular() {
		return tCelular;
	}

	public void settCelular(String tCelular) {
		this.tCelular = tCelular;
	}

	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getPNombre(){
		return pNombre;
	}
	
	public void setPNombre( String pNombre){
		this.pNombre = pNombre;
	}	
	
	public String getPReligion(){
		return pReligion;
	}
	
	public void setPReligion( String pReligion){
		this.pReligion = pReligion;
	}
	
	public String getPNacionalidad(){
		return pNacionalidad;
	}
	
	public void setPNacionalidad( String pNacionalidad){
		this.pNacionalidad = pNacionalidad;
	}
	
	public String getMNombre(){
		return mNombre;
	}
	
	public void setMNombre( String mNombre){
		this.mNombre = mNombre;
	}
	
	public String getMReligion(){
		return mReligion;
	}
	
	public void setMReligion( String mReligion){
		this.mReligion = mReligion;
	}
	
	public String getMNacionalidad(){
		return mNacionalidad;
	}
	
	public void setMNacionalidad( String mNacionalidad){
		this.mNacionalidad = mNacionalidad;
	}
	
	public String getTNombre(){
		return tNombre;
	}
	
	public void setTNombre( String tNombre){
		this.tNombre = tNombre;
	}
	
	public String getTDireccion(){
		return tDireccion;
	}
	
	public void setTDireccion( String tDireccion){
		this.tDireccion = tDireccion;
	}
	
	public String getTColonia(){
		return tColonia;
	}
	
	public void setTColonia( String tColonia){
		this.tColonia = tColonia;
	}
	
	public String getTCodigo(){
		return tCodigo;
	}
	
	public void setTCodigo( String tCodigo){
		this.tCodigo = tCodigo;
	}
	
	public String getTApartado(){
		return tApartado;
	}
	
	public void setTApartado( String tApartado){
		this.tApartado = tApartado;
	}
	
	public String getTTelefono(){
		return tTelefono;
	}
	
	public void setTTelefono( String tTelefono){
		this.tTelefono = tTelefono;
	}
	
	public String getTEmail(){
		return tEmail;
	}
	
	public void setTEmail( String tEmail){
		this.tEmail = tEmail;
	}
	
	public String getTPais(){
		return tPais;
	}
	
	public void setTPais( String tPais){
		this.tPais = tPais;
	}
	
	public String getTEstado(){
		return tEstado;
	}
	
	public void setTEstado( String tEstado){
		this.tEstado = tEstado;
	}
	
	public String getTCiudad(){
		return tCiudad;
	}
	
	public void setTCiudad( String tCiudad){
		this.tCiudad = tCiudad;
	}
	
	public String getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public String getCodigoMadre() {
		return codigoMadre;
	}

	public void setCodigoMadre(String codigoMadre) {
		this.codigoMadre = codigoMadre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal = rs.getString("CODIGO_PERSONAL");
		pNombre 		= rs.getString("P_NOMBRE");
		pReligion 		= rs.getString("P_RELIGION");
		pNacionalidad	= rs.getString("P_NACIONALIDAD");
		mNombre 		= rs.getString("M_NOMBRE");
		mReligion		= rs.getString("M_RELIGION");
		mNacionalidad	= rs.getString("M_NACIONALIDAD");
		tNombre			= rs.getString("T_NOMBRE");
		tDireccion		= rs.getString("T_DIRECCION");
		tColonia		= rs.getString("T_COLONIA");
		tCodigo			= rs.getString("T_CODIGO");
		tApartado		= rs.getString("T_APARTADO");
		tTelefono		= rs.getString("T_TELEFONO");
		tEmail			= rs.getString("T_EMAIL");
		tPais			= rs.getString("T_PAIS");
		tEstado			= rs.getString("T_ESTADO");
		tCiudad			= rs.getString("T_CIUDAD");
		tCelular		= rs.getString("T_CELULAR");
		tComunica		= rs.getString("T_COMUNICA");
		codigoPadre     = rs.getString("CODIGO_PADRE");
		codigoMadre 	= rs.getString("CODIGO_MADRE");
		fecha     		= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, "+
				"P_NOMBRE, P_RELIGION, P_NACIONALIDAD, "+
				"M_NOMBRE, M_RELIGION, M_NACIONALIDAD, "+
				"T_NOMBRE, T_DIRECCION, T_COLONIA, T_CODIGO, T_APARTADO, T_TELEFONO, "+
				"T_EMAIL, T_PAIS, T_ESTADO, T_CIUDAD, T_CELULAR, T_COMUNICA, CODIGO_PADRE, CODIGO_MADRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA "+
				"FROM ENOC.ALUM_UBICACION WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.UbicacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	
	}
	
}