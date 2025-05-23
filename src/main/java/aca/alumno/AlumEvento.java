// Clase para la tabla de Modulo
package aca.alumno;
import java.sql.*;

public class AlumEvento{	
	private String eventoId;
	private String eventoNombre;
	private String fecha;
	
	public AlumEvento(){
		eventoId			= "0";
		eventoNombre		= "-";
		fecha				= aca.util.Fecha.getHoy();
	}
	
	
	/**
	 * @return the eventoId
	 */
	public String getEventoId() {
		return eventoId;
	}

	/**
	 * @param eventoId the eventoId to set
	 */
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}


	/**
	 * @return the eventoNombre
	 */
	public String getEventoNombre() {
		return eventoNombre;
	}

	/**
	 * @param eventoNombre the eventoNombre to set
	 */
	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 			= rs.getString("EVENTO_ID");
		eventoNombre 		= rs.getString("EVENTO_NOMBRE");
		fecha				= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String eventoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA"+
				" FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = ?"); 
			ps.setInt(1, Integer.parseInt(eventoId));
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEventoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}