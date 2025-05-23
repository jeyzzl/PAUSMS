/**
 * 
 */
package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BecPeriodoUtil {
	
	public boolean insertReg(Connection conn, BecPeriodo becPeriodo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_PERIODO"+ 
				"(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO, ID_EJERCICIO) "+
				"VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)");
				
			ps.setString(1, becPeriodo.getPeriodoId());
			ps.setString(2, becPeriodo.getPeriodoNombre());
			ps.setString(3, becPeriodo.getFechaIni());
			ps.setString(4, becPeriodo.getFechaFin());
			ps.setString(5, becPeriodo.getEstado());
			ps.setString(6, becPeriodo.getIdEjercicio());
			ps.setString(7, becPeriodo.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	public boolean updateReg(Connection conn, BecPeriodo becPeriodo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PERIODO "+ 
				"SET "+
				"PERIODO_NOMBRE = ?, "+
				"FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'), "+
				"FECHA_fIN = TO_DATE(?, 'DD/MM/YYYY'), ESTADO = ?, ID_EJERCICIO = ?, TIPO = ? "+
				"WHERE PERIODO_ID = ? ");
			
			ps.setString(1, becPeriodo.getPeriodoNombre());
			ps.setString(2, becPeriodo.getFechaIni());
			ps.setString(3, becPeriodo.getFechaFin());
			ps.setString(4, becPeriodo.getEstado());
			ps.setString(5, becPeriodo.getIdEjercicio());
			ps.setString(6, becPeriodo.getPeriodoId());
			ps.setString(7, becPeriodo.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String periodoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_PERIODO "+ 
				"WHERE PERIODO_ID = ?");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	

	public BecPeriodo mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		BecPeriodo becPeriodo = new BecPeriodo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO "+
				" FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ? "); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becPeriodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPeriodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_PERIODO  "+ 
				"WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.BEC_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|getPeriodoActual|:"+ex);
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
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = '"+periodoId+"' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getPeriodoDeFecha(Connection conn, String fecha, String ejercicioId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	periodo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.BEC_PERIODO"
					+ " WHERE TO_DATE('"+fecha+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " AND ID_EJERCICIO = '"+ejercicioId+"' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	periodo = rs.getString("PERIODO_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|getPeriodoDeFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public ArrayList<BecPeriodo> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<BecPeriodo> lisPre	= new ArrayList<BecPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO "+
					  " FROM ENOC.BEC_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecPeriodo alumPre = new BecPeriodo();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<BecPeriodo> getAllActivos(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<BecPeriodo> lisPre	= new ArrayList<BecPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO "+
					  " FROM ENOC.BEC_PERIODO WHERE ESTADO = 'A' AND ID_EJERCICIO = '"+ejercicioId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecPeriodo alumPre = new BecPeriodo();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPeriodoUtil|getAllActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
}