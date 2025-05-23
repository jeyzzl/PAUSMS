/**
 * 
 */
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author jazer
 *
 */
@Component
public class MapaNuevoCursoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MapaNuevoCurso mapaNuevoCurso ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_CURSO"+ 
				"(PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, HSS, HAS, PROYECTO) "+
				"VALUES( ?, TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), ?,"+
				" ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), ?," +
				" ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99'), TO_NUMBER(?, '9999'), TO_NUMBER(?, '99.9999'), ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, TO_NUMBER(?, '9')," +
				" ?, ?, TO_NUMBER(?, '9999'),TO_NUMBER(?, '9999'), ?)";	
			Object[] parametros = new Object[] {
					mapaNuevoCurso.getPlanId(), mapaNuevoCurso.getCursoId(), mapaNuevoCurso.getVersionId(), 
					mapaNuevoCurso.getClave(), mapaNuevoCurso.getNombre(), mapaNuevoCurso.getCiclo(), mapaNuevoCurso.getFCreada(),
					mapaNuevoCurso.getCodigoMaestro(), mapaNuevoCurso.getUbicacion(), mapaNuevoCurso.getSeriacion(), mapaNuevoCurso.getHst(),
					mapaNuevoCurso.getHsp(), mapaNuevoCurso.getThs(), mapaNuevoCurso.getHt(), mapaNuevoCurso.getCreditos(), mapaNuevoCurso.getDescripcion(),
					mapaNuevoCurso.getBibliografia(), mapaNuevoCurso.getCompetencia(), mapaNuevoCurso.getMediosRecursos(), mapaNuevoCurso.getEeDiagnostica(),
					mapaNuevoCurso.getEeFormativa(), mapaNuevoCurso.getEeSumativa(), mapaNuevoCurso.getEscala(), mapaNuevoCurso.getEstado(),
					mapaNuevoCurso.getHei(), mapaNuevoCurso.getHfd(), mapaNuevoCurso.getHss(), mapaNuevoCurso.getHas(), mapaNuevoCurso.getProyecto()
					};
					if (enocJdbc.update(comando,parametros)==1){
						ok = true;
					}
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|insertReg|:"+ex);			
		}		
		
		return ok;
	}	
	
	public boolean updateReg(MapaNuevoCurso mapaNuevoCurso ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_CURSO" + 
					" SET CLAVE = ?," +
					" NOMBRE = ?, " +
					" CICLO = TO_NUMBER(?, '99'), " +
					" F_CREADA = TO_DATE(?, 'DD/MM/YYYY'), " +
					" CODIGO_MAESTRO = ?," +
					" UBICACION = ?," +
					" SERIACION = ?," +
					" HST = TO_NUMBER(?, '99')," +
					" HSP = TO_NUMBER(?, '99')," +
					" THS = TO_NUMBER(?, '99')," +
					" HT = TO_NUMBER(?, '9999')," +
					" CREDITOS = TO_NUMBER(?, '99.9999')," +
					" DESCRIPCION = ?," +
					" BIBLIOGRAFIA = ?," +
					" COMPETENCIA = ?," +
					" MEDIOS_RECURSOS = ?," +
					" EE_DIAGNOSTICA = ?," +
					" EE_FORMATIVA = ?," +
					" EE_SUMATIVA = ?," +
					" ESCALA = ?," +
					" ESTADO = TO_NUMBER(?, '9')," +
					" HEI = ?," +
					" HFD = ?," +
					" HSS = TO_NUMBER(?,'9999'),"+
					" HAS = TO_NUMBER(?,'9999'),"+
					" PROYECTO = ? "+
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {
					mapaNuevoCurso.getClave(), mapaNuevoCurso.getNombre(), mapaNuevoCurso.getCiclo(), 
					mapaNuevoCurso.getFCreada(), mapaNuevoCurso.getCodigoMaestro(), mapaNuevoCurso.getUbicacion(), mapaNuevoCurso.getSeriacion(),
					mapaNuevoCurso.getHst(), mapaNuevoCurso.getHsp(), mapaNuevoCurso.getThs(), mapaNuevoCurso.getHt(),
					mapaNuevoCurso.getCreditos(), mapaNuevoCurso.getDescripcion(), mapaNuevoCurso.getBibliografia(), mapaNuevoCurso.getCompetencia(), mapaNuevoCurso.getMediosRecursos(),
					mapaNuevoCurso.getEeDiagnostica(), mapaNuevoCurso.getEeFormativa(), mapaNuevoCurso.getEeSumativa(), mapaNuevoCurso.getEscala(),
					mapaNuevoCurso.getEstado(), mapaNuevoCurso.getHei(), mapaNuevoCurso.getHfd(), mapaNuevoCurso.getHss(),
					mapaNuevoCurso.getHas(), mapaNuevoCurso.getProyecto(), mapaNuevoCurso.getCursoId(), mapaNuevoCurso.getPlanId(), mapaNuevoCurso.getVersionId()
					};
					if (enocJdbc.update(comando,parametros)==1){
						ok = true;
					}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|updateReg|:"+ex);		
		}		
		
		return ok;
	}
	
	public boolean deleteReg(String cursoId, String planId, String versionId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE CURSO_ID = ?" +
				" AND PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, planId, versionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public MapaNuevoCurso mapeaRegId( String cursoId, String planId, String versionId){
		
		MapaNuevoCurso objeto = new MapaNuevoCurso();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
					" NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO," +
					" UBICACION, SERIACION, HST, HSP," +
					" THS, HT, CREDITOS, DESCRIPCION," +
					" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
					" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
					" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
					" FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, planId, versionId};
			objeto = enocJdbc.queryForObject(comando, new MapaNuevoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public MapaNuevoCurso mapeaRegId( String cursoId){
		
		MapaNuevoCurso objeto = new MapaNuevoCurso();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
					" NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO," +
					" UBICACION, SERIACION, HST, HSP," +
					" THS, HT, CREDITOS, DESCRIPCION," +
					" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
					" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
					" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
					" FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')";					
			Object[] parametros = new Object[] {cursoId};
			objeto = enocJdbc.queryForObject(comando, new MapaNuevoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String cursoId, String planId, String versionId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CURSO_ID = ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, planId, versionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|existeReg|:"+ex);
		}		
		
		return ok;
	}
	
	public boolean existeReg(String cursoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|existeReg|:"+ex);
		}		
		
		return ok;
	}

	public boolean existeClave(String clave, String planId, String versionId, String cursoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE CLAVE LIKE ?" +
					" AND PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND CURSO_ID != TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {clave, cursoId, planId, versionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|existeClave|:"+ex);
		}		
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo		 	= "1";
		
		try{
			String comando = "SELECT MAX(CURSO_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_CURSO"; 
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|maximoReg|:"+ex);
		}		
		
		return maximo;
	}
	
	public String porcentajeEnEstado(String planId, String versionId, String estado){
		String porcentaje	 	= "Invalido";
		int cantidad			= 0;
		int total				= 0;
		
		try{
			String comando = "SELECT COUNT(NOMBRE) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND ESTADO = TO_NUMBER(?, '9')";
			Object[] parametros = new Object[] {planId, versionId, estado};
			porcentaje = enocJdbc.queryForObject(comando, String.class, parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|porcentajeEnEstado(Primer query)|:"+ex);

		}
		
		try{
			String comando = "SELECT COUNT(NOMBRE) AS TOTAL FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {planId, versionId};
			porcentaje = enocJdbc.queryForObject(comando, String.class, parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|porcentajeEnEstado(Segundo query)|:"+ex);
	
		}
		if(cantidad == 0){
			porcentaje = "0";
		}else if(total == 0){
			porcentaje = "Invalido";
		}else{
			porcentaje = String.valueOf((cantidad/(float)total*100));
			if(porcentaje.indexOf(".") != -1){
				porcentaje = porcentaje.substring(0, porcentaje.indexOf(".")+2);
			}
		}		
		
		return porcentaje;
	}
	
	public String getMateriasTotales(String planId, String versionId){
		String cantidad			= "X";
		
		try{
			String comando = "SELECT COUNT(CURSO_ID) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {planId, versionId};
			cantidad = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getMateriasTotales|:"+ex);
		}		
		
		return cantidad;
	}
	
	public String getMateriasConUnidades(String planId, String versionId){
		String cantidad			= "X";
		
		try{
			String comando = "SELECT COUNT(CURSO_ID) AS CANTIDAD FROM ENOC.MAPA_NUEVO_CURSO" + 
					" WHERE PLAN_ID = ?" +
					" AND VERSION_ID = TO_NUMBER(?, '999')" +
					" AND (SELECT COUNT(UNIDAD_ID) FROM ENOC.MAPA_NUEVO_UNIDAD" + 
						" WHERE CURSO_ID = ENOC.MAPA_NUEVO_CURSO.CURSO_ID) > 0";
			Object[] parametros = new Object[] {planId, versionId};
			cantidad = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getMateriasConUnidades|:"+ex);
		}	
		
		return cantidad;
	}
	
	public int getHorasTotales(int cursoId ){
		
		int hT = 0; 
		try{
			String comando = "SELECT HT FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"; 
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				hT = enocJdbc.queryForObject(comando, Integer.class);
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getHorasTotales|:"+ex);
		}		
		
		return hT;
	}
	
	public int getHfd(int cursoId ){
		
		int hfd = 0; 
		try{
			String comando = "SELECT HFD FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"; 
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				hfd = enocJdbc.queryForObject(comando, Integer.class);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getHfd|:"+ex);
		}		
		
		return hfd;
	}
	
	public int getIdioma(int cursoId ){
		
		int idioma = 0; 
		try{
			String comando = "SELECT IDIOMA FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"; 
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				idioma = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getIdioma|:"+ex);
		}		
		
		return idioma;
	}
	
	public String getNuevoCursoId(String cursoId, String version ){
		
		String curso = "0"; 
		try{
			String comando = "SELECT CURSO_ID FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID||TRIM(CLAVE) = ? AND VERSION_ID = ?"; 
			Object[] parametros = new Object[] {cursoId, version};
			curso = enocJdbc.queryForObject(comando, String.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getNuevoCursoId|:"+ex);
		}		
		
		return curso;
	}
	
	public String getMaxVersionCurso(String cursoId ){
		
		String curso = "0"; 
		try{
			String comando = "SELECT COALESCE(MAX(VERSION_ID),1) AS MAXVER FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID||TRIM(CLAVE) = ?";
			Object[] parametros = new Object[] {cursoId};
			curso = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getMaxVersionCurso|:"+ex);
		}		
		
		return curso;
	}
	
	public String getMaestros(String cursoId ){
		
		String maestros = ""; 
		try{
			String comando = "SELECT CODIGO_MAESTRO FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = ?"; 
			Object[] parametros = new Object[] {cursoId};
			maestros = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getMaestros|:"+ex);
		}		
		
		return maestros;
	}
	
	public List<MapaNuevoCurso> getListPlan(String planId, String versionId, String orden){
		
		List<MapaNuevoCurso> lista		= new ArrayList<MapaNuevoCurso>();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
				" FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(? , '999') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoCursoMapper(),planId,versionId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getListPlan|:"+ex);
		}		
		return lista;	
	}
	
	public List<MapaNuevoCurso> getListPlanMaestro(String planId, String versionId, String codigoMaestro, String orden){
		
		List<MapaNuevoCurso> lista		= new ArrayList<MapaNuevoCurso>();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE," +
				" NOMBRE, CICLO, F_CREADA, CODIGO_MAESTRO," +
				" UBICACION, SERIACION, HST, HSP," +
				" THS, HT, CREDITOS, DESCRIPCION," +
				" BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA," +
				" EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO," +
				" HEI, HFD, IDIOMA, HSS, HAS, PROYECTO" +
				" FROM ENOC.MAPA_NUEVO_CURSO" + 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = ?" +
				" AND CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%' "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoCursoMapper(),planId,versionId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getListPlanMaestro|:"+ex);
		}		
		
		return lista;	
	}
	
	public String getNombre(String cursoId ){
		
		String curso = "0"; 
		try{
			String comando = "SELECT NOMBRE FROM ENOC.MAPA_NUEVO_CURSO WHERE CURSO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {cursoId};
			curso = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getNombre|:"+ex);
		}		
		
		return curso;
	}
	
	public String cortaFrase( String frase, int limite ){
		frase = frase.replaceAll("\n", " ");
		String nuevaFrase = "";
		
		if (frase.length() > limite){
			String[] arreglo = frase.split(" ");
			
			if (limite == 61) System.out.println("Frase:"+frase);
			boolean inicio = true;
			int largo = 0;
			for (int z=0; z < arreglo.length; z++){
				largo = largo + arreglo[z].length();
				if (largo < limite){					
					if (arreglo[z].equals("-")){
						if (largo > arreglo[z].length() || inicio == false)
							nuevaFrase = nuevaFrase + "\n" + arreglo[z];
						else
							nuevaFrase = nuevaFrase + " " + arreglo[z];
						largo = 0;
					}else{
						nuevaFrase = nuevaFrase + " " + arreglo[z];
					}
				}else{		
					nuevaFrase = nuevaFrase + "\n" + arreglo[z];
					largo = 0;
				}
				if (limite == 61) System.out.println("Largo:"+largo+":"+arreglo[z]);
				inicio = false;
			}
		}else{
			nuevaFrase = frase;
		}
		if (limite == 61) System.out.println("Frase:"+nuevaFrase);
		return nuevaFrase;
	}
	
	public List<MapaNuevoCurso> listaSinCursoNuevo(String planId, String planOrigen, String orden ) {
		List<MapaNuevoCurso> lista	= new ArrayList<MapaNuevoCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE, NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO,"
					+ " UBICACION, SERIACION, HST, HSP, THS, HT, CREDITOS, DESCRIPCION, BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA,"
					+ " EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO, HEI, HFD, IDIOMA, HSS, HAS, PROYECTO"
					+ " FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID = ? AND CURSO_ID NOT IN(SELECT CURSO_NUEVO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?) "+orden;			
			Object[] parametros = new Object[] {planOrigen,planId};	
			lista = enocJdbc.query(comando, new MapaNuevoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|listaSinCursoNuevo|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, MapaNuevoCurso> mapaCursos(String planId) {
		
		HashMap<String, MapaNuevoCurso> mapa		= new HashMap<String, MapaNuevoCurso>();
		List<MapaNuevoCurso> lista		= new ArrayList<MapaNuevoCurso>();
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE, NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO,"
					+ " UBICACION, SERIACION, HST, HSP, THS, HT, CREDITOS, DESCRIPCION, BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA,"
					+ " EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO, HEI, HFD, IDIOMA, HSS, HAS, PROYECTO"
					+ " FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			lista =  enocJdbc.query(comando, new MapaNuevoCursoMapper(), parametros);
			for (MapaNuevoCurso curso : lista) {
				mapa.put(curso.getPlanId()+curso.getClave(), curso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapaCursos|:"+ex);
		}
		
		return mapa;	
	}	
	
	public HashMap<String, MapaNuevoCurso> mapaCursosNuevos(String planId) {
		
		HashMap<String, MapaNuevoCurso> mapa		= new HashMap<String, MapaNuevoCurso>();
		List<MapaNuevoCurso> lista					= new ArrayList<MapaNuevoCurso>();
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE, NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO,"
					+ " UBICACION, SERIACION, HST, HSP, THS, HT, CREDITOS, DESCRIPCION, BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA,"
					+ " EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO, HEI, HFD, IDIOMA, HSS, HAS, PROYECTO"
					+ " FROM ENOC.MAPA_NUEVO_CURSO"
					+ " WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			lista =  enocJdbc.query(comando, new MapaNuevoCursoMapper(), parametros);
			for (MapaNuevoCurso curso : lista) {
				mapa.put(curso.getCursoId(), curso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapaCursosNuevos|:"+ex);
		}
		
		return mapa;	
	}	
	
	public HashMap<String, MapaNuevoCurso> mapaCursosNuevos(String planId, String planOrigen) {		
		HashMap<String, MapaNuevoCurso> mapa		= new HashMap<String, MapaNuevoCurso>();
		List<MapaNuevoCurso> lista					= new ArrayList<MapaNuevoCurso>();
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, VERSION_ID, CLAVE, NOMBRE, CICLO, TO_CHAR(F_CREADA, 'DD/MM/YYYY') AS F_CREADA, CODIGO_MAESTRO,"
					+ " UBICACION, SERIACION, HST, HSP, THS, HT, CREDITOS, DESCRIPCION, BIBLIOGRAFIA, COMPETENCIA, MEDIOS_RECURSOS, EE_DIAGNOSTICA,"
					+ " EE_FORMATIVA, EE_SUMATIVA, ESCALA, ESTADO, HEI, HFD, IDIOMA, HSS, HAS, PROYECTO"
					+ " FROM ENOC.MAPA_NUEVO_CURSO"
					+ " WHERE PLAN_ID = ? OR PLAN_ID = ?";
			Object[] parametros = new Object[] {planId, planOrigen};
			lista =  enocJdbc.query(comando, new MapaNuevoCursoMapper(), parametros);
			for (MapaNuevoCurso curso : lista) {
				mapa.put(curso.getCursoId(), curso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapaCursosNuevos|:"+ex);
		}
		
		return mapa;	
	}
	
	public HashMap<String,String> getPorcentajePlan(){
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		try {
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM MAPA_NUEVO_CURSO GROUP BY PLAN_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}	 
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getPorcentajePlan|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> getPorcentajeEstado(){
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		try {
			String comando = "SELECT PLAN_ID||ESTADO AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM MAPA_NUEVO_CURSO GROUP BY PLAN_ID||ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}	 
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|getPorcentajeEstado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasTotales(){
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		try {
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_NUEVO_CURSO GROUP BY PLAN_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}	 
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapaMateriasTotales|:"+ex);
		}
		
		return mapa;
	}
	
public HashMap<String,String> mapaMateriasConUnidades(){
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();	
		try {
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_NUEVO_CURSO "
					+ "WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM ENOC.MAPA_NUEVO_UNIDAD) "
					+ "GROUP BY PLAN_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}	 
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoCursoDao|mapaMateriasConUnidades|:"+ex);
		}
		
		return mapa;
	}
	
}