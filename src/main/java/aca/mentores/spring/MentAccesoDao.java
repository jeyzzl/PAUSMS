/**
 * 
 */
package aca.mentores.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Elifo
 *
 */
@Component
public class MentAccesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentAcceso acceso){
		boolean ok = true;
	
		try{
			String comando = "INSERT INTO ENOC.MENT_ACCESO(CODIGO_PERSONAL, "+ 
					"ACCESO) VALUES( ?, ?)";
			Object[] parametros = new Object[] {acceso.getCodigoPersonal(),acceso.getAcceso()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|insertReg|:"+ex);
		
		}
		return ok;
	}
		
	public boolean updateReg(MentAcceso acceso){
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENT_ACCESO "+ 
					"SET ACCESO = ? WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {acceso.getAcceso(),acceso.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
		
	public boolean deleteReg(String codigoPersonal){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_ACCESO "+ 
				"WHERE CODIGO_PERSONAL= ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|deleteReg|:"+ex);
		
		}
		return ok;
	}
	
	public MentAcceso mapeaRegId(String codigoPersonal){
		MentAcceso  acceso = new MentAcceso();		
		try{ 
			String comando 	= "SELECT COUNT(*) FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando 	= "SELECT CODIGO_PERSONAL, ACCESO FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ?";
				acceso 		= enocJdbc.queryForObject(comando, new MentAccesoMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|mapeaRegId|:"+ex);
		}
		
		return acceso;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean tieneAccesoCarrera(String codigoPersonal, String carreraId){
		boolean 		ok 		= false;

		try{
			String comando = "SELECT CODIGO_PERSONAL" +
					" FROM ENOC.MENT_ACCESO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND ACCESO LIKE '%'||?||'%'";
			Object[] parametros = new Object[] {codigoPersonal, carreraId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|tieneAccesoCarrera|:"+ex);
		}
		
		return ok;
	}
	
	public List<MentAcceso> getListAll( String orden ){
		
		List<MentAcceso> lista		= new ArrayList<MentAcceso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ACCESO FROM ENOC.MENT_ACCESO "+ orden; 
			lista = enocJdbc.query(comando, new MentAccesoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|getListAll|:"+ex);
		}
		
		return lista;
	}

	public List<MentAcceso> getLista(String codigoPersonal, String orden ){
		
		List<MentAcceso> lista	= new ArrayList<MentAcceso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ACCESO FROM ENOC.MENT_ACCESO WHERE CODIGO_PERSONAL = ? "+ orden;
			lista = enocJdbc.query(comando, new MentAccesoMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAccesoDao|getLista|:"+ex);
		}		
		return lista;
	}
}