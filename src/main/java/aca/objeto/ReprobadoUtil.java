package aca.objeto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ReprobadoUtil {
	
	public static ArrayList<Reprobado> getListCargas(Connection conn, String carga, String orden ) throws SQLException{
		
		ArrayList<Reprobado> lisRep	= new ArrayList<Reprobado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	" SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID," +
						" ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID," +
						" CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
						" FROM ENOC.KRDX_CURSO_ACT" +
						" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+carga+")" +
						" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
						" AND TIPOCAL_ID IN ('2','4')" +
						" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6),ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)),CODIGO_PERSONAL " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Reprobado alumno = new Reprobado();
				alumno.mapeaReg(rs);
				lisRep.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.ReprobadoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisRep;
	}


	public static ArrayList<Reprobado> getListCarga(Connection conn, String carga, String orden ) throws SQLException{
		ArrayList<Reprobado> lisRep	= new ArrayList<Reprobado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	" SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID," +
						" ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID," +
						" CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
						" FROM ENOC.KRDX_CURSO_ACT" +
						" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
						" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
						" AND TIPOCAL_ID IN ('2','4')" +
						" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6),ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)),CODIGO_PERSONAL " + orden; 							 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Reprobado alumno = new Reprobado();
				alumno.mapeaReg(rs);
				lisRep.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.ReprobadoUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisRep;
	}
	
	public static ArrayList<String[]> getListaReprobadosFacultadCarrera(Connection conn, String carga, String orden ) throws SQLException{
		ArrayList<String[]> lisRep	= new ArrayList<String[]>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID," +
					" ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)))) AS NOMBRE_FACULTAD," +
					" ENOC.FACULTAD(ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))) AS FACULTAD_ID," +
					" ENOC.NOMBRE_CARRERA(ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))) AS NOMBRE_CARRERA," +
					" ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID," +
					" CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
					" FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
					" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
					" AND TIPOCAL_ID IN ('2','4')" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6),ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)),CODIGO_PERSONAL " + orden; 							 
			rs = st.executeQuery(comando);
			while (rs.next()){
				String[] arr = new String[7];
				arr[0] = rs.getString("CARGA_ID");
				arr[1] = rs.getString("NOMBRE_FACULTAD");
				arr[2] = rs.getString("FACULTAD_ID");
				arr[3] = rs.getString("NOMBRE_CARRERA");
				arr[4] = rs.getString("CARRERA_ID");
				arr[5] = rs.getString("CODIGO_PERSONAL");
				arr[6] = rs.getString("NUMMAT");
				lisRep.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.ReprobadoUtil|getListaReprobadosFacultadCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		return lisRep;
	}
	
	public static HashMap<String,String> getMapMaterias(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> mapMaterias = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+")" +
					" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +			
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6)";
			rs = st.executeQuery(comando);
			while (rs.next()){		
				llave = rs.getString("CARGA_ID");
				mapMaterias.put(llave, rs.getString("NUMMAT"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMaterias;
	}
	
	
	public static HashMap<String,String> getMapMateriasRep(Connection conn, String cargas) throws SQLException{
		
		HashMap<String,String> mapMaterias = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN("+cargas+")" +
					" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
					" AND TIPOCAL_ID IN ('2','4')" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6)";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				llave = rs.getString("CARGA_ID");
				mapMaterias.put(llave, rs.getString("NUMMAT"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapMateriasRep|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMaterias;
	}

	public static HashMap<String,String> getMapMateriasCarreras(Connection conn, String carga) throws SQLException{
		
		HashMap<String,String> mapMaterias = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
					" FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
					" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
					" GROUP BY ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))";
			rs = st.executeQuery(comando);
			while (rs.next()){		
				llave = rs.getString("CARRERA_ID");
				mapMaterias.put(llave, rs.getString("NUMMAT"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapMateriasCarreras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMaterias;
	}
	
	
	public static HashMap<String,String> getMapMateriasCarrerasRep(Connection conn, String carga) throws SQLException{
		
		HashMap<String,String> mapMaterias = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA_ID, COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
					" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) NOT IN ('4','6')" +
					" AND TIPOCAL_ID IN ('2','4')" +
					" GROUP BY ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				llave = rs.getString("CARRERA_ID");
				mapMaterias.put(llave, rs.getString("NUMMAT"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapMateriasCarrerasRep|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapMaterias;
	}

}
