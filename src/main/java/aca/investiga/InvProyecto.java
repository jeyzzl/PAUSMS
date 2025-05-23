package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Karelly
 *
 */
public class InvProyecto {
	private String proyectoId;
	private String proyectoNombre;
	private String codigoPersonal;
	private String tipo;
	private String linea;
	private String carreraId;
	private String departamento;
	private String fechaInicio;
	private String fechaFinal;
	private String resumen;
	private String documento;
	private String estado;
	private String estadoArte;
	private String antecedentes;
	private String justificacion;	
	private String resDocente;
	private String resAlumno;
	private String folio;
	private String investigadores;
	
	public InvProyecto(){
		proyectoId			= "";
		codigoPersonal		= "";
		proyectoNombre		= "-";		
		tipo 				= "0";
		linea 				= "-";
		carreraId 			= "-";
		departamento 		= "-";
		fechaInicio 		= "";
		fechaFinal			= "";
		resumen 			= "-";
		documento 			= "N";
		estado 				= "0";
		estadoArte 			= "-";
		folio 				= "-";
		antecedentes		= "-";
		justificacion		= "-";		
		resDocente			= "-";
		resAlumno			= "-";
		investigadores		= "-";
	}	
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getProyectoNombre() {
		return proyectoNombre;
	}
	public void setProyectoNombre(String proyectoNombre) {
		this.proyectoNombre = proyectoNombre;
	}
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public String getCarreraId() {
		return carreraId;
	}
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEstadoArte() {
		return estadoArte;
	}
	public void setEstadoArte(String estadoArte) {
		this.estadoArte = estadoArte;
	}
	public String getAntecedentes() {
		return antecedentes;
	}
	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}
	public String getJustificacion() {
		return justificacion;
	}
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}	
	public String getResDocente() {
		return resDocente;
	}
	public void setResDocente(String resDocente) {
		this.resDocente = resDocente;
	}
	public String getResAlumno() {
		return resAlumno;
	}
	public void setResAlumno(String resAlumno) {
		this.resAlumno = resAlumno;
	}	
	public String getInvestigadores() {
		return investigadores;
	}
	public void setInvestigadores(String investigadores) {
		this.investigadores = investigadores;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		proyectoId		= rs.getString("PROYECTO_ID");
		proyectoNombre	= rs.getString("PROYECTO_NOMBRE");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		tipo			= rs.getString("TIPO");
		linea			= rs.getString("LINEA");
		carreraId		= rs.getString("CARRERA_ID");
		departamento	= rs.getString("DEPARTAMENTO");
		fechaInicio		= rs.getString("FECHA_INICIO");
		fechaFinal		= rs.getString("FECHA_FINAL");
		resumen			= rs.getString("RESUMEN");
		estadoArte		= rs.getString("ESTADO_ARTE");
		documento		= rs.getString("DOCUMENTO");
		estado			= rs.getString("ESTADO");
		folio			= rs.getString("FOLIO");
		antecedentes	= rs.getString("ANTECEDENTES");
		justificacion	= rs.getString("JUSTIFICACION");	
		resDocente		= rs.getString("RES_DOCENTE");
		resAlumno		= rs.getString("RES_ALUMNO");
		investigadores	= rs.getString("INVESTIGADORES");
	}
	
	public void mapeaRegId(Connection con, String proyectoId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA,"
					+ " CARRERA_ID, DEPARTAMENTO, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, "
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, "
					+ " ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO"
					+ " WHERE PROYECTO_ID = ? "); 
			ps.setString(1,proyectoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}