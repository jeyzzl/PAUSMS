	// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EscuelaUtil{
	
	public boolean insertReg(Connection conn, CatEscuela escuela ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_ESCUELA(ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID) "+
				"VALUES( TO_NUMBER(?,'999'), ?, "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999')) ");
			ps.setString(1, escuela.getEscuelaId());
			ps.setString(2, escuela.getNombreEscuela());			
			ps.setString(3, escuela.getPaisId());
			ps.setString(4, escuela.getEstadoId());
			ps.setString(5, escuela.getCiudadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatEscuela escuela ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ESCUELA "+ 
				"SET NOMBRE_ESCUELA = ?, "+
				"PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), "+
				"CIUDAD_ID = TO_NUMBER(?,'999') "+
				"WHERE ESCUELA_ID = TO_NUMBER(?,'999')");
			ps.setString(1, escuela.getNombreEscuela());
			ps.setString(2, escuela.getPaisId());
			ps.setString(3, escuela.getEstadoId());
			ps.setString(4, escuela.getCiudadId());
			ps.setString(5, escuela.getEscuelaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String escuelaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ESCUELA "+ 
				"WHERE ESCUELA_ID = TO_NUMBER(?,'999')");
			ps.setString(1, escuelaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatEscuela mapeaRegId( Connection conn, String escuelaId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatEscuela escuela 		= new CatEscuela();
		
		try{
			ps = conn.prepareStatement("SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,escuelaId);
			rs = ps.executeQuery();
			if (rs.next()){
				escuela.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return escuela;
	}
	
	public boolean existeReg(Connection conn, String escuelaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,escuelaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ESCUELA_ID)+1 MAXIMO FROM ENOC.CAT_ESCUELA"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
		
	public ArrayList<CatEscuela> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatEscuela> lisEscuela  = new ArrayList<CatEscuela>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
			"FROM ENOC.CAT_ESCUELA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEscuela escuela = new CatEscuela();
				escuela.mapeaReg(rs);
				lisEscuela.add(escuela);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEscuela;
	}
	
	public HashMap<String,CatEscuela> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEscuela> map = new HashMap<String,CatEscuela>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
			"FROM ENOC.CAT_ESCUELA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEscuela obj = new CatEscuela();
				obj.mapeaReg(rs);
				llave = obj.getEscuelaId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String getNombreEscuela(Connection conn, String escuelaId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombreEscuela= "";
		
		try{
			comando = "SELECT NOMBRE_ESCUELA FROM ENOC.CAT_ESCUELA "+ 
				"WHERE ESCUELA_ID = TO_NUMBER('"+escuelaId+"','999')";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombreEscuela = rs.getString("NOMBRE_ESCUELA");				
			}else{
				nombreEscuela = "x";  
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EscuelaUtil|getNombreEscuela|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombreEscuela;
	}
}