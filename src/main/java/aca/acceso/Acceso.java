// Clase para la tabla de Acceso
package aca.acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Acceso{
	private String codigoPersonal;
	private String administrador;
	private String supervisor;
	private String cotejador;
	private String accesos;
	private String modalidad;
	private String usuario;
	private String clave;
	private String expira;
	private String ingreso;
	private String convalida;
	private String becas;
	private String portalAlumno;
	private String portalMaestro;
	private String idioma;
	private String menu;
	private String alumnoMovil;
	private String claveInicial;
	private String claveHexa;
	
	// Constructor
	public Acceso(){		
		codigoPersonal	= "0";
		administrador	= "";
		supervisor		= "";
		cotejador		= "";
		accesos			= "";
		modalidad		= "";
		usuario  		= "";
		clave    		= "";
		expira    		= "";
		ingreso    		= "";
		convalida 		= "N";
		becas			= "N";
		portalAlumno	= "N";
		portalMaestro	= "N";
		idioma			= "EN";
		menu			= "1";	
		alumnoMovil		= "N";
		claveInicial	= "-";
		claveHexa		= "X";
	}
	
	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the administrador
	 */
	public String getAdministrador() {
		return administrador;
	}

	/**
	 * @param administrador the administrador to set
	 */
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	/**
	 * @return the supervisor
	 */
	public String getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	/**
	 * @return the cotejador
	 */
	public String getCotejador() {
		return cotejador;
	}

	/**
	 * @param cotejador the cotejador to set
	 */
	public void setCotejador(String cotejador) {
		this.cotejador = cotejador;
	}

	/**
	 * @return the accesos
	 */
	public String getAccesos() {
		return accesos;
	}

	/**
	 * @param accesos the accesos to set
	 */
	public void setAccesos(String accesos) {
		this.accesos = accesos;
	}

	/**
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	/**
	 * @return the expira
	 */
	public String getExpira() {
		return expira;
	}

	/**
	 * @param expira the expira to set
	 */
	public void setExpira(String expira) {
		this.expira = expira;
	}

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}
		
	public String getConvalida() {
		return convalida;
	}

	public void setConvalida(String convalida) {
		this.convalida = convalida;
	}

	public String getBecas() {
		return becas;
	}

	public void setBecas(String becas) {
		this.becas = becas;
	}

	/**
	 * @return the portalAlumno
	 */
	public String getPortalAlumno() {
		return portalAlumno;
	}

	/**
	 * @param portalAlumno the portalAlumno to set
	 */
	public void setPortalAlumno(String portalAlumno) {
		this.portalAlumno = portalAlumno;
	}

	/**
	 * @return the portalMaestro
	 */
	public String getPortalMaestro() {
		return portalMaestro;
	}

	/**
	 * @param portalMaestro the portalMaestro to set
	 */
	public void setPortalMaestro(String portalMaestro) {
		this.portalMaestro = portalMaestro;
	}
	
	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public String getAlumnoMovil() {
		return alumnoMovil;
	}

	public void setAlumnoMovil(String alumnoMovil) {
		this.alumnoMovil = alumnoMovil;
	}

	public String getClaveInicial() {
		return claveInicial;
	}

	public void setClaveInicial(String claveInicial) {
		this.claveInicial = claveInicial;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
		administrador	= rs.getString("ADMINISTRADOR");
		supervisor		= rs.getString("SUPERVISOR");
		cotejador 		= rs.getString("COTEJADOR");
		accesos			= rs.getString("ACCESOS");
		modalidad		= rs.getString("MODALIDAD");
		usuario	     	= rs.getString("USUARIO");
		clave		    = rs.getString("CLAVE");
		ingreso		    = rs.getString("INGRESO");
		convalida	    = rs.getString("CONVALIDA");
		becas			= rs.getString("BECAS");
		portalAlumno	= rs.getString("PORTAL_ALUMNO");
		portalMaestro	= rs.getString("PORTAL_MAESTRO");
		idioma			= rs.getString("IDIOMA");
		menu			= rs.getString("MENU");
		alumnoMovil		= rs.getString("ALUMNO_MOVIL");
		claveInicial	= rs.getString("CLAVE_INICIAL");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{		
		PreparedStatement ps =null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, ADMINISTRADOR, "+
					"SUPERVISOR, COTEJADOR, COALESCE(ACCESOS,'X') AS ACCESOS, MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA, BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL, CLAVE_INICIAL "+
					"FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|mapeaRegId|:"+ex);
		}finally{			
			 try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
 
}