// Clase para la tabla de CandTipo
package aca.candado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CandadoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( Candado cand ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CANDADO(CANDADO_ID, NOMBRE_CANDADO, TIPO_ID) VALUES(?,?,?)"; 
			Object[] parametros = new Object[] {cand.getCandadoId(),cand.getNombreCandado(), cand.getTipoId()};		
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( Candado cand ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CANDADO SET NOMBRE_CANDADO = ? WHERE CANDADO_ID = ? AND TIPO_ID = ?";	
			Object[] parametros = new Object[] {cand.getNombreCandado(),cand.getCandadoId(), cand.getTipoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String candadoId, String tipoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CANDADO WHERE CANDADO_ID = ? AND TIPO_ID = ?"; 
			Object[] parametros = new Object[] {candadoId, tipoId};
						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String tipoId ) {		
		String maximo 			= "0";		
		try{
			String comando = "SELECT COALESCE(MAX(CANDADO_ID)+1,1) AS MAXIMO FROM ENOC.CANDADO WHERE TIPO_ID = ?";
			Object[] parametros = new Object[] {tipoId};
			maximo = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public Candado mapeaRegId( String candadoId) {
		Candado candado = new Candado();		
		try{
			String comando = "SELECT CANDADO_ID, NOMBRE_CANDADO FROM ENOC.CANDADO WHERE CANDADO_ID = ? ";
			Object[] parametros = new Object[] {candadoId};
			candado = enocJdbc.queryForObject(comando, new BeanPropertyRowMapper<Candado>(Candado.class),parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|mapeaRegId|:"+ex);
		}
		return candado;
	}
	
	public boolean existeReg( String candadoId, String tipoId) {
		boolean ok 			= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.CANDADO WHERE CANDADO_ID = ? AND TIPO_ID = ?"; 
			Object[] parametros = new Object[] {candadoId, tipoId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String getNombreCandado( String candadoId) {		
		String nombre			= "";		
		try{
			String comando = "SELECT NOMBRE_CANDADO FROM ENOC.CANDADO WHERE CANDADO_ID = ? ";
			Object[] parametros = new Object[] {candadoId};
			nombre = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.Candado|getNombreCandado|:"+ex);
		}		
		return nombre;
	}
		
	public List<Candado> getListAll( String orden ) {
		
		List<Candado> lista 	= new ArrayList<Candado>();
		String comando	= "";
		
		try{
			comando = "SELECT CANDADO_ID, NOMBRE_CANDADO FROM ENOC.CANDADO "+orden; 
			
			Object[] parametros = new Object[] {orden};	
			lista = enocJdbc.query(comando, new CandadoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<Candado> getLista( String tipoId, String orden ) {
		
		List<Candado> lista 	= new ArrayList<Candado>();
		try{
			String comando = "SELECT CANDADO_ID, NOMBRE_CANDADO, TIPO_ID FROM ENOC.CANDADO WHERE TIPO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new CandadoMapper(), tipoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,Candado> getMapaCandados() {
		
		HashMap<String,Candado> map = new HashMap<String,Candado>();
		List<Candado> lista = new ArrayList<Candado>(); 
		
		try{
			String comando = " SELECT CANDADO_ID, NOMBRE_CANDADO, TIPO_ID FROM ENOC.CANDADO";
			
			lista = enocJdbc.query(comando, new CandadoMapper());
			for (Candado candado : lista ) {
				map.put(candado.getCandadoId()+candado.getTipoId(), candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoDao|getMapaCandado|:"+ex);
		}		
		return map;
	}
	
}