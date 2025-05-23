/**
 * 
 */
package aca.bec.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class BecPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecPeriodo becPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PERIODO"
					+ " (PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO, FECHAS_PREPA, FECHAS_UNIVERSIDAD, FECHAS_PERIODO)"
					+ " VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'),TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?,?)";
			
			Object[] parametros = new Object[] {becPeriodo.getPeriodoId(),becPeriodo.getPeriodoNombre(),becPeriodo.getFechaIni(),
				becPeriodo.getFechaFin(),becPeriodo.getEstado(),becPeriodo.getIdEjercicio(),becPeriodo.getTipo(),
				becPeriodo.getFechasPrepa(),becPeriodo.getFechasUniversidad(),becPeriodo.getFechasPeriodo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|insertReg|:"+ex);			
		}
		return ok;
	} 
	public boolean updateReg( BecPeriodo becPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_PERIODO SET PERIODO_NOMBRE = ?,"
					+ " FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'), FECHA_fIN = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ESTADO = ?, ID_EJERCICIO = ?, TIPO = ?, FECHAS_PREPA = ?, FECHAS_UNIVERSIDAD = ?, FECHAS_PERIODO = ?"
					+ " WHERE PERIODO_ID = ? ";			
			Object[] parametros = new Object[] {becPeriodo.getPeriodoNombre(),becPeriodo.getFechaIni(),becPeriodo.getFechaFin(),
				becPeriodo.getEstado(),becPeriodo.getIdEjercicio(),becPeriodo.getTipo(),becPeriodo.getFechasPrepa(),becPeriodo.getFechasUniversidad(),
				becPeriodo.getFechasPeriodo(),becPeriodo.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg( String periodoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	

	public BecPeriodo mapeaRegId(  String periodoId ) {
		BecPeriodo becPeriodo = new BecPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO, FECHAS_PREPA, FECHAS_UNIVERSIDAD, FECHAS_PERIODO "+
				" FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ? "; 
			Object[] parametros = new Object[] {periodoId};
			becPeriodo = enocJdbc.queryForObject(comando, new BecPeriodoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|mapeaRegId|:"+ex);
		}
		return becPeriodo;
	}
	
	public boolean existeReg( String periodoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getPeriodoActual(){
		String	periodo			= "";	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A'";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				comando = "SELECT PERIODO_ID FROM ENOC.BEC_PERIODO WHERE now() >= FECHA_INI AND now() <= FECHA_FIN AND ESTADO = 'A'";
				periodo = enocJdbc.queryForObject(comando,String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|getPeriodoActual|:"+ex);
		}		
		return periodo;
	}
	
	public String getPeriodoNombre(String periodoId) {
		String	periodo			= "";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT PERIODO_NOMBRE FROM ENOC.BEC_PERIODO WHERE PERIODO_ID = ?";				
				periodo = enocJdbc.queryForObject(comando,String.class,parametros);	
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|getPeriodoNombre|:"+ex);
		}		
		return periodo;
	}
	
	public String getPeriodoDeFecha( String fecha, String ejercicioId) {
		String	periodo			= "";		
		try{
			String comando = "SELECT PERIODO_ID FROM ENOC.BEC_PERIODO"
					+ " WHERE TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " AND ID_EJERCICIO = ? ";						
			Object[] parametros = new Object[] {fecha, ejercicioId};
			periodo = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|getPeriodoDeFecha|:"+ex);
		}		
		return periodo;
	}
	
	public List<BecPeriodo> getAll( String orden) {
		List<BecPeriodo> lista	= new ArrayList<BecPeriodo>();	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO, FECHAS_PREPA, FECHAS_UNIVERSIDAD, FECHAS_PERIODO"
					+ " FROM ENOC.BEC_PERIODO "+ orden;			
			lista = enocJdbc.query(comando, new BecPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|getAll|:"+ex);
		}
		return lista;
	}
	
	public List<BecPeriodo> getAllActivos( String ejercicioId, String orden) {
		List<BecPeriodo> lista	= new ArrayList<BecPeriodo>();	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO, FECHAS_PREPA, FECHAS_UNIVERSIDAD, FECHAS_PERIODO"
					+ " FROM ENOC.BEC_PERIODO WHERE ESTADO = 'A' AND ID_EJERCICIO = ? "+ orden;
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new BecPeriodoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|getAllActivos|:"+ex);
		}
		return lista;
	}
	
	public List<BecPeriodo> lisVigentes( String orden) {
		List<BecPeriodo> lista	= new ArrayList<BecPeriodo>();	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO, ID_EJERCICIO, TIPO, FECHAS_PREPA, FECHAS_UNIVERSIDAD, FECHAS_PERIODO"
					+ " FROM ENOC.BEC_PERIODO WHERE ENOC.BEC_PERIODO.FECHA_FIN >= TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') "+ orden;			
			lista = enocJdbc.query(comando, new BecPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPeriodoDao|lisVigentes|:"+ex);
		}
		return lista;
	}
	
}