package aca.exa.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaEgresoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaEgreso egreso ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.EXA_EGRESO(EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID)"+
				" VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), TO_NUMBER(?,'9'), ?, ? )";
			Object[] parametros = new Object[]{ egreso.getEgresoId(), egreso.getMatricula(), egreso.getCarreraId(), egreso.getYear(), egreso.getFechaAct(), 
					egreso.getEliminado(), egreso.getIdEgresadoAno(), egreso.getPlanId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean eliminar( String egresoId ) {
		boolean ok = false;	
		try{
			String comando = "UPDATE ENOC.EXA_EGRESO SET ELIMINADO = '1' WHERE EGRESO_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.update(comando,egresoId)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|eliminar|:"+ex);		
		}
		return ok;
	}	
	
	public ExaEgreso mapeaRegIdEstudio(  String estudioId) {
		ExaEgreso exaEgreso = new ExaEgreso();				 
		try{
			String comando = "SELECT EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID"
					+ " FROM ENOC.EXA_EGRESO WHERE EGRESO_ID = TO_NUMBER(?,'99999999')";			
			exaEgreso = enocJdbc.queryForObject(comando, new ExaEgresoMapper(), estudioId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaEgreso;
	}
	
	public ExaEgreso mapeaRegId(  String matricula) {
		ExaEgreso exaEgreso = new ExaEgreso();
		try{
			String comando = "SELECT EGRESO_ID, MATRICULA, CARRERAID, YEAR, FECHAACTUALIZACION, ELIMINADO, IDEGRESADOANO, PLAN_ID"
					+ " FROM ENOC.EXA_EGRESO WHERE MATRICULA = ?";
			exaEgreso = enocJdbc.queryForObject(comando, new ExaEgresoMapper(), matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaEgreso;
	}
	
	public boolean existeReg( String egresoId) {
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EGRESO WHERE EGRESO_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.queryForObject(comando,Integer.class, egresoId) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeAlumno( String matricula){
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_EGRESO WHERE MATRICULA = ? AND ELIMINADO!=1";
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoRegAlumno( String matricula) {
		int maximo 		= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(EGRESO_ID)+1,1) AS MAXIMO FROM ENOC.EXA_EGRESO WHERE ELIMINADO !=1 AND MATRICULA = ?";
			maximo = enocJdbc.queryForObject(comando,Integer.class, matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|maximoRegAlumno|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String maximoReg() {
		int maximo 			= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(EGRESO_ID)+1,1) AS MAXIMO FROM ENOC.EXA_EGRESO";		
			maximo = enocJdbc.queryForObject(comando,Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public List<ExaEgreso> getEgresos( String matricula, String orden) {		
		List<ExaEgreso> lista		= new ArrayList<ExaEgreso>();		
		try{
			String comando = "SELECT * FROM EXA_EGRESO WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;	
			lista = enocJdbc.query(comando, new ExaEgresoMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|getEgresos|:"+ex);
		}		
		return lista;
	}	
	
	public List<ExaEgreso> getEgresos( String orden) {		
		List<ExaEgreso> lista		= new ArrayList<ExaEgreso>();	
		try{
			String comando = "SELECT * FROM EXA_EGRESO WHERE ELIMINADO != 1 "+orden;
			lista = enocJdbc.query(comando, new ExaEgresoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|getEgresos|:"+ex);
		}		
		return lista;
	}
	
	public List<ExaEgreso> getEgresadosPorCarrera( String carreraId, String orden) {		
		List<ExaEgreso> lista				= new ArrayList<ExaEgreso>();	
		try{
			String comando = "SELECT * FROM EXA_EGRESO WHERE CARRERAID = ? AND ELIMINADO != 1 "+orden;	
			lista = enocJdbc.query(comando, new ExaEgresoMapper(), carreraId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|getEgresadosPorCarrera|:"+ex);
		}
		return lista;
	}
	
	
	public List<String> getAnios( String orden) {		
		List<String> lista					= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(YEAR) AS YEAR  FROM EXA_EGRESO " +orden;
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|getAnios|:"+ex);
		}		
		return lista;
	}
	
	public List<String> getCarreras( String orden) {		
		List<String> lista					= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CARRERAID) AS CARRERAID FROM EXA_EGRESO " +orden;		
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEgresoDao|getCarreras|:"+ex);
		}		
		return lista;
	}
}
