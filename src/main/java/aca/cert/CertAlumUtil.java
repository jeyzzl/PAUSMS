package aca.cert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CertAlumUtil {
	
	public boolean insertReg(Connection conn, CertAlum cer) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CERT_ALUM"+ 
				"(CODIGO_PERSONAL, PLAN_ID, AVANCE, NUM_CURSOS, FECHA, EQUIVALENCIA, ESTADO, ENCABEZADO, LINEA)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '999'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?)");
					
			ps.setString(1, cer.getCodigoPersonal());
			ps.setString(2, cer.getPlanId());
			ps.setString(3, cer.getAvance());
			ps.setString(4, cer.getNumCursos());
			ps.setString(5, cer.getFecha());
			ps.setString(6, cer.getEquivalencia());			
			ps.setString(7, cer.getEstado());
			ps.setString(8, cer.getEncabezado());
			ps.setString(9, cer.getLinea());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CertAlum cer) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CERT_ALUM"+ 
				" SET AVANCE = ?," +
				" NUM_CURSOS = TO_NUMBER(?, '999')," +
				" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" EQUIVALENCIA = ?," +				
				" ESTADO = ?," +
				" ENCABEZADO = ?, LINEA = ?" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");		
			
			ps.setString(1, cer.getAvance());
			ps.setString(2, cer.getNumCursos());
			ps.setString(3, cer.getFecha());
			ps.setString(4, cer.getEquivalencia());			
			ps.setString(5, cer.getEstado());
			ps.setString(6, cer.getEncabezado());
			ps.setString(7, cer.getLinea());
			ps.setString(8, cer.getCodigoPersonal());
			ps.setString(9, cer.getPlanId());			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String planId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CERT_ALUM"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public CertAlum mapeaRegId(Connection conn, String codigoPersonal, String planId) throws SQLException{
		CertAlum cer = new CertAlum();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, PLAN_ID, AVANCE," +
					" NUM_CURSOS, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, EQUIVALENCIA, ESTADO, ENCABEZADO, LINEA " +
					" FROM ENOC.CERT_ALUM" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				cer.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return cer;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String planId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CERT_ALUM"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertAlum|existeReg|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return ok;
	}

}
