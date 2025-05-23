// Clase Util para la tabla de Cat_Division
package aca.exa;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExaPaisUtil{
	
	public boolean insertReg(Connection conn, ExaPais exaPais ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EXA_PAIS(PAIS_ID, PAIS_NOMBRE, NACIONALIDAD, INTERAMERICA) "+
				"VALUES( TO_NUMBER (?,'999'), ?, ?, ? )");
			
			ps.setString(1, exaPais.getPaisId());
			ps.setString(2, exaPais.getNombrePais());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPais|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn, ExaPais exaPais ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_PAIS "+ 
				"SET PAIS_NOMBRE = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999')");
			
			ps.setString(1, exaPais.getNombrePais());
			ps.setString(2, exaPais.getPaisId());
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}

	
	public boolean deleteReg(Connection conn, String paisId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EXA_PAIS "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ExaPais mapeaRegId( Connection conn, String paisId) throws SQLException{
		ExaPais exaPais = new ExaPais();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, PAIS_NOMBRE "+
				"FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaPais.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaPais;
	}
	
	public boolean existeReg(Connection conn, String paisId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, paisId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(PAIS_ID)+1 MAXIMO FROM ENOC.EXA_PAIS");			 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombrePais(Connection conn, String paisId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT PAIS_NOMBRE FROM ENOC.EXA_PAIS WHERE PAIS_ID = ?"); 
			ps.setString(1, paisId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("PAIS_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|getNombrePais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
		
	public ArrayList<ExaPais> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ExaPais> lisPais		= new ArrayList<ExaPais>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PAIS_ID, PAIS_NOMBRE FROM ENOC.EXA_PAIS "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ExaPais pais = new ExaPais();
				pais.mapeaReg(rs);
				lisPais.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPais;
	}
	
	public ArrayList<ExaPais> getListPaisesDistintos(Connection conn, String orden) throws SQLException{
		ArrayList<ExaPais> lisPais		= new ArrayList<ExaPais>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DISTINCT(A.PAIS_NOMBRE) AS PAIS_NOMBRE, A.PAIS_ID FROM ENOC.EXA_PAIS A "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ExaPais pais = new ExaPais();
				pais.mapeaReg(rs);
				lisPais.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|getListPaisesDistintos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPais;
	}
	
	public static HashMap<String,ExaPais> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ExaPais> mapPais = new HashMap<String,ExaPais>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, PAIS_NOMBRE FROM ENOC.EXA_PAIS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ExaPais pais = new ExaPais();
				pais.mapeaReg(rs);
				llave = pais.getPaisId();
				mapPais.put(llave, pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaPaisUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapPais;
	}
}