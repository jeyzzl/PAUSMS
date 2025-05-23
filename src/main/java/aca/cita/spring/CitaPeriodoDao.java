/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.cita.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CitaPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CitaPeriodo objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CITA_PERIODO(EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO)"
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
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( CitaPeriodo objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CITA_PERIODO"
					+ " SET HORA_INICIO = TO_NUMBER(?,'99'), MIN_INICIO = TO_NUMBER(?,'99'),"
					+ " HORA_FIN = TO_NUMBER(?,'99'), MIN_FIN = TO_NUMBER(?,'99'),"
					+ " CUPO = TO_NUMBER(?,'9999')"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'99') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {objeto.getHoraInicio(), objeto.getMinInicio(), 
					objeto.getHoraFin(), objeto.getMinFin(), 
					objeto.getCupo(), objeto.getEventoId(), objeto.getDia(), objeto.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String eventoId, String dia, String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CITA_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'99') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {eventoId, dia, periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CitaPeriodo mapeaRegId( String eventoId, String dia, String periodoId) {
		CitaPeriodo objeto = new CitaPeriodo();
		
		try{
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID, HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM ENOC.CITA_PERIODO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'99') AND PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {dia, periodoId};
			objeto = enocJdbc.queryForObject(comando, new CitaPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return objeto;
	}
	
	public boolean existeReg( String eventoId, String dia, String periodoId) {
		boolean ok = false;			
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CITA_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'99') AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {eventoId, dia, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int getCupo( String eventoId, String dia, String periodoId) {
		int cupo = 0;	
		
		try{
			String comando = "SELECT COALESCE(CUPO,0) FROM ENOC.CITA_PERIODO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND DIA = TO_NUMBER(?,'99') AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {eventoId, dia, periodoId};
			cupo = enocJdbc.queryForObject(comando,Integer.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|existeReg|:"+ex);
		}
		
		return cupo;
	}
	
	public List<CitaPeriodo> getListAll ( String orden){
		List<CitaPeriodo> lista = new ArrayList<CitaPeriodo>();
		
		try {
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID,"
					+ " HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM ENOC.CITA_PERIODO "+ orden;
			
			lista = enocJdbc.query(comando, new CitaPeriodoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CitaPeriodo> getListEvento (String eventoId, String orden){
		List<CitaPeriodo> lista = new ArrayList<CitaPeriodo>();
		
		try {
			String comando = "SELECT EVENTO_ID, DIA, PERIODO_ID,"
					+ " HORA_INICIO, MIN_INICIO, HORA_FIN, MIN_FIN, CUPO"
					+ " FROM ENOC.CITA_PERIODO WHERE EVENTO_ID = ? "+ orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new CitaPeriodoMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|getListEvento|:"+ex);
		}
		return lista;
	}
	
	public int getMaxPeriodo (String eventoId){
		int maximo = 0;
		
		try {
			String comando = "SELECT MAX(PERIODO_ID) FROM ENOC.CITA_PERIODO WHERE EVENTO_ID = ?";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) > 0){
				maximo = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}	
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|getListEvento|:"+ex);
		}
		return maximo;
	}
	
	public HashMap<String, String> mapaCupo (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT EVENTO_ID||DIA||PERIODO_ID AS LLAVE, CUPO AS VALOR FROM ENOC.CITA_PERIODO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.cita.spring.CitaPeriodoDao|mapaCupo|:"+ex);
		}
		return mapa;
	}
	
}