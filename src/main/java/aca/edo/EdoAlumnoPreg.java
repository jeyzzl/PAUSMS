/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class EdoAlumnoPreg {
	private String preguntaId;
	private String edoId;
	private String pregunta;
	private String tipo;	
	private String orden;
	private String areaId;
	private String seccion;
	private String seccionNombre;
	
	public EdoAlumnoPreg(){
		preguntaId		= "";
		edoId			= "";
		pregunta		= "";
		tipo			= "";
		orden			= "";
		areaId			= "";
		seccion 		= "A";
		seccionNombre	= "";
	}

	public String getSeccionNombre() {
		return seccionNombre;
	}

	public void setSeccionNombre(String seccionNombre) {
		this.seccionNombre = seccionNombre;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	/**
	 * @return the preguntaId
	 */
	public String getPreguntaId() {
		return preguntaId;
	}

	/**
	 * @param preguntaId the preguntaId to set
	 */
	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	/**
	 * @return the edoId
	 */
	public String getEdoId() {
		return edoId;
	}

	/**
	 * @param edoId the edoId to set
	 */
	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
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
	
	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

		
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		preguntaId	= rs.getString("PREGUNTA_ID");
		edoId		= rs.getString("EDO_ID");
		pregunta	= rs.getString("PREGUNTA");
		tipo		= rs.getString("TIPO");
		orden		= rs.getString("ORDEN");
		areaId		= rs.getString("AREA_ID");
		seccion		= rs.getString("SECCION");
	}
	
	public void mapeaRegId(Connection con, String preguntaId, String edoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID" +
					" FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}

