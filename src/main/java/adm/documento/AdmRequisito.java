// Bean del Catalogo de Estados
package  adm.documento;


import java.sql.*;

public class AdmRequisito{
	private String carreraId;
	private String documentoId;
	private String modalidades;	
	private String requerido;
	
	public AdmRequisito(){
		carreraId 		= "";
		documentoId 	= "";
		modalidades		= "";
		requerido		= "";
			
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}


	/**
	 * @return the documentoId
	 */
	public String getDocumentoId() {
		return documentoId;
	}

	/**
	 * @param documentoId the documentoId to set
	 */
	public void setDocumentoId(String documentoId) {
		this.documentoId = documentoId;
	}


	/**
	 * @return the modalidades
	 */
	public String getModalidades() {
		return modalidades;
	}

	/**
	 * @param modalidades the modalidades to set
	 */
	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}
	
	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	public boolean insertReg(Connection Conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO SALOMON.ADM_REQUISITO"+
				" (CARRERA_ID, DOCUMENTO_ID, MODALIDADES)"+
				" VALUES( ?, TO_NUMBER(?,'99')),?)");
			
			ps.setString(1, carreraId);
			ps.setString(2, documentoId);
			ps.setString(3, modalidades);		
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmRequisito|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
			
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_REQUISITO" +
				" SET MODALIDADES = ?"+
				" WHERE CARRERA_ID = ?" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, modalidades);
			ps.setString(2, carreraId);
			ps.setString(3, documentoId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmRequisito|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM  SALOMON.ADM_REQUISITO"+
				" WHERE CARRERA_ID = ?" +
				" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, carreraId);
			ps.setString(2, documentoId);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmRequisito|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		carreraId 		= rs.getString("CARRERA_ID");
		documentoId	 	= rs.getString("DOCUMENTO_ID");
		modalidades		= rs.getString("MODALIDADES");
		requerido		= rs.getString("REQUERIDO");
	}
	
	public void mapeaRegId( Connection conn, String carreraId, String documentoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID, DOCUMENTO_ID, MODALIDADES, REQUERIDO"+
				" FROM SALOMON.ADM_REQUISITO" +
				" WHERE CARRERA_ID = ?" +
				" AND DOCUMENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,carreraId);
			ps.setString(2,documentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmRequisito|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_REQUISITO "+
							" WHERE CARRERA_ID = ? " +
							" AND DOCUMENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, carreraId);
			ps.setString(2, documentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmRequisito|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}