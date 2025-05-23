// Bean de datos academicos del alumno
package  aca.alumno;
import java.sql.*;

public class AlumAcademico{
	private String codigoPersonal;
	private String tipoAlumno;
	private String clasFin;
	private String obrero;
	private String obreroInstitucion;
	private String residenciaId;
	private String dormitorio;
	private String fSolicitud;
	private String fAdmision;
	private String fAlta;
	private String modalidadId;
	private String modalidad;
	private String extensionId;
	private String prepaLugar;
	private String requerido;
	
	public AlumAcademico(){
		codigoPersonal		= "";
		tipoAlumno			= "";
		clasFin				= "";
		obrero				= "";
		obreroInstitucion	= "";
		residenciaId		= "I";
		dormitorio			= "0";
		fSolicitud			= "";
		fAdmision			= "";
		fAlta				= "";
		modalidadId 		= "1";
		modalidad	 		= "";
		extensionId			= "0";
		prepaLugar			= "";
		requerido			= "N";
	}
	
	public String getClasFin() {
		return clasFin;
	}
	
	public void setClasFin(String clasFin) {
		this.clasFin = clasFin;
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getDormitorio() {
		return dormitorio;
	}
	
	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}
	
	public String getExtensionId() {
		return extensionId;
	}
	
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
	
	public String getFAdmision() {
		return fAdmision;
	}
	
	public void setFAdmision(String admision) {
		fAdmision = admision;
	}
	
	public String getFAlta() {
		return fAlta;
	}
	
	public void setFAlta(String alta) {
		fAlta = alta;
	}
	
	public String getFSolicitud() {
		return fSolicitud;
	}
	
	public void setFSolicitud(String solicitud) {
		fSolicitud = solicitud;
	}
	
	public String getModalidad() {
		return modalidad;
	}
	
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
	public String getModalidadId() {
		return modalidadId;
	}
	
	public void setModalidadId(String modalidadId) {
		this.modalidadId = modalidadId;
	}
	
	public String getObrero() {
		return obrero;
	}
	
	public void setObrero(String obrero) {
		this.obrero = obrero;
	}
	
	public String getObreroInstitucion() {
		return obreroInstitucion;
	}
	
	public void setObreroInstitucion(String obreroInstitucion) {
		this.obreroInstitucion = obreroInstitucion;
	}
	
	public String getResidenciaId() {
		return residenciaId;
	}
	
	public void setResidenciaId(String residenciaId) {
		this.residenciaId = residenciaId;
	}
	
	public String getTipoAlumno() {
		return tipoAlumno;
	}
	
	public void setTipoAlumno(String tipoAlumno) {
		this.tipoAlumno = tipoAlumno;
	}	
	
	public String getPrepaLugar() {
		return prepaLugar;
	}

	public void setPrepaLugar(String prepaLugar) {
		this.prepaLugar = prepaLugar;
	}
	
	public String getRequerido() {
		return requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		tipoAlumno 			= rs.getString("TIPO_ALUMNO");
		clasFin 			= rs.getString("CLAS_FIN");
		obrero	 			= rs.getString("OBRERO");
		obreroInstitucion 	= rs.getString("OBRERO_INSTITUCION");
		residenciaId		= rs.getString("RESIDENCIA_ID");
		dormitorio 			= rs.getString("DORMITORIO");
		fSolicitud			= rs.getString("F_SOLICITUD");
		fAdmision			= rs.getString("F_ADMISION");
		fAlta				= rs.getString("F_ALTA");
		modalidadId			= rs.getString("MODALIDAD_ID");
		modalidad  			= rs.getString("MODALIDAD");
		extensionId			= rs.getString("EXTENSION_ID");
		prepaLugar			= rs.getString("PREPA_LUGAR");
		requerido			= rs.getString("REQUERIDO");
	}
	
	public void mapeaRegId( Connection conn, String Codigo_personal ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, TIPO_ALUMNO, COALESCE(CLAS_FIN,1) CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				"COALESCE(RESIDENCIA_ID,'I') RESIDENCIA_ID, DORMITORIO, "+
				"COALESCE(TO_CHAR(F_SOLICITUD,'DD/MM/YYYY'),'01/01/1900') AS F_SOLICITUD, "+
				"COALESCE(TO_CHAR(F_ADMISION,'DD/MM/YYYY'),'01/01/1900') AS F_ADMISION, "+
				"COALESCE(TO_CHAR(F_ALTA,'DD/MM/YYYY'),'01/01/1900') F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, "+
				"EXTENSION_ID, PREPA_LUGAR, REQUERIDO "+
				"FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|mapeaRegId|:"+ex);
		}finally{
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
	}

}