package aca.salida.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalInvitadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalInvitado salInvitado) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_INVITADO " + 
					"(SALIDA_ID, FOLIO, NOMBRE, TIPO) " +
					" VALUES(TO_NUMBER(?,'99999'), ?, ?, ?) ";
			
			Object[] parametros = new Object[] {
				salInvitado.getSalidaId(), salInvitado.getFolio(), salInvitado.getNombre(), salInvitado.getTipo()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(SalInvitado salInvitado) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SAL_INVITADO "+ 
				" SET NOMBRE = ?, " +
				" TIPO = ? " +
				" WHERE SALIDA_ID = TO_NUMBER(?,'99999')" +
				" AND  FOLIO = ? ";
			
			Object[] parametros = new Object[]{
				salInvitado.getSalidaId(), salInvitado.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1) {
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|updateReg|:"+ex);
		}
		return ok;
	}	

    public boolean deleteReg(SalInvitado salInvitado) {
    	boolean ok = false;

        try {
             String comando =
                    " DELETE FROM ENOC.SAL_INVITADO" + 
                    " WHERE SALIDA_ID = TO_NUMBER(?, '99999')" +
                    " AND FOLIO = ?";
             
             Object[] parametros = new Object[]{
        		 salInvitado.getSalidaId(), salInvitado.getFolio()
             };
             
             if (enocJdbc.update(comando, parametros)==1) {
            	 ok = true;
             }
 			
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalInvitadoDao|deleteReg|:" + ex);
        }
        return ok;
    }
    
    public boolean deleteInvitados(String salidaId) {
    	boolean ok = false;

        try {
             String comando = "DELETE FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
             
             Object[] parametros = new Object[]{salidaId};
             
             if (enocJdbc.update(comando, parametros) >= 1) {
            	 ok = true;
             }

        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalInvitadoDao|deleteInvitados|:" + ex);
        }
        return ok;
    }
    
    public boolean existeReg(String salidaId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?, '99999') AND FOLIO = ?";
			Object[] parametros = new Object[] {salidaId, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) == 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public boolean tieneAlumnos(String salida) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {salida};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SalInvitado mapeaRegId(String salidaId, String folio) {
    	SalInvitado salInvitado = new SalInvitado();
		try{ 
	    	String comando = "SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO"
	    			+ " FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND FOLIO = ? ";	    	
	    	Object[] parametros = new Object[] {salidaId,folio};	    	
	    	salInvitado = enocJdbc.queryForObject(comando, new SalInvitadoMapper(), parametros);	    	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|mapeaRegId|:"+ex);
		}
		return salInvitado;
	}
    
    public String getFolio(String salidaId) {
		String folio = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS FOLIO FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?,'99999')"; 
			
			Object[] parametros = new Object[] {salidaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				folio = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|folio|:"+ex);
		}
		return folio;
	}
    
    public List<SalInvitado> listAll(String orden ) {
		List<SalInvitado> lista = new ArrayList<SalInvitado>();		
		try{
			String comando = "SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO FROM ENOC.SAL_INVITADO "+orden;			
			lista = enocJdbc.query(comando, new SalInvitadoMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public List<SalInvitado> getListAll(String salidaId, String orden ) {
		List<SalInvitado> lista = new ArrayList<SalInvitado>();		
		try{
			String comando = "SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = ? "+orden;			
			Object[] parametros = new Object[] {salidaId};			
			lista = enocJdbc.query(comando, new SalInvitadoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalInvitadoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<SalInvitado> lisInvitados(String salidaId, String orden ){
		List<SalInvitado> lista 	= new ArrayList<SalInvitado>();		
		try{
			String comando = "SELECT SALIDA_ID, FOLIO, NOMBRE, TIPO FROM ENOC.SAL_INVITADO WHERE SALIDA_ID = TO_NUMBER(?,'99999') "+orden;	
			Object[] parametros = new Object[] {salidaId};			
			lista = enocJdbc.query(comando, new SalInvitadoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalInvitado|lisInvitados|:"+ex);
		}		
		return lista;
	}
}
