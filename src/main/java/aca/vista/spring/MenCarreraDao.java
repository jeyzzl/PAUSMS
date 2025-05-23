/**
 * 
 */
package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */

@Component
public class MenCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public MenCarrera mapeaRegId( String mentorId ){
		
		MenCarrera menCarrera = new MenCarrera();
		try{
			String comando = "SELECT"+
					" PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
					" CARRERA_NOMBRE, MENTOR_ID, MENTOR_NOMBRE FROM ENOC.MEN_CARRERA"+
					" WHERE MENTOR_ID = ? ";
			Object[] parametros = new Object[] {mentorId};
			menCarrera = enocJdbc.queryForObject(comando, new MenCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|mapeaRegId|:"+ex);
			ex.printStackTrace();		
		}
		
		return menCarrera;
	}
	
	public boolean existeReg( String periodoId, String carreraId, String mentorId){
		
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MEN_CARRERA "+
				"WHERE PERIODO_ID = ? " +
				"AND CARRERA_ID = ? " +
				"AND MENTOR_ID = ? ";
			Object[] parametros = new Object[] {periodoId, carreraId, mentorId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CarreraMentorDao|existeReg|:"+ex);		
		}
		
		return ok;
	}
	
	public List<MenCarrera> getListCarrera( String carreraId, String orden ){		
		List<MenCarrera> lista	= new ArrayList<MenCarrera>();		
		try{
			String comando = "SELECT PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE, MENTOR_ID, MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE CARRERA_ID = ? "+orden;
			Object[] parametros = new Object[] {carreraId};
			lista = enocJdbc.query(comando, new MenCarreraMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getListCarrera|:"+ex);
		}	
		return lista;	
	}
	// LISTADO DE MENTORES EN GENERAL
	public List<MenCarrera> getLista( String orden ){		
		List<MenCarrera> lista	= new ArrayList<MenCarrera>();		
		try{
			String comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID, MENTOR_NOMBRE," +
					" FACULTAD_ID, FACULTAD_NOMBRE,"+
					" CARRERA_ID, CARRERA_NOMBRE, PERIODO_ID FROM ENOC.MEN_CARRERA "+orden;
			lista = enocJdbc.query(comando, new MenCarreraMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getLista|:"+ex);
		}
	
		return lista;	
	}
	
	public List<MenCarrera> getListPeriodo( String periodoId, String orden ){		
		List<MenCarrera> lista	= new ArrayList<MenCarrera>();		
		try{
			String comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new MenCarreraMapper(),periodoId);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getListPeriodo|:"+ex);
		}		
	
		return lista;	
	}
	
	public List<MenCarrera> getListMentPeriodo( String periodoId, String orden ){		
		List<MenCarrera> lista	= new ArrayList<MenCarrera>();	
		try{
			String comando = "SELECT PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID, CARRERA_NOMBRE, MENTOR_ID, MENTOR_NOMBRE"
					+ " FROM ENOC.MEN_CARRERA"
					+ " WHERE PERIODO_ID = ? "+orden;
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new MenCarreraMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getListMentPeriodo|:"+ex);
		}		
		
		return lista;	
	}	
	
	
	public List<MenCarrera> getListMentPeriodo( String periodoId, String facultadId, String orden ){	
		List<MenCarrera> lista	= new ArrayList<MenCarrera>();	
		try{
			String comando = "SELECT MENTOR_ID, PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID,"
					+ " CARRERA_NOMBRE, MENTOR_NOMBRE FROM ENOC.MEN_CARRERA"
					+ " WHERE PERIODO_ID = ?"
					+ " AND FACULTAD_ID = ? "+orden;
			Object[] parametros = new Object[] {periodoId, facultadId};
			lista = enocJdbc.query(comando, new MenCarreraMapper(), parametros);			
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getListMentPeriodo|:"+ex);
		}		
	
		return lista;	
	}	
	
	public List<MenCarrera> getListMentPerFecha( String periodoId, String fecha, String orden ){	
		List<String> lista	= new ArrayList<String>();
		List<MenCarrera> lisMentor	= new ArrayList<MenCarrera>();
	
		try{
			String comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID ,PERIODO_ID, FACULTAD_ID, FACULTAD_NOMBRE, CARRERA_ID," +
				" CARRERA_NOMBRE,MENTOR_NOMBRE FROM ENOC.MEN_CARRERA" +
				" WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, periodoId);
		
			for (String mentorId: lista ){				
				MenCarrera mentor = new MenCarrera();
				mentor = mapeaRegId(mentorId);
				lisMentor.add(mentor);					
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MenCarreraDao|getListMentPerFecha|:"+ex);
		}		
	
		return lisMentor;	
	}
	
}