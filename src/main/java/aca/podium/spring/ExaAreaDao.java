package aca.podium.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaAreaDao {
	
	@Autowired
	@Qualifier("jdbcExa")
	private JdbcTemplate jdbcExamen;

	public List<ExaArea> lisAreasPorCarreraId(int carreraId) {
		List<ExaArea> lista = new ArrayList<ExaArea>();				
		try {
			String query = "SELECT * FROM EXA.AREA WHERE ID IN(SELECT AREA_ID FROM EXA.CARRERA_AREA WHERE CARRERA_ID = ? ORDER BY AREA_ID) ORDER BY ID";	
			lista = jdbcExamen.query(query,  new ExaAreaMapper(), carreraId);
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AreaDao|lisAreasPorCarreraId| "+e);
		}		
		return lista;
	}

	public ExaArea buscaAreaPorId(int id) {
		ExaArea objeto = new ExaArea();		
		try {
			String query = "SELECT * FROM EXA.AREA WHERE ID = ?";
			objeto = jdbcExamen.queryForObject(query, new ExaAreaMapper(), id);
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AreaDao|buscarAreaPorId| "+e);
		}
	
		return objeto;
	}
	
	public boolean existeArea(int areaId) {
		boolean ok = false;		
		try {
			String query = "SELECT COUNT(*) FROM EXA.AREA WHERE ID = ?";			
			if(jdbcExamen.queryForObject(query, Integer.class, areaId) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AreaDao|existeArea| "+e);
		}		
		return ok;
	}
	
	public HashMap<Integer,ExaArea> mapaArea(){
		HashMap<Integer,ExaArea> mapa = new HashMap<Integer,ExaArea>();
		List<ExaArea> lista	= new ArrayList<ExaArea>();		
		try {
			String query = "SELECT * FROM EXA.AREA";			
			lista = jdbcExamen.query(query, new ExaAreaMapper());
			for(ExaArea area : lista) {
				mapa.put(area.getId(), area);
			}			
		} catch (Exception e) {
			System.out.println("Error - aca.podium.spring.AreaDao|mapaArea| "+e);
		}		
		return mapa;
	}

}
