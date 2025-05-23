package aca.cred.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredEmpleadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CredEmpleado per ){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.CRED_EMPLEADO"
					+ " (ID, CLAVE, NOMBRE, APELLIDOS, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, IMPRIMIR, TIPOCRED)"
					+ " VALUES(TO_NUMBER(?,'9999'),?,?,?,?,?,?,?,?,?)";
			Object[] parametros = new Object[] {
				per.getId(),per.getClave(),per.getNombre(),per.getApellidos(),per.getPuesto(),per.getDepartamento(),per.getStatus(),
				per.getCotejado(),per.getImprimir(),per.getTipoCred()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredEmpleado per ){
		boolean ok = false;
	
		try{
			String comando = "UPDATE ENOC.CRED_EMPLEADO"
					+ " SET "
					+ " NOMBRE = ?,"
					+ " APELLIDOS = ?,"
					+ " PUESTO = ?,"
					+ " DEPARTAMENTO = ?,"
					+ " STATUS = ?,"
					+ " COTEJADO = ?,"
					+ " IMPRIMIR = ?,"
					+ " TIPOCRED = ?"
					+ " WHERE ID = ? AND CLAVE = ? ";
			Object[] parametros = new Object[] {
				per.getNombre(),per.getApellidos(),per.getPuesto(),per.getDepartamento(),per.getStatus(),
				per.getCotejado(),per.getImprimir(),per.getTipoCred(),per.getId(),per.getClave()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public CredEmpleado mapeaRegId(String id, String clave){
		CredEmpleado per = new CredEmpleado();
		
		try{
			String comando = "SELECT ID, CLAVE, NOMBRE, APELLIDOS, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, IMPRIMIR, TIPOCRED"
					+ " FROM ENOC.CRED_EMPLEADO WHERE ID = TO_NUMBER(?,999) AND CLAVE = ?"; 
			Object[] parametros = new Object[] {id, clave};
			per = enocJdbc.queryForObject(comando, new CredEmpleadoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|mapeaRegId|:"+ex);
		}
		
		return per;
	}
	
	
	public boolean existeReg(String id, String clave){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) NOMBRE FROM ENOC.CRED_EMPLEADO"
					+ " WHERE ID = ? AND CLAVE = ?";
			Object[] parametros = new Object[] {id, clave};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maxReg(){
		String maximo = "0";
		
		try{
			String comando = " SELECT COALESCE(MAX(TO_NUMBER(ID))+1,0) AS MAXIMO FROM ENOC.CRED_EMPLEADO";
			maximo = enocJdbc.queryForObject(comando,String.class);
						
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|maxReg|:"+ex);
		}
		
		return maximo;
	}
	
	
	public List<CredEmpleado> getListAll(String orden ){
		
		List<CredEmpleado> lista		= new ArrayList<CredEmpleado>();
		
		try{
			String comando = "SELECT ID, CLAVE, NOMBRE, APELLIDOS, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, IMPRIMIR, TIPOCRED FROM ENOC.CRED_EMPLEADO "+orden; 
			lista = enocJdbc.query(comando, new CredEmpleadoMapper());

		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.CredEmpleadoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, CredEmpleado> mapaCredenciales( ) {
		List<CredEmpleado> lista 				= new ArrayList<CredEmpleado>();		
		HashMap<String, CredEmpleado> mapa		= new HashMap<String,CredEmpleado>();		
		try{
			String comando = "SELECT ID, CLAVE, NOMBRE, APELLIDOS, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, IMPRIMIR, TIPOCRED FROM ENOC.CRED_EMPLEADO";	
			lista = enocJdbc.query(comando, new CredEmpleadoMapper());
			for ( CredEmpleado emp : lista) {
				mapa.put(emp.getClave(), emp);
			}			
		}catch(Exception ex){
			System.out.println("Error -aca.cred.spring.CredEmpleadoDao|mapaCredenciales|:"+ex);
		}
		
		return mapa;
	}
	
}
