package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecFija {
	private String idEjercicio;
	private String idCcosto;
	private String fecha;
	private String usuario;
	
	public BecFija(){
		setIdEjercicio("");
		setIdCcosto("");
		setFecha("");
		setUsuario("");
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
		idEjercicio		= rs.getString("ID_EJERCICIO");
		idCcosto		= rs.getString("ID_CCOSTO");
		fecha			= rs.getString("FECHA");
		usuario			= rs.getString("USUARIO");
	}
	
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFija|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}
