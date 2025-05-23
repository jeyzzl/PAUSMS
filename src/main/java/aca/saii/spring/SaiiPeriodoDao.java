package aca.saii.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaiiPeriodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( SaiiPeriodo saiiPeriodo ) {
		boolean ok = false;
		
		try {
			String comando = "INSERT INTO ENOC.SAII_PERIODO "
					+ " (PERIODO_ID, PERIODO_NOMBRE, ESTADO, FECHA) "
					+ " VALUES(?, ?, ?, TO_DATE(?,'DD/MM/YYYY')) ";
			Object[] parametros = new Object[] {saiiPeriodo.getPeriodoId(), saiiPeriodo.getPeriodoNombre(), saiiPeriodo.getEstado(), saiiPeriodo.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( SaiiPeriodo saiiPeriodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAII_PERIODO "
					+ " SET PERIODO_NOMBRE = ?, "
					+ " ESTADO = ?, "
					+ " FECHA = TO_DATE(?,'DD/MM/YYYY') "
					+ " WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[]{saiiPeriodo.getPeriodoNombre(), saiiPeriodo.getEstado(), saiiPeriodo.getFecha(), saiiPeriodo.getPeriodoId()};
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg( String periodoId) {
    	boolean ok = false;

        try {
             String comando =
                    " DELETE FROM ENOC.SAII_PERIODO WHERE PERIODO_ID = ?";
             Object[] parametros = new Object[]{periodoId};
             if (enocJdbc.update(comando, parametros)==1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg(String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAII_PERIODO WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SaiiPeriodo mapeaRegId(String periodoId) {
    	SaiiPeriodo saiiPeriodo = new SaiiPeriodo();
		try{ 
	    	String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
	    			+ " FROM ENOC.SAII_PERIODO WHERE PERIODO_ID = ? ";
	    	Object[] parametros = new Object[] {periodoId};
	    	saiiPeriodo = enocJdbc.queryForObject(comando, new SaiiPeriodoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|mapeaRegId|:"+ex);
		}
		return saiiPeriodo;
	}
    
    public List<SaiiPeriodo> listAll( String orden ) {
		List<SaiiPeriodo> lista = new ArrayList<SaiiPeriodo>();
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM ENOC.SAII_PERIODO "+orden;
			lista = enocJdbc.query(comando, new SaiiPeriodoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<SaiiPeriodo> getListAll( String periodoId, String orden ) {
		List<SaiiPeriodo> lista = new ArrayList<SaiiPeriodo>();
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA "
					+ " FROM ENOC.SAII_PERIODO WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new SaiiPeriodoMapper(),periodoId);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiPeriodoDao|getListAll|:"+ex);
		}
		return lista;
	}
}