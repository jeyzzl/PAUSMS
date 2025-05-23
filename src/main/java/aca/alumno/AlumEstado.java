// Bean de Estado del alumno en el procesos de matrícula( Por cada bloque).
package  aca.alumno;

import java.sql.*;

public class AlumEstado{
	private String codigoPersonal;
	private String cargaId;
	private String bloqueId;
	private String estado;	
	private String modalidadId;
	private String tipoalumnoId;
	private String facultadId;
	private String carreraId;
	private String planId;
	private String fecha;
	private String residenciaId;
	private String dormitorio;
	private String ciclo;
	private String grado;
	private String clasFin;
	
	public AlumEstado(){
		codigoPersonal		= "";
		cargaId				= "";
		bloqueId			= "0";
		estado				= "";
		modalidadId			= "0";
		tipoalumnoId		= "0";
		facultadId			= "";
		carreraId			= "";
		planId				= "";
		fecha				= "";
		residenciaId		= "";
		dormitorio			= "0";
		ciclo 				= "0";
		grado 				= "0";
		clasFin 			= "0"; 
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getCargaId(){
		return cargaId;
	}
	
	public void setCargaId( String Carga_id){
		this.cargaId = Carga_id;
	}	
	
	public String getBloqueId(){
		return bloqueId;
	}
	
	public void setBloqueId( String bloqueId){
		this.bloqueId = bloqueId;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado( String estado){
		this.estado = estado;
	}	
	
	public String getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}

	public String getTipoalumnoId() {
		return tipoalumnoId;
	}

	public void setTipoalumnoId(String tipoalumnoId) {
		this.tipoalumnoId = tipoalumnoId;
	}
	
	public String getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(String facultadId) {
		this.facultadId = facultadId;
	}

	public String getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getResidenciaId() {
		return residenciaId;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}

	public String getDormitorio() {
		return dormitorio;
	}
	
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}

	public String getCiclo() {
		return ciclo;
	}
	
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	
	public String getClasFin() {
		return clasFin;
	}

	public void setClasFin(String clasFin) {
		this.clasFin = clasFin;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cargaId 		= rs.getString("CARGA_ID");
		bloqueId		= rs.getString("BLOQUE_ID");
		estado	 		= rs.getString("ESTADO");	
		modalidadId		= rs.getString("MODALIDAD_ID");
		tipoalumnoId	= rs.getString("TIPOALUMNO_ID");
		facultadId 		= rs.getString("FACULTAD_ID");
		carreraId 		= rs.getString("CARRERA_ID");
		planId     		= rs.getString("PLAN_ID");
		fecha			= rs.getString("FECHA");
		residenciaId 	= rs.getString("RESIDENCIA_ID");
		dormitorio 		= rs.getString("DORMITORIO");
		ciclo			= rs.getString("CICLO");
		grado			= rs.getString("GRADO");
		clasFin 		= rs.getString("CLAS_FIN");

	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cargaId) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, COALESCE(MODALIDAD_ID,0) AS MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN "+
				"FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID IN (?) ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
	public AlumEstado mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws SQLException{
		
		AlumEstado alumEstado = new AlumEstado();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, ESTADO, MODALIDAD_ID, TIPOALUMNO_ID, FACULTAD_ID, CARRERA_ID, PLAN_ID, FECHA, RESIDENCIA_ID, DORMITORIO, CICLO, GRADO, CLAS_FIN "+
				"FROM ENOC.ALUM_ESTADO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.EstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return alumEstado;
	}

}