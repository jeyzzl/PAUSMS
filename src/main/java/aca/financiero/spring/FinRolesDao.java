package aca.financiero.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinRolesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String traeRol(String codigoEmpleado){
		String regresa 			= "";
		String compara 			= "";
		
		try{
			String comando = "SELECT COUNT(RECTOR) FROM ENOC.FIN_ROLES";
			
			boolean ok = false;
			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				ok = true;
			}
			
			if (ok){
				ok = false;
				comando = "SELECT RECTOR FROM ENOC.FIN_ROLES";
				compara = enocJdbc.queryForObject(comando, String.class);
				
				if(compara.contains(codigoEmpleado)){
					regresa	= "R";
				}else{
					comando = "SELECT COUNT(VICERRECTOR) FROM ENOC.FIN_ROLES";
					
					if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
						ok = true;
					}
					
					if (ok){
						ok = false;
						comando = "SELECT VICERRECTOR FROM ENOC.FIN_ROLES";
						compara = enocJdbc.queryForObject(comando, String.class);
						if(compara.contains(codigoEmpleado)){
							regresa	= "V";	
						}else{
							comando = "SELECT COUNT(DIRECTOR) FROM ENOC.FIN_ROLES";

							if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
								ok = true;
							}

							if (ok){
								ok = false;
								comando = "SELECT DIRECTOR FROM ENOC.FIN_ROLES";
								compara = enocJdbc.queryForObject(comando, String.class);
								
								if(compara.contains(codigoEmpleado)){
									regresa	= "D";	
								}else{
									comando = "SELECT COUNT(COORDINADOR) FROM ENOC.FIN_ROLES";
									
									if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
										ok = true;
									}

									if (ok){
										ok = false;
										comando = "SELECT COORDINADOR FROM ENOC.FIN_ROLES";
										if(compara.contains(codigoEmpleado)){
											regresa	= "C";
										}else{
											comando = "SELECT COUNT(ASESOR) FROM ENOC.FIN_ROLES";
											
											if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
												ok = true;
											}
											
											if (ok){
												comando = "SELECT ASESOR FROM ENOC.FIN_ROLES";
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
			System.out.println("Error - aca.financiero.spring.FinRolesDao|traeRol|:"+ex);
		}	
		return regresa;
	}

}
