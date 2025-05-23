
package aca.kardex.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActividadDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<KrdxAlumnoActiv> getListAll( String orden ) {
		List<KrdxAlumnoActiv> lista		= new ArrayList<KrdxAlumnoActiv>();
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA "+
				"FROM ENOC.KRDX_ALUMNO_ACTIV "+ orden; 
			
			lista = enocJdbc.query(comando, new KrdxAlumnoActivMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxAlumnoActiv> getLista( String codigoPersonal, String cursoCargaId, String orden ) {
		List<KrdxAlumnoActiv> lista	= new ArrayList<KrdxAlumnoActiv>();
		String comando			= "";		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA "+
					"FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CURSO_CARGA_ID = ? "+ orden;			
			lista = enocJdbc.query(comando, new KrdxAlumnoActivMapper(), codigoPersonal, cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getLista|:"+ex);
		}
		return lista;
	}	
	
	
	public String getAlumnoEvaluacion( String codigoPersonal, String actividadId ) {
		String comando	= "";
		String evaluacion = "-";
		
		try{
			comando = "SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {codigoPersonal, actividadId};
			evaluacion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActividadUtil|getAlumnoEvaluacion|:"+ex);
		}
		return evaluacion;
	}	

}