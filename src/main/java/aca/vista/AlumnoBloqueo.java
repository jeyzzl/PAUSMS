package aca.vista;

import java.sql.*;

public class AlumnoBloqueo {
	private String codigoPersonal;
	private String cargaId;
	private String modalidadId;
	private String facultadId;
	private String carreraId;	
		
	public AlumnoBloqueo(){
		codigoPersonal	= "";
		cargaId	= "";
		modalidadId	= "";
		facultadId			= "";
		carreraId			= "";		
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
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}


	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}


	/**
	 * @return the modalidadId
	 */
	public String getModalidadId() {
		return modalidadId;
	}


	/**
	 * @param modalidadId the modalidadId to set
	 */
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}


	/**
	 * @return the facultadId
	 */
	public String getFacultadId() {
		return facultadId;
	}


	/**
	 * @param facultadId the facultadId to set
	 */
	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
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


	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		cargaId				= rs.getString("CARGA_ID");
		modalidadId 		= rs.getString("MODALIDAD_ID");
		facultadId			= rs.getString("FACULTAD_ID");
		carreraId			= rs.getString("CARRERA_ID");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID, FACULTAD_ID, CARRERA_ID"
					+ " FROM ENOC.ALUMNO_BLOQUEO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoBloqueo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ALUMNO_BLOQUEO WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoBloqueo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}