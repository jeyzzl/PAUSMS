/**
 * 
 */
package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class CertCicloUtil {
	
	public boolean insertReg(Connection conn, CertCiclo ciclo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_CICLO"+ 
				"(PLAN_ID, CICLO_ID, TITULO, FST, FSP, CREDITOS)"+
				" VALUES(?, TO_NUMBER(?, '99'), ?, ?, ?, ?)");
					
			ps.setString(1, ciclo.getPlanId());
			ps.setString(2, ciclo.getCicloId());
			ps.setString(3, ciclo.getTitulo());
			ps.setString(4, ciclo.getFst());
			ps.setString(5, ciclo.getFsp());
			ps.setString(6, ciclo.getCreditos());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|insertReg|:"+ex);			
		}finally{
			ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CertCiclo ciclo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_CICLO"+ 
				" SET TITULO = ?," +
				" FST = ?," +
				" FSP = ?," +
				" CREDITOS = ?"+				
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')");		
			
			ps.setString(1, ciclo.getTitulo());
			ps.setString(2, ciclo.getFst());
			ps.setString(3, ciclo.getFsp());
			ps.setString(4, ciclo.getCreditos());
			ps.setString(5, ciclo.getPlanId());
			ps.setString(6, ciclo.getCicloId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|updateReg|:"+ex);		
		}finally{
			ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String planId, String cicloId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, planId);
			ps.setString(2, cicloId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|deleteReg|:"+ex);			
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public CertCiclo mapeaRegId(Connection conn, String planId, String cicloId) throws SQLException{
		CertCiclo ciclo = new CertCiclo();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO_ID," +
					" TITULO, FST, FSP, CREDITOS" +
					" FROM ENOC.CERT_CICLO" + 
					" WHERE PLAN_ID = ?" +
					" AND CICLO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, planId);
			ps.setString(2, cicloId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ciclo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|existeReg|:"+ex);
		}finally{
			rs.close();
			ps.close();
		}
		return ciclo;
	}
	
	public boolean existeReg(Connection conn, String planId, String cicloId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT TITULO FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, planId);
			ps.setString(2, cicloId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|existeReg|:"+ex);
		}finally{
			rs.close();
			ps.close();
		}
		
		return ok;
	}
	
	public static String maxReg(Connection conn, String planId) throws SQLException{
		String maximo 			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(CICLO_ID)+1,1) AS MAXIMO FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?");
			
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCiclo|maxReg|:"+ex);
		}finally{
			rs.close();
			ps.close();
		}
		
		return maximo;
	}	
	
	public ArrayList<CertCiclo> getListPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<CertCiclo> lisCiclos	= new ArrayList<CertCiclo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CICLO_ID," +
					" TITULO, FST, FSP, CREDITOS" +
					" FROM ENOC.CERT_CICLO" + 
					" WHERE PLAN_ID = '"+planId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CertCiclo cc = new CertCiclo();
				cc.mapeaReg(rs);
				lisCiclos.add(cc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertCicloUtil|getListPlan|:"+ex);
		}finally{
			rs.close();
			st.close();
		}
		
		return lisCiclos;
	}
}