package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaAlumEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaAlumEvento exaAlumEvento ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_ALUMEVENTO(ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? , TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'9'), ? )";
			Object[] parametros = new Object[]{
				exaAlumEvento.getAlumEventoId(),exaAlumEvento.getMatricula(),exaAlumEvento.getIdEvento(),exaAlumEvento.getFechaActualizacion(),exaAlumEvento.getEliminado(),exaAlumEvento.getIdEventoAsistido()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean eliminar( String alumEventoId){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXA_ALUMEVENTO SET ELIMINADO = '1' WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.update(comando,alumEventoId)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|eliminar|:"+ex);
		}		
		return ok;
	}
	
	public ExaAlumEvento mapeaRegId(  String alumEventoId){
		ExaAlumEvento exaAlumEvento = new ExaAlumEvento();	 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')"; 
			if (enocJdbc.queryForObject(comando, Integer.class, alumEventoId) >= 1){
				comando = "SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO"
					+ " FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')";
				exaAlumEvento = enocJdbc.queryForObject(comando, new ExaAlumEventoMapper(), alumEventoId);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|mapeaRegId|:"+ex);		
		}
		
		return exaAlumEvento;
	}
	
	public boolean existeReg( String alumEventoId){
		boolean 		ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ALUMEVENTO WHERE ALUMEVENTO_ID = TO_NUMBER(?,'99999999')";			
			if (enocJdbc.queryForObject(comando, Integer.class, alumEventoId) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno( String eventoId, String matricula){
		boolean 		ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ALUMEVENTO WHERE IDEVENTO = ? AND MATRICULA = ? AND ELIMINADO = 0 ";
			if (enocJdbc.queryForObject(comando, Integer.class, eventoId, matricula) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|existeAlumno|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String matricula){
		int maximo 			= 1;	
		try{
			String comando = "SELECT MAX(ALUMEVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_ALUMEVENTO WHERE ELIMINADO !=1 AND MATRICULA = ?";
			maximo = enocJdbc.queryForObject(comando, Integer.class, matricula);				
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String maximoReg(){
		int maximo 			= 1;		
		try{
			String comando = "SELECT MAX(ALUMEVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_ALUMEVENTO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<ExaAlumEvento> lisPorEvento( String idevento, String orden) {
		
		List<ExaAlumEvento> lista		= new ArrayList<ExaAlumEvento>();
		try{
			String comando = "SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO " +
					" FROM ENOC.EXA_ALUMEVENTO" +
					" WHERE IDEVENTO = ? AND ELIMINADO != 1 "+orden;	
			lista = enocJdbc.query(comando, new ExaAlumEventoMapper(), idevento);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|getAlumnosEvento|:"+ex);
		}
		
		return lista;
	}
	
	public List<ExaAlumEvento> lisPorAlumno( String matricula, String orden) {
		
		List<ExaAlumEvento> lista		= new ArrayList<ExaAlumEvento>();		
		try{
			String comando = "SELECT ALUMEVENTO_ID, MATRICULA, IDEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTOASISTIDO " +
					" FROM ENOC.EXA_ALUMEVENTO" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;		
			lista = enocJdbc.query(comando, new ExaAlumEventoMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaAlumEventoDao|getAlumEvento|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaAlumnosPorEvento() {		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT IDEVENTO AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ENOC.EXA_ALUMEVENTO GROUP BY IDEVENTO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ExaAlumEventoDao|mapaAlumnosPorEvento|:"+ex);
		}		
		return mapa;
	}
}
