package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.financiero.spring.FinQuote;
import aca.financiero.spring.FinQuoteMapper;
@Component
public class FinQuoteConceptDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinQuoteConcept financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_QUOTE_CONCEPT "
					+ " (QUOTE_ID, CONC_ID, NO_UNITS, AMOUNT, DISCOUNT_AMT) "
					+ " VALUES( TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?))";
			Object[] parametros = new Object[] {
						financiero.getQuoteId(), financiero.getConcId(), financiero.getNoUnits(), financiero.getAmount(), financiero.getDiscountAmt()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( FinQuoteConcept financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_QUOTE_CONCEPT"
					+ " SET NO_UNITS = TO_NUMBER(?),"
					+ " AMOUNT = TO_NUMBER(?),"
					+ " DISCOUNT_AMT = TO_NUMBER(?)"					
					+ " WHERE QUOTE_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {
						financiero.getNoUnits(), financiero.getAmount(), financiero.getDiscountAmt(), financiero.getQuoteId(), financiero.getConcId()
					};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String quoteId, String concId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_QUOTE_CONCEPT "
					+ " WHERE QUOTE_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId, concId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public FinQuoteConcept mapeaRegId( String quoteId, String concId ) {
		
		FinQuoteConcept objeto = new FinQuoteConcept();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE_CONCEPT WHERE QUOTE_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId, concId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT *"
						+ " FROM ENOC.FIN_QUOTE_CONCEPT"
						+ " WHERE QUOTE_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
				objeto = enocJdbc.queryForObject(comando, new FinQuoteConceptMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String quoteId, String concId ) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE_CONCEPT"
					+ " WHERE QUOTE_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId, concId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public List<FinQuoteConcept> getListAll( String quoteId, String orden ) {
		
		List<FinQuoteConcept> lista = new ArrayList<FinQuoteConcept>();		
		try{
			String comando = "SELECT * "
					+ " FROM ENOC.FIN_QUOTE_CONCEPT WHERE QUOTE_ID = TO_NUMBER(?)"+orden;
			lista = enocJdbc.query(comando, new FinQuoteConceptMapper(), quoteId);			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|getListAll|:"+ex);
		}
		return lista;
	}

	public List<FinQuoteConcept> getSubjectsPerQuote( String quoteId, String orden ) {
		
		List<FinQuoteConcept> lista = new ArrayList<FinQuoteConcept>();		
		try{
			String comando = "SELECT * "
					+ " FROM ENOC.FIN_QUOTE_CONCEPT"
					+ " WHERE QUOTE_ID = TO_NUMBER(?)"
					+ " AND CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S')"+orden;
			lista = enocJdbc.query(comando, new FinQuoteConceptMapper(), quoteId);			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|getSubjectsPerQuote|:"+ex);
		}
		return lista;
	}

	public int getSubjectsPerQuote( String quoteId ) {
		int subjects			= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE_CONCEPT WHERE QUOTE_ID IN (SELECT QUOTE_ID FROM FIN_QUOTE WHERE PERIODO_ID = ? AND SEMESTER = TO_NUMBER(?)) AND CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S')";
			Object[] parametros = new Object[] {quoteId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE_CONCEPT WHERE QUOTE_ID IN (SELECT QUOTE_ID FROM FIN_QUOTE WHERE PERIODO_ID = ? AND SEMESTER = TO_NUMBER(?)) AND CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S')";		
				subjects = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getDescuento|:"+ex);
		}
		return subjects;
	}

	public List<FinQuoteConcept> getFeesSubjectsPerQuote( String periodId, String semester, String orden) {
		
		List<FinQuoteConcept> lista = new ArrayList<FinQuoteConcept>();		
		try{
			String comando = "SELECT * "
					+ " FROM ENOC.FIN_QUOTE_CONCEPT"
					+ " WHERE QUOTE_ID IN (SELECT QUOTE_ID FROM FIN_QUOTE WHERE PERIODO_ID = ? AND SEMESTER = TO_NUMBER(?))"
					+ " AND CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S' OR TYPE = 'F')"+orden;
			Object[] parametros = new Object[] {periodId, semester};
			lista = enocJdbc.query(comando, new FinQuoteConceptMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|getFeesSubjectsPerQuote|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapConceptsPerQuote() {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT QUOTE_ID AS LLAVE, COUNT(CONC_ID) AS VALOR FROM FIN_QUOTE_CONCEPT GROUP BY QUOTE_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|mapConceptsPerQuote|:" + ex);
		}
		return map;
	}

	public HashMap<String, String> mapConceptsPerQuote(String periodoId, String semester, String type) {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT QUOTE_ID AS LLAVE, COUNT(CONC_ID) AS VALOR FROM FIN_QUOTE_CONCEPT WHERE CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = ?) AND QUOTE_ID IN (SELECT QUOTE_ID FROM FIN_QUOTE WHERE PERIODO_ID = ? AND SEMESTER = TO_NUMBER(?)) GROUP BY QUOTE_ID";
			Object[] parametros = new Object[] {type, periodoId, semester};
			list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.fiannciero.spring.FinQuoteConceptDao|mapConceptsPerQuote|:" + ex);
		}
		return map;
	}
}