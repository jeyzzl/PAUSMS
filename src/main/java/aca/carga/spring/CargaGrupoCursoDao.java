// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.plan.spring.MapaCursoMapper;

@Component
public class CargaGrupoCursoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaGrupoCurso curso){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_CURSO "
					+ " (CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO) "
					+ " VALUES(?, ?, ?, ?)";
			Object[] parametros = new Object[] {curso.getCursoCargaId(),curso.getCursoId(),curso.getOrigen(), curso.getGrupoHorario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|insertReg|:"+ex);			
		
		}
		
		return ok;
	}	
	
	public boolean updateReg(CargaGrupoCurso curso ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_CURSO "
					+ " SET ORIGEN = ?, GRUPO_HORARIO = ? "
					+ " WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ?";
			Object[] parametros = new Object[] {curso.getOrigen(),curso.getGrupoHorario(),curso.getCursoCargaId(),curso.getCursoId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|updateReg|:"+ex);		 
		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(String cursoCargaId, String cursoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_CURSO "+ 
				"WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId,cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public boolean deleteGrupo(String cursoCargaId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|deleteGrupo|:"+ex);			
		
		}
		return ok;
	}
	
	public CargaGrupoCurso mapeaRegId( String cursoCargaId, String cursoId ){
		
		CargaGrupoCurso curso = new CargaGrupoCurso();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO"
					+ " FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE CURSO_CARGA_ID = ? AND TRIM(CURSO_ID) = TRIM(?)";
			
			Object[] parametros = new Object[] {cursoCargaId,cursoId};
			curso = enocJdbc.queryForObject(comando, new CargaGrupoCursoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|mapeaRegId|:"+ex);			
		}		
		return curso;
	}
	
	public boolean existeReg(String cursoCargaId, String cursoId){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ? AND TRIM(CURSO_ID) = TRIM(?)";
			Object[] parametros = new Object[] {cursoCargaId,cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|existeReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean existeReg(String cursoCargaId){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|existeReg|:"+ex);		
		}		
		return ok;
	}
	
	public String cursoIdOrigen(String cursoCargaId){
		
		String cursoId			= "";
		
		try{
			String comando = "SELECT COUNT(CURSO_ID) FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ORIGEN = 'O'";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO"
						+ " WHERE CURSO_CARGA_ID = ?"
						+ " AND ORIGEN = 'O'";
				cursoId = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoCurso|cursoIdOrigen|:"+ex);
		
		}
		
		return cursoId;
	}
	
		
	public ArrayList<CargaGrupoCurso> getListAll(String orden ){
		
		List<CargaGrupoCurso> lista		= new ArrayList<CargaGrupoCurso>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CURSO_ID, "
					+ " ORIGEN, GRUPO_HORARIO "
					+ " FROM ENOC.CARGA_GRUPO_CURSO "+ orden; 
			Object[] parametros = new Object[] {orden};	
			lista = enocJdbc.query(comando, new CargaGrupoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CursosUtil|getListAll|:"+ex);
		
		}
		
		return (ArrayList<CargaGrupoCurso>) lista;
	}
	
	public ArrayList<CargaGrupoCurso> getLista(String cursoCargaId, String orden ){
		
		List<CargaGrupoCurso> lista		= new ArrayList<CargaGrupoCurso>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CURSO_ID, "
					+ " ORIGEN, GRUPO_HORARIO "
					+ " FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE CURSO_CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId};	
			lista = enocJdbc.query(comando, new CargaGrupoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|getLista|:"+ex);
		
		}
		
		return (ArrayList<CargaGrupoCurso>) lista;
	}
	
	public ArrayList<CargaGrupoCurso> lisCursosEnCarga(String cargaId, String orden ){
		
		List<CargaGrupoCurso> lista		= new ArrayList<CargaGrupoCurso>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CURSO_ID, "
					+ " ORIGEN, GRUPO_HORARIO "
					+ " FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new CargaGrupoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|getLista|:"+ex);
		
		}
		
		return (ArrayList<CargaGrupoCurso>) lista;
	}

	
	public String obtenEstado(String cursoCargaId){		
		String estado="";		
		try{
			String comando = "SELECT ESTADO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				estado = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|obtenEstado|:"+ex);
		}		
		return estado;
	}
	
	public String getPlanes(String cursoCargaId){
		String planes 			= "X";
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando =  "SELECT CURSO_PLAN(CURSO_ID) AS PLAN FROM CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};	
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String plan : lista) {
				planes += ","+plan;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|getPlanes|:"+ex);		
		}		
		return planes;
	}
	
	public List<aca.Mapa> getPlanesAll(String cargaId, String orden){		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			//UNAV-- DISTINCT((SELECT PLAN_ID FROM MAPA_CURSO WHERE CURSO_ID = ENOC.CARGA_GRUPO_CURSO.CURSO_ID)) AS LLAVE, (SELECT CARRERA_ID FROM MAPA_PLAN WHERE PLAN_ID = (SELECT PLAN_ID FROM MAPA_CURSO WHERE CURSO_ID = ENOC.CARGA_GRUPO_CURSO.CURSO_ID)) AS VALOR
			String comando =  "SELECT DISTINCT(SUBSTR(CURSO_ID,1,8)) AS LLAVE, (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = SUBSTR(CURSO_ID,1,8)) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+orden;
			Object[] parametros = new Object[] {cargaId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|getPlanesAll|:"+ex);		
		}		
		return lista;
	}
	
	public ArrayList<CargaGrupoCurso> getMateriasSalon(String salonId, String orden ){
		
		List<CargaGrupoCurso> lista	= new ArrayList<CargaGrupoCurso>();		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN"
					+ " (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_HORA WHERE SALON_ID = ?) "
					+ "AND ORIGEN = 'O' "+ orden;
			Object[] parametros = new Object[] {salonId};	
			lista = enocJdbc.query(comando, new CargaGrupoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|getMateriasSalon|:"+ex);
		
		}
		
		return (ArrayList<CargaGrupoCurso>) lista;
	}
	
	public List<aca.Mapa> lisPlanesEnMaterias(String cargas, String orden ){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, SUBSTR(CURSO_ID,1,8) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+") GROUP BY CURSO_CARGA_ID, SUBSTR(CURSO_ID,1,8) "+ orden;				
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoCursoDao|lisPlanesEnMaterias|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapMateriasOrigenAlumno(String codigoPersonal ){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, CURSO_ID AS VALOR FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?)"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapMateriasOrigenAlumno|:"+ex);		
		}
		return mapa;
	}	
	
	public HashMap<String, String> mapMateriasDelGrupo(String cursoCargaId ){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT CURSO_ID AS LLAVE, CURSO_ID AS VALOR FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID = ?"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapMateriasDelGrupo|:"+ex);		
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCursosUsados(String planId ){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?) GROUP BY CURSO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapCursosUsados|:"+ex);		
		}
		return mapa;
	}	
	
	public HashMap<String, String> mapCursosPorCiclo(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
	
		try{			
			String comando = "SELECT CARRERA(CURSO_PLAN(CURSO_ID))||CICLO_CURSO(CURSO_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND ORIGEN = 'O'"
					+ " GROUP BY CARRERA(CURSO_PLAN(CURSO_ID))||CICLO_CURSO(CURSO_ID)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);				
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapCursosPorCiclo|:"+ex);
		
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaMateriasOrigen(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, CURSO_ID AS VALOR FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);				
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapaMateriasOrigen|:"+ex);		
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaMateriasPorPlan( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.CURSO_PLAN(CURSO_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND ORIGEN = 'O'"
					+ " GROUP BY ENOC.CURSO_PLAN(CURSO_ID)";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapa.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaMateriasPorPlan|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapCursosPorGrupo(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);				
			for (aca.Mapa map:lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapCursosPorGrupo|:"+ex);		
		}
		return mapa;
	}

	public HashMap<String, CargaGrupoCurso> mapaCursosOrigenPorCarga(String cargaId) {		
		HashMap<String, CargaGrupoCurso> lisCurso	= new HashMap<String,CargaGrupoCurso>();
		List<CargaGrupoCurso> lista			= new ArrayList<CargaGrupoCurso>();		
		try{
			String comando = " SELECT CURSO_CARGA_ID, CURSO_ID, ORIGEN, GRUPO_HORARIO"
			+ " FROM ENOC.CARGA_GRUPO_CURSO"
			+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND ORIGEN = 'O'" ;
			lista = enocJdbc.query(comando,new CargaGrupoCursoMapper(),cargaId);
			for(CargaGrupoCurso mapa : lista){				
				lisCurso.put(mapa.getCursoCargaId(), mapa);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaGrupoCursoDao|mapaCursosAlumnoEnTraspaso|:"+ex);
		}
			
		return lisCurso;
	}
	
}