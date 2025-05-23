package aca.sep.spring;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class SepEstadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public List<SepEstado> lisEstados(){
		List<SepEstado> lista = new ArrayList<SepEstado>();
		
		try {
			String comando = "SELECT ESTADO_SE, NOMBRE FROM SEP_ESTADO";
			
			lista = enocJdbc.query(comando, new SepEstadoMapper());
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listPlanes|:"+ex);
		}
		
		return lista;
	}

}
