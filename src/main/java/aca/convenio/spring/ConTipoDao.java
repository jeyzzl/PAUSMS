package aca.convenio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConTipoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ConTipo conv) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CON_TIPO (TIPO_ID, TIPO_NOMBRE) VALUES(TO_NUMBER(?,'99'),?)";			
			Object[] parametros = new Object[]{
				conv.getTipoId(),conv.getTipoNombre()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ConTipo conv ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CON_TIPO SET TIPO_NOMBRE = ? WHERE TIPO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {conv.getTipoNombre(),conv.getTipoId()};			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String id) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CON_TIPO WHERE TIPO_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] { id };			
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg( String id) {
		boolean ok 	= false;				
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CON_TIPO WHERE TIPO_ID = ? ";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public ConTipo mapeaRegId( String id ) {
		ConTipo objeto = new ConTipo();		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CON_TIPO WHERE TIPO_ID = ? ";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				comando = "SELECT TIPO_ID, TIPO_NOMBRE FROM ENOC.CON_TIPO WHERE TIPO_ID = TO_NUMBER(?,'99')";				
				objeto = enocJdbc.queryForObject(comando, new ConTipoMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|mapeaRegId|:"+ex);
		}		
		return objeto;		
	}
	
	public String maximoReg( ) {
		int maximo = 1;		
		try{
			String comando = "SELECT COALESCE(MAX(TIPO_ID)+1,1) FROM ENOC.CON_TIPO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);		
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|mapeaRegId|:"+ex);
		}
		
		return String.valueOf(maximo);		
	}
	
	public List<ConTipo> lisTodos( String orden ) {
		List<ConTipo> lista	= new ArrayList<ConTipo>();
		try{
			String comando = " SELECT TIPO_ID, TIPO_NOMBRE FROM ENOC.CON_TIPO "+orden;				
			lista = enocJdbc.query(comando, new ConTipoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,ConTipo> mapaConTipo() {
		HashMap<String,ConTipo> mapa = new HashMap<String,ConTipo>();
		List<ConTipo> lista 	= new ArrayList<ConTipo>();
		
		try{
			String comando = "SELECT TIPO_ID, TIPO_NOMBRE FROM ENOC.CON_TIPO"; 
			
			lista = enocJdbc.query(comando, new ConTipoMapper());
			for(ConTipo objeto : lista){				
				mapa.put(objeto.getTipoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.convenio.spring.ConvTipoDao|mapaConTipo|:"+ex);
		}
		
		return mapa;		
	}
	
}