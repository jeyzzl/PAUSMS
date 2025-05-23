// Bean del Catalogo de Facultades
package  adm.catalogo;

import java.sql.*;

public class CatFacultad{
	private String areaId;
	private String facultadId;
	private String titulo;
	private String nombreFacultad;
	private String codigoPersonal;
	
	public CatFacultad(){
		areaId 		= "";
		facultadId		= "";
		titulo			= "";
		nombreFacultad	= "";
		codigoPersonal	= "";
	}
	
	public String getAreaId(){
		return areaId;
	}
	
	public void setAreaId( String areaId){
		this.areaId = areaId;
	}
	
	public String getFacultadId(){
		return facultadId;
	}
	
	public void setFacultadId( String facultadId){
		this.facultadId = facultadId;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo( String titulo){
		this.titulo = titulo;
	}
	
	public String getNombreFacultad(){
		return nombreFacultad;
	}
	
	public void setNombreFacultad( String nombreFacultad){
		this.nombreFacultad = nombreFacultad;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		areaId 			= rs.getString("AREA_ID");
		facultadId 		= rs.getString("FACULTAD_ID");
		titulo	 			= rs.getString("TITULO");
		nombreFacultad 	= rs.getString("NOMBRE_FACULTAD");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
	}
	
	public void mapeaRegId( Connection conn, String facultadId, String areaId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL "+
			"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ? AND AREA_ID = ?");		
		ps.setString(1, facultadId);
		ps.setString(2, areaId);
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public void mapeaRegId( Connection conn, String facultadId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL "+
			"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?");		
		ps.setString(1, facultadId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
}