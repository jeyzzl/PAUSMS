package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jvnet.staxex.util.FinalArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.catalogo.spring.CatPaisMapper;
import aca.financiero.spring.FinConcept;
import aca.financiero.spring.FinConceptMapper;

@Component
public class FinConceptDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinConcept financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_CONCEPT "
					+ " (CONC_ID, NAME, UNIT_COST, TYPE, STATUS, CURSO_CLAVE, CODE) "
					+ " VALUES( TO_NUMBER(?), ?, TO_NUMBER(?), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
						financiero.getConcId(), financiero.getName(), financiero.getUnitCost(), financiero.getType(), financiero.getStatus(), financiero.getCursoClave(), financiero.getCode()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( FinConcept financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_CONCEPT"
					+ " SET NAME = ?,"
					+ " UNIT_COST = TO_NUMBER(?),"					
					+ " TYPE = ?,"
					+ " STATUS = ?,"
					+ " CURSO_CLAVE = ?,"
					+ " CODE = ?"	
					+ " WHERE CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {
				financiero.getName(), financiero.getUnitCost(), financiero.getType(), financiero.getStatus(), financiero.getCursoClave(), financiero.getCode(), financiero.getConcId(), 
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String concId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_CONCEPT "
					+ " WHERE CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {concId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public FinConcept mapeaRegId( String concId ) {
		
		FinConcept objeto = new FinConcept();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_CONCEPT WHERE CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {concId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT *"
						+ " FROM ENOC.FIN_CONCEPT"
						+ " WHERE CONC_ID = TO_NUMBER(?)";
				objeto = enocJdbc.queryForObject(comando, new FinConceptMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String concId ) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_CONCEPT"
					+ " WHERE CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {concId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|existeReg|:"+ex);
		}
		return ok;
	}	

	public int maximoReg() {
		
		int maximo			= 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(CONC_ID)+1,1) AS MAXIMO FROM ENOC.FIN_CONCEPT";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}

	public List<FinConcept> lisAll (String orden){
		List<FinConcept> lista = new ArrayList<FinConcept>();

		try{
			String comando = "SELECT * FROM ENOC.FIN_CONCEPT"+orden;
			lista = enocJdbc.query(comando, new FinConceptMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|lisAll|:"+ex);
		}

		return lista;
	}

	public HashMap<Integer,FinConcept> mapAll() {
		
		HashMap<Integer,FinConcept> mapa 	= new HashMap<Integer,FinConcept>();
		List<FinConcept> lista				= new ArrayList<FinConcept>();		
		try{
			String comando = "SELECT * FROM ENOC.FIN_CONCEPT";
			lista = enocJdbc.query(comando, new FinConceptMapper());
			for (FinConcept concept : lista){
				mapa.put(concept.getConcId(), concept);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinConceptDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapConceptsUsed() {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT CONC_ID AS LLAVE, COUNT(CONC_ID) AS VALOR FROM FIN_QUOTE_CONCEPT GROUP BY CONC_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa conc : list) {
				map.put(conc.getLlave(), conc.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.financiero.spring.FinConceptDao|mapConceptsUsed|:" + ex);
		}
		return map;
	}

	public HashMap<Integer, String> mapConceptLocation() {

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT FC.CONC_ID AS LLAVE, MAX(AP.LOCATION) AS VALOR FROM FIN_CONCEPT FC JOIN ATTACHE_PRODUCT AP ON (FC.CURSO_CLAVE = TRIM(AP.CODE) OR FC.CODE = TRIM(AP.CODE)) GROUP BY CONC_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa conc : list) {
				map.put(Integer.parseInt(conc.getLlave()), conc.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.financiero.spring.FinConceptDao|mapConceptLocation|:" + ex);
		}
		return map;
	}
	
}