// Clase para la tabla de Cursos Actuales
package aca.kardex;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

public class EvaluacionUtil{
		
	public ArrayList<KrdxAlumnoEval> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoEval> lisEvaluacion		= new ArrayList<KrdxAlumnoEval>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoEval evaluacion = new KrdxAlumnoEval();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}
	
	public TreeMap<String,KrdxAlumnoEval> getMapAll(Connection conn, String orden ) throws SQLException{
		
		TreeMap<String,KrdxAlumnoEval> mapEvaluacion	= new TreeMap<String,KrdxAlumnoEval>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoEval evaluacion = new KrdxAlumnoEval();
				evaluacion.mapeaReg(rs);
				llave = evaluacion.getCursoCargaId()+evaluacion.getCodigoPersonal()+evaluacion.getEvaluacionId();
				mapEvaluacion.put(llave, evaluacion );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEvaluacion;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,String> getMapAlumEval(Connection conn, String cursoCargaId ) throws SQLException{
		
		HashMap<String,String> mapEvaluacion	= new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, COALESCE(NOTA,0) AS NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				llave = rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID")+rs.getString("EVALUACION_ID");
				mapEvaluacion.put(llave, rs.getString("NOTA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getMapAlumEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEvaluacion;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,KrdxAlumnoEval> mapAlumEval(Connection conn, String cursoCargaId ) throws SQLException{
		
		HashMap<String,KrdxAlumnoEval> mapEvaluacion	= new HashMap<String,KrdxAlumnoEval>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, COALESCE(NOTA,0) AS NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				KrdxAlumnoEval evaluacion = new KrdxAlumnoEval();
				evaluacion.mapeaReg(rs);
				llave = evaluacion.getCodigoPersonal()+evaluacion.getCursoCargaId()+evaluacion.getEvaluacionId();					
				mapEvaluacion.put(llave, evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|mapAlumEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEvaluacion;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,String> mapCuentaEval(Connection conn, String cargaId ) throws SQLException{
		
		HashMap<String,String> mapEvaluacion	= new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, COALESCE(COUNT(DISTINCT(EVALUACION_ID)),0) AS TOTAL"
					+ " FROM ENOC.KRDX_ALUMNO_EVAL"
					+ " WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%'"
					+ " GROUP BY CURSO_CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapEvaluacion.put(rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|mapCuentaEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEvaluacion;
	}
	
	public ArrayList<KrdxAlumnoEval> getLista(Connection conn, String codigoPersonal, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoEval> lisEvaluacion	= new ArrayList<KrdxAlumnoEval>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
					"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					"AND CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoEval evaluacion = new KrdxAlumnoEval();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}
	
	public ArrayList<KrdxAlumnoEval> getListaSinNotas(Connection conn, String codigoPersonal, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxAlumnoEval> lisEvaluacion	= new ArrayList<KrdxAlumnoEval>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "select ev.curso_Carga_id,ev.evaluacion_id, "+
	    "(select nota from ENOC.KRDX_ALUMNO_EVAL " + 
	    " where curso_carga_id=ev.curso_carga_id" + 
	 	" and evaluacion_id=ev.evaluacion_id" +
		" and codigo_personal='"+codigoPersonal+"'" +
	    ") as nota "+
		"from ENOC.carga_grupo_evaluacion ev where ev.curso_carga_id='"+cursoCargaId+"' "+ orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxAlumnoEval evaluacion = new KrdxAlumnoEval();
				evaluacion.mapeaRegArrayListSinNotas(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getListaSinNotas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}

	
	public String getAlumnoEvaluacion(Connection conn, String cursoCargaId, String codigoPersonal, String evaluacionId ) throws SQLException{
				
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String evaluacion = "-";
		
		try{
			comando = "SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND EVALUACION_ID = TO_NUMBER('"+evaluacionId+"','999')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getString("NOTA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoEvaluacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	public String getAlumnoPuntosExtras(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{	
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String evaluacion = "0";
		try{
			comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
			"AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO = 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoPuntosExtras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return evaluacion;
	}	
	
	// ESTA FUNCION HACE REFERENCIA A LA VISTA ALUMNO_EVALUACION QUE FUCIONA CARGA_GRUPO_EVALUACION Y KRDX_ALUMNO_EVAL
	public String getAlumnoPuntos(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String evaluacion = "0";
		try{
			comando = "SELECT COALESCE(ROUND(SUM(CASE TIPO WHEN 'P' THEN NOTA ELSE (VALOR*NOTA/100) END),2),0) AS PUNTOS "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"'  AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnosPuntos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	public String getAlumnoMaximosPuntos(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String evaluacion = "0";
		try{
			comando = "SELECT COALESCE(SUM(VALOR* (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1)" +
					" FROM ENOC.ALUMNO_EFICIENCIA" +
					" WHERE CURSO_CARGA_ID =  '"+cursoCargaId+"' "+
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoMaximoPuntos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	
	public String getAlumnoPromedio(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		ResultSet rs3 		= null;
		String comando		= "";
		String dev = "";
		double evaluacion = 0;		
		try{		
			
			//Si la suma de las estrategias es mayor de 100 entonces devolvera 100, de lo contrario devuelve la suma.
			comando =	"SELECT ((SUM(CASE TIPO WHEN 'P' THEN (SELECT NOTA FROM ENOC.ALUMNO_EVALUACION B " +
											" WHERE B.CURSO_CARGA_ID = A.CURSO_CARGA_ID" +
											" AND A.EVALUACION_ID = B.EVALUACION_ID" +
											" AND B.CODIGO_PERSONAL = '"+codigoPersonal+"') ELSE " +
								" (VALOR*(SELECT NOTA FROM ENOC.ALUMNO_EVALUACION B" +
										" WHERE B.CURSO_CARGA_ID = A.CURSO_CARGA_ID" +
										" AND A.EVALUACION_ID = B.EVALUACION_ID" +
										" AND B.CODIGO_PERSONAL = '"+codigoPersonal+"')" +
							"/100) END )*100)/CASE trunc((SUM(VALOR)-1)/100) WHEN 0 THEN SUM(VALOR) ELSE 100 END) AS PROMEDIO" +
						" FROM ENOC.CARGA_GRUPO_EVALUACION A" + 
						" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
						" AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getDouble("PROMEDIO");
				double pextra = 0;
				comando = "SELECT SUM(NOTA) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO = 'E'";
				rs2 = st.executeQuery(comando);
				if (rs2.next()) pextra = rs2.getDouble("PEXTRA");				
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				comando = "SELECT round("+evaluacion+") from ENOC.alum_personal where codigo_personal='"+codigoPersonal+"'";
				rs3 = st.executeQuery(comando);
				if (rs3.next()) dev = rs3.getString(1);				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dev;
	}
	
	public String getAlumnoEficiencia(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		ResultSet rs3 		= null;
		String comando	= "";
		String dev = "";
		double evaluacion = 0;
		//System.out.println("Datos:"+cursoCargaId+":"+codigoPersonal);
		try{
			// SELECT ((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100)) AS PROMEDIO			
			comando =	"SELECT (COALESCE(" +
							"		SUM( CASE TIPO WHEN '%' THEN VALOR*NOTA/100 ELSE NOTA END),0) / " +
							"		(CASE WHEN COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) = 0 THEN 1"+
							" 		ELSE COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) END) * 100) AS PROMEDIO "+
					" FROM ENOC.ALUMNO_EFICIENCIA" +
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND TIPO IN ('%','P')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getDouble("PROMEDIO");
				double pextra = 0;				
				comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO = 'E'";
				rs2 = st.executeQuery(comando);				
				if (rs2.next()) pextra = rs2.getDouble("PEXTRA");
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL= '"+codigoPersonal+"'"; 
				rs3 = st.executeQuery(comando);
				if (rs3.next()) dev = rs3.getString(1);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoEficiencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dev;
	}
	
	public String getAlumnoNota(Connection Conn, String cursoCargaId, String codigoPersonal ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String dev = "";
		try{
			comando = "SELECT COALESCE(NOTA,0) AS NOTAS FROM ENOC.KRDX_CURSO_ACT	WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
			    	 "AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
			    if(rs.getInt(1)==0) dev="";
			    else dev=rs.getInt(1)+"";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoNota|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dev;
	}	
	
	public String getAlumnoPromedio(Connection Conn, String cursoCargaId, String codigoPersonal, String mes ) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		ResultSet rs2 		= null;
		ResultSet rs3 		= null;
		String comando	= "";
		String dev = "";
		double evaluacion = 0;
		
		try{
			comando = "SELECT ((SUM(CASE TIPO WHEN 'P' THEN NOTA ELSE (VALOR*NOTA/100) END )*100)/SUM(VALOR)) AS PROMEDIO "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+
				"AND TO_CHAR(fecha,'MM') = '" + mes+"' AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				evaluacion = rs.getDouble("PROMEDIO");				
				double pextra = 0;
				comando = "SELECT SUM(NOTA) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
				"AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND TIPO = 'E' AND TO_CHAR(fecha,'MM') = '" + mes+"'";
				rs2 = st.executeQuery(comando);
				if (rs2.next()) pextra = rs2.getDouble("PEXTRA");				
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				comando = "SELECT round("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL='"+codigoPersonal+"'"; 
				rs3 = st.executeQuery(comando);
				if (rs3.next()) dev = rs3.getString(1);				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getAlumnoPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dev;
	}
	
	public ArrayList<String> getActaMeses(Connection Conn, String cursoCargaId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		ArrayList<String> 	listor = new ArrayList<String>();
		
		try{
			comando = "SELECT DISTINCT TO_CHAR(FECHA,'MM') FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'  AND TIPO != 'E' order by 1";
			rs = st.executeQuery(comando);
			while (rs.next()){
				listor.add(rs.getString(1));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getActaMeses|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listor;
	}	
	public String getActaPuntosEvaluados(Connection Conn, String cursoCargaId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		float suma = 0;
		
		try{
			comando = "SELECT DISTINCT EVALUACION_ID, VALOR FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' AND TIPO != 'E'";
			rs = st.executeQuery(comando);
			
			while (rs.next()){				
				suma += Float.parseFloat(rs.getString(2));	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getActaPuntosEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return String.valueOf(suma);
	}
/*	
	public static void main(String args[]){
		try{
			Connection Conn = null;
			System.out.println( "Inicio");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1", "enoc", "caminacondios");
			String Suma= "";
			EvaluacionUtil eu = new EvaluacionUtil();  
			System.out.println( "Conexion");
			Suma = eu.getActaPuntosEvaluados(Conn, "05063A-0003");
			System.out.println( "Fin..."+Suma);
			Conn.commit();
			Conn.close();
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
*/
}