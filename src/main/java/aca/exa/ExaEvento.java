package  aca.exa;

import java.sql.*;

public class ExaEvento{
	private String eventoId;	
	private String nombre;
	private String lugar;	
	private String fechaEvento;
	private String fechaAct;
	private String eliminado;
	private String idEvento;
	
	public ExaEvento(){
		eventoId 			= "";
		nombre 				= "";
		lugar				= "";
		fechaEvento	 		= "";
		fechaAct			= "";
		eliminado			= "";
		idEvento			= "";
	}

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(String fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getFechaAct() {
		return fechaAct;
	}

	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 			= rs.getString("EVENTO_ID");
		nombre 				= rs.getString("NOMBRE");
		lugar 				= rs.getString("LUGAR");
		fechaEvento			= rs.getString("FECHAEVENTO");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idEvento 			= rs.getString("IDEVENTO");
	}
	
	public void mapeaRegIdEvento( Connection conn, String eventoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID, NOMBRE, LUGAR, " +
					" TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTO "+
				"FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,eventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}