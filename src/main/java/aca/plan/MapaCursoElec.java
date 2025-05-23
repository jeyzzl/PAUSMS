// Bean del Catalogo Cursos Electivos
package  aca.plan;

import java.sql.*;

public class MapaCursoElec{
	
	private String cursoId;
	private String folio;
	private String cursoElec;	
	private String cursoNombre;
	
	public MapaCursoElec(){
		cursoId		= "";		
		folio		= "";
		cursoElec 	= "";
		cursoNombre	= "";
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the cursoElec
	 */
	public String getCursoElec() {
		return cursoElec;
	}

	/**
	 * @param cursoElec the cursoElec to set
	 */
	public void setCursoElec(String cursoElec) {
		this.cursoElec = cursoElec;
	}

	/**
	 * @return the cursoNombre
	 */
	public String getCursoNombre() {
		return cursoNombre;
	}

	/**
	 * @param cursoNombre the cursoNombre to set
	 */
	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoId 			= rs.getString("CURSO_ID");
		folio	 			= rs.getString("FOLIO");
		cursoElec 			= rs.getString("CURSO_ELEC");
		cursoNombre 		= rs.getString("CURSO_NOMBRE");
	}
	
	public void mapeaRegId( Connection conn, String cursoId, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID = ? AND FOLIO = TO_NUMBER(?,'9999') ");
				ps.setString(1, cursoId);
				ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.ElectivaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}

}