package aca.idioma.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MsgEspDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MsgEsp esp){
		boolean ok = false;		
		try{
			String comando = " INSERT INTO ENOC.MSG_ES(CLAVE, VALOR) VALUES(?, ?)";
			Object[] parametros = new Object[] {esp.getClave(), esp.getValor() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgEspDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg(String clave){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MSG_ES WHERE CLAVE = ? ";			
			Object[] parametros = new Object[] {clave };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgEspDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodos(){
		boolean ok = false;		
		try{
			String comando = " DELETE FROM ENOC.MSG_ES ";
			if (enocJdbc.update(comando) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgEspDao|deleteTodo|:"+ex);
		}
		return ok;
	}
	
	public MsgEsp mapeaRegId( String clave ){
		MsgEsp esp = new MsgEsp();
		try{
			String comando = "SELECT CLAVE, VALOR FROM ENOC.MSG_ES WHERE CLAVE = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, clave) >= 1){
				comando = "SELECT CLAVE, VALOR FROM ENOC.MSG_ES WHERE CLAVE = ?";
				esp = enocJdbc.queryForObject(comando,new MsgEspMapper(), clave);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgEspDao|mapeaRegId|:"+ex);
		}
		return esp;
	}
	
	public HashMap<String, String> mapTodos(){
		
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CLAVE AS LLAVE, VALOR FROM ENOC.MSG_ES ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgEspDao|mapTodos|:"+ex);
		}
		
		return mapa;
	}
}