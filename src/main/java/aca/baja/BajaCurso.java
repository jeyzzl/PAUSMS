/**
 * 
 */
package aca.baja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class BajaCurso {
	private String bajaId;
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String creditos;
	
	public BajaCurso(){
		bajaId			= "";
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		creditos		= "";
	}

	/**
	 * @return the bajaId
	 */
	public String getBajaId() {
		return bajaId;
	}

	/**
	 * @param bajaId the bajaId to set
	 */
	public void setBajaId(String bajaId) {
		this.bajaId = bajaId;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the creditos
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		bajaId			= rs.getString("BAJA_ID");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		cursoId			= rs.getString("CURSO_ID");
		creditos		= rs.getString("CREDITOS");
	}
	
	public void mapeaRegId(Connection conn, String bajaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT BAJA_ID, CODIGO_PERSONAL," +
					" CURSO_CARGA_ID, CURSO_ID, CREDITOS" +
					" FROM ENOC.BAJA_CURSO WHERE BAJA_ID = ? "); 
			
			ps.setString(1, bajaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaCursoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}