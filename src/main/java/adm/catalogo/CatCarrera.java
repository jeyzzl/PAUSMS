// Bean del Catalogo de Carreras
package  adm.catalogo;

import java.sql.*;

public class CatCarrera{
	private String facultadId;
	private	String carreraId;
	private String nivelId;
	private String titulo;
	private String nombreCarrera;
	private String nombreCorto;
	private String ccostoId;
	private String codigoPersonal;	
	
	public CatCarrera(){
		facultadId 	= "";
		carreraId		= "";
		nivelId		= "";
		titulo			= "";
		nombreCarrera	= "";
		nombreCorto	= "";
		ccostoId		= "";
		codigoPersonal	= "";		
	}
	
	public String getFacultadId(){
		return facultadId;
	}
	
	public void setFacultadId( String facultadId){
		this.facultadId = facultadId;
	}
	
	public String getCarreraId(){
		return carreraId;
	}
	
	public void setCarreraId( String carreraId){
		this.carreraId = carreraId;
	}
	
	public String getNivelId(){
		return nivelId;
	}
	
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}
	
	public String getTitulo(){
		return titulo;
	}
		
	public void setTitulo( String titulo){
		this.titulo = titulo;
	}
	
	public String getNombreCarrera(){
		return nombreCarrera;
	}
	
	public void setNombreCarrera( String nombreCarrera){
		this.nombreCarrera = nombreCarrera;
	}
		
	public String getNombreCorto(){
		return nombreCorto;
	}

	public void setNombreCorto( String nombreCorto){
		this.nombreCorto = nombreCorto;
	}
	
	public String getCcostoId(){
		return ccostoId;
	}
	
	public void setCcostoId( String ccostoId){
		this.ccostoId = ccostoId;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}
	
		
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			 
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_CARRERA"+
				"(FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'99') , ? , ?, ?, ?, ?)");		
			ps.setString(1, facultadId);
			ps.setString(2, carreraId);
			ps.setString(3, nivelId);
			ps.setString(4, titulo);
			ps.setString(5, nombreCarrera);
			ps.setString(6, nombreCorto);
			ps.setString(7, ccostoId);
			ps.setString(8, codigoPersonal);						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_CARRERA "+
				"SET FACULTAD_ID = ?, NIVEL_ID = TO_NUMBER(?,'99'), TITULO = ?, "+
				"NOMBRE_CARRERA = ?, NOMBRE_CORTO = ?, CCOSTO_ID = ?, "+
				"CODIGO_PERSONAL = ? "+
				"WHERE CARRERA_ID = ?");
			ps.setString(1, facultadId);
			ps.setString(2, nivelId);
			ps.setString(3, titulo);
			ps.setString(4, nombreCarrera);
			ps.setString(5, nombreCorto);
			ps.setString(6, ccostoId);
			ps.setString(7, codigoPersonal);			
			ps.setString(9, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_CARRERA "+
				"WHERE CARRERA_ID = ?");			
			ps.setString(1, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		facultadId 			= rs.getString("FACULTAD_ID");
		carreraId 			= rs.getString("CARRERA_ID");
		nivelId 			= rs.getString("NIVEL_ID");
		titulo	 			= rs.getString("TITULO");
		nombreCarrera 		= rs.getString("NOMBRE_CARRERA");
		nombreCorto		= rs.getString("NOMBRE_CORTO");
		ccostoId 			= rs.getString("CCOSTO_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");		
	}
	
	public void mapeaRegId(Connection conn, String facultadId, String carreraId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FACULTAD_ID, "+
			"CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
			"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? AND CARRERA_ID = ?");		
		ps.setString(1, facultadId);
		ps.setString(2, carreraId);
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public void mapeaRegIdsinFac(Connection conn, String carreraId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FACULTAD_ID, "+
			"CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
			"FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");		
		ps.setString(1, carreraId);
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CARRERA_ID)+1 MAXIMO FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?");
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public String getNombreCarrera(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CARRERA");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getNombreCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreCarrera(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CARRERA");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getNombreCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreCorto(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= " ";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getFacultadId(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String facultadId		= "";
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				facultadId = rs.getString("FACULTAD_ID");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getFacultadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultadId;
	}
	
	public static String getCoordinador(Connection conn, String carreraId) throws SQLException{
		ResultSet rs				= null;
		PreparedStatement ps		= null;
		String coordinadorId		= "";
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				coordinadorId = rs.getString("CODIGO_PERSONAL");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getFacultadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return coordinadorId;
	}
	
	public static String getNivel(Connection conn, String carreraId) throws SQLException{
		ResultSet rs				= null;
		PreparedStatement ps		= null;
		String nivelId		= "0";
		
		try{
			ps = conn.prepareStatement("SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nivelId = rs.getString("NIVEL_ID");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarrera|getNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nivelId;
	}
	
	public static String getNombreFacultad(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT TITULO||CASE FACULTAD_ID WHEN '107' THEN ' ' ELSE ' de ' END ||NOMBRE_FACULTAD AS NOMBRE "+
					"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarrera|getNombreFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}