package adm.alumno;
import java.sql.*;

public class Solicitud {
	private String folio;
	private String perNombre;
	private String perPaterno;
	private String perMaterno;
	private String nacCiudad;
	private String nacEdo;
	private String nacPais;
	private String nacNacionalidad;
	private String perNacimiento;
	private String perEdocivil;
	private String perGenero;
	private String perReligion;
	private String perBautizado;
	private String perCalle;
	private String perNumero;
	private String perColonia;
	private String perCiudad;
	private String perEdo;
	private String perPais;
	private String perCp;
	private String perFax;
	private String perTel;
	private String perEmail;
	private String acaCarrera;
	private String acaFecha;
	private String bacInstitucion;
	private String bacDireccion;
	private String bacCiudad;
	private String bacEdo;
	private String bacPais;
	private String bacFinicio;
	private String bacFfinal;
	private String antInstitucion;
	private String antDireccion;
	private String antCiudad;
	private String antEdo;
	private String antPais;
	private String antGrado;
	private String antFinicio;
	private String antFfinal;	
	private String usuario;
	private String clave;
	private String fecha;
	private String matricula;
	private String estado;
	private String modalidadId;
	
	public Solicitud(){
		folio				= "";
		perNombre			= "";
		perPaterno			= "";
		perMaterno			= "";
		nacCiudad			= "";
		nacEdo				= "";
		nacPais				= "";
		nacNacionalidad 	= "";
		perNacimiento		= "";
		perEdocivil			= "";
		perGenero			= "";
		perReligion			= "";
		perBautizado		= "";
		perCalle			= "";
		perNumero			= "";
		perColonia			= "";
		perCiudad			= "";
		perEdo				= "";
		perPais				= "";
		perCp				= "";
		perFax				= "";
		perTel				= "";
		perEmail			= "";
		acaCarrera			= "";
		acaFecha			= "";
		bacInstitucion		= "";
		bacDireccion		= "";
		bacCiudad			= "";
		bacEdo				= "";
		bacPais				= "";
		bacFinicio			= "";
		bacFfinal			= "";
		antInstitucion		= "";
		antDireccion		= "";
		antCiudad			= "";
		antEdo				= "";
		antPais				= "";
		antGrado			= "";
		antFinicio			= "";
		antFfinal			= "";		
		clave				= "";
		fecha				= "";
		matricula			= "";
		estado				= "";
		modalidadId			= "";
	}

	
	public String getFolio() {
		return folio;
	}



	public void setFolio(String folio) {
		this.folio = folio;
	}



	public String getPerNombre() {
		return perNombre;
	}



	public void setPerNombre(String perNombre) {
		this.perNombre = perNombre;
	}



	public String getPerPaterno() {
		return perPaterno;
	}



	public void setPerPaterno(String perPaterno) {
		this.perPaterno = perPaterno;
	}



	public String getPerMaterno() {
		return perMaterno;
	}



	public void setPerMaterno(String perMaterno) {
		this.perMaterno = perMaterno;
	}



	public String getNacCiudad() {
		return nacCiudad;
	}



	public void setNacCiudad(String nacCiudad) {
		this.nacCiudad = nacCiudad;
	}



	public String getNacEdo() {
		return nacEdo;
	}



	public void setNacEdo(String nacEdo) {
		this.nacEdo = nacEdo;
	}



	public String getNacPais() {
		return nacPais;
	}



	public void setNacPais(String nacPais) {
		this.nacPais = nacPais;
	}



	public String getNacNacionalidad() {
		return nacNacionalidad;
	}



	public void setNacNacionalidad(String nacNacionalidad) {
		this.nacNacionalidad = nacNacionalidad;
	}



	public String getPerNacimiento() {
		return perNacimiento;
	}



	public void setPerNacimiento(String perNacimiento) {
		this.perNacimiento = perNacimiento;
	}



	public String getPerEdocivil() {
		return perEdocivil;
	}



	public void setPerEdocivil(String perEdocivil) {
		this.perEdocivil = perEdocivil;
	}



	public String getPerGenero() {
		return perGenero;
	}



	public void setPerGenero(String perGenero) {
		this.perGenero = perGenero;
	}



	public String getPerReligion() {
		return perReligion;
	}



	public void setPerReligion(String perReligion) {
		this.perReligion = perReligion;
	}



	public String getPerBautizado() {
		return perBautizado;
	}



	public void setPerBautizado(String perBautizado) {
		this.perBautizado = perBautizado;
	}



