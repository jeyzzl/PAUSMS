// Bean folio la tabla folio Adm_Contacto
package  aca.admision;

import java.sql.*;

public class AdmSolicitud{	
	private String folio;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String nacionalidad;
	private String fechaNac;
	private String estadoCivil;
	private String genero;
	private String religionId;
	private String bautizado;
	private String clave;
	private String fecha;
	private String matricula;
	private String email;
	private String estado;
	private String asesorId;
	private String curp;
	private String fechaIngreso;
	private String agente;
	private String asesorSec;
	private String telefono;
	
	public AdmSolicitud(){
		folio 			= "";
		nombre 			= "";
		apellidoPaterno	= ""; 
		apellidoMaterno	= "";
		paisId			= "";
		estadoId 		= "";
		ciudadId		= "";
		nacionalidad	= "";
		fechaNac		= "";
		estadoCivil		= "";
		genero			= "";
		religionId		= "";
		bautizado 		= "";
		clave			= "";
		fecha 			= "";
		matricula 		= "";
		email			= "";
		estado 			= "";
		asesorId		= "";
		curp			= "";
		fechaIngreso	= "";
		agente 			= "";
		asesorSec 		= "";
		telefono 		= "-";
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
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
	 * @return the nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}


	/**
	 * @param nacionalidad the nacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	/**
	 * @return the fechaNac
	 */
	public String getFechaNac() {
		return fechaNac;
	}


