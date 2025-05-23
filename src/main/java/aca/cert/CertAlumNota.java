/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jose Torres
 *
 */
public class CertAlumNota {
	private String codigoPersonal;
	private String folio;
	private String planId;
	private String cicloId;
	private String cursoId;
	private String curso;
	private String estado;
	private String nota;
	private String notaLetra;
	private String optativa;
	private String promedia;
	
	
	public CertAlumNota(){
		codigoPersonal	= "";
		folio			= "";
		planId			= "";
		cicloId			= "";
		cursoId			= "";
		curso			= "";
		estado			= "";
		nota			= "";
		notaLetra		= "";
		optativa		= "";
		promedia		= "";
	}



	/**
	 * @return the promedia
	 */
	public String getPromedia() {
		return promedia;
	}



	/**
	 * @param promedia the promedia to set
	 */
	public void setPromedia(String promedia) {
		this.promedia = promedia;
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * @return the cicloId
	 */
	public String getCicloId() {
		return cicloId;
	}

	/**
	 * @param cicloId the cicloId to set
	 */
	public void setCicloId(String cicloId) {
		this.cicloId = cicloId;
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
	 * @return the notaLetra
	 */
	public String getNotaLetra() {
		return notaLetra;
	}

	/**
	 * @param notaLetra the notaLetra to set
	 */
	public void setNotaLetra(String notaLetra) {
		this.notaLetra = notaLetra;
	}

	/**
	 * @return the optativa
	 */
	public String getOptativa() {
		return optativa;
	}

	/**
	 * @param optativa the optativa to set
	 */
	public void setOptativa(String optativa) {
		this.optativa = optativa;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		planId			= rs.getString("PLAN_ID");
		cicloId			= rs.getString("CICLO_ID");
		cursoId			= rs.getString("CURSO_ID");
		curso			= rs.getString("CURSO");
		estado			= rs.getString("ESTADO");
		nota			= rs.getString("NOTA");
		notaLetra		= rs.getString("NOTA_LETRA");
		optativa		= rs.getString("OPTATIVA");
		promedia		= rs.getString("PROMEDIA");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String cursoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID," +
					" CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA, PROMEDIA" +
					" FROM ENOC.CERT_ALUM_NOTA" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|mapeaRegId|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		
	}
	
}