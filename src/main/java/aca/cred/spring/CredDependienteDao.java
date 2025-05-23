package aca.cred.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredDependienteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CredDependiente dependiente ){

		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CRED_DEPENDIENTE(ID_DEPENDIENTE, ID_EMPLEADO, EMP_NOMBRE, EMP_APELLIDOS, RELACION, PUESTO, "
					+ " DEPARTAMENTO, COTEJADO, FECHA, FOLIO, F_ACTUALIZA, DEP_NOMBRE, DEP_APELLIDOS)"
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, NOW(), ?, ?)";
			Object[] parametros = new Object[] {
				dependiente.getIdDependiente(),dependiente.getIdEmpleado(),dependiente.getEmpNombre(), dependiente.getEmpApellidos(),dependiente.getRelacion(),
				dependiente.getPuesto(),dependiente.getDepartamento(),dependiente.getCotejado(),dependiente.getFecha(), dependiente.getFolio(), dependiente.getDepNombre(), dependiente.getDepApellidos()					
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredDependiente dependiente ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CRED_DEPENDIENTE SET ID_EMPLEADO= ?, EMP_NOMBRE= ?, EMP_APELLIDOS= ?, RELACION= ?, PUESTO= ?, DEPARTAMENTO= ?, "
					+ " COTEJADO= ?, FECHA= ?, FOLIO= ?, F_ACTUALIZA = NOW(), DEP_NOMBRE = ? , DEP_APELLIDOS = ? "
					+ " WHERE ID_DEPENDIENTE = ?";
			Object[] parametros = new Object[] {
					dependiente.getIdEmpleado(),dependiente.getEmpNombre(), dependiente.getEmpApellidos(),dependiente.getRelacion(),
					dependiente.getPuesto(),dependiente.getDepartamento(),dependiente.getCotejado(),dependiente.getFecha(), dependiente.getFolio(), 
					dependiente.getDepNombre(), dependiente.getDepApellidos(), dependiente.getIdDependiente()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String idDependiente ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ? ";
			Object[] parametros = new Object[] {idDependiente};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CredDependiente mapeaRegId( String idDependiente){
		
		CredDependiente evento = new CredDependiente();		
		
		try{
			String comando = "SELECT COUNT(ID_DEPENDIENTE) FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ?";
			Object[] parametros = new Object[] {idDependiente};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT ID_DEPENDIENTE, ID_EMPLEADO, EMP_NOMBRE, EMP_APELLIDOS, RELACION, PUESTO,"
					+ " DEPARTAMENTO, COTEJADO, FECHA, FOLIO, F_ACTUALIZA, DEP_NOMBRE, DEP_APELLIDOS "
					+ " FROM ENOC.CRED_DEPENDIENTE"
					+ " WHERE ID_DEPENDIENTE = ?";			
				evento = enocJdbc.queryForObject(comando, new CredDependienteMapper(), parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|mapeaRegId|:"+ex);
		}
		
		return evento;
	}
	
	public boolean existeReg( String idDependiente){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CRED_DEPENDIENTE WHERE ID_DEPENDIENTE = ? "; 
			Object[] parametros = new Object[] {idDependiente};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String codigoPersonal){

		String maximo			= "1";
		
		try{
			String comando = "SELECT TO_CHAR((MAX(TO_NUMBER(CODIGO_PERSONAL,'9999999')+1)) AS MAXIMO FROM ENOC.CRED_DEPENDIENTE WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}	
	
	public List<CredDependiente> lisTodos( String orden){
		
		List<CredDependiente> lista		= new ArrayList<CredDependiente>();

		try{
			String comando = "SELECT ID_DEPENDIENTE, ID_EMPLEADO, EMP_NOMBRE, EMP_APELLIDOS, RELACION, PUESTO,"
					+ " DEPARTAMENTO, COTEJADO, FECHA, FOLIO, F_ACTUALIZA, DEP_NOMBRE, DEP_APELLIDOSFROM"
					+ " FROM ENOC.CRED_DEPENDIENTE "+orden;	 
			lista = enocJdbc.query(comando, new CredDependienteMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credDependienteDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
}
