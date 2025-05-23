//Beans de la tabla ARCH_DOCUMENTOS

package aca.archivo;
import java.sql.*;

public class ArchEntrega {
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String documentos;
	private byte[] identificacion;
	private byte[] poder;
	private byte[] firma;
	private byte[] envio;
	private byte[] extra;

	public ArchEntrega(){
		codigoPersonal	= "";
		folio			= "0";
		fecha			= "";
		documentos		= "0";
		identificacion 	= null;
		poder 			= null;
		firma 			= null;
		envio 			= null;
		extra 			= null;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public byte[] getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(byte[] identificacion) {
		this.identificacion = identificacion;
	}

	public byte[] getPoder() {
		return poder;
	}

	public void setPoder(byte[] poder) {
		this.poder = poder;
	}

	public byte[] getFirma() {
		return firma;
	}

	public void setFirma(byte[] firma) {
		this.firma = firma;
	}

	public byte[] getEnvio() {
		return envio;
	}

	public void setEnvio(byte[] envio) {
		this.envio = envio;
	}

	public byte[] getExtra() {
		return extra;
	}

	public void setExtra(byte[] extra) {
		this.extra = extra;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		folio 				= rs.getString("FOLIO");		
		fecha	 			= rs.getString("FECHA");
		documentos			= rs.getString("DOCUMENTOS");
	}
	
	public void mapeaRegAll(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		folio 				= rs.getString("FOLIO");		
		fecha	 			= rs.getString("FECHA");
		documentos			= rs.getString("DOCUMENTOS");
		identificacion		= rs.getBytes("IDENTIFICACION");
		poder				= rs.getBytes("PODER");
		envio				= rs.getBytes("ENVIO");
		firma				= rs.getBytes("FIRMA");
		extra				= rs.getBytes("EXTRA");
	}
	
}		