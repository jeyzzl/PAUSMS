package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecInformeAlumno {	
	
	private String codigoPersonal;
	private String informeId;
	private String idEjercicio;	
	private String puestoId;
	private String fecha;
	private String horas;
	private String tardanzas;
	private String ausencias;
	private String puntualidad;
	private String funcion;
	private String tiempo;
	private String iniciativa;
	private String relacion;
	private String respeto;
	private String productivo;
	private String cuidado;
	private String estado;
	private String idCcosto;
	private String usuario;
	
	public BecInformeAlumno(){
		codigoPersonal		= "";
		informeId			= "";
		idEjercicio			= "";
		fecha				= "";
		puestoId			= "";
		horas				= "";
		tardanzas			= "";
		ausencias			= "";
		puntualidad			= "";
		funcion				= "";
		tiempo				= "";
		iniciativa			= "";
		relacion			= "";
		respeto				= "";
		productivo			= "";
		cuidado				= "";
		estado 				= "";
		idCcosto			= "";
		usuario				= "";
	}
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getInformeId() {
		return informeId;
	}

	public void setInformeId(String informeId) {
		this.informeId = informeId;
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(String puestoId) {
		this.puestoId = puestoId;
	}	
	

	/**
	 * @return the tardanzas
	 */
	public String getTardanzas() {
		return tardanzas;
	}


	/**
	 * @param tardanzas the tardanzas to set
	 */
	public void setTardanzas(String tardanzas) {
		this.tardanzas = tardanzas;
	}


	/**
	 * @return the ausencias
	 */
	public String getAusencias() {
		return ausencias;
	}


	/**
	 * @param ausencias the ausencias to set
	 */
	public void setAusencias(String ausencias) {
		this.ausencias = ausencias;
	}


	/**
	 * @return the puntualidad
	 */
	public String getPuntualidad() {
		return puntualidad;
	}


	/**
	 * @param puntualidad the puntualidad to set
	 */
	public void setPuntualidad(String puntualidad) {
		this.puntualidad = puntualidad;
	}


	/**
	 * @return the funcion
	 */
	public String getFuncion() {
		return funcion;
	}


	/**
	 * @param funcion the funcion to set
	 */
	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}


	/**
	 * @return the tiempo
	 */
	public String getTiempo() {
		return tiempo;
	}


	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}


	/**
	 * @return the iniciativa
	 */
	public String getIniciativa() {
		return iniciativa;
	}


	/**
	 * @param iniciativa the iniciativa to set
	 */
	public void setIniciativa(String iniciativa) {
		this.iniciativa = iniciativa;
	}


	/**
	 * @return the relacion
	 */
	public String getRelacion() {
		return relacion;
	}


	/**
	 * @param relacion the relacion to set
	 */
	public void setRelacion(String relacion) {
		this.relacion = relacion;
	}


	/**
	 * @return the respeto
	 */
	public String getRespeto() {
		return respeto;
	}


	/**
	 * @param respeto the respeto to set
	 */
	public void setRespeto(String respeto) {
		this.respeto = respeto;
	}


	/**
	 * @return the productivo
	 */
	public String getProductivo() {
		return productivo;
	}


	/**
	 * @param productivo the productivo to set
	 */
	public void setProductivo(String productivo) {
		this.productivo = productivo;
	}


	/**
	 * @return the cuidado
	 */
	public String getCuidado() {
		return cuidado;
	}


	/**
	 * @param cuidado the cuidado to set
	 */
	public void setCuidado(String cuidado) {
		this.cuidado = cuidado;
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

	public String getIdCcosto() {
		return idCcosto;
	}
	
	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void mapeaReg(ResultSet rs) throws SQLException{
		
		codigoPersonal			= rs.getString("CODIGO_PERSONAL");
		informeId				= rs.getString("INFORME_ID");
		idEjercicio				= rs.getString("ID_EJERCICIO");
		horas					= rs.getString("HORAS");
		puestoId				= rs.getString("PUESTO_ID");
		tardanzas				= rs.getString("TARDANZAS");
		ausencias				= rs.getString("AUSENCIAS");
		puntualidad				= rs.getString("PUNTUALIDAD");
	    fecha					= rs.getString("FECHA");
		funcion					= rs.getString("FUNCION");
		tiempo					= rs.getString("TIEMPO");
		iniciativa				= rs.getString("INICIATIVA");
		relacion				= rs.getString("RELACION");
		respeto					= rs.getString("RESPETO");
		productivo				= rs.getString("PRODUCTIVO");
		cuidado					= rs.getString("CUIDADO");
		estado 					= rs.getString("ESTADO");
		idCcosto				= rs.getString("ID_CCOSTO");
		usuario					= rs.getString("USUARIO");
	}
	
	public void mapeaRegId(Connection conn, String informeId, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * FROM ENOC.BEC_INFORME_ALUMNO  WHERE INFORME_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, informeId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecInformeAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}
