package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecPuesto {
	private String idEjercicio;
	private String idCcosto;
	private String categoriaId;
	private String justificacion;
	private String funcion;
	private String competencias;
	private String certificaciones;
	private String cantidad;
	private String estado;
	private String otrasComp;
	private String periodoId;
	
	public BecPuesto(){
		idEjercicio		= "";
		idCcosto		= "";
		categoriaId		= "";
		justificacion	= "";
		funcion			= "";
		competencias	= "";
		certificaciones	= "";
		cantidad		= "";
		estado			= "";
		otrasComp		= "";
		periodoId		= "";
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

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getCompetencias() {
		return competencias;
	}

	public void setCompetencias(String competencias) {
		this.competencias = competencias;
	}

	public String getCertificaciones() {
		return certificaciones;
	}

	public void setCertificaciones(String certificaciones) {
		this.certificaciones = certificaciones;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getOtrasComp() {
		return otrasComp;
	}

	public void setOtrasComp(String otrasComp) {
		this.otrasComp = otrasComp;
	}

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		idEjercicio		= rs.getString("ID_EJERCICIO");
		idCcosto		= rs.getString("ID_CCOSTO");
		categoriaId		= rs.getString("CATEGORIA_ID");
		justificacion	= rs.getString("JUSTIFICACION");
		funcion			= rs.getString("FUNCION");
		competencias	= rs.getString("COMPETENCIAS");
		certificaciones	= rs.getString("CERTIFICACIONES");
		cantidad		= rs.getString("CANTIDAD");
		estado			= rs.getString("ESTADO");
		otrasComp		= rs.getString("OTRAS_COMP");
		periodoId		= rs.getString("PERIODO_ID");
	}
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  categoriaId);
			ps.setString(4,  periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuesto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}