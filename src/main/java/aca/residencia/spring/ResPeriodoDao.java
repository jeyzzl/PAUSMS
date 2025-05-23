package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResPeriodo objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_PERIODO"
					+ " (PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "
					+ " VALUES(TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)";
			
			Object[] parametros = new Object[] {
				objeto.getPeriodoId(),objeto.getPeriodoNombre(),objeto.getFechaIni(),objeto.getFechaFin(), objeto.getEstado()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ResPeriodo objeto){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_PERIODO" 
				+ " SET PERIODO_NOMBRE = ?,"
				+ " FECHA_INI = TO_DATE(?,'DD/MM/YYYY'),"
				+ " FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'),"
				+ " ESTADO = ?"
				+ " WHERE PERIODO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				objeto.getPeriodoNombre(),objeto.getFechaIni(),objeto.getFechaFin(),objeto.getEstado(),objeto.getPeriodoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String periodoId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.RES_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public ResPeriodo mapeaRegId(String periodoId) {
		ResPeriodo objeto = new ResPeriodo();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
						+ " FROM ENOC.RES_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')";
				objeto = enocJdbc.queryForObject(comando, new ResPeriodoMapper(), parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResPeriodoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public String maximoReg(){
 		String maximo = "1"; 		
 		try{
 			String comando = "SELECT COALESCE (MAX(PERIODO_ID)+1,1) AS MAXIMO FROM ENOC.RES_PERIODO"; 			
 			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.residencia.spring.ResPeriodoDao|maximoReg|:"+ex);
 		} 		
 		return maximo;
	}
	
	public boolean existeReg(String periodoId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.RES_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<ResPeriodo> lisTodos(String orden){
		
		List<ResPeriodo> lista = new ArrayList<ResPeriodo>();
	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO" +
					" FROM ENOC.RES_PERIODO "+orden; 
			lista = enocJdbc.query(comando, new ResPeriodoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodo|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, ResPeriodo> mapaPeriodo(){
		HashMap<String, ResPeriodo> mapa = new HashMap<String, ResPeriodo>();
		List<ResPeriodo> lista	    	= new ArrayList<ResPeriodo>();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO FROM ENOC.RES_PERIODO";
			lista = enocJdbc.query(comando, new ResPeriodoMapper());			
			for(ResPeriodo periodo : lista){				
				mapa.put(periodo.getPeriodoId(), periodo);					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResPeriodo|mapaPeriodo|:"+ex);
		}
		
		return mapa;
	}	

}