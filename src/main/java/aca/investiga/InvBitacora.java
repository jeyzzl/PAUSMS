package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Karelly
 *
 */
public class InvBitacora {
	private String proyectoId;
	private String folio;
	private String fecha;
	private String usuario;
	private String estadoOld;
	private String estadoNew;

	public InvBitacora(){
		proyectoId 		= "";
		folio			= "";
		fecha			= "";
		usuario			= "";
		estadoOld		= "";
		estadoNew		= "";
	}
	public String getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getEstadoOld() {
		return estadoOld;
	}
	
	public void setEstadoOld(String estadoOld) {
		this.estadoOld = estadoOld;
	}
	
	public String getEstadoNew() {
		return estadoNew;
	}
	
	public void setEstadoNew(String estadoNew) {
		this.estadoNew = estadoNew;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		proyectoId		= rs.getString("PROYECTO_ID");
		folio			= rs.getString("FOLIO");
		fecha			= rs.getString("FECHA");
		usuario			= rs.getString("USUARIO");
		estadoOld		= rs.getString("ESTADO_OLD");
		estadoNew		= rs.getString("ESTADO_NEW");		
	}
	
	public void mapeaRegId(Connection con, String proyectoId, String folio) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, FOLIO, TO_DATE(?, 'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, ESTADO_OLD, ESTADO_NEW"
					+ " FROM ENOC.INV_BITACORA"
					+ " WHERE PROYECTO_ID = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, proyectoId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvBitacora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	
	}

}