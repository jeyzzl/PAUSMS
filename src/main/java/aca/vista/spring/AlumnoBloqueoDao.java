package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class AlumnoBloqueoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<AlumnoBloqueo> getListAll(String orden ){
		
		   List<AlumnoBloqueo> lista		= new ArrayList<AlumnoBloqueo>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID, FACULTAD_ID, CARRERA_ID FROM ENOC.ALUMNO_BLOQUEO "+ orden;
			lista = enocJdbc.query(comando, new AlumnoBloqueoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoBloqueoDao|getListAll|:"+ex);
		}
		
		return lista;
	}	

}