package aca.covid.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CovidPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public Covid mapeaRegId(String codigoPersonal, String periodoId){
		Covid objeto = new Covid();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA_FINAL"
					+ " FROM ENOC.COVID_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			objeto = enocJdbc.queryForObject(comando, new CovidMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidPeriodoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;		
	}
	
	public int getActual() {
		int actual = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COVID_PERIODO WHERE SYSDATE BETWEEN FECHA_INICIO AND FECHA_FINAL";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){	
				comando = "SELECT PERIODO_ID FROM ENOC.COVID_PERIODO WHERE SYSDATE BETWEEN FECHA_INICIO AND FECHA_FINAL";
				actual = enocJdbc.queryForObject(comando,Integer.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidPeriodoDao|getActual|:"+ex);
		}		
		return actual;
	}
	
	public boolean existe(String periodoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COVID_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {periodoId};				
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidPeriodoDao|existe|:"+ex);
		}		
		return ok;
	}
	
	public ArrayList<Covid> getLista(String orden ) {
		List<Covid> lista = new ArrayList<Covid>();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA_FINAL"
					+ " FROM ENOC.COVID_PERIODO "+orden; 
			
			lista = enocJdbc.query(comando, new CovidMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.CovidPeriodoDao|getLista|:"+ex);
		}
		
		return (ArrayList<Covid>)lista;
	}

}
