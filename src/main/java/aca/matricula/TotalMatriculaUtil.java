
package aca.matricula;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class TotalMatriculaUtil{

	public int totalInscritos(Connection conn) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.INSCRITOS";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("total");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|totalInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public int totalInscritosPorCarga(Connection conn, String cargas, String modalidades) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.INSCRITOS WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("total");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|totalInscritosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public int totalInscritosPorCarga(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	= " SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS TOTAL FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("total");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|totalInscritosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public HashMap<String,String> totalInscritosCarga(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		HashMap<String,String> mapa = new HashMap<String,String>();
		String comando		= "";	
		
		try{			
			comando	= " SELECT A.CARGA_ID, A.NOMBRE_CARGA, "
					+ " (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B"
					+ "  WHERE B.CARGA_ID = A.CARGA_ID AND MODALIDAD_ID IN ("+modalidades+")"
					+ "  AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " ) AS NUM_ALUMNOS"
					+ " FROM ENOC.CARGA A"
					+ " WHERE CARGA_ID IN ("+cargas+") ORDER BY NUM_ALUMNOS DESC";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CARGA_ID"), rs.getString("NUM_ALUMNOS"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.totalInscritosCarga|totalInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}	
	
	public int totalCCobro(Connection conn, String cargas, String modalidades) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT COUNT(MATRICULA) AS NUM_COBROS FROM MATEO.FES_CCOBRO"
						+ " WHERE CARGA_ID IN ("+cargas+")"
						+ " AND INSCRITO = 'N'"
						+ " AND MATRICULA IN"
						+ "	(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID, 1, 6) IN ("+cargas+") AND TIPOCAL_ID = 'M')"
						+ " AND MODALIDAD_ID IN ("+modalidades+")";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cobros");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCCObro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public int totalCarga(Connection conn, String cargas, String modalidades) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT"+ 
				" COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_CARGAS" +
				" FROM ENOC.KRDX_CURSO_ACT" + 
				" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+")" +
				" AND CODIGO_PERSONAL NOT IN "+ 
			  		"(SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")) "+
			  	" AND TIPOCAL_ID = 'M'";
			
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("NUM_CARGAS");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public int totalInscritosFac(Connection conn, String facultadId) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	="SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL" +
					" FROM ENOC.INSCRITOS" +
					" WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"'";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("TOTAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|totalInscritosFac|:"+ex);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}						
		return total;
	}
	
	public int totalInscritosFacPorCarga(Connection conn, String facultadId, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	="SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL" +
					" FROM ENOC.INSCRITOS" +
					" WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"+
					" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("TOTAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|totalInscritosFacPorCarga|:"+ex);
		}
		finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}						
		return total;
	}
	
	public int totalCCobroFac(Connection conn, String cargas, String modalidades, String facultad) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	" SELECT COUNT(MATRICULA) AS NUM_COBROS FROM MATEO.FES_CCOBRO"
						+ " WHERE CARGA_ID IN ("+cargas+")"
						+ " AND FACULTAD_ID = '"+facultad+"'"
						+ " AND INSCRITO = 'N'"
						+ " AND MATRICULA IN"
						+ "	(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID, 1, 6) IN ("+cargas+") AND TIPOCAL_ID = 'M')"
						+ " AND MODALIDAD_ID IN ("+modalidades+")";
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("num_cobros");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCCobroFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public int totalCargaFac(Connection conn, String cargas, String modalidades, String facultad) throws SQLException{
		int total=0;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT"+ 
				" COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_CARGAS" +
				" FROM ENOC.KRDX_CURSO_ACT" + 
				" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+")" +
				" AND ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL)  = '"+facultad+"'"+
				" AND CODIGO_PERSONAL NOT IN "+ 
			  		"(SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN("+modalidades+") )"+
			  	" AND TIPOCAL_ID = 'M'";
			
			rs = st.executeQuery(comando);
			if (rs.next())
				total = rs.getInt("NUM_CARGAS");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.MatriculaUtil|totalCargaFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}	
	
	public int[] estadisticaPorCarga(Connection conn, String cargas, String modalidades) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 					ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 					ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 			ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID)) AS CREDITOS FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"; 
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("creditos");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public int[] estadisticaPorCarga(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{	
			comando	= " SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN"
					+ " FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 					ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 					ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 			ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID)) AS CREDITOS FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"; 
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("creditos");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public int[] estadistica(Connection conn) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{	
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN FROM ENOC.ESTADISTICA";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 					ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 					ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 			ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID)) AS CREDITOS FROM ENOC.ESTADISTICA"; 
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("creditos");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadistica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public int[] facultadEstadistica(Connection conn, String facultadId) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN FROM ENOC.ESTADISTICA WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"'";									
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 		ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 		ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID)) AS CREDITOS " +
					"FROM ENOC.ESTADISTICA WHERE ENOC.FACULTAD(CARRERA_ID)= '"+facultadId+"'";
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|facultadEstadistica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public int[] facultadEstadisticaPorCarga(Connection conn, String facultadId, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{
			comando	=	" SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN FROM ENOC.ESTADISTICA "
					  + " WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					  + " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";									
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 		ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 		ret[6]++; else ret[7]++;								
			}
			comando	=	" SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID)) AS CREDITOS " +
					    " FROM ENOC.ESTADISTICA WHERE ENOC.FACULTAD(CARRERA_ID)= '"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+") "+
					    " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|facultadEstadisticaPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs.close();
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public int[] facultadEstadisticaPorCarga(Connection conn, String facultadId, String cargas, String modalidades) throws SQLException{
		int ret[]= new int[9];
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		String comando	= "";
		try{
			comando	=	"SELECT DISTINCT(CODIGO_PERSONAL), RESIDENCIA_ID, SEXO, NACIONALIDAD, CLAS_FIN FROM ENOC.ESTADISTICA WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")";									
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("residencia_id").equals("I")) 	ret[0]++; else ret[1]++;
				if (rs.getString("sexo").equals("M")) 			ret[2]++; else ret[3]++;
				if (rs.getInt("nacionalidad")==91)		 		ret[4]++; else ret[5]++;
				if (rs.getString("clas_fin").equals("1")) 		ret[6]++; else ret[7]++;								
			}
			comando	=	"SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL,CARGA_ID, MODALIDAD_ID)) AS CREDITOS " +
					"FROM ENOC.ESTADISTICA WHERE ENOC.FACULTAD(CARRERA_ID)= '"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+") ";
			rs2 = st.executeQuery(comando);
			rs2.next();
			ret[8] = rs2.getInt("CREDITOS");
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|facultadEstadisticaPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs.close();
			try { st.close(); } catch (Exception ignore) { }
		}						
		return ret;
	}
	
	public ArrayList<String> estadisticaFac(Connection conn) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	=	"SELECT" +
					" ENOC.FACULTAD_NOMBRE(ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL)) AS NOMBRE_FACULTAD," +
					" ENOC.FACULTAD_TITULO(ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL)) AS TITULO_FACULTAD," +
					" ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL) AS FACULTAD_ID," +
					" ENOC.TOTALINSCRITOS_FAC(ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL)) AS INSCRITOS" +
					" FROM ENOC.INSCRITOS" +					
					" GROUP BY ENOC.ALUMNO_FAC_ID(CODIGO_PERSONAL)" +
					" ORDER BY 4 DESC";
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("NOMBRE_FACULTAD")+"~"+
											rs.getString("TITULO_FACULTAD")+"~"+
											rs.getString("FACULTAD_ID")+"~"+
											rs.getString("INSCRITOS");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}						
		return lisFac;
	}
	
	public ArrayList<String> estadisticaFacCargas(Connection conn, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		
		try{	
			comando	=	"SELECT" 
			  + " FACULTAD_ID, TITULO, NOMBRE_FACULTAD,"
			  + " (SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ESTADISTICA"
			  + "	WHERE ENOC.FACULTAD(CARRERA_ID) = ENOC.CAT_FACULTAD.FACULTAD_ID"
			  + "	AND CARGA_ID IN ("+cargas+") "
			  + "	AND MODALIDAD_ID IN ("+modalidades+")"
			  + "   AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"			  
			  + " ) AS INSCRITOS"
			  + " FROM ENOC.CAT_FACULTAD"
			  + " WHERE FACULTAD_ID IN (SELECT DISTINCT(ENOC.FACULTAD(CARRERA_ID)) FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+"))"
			  +" ORDER BY 4 DESC";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("NOMBRE_FACULTAD")+"~"+
								rs.getString("TITULO")+"~"+
								rs.getString("FACULTAD_ID")+"~"+
								rs.getString("INSCRITOS");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaFacCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}						
		return lisFac;
	}
	
	public ArrayList<String> estadisticaCarrera(Connection Conn, String facultadId) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	="SELECT " +
					" CARRERA_ID, " +
					" ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, " +
					" ENOC.NOMBRE_CARRERA(CARRERA_ID) AS NOMBRE, " +
					" COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL"+
					" FROM ENOC.INSCRITOS " +
					" WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' " +
					" GROUP BY CARRERA_ID " +
					" ORDER BY 4 DESC";					
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("carrera_id")+"~"+
											rs.getString("facultad")+"~"+
											rs.getString("nombre")+"~"+
											rs.getString("total");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}					
		return lisFac;
	}
	
	public ArrayList<String> estadisticaCarreraPorCarga(Connection Conn, String facultadId, String cargas, String modalidades, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<String> lisFac	= new ArrayList<String>();
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "", cadena="";
		try{	
			comando	="SELECT " +
					" CARRERA_ID, " +
					" ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, " +
					" ENOC.NOMBRE_CARRERA(CARRERA_ID) AS NOMBRE, " +
					" COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL"+
					" FROM ENOC.INSCRITOS " +
					" WHERE ENOC.FACULTAD(CARRERA_ID)='"+facultadId+"' AND CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")" +
					" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
					" GROUP BY CARRERA_ID " +
					" ORDER BY 4 DESC";					
			rs = st.executeQuery(comando);
			while (rs.next()){
					cadena = 	rs.getString("carrera_id")+"~"+
											rs.getString("facultad")+"~"+
											rs.getString("nombre")+"~"+
											rs.getString("total");
					lisFac.add(cadena);

			}
		}catch(Exception ex){
			System.out.println("Error - aca.matricula.TotalMatriculaUtil|estadisticaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}					
		return lisFac;
	}
}