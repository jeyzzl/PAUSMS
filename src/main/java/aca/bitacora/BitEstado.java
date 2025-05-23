package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitEstado {
	
	private String estado;
	private String estadoNombre; 
	
	public BitEstado(){
		estado 			= "";
		estadoNombre 	= "";
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoNombre() {
		return estadoNombre;
	}

	public void setEstado_nombre(String estado_nombre) {
		this.estadoNombre = estado_nombre;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		estado			= rs.getString("ESTADO");
		estadoNombre	= rs.getString("ESTADO_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String estadoId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		
		try{
			ps = conn.prepareStatement(" SELECT ESTADO, ESTADO_NOMBRE "
									 + " FROM ENOC.BIT_ESTADO WHERE ESTADO = "+estadoId); 
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitEstadoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}

}
