// Clase Util para la tabla de Cat_Carrera
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UnionUtil{
		
	public String getNombreUnion(Connection conn, String divisionId, String unionId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "";
		
		try{
			comando = "SELECT NOMBRE_UNION FROM ENOC.CAT_UNION "+ 
				"WHERE DIVISION_ID = TO_NUMBER('"+divisionId+"','999') "+
				"AND UNION_ID = TO_NUMBER('"+unionId+"','999') ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_UNION");
			}else{
				nombre = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.UnionUtil|getNombreUnion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CatUnion> getLista(Connection conn, String divisionId, String orden ) throws SQLException{
		
		ArrayList<CatUnion> lisUnion	= new ArrayList<CatUnion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			if (divisionId==null||divisionId.equals(" ")) divisionId = "0";
			comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA,COD_POSTAL, "+
				"TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID FROM ENOC.CAT_UNION "+ 
				"WHERE DIVISION_ID = TO_NUMBER('"+divisionId+"','999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatUnion union = new CatUnion();
				union.mapeaReg(rs);
				lisUnion.add(union);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.UnionUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUnion;
	}	
	
	public ArrayList<CatUnion> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<CatUnion> lisUnion	= new ArrayList<CatUnion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_UNION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatUnion union = new CatUnion();
				union.mapeaReg(rs);
				lisUnion.add(union);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.UnionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUnion;
	}
	
	public HashMap<String,CatUnion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatUnion> map = new HashMap<String,CatUnion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
				"FROM ENOC.CAT_UNION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatUnion obj = new CatUnion();
				obj.mapeaReg(rs);
				llave = obj.getDivisionId()+obj.getUnionId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.UnionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}