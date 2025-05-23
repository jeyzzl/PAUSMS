// Clase Util para la tabla de Cat_Area
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CiudadUtil{
	
	public boolean insertReg(Connection conn, CatCiudad ciudad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_CIUDAD"+ 
				"(PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD) "+
				"VALUES( TO_NUMBER (?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)");
			ps.setString(1, ciudad.getPaisId());
			ps.setString(2, ciudad.getEstadoId());
			ps.setString(3, ciudad.getCiudadId());
			ps.setString(4, ciudad.getNombreCiudad());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatCiudad ciudad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_CIUDAD "+ 
				"SET NOMBRE_CIUDAD = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, ciudad.getNombreCiudad());
			ps.setString(2, ciudad.getPaisId());
			ps.setString(3, ciudad.getEstadoId());
			ps.setString(4, ciudad.getCiudadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn,String paisId, String estadoId, String ciudadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatCiudad mapeaRegId( Connection conn, String paisId, String estadoId, String ciudadId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatCiudad ciudad		= new CatCiudad();			
		
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD "+
				"FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ciudad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ciudad;
	}
	
	public boolean existeReg(Connection conn, String paisId, String estadoId, String ciudadId ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			ps.setString(3,ciudadId);
			
			rs = ps.executeQuery();
				if (rs.next()){
				ok = true;
			}else{
			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String paisId, String estadoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "001";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CIUDAD_ID)+1 AS MAXIMO FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCiudad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public static String getNombreCiudad(Connection conn, String paisId, String estadoId, String ciudadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999') AND CIUDAD_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			ps.setString(3, ciudadId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CIUDAD");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getNombreCiudad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public ArrayList<CatCiudad> getLista(Connection conn, String paisId, String estadoId, String orden ) throws SQLException{
		
		ArrayList<CatCiudad> lisCiudad 	= new ArrayList<CatCiudad>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD "+
				"FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999') "+
				"AND ESTADO_ID = TO_NUMBER('"+estadoId+"','999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCiudad ciudad = new CatCiudad();
				ciudad.mapeaReg(rs);
				lisCiudad.add(ciudad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiudad;
	}
	
	public ArrayList<CatCiudad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatCiudad> lisCiudad = new ArrayList<CatCiudad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCiudad ciudad = new CatCiudad();
				ciudad.mapeaReg(rs);
				lisCiudad.add(ciudad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCiudad;
	}
	
	public HashMap<String,CatCiudad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatCiudad> map = new HashMap<String,CatCiudad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatCiudad obj = new CatCiudad();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId()+obj.getCiudadId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	
	public String getCiudad(Connection Conn,String paisId,String estadoId, String ciudadId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD where ciudad_id = "+ciudadId+" and estado_id ="+estadoId+" and pais_id = "+paisId; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CiudadUtil|getCiudad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
/*		
	public static void main(String args[]){
		try{
			Connection conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "camina_con_dios");
			
			CiudadUtil cu = new CiudadUtil();
			ArrayList lis = new ArrayList();			
			lis = cu.getLista(conn, "91","19","4");
			
			for (int i=0; i<lis.size(); i++){
				CatCiudad c = (CatCiudad) lis.get(i);
				System.out.println(c.getPaisId()+"--"+c.getEstadoId()+"--"+c.getCiudadId()+"--"+c.getNombreCiudad());
			}			
			conn.commit();
			conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}	
*/			
}