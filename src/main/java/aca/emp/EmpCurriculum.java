/**
 * 
 */
package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class EmpCurriculum {
	private String idEmpleado;
	private String lugarNac;
	private String tProfesional;
	private String gPosgrado;
	private String tUniversitario;
	private String respActual;
	private String respAnterior;
	private String expDocente;
	private String fNacimiento;
	private String nacionalidad;
	private String nivelId;
	private String revisado;
	private String tDoctorado;
	private String fechaLic;
	private String fechaMae;
	private String fechaDoc;
	private String instLic;
	private String instMae;
	private String instDoc;
	
	public EmpCurriculum(){
		idEmpleado		= "";
		lugarNac		= "";
		tProfesional	= "";
		gPosgrado		= "";
		tUniversitario	= "";
		respActual		= "";
		respAnterior	= "";
		expDocente		= "";
		fNacimiento		= "";
		nacionalidad	= "";
		nivelId			= "";
		revisado		= "";
		tDoctorado		= "";
		fechaLic		= "";
		fechaMae		= "";
		fechaDoc		= "";
		instLic			= "";
		instMae			= "";
		instDoc			= "";
	}

	
	public String getNivelId() {
		return nivelId;
	}


	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}


	public String getRevisado() {
		return revisado;
	}


	public void setRevisado(String revisado) {
		this.revisado = revisado;
	}


	public String getFNacimiento() {
		return fNacimiento;
	}

	public void setFNacimiento(String nacimiento) {
		fNacimiento = nacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return the idEmpleado
	 */
	public String getIdEmpleado() {
		return idEmpleado;
	}

	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	/**
	 * @return the lugarNac
	 */
	public String getLugarNac() {
		return lugarNac;
	}

	/**
	 * @param lugarNac the lugarNac to set
	 */
	public void setLugarNac(String lugarNac) {
		this.lugarNac = lugarNac;
	}

	/**
	 * @return the tProfesional
	 */
	public String getTProfesional() {
		return tProfesional;
	}

	/**
	 * @param profesional the tProfesional to set
	 */
	public void setTProfesional(String profesional) {
		tProfesional = profesional;
	}

	/**
	 * @return the gPosgrado
	 */
	public String getGPosgrado() {
		return gPosgrado;
	}

	/**
	 * @param posgrado the gPosgrado to set
	 */
	public void setGPosgrado(String posgrado) {
		gPosgrado = posgrado;
	}

	/**
	 * @return the tUniversitario
	 */
	public String getTUniversitario() {
		return tUniversitario;
	}

	/**
	 * @param universitario the tUniversitario to set
	 */
	public void setTUniversitario(String universitario) {
		tUniversitario = universitario;
	}

	/**
	 * @return the respActual
	 */
	public String getRespActual() {
		return respActual;
	}

	/**
	 * @param respActual the respActual to set
	 */
	public void setRespActual(String respActual) {
		this.respActual = respActual;
	}

	/**
	 * @return the respAnterior
	 */
	public String getRespAnterior() {
		return respAnterior;
	}

	/**
	 * @param respAnterior the respAnterior to set
	 */
	public void setRespAnterior(String respAnterior) {
		this.respAnterior = respAnterior;
	}

	/**
	 * @return the expDocente
	 */
	public String getExpDocente() {
		return expDocente;
	}

	/**
	 * @param expDocente the expDocente to set
	 */
	public void setExpDocente(String expDocente) {
		this.expDocente = expDocente;
	}
	
	
	
	public String gettDoctorado() {
		return tDoctorado;
	}


	public void settDoctorado(String tDoctorado) {
		this.tDoctorado = tDoctorado;
	}


	public String getFechaLic() {
		return fechaLic;
	}


	public void setFechaLic(String fechaLic) {
		this.fechaLic = fechaLic;
	}


	public String getFechaMae() {
		return fechaMae;
	}


	public void setFechaMae(String fechaMae) {
		this.fechaMae = fechaMae;
	}


	public String getFechaDoc() {
		return fechaDoc;
	}


	public void setFechaDoc(String fechaDoc) {
		this.fechaDoc = fechaDoc;
	}

	public String gettProfesional() {
		return tProfesional;
	}

	public void settProfesional(String tProfesional) {
		this.tProfesional = tProfesional;
	}

	public String getgPosgrado() {
		return gPosgrado;
	}

	public void setgPosgrado(String gPosgrado) {
		this.gPosgrado = gPosgrado;
	}

	public String gettUniversitario() {
		return tUniversitario;
	}

	public void settUniversitario(String tUniversitario) {
		this.tUniversitario = tUniversitario;
	}

	public String getfNacimiento() {
		return fNacimiento;
	}


	public void setfNacimiento(String fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public String getInstLic() {
		return instLic;
	}

	public void setInstLic(String instLic) {
		this.instLic = instLic;
	}

	public String getInstMae() {
		return instMae;
	}

	public void setInstMae(String instMae) {
		this.instMae = instMae;
	}

	public String getInstDoc() {
		return instDoc;
	}

	public void setInstDoc(String instDoc) {
		this.instDoc = instDoc;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		idEmpleado		= rs.getString("ID_EMPLEADO");
		lugarNac		= rs.getString("LUGAR_NAC");
		tProfesional	= rs.getString("T_PROFESIONAL");
		gPosgrado		= rs.getString("G_POSGRADO");
		tUniversitario	= rs.getString("T_UNIVERSITARIO");
		respActual		= rs.getString("RESP_ACTUAL");
		respAnterior	= rs.getString("RESP_ANTERIOR");
		expDocente		= rs.getString("EXP_DOCENTE");
		fNacimiento		= rs.getString("F_NACIMIENTO");
		nacionalidad	= rs.getString("NACIONALIDAD");
		nivelId			= rs.getString("NIVEL_ID");
		revisado		= rs.getString("REVISADO");
		tDoctorado		= rs.getString("T_DOCTORADO");
		fechaLic		= rs.getString("FECHA_LIC");
		fechaMae		= rs.getString("FECHA_MAE");
		fechaDoc		= rs.getString("FECHA_DOC");
		instLic			= rs.getString("INST_LIC");
		instMae			= rs.getString("INST_MAE");
		instDoc			= rs.getString("INST_DOC");
	}
	
	public void mapeaRegId(Connection con, String idEmpleado) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT ID_EMPLEADO, LUGAR_NAC, T_PROFESIONAL, G_POSGRADO," +
					" T_UNIVERSITARIO, RESP_ACTUAL, RESP_ANTERIOR, EXP_DOCENTE, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO," +
					" NACIONALIDAD, NIVEL_ID, REVISADO, T_DOCTORADO, " +
					" TO_CHAR(FECHA_LIC,'DD/MM/YYYY') AS FECHA_LIC, TO_CHAR(FECHA_MAE,'DD/MM/YYYY') AS FECHA_MAE, TO_CHAR(FECHA_DOC,'DD/MM/YYYY') AS FECHA_DOC," +
					" INST_LIC, INST_MAE, INST_DOC FROM ENOC.EMP_CURRICULUM " + 
					" WHERE ID_EMPLEADO = ?");
			
			ps.setString(1, idEmpleado);
			
			rs = ps.executeQuery();
			
			if(rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpCurriculumUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
}