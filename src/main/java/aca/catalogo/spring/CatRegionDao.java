package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatRegionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatRegion region ) {
		
 		boolean ok = false;
 		try{ 			
 			String comando = "INSERT INTO ENOC.CAT_REGION"+ 
 				" (REGION_ID, CULTURAL_ID, NOMBRE_REGION)"+
 				" VALUES(?, ?, ?)";
 			Object[] parametros = new Object[] {
 					region.getRegionId(), region.getCulturalId(), region.getNombreRegion()
 		 	};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.catalogo.spring.CatRegionDao|insertReg|:"+ex);	
 		}
 		return ok;
 	}
	
 	public boolean updateReg( CatRegion region ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.CAT_REGION "+ 
 				" SET "+
 				" NOMBRE_REGION = ? "+
 				" WHERE REGION_ID = ? ";
 			Object[] parametros = new Object[] {
 					region.getNombreRegion(), region.getRegionId()
 		 	};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.catalogo.spring.CatRegionDao|updateReg|:"+ex);		
 		} 		
 		return ok;
 	}
 	
 	public boolean deleteReg( String regionId, String culturalId ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.CAT_REGION WHERE REGION_ID = TO_NUMBER(?,'999') AND CULTURAL_ID = TO_NUMBER(?,'999')";
 			Object[] parametros = new Object[] {regionId, culturalId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.catalogo.spring.CatRegionDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public CatRegion mapeaRegId(  String regionId, String culturalId ){
 		
 		CatRegion region 	= new CatRegion(); 		
 		try{
	 		String comando = "SELECT COUNT(*) FROM ENOC.CAT_REGION WHERE REGION_ID = TO_NUMBER(?,'999') AND CULTURAL_ID = TO_NUMBER(?,'999')";
	 		Object[] parametros = new Object[] {regionId, culturalId };
	 		if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
	 			comando = "SELECT REGION_ID, CULTURAL_ID, NOMBRE_REGION FROM ENOC.CAT_REGION WHERE REGION_ID = TO_NUMBER(?, '999') AND CULTURAL_ID = TO_NUMBER(?, '999')";
	 			region = enocJdbc.queryForObject(comando, new CatRegionMapper(), parametros);
	 		}	 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.catalogo.spring.CatRegionDao|mapeaRegId|:"+ex);
 		} 		
 		return region;
 	}
 	
 	public boolean existeReg( String regionId, String culturalId ) {
 		boolean 		ok 	= false;
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.CAT_REGION WHERE REGION_ID = TO_NUMBER(?,'999') AND CULTURAL_ID = TO_NUMBER(?,'999')";
 			Object[] parametros = new Object[] {regionId, culturalId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.cultural.spring.CatRegionDao|existeReg|:"+ex);
 		}
 		return ok;
 	}
 	
	public String maximoReg( String culturalId) {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(REGION_ID)+1 AS MAXIMO FROM ENOC.CAT_REGION WHERE CULTURAL_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {culturalId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRegionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
 	
	public HashMap<String,String> mapaTotalRegiones(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CULTURAL_ID AS LLAVE, COUNT(REGION_ID) AS VALOR FROM ENOC.CAT_REGION GROUP BY CULTURAL_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRegionDao|mapaTotalEstados|:"+ex);
		}		
		return mapa;
	}
	
	public List<CatRegion> getLista( String culturalId, String orden ) {
		
		List<CatRegion> lista	= new ArrayList<CatRegion>();	
		
		try{
			String comando = "SELECT REGION_ID, CULTURAL_ID, NOMBRE_REGION FROM ENOC.CAT_REGION WHERE CULTURAL_ID = TO_NUMBER(?,'999') "+ orden;
			Object[] parametros = new Object[] {culturalId};
			lista = enocJdbc.query(comando, new CatRegionMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRegionDao|getLista|:"+ex);
		}		
		
		return lista;
	}

	public HashMap<String,CatRegion> getMapAll( String orden ) {
		
		HashMap<String,CatRegion> mapa 	= new HashMap<String,CatRegion>();
		List<CatRegion> lista 			= new ArrayList<CatRegion>();
		
		try{
			String comando = "SELECT * FROM ENOC.CAT_REGION"+ orden;
			lista = enocJdbc.query(comando, new CatRegionMapper());
			for(CatRegion region : lista){
				mapa.put(region.getRegionId()+region.getCulturalId(), region);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRegionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombreRegion( String culturalId, String regionId) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_REGION WHERE CULTURAL_ID = TO_NUMBER(?,'999') AND REGION_ID = TO_NUMBER(?,'999')";		
			Object[] parametros = new Object[] {culturalId, regionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_REGION FROM ENOC.CAT_REGION WHERE CULTURAL_ID = TO_NUMBER(?,'999') AND REGION_ID = TO_NUMBER(?,'999')";	
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){		
			System.out.println("Error - aca.catalogo.spring.CatRegionDao|getNombreRegion|:"+ex);
		}
		
		return nombre;
	}
	
}