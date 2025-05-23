package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPruebaAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmPruebaAlumno admPruebaAlumno) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_PRUEBA_ALUMNO"+ 
				"(FOLIO, PRUEBA_ID, USUARIO, FECHA ) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?, SYSDATE)";			
			Object[] parametros = new Object[] {admPruebaAlumno.getFolio(),admPruebaAlumno.getPruebaId(),admPruebaAlumno.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(AdmPruebaAlumno admPruebaAlumno ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_PRUEBA_ALUMNO " + 
					" SET PRUEBA_ID = TO_NUMBER(?,'99'), " +
					" USUARIO = TO_NUMBER(?,'99'), " +					
					" FECHA = SYSDATE " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {
				admPruebaAlumno.getPruebaId(), admPruebaAlumno.getUsuario(), admPruebaAlumno.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_PRUEBA_ALUMNO WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmPruebaAlumno mapeaRegId(String folio ){
		AdmPruebaAlumno objeto = new AdmPruebaAlumno();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PRUEBA_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT FOLIO, PRUEBA_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA " +
						" FROM SALOMON.ADM_PRUEBA_ALUMNO "+ 
						" WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmPruebaAlumnoMapper(),parametros);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}	
	
	public boolean existeReg(String folio ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PRUEBA_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}	
	
	public List<AdmPruebaAlumno> lisTodos(String orden){
		List<AdmPruebaAlumno> lista	= new ArrayList<AdmPruebaAlumno>();
		try{
			String comando = "SELECT FOLIO, PRUEBA_ID, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM SALOMON.ADM_PRUEBA_ALUMNO "+ orden;
			lista = enocJdbc.query(comando, new AdmPruebaAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmPruebaAlumno> lisPorPrueba(String pruebaId, String orden){
		List<AdmPruebaAlumno> lista	= new ArrayList<AdmPruebaAlumno>();
		try{
			String comando = "SELECT FOLIO, PRUEBA_ID, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM SALOMON.ADM_PRUEBA_ALUMNO WHERE PRUEBA_ID = TO_NUMBER(?,'99') "+ orden;
			lista = enocJdbc.query(comando, new AdmPruebaAlumnoMapper(), pruebaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaAlumnoDao|lisPorPrueba|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AdmPruebaAlumno> mapaTodos (){
		HashMap<String, AdmPruebaAlumno> mapa = new HashMap<String, AdmPruebaAlumno>();
		List<AdmPruebaAlumno> lista = new ArrayList<AdmPruebaAlumno>();		
		try {
			String comando = "SELECT FOLIO, PRUEBA_ID, USUARIO, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA FROM SALOMON.ADM_PRUEBA_ALUMNO";
			lista = enocJdbc.query(comando, new AdmPruebaAlumnoMapper());
			for (AdmPruebaAlumno aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaTodos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPruebas(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, (SELECT NOMBRE FROM SALOMON.ADM_PRUEBA WHERE PRUEBA_ID = SALOMON.ADM_PRUEBA_ALUMNO.PRUEBA_ID) AS VALOR FROM SALOMON.ADM_PRUEBA_ALUMNO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaPruebas|:"+ex);
		}
		return mapa;
	}
}
