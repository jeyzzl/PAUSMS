package  aca.exa;

import java.sql.*;

public class ExaRed{
	private String redSocialId;	
	private String matricula;
	private String red;	
	private String fechaAct;
	private String eliminado;
	private String idRedSocial;
	
	public ExaRed(){
		redSocialId 		= "";
		matricula			= "";
		red 				= "";
		fechaAct			= "";
		eliminado			= "";
		idRedSocial			= "";
	}
	
	public String getRedSocialId() {
		return redSocialId;
	}

	public void setRedSocialId(String redSocialId) {
		this.redSocialId = redSocialId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
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

	public String getIdRedSocial() {
		return idRedSocial;
	}

	public void setIdRedSocial(String idRedSocial) {
		this.idRedSocial = idRedSocial;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		redSocialId 	= rs.getString("REDSOCIAL_ID");
		matricula		= rs.getString("MATRICULA");
		red				= rs.getString("RED");
		fechaAct 		= rs.getString("FECHAACTUALIZACION");
		eliminado 		= rs.getString("ELIMINADO");
		idRedSocial		= rs.getString("IDREDSOCIAL");
	}
	
	public void mapeaRegIdEstudio( Connection conn, String redSocialId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED, " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,redSocialId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public void mapeaRegId( Connection conn, String matricula) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}