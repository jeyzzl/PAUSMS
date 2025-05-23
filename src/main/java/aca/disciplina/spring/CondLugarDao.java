package aca.disciplina.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CondLugarDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CondLugar lugar ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.COND_LUGAR(IDLUGAR, NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ";
					
			Object[] parametros = new Object[] {lugar.getIdLugar(),lugar.getNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CondLugar lugar ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.COND_LUGAR "+ 
				"SET NOMBRE = ? "+
				"WHERE IDLUGAR = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {lugar.getNombre(),lugar.getIdLugar()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String idLugar ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.COND_LUGAR "+ 
				"WHERE IDLUGAR = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {idLugar};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CondLugar mapeaRegId( String idLugar) {
		CondLugar lugar 		= new CondLugar();
		
		try{
			String comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR " + 
					"WHERE IDLUGAR = TO_NUMBER(?,'999') ";				
				
				Object[] parametros = new Object[] {idLugar};
				lugar = enocJdbc.queryForObject(comando, new CondLugarMapper(), parametros);
	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|mapeaRegId|:"+ex);
		}
		return lugar;
	}
	
	public boolean existeReg( String idLugar) {
		boolean 		ok 	= false;
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COND_LUGAR WHERE IDLUGAR = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {idLugar};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
				
		try{
			String comando = "SELECT MAX(IDLUGAR)+1 MAXIMO FROM ENOC.COND_LUGAR"; 
			
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String nombreLugar( String idLugar) {
		String nombre 			= "";		
		
		try{
			String comando = "SELECT COALESCE(NOMBRE,'X')NOMBRE FROM ENOC.COND_LUGAR WHERE IDLUGAR = TO_NUMBER(?,'99')"; 
						
			Object[] parametros = new Object[] {idLugar};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|nombreLugar|:"+ex);
		}
		return nombre;
	}
	
	public List<CondLugar> getListAll( String orden ) {
		List<CondLugar> lista 			= new ArrayList<CondLugar>();
		String comando	= "";
		
		try{
			comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR "+ orden; 
			
			lista = enocJdbc.query(comando, new CondLugarMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CondLugar> getLista( String idLugar, String orden ) {
		List<CondLugar> lista 			= new ArrayList<CondLugar>();
		String comando	= "";		
		try{
			comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR WHERE IDLUGAR = ? "+ orden;			
			lista = enocJdbc.query(comando, new CondLugarMapper(), idLugar);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaLugar( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<CondLugar>	lista 		= new ArrayList<CondLugar>();
		String comando		= "";
		
		try{
			comando = "SELECT IDLUGAR, NOMBRE FROM ENOC.COND_LUGAR";
			lista = enocJdbc.query(comando, new CondLugarMapper());
			for (CondLugar lugar : lista){
				mapa.put(lugar.getIdLugar(), lugar.getNombre());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondLugarDao|mapaLugar|:"+ex);
		}
		
		return mapa;
	}
}