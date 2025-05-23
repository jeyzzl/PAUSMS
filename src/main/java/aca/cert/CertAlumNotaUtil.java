/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jose Torres
 *
 */
public class CertAlumNotaUtil {
	public boolean insertReg(Connection conn, CertAlumNota cerNota) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_ALUM_NOTA"+ 
				"(CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID," +
				" CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA,PROMEDIA)"+
				" VALUES(?, TO_NUMBER(?, '999'), ?, ?, ?," +
				" ?, ?, ?, ?, ?,? )");
			ps.setString(1, cerNota.getCodigoPersonal());
			ps.setString(2, cerNota.getFolio());
			ps.setString(3, cerNota.getPlanId());
			ps.setString(4, cerNota.getCicloId());
			ps.setString(5, cerNota.getCursoId());
			ps.setString(6, cerNota.getCurso());
			ps.setString(7, cerNota.getEstado());
			ps.setString(8, cerNota.getNota());
			ps.setString(9, cerNota.getNotaLetra());
			ps.setString(10, cerNota.getOptativa());
			ps.setString(11, cerNota.getPromedia());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|insertReg|:"+ex);			
		}finally{
			if (ps != null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CertAlumNota cerNota) throws Exception{
		PreparedStatement ps = null;
		boolean ok = false;
		
		try{			
			ps = conn.prepareStatement("UPDATE ENOC.CERT_ALUM_NOTA"+ 
				" SET PLAN_ID = ?," +
				" CICLO_ID = ?," +
				" CURSO_ID = ?," +
				" CURSO = ?," +
				" ESTADO = ?," +
				" NOTA = ?,"+
				" NOTA_LETRA = ?," +
				" OPTATIVA = ? ," +
				" PROMEDIA = ? "+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '999')");
			
			
			ps.setString(1, cerNota.getPlanId());
			ps.setString(2, cerNota.getCicloId());
			ps.setString(3, cerNota.getCursoId());
			ps.setString(4, cerNota.getCurso());
			ps.setString(5, cerNota.getEstado());
			ps.setString(6, cerNota.getNota());
			ps.setString(7, cerNota.getNotaLetra());
			ps.setString(8, cerNota.getOptativa());
			ps.setString(9, cerNota.getPromedia());
			ps.setString(10, cerNota.getCodigoPersonal());
			ps.setString(11, cerNota.getFolio());		
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|updateReg|:"+ex);
		}finally{
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String folio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '999')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|deleteReg|:"+ex);			
		}finally{
			if (ps != null) ps.close();
		}
		return ok;
	}
	
	public boolean deleteNotas(Connection conn, String codigoPersonal, String planId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()>= 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|deleteNotas|:"+ex);			
		}finally{
			if (ps != null) ps.close();
		}
		return ok;
	}
	
	public CertAlumNota mapeaRegId(Connection conn, String codigoPersonal, String cursoId) throws SQLException{
		CertAlumNota cerNota = new CertAlumNota();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID," +
					" CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA, PROMEDIA" +
					" FROM ENOC.CERT_ALUM_NOTA" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				cerNota.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|mapeaRegId|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		return cerNota;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cursoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CURSO_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|existeReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		
		return ok;
	}
	
	public int getNumAlumNotas(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int numNotas 			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_NOTAS FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numNotas = rs.getInt("NUM_NOTAS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|existeReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		
		return numNotas;
	}
	
	public boolean cerrarNotas(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_ALUM_NOTA SET ESTADO = 'C'"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);			
			if (ps.executeUpdate() >= 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|cerrarNotas|:"+ex);
		}finally{			
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean abrirNotas(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_ALUM_NOTA SET ESTADO = 'A'"+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate() >= 1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|abrirNotas|:"+ex);
		}finally{			
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public static String maxReg(Connection conn, String codigoPersonal) throws SQLException{
		String maximo 			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|maxReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static int getNumMatCiclo(Connection conn, String codigoPersonal, String planId, String ciclo) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		int numMat				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS NUMMAT FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND CICLO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			ps.setString(3, ciclo);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlumNotaUtil|getNumMatCiclo|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }			
		}
		
		return numMat;
	}
	
}