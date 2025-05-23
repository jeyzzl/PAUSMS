//Clase para la vista MAESTRO_GRUPOS

package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MaestroGrupoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public List<MaestroGrupo> getListAll( String orden ){
			
		List<MaestroGrupo> lista	= new ArrayList<MaestroGrupo>();
		
		try{
			String comando = "SELECT MAESTRO_CARRERA, CODIGO_PERSONAL, CARRERA_ID, " +
					"NUM_GRUPOS FROM ENOC.MAESTRO_GRUPOS "+orden;
			lista = enocJdbc.query(comando, new MaestroGrupoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestroGrupoUtil|getListAll|:"+ex);
		}
		
		return lista;
	}	
	
	public List<MaestroGrupo> getLista( String codigoPersonal, String orden ){
		
		List<MaestroGrupo> lista	= new ArrayList<MaestroGrupo>();
		
		try{
			String comando = "SELECT MAESTRO_CARRERA, CODIGO_PERSONAL, CARRERA_ID, "+
					"NUM_GRUPOS FROM ENOC.MAESTRO_GRUPOS "+
					"WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new MaestroGrupoMapper(),codigoPersonal);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestroGrupoUtil|getLista|:"+ex);
		}
	
		return lista;
	}	
}