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
public class CertCurso {
	 private String cursoId;
	 private String planId;
	 private String clave;
	 private String cicloId;
	 private String cursoNombre;
	 private String fst;
	 private String fsp;
	 private String creditos;
	 private String orden;
	 private String tipoCursoId;
	 private String creditos2;
	 
	 public CertCurso(){
		 cursoId		= "";
		 planId			= "";
		 clave			= "";
		 cicloId		= "";
		 cursoNombre	= "";
		 fst			= "";
		 fsp			= "";
		 creditos		= "";
		 orden			= "";
		 tipoCursoId	= "";
		 creditos2		= "0";
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
	 * @return the cursoNombre
	 */
	public String getCursoNombre() {
		return cursoNombre;
	}

	/**
	 * @param cursoNombre the cursoNombre to set
	 */
	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
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
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
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
	 * @return the tipoCursoId
	 */
	public String getTipoCursoId() {
		return tipoCursoId;
	}

	/**
	 * @param tipoCursoId the tipoCursoId to set
	 */
	public void setTipoCursoId(String tipoCursoId) {
		this.tipoCursoId = tipoCursoId;
	}
	

	public String getCreditos2() {
		return creditos2;
	}

	public void setCreditos2(String creditos2) {
		this.creditos2 = creditos2;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		cursoId			= rs.getString("CURSO_ID");
		planId			= rs.getString("PLAN_ID");
		clave			= rs.getString("CLAVE")==null?"":rs.getString("CLAVE");
		cicloId			= rs.getString("CICLO_ID");
		cursoNombre		= rs.getString("CURSO_NOMBRE")==null?"":rs.getString("CURSO_NOMBRE");
		fst				= rs.getString("FST")==null?"":rs.getString("FST");
		fsp				= rs.getString("FSP")==null?"":rs.getString("FSP");
		creditos		= rs.getString("CREDITOS");
		orden			= rs.getString("ORDEN");
		tipoCursoId		= rs.getString("TIPOCURSO_ID");
		creditos2		= rs.getString("CREDITOS2");
	}
	
	public void mapeaRegId(Connection conn, String cursoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, PLAN_ID, CLAVE, CICLO_ID," +
				" CURSO_NOMBRE, FST, FSP, CREDITOS, ORDEN, TIPOCURSO_ID, CREDITOS2" +
				" FROM ENOC.CERT_CURSO" + 
				" WHERE CURSO_ID = ?");
		
			ps.setString(1, cursoId);
		
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCurso|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
}