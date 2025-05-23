package aca.cred;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredHsm {
	
	private String clave;
	private String nombre;
	private String area;
	private String fondo;
	private String estado;
			
	public CredHsm(){
		clave 		= "";
		nombre		= "";
		area		= "";
		fondo		= "";
		estado 		= "";
	}	

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		clave			 	= rs.getString("CLAVE");
		nombre 				= rs.getString("NOMBRE");
		area	 			= rs.getString("AREA");
		fondo				= rs.getString("FONDO");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String clave) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CLAVE, NOMBRE, AREA, FONDO, ESTADO " +
				"FROM ENOC.CRED_HSM WHERE CLAVE = ? "); 
			ps.setString(1, clave);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
	}
}