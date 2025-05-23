package aca.unav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ColumnaUnavDao {
/*	
	@Autowired
	@Qualifier("jdbcUnav")
	private JdbcTemplate enocUnav;	
	
	public boolean existeReg(String usuario, String tableName, String columnName){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = ? AND TABLE_NAME = ? AND COLUMN_NAME = ?";
			Object[] parametros = new Object[] {usuario, tableName, columnName};
			if (enocUnav.queryForObject(comando, Integer.class, parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.unav.ColumnaDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<Columna> lisPorUsuario(String usuario, String orden){
		List<Columna> lista 	= new ArrayList<Columna>();		
		try{
			String comando = "SELECT OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COALESCE(DATA_PRECISION,0) AS DATA_PRECISION, DATA_DEFAULT, DATA_SCALE, NULLABLE"
					+ " FROM SYS.ALL_TAB_COLUMNS "
					+ " WHERE OWNER = ?"
					+ " AND TABLE_NAME NOT LIKE '%$%'"
					+ " AND TABLE_NAME IN (SELECT TABLE_NAME FROM SYS.DBA_TABLES WHERE OWNER = ?)"+orden;			
			lista = enocUnav.query(comando, new ColumnaMapper(), usuario, usuario);			
		}catch(Exception ex){
			System.out.println("Error - aca.unav.ColumnaDao|lisPorUsuario|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,Columna> mapaPorUsuario(String usuario){	
		HashMap<String,Columna> mapa = new HashMap<String,Columna>();
		List<Columna> lista		= new ArrayList<Columna>();
		
		try{
			String comando = "SELECT OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COALESCE(DATA_PRECISION,0) AS DATA_PRECISION, DATA_DEFAULT, DATA_SCALE, NULLABLE"
					+ " FROM SYS.ALL_TAB_COLUMNS"
					+ " WHERE OWNER = ?"
					+ " AND TABLE_NAME IN (SELECT TABLE_NAME FROM SYS.DBA_TABLES WHERE OWNER = ?)";
			lista = enocUnav.query(comando, new ColumnaMapper(), usuario, usuario);
			for (Columna objeto : lista ) {
				mapa.put(objeto.getOwner()+objeto.getTableName()+objeto.getColumnName(), objeto);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.ColumnaDao|mapaNumSector|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
*/	
}
