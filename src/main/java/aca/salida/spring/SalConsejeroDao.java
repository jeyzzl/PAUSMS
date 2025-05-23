package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalConsejeroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalConsejero salida ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_CONSEJERO"
					+ "(SALIDA_ID, FOLIO, CONSEJERO, TRABAJO, CLAVE) "
					+ "VALUES(TO_NUMBER(?,'99999'), TO_NUMBER(?,'99'), ?, ?, ?) ";
			
			Object[] parametros = new Object[] {salida.getSalidaId(),salida.getFolio(),salida.getConsejero(),salida.getTrabajo(),salida.getClave()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(SalConsejero salida) {
        boolean ok = false;
		
        try{
             String comando = 
                    "UPDATE ENOC.SAL_CONSEJERO" + 
                    " SET CONSEJERO = ?," +
                    " TRABAJO = ?," +
                    " CLAVE = ? " +                    
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')";
                    
            
            Object[] parametros = new Object[] {salida.getConsejero(),salida.getTrabajo(),salida.getClave(),salida.getSalidaId(),salida.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaConsejero|updateReg|:" + ex);
        }
        return ok;
    }

    public boolean deleteReg(String salidaId, String folio) {
    	boolean ok = false;

        try {
             String comando = "DELETE FROM ENOC.SAL_CONSEJERO"
             		+ " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')";
                    
             Object[] parametros = new Object[] {salidaId,folio};
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
 			}            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.SalidaConsejero|deleteReg|:" + ex);
        }
        return ok;
    }    
	
	public SalConsejero mapeaRegId( String salidaId, String folio) {
		SalConsejero salida = new SalConsejero();
		
		try{ 
			String comando = "SELECT SALIDA_ID, FOLIO, CONSEJERO, TRABAJO, CLAVE "
					+ " FROM 	ENOC.SAL_CONSEJERO "
					+ " WHERE SALIDA_ID = ? AND FOLIO = ?";			
			Object[] parametros = new Object[] {salidaId,folio};
			salida = enocJdbc.queryForObject(comando, new SalConsejeroMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|mapeaRegId|:"+ex);
		}
		return salida;
	}
	
	public boolean existeReg(String salidaId, String folio) {
		boolean ok 				= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_CONSEJERO"
					+ " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?, '99')";					
			Object[] parametros = new Object[] {salidaId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejero|existeReg|:"+ex);
		}
		return ok;
	}
	 
	public String maximoReg( String salidaId) {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.SAL_CONSEJERO WHERE SALIDA_ID = ? " ; 
			
			Object[] parametros = new Object[] {salidaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<SalConsejero> listAll(String orden ) {
		List<SalConsejero> lista 	= new ArrayList<SalConsejero>();
		try{
			String comando = "SELECT SALIDA_ID , FOLIO, CONSEJERO, TRABAJO, CLAVE FROM ENOC.SAL_CONSEJERO "+orden;			
			lista = enocJdbc.query(comando, new SalConsejeroMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaConsejeroDao|listAll|:"+ex);
		}		
		return lista;
	}
	
	public List<SalConsejero> getListAll(String salidaId, String orden ) {
		List<SalConsejero> lista 	= new ArrayList<SalConsejero>();	
		try{
			String comando = "SELECT SALIDA_ID , FOLIO, CONSEJERO, TRABAJO, CLAVE FROM ENOC.SAL_CONSEJERO WHERE SALIDA_ID = ? "+orden;
			Object[] parametros = new Object[] {salidaId};			
			lista = enocJdbc.query(comando, new SalConsejeroMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaConsejeroDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaPorSolicitud() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT SALIDA_ID AS LLAVE, COUNT(CONSEJERO) AS VALOR FROM ENOC.SAL_CONSEJERO GROUP BY SALIDA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaConsejeroDao|mapaPorSolicitud|:"+ex);
		}		
		return mapa;
	}

}