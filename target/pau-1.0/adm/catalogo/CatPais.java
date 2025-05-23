// Bean del Catalogo de Paises
package  adm.catalogo;

import java.sql.*;

public class CatPais{
	private String paisId;	
	private String nombrePais;
	private String nacionalidad;
	
	public CatPais(){
		paisId 		= "";
		nombrePais 	= "";
		nacionalidad	= "";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getNombrePais(){
		return nombrePais;
	}
	
	public void setNombrePais( String nombrePais){
		this.nombrePais = nombrePais;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public void setNacionalidad( String nacionalidad){
		this.nacionalidad = nacionalidad;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_PAIS(PAIS_ID, NOMBRE_PAIS, NACIONALIDAD) "+
				"VALUES( TO_NUMBER (?,'999'), ?, ? )");
			ps.setString(1, paisId);
			ps.setString(2, nombrePais);
			ps.setString(3, nacionalidad);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_PAIS "+
				"SET NOMBRE_PAIS = ?, NACIONALIDAD = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombrePais);
			ps.setString(2, nacionalidad);
			ps.setString(3, paisId);
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}

	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_PAIS "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1, paisId);
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 		= rs.getString("PAIS_ID");
		nombrePais 	= rs.getString("NOMBRE_PAIS");
		nacionalidad 	= rs.getString("NACIONALIDAD");
	}

	public void mapeaRegId( Connection conn, String paisId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD "+
			"FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')");
		ps.setString(1,paisId);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')");
			ps.setString(1,paisId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(PAIS_ID)+1 MAXIMO FROM ENOC.CAT_PAIS");			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombrePais(Connection conn, String paisId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = ?");
			ps.setString(1, paisId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_PAIS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|getNombrePais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNacionalidad(Connection Conn, String paisId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NACIONALIDAD FROM ENOC.CAT_PAIS WHERE PAIS_ID = "+paisId;
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPais|getNacionalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}