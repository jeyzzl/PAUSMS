package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumIngreso {

	private String codigoPersonal;
	private String planId;
	private String cargaId;
	private String carreraId;
	private String newUm;
	private String newPlan;
	
	public AlumIngreso(){
		codigoPersonal	= "";
		planId	        = "";
		cargaId			= "";
		carreraId    	= "";
		newUm	    	= "";
		newPlan		    = "";
		
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
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}


	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
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
	 * @return the newum
	 */
	public String getNewum() {
		return newUm;
	}


	/**
	 * @param newum the newum to set
	 */
	public void setNewum(String newum) {
		this.newUm = newum;
	}


	/**
	 * @return the newplan
	 */
	public String getNewplan() {
		return newPlan;
	}


	/**
	 * @param newplan the newplan to set
	 */
	public void setNewplan(String newplan) {
		this.newPlan = newplan;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		planId	 		= rs.getString("PLAN_ID");
		cargaId       	= rs.getString("CARGA_ID");
		carreraId     	= rs.getString("CARRERA_ID");
		newUm  			= rs.getString("NEWUM");
		newPlan	  		= rs.getString("NEWPLAN");	
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String planId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, "+
				" PLAN_ID, CARGA_ID, " +
				" CARRERA_ID, NEWUM, NEWPLAN, " +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumIngresoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}