package aca.well;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WellPermisoUtil {

	public boolean insertReg(Connection conn, WellPermiso wellPermiso ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELL_PERMISO "
 					+ " (PERIODO, CODIGO_EMPLEADO, FECHA, IP) "
 					+ " VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?)");
 			ps.setString(1, wellPermiso.getPeriodo());
 			ps.setString(2, wellPermiso.getCodigoEmpleado());
 			ps.setString(3, wellPermiso.getFecha());
 			ps.setString(4, wellPermiso.getIp());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellPermisoUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}	
	
	public boolean existeReg(Connection conn, String periodo, String codigoEmpleado) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_PERMISO "
					+ " WHERE PERIODO = ? AND CODIGO_EMPLEADO = ? ");
			ps.setString(1, periodo);
			ps.setString(2, codigoEmpleado);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPermisoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String getPeriodoActual(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	periodo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(SYSDATE,'YYYY') AS PERIODO FROM DUAL"); 
						
			rs = ps.executeQuery();
			if (rs.next())	periodo = rs.getString("PERIODO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPermisoUtil|getPeriodoActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return periodo;
	}
	
	public boolean tienePermiso(Connection conn, String periodo, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean autorizo		= false;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_PERMISO WHERE PERIODO = ? AND CONDIGO_PERSONAL = ?"); 
			
			ps.setString(1, periodo);
			ps.setString(2, codigoPersonal);
						
			rs = ps.executeQuery();
			if (rs.next())	autorizo = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellPermisoUtil|tienePermiso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return autorizo;
	}
		
}
