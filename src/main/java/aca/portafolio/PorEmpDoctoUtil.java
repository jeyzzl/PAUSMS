package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorEmpDoctoUtil {
	
	public ArrayList<PorEmpDocto> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorEmpDocto> lista	= new ArrayList<PorEmpDocto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_EMPDOCTO "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorEmpDocto periodo = new PorEmpDocto();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDoctoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public ArrayList<PorEmpDocto> getListEmpleados(Connection conn,String usuario, String orden ) throws SQLException{
		
		ArrayList<PorEmpDocto> lisPeriodo	= new ArrayList<PorEmpDocto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_EMPDOCTO WHERE USUARIO = '"+usuario+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorEmpDocto periodo = new PorEmpDocto();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDoctoUtil|getListEmpleados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	
	
	public HashMap<String,PorEmpDocto> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,PorEmpDocto> map = new HashMap<String,PorEmpDocto>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO"+
					" FROM DANIEL.POR_PERIODO "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorEmpDocto obj = new PorEmpDocto();
				obj.mapeaReg(rs);
				llave = obj.getCodigoPersonal()+obj.getPeriodoId()+obj.getDocumentoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDoctoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
}