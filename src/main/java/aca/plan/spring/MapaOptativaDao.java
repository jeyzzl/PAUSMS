package aca.plan.spring;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaOptativaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MapaOptativa mapaOptativa ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO MAPA_OPTATIVA"+
				"(CURSO_ID, OPTATIVA_ID ) VALUES( ?, ?)";	
			Object[] parametros = new Object[] {mapaOptativa.getCursoId(),mapaOptativa.getOptativaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|insertReg|:"+ex);			
		
		}
		
		return ok;
	}	
	
	public boolean updateReg(MapaOptativa mapaOptativa ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE MAPA_OPTATIVA " + 
					"SET OPTATIVA_ID = ?, WHERE CURSO_ID = ? ";
			Object[] parametros = new Object[] {mapaOptativa.getOptativaId(),mapaOptativa.getCursoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|updateReg|:"+ex);		
		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String cursoId, String optativaId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM MAPA_OPTATIVA " +
				"WHERE CURSO_ID = ? AND OPTATIVA_ID =? ";
			Object[] parametros = new Object[] {cursoId, optativaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public MapaOptativa mapeaRegId(String cursoId){
		
		MapaOptativa mapaOptativa = new MapaOptativa();
		try{
			String comando = "SELECT CURSO_ID, OPTATIVA_ID " +
				"FROM MAPA_OPTATIVA WHERE CURSO_ID = ? ";
			Object[] parametros = new Object[] {cursoId};
			mapaOptativa = enocJdbc.queryForObject(comando, new MapaOptativaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|mapeaRegId|:"+ex);
		
		}
		return mapaOptativa;
	}	
	

	public boolean existeReg( String cursoId, String optativaId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM MAPA_OPTATIVA WHERE CURSO_ID = ? AND OPTATIVA_ID= ?";
			Object[] parametros = new Object[] {cursoId, optativaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public List<MapaOptativa> getListAll( String orden ){
		
		List<MapaOptativa> lista		= new ArrayList<MapaOptativa>();
		
		try{
			String comando = "SELECT CURSO_ID, OPTATIVA_ID FROM MAPA_OPTATIVA "+orden;
			lista = enocJdbc.query(comando, new MapaOptativaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.OptativaDao|getListAll|:"+ex);
		
		}
		
		return lista;
	}
	
	public List<MapaOptativa> getLista( String cursoId, String orden ){
		
		List<MapaOptativa> lista	= new ArrayList<MapaOptativa>();
		
		try{
			String comando = "SELECT CURSO_ID, OPTATIVA_ID FROM MAPA_OPTATIVA "+
				"WHERE CURSO_ID = ? " +orden;
			lista = enocJdbc.query(comando, new MapaOptativaMapper(),cursoId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.OptativaUtil|getLista|:"+ex);
		
		}
		
		return lista;
	}
}