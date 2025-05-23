// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EmpPeriodoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( EmpPeriodo periodo ) {		
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_PERIODO"+ 
				"(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "+
				"VALUES( TO_NUMBER(?,'999'), ?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'),?)";
			Object[] parametros = new Object[] {
					periodo.getPeriodoId(),periodo.getPeriodoNombre(),periodo.getFechaIni(),periodo.getFechaFin(),periodo.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( EmpPeriodo periodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_PERIODO"
				+ " SET PERIODO_NOMBRE = ?,"
				+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
				+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"
				+ " ESTADO = ?"				
				+ " WHERE PERIODO_ID = ? ";
			Object[] parametros = new Object[] {
					periodo.getPeriodoNombre(),periodo.getFechaIni(),periodo.getFechaFin(),periodo.getEstado(),periodo.getPeriodoId()				
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean deleteReg( String periodoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	
	public EmpPeriodo mapeaRegId(  String periodoId ) {
		
		EmpPeriodo periodo = new EmpPeriodo();
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
					+ " FROM ENOC.EMP_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {periodoId};
			periodo = enocJdbc.queryForObject(comando, new EmpPeriodoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|mapeaRegId|:"+ex);
		}
		
		return periodo;
	}
	
	public boolean existeReg( String periodoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean periodoActivo( String fecha) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_PERIODO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
			Object[] parametros = new Object[] {fecha};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|periodoActivo|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {		
		
		int  maximo 		= 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) FROM ENOC.EMP_PERIODO";
			maximo = enocJdbc.queryForObject(comando,Integer.class);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|maximoCurso|:"+ex);
		}
		
		return String.valueOf(maximo);
	}	
	
	public String getPeriodoNombre( String periodoId ) {
				
		String  nombre	 		= "VACIO";
		
		try{
			String comando = "SELECT PERIODO_NOMBRE FROM ENOC.EMP_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {periodoId};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|getPeriodoNombre|:"+ex);		
		}
		
		return nombre;
	}	
	
	public List<EmpPeriodo> listAll( String orden ) {
		
		List<EmpPeriodo> lista	= new ArrayList<EmpPeriodo>();	
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
					+ " FROM ENOC.EMP_PERIODO "+orden; 
			lista = enocJdbc.query(comando, new EmpPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpPeriodo> listActivas( String orden ) {
		
		List<EmpPeriodo> lista	= new ArrayList<EmpPeriodo>();	
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
					+ " FROM ENOC.EMP_PERIODO"
					+ " WHERE ESTADO = 'A' "+orden;
			lista = enocJdbc.query(comando, new EmpPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpPeriodoDao|listActivas|:"+ex);
		}
		
		return lista;
	}	
}