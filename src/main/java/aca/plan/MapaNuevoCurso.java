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
public class MapaNuevoCurso {
	private String planId;
	private String cursoId;
	private String versionId;
	private String clave;
	private String nombre;
	private String ciclo;
	private String fCreada;
	private String codigoMaestro;
	private String ubicacion;
	private String seriacion;
	private String hst;
	private String hsp;
	private String ths;
	private String ht;
	private String creditos;
	private String descripcion;
	private String bibliografia;
	private String competencia;
	private String mediosRecursos;
	private String eeDiagnostica;
	private String eeFormativa;
	private String eeSumativa;
	private String escala;
	private String estado;
	private String hei;
	private String hfd;
	private String idioma;
	private String hss;
	private String has;
	private String proyecto;
	
	public MapaNuevoCurso(){
		planId			= "";
		cursoId			= "";
		versionId		= "";
		clave			= "";
		nombre			= "";
		ciclo			= "";
		fCreada			= "";
		codigoMaestro	= "";
		ubicacion		= "";
		seriacion		= "";
		hst				= "";
		hsp				= "";
		ths				= "";
		ht				= "";
		creditos		= "";
		descripcion		= "";
		bibliografia	= "";
		competencia		= "";
		mediosRecursos	= "";
		eeDiagnostica	= "";
		eeFormativa		= "";
		eeSumativa		= "";
		escala			= "";
		estado			= "";
		hei				= "";
		hfd				= "";
		idioma			= "";
		hss				= "";
		has				= "";
		proyecto		= "";
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
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
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
	 * @return the ciclo
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @param ciclo the ciclo to set
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	/**
	 * @return the fCreada
	 */
	public String getFCreada() {
		return fCreada;
	}

	/**
	 * @param creada the fCreada to set
	 */
	public void setFCreada(String creada) {
		fCreada = creada;
	}

	/**
	 * @return the codigoMaestro
	 */
	public String getCodigoMaestro() {
		return codigoMaestro;
	}

	/**
	 * @param codigoMaestro the codigoMaestro to set
	 */
	public void setCodigoMaestro(String codigoMaestro) {
		this.codigoMaestro = codigoMaestro;
	}

	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the seriacion
	 */
	public String getSeriacion() {
		return seriacion;
	}

	/**
	 * @param seriacion the seriacion to set
	 */
	public void setSeriacion(String seriacion) {
		this.seriacion = seriacion;
	}

	/**
	 * @return the hst
	 */
	public String getHst() {
		return hst;
	}

	/**
	 * @param hst the hst to set
	 */
	public void setHst(String hst) {
		this.hst = hst;
	}

	/**
	 * @return the hsp
	 */
	public String getHsp() {
		return hsp;
	}

	/**
	 * @param hsp the hsp to set
	 */
	public void setHsp(String hsp) {
		this.hsp = hsp;
	}

	/**
	 * @return the ths
	 */
	public String getThs() {
		return ths;
	}

	/**
	 * @param ths the ths to set
	 */
	public void setThs(String ths) {
		this.ths = ths;
	}

	/**
	 * @return the ht
	 */
	public String getHt() {
		return ht;
	}

	/**
	 * @param ht the ht to set
	 */
	public void setHt(String ht) {
		this.ht = ht;
	}

	/**
	 * @return the creditos
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the bibliografia
	 */
	public String getBibliografia() {
		return bibliografia;
	}

	/**
	 * @param bibliografia the bibliografia to set
	 */
	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}

	/**
	 * @return the competencia
	 */
	public String getCompetencia() {
		return competencia;
	}

	/**
	 * @param competencia the competencia to set
	 */
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	/**
	 * @return the madiosRecursos
	 */
	public String getMediosRecursos() {
		return mediosRecursos;
	}

	/**
	 * @param madiosRecursos the madiosRecursos to set
	 */
	public void setMediosRecursos(String mediosRecursos) {
		this.mediosRecursos = mediosRecursos;
	}

	/**
	 * @return the eeDiagnostica
	 */
	public String getEeDiagnostica() {
		return eeDiagnostica;
	}

	/**
	 * @param eeDiagnostica the eeDiagnostica to set
	 */
	public void setEeDiagnostica(String eeDiagnostica) {
		this.eeDiagnostica = eeDiagnostica;
	}

	/**
	 * @return the eeFormativa
	 */
	public String getEeFormativa() {
		return eeFormativa;
	}

	/**
	 * @param eeFormativa the eeFormativa to set
	 */
	public void setEeFormativa(String eeFormativa) {
		this.eeFormativa = eeFormativa;
	}

