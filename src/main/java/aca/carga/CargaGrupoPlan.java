package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaGrupoPlan{
	private String cursoCargaId;
	private String estudios;
	private String ocupacion;
	private String lugar;
	private String horario;
	private String oficina;
	private String telefono;
	private String tiempo;
	private String atencion;
	private String correo;
	private String descripcion;
	private String perspectiva;
	private String estado;
	
	
	public CargaGrupoPlan(){
	    cursoCargaId  = "";	
		estudios      = "";
		ocupacion     = "";
		lugar         = "";
		horario       = "";
		oficina       = "";
		telefono      = "";
		tiempo        = "";
		atencion      = "";
	    correo	      = "";
		descripcion   = "";
		perspectiva   = "";
		estado        = "";
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getAtencion() {
		return atencion;
	}

	public void setAtencion(String atencion) {
		this.atencion = atencion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(String perspectiva) {
		this.perspectiva = perspectiva;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		estudios 			= rs.getString("ESTUDIOS");
		ocupacion 			= rs.getString("OCUPACION");
		lugar 				=  rs.getString("LUGAR");
		horario 			=  rs.getString("HORARIO");
		oficina				=  rs.getString("OFICINA");
		telefono 			= rs.getString("TELEFONO");
		tiempo 				= rs.getString("TIEMPO");
		atencion 			= rs.getString("ATENCION");
	    correo				= rs.getString("CORREO");
	    descripcion		    = rs.getString("DESCRIPCION");
	    perspectiva		    = rs.getString("PERSPECTIVA");
	    estado		        = rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, ESTUDIOS, OCUPACION, LUGAR," +
					" HORARIO, OFICINA, TELEFONO, TIEMPO, ATENCION, CORREO, DESCRIPCION, PERSPECTIVA, ESTADO "+
				    " FROM ENOC.CARGA_GRUPO_PLAN WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoPlan|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
		
}