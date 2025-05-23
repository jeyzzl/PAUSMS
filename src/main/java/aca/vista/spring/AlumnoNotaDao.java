// Clase Util para la tabla de Carga
package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component
public class AlumnoNotaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;		
	
	@Autowired
	aca.kardex.spring.KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	public boolean existeReg( String codigoPersonal, String cursoCargaId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_NOTA "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? ";
			
				Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
				if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
					ok = true;
				}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoNota|existeReg|:"+ex);
		}
		return ok;
	}
	
	public AlumnoNota mapeaRegId(  String codigoPersonal, String cursoCargaId ) {
		AlumnoNota alumnoNota = new AlumnoNota();
		try{
			String comando = "SELECT CODIGO_PERSONAL,  CURSO_CARGA_ID,  EVALUADOS, PUNTOS, PORCENTAJE, EXTRAS,"
					+ " TO_CHAR(((PORCENTAJE+PUNTOS+EXTRAS)*100/CASE EVALUADOS WHEN 0 THEN 1 ELSE EVALUADOS END),'990.99') AS NOTA"
					+ " FROM ENOC.ALUMNO_NOTA"
					+ " WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ? ";
			
				Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
				alumnoNota = enocJdbc.queryForObject(comando, new AlumnoNotaMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoNota|mapeaRegId|:"+ex);
		}
		return alumnoNota;
	}
	
	public List<AlumnoNota> lista( String codigoPersonal) {	
		
		List<AlumnoNota> lista = new ArrayList<AlumnoNota>();		
		try{
			
			String comando = "SELECT CODIGO_PERSONAL,  CURSO_CARGA_ID,  EVALUADOS, PUNTOS, PORCENTAJE, EXTRAS," + 
					" TO_CHAR(((PORCENTAJE+PUNTOS+EXTRAS)*100/CASE EVALUADOS WHEN 0 THEN 1 ELSE EVALUADOS END),'990.99') AS NOTA" + 
					" FROM ENOC.ALUMNO_NOTA" + 
					" WHERE CODIGO_PERSONAL = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumnoNotaMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoNota|lista|:"+ex); 
		}
		return lista;
	}
	
	
	public HashMap<String,AlumnoNota> mapaNotas( String codigoPersonal) {
		List<AlumnoNota> lista	= new ArrayList<AlumnoNota>();
		HashMap<String,AlumnoNota> mapa		= new HashMap<String,AlumnoNota>();
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL,  CURSO_CARGA_ID,  EVALUADOS, PUNTOS, PORCENTAJE, EXTRAS," + 
					" TO_CHAR(((PORCENTAJE+PUNTOS+EXTRAS)*100/CASE EVALUADOS WHEN 0 THEN 1 ELSE EVALUADOS END),'990.99') AS NOTA" + 
					" FROM ENOC.ALUMNO_NOTA" + 
					" WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumnoNotaMapper(), parametros);
			
			for (AlumnoNota alumnoNota : lista){			
				mapa.put(alumnoNota.getCodigoPersonal()+alumnoNota.getCursoCargaId(), alumnoNota);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoNota.mapaNotas|:"+ex);
		}
		return mapa;
	}
	
}