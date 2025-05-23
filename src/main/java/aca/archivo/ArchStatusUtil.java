//Clase  para la tabla ARCH_STATUS
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchStatusUtil {	 
     
     public boolean insertReg(Connection conn, ArchStatus status ) throws SQLException {
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_STATUS(IDSTATUS, " + 
 					"DESCRIPCION) VALUES(TO_NUMBER(?,'99'),?)");
 			ps.setString(1, status.getIdStatus());
 			ps.setString(2, status.getDescripcion());
 									
 			if ( ps.executeUpdate()== 1){
 				ok = true; 				
 			}else{
 				ok = false;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|insertReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public boolean updateReg(Connection conn, ArchStatus status ) throws SQLException{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.ARCH_STATUS " + 
 			"SET DESCRIPCION = ? WHERE IDSTATUS = TO_NUMBER(?,'99')");
 			
 			ps.setString(1, status.getDescripcion());
 			ps.setString(2, status.getIdStatus());
 									
 			if ( ps.executeUpdate()== 1){
 				ok = true; 				
 			}else{
 				ok = false;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|updateReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public boolean deleteReg(Connection conn, String idStatus ) throws SQLException{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')"); 
 			ps.setString(1, idStatus);	
 			if ( ps.executeUpdate()== 1){
 				ok = true;
 				
 			}else{
 				ok = false;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|deleteReg|:"+ex);
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public ArchStatus mapeaRegId(Connection con, String IdStatus) throws SQLException{
 		ArchStatus status = new ArchStatus();
 		PreparedStatement ps = null;
 		ResultSet rs = null;		
 		try{
 			ps = con.prepareStatement("SELECT IDSTATUS,DESCRIPCION " +
 					"FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')"); 
 			ps.setString(1, IdStatus);
 			rs = ps.executeQuery();
 			
 			if(rs.next()){
 				status.mapeaReg(rs);
 			}
 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		try { rs.close(); } catch (Exception ignore) { }
 		try { ps.close(); } catch (Exception ignore) { }
 		
 		return status;
 	}
 	
 	public boolean existeReg(Connection conn, String idStatus) throws SQLException{
 		boolean ok 			= false;
 		ResultSet rs 			= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')"); 
 			ps.setString(1, idStatus);
 			rs= ps.executeQuery();		
 			if(rs.next()){
 				ok = true;
 			}else{
 				ok = false;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|existeReg|:"+ex);
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
 			ps = conn.prepareStatement("SELECT MAX(IDSTATUS)+1 MAXIMO FROM ENOC.ARCH_STATUS"); 
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|maximoReg|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
 	
 	public static String getDescripcion( Connection conn, String IdStatus) throws SQLException, Exception {
 		
 		PreparedStatement ps	= null;
 		ResultSet rs 			= null;
 		String descripcion		= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')"); 
 			ps.setString(1,IdStatus);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				descripcion = rs.getString("DESCRIPCION");
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatus|getDescripcion|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return descripcion;
 	} 

		
	public ArrayList<ArchStatus> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchStatus> lisArchStatus 	= new ArrayList<ArchStatus>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDSTATUS, DESCRIPCION " +					
					"FROM ENOC.ARCH_STATUS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchStatus documento = new ArchStatus();
				documento.mapeaReg(rs);
				lisArchStatus.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchStatusUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchStatus;
	}
	
	public ArrayList<String> getListStatusUsados(Connection conn, String orden) throws SQLException{
		ArrayList<String> lisArchStatus 	= new ArrayList<String>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(ENOC.ARCH_STATUS.IDSTATUS) FROM ENOC.ARCH_STATUS INNER JOIN ENOC.ARCH_DOCALUM ON ENOC.ARCH_STATUS.IDSTATUS=ENOC.ARCH_DOCALUM.IDSTATUS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisArchStatus.add(rs.getString("IDSTATUS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchStatusUtil|getListStatusUsados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchStatus;
	}
	
	public ArrayList<ArchStatus> getListNow(Connection conn, String IdDocumento, String orden ) throws SQLException{
		
		ArrayList<ArchStatus> lisArchStatus 	= new ArrayList<ArchStatus>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDSTATUS, DESCRIPCION FROM ENOC.ARCH_STATUS " + 
					"WHERE IDSTATUS IN(SELECT IDSTATUS FROM ENOC.ARCH_DOCSTATUS " + 
					"WHERE IDDOCUMENTO = '"+IdDocumento+"') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchStatus documento = new ArchStatus();
				documento.mapeaReg(rs);
				lisArchStatus.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchStatusUtil|getListNow|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchStatus;
	} 
	
	public ArrayList<ArchStatus> getListRest(Connection conn, String IdDocumento, String orden ) throws SQLException{
		
		ArrayList<ArchStatus> lisArchStatus	= new ArrayList<ArchStatus>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDSTATUS, DESCRIPCION FROM ENOC.ARCH_STATUS " + 
					"WHERE IDSTATUS NOT IN(SELECT IDSTATUS FROM ENOC.ARCH_DOCSTATUS " + 
					"WHERE IDDOCUMENTO = '"+IdDocumento+"') " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchStatus documento = new ArchStatus();
				documento.mapeaReg(rs);
				lisArchStatus.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchStatusUtil|getListRest|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchStatus;
	}	
	
}