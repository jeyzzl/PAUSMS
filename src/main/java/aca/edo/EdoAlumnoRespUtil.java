/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Nio
 *
 */
public class EdoAlumnoRespUtil {
	
	public boolean insertReg(Connection conn, EdoAlumnoResp resp) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO_ALUMNORESP(EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA)" +
				" VALUES(TO_NUMBER(?, '99999'), TO_NUMBER(?, '99'), ?, ?, ?, ?)");
			
			ps.setString(1, resp.getEdoId());
			ps.setString(2, resp.getPreguntaId());
			ps.setString(3, resp.getCodigoPersonal());
			ps.setString(4, resp.getCursoCargaId());
			ps.setString(5, resp.getCodigoMaestro());
			ps.setString(6, resp.getRespuesta());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|insertReg|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EdoAlumnoResp resp) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_ALUMNORESP" + 
				" SET RESPUESTA = ?" +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?" +
				" AND CODIGO_MAESTRO = ?");
			
			ps.setString(1, resp.getRespuesta());
			ps.setString(2, resp.getEdoId());
			ps.setString(3, resp.getPreguntaId());			
			ps.setString(4, resp.getCodigoPersonal());
			ps.setString(5, resp.getCursoCargaId());
			ps.setString(6, resp.getCodigoMaestro());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String respuesta, String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_ALUMNORESP"+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?" +
				" AND CODIGO_MAESTRO = ?");
			
			ps.setString(1, respuesta);
			ps.setString(2, edoId);
			ps.setString(3, preguntaId);			
			ps.setString(4, codigoPersonal);
			ps.setString(5, cursoCargaId);
			ps.setString(6, codigoMaestro);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoAlumnoResp mapeaRegId(Connection con, String respuesta, String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) throws SQLException{
		EdoAlumnoResp resp = new EdoAlumnoResp();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA" +
					" FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?" +
					" AND CODIGO_MAESTRO = ?");
				
				ps.setString(1, respuesta);
				ps.setString(2, edoId);
				ps.setString(3, preguntaId);			
				ps.setString(4, codigoPersonal);
				ps.setString(5, cursoCargaId);
				ps.setString(6, codigoMaestro);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				resp.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return resp;
	}
	
	public boolean existeReg(Connection conn, String respuesta, String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?" +
				" AND CODIGO_MAESTRO = ?");
			
			ps.setString(1, respuesta);
			ps.setString(2, edoId);
			ps.setString(3, preguntaId);			
			ps.setString(4, codigoPersonal);
			ps.setString(5, cursoCargaId);
			ps.setString(6, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public static boolean yaContesto(Connection conn, String edoId, String codigoPersonal, String cursoCargaId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, edoId);	
			ps.setString(2, codigoPersonal);
			ps.setString(3, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|yaContesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public static String getPromedioMaestro(Connection conn, String edoId, String codigoMaestro) throws SQLException{		
		String promedio			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE((SUM(RESPUESTA)/COUNT(RESPUESTA)*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A" + 
					" WHERE A.EDO_ID = TO_NUMBER(?, '99999')" +
					" AND A.CODIGO_MAESTRO = ?" +
					" AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B" + 
										  " WHERE B.EDO_ID = A.EDO_ID" +
										  " AND B.TIPO = 'O')");
			
			ps.setString(1, edoId);	
			ps.setString(2, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	public static String getPromedioEvaluacion(Connection conn, String edoId) throws SQLException{
		String promedio			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A" + 
					" WHERE A.EDO_ID = TO_NUMBER(?, '99999')" +					
					" AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B" + 
										  " WHERE B.EDO_ID = A.EDO_ID" +
										  " AND B.TIPO = 'O')");			
			ps.setString(1, edoId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioEvaluacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	// CAST(AVG(RESPUESTA)*20 AS NUMERIC(10,2)) AS PROMEDIO
	public static String getPromedioMateria(Connection conn, String cursoCargaId, String edoId) throws SQLException{
		String promedio			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(CAST(AVG(TO_NUMBER(RESPUESTA,'99'))*20 AS NUMERIC(10,2)),0) AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A"
					+ " WHERE A.CURSO_CARGA_ID = ?"
					+ " AND EDO_ID = TO_NUMBER(?,'99')"
					+ " AND A.PREGUNTA_ID IN "
					+ "		(SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B WHERE B.EDO_ID = A.EDO_ID AND SECCION = 'B' AND B.TIPO = 'O')");
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	
	/**
	 * @author Etorres
	 * @return El numero de alumnos que faltan por contestar el insctrumento de evaluacion
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia
	 **/
	public static String getAlumFaltantesMateria(Connection conn, String cursoCargaId) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EDO_ALUMNORESP" + 
												" WHERE CURSO_CARGA_ID = ?)");
			
			ps.setString(1, cursoCargaId);			
			ps.setString(2, cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getAlumFaltantesMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}	
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que han evaluado la materia
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El cursoCargaId de la materia a buscar
	 **/
	public static String getNumAlumEval(Connection conn, String cursoCargaId) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getNumAlumEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que han evaluado al profesor
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId en el cual se buscaran los alumnos
	 * @param codigoMaestro El codigo del maestro para el cual se buscaran los alumnos
	 * */
	public static String getAlumEvalProf(Connection conn, String edoId, String codigoMaestro) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE EDO_ID = ?" +
									  " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
									  						 " WHERE CODIGO_PERSONAL = ?)");
			
			ps.setString(1, edoId);
			ps.setString(2, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getAlumEvalProf|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos correspondientes al profesor dado que faltan por contestar la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId de la evaluacion a buscar
	 * @param codigoMaestro El codigo del profesor del que se desea el resultado
	 * */
	public static String getAlumFaltantesProf(Connection conn, String edoId, String codigoMaestro) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A" + 
					" WHERE A.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO B" + 
											   " WHERE B.CODIGO_PERSONAL = ?" +
											   " AND B.CARGA_ID IN ("+EdoUtil.getCargasEdo(conn, edoId)+"))" +
					" AND A.CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EDO_ALUMNORESP" + 
												  " WHERE EDO_ID = ?" +
												  " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
												  						 " WHERE CODIGO_PERSONAL = ?" +
												  						 " AND CARGA_ID IN ("+EdoUtil.getCargasEdo(conn, edoId)+")))");
			
			ps.setString(1, codigoMaestro);
			ps.setString(2, edoId);
			ps.setString(3, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getAlumFaltantesProf|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El promedio de la pregunta dada del profesor dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la cual se desea el promedio de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el promedio
	 * @param codigoMaestro El codigo del maestro del cual se desea el promedio de la pregunta dada
	 * */
	public static String getPromedioPregunta(Connection conn, String edoId, String preguntaId, String codigoMaestro) throws SQLException{		
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String promedio			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99999')" +
					" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
										   " WHERE CODIGO_PERSONAL = ?)");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioPregunta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	/**
	 * @author Elifo
	 * @return El valor minimo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public static String getMinPregunta(Connection conn, String edoId, String preguntaId, String codigoMaestro) throws SQLException{		
		String minimo			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MIN(RESPUESTA), '--') AS MINIMO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
										   " WHERE CODIGO_PERSONAL = ?)");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				minimo = rs.getString("MINIMO");
			else
				minimo = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMinPregunta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return minimo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor maximo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public static String getMaxPregunta(Connection conn, String edoId, String preguntaId, String codigoMaestro) throws SQLException{		
		String maximo			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(RESPUESTA), '--') AS MAXIMO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
										   " WHERE CODIGO_PERSONAL = ?)");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, codigoMaestro);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			else
				maximo = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMaxPregunta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return maximo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor minimo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public static String getMinPreguntaMateria(Connection conn, String edoId, String preguntaId, String cursoCargaId) throws SQLException{		
		String minimo			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MIN(RESPUESTA), '--') AS MINIMO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				minimo = rs.getString("MINIMO");
			else
				minimo = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMinPreguntaMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return minimo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor maximo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public static String getMaxPreguntaMateria(Connection conn, String edoId, String preguntaId, String cursoCargaId) throws SQLException{		
		String maximo			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(RESPUESTA), '--') AS MAXIMO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			else
				maximo = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMaxPreguntaMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return maximo;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que han contestado la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId en el cual se buscaran los alumnos
	 * */
	public static String getAlumEvalEdo(Connection conn, String edoId) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE EDO_ID = ?");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getAlumEvalEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de encuestas/materia contestadas
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId para el cual se desea el resultado
	 * */
	public static String getNumEvalEdo(Connection conn, String edoId) throws SQLException{
		String numEval			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) AS NUM_EVAL FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE EDO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numEval = rs.getString("NUM_EVAL");
			else
				numEval = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getNumEvalEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numEval;
	}
	
	/**
	 * @author Elifo
	 * @return El promedio de la pregunta dada de la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la cual se desea el promedio de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el promedio
	 * */
	public static String getPromedioPregunta(Connection conn, String edoId, String preguntaId) throws SQLException{		
		String promedio			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')"+
					" AND RESPUESTA > 0");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioPregunta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de respuestas con un valor dado
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId para el cual se desea el resultado
	 * @param preguntaId El numero de pregunta par la cual se desea el resultado
	 * @param repuestaVal El valor de la respuesta que se busca
	 * */
	public static String getNumRespuestasPreg(Connection conn, String edoId, String preguntaId, String repuestaVal) throws SQLException{
		String numResp			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS NUM_RESPUESTAS FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND RESPUESTA = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, repuestaVal);
			
			rs = ps.executeQuery();
			if (rs.next())
				numResp = rs.getString("NUM_RESPUESTAS");
			else
				numResp = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getNumRespuestasPreg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numResp;
	}
	
	/**
	 * @author Elifo
	 * @return La cantidad de cursos que lleva el alumno en las cargas habiles para la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se desea buscar
	 * @param codigoPersonal El codigo del alumno del que se desean saber el numero de cursos
	 * */
	public static String getNumCursos(Connection conn, String edoId, String codigoPersonal) throws SQLException{
		String numMat			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS NUM_MAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND SUBSTR(CURSO_CARGA_ID, 1, 6) IN ("+EdoUtil.getCargasEdo(conn, edoId)+")");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getString("NUM_MAT");
			else
				numMat = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getNumCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numMat;
	}
	
	/**
	 * @author Elifo
	 * @return La cantidad de cursos que ha evaluado(contestado) el alumno en la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se desea buscar
	 * @param codigoPersonal El codigo del alumno del que se desean saber el numero de cursos evaluados
	 * */
	public static String getNumCursosEvaluados(Connection conn, String edoId, String codigoPersonal) throws SQLException{
		String numMat			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(DISTINCT(CURSO_CARGA_ID)) AS NUM_MAT FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND EDO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getString("NUM_MAT");
			else
				numMat = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getNumCursosEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numMat;
	}
	
	public static String getPromedioPreguntaPorMateria(Connection conn, String edoId, String preguntaId, String CursoCargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String promedio			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR( COALESCE(SUM(RESPUESTA*20),0) / COALESCE(COUNT(RESPUESTA),1), '999.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = TO_NUMBER(?,'99999')"
					+ " AND PREGUNTA_ID = TO_NUMBER(?, '99999')"
					+ " AND CURSO_CARGA_ID = ?"
					+ " AND RESPUESTA IN('0','1','2','3','4','5')");
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);
			ps.setString(3, CursoCargaId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedioPreguntaPorMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
	
	/**
	 * @author Etorres
	 * @return El numero de alumnos que han evaluado la materia
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia para el cual se buscaron los alumnos
	 * */
	public static String getAlumEvalMateria(Connection conn, String cursoCargaId) throws SQLException{
		String numAlum			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE CURSO_CARGA_ID = ?");			
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getString("NUM_ALUM");
			else
				numAlum = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getAlumEvalMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return numAlum;
	}	
	
	/**
	 * @author Etorres
	 * @return Regresa el número de EdoId con que se evaluo la materia 
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia
	 * */
	public static String getEdoId(Connection conn, String cursoCargaId) throws SQLException{
		String edoId			= "0";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT EDO_ID FROM ENOC.EDO_ALUMNORESP" + 
									  " WHERE CURSO_CARGA_ID = ?");			
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				edoId = rs.getString("EDO_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getEdoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return edoId;
	}
	
	public static String nombreCurso(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre	= "";
				
		try{
			ps = conn.prepareStatement("SELECT GRUPO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?");			

			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("GRUPO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|nombreCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<EdoAlumnoResp> getListRespuestas(Connection conn, String cursoCargaId, String preguntaId, String orden ) throws SQLException{
		
		ArrayList<EdoAlumnoResp> lisR	= new ArrayList<EdoAlumnoResp>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA " +
					"FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' AND PREGUNTA_ID = "+preguntaId+" "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoAlumnoResp eap = new EdoAlumnoResp();
				eap.mapeaReg(rs);
				lisR.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getListRespuestas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisR;
	}
	
	public TreeMap<String,String> getMapCursos(Connection conn, String evaluacionId, String cargas ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+			
			" ("+
			"   SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO B"+ 
			"   WHERE B.CODIGO_PERSONAL = CA.CODIGO_PERSONAL"+
			"   AND B.CARGA_ID IN ("+cargas+")"+
			" ) AS NUMCURSOS"+											  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				treeMaestro.put( rs.getString("CODIGO_PERSONAL"), rs.getString("NUMCURSOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeMaestro;
	}
	
	public ArrayList<String> getCursos(Connection conn, String cargas, String evaluacionId, String orden ) throws SQLException{
		
		ArrayList<String> list	= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT"+
			" CODIGO_PERSONAL, CURSO_CARGA_ID "+											  		
			" FROM ENOC.CARGA_GRUPO "+ 
			" WHERE CARGA_ID IN ("+cargas+") AND CODIGO_PERSONAL IN (SELECT CODIGO_MAESTRO FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = "+evaluacionId+") "+orden;			
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				list.add(rs.getString("CODIGO_PERSONAL")+"@"+rs.getString("CURSO_CARGA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public TreeMap<String,String> getMapAlumnos(Connection conn, String evaluacionId, String cargas ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+			
			" ("+
			"   SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A"+ 
			"   WHERE CURSO_CARGA_ID IN "+
			"   (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO B WHERE B.CODIGO_PERSONAL = CA.CODIGO_PERSONAL"+ 
			"		AND B.CARGA_ID IN ("+cargas+") )"+
			" ) AS TOTALUM"+								  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CODIGO_PERSONAL"), rs.getString("TOTALUM"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapAlumnosMateria(Connection conn, String evaluacionId, String cargas ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CURSO_CARGA_ID) AS CURSO_CARGA_ID,"+			
			" ("+
			"   SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A"+ 
			"   WHERE CURSO_CARGA_ID = CA.CURSO_CARGA_ID "+
			" ) AS TOTALUM"+								  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CURSO_CARGA_ID"), rs.getString("TOTALUM"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapEvaluados(Connection conn, String evaluacionId, String cargas ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS CODIGO_PERSONAL,"+			
			" ("+
			"   SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) FROM ENOC.EDO_ALUMNORESP"+ 
			"   WHERE EDO_ID = TO_NUMBER('"+evaluacionId+"','99999') " +
			"	AND CODIGO_MAESTRO = CA.CODIGO_PERSONAL "+
			" ) AS NUMEVAL "+											  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CODIGO_PERSONAL"), rs.getString("NUMEVAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeMaestro;
	}	
	
	public TreeMap<String,String> getMapEvaluadosMaterial(Connection conn, String evaluacionId, String cargas ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CURSO_CARGA_ID) AS CURSO_CARGA_ID,"+			
			" ("+
			"   SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) FROM ENOC.EDO_ALUMNORESP"+ 
			"   WHERE EDO_ID = TO_NUMBER('"+evaluacionId+"','99999') " +
			"	AND CURSO_CARGA_ID = CA.CURSO_CARGA_ID "+
			" ) AS NUMEVAL "+											  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CURSO_CARGA_ID"), rs.getString("NUMEVAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapPromedioMateria(Connection conn, String evaluacionId ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;		
		try{
			String comando = "SELECT CURSO_CARGA_ID, COALESCE(CAST(AVG(TO_NUMBER(RESPUESTA,'99'))*20 AS NUMERIC(10,2)),0) AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A"
					+ " WHERE EDO_ID = TO_NUMBER('"+evaluacionId+"','99999')"
					+ " AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B WHERE B.EDO_ID = A.EDO_ID AND SECCION = 'B' AND B.TIPO = 'O')"
					+ " GROUP BY A.CURSO_CARGA_ID";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CURSO_CARGA_ID"), rs.getString("PROMEDIO"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapPromedioMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapPromedio(Connection conn, String evaluacionId ) throws SQLException{
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;		
		try{
			String comando = "SELECT CODIGO_MAESTRO, COALESCE(CAST(AVG(TO_NUMBER(RESPUESTA,'99'))*20 AS NUMERIC(10,2)),0) AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A"
				+ " WHERE EDO_ID = TO_NUMBER('"+evaluacionId+"','99999')"
				+ " AND A.PREGUNTA_ID IN"
				+ " (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B WHERE B.EDO_ID = A.EDO_ID AND SECCION = 'B' AND B.TIPO = 'O') "
				+ " GROUP BY A.CODIGO_MAESTRO";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				treeMaestro.put( rs.getString("CODIGO_MAESTRO"), rs.getString("PROMEDIO"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return treeMaestro;
	}
	
	public HashMap<String,String> getPromedio(Connection conn, String cargas) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
		try{
			comando = " SELECT CODIGO_MAESTRO, PREGUNTA_ID, TRIM(TO_CHAR( SUM(COALESCE(TO_NUMBER(RESPUESTA),0))/COUNT(PREGUNTA_ID) ,'99.99')) AS PROMEDIO "+
					  " FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+")) "+
					  " AND PREGUNTA_ID IN (SELECT PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = ENOC.EDO_ALUMNORESP.EDO_ID AND TIPO='O') "+
					  " GROUP BY CODIGO_MAESTRO, PREGUNTA_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){					
				mapa.put(rs.getString("CODIGO_MAESTRO")+rs.getString("PREGUNTA_ID"), rs.getString("PROMEDIO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getPromedio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}