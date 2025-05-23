package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class BitTramiteUtil {
	
	public boolean insertReg(Connection conn, BitTramite tramite) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BIT_TRAMITE"+ 
				"(TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO)"+
				" VALUES(TO_NUMBER(?, '9999'), ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999'), ?, ?)");
					
			ps.setString(1,	tramite.getTramiteId());
			ps.setString(2, tramite.getNombre());
			ps.setString(3, tramite.getMinimo());
			ps.setString(4, tramite.getMaximo());
			ps.setString(5, tramite.getPromedio());
			ps.setString(6, tramite.getAreaId());
			ps.setString(7, tramite.getRequisitos());
			ps.setString(8, tramite.getTipo());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BitTramite tramite) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_TRAMITE "
					+ " SET NOMBRE = ?, "
					+ " MINIMO = TO_NUMBER(?,'999.99'), "
					+ " MAXIMO = TO_NUMBER(?,'999.99'), "
					+ " PROMEDIO = TO_NUMBER(?,'999.99'), "
					+ " AREA_ID = TO_NUMBER(?, '999'),"
					+ " REQUISITOS = ?,"
					+ " TIPO = ?"		
					+ " WHERE TRAMITE_ID = TO_NUMBER(?, '9999')");
			
			ps.setString(1, tramite.getNombre());
			ps.setString(2, tramite.getMinimo());
			ps.setString(3, tramite.getMaximo());
			ps.setString(4, tramite.getPromedio());
			ps.setString(5, tramite.getAreaId());
			ps.setString(6, tramite.getRequisitos());
			ps.setString(7, tramite.getTipo());
			ps.setString(8, tramite.getTramiteId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean actualizaTiempos(Connection conn, String minimo, String maximo, String promedio, String tramiteId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_TRAMITE"
					+ " SET MINIMO = TO_NUMBER(?,'999.99'), "
					+ " MAXIMO = TO_NUMBER(?,'999.99'), "
					+ " PROMEDIO = TO_NUMBER(?,'999.99')"
					+ " WHERE TRAMITE_ID = TO_NUMBER(?, '9999')");
			
			ps.setString(1, minimo);
			ps.setString(2, maximo);
			ps.setString(3, promedio);			
			ps.setString(4, tramiteId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|actualizaTiempos|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String tramiteId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_TRAMITE"+ 
				" WHERE TRAMITE_ID = TO_NUMBER(?, '9999')");
			
			ps.setString(1, tramiteId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BitTramite mapeaRegId( Connection conn, String tramiteId) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		BitTramite tramite 		= new BitTramite();
		try{
			ps = conn.prepareStatement(" SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
									 + " FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = "+tramiteId); 
			rs = ps.executeQuery();
			if (rs.next()){
				tramite.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return tramite;
	}
	
	public static String getNombre( Connection conn, String tramiteId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String nombre 			= "-"; 
		
		try{
			ps = conn.prepareStatement(" SELECT NOMBRE FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')");
			ps.setString(1, tramiteId);
			
			rs = ps.executeQuery();			
			if (rs.next()){
				nombre = rs.getString("NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|getNombre|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public static String getAreaId( Connection conn, String tramiteId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		String area 			= "0"; 
		
		try{
			ps = conn.prepareStatement(" SELECT AREA_ID FROM ENOC.BIT_TRAMITE WHERE TRAMITE_ID = TO_NUMBER(?,'9999')");
			ps.setString(1, tramiteId);
			
			rs = ps.executeQuery();			
			if (rs.next()){
				area = rs.getString("AREA_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|getAreaId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return area;
	}
	
	public ArrayList<BitTramite> lisTramites(Connection conn, String orden) throws SQLException{
		ArrayList<BitTramite> tramites = new ArrayList<BitTramite>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
					+ " FROM ENOC.BIT_TRAMITE "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramite tramite = new BitTramite();
				tramite.mapeaReg(rs);
				tramites.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|getTramiteList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return tramites;
	}
	
	public ArrayList<BitTramite> lisTramitesPorTipos(Connection conn, String tipos, String orden) throws SQLException{
		ArrayList<BitTramite> tramites = new ArrayList<BitTramite>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
					+ " FROM ENOC.BIT_TRAMITE "
					+ " WHERE TIPO IN ("+tipos+") "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramite tramite = new BitTramite();
				tramite.mapeaReg(rs);
				tramites.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|getTramiteList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return tramites;
	}
	
	public ArrayList<BitTramite> lisTramites(Connection conn, String areaId, String orden) throws SQLException{
		ArrayList<BitTramite> tramites = new ArrayList<BitTramite>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
					+ " FROM ENOC.BIT_TRAMITE "
					+ " WHERE AREA_ID = TO_NUMBER('"+areaId+"','99') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramite tramite = new BitTramite();
				tramite.mapeaReg(rs);
				tramites.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|lisTramites|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return tramites;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(TRAMITE_ID)+1,1) AS MAXIMO FROM ENOC.BIT_TRAMITE"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.TramiteUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public String minTiempoTramite(Connection conn, String tramiteId, String estados) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet rs			= null;
		String minimo			= "1";
		
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MIN(FECHA_FINAL-FECHA_INICIO),0) AS MINIMO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER('"+tramiteId+"','9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+") ");
						
			rs = ps.executeQuery();
			if (rs.next())
				minimo = rs.getString("MINIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.TramiteUtil|minTiempoTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return minimo;
	}
	
	public String maxTiempoTramite(Connection conn, String tramiteId, String estados) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet rs			= null;
		String promedio			= "0";		
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(FECHA_FINAL-FECHA_INICIO),0) AS MAXIMO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER('"+tramiteId+"','9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+")");			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.TramiteUtil|maxTiempoTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return promedio;
	}
	
	public String promedioTiempoTramite(Connection conn, String tramiteId, String estados) throws SQLException{
		
		PreparedStatement ps	= null;
		PreparedStatement ps2	= null;
		ResultSet rs			= null;
		ResultSet rs2			= null;
		String maximo			= "1";
		
		try{
			ps2 = conn.prepareStatement ("SELECT COUNT(*) AS TOTAL"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER('"+tramiteId+"','9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+")"); 
			rs2 = ps2.executeQuery();
			if (rs2.next() && rs2.getInt("TOTAL") > 0){
			
				ps = conn.prepareStatement ("SELECT TO_NUMBER(COALESCE(AVG(FECHA_FINAL-FECHA_INICIO),0),'999.99') AS PROMEDIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE TRAMITE_ID = TO_NUMBER('"+tramiteId+"','9999')"
					+ " AND FECHA_FINAL IS NOT NULL"
					+ " AND ESTADO IN ("+estados+")");
			
				rs = ps.executeQuery();
				if (rs.next())
					maximo = rs.getString("PROMEDIO");
			}
			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.TramiteUtil|promedioTiempoTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (ps2!=null) ps2.close();
		}
		
		return maximo;
	}
	
	public HashMap<String, aca.bitacora.BitTramite> mapTramites(Connection conn) throws SQLException{
		HashMap<String, aca.bitacora.BitTramite> mapTramite 	= new HashMap<String, aca.bitacora.BitTramite>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT TRAMITE_ID, NOMBRE, MINIMO, MAXIMO, PROMEDIO, AREA_ID, REQUISITOS, TIPO"
					+ " FROM ENOC.BIT_TRAMITE ";
			rs = st.executeQuery(comando);
			while (rs.next()){
				aca.bitacora.BitTramite tramite = new BitTramite();
				tramite.mapeaReg(rs);
				mapTramite.put(rs.getString("TRAMITE_ID"), tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapTramites|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}

}
