// Bean del Catalogo de Uniones
package  aca.catalogo;

import java.sql.*;

public class CatUnion{
	private String divisionId;
	private String unionId;
	private String nombreUnion;
	private String direccion;
	private String colonia;
	private String codPostal;
	private String telefono;
	private String fax;
	private String email;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	
	public CatUnion(){
		divisionId		= "";
		unionId 		= "";
		nombreUnion	= "";
		direccion		= "";
		colonia			= "";
		codPostal		= "";
		telefono		= "";
		fax				= "";
		email			= "";
		paisId			= "";
		estadoId		= "";
		ciudadId		= "";
	}
	
	public String getDivisionId(){
		return divisionId;
	}
	
	public void setDivisionId( String divisionId){
		this.divisionId = divisionId;
	}	

	public String getUnionId(){
		return unionId;
	}

	public void setUnionId( String unionId){
		this.unionId = unionId;
	}
	
	public String getNombreUnion(){
		return nombreUnion;
	}
	
	public void setNombreUnion( String nombreUnion){
		this.nombreUnion = nombreUnion;
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
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getCiudadId(){
		return ciudadId;
	}
	
	public void setCiudadId( String ciudadId){
		this.ciudadId = ciudadId;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_UNION"+ 
				"(DIVISION_ID, UNION_ID, NOMBRE_UNION, DIRECCION, COLONIA, "+
				"COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID ) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'))");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);
			ps.setString(3, nombreUnion);
			ps.setString(4, direccion);
			ps.setString(5, colonia);
			ps.setString(6, codPostal);
			ps.setString(7, telefono);
			ps.setString(8, fax);
			ps.setString(9, email);
			ps.setString(10,paisId);
			ps.setString(11,estadoId);
			ps.setString(12,ciudadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_UNION "+ 
				"SET NOMBRE_UNION = ?, DIRECCION = ?, COLONIA = ?, COD_POSTAL = ?, "+
				"TELEFONO = ?, FAX = ?, EMAIL = ?, PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), CIUDAD_ID = TO_NUMBER(?,'999') "+
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') AND UNION_ID = TO_NUMBER(?,'999') ");
			ps.setString(1, nombreUnion);
			ps.setString(2, direccion);
			ps.setString(3, colonia);
			ps.setString(4, codPostal);
			ps.setString(5, telefono);
			ps.setString(6, fax);
			ps.setString(7, email);
			ps.setString(8, paisId);
			ps.setString(9, estadoId);
			ps.setString(10,ciudadId);
			ps.setString(11,divisionId);
			ps.setString(12,unionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_UNION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?,'999') AND UNION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		divisionId 		= rs.getString("DIVISION_ID");
		unionId 			= rs.getString("UNION_ID");
		nombreUnion 		= rs.getString("NOMBRE_UNION");
		direccion 			= rs.getString("DIRECCION");
		colonia	 			= rs.getString("COLONIA");
		codPostal 			= rs.getString("COD_POSTAL");
		telefono			= rs.getString("TELEFONO");
		fax 				= rs.getString("FAX");
		email 				= rs.getString("EMAIL");
		paisId				= rs.getString("PAIS_ID");
		estadoId 			= rs.getString("ESTADO_ID");
		ciudadId			= rs.getString("CIUDAD_ID");
	}
	
	public void mapeaRegId( Connection conn, String divisionId, String unionId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT DIVISION_ID, UNION_ID, NOMBRE_UNION, "+
					"DIRECCION, COLONIA, COD_POSTAL, TELEFONO, FAX, EMAIL, PAIS_ID, ESTADO_ID, CIUDAD_ID "+
					"FROM ENOC.CAT_UNION WHERE DIVISION_ID = TO_NUMBER(?, '999') AND UNION_ID = TO_NUMBER(?, '999')"); 
			ps.setString(1, divisionId);
			ps.setString(2, unionId);		
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_UNION "+ 
				"WHERE DIVISION_ID = TO_NUMBER(?, '999') AND UNION_ID = TO_NUMBER(?, '999')");
			ps.setString(1, divisionId);
			ps.setString(2, unionId);			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String divisionId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(UNION_ID)+1,1) MAXIMO FROM ENOC.CAT_UNION WHERE DIVISION_ID = ?"); 
			ps.setString(1,divisionId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatUnion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
}