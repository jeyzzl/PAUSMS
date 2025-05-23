// Clase Util para la tabla de Carga
package aca.est.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstMaestroDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( EstMaestro maestro ) {		
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EST_MAESTRO(CODIGO_PERSONAL, IMPORTE, HORAS,TIPO) "+
				"VALUES(?, TO_NUMBER(?,'999999.99'), TO_NUMBER(?,'999.99'),?";
			Object[] parametros = new Object[] {maestro.getCodigoPersonal(), maestro.getImporte(), maestro.getHoras(), maestro.getTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg( EstMaestro maestro ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EST_MAESTRO"
				+ " SET IMPORTE = TO_NUMBER(?,'999999.99'),"							
				+ " HORAS = TO_NUMBER(?,'999.99'),"
				+ " TIPO = ?" 
				+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {maestro.getImporte(), maestro.getHoras(), maestro.getTipo(), maestro.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EST_MAESTRO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public EstMaestro mapeaRegId( String codigoPersonal ) {
		
		EstMaestro maestro = new EstMaestro();
		try{
			String comando = "SELECT CODIGO_PERSONAL, IMPORTE, HORAS, TIPO"
					+ " FROM ENOC.EST_MAESTRO"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			maestro = enocJdbc.queryForObject(comando, new EstMaestroMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|mapeaRegId|:"+ex);
		}
		
		return maestro;
	}	
	
	public boolean existeReg(String codigoPersonal) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EST_MAESTRO"
					+ " WHERE CODIGO_PERSONAL = ?";					
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	
	public List<EstMaestro> lisAll( String orden ) {
		
		List<EstMaestro> lista	= new ArrayList<EstMaestro>();	
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, IMPORTE, HORAS, TIPO"
					+ " FROM ENOC.EST_MAESTRO "+orden; 
			lista = enocJdbc.query(comando, new EstMaestroMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,EstMaestro> mapaTodos( ) {
		
		HashMap<String,EstMaestro> mapa = new HashMap<String,EstMaestro>();
		List<EstMaestro> lista	= new ArrayList<EstMaestro>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, IMPORTE, HORAS, TIPO FROM ENOC.EST_MAESTRO";
			lista = enocJdbc.query(comando, new EstMaestroMapper());
			for (EstMaestro maestro : lista ) {
				mapa.put(maestro.getCodigoPersonal(), maestro );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.est.spring.EstMaestroDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}

}