// Clase Util para la tabla de Cat_Carrera
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CarreraUtil{
		
	public ArrayList<CatCarrera> getLista(Connection conn, String facultadId, String orden ) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera	= new ArrayList<CatCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCarrera carrera = new CatCarrera();
				carrera.mapeaReg(rs);
				lisCarrera.add(carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}
	
	public ArrayList<String> getCarrerasAlumno(Connection conn, String matricula, String orden ) throws SQLException{
		
		ArrayList<String> lisCarrera	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ALUM_PLAN.PLAN_ID) AS CARRERA " +
					"FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+matricula+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisCarrera.add(rs.getString("CARRERA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getCarrerasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}
	
	
	public ArrayList<CatCarrera> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera	= new ArrayList<CatCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCarrera carrera = new CatCarrera();
				carrera.mapeaReg(rs);
				lisCarrera.add(carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public HashMap<String,CatCarrera> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatCarrera> map = new HashMap<String,CatCarrera>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatCarrera obj = new CatCarrera();
				obj.mapeaReg(rs);
				llave = obj.getCarreraId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<CatCarrera> getListDisciplina(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera	= new ArrayList<CatCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
					"C.FACULTAD_ID, C.CARRERA_ID, C.NIVEL_ID, "+
					"C.TITULO, C.NOMBRE_CARRERA, C.NOMBRE_CORTO, C.CCOSTO_ID, "+
					"C.CODIGO_PERSONAL "+
					"FROM ENOC.CAT_CARRERA C "+ 
					"WHERE CARRERA_ID IN (SELECT DISTINCT(ENOC.ALUM_CARRERA_ID(MATRICULA)) FROM ENOC.COND_ALUMNO) "+ 
					"AND CARRERA_ID IN (SELECT DISTINCT(CARRERA_ID) FROM ENOC.INSCRITOS) "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCarrera carrera = new CatCarrera();
				carrera.mapeaReg(rs);
				lisCarrera.add(carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getListDisciplina|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	// Lista de carreras con mentor en un periodo
	public ArrayList<CatCarrera> listConMentor(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera	= new ArrayList<CatCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO,"
					+ " NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = '"+periodoId+"') "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCarrera carrera = new CatCarrera();
				carrera.mapeaReg(rs);
				lisCarrera.add(carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|listConMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public String  getNombreCarrera(Connection conn, String carreraId, String opcion ) throws SQLException{
	
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String nombreCarrera = "Vacio";
		String comando	= "";
		
		try{
			
			if(opcion.equals("1")){
				comando = "SELECT NOMBRE_CARRERA AS NOMBRE FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = '"+carreraId+"' "; 
			}else{
				comando = "SELECT NOMBRE_CORTO AS NOMBRE FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = '"+carreraId+"' "; 
			}
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				
				nombreCarrera = rs.getString("NOMBRE");
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getNombreCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombreCarrera;
	}
	
	// Lista de carreras con materias en una carga
	public ArrayList<CatCarrera> getListCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"') "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
}