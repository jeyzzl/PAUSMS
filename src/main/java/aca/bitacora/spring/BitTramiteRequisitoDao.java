package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitTramiteRequisitoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public int existeReg(String tramiteId) {
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteRequisitoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return total;
	}
	
	public List<BitTramiteRequisito> lisTramites(String tramiteId, String orden) {
		
		List<BitTramiteRequisito> lista = new ArrayList<BitTramiteRequisito>();		
		try{
			String comando = " SELECT TRAMITE_ID, REQUISITO_ID FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID = ?"+orden;
			lista = enocJdbc.query(comando, new BitTramiteRequisitoMapper(),tramiteId);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteRequisitoDao|lisTramites|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaRequisitosEnTramite() {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT TRAMITE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.BIT_TRAMITE_REQUISITO GROUP BY TRAMITE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteRequisitoDao|mapaRequisitosEnTramite|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaRequisitoAsignados() {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT REQUISITO_ID AS LLAVE, COUNT(REQUISITO_ID) AS VALOR FROM ENOC.BIT_TRAMITE_REQUISITO GROUP BY REQUISITO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitTramiteRequisitoDao|mapaRequisitoAsignados|:"+ex);
		}
		
		return mapa;
	}
	
}
