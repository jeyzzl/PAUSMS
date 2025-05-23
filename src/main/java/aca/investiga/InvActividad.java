package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Manuel
 *
 */
public class InvActividad {
	private String proyectoId;
	private String actividadId;
	private String actividadNombre;
	private String fechaIni;
	private String fechaFin;
	
	public InvActividad() {
		proyectoId 		= "0";
		actividadId 	= "0";
		actividadNombre = "-";
		fechaIni 		= "0";
		fechaFin 		= "0";
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getActividadId() {
		return actividadId;
	}
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}
	public String getActividadNombre() {
		return actividadNombre;
	}
	public void setActividadNombre(String actividadNombre) {
		this.actividadNombre = actividadNombre;
	}
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException {
		proyectoId 		= rs.getString("PROYECTO_ID");
		actividadId		= rs.getString("ACTIVIDAD_ID");
		actividadNombre	= rs.getString("ACTIVIDAD_NOMBRE");
		fechaIni 		= rs.getString("FECHA_INI");
		fechaFin		= rs.getString("FECHA_FIN");
	}
	
	public boolean mapeaRegId(Connection conn, String proyectoId, String actividadId) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT PROYECTO_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,  TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_FIN"
					+ " FROM ENOC.INV_ACTIVIDAD"
					+ " WHERE PROYECTO_ID = ? AND ACTIVIDAD_ID = TO_NUMBER(?,'999')");
			ps.setString(1, proyectoId);
			ps.setString(2, actividadId);
			
			rs = ps.executeQuery();		
			if(rs.next()){
				mapeaReg(rs);
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
}
