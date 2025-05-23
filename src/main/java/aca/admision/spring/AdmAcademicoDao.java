package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmAcademicoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmAcademico admAcademico) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_ACADEMICO"+ 
				"(FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, FECHA, PLAN_ID, PERIODO_ID ) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'),TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {
				admAcademico.getFolio(),admAcademico.getModalidad(),admAcademico.getNivelId(),admAcademico.getCarreraId(),
				admAcademico.getFecha(), admAcademico.getPlanId(), admAcademico.getPeriodoId(),admAcademico.getTipo()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmAcademico admAcademico ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_ACADEMICO " + 
					" SET MODALIDAD_ID = TO_NUMBER(?,'99'), " +
					" NIVEL_ID = TO_NUMBER(?,'99'), " +
					" CARRERA_ID = ?, " +
					" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
					" TIPO = ? " +		
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {
				admAcademico.getModalidad(),admAcademico.getNivelId(),admAcademico.getCarreraId(),admAcademico.getFecha(),admAcademico.getTipo(),admAcademico.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_ACADEMICO "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmAcademico mapeaRegId(String folio ){
		AdmAcademico objeto = new AdmAcademico();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACADEMICO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PLAN_ID, PERIODO_ID, TIPO " +
						" FROM SALOMON.ADM_ACADEMICO "+ 
						" WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmAcademicoMapper(),parametros);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}

	public AdmAcademico mapeaRegIdPorMatriculaCarrera(String codigoAlumno, String carrera ){
		AdmAcademico objeto = new AdmAcademico();		
		try {			
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACADEMICO WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?) AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {codigoAlumno,carrera};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA " +
						" FROM SALOMON.ADM_ACADEMICO "+ 
						" WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?) AND CARRERA_ID = ? AND ROWNUM = 1";				
				objeto = enocJdbc.queryForObject(comando, new AdmAcademicoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|mapeaRegIdPorMatriculaCarrera|:"+ex);
		}
		
		return objeto;
	}
	
	public AdmAcademico mapeaRegIdPorMatricula(String codigoAlumno ){
		AdmAcademico objeto = new AdmAcademico();		
		try {			
			String comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM SALOMON.ADM_ACADEMICO"
					+ " WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?) AND ROWNUM = 1";			
			Object[] parametros = new Object[] {codigoAlumno};
			objeto = enocJdbc.queryForObject(comando, new AdmAcademicoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|mapeaRegIdPorMatricula|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg(String folio ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACADEMICO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|existeReg|:"+ex);
		}		
		return ok;
	}

	public boolean existeRegPorMatriculaCarrera(String codigoAlumno, String carrera ) {
		boolean ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACADEMICO WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?) AND CARRERA_ID = ?";			
			Object[] parametros = new Object[] {codigoAlumno,carrera};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|existeRegPorMatriculaCarrera|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeRegPorMatricula(String codigoAlumno ) {
		boolean ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACADEMICO WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?) AND ROWNUM = 1";
			Object[] parametros = new Object[] {codigoAlumno};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|existeRegPorMatricula|:"+ex);
		}		
		return ok;
	}
	
	public List<AdmAcademico> getAll(String orden) {
		List<AdmAcademico> lista	= new ArrayList<AdmAcademico>();		
		try{
			String comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID,CARRERA_ID, FECHA, PLAN_ID, PERIODO_ID, TIPO FROM SALOMON.ADM_ACADEMICO "+ orden;			
			lista = enocJdbc.query(comando, new AdmAcademicoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcademicoDao|getAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AdmAcademico> mapaTodos (){
		HashMap<String, AdmAcademico> mapa = new HashMap<String, AdmAcademico>();
		List<AdmAcademico> lista = new ArrayList<AdmAcademico>();		
		try {
			String comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PLAN_ID, PERIODO_ID, TIPO FROM SALOMON.ADM_ACADEMICO";
			lista = enocJdbc.query(comando, new AdmAcademicoMapper());
			for (AdmAcademico aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaTodos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmAcademico> mapaAlumnos (String condicion){
		HashMap<String, AdmAcademico> mapa = new HashMap<String, AdmAcademico>();
		List<AdmAcademico> lista = new ArrayList<AdmAcademico>();
		
		try {
			String comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PLAN_ID"
					+ " FROM SALOMON.ADM_ACADEMICO "+condicion;
			lista = enocJdbc.query(comando, new AdmAcademicoMapper());
			for (AdmAcademico aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnosSede|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmAcademico> mapaAlumnosSede (String eventoId){
		HashMap<String, AdmAcademico> mapa = new HashMap<String, AdmAcademico>();
		List<AdmAcademico> lista = new ArrayList<AdmAcademico>();
		
		try {
			String comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"					
					+ " FROM SALOMON.ADM_ACADEMICO "
					+ " WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AdmAcademicoMapper(),parametros);
			for (AdmAcademico aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnosSede|:"+ex);
		}
		return mapa;
	}

}
