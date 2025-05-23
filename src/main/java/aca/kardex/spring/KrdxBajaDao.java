package aca.kardex.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxBajaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<KrdxAlta> getLista( String year, String orden ) {
		
		List<KrdxAlta> lista		= new ArrayList<KrdxAlta>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CREDITOS, CURSO_ID, CARRERA_ID, MODALIDAD_ID, YEAR, TIPO"
					+ " FROM ENOC.KRDX_BAJA"
					+ " WHERE YEAR = "+year+" "+orden; 
			lista = enocJdbc.query(comando, new KrdxAltaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxBajaDao|getLista|:"+ex);
		}	
		return lista;
	}
}