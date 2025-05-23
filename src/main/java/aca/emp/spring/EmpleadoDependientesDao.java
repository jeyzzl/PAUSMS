package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoDependientesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpleadoDependientes empleadoDependientes ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ARON.EMPLEADO_DEPENDIENTES"
				+ "(EMPLEADO_ID, ID, NOMBRE, TO_CHAR(BDAY,'DD/MM/YYYY') AS BDAY, RELACION_ID, MATRICULA " 
 				+ "VALUES( ?, ?, UPPER(?), TO_DATE(?, 'DD/MM/YYYY'), ?, ?)";
			
			Object[] parametros = new Object[] {empleadoDependientes.getEmpleadoId(), empleadoDependientes.getId(), empleadoDependientes.getNombre(), empleadoDependientes.getBday(), empleadoDependientes.getRelacionId(), empleadoDependientes.getMatricula()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpleadoDependientes empleadoDependientes ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ARON.EMPLEADO_DEPENDIENTES "
					+ " SET NOMBRE = UPPER(?),"
					+ " BDAY = TO_DATE(?, 'DD/MM/YYYY') ,"
					+ " RELACION_ID = ?,"
					+ " MATRICULA = ?"
					+ " WHERE EMPLEADO_ID = ? AND ID = ?";			
			Object[] parametros = new Object[] {empleadoDependientes.getNombre(), empleadoDependientes.getBday(), empleadoDependientes.getRelacionId(), empleadoDependientes.getMatricula(), empleadoDependientes.getEmpleadoId(), empleadoDependientes.getId()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public EmpleadoDependientes mapeaRegId( String empleadoId, String id) {
		EmpleadoDependientes dependientes = new EmpleadoDependientes();
		try{
			String comando = "SELECT EMPLEADO_ID, ID, NOMBRE, TO_CHAR(BDAY,'DD/MM/YYYY') AS BDAY, RELACION_ID, MATRICULA"
					+ " FROM ARON.EMPLEADO_DEPENDIENTES"
					+ " WHERE EMPLEADO_ID = ?"
					+ " AND ID = ?";
			Object[] parametros = new Object[] {empleadoId,id};
			dependientes = enocJdbc.queryForObject(comando, new EmpleadoDependientesMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|mapeaRegId|:"+ex);
		}
		
		return dependientes;
	}
	
	public boolean existeReg(String empleadoId, String id) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(ID) FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
			Object[] parametros = new Object[] {empleadoId,id};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeDependiente(String empleadoId, String id) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(ID) FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
			Object[] parametros = new Object[] {empleadoId,id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|existeRegId|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombre(String empleadoId, String id) {
		
		String nombre			= "X";
		
		try{
			String comando = "SELECT COUNT(NOMBRE) FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
			Object[] parametros = new Object[] {empleadoId,id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT NOMBRE FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<EmpleadoDependientes> getLista( String id, String orden ){
		
		List<EmpleadoDependientes> lista	    = new ArrayList<EmpleadoDependientes>();

		try{
			String comando = "SELECT EMPLEADO_ID, ID, NOMBRE, BDAY, RELACION_ID, MATRICULA FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = "+id+" "+ orden;
			lista = enocJdbc.query(comando, new EmpleadoDependientesMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpleadoDependientesDao|getLista|:"+ex);
		
		}
		
		return lista;
	}

}