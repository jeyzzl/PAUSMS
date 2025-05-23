package aca.vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuarios {
	private String codigoPersonal;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String fNacimiento;
	private String genero;	
	private String usuario;
	private String clave;
	private String estado;	
	
	public Usuarios(){
		codigoPersonal	= "";
		nombre			= "";
		apellidoPaterno	= "";
		apellidoMaterno	= "";
		fNacimiento    	= "";
		genero	     	= "";		
		usuario		    = "";
		clave           = "";
		estado          = "";		
	}
	

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the fNacimiento
	 */
	public String getFNacimiento() {
		return fNacimiento;
	}

	/**
	 * @param nacimiento the fNacimiento to set
	 */
	public void setFNacimiento(String nacimiento) {
		fNacimiento = nacimiento;
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
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		nombre			= rs.getString("NOMBRE");
		apellidoPaterno	= rs.getString("APELLIDO_PATERNO");
		apellidoMaterno	= rs.getString("APELLIDO_MATERNO");
		fNacimiento     = rs.getString("F_NACIMIENTO");
		genero		    = rs.getString("GENERO");		
		usuario	    	= rs.getString("USUARIO");
		clave		    = rs.getString("CLAVE");
		estado		    = rs.getString("ESTADO");	
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,  " +
				"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, USUARIO, CLAVE, ESTADO " +			   
				"FROM ENOC.USUARIOS "+
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Usuarios|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Usuarios|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.USUARIOS "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Usuarios|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean borrarPriv(Connection conn,String codigo) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs	= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("UPDATE MODULO_OPCION SET USUARIOS =  REPLACE(USUARIOS,'-"+codigo+"-', '-')");
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Usuarios|borrarPriv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

}