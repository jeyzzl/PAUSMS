package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContConcepto {
	
	private String id;
	private String version;
	private String descripcion;
	private String status;
	private String nombre;
	private String tags;
	
	
	public ContConcepto(){
		
		id 			= "";
		version 	= "";
		descripcion = "";
		status 		= "";
		nombre		= "";
		tags 		= "";
		
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		id	       	= rs.getString("ID");
		version	   	= rs.getString("VERSION");
		descripcion	= rs.getString("DESCRIPCION");
		status		= rs.getString("STATUS");
		nombre		= rs.getString("NOMBRE");
		tags		= rs.getString("TAGS");
	}
	
	public void mapeaRegId(Connection conn) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID, VERSION, DESCRIPCION, STATUS, NOMBRE, TAGS FROM MATEO.CONT_CONCEPTO");
	 		ps.setString(1, id);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCcosto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	

}
