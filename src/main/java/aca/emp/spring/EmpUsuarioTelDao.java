package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpUsuarioTelDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpUsuarioTel usuarioTel ) {
		boolean ok = false;
		
		try{
			
			String comando = "INSERT INTO ENOC.USUARIO_TEL"+ 
				" (CODIGO_PERSONAL, CARGAS, MODALIDADES)"+
				" VALUES( ?, ?, ? )";
						
			Object[] parametros = new Object[] {usuarioTel.getCodigoPersonal(),usuarioTel.getTelefono(),usuarioTel.getExtension(),usuarioTel.getLugar()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|insertReg|:"+ex);
		}
		return ok;
	}	

	public boolean updateReg( EmpUsuarioTel usuarioTel ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.USUARIO_TEL"+ 
				" SET "+
				" TELEFONO = ?,"+
				" EXTENSION = ?," +
				" LUGAR "+
				" WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {usuarioTel.getTelefono(),usuarioTel.getExtension(),usuarioTel.getLugar(),usuarioTel.getCodigoPersonal()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.USUARIO_TEL"+ 
				"WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public EmpUsuarioTel mapeaRegId(  String codigoPersonal ) {
		EmpUsuarioTel usuarioTel = new EmpUsuarioTel();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TELEFONO, EXTENSION, LUGAR" +						
				" FROM ENOC.USUARIO_TEL WHERE CODIGO_PERSONAL = ?"; 
			
				Object[] parametros = new Object[] {codigoPersonal};
				usuarioTel = enocJdbc.queryForObject(comando, new EmpUsuarioTelMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|mapeaRegId|:"+ex);
		}
		return usuarioTel;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.USUARIO_TEL "+ 
				"WHERE CODIGO_PERSONAL = ? ";
		
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getExtension( String codigoEmpleado) {
		String extension		= "-";
		
		try{
			String comando = "SELECT EXTENSION FROM ENOC.USUARIO_TEL WHERE CODIGO_PERSONAL = ? "; 
			
			Object[] parametros = new Object[] {codigoEmpleado};
			extension = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|getExtension|:"+ex);
		}
		return extension;
	}
	
	public List<EmpUsuarioTel> getListAll( String orden ) {
		List<EmpUsuarioTel> lista	= new ArrayList<EmpUsuarioTel>();
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, TELEFONO, EXTENSION, LUGAR FROM ENOC.USUARIO_TEL "+ orden; 
			
			lista = enocJdbc.query(comando, new EmpUsuarioTelMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.UsuarioTelUtil|getListAll|:"+ex);
		}
		return lista;
	}
	
}