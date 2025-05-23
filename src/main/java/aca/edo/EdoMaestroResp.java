package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdoMaestroResp {

	private String edoId;
	private String preguntaId;
	private String codigoPersonal;
	private String maestro;
	private String respuesta;
	
	public EdoMaestroResp(){
		edoId 			= "";
		preguntaId 		= "";
		codigoPersonal 	= "";
		maestro 		= "";
		respuesta 		= "";
	}
	
	public String getEdoId() {
		return edoId;
	}

	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edoId			= rs.getString("EDO_ID");
		preguntaId		= rs.getString("PREGUNTA_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		maestro			= rs.getString("MAESTRO");
		respuesta		= rs.getString("RESPUESTA");
	}
	
	public void mapeaRegId(Connection con, String edoId, String preguntaId, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT * " +
					" FROM ENOC.EDO_MAESTRORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoMaestroRespUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}
