//Clase  para la tabla ARCH_DOCALUM
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchGruposCarreraUtil {
	private String carrera;
	private String grupos;
	
	public ArrayList<ArchGruposCarrera> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchGruposCarrera> lisArchGruposCarrera 	= new ArrayList<ArchGruposCarrera>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT GRUPOS, CARRERA, " +
					"FROM ENOC.ARCH_GRUPOS_CARRERA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchGruposCarrera documento = new ArchGruposCarrera();
				documento.mapeaReg(rs);
				lisArchGruposCarrera.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisArchGruposCarrera;
	}
	
	public boolean insertReg(Connection conn ) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_GRUPOS_CARRERA(GRUPOS,CARRERA) VALUES(?,?)");
			ps.setString(1,grupos);
			ps.setString(2,carrera);									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean insertReg(Connection conn, String grupos, String carrera ) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_GRUPOS_CARRERA(GRUPOS,CARRERA) VALUES(?,?)");
			ps.setString(1,grupos);
			ps.setString(2,carrera);									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_GRUPOS_CARRERA" + 
					" SET GRUPOS = ?" +
					" WHERE CARRERA = ?");			
			ps.setString(1,grupos);
			ps.setString(2,carrera);
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, String grupos, String carrera ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_GRUPOS_CARRERA" + 
					" SET GRUPOS = ?" +
					" WHERE CARRERA = ?");			
			ps.setString(1,grupos);
			ps.setString(2,carrera);
									
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ? AND GRUPOS = ?"); 
			ps.setString(1,carrera);
			ps.setString(2,grupos);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|deleteReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaRegId(Connection con, String carrera) throws SQLException{
		ArchGruposCarrera archGrup = new ArchGruposCarrera();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT CARRERA, GRUPOS" +
					" FROM ENOC.ARCH_GRUPOS_CARRERA" + 
					" WHERE CARRERA = ?");
			
			ps.setString(1, carrera);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				archGrup.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?"); 
			ps.setString(1,carrera);			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String carrera) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?"); 
			ps.setString(1,carrera);			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
