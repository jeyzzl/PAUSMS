//Clase para la tabla de Modulo
package aca.graduacion.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.exa.spring.ExaEgreso;
import aca.exa.spring.ExaEgresoDao;
import aca.util.Fecha;

@Component
public class AlumEgresoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private ExaEgresoDao exaEgresoDao;
	
	public boolean insertReg( AlumEgreso egreso ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.ALUM_EGRESO"+ 
 					" (EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA,PERMISO, CONFIRMAR, COMENTARIO)"+
 					" VALUES( ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999.99'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?)";
 			Object[] parametros = new Object[] {Integer.parseInt(egreso.getEventoId()), egreso.getCodigoPersonal(), egreso.getCarreraId(), egreso.getPlanId(), 
 				egreso.getAvance(), egreso.getTitulado(), egreso.getPromedio(), egreso.getFecha(), egreso.getUsuario(), egreso.getDiploma(), egreso.getPermiso(),
 				egreso.getConfirmar(), egreso.getComentario()
 			}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|insertReg|:"+ex);			
 		}
 		return ok;
 	}	
 	
 	public boolean updateReg( AlumEgreso egreso ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_EGRESO SET"
 					+ " EVENTO_ID = ?,"
 					+ " CODIGO_PERSONAL = ?,"
 					+ " CARRERA_ID = ?,"
 					+ " PLAN_ID = ?,"
 					+ " AVANCE = ?,"
 					+ " TITULADO = ?,"
 					+ " PROMEDIO = TO_NUMBER(?,'999.99'),"
 					+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
 					+ " USUARIO = ?,"
 					+ " DIPLOMA = ?,"
 					+ " PERMISO = ?,"
 					+ " CONFIRMAR = ?,"
 					+ " COMENTARIO = ?"
 					+ " WHERE EVENTO_ID = ?"
 					+ " AND CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {Integer.parseInt(egreso.getEventoId()), egreso.getCodigoPersonal(), egreso.getCarreraId(), egreso.getPlanId(),
 			egreso.getAvance(), egreso.getTitulado(), egreso.getPromedio(), egreso.getFecha(),egreso.getUsuario(), egreso.getDiploma(), egreso.getPermiso(),
 			egreso.getConfirmar(), egreso.getComentario(), egreso.getEventoId(), egreso.getCodigoPersonal()}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|updateReg|:"+ex);		
 		}
 		return ok;
 	}
 	
 	public boolean updatePromedio( String eventoId ) {
 		boolean ok = false; 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_EGRESO SET PROMEDIO = (SELECT PROMEDIO FROM ALUM_PLAN WHERE CODIGO_PERSONAL = ALUM_EGRESO.CODIGO_PERSONAL AND PLAN_ID = ALUM_EGRESO.PLAN_ID)"
 					+ " WHERE EVENTO_ID = ?";	
 			if (enocJdbc.update(comando, eventoId) >= 1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|updatepPromedio|:"+ex);
 		}
 		return ok;
 	}
 	
 	public boolean updateConfirmar(  String eventoId, String codigoAlumno, String confirmar ) {
 		boolean ok = false; 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_EGRESO SET CONFIRMAR = ? WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";	
 			if (enocJdbc.update(comando, confirmar, eventoId, codigoAlumno) >= 1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|updateConfirmar|:"+ex);
 		}
 		return ok;
 	}
 	 	
 	public boolean deleteReg( String eventoId, String codigoPersonal ) {
 		boolean ok = false; 		
 		try{
 			String comando = "DELETE FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public AlumEgreso mapeaRegId( String eventoId, String codigoPersonal ){
 		AlumEgreso objeto = new AlumEgreso(); 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EGRESO  WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {eventoId, codigoPersonal}; 			
	 		if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
	 			comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
		 				+ " FROM ENOC.ALUM_EGRESO "
		 				+ " WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";		 		
				objeto = enocJdbc.queryForObject(comando, new AlumEgresoMapper(),parametros);	 			
	 		}	 					
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapeaRegId|:"+ex);		
		}
 		return objeto;
 	}
 	
 	public AlumEgreso mapeaPorCodigoyPlan( String codigoPersonal, String planId ){	
 		AlumEgreso objeto = new AlumEgreso(); 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal, planId};
	 		if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	 			
	 			comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
	 				+ " FROM ENOC.ALUM_EGRESO "
	 				+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"
	 				+ " AND ROWNUM = 1";	 		
	 			objeto = enocJdbc.queryForObject(comando, new AlumEgresoMapper(),parametros);
	 		}						
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapeaPorCodigoyPlan|:"+ex);		
		}
 		return objeto;
 	}
 	
 	public AlumEgreso mapeaRegFecha( String codigoPersonal ) {
 		
 		AlumEgreso objeto = new AlumEgreso();
 		
 		try{
	 		String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE,"
	 				+ " TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
	 				+ " FROM ENOC.ALUM_EGRESO "
	 				+ " WHERE CODIGO_PERSONAL = ?"
	 				+ " AND FECHA = (SELECT MAX(FECHA) FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ?)";
	 		Object[] parametros = new Object[] {codigoPersonal, codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AlumEgresoMapper(),parametros);			
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapeaRegFecha|:"+ex);
			ex.printStackTrace();
		}
 		return objeto;
 	}
 	
 	public int getNumResidencia(String eventoId, String residencia ){
		int numAlumnos = 0;
		
		try{
			String comando = "SELECT COUNT(*) AS RESIDENCIA" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE RESIDENCIA_ID = ?)"; 
			
			Object[] parametros = new Object[] {eventoId, residencia};
			numAlumnos = enocJdbc.queryForObject(comando, Integer.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getNumResidencia|:"+ex);
		}
		
		return numAlumnos;
	}
 	
 	public int invitacionId(String codigoPersonal){
 		int numAlumnos = 0; 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EVENTO "
 					+ "WHERE ARCHIVO = 'S' AND EVENTO_ID IN(SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PERMISO = 'S')";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT EVENTO_ID FROM ENOC.ALUM_EVENTO WHERE ARCHIVO = 'S' AND EVENTO_ID IN(SELECT EVENTO_ID FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PERMISO = 'S' AND ROWNUM=1)"; 
 				numAlumnos = enocJdbc.queryForObject(comando, Integer.class,parametros);
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|invitacionId|:"+ex);
 		}
 		
 		return numAlumnos;
 	}
 	
 	public boolean existeReg( String eventoId, String codigoPersonal) {
 		boolean ok = false; 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|existeReg|:"+ex);
 		}
 		return ok;
 	}
 	
 	public boolean existeRegAlum( String codigoPersonal) {
 		boolean ok = false;
 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EGRESO"+ 
 					" WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|existeRegAlum|:"+ex);
 		}
 		return ok;
 	}
 	
 	public boolean existeRegAlum( String codigoPersonal, String planId) {
 		boolean ok = false; 		
 		try{
 			String comando = "SELECT COUNT(EVENTO_ID) FROM ENOC.ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|existeRegAlum|:"+ex);
 		}
 		return ok;
 	}
 
 	public boolean existePermisoAlum( String codigoPersonal, String permiso, String estado) {
 		boolean ok = false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ALUM_EGRESO WHERE CODIGO_PERSONAL = ? AND PERMISO = ? AND EVENTO_ID IN (SELECT EVENTO_ID FROM ALUM_EVENTO WHERE ESTADO = ?)";
 			Object[] parametros = new Object[] {codigoPersonal,permiso,estado};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				ok = true;
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|existePermisoAlum|:"+ex);
 		}
 		return ok;
 	}
 	
 	public int existenTraspasos( ){
 		int total = 0; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ALUM_EVENTO WHERE FECHA <= SYSDATE)"
 					+ " AND CODIGO_PERSONAL||PLAN_ID NOT IN (SELECT MATRICULA||PLAN_ID FROM ENOC.EXA_EGRESO)";
			total = enocJdbc.queryForObject(comando,Integer.class);
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|existenTraspasos|:"+ex);
 		}
 		return total;
 	}
 	
	public int traspasar() {
		int grabados = 0;
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID,AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
					+ " FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ALUM_EVENTO WHERE FECHA <= SYSDATE)"
					+ " AND CODIGO_PERSONAL||PLAN_ID NOT IN (SELECT MATRICULA||PLAN_ID FROM ENOC.EXA_EGRESO)";
			lista = enocJdbc.query(comando, new AlumEgresoMapper());	
			
			for(AlumEgreso alumno : lista) {
				ExaEgreso egreso = new ExaEgreso();

				String year = alumno.getFecha().substring(0, 4);

				egreso.setEgresoId(exaEgresoDao.maximoReg());
				egreso.setCarreraId(alumno.getCarreraId());
				egreso.setMatricula(alumno.getCodigoPersonal());
				egreso.setYear(year);
				egreso.setFechaAct(Fecha.getHoy());
				egreso.setEliminado("0");
				egreso.setIdEgresadoAno("X");
				egreso.setPlanId(alumno.getPlanId());
				//System.out.println("Datos:"+egreso.getMatricula()+":"+egreso.getPlanId()+":"+egreso.getCarreraId()+":"+egreso.getYear());
				
				if (exaEgresoDao.insertReg(egreso)) {
					grabados++;
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|traspasar|:"+ex);
		}
		
		return grabados;
	}
	
	public List<AlumEgreso> getListAll( String orden ) {
		
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
					+ " FROM ENOC.ALUM_EGRESO "+orden;
			lista = enocJdbc.query(comando, new AlumEgresoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEgreso> getListaEvento( String eventoId, String orden ){
		
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new AlumEgresoMapper(), eventoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getListaEvento|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEgreso> getListaEvento( String eventoId, String filtro,  String orden ){
		
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = ? "
					+ " AND CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ALUM_PLAN WHERE EVENTO IN ("+filtro+")) "+orden;
			lista = enocJdbc.query(comando, new AlumEgresoMapper(), eventoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getListaEvento|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEgreso> getListAlum( String codigoAlumno, String Orden ) {
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
				+ " FROM ENOC.ALUM_EGRESO"
				+ " WHERE CODIGO_PERSONAL = ? "+Orden;
			lista = enocJdbc.query(comando, new AlumEgresoMapper(), codigoAlumno);
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getListAlum|:"+ex);
		}
		return lista;
	}
	
	public List<AlumEgreso> lisEventosActivos( String codigoAlumno, String Orden ) {
		List<AlumEgreso> lista = new ArrayList<AlumEgreso>();		
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, CARRERA_ID, PLAN_ID, AVANCE, TITULADO, PROMEDIO, FECHA, USUARIO, DIPLOMA, PERMISO, CONFIRMAR, COMENTARIO"
				+ " FROM ENOC.ALUM_EGRESO"
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.ALUM_EVENTO WHERE ESTADO = 'A') "+Orden;
			lista = enocJdbc.query(comando, new AlumEgresoMapper(), codigoAlumno);
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|getListAlum|:"+ex);
		}
		return lista;
	}

	public HashMap<String, String> mapaTotAlumnos( ) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT EVENTO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_EGRESO GROUP BY EVENTO_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaTotAlumnos|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapaTotalAlumnosEnGraduacion( String eventoId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.CARRERA(PLAN_ID) AS LLAVE, COUNT(*) AS VALOR FROM ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999') GROUP BY ENOC.CARRERA(PLAN_ID)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaTotalAlumnosEnGraduacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaTotPorPaisEnGraduacion( String eventoId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.ALUM_PAIS(CODIGO_PERSONAL) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999')"
					+ " GROUP BY ENOC.ALUM_PAIS(CODIGO_PERSONAL)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaTotalAlumnosEnGraduacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaTotPorEstadoEnGraduacion( String eventoId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT ENOC.ALUM_PAIS(CODIGO_PERSONAL)||ENOC.ALUM_ESTADO_ID(CODIGO_PERSONAL) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999')"
					+ " GROUP BY ENOC.ALUM_PAIS(CODIGO_PERSONAL)||ENOC.ALUM_ESTADO_ID(CODIGO_PERSONAL)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaTotPorEstadoEnGraduacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaCarrerayGenero( String eventoId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CARRERA_ID||ENOC.ALUM_GENERO(CODIGO_PERSONAL) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_EGRESO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999')"
					+ " GROUP BY CARRERA_ID||ENOC.ALUM_GENERO(CODIGO_PERSONAL)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaCarrerayGenero|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaNivel( String eventoId ) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CARRERA_ID||ENOC.CARRERA_NIVEL(CARRERA_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_EGRESO "
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999')"
					+ " GROUP BY CARRERA_ID||ENOC.CARRERA_NIVEL(CARRERA_ID)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaNivel|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaEventoAlumno() {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, EVENTO_ID AS VALOR FROM ENOC.ALUM_EGRESO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaEventoAlumno|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumGraduados() {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.ALUM_EGRESO "
					+ " GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaAlumGraduados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaFechas(){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT EVENTO_ID AS LLAVE, TO_CHAR(FECHA,'DD/MM/YYYY') AS VALOR FROM ENOC.ALUM_EVENTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaFechas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaGraduadosEnMateria( String cursoCargaId){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_EGRESO"
					+ " WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||ENOC.PLAN(CURSO_ID) FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?)"
					+ " GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaGraduadosEnMateria|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,AlumEgreso> mapaAlumGraduadosPorEvento(String eventoId) {
		List<AlumEgreso> lista		= new ArrayList<AlumEgreso>();
		HashMap<String,AlumEgreso> mapa = new HashMap<String,AlumEgreso>();		
 		
 		try{
 			String comando = "SELECT EVENTO_ID,CODIGO_PERSONAL,CARRERA_ID,PLAN_ID,AVANCE,TITULADO,PROMEDIO,FECHA,USUARIO,DIPLOMA,PERMISO,CONFIRMAR,COMENTARIO"
 					+ " FROM ALUM_EGRESO WHERE EVENTO_ID = ?";
 			lista = enocJdbc.query(comando, new AlumEgresoMapper(),eventoId);
			for(AlumEgreso alumno : lista){
				mapa.put(alumno.getCodigoPersonal(),alumno);
			}	
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaAlumGraduadosPorEvento|:"+ex);
 		}
 		return mapa;
 	}	
	public HashMap<String,String> mapaNivelAlumGraduados(String eventoId) {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NIVEL_PLAN(PLAN_ID) AS VALOR FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),eventoId);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaNivelAlumGraduados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaGraduados() {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, EVENTO_ID AS VALOR FROM ENOC.ALUM_EGRESO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaGraduados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaGraduadosPorPlan() {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT PLAN_IDCODIGO_PERSONAL||PLAN_ID AS LLAVE, EVENTO_ID AS VALOR FROM ENOC.ALUM_EGRESO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.AlumEgresoDao|mapaGraduados|:"+ex);
		}		
		return mapa;
	}
}