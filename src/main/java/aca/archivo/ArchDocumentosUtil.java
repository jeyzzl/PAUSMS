//Clase  para la tabla ARCH_DOCUMENTOS
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchDocumentosUtil {
	
	public boolean insertReg(Connection conn, ArchDocumentos doc ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ARCH_DOCUMENTOS(IDDOCUMENTO, " + 
					"DESCRIPCION, IMAGEN, ORDEN) VALUES(TO_NUMBER(?,'999'),?,?, TO_NUMBER(?,'999.9'))");
			
			ps.setString(1, doc.getIdDocumento());
			ps.setString(2, doc.getDescripcion());
			ps.setString(3, doc.getImagen());
			ps.setString(4, doc.getOrden());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, ArchDocumentos doc ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_DOCUMENTOS " + 
					"SET DESCRIPCION = ?, IMAGEN = ?, ORDEN = TO_NUMBER(?, '999.9') WHERE IDDOCUMENTO = TO_NUMBER(?,'999')");
			
			ps.setString(1, doc.getDescripcion());
			ps.setString(2, doc.getImagen());
			ps.setString(3, doc.getOrden());
			ps.setString(4, doc.getIdDocumento());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String idDocumento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"); 
			ps.setString(1, idDocumento);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchDocumentos mapeaRegId(Connection con, String IdDocumento) throws SQLException{
		
		ArchDocumentos archDoc = new ArchDocumentos();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDDOCUMENTO, " +
					"DESCRIPCION, IMAGEN, ORDEN " +				
					"FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"); 
			ps.setString(1,IdDocumento);
			rs = ps.executeQuery();
			
			if(rs.next()){
				archDoc.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return archDoc;
	}
	
	public boolean existeReg(Connection conn, String idDocumento) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') "); 
			ps.setString(1, idDocumento);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(IDDOCUMENTO)+1 MAXIMO FROM ENOC.ARCH_DOCUMENTOS"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getDescripcion( Connection conn, String IdDocumento) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String descripcion		= "";
		
		try{
			ps = conn.prepareStatement("SELECT DESCRIPCION FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"); 
			ps.setString(1, IdDocumento);
			rs = ps.executeQuery();
			if (rs.next()){
				descripcion = rs.getString("DESCRIPCION");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|getDescripcion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return descripcion;
	} 
	
	public ArrayList<ArchDocumentos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchDocumentos> lisArchAlum 	= new ArrayList<ArchDocumentos>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDDOCUMENTO, DESCRIPCION, IMAGEN, ORDEN " +					
					"FROM ENOC.ARCH_DOCUMENTOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchDocumentos documento = new ArchDocumentos();
				documento.mapeaReg(rs);
				lisArchAlum.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}
	
	public ArrayList<ArchDocumentos> getListOne(Connection conn, String Matricula, String orden ) throws SQLException{
		
		ArrayList<ArchDocumentos> lisArchAlum 	= new ArrayList<ArchDocumentos>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDDOCUMENTO, DESCRIPCION, IMAGEN, ORDEN FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO NOT IN (SELECT IDDOCUMENTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = '"+Matricula+"') "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchDocumentos documento = new ArchDocumentos();
				documento.mapeaReg(rs);
				lisArchAlum.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentosUtil|getListOne|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchAlum;
	}	
}