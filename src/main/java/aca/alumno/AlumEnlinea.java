package aca.alumno;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumEnlinea {
	 
	private String codigoPersonal;
	private String cargaId;
 	private String usuario;
 	private String solicitud;
 	private String comentarios;
 	private String fecha;
 	private String residenciaId; 	
 	private String coordinador;
 	private String estado;
 	
 	public AlumEnlinea(){
 		codigoPersonal	= "";
 		cargaId			= "";
 		usuario			= "";
 		solicitud		= "";
 		comentarios		= "";
 		fecha			= "";
 		estado			= "";
 		residenciaId 	= "-";
 		coordinador		= "0000000";
 	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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

	/**
	 * @return the residenciaId
	 */
	public String getResidenciaId() {
		return residenciaId;
	}

	/**
	 * @param residenciaId the residenciaId to set
	 */
	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}

	/**
	 * @return the coordinador
	 */
	public String getCoordinador() {
		return coordinador;
	}

	/**
	 * @param coordinador the coordinador to set
	 */
	public void setCoordinador(String coordinador) {
		this.coordinador = coordinador;
	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
 		cargaId		 		= rs.getString("CARGA_ID");
 		usuario				= rs.getString("USUARIO");
 		solicitud			= rs.getString("SOLICITUD");	
 		comentarios			= rs.getString("COMENTARIOS");
 		fecha				= rs.getString("FECHA");
 		estado				= rs.getString("ESTADO");
 		residenciaId		= rs.getString("RESIDENCIA_ID");
 		coordinador			= rs.getString("COORDINADOR");
 	}
	
 	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId ) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT"+
	 			" CODIGO_PERSONAL, CARGA_ID, USUARIO, SOLICITUD, COMENTARIOS, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO, RESIDENCIA_ID, COORDINADOR" +
	 			" FROM ENOC.ALUM_ENLINEA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?"); 
	 		ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){	 			
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AlumEnlineaUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 	}
 }