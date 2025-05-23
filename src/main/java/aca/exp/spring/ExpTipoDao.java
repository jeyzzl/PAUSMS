package aca.exp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExpTipoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(String codigoPersonal, String tipo, String estado){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.EXP_TIPO(CODIGO_PERSONAL, TIPO, ESTADO) VALUES(?, ?, ?)";
			Object[] parametros = new Object[] {codigoPersonal, tipo, estado};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateTipo(String codigoPersonal, String tipo){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.EXP_TIPO SET TIPO = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {tipo, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|updateTipo|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateEstado(String codigoPersonal, String estado){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.EXP_TIPO SET ESTADO = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {estado, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|updateEstado|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXP_TIPO WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public ExpTipo mapeaRegId(String codigoPersonal) {
		ExpTipo emp = new ExpTipo();			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXP_TIPO WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL, TIPO, ESTADO FROM ENOC.EXP_TIPO WHERE CODIGO_PERSONAL = ?";
				emp = enocJdbc.queryForObject(comando, new ExpTipoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|mapeaRegId|:"+ex);
		}
		
		return emp;
	}
	
	public HashMap<String,ExpTipo> mapaTipos(){
		HashMap<String,ExpTipo> mapa	= new HashMap<String, ExpTipo>();
		List<ExpTipo> lista	    	= new ArrayList<ExpTipo>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO, ESTADO FROM ENOC.EXP_TIPO";
			lista = enocJdbc.query(comando,new ExpTipoMapper());
			for(ExpTipo tipo : lista){				
				mapa.put(tipo.getCodigoPersonal(), tipo);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|mapaTipos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaTodos( ){
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TIPO AS VALOR FROM ENOC.EXP_TIPO";
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpTipoDao|mapaTodos|:"+ex);
		}		
		return mapa;
	}
}