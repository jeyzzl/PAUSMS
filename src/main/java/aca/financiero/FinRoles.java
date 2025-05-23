package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinRoles {
	
	private String rol;
	
	public FinRoles(){
		rol	= "";
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public String traeRol(Connection conn, String codigoEmpleado) throws SQLException{
		String regresa 			= "";
		String compara 			= "";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps = conn.prepareStatement("SELECT RECTOR FROM ENOC.FIN_ROLES");
			rs = ps.executeQuery();
			if (rs.next()){
				compara = rs.getString("RECTOR");
				if(compara.contains(codigoEmpleado)){
					regresa	= "R";
				}else{
					rs.close();
					ps.close();
					ps = conn.prepareStatement("SELECT VICERRECTOR FROM ENOC.FIN_ROLES");
					rs = ps.executeQuery();
					if (rs.next()){
						compara = rs.getString("VICERRECTOR");
						if(compara.contains(codigoEmpleado)){
							regresa	= "V";	
						}else{
							rs.close();
							ps.close();
							ps = conn.prepareStatement("SELECT DIRECTOR FROM ENOC.FIN_ROLES");
							rs = ps.executeQuery();
							if (rs.next()){
								compara = rs.getString("DIRECTOR");
								if(compara.contains(codigoEmpleado)){
									regresa	= "D";	
								}else{
									rs.close();
									ps.close();
									ps = conn.prepareStatement("SELECT COORDINADOR FROM ENOC.FIN_ROLES");
									rs = ps.executeQuery();
									if (rs.next()){
										compara = rs.getString("COORDINADOR");
										if(compara.contains(codigoEmpleado)){
											regresa	= "C";
										}else{
											rs.close();
											ps.close();
											ps = conn.prepareStatement("SELECT ASESOR FROM ENOC.FIN_ROLES");
											rs = ps.executeQuery();
											if (rs.next()){
												compara = rs.getString("ASESOR");
												if(compara.contains(codigoEmpleado)){
													regresa	= "A";
												}
											}	
										}
									}						
								}			
							}					
						}			
					}			
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FinRoles|traeRol|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return regresa;
	}

}
