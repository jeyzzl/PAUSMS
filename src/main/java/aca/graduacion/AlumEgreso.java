 // Bean de datos personales del alumno 
 package  aca.graduacion;

 import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 public class AlumEgreso{
	 
 	private String codigoPersonal;
 	private String planId;
 	private String eventoId;
 	private String promedio;
 	private String fecha;
 	private String titulado;
 	private String fIngreso;
 	private String fEgreso;
 	
 	public AlumEgreso(){
 		codigoPersonal		= "";
 		planId				= "";
 		eventoId			= "";
 		promedio			= "";
 		fecha				= "";
 		titulado			= "";
 		fIngreso			= "";
 		fEgreso				= "";
 	}
 	
 	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFEgreso() {
		return fEgreso;
	}
	
	public void setFEgreso(String egreso) {
		fEgreso = egreso;
	}

	public String getFIngreso() {
		return fIngreso;
	}

	public void setFIngreso(String ingreso) {
		fIngreso = ingreso;
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
	
	public String getTitulado() {
		return titulado;
	}
	
	public void setTitulado(String titulado) {
		this.titulado = titulado;
	}	
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		planId 				= rs.getString("PLAN_ID");
 		eventoId		 	= rs.getString("EVENTO_ID");
 		promedio			= rs.getString("PROMEDIO");
 		fecha 				= rs.getString("FECHA");
 		titulado			= rs.getString("TITULADO");
 		fIngreso 			= rs.getString("F_INGRESO");
 		fEgreso 			= rs.getString("F_EGRESO");
 	}
 	
 	public void mapeaRegId( Connection conn, String Codigo_Personal, String Plan_Id ) throws SQLException, IOException{
 		
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, PLAN_ID, EVENTO_ID, PROMEDIO, FECHA,"+
	 			" TITULADO, F_INGRESO, F_EGRESO"+
	 			" FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"); 
	 		ps.setString(1, Codigo_Personal);
			ps.setString(2, Plan_Id);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEgreso|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
 		
 	}
 	
 }