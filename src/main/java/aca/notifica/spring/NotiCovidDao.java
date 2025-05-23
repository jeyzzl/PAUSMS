package aca.notifica.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotiCovidDao {
	
	@Autowired		
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	/*
	public NotiCovid mapeaRegId(String matricula){
		
		NotiCovid objeto 	= new NotiCovid();	
		boolean ok 			= false;
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_COVID WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando,parametros,Integer.class) >= 1){	
							
				objeto.setMatricula(matricula);
				objeto.setFecha(fecha);
				dato.setDato("NO");
			}
			
		}catch( Exception ex){
			System.out.println("Error: aca.bean.MovPlanDao||mapeaRegId"+ex);
		}
		
		return objeto;
	}	
*/	
}

