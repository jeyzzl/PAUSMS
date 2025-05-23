package aca.alerta.spring;
import java.sql.*;

public class AlertaHistorial{
	
	private String periodoId		= "";	
	private String codigoPersonal	= "";	
	private String pregunta			= "";
	private String respuesta		= "-";
	private String comentario1		= "-";
	private String comentario2		= "-";	
	
	
	public AlertaHistorial(){
		
		periodoId			= "";		
		codigoPersonal		= "";
		pregunta			= "";
		respuesta			= "-";
		comentario1			= "-";
		comentario2			= "-";	
	}
	
	
	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}


	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
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


	/**
	 * @return the comentario1
	 */
	public String getComentario1() {
		return comentario1;
	}


	/**
	 * @param comentario1 the comentario1 to set
	 */
	public void setComentario1(String comentario1) {
		this.comentario1 = comentario1;
	}


	/**
	 * @return the comentario2
	 */
	public String getComentario2() {
		return comentario2;
	}


	/**
	 * @param comentario2 the comentario2 to set
	 */
	public void setComentario2(String comentario2) {
		this.comentario2 = comentario2;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId			= rs.getString("PERIODO_ID");		
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		pregunta			= rs.getString("PREGUNTA");
		respuesta			= rs.getString("RESPUESTA");
		comentario1			= rs.getString("COMENTARIO1");
		comentario2			= rs.getString("COMENTARIO2");		
	}
	
public void mapeaRegId( Connection conn, String periodoId, String codigoPersonal, String pregunta ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2"
					+ " FROM ENOC.ALERTA_HISTORIAL WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ? ");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, pregunta);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}