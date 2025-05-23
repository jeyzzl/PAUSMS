package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaEmpleoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaEmpleo exaEmpleo ){
		boolean ok = false;

		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_EMPLEO(EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDEMPLEO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )";
			
			Object[] parametros = new Object[] {
					exaEmpleo.getEmpleoId(), exaEmpleo.getMatricula(), exaEmpleo.getEmpresa(), exaEmpleo.getPeriodo(), exaEmpleo.getFechaAct(), exaEmpleo.getEliminado(), exaEmpleo.getIdEmpleo() 
				};	
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean eliminar(String empleoId ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.EXA_EMPLEO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE EMPLEO_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {empleoId};
			if(enocJdbc.update(comando, parametros)==1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|eliminar|:"+ex);		
		}
		
		return ok;
	}	
	
	public ExaEmpleo mapeaRegIdEstudio(String empleoId){
		ExaEmpleo exaEmpleo = new ExaEmpleo();
		
		try{
			String comando = "SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO "+
				"FROM ENOC.EXA_EMPLEO WHERE EMPLEO_ID = TO_NUMBER(?,'99999999')"; 
			Object[] parametros = new Object[] {empleoId};
			exaEmpleo = enocJdbc.queryForObject(comando, new ExaEmpleoMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaEmpleo;
	}
	
	public ExaEmpleo mapeaRegId(String matricula){
		ExaEmpleo exaEmpleo = new ExaEmpleo();
		
		try{
			String comando = "SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO "+
				"FROM ENOC.EXA_EMPLEO WHERE MATRICULA = ?"; 
			Object[] parametros = new Object[] {matricula};
			exaEmpleo = enocJdbc.queryForObject(comando, new ExaEmpleoMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaEmpleo;
	}
	
	public boolean existeReg(String empleoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.EXA_EMPLEO WHERE EMPLEO_ID = TO_NUMBER(?,'99999999') "; 
			Object[] parametros = new Object[] {empleoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EMPLEO WHERE MATRICULA = ? AND ELIMINADO!=1 "; 
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String matricula){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(EMPLEO_ID)+1 AS MAXIMO FROM ENOC.EXA_EMPLEO WHERE ELIMINADO !=1 AND MATRICULA = ?"; 
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class, parametros));
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEmpleoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String maximoReg(){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(EMPLEO_ID)+1 AS MAXIMO FROM ENOC.EXA_EMPLEO";
			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<ExaEmpleo> getEmpleos(String matricula, String orden){
		
		List<ExaEmpleo> list		= new ArrayList<ExaEmpleo>();
		
		try{
			String comando = "SELECT EMPLEO_ID, MATRICULA, EMPRESA, PERIODO," +
					" FECHAACTUALIZACION, ELIMINADO, IDEMPLEO FROM ENOC.EXA_EMPLEO" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
			Object[] parametros = new Object[] {matricula};	
			list = enocJdbc.query(comando, new ExaEmpleoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEmpleoUtil|getEmpleos|:"+ex);
		}
		
		return list;
	}
}
