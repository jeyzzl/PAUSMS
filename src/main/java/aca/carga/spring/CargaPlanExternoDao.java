package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPlanExternoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaPlanExterno externo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PLAN_EXTERNO(CARGA_ID, PLAN_ID)" +
				" VALUES( ?, ?)";
			
				Object[] parametros = new Object[] {externo.getCargaId(),externo.getPlanId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean deleteReg(String cargaId, String planId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PLAN_EXTERNO "+ 
				" WHERE CARGA_ID = ?" +
				" AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaPlanExterno mapeaRegId(String cargaId, String planId) {
		CargaPlanExterno externo = new CargaPlanExterno();
		
		try{
			String comando = "SELECT CARGA_ID, PLAN_ID FROM ENOC.CARGA_PLAN_EXTERNO WHERE CARGA_ID = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cargaId,planId};
			externo = enocJdbc.queryForObject(comando, new CargaPlanExternoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|mapeaRegId|:"+ex);
		}
		return externo;
	}
	
	public boolean existeReg(String cargaId, String planId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PLAN_EXTERNO WHERE CARGA_ID = ?  AND PLAN_ID = ?";

			Object[] parametros = new Object[] {cargaId,planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<CargaPlanExterno> getListAll(String orden) {
		List<CargaPlanExterno> lista = new ArrayList<CargaPlanExterno>();
		String comando = "";
		
		try{
			comando = "SELECT CARGA_ID, PLAN_ID FROM ENOC.CARGA_PLAN_EXTERNO"+ orden; 
			
			lista = enocJdbc.query(comando, new CargaPlanExternoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|getListAll|:"+ex);
		}
		return lista;
	}

	public HashMap<String,String> mapaCargaPlanExterno(String cargaId) {
		List<CargaPlanExterno> lista 	= new ArrayList<CargaPlanExterno>();
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		
		String comando = "";
		
		try{
			comando = "SELECT CARGA_ID, PLAN_ID FROM ENOC.CARGA_PLAN_EXTERNO WHERE CARGA_ID = ?"; 
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPlanExternoMapper(), parametros);
			
			for(CargaPlanExterno externo : lista) {
				mapa.put(externo.getPlanId(), externo.getCargaId());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanExternoDao|mapaCargaPlanExterno|:"+ex);
		}
		return mapa;
	}

}