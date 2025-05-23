package  aca.exa;

import java.sql.*;

public class ExaAlumEvento{
	private String alumEventoId;	
	private String matricula;
	private String idEvento;
	private String fechaActualizacion;	
	private String eliminado;
	private String idEventoAsistido;
	
	public ExaAlumEvento(){
		alumEventoId 		= "";
		matricula			= "";
		idEvento 			= "";
		fechaActualizacion	= "";
		eliminado	 		= "";
		idEventoAsistido	= "";
	}
	
	public String getAlumEventoId() {
		return alumEventoId;
	}

	public void setAlumEventoId(String alumEventoId) {
		this.alumEventoId = alumEventoId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdEventoAsistido() {
		return idEventoAsistido;
	}

	public void setIdEventoAsistido(String idEventoAsistido) {
		this.idEventoAsistido = idEventoAsistido;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		alumEventoId 		= rs.getString("ALUMEVENTO_ID");
		matricula 			= rs.getString("MATRICULA");
		idEvento 			= rs.getString("IDEVENTO");
		fechaActualizacion 	= rs.getString("FECHAACTUALIZACION");
		eliminado 			= rs.getString("ELIMINADO");
		idEventoAsistido 	= rs.getString("IDEVENTOASISTIDO");
	}
	
	public void mapeaRegId( Connection conn, String alumEventoId) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO "+
				"FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,alumEventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaAlumEventoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}