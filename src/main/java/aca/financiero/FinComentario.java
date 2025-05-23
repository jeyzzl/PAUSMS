package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinComentario {
	private String codigoPersonal;
	private String folio;
	private String comentario;
	private String fecha;
	private String usuario;	
	private String tipo;
	
	public FinComentario(){
		codigoPersonal	= "0000000";
		folio			= "0";
		comentario		= "-";
		fecha			= "01/01/1950";
		usuario			= "0000000";
		tipo			= "X";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		folio				= rs.getString("FOLIO");
		comentario			= rs.getString("COMENTARIO");
		fecha				= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		tipo				= rs.getString("TIPO");
	}
	
	public void mapeaRegId(Connection con, String Id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, COMENTARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, TIPO"
					+ " FROM ENOC.FIN_COMENTARIO"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinComentario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	

}