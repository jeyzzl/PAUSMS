package aca.well;

import java.sql.*;

public class WellEjercicio {
	private String domingo;
	private String lunes;
	private String martes;
	private String miercoles;
	private String jueves;
	private String viernes;
	private String codigoPersonal;

	public WellEjercicio(){
		domingo 		= "";
		lunes			= "";
		martes  		= "";
		miercoles 		= "";
		jueves 			= "";
		viernes 		= "";
		codigoPersonal  = "";
	}
	
	public String getDomingo() {
		return domingo;
	}

	public void setDomingo(String domingo) {
		this.domingo = domingo;
	}

	public String getLunes() {
		return lunes;
	}

	public void setLunes(String lunes) {
		this.lunes = lunes;
	}

	public String getMartes() {
		return martes;
	}

	public void setMartes(String martes) {
		this.martes = martes;
	}

	public String getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(String miercoles) {
		this.miercoles = miercoles;
	}

	public String getJueves() {
		return jueves;
	}

	public void setJueves(String jueves) {
		this.jueves = jueves;
	}

	public String getViernes() {
		return viernes;
	}

	public void setViernes(String viernes) {
		this.viernes = viernes;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELLNESS_EJERCICIO"+ 
 				"(DOMINGO, LUNES, MARTES, MIERCOLES, JEUVES, VIERNES, CODIGO_PERSONAL) "+
 				"VALUES( ?,?,?,?,?,?,?,?)");
 			ps.setString(1, domingo);
 			ps.setString(2, lunes);
 			ps.setString(3, martes);
 			ps.setString(4, miercoles);
 			ps.setString(5, jueves);
 			ps.setString(6, viernes);
 			ps.setString(8, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.WellnessEjercicio|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.WELLNESS_EJERCICIO"+			 
 				" SET DOMINGO = ?,"
 				+ "LUNES = ?,"
 				+ "MARTES = ?,"
 				+ "MIERCOLES = ?,"
 				+ "JUEVES = ?, "
 				+ "VIERNES = ?,"
 				+ "CODIGO_PERSONAL = ? "
 				+ "WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.wellness.WellnessEjercicio|updateReg|:"+ex);		
 		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.WELLNESS_EJERCICIO "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.wellness.WellnessEjercicio|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		domingo 		= rs.getString("DOMINGO");
		lunes			= rs.getString("LUNES");
		martes  		= rs.getString("MARTES");
		miercoles 		= rs.getString("MIERCOLES");
		jueves 			= rs.getString("JUEVES");
		viernes 		= rs.getString("VIERNES");
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
	}
	
	public void mapeaRegId( Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT DOMINGO, LUNES, MARTES, MIERCOLES, JEUVES, VIERNES, CODIGO_PERSONAL FROM ENOC.WELLNESS_EJERCICIO WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaAvance|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	
}
	