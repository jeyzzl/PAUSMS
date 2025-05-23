/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class MapaNuevoActividad {
	private String cursoId;
	private String unidadId;
	private String actividadId;
	private String objetivo;
	private String descripcion;
	private String criterio;
	private String tipo;
	
	public MapaNuevoActividad(){
		cursoId			= "";
		unidadId		= "";
		actividadId		= "";
		objetivo		= "";
		descripcion		= "";
		criterio		= "";
		tipo			= "";
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	/**
	 * @return the unidadId
	 */
	public String getUnidadId() {
		return unidadId;
	}

	/**
	 * @param unidadId the unidadId to set
	 */
	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	/**
	 * @return the actividadId
	 */
	public String getActividadId() {
		return actividadId;
	}

	/**
	 * @param actividadId the actividadId to set
	 */
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}

	/**
	 * @return the objetivo
	 */
	public String getObjetivo() {
		return objetivo;
	}

	/**
	 * @param objetivo the objetivo to set
	 */
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the criterio
	 */
	public String getCriterio() {
		return criterio;
	}

	/**
	 * @param criterio the criterio to set
	 */
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId			= rs.getString("CURSO_ID")==null?"0":rs.getString("CURSO_ID");
		unidadId		= rs.getString("UNIDAD_ID")==null?"0":rs.getString("UNIDAD_ID");
		actividadId		= rs.getString("ACTIVIDAD_ID")==null?"0":rs.getString("ACTIVIDAD_ID");
		objetivo		= rs.getString("OBJETIVO")==null?"-":rs.getString("OBJETIVO");
		descripcion		= rs.getString("DESCRIPCION")==null?"-":rs.getString("DESCRIPCION");
		criterio		= rs.getString("CRITERIO")==null?"-":rs.getString("CRITERIO");
		tipo			= rs.getString("TIPO")==null?"0":rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String unidadId, String actividadId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
					" DESCRIPCION, CRITERIO, TIPO" +
					" FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
				   	" AND ACTIVIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, cursoId);
			ps.setString(2, unidadId);
			ps.setString(3, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}