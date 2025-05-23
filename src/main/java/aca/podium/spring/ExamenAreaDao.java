package aca.podium.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExamenAreaDao {

	@Autowired
	@Qualifier("jdbcExa")
	private JdbcTemplate jdbcExamen;
	
	public List<ExamenArea> listaExamenArea(int examenId) {
		List<ExamenArea> lista = new ArrayList<ExamenArea>();
		String query = "SELECT * FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? ORDER BY AREA_ID";
		
		try {
			lista = jdbcExamen.query(query, new ExamenAreaMapper(), new Object[]{examenId});
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | listaExamenArea | "+e);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaNotas(int folio) {
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();			
		try {
			String query = "SELECT TRIM(TO_CHAR(EXAMEN_ID,'9999'))||'-'||TRIM(TO_CHAR(AREA_ID,'9999')) AS LLAVE, PUNTOS AS VALOR"
					+ " FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID IN (SELECT ID FROM EXA.EXAMEN WHERE FOLIO = ?)";
			lista = jdbcExamen.query(query, new aca.MapaMapper(), new Object[]{folio});
			for(aca.Mapa map :lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | lisPorFolio | "+e);
		}		
		return mapa;
	}
	
	public boolean existeExamenArea(int examenId) {
		boolean ok = false;
		String query = "SELECT COUNT(*) FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ?";
		
		try {
			if(jdbcExamen.queryForObject(query, Integer.class, new Object[]{examenId}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | existeExamenArea | "+e);
		}
		
		return ok;
	}

	public boolean creaExamenArea(int areaId, int examenId) {
		boolean ok = false;
		String query = "INSERT INTO EXA.EXAMEN_AREA(TERMINO,ACTIVO,AREA_ID,EXAMEN_ID)"
				+ " VALUES(FALSE,FALSE,?,?)";
		
		try {
			if(jdbcExamen.update(query, new Object[]{areaId,examenId}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | creaExamenArea | "+e);
		}
		
		return ok;
	}

	public boolean activarExamenArea(int examenId, int areaId) {
		boolean ok = false;
		String query = "UPDATE EXA.EXAMEN_AREA SET ACTIVO = TRUE WHERE EXAMEN_ID = ? AND AREA_ID = ?";
		
		try {
			if(jdbcExamen.update(query, new Object[]{examenId,areaId}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | activarExamenArea | "+e);
		}
		
		return ok;
	}

	public boolean terminarExamenArea(int examenId, int areaId) {
		boolean ok = false;
		String query = "UPDATE EXA.EXAMEN_AREA SET ACTIVO = FALSE, TERMINO = TRUE WHERE EXAMEN_ID = ? AND AREA_ID = ?";
		
		try {
			if(jdbcExamen.update(query, new Object[]{examenId,areaId}) >= 1){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | terminarExamenArea | "+e);
		}
		
		return ok;
	}
	
	public boolean examenAreaTerminadoCompletamente(int examenId) {
		boolean ok = false;
		String query = "SELECT COUNT(*) FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? AND TERMINO = FALSE";
		
		try {
			if(jdbcExamen.queryForObject(query, Integer.class, new Object[]{examenId}) == 0){
				ok = true;
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | ExamenAreaTerminadoCompletamente | "+e);
		}
		
		return ok;
	}

	public int examenAreaActiva(int examenId) {
		int dato = 0;
		String query = "SELECT COUNT(*) FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? AND ACTIVO = TRUE AND TERMINO = FALSE";		
		try {
			if(jdbcExamen.queryForObject(query, Integer.class, new Object[]{examenId}) >= 1){
				query = "SELECT AREA_ID FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? AND ACTIVO = TRUE AND TERMINO = FALSE";				
				dato = jdbcExamen.queryForObject(query, Integer.class, new Object[]{examenId});
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | examenAreaActiva | "+e);
		}
		
		return dato;
	}
	
	public float getPuntosPorArea(int examenId, String areas){
		float puntos 	= 0;				
		try {
			String query = "SELECT COUNT(*) FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? AND AREA_ID IN ("+areas+")";
			if(jdbcExamen.queryForObject(query, Integer.class, new Object[]{examenId}) >= 1){
				query = "SELECT COALESCE(SUM(PUNTOS),0) FROM EXA.EXAMEN_AREA WHERE EXAMEN_ID = ? AND AREA_ID IN ("+areas+")";				
				puntos = jdbcExamen.queryForObject(query, Float.class, new Object[]{examenId});
			}
		} catch (Exception e) {
			System.out.println("ERROR : ExamenAreaDao | getPuntosPorArea | "+e);
		}		
		return puntos;
	}		
}
