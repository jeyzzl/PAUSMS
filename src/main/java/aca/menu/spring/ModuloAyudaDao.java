package aca.menu.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ModuloAyudaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public ModuloAyuda mapeaRegId( String opcionId, String ayudaId) {
		ModuloAyuda ayuda = new ModuloAyuda();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_AYUDA WHERE OPCION_ID = ? AND AYUDA_ID = ?";
			Object[] parametros = new Object[] {opcionId, ayudaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT OPCION_ID, AYUDA_ID, AYUDA FROM ENOC.MODULO_AYUDA WHERE OPCION_ID = ? AND AYUDA_ID = ?";
				ayuda = enocJdbc.queryForObject(comando, new ModuloAyudaMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloAyudaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return ayuda;
	}
	
	public boolean existeReg(String opcionId, String ayudaId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_AYUDA WHERE OPCION_ID = ? AND AYUDA_ID = ?";
			
			Object[] parametros = new Object[] {opcionId,ayudaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloAyudaDao|existeReg|:"+ex);
		}
		return ok;
	}	
}

