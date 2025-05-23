/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class EdoPeriodo {
	private String periodoId;
	private String periodoNombre;
	private String fInicio;
	private String fFinal;
	private String estado;
	
	public EdoPeriodo(){
		periodoId		= "";
		periodoNombre	= "";
		fInicio			= "";
		fFinal			= "";
		estado 			= "";
	}
	

	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}



	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}



	/**
	 * @return the periodoNombre
	 */
	public String getPeriodoNombre() {
		return periodoNombre;
	}



	/**
	 * @param periodoNombre the periodoNombre to set
	 */
	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}



	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}



	/**
	 * @param inicio the fInicio to set
	 */
	public void setFInicio(String inicio) {
		fInicio = inicio;
	}



	/**
	 * @return the fFinal
	 */
	public String getFFinal() {
		return fFinal;
	}



	/**
	 * @param final1 the fFinal to set
	 */
	public void setFFinal(String final1) {
		fFinal = final1;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId		= rs.getString("PERIODO_ID");
		periodoNombre	= rs.getString("PERIODO_NOMBRE");
		fInicio			= rs.getString("F_INICIO");
		fFinal			= rs.getString("F_FINAL");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String actividadId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT " +
					" PERIODO_ID, PERIODO_NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, " +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, " +
					" ESTADO " +
					" FROM ENOC.EDO_PERIODO" + 
					" WHERE PERIODO_ID = ?");
			
			ps.setString(1, actividadId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}