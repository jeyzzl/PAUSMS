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
public class EmpHorasPresDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpHorasPres horas ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EMP_HORAS_PRES (DEPARTAMENTO_ID, CARGA_ID, YEAR, IMPORTE, SALDO_ANT)"
					+ " VALUES( ?, ?, ?, TO_NUMBER(?,'99999999.99'), TO_NUMBER(?,'99999999.99'))";
			Object[] parametros = new Object[] {
					horas.getDepartamentoId(), horas.getCargaId(), horas.getYear(), horas.getImporte(), horas.getSaldoAnt()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|insertReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateReg( EmpHorasPres horas ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.EMP_HORAS_PRES "
					+ " SET YEAR = ?, IMPORTE = TO_NUMBER(?,'99999999.99'), SALDO_ANT = TO_NUMBER(?,'99999999.99')"			
					+ " WHERE DEPARTAMENTO_ID = ?"
					+ " AND CARGA_ID = ?";
			Object[] parametros = new Object[] {
				horas.getYear(), horas.getImporte(), horas.getSaldoAnt(), horas.getDepartamentoId(), horas.getCargaId() 
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String departamentoId, String cargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_HORAS_PRES WHERE DEPARTAMENTO_ID = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {departamentoId, cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public EmpHorasPres mapeaRegId(  String departamentoId, String cargaId ) {
		
		EmpHorasPres objeto = new EmpHorasPres();
		try{
			String comando = "SELECT DEPARTAMENTO_ID, CARGA_ID, YEAR, IMPORTE, SALDO_ANT FROM ENOC.EMP_HORAS_PRES"
					+ " WHERE DEPARTAMENTO_ID = ?"
					+ " AND CARGA_ID = ?";
			Object[] parametros = new Object[] {departamentoId, cargaId};
			objeto = enocJdbc.queryForObject(comando, new EmpHorasPresMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String departamentoId, String cargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_HORAS_PRES WHERE DEPARTAMENTO_ID = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {departamentoId, cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public List<EmpHorasPres> lisTodos( String orden ) {
		
		List<EmpHorasPres> lista	= new ArrayList<EmpHorasPres>();	
		String comando	= "";		
		try{
			comando = " SELECT DEPARTAMENTO_ID, CARGA_ID, YEAR, IMPORTE, SALDO_ANT FROM ENOC.EMP_HORAS_PRES " +orden; 
			lista = enocJdbc.query(comando, new EmpHorasPresMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHorasPres> lisPorYear( String year, String orden ) {
		
		List<EmpHorasPres> lista	= new ArrayList<EmpHorasPres>();	
		String comando				= "";		
		try{
			comando = " SELECT DEPARTAMENTO_ID, CARGA_ID, YEAR, IMPORTE, SALDO_ANT FROM ENOC.EMP_HORAS_PRES WHERE YEAR = ? "+orden;
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new EmpHorasPresMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|lisPorYear|:"+ex);
		}
		
		return lista;
	}
	
	public List<EmpHorasPres> lisPorCarga( String cargaId, String orden ) {
		
		List<EmpHorasPres> lista	= new ArrayList<EmpHorasPres>();	
		String comando				= "";		
		try{
			comando = " SELECT DEPARTAMENTO_ID, CARGA_ID, YEAR, IMPORTE, SALDO_ANT FROM ENOC.EMP_HORAS_PRES WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new EmpHorasPresMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|lisPorCarga|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapPorDepto(String year) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT DEPARTAMENTO_ID AS LLAVE, IMPORTE AS VALOR FROM ENOC.EMP_HORAS_PRES WHERE YEAR = ? GROUP BY DEPARTAMENTO_ID";
			Object[] parametros = new Object[] {year};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpHorasPresDao|mapPorDepto|:"+ex);
		}
		
		return mapa;
	}
}