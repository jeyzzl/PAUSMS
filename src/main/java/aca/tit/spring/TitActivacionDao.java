package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitActivacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public TitActivacion mapeaRegId(String institucion, String inicio, String fin, String sello) {
		TitActivacion antecedente = new TitActivacion();
		 
		try{
			String comando = "SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD,"
					+ " TO_CHAR(FECHAINICIO, 'YYYY-MM-DD') AS FECHAINICIO, TO_CHAR(FECHATERMINACION, 'YYYY-MM-DD') AS FECHATERMINACION, CEDULA"
					+ " FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {institucion,inicio,fin,sello};
			antecedente = enocJdbc.queryForObject(comando, new TitActivacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitActivacionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return antecedente;
		
	}
	
	public boolean existeReg(String institucion, String inicio, String fin, String sello) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_ANTECEDENTE WHERE FOLIO = TO_NUMBER(?,'9999999') "; 
			
			Object[] parametros = new Object[] {institucion,inicio,fin,sello};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitActivacionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitActivacion> listAll(String orden) {
		List<TitActivacion> lista		= new ArrayList<TitActivacion>();
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD, FECHAINICIO, FECHATERMINACION, CEDULA"
					+ " FROM ENOC.TIT_ANTECEDENTE "+orden;	 
			
			lista = enocJdbc.query(comando, new TitActivacionMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.TitActivacionDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_ANTECEDENTE"; 
					
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitActivacionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String, TitActivacion> mapaAll( ) {
		List<TitActivacion> lista			= new ArrayList<TitActivacion>();
		HashMap<String,TitActivacion> mapa	= new HashMap<String,TitActivacion>();	
		
		try{
			String comando	= "SELECT FOLIO, INSTITUCION, ESTUDIOID, ESTUDIO, ENTIDADID, ENTIDAD, FECHAINICIO, FECHATERMINACION, CEDULA"					
					+ " FROM ENOC.TIT_ANTECEDENTE";							
			lista = enocJdbc.query(comando, new TitActivacionMapper());
			
			for (TitActivacion ant : lista){
				mapa.put(ant.getInstitucion(), ant);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitActivacionDao|mapaAll|:"+ex);
		}
		return mapa;
	}

}
