/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class BajaAlumnoUtil {
	
	public boolean insertReg(Connection conn, BajaAlumno bajaAlum ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BAJA_ALUMNO"+ 
				"(BAJA_ID, CODIGO_PERSONAL, CARGA_ID, CARRERA_ID," +
				" PLAN_ID, F_INICIO, F_BAJA, COMENTARIO)"+
				" VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?," +
				" ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?)");
					
			ps.setString(1,  bajaAlum.getBajaId());
			ps.setString(2,  bajaAlum.getCodigoPersonal());
			ps.setString(3,  bajaAlum.getCargaId());
			ps.setString(4,  bajaAlum.getCarreraId());
			ps.setString(5,  bajaAlum.getPlanId());
			ps.setString(6,  bajaAlum.getFInicio());
			ps.setString(7,  bajaAlum.getFBaja());
			ps.setString(8,  bajaAlum.getComentario());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BajaAlumno bajaAlum ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BAJA_ALUMNO"+ 
				" SET CODIGO_PERSONAL = ?," +
				" CARGA_ID = ?," +
				" CARRERA_ID = ?," +
				" PLAN_ID = ?," +
				" F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" F_BAJA = TO_DATE(?, 'DD/MM/YYYY'),"+
				" COMENTARIO = ?"+				
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1,  bajaAlum.getCodigoPersonal());
			ps.setString(2,  bajaAlum.getCargaId());
			ps.setString(3,  bajaAlum.getCarreraId());
			ps.setString(4,  bajaAlum.getPlanId());
			ps.setString(5,  bajaAlum.getFInicio());
			ps.setString(6,  bajaAlum.getFBaja());
			ps.setString(7,  bajaAlum.getComentario());
			ps.setString(8,  bajaAlum.getBajaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String bajaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BAJA_ALUMNO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, bajaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BajaAlumno mapeaRegId(Connection conn, String bajaId) throws SQLException{
		BajaAlumno bajaAlum = new BajaAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CARGA_ID, CARRERA_ID, PLAN_ID, F_INICIO, F_BAJA, COMENTARIO" +
					" FROM ENOC.BAJA_ALUMNO WHERE BAJA_ID = ? "); 
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				bajaAlum.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return bajaAlum;
	}
	
	public BajaAlumno mapeaRegSolicitado(Connection conn, String codigoPersonal) throws SQLException{
		BajaAlumno bajaAlum = new BajaAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CARGA_ID, CARRERA_ID, PLAN_ID," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" F_BAJA, COMENTARIO" +
					" FROM ENOC.BAJA_ALUMNO WHERE CODIGO_PERSONAL = ?" + 
					" AND F_BAJA IS NULL");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				bajaAlum.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|mapeaRegSolicitado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return bajaAlum;
	}
	
	public boolean existeReg(Connection conn, String bajaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId, String carreraId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMNO"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?" +
				" AND CARRERA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximo(Connection conn) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  maximo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(BAJA_ID+1), 1) AS MAXIMO FROM ENOC.BAJA_ALUMNO"); 
			
			rs = ps.executeQuery();
			if (rs.next()){		
				maximo = rs.getString("MAXIMO");
				
			}else{				
				maximo = "1";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|maximoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<BajaAlumno> getListSolicitudes(Connection conn, String orden) throws SQLException{
		
		ArrayList<BajaAlumno> lisPaso 	= new ArrayList<BajaAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT BAJA_ID, CODIGO_PERSONAL, CARGA_ID, CARRERA_ID, PLAN_ID," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_BAJA, 'DD/MM/YYYY') AS F_BAJA, COMENTARIO" +
					" FROM ENOC.BAJA_ALUMNO A" + 
					" WHERE F_BAJA IS NULL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BajaAlumno ba= new BajaAlumno();
				ba.mapeaReg(rs);
				lisPaso.add(ba);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|getListSolicitudes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPaso;
	}
}