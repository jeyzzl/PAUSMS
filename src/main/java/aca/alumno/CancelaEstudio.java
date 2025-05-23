package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelaEstudio {
	private String codigoPersonal;
	private String planId;
	private String usuario;
	private String fecha;
	private String comentario;
	private String estado;
	
	public CancelaEstudio(){
		codigoPersonal	= "";
		planId			= "";
		usuario			= "";
		fecha			= "";
		comentario		= "";
		estado			= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		planId			= rs.getString("PLAN_ID");
		usuario			= rs.getString("USUARIO");
		fecha	 		= rs.getString("FECHA");
		comentario		= rs.getString("COMENTARIO");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, PLAN_ID, USUARIO, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, COMENTARIO, ESTADO "+
				"FROM ENOC.CANCELA_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.CancelaEstudioUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}