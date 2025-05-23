// Bean del Catalogo de Niveles
package  adm.catalogo;

import java.sql.*;

public class CatNivel{
	private String nivelId;
	private String nombreNivel;
		
	public CatNivel(){
		nivelId		= "";
		nombreNivel	= "";	
	}
	
	public String getNivelId(){
		return nivelId;
	}
	
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}	
	
	public String getNombreNivel(){
		return nombreNivel;
	}
	
	public void setNombreNivel( String nombreNivel){
		this.nombreNivel = nombreNivel;
	}	
				
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_NIVEL(NIVEL_ID, NOMBRE_NIVEL) "+
				"VALUES( TO_NUMBER(?,'99'), ? ) ");
			ps.setString(1, nivelId);
			ps.setString(2, nombreNivel);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_NIVEL "+
				"SET NOMBRE_NIVEL = ? "+
				"WHERE NIVEL_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nombreNivel);			
			ps.setString(2, nivelId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_NIVEL "+
				"WHERE NIVEL_ID = TO_NUMBER(?,'99')");
			ps.setString(1, nivelId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		nivelId 		= rs.getString("NIVEL_ID");
		nombreNivel 	= rs.getString("NOMBRE_NIVEL");		
	}
	
	public void mapeaRegId( Connection conn, String nivelId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT NIVEL_ID, NOMBRE_NIVEL "+
			"FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')");
		ps.setString(1,nivelId);
		
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99') ");
			ps.setString(1,nivelId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(NIVEL_ID)+1 MAXIMO FROM ENOC.CAT_NIVEL");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNivelNombre(Connection conn, String nivelId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_NIVEL FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = ?");
			ps.setString(1, nivelId);			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_NIVEL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNivel|getNivelNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}