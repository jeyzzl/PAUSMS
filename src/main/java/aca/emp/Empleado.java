package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Empleado {
	private String id;
	private String clave;
	private String nombre;
	private String appaterno;
	private String apmaterno;
	private String fechanacimiento;
	private String direccion;
	private String genero;
	private String status;
	private String nacionalidad;
	
	public Empleado(){
		id           	= "";
		clave			= "";
		nombre      	= "";
		appaterno   	= "";
		apmaterno    	= "";
		fechanacimiento	= "";
		direccion	   	= "";
		genero	        = "";
		status          = "";
		nacionalidad    = "";
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the appaterno
	 */
	public String getAppaterno() {
		return appaterno;
	}

	/**
	 * @param appaterno the appaterno to set
	 */
	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}

	/**
	 * @return the apmaterno
	 */
	public String getApmaterno() {
		return apmaterno;
	}

	/**
	 * @param apmaterno the apmaterno to set
	 */
	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}

	/**
	 * @return the fechanacimiento
	 */
	public String getFechanacimiento() {
		return fechanacimiento;
	}

	/**
	 * @param fechanacimiento the fechanacimiento to set
	 */
	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad the nacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		id	            = rs.getString("ID");
		clave			= rs.getString("CLAVE");
		nombre       	= rs.getString("NOMBRE");
		appaterno    	= rs.getString("APPATERNO");
		apmaterno       = rs.getString("APMATERNO");
		fechanacimiento = rs.getString("FECHANACIMIENTO");
		direccion	    = rs.getString("DIRECCION");
		genero   	    = rs.getString("GENERO");
		status		    = rs.getString("STATUS");
		nacionalidad    = rs.getString("NACIONALIDAD");
	}
	
	public void mapeaRegId( Connection conn, String id) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+   
				"FROM ARON.EMPLEADO "+
				"WHERE ID = ?");
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
	public void mapeaRegClave( Connection conn, String clave) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"TO_NUMBER(ID,'9999999') AS ID, CLAVE, NOMBRE, APPATERNO, APMATERNO,  " +
				"TO_CHAR(FECHANACIMIENTO, 'DD/MM/YYYY') AS FECHANACIMIENTO, DIRECCION, GENERO, STATUS, " +
				"TO_NUMBER(NACIONALIDAD,'999') AS NACIONALIDAD "+
				"FROM ARON.EMPLEADO "+
				"WHERE ID = ?");
			
			ps.setString(1, clave);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.EmpleadoUtil|mapeaRegClave|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
}