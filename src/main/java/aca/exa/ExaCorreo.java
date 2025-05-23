package  aca.exa;

import java.sql.*;

public class ExaCorreo{
	private String correoId;	
	private String matricula;
	private String correo;	
	private String fechaAct;
	private String eliminado;
	private String idCorreo;
	
	public ExaCorreo(){
		correoId 			= "";
		matricula			= "";
		correo 				= "";
		fechaAct			= "";
		eliminado			= "";
		idCorreo			= "";
	}

	public String getCorreoId() {
		return correoId;
	}

	public void setCorreoId(String correoId) {
		this.correoId = correoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public String getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(String idCorreo) {
		this.idCorreo = idCorreo;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		correoId	 	= rs.getString("CORREO_ID");
		matricula		= rs.getString("MATRICULA");
		correo			= rs.getString("CORREO");
		fechaAct 		= rs.getString("FECHAACTUALIZACION");
		eliminado 		= rs.getString("ELIMINADO");
		idCorreo		= rs.getString("IDCORREO");
	}
	
	public void mapeaRegIdCorreo( Connection conn, String correoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT CORREO_ID, MATRICULA, CORREO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDCORREO "+
				"FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,correoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaCorreoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}