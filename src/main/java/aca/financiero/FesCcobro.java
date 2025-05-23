package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesCcobro {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String nombre;
	private String modalidadId;
	private String modalidad;
	private String tAlumnoId;
	private String tAlumno;
	private String semestre;
	private String fecha;
	private String formaPago;
	private String religion;
	private String nacionalidad;
	private String residencia;
	private String facultadId;
	private String facultad;
	private String carreraId;
	private String carrera;
	private String planId;
	private String nombrePlan;
	private String grado;
	private String inscrito;
	private String folio;
	private String dormitorio;
	
	// Constructor
	public FesCcobro(){		
		matricula		= "";
		cargaId			= "";
		bloque			= "";
		nombre			= "";
		modalidadId		= "";
		modalidad		= "";
		tAlumnoId		= "";
		tAlumno			= "";
		semestre		= "";
		fecha			= "";
		formaPago		= "";
		religion		= "";
		nacionalidad	= "";
		residencia		= "";
		facultadId		= "";
		facultad		= "";
		carreraId		= "";
		carrera			= "";
		planId			= "";
		nombrePlan		= "";
		grado			= "";
		inscrito		= "";
		folio			= "";
		dormitorio 		= "0";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String gettAlumnoId() {
		return tAlumnoId;
	}

	public void settAlumnoId(String tAlumnoId) {
		this.tAlumnoId = tAlumnoId;
	}

	public String gettAlumno() {
		return tAlumno;
	}

	public void settAlumno(String tAlumno) {
		this.tAlumno = tAlumno;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getNombrePlan() {
		return nombrePlan;
	}

	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getInscrito() {
		return inscrito;
	}

	public void setInscrito(String inscrito) {
		this.inscrito = inscrito;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula	         	= rs.getString("MATRICULA");	
		cargaId		         	= rs.getString("CARGA_ID");   
		bloque		         	= rs.getString("BLOQUE");     
		nombre		         	= rs.getString("NOMBRE");    
		modalidadId	         	= rs.getString("MODALIDAD_ID");
		modalidad	         	= rs.getString("MODALIDAD");    
		tAlumnoId	         	= rs.getString("TALUMNO_ID"); 
		tAlumno			        = rs.getString("TALUMNO");
		semestre		        = rs.getString("SEMESTRE");
		fecha			        = rs.getString("FECHA");
		formaPago		        = rs.getString("FORMAPAGO");
		religion		        = rs.getString("RELIGION");
		nacionalidad	        = rs.getString("NACIONALIDAD");
		residencia		        = rs.getString("RESIDENCIA");
		facultadId		        = rs.getString("FACULTAD_ID");
		facultad		        = rs.getString("FACULTAD");
		carreraId		        = rs.getString("CARRERA_ID");
		carrera			        = rs.getString("CARRERA");
		planId			        = rs.getString("PLAN_ID");
		nombrePlan		        = rs.getString("NOMBRE_PLAN");
		grado			        = rs.getString("GRADO");
		inscrito		        = rs.getString("INSCRITO");
		folio			        = rs.getString("FOLIO");
		dormitorio		        = rs.getString("DORMITORIO");
	}	
	
 	public void mapeaRegId(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT MATRICULA, CARGA_ID, BLOQUE, NOMBRE, MODALIDAD_ID, MODALIDAD," +
	 					" TALUMNO_ID, TALUMNO, SEMESTRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, FORMAPAGO, RELIGION, NACIONALIDAD, RESIDENCIA, FACULTAD_ID," +
	 					" FACULTAD, CARRERA_ID, CARRERA, PLAN_ID, NOMBRE_PLAN, GRADO, INSCRITO, FOLIO, DORMITORIO" +
	 				" FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
	 		ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public boolean existeReg(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateInscrito(Connection conn, String matricula, String cargaId, String bloqueId, String inscrito) throws SQLException{
		boolean 			ok 	= false;		
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CCOBRO SET INSCRITO = ? WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, inscrito);
			ps.setString(2, matricula);
	 		ps.setString(3, cargaId);
	 		ps.setString(4, bloqueId);
	 		
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|updateInscrito|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateSemestre(Connection conn, String matricula, String cargaId, String bloqueId, String semestre) throws SQLException{
		boolean 			ok 	= false;		
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CCOBRO SET SEMESTRE = ? WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, semestre);
			ps.setString(2, matricula);
	 		ps.setString(3, cargaId);
	 		ps.setString(4, bloqueId);
	 		
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|updateSemestre|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateGrado(Connection conn, String matricula, String cargaId, String bloqueId, String grado) throws SQLException{
		boolean 			ok 	= false;		
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("UPDATE MATEO.FES_CCOBRO SET GRADO = ? WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, grado);
			ps.setString(2, matricula);
	 		ps.setString(3, cargaId);
	 		ps.setString(4, bloqueId);
	 		
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|updateGrado|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getForma(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException{
		String 			forma 	= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FORMAPAGO FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
			rs = ps.executeQuery();
			if (rs.next())
				forma = rs.getString("FORMAPAGO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|getForma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return forma;
	}
	
	/*
	 * BUSCA UN CALCULO DE COBRO ACTIVO EN LA CARGA Y BLOQUE ACTUAL DEL ALUMNO
	 * */
	public static String getFormaPago(Connection conn, String matricula, String cargaId, String modalidadId) throws SQLException{
		String 			forma 	= "X";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FORMAPAGO FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = ?"
					+ " AND CARGA_ID = ?"
					+ " AND INSCRITO = 'N'"
					+ " AND MODALIDAD_ID = ?"
					+ " AND BLOQUE IN (SELECT BLOQUE_ID FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE)");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, modalidadId);
	 		ps.setString(4, cargaId);
			rs = ps.executeQuery();
			if (rs.next())
				forma = rs.getString("FORMAPAGO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|getForma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return forma;
	}
	
	/* Busca la modalidad del alumno en una carga */
	public static String getModalidad(Connection conn, String matricula, String cargaId ) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String modalidad 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND CARGA_ID = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);	 		
			rs = ps.executeQuery();
			if (rs.next())
				modalidad = rs.getString("MODALIDAD");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcobro|getModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return modalidad;
	}
	
}