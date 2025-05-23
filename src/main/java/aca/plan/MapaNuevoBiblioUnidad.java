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
public class MapaNuevoBiblioUnidad {
	private String cursoId;
	private String unidadId;
	private String bibliografiaId;
	private String id;
	private String especificacion;
	
	public MapaNuevoBiblioUnidad(){
		cursoId			= "";
		unidadId		= "";
		bibliografiaId	= "";
		id				= "";
		especificacion	= "";
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
	 * @return the bibliografiaId
	 */
	public String getBibliografiaId() {
		return bibliografiaId;
	}

	/**
	 * @param bibliografiaId the bibliografiaId to set
	 */
	public void setBibliografiaId(String bibliografiaId) {
		this.bibliografiaId = bibliografiaId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the especificacion
	 */
	public String getEspecificacion() {
		return especificacion;
	}

	/**
	 * @param especificacion the especificacion to set
	 */
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId				= rs.getString("CURSO_ID")==null?"0":rs.getString("CURSO_ID");
		unidadId			= rs.getString("UNIDAD_ID")==null?"0":rs.getString("UNIDAD_ID");
		bibliografiaId		= rs.getString("BIBLIOGRAFIA_ID")==null?"0":rs.getString("BIBLIOGRAFIA_ID");
		id					= rs.getString("ID")==null?"0":rs.getString("ID");
		especificacion		= rs.getString("ESPECIFICACION")==null?"-":rs.getString("ESPECIFICACION");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String unidadId, String bibliografiaId, String id) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;	
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
					" ESPECIFICACION" +
					" FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')");
				
				ps.setString(1,  cursoId);
				ps.setString(2,  unidadId);
				ps.setString(3,  bibliografiaId);
				ps.setString(4,  id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}