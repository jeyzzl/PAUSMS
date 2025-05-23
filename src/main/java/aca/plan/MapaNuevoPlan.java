/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class MapaNuevoPlan {
	private String planId;
	private String carreraId;
	private String nombre;
	private	String tipo;
	private String versionId;
	private String versionNombre;
	private String estado;	
	private String hts;
	private String hps;
	private String hfd;
	private String hei;
	private String idioma;
	private String hss;
	private String has;
	private String year;
	
	public MapaNuevoPlan(){
		planId			= "";
		carreraId		= "";
		nombre			= "";
		tipo			= "";
		versionId		= "";
		versionNombre	= "";
		estado			= "";
		hts				= "";
		hps				= "";
		hfd				= "";
		hei				= "";
		idioma			= "";
		hss				= "";
		has				= "";
		year			= "";
	}
	
	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}

	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the versionNombre
	 */
	public String getVersionNombre() {
		return versionNombre;
	}

	/**
	 * @param versionNombre the versionNombre to set
	 */
	public void setVersionNombre(String versionNombre) {
		this.versionNombre = versionNombre;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}	

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the hts
	 */
	public String getHts() {
		return hts;
	}

	/**
	 * @param hts the hts to set
	 */
	public void setHts(String hts) {
		this.hts = hts;
	}

	/**
	 * @return the hps
	 */
	public String getHps() {
		return hps;
	}

	/**
	 * @param hps the hps to set
	 */
	public void setHps(String hps) {
		this.hps = hps;
	}

	/**
	 * @return the hfd
	 */
	public String getHfd() {
		return hfd;
	}

	/**
	 * @param hfd the hfd to set
	 */
	public void setHfd(String hfd) {
		this.hfd = hfd;
	}
	
	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the hei
	 */
	public String getHei() {
		return hei;
	}

	/**
	 * @param hei the hei to set
	 */
	public void setHei(String hei) {
		this.hei = hei;
	}	

	/**
	 * @return the hss
	 */
	public String getHss() {
		return hss;
	}

	/**
	 * @param hss the hss to set
	 */
	public void setHss(String hss) {
		this.hss = hss;
	}

	/**
	 * @return the has
	 */
	public String getHas() {
		return has;
	}

	/**
	 * @param has the has to set
	 */
	public void setHas(String has) {
		this.has = has;
	}
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId 			= rs.getString("PLAN_ID");
		carreraId 		= rs.getString("CARRERA_ID");
		nombre	 		= rs.getString("NOMBRE");
		versionId		= rs.getString("VERSION_ID");
		versionNombre	= rs.getString("VERSION_NOMBRE");
		estado			= rs.getString("ESTADO");
		tipo			= rs.getString("TIPO");
		hts				= rs.getString("HTS");
		hps				= rs.getString("HPS");
		hfd				= rs.getString("HFD");
		hei				= rs.getString("HEI");
		idioma			= rs.getString("IDIOMA");
		hss				= rs.getString("HSS");
		has				= rs.getString("HAS");
		year			= rs.getString("YEAR");
	}
	
	public void mapeaRegId( Connection conn, String planId, String versionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," +
								" VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR"+
								" FROM ENOC.MAPA_NUEVO_PLAN" + 
								" WHERE PLAN_ID = ?" +
								" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}