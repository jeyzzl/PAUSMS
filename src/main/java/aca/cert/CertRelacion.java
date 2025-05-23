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
public class CertRelacion {
	private String cursoId;
	private String cursoCert;
	
	public CertRelacion(){
		cursoId		= "";
		cursoCert	= "";
	}

	/**
	 * @return the cursoCert
	 */
	public String getCursoCert() {
		return cursoCert;
	}

	/**
	 * @param cursoCert the cursoCert to set
	 */
	public void setCursoCert(String cursoCert) {
		this.cursoCert = cursoCert;
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		cursoId			= rs.getString("CURSO_ID");
		cursoCert		= rs.getString("CURSO_CERT");
	}
	
	public void mapeaRegId(Connection conn, String cursoCert) throws SQLException{		
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID, CURSO_CERT" +
				" FROM ENOC.CERT_RELACION" + 
				" WHERE CURSO_CERT = ?");
		
			ps.setString(1, cursoCert);
		
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertRelacion|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
}