	public String getPerCalle() {
		return perCalle;
	}



	public void setPerCalle(String perCalle) {
		this.perCalle = perCalle;
	}



	public String getPerNumero() {
		return perNumero;
	}



	public void setPerNumero(String perNumero) {
		this.perNumero = perNumero;
	}



	public String getPerColonia() {
		return perColonia;
	}



	public void setPerColonia(String perColonia) {
		this.perColonia = perColonia;
	}



	public String getPerCiudad() {
		return perCiudad;
	}



	public void setPerCiudad(String perCiudad) {
		this.perCiudad = perCiudad;
	}



	public String getPerEdo() {
		return perEdo;
	}



	public void setPerEdo(String perEdo) {
		this.perEdo = perEdo;
	}



	public String getPerPais() {
		return perPais;
	}



	public void setPerPais(String perPais) {
		this.perPais = perPais;
	}



	public String getPerCp() {
		return perCp;
	}



	public void setPerCp(String perCp) {
		this.perCp = perCp;
	}



	public String getPerFax() {
		return perFax;
	}



	public void setPerFax(String perFax) {
		this.perFax = perFax;
	}



	public String getPerTel() {
		return perTel;
	}



	public void setPerTel(String perTel) {
		this.perTel = perTel;
	}



	public String getPerEmail() {
		return perEmail;
	}
	

	public void setPerEmail(String perEmail) {
		this.perEmail = perEmail;
	}



	public String getAcaCarrera() {
		return acaCarrera;
	}



	public void setAcaCarrera(String acaCarrera) {
		this.acaCarrera = acaCarrera;
	}



	public String getAcaFecha() {
		return acaFecha;
	}



	public void setAcaFecha(String acaFecha) {
		this.acaFecha = acaFecha;
	}



	public String getBacInstitucion() {
		return bacInstitucion;
	}



	public void setBacInstitucion(String bacInstitucion) {
		this.bacInstitucion = bacInstitucion;
	}



	public String getBacDireccion() {
		return bacDireccion;
	}



	public void setBacDireccion(String bacDireccion) {
		this.bacDireccion = bacDireccion;
	}



	public String getBacCiudad() {
		return bacCiudad;
	}



	public void setBacCiudad(String bacCiudad) {
		this.bacCiudad = bacCiudad;
	}



	public String getBacEdo() {
		return bacEdo;
	}



	public void setBacEdo(String bacEdo) {
		this.bacEdo = bacEdo;
	}



	public String getBacPais() {
		return bacPais;
	}



	public void setBacPais(String bacPais) {
		this.bacPais = bacPais;
	}



	public String getBacFinicio() {
		return bacFinicio;
	}



	public void setBacFinicio(String bacFinicio) {
		this.bacFinicio = bacFinicio;
	}



	public String getBacFfinal() {
		return bacFfinal;
	}



	public void setBacFfinal(String bacFfinal) {
		this.bacFfinal = bacFfinal;
	}



	public String getAntInstitucion() {
		return antInstitucion;
	}



	public void setAntInstitucion(String antInstitucion) {
		this.antInstitucion = antInstitucion;
	}



	public String getAntDireccion() {
		return antDireccion;
	}



	public void setAntDireccion(String antDireccion) {
		this.antDireccion = antDireccion;
	}



	public String getAntCiudad() {
		return antCiudad;
	}



	public void setAntCiudad(String antCiudad) {
		this.antCiudad = antCiudad;
	}



	public String getAntEdo() {
		return antEdo;
	}



	public void setAntEdo(String antEdo) {
		this.antEdo = antEdo;
	}



	public String getAntPais() {
		return antPais;
	}



	public void setAntPais(String antPais) {
		this.antPais = antPais;
	}



	public String getAntGrado() {
		return antGrado;
	}



	public void setAntGrado(String antGrado) {
		this.antGrado = antGrado;
	}



	public String getAntFinicio() {
		return antFinicio;
	}



	public void setAntFinicio(String antFinicio) {
		this.antFinicio = antFinicio;
	}



	public String getAntFfinal() {
		return antFfinal;
	}



