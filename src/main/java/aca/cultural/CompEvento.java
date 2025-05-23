// Bean de Alum_Foto
package  aca.cultural;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompEvento{
	private String eventoId;
	private String eventoNombre;
	private String fecha;
	private String descripcion;
	private String capacidad;
	private String lugar;
	private String estado;
	private String nivel;
		
	public CompEvento(){
		eventoId		="";
		eventoNombre	="";
		fecha			="";
		descripcion		="";
		capacidad 		="";
		lugar			="";
		estado			="";
		nivel			="";	
	}	
	
	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}
	
	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId			= rs.getString("EVENTO_ID");
		eventoNombre		= rs.getString("EVENTO_NOMBRE");
		fecha				= rs.getString("FECHA");
		descripcion			= rs.getString("DESCRIPCION");
		capacidad 			= rs.getString("CAPACIDAD");
		lugar				= rs.getString("LUGAR");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String eventoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, DESCRIPCION, CAPACIDAD, LUGAR, ESTADO"+
			" FROM ENOC.COMP_EVENTO"+ 
			" WHERE EVENTO_ID = TO_NUMBER(?,'99')");
		ps.setString(1, eventoId);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}