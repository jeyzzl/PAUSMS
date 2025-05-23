// Clase Util para la tabla de Cat_Asociacion
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AsociacionUtil{
	
	public boolean insertReg(Connection conn, CatAsociacion asociacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_ASOCIACION "+ 
				"(DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID ) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'))");		
			ps.setString(1, asociacion.getDivisionId());
			ps.setString(2, asociacion.getUnionId());
			ps.setString(3, asociacion.getAsociacionId());
			ps.setString(4, asociacion.getNombreAsociacion());
			ps.setString(5, asociacion.getDireccion());
			ps.setString(6, asociacion.getColonia());
			ps.setString(7, asociacion.getCodPostal());
			ps.setString(8, asociacion.getTelefono());
			ps.setString(9, asociacion.getFax());
			ps.setString(10,asociacion.getEmail());
			ps.setString(11,asociacion.getPaisId());
			ps.setString(12,asociacion.getEstadoId());
			ps.setString(13,asociacion.getCiudadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CatAsociacion asociacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ASOCIACION "+ 
				"SET NOMBRE_ASOCIACION = ?, DIRECCION = ?, COLONIA = ?, COD_POSTAL = ?, "+
				"TELEFONO = ?, FAX = ?, EMAIL = ?, PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), CIUDAD_ID = TO_NUMBER(?,'999') "+
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') "+
				"AND UNION_ID = TO_NUMBER(?,'999') "+
				"AND ASOCIACION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, asociacion.getNombreAsociacion());
			ps.setString(2, asociacion.getDireccion());
			ps.setString(3, asociacion.getColonia());
			ps.setString(4, asociacion.getCodPostal());
			ps.setString(5, asociacion.getTelefono());
			ps.setString(6, asociacion.getFax());
			ps.setString(7, asociacion.getEmail());
			ps.setString(8, asociacion.getPaisId());
			ps.setString(9, asociacion.getEstadoId());
			ps.setString(10,asociacion.getCiudadId());
			ps.setString(11,asociacion.getDivisionId());
			ps.setString(12,asociacion.getUnionId());
			ps.setString(13,asociacion.getAsociacionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String divisionId, String unionId, String asociacionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_ASOCIACION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') AND "+
				"UNION_ID = TO_NUMBER(?,'999') AND "+
				"ASOCIACION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);
			ps.setString(3, asociacionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatAsociacion mapeaRegId( Connection conn, String divisionId, String unionId, String asociacionId ) throws SQLException{
		ResultSet rs 				= null;
		PreparedStatement ps 		= null; 
		CatAsociacion asociacion 	= new CatAsociacion();
		
		try{
			ps = conn.prepareStatement("SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, "+
				"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_ASOCIACION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') "+
				"AND UNION_ID = TO_NUMBER(?,'999') "+
				"AND ASOCIACION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);
			ps.setString(3, asociacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				asociacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return asociacion;
	}
	
	public boolean existeReg(Connection conn, String divisionId, String unionId, String asociacionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_ASOCIACION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') "+
				"AND UNION_ID = TO_NUMBER(?,'999')"+
				"AND ASOCIACION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);
			ps.setString(3, asociacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String divisionId, String unionId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ASOCIACION_ID)+1 MAXIMO "+
			"FROM ENOC.CAT_ASOCIACION WHERE DIVISION_ID = TO_NUMBER (?,'999') AND UNION_ID = TO_NUMBER (?,'999')");			 
			ps.setString(1,divisionId);
			ps.setString(2,unionId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatAsociacion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;		
	}
		
	public ArrayList<CatAsociacion> getLista(Connection conn, String divisionId, String unionId, String orden ) throws SQLException{
		
		ArrayList<CatAsociacion> lisAsociacion	= new ArrayList<CatAsociacion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			if (divisionId==null||divisionId.equals(" ")) divisionId = "0";
			if (unionId==null||unionId.equals(" ")) unionId = "0";
			
			comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, "+
					"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, "+
					"ESTADO_ID, CIUDAD_ID "+
					"FROM ENOC.CAT_ASOCIACION "+ 
					"WHERE DIVISION_ID = TO_NUMBER('"+divisionId+"','999') "+
					"AND UNION_ID = TO_NUMBER('"+unionId+"','999') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatAsociacion asociacion = new CatAsociacion();
				asociacion.mapeaReg(rs);
				lisAsociacion.add(asociacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAsociacion;
	}
	
	public ArrayList<CatAsociacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatAsociacion> lisAsociacion	= new ArrayList<CatAsociacion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, "+
				"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_ASOCIACION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatAsociacion asociacion = new CatAsociacion();
				asociacion.mapeaReg(rs);
				lisAsociacion.add(asociacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAsociacion;
	}
	
	public HashMap<String,CatAsociacion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatAsociacion> map = new HashMap<String,CatAsociacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DIVISION_ID, UNION_ID, ASOCIACION_ID, NOMBRE_ASOCIACION, "+
				"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_ASOCIACION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatAsociacion obj = new CatAsociacion();
				obj.mapeaReg(rs);
				llave = obj.getDivisionId()+obj.getUnionId()+obj.getAsociacionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.AsociacionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}