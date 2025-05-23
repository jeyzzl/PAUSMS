package aca.sep.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SepLugarDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
		
	public SepLugar mapeaRegId( String lugarId){
		
		SepLugar sep = new SepLugar();
		try{
			String comando = "SELECT LUGAR_ID, LUGAR_NOMBRE, ORDEN "
					+ " FROM ENOC.SEP_LUGAR WHERE LUGAR_ID = TO_NUMBER(?,'99') "; 
			Object[] parametros = new Object[] {lugarId};
 			sep = enocJdbc.queryForObject(comando, new SepLugarMapper(), parametros);			
						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepLugarDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return sep;
	}
	
	public boolean insertReg(SepLugar lugar ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SEP_LUGAR "
					+ " (LUGAR_ID, LUGAR_NOMBRE, ORDEN) "
					+ " VALUES( TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99.99'))";
			
			Object[] parametros = new Object[] {lugar.getLugarId(), lugar.getLugarNombre(), lugar.getOrden()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepLugarDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(SepLugar lugar ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SEP_LUGAR "
				+ " SET LUGAR_NOMBRE = ?, "
				+ " ORDEN = ?, "
				+ " WHERE LUGAR_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {lugar.getLugarNombre(), lugar.getOrden(), lugar.getLugarId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepLugarDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public List<SepLugar> lisAll(String orden ) {
		List<SepLugar> lista = new ArrayList<SepLugar>();
		String comando	= "";
		
		try{
			comando = "SELECT LUGAR_ID, LUGAR_NOMBRE, ORDEN FROM SEP_LUGAR " +orden;
			lista = enocJdbc.query(comando, new SepLugarMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepLugarDao|lisSepLugares|:"+ex);
		}
		return lista;
	}
	
}
