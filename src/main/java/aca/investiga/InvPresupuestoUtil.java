package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InvPresupuestoUtil {
	
	public boolean insertReg(Connection conn, InvPresupuesto inv) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_PRESUPUESTO (PROYECTO_ID,"
					+ " PRESUPUESTO_ID, PRESUPUESTO_NOMBRE, TIPO, MONTO)"
					+ " VALUES (?, TO_NUMBER(?,'99'), ?, ?, TO_NUMBER(?,'999999999'))");
			
			ps.setString(1, inv.getProyectoId());
			ps.setString(2, inv.getPresupuestoId());
			ps.setString(3, inv.getPresupuestoNombre());
			ps.setString(4, inv.getTipo());
			ps.setString(5, inv.getMonto());	
			
			
			if (ps.executeUpdate() == 1){
				ok = true;				
			}else{
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|insertReg|:"+ex);
		}finally{
			if(ps != null) ps.close(); 
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvPresupuesto inv) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE ENOC.INV_PRESUPUESTO"
					+ " SET PRESUPUESTO_NOMBRE = ?, "
					+ " TIPO = ?, "
					+ " MONTO = TO_NUMBER(?,'999999999')"
					+ " WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?");
			
			ps.setString(1, inv.getPresupuestoNombre());
			ps.setString(2, inv.getTipo());
			ps.setString(3, inv.getMonto());
			ps.setString(4, inv.getProyectoId());
			ps.setString(5, inv.getPresupuestoId());
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|updateReg|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteRegId(Connection conn, String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, presupuestoId);
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|deleteRegId|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|deleteReg|:"+ex);
		} finally {
			if (ps != null) ps.close();
		}
		
		return ok;
	}
	
	public boolean mapeaRegId(Connection conn, String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, PRESUPUESTO_ID, "
					+ "PRESUPUESTO_NOMBRE, MONTO, TIPO FROM ENOC.INV_PRESUPUESTO "
					+ "WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, presupuestoId);
			
			rs = ps.executeQuery();
			
			if (ps.executeUpdate() == 1) {
				ok = true;				
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|mapeaRegId|:"+ex);
		} finally {
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		}
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_PRESUPUESTO"
					+ " WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, presupuestoId);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				ok = true;
			else
				ok = false;
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|existeRegId|:"+ex);
		} finally {
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String proyectoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_PRESUPUESTO WHERE "
					+ "PROYECTO_ID = ?");
			ps.setString(1, proyectoId);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				ok = true;
			else
				ok = false;
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|existeReg|:"+ex);
		} finally {
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String proyectoId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;

		System.out.println(proyectoId);
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(PRESUPUESTO_ID)+1) AS MAXIMO FROM ENOC.INV_PRESUPUESTO WHERE PROYECTO_ID = "+proyectoId);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("NEXT : "+rs.getString("MAXIMO"));
				maximo = rs.getString("MAXIMO");
			}
			if(maximo == null) {
				System.out.println("NULL");
				maximo = "1";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvPresupuesto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	public ArrayList<InvPresupuesto> getListPresupuesto(Connection conn, String proyectoId, String orden ) throws SQLException{
		
		ArrayList<InvPresupuesto> lista	= new ArrayList<InvPresupuesto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
	 
		try{
			comando = " SELECT PROYECTO_ID, PRESUPUESTO_ID, PRESUPUESTO_NOMBRE, MONTO, TIPO"
					+ " FROM ENOC.INV_PRESUPUESTO"
					+ " WHERE PROYECTO_ID = '"+proyectoId+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				InvPresupuesto objeto = new InvPresupuesto();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvPresupuesto|getListPresupuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
}
