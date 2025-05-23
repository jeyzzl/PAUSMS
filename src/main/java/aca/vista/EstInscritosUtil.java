// Clase para la vista EstInscritos
package  aca.vista;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EstInscritosUtil{
	
	public ArrayList<EstInscritos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<EstInscritos> lisEstInscritos	= new ArrayList<EstInscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE,"+
						"EDAD, SEXO, RESIDENCIA_ID, NOMBRE_RELIGION, "+
						"TIPO, CARGA_ID, MODALIDAD, PLAN_ID " +
						"FROM ENOC.ESTINSCRITOS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EstInscritos estInscritos = new EstInscritos();
				estInscritos.mapeaReg(rs);
				lisEstInscritos.add(estInscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstInscritos;
	}
	
	public ArrayList<EstInscritos> getListPrimerIngreso(Connection conn, String orden ) throws SQLException{
		
		ArrayList<EstInscritos> lisEstInscritos	= new ArrayList<EstInscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+ 
				"E.NOMBRE,EDAD, E.SEXO, E.RESIDENCIA_ID, E.NOMBRE_RELIGION,E.TIPO, " +
				"E.CARGA_ID, E.MODALIDAD, E.PLAN_ID "+
				"FROM ENOC.ESTINSCRITOS E " +
				"WHERE E.CODIGO_PERSONAL NOT IN" +
					"(SELECT KA.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT KA " + 
					"WHERE KA.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KA.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KA.TIPOCAL_ID NOT IN('I','M','3')) " +
				"AND E.CODIGO_PERSONAL NOT IN "+
					"(SELECT KI.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP KI " + 
					"WHERE KI.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KI.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KI.TIPOCAL_ID NOT IN('I','M')) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EstInscritos estInscritos = new EstInscritos();
				estInscritos.mapeaReg(rs);
				lisEstInscritos.add(estInscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritosUtil|getListPrimerIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstInscritos;
	}
	
	public ArrayList<EstInscritos> getListPrimerIngresoCargaModalidad(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		
		ArrayList<EstInscritos> lisEstInscritos	= new ArrayList<EstInscritos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+ 
				"E.NOMBRE,EDAD, E.SEXO, E.RESIDENCIA_ID, E.NOMBRE_RELIGION,E.TIPO, " +
				"E.CARGA_ID, E.MODALIDAD, E.PLAN_ID "+
				"FROM ENOC.ESTINSCRITOS E " +
				"WHERE E.CARGA_ID IN ("+cargas+") AND E.MODALIDAD IN ("+modalidades+") AND E.CODIGO_PERSONAL NOT IN" +
					"(SELECT KA.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT KA " + 
					"WHERE KA.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KA.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KA.TIPOCAL_ID NOT IN('I','M','3')) " +
				"AND E.CODIGO_PERSONAL NOT IN "+
					"(SELECT KI.CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_IMP KI " + 
					"WHERE KI.CODIGO_PERSONAL = E.CODIGO_PERSONAL " +
					"AND SUBSTR(KI.CURSO_ID,1,8)= E.PLAN_ID " +
					"AND KI.TIPOCAL_ID NOT IN('I','M')) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EstInscritos estInscritos = new EstInscritos();
				estInscritos.mapeaReg(rs);
				lisEstInscritos.add(estInscritos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritosUtil|getListPrimerIngresoCargaModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstInscritos;
	}
	
	public ArrayList<InscritoVO> getInscritos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<InscritoVO> inscritos	= new ArrayList<InscritoVO>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO, "+
			"E.PLAN_ID, "+
			"ENOC.ALUM_APELLIDO(E.CODIGO_PERSONAL) AS NOMBRE, "+
			"TO_CHAR(E.F_NACIMIENTO,'DD-MM-YYYY') AS FECHA, "+
			"ENOC.ALUMNO_MODALIDAD(E.CODIGO_PERSONAL) AS MODALIDAD, "+
			"ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(E.CARRERA_ID)) AS FACULTAD, "+
			"ENOC.NOMBRE_CARRERA(E.CARRERA_ID) AS CARRERA, "+
			"CASE E.RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA, "+
			"CASE E.SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO, " +
			"(SELECT GRADO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = E.CODIGO_PERSONAL AND PLAN_ID = E.PLAN_ID) AS GRADO, " + 
			"(SELECT CICLO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = E.CODIGO_PERSONAL AND PLAN_ID = E.PLAN_ID) AS SEMESTRE "+ 
			"FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A "+ 
			"WHERE CARGA_ID IN "+
				"(SELECT CARGA_ID FROM ENOC.CARGA "+ 
				"WHERE NOW() BETWEEN F_INICIO AND F_FINAL) "+
			"AND E.CODIGO_PERSONAL=A.CODIGO_PERSONAL " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				InscritoVO inscrito = new InscritoVO();
				inscrito.setCodigoPersonal(rs.getString(1));
				inscrito.setPlanId(rs.getString(2));
				inscrito.setNombre(rs.getString(3));
				inscrito.setFechaDeNac(rs.getString(4));
				inscrito.setModalidad(rs.getString(5));
				inscrito.setFacultad(rs.getString(6));
				inscrito.setCarrera(rs.getString(7));
				inscrito.setResidencia(rs.getString(8));
				inscrito.setSexo(rs.getString(9));
				inscrito.setGrado(rs.getString(10));
				inscrito.setSemestre(rs.getString(11));
				inscritos.add(inscrito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritosUtil|getInscritos|:"+ex);
		}
		finally{
		    try { rs.close(); } catch (Exception ignore) { }
		    try { st.close(); } catch (Exception ignore) { }
		}
		
		return inscritos;
	}
	
	public ArrayList<ArrayList<String>> getInscritosRemediales(Connection conn, String orden) throws SQLException{
		ArrayList<ArrayList<String>> inscritos	= new ArrayList<ArrayList<String>>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "	SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO, E.PLAN_ID," +
					"	(SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE FROM ENOC.ALUM_PERSONAL" +
					"		WHERE CODIGO_PERSONAL = E.CODIGO_PERSONAL) AS NOMBRE," +
					"	ENOC.FACULTAD_NOMBRE((SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = E.CARRERA_ID)) AS FACULTAD," +
					"	(SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = E.CARRERA_ID) AS CARRERA," +
					"	(SELECT GRADO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = E.CODIGO_PERSONAL AND PLAN_ID = E.PLAN_ID) AS GRADO," +
					"	(SELECT CICLO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = E.CODIGO_PERSONAL AND PLAN_ID = E.PLAN_ID) AS SEMESTRE" +
					"	FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A" +
					"	WHERE CARGA_ID IN" +
					"		(SELECT CARGA_ID FROM ENOC.CARGA" +
					"			WHERE TO_DATE(now(), 'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL)" +
					"			AND E.CODIGO_PERSONAL=A.CODIGO_PERSONAL AND ES_PLAN_REMEDIAL(PLAN_ID)='S' " + orden;
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("CODIGO"));
				arr.add(rs.getString("PLAN_ID"));
				arr.add(rs.getString("NOMBRE"));
				arr.add(rs.getString("FACULTAD"));
				arr.add(rs.getString("CARRERA"));
				arr.add(rs.getString("GRADO"));
				arr.add(rs.getString("SEMESTRE"));
				inscritos.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInscritosUtil|getInscritosRemediales|:"+ex);
		}
		finally{
		    try { rs.close(); } catch (Exception ignore) { }
		    try { st.close(); } catch (Exception ignore) { }
		}
		
		return inscritos;
	}
	
	public static HashMap<String,String> getAlumIngreso(Connection conn, String cargas, String modalidades) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL, " +
					" TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, " +
					" ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) AS INGRESO_UM, " +
					" ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) AS INGRESO_PLAN  " +
					" FROM ENOC.ESTINSCRITOS" +
					" WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD IN ("+modalidades+") "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("F_INSCRIPCION")+"%"+rs.getString("INGRESO_UM")+"%"+rs.getString("INGRESO_PLAN") );
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstInscritosUtil|getAlumIngreso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	
	/*
	public static void main(String args[]){
	 try{
	 	Connection Conn = null;
	 
	 	Class.forName("oracle.jdbc.driver.OracleDriver");
	 	Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
	 
	 	Statement st 		= Conn.createStatement();
	 	ResultSet rs 		= null;
	 	String comando	= "";
	 	String orden 		= "1";
	 
	 	comando = "SELECT CODIGO_PERSONAL, NOMBRE,"+
						"EDAD, SEXO, RESIDENCIA_ID, NOMBRE_RELIGION, "+
						"TIPO, CARGA_ID, MODALIDAD, PLAN_ID " +
						"FROM ENOC.ESTINSCRITOS ORDER BY  "+ orden;
	 
	 	rs = st.executeQuery(comando);
	 	while (rs.next()){
	 			System.out.println(rs.getString("Codigo_personal")+" : "+rs.getString("Nombre"));
	 	}			
	 
	 	try { rs.close(); } catch (Exception ignore) { }
	 	try { st.close(); } catch (Exception ignore) { }
	 
	 	Conn.commit();
	 	Conn.close();
	 }
	 catch(Exception e){
	 	System.out.println(e);
	 }
	}*/
}