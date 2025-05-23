/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aca.edo.EdoArea;

/**
 * @author Nio
 *
 */
public class EdoAreaUtil {
	
	public boolean insertReg(Connection conn, EdoArea area) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO_AREA(	AREA_ID, AREA_NOMBRE,AREA_TITULO)" +
				" VALUES(TO_NUMBER(?, '99'), ?,?) ");
			
			ps.setString(1, area.getAreaId());
			ps.setString(2, area.getAreaNombre());
			ps.setString(3, area.getAreaTitulo());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EdoArea area) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_AREA" + 
				" SET AREA_NOMBRE = ?," +
				" AREA_TITULO = ?" +	
				" WHERE EDO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, area.getAreaNombre());
			ps.setString(2, area.getAreaTitulo());
			ps.setString(3, area.getAreaId());	
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String areaId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_AREA"+ 
				" WHERE AREA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, areaId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}	
	
	public EdoArea mapeaRegId(Connection con, String areaId) throws SQLException{
		EdoArea area = new EdoArea(); 
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT AREA_ID, AREA_NOMBRE, AREA_TITULO" +
					" FROM ENOC.EDO_AREA" + 
					" WHERE AREA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				area.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return area;
	}	
	
	public boolean existeReg(Connection conn, String areaId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_AREA" + 
					" WHERE AREA_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(AREA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_AREA"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public ArrayList<EdoArea> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<EdoArea> lis 			= new ArrayList<EdoArea>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.EDO_AREA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EdoArea obj= new EdoArea();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoArea|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}