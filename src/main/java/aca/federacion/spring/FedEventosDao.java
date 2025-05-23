package aca.federacion.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FedEventosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public FedEventos mapeaRegId( String eventoId ){
		FedEventos objeto = new FedEventos();
 		
 		try{
	 		String comando = "SELECT EVENTO_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN," +
	 			" EVENTO_NOMBRE, EVENTO_DESCRIPCION, TIPO FROM ENOC.FED_EVENTO WHERE EVENTO_ID = ?"; 

	 		Object[] parametros = new Object[] {
 				eventoId
	 		};
	 		objeto = enocJdbc.queryForObject(comando, new FedEventosMapper(), parametros);
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedEventosDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

 		return objeto;
 	} 	
		
	public List<FedEventos> getListAll(){
		List<FedEventos> lista= new ArrayList<FedEventos>();
		
		try{
			String comando = " SELECT EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO" +
				             " FROM ENOC.FED_EVENTO "; 
			
			lista = enocJdbc.query(comando, new FedEventosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedEventosDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<FedEventos> getListEventosActuales() {
		List<FedEventos> lista= new ArrayList<FedEventos>();
		
		try{
			String comando = " SELECT EVENTO_ID, EVENTO_NOMBRE, EVENTO_DESCRIPCION, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, TIPO"
					+ " FROM ENOC.FED_EVENTO  WHERE NOW() BETWEEN FECHA_INI AND FECHA_FIN "; 
			
			lista = enocJdbc.query(comando, new FedEventosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedEventosDao|getListEventosActuales|:"+ex);
		}
		
		return lista;
	}
	
	public boolean eventoPorFecha(String eventoId) {
 		boolean ok = false;
 		
		try{
			String comando = " SELECT * FROM ENOC.FED_EVENTO"					
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'999') AND NOW() BETWEEN FECHA_INI AND FECHA_FIN "; 
			
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedEventosDao|eventoPorFecha|:"+ex);
		}

		return ok;
	}

}
