package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InvActividadUtil {
	
	public boolean insertReg(Connection conn, InvActividad act) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_ACTIVIDAD(PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, FECHA_INI, FECHA_FIN) "
					+ "VALUES (?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'))");
			
			ps.setString(1, act.getProyectoId());
			ps.setString(2, act.getActividadId());
			ps.setString(3, act.getActividadNombre());
			ps.setString(4, act.getFechaIni());
			ps.setString(5, act.getFechaFin());
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.InvActividad|insertReg|:"+ex);
		}finally{
			if(ps != null) ps.close();
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvActividad act) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE ENOC.INV_ACTIVIDAD "
					+ "SET ACTIVIDAD_NOMBRE = ?, "
					+ "FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), "
					+ "FECHA_FIN = TO_DATE(?,'DD/MM/YYYY') "
					+ "WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = ?");		
			ps.setString(1, act.getActividadNombre());
			ps.setString(2, act.getFechaIni());
			ps.setString(3, act.getFechaFin());
			ps.setString(4, act.getProyectoId());
			ps.setString(5, act.getActividadId());
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvActividad|updateReg|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteRegId(Connection conn, String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?"
					+ " AND ACTIVIDAD_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, actividadId);
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvActividad|deleteRegId|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvActividad|deleteReg|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean mapeaRegId(Connection conn, String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,  TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD"
					+ " WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, actividadId);
			
			rs = ps.executeQuery();
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	
	public boolean existeRegId(Connection conn, String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD "
					+ " WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, proyectoId);
			ps.setString(2, actividadId);
			rs = ps.executeQuery();
			
			if (rs.next())
				ok = true;
			else
				ok = false;
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvActividad|existeRegId|:"+ex);
		} finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		} 
				
		return ok;
	}
	
	public boolean existeReg(Connection conn, String proyectoId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				ok = true;
			else
				ok = false;
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvActividad|existeReg|:"+ex);
		} finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		} 
				
		return ok;
	}
	
	public String maximoReg(Connection conn, String proyectoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(ACTIVIDAD_ID)+1) AS MAXIMO FROM ENOC.INV_ACTIVIDAD WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if(maximo == null)
				maximo = "1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvActividad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<InvActividad> getListActividad(Connection conn, String proyectoId, String orden ) throws SQLException{
		
		ArrayList<InvActividad> lista	= new ArrayList<InvActividad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
	 
		try{
			comando = " SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD"
					+ " WHERE PROYECTO_ID = '"+proyectoId+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				InvActividad objeto = new InvActividad();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvActividad|getListActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
}
