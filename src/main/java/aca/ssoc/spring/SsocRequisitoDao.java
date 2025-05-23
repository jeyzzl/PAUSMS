package aca.ssoc.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.ssoc.RequisitoVO;

@Component
public class SsocRequisitoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(SsocRequisito requisito){
    	boolean grabo = false;
    	try{
    	    String comando="INSERT INTO ENOC.SSOC_REQUISITOS "
    	    		+ " (REQUISITO_ID, REQUISITO_NOMBRE, ORDEN) "
    	    		+ " VALUES (?,?,?)";
    	    Object[] parametros = new Object[] {requisito.getRequisitoId(), requisito.getRequisitoNombre(), requisito.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				grabo = true;
			}   	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|insertReg|:"+ex);
    	}    	
    	return grabo;
    }
	
	public boolean updateReg( SsocRequisito requisito) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SSOC_REQUISITOS"
					+ " SET REQUISITO_NOMBRE = ?,"
					+ " ORDEN = ?"
					+ " WHERE REQUISITO_ID = ?";
			Object[] parametros = new Object[] {requisito.getRequisitoNombre(), 
					requisito.getOrden(), requisito.getRequisitoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String requisitoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SSOC_REQUISITOS WHERE REQUISITO_ID = ?";
			Object[] parametros = new Object[] {requisitoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public SsocRequisito mapeaRegId(String requisitoId) {		
		SsocRequisito objeto = new SsocRequisito();
		
		try{
			String comando = "SELECT REQUISITO_ID, REQUISITO_NOMBRE, ORDEN "
					+ " FROM ENOC.SSOC_REQUISITOS "
					+ " WHERE REQUISITO_ID = ?";
			Object[] parametros = new Object[] {requisitoId};
			objeto = enocJdbc.queryForObject(comando, new SsocRequisitoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeReg(String requisitoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_REQUISITOS WHERE REQUISITO_ID = ?";
			Object[] parametros = new Object[] {requisitoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando =  "SELECT COALESCE(MAX(REQUISITO_ID),0)+1 FROM ENOC.SSOC_REQUISITOS";
			if (enocJdbc.queryForObject(comando, Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando, String.class);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<SsocRequisito> getListAll(String orden) {
		
		List<SsocRequisito> lista 		= new ArrayList<SsocRequisito>();
		
		try{
			String comando = "SELECT REQUISITO_ID, REQUISITO_NOMBRE, ORDEN FROM ENOC.SSOC_REQUISITOS "+orden;
			lista = enocJdbc.query(comando, new SsocRequisitoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocRequisitoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SsocRequisito> getRequisitos(String orden) {
        List<SsocRequisito> requisitos	= new ArrayList<SsocRequisito>();
        String comando = "";
    	
    	try{
    	    comando="select * from ENOC.SSOC_REQUISITOS " +orden; 
    	    requisitos = enocJdbc.query(comando, new SsocRequisitoMapper());
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Requisitos|getRequisitos|:"+ex);
    	}
    	
    	return requisitos;
    }
}