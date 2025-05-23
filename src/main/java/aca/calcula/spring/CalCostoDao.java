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
public class CalCostoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalCosto objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_COSTO(COSTO_ID, CONCEPTO_ID, CARGA_ID, BLOQUE_ID, TIPO, IMPORTE, COMENTARIO) VALUES(TO_NUMBER(?,'9999'), TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99999999'), ?)";
			Object[] parametros = new Object[] {
				objeto.getCostoId(), objeto.getConceptoId(), objeto.getCargaId(), objeto.getBloqueId(), objeto.getTipo(), objeto.getImporte(), objeto.getComentario()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(CalCosto objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAL_COSTO SET CARGA_ID = ?, BLOQUE_ID = TO_NUMBER(?,'99'), TIPO = ?, IMPORTE = TO_NUMBER(?,'99999999'), COMENTARIO = ? WHERE COSTO_ID = TO_NUMBER(?,'9999') AND CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
				 objeto.getCargaId(), objeto.getBloqueId(), objeto.getTipo(), objeto.getImporte(), objeto.getComentario(),objeto.getCostoId(), objeto.getConceptoId()
			};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|updateReg|:"+ex);		

		}
		return ok;
	}	
	
	public boolean deleteReg(String costoId,String conceptoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_COSTO WHERE COSTO_ID = TO_NUMBER(?,'9999') AND CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {costoId,conceptoId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalCosto mapeaRegId(String costoId,String conceptoId) {
		
		CalCosto objeto = new CalCosto();
		try{
			String comando = "SELECT COSTO_ID, CONCEPTO_ID, CARGA_ID, BLOQUE_ID, TIPO, IMPORTE, COMENTARIO FROM ENOC.CAL_COSTO WHERE COSTO_ID = TO_NUMBER(?,'9999') AND CONCEPTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {costoId,conceptoId};		
			objeto = enocJdbc.queryForObject(comando, new CalCostoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|mapeaRegId|:"+ex);						
		}
		return objeto;
	}
	
	public boolean existeReg(String costoId,String conceptoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_COSTO WHERE COSTO_ID = TO_NUMBER(?,'9999') AND CONCEPTO_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {costoId,conceptoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(COSTO_ID)+1,1) AS MAXIMO FROM ENOC.CAL_COSTO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<CalCosto> lisTodos(String orden ) {
		
		List<CalCosto> lista		= new ArrayList<CalCosto>();		
		try{
			String comando = "SELECT COSTO_ID, CONCEPTO_ID, CARGA_ID, BLOQUE_ID, TIPO, IMPORTE, COMENTARIO FROM ENOC.CAL_COSTO "+ orden; 
			lista = enocJdbc.query(comando, new CalCostoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public List<CalCosto> lisPorCarga(String cargaId, String bloqueId, String orden ){
		List<CalCosto> lista		= new ArrayList<CalCosto>();
		try{
			String comando = "SELECT COSTO_ID, CONCEPTO_ID, CARGA_ID, BLOQUE_ID, TIPO, IMPORTE, COMENTARIO FROM ENOC.CAL_COSTO"
					+ " WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') "+ orden; 
			lista = enocJdbc.query(comando, new CalCostoMapper(), cargaId, bloqueId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|lisPorCarga|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalCosto> mapaTodos() {
		List<CalCosto> lista		= new ArrayList<CalCosto>();
		HashMap<String,CalCosto> mapa = new HashMap<String,CalCosto>();		
		try{
			String comando = "SELECT COSTO_ID, CONCEPTO_ID, CARGA_ID, BLOQUE_ID, TIPO, IMPORTE, COMENTARIO FROM ENOC.CAL_COSTO ";
			lista = enocJdbc.query(comando, new CalCostoMapper());
			for(CalCosto objeto: lista){
				mapa.put(objeto.getConceptoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|mapaTodos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaConceptos() {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT DISTINCT(CONCEPTO_ID) AS LLAVE, CONCEPTO_ID AS VALOR FROM ENOC.CAL_COSTO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto: lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalCostoDao|mapaConceptos|:"+ex);
		}
		return mapa;
	}

}