	public void setAntFfinal(String antFfinal) {
		this.antFfinal = antFfinal;
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


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_SOLICITUD(" + 
					" FOLIO, PER_NOMBRE, PER_PATERNO," +
					" PER_MATERNO, NAC_CIUDAD, NAC_EDO," +
					" NAC_PAIS, NAC_NACIONALIDAD, PER_NACIMIENTO," +
					" PER_EDOCIVIL, PER_GENERO, PER_RELIGION," +
					" PER_BAUTIZADO, PER_CALLE, PER_NUMERO," +
					" PER_COLONIA, PER_CIUDAD, PER_EDO, " +
					" PER_PAIS, PER_CP, PER_FAX," +
					" PER_TEL, PER_EMAIL, ACA_CARRERA," +
					" ACA_FECHA, BAC_INSTITUCION, BAC_DIRECCION," +
					" BAC_CIUDAD, BAC_EDO, BAC_PAIS," +
					" BAC_FINICIO, BAC_FFINAL, ANT_INSTITUCION," +
					" ANT_DIRECCION, ANT_CIUDAD, ANT_EDO," +
					" ANT_PAIS, ANT_GRADO, ANT_FINICIO," +
					" ANT_FFINAL, USUARIO, CLAVE," +
					" FECHA, MATRICULA, ESTADO," +
					" MODALIDAD_ID)" +
					" VALUES (" +
					" TO_NUMBER(?,'99999999'), ?, ?," +
					" ?,TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
					" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_DATE(?,'DD/MM/YYYY')," +
					" ?, ?, TO_NUMBER(?,'99')," +
					" ?, ?, ?," +
					" ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
					" TO_NUMBER(?,'999'), ?, ?," +
					" ?, ?, ?," +
					" ?, ?, ?," +
					" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999')," +
					" TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?," +
					" ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999')," +
					" TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY')," +
					" TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
					" TO_DATE(?, 'DD/MM/YYYY'), ?, ?," +
					" TO_NUMBER(?,'99'))"); 
					
			ps.setString(1,  folio);		ps.setString(2,  perNombre);		ps.setString(3,  perPaterno);
			ps.setString(4,  perMaterno);	ps.setString(5,  nacCiudad);		ps.setString(6,  nacEdo);
			ps.setString(7,  nacPais);		ps.setString(8,  nacNacionalidad);	ps.setString(9,  perNacimiento);
			ps.setString(10, perEdocivil);	ps.setString(11, perGenero);		ps.setString(12, perReligion);
			ps.setString(13, perBautizado);	ps.setString(14, perCalle);			ps.setString(15, perNumero);
			ps.setString(16, perColonia);	ps.setString(17, perCiudad);		ps.setString(18, perEdo);
			ps.setString(19, perPais);		ps.setString(20, perCp);			ps.setString(21, perFax);
			ps.setString(22, perTel);		ps.setString(23, perEmail);			ps.setString(24, acaCarrera);
			ps.setString(25, acaFecha);		ps.setString(26, bacInstitucion);	ps.setString(27, bacDireccion);
			ps.setString(28, bacCiudad);	ps.setString(29, bacEdo);			ps.setString(30, bacPais);
			ps.setString(31, bacFinicio);	ps.setString(32, bacFfinal);		ps.setString(33, antInstitucion);
			ps.setString(34, antDireccion);	ps.setString(35, antCiudad);		ps.setString(36, antEdo);
			ps.setString(37, antPais);		ps.setString(38, antGrado);			ps.setString(39, antFinicio);
			ps.setString(40, antFfinal);	ps.setString(41, usuario);			ps.setString(42, clave);		
			ps.setString(43, fecha);		ps.setString(44, matricula);		ps.setString(45, estado);
			ps.setString(46, modalidadId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;			
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|insertReg|:"+ex);
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
					"(FOLIO, PER_NOMBRE, PER_PATERNO, PER_MATERNO, PER_GENERO, PER_EMAIL, USUARIO, CLAVE, ESTADO)" +
					" VALUES (TO_NUMBER(?,'99999999'), ?, ?, ?, ?, ?, ?, ?,1)"); 
					
			ps.setString(1,  folio);
			ps.setString(2,  perNombre);
			ps.setString(3,  perPaterno);
			ps.setString(4,  perMaterno);			
			ps.setString(5,  perGenero);			
			ps.setString(6,  perEmail);
			ps.setString(7,  usuario);
			ps.setString(8,  clave);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|insertRegistro|:"+ex);
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
					" PER_NOMBRE = ?," +
					" PER_PATERNO = ?," +
					" PER_MATERNO = ?," +
					" NAC_CIUDAD = TO_NUMBER(?,'999')," +
					" NAC_EDO = TO_NUMBER(?,'999')," +
					" NAC_PAIS = TO_NUMBER(?,'999')," +
					" NAC_NACIONALIDAD = TO_NUMBER(?,'999')," +
					" PER_NACIMIENTO = TO_DATE(?,'DD/MM/YYYY')," +
					" PER_EDOCIVIL = ?," +
					" PER_GENERO = ?," +
					" PER_RELIGION = TO_NUMBER(?,'99')," +
					" PER_BAUTIZADO = ?," +
					" PER_CALLE = ?," +
					" PER_NUMERO = ?," +
					" PER_COLONIA = ?," +
					" PER_CIUDAD = TO_NUMBER(?,'999')," +
					" PER_EDO = TO_NUMBER(?,'999')," +
					" PER_PAIS = TO_NUMBER(?,'999')," +
					" PER_CP = ?," +
					" PER_FAX = ?," +
					" PER_TEL = ?," +
					" PER_EMAIL = ?," +
					" ACA_CARRERA = ?," +
					" ACA_FECHA = ?," +
					" BAC_INSTITUCION = ?," +
					" BAC_DIRECCION = ?," +
					" BAC_CIUDAD = TO_NUMBER(?,'999')," +
					" BAC_EDO = TO_NUMBER(?,'999')," +
					" BAC_PAIS = TO_NUMBER(?,'999')," +
					" BAC_FINICIO = TO_DATE(?,'DD/MM/YYYY')," +
					" BAC_FFINAL = TO_DATE(?,'DD/MM/YYYY')," +
					" ANT_INSTITUCION = ?," +
					" ANT_DIRECCION = ?," +
					" ANT_CIUDAD = TO_NUMBER(?,'999')," +
					" ANT_EDO = TO_NUMBER(?,'999')," +
					" ANT_PAIS = TO_NUMBER(?,'999')," +
					" ANT_GRADO = ?," +
					" ANT_FINICIO = TO_DATE(?,'DD/MM/YYYY')," +
					" ANT_FFINAL = TO_DATE(?,'DD/MM/YYYY')," +
					" USUARIO = ?," +
					" CLAVE = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" MATRICULA = ?," +
					" ESTADO = ?" +
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			
			ps.setString(1,  perNombre);
			ps.setString(2,  perPaterno);
			ps.setString(3,  perMaterno);
			ps.setString(4,  nacCiudad);
			ps.setString(5,  nacEdo);
			ps.setString(6,  nacPais);
			ps.setString(7,  nacNacionalidad);
			ps.setString(8,  perNacimiento);
			ps.setString(9,  perEdocivil);
			ps.setString(10, perGenero);
			ps.setString(11, perReligion);
			ps.setString(12, perBautizado);
			ps.setString(13, perCalle);
			ps.setString(14, perNumero);
			ps.setString(15, perColonia);
			ps.setString(16, perCiudad);
			ps.setString(17, perEdo);
			ps.setString(18, perPais);
			ps.setString(19, perCp);
			ps.setString(20, perFax);
			ps.setString(21, perTel);
			ps.setString(22, perEmail);
			ps.setString(23, acaCarrera);
			ps.setString(24, acaFecha);
			ps.setString(25, bacInstitucion);
			ps.setString(26, bacDireccion);
			ps.setString(27, bacCiudad);
			ps.setString(28, bacEdo);
			ps.setString(29, bacPais);
			ps.setString(30, bacFinicio);
			ps.setString(31, bacFfinal);
			ps.setString(32, antInstitucion);
			ps.setString(33, antDireccion);
			ps.setString(34, antCiudad);
			ps.setString(35, antEdo);
			ps.setString(36, antPais);
			ps.setString(37, antGrado);
			ps.setString(38, antFinicio);
			ps.setString(39, antFfinal);
			ps.setString(40, usuario);
			ps.setString(41, clave);
			ps.setString(42, fecha);
			ps.setString(43, matricula);
			ps.setString(44, estado);
			ps.setString(45, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = ?"); 
			ps.setString(1,folio);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 				= rs.getString("FOLIO")==null?"":rs.getString("FOLIO");
		perNombre 			= rs.getString("PER_NOMBRE")==null?"":rs.getString("PER_NOMBRE");
		perPaterno 			= rs.getString("PER_PATERNO")==null?"":rs.getString("PER_PATERNO");
		perMaterno 			= rs.getString("PER_MATERNO")==null?"":rs.getString("PER_MATERNO");
		nacCiudad 			= rs.getString("NAC_CIUDAD")==null?"":rs.getString("NAC_CIUDAD");
		nacEdo 				= rs.getString("NAC_EDO")==null?"":rs.getString("NAC_EDO");
		nacPais 			= rs.getString("NAC_PAIS")==null?"":rs.getString("NAC_PAIS");
		nacNacionalidad 	= rs.getString("NAC_NACIONALIDAD")==null?"":rs.getString("NAC_NACIONALIDAD");
		perNacimiento 		= rs.getString("PER_NACIMIENTO")==null?"":rs.getString("PER_NACIMIENTO");
		perEdocivil 		= rs.getString("PER_EDOCIVIL")==null?"":rs.getString("PER_EDOCIVIL");
		perGenero 			= rs.getString("PER_GENERO")==null?"":rs.getString("PER_GENERO");
		perReligion 		= rs.getString("PER_RELIGION")==null?"":rs.getString("PER_RELIGION");
		perBautizado 		= rs.getString("PER_BAUTIZADO")==null?"":rs.getString("PER_BAUTIZADO");
		perCalle 			= rs.getString("PER_CALLE")==null?"":rs.getString("PER_CALLE");
		perNumero 			= rs.getString("PER_NUMERO")==null?"":rs.getString("PER_NUMERO");
		perColonia 			= rs.getString("PER_COLONIA")==null?"":rs.getString("PER_COLONIA");
		perCiudad 			= rs.getString("PER_CIUDAD")==null?"":rs.getString("PER_CIUDAD");
		perEdo 				= rs.getString("PER_EDO")==null?"":rs.getString("PER_EDO");
		perPais 			= rs.getString("PER_PAIS")==null?"":rs.getString("PER_PAIS");
		perCp 				= rs.getString("PER_CP")==null?"":rs.getString("PER_CP");
		perFax 				= rs.getString("PER_FAX")==null?"":rs.getString("PER_FAX");
		perTel 				= rs.getString("PER_TEL")==null?"":rs.getString("PER_TEL");
		perEmail 			= rs.getString("PER_EMAIL")==null?"":rs.getString("PER_EMAIL");
		acaCarrera 			= rs.getString("ACA_CARRERA")==null?"":rs.getString("ACA_CARRERA");
		acaFecha 			= rs.getString("ACA_FECHA")==null?"":rs.getString("ACA_FECHA");
		bacInstitucion 		= rs.getString("BAC_INSTITUCION")==null?"":rs.getString("BAC_INSTITUCION");
		bacDireccion 		= rs.getString("BAC_DIRECCION")==null?"":rs.getString("BAC_DIRECCION");
		bacCiudad 			= rs.getString("BAC_CIUDAD")==null?"":rs.getString("BAC_CIUDAD");
		bacEdo 				= rs.getString("BAC_EDO")==null?"":rs.getString("BAC_EDO");
		bacPais 			= rs.getString("BAC_PAIS")==null?"":rs.getString("BAC_PAIS");
		bacFinicio 			= rs.getString("BAC_FINICIO")==null?"":rs.getString("BAC_FINICIO");
		bacFfinal 			= rs.getString("BAC_FFINAL")==null?"":rs.getString("BAC_FFINAL");
		antInstitucion 		= rs.getString("ANT_INSTITUCION")==null?"":rs.getString("ANT_INSTITUCION");
		antDireccion 		= rs.getString("ANT_DIRECCION")==null?"":rs.getString("ANT_DIRECCION");
		antCiudad 			= rs.getString("ANT_CIUDAD")==null?"":rs.getString("ANT_CIUDAD");
		antEdo 				= rs.getString("ANT_EDO")==null?"":rs.getString("ANT_EDO");
		antPais 			= rs.getString("ANT_PAIS")==null?"":rs.getString("ANT_PAIS");
		antGrado 			= rs.getString("ANT_GRADO")==null?"":rs.getString("ANT_GRADO");
		antFinicio 			= rs.getString("ANT_FINICIO")==null?"":rs.getString("ANT_FINICIO");
		antFfinal 			= rs.getString("ANT_FFINAL")==null?"":rs.getString("ANT_FFINAL");
		usuario				= rs.getString("USUARIO")==null?"":rs.getString("USUARIO");
		clave				= rs.getString("CLAVE")==null?"":rs.getString("CLAVE");
		fecha				= rs.getString("FECHA")==null?"":rs.getString("FECHA");
		matricula			= rs.getString("MATRICULA")==null?"":rs.getString("MATRICULA");
		estado				= rs.getString("ESTADO")==null?"":rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String folio) throws SQLException{
		ResultSet rs = null;		
		PreparedStatement ps = con.prepareStatement("SELECT FOLIO, PER_NOMBRE, PER_PATERNO, PER_MATERNO," +
					" NAC_CIUDAD, NAC_EDO, NAC_PAIS, NAC_NACIONALIDAD," +
					" TO_CHAR(PER_NACIMIENTO, 'DD/MM/YYYY') AS PER_NACIMIENTO, PER_EDOCIVIL, PER_GENERO, PER_RELIGION," +
					" PER_BAUTIZADO, PER_CALLE, PER_NUMERO, PER_COLONIA," +
					" PER_CIUDAD, PER_EDO, PER_PAIS, PER_CP," +
					" PER_FAX, PER_TEL, PER_EMAIL, ACA_CARRERA," +
					" ACA_FECHA, BAC_INSTITUCION, BAC_DIRECCION, BAC_CIUDAD," +
					" BAC_EDO, BAC_PAIS, TO_CHAR(BAC_FINICIO, 'DD/MM/YYYY') AS BAC_FINICIO, TO_CHAR(BAC_FFINAL, 'DD/MM/YYYY') AS BAC_FFINAL," +
					" ANT_INSTITUCION, ANT_DIRECCION, ANT_CIUDAD, ANT_EDO," +
					" ANT_PAIS, ANT_GRADO, TO_CHAR(ANT_FINICIO, 'DD/MM/YYYY') AS ANT_FINICIO, TO_CHAR(ANT_FFINAL, 'DD/MM/YYYY') AS ANT_FFINAL," +
					" USUARIO, CLAVE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ESTADO" +
					" FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'99999999')"); 
		ps.setString(1,folio);
		rs = ps.executeQuery();
		if(rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT FOLIO, PER_NOMBRE, PER_PATERNO, PER_MATERNO," +
					" NAC_CIUDAD, NAC_EDO, NAC_PAIS, NAC_NACIONALIDAD," +
					" PER_NACIMIENTO, PER_EDOCIVIL, PER_GENERO, PER_RELIGION," +
					" PER_BAUTIZADO, PER_CALLE, PER_NUMERO, PER_COLONIA," +
					" PER_CIUDAD, PER_EDO, PER_PAIS, PER_CP," +
					" PER_FAX, PER_TEL, PER_EMAIL, ACA_CARRERA," +
					" ACA_FECHA, BAC_INSTITUCION, BAC_DIRECCION, BAC_CIUDAD," +
					" BAC_EDO, BAC_PAIS, BAC_FINICIO, BAC_FFINAL," +
					" ANT_INSTITUCION, ANT_DIRECCION, ANT_CIUDAD, ANT_EDO," +
					" ANT_PAIS, ANT_GRADO, ANT_FINICIO, ANT_FFINAL, USUARIO," +
					" CLAVE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ESTADO" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE USUARIO = ?" +
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
			System.out.println("Error - adm.alumno.Solicitud|existeUsuario|:"+ex);
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
			ps = conn.prepareStatement("SELECT FOLIO, PER_NOMBRE, PER_PATERNO, PER_MATERNO," +
					" NAC_CIUDAD, NAC_EDO, NAC_PAIS, NAC_NACIONALIDAD," +
					" PER_NACIMIENTO, PER_EDOCIVIL, PER_GENERO, PER_RELIGION," +
					" PER_BAUTIZADO, PER_CALLE, PER_NUMERO, PER_COLONIA," +
					" PER_CIUDAD, PER_EDO, PER_PAIS, PER_CP," +
					" PER_FAX, PER_TEL, PER_EMAIL, ACA_CARRERA," +
					" ACA_FECHA, BAC_INSTITUCION, BAC_DIRECCION, BAC_CIUDAD," +
					" BAC_EDO, BAC_PAIS, BAC_FINICIO, BAC_FFINAL," +
					" ANT_INSTITUCION, ANT_DIRECCION, ANT_CIUDAD, ANT_EDO," +
					" ANT_PAIS, ANT_GRADO, ANT_FINICIO, ANT_FFINAL," +
					" USUARIO, CLAVE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ESTADO" +
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
			System.out.println("Error - adm.alumno.Solicitud|existeUsuario|:"+ex);
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
			System.out.println("Error - adm.alumno.Solicitud|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT FLOOR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), PER_NACIMIENTO)/12) AS EDAD" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999999')");
			
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				edad = rs.getString("EDAD");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|getEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return edad;
	}
	
	public static String getCarrera(Connection conn, String folio) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String carrera			= "";
		
		try{
			ps = conn.prepareStatement("ACA_CARRERA AS CARRERA" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999999')");
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CARRERA");
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.Solicitud|getCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
}