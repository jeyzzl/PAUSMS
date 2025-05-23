package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdoPar {
	private String codigoPersonal;
	private String edoId;
	private String maestros;
	private String usuario;
	private String fecha;
	
	public EdoPar(){
		codigoPersonal	= "";
		edoId		= "";
		maestros	= "";
		usuario		= "";
		fecha		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getEdoId() {
		return edoId;
	}

	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	public String getMaestros() {
		return maestros;
	}

	public void setMaestros(String maestros) {
		this.maestros = maestros;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edoId			= rs.getString("EDO_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		edoId			= rs.getString("EDO_ID");
		maestros		= rs.getString("MAESTROS");
		usuario			= rs.getString("USUARIO");
		fecha			= rs.getString("FECHA");
		
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String edoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT MAESTROS, USUARIO, FECHA" +
					" FROM ENOC.EDO_PAR" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoParUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}
