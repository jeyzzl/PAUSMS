/**
 * 
 */
package aca.alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlertaPeriodoUtil {
	
	public boolean insertReg(Connection conn, AlertaPeriodo alertaPeriodo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALERTA_PERIODO"+ 
				"(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, MODALIDADES, ESTADO, "+
				"EXCEPTO) "+
				"VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)");
				
			ps.setString(1, alertaPeriodo.getPeriodoId());
			ps.setString(2, alertaPeriodo.getPeriodoNombre());
			ps.setString(3, alertaPeriodo.getFechaIni());
			ps.setString(4, alertaPeriodo.getFechaFin());
			ps.setString(5, alertaPeriodo.getModalidades());
			ps.setString(6, alertaPeriodo.getEstado());
			ps.setString(7, alertaPeriodo.getExcepto());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	
	public boolean updateReg(Connection conn, AlertaPeriodo alertaPeriodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALERTA_PERIODO "+ 
				"SET "+
				"PERIODO_NOMBRE = ?, "+
				"FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'), "+
				"FECHA_fIN = TO_DATE(?, 'DD/MM/YYYY'), "+
				"MODALIDADES = ?, "+
				"ESTADO = ?, "+
				"EXCEPTO = ? "+
				"WHERE PERIODO_ID = ? ");
			
			ps.setString(1, alertaPeriodo.getPeriodoNombre());
			ps.setString(2, alertaPeriodo.getFechaIni());
			ps.setString(3, alertaPeriodo.getFechaFin());
			ps.setString(4, alertaPeriodo.getModalidades());
			ps.setString(5, alertaPeriodo.getEstado());
			ps.setString(6, alertaPeriodo.getExcepto());
			ps.setString(7, alertaPeriodo.getPeriodoId());

			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALERTA_PERIODO "+ 
				"WHERE PERIODO_ID = ?");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlertaPeriodo mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		AlertaPeriodo alertaPeriodo = new AlertaPeriodo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
				"MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = ? "); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				alertaPeriodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alertaPeriodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_PERIODO  "+ 
				"WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getPeriodoActual(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.ALERTA_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|getTipoAlumnoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getPeriodoNombre(Connection conn, String periodoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = '"+periodoId+"' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public ArrayList<AlertaPeriodo> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaPeriodo> lisPre	= new ArrayList<AlertaPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
					  " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaPeriodo alumPre = new AlertaPeriodo();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<AlertaPeriodo> getAllActivos(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaPeriodo> lisPre	= new ArrayList<AlertaPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
					  " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaPeriodo alumPre = new AlertaPeriodo();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaPeriodoUtil|getAllActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
}