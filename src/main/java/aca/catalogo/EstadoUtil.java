// Clase Util para la tabla de Cat_Carrera
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EstadoUtil{
	
	public boolean insertReg(Connection Conn, CatEstado estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO ENOC.CAT_ESTADO(PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO)"
					+ " VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?)");
			ps.setString(1, estado.getPaisId());
			ps.setString(2, estado.getEstadoId());
			ps.setString(3, estado.getNombreEstado());
			ps.setString(4, estado.getCorto());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatEstado estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ESTADO"
					+ " SET NOMBRE_ESTADO = ?, CORTO = ?"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, estado.getNombreEstado());
			ps.setString(2, estado.getCorto());
			ps.setString(3, estado.getPaisId());
			ps.setString(4, estado.getEstadoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String paisId, String estadoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ESTADO"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public CatEstado mapeaRegId( Connection conn, String paisId, String estadoId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatEstado estado 		= new CatEstado();
		
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO"
					+ " FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '999')"); 
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				estado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return estado;
	}
	
	public boolean existeReg(Connection conn, String paisId, String estadoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ESTADO "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String paisId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ESTADO_ID)+1 AS MAXIMO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public static String getNombreEstado(Connection conn, String paisId, String estadoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_ESTADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getNombreEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
		
	public ArrayList<CatEstado> getLista(Connection conn, String paisId, String orden ) throws SQLException{
		
		ArrayList<CatEstado> lisEstado	= new ArrayList<CatEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO"
					+ " FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999') "+ orden; 
			//System.out.println(comando);
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEstado estado = new CatEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<CatEstado> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<CatEstado> lisEstado	= new ArrayList<CatEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO"
					+ " FROM ENOC.CAT_ESTADO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEstado estado = new CatEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public HashMap<String,CatEstado> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEstado> map = new HashMap<String,CatEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO"
					+ " FROM ENOC.CAT_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEstado obj = new CatEstado();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId();
				map.put( llave, obj );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,CatEstado> getMapPaisEstado(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEstado> map = new HashMap<String,CatEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO"
					+ " FROM ENOC.CAT_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEstado obj = new CatEstado();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getMapPaisEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public static HashMap<String,String> getMapEstado(Connection conn ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO FROM ENOC.CAT_ESTADO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEstado obj = new CatEstado();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId();
				map.put(llave, obj.getNombreEstado());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static String getEstado(Connection Conn, String paisId, String estadoId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE estado_id = "+estadoId+" and pais_id = "+paisId; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}		
}