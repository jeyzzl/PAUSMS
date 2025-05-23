/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author elifo
 *
 */
public class CertRelacionUtil {
	
	public boolean insertReg(Connection conn, CertRelacion rel) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_RELACION"+ 
				"(CURSO_ID, CURSO_CERT)"+
				" VALUES(?, ?)");
					
			ps.setString(1, rel.getCursoId());
			ps.setString(2, rel.getCursoCert());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertRelacion|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCert ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_RELACION"+ 
				" WHERE CURSO_CERT = ?");
			
			ps.setString(1, cursoCert);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertRelacion|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public CertRelacion mapeaRegId(Connection conn, String cursoCert) throws SQLException{
		
		CertRelacion rel = new CertRelacion();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_CERT" +
				" FROM ENOC.CERT_RELACION" + 
				" WHERE CURSO_CERT = ?");
		
			ps.setString(1, cursoCert);
		
			rs = ps.executeQuery();
			if (rs.next()){
				rel.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertRelacion|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return rel;
	}
	
	public boolean existeReg(Connection conn, String cursoCert) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.CERT_RELACION"+ 
				" WHERE CURSO_CERT = ?");
			
			ps.setString(1, cursoCert);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertRelacion|existeReg|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
}