// Clase Util para la tabla de Carga
package aca.emp.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EmpTipoPagoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpTipoPago tipo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_TIPOPAGO"
					+ " (TIPOPAGO_ID, TIPOPAGO_NOMBRE, CORTO)"
					+ " TO_NUMBER(?,'99'), ?, ?)";
			Object[] parametros = new Object[] {
					tipo.getTipopagoId(), tipo.getTipopagoNombre(), tipo.getCorto()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpTipoPago tipo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_TIPOPAGO"
				+ " SET AND TIPOPAGO_NOMBRE = ?,"
				+ " CORTO = ?"
				+ " WHERE TIPOPAGO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
				tipo.getTipopagoNombre(), tipo.getCorto(), tipo.getTipopagoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String tipopagoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_TIPOPAGO WHERE TIPOPAGO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {tipopagoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public EmpTipoPago mapeaRegId( String tipopagoId ) {
		
		EmpTipoPago objeto = new EmpTipoPago();
		try{
			String comando = "SELECT TIPOPAGO_ID, TIPOPAGO_NOMBRE, CORTO"
					+ " FROM ENOC.EMP_TIPOPAGO"
					+ " WHERE TIPOPAGO_ID = TO_NUMBER(?,'99')"
					+ " AND FOLIO = ?";
			Object[] parametros = new Object[] {tipopagoId};
			objeto = enocJdbc.queryForObject(comando, new EmpTipoPagoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String tipopagoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_TIPOPAGO WHERE TIPOPAGO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {tipopagoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public String maximoReg( String tipopagoId) {
		
		int  maximo = 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(TIPOPAGO_ID)+1,1) FROM ENOC.EMP_TIPOPAGO WHERE TIPOPAGO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {tipopagoId};
			maximo = enocJdbc.queryForObject(comando,Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|maximoCurso|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<EmpTipoPago> lisTodos( String orden ) {
		
		List<EmpTipoPago> lista	= new ArrayList<EmpTipoPago>();	
		String comando	= "";
		
		try{
			comando = " SELECT TIPOPAGO_ID, TIPOPAGO_NOMBRE, CORTO "
					+ " FROM ENOC.EMP_TIPOPAGO "+orden; 
			lista = enocJdbc.query(comando, new EmpTipoPagoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EmpTipoPago> mapaTodos() {
		
		HashMap<String,EmpTipoPago> mapa = new HashMap<String,EmpTipoPago>();
		List<EmpTipoPago> lista		= new ArrayList<EmpTipoPago>();
		
		try{
			String comando = "SELECT TIPOPAGO_ID, TIPOPAGO_NOMBRE, CORTO FROM ENOC.EMP_TIPOPAGO";			
			lista = enocJdbc.query(comando, new EmpTipoPagoMapper());
			for (EmpTipoPago tipo : lista ) {
				mapa.put(tipo.getTipopagoId(), tipo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaTipos() {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT TIPOPAGO_ID AS LLAVE, TIPOPAGO_NOMBRE AS VALOR FROM ENOC.EMP_TIPOPAGO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpTipoPagoDao|mapaTipos|:"+ex);
		}
		
		return mapa;
	}
		
}