	/**
	 * @return the eeSumativa
	 */
	public String getEeSumativa() {
		return eeSumativa;
	}

	/**
	 * @param eeSumativa the eeSumativa to set
	 */
	public void setEeSumativa(String eeSumativa) {
		this.eeSumativa = eeSumativa;
	}

	/**
	 * @return the escala
	 */
	public String getEscala() {
		return escala;
	}

	/**
	 * @param escala the escala to set
	 */
	public void setEscala(String escala) {
		this.escala = escala;
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
	 * @return the ehi
	 */
	public String getHei() {
		return hei;
	}

	/**
	 * @param ehi the ehi to set
	 */
	public void setHei(String hei) {
		this.hei = hei;
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
	
	public String getHss() {
		return hss;
	}

	public void setHss(String hss) {
		this.hss = hss;
	}

	public String getHas() {
		return has;
	}

	public void setHas(String has) {
		this.has = has;
	}
	
	public String getProyecto() {
		return proyecto;
	}
	
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId			= rs.getString("PLAN_ID")==null?"-":rs.getString("PLAN_ID");
		cursoId			= rs.getString("CURSO_ID")==null?"0":rs.getString("CURSO_ID");
		versionId		= rs.getString("VERSION_ID")==null?"0":rs.getString("VERSION_ID");
		clave			= rs.getString("CLAVE")==null?"-":rs.getString("CLAVE");
		nombre			= rs.getString("NOMBRE")==null?"-":rs.getString("NOMBRE");
		ciclo			= rs.getString("CICLO")==null?"0":rs.getString("CICLO");
		fCreada			= rs.getString("F_CREADA")==null?"00/00/0000":rs.getString("F_CREADA");
		codigoMaestro	= rs.getString("CODIGO_MAESTRO")==null?"-":rs.getString("CODIGO_MAESTRO");
		ubicacion		= rs.getString("UBICACION")==null?"-":rs.getString("UBICACION");
		seriacion		= rs.getString("SERIACION")==null?"-":rs.getString("SERIACION");
		hst				= rs.getString("HST")==null?"0":rs.getString("HST");
		hsp				= rs.getString("HSP")==null?"0":rs.getString("HSP");
		ths				= rs.getString("THS")==null?"0":rs.getString("THS");
		ht				= rs.getString("HT")==null?"0":rs.getString("HT");
		creditos		= rs.getString("CREDITOS")==null?"0":rs.getString("CREDITOS");
		descripcion		= rs.getString("DESCRIPCION")==null?"-":rs.getString("DESCRIPCION");
		bibliografia	= rs.getString("BIBLIOGRAFIA")==null?"-":rs.getString("BIBLIOGRAFIA");
		competencia		= rs.getString("COMPETENCIA")==null?"-":rs.getString("COMPETENCIA");
		mediosRecursos	= rs.getString("MEDIOS_RECURSOS")==null?"-":rs.getString("MEDIOS_RECURSOS");
		eeDiagnostica	= rs.getString("EE_DIAGNOSTICA")==null?"-":rs.getString("EE_DIAGNOSTICA");
		eeFormativa		= rs.getString("EE_FORMATIVA")==null?"-":rs.getString("EE_FORMATIVA");
		eeSumativa		= rs.getString("EE_SUMATIVA")==null?"-":rs.getString("EE_SUMATIVA");
		escala			= rs.getString("ESCALA")==null?"-":rs.getString("ESCALA");
		estado			= rs.getString("ESTADO")==null?"0":rs.getString("ESTADO");
		hei				= rs.getString("HEI")==null?"0":rs.getString("HEI");
		hfd				= rs.getString("HFD")==null?"0":rs.getString("HFD");
		idioma			= rs.getString("IDIOMA")==null?"0":rs.getString("IDIOMA");
		hss				= rs.getString("HSS")==null?"0":rs.getString("HSS");
		has				= rs.getString("HAS")==null?"0":rs.getString("HAS");
		proyecto		= rs.getString("PROYECTO")==null?"-":rs.getString("PROYECTO");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String planId, String versionId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
					" NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO," +
					" UBICACION, SERIACION, HST, HSP," +
					" THS, HT, CREDITOS, DESCRIPCION," +
					" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
					" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
					" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
					" FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, planId);
			ps.setString(3, versionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

	public void mapeaRegIdPorCursoID( Connection conn, String cursoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
					" NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO," +
					" UBICACION, SERIACION, HST, HSP," +
					" THS, HT, CREDITOS, DESCRIPCION," +
					" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
					" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
					" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
					" FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?");
			
			ps.setString(1, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoCursoUtil|mapeaRegIdPorCursoID|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}