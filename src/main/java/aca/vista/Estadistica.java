// Clase para la vista CARGA_ACADEMICA
package  aca.vista;

import java.sql.*;

public class Estadistica{
	private String cargaId;
	private String bloqueId;
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreLegal;
	private String cotejado;
	private String fNacimiento;
	private String sexo;
	private String estadoCivil;
	private String religionId;	
	private String paisId;
	private String estadoId;	
	private String ciudadId;
	private String nacionalidad;
	private String curp;	
	private String modalidadId;
	private String clasFin;
	private String residenciaId;
	private String estado;
	private String planId;
	private String carreraId;
	private String facultadId;
	private String fInscripcion;
	private String credAlta;
	private String credBaja;
	private String tipoAlumnoId;
	
	public Estadistica(){
		cargaId			= "";
		bloqueId		= "";
		codigoPersonal	= "";
		nombre			= "";
		apellidoPaterno	= "";
		apellidoMaterno	= "";
		nombreLegal		= "";
		cotejado		= "";
		fNacimiento		= "";
		sexo			= "";
		estadoCivil		= "";
		religionId		= "";
		paisId			= "";
		estadoId		= "";	
		ciudadId		= "";
		nacionalidad	= "";
		curp			= "";
		modalidadId		= "";
		clasFin			= "";
		residenciaId	= "";
		estado			= "";
		planId			= "";
		carreraId		= "";
		facultadId		= "";
		fInscripcion	= "";
		credAlta		= "";
		credBaja		= "";
		tipoAlumnoId	= "";
	}
	
	
	public String getCargaId(){
		return cargaId;
	}
		
	public String getBloqueId(){
		return bloqueId;
	}	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
		
	public String getNombre(){
		return nombre;
	}			
	public String getApellidoPaterno(){
		return apellidoPaterno;
	}	
		
	public String getApellidoMaterno(){
		return apellidoMaterno;
	}	
		
	public String getNombreLegal(){
		return nombreLegal;
	}
	
	public String getCotejado(){
		return cotejado;
	}	
		
	public String getFNacimiento(){
		return fNacimiento;
	}	
		
	public String getSexo(){
		return sexo;
	}
	
	public String getEstadoCivil(){
		return estadoCivil;
	}
		
