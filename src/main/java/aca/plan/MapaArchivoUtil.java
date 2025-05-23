// Clase para la tabla de Mapa_Archivo
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MapaArchivoUtil{	
	
	public boolean insertReg(Connection conn, MapaArchivo mapaArchivo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_ARCHIVO (PLAN_ID, FOLIO, NOMBRE, ARCHIVO )"
					+ " VALUES( ?, TO_NUMBER(?,'99'), ?, ? )");
			
			ps.setString(1, mapaArchivo.getPlanId());
			ps.setString(2, mapaArchivo.getFolio());
			ps.setString(3, mapaArchivo.getNombre());
			ps.setBytes(4, mapaArchivo.getArchivo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaArchivo mapaArchivo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_ARCHIVO SET ARCHIVO = ?, NOMBRE = ? WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')");
			
			ps.setBytes(1, mapaArchivo.getArchivo());
			ps.setString(2, mapaArchivo.getNombre());			
			ps.setString(3, mapaArchivo.getPlanId());
			ps.setString(4, mapaArchivo.getFolio());
							
			if (ps.executeUpdate()== 1){
				ok = true;								
			}else{
				ok = false;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String planId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')");
			
			ps.setString(1, planId);
			ps.setString(2, folio);
			
			if (ps.executeUpdate() > 0)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaArchivo mapeaRegId(Connection conn, String planId, String folio) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		MapaArchivo mapaArchivo = new MapaArchivo();
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, FOLIO, NOMBRE, ARCHIVO FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO =  TO_NUMBER(?,'99')");
			
			ps.setString(1, planId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaArchivo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaArchivo;
	}	
	
	public boolean existeReg(Connection conn, String planId, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO= TO_NUMBER(?,'99')");
			
			ps.setString(1, planId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<MapaArchivo> getLista(Connection conn, String planId, String orden )  throws SQLException{
		ArrayList<MapaArchivo> lista = new ArrayList<MapaArchivo>();
		String comando		= "";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		
		try{
			comando = " SELECT PLAN_ID, FOLIO, NOMBRE, ARCHIVO FROM ENOC.MAPA_ARCHIVO"
					+ " WHERE PLAN_ID = '"+planId+"' " +orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MapaArchivo archivo = new MapaArchivo();
				archivo.mapeaReg(rs);
				lista.add(archivo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public HashMap<String, String> mapaArchivos(Connection conn ) throws SQLException{
		
		HashMap<String, String> map		= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{			
			comando = "SELECT PLAN_ID||FOLIO AS LLAVE, PLAN_ID||FOLIO AS VALOR FROM MAPA_ARCHIVO WHERE ARCHIVO IS NOT NULL";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaArchivoUtil|mapaArchivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;	
	}
	
}