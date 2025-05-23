package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntLugarComida {
	
	private String lugarId;
	private String nombreLugar;

	
	public IntLugarComida() {
		super();
		// TODO Auto-generated constructor stub
		lugarId = "";
		nombreLugar = "";
	}

	public String getLugarId() {
		return lugarId;
	}
	
	public void setLugarId(String lugarId) {
		this.lugarId = lugarId;
	}
	
	public String getNombreLugar() {
		return nombreLugar;
	}
	
	public void setNombreLugar(String nombreLugar) {
		this.nombreLugar = nombreLugar;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		lugarId			= rs.getString("LUGAR_ID");
		nombreLugar		= rs.getString("NOMBRE_LUGAR");
		
	}
	
	public void mapeaRegId(Connection con, String LugarId, String orden) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_LUGARCOMIDA WHERE LUGAR_ID = ? "+orden); 
			ps.setInt(1,Integer.parseInt(LugarId));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntLugarComidaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}