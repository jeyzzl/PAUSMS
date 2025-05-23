package aca.saii.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaiiGrupoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SaiiGrupo saiiPeriodo ) {
		boolean ok = false;		
		try {
			String comando = "INSERT INTO ENOC.SAII_GRUPO (GRUPO_ID, PERIODO_ID, PLAN_ID) "
					+ " VALUES(TO_NUMBER(?,'9999999'), ?, ?)";
			Object[] parametros = new Object[] {saiiPeriodo.getGrupoId(), saiiPeriodo.getPeriodoId(), saiiPeriodo.getPlanId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(SaiiGrupo saiiGrupo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.SAII_GRUPO "
					+ " SET GRUPO_ID = TO_NUMBER(?,'9999999')"
					+ " WHERE PERIODO_ID = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[]{saiiGrupo.getGrupoId(), saiiGrupo.getPeriodoId(), saiiGrupo.getPlanId()};
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg(String periodoId, String planId) {
    	boolean ok = false;

        try {
             String comando =" DELETE FROM ENOC.SAII_GRUPO WHERE PERIODO_ID = ? AND PLAN_ID = ?";
             Object[] parametros = new Object[]{periodoId, planId};
             if (enocJdbc.update(comando, parametros)==1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.saii.spring.SaiiGrupoDao|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg(String periodoId, String planId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAII_GRUPO WHERE PERIODO_ID = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[]{ periodoId, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public String maximoReg() {
		int maximo = 0;		
		try{
			String comando = "SELECT COALESCE(MAX(GRUPO_ID)+1,1) FROM ENOC.SAII_GRUPO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);		
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|existeReg|:"+ex);
		}
		return String.valueOf(maximo);
	}
    
    public SaiiGrupo mapeaRegId(String periodoId, String planId) {
    	SaiiGrupo saiiGrupo = new SaiiGrupo();
		try{ 
	    	String comando = "SELECT GRUPO_ID, PERIODO_ID, PLAN_ID FROM ENOC.SAII_GRUPO WHERE PERIODO_ID = ? AND PLAN_ID = ?";
	    	Object[] parametros = new Object[] {periodoId, planId};
	    	saiiGrupo = enocJdbc.queryForObject(comando, new SaiiGrupoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|mapeaRegId|:"+ex);
		}
		return saiiGrupo;
	}    
	
    public List<SaiiGrupo> lisGrupo() {
    	List<SaiiGrupo> lista = new ArrayList<SaiiGrupo>();
    	try{
    		String comando = "SELECT GRUPO_ID, PERIODO_ID, PLAN_ID FROM ENOC.SAII_GRUPO";
    		lista = enocJdbc.query(comando, new SaiiGrupoMapper());
    	}catch(Exception ex){
    		System.out.println("Error - aca.saii.spring.SaiiGrupoDao|lisGrupo|:"+ex);
    	}
    	return lista;
    }
	
    public List<SaiiGrupo> lisPorPeriodo( String periodoId, String orden ) {
		List<SaiiGrupo> lista = new ArrayList<SaiiGrupo>();
		try{
			String comando = "SELECT GRUPO_ID, PERIODO_ID, PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'9999999') "+orden;
			lista = enocJdbc.query(comando, new SaiiGrupoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|lisPorPeriodo|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaGrupos(String periodoId){		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, GRUPO_ID AS VALOR FROM ENOC.SAII_GRUPO WHERE PERIODO_ID = ?";	
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiGrupoDao|mapaGrupos|:"+ex);
		}
		
		return mapa;
	}
}