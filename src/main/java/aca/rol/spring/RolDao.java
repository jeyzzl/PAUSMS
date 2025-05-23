package aca.rol.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RolDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Rol rol ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ROL(ROL_ID, ROL_NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ";
			
			Object[] parametros = new Object[] {rol.getRolId(),rol.getRolNombre()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(Rol rol ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ROL "+ 
				"SET ROL_NOMBRE = ? "+
				"WHERE ROL_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {rol.getRolNombre(),rol.getRolId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String rolId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ROL "+ 
				"WHERE ROL_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {rolId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Rol mapeaRegId(  String rolId) {
		 Rol rol = new Rol();
		 
		try{
			String comando = "SELECT ROL_ID, ROL_NOMBRE "+
				"FROM ENOC.ROL WHERE ROL_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {rolId};
			rol = enocJdbc.queryForObject(comando, new RolMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return rol;
		
	}
	
	public boolean existeReg(String rolId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROL WHERE ROL_ID = TO_NUMBER(?,'999') "; 
			
			Object[] parametros = new Object[] {rolId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";				
		try{
			String comando = "SELECT COALESCE(MAX(ROL_ID)+1,1) AS MAXIMO FROM ENOC.ROL";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getNombreTipo( String rolId) {
		String rolNombre 		= "X";
		
		try{
			String comando = "SELECT ROL_NOMBRE FROM ENOC.ROL WHERE ROL_ID = ?";			
			Object[] parametros = new Object[] {rolId};
			rolNombre = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|getNombreTipo|:"+ex);
		}
		return rolNombre;
	}
	
	public List<Rol> getListAll( String orden) {
		List<Rol> lista		= new ArrayList<Rol>();
		String comando		= "";
		
		try{
			comando = "SELECT ROL_ID, ROL_NOMBRE FROM ENOC.ROL "+orden;	 
			
			lista = enocJdbc.query(comando, new RolMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolDo|getListAll|:"+ex);
		}
		return lista;
	}
}