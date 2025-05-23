// Clase Util para la tabla de Carga
package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpDatosDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(EmpDatos datos){
		
		boolean ok = false;
		try{
			String comando = "INSERT INTO EMP_DATOS (ID_EMPLEADO, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, IMPRESO, ID, NOMBRE, APELLIDOS, TIPOCRED)"
					+" VALUES(?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99999'), ?, ?, ?)";
			Object[] parametros = new Object[] {
				datos.getIdEmpleado(),datos.getPuesto(),datos.getDepartamento(),datos.getStatus(),datos.getCotejado(),datos.getImpreso(),
				datos.getId(),datos.getNombre(),datos.getApellidos(),datos.getTipocred()
			};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|insertReg:"+ex);
		}
		
		return ok;		
	}
	
	public boolean updateReg(EmpDatos datos){
		
		boolean ok = false;
		try{
			String comando = "UPDATE EMP_DATOS SET ID_EMPLEADO = ?, PUESTO = ?, DEPARTAMENTO = ?, STATUS = ?, COTEJADO = ?, IMPRESO = ?, NOMBRE = ?, APELLIDOS = ?, TIPOCRED = ?"
					+ " WHERE ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {
				datos.getIdEmpleado(),datos.getPuesto(),datos.getDepartamento(),datos.getStatus(),datos.getCotejado(),datos.getImpreso(),
				datos.getNombre(),datos.getApellidos(),datos.getTipocred(),datos.getId()
			};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|updateReg:"+ex);
		}
		
		return ok;		
	}
	
	public EmpDatos mapeaRegId(String idEmpleado) {
		
		EmpDatos objeto = new EmpDatos();
		try{
			String comando = "SELECT ID_EMPLEADO,PUESTO,DEPARTAMENTO,STATUS,COTEJADO,IMPRESO,ID,NOMBRE, APELLIDOS, TIPOCRED"
					+ " FROM ENOC.EMP_DATOS"
					+ " WHERE ID_EMPLEADO = ?";
			Object[] parametros = new Object[] {idEmpleado};
			objeto = enocJdbc.queryForObject(comando, new EmpDatosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String idEmpleado) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
			Object[] parametros = new Object[] {idEmpleado};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getDepartamento(String id){ 		
		String depto = "X";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ? ";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT ENOC.EMP_CCOSTO(ID) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
 				depto = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|getDepartamento|:"+ex);
		
		}
		
		return depto;
	}
	
	public String getNombreDepartamento(String id){ 		
		String depto = "X";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT DEPARTAMENTO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
 				depto = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|getNombreDepartamento|:"+ex);
		}
		
		return depto;
	}
	
	public String getPuesto(String id ){
		String puesto = "X";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ? ";
			Object[] parametros = new Object[] {id};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				comando = "SELECT ENOC.EMP_PUESTO2(ID) FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
 				puesto = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|getPuesto|:"+ex);		
		}
		
		return puesto;
	}
	
	public List<EmpDatos> lisTodos( String orden ) {
		
		List<EmpDatos> lista	= new ArrayList<EmpDatos>();
		String comando	= "";
		
		try{
			comando = " SELECT ID_EMPLEADO,PUESTO,DEPARTAMENTO,STATUS,COTEJADO,IMPRESO,ID,NOMBRE, APELLIDOS, TIPOCRED FROM ENOC.EMP_DATOS "+orden;
			lista = enocJdbc.query(comando, new EmpDatosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|lisTodos|:"+ex);
		}
		
		return lista;
	}

	public List<String> lisPorEmpleadoId(String empleadoId) {
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT ID_EMPLEADO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = ?";
			Object[] parametros = new Object[] {empleadoId};
			lista = enocJdbc.queryForList(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpDatosDao|lisPorEmpleadoId|:"+ex);
		}
		
		return lista;
	}

}