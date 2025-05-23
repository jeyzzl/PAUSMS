// Bean del Catalogo Planes
package  aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumPlan{
	private String codigoPersonal;
	private String planId;	
	private String fInicio;
	private String estado;
	private String escuelaId;
	private String avanceId;
	private String permiso;
	private String evento;
	private String fGraduacion;
	private String fEgreso;
	private String grado;
	private String ciclo;
	private String principal;
	private String escala;
	private String primerMatricula;	
	private String actualizado;
	private String cicloSep;

	public AlumPlan(){
		codigoPersonal	= "";
		planId			= "";		
		fInicio			= "";
		estado			= "";
		escuelaId		= "";
		avanceId		= "";
		permiso			= "";
		evento			= "";
		fGraduacion 	= "";
		fEgreso			= "";
		grado			= "";
		ciclo			= "";
		principal		= "";
		escala			= "";
		primerMatricula = "";
		actualizado		= "";
		cicloSep		= "0";
	}
	
	
		public String getEscala() {
		return escala;
	}


	public void setEscala(String escala) {
		this.escala = escala;
	}


		/**
	 * @return the grado
	 */
	public String getGrado() {
		return grado;
	}
	/**
	 * @param grado the grado to set
	 */
	public void setGrado(String grado) {
		this.grado = grado;
	}
	/**
	 * @return the ciclo
	 */
	public String getCiclo() {
		return ciclo;
	}
	/**
	 * @param ciclo the ciclo to set
	 */
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	/**
	 * @return the fEgreso
	 */
	public String getFEgreso() {
		return fEgreso;
	}

	/**
	 * @param termino the fEgreso to set
	 */
	public void setFEgreso(String egreso) {
		fEgreso = egreso;
	}

	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	
	/**
	 * @return Returns the escuelaId.
	 */
	public String getEscuelaId() {
		return escuelaId;
	}
	/**
	 * @param escuelaId The escuelaId to set.
	 */
	public void setEscuelaId(String escuelaId) {
		this.escuelaId = escuelaId;
	}
	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	/**
	 * @return Returns the fGraduacion.
	 */
	public String getFGraduacion() {
		return fGraduacion;
	}	
	/**
	 * @param inicio The fGraduacion to set.
	 */
	public void setFGraduacion(String fGraduacion) {
		this.fGraduacion = fGraduacion;
	}	
	/**
	 * @return Returns the fInicio.
	 */
	public String getFInicio() {
		return fInicio;
	}
	/**
	 * @param inicio The fInicio to set.
	 */
	public void setFInicio(String fInicio) {
		this.fInicio = fInicio;
	}
			
	/**
	 * @return Returns the planId.
	 */
	public String getPlanId() {
		return planId;
	}
	/**
	 * @param planId The planId to set.
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}	
	/**
	 * @return Returns the avanceId.
	 */
	public String getAvanceId() {
		return avanceId;
	}
	/**
	 * @param avanceId The avanceId to set.
	 */
	public void setAvanceId(String avanceId) {
		this.avanceId = avanceId;
	}
	/**
	 * @return Returns the evento.
	 */
	public String getEvento() {
		return evento;
	}
	/**
	 * @param evento The evento to set.
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}
	/**
	 * @return Returns the permiso.
	 */
	public String getPermiso() {
		return permiso;
	}
	/**
	 * @param permiso The permiso to set.
	 */
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}	
	
	/**
	 * @return the principal
	 */
	public String getPrincipal() {
		return principal;
	}
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	public String getPrimerMatricula() {
		return primerMatricula;
	}
	
	public void setPrimerMatricula(String primerMatricula) {
		this.primerMatricula = primerMatricula;
	}
	
	/**
	 * @return the actualizado
	 */
	public String getActualizado() {
		return actualizado;
	}

	/**
	 * @param actualizado the actualizado to set
	 */
	public void setActualizado(String actualizado) {
		this.actualizado = actualizado;
	}
	
	public String getCicloSep() {
		return cicloSep;
	}
	
	public void setCicloSep(String cicloSep) {
		this.cicloSep = cicloSep;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		planId 				= rs.getString("PLAN_ID");		
		fInicio	 			= rs.getString("F_INICIO");
		estado				= rs.getString("ESTADO");
		escuelaId			= rs.getString("ESCUELA_ID");
		avanceId			= rs.getString("AVANCE_ID");
		permiso				= rs.getString("PERMISO");
		evento				= rs.getString("EVENTO");
		fGraduacion			= rs.getString("F_GRADUACION");
		fEgreso				= rs.getString("F_EGRESO");
		grado				= rs.getString("GRADO");
		ciclo				= rs.getString("CICLO");
		principal			= rs.getString("PRINCIPAL");
		escala				= rs.getString("ESCALA");
		primerMatricula		= rs.getString("PRIMER_MATRICULA");
		actualizado			= rs.getString("ACTUALIZADO");
		cicloSep			= rs.getString("CICLO_SEP");
	}
	
	public void mapeaRegId( Connection conn, String Codigo_personal, String Plan_id ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"
					+ " CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO, 'DD/MM/YYYY') AS F_EGRESO,"
					+ " GRADO, CICLO, PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA, 'DD/MM/YYYY') AS PRIMER_MATRICULA,"
					+ " ACTUALIZADO, CICLO_SEP"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?");
			ps.setString(1, Codigo_personal);
			ps.setString(2, Plan_id);
			
			rs = ps.executeQuery();
			if (rs.next()){
				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public void mapeaRegId( Connection conn, String Codigo_personal) throws SQLException{		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=? AND ESTADO='1'"); 
			ps.setString(1, Codigo_personal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
	
	public void mapeaRegIdE( Connection conn, String codigoPersonal, String planId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=? AND PLAN_ID='"+planId+"'"); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.PlanUtil|mapeaRegIdE|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}