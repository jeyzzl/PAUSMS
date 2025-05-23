package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitRequisitoTramiteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(String requisitoId, String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_TRAMITE_REQUISITO (REQUISITO_ID, TRAMITE_ID) VALUES(TO_NUMBER(?, '9999'), TO_NUMBER(?, '9999'))";
			Object[] parametros = new Object[] {
				requisitoId,tramiteId
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoTramiteDao|insertReg|:"+ex);	
		}
		return ok;
	}	
	
	public boolean deleteReg(String requisitoId, String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_TRAMITE_REQUISITO WHERE REQUISITO_ID = TO_NUMBER(?, '9999') AND TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {requisitoId,tramiteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoTramiteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTotalReg(String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoTramiteDao|deleteTotalReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String tramiteId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoTramiteDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public HashMap<String, String> mapaRequisitosTramite(String tramiteId) {		
		List<BitRequisitoTramite> lista = new ArrayList<BitRequisitoTramite>();		
		HashMap<String, String> mapa = new HashMap<String, String>();		
		try{
			String comando = " SELECT REQUISITO_ID, TRAMITE_ID FROM ENOC.BIT_TRAMITE_REQUISITO WHERE TRAMITE_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {tramiteId};
			lista = enocJdbc.query(comando, new BitRequisitoTramiteMapper(),parametros);	
			for(BitRequisitoTramite objeto : lista) {
				mapa.put(objeto.getRequisitoId(), tramiteId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|mapaRequisitosTramite|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalRequisitos() {		
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa = new HashMap<String, String>();		
		try{
			String comando = " SELECT TRAMITE_ID AS LLAVE, COUNT(REQUISITO_ID) AS VALOR FROM ENOC.BIT_TRAMITE_REQUISITO GROUP BY TRAMITE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitRequisitoDao|mapaTotalRequisitos|:"+ex);
		}
		return mapa;
	}
}
