package aca.unav;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TablaUnavDao {
/*	
	@Autowired
	@Qualifier("jdbcUnav")
	private JdbcTemplate enocUnav;	
	
	public boolean existeReg(String usuario, String tableName){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SYS.DBA_TABLES WHERE OWNER = ? AND TABLE_NAME = ?";
			Object[] parametros = new Object[] {usuario, tableName};
			if (enocUnav.update(comando, Integer.class, parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.unav.TableUnavDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<Tabla> lisPorUsuario(String usuario, String orden){
		List<Tabla> lista 	= new ArrayList<Tabla>();		
		try{
			String comando = "SELECT OWNER, TABLE_NAME FROM SYS.DBA_TABLES WHERE OWNER = ? "+orden;			
			lista = enocUnav.query(comando, new TablaMapper(), usuario);			
		}catch(Exception ex){
			System.out.println("Error - aca.unav.TableUnavDao|lisPorUsuario|:"+ex);
		}		
		return lista;
	}
*/	
}
