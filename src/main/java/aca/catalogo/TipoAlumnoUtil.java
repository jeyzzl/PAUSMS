// Clase para la tabla de Modulo
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TipoAlumnoUtil{
		
	public ArrayList<CatTipoAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatTipoAlumno> lisTipoalumno = new ArrayList<CatTipoAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatTipoAlumno tipoalumno = new CatTipoAlumno();
				tipoalumno.mapeaReg(rs);
				lisTipoalumno.add(tipoalumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTipoalumno;
	}
	
	public static HashMap<String,CatTipoAlumno> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatTipoAlumno> mapTipo = new HashMap<String,CatTipoAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatTipoAlumno tipo = new CatTipoAlumno();
				tipo.mapeaReg(rs);
				llave = tipo.getTipoId();
				mapTipo.put(llave, tipo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoAlumnoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapTipo;
	}
	
	public static HashMap<String,String> getMapNombreTipo(Connection conn) throws SQLException{
		
		HashMap<String,String> mapTipo = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT TIPO_ID, NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTipo.put(rs.getString("TIPO_ID"), rs.getString("NOMBRE_TIPO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.TipoAlumnoUtil|getMapNombreTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapTipo;
	}
}