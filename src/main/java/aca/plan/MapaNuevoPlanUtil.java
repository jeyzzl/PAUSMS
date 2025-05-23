/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author elifo
 *
 */
public class MapaNuevoPlanUtil {
	public boolean insertReg(Connection conn, MapaNuevoPlan mapaNuevoPlan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_NUEVO_PLAN"+ 
				" (PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, IDIOMA, HFD, HPS, HTS, HEI, HSS, HAS, YEAR) "+
				" VALUES( ?, ?, ?, TO_NUMBER(?, '999'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'9999'))");
					
			ps.setString(1, mapaNuevoPlan.getPlanId());
			ps.setString(2, mapaNuevoPlan.getCarreraId());
			ps.setString(3, mapaNuevoPlan.getNombre());
			ps.setString(4, mapaNuevoPlan.getVersionId());
			ps.setString(5, mapaNuevoPlan.getVersionNombre());
			ps.setString(6, mapaNuevoPlan.getEstado());
			ps.setString(7, mapaNuevoPlan.getTipo());
			ps.setString(8, mapaNuevoPlan.getIdioma());
			ps.setString(9, mapaNuevoPlan.getHfd());
			ps.setString(10, mapaNuevoPlan.getHps());
			ps.setString(11, mapaNuevoPlan.getHts());
			ps.setString(12, mapaNuevoPlan.getHei());
			ps.setString(13, mapaNuevoPlan.getHss());
			ps.setString(14, mapaNuevoPlan.getHas());
			ps.setString(15, mapaNuevoPlan.getYear());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaNuevoPlan mapaNuevoPlan ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_NUEVO_PLAN"+ 
				" SET CARRERA_ID = ?," +
				" NOMBRE = ?,"+
				" VERSION_NOMBRE = ?," +
				" ESTADO = ?," +
				" TIPO = ?," +
				" IDIOMA = ?," +
				" HFD = ?," +
				" HPS = ?," +
				" HTS = ?," +
				" HEI = ?," +
				" HSS = ?," +
				" HAS = ?," +
				" YEAR = TO_NUMBER(?,'9999')" +
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, mapaNuevoPlan.getCarreraId());
			ps.setString(2, mapaNuevoPlan.getNombre());
			ps.setString(3, mapaNuevoPlan.getVersionNombre());
			ps.setString(4, mapaNuevoPlan.getEstado());
			ps.setString(5, mapaNuevoPlan.getTipo());
			ps.setString(6, mapaNuevoPlan.getIdioma());
			ps.setString(7, mapaNuevoPlan.getHfd());
			ps.setString(8, mapaNuevoPlan.getHps());
			ps.setString(9, mapaNuevoPlan.getHts());
			ps.setString(10, mapaNuevoPlan.getHei());
			ps.setString(11, mapaNuevoPlan.getHss());
			ps.setString(12, mapaNuevoPlan.getHas());
			ps.setString(13, mapaNuevoPlan.getYear());
			ps.setString(14, mapaNuevoPlan.getPlanId());
			ps.setString(15, mapaNuevoPlan.getVersionId());
			
			if (ps.executeUpdate()== 1){
				ok = true;	
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String planId, String versionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_NUEVO_PLAN"+ 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaNuevoPlan mapeaRegId( Connection conn, String planId, String versionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaNuevoPlan mapaNuevoPlan = new MapaNuevoPlan();
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," +
								" VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR"+
								" FROM ENOC.MAPA_NUEVO_PLAN" + 
								" WHERE PLAN_ID = ?" +
								" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaNuevoPlan.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaNuevoPlan;
	}
	
	public boolean existeReg(Connection conn, String planId, String versionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_NUEVO_PLAN"+ 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, planId);
			ps.setString(2, versionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String planId) throws SQLException{
		String maximo		 	= "1";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(VERSION_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_PLAN" + 
					" WHERE PLAN_ID = ?");
			
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				maximo = rs.getString("MAXIMO")==null?"1":rs.getString("MAXIMO");
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getMaxVersionPlan(Connection conn, String planId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String curso = "0"; 
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(VERSION_ID),1) AS MAXVER FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				curso = rs.getString("MAXVER");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getMaxVersionPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return curso;
	}
	
	public static String getHts(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String hts 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HTS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				hts = rs.getString("HTS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHts|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hts;
	}
	
	public static String getHps(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String hps 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HPS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				hps = rs.getString("HPS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHps|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hps;
	}

	public static String getHfd(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String hfd 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HFD FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				hfd = rs.getString("HFD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHfd|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hfd;
	}
	
	public static String getHei(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String hfd 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HEI FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				hfd = rs.getString("HEI");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHei|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hfd;
	}
	
	public static String getIdioma(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String idioma 			= "E";
		
		try{
			ps = conn.prepareStatement("SELECT IDIOMA FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				idioma = rs.getString("IDIOMA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getIdioma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return idioma;
	}
	
	public static String getHss(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String hss 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HSS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				hss = rs.getString("HSS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHss|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return hss;
	}
	
	public static String getHas(Connection conn, String planId ) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String has 				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT HAS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);		
			rs = ps.executeQuery();
			if (rs.next()){
				has = rs.getString("HAS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getHas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return has;
	}
	
	public ArrayList<MapaNuevoPlan> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MapaNuevoPlan> lisPlan		= new ArrayList<MapaNuevoPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," +
					  " VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaNuevoPlan plan = new MapaNuevoPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
public ArrayList<MapaNuevoPlan> getListPorYear(Connection conn, String year, String orden) throws SQLException{
		
		ArrayList<MapaNuevoPlan> lisPlan		= new ArrayList<MapaNuevoPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," 
					  + " VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR"
					  + " FROM ENOC.MAPA_NUEVO_PLAN "
					  + " WHERE YEAR = TO_NUMBER("+year+", '9999')"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaNuevoPlan plan = new MapaNuevoPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
	public ArrayList<MapaNuevoPlan> getListMaestro(Connection conn, String codigoMaestro, String orden) throws SQLException{
		
		ArrayList<MapaNuevoPlan> lisPlan		= new ArrayList<MapaNuevoPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN" + 
					  " WHERE (PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ENOC.MAPA_NUEVO_CURSO" + 
					  					" WHERE CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%')" +
					  " OR (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoMaestro+"') LIKE '%'||CARRERA_ID||'%')" + 
					  " AND ESTADO = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaNuevoPlan plan = new MapaNuevoPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getListMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
public ArrayList<MapaNuevoPlan> getListMaestroPorYear(Connection conn, String codigoMaestro, String orden, String year) throws SQLException{
		
		ArrayList<MapaNuevoPlan> lisPlan		= new ArrayList<MapaNuevoPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN" + 
					  " WHERE YEAR = TO_NUMBER("+year+", '9999') AND (PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ENOC.MAPA_NUEVO_CURSO" + 
					  					" WHERE CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%')" +
					  " OR (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoMaestro+"') LIKE '%'||CARRERA_ID||'%')" + 
					  " AND ESTADO = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaNuevoPlan plan = new MapaNuevoPlan();
				plan.mapeaReg(rs);
				lisPlan.add(plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|getListMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPlan;	
	}
	
	public HashMap<String, MapaNuevoPlan> mapaPlan(Connection conn) throws SQLException{
		
		HashMap<String, MapaNuevoPlan> mapa		= new HashMap<String, MapaNuevoPlan>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN";
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				MapaNuevoPlan plan = new MapaNuevoPlan();
				plan.mapeaReg(rs);
				mapa.put(rs.getString("PLAN_ID"), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoPlanUtil|mapaPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return mapa;	
	}
}