package  aca.exa;

import java.sql.*;

public class ExaEstudio{
	private String estudioId;	
	private String matricula;
	private String estudios;
	private String institucion;	
	private String periodo;
	private String fechaAct;
	private String eliminado;
	private String idEstudio;
	
	public ExaEstudio(){
		estudioId 			= "";
		matricula			= "";
		estudios 			= "";
		institucion			= "";
		periodo	 			= "";
		fechaAct			= "";
		eliminado			= "";
		idEstudio	 		= "";
	
	}

	public String getEstudioId() {
		return estudioId;
	}

	public void setEstudioId(String estudioId) {
		this.estudioId = estudioId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
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

	public String getIdEstudio() {
		return idEstudio;
	}

	public void setIdEstudio(String idEstudio) {
		this.idEstudio = idEstudio;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		estudioId 			= rs.getString("ESTUDIO_ID");
		matricula 			= rs.getString("MATRICULA");
		estudios 			= rs.getString("ESTUDIOS");
		institucion 		= rs.getString("INSTITUCION");
		periodo 			= rs.getString("PERIODO");
		fechaAct 			= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idEstudio 			= rs.getString("IDESTUDIO");
	}
	
	public void mapeaRegIdEstudio( Connection conn, String estudioId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, " +
					" PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "+
				"FROM ENOC.EXA_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,estudioId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, " +
					" PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "+
				"FROM ENOC.EXA_ESTUDIO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEstudioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}