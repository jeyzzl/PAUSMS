package aca.bec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class BecAcceso {
	private String codigoPersonal;
	private String idEjercicio;
	private String idCcosto;
	private String fecha;
	private String usuario;
	
	public BecAcceso(){
		codigoPersonal	= "";
		idEjercicio		= "";
		idCcosto		= "";
		fecha			= "";
		usuario			= "";
	}	
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getIdEjercicio() {
		return idEjercicio;
	}


	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}


	public String getIdCcosto() {
		return idCcosto;
	}


	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
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
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		idEjercicio		= rs.getString("ID_EJERCICIO");
		idCcosto		= rs.getString("ID_CCOSTO");
		fecha			= rs.getString("FECHA");
		usuario			= rs.getString("USUARIO");
	}
	
	public void mapeaRegId(Connection conn, String idEjercicio, String idCcosto, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO " +
					" FROM ENOC.BEC_ACCESO WHERE CODIGO_PERSONAL = ? AND ID_EJERCICIO = ? AND ID_CCOSTO = ? "); 
			
			ps.setString(1,  codigoPersonal);
			ps.setString(2,  idEjercicio);
			ps.setString(3,  idCcosto);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcceso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}