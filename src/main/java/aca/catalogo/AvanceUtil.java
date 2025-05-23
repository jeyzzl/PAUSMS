// Clase para la tabla Cat_Avance
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AvanceUtil{
	public boolean insertReg(Connection conn, CatAvance avance ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_AVANCE(AVANCE_ID, NOMBRE_AVANCE ) "+
				"VALUES( TO_NUMBER(?,'99'), ? ) ");
			ps.setString(1, avance.getAvanceId());
			ps.setString(2, avance.getNombreAvance());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatAvance avance ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_AVANCE "+ 
				"SET NOMBRE_AVANCE = ? "+
				"WHERE AVANCE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, avance.getNombreAvance());
			ps.setString(2, avance.getAvanceId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String avanceId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_AVANCE "+ 
				"WHERE AVANCE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, avanceId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatAvance mapeaRegId( Connection conn, String avanceId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatAvance avance 		= new CatAvance();
		
		try{
			ps = conn.prepareStatement("SELECT AVANCE_ID, NOMBRE_AVANCE "+
				"FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,avanceId);
			rs = ps.executeQuery();
			if (rs.next()){
				avance.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return avance;
	}
	
	public boolean existeReg(Connection conn, String avanceId ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,avanceId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(AVANCE_ID)+1 MAXIMO FROM ENOC.CAT_AVANCE"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public static String getNombreAvance(Connection conn, String avanceId) throws SQLException{
		String nombre 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_AVANCE FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, avanceId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_AVANCE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|getNombreAvance|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	
		
	public ArrayList<CatAvance> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatAvance> lisAvance	= new ArrayList<CatAvance>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT AVANCE_ID, NOMBRE_AVANCE FROM ENOC.CAT_AVANCE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatAvance avance = new CatAvance();
				avance.mapeaReg(rs);
				lisAvance.add(avance);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAvance;
	}
	
	public HashMap<String,CatAvance> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatAvance> map = new HashMap<String,CatAvance>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT AVANCE_ID, NOMBRE_AVANCE FROM ENOC.CAT_AVANCE "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatAvance obj = new CatAvance();
				obj.mapeaReg(rs);
				llave = obj.getAvanceId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AvanceUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}