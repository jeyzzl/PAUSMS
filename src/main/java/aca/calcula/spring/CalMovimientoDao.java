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
public class CalMovimientoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(CalMovimiento objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAL_MOVIMIENTO(MOVTO_ID, CODIGO_PERSONAL, CONCEPTO_ID, IMPORTE, CARGA_ID, BLOQUE_ID, FECHA, TIPO)"
					+ " VALUES(TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99999999.99'), ?, TO_NUMBER(?,'99'), SYSDATE, ?)";
			Object[] parametros = new Object[] {
				objeto.getMovimientoId(), objeto.getCodigoPersonal(), objeto.getConceptoId(), objeto.getImporte(), objeto.getCargaId(), objeto.getBloqueId(), objeto.getTipo() 
			};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String movimientoId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAL_MOVIMIENTO WHERE MOVTO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {movimientoId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public CalMovimiento mapeaRegId(String movimientoId) {
		
		CalMovimiento objeto = new CalMovimiento();
		try{
			String comando = "SELECT MOVTO_ID, CODIGO_PERSONAL, CONCEPTO_ID, IMPORTE, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TIPO FROM ENOC.CAL_MOVIMIENTO "
					+ " WHERE MOVTO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {movimientoId};		
			objeto = enocJdbc.queryForObject(comando, new CalMovimientoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|mapeaRegId|:"+ex);						
		}
		
		return objeto;
	}
	
	public boolean existeReg(String movimientoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAL_MOVIMIENTO"
					+ " WHERE MOVTO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {movimientoId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(MOVTO_ID)+1,1) AS MAXIMO FROM ENOC.CAL_MOVIMIENTO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
		
	public List<CalMovimiento> lisTodos(String orden ) {
		List<CalMovimiento> lista = new ArrayList<CalMovimiento>();		
		try{
			String comando = "SELECT MOVTO_ID, CODIGO_PERSONAL, CONCEPTO_ID, IMPORTE, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TIPO FROM ENOC.CAL_MOVIMIENTO "+ orden; 
			lista = enocJdbc.query(comando, new CalMovimientoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|lisTodos|:"+ex);	
		}		
		return lista;
	}
	
	public List<CalMovimiento> lisPorAlumno(String codigoAlumno, String cargaId, String bloqueId, String orden ) {
		List<CalMovimiento> lista = new ArrayList<CalMovimiento>();		
		try{
			String comando = "SELECT MOVTO_ID, CODIGO_PERSONAL, CONCEPTO_ID, IMPORTE, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TIPO FROM ENOC.CAL_MOVIMIENTO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') "+ orden; 
			lista = enocJdbc.query(comando, new CalMovimientoMapper(), codigoAlumno, cargaId, bloqueId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|lisPorAlumno|:"+ex);	
		}		
		return lista;
	}
	
	public HashMap<String,CalMovimiento> mapaTodos(String orden) {
		List<CalMovimiento> lista			= new ArrayList<CalMovimiento>();
		HashMap<String,CalMovimiento> mapa 	= new HashMap<String,CalMovimiento>();		
		try{
			String comando = "SELECT MOVTO_ID, CODIGO_PERSONAL, CONCEPTO_ID, IMPORTE, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TIPO FROM ENOC.CAL_MOVIMIENTO "+orden;
			lista = enocJdbc.query(comando, new CalMovimientoMapper());
			for(CalMovimiento objeto: lista){
				mapa.put(objeto.getMovimientoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaConceptoMovimientos(String orden) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CONCEPTO_ID AS LLAVE, MOVTO_ID AS VALOR FROM ENOC.CAL_MOVIMIENTO "+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto: lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CalMovimientoDao|mapaConceptoMovimientos|:"+ex);
		}
		
		return mapa;
	}

}