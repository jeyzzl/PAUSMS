//Clase para la vista MAESTRO_EVALUACION

package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MaestroEvaluacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public MaestroEvaluacion mapeaRegId( String cursoCargaId, String evaluacionId){
		
		MaestroEvaluacion maestro = new MaestroEvaluacion();
		 		 		
 		try{
 			String comando = " SELECT CURSO_CARGA_ID, MAESTRO, EVALUACION_ID, NOMBRE_EVALUACION," 
					+ " FECHA, ESTRATEGIA_ID," 
					+ " VALOR, TIPO, NUM_ALUMNOS"
					+ "NUM_ACT, NUM_EVAL, ACT_EVAL"
					+ " FROM ENOC.MAESTRO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ?"; 					
 			Object[] parametros = new Object[] {cursoCargaId, evaluacionId}; 			
 			maestro = enocJdbc.queryForObject(comando, new MaestroEvaluacionMapper(), parametros);
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|mapeaRegId|:"+ex);
 		}
 		
 		return maestro;
 	}

	public List<MaestroEvaluacion> getListAll( String orden ){
		
		List<MaestroEvaluacion> lista	    	= new ArrayList<MaestroEvaluacion>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, MAESTRO, EVALUACION_ID, NOMBRE_EVALUACION, " +
					"FECHA, ESTRATEGIA_ID, VALOR, TIPO, NUM_ALUMNOS, NUM_ACT, NUM_EVAL, ACT_EVAL FROM ENOC.MAESTRO_EVALUACION" + orden;	
			lista = enocJdbc.query(comando, new MaestroEvaluacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroEvaluacionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}