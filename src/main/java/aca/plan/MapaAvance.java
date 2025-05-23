package  aca.plan;

import java.sql.*;

public class MapaAvance{
	
	private String planId;
	private String ciclo;
	private String tipocursoId;
	private String creditos;
	
	public MapaAvance(){
		planId			= "";
		ciclo			= "";
		tipocursoId		= "";
		creditos		= "";
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getTipocursoId() {
		return tipocursoId;
	}

	public void setTipocursoId(String tipocursoId) {
		this.tipocursoId = tipocursoId;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId 			= rs.getString("PLAN_ID");
		ciclo 			= rs.getString("CICLO");
		tipocursoId 	= rs.getString("TIPOCURSO_ID");
		creditos 		= rs.getString("CREDITOS");
	}
	
	public void mapeaRegId( Connection conn, String planId, String ciclo, String tipocursoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO, TIPOCURSO_ID, CREDITOS FROM ENOC.MAPA_AVANCE"
					+ " WHERE PLAN_ID = '"+planId+"' AND CICLO = '"+ciclo+"' AND TIPOCURSO_ID = '"+tipocursoId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvanceUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}