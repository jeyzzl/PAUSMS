package  aca.exa;

import java.sql.*;

public class ExaIglesia{
	private String iglesiaId;	
	private String matricula;
	private String iglesia;
	private String funcion;	
	private String fechaAct;
	private String eliminado;
	private String idEgresadoIglesia;
	
	public ExaIglesia(){
		iglesiaId 			= "";
		matricula			= "";
		iglesia 			= "";
		funcion				= "";
		fechaAct			= "";
		eliminado			= "";
		idEgresadoIglesia	= "";
	}

	public String getIglesiaId() {
		return iglesiaId;
	}

	public void setIglesiaId(String iglesiaId) {
		this.iglesiaId = iglesiaId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIglesia() {
		return iglesia;
	}

	public void setIglesia(String iglesia) {
		this.iglesia = iglesia;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
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

	public String getIdEgresadoIglesia() {
		return idEgresadoIglesia;
	}

	public void setIdEgresadoIglesia(String idEgresadoIglesia) {
		this.idEgresadoIglesia = idEgresadoIglesia;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		iglesiaId 			= rs.getString("IGLESIA_ID");
		matricula 			= rs.getString("MATRICULA");
		iglesia 			= rs.getString("IGLESIA");
		funcion 			= rs.getString("FUNCION");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idEgresadoIglesia 	= rs.getString("IDEGRESADOIGLESIA");
	}
	
	public void mapeaRegIdIglesia( Connection conn, String iglesiaId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "+
				"FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,iglesiaId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "+
				"FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaIglesiaUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}