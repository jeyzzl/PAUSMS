package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumVacacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumVacacion alumVacacion){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_VACACION " + 
				"(NIVEL_ID, MODALIDAD_ID, F_EXAMEN, F_INICIO, F_FINAL) " +
				"VALUES(TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), " +
				"TO_DATE(?,'DD/MM/YYYY'), " +
				"TO_DATE(?,'DD/MM/YYYY'), " +
				"TO_DATE(?,'DD/MM/YYYY'))";
			
			Object[] parametros = new Object[] {
					alumVacacion.getNivelId(),alumVacacion.getModalidadId(),alumVacacion.getfExamen(),alumVacacion.getfInicio(), alumVacacion.getfFinal()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|insertReg|:"+ex);			
		
		}
		return ok;
	}	
	
	public boolean updateReg(AlumVacacion alumVacacion){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_VACACION SET " + 
					"F_EXAMEN = TO_DATE(?,'DD/MM/YYYY'), " +
					"F_INICIO = TO_DATE(?,'DD/MM/YYYY'), " +
					"F_FINAL = TO_DATE(?,'DD/MM/YYYY') " +
					"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
					alumVacacion.getfExamen(),alumVacacion.getfInicio(), alumVacacion.getfFinal(), alumVacacion.getNivelId(),alumVacacion.getModalidadId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|updateReg|:"+ex);			
		
		}
		return ok;
	}	
	
	public boolean deleteReg(String nivelId, String modalidadId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_VACACION "+ 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
				"AND MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId, modalidadId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error  - aca.alumno.spring.AlumVacacionDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public AlumVacacion mapeaRegId( String nivelId, String modalidadId){
		
		AlumVacacion vacacion = new AlumVacacion();

		try{
			String comando = "SELECT "+
				"NIVEL_ID, MODALIDAD_ID, "+
				"TO_CHAR(F_EXAMEN,'DD/MM/YYYY') AS F_EXAMEN, " +
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL " +
				"FROM ENOC.ALUM_VACACION " + 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
				"AND MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId, modalidadId};
			vacacion = enocJdbc.queryForObject(comando, new AlumVacacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|mapeaRegId|:"+ex);
		}
		
		return vacacion;
	}

	public boolean existeReg(String nivelId, String modalidadId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = TO_NUMBER(?,'99') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId, modalidadId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|existeReg|:"+ex);		
		}		
		return ok;
	}	
	
	public String getFechaExamen(String nivelId, String modalidadId){
		String examen	= "X";
		
		try{
			String comando = "SELECT COALESCE(TO_CHAR(F_EXAMEN,'DD/MM/YYYY'),'01/01/2010') AS F_EXAMEN " +
					"FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = ? "+
					"AND MODALIDAD_ID = ? ";			
				examen = enocJdbc.queryForObject(comando, String.class,nivelId,modalidadId);					
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|getFechaExamen|:"+ex);	
		}		
		return examen;
	}	
	
	public String getFechaInicio(String nivelId, String modalidadId){

		String inicio	= "X";
		
		try{
			String comando = "SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO " +
					"FROM ENOC.ALUM_VACACION " + 
					"WHERE NIVEL_ID = ? " +
					"AND MODALIDAD_ID = ? ";
			inicio = enocJdbc.queryForObject(comando, String.class,nivelId,modalidadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|getFechaInicio|:"+ex);		
		}		
		return inicio;
	}	
	
	public String getFechaFinal(String nivelId, String modalidadId){

		String end	= "X";
		
		try{
			String comando = "SELECT TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL " +
				"FROM ENOC.ALUM_VACACION " + 
				"WHERE NIVEL_ID = ? " +
				"AND MODALIDAD_ID = ? ";
			end = enocJdbc.queryForObject(comando, String.class,nivelId,modalidadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|getFechaFinal|:"+ex);		
		}
		return end;
	}	

	public ArrayList<AlumVacacion> getListAll(String orden ){		
		
		List<AlumVacacion> lista= new ArrayList<AlumVacacion>();
		
		try{
			String comando = "SELECT NIVEL_ID, MODALIDAD_ID, " +
					"TO_CHAR(F_EXAMEN,'DD/MM/YYYY') AS F_EXAMEN, " +
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, " +
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL "+
					"FROM ENOC.ALUM_VACACION "+orden; 
			lista = enocJdbc.query(comando, new AlumVacacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumVacacionDao|getListAll|:"+ex);
		
		}				
		
		return (ArrayList<AlumVacacion>) lista;
	}
}