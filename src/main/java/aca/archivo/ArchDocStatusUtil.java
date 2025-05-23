//Clase  para la tabla ARCH_DOCSTATUS
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchDocStatusUtil {
	
	public boolean insertReg(Connection conn, ArchDocStatus status ) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_DOCSTATUS(IDDOCUMENTO, " + 
					"IDSTATUS) VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'99'))");
			ps.setString(1, status.getIdDocumento());
			ps.setString(2, status.getIdStatus());
						
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, ArchDocStatus status ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_DOCSTATUS( " + 
					"IDDOCUMENTO,IDSTATUS)WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'999')");
			ps.setString(1, status.getIdDocumento());
			ps.setString(2, status.getIdStatus());			
						
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String idDocumento, String idStatus ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'999')"); 
			ps.setString(1,idDocumento);
			ps.setString(2,idStatus);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public ArchDocStatus mapeaRegId(Connection con, String idDocumento, String idStatus) throws SQLException{
		
		ArchDocStatus archDoc = new ArchDocStatus();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDDOCUMENTO, IDSTATUS FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')"); 
			ps.setString(1, idDocumento);
			ps.setString(2, idStatus);
			rs = ps.executeQuery();		
			if(rs.next()){
				archDoc.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return archDoc;
	}
	
	public boolean existeReg(Connection conn, String idDocumento, String idStatus) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_DOCSTATUS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')"); 
			ps.setString(1, idDocumento);
			ps.setString(2, idStatus);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.academico.ArchDocStatusUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IDDOCUMENTO)+1 MAXIMO FROM ENOC.ARCH_DOCSTATUS"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ArchDocStatus> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchDocStatus> lisArchDocStatus 	= new ArrayList<ArchDocStatus>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT IDDOCUMENTO, IDSTATUS FROM ENOC.ARCH_DOCSTATUS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchDocStatus documento = new ArchDocStatus();
				documento.mapeaReg(rs);
				lisArchDocStatus.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchDocStatus;
	}
	
	public ArrayList<ArchDocStatus> getListDocumento(Connection conn, String documentoId, String orden ) throws SQLException{
		
		ArrayList<ArchDocStatus> lisArchDocStatus 	= new ArrayList<ArchDocStatus>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT IDDOCUMENTO, IDSTATUS FROM ENOC.ARCH_DOCSTATUS" + 
					" WHERE IDDOCUMENTO = "+documentoId+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchDocStatus documento = new ArchDocStatus();
				documento.mapeaReg(rs);
				lisArchDocStatus.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocStatusUtil|getListDocumento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchDocStatus;
	}
	
}