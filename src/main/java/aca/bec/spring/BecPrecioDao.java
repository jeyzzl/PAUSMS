package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecPrecioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BecPrecio becPrecio) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PRECIO (ID_EJERCICIO, PRECIO) VALUES( ?, ?)";
					
			Object[] parametros = new Object[] {becPrecio.getIdEjercicio(),becPrecio.getPrecio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(BecPrecio becPrecio) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_PRECIO SET PRECIO = ? WHERE ID_EJERCICIO = ?";
			
			Object[] parametros = new Object[] {becPrecio.getPrecio(),becPrecio.getIdEjercicio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String idEjercicio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_PRECIO WHERE ID_EJERCICIO = ?";
			
			Object[] parametros = new Object[] {idEjercicio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String idEjercicio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PRECIO WHERE ID_EJERCICIO = ?"; 
				
			Object[] parametros = new Object[] {idEjercicio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public BecPrecio mapeaRegId(String idEjercicio){
		BecPrecio becPrecio = new BecPrecio();
		
		try{
			String comando = "SELECT ID_EJERCICIO, PRECIO FROM ENOC.BEC_PRECIO WHERE ID_EJERCICIO = ? "; 
			Object[] parametros = new Object[] {idEjercicio};
			becPrecio = enocJdbc.queryForObject(comando, new BecPrecioMapper(),parametros );
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|mapeaRegId|:"+ex);
		}
		return becPrecio;
	}
	
	public List<BecPrecio> getListAll(String orden) {
		List<BecPrecio> lista = new ArrayList<BecPrecio>();
		
		try{
			String comando = "SELECT ID_EJERCICIO, PRECIO FROM ENOC.BEC_PRECIO "+orden;
			
			lista = enocJdbc.query(comando, new BecPrecioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaPrecios() {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
				
		try{
			String comando = "SELECT ID_EJERCICIO AS LLAVE, PRECIO AS VALOR FROM ENOC.BEC_PRECIO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPrecioDao|mapaPrecios|:"+ex);
		}
		
		return mapa;
	}
	
}