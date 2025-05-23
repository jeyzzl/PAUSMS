// Clase para la tabla de Modulo
package aca.candado.spring;
import java.sql.*;

public class CandAlumno{ 
	private String codigoPersonal;
	private String folio;
	private String candadoId;
	private String fCreado;
	private String fBorrado;
	private String usAlta;
	private String usBaja;
	private String comentario;
	private String estado;
	private String tipoId;
	
	// Constructor
	public CandAlumno(){
		codigoPersonal	= "0";
		folio			= "0";
		candadoId		= "0";
		fCreado			= aca.util.Fecha.getHoy();
		fBorrado		= "";
		usAlta			= "";
		usBaja			= "";
		comentario		= "";
		estado			= "A";	
		tipoId 			= "0";			
	}	
	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}


	public String getCandadoId() {
		return candadoId;
	}


	public void setCandadoId(String candadoId) {
		this.candadoId = candadoId;
	}


	public String getfCreado() {
		return fCreado;
	}


	public void setfCreado(String fCreado) {
		this.fCreado = fCreado;
	}


	public String getfBorrado() {
		return fBorrado;
	}


	public void setfBorrado(String fBorrado) {
		this.fBorrado = fBorrado;
	}


	public String getUsAlta() {
		return usAlta;
	}


	public void setUsAlta(String usAlta) {
		this.usAlta = usAlta;
	}


	public String getUsBaja() {
		return usBaja;
	}


	public void setUsBaja(String usBaja) {
		this.usBaja = usBaja;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		candadoId  		= rs.getString("CANDADO_ID");
		fCreado			= rs.getString("F_CREADO");
		fBorrado		= rs.getString("F_BORRADO");
		usAlta			= rs.getString("US_ALTA");
		usBaja			= rs.getString("US_BAJA");
		comentario		= rs.getString("COMENTARIO");
		estado			= rs.getString("ESTADO");
		tipoId 			= rs.getString("TIPO_ID");
	}

}