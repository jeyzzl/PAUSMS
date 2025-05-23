// Clase Util para la tabla de Cat_Edificios
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EdificioUtil{
	
	public boolean insertReg(Connection conn, CatEdificio edificio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_EDIFICIO(EDIFICIO_ID, NOMBRE_EDIFICIO, USUARIOS) "+
				"VALUES( ?, ? , ?) ");
			ps.setString(1, edificio.getEdificioId());
			ps.setString(2, edificio.getNombreEdificio());
			ps.setString(3, edificio.getUsuarios());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatEdificio edificio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_EDIFICIO "+ 
				"SET NOMBRE_EDIFICIO = ?, USUARIOS = ? "+
				"WHERE EDIFICIO_ID = ?");
			ps.setString(1, edificio.getNombreEdificio());
			ps.setString(2, edificio.getUsuarios());
			ps.setString(3, edificio.getEdificioId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String edificioId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_EDIFICIO "+ 
				"WHERE EDIFICIO_ID = ?");
			ps.setString(1, edificioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatEdificio mapeaRegId( Connection conn, String edificioId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatEdificio edificio 	= new CatEdificio(); 
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ?"); 
			ps.setString(1,edificioId);
			rs = ps.executeQuery();
			if (rs.next()){
				edificio.mapeaReg(rs);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return edificio;
	}
	
	public boolean existeReg(Connection conn, String edificioId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ? ");
			ps.setString(1,edificioId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(EDIFICIO_ID)+1 MAXIMO FROM ENOC.CAT_EDIFICIO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public ArrayList<CatEdificio> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatEdificio> lisEdificio = new ArrayList<CatEdificio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO, USUARIOS FROM ENOC.CAT_EDIFICIO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEdificio edificio = new CatEdificio();
				edificio.mapeaReg(rs);
				lisEdificio.add(edificio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdificio;
	}
	
	public HashMap<String,CatEdificio> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEdificio> map = new HashMap<String,CatEdificio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO, USUARIOS FROM ENOC.CAT_EDIFICIO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEdificio obj = new CatEdificio();
				obj.mapeaReg(rs);
				llave = obj.getEdificioId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EdificioUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapUsuarios(Connection conn) throws SQLException{
		HashMap<String, String> mapaFacultadId = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO, USUARIOS FROM ENOC.CAT_EDIFICIO ";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaFacultadId.put(rs.getString("EDIFICIO_ID"), rs.getString("USUARIOS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapUsuarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaFacultadId;
	}
}