package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 */
public class Dormitorio {
	private String dormitorioId, nombre, preceptor,sexo;
	
	public String getDormitorioId() {
		return dormitorioId;
	}
	public void setDormitorioId(String dormitorioId) {
		this.dormitorioId = dormitorioId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPreceptor() {
		return preceptor;
	}
	public void setPreceptor(String preceptor) {
		this.preceptor = preceptor;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		dormitorioId	= rs.getString("DORMITORIO_ID");
		nombre			= rs.getString("NOMBRE");
		preceptor		= rs.getString("PRECEPTOR");
		sexo			= rs.getString("SEXO");
	}

	public void mapeaRegId(Connection con, String dormitorioId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,dormitorioId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}