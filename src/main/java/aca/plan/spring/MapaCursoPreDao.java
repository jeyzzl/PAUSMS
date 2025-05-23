// Clase DAO para la tabla de Prerrequisitos
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class MapaCursoPreDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaCursoPre mapaCursoPre ) {		
		boolean ok 		= false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_CURSO_PRE(CURSO_ID, CURSO_ID_PRE ) VALUES( ?, ? )";
			Object[] parametros = new Object[] {mapaCursoPre.getCursoId(),mapaCursoPre.getCursoIdPre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg( MapaCursoPre mapaCursoPre ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ";
			Object[] parametros = new Object[] {mapaCursoPre.getCursoId(),mapaCursoPre.getCursoIdPre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean deletePrerrequisitos( String cursoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|deletePrerrequisitos|:"+ex);
		}		
		return ok;
	}
	
	public MapaCursoPre mapeaRegId(  String cursoId, String cursoIdPre) {
		
		MapaCursoPre mapaCursoPre = new MapaCursoPre();
		try{
			String comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ";
			Object[] parametros = new Object[] {cursoId, cursoIdPre};
			mapaCursoPre = enocJdbc.queryForObject(comando, new MapaCursoPreMapper(), parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|mapeaRegId|:"+ex);
		}
		
		return mapaCursoPre;
	}
	
	public boolean existeReg( String cursoId, String cursoIdPre) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ? AND CURSO_ID_PRE = ? ";
			Object[] parametros = new Object[] {cursoId, cursoIdPre};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public int getNumPre( String planId) {
	
		int nPrePlan =0;
		
		try{
			String comando = "SELECT COUNT(CURSO_ID) AS PRE FROM ENOC.MAPA_CURSO_PRE "+ 
				"WHERE SUBSTR(CURSO_ID,1,8) = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				nPrePlan = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}				
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|getNumPre|:"+ex);
	
		}
		
		return nPrePlan;
	}
		
	public List<MapaCursoPre> getLista( String planId, String orden ) {
		
		List<MapaCursoPre> lista		= new ArrayList<MapaCursoPre>();		
		try{
			String comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE"
					+ " WHERE ENOC.CURSO_PLAN(CURSO_ID) = ? "+ orden;
			lista = enocJdbc.query(comando, new MapaCursoPreMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|getLista|:"+ex);
		}		
		return lista;
	}
			
	public List<MapaCursoPre> getListAll( String orden ) {
		
		List<MapaCursoPre> lista		= new ArrayList<MapaCursoPre>();
		
		try{
			String comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE "+ orden;
			lista = enocJdbc.query(comando, new MapaCursoPreMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	//listor que regresa los prerrequisitos de una materia
	
	public List<MapaCursoPre> getListPrerrequisioMateria( String cursoId, String orden ) {
		
		List<MapaCursoPre> lista		= new ArrayList<MapaCursoPre>();		
		try{
			String comando = "SELECT CURSO_ID, CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ? "+ orden; 
			lista = enocJdbc.query(comando, new MapaCursoPreMapper(), cursoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|getListPrerrequisitoMateria|:"+ex);
		}		
		return lista;
	}
	
	public List<String> lisPrerrequisitos(String cursoId){
		
		List<String> lista = new ArrayList<String>();
		try{			
			String query="SELECT CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			lista = enocJdbc.queryForList(query, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|lisPrerrequisitos|:"+ex);
		}
		return lista;
	}
	
	public String getPrerrequisitos( String cursoId) {
		
		String prerrequisitos	= "";		
		try{
			String comando = "SELECT CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ? ORDER BY 1  "; 
			Object[] parametros = new Object[] {cursoId};
			prerrequisitos = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|getPrerrequisitos|:"+ex);
		}
		
		return prerrequisitos;
	}	
	
	public HashMap<String, String> mapaPrePorPlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT SUBSTR(CURSO_ID,1,8) AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO_PRE GROUP BY SUBSTR(CURSO_ID,1,8)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoPreDao|mapaPrePorPlan|:"+ex);
		}
		return mapa;
	}
}