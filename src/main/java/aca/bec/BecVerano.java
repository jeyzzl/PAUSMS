package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecVerano {
	private String codigoPersonal;
	private String departamento;
	private String puesto;
	private String telefono;
	private String correo;
	private String importe;
	
	public BecVerano(){
		codigoPersonal	= "";
		departamento	= "";
		puesto			= "";
		telefono		= "";
		correo			= "";
		importe			= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		departamento		= rs.getString("DEPARTAMENTO");
		puesto				= rs.getString("PUESTO");
		telefono			= rs.getString("TELEFONO");
		correo				= rs.getString("CORREO");
		importe				= rs.getString("IMPORTE");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, DEPARTAMENTO, PUESTO, TELEFONO, CORREO, IMPORTE"
				+ " FROM ENOC.BEC_VERANO WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1,  codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecVerano|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}