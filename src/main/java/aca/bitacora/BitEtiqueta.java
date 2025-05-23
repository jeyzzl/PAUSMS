package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitEtiqueta {

	private String folio;
	private String etiquetaId;
	private String areaId;
	private String comentario;
	private String fecha;
	private String usuario;
	private String turnado;
	
	public BitEtiqueta(){
		folio 		= "";
		etiquetaId 	= "";
		areaId		= "";
		comentario 	= "";
		fecha 		= "";
		usuario 	= "";
		turnado		= "-";
	}	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEtiquetaId() {
		return etiquetaId;
	}

	public void setEtiquetaId(String etiquetaId) {
		this.etiquetaId = etiquetaId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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
	
	public String getTurnado() {
		return turnado;
	}

	public void setTurnado(String turnado) {
		this.turnado = turnado;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		folio		= rs.getString("FOLIO");
		etiquetaId	= rs.getString("ETIQUETA_ID");
		areaId		= rs.getString("AREA_ID");
		comentario	= rs.getString("COMENTARIO");
		fecha		= rs.getString("FECHA");
		usuario		= rs.getString("USUARIO");
		turnado		= rs.getString("TURNADO");
	}
	
	public void mapeaRegId( Connection conn, String folio, String etiquetaId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT FOLIO, ETIQUETA_ID, AREA_ID, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, TURNADO "
									 + " FROM ENOC.BIT_ETIQUETA WHERE FOLIO = ? AND ETIQUETA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, folio);
			ps.setString(2, etiquetaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.EtiquetaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}
