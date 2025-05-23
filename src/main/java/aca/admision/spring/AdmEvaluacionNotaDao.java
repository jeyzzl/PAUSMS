package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmEvaluacionNotaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmEvaluacionNota objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_EVALUACION_NOTA(EVALUACION_ID, FOLIO, NOTA, FECHA, USUARIO, COMENTARIO)"
					+ "VALUES(TO_NUMBER(?,'99'),?,?,NOW(),?, ?)";
			Object[] parametros = new Object[] {objeto.getEvaluacionId(), objeto.getFolio(),objeto.getNota(), objeto.getUsuario(), objeto.getComentario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(AdmEvaluacionNota objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_EVALUACION_NOTA SET NOTA = ?, FECHA = NOw(), USUARIO = ?, COMENTARIO = ? WHERE EVALUACION_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {objeto.getNota(), objeto.getUsuario(), objeto.getComentario(), objeto.getEvaluacionId(), objeto.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg(String evaluacionId, String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_EVALUACION_NOTA WHERE EVALUACION_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {evaluacionId, folio};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public AdmEvaluacionNota mapeaRegId(String evaluacionId, String folio) {
		AdmEvaluacionNota objeto = new AdmEvaluacionNota();		
		try{
			String comando = "SELECT EVALUACION_ID, FOLIO, NOTA, FECHA, USUARIO, COMENTARIO FROM SALOMON.ADM_EVALUACION_NOTA WHERE EVALUACION_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] { evaluacionId,folio};
			objeto = enocJdbc.queryForObject(comando, new AdmEvaluacionNotaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg( String evaluacionId, String folio) {
		boolean 		ok 	= false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_EVALUACION_NOTA WHERE EVALUACION_ID = TO_NUMBER(?,'99') AND FOLIO = TO_NUMBER(?,'9999999')"; 
			Object[] parametros = new Object[] {evaluacionId, folio};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmEvaluacionNota> getListAll (){
		List<AdmEvaluacionNota> lista = new ArrayList<AdmEvaluacionNota>();
		
		try {
			String comando = "SELECT EVALUACION_ID, FOLIO, NOTA, FECHA, USUARIO, COMENTARIO FROM SALOMON.ADM_EVALUACION_NOTA";			
			lista = enocJdbc.query(comando, new AdmEvaluacionNotaMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AdmEvaluacionNota> listEvaluaciones (String folio){
		List<AdmEvaluacionNota> lista = new ArrayList<AdmEvaluacionNota>();		
		try {
			String comando = "SELECT EVALUACION_ID, FOLIO, NOTA, TO_CHAR(FECHA,'YYYY-MM-DD HH24:MI:SS') AS FECHA, USUARIO, COMENTARIO"
				+ " FROM SALOMON.ADM_EVALUACION_NOTA WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmEvaluacionNotaMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|listEvaluaciones|:"+ex);
		}
		return lista;
	}
	
	public List<AdmEvaluacionNota> getListReprobadas (String folio){
		List<AdmEvaluacionNota> lista = new ArrayList<AdmEvaluacionNota>();		
		try {
			String comando = "SELECT * FROM SALOMON.ADM_EVALUACION_NOTA "
					+ " WHERE NOTA NOT LIKE '%A%' "
					+ " AND TO_NUMBER(TRIM(NOTA),'9999.99') < (SELECT PUNTOS FROM SALOMON.ADM_EVALUACION "
					+ " WHERE EVALUACION_ID = SALOMON.ADM_EVALUACION_NOTA.EVALUACION_ID)"
					+ " AND FOLIO = TO_NUMBER(?,'9999999') ";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmEvaluacionNotaMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmEvaluacionNotaDao|getListReprobadas|:"+ex);
		}
		return lista;
	}

	public HashMap<String,String> mapaResultados(){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_EVALUACION_NOTA GROUP BY FOLIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaResultados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaResultadosExamenes(){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO||EVALUACION_ID AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_EVALUACION_NOTA GROUP BY FOLIO||EVALUACION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaResultadosExamenes|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaNotaResultadosExamenes(){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO||EVALUACION_ID AS LLAVE, NOTA AS VALOR FROM SALOMON.ADM_EVALUACION_NOTA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaNotaResultadosExamenes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AdmEvaluacionNota> mapaNotaResultados(){
		
		HashMap<String, AdmEvaluacionNota> mapa = new HashMap<String, AdmEvaluacionNota>();
		List<AdmEvaluacionNota> lista = new ArrayList<AdmEvaluacionNota>();		
		try {
			String comando = "SELECT EVALUACION_ID, FOLIO, NOTA, FECHA, USUARIO, COMENTARIO FROM SALOMON.ADM_EVALUACION_NOTA";
			lista = enocJdbc.query(comando, new AdmEvaluacionNotaMapper());
			for (AdmEvaluacionNota nota : lista ){
				mapa.put(nota.getFolio()+nota.getEvaluacionId(), nota);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaNotaResultados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AdmEvaluacionNota> mapaNotaResultados(String folio){
		
		HashMap<String, AdmEvaluacionNota> mapa = new HashMap<String, AdmEvaluacionNota>();
		List<AdmEvaluacionNota> lista 			= new ArrayList<AdmEvaluacionNota>();		
		try {
			String comando = "SELECT EVALUACION_ID, FOLIO, NOTA, FECHA, USUARIO, COMENTARIO FROM SALOMON.ADM_EVALUACION_NOTA WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmEvaluacionNotaMapper(), parametros);
			for (AdmEvaluacionNota nota : lista ){
				mapa.put(nota.getFolio()+nota.getEvaluacionId(), nota);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaNotaResultados|:"+ex);
		}
		return mapa;
	}
	
}
