package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaEstadoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaEstado exaEstado ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EXA_ESTADO"+ 
				"(PAIS_ID, ESTADO_ID, ESTADO_NOMBRE) "+
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'9999'), ?)";
				
			Object[] parametros = new Object[]{
				exaEstado.getPaisId(),exaEstado.getEstadoId(),exaEstado.getNombreEstado()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|insertReg|:"+ex);
		}
		return ok;
	}
			
	public boolean updateReg(ExaEstado exaEstado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_ESTADO "+ 
				"SET ESTADO_NOMBRE = ? "+
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')";
			
			Object[] parametros = new Object[]{
				exaEstado.getNombreEstado(),exaEstado.getPaisId(),exaEstado.getEstadoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|updateReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg(String paisId, String estadoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EXA_ESTADO "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')";
			
			Object[] parametros = new Object[]{
				paisId,estadoId
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|deleteReg|:"+ex);
			ok = false;
		}
				
		return ok;
	}

	public ExaEstado mapeaRegId( String paisId, String estadoId ){
		ExaEstado exaEstado = new ExaEstado();
		 
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[]{
				paisId,estadoId
			};
			exaEstado = enocJdbc.queryForObject(comando, new ExaEstadoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaEstado;
	}
	
	public boolean existeReg(String paisId, String estadoId){
		boolean 		ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_ESTADO "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')";
			
			Object[] parametros = new Object[]{
				paisId,estadoId
			};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String paisId){
		int maximo 			= 1;
		
		try{
			String comando = "SELECT MAX(ESTADO_ID)+1 AS MAXIMO FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999')"; 
			maximo = enocJdbc.queryForObject(comando,Integer.class, paisId);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String getNombreEstado(String paisId, String estadoId){
		String nombre			= "vacío";
		
		try{
			String comando = "SELECT ESTADO_NOMBRE FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'9999')"; 
			
			Object[] parametros = new Object[]{
				paisId,estadoId
			};
			
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|getNombreEstado|:"+ex);
		}
		
		return nombre;
	}
		
	public List<ExaEstado> getLista(String paisId, String orden ){
		List<ExaEstado> lista	= new ArrayList<ExaEstado>();		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE FROM ENOC.EXA_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') "+ orden;			
			lista = enocJdbc.query(comando, new ExaEstadoMapper(), paisId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<ExaEstado> getListAll(String orden ){
		List<ExaEstado> lista	= new ArrayList<ExaEstado>();
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO "+ orden; 
			
			lista = enocJdbc.query(comando, new ExaEstadoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,ExaEstado> getMapAll(String orden ){
		HashMap<String,ExaEstado> map = new HashMap<String,ExaEstado>();
		List<ExaEstado> lista	= new ArrayList<ExaEstado>();
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, ESTADO_NOMBRE "+
				"FROM ENOC.EXA_ESTADO "+ orden;
			
			lista = enocJdbc.query(comando, new ExaEstadoMapper());
			
			for(ExaEstado objeto : lista) {
				map.put(objeto.getPaisId()+objeto.getEstadoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|getMapAll|:"+ex);
		}
		
		return map;
	}
	
	public String getEstado(String paisId, String estadoId){
		String nombre		= "No encontro";
		
		try{
			String comando = "SELECT ESTADO_NOMBRE FROM ENOC.EXA_ESTADO where estado_id = "+estadoId+" and pais_id = "+paisId; 
			nombre = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaEstadoDao|getEstado|:"+ex);
		}
		
		return nombre;
	}		
}