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
public class CertAlum {
	private String codigoPersonal;
	private String planId;
	private String avance;
	private String numCursos;
	private String fecha;
	private String equivalencia;	
	private String estado;
	private String encabezado;
	private String linea;
	
	public CertAlum(){
		codigoPersonal	= "";
		planId			= "";
		avance			= "";
		numCursos		= "";
		fecha			= "";
		equivalencia	= "";		
		estado			= "";
		encabezado 		= "";
		linea	 		= "";
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
	 * @return the avance
	 */
	public String getAvance() {
		return avance;
	}

	/**
	 * @param avance the avance to set
	 */
	public void setAvance(String avance) {
		this.avance = avance;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the equivalencia
	 */
	public String getEquivalencia() {
		return equivalencia;
	}

	/**
	 * @param equivalencia the equivalencia1 to set
	 */
	public void setEquivalencia(String equivalencia) {
		this.equivalencia = equivalencia;
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
	 * @return the encabezado
	 */
	public String getEncabezado() {
		return encabezado;
	}

	/**
	 * @param encabezado the encabezado to set
	 */
	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		planId			= rs.getString("PLAN_ID");
		avance			= rs.getString("AVANCE");
		numCursos		= rs.getString("NUM_CURSOS");
		fecha			= rs.getString("FECHA");
		equivalencia	= rs.getString("EQUIVALENCIA");		
		estado			= rs.getString("ESTADO");
		encabezado		= rs.getString("ENCABEZADO");
		linea			= rs.getString("LINEA");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, PLAN_ID, AVANCE," +
					" NUM_CURSOS, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, EQUIVALENCIA, ESTADO, ENCABEZADO, LINEA " +
					" FROM ENOC.CERT_ALUM" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}		
		
	}
}