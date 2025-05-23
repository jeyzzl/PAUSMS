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
public class CalPagareDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalPagare pagare){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_PAGARE(PAGARE_ID, PAGARE_NOMBRE, FECHA, CARGA_ID, BLOQUE_ID)"
					+ " VALUES( TO_NUMBER(?,'9999'), ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {pagare.getPagareId(), pagare.getPagareNombre(), pagare.getFecha(), pagare.getCargaId(), pagare.getBloqueId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg(CalPagare pagare) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAL_PAGARE SET PAGARE_NOMBRE = ?, FECHA = TO_DATE(?,'DD/MM/YYYY'), CARGA_ID = ?, BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " WHERE PAGARE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {pagare.getPagareNombre(), pagare.getFecha(), pagare.getCargaId(), pagare.getBloqueId(), pagare.getPagareId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String pagareId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_PAGARE WHERE PAGARE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {pagareId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalPagare mapeaRegId(String pagareId) {
		
		CalPagare pagare = new CalPagare();
		try{
			String comando = "SELECT PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID"
					+ " FROM ENOC.CAL_PAGARE WHERE PAGARE_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {pagareId};		
			pagare = enocJdbc.queryForObject(comando, new CalPagareMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|mapeaRegId|:"+ex);						
		}
		
		return pagare;
	}
	
	public boolean existeReg(String pagareId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_PAGARE WHERE PAGARE_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {pagareId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(PAGARE_ID)+1,1) AS MAXIMO FROM ENOC.CAL_PAGARE";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getPagareNombre(String pagareId) {
		
		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_PAGARE WHERE PAGARE_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {pagareId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT PAGARE_NOMBRE FROM ENOC.CAL_PAGARE WHERE PAGARE_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|getNivelNombre|:"+ex);
		}		
		return nombre;
	}
		
	public List<CalPagare> lisTodos(String orden ) {
		
		List<CalPagare> lista		= new ArrayList<CalPagare>();		
		try{
			String comando = "SELECT PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.CAL_PAGARE "+ orden; 
			lista = enocJdbc.query(comando, new CalPagareMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public List<CalPagare> lisPorCarga(String cargaId, String bloqueId, String orden ) {		
		List<CalPagare> lista		= new ArrayList<CalPagare>();		
		try{
			String comando = "SELECT PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.CAL_PAGARE"
					+ " WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') "+ orden;
			lista = enocJdbc.query(comando, new CalPagareMapper(), cargaId, bloqueId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|lisPorCarga|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalPagare> mapaTodos(String orden ) {
		
		List<CalPagare> lista		= new ArrayList<CalPagare>();
		HashMap<String,CalPagare> mapa = new HashMap<String,CalPagare>();		
		try{
			String comando = "SELECT PAGARE_ID, PAGARE_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CARGA_ID, BLOQUE_ID FROM ENOC.CAL_PAGARE "+ orden;
			lista = enocJdbc.query(comando, new CalPagareMapper());
			for(CalPagare pagare: lista){
				mapa.put(pagare.getPagareId(), pagare);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaNombrePagarePorCargaId(String cargaId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();		
		try{
			String comando = "SELECT PAGARE_ID AS LLAVE, PAGARE_NOMBRE AS VALOR FROM ENOC.CAL_PAGARE WHERE CARGA_ID ='23242B' ORDER BY FECHA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto: lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalPagareDao|mapaNombrePagarePorCargaId|:"+ex);
		}
		
		return mapa;
	}

}