package aca.salida.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SalGrupoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(SalGrupo salGrupo ) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.SAL_GRUPO (GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS) VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, ?) ";			
			Object[] parametros = new Object[] {
				salGrupo.getGrupoId(),salGrupo.getGrupoNombre(), salGrupo.getResponsable(), salGrupo.getCorreo(),salGrupo.getUsuarios()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(SalGrupo salGrupo) {
		boolean ok = false;

        try{
             String comando = 
                    "UPDATE ENOC.SAL_GRUPO" + 
                    " SET GRUPO_NOMBRE = ?," +
                    " RESPONSABLE = ?," +
                    " CORREO = ?," +
                    " USUARIOS = ?" +
                    " WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
             
            Object[] parametros = new Object[]{
            		salGrupo.getGrupoNombre(),salGrupo.getResponsable(),salGrupo.getCorreo(),salGrupo.getUsuarios(),salGrupo.getGrupoId()
            };
 			if (enocJdbc.update(comando,parametros)==1) {
 				ok = true;
 			}    
           
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalidaGrupoDao|updateReg|:" + ex);
        }

        return ok;
    }

    public boolean deleteReg(String grupoId) {        
    	boolean ok = false;
        try {
             String comando = "DELETE FROM ENOC.SAL_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";           
             if (enocJdbc.update(comando, grupoId)==1) {
 				ok = true;
             }
        }catch (Exception ex){
            System.out.println("Error - aca.salida.spring.SalidaGrupoDao|deleteReg|:" + ex);
        }

        return ok;
    }

	public SalGrupo mapeaRegId( String grupoId) {
		SalGrupo salGrupo = new SalGrupo();
		
		try{ 
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS FROM ENOC.SAL_GRUPO WHERE GRUPO_ID = ?";			
			Object[] parametros = new Object[] {grupoId};
			salGrupo = enocJdbc.queryForObject(comando, new SalGrupoMapper(), parametros);							
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|mapeaRegId|:"+ex);
		}

		return salGrupo;
	}
	
	public SalGrupo mapeaRegSalida( String salidaId) {
		SalGrupo salGrupo = new SalGrupo();
		
		try{ 
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS " +
					" FROM 	ENOC.SAL_GRUPO" + 
					" WHERE GRUPO_ID IN (SELECT GRUPO_ID FROM ENOC.SAL_SOLICITUD WHERE SALIDA_ID = TO_NUMBER(?,'99999'))";			
			Object[] parametros = new Object[] {salidaId};
			salGrupo = enocJdbc.queryForObject(comando, new SalGrupoMapper(), parametros);
							
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|mapeaRegId|:"+ex);
		}

		return salGrupo;
	}
	
	public boolean existeReg(String grupoId) {
		boolean ok 	= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAL_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 	= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(GRUPO_ID)+1, 1) AS MAXIMO FROM ENOC.SAL_GRUPO";	
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public List<SalGrupo> getListAll( String orden ) {
		List<SalGrupo> lista 	= new ArrayList<SalGrupo>();
		
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS FROM ENOC.SAL_GRUPO "+orden; 
			
			lista = enocJdbc.query(comando, new SalGrupoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SalGrupo> getListUsuario(String codigoPersonal, String orden ) {
		List<SalGrupo> lista 	= new ArrayList<SalGrupo>();
		
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS"+			        
					" FROM ENOC.SAL_GRUPO WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%' "+orden; 
			
			lista = enocJdbc.query(comando, new SalGrupoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,SalGrupo> mapaTodos() {
		HashMap<String,SalGrupo> mapa = new HashMap<String,SalGrupo>();
		List<SalGrupo> lista		= new ArrayList<SalGrupo>();
		
		try{
			String comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS FROM ENOC.SAL_GRUPO";			        		
			lista = enocJdbc.query(comando, new SalGrupoMapper());
			for (SalGrupo grupo : lista ) {
				mapa.put(grupo.getGrupoId(), grupo);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.spring.SalidaGrupoDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}

}
