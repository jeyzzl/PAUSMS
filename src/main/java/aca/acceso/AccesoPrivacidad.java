package aca.acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoPrivacidad {	
	
	private String codigoPersonal;
	private String fecha;
	
	
	public AccesoPrivacidad(){
		codigoPersonal	= "";
		fecha			= "";
	}	
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ACCESO_PRIVACIDAD(CODIGO_PERSONAL, FECHA)" +
					" VALUES(?,now())");
			ps.setString(1,codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoPrivacidad|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY'),'01/01/1900') FECHA"+
					" FROM ENOC.ACCESO_PRIVACIDAD WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AccesoPrivacidad|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ACCESO_PRIVACIDAD WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoPrivacidad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}