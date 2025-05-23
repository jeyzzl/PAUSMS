// Clase Util para la tabla de Cat_Area
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatNivelUtil{
	
	public boolean insertReg(Connection conn, CatNivel nivel ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_NIVEL(NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN) "+
				"VALUES( TO_NUMBER(?,'99'), ? , ?, TO_NUMBER(?,'99')) ");
			ps.setString(1, nivel.getNivelId());
			ps.setString(2, nivel.getNombreNivel());
			ps.setString(3, nivel.getEstado());
			ps.setString(4, nivel.getOrden());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatNivel nivel ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_NIVEL "+ 
				" SET NOMBRE_NIVEL = ?," +
				" ESTADO = ? , " +
				" ORDEN = TO_NUMBER(?,'99') "+
				" WHERE NIVEL_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivel.getNombreNivel());			
			ps.setString(2, nivel.getEstado());			
			ps.setString(3, nivel.getOrden());			
			ps.setString(4, nivel.getNivelId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String nivelId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_NIVEL "+ 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatNivel mapeaRegId( Connection conn, String nivelId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		CatNivel nivel = new CatNivel();
		try{
			ps = conn.prepareStatement("SELECT NIVEL_ID, NOMBRE_NIVEL, ORDEN, ESTADO "+
				"FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,nivelId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				nivel.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nivel;
	}
	
	public boolean existeReg(Connection conn, String nivelId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,nivelId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(NIVEL_ID)+1 MAXIMO FROM ENOC.CAT_NIVEL"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNivelNombre(Connection conn, String nivelId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_NIVEL FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, nivelId);			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_NIVEL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|getNivelNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public ArrayList<CatNivel> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatNivel> lisNivel 		= new ArrayList<CatNivel>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN FROM ENOC.CAT_NIVEL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatNivel nivel = new CatNivel();
				nivel.mapeaReg(rs);
				lisNivel.add(nivel);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisNivel;
	}
	
	public HashMap<String,CatNivel> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatNivel> map = new HashMap<String,CatNivel>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN FROM ENOC.CAT_NIVEL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatNivel obj = new CatNivel();
				obj.mapeaReg(rs);
				llave = obj.getNivelId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}