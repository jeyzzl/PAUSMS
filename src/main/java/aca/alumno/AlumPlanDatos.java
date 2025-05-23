package aca.alumno;
import java.sql.*;

public class AlumPlanDatos{	
	private String codigoPersonal;
	private String planId;
	private String promedio;
	private String creditos;
	
	public AlumPlanDatos(){
		codigoPersonal	= "";
		planId			= "";
		promedio		= "";
		creditos		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 			= rs.getString("CODIGO_PERSONAL");
		planId 					= rs.getString("PLAN_ID");
		promedio				= rs.getString("PROMEDIO");
		creditos				= rs.getString("CREDITOS");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, PLAN_ID, PROMEDIO, CREDITOS"+
				" FROM ENOC.ALUM_PLAN_DATOS WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? "); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumPlanDatosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}