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

/**
 * @author Elifo
 *
 */
public class EdoUtil {
	
	public boolean insertReg(Connection conn, Edo edo) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO(EDO_ID, NOMBRE, F_INICIO, F_FINAL, PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO)" +
				" VALUES(TO_NUMBER(?, '99999'), ?," +
				" TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?) ");
			
			ps.setString(1, edo.getEdoId());
			ps.setString(2, edo.getNombre());			
			ps.setString(3, edo.getFInicio());
			ps.setString(4, edo.getFFinal());
			ps.setString(5, edo.getPeriodoId());
			ps.setString(6, edo.getTipo());
			ps.setString(7, edo.getModalidad());
			ps.setString(8, edo.getEncabezado());
			ps.setString(9, edo.getTipoEncuesta());
			ps.setString(10, edo.getCargas());
			ps.setString(11, edo.getExcepto());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, Edo edo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO" + 
				" SET NOMBRE = ?," +
				" F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" F_FINAL = TO_DATE(?, 'DD/MM/YYYY')," +
				" PERIODO_ID = ?," +
				" TIPO = ?," +
				" MODALIDAD = ?, TIPO_ENCUESTA = ?,"+
				" ENCABEZADO = ?, CARGAS = ?, EXCEPTO = ? " +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')");
					
			ps.setString(1, edo.getNombre());			
			ps.setString(2, edo.getFInicio());
			ps.setString(3, edo.getFFinal());
			ps.setString(4, edo.getPeriodoId());
			ps.setString(5, edo.getTipo());
			ps.setString(6, edo.getModalidad());			
			ps.setString(7, edo.getTipoEncuesta());
			ps.setString(8, edo.getEncabezado());
			ps.setString(9, edo.getCargas());
			ps.setString(10, edo.getExcepto());
			ps.setString(11, edo.getEdoId());		
			
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String edoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO"+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public Edo mapeaRegId(Connection con, String edoId) throws SQLException{
		
		Edo edo = new Edo();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EDO_ID, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO " +
					" FROM ENOC.EDO" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				edo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return edo;
	}
	
	public boolean existeReg(Connection conn, String edoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(EDO_ID)+1,1) AS MAXIMO FROM ENOC.EDO"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public boolean edoActivo(Connection conn, String tipo) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT EDO_ID, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO " +
					" FROM ENOC.EDO" + 
					" WHERE TIPO = ?" +
					" AND now() BETWEEN F_INICIO AND F_FINAL");
			
			ps.setString(1, tipo);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|edoActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return ok;
	}
	
	public static String getEdoActual(Connection conn, String tipo, String modalidad) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String edoId			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT EDO_ID FROM ENOC.EDO WHERE TIPO = ? AND MODALIDAD LIKE '%-'||?||'-%' AND now() BETWEEN F_INICIO AND F_FINAL");
			
			ps.setString(1, tipo);
			ps.setString(2, modalidad);
			
			rs = ps.executeQuery();
			if (rs.next()){
				edoId = rs.getString("EDO_ID");
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getEdoActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return edoId;
	}
	
	public static String getEdoId(Connection conn, String tipo) throws SQLException{		
		String edo			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT EDO_ID AS EDO, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO" +
					" FROM ENOC.EDO" + 
					" WHERE TIPO = ?");
			
			ps.setString(1, tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				edo = rs.getString("EDO");
			else
				edo = "";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getEdoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return edo;
	}
	
	public static String getEdoNombre(Connection conn, String edoId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.EDO WHERE EDO_ID = TO_NUMBER(?,'99999')");			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getEdoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return nombre;
	}
	
	/**
	 * @author etorres
	 * @return Las cargas que su rango de fechas concuerda con la fecha en que se aplico la evaluacion
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion sobre la cual se hara la busqueda
	 * */
	public static String getCargasEdo(Connection conn, String edoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String cargas			= "";
		
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA" + 
					" WHERE (SELECT F_INICIO FROM ENOC.EDO WHERE EDO_ID = TO_NUMBER(?, '99')) BETWEEN F_INICIO AND F_FINAL"); 
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				if(!cargas.equals(""))
					cargas+=",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getCargasEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return cargas;
	}
	
	public static String getCargasEvaluadas(Connection conn, String edoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String cargas			= "";
		
		try{
			ps = conn.prepareStatement("SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO" + 
					" WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = ?)"); 
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				if(!cargas.equals(""))
					cargas+=",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getCargasEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return cargas;
	}
	
	//SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = 11);
	
	/**
	 * @author etorres
	 * @return La cantidad de cursos que estaba impartiendo un profesor cuando se activo la evaluacion
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se basa la busqueda
	 * @param codigoMaestro El codigo del profesor del que se desea saber la cantidad de cursos impartidos
	 * */
	public static String getCursosProf(Connection conn, String edoId, String codigoMaestro) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String numCursos		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUM_CURSOS FROM ENOC.CARGA_GRUPO" + 
										" WHERE CODIGO_PERSONAL = ?" +
										" AND CARGA_ID IN ("+EdoUtil.getCargasEdo(conn, edoId)+")");
			
			ps.setString(1, codigoMaestro);
			
			rs = ps.executeQuery();
			while(rs.next()){
				numCursos = rs.getString("NUM_CURSOS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getCursosProf|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return numCursos;
	}	
	
	
	public ArrayList<Edo> getListTipo(Connection conn, String tipo, String orden ) throws SQLException{
		
		ArrayList<Edo> lisEdo		= new ArrayList<Edo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT EDO_ID, NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO "+
					" FROM ENOC.EDO" + 
					" WHERE TIPO = '"+tipo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Edo edo = new Edo();
				edo.mapeaReg(rs);
				lisEdo.add(edo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdo;
	}
	
	public ArrayList<Edo> getTipo(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Edo> lisEdo		= new ArrayList<Edo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	= "";
		
		try{
			comando = "SELECT EDO_ID, NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO "+
					" FROM ENOC.EDO" + 
					" WHERE TIPO != 'E' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Edo edo = new Edo();
				edo.mapeaReg(rs);
				lisEdo.add(edo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdo;
	}
	
	public ArrayList<Edo> getListPeriodo(Connection conn, String periodoId, String orden ) throws SQLException{
		
		ArrayList<Edo> lisEdo		= new ArrayList<Edo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT EDO_ID, NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO "+
					" FROM ENOC.EDO" + 
					" WHERE PERIODO_ID = '"+periodoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Edo edo = new Edo();
				edo.mapeaReg(rs);
				lisEdo.add(edo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEdo;
	}
}