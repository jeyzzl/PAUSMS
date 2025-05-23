package aca.disciplina.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CondJuezDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CondJuez juez ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.COND_JUEZ(IDJUEZ, NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ";
			
			Object[] parametros = new Object[] {juez.getIdJuez(),juez.getNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CondJuez juez ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.COND_JUEZ "+ 
				"SET NOMBRE = ? "+
				"WHERE IDJUEZ = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {juez.getNombre(),juez.getIdJuez()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String idJuez ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.COND_JUEZ "+ 
				"WHERE IDJUEZ = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {idJuez};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CondJuez mapeaRegId(String idJuez) {
		CondJuez juez = new CondJuez();
			
		try{
			String comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ " + 
					"WHERE IDJUEZ = TO_NUMBER(?,'999') ";				
				
			Object[] parametros = new Object[] {idJuez};
			juez = enocJdbc.queryForObject(comando, new CondJuezMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|mapeaRegId|:"+ex);
		}
		return juez;
	}
	
	public boolean existeReg( String idJuez) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COND_JUEZ WHERE IDJUEZ = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {idJuez};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(IDJUEZ)+1 MAXIMO FROM ENOC.COND_JUEZ"; 
						
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String nombreJuez( String idJuez) {
		String nombre 			= "X";
		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.COND_JUEZ WHERE IDJUEZ = TO_NUMBER(?,'99')"; 

			Object[] parametros = new Object[] {idJuez};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.sprinig.CondJuezDao|nombreJuez|:"+ex);
		}
		return nombre;
	}
	
	public List<CondJuez> getListAll( String orden ) {
		List<CondJuez> lista 			= new ArrayList<CondJuez>();		
		try{
			String comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ "+ orden;			
			lista = enocJdbc.query(comando, new CondJuezMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondJuezDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CondJuez> getLista( String idJuez, String orden ) {
		List<CondJuez> lista 			= new ArrayList<CondJuez>();
		String comando	= "";
		
		try{
			comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ WHERE IDJUEZ= ? "+ orden;			
			lista = enocJdbc.query(comando, new CondJuezMapper(), idJuez);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondJuezDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaJuez( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<CondJuez>	lista 		= new ArrayList<CondJuez>();
		String comando		= "";
		
		try{
			comando = "SELECT IDJUEZ, NOMBRE FROM ENOC.COND_JUEZ";
			lista = enocJdbc.query(comando, new CondJuezMapper());
			for (CondJuez juez : lista){
				mapa.put(juez.getIdJuez(), juez.getNombre());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondJuezDao|mapaJuez|:"+ex);
		}
		
		return mapa;
	}	
}