package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentCarrera carrera) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MENT_CARRERA" + 
					" (CARRERA_ID, MENTOR_ID, PERIODO_ID)" +
					" VALUES(?, ?, ?)";
					
			Object[] parametros = new Object[] {carrera.getCarreraId(),carrera.getMentorId(),carrera.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String carreraId, String mentorId, String periodoId){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_CARRERA"+ 
				" WHERE CARRERA_ID = ?" +
				" AND MENTOR_ID = ?" +
				" AND PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {carreraId,mentorId,periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg(String carreraId, String mentorId, String periodoId) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_CARRERA" + 
					" WHERE CARRERA_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {carreraId,mentorId,periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getNumMentoresCarrera( String carreraId, String periodoId) {
		String cantidad 		= "0";
				
		try{
			String comando = "SELECT COUNT(MENTOR_ID) AS CANTIDAD" +
					" FROM ENOC.MENT_CARRERA" + 
					" WHERE CARRERA_ID = ?" +
					" AND PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {carreraId,periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				cantidad = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|getNumMentoresCarrera|:"+ex);
		}
		return cantidad;
	}
		
	//Cuando uses este metodo, porfavor borra este comentario para saber que ya se uso
	public List<MentCarrera> getListAll( String orden ) {
		List<MentCarrera> lista	= new ArrayList<MentCarrera>();
		String comando	= "";		
		try{
			comando = "SELECT CARRERA_ID, MENTOR_ID, PERIODO_ID FROM ENOC.MENT_CARRERA "+orden;			
			lista = enocJdbc.query(comando, new MentCarreraMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<MentCarrera> getListCarrera( String carreraId, String periodoId, String orden ) {
		List<MentCarrera> lista	= new ArrayList<MentCarrera>();
		String comando	= "";		
		try{
			comando = "SELECT CARRERA_ID, MENTOR_ID, PERIODO_ID FROM ENOC.MENT_CARRERA WHERE CARRERA_ID = ? AND PERIODO_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new MentCarreraMapper(), carreraId, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|getListCarrera|:"+ex);
		}
		return lista;
	}
	
	public List<String> listMentorFacultad( String periodoId, String facultadId, String fecha, String orden ) {
		List<String> lista	= new ArrayList<String>();
		String comando	= "";		
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID FROM ENOC.MENT_CARRERA"
					+ " WHERE PERIODO_ID = ?"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND MENTOR_ID IN "
					+ " 	(SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? "
					+ "		AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL ) " + orden;		
			lista = enocJdbc.queryForList(comando, String.class, periodoId, facultadId, periodoId, fecha);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|listMentorFacultad|:"+ex);
		}
		return lista;
	}
	
	public List<String> listMentoresPeriodo( String periodoId, String orden ) {
		List<String> lista	= new ArrayList<String>();
		String comando	= "";		
		try{
			comando = "SELECT DISTINCT(MENTOR_ID) AS MENTOR_ID FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = ? "+ orden;			
			lista = enocJdbc.queryForList(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|listMentoresPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<MentCarrera> getCarrerasMentor(  String periodoId, String mentorId, String orden ) {
		List<MentCarrera> lista	= new ArrayList<MentCarrera>();	
		try{
			String comando = " SELECT PERIODO_ID, CARRERA_ID, MENTOR_ID FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = ? AND MENTOR_ID = ? "+ orden;
			Object[] parametros = new Object[] {periodoId, mentorId}; 
			lista = enocJdbc.query(comando, new MentCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|getCarrerasMentor|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,MentCarrera> getMapAll(String orden) {
		
		HashMap<String,MentCarrera> mapa = new HashMap<String,MentCarrera>();
		List<MentCarrera> lista = new ArrayList<MentCarrera>();		
		try{
			String comando = "SELECT CARRERA_ID, MENTOR_ID, PERIODO_ID FROM ENOC.MENT_CARRERA "+orden;
			lista = enocJdbc.query(comando, new MentCarreraMapper());			
			for(MentCarrera objeto : lista){
				mapa.put(objeto.getCarreraId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapNumMentoresCarrera() {
		HashMap<String,String> map	= new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CARRERA_ID||PERIODO_ID AS LLAVE, COUNT(MENTOR_ID) AS VALOR FROM ENOC.MENT_CARRERA GROUP BY CARRERA_ID||PERIODO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|mapNumMentoresCarrera|:"+ex);
		}
		return map;
	}
	
	public HashMap<String,String> mapNumMentoresCarrera(String periodoId) {
		HashMap<String,String> map	= new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(MENTOR_ID) AS VALOR FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = ? GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentCarreraDao|mapNumMentoresCarrera|:"+ex);
		}
		return map;
	}
		
}