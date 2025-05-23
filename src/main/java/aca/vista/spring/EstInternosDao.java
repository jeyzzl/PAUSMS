// Clase para la vista ESTINTERNOS

package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstInternosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<EstInternos> getListAll (String orden){
		
		List<EstInternos> lista = new ArrayList<EstInternos>();
		
		try {
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, DORMITORIO FROM ENOC.ESTINTERNOS "+ orden;
			lista = enocJdbc.query(comando, new EstInternosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInternosDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<EstInternos> getListAllUM (String orden){
		
		List<EstInternos> lista = new ArrayList<EstInternos>();		
		try {
			String comando = "SELECT E.FACULTAD_ID AS FACULTAD_ID,"
				+ " E.CARRERA_ID AS CARRERA_ID,"
				+ " E.CODIGO_PERSONAL AS CODIGO_PERSONAL,"
				+ " E.DORMITORIO AS DORMITORIO"
				+ " FROM ENOC.ESTINTERNOS E, ENOC.INSCRITOS I"
				+ " WHERE E.CODIGO_PERSONAL = I.CODIGO_PERSONAL"
				+ " AND I.MODALIDAD_ID IN (1,2,3,4) "+ orden;
			lista = enocJdbc.query(comando, new EstInternosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstInternosDao|getListAllUM|:"+ex);
		}
		return lista;
	}
}