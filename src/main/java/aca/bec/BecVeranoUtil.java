package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecVeranoUtil {
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_VERANO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,  codigoPersonal);			
			
			rs = ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecVeranoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}		
	
	public BecVerano mapeaRegId(Connection conn, String codigoPersonal ) throws SQLException{
		BecVerano becVerano = new BecVerano();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, DEPARTAMENTO, PUESTO, TELEFONO, CORREO, IMPORTE"
				+ " FROM ENOC.BEC_VERANO WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1,  codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becVerano.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecVeranoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becVerano;
	}	
	
}