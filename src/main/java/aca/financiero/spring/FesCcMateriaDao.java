package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FesCcMateriaDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
 	public FesCcMateria mapeaRegId(String matricula, String cargaId, String bloqueId){
 		FesCcMateria objeto = new FesCcMateria();
 		try{
	 		String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, CURSO_CARGA_ID, CURSO_ID, COSTO_CREDITO, COSTO_CURSO"
	 				+ " FROM MATEO.COSTO_CURSO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";	 		
	 		Object[] parametros = new Object[] {matricula, cargaId, bloqueId};
	 		objeto = enocJdbc.queryForObject(comando, new FesCcMateriaMapper(),parametros);			
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapeaRegId|:"+ex);
		}
 		
 		return objeto;
 	}
	
	public boolean existeReg(String matricula, String cargaId, String bloqueId){
		boolean ok = false;		
		try{
			String comando = "SELECT MATRICULA FROM MATEO.COSTO_CURSO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?";
			Object[] parametros = new Object[] {matricula, cargaId, bloqueId};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<FesCcMateria> getListAll(String orden){
		List<FesCcMateria> lista 	= new ArrayList<FesCcMateria>();
		
		try{			
			String comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, CURSO_CARGA_ID, CURSO_ID, COSTO_CREDITO, COSTO_CURSO"
					+ " FROM MATEO.FESCCMATERIA "+orden;

			lista = enocJdbc.query(comando, new FesCcMateriaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapCostoCredito(String cargas, String estados){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, MAX(COALESCE(COSTO_CREDITO,0)) AS VALOR"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapCostoCredito|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCostoCreditoCarga(String cargas, String estados){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT MATRICULA||CARGA_ID AS LLAVE, MAX(COALESCE(COSTO_CREDITO,0)) AS VALOR"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapCostoCreditoCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCreditosCarga(String cargas, String estados){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, SUM(COALESCE(CREDITOS,0)) AS VALOR"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapCreditosCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCreditosCargaCero(String cargas){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();				
		try{
			String comando = " SELECT MATRICULA||CARGA_ID||BLOQUE AS LLAVE, SUM(CREDITOS) AS VALOR FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = ? AND COSTO_CURSO < 1 "
					+ " GROUP BY CARGA_ID, BLOQUE, MATRICULA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargas);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapCreditosCargaCero|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCostoMateria(String cargas){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT MATRICULA||CURSO_CARGA_ID||CURSO_ID AS LLAVE, COALESCE(COSTO_CURSO,0) AS VALOR"
					+ " FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND COSTO_CURSO > 0";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapCostoMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapMatEnCalculo(String cargaId){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT MATRICULA AS LLAVE, COALESCE(COUNT(MATRICULA),0) AS VALOR FROM MATEO.FES_CC_MATERIA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MATRICULA IN (SELECT MATRICULA FROM MATEO.FES_CCOBRO WHERE MATRICULA = FES_CC_MATERIA.MATRICULA AND INSCRITO = 'S' AND CARGA_ID = ?)"
					+ " GROUP BY MATRICULA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, cargaId);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapMatEnCalculo|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, String> mapaCreditosEnCalculo(String cargaId){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT MATRICULA AS LLAVE, SUM(CREDITOS) AS VALOR FROM MATEO.FES_CC_MATERIA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " GROUP BY MATRICULA";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapaCreditosEnCalculo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCreditosEnCalculo(String cargaId, String bloqueId){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT MATRICULA AS LLAVE, SUM(CREDITOS) AS VALOR FROM MATEO.FES_CC_MATERIA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND BLOQUE = TO_NUMBER(?,'99')"
					+ " GROUP BY MATRICULA";
			Object[] parametros = new Object[] {cargaId, bloqueId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcMateriaDao|mapaCreditosEnCalculo|:"+ex);
		}
		
		return mapa;
	}

}
