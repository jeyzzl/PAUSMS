package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IntLugarComidaUtil {
	
	public boolean insertReg(Connection conn, IntLugarComida intLugCom ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_LUGARCOMIDA(LUGAR_ID,NOMBRE_LUGAR) VALUES(?,?)"); 
			ps.setInt(1, Integer.parseInt(intLugCom.getLugarId()));
			ps.setString(2, intLugCom.getNombreLugar());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntLugarComidaUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, IntLugarComida intLugCom ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_LUGARCOMIDA SET LUGAR_ID = ?, NOMBRE_LUGAR = ? WHERE LUGAR_ID = ? ");			 
			ps.setInt(1, Integer.parseInt(intLugCom.getLugarId()));
			ps.setString(2, intLugCom.getNombreLugar());
			ps.setInt(3, Integer.parseInt(intLugCom.getLugarId()));
			
			if ( ps.executeUpdate()== 1){
				ok = true;			
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntLugarComidaUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String lugarId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_LUGARCOMIDA WHERE LUGAR_ID = ? "); 
			ps.setInt(1,Integer.parseInt(lugarId));			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntLugarComidaUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public IntLugarComida mapeaRegId(Connection con, String LugarId, String orden) throws SQLException{
		IntLugarComida intLugCom = new IntLugarComida();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_LUGARCOMIDA WHERE LUGAR_ID = ? "+orden); 
			ps.setInt(1,Integer.parseInt(LugarId));
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				intLugCom.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntLugarComidaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return intLugCom;
	}
	
}