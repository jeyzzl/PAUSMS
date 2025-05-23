/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author etorres
 *
 */
public class Edo {
	private String edoId;
	private String nombre;
	private String fInicio;
	private String fFinal;
	private String periodoId;
	private String tipo;
	private String modalidad;
	private String encabezado;
	private String tipoEncuesta;
	private String cargas;
	private String excepto;
	
	public Edo(){
		edoId			= "";
		nombre			= "";
		fInicio			= "";
		fFinal			= "";
		periodoId 		= "";
		tipo			= "";
		modalidad		= "";
		encabezado		= "";
		tipoEncuesta	= "";
		cargas			= "";
		excepto			= "";
	}

	public String getEdoId() {
		return edoId;
	}
	
	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFInicio() {
		return fInicio;
	}

	public void setFInicio(String inicio) {
		fInicio = inicio;
	}

	public String getFFinal() {
		return fFinal;
	}

	public void setFFinal(String final1) {
		fFinal = final1;
	}	
	
	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	
	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}
	
	public String getTipoEncuesta() {
		return tipoEncuesta;
	}

	public void setTipoEncuesta(String tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}
	
	public String getExcepto() {
		return excepto;
	}

	public void setExcepto(String excepto) {
		this.excepto = excepto;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edoId			= rs.getString("EDO_ID");
		nombre			= rs.getString("NOMBRE");
		fInicio			= rs.getString("F_INICIO");
		fFinal			= rs.getString("F_FINAL");
		periodoId		= rs.getString("PERIODO_ID");
		tipo			= rs.getString("TIPO");
		modalidad		= rs.getString("MODALIDAD");
		encabezado		= rs.getString("ENCABEZADO");
		tipoEncuesta	= rs.getString("TIPO_ENCUESTA");
		cargas			= rs.getString("CARGAS");
		excepto			= rs.getString("EXCEPTO");
	}
	
	public void mapeaRegId(Connection con, String edoId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT EDO_ID, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO " +
					" FROM ENOC.EDO" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, edoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}