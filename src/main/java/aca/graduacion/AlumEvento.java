// Clase para la tabla de Modulo
package aca.graduacion;
import java.sql.*;

public class AlumEvento{	
	private String eventoId;
	private String nombreEvento;
	private String fechaEvento;
	
	public AlumEvento(){
		eventoId			= "";
		nombreEvento		= "";
		fechaEvento			= "";
	}	
	
	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	
	public String getFechaEvento() {
		return fechaEvento;
	}
	
	public void setFechaEvento(String fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	
	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 			= rs.getString("EVENTO_ID");
		nombreEvento 		= rs.getString("NOMBRE_EVENTO");
		fechaEvento 		= rs.getString("FECHA_EVENTO");
	}
	
	public void mapeaRegId( Connection conn, String evento_Id ) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT"+
				" EVENTO_ID, NOMBRE_EVENTO, FECHA_EVENTO"+
				" FROM ENOC.ALUM_EVENTO WHERE EVENTO_ID = ?"); 
			ps.setInt(1, Integer.parseInt(evento_Id));
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.AlumEvento|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}