	/**
	 * @param fechaNac the fechaNac to set
	 */
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}


	/**
	 * @return the estadoCivil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}


	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}


	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}


	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}


	/**
	 * @return the religionId
	 */
	public String getReligionId() {
		return religionId;
	}


	/**
	 * @param religionId the religionId to set
	 */
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}


	/**
	 * @return the bautizado
	 */
	public String getBautizado() {
		return bautizado;
	}


	/**
	 * @param bautizado the bautizado to set
	 */
	public void setBautizado(String bautizado) {
		this.bautizado = bautizado;
	}


	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}


	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAsesorId() {
		return asesorId;
	}

	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}
	
	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getAsesorSec() {
		return asesorSec;
	}

	public void setAsesorSec(String asesorSec) {
		this.asesorSec = asesorSec;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @param conn the connection to set
	 * @return si grabó o no
	 */
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_SOLICITUD"+ 
				" (FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD," +
				" FECHA_NAC, ESTADOCIVIL, GENERO," +
				" RELIGION_ID, BAUTIZADO, CLAVE, FECHA, MATRICULA, EMAIL, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO)"+
				" VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?," +
				" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
				" TO_NUMBER(?,'99'), ?, ?, now(), ?, ?, ?, ?, ?, ?)");
			ps.setString( 1, folio);
			ps.setString( 2, nombre);
			ps.setString( 3, apellidoPaterno);
			ps.setString( 4, apellidoMaterno);
			ps.setString( 5, paisId);
			ps.setString( 6, estadoId);
			ps.setString( 7, ciudadId);
			ps.setString( 8, nacionalidad);
			ps.setString( 9, fechaNac);
			ps.setString(10, estadoCivil);
			ps.setString(11, genero);
			ps.setString(12, religionId);
			ps.setString(13, bautizado);
			ps.setString(14, clave);
			ps.setString(15, matricula);
			ps.setString(16, email);
			ps.setString(17, estado);
			ps.setString(18, asesorId);
			ps.setString(19, curp);
			ps.setString(20, fechaIngreso);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean insertRegistro(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_SOLICITUD" + 
					"(FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, GENERO, EMAIL, USUARIO, CLAVE, ESTADO, CURP, FECHA_INGRESO)" +
					" VALUES (TO_NUMBER(?,'99999999'), ?, ?, ?, ?, ?, ?, 1, ?, ?)");
					
			ps.setString(1, folio);
			ps.setString(2, nombre);
			ps.setString(3, apellidoPaterno);
			ps.setString(4, apellidoMaterno);
			ps.setString(5, genero);
			ps.setString(6, email);
			ps.setString(7, clave);
			ps.setString(8, curp);
			ps.setString(9, fechaIngreso);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|insertRegistro|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		
		try{
  		   ps = conn.prepareStatement("UPDATE SALOMON.ADM_SOLICITUD " + 
					" SET" +
					" NOMBRE = ?," +
					" APELLIDO_PATERNO = ?," +
					" APELLIDO_MATERNO = ?," +
					" PAIS_ID = TO_NUMBER(?,'999')," +
					" ESTADO_ID = TO_NUMBER(?,'999')," +
					" CIUDAD_ID = TO_NUMBER(?,'999')," +
					" NACIONALIDAD = TO_NUMBER(?,'999')," +
					" FECHA_NAC = TO_DATE(?,'DD/MM/YYYY')," +
					" ESTADOCIVIL = ?," +
					" GENERO = ?," +
					" RELIGION_ID = TO_NUMBER(?,'99')," +
					" BAUTIZADO = ?," +
					" EMAIL = ?," +
					" CLAVE = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" MATRICULA = ?," +
					" ESTADO = ?, ASESOR_ID = ?, CURP = ?," +
					" FECHA_INGRESO = ? " +
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString( 1, nombre);
			ps.setString( 2, apellidoPaterno);
			ps.setString( 3, apellidoMaterno);
			ps.setString( 4, paisId);
			ps.setString( 5, estadoId);
			ps.setString( 6, ciudadId);
			ps.setString( 7, nacionalidad);
			ps.setString( 8, fechaNac);
			ps.setString( 9, estadoCivil);
			ps.setString(10, genero);
			ps.setString(11, religionId);
			ps.setString(12, bautizado);			
			ps.setString(13, email);			
			ps.setString(14, clave);
			ps.setString(15, fecha);
			ps.setString(16, matricula);
			ps.setString(17, estado);
			ps.setString(18, asesorId);
			ps.setString(19, curp);
			ps.setString(20, fechaIngreso);
			ps.setString(21, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateRegistro(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_SOLICITUD " + 
					" SET" +
					" NOMBRE = ?," +
					" APELLIDO_PATERNO = ?," +
					" APELLIDO_MATERNO = ?," +
					" GENERO = ?," +					
					" EMAIL = ?," +
					" TELEFONO = ?," +
					" CLAVE = ?, ESTADO=?, CURP = ?, FECHA_INGRESO = ? " + 					
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, nombre);
			ps.setString(2, apellidoPaterno);
			ps.setString(3, apellidoMaterno);			
			ps.setString(4, genero);						
			ps.setString(5, email);			
			ps.setString(6, telefono);
			ps.setString(7, clave);	
			ps.setString(8, estado);
			ps.setString(9, curp);
			ps.setString(10, fechaIngreso);
			ps.setString(11, folio);
			
			if(ps.executeUpdate()==1){
				ok = true;				
			}
			else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|updateRegistro|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_SOLICITUD "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 				= rs.getString("FOLIO");
		nombre	 			= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno	 	= rs.getString("APELLIDO_MATERNO");
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId			= rs.getString("CIUDAD_ID");
		nacionalidad		= rs.getString("NACIONALIDAD");
		fechaNac			= rs.getString("FECHA_NAC");
		estadoCivil			= rs.getString("ESTADOCIVIL");
		genero				= rs.getString("GENERO");
		religionId			= rs.getString("RELIGION_ID");
		bautizado			= rs.getString("BAUTIZADO");
		email				= rs.getString("EMAIL");	
		clave				= rs.getString("CLAVE");
		fecha				= rs.getString("FECHA");
		matricula			= rs.getString("MATRICULA");
		estado				= rs.getString("ESTADO");
		asesorId			= rs.getString("ASESOR_ID");
		curp				= rs.getString("CURP");
		fechaIngreso		= rs.getString("FECHA_INGRESO");
		agente				= rs.getString("AGENTE");
		asesorSec			= rs.getString("ASESOR_SEC");
		telefono			= rs.getString("TELEFONO");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
			" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC," +
			" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, CLAVE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
			" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC, TELEFONO"+
			" FROM SALOMON.ADM_SOLICITUD"+ 
			" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_SOLICITUD "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999')");
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
	
	
	public boolean existeUsuario(Connection conn, String usuario, String clave) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, FECHA_NAC," +
					" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, CLAVE, FECHA," +
					" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC,TELEFONO "+					
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE USUARIO_ID = ?"+
					" AND CLAVE=?");
			ps.setString(1, usuario);
			ps.setString(2, clave);
			
			rs= ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|existeUsuario1|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeUsuario(Connection conn, String usuario ) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, FECHA_NAC," +
					" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, CLAVE, FECHA," +
					" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC,TELEFONO"+					
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE USUARIO_ID = ?");			
			ps.setString(1, usuario);			
			
			rs= ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|existeUsuario2|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	/*
	 * Obtiene el siguiente numero de folio.
	 * */
	public String maximoReg(Connection conn) throws SQLException{
		String folio 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO), 0)+1 AS MAXIMO FROM SALOMON.ADM_SOLICITUD"); 
			rs = ps.executeQuery();
			if (rs.next())
				folio = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
	
	public String getEdad(Connection conn) throws SQLException{
		String edad 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FLOOR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), FECHA_NAC)/12) AS EDAD" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999999')");			
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				edad = rs.getString("EDAD");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|getEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return edad;
	}
	
	public static String getNombre(Connection conn, String folio) throws SQLException{
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombre 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999999')");			
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|getEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getFolio(Connection conn, String codigoPersonal) throws SQLException{
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombre 			= "";
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = '"+codigoPersonal+"' ");			
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("FOLIO");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSolicitud|getFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}