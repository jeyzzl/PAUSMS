//Bean del Catalogo de Periodos

package aca.portafolio;

import java.sql.*;

public class PorEmpDocto {
	
	private String codigoPersonal;
	private String periodoId;
	private String documentoId;
	private String hojas;
	private String fecha;
	private String usuario;
	
	public PorEmpDocto(){		
		codigoPersonal 	= "";
		periodoId		= "";
		documentoId 	= "";
		hojas			= "";
		fecha			= "";
		usuario 		= ""; 
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
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
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
	 * @return the hojas
	 */
	public String getHojas() {
		return hojas;
	}

	/**
	 * @param hojas the hojas to set
	 */
	public void setHojas(String hojas) {
		this.hojas = hojas;
	}
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_EMPDOCTO( CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO) "+
				"VALUES( ?, ?, ?, ?,  TO_DATE(?,'DD/MM/YYYY'), ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			ps.setString(4, hojas);
			ps.setString(5, fecha);
			ps.setString(6, usuario);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_EMPDOCTO"+ 
				" SET HOJAS = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?");
			ps.setString(1, hojas);
			ps.setString(2, fecha);			
			ps.setString(3, usuario);
			ps.setString(4, codigoPersonal);
			ps.setString(5, periodoId);
			ps.setString(6, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateHojas(Connection conn, String codigoPersonal, String periodoID, String documentoId, String hoja ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_EMPDOCTO"+ 
				" SET HOJAS = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?");
			ps.setString(1, hojas);
			ps.setString(2, fecha);			
			ps.setString(3, usuario);
			ps.setString(4, codigoPersonal);
			ps.setString(5, periodoId);
			ps.setString(6, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM DANIEL.POR_EMPDOCTO "+ 
				"WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		periodoId			= rs.getString("PERIODO_ID");
		documentoId 		= rs.getString("DOCUMENTO_ID");
		hojas			 	= rs.getString("HOJAS");
		fecha 				= rs.getString("FECHA");
		usuario 			= rs.getString("USUARIO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String periodoId, String documentoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, PERIODO_ID, DOCUMENTO_ID, HOJAS, FECHA, USUARIO" +
					" FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERTSONAL = ? AND PERIODO_ID = ? DOCUMENTO_ID = ?");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ? ");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean maximoReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_EMPDOCTO WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND DOCUMENTO_ID = ? ");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getHojas(Connection conn,String codigoPersonal, String periodoId, String documentoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String hojas			= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(HOJAS,'-') AS HOJAS FROM DANIEL.POR_EMPDOCTO" +
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PERIODO_ID = ?" +
				" AND DOCUMENTO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, periodoId);
			ps.setString(3, documentoId);
			rs = ps.executeQuery();
			if (rs.next()){
				hojas = rs.getString("HOJAS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEmpDocto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hojas;
	}
	
}