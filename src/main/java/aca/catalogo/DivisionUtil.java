// Clase Util para la tabla de Cat_Division
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DivisionUtil{
	
	public boolean insertReg(Connection conn, CatDivision division ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_DIVISION"
					+ " (DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA, COD_POSTAL,"
					+ " TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999'),"
					+ " TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)");
			ps.setString(1, division.getDivisionId());
			ps.setString(2, division.getNombreDivision());
			ps.setString(3, division.getDireccion());
			ps.setString(4, division.getColonia());
			ps.setString(5, division.getCodPostal());
			ps.setString(6, division.getTelefono());
			ps.setString(7, division.getFax());
			ps.setString(8, division.getEmail());
			ps.setString(9, division.getPaisId());
			ps.setString(10,division.getEstadoId());
			ps.setString(11,division.getCiudadId());
			ps.setString(12,division.getNombreCorto());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatDivision division ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_DIVISION "+ 
				"SET NOMBRE_DIVISION = ?, "+
				"DIRECCION = ?, "+
				"COLONIA = ?, "+
				"COD_POSTAL = ?, "+
				"TELEFONO = ?, "+
				"FAX = ?, "+
				"EMAIL = ?, "+
				"PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), "+
				"CIUDAD_ID = TO_NUMBER(?,'999'),"+
				"NOMBRE_CORTO = ? "+
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') ");
			ps.setString(1, division.getNombreDivision());
			ps.setString(2, division.getDireccion());
			ps.setString(3, division.getColonia());
			ps.setString(4, division.getCodPostal());
			ps.setString(5, division.getTelefono());
			ps.setString(6, division.getFax());
			ps.setString(7, division.getEmail());
			ps.setString(8, division.getPaisId());
			ps.setString(9, division.getEstadoId());
			ps.setString(10,division.getCiudadId());
			ps.setString(11,division.getNombreCorto());
			ps.setString(12,division.getDivisionId());		
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String divisionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_DIVISION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatDivision mapeaRegId( Connection conn, String divisionId ) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		CatDivision division 	= new CatDivision();
		
		try{
			ps = conn.prepareStatement("SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, "+
				"COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO "+ 
				"FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, divisionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				division.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return division;
	}
	
	public boolean existeReg(Connection conn, String divisionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_DIVISION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(DIVISION_ID)+1 MAXIMO FROM ENOC.CAT_DIVISION "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
		
	public ArrayList<CatDivision> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatDivision> lisDivision = new ArrayList<CatDivision>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =  "SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO "+
				"FROM ENOC.CAT_DIVISION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatDivision division = new CatDivision();
				division.mapeaReg(rs);
				lisDivision.add(division);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDivision;
	}
	
	public HashMap<String,CatDivision> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatDivision> map = new HashMap<String,CatDivision>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DIVISION_ID, NOMBRE_DIVISION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CORTO "+
				"FROM ENOC.CAT_DIVISION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatDivision obj = new CatDivision();
				obj.mapeaReg(rs);
				llave = obj.getDivisionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String getNombreDivision(Connection conn, String divisionId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre 		= "";
		
		try{
			comando =  "SELECT NOMBRE_DIVISION FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = "+divisionId; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_DIVISION");
			}else{
				nombre = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|getNombreDivision|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public String getNombreCorto(Connection conn, String divisionId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre 		= "";
		
		try{
			comando =  "SELECT NOMBRE_CORTO FROM ENOC.CAT_DIVISION WHERE DIVISION_ID = "+divisionId; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_CORTO");
			}else{
				nombre = "-";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DivisionUtil|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}