	public String getReligionId(){
		return religionId;
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public String getCurp(){
		return curp;
	}
	
	public String getModalidadId(){
		return modalidadId;
	}
	
	public String getClasFin(){
		return clasFin;
	}
	
	public String getResidenciaId(){
		return residenciaId;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public String getPlanId(){
		return planId;
	}
	
	public String getCarreraId(){
		return carreraId;
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
	 * @return the fInscripcion
	 */
	public String getFInscripcion() {
		return fInscripcion;
	}


	/**
	 * @param inscripcion the fInscripcion to set
	 */
	public void setFInscripcion(String inscripcion) {
		fInscripcion = inscripcion;
	}


	/**
	 * @return the credAlta
	 */
	public String getCredAlta() {
		return credAlta;
	}
	
	public String getCredBaja() {
		return credBaja;
	}

	public String getTipoAlumnoId() {
		return tipoAlumnoId;
	}

	public void setTipoAlumnoId(String tipoAlumnoId) {
		this.tipoAlumnoId = tipoAlumnoId;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{		
		cargaId				= rs.getString("CARGA_ID");
		bloqueId			= rs.getString("BLOQUE_ID");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");		
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
		cotejado			= rs.getString("COTEJADO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		estadoCivil			= rs.getString("ESTADO_CIVIL");		
		religionId 			= rs.getString("RELIGION_ID");		
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId 			= rs.getString("CIUDAD_ID");
		nacionalidad 		= rs.getString("NACIONALIDAD");
		curp				= rs.getString("CURP");
		modalidadId 		= rs.getString("MODALIDAD_ID");
		clasFin 			= rs.getString("CLAS_FIN");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		estado				= rs.getString("ESTADO");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
		facultadId 			= rs.getString("FACULTAD_ID");
		fInscripcion 		= rs.getString("F_INSCRIPCION");
		credAlta			= rs.getString("CRED_ALTA");
		credBaja			= rs.getString("CRED_BAJA");
		tipoAlumnoId		= rs.getString("TIPOALUMNO_ID");
	}
	
	public void mapeaRegCorto(ResultSet rs)throws SQLException{
		cargaId				= rs.getString("CARGA_ID");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
		facultadId 			= rs.getString("FACULTAD_ID");
		modalidadId 		= rs.getString("MODALIDAD_ID");
		paisId 				= rs.getString("PAIS_ID");
	}
	
	public void mapeaRegCorto2(ResultSet rs)throws SQLException{
		cargaId				= rs.getString("CARGA_ID");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
		facultadId 			= rs.getString("FACULTAD_ID");
		modalidadId 		= rs.getString("MODALIDAD_ID");		
		bloqueId 			= rs.getString("BLOQUE_ID");
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId 			= rs.getString("CIUDAD_ID");
		religionId 			= rs.getString("RELIGION_ID");
		tipoAlumnoId		= rs.getString("TIPOALUMNO_ID");
		nacionalidad		= rs.getString("NACIONALIDAD");
		fInscripcion 		= rs.getString("F_INSCRIPCION");
	}
	
	public void mapeaRegEs(ResultSet rs ) throws SQLException{		
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		bloqueId			= rs.getString("BLOQUE_ID");
		nombre				= rs.getString("NOMBRE");
		apellidoPaterno		= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno		= rs.getString("APELLIDO_MATERNO");
		nombreLegal 		= rs.getString("NOMBRE_LEGAL");
		cotejado			= rs.getString("COTEJADO");
		fNacimiento 		= rs.getString("F_NACIMIENTO");
		sexo 				= rs.getString("SEXO");
		estadoCivil			= rs.getString("ESTADO_CIVIL");		
		religionId 			= rs.getString("RELIGION_ID");		
		paisId 				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId 			= rs.getString("CIUDAD_ID");
		nacionalidad 		= rs.getString("NACIONALIDAD");
		curp				= rs.getString("CURP");
		modalidadId 		= rs.getString("MODALIDAD_ID");
		clasFin 			= rs.getString("CLAS_FIN");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		estado				= rs.getString("ESTADO");
		planId 				= rs.getString("PLAN_ID");
		carreraId			= rs.getString("CARRERA_ID");
/*		
		facultadId			= rs.getString("FACULTAD_ID");
		fInscripcion		= rs.getString("F_INSCRIPCION");
		credAlta			= rs.getString("CRED_ALTA");
		credBaja			= rs.getString("CRED_BAJA");
*/		
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,CARRERA_ID, TIPOALUMNO_ID "+
				"FROM ENOC.ESTADISTICA "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, "+
				"CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
				"CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_ALTA, CRED_BAJA, TIPOALUMNO_ID "+
				"FROM ENOC.ESTADISTICA "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){			
				mapeaReg(rs);			
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String bloqueId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, "+
				"CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
				"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
				"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
				"CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_ALTA, CRED_BAJA, TIPOALUMNO_ID "+
				"FROM ENOC.ESTADISTICA "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? " +
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next()){			
				mapeaReg(rs);			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ESTADISTICA "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	public static int numeroAlumnos(Connection conn, String cargaId) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT count(distinct codigo_personal) FROM ENOC.ESTADISTICA where carga_id='"+cargaId+"'"; 
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|numeroAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}

	public static int numeroAlumnos(Connection conn, String cargaId,String facultadId) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT count(distinct codigo_personal) FROM ENOC.ESTADISTICA where carga_id='"+cargaId+"' and ENOC.FACULTAD(carrera_id)='"+facultadId+"'"; 
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|numeroAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public static int numeroAlumnosCarrera(Connection conn, String cargaId,String carreraId) throws SQLException{
		int total=0;
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		try {
			comando = "SELECT count(distinct codigo_personal) FROM ENOC.ESTADISTICA where carga_id='"+cargaId+"' and carrera_id='"+carreraId+"'"; 
			rs = st.executeQuery (comando);
			while (rs.next()){
				total = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|numeroAlumnosCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}

	public static String getGrupo(Connection conn, String codigo) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		String grupo ="-";
		try {
			comando = "SELECT ALUM_GRUPO(CODIGO_PERSONAL, CARGA_ID) AS GRUPO " +
					"FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL ='"+codigo+"' " +
					"AND CARGA_ID IN(SELECT CARGA_ID FROM ENOC.CARGA " + 
					"WHERE TO_DATE(now(), 'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL)";
			rs = st.executeQuery (comando);
			if (rs.next()){
				grupo = rs.getString("GRUPO");
				if(grupo.equals("") || grupo == null) grupo = "-";
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return grupo;
	}	
		
	
	public static String getAlumnosInscritos(Connection conn, String cargaId, String carreraId) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		String alumnos ="";
		try {
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS MATRICULA " +
					"FROM ENOC.ESTADISTICA " +
					"WHERE CARGA_ID = '"+cargaId+"' AND CARRERA_ID = '"+carreraId+"' ";
			rs = st.executeQuery (comando);
			if (rs.next()){
				alumnos = rs.getString("MATRICULA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getAlumnosInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return alumnos;
	}
	
	public static boolean getNuevoUm(Connection conn, String codigoId, String cargaId) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		boolean alumnos =false;
		try {
			comando = "SELECT MATRICULA AS NUEVO FROM MATEO.FES_CCOBRO " +
					" WHERE MATRICULA = '"+codigoId+"' " +
					" AND MATRICULA LIKE '1%'" +
					" AND CARGA_ID = '"+cargaId+"' " +
					" AND TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_UM(MATRICULA) ";
			rs = st.executeQuery (comando);
			if (rs.next()){
				alumnos = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getAlumnosInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return alumnos;
	}
	
	public static String getPlanId(Connection conn, String cargaId, String codigoPersonal) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		String plan ="";
		try {
			comando = "SELECT PLAN_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = '"+cargaId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery (comando);
			if (rs.next()){
				plan = rs.getString("PLAN_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getPlanId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return plan;
	}
	
	public static String getPrimerDiaInscrpcion(Connection conn, String cargaId) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		String fecha ="";
		try {
			comando = "SELECT MIN(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS FECHA FROM ENOC.ESTADISTICA WHERE CARGA_ID = '"+cargaId+"'";
			rs = st.executeQuery (comando);
			if (rs.next()){
				fecha = rs.getString("FECHA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getPrimerDiaInscrpcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getUltimoDiaInscrpcion(Connection conn, String cargaId) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = null;
		String comando="";
		String fecha ="";
		try {
			comando = "SELECT MAX(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS FECHA FROM ENOC.ESTADISTICA WHERE CARGA_ID = '"+cargaId+"'";
			rs = st.executeQuery (comando);
			if (rs.next()){
				fecha = rs.getString("FECHA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Estadistica|getUltimoDiaInscrpcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
}