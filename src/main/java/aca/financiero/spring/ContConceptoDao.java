package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContConceptoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean existeReg( String id) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.CONT_CONCEPTO WHERE ID = TO_NUMBER(?, '9999999999')";
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContConceptoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ContConcepto mapeaRegId(String id) {
		
		ContConcepto concepto = new ContConcepto();
		
		try{
			String comando = "SELECT ID, VERSION, DESCRIPCION, STATUS, NOMBRE, TAGS "
					+ " FROM MATEO.CONT_CONCEPTO WHERE ID = TO_NUMBER(?, '9999999999') ";
			Object[] parametros = new Object[] {id};
			concepto = enocJdbc.queryForObject(comando, new ContConceptoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContConceptoDao|mapeaRegId|:"+ex);
		}
		
		return concepto;
	}
	
	public List<ContConcepto> lisAll( String orden) {
		
		List<ContConcepto> lista = new ArrayList<ContConcepto>();
		
		try{
			String comando = "SELECT ID, VERSION, DESCRIPCION, STATUS, NOMBRE, TAGS"
					+ " FROM MATEO.CONT_CONCEPTO "+orden;
			lista = enocJdbc.query(comando, new ContConceptoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContConceptoDao|lisAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, ContConcepto> mapaConceptos( ){
		HashMap<String, ContConcepto> mapa		= new HashMap<String, ContConcepto>();
		List<ContConcepto> lista 				= new ArrayList<ContConcepto>();
		try{			
			String comando = "SELECT ID, VERSION, DESCRIPCION, STATUS, NOMBRE, TAGS"
					+ " FROM MATEO.CONT_CONCEPTO"; 								
			lista = enocJdbc.query(comando, new ContConceptoMapper());
			for (ContConcepto concepto : lista) {
				mapa.put(concepto.getId(), concepto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContConceptoDao|mapaConceptos|:"+ex);
		}
		
		return mapa;
	}
	
}
