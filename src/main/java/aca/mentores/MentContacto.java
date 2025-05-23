/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author etorres
 *
 */
public class MentContacto {
	private String periodoId;
	private String mentorId;
	private String contactoId;
	private String codigoPersonal;
	private String motivoId;
	private String miAconsejado;
	private String fecha;
	private String fechaContacto;
	private String comentario;
	private String tipo;
	private String carreraId;
	private String motivos;
	
	public MentContacto(){
		periodoId		= "";
		mentorId		= "";
		contactoId		= "";
		codigoPersonal	= "";
		motivoId		= "";
		miAconsejado	= "";
		fecha			= "";
		fechaContacto	= "";
		comentario		= "";
		tipo			= "";
		carreraId		= "";
		motivos 		= "0";
	}
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getMentorId() {
		return mentorId;
	}

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	public String getContactoId() {
		return contactoId;
	}

	public void setContactoId(String contactoId) {
		this.contactoId = contactoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(String motivoId) {
		this.motivoId = motivoId;
	}

	public String getMiAconsejado() {
		return miAconsejado;
	}

	public void setMiAconsejado(String miAconsejado) {
		this.miAconsejado = miAconsejado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaContacto() {
		return fechaContacto;
	}

	public void setFechaContacto(String fechaContacto) {
		this.fechaContacto = fechaContacto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getMotivos() {
		return motivos;
	}

	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		periodoId		= rs.getString("PERIODO_ID");
		mentorId		= rs.getString("MENTOR_ID");
		contactoId		= rs.getString("CONTACTO_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		motivoId		= rs.getString("MOTIVO_ID");
		miAconsejado	= rs.getString("MI_ACONSEJADO");
		fecha			= rs.getString("FECHA");
		fechaContacto	= rs.getString("FECHA_CONTACTO");
		comentario		= rs.getString("COMENTARIO");
		tipo			= rs.getString("TIPO");
		carreraId		= rs.getString("CARRERA_ID");
		motivos			= rs.getString("MOTIVOS");
	}
	
	public void mapeaRegId(Connection conn, String idMentor) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CONTACTO_ID = TO_NUMBER(?, '99999')");
				
				ps.setString(1, periodoId);
				ps.setString(2, mentorId);
				ps.setString(3, contactoId);
				
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
		
}