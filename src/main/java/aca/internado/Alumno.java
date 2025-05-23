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
public class Alumno {
	private String dormitorioId, cuartoId, codigoPersonal;
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getCuartoId() {
		return cuartoId;
	}
	public void setCuartoId(String cuartoId) {
		this.cuartoId = cuartoId;
	}
	public String getDormitorioId() {
		return dormitorioId;
	}
	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		dormitorioId	= rs.getString("DORMITORIO_ID");
		cuartoId		= rs.getString("CUARTO_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();		
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.AlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}