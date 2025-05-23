package aca.leg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LegNotAlum {

	public String codigo;
	public String folio;
	public String notificacionId;
	public String fecha;
	public String estado;	
	
	public LegNotAlum(){	

		codigo			= "";
		folio			= "";
		notificacionId	= "";
		fecha			= "";
		estado			= "";	
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() { 
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the notificacionId
	 */
	public String getNotificacionId() {
		return notificacionId;
	}

	/**
	 * @param notificacionId the notificacionId to set
	 */
	public void setNotificacionId(String notificacionId) {
		this.notificacionId = notificacionId;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigo			= rs.getString("CODIGO");
		folio			= rs.getString("FOLIO");
		notificacionId	= rs.getString("NOTIFICACION_ID");
		fecha			= rs.getString("FECHA");
		estado			= rs.getString("ESTADO");		
	}
	
	public void mapeaRegId(Connection conn, String codigo, String folio) throws SQLException{
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, FOLIO," +
					" NOTIFICACION_ID," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
					" ESTADO" +
					" FROM ENOC.LEG_NOT_ALUM " + 
					" WHERE CODIGO = ?" +
					" AND FOLIO= TO_NUMBER(?,'99')");
			ps.setString(1, codigo);
			ps.setString(2, folio);
			rs=ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegNotAlum|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}