package aca.salida.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalClubDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalClub salidaClub ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_CLUB (GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, ALUMNOS) VALUES(TO_NUMBER(?,'9999'), ?, ?, ?) ";
			
			Object[] parametros = new Object[] {
				salidaClub.getGrupoId(),salidaClub.getGrupoNombre(),salidaClub.getResponsable(),salidaClub.getAlumnos()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(SalClub salidaClub) {
		boolean ok = false;

        try{
             String comando ="UPDATE ENOC.SAL_CLUB"
                    + " SET GRUPO_NOMBRE = ?, RESPONSABLE = ?, ALUMNOS = ?"
                    + " WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
             
            Object[] parametros = new Object[]{
            	salidaClub.getGrupoNombre(),salidaClub.getResponsable(),salidaClub.getAlumnos(),salidaClub.getGrupoId()
            };
 			if (enocJdbc.update(comando,parametros)==1) {
 				ok = true;
 			}    
           
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalidaClubDao|updateReg|:" + ex);
        }

        return ok;
    }

    public boolean deleteReg( String grupoId ) {
        
    	boolean ok = false;

        try {
        	String comando = "DELETE FROM ENOC.SAL_CLUB WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
                    
            Object[] parametros = new Object[]{ grupoId };
            if (enocJdbc.update(comando, parametros) == 1) {
            	ok = true;
            }
        }catch (Exception ex){
        	System.out.println("Error - aca.salida.spring.SalidaClubDao|deleteReg|:" + ex);
        }

        return ok;
    }

	public SalClub mapeaRegId(String grupoId) {
		SalClub salidaClub = new SalClub();	
		try{ 
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, ALUMNOS FROM ENOC.SAL_CLUB WHERE GRUPO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[] {grupoId};
			salidaClub = enocJdbc.queryForObject(comando, new SalClubMapper(), parametros);							
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|mapeaRegId|:"+ex);
		}

		return salidaClub;
	}
	
	public boolean existeReg(String grupoId) {
		boolean ok 	= false;

		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_CLUB WHERE GRUPO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
					
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getAlumnos(String grupoId) {
		String alumnos 	= "-";

		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_CLUB WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT ALUMNOS FROM ENOC.SAL_CLUB WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
				alumnos = enocJdbc.queryForObject(comando,String.class, parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|existeReg|:"+ex);
		}
		
		return alumnos;
	}
	
	public String maximoReg() {
		String maximo 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(GRUPO_ID)+1, 1) AS MAXIMO FROM ENOC.SAL_CLUB";			
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<SalClub> getListAll( String orden ) {
		List<SalClub> lista 	= new ArrayList<SalClub>();		
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, ALUMNOS FROM ENOC.SAL_CLUB "+orden;			
			lista = enocJdbc.query(comando, new SalClubMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalClub> listResponsable( String responsable, String orden ) {
		List<SalClub> lista 	= new ArrayList<SalClub>();		
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, ALUMNOS FROM ENOC.SAL_CLUB WHERE RESPONSABLE = ? "+orden; 
			Object[] parametros = new Object[] {responsable};			
			lista = enocJdbc.query(comando,  new SalClubMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaClubDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}
