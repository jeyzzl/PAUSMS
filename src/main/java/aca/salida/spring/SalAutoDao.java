package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalAutoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalAuto auto ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_AUTO (SALIDA_ID, FOLIO, TIPO, POLIZA, TELEFONO) "
					+ "VALUES(TO_NUMBER(?,'99999'), TO_NUMBER(?,'99'), ?, ?, ? ) ";
			
			Object[] parametros = new Object[] {auto.getSalidaId(),auto.getFolio(),auto.getTipo(),auto.getPoliza(),auto.getTelefono()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(SalAuto auto) {
        boolean ok = false;		
        try{
             String comando = 
                    "UPDATE ENOC.SAL_AUTO"
                    + " SET TIPO = ?,"
                    + " POLIZA = ?,"
                    + " TELEFONO = ?,"
                    + " IMAGEN = ?"
                    + " WHERE SALIDA_ID = TO_NUMBER(?, '99999') "
                    + " AND FOLIO = TO_NUMBER(?, '99')";           
            Object[] parametros = new Object[] {
        		auto.getPoliza(),auto.getTelefono(),auto.getImagen(),auto.getSalidaId(),auto.getTipo()
        	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalAutoDao|updateReg|:" + ex);
        }
        return ok;
    }

    public boolean deleteReg(String salidaId, String folio) {
    	boolean ok = false;
        try {
             String comando = "DELETE FROM ENOC.SAL_AUTO"
             		+ " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?,'99')";                    
             Object[] parametros = new Object[] {salidaId,folio};
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
 			}            
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalAutoDao|deleteReg|:" + ex);
        }
        return ok;
    }    
	
	public SalAuto mapeaRegId( String salidaId, String folio) {
		SalAuto salida = new SalAuto();		
		try{ 
			String comando = "SELECT SALIDA_ID, FOLIO, TIPO, POLIZA, TELEFONO, IMAGEN FROM ENOC.SAL_AUTO"
					+ " WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND FOLIO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {salidaId,folio};
			salida = enocJdbc.queryForObject(comando, new SalAutoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|mapeaRegId|:"+ex);
		}
		return salida;
	}
	
	public boolean existeReg(String salidaId, String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_AUTO"
					+ " WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = TO_NUMBER(?,'99')";					
			Object[] parametros = new Object[] {salidaId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|existeReg|:"+ex);
		}
		return ok;
	}
	 
	public String maximoReg( String salidaId) {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.SAL_AUTO WHERE SALIDA_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {salidaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<SalAuto> lisSalida(String salidaId, String orden) {
		List<SalAuto> lista 	= new ArrayList<SalAuto>();		
		try{
			String comando = "SELECT SALIDA_ID, FOLIO, TIPO, POLIZA, TELEFONO FROM ENOC.SAL_AUTO WHERE SALIDA_ID = TO_NUMBER(?,'99999') "+orden; 
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando, new SalAutoMapperCorto(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|lisSalida|:"+ex);
		}		
		return lista;
	}
	
	public List<SalAuto> listAll( String orden ) {
		List<SalAuto> lista = new ArrayList<SalAuto>();
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, FOLIO, TIPO, POLIZA, TELEFONO"		        
					+ " FROM ENOC.SAL_AUTO "+orden;
			lista = enocJdbc.query(comando, new SalAutoMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaPorSolicitud() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT SALIDA_ID AS LLAVE, COUNT(FOLIO) AS VALOR FROM ENOC.SAL_AUTO GROUP BY SALIDA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalAutoDao|mapaPorSolicitud|:"+ex);
		}		
		return mapa;
	}
}