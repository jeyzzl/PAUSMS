package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SalInformeDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalInforme salInforme) {
		boolean ok = false;		
		try{			
			String comando = "INSERT INTO ENOC.SAL_INFORME(SALIDA_ID, INCIDENTES, OBSERVACIONES, FECHA, USUARIO)"
					+ " VALUES(TO_NUMBER(?,'99999'), ?, ?, SYSDATE, ?) ";			
			Object[] parametros = new Object[] {
				salInforme.getSalidaId(), salInforme.getIncidentes(), salInforme.getObservaciones(), salInforme.getUsuario()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(SalInforme salInforme) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.SAL_INFORME" 
				+ " SET INCIDENTES = ?,"
				+ " OBSERVACIONES = ?,"
				+ " FECHA = SYSDATE,"
				+ " USUARIO = ?"
				+ " WHERE SALIDA_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[]{ salInforme.getIncidentes(), salInforme.getObservaciones(), salInforme.getUsuario(), salInforme.getSalidaId() };			
			if (enocJdbc.update(comando,parametros)==1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg(SalInforme salInforme) {
    	boolean ok = false;
        try {
             String comando ="DELETE FROM ENOC.SAL_INFORME WHERE SALIDA_ID = TO_NUMBER(?, '99999')";             
             Object[] parametros = new Object[]{ salInforme.getSalidaId() };             
             if (enocJdbc.update(comando, parametros)==1) {
            	 ok = true;
             } 			
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalInformeDao|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg(String salidaId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_INFORME WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {salidaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SalInforme mapeaRegId(String salidaId) {
    	SalInforme salInforme = new SalInforme();
		try{ 
	    	String comando = "SELECT SALIDA_ID, INCIDENTES, OBSERVACIONES, TO_CHAR(FECHA,'DD/MM/YYYY HH:MI:SS AM') AS FECHA, USUARIO"
	    			+ " FROM ENOC.SAL_INFORME WHERE SALIDA_ID = TO_NUMBER(?,'99999')";	    	
	    	Object[] parametros = new Object[] {salidaId};	    	
	    	salInforme = enocJdbc.queryForObject(comando,  new SalInformeMapper(), parametros);	    	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|mapeaRegId|:"+ex);
		}
		return salInforme;
	}
    
    public List<SalInforme> listAll(String orden ) {
		List<SalInforme> lista = new ArrayList<SalInforme>();		
		try{
			String comando = "SELECT SALIDA_ID, INCIDENTES, OBSERVACIONES, FECHA, USUARIO FROM ENOC.SAL_INFORME "+orden;			
			lista = enocJdbc.query(comando, new SalInformeMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|listAll|:"+ex);
		}
		return lista;
	}
    
    public List<SalInforme> lisPorFechas(String fechaIni, String fechaFin, String orden ) {
		List<SalInforme> lista = new ArrayList<SalInforme>();		
		try{
			String comando = "SELECT SALIDA_ID, INCIDENTES, OBSERVACIONES, FECHA, USUARIO FROM ENOC.SAL_INFORME"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+orden;
			Object[] parametros = new Object[]{ fechaIni, fechaFin }; 
			lista = enocJdbc.query(comando, new SalInformeMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|lisPorFechas|:"+ex);
		}
		return lista;
	}
    
    public HashMap<String,SalInforme> mapaInformes( ){		
		HashMap<String,SalInforme> mapa = new HashMap<String,SalInforme>();
		List<SalInforme> lista	= new ArrayList<SalInforme>();		
		try{
			String comando = "SELECT SALIDA_ID, INCIDENTES, OBSERVACIONES, TO_CHAR(FECHA,'DD/MM/YYYY HH:MI:SS AM') AS FECHA, USUARIO FROM ENOC.SAL_INFORME";
			lista = enocJdbc.query(comando, new SalInformeMapper());
			for (SalInforme informe : lista ) {
				mapa.put(informe.getSalidaId(), informe );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInformeDao|mapaInformes|:"+ex);
		}
		
		return mapa;
	}
}
