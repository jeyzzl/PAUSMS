// Bean del Catalogo de Paises
package  aca.sep;

import java.sql.*;

public class SepLugar{
	private String LugarId;	
	private String lugarNombre;
	private String orden;	
	
	public SepLugar(){
		LugarId 			= "";
		lugarNombre 		= "";
		orden	= "";	
	}
	
	public String getLugarId(){
		return LugarId;
	}
	
	public void setLugarId( String LugarId){
		this.LugarId = LugarId;
	}
	
	public String getLugarNombre(){
		return lugarNombre;
	}
	
	public void setLugarNombre( String lugarNombre){
		this.lugarNombre = lugarNombre;
	}
	
	public String getOrden(){
		return orden;
	}
	
	public void setOrden( String orden){
		this.orden = orden;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SEP_LUGAR(LUGAR_ID, LUGAR_NOMBRE, ORDEN)"
					+ " VALUES( TO_NUMBER (?,'99'), ?, TO_NUMBER (?,'99.99'))");
			ps.setString(1, LugarId);
			ps.setString(2, lugarNombre);
			ps.setString(3, orden);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SEP_LUGAR"
					+ " SET LUGAR_NOMBRE = ?, ORDEN = TO_NUMBER(?,'99.99')"
					+ " WHERE LUGAR_ID = TO_NUMBER(?,'99')");
			ps.setString(1, lugarNombre);
			ps.setString(2, orden);
			ps.setString(3, LugarId);
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}

	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SEP_LUGAR "+ 
				"WHERE LUGAR_ID = TO_NUMBER(?,'999')");
			ps.setString(1, LugarId);
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		LugarId 			= rs.getString("LUGAR_ID");
		lugarNombre 		= rs.getString("LUGAR_NOMBRE");
		orden 				= rs.getString("ORDEN");		
	}

	public void mapeaRegId( Connection conn, String LugarId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT LUGAR_ID, LUGAR_NOMBRE, ORDEN "+
				"FROM ENOC.SEP_LUGAR WHERE LUGAR_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,LugarId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SEP_LUGAR WHERE LUGAR_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,LugarId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(LUGAR_ID)+1 MAXIMO FROM ENOC.SEP_LUGAR");			 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static String getLugarNombre(Connection conn, String LugarId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacío";
		
		try{
			ps = conn.prepareStatement("SELECT LUGAR_NOMBRE FROM ENOC.SEP_LUGAR WHERE LUGAR_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, LugarId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("LUGAR_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.SepLugar|getLugarNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
	
}