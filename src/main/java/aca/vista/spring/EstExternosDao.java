//Clase para la vista MOD_OPCION

package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstExternosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<EstExternos> getListAll ( String orden) {
		
		List<EstExternos> lista = new ArrayList<EstExternos>();
		
		try {
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, RAZON, FECHA "+
				"FROM ENOC.ESTEXTERNOS "+ orden;
			lista = enocJdbc.query(comando, new EstExternosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstExternosDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<EstExternos> getListAllPorFecha( String fechaIni, String fechaFin, String orden) {
		
		List<EstExternos> lista = new ArrayList<EstExternos>();		
		try {
			String comando = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, RAZON, FECHA FROM ENOC.ESTEXTERNOS "
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+ orden;
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new EstExternosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstExternosDao|getListAllPorFecha|:"+ex);
		}
		return lista;
	}
}