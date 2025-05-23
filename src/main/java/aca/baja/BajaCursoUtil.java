/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class BajaCursoUtil {
	
	public boolean insertReg(Connection conn, BajaCurso bajaCurso) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BAJA_CURSO"+ 
				"(BAJA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CREDITOS)"+
				" VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?, TO_NUMBER(?, '9999999'))");
					
			ps.setString(1,  bajaCurso.getBajaId());
			ps.setString(2,  bajaCurso.getCodigoPersonal());
			ps.setString(3,  bajaCurso.getCursoCargaId());
			ps.setString(4,  bajaCurso.getCursoId());
			ps.setString(5,  bajaCurso.getCreditos());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaCursoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public BajaCurso mapeaRegId(Connection conn, String bajaId) throws SQLException{
		BajaCurso bajaCurso = new BajaCurso();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CURSO_CARGA_ID, CURSO_ID, CREDITOS" +
					" FROM ENOC.BAJA_CURSO WHERE BAJA_ID = ? "); 
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				bajaCurso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaCursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return bajaCurso;
	}
	
	public ArrayList<BajaCurso> getListAlumno(Connection conn, String bajaId, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<BajaCurso> lisPaso 	= new ArrayList<BajaCurso>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CREDITOS" +
					" FROM ENOC.BAJA_CURSO" + 
					" WHERE BAJA_ID = " + bajaId +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BajaCurso bc= new BajaCurso();
				bc.mapeaReg(rs);
				lisPaso.add(bc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaCursoUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPaso;
	}
}