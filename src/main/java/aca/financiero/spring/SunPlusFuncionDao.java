package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SunPlusFuncionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<SunPlusFuncion> lisTodos(String orden){
		
		List<SunPlusFuncion> lista 	= new ArrayList<SunPlusFuncion>();		
		try{			
			String comando = " SELECT DEPARTAMENTO, FUNCION FROM ENOC.SUNPLUS_FUNCION "+orden;
			
			lista = enocJdbc.query(comando, new SunPlusFuncionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.SunPlusFuncionDao|lisTodos|:"+ex);
		}
		return lista;
	}
	
	// Map de funciones
	public HashMap<String,String> mapaFunciones( ) {
		HashMap<String,String> map 	= new HashMap<String,String>();	
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT DEPARTAMENTO AS LLAVE, FUNCION AS VALOR FROM ENOC.SUNPLUS_FUNCION";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.SunPlusFuncionDao|mapaFunciones|:"+ex);
		}
		
		return map;
	}
	
}