package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPronDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaPron cargaPron ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PRON (CURSO_CARGA_ID) VALUES(?)";
			
			Object[] parametros = new Object[] {cargaPron.getCursoCargaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPronDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean existeReg(String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PRON WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPronDao|existeReg|:"+ex);			
		}		
		return ok;
	}
	
	public HashMap<String,CargaPron> getMapCargaPron() {
		HashMap<String,CargaPron> mapa	= new HashMap<String,CargaPron>();
		List<CargaPron> lista 			= new ArrayList<CargaPron>();
				
		try{
			String comando = "SELECT CURSO_CARGA_ID FROM ENOC.CARGA_PRON"; 			
			lista = enocJdbc.query(comando,new CargaPronMapper());
			for(CargaPron objeto : lista){				
				mapa.put(objeto.getCursoCargaId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPronDao|getMapCargaPron|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaTodos() {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, CURSO_CARGA_ID AS VALOR FROM ENOC.CARGA_PRON"; 			
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa objeto : lista){		
				mapa.put(objeto.getLlave() , objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPronDao|mapTodos|:"+ex);
		}
		return mapa;
	}
}
