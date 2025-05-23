// Bean del Catalogo de Roles
package  aca.rol;

import java.sql.*;

public class RolOpcion{
	private String rolId;	
	private String opcionId;
	
	public RolOpcion(){
		rolId 		= "";
		opcionId	= "";
	}
	
	public String getRolId() {
		return rolId;
	}

	public void setRolId(String rolId) {
		this.rolId = rolId;
	}

	public String getOpcionId() {
		return opcionId;
	}

	public void setOpcionId(String opcionId) {
		this.opcionId = opcionId;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.ROL_OPCION(ROL_ID, OPCION_ID ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ");
			ps.setString(1, rolId);
			ps.setString(2, opcionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROL_OPCION "+ 
				"SET OPCION_ID = ? "+
				"WHERE ROL_ID = TO_NUMBER(?,'999')");
			ps.setString(1, opcionId);
			ps.setString(2, rolId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROL_OPCION "+ 
				"WHERE ROL_ID = TO_NUMBER(?,'999')");
			ps.setString(1, rolId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		rolId 		= rs.getString("ROL_ID");
		opcionId 	= rs.getString("OPCION_ID");		
	}
	
	public void mapeaRegId( Connection conn, String rolId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT ROL_ID, OPCION_ID "+
				"FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1,rolId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999') AND OPCION_ID = ?"); 
			ps.setString(1,rolId);
			ps.setString(2,opcionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Regresa un String que contiene las opciones de un rol
	public String getOpcRol(Connection conn, String rolId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String opcion		= "";
		
		try{
			comando = "SELECT OPCION_ID FROM ENOC.ROL_OPCION"+ 
					" WHERE ROL_ID = '"+rolId+"' ";			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				opcion = rs.getString("OPCION_ID") +"@@"+ opcion ;				
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getOpcUser|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return opcion;
	}
	
	// Cuenta el numero de opciones que contiene un rol
	public static String getNumOpciones(Connection conn, String rolId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String total		= "0";
		
		try{
			comando = "SELECT COUNT(OPCION_ID) AS TOTAL FROM ENOC.ROL_OPCION WHERE ROL_ID = "+rolId+"";
			rs = st.executeQuery(comando);
			if (rs.next()){				
				total = rs.getString("TOTAL");
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getNumOpciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
}