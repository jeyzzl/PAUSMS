package aca.apFisica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;




@Component
public class ApFisicaAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ApFisicaAlumno apFAlumno ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO" +
					" ENOC.APFISICA_ALUMNO(GRUPO_ID, CODIGO_PERSONAL, FECHA, ESTADO, CURSO_CARGA_ID)" +
					" VALUES(TO_NUMBER(?,'9999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
			Object[] parametros = new Object[] {apFAlumno.getGrupoId(), apFAlumno.getCodigoPersonal(), apFAlumno.getFecha(),apFAlumno.getEstado(), apFAlumno.getCursoCargaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public ApFisicaAlumno mapeaRegId(String codigoPersonal, String grupoId){
		ApFisicaAlumno apFAlumno = new ApFisicaAlumno();
		try{
			String comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO, CURSO_CARGA_ID, CARRERA_ID"
					+ " FROM ENOC.APFISICA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND GRUPO_ID = ?";			
			apFAlumno = enocJdbc.queryForObject(comando, new ApFisicaAlumnoMapper(), codigoPersonal, grupoId);			
		}catch(Exception ex){
 			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|mapeaRegId|:"+ex);
 		}
		return apFAlumno;
	}
	
	public boolean deleteAlumno(String grupoId, String codigoPersonal){
	
		boolean ok 				= false;		
		try{
			String comando ="DELETE FROM ENOC.APFISICA_ALUMNO WHERE GRUPO_ID = TO_NUMBER(?, '9999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {grupoId, codigoPersonal};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|deleteAlumno|:"+ex);
		}
		return ok;
	}
	
	public int registrados(String grupoId){
		int folio				= 0;		
		try{
			String comando = "SELECT COALESCE(COUNT(GRUPO_ID),0) AS MAXIMO FROM ENOC.APFISICA_ALUMNO WHERE GRUPO_ID = ? AND ESTADO = 'A'"; 
			Object[] parametros = new Object[] {grupoId};
			folio = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|registrados|:"+ex);
		}		
		return folio;
	}
	
	public String grupoAlumnoActivo(String matricula, String clave){
		String grupo = "0";		
		try{
			String comando = "SELECT GRUPO_ID FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A' AND GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO WHERE CLAVE = ?)";			
			grupo = enocJdbc.queryForObject(comando, String.class, matricula, clave);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|grupoAlumnoActivo|:"+ex);
		}		
		return grupo;
	}
	
	public String grupoDelAlumno(String matricula, String cursoCargaId){
		String grupo = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] {matricula, cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				comando = "SELECT GRUPO_ID FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ? AND ESTADO = 'A'";
				grupo = enocJdbc.queryForObject(comando, String.class, parametros);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|grupoDelAlumno|:"+ex);
		}		
		return grupo;
	}
	
	public boolean existeReg(String codigoPersonal, String grupoId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND GRUPO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[] {codigoPersonal,grupoId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg(ApFisicaAlumno apFAlumno){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.APFISICA_ALUMNO SET FECHA = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?, CURSO_CARGA_ID = ? WHERE GRUPO_ID = TO_NUMBER(?,'9999') AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {apFAlumno.getFecha(),apFAlumno.getEstado(),apFAlumno.getCursoCargaId(), apFAlumno.getGrupoId(),apFAlumno.getCodigoPersonal()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumno|updateReg|: "+ex);
		}
		return ok;
	}
	
	public boolean updateQuitarGrupo(String codigoPersonal, String clave ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.APFISICA_ALUMNO SET ESTADO = 'I' WHERE CODIGO_PERSONAL = ? AND GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO WHERE CLAVE = ?)";			
			Object[] parametros = new Object[] {codigoPersonal,clave};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|updateQuitarGrupo|: "+ex);
		}
		return ok;
	}
	
	public boolean tieneGrupo(String codigoPersonal, String cursoCargaId) {
		boolean ok = false;		
		try{
			String comando = "SELECT * FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|tieneGrupo|:"+ex);
		}
		
		return ok;
	}
	
	public List<ApFisicaAlumno> alumPorMateria(String clave, String fecha, String orden){
		
		List<ApFisicaAlumno> lista 	= new ArrayList<ApFisicaAlumno>();		
		try{
			String comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, FECHA, ESTADO, CURSO_CARGA_ID, CARRERA_ID FROM ENOC.APFISICA_ALUMNO"
					+ " WHERE GRUPO_ID IN "
					+ "		(SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO "
					+ "		WHERE CLAVE = ? AND F_INICIO <= TO_DATE(?, 'DD/MM/YYYY') "
					+ "		AND F_FINAL >= TO_DATE(?, 'DD/MM/YYYY')"
					+ "		) "
					+ " AND ESTADO = 'A'" +orden;
			Object[] parametros = new Object[] {clave, fecha, fecha};
			lista = enocJdbc.query(comando, new ApFisicaAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaAlumnoUtil|alumPorMateria|:"+ex);
		}
		
		return lista;
	}

	public List<ApFisicaAlumno> alumSinRegistro(String cargaId, String clave, String modalidades, String orden ) {
		
		List<ApFisicaAlumno> lisAcceso 	= new ArrayList<ApFisicaAlumno>();
		
		try{
			String comando = "SELECT ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE, CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = ?"
					+ " AND TIPOCAL_ID IN ('I','1','2','4','5')"
					+ " AND CURSO_ID LIKE '%"+clave+"%'"
					+ " AND CODIGO_PERSONAL||SUBSTR(CURSO_ID,9,7) NOT IN"
					+ " (SELECT CODIGO_PERSONAL||(SELECT CLAVE FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = APFISICA_ALUMNO.GRUPO_ID) FROM ENOC.APFISICA_ALUMNO WHERE ESTADO = 'A')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")" + orden;
			lisAcceso = enocJdbc.query(comando, new ApFisicaAlumnoMapper(), cargaId);			
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|alumSinRegistro|:"+ex);
		}
		
		return lisAcceso;
	}
	
	public List<String> lisAmigos(String grupoId, String orden){
		List<String> lista		= new ArrayList<String>();	
		try{
			String comando = " SELECT AP.APELLIDO_PATERNO ||' '|| AP.APELLIDO_MATERNO||' '|| AP.NOMBRE AS NOMBRE"
					+ " FROM ENOC.APFISICA_ALUMNO AF, ENOC.ALUM_PERSONAL AP"
					+ " WHERE AP.CODIGO_PERSONAL = AF.CODIGO_PERSONAL"
					+ " AND AF.GRUPO_ID = ? AND AF.ESTADO = 'A' "+orden;
			Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|lisAmigos|:"+ex);
		}		
		return lista;
	}
	
	public List<aca.Mapa> lisAmigosDelGrupo(String grupoId, String orden){
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT AP.CODIGO_PERSONAL AS LLAVE, AP.APELLIDO_PATERNO ||' '|| AP.APELLIDO_MATERNO||' '|| AP.NOMBRE AS VALOR"
					+ " FROM ENOC.APFISICA_ALUMNO AF, ENOC.ALUM_PERSONAL AP"
					+ " WHERE AP.CODIGO_PERSONAL = AF.CODIGO_PERSONAL"
					+ " AND AF.GRUPO_ID = ? AND AF.ESTADO = 'A' "+orden;
			Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|lisAmigosDelGrupo|:"+ex);
		}		
		return lista;
	}

	public List<ApFisicaAlumno> getListAlum(String estado, String grupoId, String orden ) {
		
		List<ApFisicaAlumno> lisAcceso 	= new ArrayList<ApFisicaAlumno>();
		String comando	    = "";
		
		try{
			comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, CURSO_CARGA_ID, CARRERA_ID FROM ENOC.APFISICA_ALUMNO WHERE ESTADO = ? AND  GRUPO_ID = ?"+orden;
			
			Object[] parametros = new Object[] {estado, grupoId};
			lisAcceso = enocJdbc.query(comando, new ApFisicaAlumnoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaAlumnoDao|getListAll|:"+ex);
		}
		
		return lisAcceso;
	}
	
	public HashMap<String, String> mapTieneGrupoEnCarga(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, GRUPO_ID AS VALOR"
				+ " FROM ENOC.APFISICA_ALUMNO "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal};		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapTieneGrupoEnCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaGruposDelAlumno(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, GRUPO_ID AS VALOR"
				+ " FROM ENOC.APFISICA_ALUMNO "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal};		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapTieneGrupoEnCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaRegistrados() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, COUNT(GRUPO_ID) AS VALOR FROM ENOC.APFISICA_ALUMNO WHERE ESTADO = 'A' GROUP BY GRUPO_ID";					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaRegistrados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCarrerasDeAlumnos( String clave, String fecha) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ALUMNO_CURSO"
					+ " WHERE SUBSTR(CURSO_ID,9,7) = ? "
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			Object[] parametros = new Object[] {clave, fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.AlumEstadoDao|mapaCarrerasDeAlumnos|:"+ex);
		}
		
		return mapa;
	}
	
	public List<ApFisicaAlumno> getPorMatricula(String codigoPersonal){		
		List<ApFisicaAlumno> lista		= new ArrayList<ApFisicaAlumno>();	
		try{
			String comando = "SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , ESTADO, CURSO_CARGA_ID, CARRERA_ID FROM ENOC.APFISICA_ALUMNO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ApFisicaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|getPorMatricula|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,ApFisicaAlumno> mapaGrupoDelAlumno() {		
		HashMap<String,ApFisicaAlumno> mapa = new HashMap<String,ApFisicaAlumno>();
		List<ApFisicaAlumno> lista = new ArrayList<ApFisicaAlumno>();		
		try{
			String comando = " SELECT GRUPO_ID, CODIGO_PERSONAL, FECHA, ESTADO, CURSO_CARGA_ID, CARRERA_ID FROM ENOC.APFISICA_ALUMNO";			
			lista = enocJdbc.query(comando, new ApFisicaAlumnoMapper());
			for(ApFisicaAlumno aptitud : lista){				
				mapa.put(aptitud.getCodigoPersonal()+aptitud.getCursoCargaId(), aptitud);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaGrupoDelAlumno|:"+ex);
		}		
		
		return mapa;
	}
}