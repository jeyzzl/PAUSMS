package  aca.exa;

import java.sql.*;

public class ExaBitacora{
	private String bitacoraId;	
	private String matricula;
	private String texto;	
	private String fecha;
	private String eliminado;
	private String idBitacora;
	
	public ExaBitacora(){
		bitacoraId 			= "";
		matricula			= "";
		texto 				= "";
		fecha				= "";
		eliminado			= "";
		idBitacora			= "";
	}

	public String getBitacoraId() {
		return bitacoraId;
	}

	public void setBitacoraId(String bitacoraId) {
		this.bitacoraId = bitacoraId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEliminado() {
		return eliminado;
	}

	public void setEliminado(String eliminado) {
		this.eliminado = eliminado;
	}

	public String getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(String idBitacora) {
		this.idBitacora = idBitacora;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		bitacoraId 		= rs.getString("BITACORA_ID");
		matricula		= rs.getString("MATRICULA");
		texto			= rs.getString("TEXTO");
		fecha 			= rs.getString("FECHA");
		eliminado 		= rs.getString("ELIMINADO");
		idBitacora		= rs.getString("IDBITACORA");
	}
	
	public void mapeaRegId( Connection conn, String matricula) throws SQLException{
		
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_BITACORA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}