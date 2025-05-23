package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BitAreaUtil {

	public boolean insertReg(Connection conn, BitArea area) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BIT_AREA"+ 
				"(AREA_ID, AREA_NOMBRE, RESPONSABLE)"+
				" VALUES(TO_NUMBER(?, '999'), ?, ?)");
					
			ps.setString(1,	area.getAreaId());
			ps.setString(2, area.getAreaNombre());
			ps.setString(3, area.getResponsable());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BitArea area) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_AREA"
					+ " SET AREA_NOMBRE = ?,"
					+ " RESPONSABLE = ?,"
					+ " ACCESO = ?" +			
				" WHERE AREA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, area.getAreaNombre());
			ps.setString(2, area.getResponsable());
			ps.setString(3, area.getAcceso());
			ps.setString(4, area.getAreaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String areaId) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_AREA"+ 
				" WHERE AREA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, areaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BitArea mapeaRegId( Connection conn, String areaId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		BitArea area 			= new BitArea();
		try{
			ps = conn.prepareStatement(" SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
									 + " FROM ENOC.BIT_AREA WHERE AREA_ID = "+areaId); 
			rs = ps.executeQuery();
			if (rs.next()){
				area.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return area;
	}
	
	public static String getAreaNombre( Connection conn, String areaId) throws SQLException{
		
		PreparedStatement ps 	= null; 
		ResultSet rs 			= null;
		String nombre			= "-";
		
		try{
			ps = conn.prepareStatement(" SELECT AREA_NOMBRE FROM ENOC.BIT_AREA WHERE AREA_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, areaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("AREA_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public ArrayList<BitArea> lisAreas(Connection conn, String orden) throws SQLException{
		ArrayList<BitArea> areas 	= new ArrayList<BitArea>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
					+ " FROM BIT_AREA "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitArea area = new BitArea();
				area.mapeaReg(rs);
				areas.add(area);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|getTramiteList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return areas;
	}	
	
	public ArrayList<BitArea> lisAreasUsuario(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<BitArea> areas 	= new ArrayList<BitArea>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;				
		try{
			String comando = " SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
					+ " FROM BIT_AREA "
					+ " WHERE AREA_ID IN (SELECT AREA_ID FROM BIT_AREA_USUARIO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitArea area = new BitArea();
				area.mapeaReg(rs);
				areas.add(area);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|getTramiteList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return areas;
	}
	
	public HashMap<String, String> mapAreas(Connection conn, String orden) throws SQLException{
		HashMap<String, String> mapAreas 	= new HashMap<String, String>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = " SELECT AREA_ID, AREA_NOMBRE "
					+ " FROM BIT_AREA "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapAreas.put(rs.getString("AREA_ID"), rs.getString("AREA_NOMBRE"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.AreaUtil|mapAreas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapAreas;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(AREA_ID)+1,1) AS MAXIMO FROM ENOC.BIT_AREA"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.AreaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
}
