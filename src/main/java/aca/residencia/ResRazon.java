// Beans para la tabla Res_Razones

package aca.residencia;

import java.sql.*;

public class ResRazon {
	private String razon;
	private String descripcion;
	
	public ResRazon(){
		razon		= "";
		descripcion = "";
	}

	public String getDescripcion() {
		return descripcion;
	}
	

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getRazon() {
		return razon;
	}
	

	public void setRazon(String razon) {
		this.razon = razon;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.RES_RAZONES"+ 
				"(RAZON, DESCRIPCION) VALUES( TO_NUMBER(?,'999'), ?)");
			ps.setString(1, razon);
			ps.setString(2, descripcion);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.RES_RAZONES "+ 
				"SET DESCRIPCION = ? WHERE RAZON= TO_NUMBER(?, '999') ");
			ps.setString(1, descripcion);
			ps.setString(2, razon);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error-aca.residencia.ResRazon|updateReg|: "+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = ? ");
			ps.setString(1, razon);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|deleteReg|: "+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		razon 			= rs.getString("RAZON");
		descripcion 	= rs.getString("DESCRIPCION");
		
	}
	
	public void mapeaRegId( Connection conn, String razon ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT RAZON, DESCRIPCION "+
				"FROM ENOC.RES_RAZONES WHERE RAZON = ? "); 
			ps.setString(1, razon);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = ?");
			ps.setString(1, razon);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error: aca.residencia.ResRazon|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String nombreRazon(Connection conn, String razon) throws SQLException{	
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String nombre			= "X"; 
		
		try{
			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = ?");
			ps.setString(1, razon);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("DESCRIPCION");
			
		}catch(Exception ex){
			System.out.println("Error: aca.residencia.ResRazon|nombreRazon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(RAZON)+1 MAXIMO FROM ENOC.RES_RAZONES"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
}