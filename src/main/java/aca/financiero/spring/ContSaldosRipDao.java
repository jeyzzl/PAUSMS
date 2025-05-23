package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContSaldosRipDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	//Metodos
	
	public boolean existeReg( String id) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.CONT_SALDOS_RIP  WHERE ID = TO_NUMBER(?, '9999999999999999999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContSaldosRipDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ContSaldosRip mapeaRegId( String id) {
		
		ContSaldosRip contSaldosRip = new ContSaldosRip();
		
		try{
			String comando = "SELECT ID, VERSION, MATRICULA, SALDO, NATURALEZA, QUIEN_REGISTRO,  TO_CHAR(CUANDO_REGISTRO, 'DD/MM/YYYY') AS CUANDO_REGISTRO,"
					+ " QUIEN_MODIFICO,  TO_CHAR(CUANDO_REGISTRO, 'DD/MM/YYYY') AS CUANDO_MODIFICO, STATUS, CONTABILIDAD, "
					+ " ID_EJERCICIO, OBSERVACION"					
					+" FROM MATEO.CONT_SALDOS_RIP"
					+ " WHERE MATRICULA = ?"
					+ " AND STATUS = ?";							 
			Object[] parametros = new Object[] {id};
			contSaldosRip = enocJdbc.queryForObject(comando, new ContSaldosRipMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContSaldosRipDao|mapeaRegId|:"+ex);
		}
		
		return contSaldosRip;
	}
	
	public ContSaldosRip mapeaRegId( String codigoPersonal, String estado) {
		
		ContSaldosRip contSaldosRip = new ContSaldosRip();
		
		try{
			String comando = "SELECT ID, VERSION, MATRICULA, SALDO, NATURALEZA, QUIEN_REGISTRO,  TO_CHAR(CUANDO_REGISTRO, 'DD/MM/YYYY') AS CUANDO_REGISTRO,"
					+ " QUIEN_MODIFICO,  TO_CHAR(CUANDO_REGISTRO, 'DD/MM/YYYY') AS CUANDO_MODIFICO, STATUS, CONTABILIDAD, "
					+ " ID_EJERCICIO, OBSERVACION"
					+ " FROM MATEO.CONT_SALDOS_RIP"
					+ " WHERE MATRICULA = ?"
					+ " AND STATUS = ?"; 
			Object[] parametros = new Object[] {codigoPersonal, estado};			
			contSaldosRip = enocJdbc.queryForObject(comando, new ContSaldosRipMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContSaldosRipDao|mapeaRegId|:"+ex);
		}
		
		return contSaldosRip;
	}
	
	public boolean tieneSaldoRip( String matricula, String status) {
		
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.CONT_SALDOS_RIP "
					+ " WHERE MATRICULA = ?"
					+ " AND STATUS = ?"; 
			Object[] parametros = new Object[] {matricula, status};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContSaldosRipDao|tieneSaldoRip|:"+ex);
		}
		
		return ok;
	}
	
	public ArrayList<ContSaldosRip> getListAll( String orden) {
		
		List<ContSaldosRip> lista = new ArrayList<ContSaldosRip>();
		
		try{
			String comando = "SELECT ID, VERSION, MATRICULA, SALDO, NATURALEZA, QUIEN_REGISTRO, CUANDO_REGISTRO,"
					+ " QUIEN_MODIFICO, CUANDO_MODIFICO, STATUS, CONTABILIDAD, "
					+ " ID_EJERCICIO, OBSERVACION" +
					  " FROM MATEO.CONT_SALDOS_RIP "+orden;	
			lista = enocJdbc.query(comando, new ContSaldosRipMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContSaldosRipDao|getListAll|:"+ex);
		}
		
		return (ArrayList<ContSaldosRip>)lista;
	}
		
}
