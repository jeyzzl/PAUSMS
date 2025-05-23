package aca.parametros.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuarioParametrosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(UsuarioParametros parametros) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.USUARIO_PARAMETROS(CODIGO_PERSONAL,CARGAS,MODALIDADES,F_INICIO,F_FINAL)" 
						+ " VALUES(?,?,?,?,?)";
			Object[] param = new Object[] {
				parametros.getCodigoPersonal(), parametros.getCargas(), parametros.getModalidades(), parametros.getfInicio(), parametros.getfFinal()
			};
			if (enocJdbc.update(comando,param) == 1){
				ok = true;
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.UsuarioParametrosDao|insertReg|:"+ex);	
		}
		return ok;
	}
	
	public boolean updateReg(UsuarioParametros parametros){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.USUARIO_PARAMETROS" 
							+" SET CARGAS = ?," 
							+" MODALIDADES = ?," 
							+" F_INICIO = ?," 
							+" F_FINAL = ?" 
							+" WHERE CODIGO_PERSONAL = ?";
			Object[] param = new Object[] {
				parametros.getCargas(), parametros.getModalidades(), parametros.getfInicio(), parametros.getfFinal(), parametros.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,param) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.UsuarioParametrosDao|updateReg|:"+ex); 
			}	
		return ok;
	}
	
	public UsuarioParametros mapeaRegId(String codigoPersonal) {
		UsuarioParametros usuarioParametros = new UsuarioParametros();
		try{
			String comando 	= " SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES, F_INICIO, F_FINAL"
							+ " FROM ENOC.USUARIO_PARAMETROS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			usuarioParametros = enocJdbc.queryForObject(comando, new UsuarioParametrosMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.UsuarioParametrosDao|mapeaRegId|:"+ex);
		}
		return usuarioParametros;
	}
	
	public boolean existeReg(String codigoPersonal) {
		boolean	ok 		= false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.USUARIO_PARAMETROS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.parametros.spring.UsuarioParametrosDao|existeReg|:"+ex);
		}
		return ok;
	}
}
