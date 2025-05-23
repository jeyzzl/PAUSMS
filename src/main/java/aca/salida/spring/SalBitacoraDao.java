package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalBitacoraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( SalBitacora salBitacora ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_BITACORA " + 
					"(SALIDA_ID, ESTADO, FECHA) " +
					" VALUES(TO_NUMBER(?,'99999'), ?, TO_TIMESTAMP(?,'DD/MM/YYYY HH24:MI:SS')) ";
			Object[] parametros = new Object[] {salBitacora.getSalidaId(), salBitacora.getEstado(), salBitacora.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( SalBitacora salBitacora ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAL_BITACORA "+ 
				" SET FECHA = TO_TIMESTAMP(?,'DD/MM/YYYY HH24:MI:SS') " +
				" WHERE SALIDA_ID = TO_NUMBER(?,'99999')" +
				" AND ESTADO = ?";
			Object[] parametros = new Object[]{salBitacora.getFecha(), salBitacora.getSalidaId(), salBitacora.getEstado()};
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg( SalBitacora salBitacora ) {
    	boolean ok = false;

        try {
             String comando =
                    " DELETE FROM ENOC.SAL_BITACORA" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')" +
                    " AND ESTADO = ?";
             Object[] parametros = new Object[]{salBitacora.getSalidaId(), salBitacora.getEstado()};
             if (enocJdbc.update(comando, parametros)==1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalBitacora|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean deleteAll( String salidaId ) {
    	boolean ok = false;

        try {
             String comando = "DELETE FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
             Object[] parametros = new Object[]{salidaId};
             if (enocJdbc.update(comando, parametros) >= 1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalBitacora|deleteAll|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg( String salidaId, String estado ) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND ESTADO = ?";
			Object[] parametros = new Object[] {salidaId, estado};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SalBitacora mapeaRegId( String salidaId, String estado ) {
    	SalBitacora salBitacora = new SalBitacora();
		try{ 
	    	String comando = "SELECT SALIDA_ID, ESTADO, FECHA"
	    			+ " FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND ESTADO = ?";
	    	Object[] parametros = new Object[] {salidaId, estado};
	    	salBitacora = enocJdbc.queryForObject(comando, new SalBitacoraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|mapeaRegId|:"+ex);
		}
		return salBitacora;
	}
    
    public List<SalBitacora> listAll( String orden ) {
		List<SalBitacora> lista = new ArrayList<SalBitacora>();
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, ESTADO, FECHA"+			        
					" FROM ENOC.SAL_BITACORA "+orden;
			lista = enocJdbc.query(comando, new SalBitacoraMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<SalBitacora> getListAll( String salidaId, String orden ) {
		List<SalBitacora> lista = new ArrayList<SalBitacora>();
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, ESTADO, FECHA"+			        
					" FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new SalBitacoraMapper(),salidaId);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaBitacora( String salidaId) {		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT SALIDA_ID||ESTADO AS LLAVE, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS VALOR FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalBitacoraDao|mapaBitacora|:"+ex);
		}		
		return mapa;
	}
}