package aca.leg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LegPermisos {

	public String codigo;
	public String usuarioAlta;
	public String usuarioBaja;
	public String fechaIni;
	public String fechaLim;
	public String status;
	public String folio;
	
	public LegPermisos(){

	codigo			= "";
	usuarioAlta		= "";
	usuarioBaja		= "";
	fechaIni		= "";
	fechaLim		= "";
	status			= "";
	folio 			= "";
	
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaLim() {
		return fechaLim;
	}

	public void setFechaLim(String fechaLim) {
		this.fechaLim = fechaLim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigo			= rs.getString("CODIGO");
		usuarioAlta		= rs.getString("USUARIO_ALTA");
		usuarioBaja		= rs.getString("USUARIO_BAJA");
		fechaIni		= rs.getString("FECHA_INI");
		fechaLim		= rs.getString("FECHA_LIM");
		status			= rs.getString("STATUS");
		folio			= rs.getString("FOLIO");
	}
	
	public void mapeaRegId(Connection conn, String codigo, String folio) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, FOLIO," +
					" USUARIO_ALTA," +
					" USUARIO_BAJA," +
					" TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI," +
					" TO_CHAR(FECHA_LIM,'DD/MM/YYYY') AS FECHA_LIM," +
					" STATUS" +
					" FROM ENOC.LEG_PERMISOS " + 
					" WHERE CODIGO = ?" +
					" AND FOLIO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, folio);
			rs=ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegPermisos|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	
	}

	
	
	
}		