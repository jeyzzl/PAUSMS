package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinCreditosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FinCreditos creditos) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_CREDITOS(CARGA_ID,DEPTO_ID,NOR_ACFE,NOR_NOACFE,DIA_ACFE,DIA_NOACFE)"
					+ " VALUES (?,?,TO_NUMBER(?,'9999999.99'),TO_NUMBER(?,'9999999.99'),TO_NUMBER(?,'9999999.99'),TO_NUMBER(?,'9999999.99'))";			
			Object[] parametros = new Object[] {
				creditos.getCargaId(), creditos.getDeptoId(), creditos.getNorAcfe(), creditos.getNorNoAfce(), creditos.getDiaAcfe(), creditos.getDiaNoAcfe()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch (Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(FinCreditos creditos ) {
		boolean ok = false;		
		try{
			String comando ="UPDATE ENOC.FIN_CREDITOS"
					+ " SET NOR_ACFE = TO_NUMBER(?,'9999999.99'),"
					+ " NOR_NOACFE = TO_NUMBER(?,'9999999.99'),"
					+ " DIA_ACFE = TO_NUMBER(?,'9999999.99'),"
					+ " DIA_NOACFE = TO_NUMBER(?,'9999999.99')"
					+ " WHERE CARGA_ID = ? AND DEPTO_ID = ?";
			Object[] parametros = new Object[] { 
				creditos.getCargaId(), creditos.getDeptoId(), creditos.getNorAcfe(), creditos.getNorNoAfce(), creditos.getDiaAcfe(), creditos.getDiaNoAcfe()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public FinCreditos mapeaRegId(String cargaId, String deptoId){
		
		FinCreditos objeto = new FinCreditos();		
		try{
			String comando = "SELECT CARGA_ID,DEPTO_ID,NOR_ACFE,NOR_NOACFE,DIA_ACFE,DIA_NOACFE"
				+ " FROM ENOC.FIN_CREDITOS WHERE CARGA_ID = ? AND DEPTO_ID = ?";
			Object[] parametros = new Object[] {cargaId,deptoId};
			objeto = enocJdbc.queryForObject(comando, new FinCreditosMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean deleteReg(String cargaId, String deptoId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_CREDITOS WHERE CARGA_ID = ? AND DEPTO_ID = ?";
			
			Object[] parametros = new Object[] {
				cargaId,deptoId
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public boolean existeReg(String cargaId, String deptoId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_CREDITOS WHERE CARGA_ID = ? AND DEPTO_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, deptoId};	
			
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<FinCreditos> listFinCreditos(String orden){
		List<FinCreditos> lista 	= new ArrayList<FinCreditos>();		
		try{			
			String comando = "SELECT CARGA_ID,DEPTO_ID,NOR_ACFE,NOR_NOACFE,DIA_ACFE,DIA_NOACFE FROM ENOC.FIN_CREDITOS " +orden;
			lista = enocJdbc.query(comando, new FinCreditosMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|listFinCreditos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,FinCreditos> mapaFinCreditos() {
		HashMap<String,FinCreditos> map 	= new HashMap<String,FinCreditos>();	
		List<FinCreditos> lista 			= new ArrayList<FinCreditos>();
		
		try{
			String comando = "SELECT CARGA_ID, DEPTO_ID ,NOR_ACFE,NOR_NOACFE,DIA_ACFE,DIA_NOACFE FROM ENOC.FIN_CREDITOS";		
			
			lista = enocJdbc.query(comando, new FinCreditosMapper());			
			for(FinCreditos mapa : lista){
				map.put(mapa.getCargaId()+mapa.getDeptoId(), mapa);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinCreditosDao|mapaFinCreditos|:"+ex);
		}
		
		return map;
	}

}
