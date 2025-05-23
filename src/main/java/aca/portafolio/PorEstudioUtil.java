package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorEstudioUtil {
	
	public ArrayList<PorEstudio> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorEstudio> lista	= new ArrayList<PorEstudio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, HOJAS"+
					" FROM DANIEL.POR_ESTUDIO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorEstudio periodo = new PorEstudio();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public ArrayList<PorEstudio> getListEmpleado(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<PorEstudio> lista	= new ArrayList<PorEstudio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, HOJAS"+
					" FROM DANIEL.POR_ESTUDIO " +
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorEstudio periodo = new PorEstudio();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudioUtil|getListEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public HashMap<String,PorEstudio> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,PorEstudio> map = new HashMap<String,PorEstudio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, HOJAS"+
					" FROM DANIEL.POR_ESTUDIO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorEstudio obj = new PorEstudio();
				obj.mapeaReg(rs);
				llave = obj.getNivelId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudioUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
}