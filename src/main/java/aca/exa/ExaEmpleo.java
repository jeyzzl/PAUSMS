package  aca.exa;

import java.sql.*;

public class ExaEmpleo{
	private String empleoId;	
	private String matricula;
	private String empresa;
	private String periodo;	
	private String fechaAct;
	private String eliminado;
	private String idEmpleo;
	
	public ExaEmpleo(){
		empleoId 			= "";
		matricula			= "";
		empresa 			= "";
		periodo				= "";
		fechaAct			= "";
		eliminado			= "";
		idEmpleo		= "";
	}

	public String getEmpleoId() {
		return empleoId;
	}

	public void setEmpleoId(String empleoId) {
		this.empleoId = empleoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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

	public String getIdEmpleo() {
		return idEmpleo;
	}

	public void setIdEmpleo(String idEmpleo) {
		this.idEmpleo = idEmpleo;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		empleoId 			= rs.getString("EMPLEO_ID");
		matricula 			= rs.getString("MATRICULA");
		empresa 			= rs.getString("EMPRESA");
		periodo 			= rs.getString("PERIODO");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idEmpleo 			= rs.getString("IDEMPLEO");
	}
	
	public void mapeaRegId( Connection conn, String matricula) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO "+
				"FROM ENOC.EXA_EMPLEO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}