// Bean folio la tabla folio Adm_Contacto
package  adm.alumno;

import java.sql.*;

public class AdmAlumDatos{	
	private String folio;
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String modalidad;
	private String carreraId;
	private String fNac;
	
	public AdmAlumDatos(){
		folio			= "";
		codigoPersonal 	= "";
		nombre 			= "";
		apellidoPaterno	= ""; 
		apellidoMaterno	= "";
		modalidad		= "";
		carreraId		= "";
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	/**
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}


	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
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
	 * @return the fNac
	 */
	public String getFNac() {
		return fNac;
	}


	/**
	 * @param nac the fNac to set
	 */
	public void setFNac(String nac) {
		fNac = nac;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio				= rs.getString("FOLIO");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre	 			= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");		
		modalidad			= rs.getString("MODALIDAD_ID");
		carreraId 			= rs.getString("CARRERA_ID");
		fNac 				= rs.getString("F_NACIMIENTO");
	}
	
	public void mapeaRegId( Connection conn, String folio) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT" +
			" ASOL.FOLIO AS FOLIO," +
			" AP.CODIGO_PERSONAL AS CODIGO_PERSONAL," +
			" AP.NOMBRE AS NOMBRE," +
			" AP.APELLIDO_PATERNO AS APELLIDO_PATERNO," +
			" AP.APELLIDO_MATERNO AS APELLIDO_MATERNO," +
			" TO_CHAR(AP.F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO," +
			" AA.MODALIDAD_ID AS MODALIDAD_ID," +
			" SALOMON.ADM_CARRERASOL(?) AS CARRERA_ID"+
			" FROM ENOC.ALUM_PERSONAL AP, ENOC.ALUM_ACADEMICO AA, SALOMON.ADM_SOLICITUD ASOL"+ 
			" WHERE ASOL.FOLIO =  TO_NUMBER(?,'999')" +
			" AND AP.CODIGO_PERSONAL = ASOL.MATRICULA" +
			" AND AA.CODIGO_PERSONAL = AP.CODIGO_PERSONAL");
		ps.setString(1, folio);
		ps.setString(2, folio);
		
		rs = ps.executeQuery();	    
		if (rs.next()){
			mapeaReg(rs); 
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		boolean 		ok 	= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_SOLICITUD "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999') AND MATRICULA IS NOT NULL");
			ps.setString(1,folio);				
			
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
	
public static String getNombreModalidad(Connection conn, String modalidadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?");
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("NOMBRE_MODALIDAD");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumDatos|getNombreModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
}