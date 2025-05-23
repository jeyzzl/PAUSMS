package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContEjercicioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean existeReg( String idEjercicio){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT NOMBRE FROM MATEO.FES_CCOBRO WHERE ID_EJERCICIO = ? ";
			Object[] parametros = new Object[] {idEjercicio};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public ContEjercicio mapeaRegId( String idEjercicio) {
		
		ContEjercicio ejercicio = new ContEjercicio();
 		
 		try{
	 		String comando = "SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR" +	 					
	 				" FROM MATEO.CONT_EJERCICIO WHERE ID_EJERCICIO = ?";
	 		Object[] parametros = new Object[] {idEjercicio};
			ejercicio = enocJdbc.queryForObject(comando, new ContEjercicioMapper(),parametros);	
	 		
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicio|mapeaRegId|:"+ex);
		}
 		
 		return ejercicio;
 	}
	
	public String getEjercicioActual( String empresa){
		
		String ejercicioId		= "0";		
		try{
			ejercicioId = empresa+"-"+aca.util.Fecha.getHoy().substring(6,10);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioDao|getEjercicioActual|:"+ex);
		}
		
		return ejercicioId;
	}
	
	public List<ContEjercicio> lisTodos( String orden){
		List<ContEjercicio> lista 	= new ArrayList<ContEjercicio>();		
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR FROM MATEO.CONT_EJERCICIO "+orden;
			lista = enocJdbc.query(comando, new ContEjercicioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioUtil|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<ContEjercicio> getListProximos ( String orden){
		List<ContEjercicio> lista = new ArrayList<ContEjercicio>();		
		String comando = "";
		try{
			comando = "SELECT * FROM MATEO.CONT_EJERCICIO WHERE SUBSTR(ID_EJERCICIO, 5) >= '2013' "+orden;
			lista = enocJdbc.query(comando, new ContEjercicioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioUtil|getListProximos|:"+ex);
		}
		
		return lista; 
	}

	public List<ContEjercicio> getListAll( String orden){
		List<ContEjercicio> lista 	= new ArrayList<ContEjercicio>();		
		String comando	= "";
		try{			
			comando = "SELECT ID_EJERCICIO, NOMBRE, STATUS, MASC_BALANCE, MASC_AUXILIAR, MASC_CCOSTO, NIVEL_CONTABLE, NIVEL_TAUXILIAR FROM MATEO.CONT_EJERCICIO "+orden;
			lista = enocJdbc.query(comando, new ContEjercicioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContEjercicioUtil|getListAll|:"+ex);
		}
		
		return lista;
}	

}