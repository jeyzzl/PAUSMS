/**
 * 
 */
package aca.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class MapaNuevoBibliografia {
	private String cursoId;
	private String bibliografiaId;
	private String bibliografia;
	private String tipo;
	private String referencia;
	
	public MapaNuevoBibliografia(){
		cursoId			= "";
		bibliografiaId	= "";
		bibliografia	= "";
		tipo			= "";
		referencia		= "";
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

	/**
	 * @return the bibliografiaId
	 */
	public String getBibliografiaId() {
		return bibliografiaId;
	}

	/**
	 * @param bibliografiaId the bibliografiaId to set
	 */
	public void setBibliografiaId(String bibliografiaId) {
		this.bibliografiaId = bibliografiaId;
	}

	/**
	 * @return the bibliografia
	 */
	public String getBibliografia() {
		return bibliografia;
	}

	/**
	 * @param bibliografia the bibliografia to set
	 */
	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId				= rs.getString("CURSO_ID")==null?"0":rs.getString("CURSO_ID");
		bibliografiaId		= rs.getString("BIBLIOGRAFIA_ID")==null?"0":rs.getString("BIBLIOGRAFIA_ID");
		bibliografia		= rs.getString("BIBLIOGRAFIA")==null?"0":rs.getString("BIBLIOGRAFIA");
		tipo				= rs.getString("TIPO")==null?"-":rs.getString("TIPO");
		referencia			= rs.getString("REFERENCIA")==null?"-":rs.getString("REFERENCIA");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String bibliografiaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
					" REFERENCIA" +
					" FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')");
			
			ps.setString(1, cursoId);
			ps.setString(2, bibliografiaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}

}