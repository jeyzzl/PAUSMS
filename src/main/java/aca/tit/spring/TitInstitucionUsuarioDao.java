package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitInstitucionUsuarioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitInstitucionUsuario titUsuario ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_INSTITUCION_USUARIO (INSTITUCION, USUARIO) VALUES(?,?) ";
			
			Object[] parametros = new Object[] {titUsuario.getInstitucion(), titUsuario.getUsuario()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionUsuarioDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean deleteReg(String institucion, String usuario) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_INSTITUCION_USUARIO WHERE INSTITUCION = ? AND USUARIO = ?";
			
			Object[] parametros = new Object[] {institucion, usuario};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionUsuarioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitInstitucionUsuario mapeaRegId(String institucion, String usuario) {
		TitInstitucionUsuario titUsuario = new TitInstitucionUsuario();		 
		try{
			String comando = "SELECT INSTITUCION, USUARIO FROM ENOC.TIT_INSTITUCION_USUARIO WHERE INSTITUCION = ? AND USUARIO = ?";
			
			Object[] parametros = new Object[] {institucion, usuario};
			titUsuario = enocJdbc.queryForObject(comando, new TitInstitucionUsuarioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionUsuarioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return titUsuario;
		
	}
	
	public boolean existeReg(String institucion, String usuario) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_INSTITUCION_USUARIO WHERE INSTITUCION = ? AND USUARIO = ?";			
			Object[] parametros = new Object[] {institucion, usuario};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionUsuarioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitInstitucionUsuario> lisInstitucionesPorUsuario( String usuario, String orden) {
		List<TitInstitucionUsuario> lista		= new ArrayList<TitInstitucionUsuario>();
		try{
			String comando = " SELECT INSTITUCION, USUARIO FROM ENOC.TIT_INSTITUCION_USUARIO WHERE USUARIO = ? "+orden;
			Object[] parametros = new Object[] {usuario};
			lista = enocJdbc.query(comando, new TitInstitucionUsuarioMapper(), parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitInstitucionUsuarioDao|lisInstitucionesPorUsuario|:"+ex);
		}
		return lista;
	}
	
}
