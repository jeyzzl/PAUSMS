// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EstrategiaUtil{
	
	public boolean insertReg(Connection conn, CatEstrategia estrategia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_ESTRATEGIA"+ 
				"(ESTRATEGIA_ID, NOMBRE_ESTRATEGIA) "+
				"VALUES( ?, ? ) ");
			ps.setString(1, estrategia.getEstrategiaId());
			ps.setString(2, estrategia.getNombreEstrategia());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatEstrategia estrategia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ESTRATEGIA "+ 
				"SET NOMBRE_ESTRATEGIA = ? "+
				"WHERE ESTRATEGIA_ID = ?");
			ps.setString(1, estrategia.getNombreEstrategia());			
			ps.setString(2, estrategia.getEstrategiaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String estrategiaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ESTRATEGIA "+ 
				"WHERE ESTRATEGIA_ID = ?");
			ps.setString(1, estrategiaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatEstrategia mapeaRegId( Connection conn, String estrategiaId) throws SQLException{
		ResultSet rs 				= null;
		PreparedStatement ps 		= null; 
		CatEstrategia estrategia 	= new CatEstrategia();
		
		try{
			ps = conn.prepareStatement("SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA "+
				"FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?"); 
			ps.setString(1,estrategiaId);
			rs = ps.executeQuery();
			if (rs.next()){
				estrategia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return estrategia;
	}
	
	public boolean existeReg(Connection conn, String estrategiaId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ? "); 
			ps.setString(1,estrategiaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getNombreEstrategia(Connection conn, String estrategiaId) throws SQLException{
		ResultSet rs = null;
		String nombre = "";
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA "+
				"FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?"); 
			
			ps.setString(1, estrategiaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("NOMBRE_ESTRATEGIA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
		
	public ArrayList<CatEstrategia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatEstrategia> lisEstrategia	= new ArrayList<CatEstrategia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA "+
				"FROM ENOC.CAT_ESTRATEGIA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEstrategia estrategia = new CatEstrategia();
				estrategia.mapeaReg(rs);
				lisEstrategia.add(estrategia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstrategia;
	}
	
	public HashMap<String,CatEstrategia> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEstrategia> map = new HashMap<String,CatEstrategia>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA "+
				"FROM ENOC.CAT_ESTRATEGIA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEstrategia obj = new CatEstrategia();
				obj.mapeaReg(rs);
				llave = obj.getEstrategiaId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String getNombre(Connection conn, String estrategiaId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String nombre			= "x";
		
		try{
			comando = "SELECT NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = '"+estrategiaId+"'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_ESTRATEGIA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstrategiaUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}