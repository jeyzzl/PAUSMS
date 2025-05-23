package  aca.exa;

import java.sql.*;

public class ExaDireccion{
	private String dirId;	
	private String matricula;
	private String ciudad;
	private String direccion;	
	private String edoId;
	private String paisId;
	private String cp;
	private String fechaAct;
	private String eliminado;
	private String idDir;
	
	public ExaDireccion(){
		dirId 			= "";
		matricula		= "";
		ciudad 			= "";
		direccion		= "";
		edoId			= "";
		paisId 			= "";
		cp				= "";
		fechaAct		= "";
		eliminado		= "";
		idDir			= "";
	}
	
	public String getDirId() {
		return dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEdoId() {
		return edoId;
	}

	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
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

	public String getIdDir() {
		return idDir;
	}

	public void setIdDir(String idDir) {
		this.idDir = idDir;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		dirId 				= rs.getString("DIRECCION_ID");
		matricula 			= rs.getString("MATRICULA");
		ciudad 				= rs.getString("CIUDAD");
		direccion 			= rs.getString("DIRECCION");
		edoId 				= rs.getString("ESTADO_ID");
		paisId				= rs.getString("PAIS_ID");
		cp		 			= rs.getString("CP");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado			= rs.getString("ELIMINADO");
		idDir		 		= rs.getString("IDDIRECCION");
	}
	
	public void mapeaRegId( Connection conn, String matricula) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION "+
				"FROM ENOC.EXA_DIRECCION WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}