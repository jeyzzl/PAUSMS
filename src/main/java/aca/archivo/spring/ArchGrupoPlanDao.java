//Clase  para la tabla ARCH_GRUPOS	
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchGrupoPlanDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchGrupoPlan grupo){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ARCH_GRUPO_PLAN(GRUPO_ID, PLAN_ID, USUARIO) VALUES(TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] {grupo.getGrupoId(),grupo.getPlanId(), grupo.getUsuario() };
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|insertReg|:"+ex);
		}
		return ok;
	}

	public boolean updateReg( ArchGrupoPlan grupo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ARCH_GRUPO_PLAN SET USUARIO = ? WHERE GRUPO_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";	
			Object[] parametros = new Object[] {grupo.getUsuario(), grupo.getGrupoId(), grupo.getPlanId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|updateReg|:"+ex);
		}
		return ok;
	}

	public boolean deleteReg( String grupoId, String planId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {grupoId, planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|deleteReg|:"+ex);
		}
		return ok;
	}

	public ArchGrupoPlan mapeaRegId( String grupoId, String planId) {
		ArchGrupoPlan archGrup = new ArchGrupoPlan();				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {grupoId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT GRUPO_ID, PLAN_ID, USUARIO FROM ENOC.ARCH_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
				archGrup = enocJdbc.queryForObject(comando, new ArchGrupoPlanMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|mapeaRegId|:"+ex);
		}
		return archGrup;
	}

	public boolean existeReg( String grupoId, String planId ) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {grupoId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<String> gruposDelPlan(String planId) {
		List<String> lista 	= new ArrayList<String>();	
		try{
			String comando = "SELECT GRUPO_ID FROM ENOC.ARCH_GRUPO_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.queryForList(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|gruposDelPlan|:"+ex);
		}
		return lista;
	}
	
	public List<ArchGrupoPlan> listTodos( String orden ) {
		List<ArchGrupoPlan> lista 	= new ArrayList<ArchGrupoPlan>();
		try{
			String comando = "SELECT GRUPO_ID, PLAN_ID, USUARIO FROM ENOC.ARCH_GRUPO_PLAN "+orden;
			lista = enocJdbc.query(comando, new ArchGrupoPlanMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|lisTodos|:"+ex);
		}
		return lista;
	}	

	public List<ArchGrupoPlan> lisPorGrupoId(String grupoId, String orden ) {
		List<ArchGrupoPlan> lista 	= new ArrayList<ArchGrupoPlan>();
		try{
			String comando = "SELECT GRUPO_ID, PLAN_ID, USUARIO FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99') "+orden;
			lista = enocJdbc.query(comando, new ArchGrupoPlanMapper(), new Object[] {grupoId});
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|lisPorGrupoId|:"+ex);
		}
		return lista;
	}	

	public HashMap<String,String> mapaPlanesFacultad(String grupoId) {
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = "SELECT CAT_CARRERA.FACULTAD_ID AS LLAVE, COUNT(MAPA_PLAN.PLAN_ID) AS VALOR"
					+ " FROM CAT_CARRERA"
					+ " INNER JOIN MAPA_PLAN ON CAT_CARRERA.CARRERA_ID = MAPA_PLAN.CARRERA_ID"
					+ " AND MAPA_PLAN.PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99'))"
					+ " GROUP BY FACULTAD_ID ORDER BY FACULTAD_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {grupoId});
			
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|mapaPlanesFacultad|:"+ex);
		}
		return mapa;
	}	

	public HashMap<String,String> mapaPlanesGrupo(String grupoId) {
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ARCH_GRUPO_PLAN WHERE GRUPO_ID = TO_NUMBER(?,'99')";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {grupoId});
			
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|mapaPlanesGrupo|:"+ex);
		}
		return mapa;
	}	

	public HashMap<String,String> mapaPlanesPorGrupo() {
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, COUNT(GRUPO_ID) AS VALOR FROM ENOC.ARCH_GRUPO_PLAN GROUP BY GRUPO_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGrupoPlanDao|mapaPlanesPorGrupo|:"+ex);
		}
		return mapa;
	}	

}