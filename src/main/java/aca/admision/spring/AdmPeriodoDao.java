/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmPeriodo objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_PERIODO(EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO)"
					+ " VALUES(TO_NUMBER(?,'99999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), "
					+ " TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),"
					+ " TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),"
					+ " TO_NUMBER(?,'9999'))";
			Object[] parametros = new Object[] {objeto.getEventoId(), objeto.getDia(), objeto.getPeriodoId(),
			objeto.getHoraInicio(), objeto.getMinInicio(), objeto.getHoraFin(), objeto.getMinFin(), objeto.getCupo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( AdmPeriodo objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_PERIODO"
				+ " SET DIA = TO_NUMBER(?,'99'),"
				+ " HORA_INICIO = TO_NUMBER(?,'99'),"
				+ " MIN_INICIO = TO_NUMBER(?,'99'),"
				+ " HORA_FIN = TO_NUMBER(?,'99'),"
				+ " MIN_FIN = TO_NUMBER(?,'99'),"
				+ " CUPO = TO_NUMBER(?,'9999')"
				+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {objeto.getDia(), objeto.getHoraInicio(), objeto.getMinInicio(), 
					objeto.getHoraFin(), objeto.getMinFin(), 
					objeto.getCupo(), objeto.getEventoId(), objeto.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|updateReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg( String eventoId, String periodoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId, periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmPeriodo mapeaRegId( String eventoId, String periodoId) {
		AdmPeriodo objeto = new AdmPeriodo();		
		try{
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM SALOMON.ADM_PERIODO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId, periodoId};
			objeto = enocJdbc.queryForObject(comando, new AdmPeriodoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}		
		return objeto;
	}

	public AdmPeriodo mapeaRegEventoId(String eventoId) {
		AdmPeriodo objeto = new AdmPeriodo();		
		try{
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM SALOMON.ADM_PERIODO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId};
			objeto = enocJdbc.queryForObject(comando, new AdmPeriodoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|mapeaRegEventoId|:"+ex);
			ex.printStackTrace();
		}		
		return objeto;
	}
	
	public boolean existeReg( String eventoId, String periodoId) {
		boolean ok = false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {eventoId, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegEvento( String eventoId) {
		boolean ok = false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|existeRegEvento|:"+ex);
		}
		
		return ok;
	}
	
	public int getCupo( String eventoId, String periodoId) {
		int cupo = 0;		
		try{
			String comando = "SELECT COALESCE(CUPO,0) FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {eventoId, periodoId};
			cupo = enocJdbc.queryForObject(comando,Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|existeReg|:"+ex);
		}		
		return cupo;
	}
	
	public List<AdmPeriodo> getListAll ( String orden){
		List<AdmPeriodo> lista = new ArrayList<AdmPeriodo>();		
		try {
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID,"
					+ " HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM SALOMON.ADM_PERIODO "+ orden;			
			lista = enocJdbc.query(comando, new AdmPeriodoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<AdmPeriodo> getListEvento (String eventoId, String orden){
		List<AdmPeriodo> lista = new ArrayList<AdmPeriodo>();
		
		try {
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID,"
					+ " HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = ? "+ orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AdmPeriodoMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|getListEvento|:"+ex);
		}
		return lista;
	}
	
	public int getMaxPeriodo (String eventoId){
		int maximo = 0;		
		try {
			String comando = "SELECT MAX(PERIODO_ID) FROM SALOMON.ADM_PERIODO WHERE EVENTO_ID = ?";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) > 0){
				maximo = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}	
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|getListEvento|:"+ex);
		}
		return maximo;
	}
	
	public HashMap<String, String> mapaCupo (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID AS LLAVE, CUPO AS VALOR FROM SALOMON.ADM_PERIODO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|mapaCupo|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, AdmPeriodo> mapaAdmPerido (){
		HashMap<String, AdmPeriodo> mapa = new HashMap<String, AdmPeriodo>();
		List<AdmPeriodo> lista = new ArrayList<AdmPeriodo>();		
		try {
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO FROM SALOMON.ADM_PERIODO";
			lista = enocJdbc.query(comando, new AdmPeriodoMapper());
			for (AdmPeriodo map : lista ){
				mapa.put(map.getEventoId(), map);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPeriodoDao|mapaAdmPerido|:"+ex);
		}
		return mapa;
	}
	
}