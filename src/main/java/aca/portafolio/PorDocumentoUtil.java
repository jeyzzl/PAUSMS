package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorDocumentoUtil {
	
	public ArrayList<PorDocumento> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorDocumento> lisPeriodo	= new ArrayList<PorDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO "+
					"FROM DANIEL.POR_DOCUMENTO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorDocumento periodo = new PorDocumento();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public ArrayList<PorDocumento> getListSoloEmpleados(Connection conn, String orden ) throws SQLException{
		
		ArrayList<PorDocumento> lisPeriodo	= new ArrayList<PorDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO "+
					"FROM DANIEL.POR_DOCUMENTO WHERE USUARIO = '*' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorDocumento periodo = new PorDocumento();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumentoUtil|getListSoloEmpleados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public ArrayList<PorDocumento> listDisponibles(Connection conn, String periodoId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<PorDocumento> lista	= new ArrayList<PorDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO" 
					+ " FROM DANIEL.POR_DOCUMENTO "
					+ " WHERE DOCUMENTO_ID NOT IN "
					+ "	(SELECT DOCUMENTO_ID FROM DANIEL.POR_EMPDOCTO WHERE PERIODO_ID = '"+periodoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"')"
					+ " AND DOCUMENTO_ID  NOT IN (SELECT DOCUMENTO_ID FROM DANIEL.POR_NIVEL WHERE DOCUMENTO_ID IS NOT NULL) "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorDocumento periodo = new PorDocumento();
				periodo.mapeaReg(rs);
				lista.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumentoUtil|listDisponibles|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
	public HashMap<String,PorDocumento> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,PorDocumento> map = new HashMap<String,PorDocumento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, USUARIO, ARCHIVO"+
					" FROM DANIEL.POR_NIVEL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorDocumento obj = new PorDocumento();
				obj.mapeaReg(rs);
				llave = obj.getDocumentoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumentoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
	public static String getdocumentoNombre(Connection conn, String documentoId) throws SQLException{
		
		String cantidad 				= "0";
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT DOCUMENTO_NOMBRE AS NOMBRE FROM DANIEL.POR_DOCUMENTO WHERE DOCUMENTO_ID='"+documentoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				cantidad = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorDocumentoUtil|getdocumentoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
}
}