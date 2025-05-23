// Clase para la tabla de Modulo
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatReligionDao{
	
	
	public boolean insertReg(Connection conn, CatReligion religion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_RELIGION(RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ? ) ");
			ps.setString(1, religion.getReligionId());
			ps.setString(2, religion.getNombreReligion());
			ps.setString(3, religion.getNombreCorto());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatReligion religion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_RELIGION"
					+ " SET NOMBRE_RELIGION = ?,"
					+ " NOMBRE_CORTO = ?"
					+ " WHERE RELIGION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, religion.getNombreReligion());
			ps.setString(2, religion.getNombreCorto());
			ps.setString(3, religion.getReligionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String religionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_RELIGION"
					+ " WHERE RELIGION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, religionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatReligion mapeaRegId( Connection conn, String religionId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatReligion religion 	= new CatReligion();
		
		try{
			ps = conn.prepareStatement("SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO "+
				"FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,religionId);
			rs = ps.executeQuery();
			if (rs.next()){
				religion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return religion;
	}
	
	public boolean existeReg(Connection conn, String religionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999') "); 
			ps.setString(1,religionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(RELIGION_ID)+1 MAXIMO FROM ENOC.CAT_RELIGION"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreReligion(Connection conn, String religionId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "empty";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, religionId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_RELIGION");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreCorto(Connection conn, String religionId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, religionId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
		
	public ArrayList<CatReligion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatReligion> lisReligion = new ArrayList<CatReligion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO FROM ENOC.CAT_RELIGION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatReligion religion = new CatReligion();
				religion.mapeaReg(rs);
				lisReligion.add(religion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReligion;
	}
	
	public HashMap<String,CatReligion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatReligion> mapReligion = new HashMap<String,CatReligion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT RELIGION_ID, NOMBRE_RELIGION, NOMBRE_CORTO FROM ENOC.CAT_RELIGION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatReligion religion = new CatReligion();
				religion.mapeaReg(rs);
				llave = religion.getReligionId();
				mapReligion.put(llave, religion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatReligionDao|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapReligion;
	}	
}