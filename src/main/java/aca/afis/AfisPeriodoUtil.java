package aca.afis;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import aca.afis.AfisPeriodo;

public class AfisPeriodoUtil {
	
	
	public boolean insertReg(Connection conn, AfisPeriodo periodo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.AFIS_PERIODO"+ 
				"(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO, FILTRO) "+
				"VALUES(TO_NUMBER(?,'999'), ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?)");
			
			ps.setString(1, periodo.getPeriodoId());
			ps.setString(2, periodo.getPeriodoNombre());
			ps.setString(3, periodo.getFechaIni());
			ps.setString(4, periodo.getFechaFin());
			ps.setString(5, periodo.getEstado());
			ps.setString(6, periodo.getFiltro());	
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AfisPeriodo periodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.AFIS_PERIODO"
				+ " SET PERIODO_NOMBRE = ?,"
				+ " FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " FECHA_fIN = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " ESTADO = ?,"
				+ " FILTRO = ?"+
				"WHERE PERIODO_ID = TO_NUMBER(?,'999') ");
			
			ps.setString(1, periodo.getPeriodoNombre());
			ps.setString(2, periodo.getFechaIni());
			ps.setString(3, periodo.getFechaFin());
			ps.setString(4, periodo.getEstado());
			ps.setString(5, periodo.getFiltro());
			ps.setString(6, periodo.getPeriodoId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.AFIS_PERIODO "+ 
				"WHERE PERIODO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public AfisPeriodo mapeaRegId( Connection conn, String periodoId ) throws SQLException{	
		
		AfisPeriodo periodo = new AfisPeriodo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " ESTADO, FILTRO"
					+ " FROM ENOC.AFIS_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				periodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.AFIS_PERIODO  "+ 
				"WHERE PERIODO_ID = TO_NUMBER(?,'999')");
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public String MaximoReg(Connection conn) throws SQLException{
		
		String maximo			= "1";
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT MAX(PERIODO_ID)+1 MAXIMO FROM ENOC.AFIS_PERIODO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("Maximo");
				
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
		
	}
	
	public static String getPeriodoActual(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	tipo			= ""; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.AFIS_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A' "); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|getPeriodoActual|:"+ex);
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
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.AFIS_PERIODO WHERE PERIODO_ID = TO_NUMBER('"+periodoId+"','999')"); 
						
			rs = ps.executeQuery();
			if (rs.next())	tipo = rs.getString("PERIODO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static String getFiltro(Connection conn, String periodoId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String	filtro			= "TODO"; 
		try{
			ps = conn.prepareStatement("SELECT FILTRO FROM ENOC.AFIS_PERIODO WHERE PERIODO_ID = TO_NUMBER('"+periodoId+"','999')"); 
						
			rs = ps.executeQuery();
			if (rs.next())
				filtro = rs.getString("FILTRO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodo|getFiltro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return filtro;
	}
	
	public ArrayList<AfisPeriodo> getAll(Connection conn, String orden) throws SQLException {
		
		ArrayList<AfisPeriodo> lisPre = new ArrayList<AfisPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, FILTRO"
					+ " FROM ENOC.AFIS_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AfisPeriodo afisPe = new AfisPeriodo();
				afisPe.mapeaReg(rs);
				lisPre.add(afisPe);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
		
	}
	
	public ArrayList<AfisPeriodo> getAllActivos(Connection conn, String orden) throws SQLException{
		
		ArrayList<AfisPeriodo> lisPre	= new ArrayList<AfisPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, FILTRO"
					+ " FROM ENOC.AFIS_PERIODO"
					+ " WHERE ESTADO = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AfisPeriodo afisPe = new AfisPeriodo();
				afisPe.mapeaReg(rs);
				lisPre.add(afisPe);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|getAllActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public HashMap<String, String> mapPeriodosActivos(Connection conn) throws SQLException{
		
		HashMap<String, String> list		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE FROM ENOC.AFIS_PERIODO WHERE ESTADO = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.put(rs.getString("PERIODO_ID"), rs.getString("PERIODO_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|mapPeriodosActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return list;
	}
	
	public HashMap<String, String> mapPeriodos(Connection conn) throws SQLException{
		
		HashMap<String, String> list		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE FROM ENOC.AFIS_PERIODO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.put(rs.getString("PERIODO_ID"), rs.getString("PERIODO_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afis.AfisPeriodoUtil|mapPeriodosActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return list;
	}	

}
