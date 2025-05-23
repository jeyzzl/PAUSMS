package aca.alerta.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlertaPeriodo alertaPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALERTA_PERIODO"
					+ " (PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, MODALIDADES, ESTADO, EXCEPTO)"
					+ " VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?)";
			
			Object[] parametros = new Object[] {alertaPeriodo.getPeriodoId(), alertaPeriodo.getPeriodoNombre(), alertaPeriodo.getFechaIni(),
					alertaPeriodo.getFechaFin(), alertaPeriodo.getModalidades(), alertaPeriodo.getEstado(), alertaPeriodo.getExcepto()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|insertReg|:"+ex);			
		}
		return ok;
	} 
	
	public boolean updateReg(AlertaPeriodo alertaPeriodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALERTA_PERIODO" 
					+ " SET"
					+ " PERIODO_NOMBRE = ?,"
					+ " FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_fIN = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " MODALIDADES = ?,"
					+ " ESTADO = ?,"
					+ " EXCEPTO = ?"
					+ " WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {alertaPeriodo.getPeriodoNombre(), alertaPeriodo.getFechaIni(), alertaPeriodo.getFechaFin(),
					alertaPeriodo.getModalidades(), alertaPeriodo.getEstado(), alertaPeriodo.getExcepto(), alertaPeriodo.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String periodoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALERTA_PERIODO "+ 
				"WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AlertaPeriodo mapeaRegId( String periodoId ) {
		AlertaPeriodo periodo = new AlertaPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = ?"; 
			
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new AlertaPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|mapeaRegId|:"+ex);
		}
		return periodo;
	}
	
	public AlertaPeriodo mapeaRegActivo( String periodoId ) {
		AlertaPeriodo periodo = new AlertaPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = ? AND ESTADO = 'A'"; 
			
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new AlertaPeriodoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|mapeaRegId|:"+ex);
		}
		return periodo;
	}
	
	public boolean existeReg(String periodoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_PERIODO"
					+ " WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getPeriodoActual() {
		String	periodo = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				comando = "SELECT PERIODO_ID FROM ENOC.ALERTA_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A'";
				periodo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|getPeriodoActual|:"+ex);
		}
		return periodo;
	}
	
	public String getPeriodoActivo() {
		String	periodo = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_PERIODO WHERE ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				comando = "SELECT PERIODO_ID FROM ENOC.ALERTA_PERIODO WHERE ESTADO = 'A' AND ROWNUM = 1";
				periodo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|getPeriodoActivo|:"+ex);
		}
		return periodo;
	}
	
	public String getPeriodoNombre(String periodoId) {
		String	tipo = ""; 		
		try{
			String comando = "SELECT PERIODO_NOMBRE FROM ENOC.ALERTA_PERIODO WHERE PERIODO_ID = ? ";
			tipo = enocJdbc.queryForObject(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|getPeriodoNombre|:"+ex);
		}
		return tipo;
	}
	
	public List<AlertaPeriodo> lisTodos(String orden) {
		List<AlertaPeriodo> lista = new ArrayList<AlertaPeriodo>();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO "+ orden;			
			lista = enocJdbc.query(comando, new AlertaPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|getAll|:"+ex);
		}
		return lista;
	}
	
	public List<AlertaPeriodo> getAllActivos(String orden) {
		List<AlertaPeriodo> lista	= new ArrayList<AlertaPeriodo>();
		String comando		= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN,"
					+ " MODALIDADES, ESTADO, EXCEPTO FROM ENOC.ALERTA_PERIODO WHERE ESTADO = 'A' "+ orden;
			
			lista = enocJdbc.query(comando, new AlertaPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaPeriodoDao|getAllActivos|:"+ex);
		}
		return lista;
	}
	
}