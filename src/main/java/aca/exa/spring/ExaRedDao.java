package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaRedDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaRed exaRed ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_REDSOCIAL(REDSOCIAL_ID, MATRICULA, RED, FECHAACTUALIZACION, " +
				"ELIMINADO, IDREDSOCIAL) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )";
			Object[] parametros = new Object[] {
					exaRed.getRedSocialId(), exaRed.getMatricula(), exaRed.getRed(), exaRed.getFechaAct(), exaRed.getEliminado(), exaRed.getIdRedSocial()
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean eliminar(String redSocialId){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_REDSOCIAL"+ 
				" SET ELIMINADO = '1' " +
				" WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {redSocialId};
			if(enocJdbc.update(comando, parametros)==1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|eliminar|:"+ex);		
		}
		
		return ok;
	}
	
	public ExaRed mapeaRegIdEstudio(String redSocialId){
		ExaRed exaRed = new ExaRed();
		
		try{
			String comando = "SELECT REDSOCIAL_ID, MATRICULA, RED, " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')"; 
			Object[] parametros = new Object[] {redSocialId};
			exaRed = enocJdbc.queryForObject(comando, new ExaRedMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaRed;
	}
	
	public ExaRed mapeaRegId(String matricula){
		ExaRed exaRed = new ExaRed();

		try{
			String comando = "SELECT REDSOCIAL_ID, MATRICULA, RED,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_REDSOCIAL WHERE MATRICULA = ?";
			exaRed = enocJdbc.queryForObject(comando, new ExaRedMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return exaRed;
	}
	
	public boolean existeReg(String redSocialId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.EXA_REDSOCIAL WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999') "; 
			Object[] parametros = new Object[] {redSocialId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_REDSOCIAL WHERE MATRICULA = ? AND ELIMINADO!=1 "; 
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String matricula){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(REDSOCIAL_ID)+1 AS MAXIMO FROM EXA_REDSOCIAL WHERE ELIMINADO !=1 AND MATRICULA = ?"; 
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class, parametros));
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaRedDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String maximoReg(){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(REDSOCIAL_ID)+1 AS MAXIMO FROM EXA_REDSOCIAL";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|maximoReg|:"+ex);
		}
		
		return maximo==null?"1":maximo;
	}
	
	public List<ExaRed> getRed(String matricula, String orden){
		
		List<ExaRed> list		= new ArrayList<ExaRed>();
		
		try{
			String comando = "SELECT REDSOCIAL_ID, MATRICULA, RED, " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL FROM ENOC.EXA_REDSOCIAL" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
			Object[] parametros = new Object[] {matricula};	
			list = enocJdbc.query(comando, new ExaRedMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaRedUtil|getRed|:"+ex);
		}
		
		return list;
	}
}
