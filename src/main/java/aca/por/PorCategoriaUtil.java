package aca.por;

import java.sql.*;
import java.util.ArrayList;

public class PorCategoriaUtil {
	
	public ArrayList<PorCategoria> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorCategoria> lista	= new ArrayList<PorCategoria>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CATEGORIA_ID, CATEGORIA_NOMBRE FROM ENOC.POR_CATEGORIA "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorCategoria objeto = new PorCategoria();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorCategoriaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public String getCategoriaNombre(Connection conn, String requisitoId) throws SQLException{
		
		String categoriaNombre = "-";
		Statement st	= conn.createStatement();
		ResultSet rs	= null;
		String comando	= "";
		
		try{
			comando = "SELECT CATEGORIA_NOMBRE FROM ENOC.POR_CATEGORIA WHERE CATEGORIA_ID IN (SELECT CATEGORIA_ID FROM ENOC.POR_REQUISITO WHERE REQUISITO_ID = '"+requisitoId+"')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				categoriaNombre = rs.getString("CATEGORIA_NOMBRE");		
			}
		}catch (Exception ex){
			System.out.println("Error - aca.por.PorCategoriaUtil|getCategoriaNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (st!=null) rs.close();
		}
		return categoriaNombre;
	}
}