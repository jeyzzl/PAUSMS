/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Cuarto {
	
	private String dormitorioId, cuartoId, pasillo, cupo, estado;
	
	public String getCuartoId() {
		return cuartoId;
	}
	public void setCuartoId(String cuartoId) {
		this.cuartoId = cuartoId;
	}
	public String getCupo() {
		return cupo;
	}
	public void setCupo(String cupo) {
		this.cupo = cupo;
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
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		dormitorioId	= rs.getString("DORMITORIO_ID");
		cuartoId		= rs.getString("CUARTO_ID");
		pasillo			= rs.getString("PASILLO");
		cupo			= rs.getString("CUPO");
		estado			= rs.getString("ESTADO");
	}

	public void mapeaRegId(Connection con, String dormitorioId, String cuartoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? "); 
			ps.setString(1,dormitorioId);
			ps.setString(2,cuartoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.CuartoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}