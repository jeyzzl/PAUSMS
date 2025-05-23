package aca.salida;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalInvitado {
	private String salidaId;
	private String folio;
	private String nombre;
	private String tipo;	
	
	public SalInvitado(){
		salidaId	= "";
		folio		= "0";
		nombre		= "-";
		tipo		= "";						
	}
	
	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void mapeaReg(ResultSet rs) throws SQLException {
        salidaId            = rs.getString("SALIDA_ID");
        folio				= rs.getString("FOLIO");
        nombre				= rs.getString("NOMBRE");
		tipo				= rs.getString("TIPO");		
    }	
}