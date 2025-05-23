 // Bean de datos personales del alumno 
 package  aca.alumno;

 import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 public class AlumEgreso{
	 
	private String eventoId;
	private String codigoPersonal;
	private String carreraId;
 	private String planId;
 	private String promedio;
 	private String avance;
 	private String titulado;
 	private String fecha; 
 	private String usuario;
 	
 	public AlumEgreso(){
 		eventoId			= "";
 		codigoPersonal		= "";
 		carreraId			= "";
 		planId				= ""; 		
 		promedio			= "";
 		avance				= "";
 		titulado			= "";
 		fecha				= "";
 		usuario 			= "0"; 
 	}
 	
 	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
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

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}

	public String getTitulado() {
		return titulado;
	}

	public void setTitulado(String titulado) {
		this.titulado = titulado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		eventoId			= rs.getString("EVENTO_ID");
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		carreraId	 		= rs.getString("CARRERA_ID");
 		planId 				= rs.getString("PLAN_ID");
 		avance			 	= rs.getString("AVANCE");
 		titulado			= rs.getString("TITULADO");
 		promedio			= rs.getString("PROMEDIO");
 		fecha 				= rs.getString("FECHA");
 		usuario				= rs.getString("USUARIO");
 	}
 	
 	public void mapeaRegId( Connection conn, String eventoId, String codigoPersonal ) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE,"
	 				+ " TITULADO, PROMEDIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO"
	 				+ " FROM ENOC.ALUM_EGRESO"
	 				+ " WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?"); 
	 		ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEgresoUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 	}

 }