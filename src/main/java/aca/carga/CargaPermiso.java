// Bean del Catalogo de Materias de los grupos
package  aca.carga;

import java.sql.*;

public class CargaPermiso{
	private String cargaId;
	private String carreraId;
	private String recuperacion;
	private String usuario;
	
	public CargaPermiso(){
		cargaId			= "0";
		carreraId		= "0";
		recuperacion	= "N";
		usuario 		= "0";
	}	
	
	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getRecuperacion() {
		return recuperacion;
	}

	public void setRecuperacion(String recuperacion) {
		this.recuperacion = recuperacion;
	}	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId 		= rs.getString("CARGA_ID");
		carreraId 		= rs.getString("CARRERA_ID");
		recuperacion	= rs.getString("RECUPERACION");
		usuario			= rs.getString("USUARIO");
	}
	
	public void mapeaRegId( Connection conn, String cargaId, String carreraId ) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO "+
				"FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?");
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPermiso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}