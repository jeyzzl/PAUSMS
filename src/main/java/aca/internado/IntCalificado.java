package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntCalificado {
	
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String numComidas;
	private String tipoComida;
	private String lugarId;
	private String comentario;

	
	public String getBloqueId() {
		return bloqueId;
	}
	

	public void setBloqueId(String bloqueId) {
		this.bloqueId = bloqueId;
	}
	

	public String getCargaId() {
		return cargaId;
	}
	

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	

	public String getComentario() {
		return comentario;
	}
	

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	

	public String getLugarId() {
		return lugarId;
	}
	

	public void setLugarId(String lugarId) {
		this.lugarId = lugarId;
	}
	

	public String getNumComidas() {
		return numComidas;
	}
	

	public void setNumComidas(String numComidas) {
		this.numComidas = numComidas;
	}
	

	public String getTipoComida() {
		return tipoComida;
	}
	

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		cargaId 		= rs.getString("CARGA_ID");
		bloqueId 		= rs.getString("BLOQUE_ID");
		numComidas 		= rs.getString("NUM_COMIDAS");
		tipoComida 		= rs.getString("TIPO_COMIDA");
		lugarId 		= rs.getString("LUGAR_ID");
		comentario 		= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection con, String CodigoPersonal, String CargaId, String BloqueId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_CALIFICADO " + 
														" WHERE CODIGO_PERSONAL = ?" +
														" AND CARGA_ID = ?" +
														" AND BLOQUE_ID = ?");
			 
			ps.setString(1,CodigoPersonal);
			ps.setString(2,CargaId);
			ps.setInt(3,Integer.parseInt(BloqueId));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntCalificadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}