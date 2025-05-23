package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Manuel
 *
 */
public class InvPresupuesto {
	private String proyectoId;
	private String presupuestoId;
	private String presupuestoNombre;
	private String monto;
	private String tipo;
	
	public InvPresupuesto(){
		proyectoId		  = "0";
		presupuestoId 	  = "0";
		presupuestoNombre = "-";
		monto			  = "-";
		tipo			  = "-";
	}
	
	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getPresupuestoId() {
		return presupuestoId;
	}
	public void setPresupuestoId(String presupuestoId) {
		this.presupuestoId = presupuestoId;
	}
	public String getPresupuestoNombre() {
		return presupuestoNombre;
	}
	public void setPresupuestoNombre(String presupuestoNombre) {
		this.presupuestoNombre = presupuestoNombre;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		proyectoId			= rs.getString("PROYECTO_ID");
		presupuestoId		= rs.getString("PRESUPUESTO_ID");
		presupuestoNombre	= rs.getString("PRESUPUESTO_NOMBRE");
		tipo				= rs.getString("TIPO");
		monto				= rs.getString("MONTO");		
	}
	
	public boolean mapeaRegId(Connection conn, String proyectoId, String presupuestoId) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, PRESUPUESTO_ID, PRESUPUESTO_NOMBRE, MONTO, TIPO"
					+ " FROM ENOC.INV_PRESUPUESTO"
					+ " WHERE PROYECTO_ID = ? AND PRESUPUESTO_ID = ?");
			ps.setString(1, proyectoId);
			ps.setString(2, presupuestoId);
			
			rs = ps.executeQuery();
			if(rs.next()){			
				mapeaReg(rs);
				ok = true;							
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.investiga.InvPresupuesto|mapeaRegId|:"+ex);
		} finally {
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		}
		return ok;
	}
	
}
