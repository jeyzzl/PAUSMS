package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmEstudio {
	private String folio;
	private String Id;
	private String titulo;
	private String institucion;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String completo;
		
	public AdmEstudio(){
		folio 			= "";
		Id		 		= "";
		titulo	 		= "";
		institucion		= "";
		paisId 			= "";
		estadoId 		= "";
		ciudadId 		= "";
		completo		= "";
		
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
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the institucion
	 */
	public String getInstitucion() {
		return institucion;
	}

	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the paisId
	 */
	public String getPaisId() {
		return paisId;
	}

	/**
	 * @param paisId the paisId to set
	 */
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	/**
	 * @return the estadoId
	 */
	public String getEstadoId() {
		return estadoId;
	}

	/**
	 * @param estadoId the estadoId to set
	 */
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	/**
	 * @return the ciudadId
	 */
	public String getCiudadId() {
		return ciudadId;
	}

	/**
	 * @param ciudadId the ciudadId to set
	 */
	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	/**
	 * @return the completo
	 */
	public String getCompleto() {
		return completo;
	}

	/**
	 * @param completo the completo to set
	 */
	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_ESTUDIO"+ 
				"(FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO ) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?,?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
				" ? )");
			ps.setString(1, folio);
			ps.setString(2, Id);
			ps.setString(3, titulo);
			ps.setString(4, institucion);
			ps.setString(5, paisId);
			ps.setString(6, estadoId);
			ps.setString(7, ciudadId);
			ps.setString(8, completo);
	
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudio|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ESTUDIO " + 
					" SET TITULO = ? , " +
					" INSTITUCION = ? , " +
					" PAIS_ID = TO_NUMBER(?,'999'), " +
					" ESTADO_ID = TO_NUMBER(?,'999')," +
					" CIUDAD_ID = TO_NUMBER(?,'999')," +
					" COMPLETO = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')" +
					" AND ID = TO_NUMBER(?,'99')");
			
			ps.setString(1,  titulo);
			ps.setString(2,  institucion);
			ps.setString(3,  paisId);
			ps.setString(4,  estadoId);
			ps.setString(5,  ciudadId);
			ps.setString(6,  completo);
			ps.setString(7,  folio);
			ps.setString(8,  Id);

			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudio|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ESTUDIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999') " +
					" AND ID = TO_NUMBER(?,'99') ");
			ps.setString(1, folio);
			ps.setString(2, Id);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudio|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		Id				= rs.getString("ID");
		titulo 			= rs.getString("TITULO");
		institucion		= rs.getString("INSTITUCION");
		paisId			= rs.getString("PAIS_ID");
		estadoId 		= rs.getString("ESTADO_ID");
		ciudadId		= rs.getString("CIUDAD_ID");
		completo		= rs.getString("COMPLETO");
	}
	
	public void mapeaRegId( Connection conn, String folio, String Id ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, " +
				" ESTADO_ID, CIUDAD_ID, COMPLETO " +
				" FROM SALOMON.ADM_ESTUDIO "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
				" AND ID = TO_NUMBER(?,'99')");
		ps.setString(1, folio);
		ps.setString(2, Id);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ESTUDIO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')" +
					" AND ID = TO_NUMBER(?,'99')");
			ps.setString(1, folio);
			ps.setString(2, Id);
						
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String Folio) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(ID)+1 MAXIMO FROM SALOMON.ADM_ESTUDIO WHERE FOLIO = ?"); 
			ps.setString(1, Folio);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudio|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	

}