package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.catalogo.spring.CatTipoCalMapper;
import aca.financiero.spring.FinQuote;
import aca.financiero.spring.FinQuoteMapper;
@Component
public class FinQuoteDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinQuote financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_QUOTE "
					+ " (PERIODO_ID, QUOTE_ID, CODIGO_PERSONAL, FECHA, STATUS, DESCRIPTION, SEMESTER) "
					+ " VALUES( ?, TO_NUMBER(?), ?, TO_DATE(?,'dd/mm/yyyy'), ?, ?, TO_NUMBER(?))";
			Object[] parametros = new Object[] {
						financiero.getPeriodoId(), financiero.getQuoteId(), financiero.getCodigoPersonal(), financiero.getFecha(), financiero.getStatus(), financiero.getDescription(), financiero.getSemester()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( FinQuote financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_QUOTE"
					+ " SET FECHA = TO_DATE(?,'dd/mm/yyyy'),"
					+ " STATUS = ?,"					
					+ " DESCRIPTION = ?,"
					+ " SEMESTER = ?"
					+ " WHERE QUOTE_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {
						financiero.getFecha(), financiero.getStatus(), financiero.getDescription(), financiero.getSemester(), financiero.getQuoteId()
					};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String quoteId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_QUOTE "
					+ " WHERE QUOTE_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public FinQuote mapeaRegId( String quoteId ) {
		
		FinQuote objeto = new FinQuote();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE WHERE QUOTE_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT PERIODO_ID, QUOTE_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'dd/mm/yyyy') AS FECHA, STATUS, DESCRIPTION, SEMESTER"
						+ " FROM ENOC.FIN_QUOTE"
						+ " WHERE QUOTE_ID = TO_NUMBER(?)";
				objeto = enocJdbc.queryForObject(comando, new FinQuoteMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String quoteId ) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_QUOTE"
					+ " WHERE QUOTE_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {quoteId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|existeReg|:"+ex);
		}
		return ok;
	}	

	public int maximoReg() {
		
		int maximo			= 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(QUOTE_ID)+1,1) AS MAXIMO FROM ENOC.FIN_QUOTE";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<FinQuote> getListAll( String orden ) {
		
		List<FinQuote> lista = new ArrayList<FinQuote>();		
		try{
			String comando = "SELECT PERIODO_ID, QUOTE_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'dd/mm/yyyy') AS FECHA, STATUS, DESCRIPTION, SEMESTER"
					+ " FROM ENOC.FIN_QUOTE "+orden;
			lista = enocJdbc.query(comando, new FinQuoteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|getListAll|:"+ex);
		}
		return lista;
	}

	public List<FinQuote> getListPeriodo( String codigoPersonal, String periodoId, String orden ) {
		
		List<FinQuote> lista = new ArrayList<FinQuote>();		
		try{
			String comando = "SELECT PERIODO_ID, QUOTE_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'dd/mm/yyyy') AS FECHA, STATUS, DESCRIPTION, SEMESTER FROM ENOC.FIN_QUOTE WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?"+orden;
			Object[] parametros = new Object[] { periodoId, codigoPersonal};

			lista = enocJdbc.query(comando, new FinQuoteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|getListPeriodo|:"+ex);
		}
		return lista;
	}

	public List<FinQuote> getListPeriodoSemestre( String periodoId, String semester, String orden ) {
		
		List<FinQuote> lista = new ArrayList<FinQuote>();		
		try{
			String comando = "SELECT PERIODO_ID, QUOTE_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'dd/mm/yyyy') AS FECHA, STATUS, DESCRIPTION, SEMESTER FROM ENOC.FIN_QUOTE WHERE PERIODO_ID = ? AND SEMESTER = TO_NUMBER(?)"+orden;
			Object[] parametros = new Object[] { periodoId, semester};

			lista = enocJdbc.query(comando, new FinQuoteMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|getListPeriodo|:"+ex);
		}
		return lista;
	}

	public HashMap<String,String> mapFeesPerQuote(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT QUOTE_ID AS LLAVE, SUM(AMOUNT) AS VALOR FROM FIN_QUOTE_CONCEPT WHERE CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'F') GROUP BY QUOTE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|mapFeesPerQuote|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapSubjectsPerQuote(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT QUOTE_ID AS LLAVE, SUM(AMOUNT) AS VALOR FROM FIN_QUOTE_CONCEPT WHERE CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S') GROUP BY QUOTE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|mapSubjectsPerQuote|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapSubDescPerQuote(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT QUOTE_ID AS LLAVE, SUM(DISCOUNT_AMT) AS VALOR FROM FIN_QUOTE_CONCEPT WHERE CONC_ID IN (SELECT CONC_ID FROM FIN_CONCEPT WHERE TYPE = 'S') GROUP BY QUOTE_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.fiannciero.spring.FinQuoteDao|mapSubDescPerQuote|:"+ex);
		}		
		return mapa;
	}
	
}