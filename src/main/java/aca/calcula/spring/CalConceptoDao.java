// Clase Util para la tabla de Cat_Area
package aca.calcula.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalConceptoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalConcepto concepto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_CONCEPTO(CONCEPTO_ID, CONCEPTO_NOMBRE, TIPO) VALUES( TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] {concepto.getConceptoId(), concepto.getConceptoNombre(), concepto.getTipo()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(CalConcepto concepto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAL_CONCEPTO SET CONCEPTO_NOMBRE = ?, TIPO = ? WHERE CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {concepto.getConceptoNombre(), concepto.getTipo(), concepto.getConceptoId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|updateReg|:"+ex);		

		}
		
		return ok;
	}	
	
	public boolean deleteReg(String conceptoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_CONCEPTO WHERE CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {conceptoId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalConcepto mapeaRegId(String conceptoId) {
		
		CalConcepto concepto = new CalConcepto();
		try{
			String comando = "SELECT CONCEPTO_ID, CONCEPTO_NOMBRE, TIPO FROM ENOC.CAL_CONCEPTO WHERE CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {conceptoId};		
			concepto = enocJdbc.queryForObject(comando, new CalConceptoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|mapeaRegId|:"+ex);						
		}
		
		return concepto;
	}
	
	public boolean existeReg(String conceptoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_CONCEPTO WHERE CONCEPTO_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {conceptoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(CONCEPTO_ID)+1,1) AS MAXIMO FROM ENOC.CAL_CONCEPTO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getConceptoNombre(String conceptoId) {
		
		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_CONCEPTO WHERE CONCEPTO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {conceptoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT CONCEPTO_NOMBRE FROM ENOC.CAL_CONCEPTO WHERE CONCEPTO_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|getConceptoNombre|:"+ex);
		}		
		return nombre;
	}
		
	public List<CalConcepto> lisTodos(String orden ) {
		
		List<CalConcepto> lista		= new ArrayList<CalConcepto>();		
		try{
			String comando = "SELECT CONCEPTO_ID, CONCEPTO_NOMBRE, TIPO FROM ENOC.CAL_CONCEPTO "+ orden; 
			lista = enocJdbc.query(comando, new CalConceptoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalConcepto> mapaTodos() {
		
		List<CalConcepto> lista		= new ArrayList<CalConcepto>();
		HashMap<String,CalConcepto> mapa = new HashMap<String,CalConcepto>();		
		try{
			String comando = "SELECT CONCEPTO_ID, CONCEPTO_NOMBRE, TIPO FROM ENOC.CAL_CONCEPTO ";
			lista = enocJdbc.query(comando, new CalConceptoMapper());
			for(CalConcepto concepto: lista){
				mapa.put(concepto.getConceptoId(), concepto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalConceptoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}

}