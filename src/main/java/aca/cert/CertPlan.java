/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class CertPlan {
	private String planId;
	private String facultad;
	private String carrera;
	private String numCursos;
	private String semanas;
	private String tInicial;
	private String tFinal;
	private String nota;
	private String pie;
	private String clave;
	private String fst;
	private String fsp;
	private String componente;
	private String curso;
	private String rvoe;
	private String fechaRetro;
	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String creditos;
	
	public CertPlan(){
		planId		= "";
		facultad	= "";
		carrera		= "";
		numCursos	= "";
		semanas		= "";
		tInicial	= "";
		tFinal		= "";
		nota		= "";
		pie			= "";
		clave		= "";
		fst			= "";
		fsp			= "";
		componente	= "";
		curso		= "";
		rvoe		= "";
		fechaRetro	= "";
		titulo1		= "";
		titulo2		= "";
		titulo3		= "";
		creditos	= "";
	}

	public String getRvoe() {
		return rvoe;
	}

	public void setRvoe(String rvoe) {
		this.rvoe = rvoe;
	}

	/**
	 * @return the curso
	 */
	public String getCurso() {
		return curso;
	}

	/**
	 * @param curso the curso to set
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
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
	 * @return the componente
	 */
	public String getComponente() {
		return componente;
	}

	/**
	 * @param componente the componente to set
	 */
	public void setComponente(String componente) {
		this.componente = componente;
	}

	/**
	 * @return the facultad
	 */
	public String getFacultad() {
		return facultad;
	}

	/**
	 * @param facultad the facultad to set
	 */
	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	/**
	 * @return the fsp
	 */
	public String getFsp() {
		return fsp;
	}

	/**
	 * @param fsp the fsp to set
	 */
	public void setFsp(String fsp) {
		this.fsp = fsp;
	}

	/**
	 * @return the fst
	 */
	public String getFst() {
		return fst;
	}

	/**
	 * @param fst the fst to set
	 */
	public void setFst(String fst) {
		this.fst = fst;
	}

	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}

	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}

	/**
	 * @return the numCursos
	 */
	public String getNumCursos() {
		return numCursos;
	}

	/**
	 * @param numCursos the numCursos to set
	 */
	public void setNumCursos(String numCursos) {
		this.numCursos = numCursos;
	}

	/**
	 * @return the pie
	 */
	public String getPie() {
		return pie;
	}

	/**
	 * @param pie the pie to set
	 */
	public void setPie(String pie) {
		this.pie = pie;
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
	 * @return the semanas
	 */
	public String getSemanas() {
		return semanas;
	}

	/**
	 * @param semanas the semanas to set
	 */
	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}

	/**
	 * @return the tFinal
	 */
	public String getTFinal() {
		return tFinal;
	}

	/**
	 * @param final1 the tFinal to set
	 */
	public void setTFinal(String final1) {
		tFinal = final1;
	}

	/**
	 * @return the tInicial
	 */
	public String getTInicial() {
		return tInicial;
	}

	/**
	 * @param inicial the tInicial to set
	 */
	public void setTInicial(String inicial) {
		tInicial = inicial;
	}	
	
	/**
	 * @return the fechaRetro
	 */
	public String getFechaRetro() {
		return fechaRetro;
	}

	/**
	 * @param fechaRetro the fechaRetro to set
	 */
	public void setFechaRetro(String fechaRetro) {
		this.fechaRetro = fechaRetro;
	}
	
	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		planId		= rs.getString("PLAN_ID");
		facultad	= rs.getString("FACULTAD")==null?"":rs.getString("FACULTAD");
		carrera		= rs.getString("CARRERA")==null?"":rs.getString("CARRERA");
		numCursos	= rs.getString("NUM_CURSOS")==null?"":rs.getString("NUM_CURSOS");
		semanas		= rs.getString("SEMANAS")==null?"":rs.getString("SEMANAS");
		tInicial	= rs.getString("T_INICIAL")==null?"":rs.getString("T_INICIAL");
		tFinal		= rs.getString("T_FINAL")==null?"":rs.getString("T_FINAL");
		nota		= rs.getString("NOTA")==null?"":rs.getString("NOTA");
		pie			= rs.getString("PIE")==null?"":rs.getString("PIE");
		clave		= rs.getString("CLAVE")==null?"":rs.getString("CLAVE");
		fst			= rs.getString("FST")==null?"":rs.getString("FST");
		fsp			= rs.getString("FSP")==null?"":rs.getString("FSP");
		componente	= rs.getString("COMPONENTE")==null?"":rs.getString("COMPONENTE");
		curso		= rs.getString("CURSO")==null?"":rs.getString("CURSO");
		rvoe		= rs.getString("RVOE")==null?"":rs.getString("RVOE");
		fechaRetro	= rs.getString("FECHARETRO")==null?"":rs.getString("FECHARETRO");
		titulo1		= rs.getString("TITULO1")==null?"":rs.getString("TITULO1");
		titulo2		= rs.getString("TITULO2")==null?"":rs.getString("TITULO2");
		titulo3		= rs.getString("TITULO3")==null?"":rs.getString("TITULO3");
		creditos 	= rs.getString("CREDITOS")==null?"":rs.getString("CREDITOS");
	}
	
	public void mapeaRegId(Connection conn, String planId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
				" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
				" PIE, CLAVE, FST, FSP," +
				" COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS" +
				" FROM ENOC.CERT_PLAN" + 
				" WHERE PLAN_ID = ?");
		
			ps.setString(1, planId);
		
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlan|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
	}
	
}