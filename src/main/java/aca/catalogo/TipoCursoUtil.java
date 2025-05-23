// Clase para la tabla de Modulo
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TipoCursoUtil{
		
	public ArrayList<CatTipoCurso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatTipoCurso> lisTipoCurso		= new ArrayList<CatTipoCurso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO FROM ENOC.CAT_TIPOCURSO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatTipoCurso tipoCurso = new CatTipoCurso();
				tipoCurso.mapeaReg(rs);
				lisTipoCurso.add(tipoCurso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipoCurso;
	}
	
	public HashMap<String,CatTipoCurso> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatTipoCurso> map = new HashMap<String,CatTipoCurso>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT TIPOCURSO_ID, NOMBRE_TIPOCURSO FROM ENOC.CAT_TIPOCURSO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatTipoCurso obj = new CatTipoCurso();
				obj.mapeaReg(rs);
				llave = obj.getTipoCursoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

	public String getNombreTipoCurso(Connection conn, String id ) throws SQLException{
		
		String nombre = "";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT NOMBRE_TIPOCURSO FROM ENOC.CAT_TIPOCURSO WHERE TIPOCURSO_ID = TO_NUMBER('"+id+"') "; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoCursoUtil|getNombreTipoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}