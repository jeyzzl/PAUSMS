package aca.salida;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalAuto {
	private String salidaId;
	private String tipo;
	private String poliza;
	private String telefono;
	private byte[] imagen;
	
	public SalAuto(){
		salidaId     = "";
		tipo		 = "";
		poliza		 = "";
		telefono	 = "";
		imagen		 = null;				
	}

	public String getSalidaId() {
		return salidaId;
	}

	public void setSalidaId(String salidaId) {
		this.salidaId = salidaId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException {
        salidaId            = rs.getString("SALIDA_ID");
		tipo				= rs.getString("TIPO");
		poliza				= rs.getString("POLIZA");
		telefono			= rs.getString("TELEFONO");
		imagen				= rs.getBytes("IMAGEN");
    }
	
	public void mapeaRegCorto(ResultSet rs) throws SQLException {
        salidaId            = rs.getString("SALIDA_ID");
		tipo				= rs.getString("TIPO");
		poliza				= rs.getString("POLIZA");
		telefono			= rs.getString("TELEFONO");		
    }
	
}