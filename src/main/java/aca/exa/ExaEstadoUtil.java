// Clase Util para la tabla de Cat_Carrera
package aca.exa;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExaEstadoUtil{
	
	public boolean insertReg(Connection Conn, ExaEstado exaEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO ENOC.EXA_ESTADO"+ 
				"(PAIS_ID, ESTADO_ID, ESTADO_NOMBRE) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'9999'), ?)");
			
			ps.setString(1, exaEstado.getPaisId());
			ps.setString(2, exaEstado.getEstadoId());
			ps.setString(3, exaEstado.getNombreEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
			
	public boolean updateReg(Connection conn, ExaEstado exaEstado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_ESTADO "+ 
				"SET ESTADO_NOMBRE = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')");
			
			ps.setString(1, exaEstado.getNombreEstado());
			ps.setString(2, exaEstado.getPaisId());
			ps.setString(3, exaEstado.getEstadoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String paisId, String estadoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EXA_ESTADO "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')");
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}

	public ExaEstado mapeaRegId( Connection conn, String paisId, String estadoId ) throws SQLException{
		ExaEstado exaEstado = new ExaEstado();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '9999')"); 
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaEstado;
	}
	
	public boolean existeReg(Connection conn, String paisId, String estadoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_ESTADO "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')");
			ps.setString(1,paisId);
			ps.setString(2,estadoId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(ESTADO_ID)+1 AS MAXIMO FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreEstado(Connection conn, String paisId, String estadoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO_NOMBRE FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')"); 
			ps.setString(1, paisId);
			ps.setString(2, estadoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("ESTADO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getNombreEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public ArrayList<ExaEstado> getLista(Connection conn, String paisId, String orden ) throws SQLException{
		
		ArrayList<ExaEstado> lisEstado	= new ArrayList<ExaEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEstado estado = new ExaEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<ExaEstado> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<ExaEstado> lisEstado	= new ArrayList<ExaEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaEstado estado = new ExaEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public HashMap<String,ExaEstado> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ExaEstado> map = new HashMap<String,ExaEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ExaEstado obj = new ExaEstado();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,ExaEstado> getMapPaisEstado(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ExaEstado> map = new HashMap<String,ExaEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ExaEstado obj = new ExaEstado();
				obj.mapeaReg(rs);
				llave = obj.getPaisId()+obj.getEstadoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String getEstado(Connection Conn,String paisId, String estadoId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT ESTADO_NOMBRE FROM ENOC.EXA_ESTADO where estado_id = "+estadoId+" and pais_id = "+paisId; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstadoUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}		
}