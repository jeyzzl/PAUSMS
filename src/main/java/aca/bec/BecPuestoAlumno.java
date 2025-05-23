package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecPuestoAlumno {
	private String puestoId;
	private String idEjercicio;
	private String idCcosto;
	private String categoriaId;
	private String codigoPersonal;
	private String fechaIni;
	private String fechaFin;
	private String tipo;
	private String estado;
	private String usuario;
	private String periodoId;
	private String nivelId;
	private String planId;
	private String descripcion;
	
	public BecPuestoAlumno(){
		puestoId		= "0";
		idEjercicio		= "0";
		idCcosto		= "0";
		categoriaId		= "0";
		codigoPersonal	= "0";
		fechaIni		= aca.util.Fecha.getHoy();
		fechaFin		= aca.util.Fecha.getHoy();
		tipo 			= "0";
		estado			= "A";
		usuario			= "0";
		periodoId		= "0";
		nivelId			= "1";
		planId			= "00000000";
		descripcion		= "-";
	}
	

	public String getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(String puestoId) {
		this.puestoId = puestoId;
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getIdCcosto() {
		return idCcosto;
	}

	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getNivelId() {
		return nivelId;
	}

	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		puestoId		= rs.getString("PUESTO_ID");
		idEjercicio 	= rs.getString("ID_EJERCICIO");
		idCcosto 		= rs.getString("ID_CCOSTO");
		categoriaId 	= rs.getString("CATEGORIA_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		fechaIni 		= rs.getString("FECHA_INI");
		fechaFin  		= rs.getString("FECHA_FIN");
		tipo 	 		= rs.getString("TIPO");
		estado 	 		= rs.getString("ESTADO");
		usuario	 		= rs.getString("USUARIO");
		periodoId 		= rs.getString("PERIODO_ID");
		nivelId 		= rs.getString("NIVEL_ID");
		planId 			= rs.getString("PLAN_ID");
		descripcion		= rs.getString("DESCRIPCION");
	}
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ?  "); 
			
			ps.setString(1,  puestoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegIdPuesto(Connection conn, String puestoId) throws SQLException{
	
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = '"+puestoId+"'  "); 
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|mapeaRegIdPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	
	}
	
}