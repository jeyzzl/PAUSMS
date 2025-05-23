package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContCcostoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public ContCcosto mapeaRegId(String ejercicioId, String ccostoId) {
		ContCcosto costo = new ContCcosto();
 		try{
	 		String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC" +	 					
	 				" FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
	 		
	 		Object[] parametros = new Object[] {ejercicioId, ccostoId};
	 		costo = enocJdbc.queryForObject(comando, new ContCcostoMapper(), parametros);
	 		
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|mapeaRegId|:"+ex);
		}
 		
 		return costo;
 	}
	
	public boolean existeReg(String ejercicioId, String ccostoId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
			
			Object[] parametros = new Object[] {ejercicioId, ccostoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1) {
 				ok = true;
 			} 		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombre( String ejercicioId, String ccostoId){
 		String nombre 	= "-";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
 			Object[] parametros = new Object[] {ejercicioId, ccostoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1) {
 				comando = "SELECT NOMBRE FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?";
 				nombre 	= enocJdbc.queryForObject(comando, String.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.financiero.spring.ContCcostoDao|getNumPuestosAlumno|:"+ex);
 		}
 		
 		return nombre;
 	}
	
	// Obtiene la lista de departamentos a la que no tiene acceso un usuario
	public List<ContCcosto> listCentrosCostoUsuario(String ejercicioId, String usuario, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND ID_CCOSTO IN (SELECT ID_CCOSTO FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = ? AND CODIGO_PERSONAL = ?) " +orden;
			Object[] parametros = new Object[] {ejercicioId, ejercicioId, usuario};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	public List<ContCcosto> getListCentrosCostoVacantes(String ejercicioId, String detalle, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ? " +
					"AND DETALLE IN("+detalle+") AND ID_CCOSTO IN (SELECT ID_CCOSTO FROM ENOC.BEC_PLAZAS WHERE (NUM_PLAZAS+NUM_TEMPORALES" +
					"+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES)>0) "+orden;
			lista = enocJdbc.query(comando, new ContCcostoMapper(), ejercicioId);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|getListCentrosCostoVacantes|:"+ex);
		}
		
		return lista;
	}
	
	public List<ContCcosto> getListCentrosCostoVacantes(String idEjercicio, String periodoId, String detalle, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = " SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND DETALLE IN("+detalle+")"							
					+ " AND ID_CCOSTO IN "
					+ "		(SELECT ID_CCOSTO FROM ENOC.BEC_PLAZAS "
					+ "		WHERE (NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES) > 0"
					+ "		AND PERIODO_ID = ?) "+orden;
			lista = enocJdbc.query(comando, new ContCcostoMapper(), idEjercicio, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|getListCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos 
	public List<ContCcosto> listCentrosCosto(String ejercicioId, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO"
				+ " WHERE ID_EJERCICIO = ? "+orden;
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos 
	public List<ContCcosto> listCentrosCosto(String ejercicioId, String detalle, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = ? " +
					" AND DETALLE = ? "+orden;
			Object[] parametros = new Object[] {ejercicioId, detalle};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos 
	public List<String> listClavesCentrosDeCosto(String ejercicioId){
		List<String> lista 	= new ArrayList<String>();		
		try{			
			String comando = "SELECT ID_CCOSTO FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ?  AND DETALLE = 'S' ORDER BY ID_CCOSTO";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.queryForList (comando, String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listClavesCentrosDeCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos
	public List<ContCcosto> listDeptosConPuestos(String ejercicioId, String detalle, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND DETALLE = 'S'"
					+ " AND ID_CCOSTO IN (SELECT DISTINCT(ID_CCOSTO) FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ?) "+orden;
			Object[] parametros = new Object[] {ejercicioId, ejercicioId};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos
	public List<ContCcosto> listDeptosConPuestos(String ejercicioId, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = ?" +
					" AND ID_CCOSTO IN (SELECT DISTINCT(ID_CCOSTO) FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ?) "+orden;
			Object[] parametros = new Object[] {ejercicioId, ejercicioId};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|listCentrosCosto|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos
	public List<ContCcosto> getListAcceso(String idEjercicio, String filtro, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = ? " +					
					" AND ID_CCOSTO LIKE '"+filtro+"%' " +orden;
			lista = enocJdbc.query(comando, new ContCcostoMapper(), idEjercicio);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|getListAcceso|:"+ex);
		}
		
		return lista;
	}
	
	// Obtiene la lista de departamentos
	public List<ContCcosto> getListAcceso(String idEjercicio, String detalle, String filtro, String orden){
		List<ContCcosto> lista 	= new ArrayList<ContCcosto>();		
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO" +
					" WHERE ID_EJERCICIO = ? " +
					" AND DETALLE = ?"+
					" AND ID_CCOSTO LIKE '"+filtro+"%' " +orden;
			lista = enocJdbc.query(comando, new ContCcostoMapper(), idEjercicio, detalle);		
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|getListAcceso|:"+ex);
		}
		
		return lista;
	}
	
	// Mapa de centros de costo
	public HashMap<String,String> mapaCcosto( String detalle ){
		HashMap<String,String> map 	= new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, NOMBRE AS VALOR FROM MATEO.CONT_CCOSTO WHERE DETALLE IN ("+detalle+")";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa mapa : lista){
				map.put(mapa.getLlave(), mapa.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcosto|mapaCcosto|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> mapaDeptoIniciales(String ejercicio){
		
		HashMap<String, String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, INICIALES AS VALOR FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {ejercicio};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.EstCcostoDao|mapaDeptoIniciales|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, ContCcosto> mapaCentrosCosto(String ejercicioId){
		HashMap<String, ContCcosto> mapa = new HashMap<String, ContCcosto>();
		List<ContCcosto> lista			= new ArrayList<ContCcosto>();
				
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);
			for (ContCcosto costo : lista) {
				mapa.put(costo.getIdCcosto(), costo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|mapaCentrosCosto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, ContCcosto> mapaDeptos(String detalle){
		HashMap<String, ContCcosto> mapa = new HashMap<String, ContCcosto>();
		List<ContCcosto> lista			= new ArrayList<ContCcosto>();
				
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO WHERE DETALLE = ?";
			Object[] parametros = new Object[] {detalle};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);
			for (ContCcosto costo : lista) {
				mapa.put(costo.getIdEjercicio()+costo.getIdCcosto(), costo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|mapaDeptos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, ContCcosto> mapaDeptosPorEjercicio(String ejercicioId){
		HashMap<String, ContCcosto> mapa = new HashMap<String, ContCcosto>();
		List<ContCcosto> lista			= new ArrayList<ContCcosto>();
				
		try{			
			String comando = "SELECT ID_EJERCICIO, ID_CCOSTO, NOMBRE, DETALLE, INICIALES, RFC FROM MATEO.CONT_CCOSTO WHERE ID_EJERCICIO = ?";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new ContCcostoMapper(), parametros);
			for (ContCcosto costo : lista) {
				mapa.put(costo.getIdCcosto(), costo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|mapaDeptos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaJefes(String ejercicioId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{			
			String comando = "SELECT CCOSTO_ID AS LLAVE, EMP_CLAVE(JEFE_ID) AS VALOR FROM ARON.CAT_JEFES WHERE EJERCICIO_ID = ? AND STATUS = 'A'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);
			for (aca.Mapa jefe : lista) {
				mapa.put(jefe.getLlave(), jefe.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContCcostoDao|mapaJefes|:"+ex);
		}
		
		return mapa;
	}	
}
