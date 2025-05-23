// Clase Util para la tabla de Cat_Area
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModalidadUtil{
		
	public ArrayList<CatModalidad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 								= conn.createStatement();
		ResultSet rs 								= null;
		String comando								= "";
		
		try{
			comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}
	
	public ArrayList<CatModalidad> getListInscritos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT A.MODALIDAD_ID AS MODALIDAD_ID," +
					" (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) AS NOMBRE_MODALIDAD," +
					" ENLINEA, ADMISIBLE" +
					" FROM ENOC.CAT_MODALIDAD A" + 
					" WHERE (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) > 0 "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}
	
	public static HashMap<String,CatModalidad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatModalidad> mapModalidad = new HashMap<String,CatModalidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				llave = modalidad.getModalidadId();
				mapModalidad.put(llave, modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapModalidad;
	}
	
	public ArrayList<CatModalidad> getListInscCargas(Connection conn, String cargas, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT A.MODALIDAD_ID AS MODALIDAD_ID," +
					" (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) AS NOMBRE_MODALIDAD," +
					" ENLINEA, ADMISIBLE" +
					" FROM ENOC.CAT_MODALIDAD A" + 
					" WHERE (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) > 0 "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListInscCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}	
	
	public String getNombreModalidad(Connection conn, String modalidadId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String nombre			= "x";
		
		try{
			comando = "SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = '"+modalidadId+"'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_MODALIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getNombreModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}