// Bean del Catalogo de Religiones
package  adm.catalogo;

import java.sql.*;

public class CatReligion{
	private String religionId;	
	private String nombreReligion;
	
	public CatReligion(){
		religionId 		= "";
		nombreReligion	= "";
	}
	
	public String getReligionId(){
		return religionId;
	}
	
	public void setReligionId( String religionId){
		this.religionId = religionId;
	}
	
	public String getNombreReligion(){
		return nombreReligion;
	}
	
	public void setNombreReligion( String nombreReligion){
		this.nombreReligion = nombreReligion;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_RELIGION(RELIGION_ID, NOMBRE_RELIGION ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, religionId);
			ps.setString(2, nombreReligion);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_RELIGION "+
				"SET NOMBRE_RELIGION = ? "+
				"WHERE RELIGION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, nombreReligion);
			ps.setString(2, religionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_RELIGION "+
				"WHERE RELIGION_ID = TO_NUMBER(?,'999')");
			ps.setString(1, religionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		religionId 		= rs.getString("RELIGION_ID");
		nombreReligion 	= rs.getString("NOMBRE_RELIGION");		
	}
	
	public void mapeaRegId( Connection conn, String religionId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT RELIGION_ID, NOMBRE_RELIGION "+
			"FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999')");
		ps.setString(1,religionId);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = TO_NUMBER(?,'999') ");
			ps.setString(1,religionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(RELIGION_ID)+1 MAXIMO FROM ENOC.CAT_RELIGION");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreReligion(Connection conn, String religionId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = ?");
			ps.setString(1, religionId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_RELIGION");
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatReligion|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}