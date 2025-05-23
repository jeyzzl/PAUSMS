package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Manuel
 *
 */

public class InvMetodologia {
	private String proyectoId;
	private String humanos;
	private String diseno;
	private String muestra;
	private String recoleccion;
	private String confidencialidad;
	private String vinculacion;
	private String responsable;
	private String actividades;
	private String entregable;
	private String plan;
	private String organizacion;
	private String problema;
	private String objetivo;
	private String hipotesis;
	private String validez;
	
	public InvMetodologia(){
		proyectoId			= "";
		humanos				= "-";
		diseno				= "";
		muestra				= "";
		recoleccion			= "";
		confidencialidad	= "";
		vinculacion			= "-";
		responsable			= "-";
		actividades			= "-";
		entregable			= "-";
		plan				= "-";
		organizacion		= "-";
	}
	
	public String getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getHumanos() {
		return humanos;
	}
	public void setHumanos(String humanos) {
		this.humanos = humanos;
	}
	public String getDiseno() {
		return diseno;
	}
	public void setDiseno(String diseno) {
		this.diseno = diseno;
	}
	public String getMuestra() {
		return muestra;
	}
	public void setMuestra(String muestra) {
		this.muestra = muestra;
	}
	public String getRecoleccion() {
		return recoleccion;
	}
	public void setRecoleccion(String recoleccion) {
		this.recoleccion = recoleccion;
	}
	public String getConfidencialidad() {
		return confidencialidad;
	}
	public void setConfidencialidad(String confidencialidad) {
		this.confidencialidad = confidencialidad;
	}
	public String getVinculacion() {
		return vinculacion;
	}
	public void setVinculacion(String vinculacion) {
		this.vinculacion = vinculacion;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getActividades() {
		return actividades;
	}
	public void setActividades(String actividades) {
		this.actividades = actividades;
	}
	public String getEntregable() {
		return entregable;
	}
	public void setEntregable(String entregable) {
		this.entregable = entregable;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}		
	public String getProblema() {
		return problema;
	}
	public void setProblema(String problema) {
		this.problema = problema;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getHipotesis() {
		return hipotesis;
	}
	public void setHipotesis(String hipotesis) {
		this.hipotesis = hipotesis;
	}
	public String getValidez() {
		return validez;
	}
	public void setValidez(String validez) {
		this.validez = validez;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		proyectoId			= rs.getString("PROYECTO_ID");
		humanos				= rs.getString("HUMANOS");
		diseno				= rs.getString("DISENO");
		muestra				= rs.getString("MUESTRA");
		recoleccion			= rs.getString("RECOLECCION");
		confidencialidad	= rs.getString("CONFIDENCIALIDAD");
		vinculacion			= rs.getString("VINCULACION");
		responsable			= rs.getString("RESPONSABLE");
		actividades			= rs.getString("ACTIVIDADES");
		entregable			= rs.getString("ENTREGABLE");
		plan				= rs.getString("PLAN");
		organizacion		= rs.getString("ORGANIZACION");
		problema			= rs.getString("PROBLEMA");
		objetivo			= rs.getString("OBJETIVO");
		hipotesis			= rs.getString("HIPOTESIS");
		validez				= rs.getString("VALIDEZ");
	}
	
	public void mapeaRegId(Connection conn, String proyectoId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION,"
					+ " PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ "
					+ " FROM ENOC.INV_METODOLOGIA"
					+ " WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
	
}
