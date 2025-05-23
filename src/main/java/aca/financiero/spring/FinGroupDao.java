package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.financiero.spring.FinGroup;
import aca.financiero.spring.FinGroupMapper;

@Component
public class FinGroupDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinGroup financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_GROUP "
					+ " (GROUP_ID, NAME, DESCRIPTION) "
					+ " VALUES( TO_NUMBER(?), ?, ?)";
			Object[] parametros = new Object[] {
						financiero.getGroupId(), financiero.getName(), financiero.getDescription()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( FinGroup financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_GROUP"
					+ " SET NAME = ?,"
					+ " DESCRIPTION = ?"					
					+ " WHERE GROUP_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {
				financiero.getName(), financiero.getDescription(), financiero.getGroupId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String groupId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_GROUP "
					+ " WHERE GROUP_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public FinGroup mapeaRegId( String groupId ) {
		
		FinGroup objeto = new FinGroup();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_GROUP WHERE GROUP_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT *"
						+ " FROM ENOC.FIN_GROUP"
						+ " WHERE GROUP_ID = TO_NUMBER(?)";
				objeto = enocJdbc.queryForObject(comando, new FinGroupMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String groupId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_GROUP"
					+ " WHERE GROUP_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|existeReg|:"+ex);
		}
		return ok;
	}	

	public int maximoReg() {
		
		int maximo			= 0;
		
		try{
			String comando = "SELECT COALESCE(MAX(GROUP_ID)+1,1) AS MAXIMO FROM ENOC.FIN_GROUP";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<FinGroup> getListAll( String orden ) {
		
		List<FinGroup> lista = new ArrayList<FinGroup>();		
		try{
			String comando = "SELECT * "
					+ " FROM ENOC.FIN_GROUP "+orden;
			lista = enocJdbc.query(comando, new FinGroupMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupDao|getListAll|:"+ex);
		}
		return lista;
	}
	
}