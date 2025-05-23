package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecSolPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BecSolPeriodo becPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_SOL_PERIODO"
					+ " (PERIODO_ID, PERIODO_NOMBRE, FECHA)"
					+ " VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'))";
			
			Object[] parametros = new Object[] {becPeriodo.getPeriodoId(),becPeriodo.getPeriodoNombre(),becPeriodo.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|insertReg|:"+ex);			
		}
		return ok;
	} 
	
	public boolean updateReg( BecSolPeriodo becPeriodo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_SOL_PERIODO SET PERIODO_NOMBRE = ?,"
					+ " FECHA = TO_DATE(?, 'DD/MM/YYYY')"
					+ " WHERE PERIODO_ID = ? ";			
			Object[] parametros = new Object[] {becPeriodo.getPeriodoNombre(),becPeriodo.getFecha(),becPeriodo.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String periodoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BEC_SOL_PERIODO WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public BecSolPeriodo mapeaRegId( String periodoId ) {
		BecSolPeriodo becPeriodo = new BecSolPeriodo();
		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA FROM ENOC.BEC_SOL_PERIODO WHERE PERIODO_ID = ? "; 
			Object[] parametros = new Object[] {periodoId};
			becPeriodo = enocJdbc.queryForObject(comando, new BecSolPeriodoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|mapeaRegId|:"+ex);
		}
		return becPeriodo;
	}
	
	public boolean existeReg( String periodoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_SOL_PERIODO WHERE PERIODO_ID = ?";			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<BecSolPeriodo> getAll(String orden) {
		List<BecSolPeriodo> lista	= new ArrayList<BecSolPeriodo>();	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA FROM ENOC.BEC_SOL_PERIODO "+ orden;			
			lista = enocJdbc.query(comando, new BecSolPeriodoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|getAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,BecSolPeriodo> mapaBecSolPeriodo() {
		HashMap<String,BecSolPeriodo> mapa	= new HashMap<String,BecSolPeriodo>();	
		List<BecSolPeriodo> lista	= new ArrayList<BecSolPeriodo>();	
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA FROM ENOC.BEC_SOL_PERIODO ";			
			lista = enocJdbc.query(comando, new BecSolPeriodoMapper());		
			
			for(BecSolPeriodo objeto : lista) {
				mapa.put(objeto.getPeriodoId(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecSolPeriodoDao|mapaBecSolPeriodo|:"+ex);
		}
		return mapa;
	}
	
}