package  aca.cultural;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompRegistro{
	private String eventoId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	private String usuario;
	private String facultadId;
	private String carreraId;
	private String fechaRegistro;
		
	public CompRegistro(){
		eventoId		= "";
		codigoPersonal	= "";
		fecha			= "";
		estado			= "";
		usuario			= "";
		facultadId		= "";
		carreraId		= "";
		fechaRegistro	= "";
	}	
	
	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}
	
	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId				= rs.getString("EVENTO_ID");
		fecha					= rs.getString("FECHA");
		usuario					= rs.getString("USUARIO");
		codigoPersonal			= rs.getString("CODIGO_PERSONAL");
		estado					= rs.getString("ESTADO");
		facultadId				= rs.getString("FACULTAD_ID");
		carreraId				= rs.getString("CARRERA_ID");
		fechaRegistro			= rs.getString("FECHA_REGISTRO");
	}
	
	public void mapeaRegId( Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EVENTO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, ESTADO, USUARIO, FACULTAD_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA_REGISTRO"+
			" FROM ENOC.COMP_REGISTRO"+ 
			" WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"'");
		
		rs = ps.executeQuery();
		if (rs.next()){			
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}