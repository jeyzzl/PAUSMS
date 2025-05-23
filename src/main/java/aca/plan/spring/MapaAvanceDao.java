package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaAvanceDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MapaAvance mapaAvance ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_AVANCE"+ 
				"(PLAN_ID, CICLO, TIPOCURSO_ID, CREDITOS ) "+
				"VALUES( ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99999.99'))";
			
			Object[] parametros = new Object[] {mapaAvance.getPlanId(), mapaAvance.getCiclo(), mapaAvance.getTipocursoId(), mapaAvance.getCreditos()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|insertReg|:"+ex);			
		}	
		return ok;
	}	
	
	public boolean updateReg(MapaAvance mapaAvance ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_AVANCE " + 
			 	 "SET CREDITOS = TO_NUMBER(?,'99999.99') "+
				 "WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] { mapaAvance.getCreditos(), mapaAvance.getPlanId(), mapaAvance.getCiclo(),mapaAvance.getTipocursoId()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean deleteReg(String planId, String ciclo, String tipocursoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {planId, ciclo, tipocursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaAvance mapeaRegId( String planId, String ciclo, String tipocursoId){
		MapaAvance mapaAvance = new MapaAvance();
		
		try{
			String comando = "SELECT PLAN_ID, CICLO, TIPOCURSO_ID, CREDITOS"
				+ " FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = ? AND TIPOCURSO_ID = ?";
			
			Object[] parametros = new Object[] {planId, ciclo, tipocursoId};
			mapaAvance = enocJdbc.queryForObject(comando, new MapaAvanceMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapeaRegId|:"+ex);
		}
		return mapaAvance;
	}
	
	public boolean existeReg(String planId, String ciclo, String tipocursoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') AND TIPOCURSO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {planId, ciclo, tipocursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getCreditosPlan(String planId){
		String creditos			= "0";
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ?";
			
			Object[] parametros = new Object[] {planId};
			creditos = enocJdbc.queryForObject(comando, String.class, parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getCreditosPlan|:"+ex);
		}		
		return creditos;
	}
	
	public String getOptativos(String planId, String ciclo){
		String creditos			= "0";
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS TOTAL FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? AND CICLO = ? AND TIPOCURSO_ID = '7'"; 
			
			Object[] parametros = new Object[] {planId,ciclo};
			creditos = enocJdbc.queryForObject(comando, String.class, parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getOptativos|:"+ex);
		}	
		return creditos;
	}
	
	public List<MapaAvance> getListAll(String orden){
		List<MapaAvance> list		= new ArrayList<MapaAvance>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE "+ orden;
			
			list = enocJdbc.query(comando, new MapaAvanceMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getListAll|:"+ex);
		}	
		return list;	
	}

	public List<String[]> getListCreditosPorTipoCurso(String condiciones, String orden) {

         List<String[]> list       = new ArrayList<String[]>();
         List<aca.Mapa> lista             = new ArrayList<aca.Mapa>();
         String comando            = "";
         
         try{
                comando = "SELECT TIPOCURSO_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_AVANCE " +
                              (!condiciones.equals("")?condiciones:"") +
                              " GROUP BY TIPOCURSO_ID " + orden;
                lista = enocJdbc.query(comando, new aca.MapaMapper());
                for (aca.Mapa map : lista ) {
                       String[] arr = new String[2];
                       arr[0] = map.getLlave();
                       arr[1] = map.getValor();
                       list.add(arr);
                }
         }catch(Exception ex){
                System.out.println("Error - aca.plan.spring.MapaAvanceDao|getListCreditosPorTipoCurso|:"+ex);
         }           

         return list;
	}
	
	public List<String> lisCiclosOficiales(String planId) {        
        List<String> lista             = new ArrayList<String>();        
        try{
        	String comando = "SELECT DISTINCT(CICLO) FROM MAPA_AVANCE WHERE PLAN_ID = ? AND CREDITOS > 0";
            lista = enocJdbc.queryForList(comando, String.class, planId);           
        }catch(Exception ex){
            System.out.println("Error - aca.plan.spring.MapaAvanceDao|getListCreditosPorTipoCurso|:"+ex);
        }
        return lista;
	}
	
	public List<MapaAvance> getListPlan(String planId, String orden){
		List<MapaAvance> list		= new ArrayList<MapaAvance>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? "+ orden;
			
			list = enocJdbc.query(comando, new MapaAvanceMapper(),planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getListPlan|:"+ex);
		}	
		return list;	
	}
	
	public List<aca.Mapa> lisCreditosRequeridos(String planId, String orden){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CICLO||TIPOCURSO_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = ? GROUP BY CICLO||TIPOCURSO_ID "+ orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|lisCreditosRequeridos|:"+ex);
		}	
		return lista;	
	}	
	
	public HashMap<String, MapaAvance> getMapAll(String condicion){
		HashMap<String, MapaAvance> map		= new HashMap<String, MapaAvance>();
		List<MapaAvance> list	= new ArrayList<MapaAvance>();
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.MAPA_AVANCE " + condicion;
			
			list = enocJdbc.query(comando,new MapaAvanceMapper());
			for(MapaAvance mapa : list){				
				map.put(mapa.getPlanId()+mapa.getCiclo()+mapa.getTipocursoId(), mapa);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getMapAll|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> getMapCreditos(String planId){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{			
			comando = " SELECT CICLO AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = TRIM(?) GROUP BY CICLO ";
			
			list = enocJdbc.query(comando, new aca.MapaMapper(),planId);
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), (String)row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|getMapCreditos|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> mapCreditosCarga(String cargaId){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{			
			comando = " SELECT PLAN_ID AS LLAVE, COALESCE(SUM(CREDITOS),0) AS VALOR FROM ENOC.MAPA_AVANCE WHERE PLAN_ID IN"
					+ " (SELECT DISTINCT(PLAN_ID) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL||PLAN_ID IN"
					+ " (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ESTADISTICA  WHERE CARGA_ID = '20211A')) GROUP BY PLAN_ID";
			
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), (String)row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapCreditosCarga|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> mapaCreditosPorTipo(String planId){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{			
			comando = "SELECT TIPOCURSO_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = TRIM(?) GROUP BY TIPOCURSO_ID";
			Object[] parametros = new Object[] {planId};
			list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapaCreditosPorTipo|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> mapaCreditosPorCicloyTipo(String planId){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{			
			comando = "SELECT CICLO||TIPOCURSO_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_AVANCE WHERE PLAN_ID = TRIM(?) GROUP BY CICLO||TIPOCURSO_ID";
			Object[] parametros = new Object[] {planId};
			list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapaCreditosPorCicloyTipo|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> mapaCreditosPorPlan(){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT PLAN_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM MAPA_AVANCE GROUP BY PLAN_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapaCreditosPorPlan|:"+ex);
		}
		return map;	
	}
	
	public HashMap<String, String> mapaCiclosRegistrados(){
		HashMap<String, String> map		= new HashMap<String, String>();
		List<aca.Mapa> list	= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(DISTINCT(CICLO)) AS VALOR FROM ENOC.MAPA_AVANCE GROUP BY PLAN_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : list) {
				map.put(row.getLlave(), row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaAvanceDao|mapaCiclosRegistrados|:"+ex);
		}
		return map;	
	}
	
}