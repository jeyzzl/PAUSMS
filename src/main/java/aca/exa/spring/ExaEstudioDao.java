package aca.exa.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaEstudioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaEstudio exaEstudio ){
		boolean ok = false;
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_ESTUDIO(ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, PERIODO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDESTUDIO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?, ?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )";			
			Object[] parametros = new Object[]{
					exaEstudio.getEstudioId(), exaEstudio.getMatricula(), exaEstudio.getEstudios(), exaEstudio.getInstitucion(),
					exaEstudio.getPeriodo(),  exaEstudio.getFechaAct(),  exaEstudio.getEliminado(), exaEstudio.getIdEstudio()
			};	
			if (enocJdbc.update(comando,parametros) ==1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean eliminar( String estudioId ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.EXA_ESTUDIO SET ELIMINADO = '1' WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[]{ estudioId };	
			if (enocJdbc.update(comando,parametros) ==1 ){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|eliminar|:"+ex);		
		}		
		return ok;
	}
	
	public ExaEstudio mapeaRegIdEstudio(  String estudioId) {
		ExaEstudio exaEstudio = new ExaEstudio();		
		try{
			String comando = "SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, "
				+ " PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "
				+ " FROM ENOC.EXA_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')";
			exaEstudio = enocJdbc.queryForObject(comando, new ExaEstudioMapper(), estudioId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|mapeaRegIdEstudio|:"+ex);			
		}
		return exaEstudio;
	}
	
	public ExaEstudio mapeaRegId(  String matricula) {
		ExaEstudio exaEstudio = new ExaEstudio();
		try{
			String comando = "SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, " +
					" PERIODO, FECHAACTUALIZACION, ELIMINADO, IDESTUDIO "+
				"FROM ENOC.EXA_ESTUDIO WHERE MATRICULA = ?"; 
			exaEstudio = enocJdbc.queryForObject(comando, new ExaEstudioMapper(), matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaEstudio;
	}
	
	public boolean existeReg( String estudioId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ESTUDIO WHERE ESTUDIO_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.queryForObject(comando,Integer.class, estudioId) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoRegAlumno( String matricula) {
		String maximo 			= "1";	
		try{
			String comando = "SELECT MAX(ESTUDIO_ID)+1 AS MAXIMO FROM ENOC.EXA_FAMILIA WHERE ELIMINADO !=1 AND MATRICULA = ?";		
			maximo =  enocJdbc.queryForObject(comando,String.class, matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|maximoRegAlumno|:"+ex);
		}
		
		return maximo;
	}
	
	
	public String maximoReg() {
		String maximo 			= "1";	
		try{
			String comando = "SELECT MAX(ESTUDIO_ID)+1 AS MAXIMO FROM ENOC.EXA_ESTUDIO";		
			maximo =  enocJdbc.queryForObject(comando,String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public List<ExaEstudio> lisEstudios( String matricula, String orden) {
		List<ExaEstudio> lista		= new ArrayList<ExaEstudio>();
		try{
			String comando = "SELECT ESTUDIO_ID, MATRICULA, ESTUDIOS, INSTITUCION, PERIODO, FECHAACTUALIZACION," +
				" ELIMINADO, IDESTUDIO FROM ENOC.EXA_ESTUDIO" +
				" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;
			lista = enocJdbc.query(comando, new ExaEstudioMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstudioDao|lisEstudios|:"+ex);
		}		
		return lista;
	}
}
