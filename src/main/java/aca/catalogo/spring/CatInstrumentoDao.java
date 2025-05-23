package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class CatInstrumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<CatInstrumento> getListAll(String orden){
		
		List<CatInstrumento> lista = new ArrayList<CatInstrumento>();
		
		try{
			String comando = "SELECT INSTRUMENTO_ID, DESCRIPCION FROM ENOC.CAT_INSTRUMENTO "+ orden;
			lista = enocJdbc.query(comando, new CatInstrumentoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatInstrumento|getListAll|:"+ex);
		}
		
		return lista;
	}

	public HashMap<String,CatInstrumento> getMapAll(String orden){
	
		HashMap<String,CatInstrumento> mapa = new HashMap<String,CatInstrumento>();
		List<CatInstrumento> lista = new ArrayList<CatInstrumento>();
		
		try{
			String comando = "SELECT INSTRUMENTO_ID, DESCRIPCION FROM ENOC.CAT_INSTRUMENTO "+ orden;
			lista = enocJdbc.query(comando, new CatInstrumentoMapper());
			
			for(CatInstrumento objeto:lista){
				mapa.put(objeto.getInstrumentoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatInstrumento|getMapAll|:"+ex);
		}
		
		return mapa;
	}

}