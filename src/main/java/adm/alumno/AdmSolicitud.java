// Bean folio la tabla folio Adm_Contacto
package  adm.alumno;

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
	private String usuario;
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
		usuario			= "";
		clave			= "";
		fecha 			= "";
		matricula 		= "";
		email			= "";
		estado 			= "1";
		asesorId		= "";
		curp			= "";
		fechaIngreso	= "";
		agente			= "";
		asesorSec 		= ""; 
	}	
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getBautizado() {
		return bautizado;
	}

	public void setBautizado(String bautizado) {
		this.bautizado = bautizado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAsesorId() {
		return asesorId;
	}

	public void setAsesorId(String asesorId) {
		this.asesorId = asesorId;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
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
				" RELIGION_ID, BAUTIZADO, USUARIO, CLAVE, FECHA, MATRICULA, EMAIL, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE)"+
				" VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?," +
				" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
				" TO_NUMBER(?,'99'), ?, ?, ?, now(), ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99'))");
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
			ps.setString(14, usuario);
			ps.setString(15, clave);
			ps.setString(16, matricula);
			ps.setString(17, email);
			ps.setString(18, estado);
			ps.setString(19, asesorId);
			ps.setString(20, curp);
			ps.setString(21, fechaIngreso);
			ps.setString(22, agente);
			
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
					"(FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, GENERO, EMAIL, USUARIO, CLAVE, ESTADO, CURP, FECHA_INGRESO, AGENTE)" +
					" VALUES (TO_NUMBER(?,'99999999'), ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, TO_NUMBER(?,'99'))");
					
			ps.setString(1, folio);
			ps.setString(2, nombre);
			ps.setString(3, apellidoPaterno);
			ps.setString(4, apellidoMaterno);
			ps.setString(5, genero);
			ps.setString(6, email);
			ps.setString(7, usuario);
			ps.setString(8, clave);
			ps.setString(9, curp);
			ps.setString(9, fechaIngreso);
			ps.setString(12, agente);
			
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
					" USUARIO = ?," +
					" CLAVE = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" MATRICULA = ?," +
					" ESTADO = ?, ASESOR_ID = ?, CURP = ?," +
					" FECHA_INGRESO = ?, " +
					" AGENTE = TO_NUMBER(?,'99') " +
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
			ps.setString(14, usuario);
			ps.setString(15, clave);
			ps.setString(16, fecha);
			ps.setString(17, matricula);
			ps.setString(18, estado);
			ps.setString(19, asesorId);
			ps.setString(20, curp);
			ps.setString(21, fechaIngreso);
			ps.setString(22, agente);
			ps.setString(23, folio);
			
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
					" USUARIO = ?," +
					" CLAVE = ?, ESTADO=?, CURP = ?, FECHA_INGRESO = ?, " +
					" AGENTE = TO_NUMBER(?,'99') " + 					
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, nombre);
			ps.setString(2, apellidoPaterno);
			ps.setString(3, apellidoMaterno);			
			ps.setString(4, genero);						
			ps.setString(5, email);			
			ps.setString(6, usuario);
			ps.setString(7, clave);	
			ps.setString(8, estado);
			ps.setString(9, curp);
			ps.setString(10, fechaIngreso);
			ps.setString(11, agente);
			ps.setString(12, folio);
			
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
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
			" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC," +
			" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, USUARIO, CLAVE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA," +
			" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC"+
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
					" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, USUARIO, CLAVE, FECHA," +
					" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC "+					
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE USUARIO = ?"+
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
					" ESTADOCIVIL, GENERO, RELIGION_ID, BAUTIZADO, EMAIL, USUARIO, CLAVE, FECHA," +
					" MATRICULA, ESTADO, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC"+
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE USUARIO = ?");			
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
}