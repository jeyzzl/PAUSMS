// Clase para la tabla de Modulo_opcion
package aca.menu;

import java.sql.*;

public class Opcion  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String moduloId;
	private String opcionId;
	private String nombreOpcion;
	private String url;
	private String icono;
	private String orden;
	private String usuarios;
	private String carpeta;
	
	// Constructor
	public Opcion(){
		moduloId 		= "";
		opcionId		= "";
		nombreOpcion	= "";
		url 			= "";
		icono			= "";
		orden 			= "";
		usuarios		= "";
		carpeta			= "/";
	}
	
	public String getModuloId(){
		return moduloId;
	}
	
	public void setModuloId(String moduloId){
		this.moduloId = moduloId;
	}

	public String getOpcionId(){
		return opcionId;
	}

	public void setOpcionId(String opcionId){
		this.opcionId = opcionId;
	}
	
	public String getNombreOpcion(){
		return nombreOpcion;
	}
	
	public void setNombreOpcion(String nombreOpcion){
		this.nombreOpcion = nombreOpcion;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getIcono(){
		return icono;
	}
	
	public void setIcono(String icono){
		this.icono = icono;
	}
	
	public String getOrden(){
		return orden;
	}
	
	public void setOrden(String orden){
		this.orden = orden;
	}
	
	public String getUsuarios(){
		return usuarios;
	}
	
	public void setUsuarios(String usuarios){
		this.usuarios = usuarios;
	}	
	
	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		moduloId 		= rs.getString("MODULO_ID");
		opcionId 		= rs.getString("OPCION_ID");		
		nombreOpcion	= rs.getString("NOMBRE_OPCION");		
		url 			= rs.getString("URL");
		icono			= rs.getString("ICONO");
		orden 			= rs.getString("ORDEN");
		usuarios		= rs.getString("USUARIOS");
		carpeta			= rs.getString("CARPETA");
		
	}
	
	public void mapeaRegId(Connection conn, String moduloId, String opcionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, "+
							"ICONO, ORDEN, USUARIOS, CARPETA FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ? "); 
			ps.setString(1,moduloId);
			ps.setString(2,opcionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Opcion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId(Connection conn, String opcionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, "+
							"ICONO, ORDEN, USUARIOS, CARPETA FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ? "); 
			ps.setString(1,opcionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Opcion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	
}