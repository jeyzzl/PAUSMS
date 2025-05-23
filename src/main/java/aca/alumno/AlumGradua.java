package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumGradua {
	private String codigoPersonal;
	private String planId;
	private String fecha;
	private String fechaGraduacion;
	private String evento;
	private String avance;
	private String matAc;
	private String matIns;
	private String matPen;
	private String diploma;
	
	public AlumGradua(){
		codigoPersonal	= "0";
		planId			= "0";
		fecha			= "";
		fechaGraduacion	= "";
		evento			= "0";
		avance			= "0";
		matAc 			= "0";
		matIns			= "0";
		matPen			= "0";
		diploma			= "-";
		
	}
	
	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
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
	 * @return the fechaGraduacion
	 */
	public String getFechaGraduacion() {
		return fechaGraduacion;
	}

	/**
	 * @param fechaGraduacion the fechaGraduacion to set
	 */
	public void setFechaGraduacion(String fechaGraduacion) {
		this.fechaGraduacion = fechaGraduacion;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
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
	 * @return the matAc
	 */
	public String getMatAc() {
		return matAc;
	}

	/**
	 * @param matAc the matAc to set
	 */
	public void setMatAc(String matAc) {
		this.matAc = matAc;
	}

	/**
	 * @return the matIns
	 */
	public String getMatIns() {
		return matIns;
	}

	/**
	 * @param matIns the matIns to set
	 */
	public void setMatIns(String matIns) {
		this.matIns = matIns;
	}

	/**
	 * @return the matPen
	 */
	public String getMatPen() {
		return matPen;
	}

	/**
	 * @param matPen the matPen to set
	 */
	public void setMatPen(String matPen) {
		this.matPen = matPen;
	}

	/**
	 * @param "conn" the connection of DB and "cargaId" the period
	*/
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		planId      	= rs.getString("PLAN_ID");
		fecha	 		= rs.getString("FECHA");
		fechaGraduacion	= rs.getString("FECHA_GRADUACION");
		evento  		= rs.getString("EVENTO");
		avance 	    	= rs.getString("AVANCE");
		matAc 		    = rs.getString("MAT_AC");
		matIns 		    = rs.getString("MAT_INS");
		matPen 			= rs.getString("MAT_PEN");
		diploma 		= rs.getString("DIPLOMA");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String planId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, PLAN_ID,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TO_CHAR(FECHA_GRADUACION,'DD/MM/YYYY') AS FECHA_GRADUACION," +
				" EVENTO, AVANCE," +
				" MAT_AC, MAT_INS, MAT_PEN, DIPLOMA " +
				" FROM ENOC.ALUM_GRADUA "+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}