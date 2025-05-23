package aca.leg;

import java.sql.*;

public class LegDocumento {
	public String idDocumentos;
	public String descripcion;
	public String imagen;
	
	public LegDocumento(){
		idDocumentos	= "";
		descripcion		= "";
		imagen			="";
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getIdDocumentos() {
		return idDocumentos;
	}
	

	public void setIdDocumentos(String idDocumentos) {
		this.idDocumentos = idDocumentos;
	}
	

	public String getImagen() {
		return imagen;
	}
	

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	

	public void mapeaReg(ResultSet rs) throws SQLException{
		idDocumentos=		rs.getString("IDDOCUMENTOS");
		descripcion=		rs.getString("DESCRIPCION");
		imagen=				rs.getString("IMAGEN");
	}
	
}