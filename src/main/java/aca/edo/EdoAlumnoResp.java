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
public class EdoAlumnoResp {
	private String edoId;
	private String preguntaId;
	private String codigoPersonal;
	private String cursoCargaId;
	private String codigoMaestro;
	private String respuesta;
	
	public EdoAlumnoResp(){
		edoId			= "";
		preguntaId		= "";
		codigoPersonal	= "";
		cursoCargaId	= "";
		codigoMaestro	= "";
		respuesta		= "";
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
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the codigoMaestro
	 */
	public String getCodigoMaestro() {
		return codigoMaestro;
	}

	/**
	 * @param codigoMaestro the codigoMaestro to set
	 */
	public void setCodigoMaestro(String codigoMaestro) {
		this.codigoMaestro = codigoMaestro;
	}

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edoId			= rs.getString("EDO_ID");
		preguntaId		= rs.getString("PREGUNTA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		codigoMaestro	= rs.getString("CODIGO_MAESTRO");
		respuesta		= rs.getString("RESPUESTA");
	}
	
	public void mapeaRegId(Connection con, String respuesta, String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA" +
					" FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?" +
					" AND CODIGO_MAESTRO = ?");
				
				ps.setString(1, respuesta);
				ps.setString(2, edoId);
				ps.setString(3, preguntaId);			
				ps.setString(4, codigoPersonal);
				ps.setString(5, cursoCargaId);
				ps.setString(6, codigoMaestro);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}