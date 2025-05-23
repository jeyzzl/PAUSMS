package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 *
 */
public class Acceso {
	private String codigoPersonal;
	private String dormitorioId;
	private String pasillo;
	private String rol;

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getDormitorioId() {
		return dormitorioId;
	}
	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}
	public String getPasillo() {
		return pasillo;
	}
	public void setPasillo(String pasillo) {
		this.pasillo = pasillo;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		dormitorioId	= rs.getString("DORMITORIO_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		rol				= rs.getString("ROL");
		pasillo			= rs.getString("PASILLO");
	}
	
	public void mapeaRegId(Connection con, String dormitorioId, String codigoPersonal, String rol) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ? AND ROL = ?"); 
			ps.setString(1,dormitorioId);
			ps.setString(2,codigoPersonal);
			ps.setString(3,rol);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AccesoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}