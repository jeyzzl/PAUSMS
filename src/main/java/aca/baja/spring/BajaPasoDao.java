package aca.baja.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BajaPasoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public BajaPaso mapeaRegId( String pasoId) {
		BajaPaso bajaPaso = new BajaPaso();

		try{
			String comando = "SELECT PASO_ID, PASO_NOMBRE FROM ENOC.BAJA_PASO WHERE PASO_ID = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {pasoId};
			bajaPaso = enocJdbc.queryForObject(comando, new BajaPasoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaPasoUtil|mapeaRegId|:"+ex);
		}
		return bajaPaso;
	}
	
	public List<BajaPaso> getListAll( String orden) {
		List<BajaPaso> lista 	= new ArrayList<BajaPaso>();
		String comando				= "";
		
		try{
			comando = "SELECT PASO_ID, PASO_NOMBRE FROM ENOC.BAJA_PASO "+orden; 
			
			lista = enocJdbc.query(comando, new BajaPasoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaPasoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,BajaPaso> mapPasos() {		
		HashMap<String,BajaPaso> mapa		= new HashMap<String,BajaPaso>();
		List<BajaPaso> lista	 			= new ArrayList<BajaPaso>();		
		try{
			String comando = " SELECT PASO_ID, PASO_NOMBRE FROM ENOC.BAJA_PASO";
			lista = enocJdbc.query(comando,new BajaPasoMapper());
			for(BajaPaso objeto : lista){				
				mapa.put(objeto.getPasoId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.BajaPasoDao|mapPasos|:"+ex);
		}
		
		return mapa;
	}
	
}