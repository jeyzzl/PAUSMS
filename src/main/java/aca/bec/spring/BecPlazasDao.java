package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.bec.spring.BecPlazasDao;

@Component
public class BecPlazasDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
		
	public boolean insertReg(BecPlazas becPlazas){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PLAZAS"+ 
				"(ID_EJERCICIO, ID_CCOSTO, PERIODO_ID, NUM_PLAZAS, NUM_INDUSTRIALES, NUM_TEMPORALES, NUM_PREINDUSTRIALES, NUM_POSGRADO, CONTACTO, TELEFONO, CORREO)"+
				" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {becPlazas.getIdEjercicio(), becPlazas.getIdCcosto(), becPlazas.getPeriodoId(), becPlazas.getNumPlazas(),
				becPlazas.getNumIndustriales(), becPlazas.getNumTemporales(), becPlazas.getNumPreIndustriales(), becPlazas.getNumPosgrado(),
				becPlazas.getContacto(),becPlazas.getTelefono(),becPlazas.getCorreo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(BecPlazas becPlazas){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_PLAZAS"+ 
				" SET NUM_PLAZAS = ?, NUM_INDUSTRIALES = ?, NUM_TEMPORALES = ?, NUM_PREINDUSTRIALES = ?, NUM_POSGRADO = ?, CONTACTO = ?, TELEFONO = ?, CORREO = ?"+				
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? ";
			
			Object[] parametros = new Object[] {becPlazas.getNumPlazas(), becPlazas.getNumIndustriales(), becPlazas.getNumTemporales(), becPlazas.getNumPreIndustriales(),
					 becPlazas.getNumPosgrado(),becPlazas.getContacto(),becPlazas.getTelefono(),becPlazas.getCorreo(), becPlazas.getIdEjercicio(), becPlazas.getIdCcosto(), becPlazas.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeReg(String idEjercicio, String idCcosto, String periodoId){
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? ";
			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	
	public boolean deleteReg(String idEjercicio, String idCcosto, String periodoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ?";			
			Object[] parametros = new Object[] {idEjercicio, idCcosto, periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BecPlazas mapeaRegId(String idEjercicio, String idCcosto, String periodoId ){
		BecPlazas becPlazas = new BecPlazas();		
		try{
			String comando = "SELECT COUNT(ID_EJERCICIO) FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {idEjercicio, idCcosto, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){				
				comando = "SELECT ID_EJERCICIO,ID_CCOSTO,NUM_PLAZAS,NUM_INDUSTRIALES,NUM_TEMPORALES,NUM_PREINDUSTRIALES,NUM_POSGRADO,PERIODO_ID,CONTACTO,TELEFONO,CORREO" +
					" FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? ";			
				becPlazas = enocJdbc.queryForObject(comando, new BecPlazasMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecPlazasDao|mapeaRegId|:"+ex);
		}
		return becPlazas;
	}
		
	public List<BecPlazas> getListAll(String orden) {
		List<BecPlazas> lis 		= new ArrayList<BecPlazas>();
		String comando					= "";
		
		try{
			comando = "SELECT ID_EJERCICIO,ID_CCOSTO,NUM_PLAZAS,NUM_INDUSTRIALES,NUM_TEMPORALES,NUM_PREINDUSTRIALES,NUM_POSGRADO,PERIODO_ID,CONTACTO,TELEFONO,CORREO FROM ENOC.BEC_PLAZAS "+orden;
			
			lis = enocJdbc.query(comando, new BecPlazasMapper());
			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao|getListAll|:"+ex);
		}
		return lis;
	}		
	
	public HashMap<String,BecPlazas> getBecPlazas(String idEjercicio, String periodoId) {
		HashMap<String, BecPlazas> mapa = new HashMap<String, BecPlazas>();
		List<BecPlazas> lis 			= new ArrayList<BecPlazas>();
		String comando					= "";			

		try{
			comando = "SELECT * FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {idEjercicio, periodoId};
			lis = enocJdbc.query(comando, new BecPlazasMapper(),parametros);
			for(BecPlazas plazas : lis){				
				mapa.put(plazas.getIdCcosto(), plazas);
			}
			
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasDepto(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		String comando			= "";
		try{
			comando = " SELECT ID_CCOSTO AS LLAVE, NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES AS VALOR"
					+ " FROM ENOC.BEC_PLAZAS"
					+ " WHERE ID_EJERCICIO = ?"
					+ " AND PERIODO_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasDepto|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasBasicas(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, SUM(NUM_PLAZAS) AS VALOR FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO ORDER BY 1";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasBasicas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasTemporales(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, SUM(NUM_TEMPORALES) AS VALOR FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO ORDER BY 1";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasTemporales|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasIndustriales(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, SUM(NUM_INDUSTRIALES) AS VALOR FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO ORDER BY 1";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasIndustriales|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasPreindustriales(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, SUM(NUM_PREINDUSTRIALES) AS VALOR FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO ORDER BY 1";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasPreindustriales|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasPosgrado(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO AS LLAVE, SUM(NUM_POSGRADO) AS VALOR FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO ORDER BY 1";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasPosgrado|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getBecPlazasAsignadas(String idEjercicio, String idCcosto, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TIPO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.BEC_PUESTO_ALUMNO "
					+ " WHERE ID_EJERCICIO = ? "
					+ " AND ID_CCOSTO = ? "
					+ " AND PERIODO_ID = ?"
					+ " GROUP BY ID_CCOSTO, TIPO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, idCcosto, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |getBecPlazasAsignadas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPlazasAsignadas(String idEjercicio, String periodoId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ID_CCOSTO||TIPO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = ? AND PERIODO_ID = ? GROUP BY ID_CCOSTO||TIPO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), idEjercicio, periodoId);	
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.bec.spring.BecPlazasDao |mapaPlazasAsignadas|:"+ex);
		}
		return mapa;
	}
	
}
