// Bean del Catalogo Cursos
package  aca.plan;
import java.sql.*;

public class MapaCredito{
	
	private String planId;
	private String ciclo;
	private String creditos;
	private String optativos;
	private String titulo;
	
	public MapaCredito(){
		planId			= "";
		ciclo			= "";
		creditos		= "0";
		optativos		= "0";
		titulo			= "";
	}
	
	/**
	 * @return Returns the ciclo.
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @param ciclo The ciclo to set.
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	/**
	 * @return Returns the creditos.
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos The creditos to set.
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return Returns the planId.
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @param planId The planId to set.
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	/**
	 * @return Returns the optativos.
	 */
	public String getOptativos() {
		return optativos;
	}
	
	/**
	 * @param optativos The optativos to set.
	 */	
	public void setOptativos(String optativos) {
		this.optativos = optativos;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		planId 		= rs.getString("PLAN_ID");
		ciclo 		= rs.getString("CICLO");
		creditos 	= rs.getString("CREDITOS");
		optativos	= rs.getString("OPTATIVOS");
		titulo		= rs.getString("TITULO");
	}
	
	public void mapeaRegId( Connection conn, String planId, String ciclo) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO, "+
					"TO_CHAR(COALESCE(CREDITOS,0),'99.99') AS CREDITOS, TO_CHAR(COALESCE(OPTATIVOS,0),'99.99') AS OPTATIVOS, TITULO "+													
					"FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO =  TO_NUMBER(?,'99')"); 
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}	
	
}