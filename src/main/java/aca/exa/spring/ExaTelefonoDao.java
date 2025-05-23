package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaTelefonoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaTelefono exaTelefono ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.EXA_TELEFONO(TELEFONO_ID, MATRICULA, TIPO, TELEFONO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDTELEFONO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )";
			
			Object[] parametros = new Object[]{
				exaTelefono.getTelefonoId(),exaTelefono.getMatricula(),exaTelefono.getTipo(),exaTelefono.getTelefono(),exaTelefono.getFechaAct(),
				exaTelefono.getEliminado(),exaTelefono.getIdTelefono()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean eliminar(String telefonoId ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_TELEFONO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE TELEFONO_ID = TO_NUMBER(?,'99999999')";
				if (enocJdbc.update(comando,telefonoId)==1){
					ok = true;
				}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|eliminar|:"+ex);		
		}
		
		return ok;
	}
	
	public ExaTelefono mapeaRegIdEstudio( String telefonoId){
		ExaTelefono exaTelefono = new ExaTelefono();
		 
		try{
			String comando = "SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO,"
					+ " FECHAACTUALIZACION, ELIMINADO, IDTELEFONO"
					+ " FROM ENOC.EXA_TELEFONO WHERE TELEFONO_ID = TO_NUMBER(?,'99999999')";
			
				exaTelefono = enocJdbc.queryForObject(comando, new ExaTelefonoMapper(), telefonoId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaTelefono;
	}
	
	public ExaTelefono mapeaRegId( String matricula){
		ExaTelefono exaTelefono = new ExaTelefono();
		 
		try{
			String comando = "SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO "+
				"FROM ENOC.EXA_TELEFONO WHERE MATRICULA = ?"; 
				exaTelefono = enocJdbc.queryForObject(comando, new ExaTelefonoMapper(), matricula);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaTelefono;
	}
	
	public boolean existeReg(String telefonoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_TELEFONO WHERE TELEFONO_ID = TO_NUMBER(?,'99999999') "; 
			if (enocJdbc.queryForObject(comando,Integer.class, telefonoId) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_TELEFONO WHERE MATRICULA = ? AND ELIMINADO!=1 "; 
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String matricula){
		int maximo 			= 1;
		try{
			String comando = "SELECT MAX(TELEFONO_ID)+1 AS MAXIMO FROM ENOC.EXA_TELEFONO WHERE ELIMINADO !=1 AND MATRICULA = ?"; 
			maximo = enocJdbc.queryForObject(comando,Integer.class, matricula);	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String maximoReg(){
		int maximo 			= 1;
		
		try{
			String comando = "SELECT MAX(TELEFONO_ID)+1 AS MAXIMO FROM ENOC.EXA_TELEFONO";
			maximo = enocJdbc.queryForObject(comando,Integer.class);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public List<ExaTelefono> getTelefono(String matricula, String orden){
		List<ExaTelefono> lista		= new ArrayList<ExaTelefono>();
		
		try{
			String comando = "SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO, FECHAACTUALIZACION, ELIMINADO, IDTELEFONO FROM ENOC.EXA_TELEFONO" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;			
			lista = enocJdbc.query(comando, new ExaTelefonoMapper(), matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|getTelefono|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, ExaTelefono> getMapTelefono(String orden){
		HashMap<String, ExaTelefono> mapa		= new HashMap<String, ExaTelefono>();
		List<ExaTelefono> lista		= new ArrayList<ExaTelefono>();		
		try{
			String comando = "SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO,FECHAACTUALIZACION, ELIMINADO, IDTELEFONO"
					+ " FROM ENOC.EXA_TELEFONO WHERE ELIMINADO != 1 "+orden;			
			lista = enocJdbc.query(comando, new ExaTelefonoMapper());			
			for(ExaTelefono objeto : lista) {
				mapa.put(objeto.getMatricula(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaTelefonoDao|getMapTelefono|:"+ex);
		}
		
		return mapa;
	}
}
