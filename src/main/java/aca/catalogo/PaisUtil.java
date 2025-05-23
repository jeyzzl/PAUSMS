// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PaisUtil{	
	
	public boolean insertReg(Connection conn, CatPais pais ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_PAIS(PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA)"
					+ " VALUES( TO_NUMBER (?,'999'), ?, ?, ? )");
			ps.setString(1, pais.getPaisId());
			ps.setString(2, pais.getNombrePais());
			ps.setString(3, pais.getNacionalidad());
			ps.setString(4, pais.getInteramerica());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn, CatPais pais ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_PAIS"
					+ " SET NOMBRE_PAIS = ?, NACIONALIDAD = ?, INTERAMERICA = ?"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1, pais.getNombrePais());
			ps.setString(2, pais.getNacionalidad());
			ps.setString(3, pais.getInteramerica());
			ps.setString(4, pais.getPaisId());
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String paisId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_PAIS "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatPais mapeaRegId( Connection conn, String paisId) throws SQLException{		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatPais pais 			= new CatPais();
		
		try{
			ps = conn.prepareStatement("SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID"
					+ " FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next()){
				pais.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return pais;
	}
	
	public boolean existeReg(Connection conn, String paisId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(PAIS_ID)+1 MAXIMO FROM ENOC.CAT_PAIS");			 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getPais(Connection Conn, String paisId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			if (paisId.equals("")||paisId.equals(null)) paisId ="0";			
			comando = "SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = "+paisId; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombrePais(Connection conn, String paisId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, paisId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_PAIS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getNombrePais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNacionalidad(Connection Conn, String paisId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "No encontro";	
		
		try{
			comando = "SELECT COALESCE(NACIONALIDAD,'vacio') FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getNacionalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return nombre;
	}
	
	
	public static String getInteramerica(Connection Conn, String paisId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "No encontro";	
		
		try{
			comando = "SELECT INTERAMERICA FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999')"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getInteramerica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return nombre;
	}
		
	public ArrayList<CatPais> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatPais> lisPais		= new ArrayList<CatPais>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID FROM ENOC.CAT_PAIS "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatPais pais = new CatPais();
				pais.mapeaReg(rs);
				lisPais.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPais;
	}
	
	public ArrayList<CatPais> listGraduandos(Connection conn, String evento, String orden ) throws SQLException{
		
		ArrayList<CatPais> lisPais		= new ArrayList<CatPais>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID"
					+ " FROM ENOC.CAT_PAIS "
					+ " WHERE PAIS_ID IN (SELECT ENOC.ALUM_PAIS(CODIGO_PERSONAL) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID ="+evento+") "+ orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatPais pais = new CatPais();
				pais.mapeaReg(rs);
				lisPais.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|listGraduandos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPais;
	}
	
	public static HashMap<String,CatPais> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatPais> mapPais = new HashMap<String,CatPais>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID FROM ENOC.CAT_PAIS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatPais pais = new CatPais();
				pais.mapeaReg(rs);
				llave = pais.getPaisId();
				mapPais.put(llave, pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapPais;
	}
	
}