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

public class AlertaAntroUtil {
	
	public boolean insertReg(Connection conn, AlertaAntro alertaAntro) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALERTA_ANTRO"
				+" (PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
			ps.setString(1, alertaAntro.getPeriodoId());			
			ps.setString(2, alertaAntro.getCodigoPersonal());
			ps.setString(3, alertaAntro.getPeso());
			ps.setString(4, alertaAntro.getTalla());
			ps.setString(5, alertaAntro.getCintura());
			ps.setString(6, alertaAntro.getImc());
			ps.setString(7, alertaAntro.getGrasa());
			ps.setString(8, alertaAntro.getMusculo());
			ps.setString(9, alertaAntro.getPresion());
			
			if (ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	
	public boolean updateReg(Connection conn, AlertaAntro alertaAntro ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALERTA_ANTRO " 
				+ " SET "
				+ " PESO = ?,"
				+ " TALLA = ?,"
				+ " CINTURA = ?,"
				+ " IMC = ?,"
				+ " GRASA = ?,"
				+ " MUSCULO = ?,"
				+ " PRESION = ?"
				+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, alertaAntro.getPeso());
			ps.setString(2, alertaAntro.getTalla());
			ps.setString(3, alertaAntro.getCintura());
			ps.setString(4, alertaAntro.getImc());
			ps.setString(5, alertaAntro.getGrasa());
			ps.setString(6, alertaAntro.getMusculo());
			ps.setString(7, alertaAntro.getPresion());
			ps.setString(8, alertaAntro.getPeriodoId());
			ps.setString(9, alertaAntro.getCodigoPersonal());

			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String periodoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALERTA_ANTRO "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlertaAntro mapeaRegId( Connection conn, String periodoId, String codigoPersonal ) throws SQLException{
		
		AlertaAntro antro = new AlertaAntro();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION"
					+ " FROM ENOC.ALERTA_ANTRO"
					+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next()){				
				antro.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return antro;
	}	
	
	public static boolean existeReg(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_ANTRO "+ 
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<AlertaAntro> listAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaAntro> lista	= new ArrayList<AlertaAntro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION FROM ENOC.ALERTA_ANTRO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaAntro antro = new AlertaAntro();
				antro.mapeaReg(rs);
				lista.add(antro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|listAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public ArrayList<AlertaAntro> listAlumno(Connection conn, String periodoId, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<AlertaAntro> lista	= new ArrayList<AlertaAntro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, PESO, TALLA, CINTURA, IMC, GRASA, MUSCULO, PRESION "
					+ " FROM ENOC.ALERTA_ANTRO"					
					+ " WHERE PERIODO_ID = '"+periodoId+"'"
					+ " AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaAntro antro = new AlertaAntro();
				antro.mapeaReg(rs);
				lista.add(antro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaAntroUtil|listAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapAlertaAll(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PESO, TALLA, PRESION FROM ENOC.ALERTA_ANTRO WHERE PERIODO_ID= '"+periodoId+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("PESO")+"_"+rs.getString("TALLA")+"_"+rs.getString("PRESION"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|getMapEdadAlerta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}

}