// Bean del Catalogo de Extensiones
package  aca.catalogo;

import java.sql.*;

public class CatExtension{
	private String extensionId;
	private String nombreExtension;
	private String referente;
	private String direccion;
	private String colonia;
	private String codPostal;
	private String telefono;
	private String fax;	
	private String email;
	
	
	public CatExtension(){
		extensionId		= "";
		nombreExtension	= "";
		referente			= "";
		direccion			= "";
		colonia				= "";
		codPostal			= "";
		telefono			= "";
		fax					= "";
		email				= "";		
	}
	
	public String getExtensionId(){
		return extensionId;
	}
	
	public void setExtensionId( String extensionId){
		this.extensionId = extensionId;
	}	
	
	public String getNombreExtension(){
		return nombreExtension;
	}
	
	public void setNombreExtension( String nombreExtension){
		this.nombreExtension = nombreExtension;
	}	

	public String getReferente(){
		return referente;
	}

	public void setReferente( String referente){
		this.referente = referente;
	}
	
	public String getDireccion(){
		return direccion;
	}
	
	public void setDireccion( String direccion){
		this.direccion = direccion;
	}
	
	public String getColonia(){
		return direccion;
	}
	
	public void setColonia( String colonia){
		this.colonia = colonia;
	}
	
	public String getCodPostal(){
		return codPostal;
	}
	
	public void setCodPostal( String codPostal){
		this.codPostal = codPostal;
	}
	
	public String getTelefono(){
		return telefono;
	}
	
	public void setTelefono( String telefono){
		this.telefono = telefono;
	}
	
	public String getFax(){
		return fax;
	}
	
	public void setFax( String fax){
		this.fax = fax;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail( String email){
		this.email = email;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_EXTENSION"+ 
				"(EXTENSION_ID, NOMBRE_EXTENSION, REFERENTE, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL ) "+
				"VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, ? )");
			ps.setString(1, extensionId);
			ps.setString(2, nombreExtension);
			ps.setString(3, referente);
			ps.setString(4, direccion);
			ps.setString(5, colonia);
			ps.setString(6, codPostal);
			ps.setString(7, telefono);
			ps.setString(8, fax);
			ps.setString(9, email);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_EXTENSION "+ 
				"SET NOMBRE_EXTENSION = ?, "+
				"REFERENTE = ?, "+
				"DIRECCION = ?, "+
				"COLONIA = ?, "+
				"COD_POSTAL = ?, "+
				"TELEFONO = ?, "+
				"FAX = ?, "+
				"EMAIL = ? "+
				"WHERE EXTENSION_ID = TO_NUMBER(?,'999') ");
			ps.setString(1, nombreExtension);
			ps.setString(2, referente);
			ps.setString(3, direccion);
			ps.setString(4, colonia);
			ps.setString(5, codPostal);
			ps.setString(6, telefono);
			ps.setString(7, fax);
			ps.setString(8, email);
			ps.setString(9, extensionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_EXTENSION "+ 
				"WHERE EXTENSION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, extensionId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		extensionId 		= rs.getString("EXTENSION_ID");
		nombreExtension 	= rs.getString("NOMBRE_EXTENSION");
		referente		 	= rs.getString("REFERENTE");
		direccion 			= rs.getString("DIRECCION");
		colonia	 			= rs.getString("COLONIA");
		codPostal 			= rs.getString("COD_POSTAL");
		telefono			= rs.getString("TELEFONO");
		fax 				= rs.getString("FAX");
		email 				= rs.getString("EMAIL");		
	}
	
	public void mapeaRegId( Connection conn, String extensionId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT EXTENSION_ID, NOMBRE_EXTENSION, "+
				"REFERENTE, DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL "+
				"FROM ENOC.CAT_EXTENSION WHERE EXTENSION_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, extensionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_EXTENSION "+ 
				"WHERE EXTENSION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, extensionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
			
	public String maximoReg(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EXTENSION_ID)+1 MAXIMO FROM ENOC.CAT_EXTENSION"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatExtension|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}