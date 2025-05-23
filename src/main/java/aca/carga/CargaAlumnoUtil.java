package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CargaAlumnoUtil {
	

	public String getPlanId(Connection conn, String codigoPersonal, String cargaId, String bloqueId) throws Exception{
		String plan = "-";
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int registro = 0;
		
		try{
			ps1 = conn.prepareStatement("SELECT COUNT(*) AS REGISTRO FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')");
			
			ps1.setString(1, codigoPersonal);
			ps1.setString(2, cargaId);
			ps1.setString(3, bloqueId);
			
			rs1 = ps1.executeQuery(); 
			if (rs1.next()){
				registro = rs1.getInt("REGISTRO");			
			}
			
			if(registro >= 1) {
				ps2 = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')");
				
				ps2.setString(1, codigoPersonal);
				ps2.setString(2, cargaId);
				ps2.setString(3, bloqueId);
				
				rs2 = ps2.executeQuery();
				if (rs2.next()){
					plan = rs2.getString("PLAN_ID");			
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaAlumnoUtil|getPlanId|:"+ex);
		}finally {
			if (rs1!=null) rs1.close();
			if (rs2!=null) rs2.close();
			if (ps1 != null) ps1.close();
			if (ps2 != null) ps2.close();
		}
		
		return plan;
	}
	
	public String getPlanIdDos(Connection conn, String codigoPersonal, String cargaId) throws Exception{
		String plan = "-";
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int registro = 0;
		
		try{
			ps1 = conn.prepareStatement("SELECT COUNT(*) AS REGISTRO FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ");
			
			ps1.setString(1, codigoPersonal);
			ps1.setString(2, cargaId);
			
			rs1 = ps1.executeQuery(); 
			if (rs1.next()){
				registro = rs1.getInt("REGISTRO");			
			}
			
			if(registro >= 1) {
				ps2 = conn.prepareStatement("SELECT PLAN_ID FROM ENOC.CARGA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ");
				
				ps2.setString(1, codigoPersonal);
				ps2.setString(2, cargaId);
				
				rs2 = ps2.executeQuery();
				if (rs2.next()){
					plan = rs2.getString("PLAN_ID");			
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaAlumnoUtil|getPlanIdDos|:"+ex);
		}finally {
			if (rs1!=null) rs1.close();
			if (rs2!=null) rs2.close();
			if (ps1 != null) ps1.close();
			if (ps2 != null) ps2.close();
		}
		
		return plan;
	}
	
}