
package  aca.carga;
import java.sql.*;

public class CargaGrupoActividad{
	private String actividadId;
	private String cursoCargaId;
	private String evaluacionId;
	private String nombre;
	private String valor;	
	private String fecha;
	private String actividadE42;
	private String agendadaE42;
	private String enviar;
	
	public CargaGrupoActividad(){
		actividadId		= "";
		cursoCargaId		= "";
		evaluacionId		= "";
		nombre				= "";
		valor				= "";
		fecha				= "";
		actividadE42		= "";
		agendadaE42			= "N";
		enviar				= "N";
	}
	
	public String getActividadE42() {
		return actividadE42;
	}	

	public void setActividadE42(String actividadE42) {
		this.actividadE42 = actividadE42;
	}	

	public String getActividadId() {
		return actividadId;
	}
	
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}	

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}	

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	
	
	public String getFecha() {
		return fecha;
	}	
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	public String getAgendadaE42() {
		return agendadaE42;
	}

	public void setAgendadaE42(String agendadaE42) {
		this.agendadaE42 = agendadaE42;
	}
	
	public String getEnviar() {
		return enviar;
	}

	public void setEnviar(String enviar) {
		this.enviar = enviar;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		actividadId 		= rs.getString("ACTIVIDAD_ID");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		evaluacionId		= rs.getString("EVALUACION_ID");
		nombre	 			= rs.getString("NOMBRE");
		valor 				= rs.getString("VALOR");
		fecha				= rs.getString("FECHA");
		actividadE42		= rs.getString("ACTIVIDAD_E42");
		agendadaE42			= rs.getString("AGENDADA_E42");
		enviar				= rs.getString("ENVIAR");
	}
	
	public void mapeaRegId( Connection conn, String actividadId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+	 
				" WHERE ACTIVIDAD_ID = ?");
			ps.setLong(1, Long.parseLong(actividadId));
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}