package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Manuel
 *
 */
public class InvMetodologiaUtil {
	
	public boolean insertReg(Connection conn, InvMetodologia metodo) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_METODOLOGIA(PROYECTO_ID, HUMANOS, DISENO, MUESTRA, "
					+ "RECOLECCION, CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, metodo.getProyectoId());
			ps.setString(2, metodo.getHumanos());
			ps.setString(3, metodo.getDiseno());
			ps.setString(4, metodo.getMuestra());
			ps.setString(5, metodo.getRecoleccion());
			ps.setString(6, metodo.getConfidencialidad());
			ps.setString(7, metodo.getVinculacion());
			ps.setString(8, metodo.getResponsable());
			ps.setString(9, metodo.getActividades());
			ps.setString(10, metodo.getEntregable());
			ps.setString(11, metodo.getPlan());
			ps.setString(12, metodo.getOrganizacion());
			ps.setString(13, metodo.getProblema());
			ps.setString(14, metodo.getObjetivo());
			ps.setString(15, metodo.getHipotesis());
			ps.setString(16, metodo.getOrganizacion());
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvMetodologia metodo) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_METODOLOGIA "
					+ " SET HUMANOS = ?,"
					+ " DISENO = ?,"
					+ " MUESTRA = ?,"
					+ " RECOLECCION = ?,"
					+ " CONFIDENCIALIDAD = ?,"
					+ " VINCULACION = ?,"
					+ " RESPONSABLE = ?,"
					+ " ACTIVIDADES = ?,"
					+ " ENTREGABLE = ?,"
					+ " PLAN = ?,"
					+ " ORGANIZACION = ?,"
					+ " PROBLEMA = ?,"
					+ " OBJETIVO = ?,"
					+ " HIPOTESIS = ?,"
					+ " VALIDEZ = ?"
					+ " WHERE PROYECTO_ID = ?");
			
			ps.setString(1, metodo.getHumanos());
			ps.setString(2, metodo.getDiseno());
			ps.setString(3, metodo.getMuestra());
			ps.setString(4, metodo.getRecoleccion());
			ps.setString(5, metodo.getConfidencialidad());
			ps.setString(6, metodo.getVinculacion());
			ps.setString(7, metodo.getResponsable());
			ps.setString(8, metodo.getActividades());
			ps.setString(9, metodo.getEntregable());
			ps.setString(10, metodo.getPlan());
			ps.setString(11, metodo.getOrganizacion());
			ps.setString(12, metodo.getProblema());
			ps.setString(13, metodo.getObjetivo());
			ps.setString(14, metodo.getHipotesis());
			ps.setString(15, metodo.getValidez());
			ps.setString(16, metodo.getProyectoId());		
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_METODOLOGIA WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else {
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public InvMetodologia mapeaRegId(Connection conn, String proyectoId) throws SQLException{
		
		InvMetodologia metodo = new InvMetodologia();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ"
					+ " FROM ENOC.INV_METODOLOGIA"
					+ " WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next()){
				metodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return metodo;
	}
	
	public boolean existeReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_METODOLOGIA WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologia|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<InvMetodologia> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<InvMetodologia> listProyectosMet = new ArrayList<InvMetodologia>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION, PROBLEMA, OBJETIVO, HIPOTESIS, VALDIEZ"
					+ " FROM ENOC.INV_METODOLOGIA " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				InvMetodologia metodologia = new InvMetodologia();
				metodologia.mapeaReg(rs);
				listProyectosMet.add(metodologia);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologiaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listProyectosMet;
	}
	
	public ArrayList<InvMetodologia> getListMetodologiaEmpleado (Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<InvMetodologia> listProyectosMet = new ArrayList<InvMetodologia>();
		Statement st	= conn.createStatement();
		ResultSet rs	= null;
		String comando	= "";
		
		try{
			comando = "SELECT PROYECTO_ID, HUMANOS, DISENO, MUESTRA, RECOLECCION,"
					+ " CONFIDENCIALIDAD, VINCULACION, RESPONSABLE, ACTIVIDADES, ENTREGABLE, PLAN, ORGANIZACION ,PROBLEMA, OBJETIVO, HIPOTESIS, VALIDEZ"
					+ " FROM ENOC.INV_METODOLOGIA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				InvMetodologia metodologia = new InvMetodologia();
				metodologia.mapeaReg(rs);
				listProyectosMet.add(metodologia);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvMetodologiaUtil|getListMetodologiaEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listProyectosMet;
	}
	
	
}
