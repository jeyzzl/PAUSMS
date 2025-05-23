package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalPropositoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( SalProposito salProposito ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_PROPOSITO " + 
					"(PROPOSITO_ID, PROPOSITO_NOMBRE) " +
					" VALUES(TO_NUMBER(?,'99'), ?) ";
			Object[] parametros = new Object[] {salProposito.getPropositoId(), salProposito.getPropositoNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( SalProposito salProposito ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.SAL_PROPOSITO SET PROPOSITO_NOMBRE = ? WHERE PROPOSITO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[]{salProposito.getPropositoNombre(), salProposito.getPropositoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg( String propositoId ) {
    	boolean ok = false;
        try {
             String comando =" DELETE FROM ENOC.SAL_PROPOSITO WHERE PROPOSITO_ID = TO_NUMBER(?, '99')";
             Object[] parametros = new Object[]{propositoId};
             if (enocJdbc.update(comando, parametros) == 1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalPropositoDao|deleteReg|:" + ex);
        }
        return ok;
    }   
    
    public boolean existeReg( String propositoId ) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_PROPOSITO WHERE PROPOSITO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {propositoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public String maximoReg( ){		
		int maximo				= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(PROPOSITO_ID)+1,1) AS MAXIMO FROM ENOC.SAL_PROPOSITO";			
			maximo = enocJdbc.queryForObject(comando,Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}	
    
    public SalProposito mapeaRegId( String propositoId ) {
    	SalProposito salProposito = new SalProposito();
		try{ 
	    	String comando = "SELECT PROPOSITO_ID, PROPOSITO_NOMBRE"
	    			+ " FROM ENOC.SAL_PROPOSITO WHERE PROPOSITO_ID = TO_NUMBER(?,'99')";
	    	Object[] parametros = new Object[] {propositoId};
	    	salProposito = enocJdbc.queryForObject(comando, new SalPropositoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|mapeaRegId|:"+ex);
		}
		return salProposito;
	}
    
    public List<SalProposito> lisTodos( String orden ) {
		List<SalProposito> lista = new ArrayList<SalProposito>();
		String comando	= "";
		
		try{
			comando = "SELECT PROPOSITO_ID, PROPOSITO_NOMBRE"+			        
					" FROM ENOC.SAL_PROPOSITO "+orden;
			lista = enocJdbc.query(comando, new SalPropositoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|listAll|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String, String> mapaPropositos( String propositoId) {		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PROPOSITO_ID AS LLAVE, PROPOSITO_NOMBRE AS VALOR FROM ENOC.SAL_PROPOSITO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalPropositoDao|mapaPropositos|:"+ex);
		}		
		return mapa;
	}
	
}