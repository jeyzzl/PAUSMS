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
import java.util.HashMap;

public class AlertaHistorialUtil {
	
	public boolean insertReg(Connection conn, AlertaHistorial alertaHistorial) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALERTA_HISTORIAL"
				+" (PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 )"
				+ " VALUES(?, ?, ?, ?, ?, ? )");
				
			ps.setString(1, alertaHistorial.getPeriodoId());			
			ps.setString(2, alertaHistorial.getCodigoPersonal());
			ps.setString(3, alertaHistorial.getPregunta());
			ps.setString(4, alertaHistorial.getRespuesta());
			ps.setString(5, alertaHistorial.getComentario1());
			ps.setString(6, alertaHistorial.getComentario2());
			
			if (ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	public boolean updateReg(Connection conn, AlertaHistorial alertaHistorial ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALERTA_HISTORIAL "+ 
				"SET "+				
				"RESPUESTA = ?, "+
				"COMENTARIO1 = ?, "+
				"COMENTARIO2 = ? "+				
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ?");
			
			ps.setString(1,  alertaHistorial.getPregunta());
			ps.setString(2,  alertaHistorial.getComentario1());
			ps.setString(3, alertaHistorial.getComentario2());
			ps.setString(4, alertaHistorial.getPeriodoId());
			ps.setString(5, alertaHistorial.getCodigoPersonal());
			ps.setString(6, alertaHistorial.getPregunta());

			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String periodoId, String codigoPersonal, String pregunta ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALERTA_HISTORIAL "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, pregunta);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlertaHistorial mapeaRegId( Connection conn, String periodoId, String codigoPersonal, String pregunta ) throws SQLException{
		AlertaHistorial alertaHistorial = new AlertaHistorial();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2"
					+ " FROM ENOC.ALERTA_HISTORIAL WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ? ");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, pregunta);
			
			rs = ps.executeQuery();
			if (rs.next()){
				alertaHistorial.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alertaHistorial;
	}
	
	public boolean existeReg(Connection conn, String periodoId, String codigoPersonal, String pregunta) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_HISTORIAL "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? AND PREGUNTA = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, pregunta);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public static boolean existeRegistro(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_HISTORIAL "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<AlertaHistorial> listAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaHistorial> lisHist	= new ArrayList<AlertaHistorial>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 FROM ENOC.ALERTA_HISTORIAL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaHistorial historial = new AlertaHistorial();
				historial.mapeaReg(rs);
				lisHist.add(historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|listAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHist;
	}
	
	public ArrayList<AlertaHistorial> listAlumno(Connection conn, String periodoId, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<AlertaHistorial> lisHist	= new ArrayList<AlertaHistorial>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 "
					+ " FROM ENOC.ALERTA_HISTORIAL"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaHistorial historial = new AlertaHistorial();
				historial.mapeaReg(rs);
				lisHist.add(historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|listAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHist;
	}
	
	public ArrayList<AlertaHistorial> listHistorial(Connection conn, String periodoId,  String orden) throws SQLException{
		
		ArrayList<AlertaHistorial> lisHist	= new ArrayList<AlertaHistorial>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2"
					+ " FROM ENOC.ALERTA_HISTORIAL"
					+ " WHERE PERIODO_ID = '"+periodoId+"'"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaHistorial historial = new AlertaHistorial();
				historial.mapeaReg(rs);
				lisHist.add(historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|listHistorial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHist;
	}
	
	public HashMap<String, AlertaHistorial> mapHistorial(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String, AlertaHistorial> mapa		= new HashMap<String,AlertaHistorial>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PREGUNTA, RESPUESTA, COMENTARIO1, COMENTARIO2 FROM ENOC.ALERTA_HISTORIAL"
					+ " WHERE PERIODO_ID= '"+periodoId+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){	
				AlertaHistorial historial = new AlertaHistorial();
				historial.mapeaReg(rs);
				mapa.put(rs.getString("CODIGO_PERSONAL")+rs.getString("PREGUNTA"), historial);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaHistorialUtil|mapHistorial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}

}