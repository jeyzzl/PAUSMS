package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CargaGrupoAsistenciaUtil {
	
	public boolean insertReg(Connection conn, CargaGrupoAsistencia asistencia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_ASISTENCIA (CURSO_CARGA_ID,FOLIO,CODIGO_PERSONAL,CURSO_ID,ESTADO) "+
				"VALUES( ?, TO_NUMBER(?,'999'), ?, ? ,?) ");
			ps.setString(1, asistencia.getCargaGrupoId()); 
			ps.setString(2, asistencia.getFolio());
			ps.setString(3, asistencia.getCodigoPersonal());
			ps.setString(4, asistencia.getCursoId());
			ps.setString(5, asistencia.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoAsistencia asistencia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_ASISTENCIA "+ 
				" SET CURSO_ID = ?, ESTADO = ?"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')" +
				" AND CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, asistencia.getCursoId());
			ps.setString(2, asistencia.getEstado());
			ps.setString(3, asistencia.getCargaGrupoId());
			ps.setString(4, asistencia.getFolio());
			ps.setString(5, asistencia.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaGrupoId, String folio, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_PROGRAMACION"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')"
					+ " AND CODIGO_PERSONAL = ?");
			ps.setString(1, cursoCargaGrupoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean deleteRegistros(Connection conn, String cursoCargaId, String folio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);			
			
			if (ps.executeUpdate()>= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|deleteRegistros|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoAsistencia mapeaRegId( Connection conn, String cursoCargaGrupoId, String folio, String codigoPersonal) throws SQLException{
		
		CargaGrupoAsistencia asistencia = new CargaGrupoAsistencia();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO FROM ENOC.CARGA_GRUPO_ASISTENCIA" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?"); 
			ps.setString(1, cursoCargaGrupoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				asistencia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return asistencia;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaGrupoId, String folio, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_ASISTENCIA WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?"); 
			ps.setString(1, cursoCargaGrupoId);
			ps.setString(2, folio);
			ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaGrupoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String maximo 			= "1";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_ASISTENCIA WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1,cursoCargaGrupoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String numRegAsistencia(Connection conn, String cursoCargaId, String folio) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String total 			= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1,cursoCargaId);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getString("TOTAL");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistencia|numRegAsistencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}

	public ArrayList<CargaGrupoAsistencia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoAsistencia> lisAsistencia	= new ArrayList<CargaGrupoAsistencia>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO"+
					" FROM ENOC.CARGA_GRUPO_ASISTENCIA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoAsistencia actividad = new CargaGrupoAsistencia();
				actividad.mapeaReg(rs);
				lisAsistencia.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistenciaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAsistencia;
	}
	
	/* Map para verificar la asistencia */
	public static HashMap<String, String> mapAsistencia(Connection conn, String cursoCargaId, String folio) throws SQLException{
		HashMap<String, String> mapaAsistencia = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ESTADO FROM ENOC.CARGA_GRUPO_ASISTENCIA" +
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' AND FOLIO = TO_NUMBER('"+folio+"')";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaAsistencia.put(rs.getString("CODIGO_PERSONAL"), rs.getString("ESTADO"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoAsistenciaUtil|mapAsistencia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaAsistencia;
	}
	
	/* Map para verificar la asistencia */
	public static HashMap<String, String> mapAsistenciaClase(Connection conn, String cursoCargaId) throws SQLException{
		HashMap<String, String> mapaAsistencia = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT FOLIO, ESTADO, COUNT(ESTADO) AS TOTAL FROM ENOC.CARGA_GRUPO_ASISTENCIA " +
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' GROUP BY FOLIO, ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaAsistencia.put(rs.getString("FOLIO")+rs.getString("ESTADO"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoAsistenciaUtil|mapAsistenciaClase|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaAsistencia;
	}
	
	/* Map para el total de asistencias por alumnos*/
	public static HashMap<String, String> mapAsistenciaTotal(Connection conn, String cursoCargaId) throws SQLException{
		HashMap<String, String> mapAsistencia = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ESTADO, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.CARGA_GRUPO_ASISTENCIA " +
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' GROUP BY CODIGO_PERSONAL,ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapAsistencia.put(rs.getString("CODIGO_PERSONAL")+rs.getString("ESTADO"),rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CargaGrupoAsistenciaUtil|mapAsistenciaClase|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapAsistencia;
	}
	
	
	public ArrayList<CargaGrupoAsistencia> getListReporte(Connection conn, String codigoPersonal, String cursoCargaId, String orden) throws SQLException{
		
		ArrayList<CargaGrupoAsistencia> asistencia = new ArrayList<CargaGrupoAsistencia>();
		Statement st 								 = conn.createStatement();
		ResultSet rs 								 = null;
		String comando								 = "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO " +
					"FROM ENOC.CARGA_GRUPO_ASISTENCIA " +
					"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' AND CODIGO_PERSONAL='"+codigoPersonal+"'"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoAsistencia horario = new CargaGrupoAsistencia();
				horario.mapeaReg(rs);
				asistencia.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoAsistenciaUtil|getListMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return asistencia;
	}

}
