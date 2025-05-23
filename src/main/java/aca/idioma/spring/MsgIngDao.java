package aca.idioma.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MsgIngDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MsgIng ing){
		boolean ok = false;		
		try{
			String comando = " INSERT INTO ENOC.MSG_EN(CLAVE, VALOR) VALUES(?, ?)";
			Object[] parametros = new Object[] {ing.getClave(), ing.getValor() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgIngDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg(String clave){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MSG_EN WHERE CLAVE = ? ";			
			Object[] parametros = new Object[] {clave };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgIngDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodos(){
		boolean ok = false;		
		try{
			String comando = " DELETE FROM ENOC.MSG_EN ";
			if (enocJdbc.update(comando) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgIngDao|deleteTodo|:"+ex);
		}
		return ok;
	}
	
	public MsgIng mapeaRegId( String clave ){
		MsgIng ing = new MsgIng();
		try{
			String comando = "SELECT CLAVE, VALOR FROM ENOC.MSG_EN WHERE CLAVE = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, clave) >= 1){
				comando = "SELECT CLAVE, VALOR FROM ENOC.MSG_ES WHERE CLAVE = ?";
				ing = enocJdbc.queryForObject(comando,new MsgIngMapper(), clave);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgIngDao|mapeaRegId|:"+ex);
		}
		return ing;
	}
	
	public HashMap<String, String> mapTodos(){
		
		HashMap<String, String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CLAVE AS LLAVE, VALOR  FROM ENOC.MSG_EN ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.idioma.spring.MsgIngDao|mapTodos|:"+ex);
		}
		
		return mapa;
	}
}