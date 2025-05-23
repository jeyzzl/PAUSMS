package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumSaldosDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public float getSaldo(String codigo) {
		float saldo	= 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_SALDOS WHERE MATRICULA = ?";
			Object[] parametros = new Object[] {codigo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT SALDO FROM ENOC.ALUM_SALDOS WHERE MATRICULA = ?";
				saldo = enocJdbc.queryForObject(comando, Float.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumSaldosDao|getSaldo|:"+ex);
		}
		
		return saldo;
	}
	
	public List<AlumSaldos> listaDeudasMayores( String orden){
		   List<AlumSaldos> lista = new ArrayList<AlumSaldos>();
		
		try{
			String comando = "SELECT * FROM ALUM_SALDOS WHERE SALDO < -59999 " + orden;
			lista = enocJdbc.query(comando, new AlumSaldosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumSaldosDao|listaDeudasMayores|:"+ex);
		}
		
		return lista;
	}	
}
