package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaIglesiaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaIglesia exaIglesia ){
		boolean ok = false;
		try{
			String comando =("INSERT INTO "+
				"ENOC.EXA_IGLESIA(IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEGRESADOIGLESIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			Object[] parametros = new Object[]{ 
					exaIglesia.getIglesiaId(), exaIglesia.getMatricula(), exaIglesia.getIglesia(), exaIglesia.getFuncion(), exaIglesia.getFechaAct(), exaIglesia.getEliminado(),
					exaIglesia.getIdEgresadoIglesia(),
				};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean eliminar( int iglesiaId ){
		boolean ok = false;
		try{			
			String comando= "UPDATE ENOC.EXA_IGLESIA SET ELIMINADO = '1' WHERE IGLESIA_ID = ?";			
			if (enocJdbc.update(comando, iglesiaId ) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|eliminar|:"+ex);		
		}		
		return ok;
	}
	
	public ExaIglesia mapeaRegIdIglesia( String iglesiaId){
		ExaIglesia exaIglesia = new ExaIglesia();
		try{
			String comando= ("SELECT COUNT(*) FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999')"); 
			if (enocJdbc.queryForObject(comando,Integer.class, iglesiaId) >= 1){
				comando = "SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "
					+ " FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999')";			
				exaIglesia = enocJdbc.queryForObject(comando, new ExaIglesiaMapper(), iglesiaId);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|mapeaRegId|:"+ex);			
		}
		return exaIglesia;
	}
	
	public ExaIglesia mapeaRegId( String matricula){
		ExaIglesia exaIglesia = new ExaIglesia();
		try{
			String comando= ("SELECT COUNT(*) FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ?"); 
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				comando=("SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA "+
					"FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ?");				
				exaIglesia = enocJdbc.queryForObject(comando, new ExaIglesiaMapper(), matricula);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|mapeaRegId|:"+ex);			
		}
		return exaIglesia;
	}
	
	public boolean existeReg(String iglesiaId){
		boolean 		ok 	= false;	
		try{
			String comando= ("SELECT COUNT(*) FROM ENOC.EXA_IGLESIA WHERE IGLESIA_ID = TO_NUMBER(?,'99999999') "); 
			if (enocJdbc.queryForObject(comando,Integer.class, iglesiaId) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = ("SELECT COUNT(*) FROM ENOC.EXA_IGLESIA WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|existeAlumno|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg(String matricula){
		String maximo 			= "1";		
		try{
			String comando = ("SELECT COALESCE(MAX(IGLESIA_ID),0) AS MAXIMO FROM ENOC.EXA_IGLESIA WHERE ELIMINADO != 1 AND MATRICULA = ?"); 
			maximo = enocJdbc.queryForObject(comando,String.class, matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String maximoReg(){
		String maximo 			= "1";		
		try{
			String comando = ("SELECT MAX(IGLESIA_ID)+1 AS MAXIMO FROM ENOC.EXA_IGLESIA");
			maximo = enocJdbc.queryForObject(comando,String.class);

		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<ExaIglesia> getIglesia( String matricula, String orden){
		
		List<ExaIglesia> list		= new ArrayList<ExaIglesia>();
		
		
		try{
		String	comando = "SELECT IGLESIA_ID, MATRICULA, IGLESIA, FUNCION, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEGRESADOIGLESIA FROM ENOC.EXA_IGLESIA" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
		list = enocJdbc.query(comando, new ExaIglesiaMapper(), matricula);

		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaIglesiaDao|getIglesia|:"+ex);
		}
		return list;
	}
}
