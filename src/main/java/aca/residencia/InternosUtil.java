//Beans para la tabla INTERNOS
package aca.residencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InternosUtil {
	
	public ArrayList<String> getListEdadInterno(Connection conn, String dormitorio, String orden ) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(ENOC.ALUM_EDAD(CODIGO_PERSONAL)) AS EDAD FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+ orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("EDAD"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListEdadInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListEdadInternoModalidad(Connection conn, String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(ENOC.ALUM_EDAD(CODIGO_PERSONAL)) AS EDAD FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "+ orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("EDAD"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListEdadInternoModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListNacionInterno(Connection conn, String dormitorio, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(NACIONALIDAD) AS NACION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("NACION"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListNacionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListNacionInternoModalidad(Connection conn, String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = " SELECT DISTINCT(NACIONALIDAD) AS NACION FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") AND DORMITORIO IN("+dormitorio+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("NACION"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListNacionInternoModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListReligionInterno(Connection conn, String dormitorio, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(RELIGION_ID) AS RELIGION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("RELIGION"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListReligionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListReligionInternoModalidad(Connection conn, String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(RELIGION_ID) AS RELIGION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("RELIGION"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListReligionInternoModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListTipoAlumnoInterno(Connection conn, String dormitorio, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(TIPOALUMNO_ID) AS TIPO FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("TIPO"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListTipoAlumnoInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	public ArrayList<String> getListTipoAlumnoInternoModalidad(Connection conn, String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";

		try{
			comando = "SELECT DISTINCT(TIPOALUMNO_ID) AS TIPO FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){				
				lista.add(rs.getString("TIPO"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListTipoAlumnoInternoModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			

		return lista;
	}
	
	
	public HashMap<String, String> mapaEdadInterno(Connection conn, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS EDAD, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY ENOC.ALUM_EDAD(CODIGO_PERSONAL)";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("EDAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaEdadInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEdadInternoModalidad(Connection conn, String modalidades, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS EDAD, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+")"+
				" GROUP BY ENOC.ALUM_EDAD(CODIGO_PERSONAL)";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("EDAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaEdadInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNacionInterno(Connection conn, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT NACIONALIDAD, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY NACIONALIDAD";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("NACIONALIDAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaNacionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNacionInternoModalidad(Connection conn, String modalidades, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT NACIONALIDAD, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+") "+
				" GROUP BY NACIONALIDAD";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("NACIONALIDAD"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaNacionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaReligionInterno(Connection conn, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT RELIGION_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY RELIGION_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("RELIGION_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaReligionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaReligionInternoModalidad(Connection conn, String modalidades, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT RELIGION_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+")"+
				" GROUP BY RELIGION_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("RELIGION_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaReligionInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTipoAlumnoInterno(Connection conn, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT TIPOALUMNO_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY TIPOALUMNO_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("TIPOALUMNO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaTipoAlumnoInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTipoAlumnoInternoModalidad(Connection conn, String modalidades, String dormitorio) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		try{
			comando = "SELECT TIPOALUMNO_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+") "+
				" GROUP BY TIPOALUMNO_ID";
					
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("TIPOALUMNO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaTipoAlumnoInterno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public ArrayList<String> getListInternos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisInter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = "SELECT E.CODIGO_PERSONAL, "+
						"ENOC.ALUM_APELLIDO(E.CODIGO_PERSONAL) AS NOMBRE, "+
						"CASE E.SEXO WHEN 'M' THEN 'MASC.' ELSE 'FEM.' END AS SEXO, "+
						"ENOC.FACULTAD(E.CARRERA_ID) AS FACULTAD, "+
						"E.CARRERA_ID, "+
						"A.DORMITORIO "+
						"FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A "+ 
						"WHERE E.CARGA_ID IN "+
						"(SELECT CARGA_ID FROM ENOC.CARGA "+ 
						"WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) "+
						"AND E.CODIGO_PERSONAL = A.CODIGO_PERSONAL "+
						"AND E.RESIDENCIA_ID = 'I' "+
						"AND A.MODALIDAD_ID IN (1,4,5)"+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				lisInter.add(rs.getString("CODIGO_PERSONAL"));
				lisInter.add(rs.getString("NOMBRE"));
				lisInter.add(rs.getString("SEXO"));
				lisInter.add(rs.getString("FACULTAD"));
				lisInter.add(rs.getString("CARRERA_ID"));
				lisInter.add(rs.getString("DORMITORIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInter;
	}
	
	public ArrayList<String> getListInternosModalidad(Connection conn, String modalidades, String fechaIni, String fechaFin, String orden ) throws SQLException{
		
		ArrayList<String> lisInter			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		try{
			comando = " SELECT E.CODIGO_PERSONAL, "
					+ " ENOC.ALUM_APELLIDO(E.CODIGO_PERSONAL) AS NOMBRE, "
					+ " CASE E.SEXO WHEN 'M' THEN 'MASC.' ELSE 'FEM.' END AS SEXO, "
					+ " ENOC.FACULTAD(E.CARRERA_ID) AS FACULTAD, "
					+ " E.CARRERA_ID, "
					+ " A.DORMITORIO "
					+ " FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A " 
					+ " WHERE E.CARGA_ID IN "
					+ "(SELECT CARGA_ID FROM ENOC.CARGA "
					+ " WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) "
					+ " AND E.CODIGO_PERSONAL = A.CODIGO_PERSONAL "
					+ " AND E.RESIDENCIA_ID = 'I' "
					+ " AND E.MODALIDAD_ID IN ("+modalidades+")"
					+ "AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE ('"+fechaFin+"','DD/MM/YYYY') "+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				lisInter.add(rs.getString("CODIGO_PERSONAL"));
				lisInter.add(rs.getString("NOMBRE"));
				lisInter.add(rs.getString("SEXO"));
				lisInter.add(rs.getString("FACULTAD"));
				lisInter.add(rs.getString("CARRERA_ID"));
				lisInter.add(rs.getString("DORMITORIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListInternos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInter;
	}	
	
	public ArrayList<String> getListSupervisor(Connection conn, String sCodigoUsuario, String orden ) throws SQLException{
		
		ArrayList<String> lisSuper			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "Select "+
			"administrador, supervisor, accesos "+
 			"from ENOC.acceso where codigo_Personal = '"+sCodigoUsuario+"'"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisSuper.add(rs.getString("ADMINISTRADOR"));
				lisSuper.add(rs.getString("SUPERVISOR"));
				lisSuper.add(rs.getString("ACCESOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|getListSupervisor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSuper;
	}

	public ArrayList<String> getInternosGenero(Connection conn, String orden) throws SQLException{
	
	ArrayList<String> lisInter			= new ArrayList<String>();
	Statement st 			= conn.createStatement();
	ResultSet rs 			= null;
	String comando	= "";
	try{
		comando = "SELECT E.CODIGO_PERSONAL, " +
				"ENOC.ALUM_NOMBRE(E.CODIGO_PERSONAL) AS NOMBRE, " +
				"ENOC.NOMBRE_CARRERA(E.CARRERA_ID) AS CARRERA, " +
				"A.DORMITORIO, "+
				"CASE E.SEXO WHEN 'M' THEN 'MASC.' ELSE 'FEM.' END AS SEXO, "+
				"FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A " + 
				"WHERE E.CARGA_ID IN " +
				"(SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + 
				"AND E.CODIGO_PERSONAL = A.CODIGO_PERSONAL AND E.RESIDENCIA_ID = 'I' " +
				"AND A.MODALIDAD_ID IN (1,2,3,4) " +
				"ORDER BY SEXO, NOMBRE "; 
			
		rs = st.executeQuery(comando);			
		while (rs.next()){
			
			lisInter.add(rs.getString("CODIGO_PERSONAL"));
			lisInter.add(rs.getString("NOMBRE"));
			lisInter.add(rs.getString("CARRERA"));
			lisInter.add(rs.getString("DORMITORIO"));
			lisInter.add(rs.getString("SEXO"));			
		}
		
	}catch(Exception ex){
		System.out.println("Error - aca.residencia.InternosUtil|getInternosGenero|:"+ex);
	}finally{
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }
	}
	
	return lisInter;
}
	
	public static String getMexicanosUM(Connection conn, String dormitorio) throws SQLException{
			
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			String nacion		= "x";
			
			try{
				comando = "SELECT COUNT(NACIONALIDAD) NACIONALIDAD FROM ENOC.INSCRITOS " +
						"WHERE RESIDENCIA_ID = 'I' " +
						" AND NACIONALIDAD= '91'" +
						" AND MODALIDAD_ID IN (1,2,3,4)" +
						" AND DORMITORIO = '"+dormitorio+"'"; 
				rs = st.executeQuery(comando);
				if (rs.next()){
					nacion = rs.getString("NACIONALIDAD");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.vista.Inscritos|getMexicanosUM|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return nacion;
		}
	
	public static String getASDUM(Connection conn, String dormitorio) throws SQLException{
			
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			String asd		= "x";
			
			try{
				comando = "SELECT COUNT(RELIGION_ID) RELIGION FROM ENOC.INSCRITOS " +
						"WHERE RESIDENCIA_ID = 'I' " +
						" AND RELIGION_ID= '1'" +
						" AND MODALIDAD_ID IN (1,2,3,4)" +
						" AND DORMITORIO = '"+dormitorio+"'"; 
				rs = st.executeQuery(comando);
				if (rs.next()){
					asd = rs.getString("RELIGION");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.vista.Inscritos|getASDUM|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return asd;
		}
	
	public static String getTipoAlumUM(Connection conn, String tipo, String dormitorio) throws SQLException{
			
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			String 	tipoAlum	= "1";
			
			try{
				comando = "SELECT COUNT(I.CODIGO_PERSONAL)CODIGO " +
				"FROM ENOC.INSCRITOS I, ENOC.ALUM_ACADEMICO AC " + 
				"WHERE I.CODIGO_PERSONAL = AC.CODIGO_PERSONAL " +
				"AND I.RESIDENCIA_ID = 'I' AND I.DORMITORIO = '"+dormitorio+"'  " +
				"AND AC.TIPO_ALUMNO = '"+tipo+"'" +
				" AND I.MODALIDAD_ID IN (1,2,3,4)"; 
				rs = st.executeQuery(comando);
				if (rs.next()){
					tipoAlum = rs.getString("CODIGO");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.res.InternosUtil|getTipoAlumUM|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return tipoAlum;
	}	

}