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
public class EdoAlumnoPregUtil {
	
	public boolean insertReg(Connection conn, EdoAlumnoPreg edo) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.EDO_ALUMNOPREG(PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?,?, TO_NUMBER(?, '99'),?)");
			
			ps.setString(1, edo.getPreguntaId());
			ps.setString(2, edo.getEdoId());			
			ps.setString(3, edo.getPregunta());
			ps.setString(4, edo.getTipo());
			ps.setString(5, edo.getOrden());
			ps.setString(6, edo.getAreaId());
			ps.setString(7, edo.getSeccion());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EdoAlumnoPreg preg) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_ALUMNOPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?," +
				" AREA_ID = ?,"+
				" SECCION = ?"+
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			
			ps.setString(1, preg.getPregunta());	
			ps.setString(2, preg.getTipo());
			ps.setString(3, preg.getOrden());
			ps.setString(4, preg.getAreaId());
			ps.setString(5, preg.getSeccion());
			ps.setString(6, preg.getPreguntaId());
			ps.setString(7, preg.getEdoId());
			
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String preguntaId, String edoId) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_ALUMNOPREG "+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EdoAlumnoPreg mapeaRegId(Connection con, String preguntaId, String edoId) throws SQLException{
		EdoAlumnoPreg preg = new EdoAlumnoPreg();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID" +
					" FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, preguntaId);
			ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				preg.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return preg;
	}
	
	public boolean existeReg(Connection conn, String preguntaId, String edoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')");
		
		ps.setString(1, preguntaId);
		ps.setString(2, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String edoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return maximo;
	}
	
	public static int getNumPreguntas(Connection conn, String edoId, String tipo) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int total				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(PREGUNTA_ID),0) AS TOTAL FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = TO_NUMBER(?, '99999') AND TIPO = ?");
			
			ps.setString(1, edoId);
			ps.setString(2, tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getInt("TOTAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|getNumPreguntas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return total;
	}
	
	public ArrayList<EdoAlumnoPreg> getListEdo(Connection conn, String edoId, String orden ) throws SQLException{
		
		ArrayList<EdoAlumnoPreg> lisEAP	= new ArrayList<EdoAlumnoPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION" +
					" FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE EDO_ID = "+edoId+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoAlumnoPreg eap = new EdoAlumnoPreg();
				eap.mapeaReg(rs);
				lisEAP.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|getListEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEAP;
	}
	
	public ArrayList<EdoAlumnoPreg> getListComentarios(Connection conn, String cursoCargaId, String tipo, String orden ) throws SQLException{
		
		ArrayList<EdoAlumnoPreg> lisEAP	= new ArrayList<EdoAlumnoPreg>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION" +
					" FROM ENOC.EDO_ALUMNOPREG " + 
					" WHERE EDO_ID = (SELECT DISTINCT(EDO_ID) FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = '"+cursoCargaId+"') " + 
					" AND TIPO = '"+tipo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoAlumnoPreg eap = new EdoAlumnoPreg();
				eap.mapeaReg(rs);
				lisEAP.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|getListComentarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEAP;
	}
	
	public ArrayList<EdoAlumnoResp> getListFortalezas(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<EdoAlumnoResp> lisF	= new ArrayList<EdoAlumnoResp>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
		
		try{
			comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA" +
					" FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = "+cursoCargaId+" AND PREGUNTA_ID = '19' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				EdoAlumnoResp eap = new EdoAlumnoResp();
				eap.mapeaReg(rs);
				lisF.add(eap);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoPregUtil|getListFortalezas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisF;
	}
}