//Clase para la tabla de Alum_Academico
package aca.federacion;

import java.sql.*;
import java.util.HashMap;

public class FedVotoUtil{
	
	public boolean insertReg( Connection conn, FedVoto fedVoto ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FED_VOTO " + 
				"(EVENTO_ID, CODIGO_PERSONAL, PRESIDENTE, ACADEMICO, DESARROLLO, FINANCIERO, EJECUTIVO, SECRETARIO, FECHA, MAESTRO, MAESTRA ) " +
				"VALUES( TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'),?,? )");			
			
			ps.setString(1, fedVoto.getEventoId());
			ps.setString(2, fedVoto.getCodigoPersonal());
			ps.setString(3, fedVoto.getPresidente());
			ps.setString(4, fedVoto.getAcademico());
			ps.setString(5, fedVoto.getDesarrollo());
			ps.setString(6, fedVoto.getFinanciero());
			ps.setString(7, fedVoto.getEjecutivo());
			ps.setString(8, fedVoto.getSecretario());
			ps.setString(9, fedVoto.getFecha());
			ps.setString(10, fedVoto.getMaestro());
			ps.setString(11, fedVoto.getMaestra());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg( Connection conn, FedVoto fedVoto ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FED_VOTO SET " + 
					"PRESIDENTE 		= ?, " +
					"ACADEMICO	 		= ?, " +
					"DESARROLLO 		= ?, " +
					"FINANCIERO 		= ?, " +
					"EJECUTIVO 			= ?, " +
					"SECRETARIO 		= ?, " +
					"FECHA 				= ?, " +
					"MAESTRO			= ?, " +
					"MAESTRA			= ? " +
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
		
			ps.setString(1, fedVoto.getPresidente());
			ps.setString(2, fedVoto.getAcademico());
			ps.setString(3, fedVoto.getDesarrollo());
			ps.setString(4, fedVoto.getFinanciero());
			ps.setString(5, fedVoto.getEjecutivo());
			ps.setString(6, fedVoto.getSecretario());
			ps.setString(7, fedVoto.getFecha());
			ps.setString(8, fedVoto.getMaestro());
			ps.setString(9, fedVoto.getMaestra());
			ps.setString(10, fedVoto.getEventoId());
			ps.setString(11, fedVoto.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String eventoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.FED_VOTO "+ 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.FedVotoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public FedVoto mapeaRegId( Connection conn, String eventoId, String codigoPersonal ) throws SQLException{
		FedVoto fedVoto = new FedVoto();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FED_VOTO " + 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
				"AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				fedVoto.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return fedVoto;
	}

	public static boolean existeReg(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FED_VOTO " + 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99') " +
					"AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}	
	
	
	public static String participantes(Connection conn, String eventoId) throws SQLException{
		String 		cantidad 	= "0";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.FED_VOTO " + 
					"WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, eventoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cantidad = rs.getString("CANTIDAD");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|participantes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return cantidad;
	}	
		
	public static HashMap<String, String> mapPresidente(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PRESIDENTE, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY PRESIDENTE ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("PRESIDENTE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapPresidente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapAcademico(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ACADEMICO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY ACADEMICO ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("ACADEMICO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapAcademico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapDesarrollo(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT DESARROLLO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY DESARROLLO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("DESARROLLO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapDesarrollo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapFinanciero(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FINANCIERO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY FINANCIERO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("FINANCIERO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapFinanciero|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapEjecutivo(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EJECUTIVO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY EJECUTIVO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("EJECUTIVO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapEjecutivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapSecretario(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT SECRETARIO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY SECRETARIO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("SECRETARIO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapSecretario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapMaestro(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MAESTRO, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY MAESTRO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("MAESTRO"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

	public static HashMap<String, String> mapMaestra(Connection conn, String eventoId) throws SQLException{
		
		HashMap<String, String> map	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT MAESTRA, COUNT(*) AS TOTAL FROM ENOC.FED_VOTO WHERE EVENTO_ID = '"+eventoId+"' GROUP BY MAESTRA";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("MAESTRA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.FedVotoUtil|mapMaestra|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}