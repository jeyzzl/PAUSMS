// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConvPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ConvPeriodo objeto ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CONV_PERIODO(PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN)"
					+ " VALUES(TO_NUMBER(?,'999'),?,TO_DATE(?,'DD/MM/YYYY'),TO_DATE(?,'DD/MM/YYYY'))";
			
			Object[] parametros = new Object[] {objeto.getPeriodoId(),objeto.getPeriodoNombre(),objeto.getFechaIni(),objeto.getFechaFin()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( ConvPeriodo objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_PERIODO"
					+ " SET PERIODO_NOMBRE = ?,"
					+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY')"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {objeto.getPeriodoNombre(),objeto.getFechaIni(),objeto.getFechaFin(),objeto.getPeriodoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateCarreras( String periodoId, String carreras ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CONV_PERIODO SET CARRERA = ? WHERE PERIODO_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {carreras, periodoId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|updateCarreras|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String periodoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CONV_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {periodoId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public ConvPeriodo mapeaRegId( String periodoId ) {
		ConvPeriodo objeto = new ConvPeriodo();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				comando = "SELECT PERIODO_ID, PERIODO_NOMBRE,"
						+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
						+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN,"
						+ " CARRERA"
						+ " FROM ENOC.CONV_PERIODO"
						+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";
				objeto = enocJdbc.queryForObject(comando, new ConvPeriodoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String periodoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";

			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String maxReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) AS MAXIMO FROM ENOC.CONV_PERIODO";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){	
				maximo = enocJdbc.queryForObject(comando,String.class);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|maxReg|:"+ex);
		}		
		return maximo;
	}
	
	public String getPeriodoNombre( String periodoId) {
		String nombre 			= "-";
		
		try{
			String comando = "SELECT PERIODO_NOMBRE FROM ENOC.CONV_PERIODO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";	
			
			Object[] parametros = new Object[] {periodoId};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|getPeriodoNombre|:"+ex);
		}
		
		return nombre;
	}	
	
	public String getPeriodoActivo( String fecha) {
		String periodo 			= "X";
		
		try{
			String comando = "SELECT PERIODO_ID FROM ENOC.CONV_PERIODO"
					+ " WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"; 
			
			
			Object[] parametros = new Object[] {fecha};
			periodo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|getPeriodoActivo|:"+ex);
		}
		
		return periodo;
	}
	
	public ArrayList<ConvPeriodo> getListAll( String orden ) {
		List<ConvPeriodo> lista		= new ArrayList<ConvPeriodo>();
		
		try{
			String comando = " SELECT PERIODO_ID, PERIODO_NOMBRE,"
					+ " TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI,"
					+ " TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, CARRERA"
					+ " FROM ENOC.CONV_PERIODO "+orden; 
			
			lista = enocJdbc.query(comando,new ConvPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|getListAll|:"+ex);
		}
		return (ArrayList<ConvPeriodo>) lista;
	}		
	
	public String carrerasPeriodo( String periodoId) {
		String carreras			= "0";		
		try{
			String comando = "SELECT CARRERA FROM ENOC.CONV_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {periodoId};
			carreras = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|carrerasPeriodo|:"+ex);
		}

		return carreras;
	}
	
	public HashMap<String,String> getMapCarreras( String periodoId ) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<String> lista 				= new ArrayList<String>();
		
		try{
			String comando = "SELECT CARRERA FROM ENOC.CONV_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String row : lista) {		
				String[]carreras = row.split("-");
				for(String carrera : carreras){
					mapa.put(carrera, carrera);
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|getMapCarreras|:"+ex);
		} 
		
		return mapa;
	}
	
	public boolean insertCarreras( String periodoId, String carreras) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CONV_PERIODO SET CARRERA = ?"
					+ "WHERE PERIODO_ID = ?";
			
			Object[] parametros = new Object[] {carreras,periodoId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|insertCarrera|:"+ex);			
		}

		return ok;
	}	
	
	public boolean deleteCarreras( String periodoId ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CONV_PERIODO SET CARRERA = '-' WHERE PERIODO_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {periodoId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|deleteCarreras|:"+ex);			
		}
		return ok;
	}
	
	public String carreraHabilitada( String carrera) {
		String periodo 			= "0";		
		try{
			String comando = "SELECT COUNT(PERIODO_ID) FROM ENOC.CONV_PERIODO WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN AND INSTR(CARRERA, ? ) > 0";
			Object[] parametros = new Object[] {carrera};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ) {
				comando 	= "SELECT PERIODO_ID FROM ENOC.CONV_PERIODO WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN AND INSTR(CARRERA, ? ) > 0";
				periodo 	= enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.spring.ConvPeriodoDao|carreraHabilitada|:"+ex);
		}

		return periodo;
	}
	
	
}