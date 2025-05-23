package aca.maestro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MaestroRangoFechaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MaestroRangoFecha maestroRangoFecha ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAESTRO_RANGO_FECHA"
				+ "(YEAR,FECHA) "
 				+ "VALUES(TO_NUMBER(?,'9999'), ?)";
			
			Object[] parametros = new Object[] {
					maestroRangoFecha.getYear(),maestroRangoFecha.getFecha()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoFechaDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public MaestroRangoFecha mapeaRegId( String year) {
		MaestroRangoFecha maestroRangoFecha = new MaestroRangoFecha();		
		try{
			String comando = "SELECT YEAR,FECHA FROM ENOC.MAESTRO_RANGO_FECHA WHERE YEAR = TO_NUMBER(?,'9999')";
				Object[] parametros = new Object[] { year};
				maestroRangoFecha = enocJdbc.queryForObject(comando, new MaestroRangoFechaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoFechaDao|mapeaRegId|:"+ex);
		}
		return maestroRangoFecha;
	}
	
	public boolean existeReg( String year) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAESTRO_RANGO_FECHA WHERE YEAR = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] { year};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoFechaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getFecha( String year) {		
		String fecha = aca.util.Fecha.getHoy();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAESTRO_RANGO_FECHA WHERE YEAR = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] { year};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT TO_CHAR(FECHA,'DD/MM/YYYY') FROM ENOC.MAESTRO_RANGO_FECHA WHERE YEAR = TO_NUMBER(?,'9999')";
				fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.maestro.spring.MestroRangoFechaDao|existeReg|:"+ex);
		}
		return fecha;
	}
	
}