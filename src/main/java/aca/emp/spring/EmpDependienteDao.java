package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpDependienteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpDependiente empDependiente ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.EMP_DEPENDIENTE"
				+ "(ID_EMPLEADO, FOLIO, ID_DEPENDIENTE, RELACION, COTEJADO) "
 				+ "VALUES( ?, ?, ?, UPPER(?), UPPER(?))";
			
			Object[] parametros = new Object[] {empDependiente.getIdEmpleado(),empDependiente.getFolio(),empDependiente.getIdDependiente(),empDependiente.getRelacion(),empDependiente.getCotejado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EmpDependiente empDependiente ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_DEPENDIENTE "
					+ " SET ID_DEPENDIENTE = ? ,"
					+ " RELACION = UPPER(?),"
					+ " COTEJADO = UPPER(?)"
					+ " WHERE ID_EMPLEADO = ? AND FOLIO = ?";
			
			Object[] parametros = new Object[] {empDependiente.getIdDependiente(), empDependiente.getRelacion(), empDependiente.getCotejado(), empDependiente.getIdEmpleado(), empDependiente.getFolio()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public EmpDependiente mapeaRegId( String idEmpleado, String folio) {
		EmpDependiente dependientes = new EmpDependiente();
		try{
			String comando = "SELECT ID_EMPLEADO, FOLIO, ID_DEPENDIENTE, RELACION, COTEJADO"
					+ " FROM ENOC.EMP_DEPENDIENTE"
					+ " WHERE ID_EMPLEADO = ?"
					+ " AND FOLIO = ?";
			Object[] parametros = new Object[] {idEmpleado,folio};
			dependientes = enocJdbc.queryForObject(comando, new EmpDependienteMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|mapeaRegId|:"+ex);
		}
		
		return dependientes;
	}
	
	public boolean existeReg(String idEmpleado, String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ENOC.EMP_DEPENDIENTE WHERE ID_EMPLEADO = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {idEmpleado,folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeDependiente(String idEmpleado, String id) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM ENOC.EMP_DEPENDIENTE WHERE ID_EMPLEADO = ? AND FOLIO = ?";
			Object[] parametros = new Object[] {idEmpleado,id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|existeRegId|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombre( String empleadoId, String id){		
		
		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
			Object[] parametros = new Object[] {empleadoId, id};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT NOMBRE FROM ARON.EMPLEADO_DEPENDIENTES WHERE EMPLEADO_ID = ? AND ID = ?";
				nombre 	= enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|getNombre|:"+ex);
		}		
		return nombre;
	}
	
	public List<EmpDependiente> lisDependientes( String idEmpleado, String orden ){
		
		List<EmpDependiente> lista	    = new ArrayList<EmpDependiente>();

		try{
			String comando = "SELECT ID_EMPLEADO, FOLIO, ID_DEPENDIENTE, RELACION, COTEJADO FROM ENOC.EMP_DEPENDIENTE WHERE ID_EMPLEADO = ? "+ orden;
			Object[] parametros = new Object[] {idEmpleado};
			lista = enocJdbc.query(comando, new EmpDependienteMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDependienteDao|getLista|:"+ex);
		
		}
		
		return lista;
	}

}