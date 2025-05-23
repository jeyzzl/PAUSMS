// Clase Util para la tabla de Cat_Facultad
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FacultadUtil{
	
	public ArrayList<CatFacultad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE "+
				"FROM ENOC.CAT_FACULTAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;	
	
	}
		
	public ArrayList<CatFacultad> lisFacultadesEnCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"+
				" FROM ENOC.CAT_FACULTAD"+
				" WHERE FACULTAD_ID IN (SELECT DISTINCT(ENOC.FACULTAD(CARRERA_ID)) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|lisFacultadesEnCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;
	}
	
	public ArrayList<CatFacultad> getLista(Connection conn, String areaId, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE "+
				"FROM ENOC.CAT_FACULTAD WHERE AREA_ID = '"+areaId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;
	}
	
	public ArrayList<CatFacultad> lisFacConMentor(Connection conn, String periodoId, String fecha, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE "
				+ " FROM ENOC.CAT_FACULTAD"
				+ " WHERE FACULTAD_ID IN "
				+ "		(SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodoId+"' "
				+ "		AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|lisFacConMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;
	}
	
	public HashMap<String,CatFacultad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatFacultad> map = new HashMap<String,CatFacultad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatFacultad obj = new CatFacultad();
				obj.mapeaReg(rs);
				llave = obj.getFacultadId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String getNombreFacultad(Connection conn, String facultadId , String orden) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String comando			= "";
		String nombre			= "Vacio";
		
		try{			
			comando = "SELECT TITULO||' '||NOMBRE_FACULTAD AS NOMBRE FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = '"+facultadId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE");				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|getNombreFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public String getNombreFacultadCarrera(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;		
		String comando			= "";
		String nombre			= "Vacio";
		
		try{			
			comando = "SELECT TITULO||' '||NOMBRE_FACULTAD AS NOMBRE FROM ENOC.CAT_FACULTAD " + 
					"WHERE FACULTAD_ID = (SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD " + 
					"WHERE CARRERA_ID = " +
					"	(SELECT CARRERA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')) "+orden;
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE");				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|getNombreFacultadCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

	public ArrayList<CatFacultad> filtroporAccesoFac(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad		= new ArrayList<CatFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.CAT_FACULTAD"+
					  " WHERE FACULTAD_ID IN"+
					  " (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'),CARRERA_ID)>0)"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|filtroporAccesoFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;
	}
	
public ArrayList<CatFacultad> facultadPorCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CatFacultad> lisFacultad		= new ArrayList<CatFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN (SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"')"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatFacultad facultad = new CatFacultad();
				facultad.mapeaReg(rs);
				lisFacultad.add(facultad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.FacultadUtil|facultadPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFacultad;
	}
	
}