// Clase Util para la tabla de Prerrequisitos
package aca.plan.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaCursoElectivaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaCursoElectiva mapaCursoElec ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_CURSO_ELEC"+ 
				"(CURSO_ID, FOLIO, CURSO_ELEC,CURSO_NOMBRE ) VALUES( ?, TO_NUMBER(?,'9999'), ?, ? )";
			Object[] parametros = new Object[] {mapaCursoElec.getCursoId(),
			mapaCursoElec.getFolio(), mapaCursoElec.getCursoElec(), mapaCursoElec.getCursoNombre()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|insertReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE  CURSO_ID=? AND FOLIO = TO_NUMBER(?,'9999') ";
			Object[] parametros = new Object[] {cursoId, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|deletetReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaCursoElectiva mapeaRegId(  String cursoId, String folio) {
		
		MapaCursoElectiva objeto = new MapaCursoElectiva();
		
		try{
			String comando = "SELECT CURSO_ID, FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID = ? AND FOLIO = TO_NUMBER(?,'9999') ";
			Object[] parametros = new Object[] {cursoId, folio};
			objeto = enocJdbc.queryForObject(comando, new MapaCursoElectivaMapper(), parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoId, String cursoElec) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID = ?  AND CURSO_ELEC = ?";
			Object[] parametros = new Object[] {cursoId, cursoElec};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg2( String cursoId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO_ELEC "+ 
				"WHERE CURSO_ID=? AND FOLIO = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {cursoId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoId ) {
		String maximo = "1";
		
		try{
			String comando = "SELECT MAX(FOLIO)+1 MAXIMO FROM ENOC.MAPA_CURSO_ELEC WHERE CURSO_ID = ? ";
			Object[] parametros = new Object[] {cursoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}else {
				maximo = "1";
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean updateReg( String cursoNombre, String cursoId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO_ELEC" +
					" SET CURSO_NOMBRE = ?" +
					" WHERE FOLIO = TO_NUMBER(?, '9999')" +
					" AND CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoNombre, folio, cursoId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|updateReg|:"+ex);		
		}
		return ok;
	}
		
	public List<MapaCursoElectiva> getLista( String planId, String orden ) {
		
		List<MapaCursoElectiva> lista = new ArrayList<MapaCursoElectiva>();		
		try{
			String comando = "SELECT CURSO_ID,FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
					"WHERE ENOC.CURSO_PLAN(CURSO_ID) = ? "+ orden;
			lista = enocJdbc.query(comando, new MapaCursoElectivaMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|getLista|:"+ex);
		}
		return lista;
	}
		
	public List<MapaCursoElectiva> getLista2( String cursoId, String orden ) {
		
		List<MapaCursoElectiva> lista = new ArrayList<MapaCursoElectiva>();
		
		try{
			String comando = "SELECT CURSO_ID,FOLIO, CURSO_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ 
					"WHERE CURSO_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoId};
			lista = enocJdbc.query(comando, new MapaCursoElectivaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|getLista2|:"+ex);
		}
		return lista;
	}

	public List<MapaCursoElectiva> getListAll( String orden ) {
		
		List<MapaCursoElectiva> lista = new ArrayList<MapaCursoElectiva>();
		
		try{
			String comando = "SELECT CURSO_ID,FOLIO, CURSO_ID_ELEC, CURSO_NOMBRE FROM ENOC.MAPA_CURSO_ELEC "+ orden; 
			lista = enocJdbc.query(comando, new MapaCursoElectivaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<String> getMaterias( String orden ) {
	
		List<String> lista				= new ArrayList<String>();
		
		try{
			String comando = "SELECT CURSO_ID FROM ENOC.MAPA_CURSO_ELEGIBLE WHERE ESTADO = 'A' "+ orden;
			lista = enocJdbc.queryForList(comando, String.class);

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoElectivaDao|getMaterias|:"+ex);
		}
		
		return lista;
	}
	
}