package aca.log.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( LogAlumno logAlumno ) {
		boolean ok = false;				
		try{
			String comando = "INSERT INTO ENOC.LOG_ALUMNO(ID, TABLA, OPERACION, IP, FECHA, USUARIO, DATOS)" +
			" VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?, now(), ?, ?)";			
			Object[] parametros = new Object[] {logAlumno.getId(),logAlumno.getTabla(),logAlumno.getOperacion(),logAlumno.getIp(),logAlumno.getUsuario(),logAlumno.getDatos()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public LogAlumno mapeaRegId( String id ) {		
		LogAlumno logAlumno = new LogAlumno();		
		try{
			String comando = "SELECT ID, TABLA, OPERACION, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO, IP, DATOS"
					+ " FROM LOG_ALUMNO"
					+ " WHERE ID = ?";
			Object[] parametros = new Object[] {id};
			logAlumno = enocJdbc.queryForObject(comando, new LogAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|mapeaRegId|:"+ex);
		}		
		return logAlumno;
	}
	
	public String maximoReg() {
		int maximo 			= 1;	
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.LOG_ALUMNO";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|maximoReg|:"+ex);
		}
		return String.valueOf(maximo);		
	}
	
	public String maximoId( String tabla, String codigoAlumno){
		int maximo 	= 0;		
		try{
			String comando = "SELECT COALESCE(MAX(ID),0) AS MAXIMO FROM ENOC.LOG_ALUMNO WHERE TABLA = ? AND DATOS = ?";
			Object[] parametros = new Object[] {tabla,codigoAlumno};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|maximoId|:"+ex);
		}
		return String.valueOf(maximo);		
	}	
	
	public List<LogAlumno> getListAll( String matricula) {		
		List<LogAlumno> lista	= new ArrayList<LogAlumno>();		
		try{
			String comando = " SELECT ID, TABLA, OPERACION, FECHA, USUARIO, IP, DATOS FROM ENOC.LOG_ALUMNO WHERE DATOS LIKE '%"+matricula+"%'";		
			lista = enocJdbc.query(comando, new LogAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisActualizadosPorFecha( String fechaIni, String fechaFin, String tablas) {		
		List<String> lista		= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT (DATOS) AS CODIGO_PERSONAL FROM LOG_ALUMNO WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') AND TABLA IN ("+tablas+")";
			lista = enocJdbc.queryForList(comando, String.class, fechaIni, fechaFin );			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogAlumnoDao|lisActualizadosPorFecha|:"+ex);
		}
		return lista;
	}	
}