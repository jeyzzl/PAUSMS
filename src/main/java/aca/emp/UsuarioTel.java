package aca.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioTel {
	private String codigoPersonal;
	private String telefono;
	private String extension;
	private String lugar;
	
	public UsuarioTel(){
		codigoPersonal		= "";
		telefono	    	= "";
		extension	    	= "";
		lugar				= "";
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
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}


	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}


	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}


	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}


	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		telefono      	= rs.getString("TELEFONO");
		extension    	= rs.getString("EXTENSION");
		lugar			= rs.getString("LUGAR");
		
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, TELEFONO, EXTENSION, LUGAR" +						
				" FROM ENOC.USUARIO_TEL WHERE CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}

}