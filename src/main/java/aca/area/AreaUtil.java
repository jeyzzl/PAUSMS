package aca.area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AreaUtil {
	
	public boolean insertReg(Connection conn, Area area) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.AREA(AREA_ID, AREA_NOMBRE, RESPONSABLE)"+
				" VALUES( TO_NUMBER(?, '999'), ?, ?)");

			ps.setString(1,  area.getAreaId());
			ps.setString(2,  area.getAreaNombre());
			ps.setString(3,  area.getResponsable());
			
			if (ps.executeUpdate()== 1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, Area area) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.AREA "
					+ " SET AREA_NOMBRE = ?, "
					+ " RESPONSABLE = ?"
					+ " WHERE AREA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1,  area.getAreaNombre());
			ps.setString(2,  area.getResponsable());
			ps.setString(3,  area.getAreaId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String areaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.AREA"+ 
				" WHERE AREA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1,  areaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Area mapeaRegId(Connection conn, String areaId) throws SQLException{
		Area area = new Area();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE "
					+ " FROM ENOC.AREA WHERE AREA_ID = TO_NUMBER(?, '999')"); 
			
			ps.setString(1,  areaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				area.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return area;
	}
	
	public boolean existeReg(Connection conn, String areaId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TIPO,0) AS TIPO FROM ENOC.AREA WHERE AREA_ID = TO_NUMBER(?, '999')"); 
			ps.setString(1,  areaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<Area> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<Area> lis 		= new ArrayList<Area>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.AREA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Area obj= new Area();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.area.AreaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
}