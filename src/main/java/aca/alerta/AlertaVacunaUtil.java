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

public class AlertaVacunaUtil {
	
	public boolean insertReg(Connection conn, AlertaVacuna alertaVacuna) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALERTA_VACUNA"
				+" (VACUNA, CODIGO_PERSONAL,APLICADA, FECHA1, FECHA2, FECHA3)"
				+ " VALUES(?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY') )");
				
			ps.setString(1, alertaVacuna.getVacuna());			
			ps.setString(2, alertaVacuna.getCodigoPersonal());
			ps.setString(3, alertaVacuna.getAplicada());
			ps.setString(4, alertaVacuna.getFecha1());
			ps.setString(5, alertaVacuna.getFecha2());
			ps.setString(6, alertaVacuna.getFecha3());
			
			if (ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	
	public boolean updateReg(Connection conn, AlertaVacuna alertaVacuna ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALERTA_VACUNA " 
				+ " SET "
				+ " APLICADA = ?, "
				+ " FECHA1 = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " FECHA2 = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " FECHA3 = TO_DATE(?, 'DD/MM/YYYY')"
				+ " WHERE VACUNA = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, alertaVacuna.getAplicada());
			ps.setString(2, alertaVacuna.getFecha1());
			ps.setString(3, alertaVacuna.getFecha2());
			ps.setString(4, alertaVacuna.getFecha3());			
			ps.setString(5, alertaVacuna.getVacuna());
			ps.setString(6, alertaVacuna.getCodigoPersonal());

			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String vacuna, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALERTA_VACUNA "+ 
				"WHERE VACUNA = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, vacuna);
			ps.setString(2, codigoPersonal);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<AlertaVacuna> listAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlertaVacuna> lista	= new ArrayList<AlertaVacuna>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT VACUNA, CODIGO_PERSONAL, APLICADA, FECHA1, FECHA2, FECHA3 FROM ENOC.ALERTA_VACUNA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlertaVacuna vacuna = new AlertaVacuna();
				vacuna.mapeaReg(rs);
				lista.add(vacuna);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|listAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public AlertaVacuna mapeaRegId( Connection conn, String vacunaId, String codigoPersonal ) throws SQLException{
		
		AlertaVacuna vacuna = new AlertaVacuna();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT VACUNA, CODIGO_PERSONAL, APLICADA, TO_CHAR(FECHA1, 'DD/MM/YYYY') AS FECHA1, TO_CHAR(FECHA2, 'DD/MM/YYYY') AS FECHA2, TO_CHAR(FECHA3, 'DD/MM/YYYY') AS FECHA3"
					+ " FROM ENOC.ALERTA_VACUNA"
					+ " WHERE VACUNA = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, vacunaId);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next()){				
				vacuna.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return vacuna;
	}
	
	public boolean existeReg(Connection conn, String vacuna, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALERTA_VACUNA "+ 
				"WHERE VACUNA = ? AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, vacuna);
			ps.setString(2, codigoPersonal);			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.AlertaVacunaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	

}