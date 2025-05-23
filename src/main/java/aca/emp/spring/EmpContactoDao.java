package aca.emp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpContactoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpContacto empContacto) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ENOC.EMP_CONTACTO(EMPLEADO_ID, CORREO, TELEFONO) VALUES(?, ?, ?)";			
			Object[] parametros = new Object[] {empContacto.getEmpleadoId(), empContacto.getCorreo(), empContacto.getTelefono() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpContacto empContacto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EMP_CONTACTO SET CORREO = ?, TELEFONO = ? WHERE EMPLEADO_ID = ?";
			Object[] parametros = new Object[] {empContacto.getCorreo(), empContacto.getTelefono(), empContacto.getEmpleadoId()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal) {		
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID = ?";	
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|deleteReg|:"+ex);	
		}
		return ok;
	}
	
	public EmpContacto mapeaRegId( String codigoPersonal) {
		EmpContacto empContacto = new EmpContacto();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT EMPLEADO_ID, CORREO, TELEFONO FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID = ?";				
				empContacto = enocJdbc.queryForObject(comando, new EmpContactoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|mapeaRegId|:"+ex);
		}
		return empContacto;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<EmpContacto> lisContactos( String orden) {
		List<EmpContacto> lista		= new ArrayList<EmpContacto>();
		try{
			String comando = "SELECT EMPLEADO_ID, CORREO, TELEFONO FROM ENOC.EMP_CONTACTO "+orden;
			lista = enocJdbc.query(comando, new EmpContactoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|lisContratos|:"+ex);
		}		
		return lista;
	}
	
	public List<EmpContacto> lisDirectores( String orden) {
		List<EmpContacto> lista		= new ArrayList<EmpContacto>();
		try{
			String comando = "SELECT EMPLEADO_ID, CORREO, TELEFONO FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_FACULTAD) "+orden;
			lista = enocJdbc.query(comando, new EmpContactoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|lisContratos|:"+ex);
		}		
		return lista;
	}
	
	public List<EmpContacto> lisCoordinadores( String orden) {
		List<EmpContacto> lista		= new ArrayList<EmpContacto>();
		try{
			String comando = "SELECT EMPLEADO_ID, CORREO, TELEFONO FROM ENOC.EMP_CONTACTO WHERE EMPLEADO_ID IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_CARRERA) "+orden;
			lista = enocJdbc.query(comando, new EmpContactoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|lisContratos|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,EmpContacto> mapaContactos( ){		
		HashMap<String,EmpContacto> mapa = new HashMap<String,EmpContacto>();
		List<EmpContacto> lista		= new ArrayList<EmpContacto>();		
		try{
			String comando = "SELECT EMPLEADO_ID, CORREO, TELEFONO FROM ENOC.EMP_CONTACTO";
			lista = enocJdbc.query(comando, new EmpContactoMapper());
			for (EmpContacto contacto : lista ) {
				mapa.put(contacto.getEmpleadoId(), contacto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpContactoDao|mapaContactos|:"+ex);
		}		
		return mapa;
	}
}