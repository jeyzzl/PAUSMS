package aca.por;

import java.sql.*;
import java.util.ArrayList;

public class PorSeccionUtil {
	
	public ArrayList<PorSeccion> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorSeccion> lisPeriodo	= new ArrayList<PorSeccion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, TITULO, SECCION_NOMBRE, SECCION_SUPERIOR, TIPO, ACCESO, ORDEN, ESTADO, INSTRUCCIONES"
					+ " FROM ENOC.POR_SECCION "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccion seccion = new PorSeccion();
				seccion.mapeaReg(rs);
				lisPeriodo.add(seccion);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public ArrayList<PorSeccion> listPortafolio(Connection conn, String porId, String orden ) throws SQLException{
		
		ArrayList<PorSeccion> lista	= new ArrayList<PorSeccion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, TITULO, SECCION_NOMBRE, SECCION_SUPERIOR, TIPO, ACCESO, ORDEN, ESTADO, INSTRUCCIONES"
					+ " FROM ENOC.POR_SECCION "
					+ " WHERE POR_ID = "+porId+" "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccion periodo = new PorSeccion();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionUtil|listPortafolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public ArrayList<PorSeccion> listPortafolio(Connection conn, String porId, String estado, String orden ) throws SQLException{
		
		ArrayList<PorSeccion> lista	= new ArrayList<PorSeccion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, TITULO, SECCION_NOMBRE, SECCION_SUPERIOR, TIPO, ACCESO, ORDEN, ESTADO, INSTRUCCIONES"
					+ " FROM ENOC.POR_SECCION"
					+ " WHERE POR_ID = "+porId+""
					+ " AND ESTADO IN ("+estado+") "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccion periodo = new PorSeccion();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionUtil|listPortafolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
}