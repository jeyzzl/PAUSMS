package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumConstancia {
	private String constanciaId;
	private String constanciaNombre;
	private String constancia;
	private String usuarios;	
	private String tipo;	
	
	public AlumConstancia(){
		constanciaId		= "";
		constanciaNombre	= "";
		constancia			= "";
		usuarios			= "";
		tipo    			= "";
	}

	public String getConstanciaId() {
		return constanciaId;
	}

	public void setConstanciaId(String constanciaId) {
		this.constanciaId = constanciaId;
	}

	public String getConstanciaNombre() {
		return constanciaNombre;
	}

	public void setConstanciaNombre(String constanciaNombre) {
		this.constanciaNombre = constanciaNombre;
	}

	public String getConstancia() {
		return constancia;
	}

	public void setConstancia(String constancia) {
		this.constancia = constancia;
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		constanciaId		= rs.getString("CONSTANCIA_ID");
		constanciaNombre	= rs.getString("CONSTANCIA_NOMBRE");
		constancia			= rs.getString("CONSTANCIA");
		usuarios 			= rs.getString("USUARIOS");		
		tipo 	 			= rs.getString("TIPO");		
	}
	
	public void mapeaRegId( Connection conn, String constanciaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_CONSTANCIA "+ 
				"WHERE CONSTANCIA_ID = ?");
			ps.setString(1, constanciaId);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}
