/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 * @author elifo
 *
 */
public class BajaAlumpasoUtil {
	
	public boolean insertReg(Connection conn, BajaAlumpaso bajaAlumP ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BAJA_ALUMPASO"+ 
				"(BAJA_ID, PASO_ID, FECHA, ESTADO)"+
				" VALUES(TO_NUMBER(?, '9999999')," +
				" TO_NUMBER(?, '99')," +
				" TO_DATE(?, 'DD/MM/YYYY')," +
				" ?)");
					
			ps.setString(1, bajaAlumP.getBajaId());
			ps.setString(2, bajaAlumP.getPasoId());
			ps.setString(3, bajaAlumP.getFecha());
			ps.setString(4, bajaAlumP.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BajaAlumpaso bajaAlumP ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BAJA_ALUMPASO"+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"+
				" ESTADO = ?"+				
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')");

			ps.setString(1, bajaAlumP.getFecha());
			ps.setString(2, bajaAlumP.getEstado());
			ps.setString(3, bajaAlumP.getBajaId());
			ps.setString(4, bajaAlumP.getPasoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String bajaId, String pasoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, bajaId);
			ps.setString(2, pasoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BajaAlumpaso mapeaRegId(Connection conn, String bajaId, String pasoId) throws SQLException{
		BajaAlumpaso bajaAlumP = new BajaAlumpaso();
		PreparedStatement ps= null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, PASO_ID," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO" +
					" FROM ENOC.BAJA_ALUMPASO" + 
					" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
					" AND PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, bajaId);
			ps.setString(2, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				bajaAlumP.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return bajaAlumP;
	}
	
	public boolean existeReg(Connection conn, String bajaId, String pasoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, bajaId);
			ps.setString(2, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean realizoPaso(Connection conn, String bajaId, String pasoId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, bajaId);
			ps.setString(2, pasoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				String estado = rs.getString("ESTADO");
				if(estado.equals("S"))
					ok = true;
				else
					ok = false;
			}else{
				ok = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumpasoUtil|pasoPorMentoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<BajaAlumpaso> getListPorBaja(Connection conn, String bajaId, String orden) throws SQLException{
		
		ArrayList<BajaAlumpaso> lisPaso 	= new ArrayList<BajaAlumpaso>();
		PreparedStatement ps			= null;
		ResultSet rs 					= null;
		
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, PASO_ID, FECHA, ESTADO" +
					" FROM ENOC.BAJA_ALUMPASO A" + 
					" WHERE BAJA_ID = ? "+orden);
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			while (rs.next()){
				
				BajaAlumpaso ba= new BajaAlumpaso();
				ba.mapeaReg(rs);
				lisPaso.add(ba);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|getListPorBaja|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lisPaso;
	}
}