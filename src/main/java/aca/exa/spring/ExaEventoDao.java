package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaEventoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaEvento exaEvento ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_EVENTO(EVENTO_ID, NOMBRE, LUGAR, FECHAEVENTO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEVENTO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )";
			
			Object[] parametros = new Object[]{
				exaEvento.getEventoId(),exaEvento.getNombre(),exaEvento.getLugar(),exaEvento.getFechaEvento(),
				exaEvento.getFechaActualizacion(),exaEvento.getEliminado(),exaEvento.getIdEvento()	
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean update( ExaEvento exaEvento ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXA_EVENTO"+ 
				" SET NOMBRE = ?, " +
				" LUGAR = ?, " +
				" FECHAEVENTO = TO_DATE(?,'DD/MM/YYYY')," +
				" FECHAACTUALIZACION = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				" ELIMINADO = TO_NUMBER(?,'9') , " +
				" IDEVENTO = ? " +
				" WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[]{
					exaEvento.getNombre(),exaEvento.getLugar(),exaEvento.getFechaEvento(),exaEvento.getFechaActualizacion(),
					exaEvento.getEliminado(),exaEvento.getIdEvento(), exaEvento.getEventoId()	
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|update|:"+ex);
		}
		
		return ok;
	}
	
	public boolean eliminar( String eventoId ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXA_EVENTO SET ELIMINADO = '1' WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[]{	eventoId };				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|eliminar|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean existeReg( String eventoId){
		boolean 		ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";	
			if (enocJdbc.queryForObject(comando, Integer.class, eventoId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ExaEvento mapeaRegIdEvento(  String eventoId) {
		ExaEvento exaEvento = new ExaEvento();		 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";	
			if (enocJdbc.queryForObject(comando, Integer.class, eventoId) >= 1){
				comando = "SELECT EVENTO_ID, NOMBRE, LUGAR, " +
						" TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTO "+
					"FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";
				Object[] parametros = new Object[] {eventoId};
				exaEvento = enocJdbc.queryForObject(comando, new ExaEventoMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|mapeaRegIdEvento|:"+ex);
		}
		return exaEvento;
	}
	
	public String maximoReg(){
		int maximo 	= 1;	
		try{
			String comando = "SELECT MAX(EVENTO_ID)+1 AS MAXIMO FROM ENOC.EXA_EVENTO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ExaEvento|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public boolean tieneAlumnos(String eventoId){
		boolean 		ok 	= false;	
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ALUMEVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";	
			if (enocJdbc.queryForObject(comando, Integer.class, eventoId) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|tieneAlumnos|:"+ex);
		}
		
		return ok;
	}	
	
	public String getNombre( String eventoId){		
		String nombre 			= "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999999')";	
			if (enocJdbc.queryForObject(comando, Integer.class, eventoId) >= 1){
				comando = "SELECT NOMBRE FROM ENOC.EXA_EVENTO WHERE EVENTO_ID = ?";
				nombre = enocJdbc.queryForObject(comando, String.class, eventoId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|getNombre|:"+ex);
		}
		
		return nombre;
	}
		
	public List<ExaEvento> lisTodos( String orden ){		
		List<ExaEvento> lista		= new ArrayList<ExaEvento>();	
		try{
			String comando = "SELECT EVENTO_ID, NOMBRE, LUGAR, TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, TO_CHAR(FECHAACTUALIZACION,'DD/MM/YYYY') AS FECHAACTUALIZACION, ELIMINADO, IDEVENTO " +
				" FROM ENOC.EXA_EVENTO "+
				" WHERE ELIMINADO != 1 "+orden;				
			lista = enocJdbc.query(comando, new ExaEventoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<ExaEvento> lisPorAlumno( String codigoPersonal, String orden ){
		
		List<ExaEvento> lista	= new ArrayList<ExaEvento>();		
		try{
			String comando = "SELECT EVENTO_ID, NOMBRE, LUGAR, TO_CHAR(FECHAEVENTO,'DD/MM/YYYY') AS FECHAEVENTO, FECHAACTUALIZACION, ELIMINADO, IDEVENTO " +
				" FROM ENOC.EXA_EVENTO " +
				" WHERE EVENTO_ID IN (SELECT IDEVENTO FROM ENOC.EXA_ALUMEVENTO WHERE MATRICULA = ?)"+
				" AND ELIMINADO != 1 "+orden;		
			lista = enocJdbc.query(comando, new ExaEventoMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEventoUtil|lisPorAlumno|:"+ex);
		}		
		return lista;
	}
		
}