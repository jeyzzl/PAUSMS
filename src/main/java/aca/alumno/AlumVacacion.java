//Bean del periodo de vacaciones del alumno
package aca.alumno;
import java.sql.*;

public class AlumVacacion {
	private String nivelId;
	private String modalidadId;
	private String fExamen;
	private String fInicio;
	private String fFinal;
	
	public AlumVacacion(){
		nivelId		= "";
		modalidadId = "";
		fExamen		= "";
		fInicio		= "";
		fFinal		= "";		
	}

	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	public String getFExamen() {
		return fExamen;
	}

	public void setFExamen(String examen) {
		fExamen = examen;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getFFinal() {
		return fFinal;
	}

	public void setFFinal(String final1) {
		fFinal = final1;
	}	
	
	/**
	 * @return the modalidadId
	 */
	public String getModalidadId() {
		return modalidadId;
	}

	/**
	 * @param modalidadId the modalidadId to set
	 */
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		nivelId 		= rs.getString("NIVEL_ID");
		modalidadId 	= rs.getString("MODALIDAD_ID");
		fExamen 		= rs.getString("F_EXAMEN");
		fInicio 		= rs.getString("F_INICIO");
		fFinal			= rs.getString("F_FINAL");				
	}
	
	public void mapeaRegId( Connection conn, String nivelId, String modalidadId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"NIVEL_ID, MODALIDAD_ID, "+
				"TO_CHAR(F_EXAMEN,'DD/MM/YYYY') AS F_EXAMEN, " +
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL " +
				"FROM ENOC.ALUM_VACACION " + 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
				"AND MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumVacacionLista|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}