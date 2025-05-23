package aca.exp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExpEmpleadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExpEmpleado emp ) {
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.EXP_EMPLEADO(CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, FECHA, USUARIO, ARCHIVO, NOMBRE)"
					+ " VALUES(?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), SYSDATE, ?, ?, ?)";
			Object[] parametros = new Object[] {
				emp.getCodigoPersonal(), emp.getDocumentoId(), emp.getHoja(), emp.getUsuario(), emp.getArchivo(), emp.getNombre() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|insertReg|:"+ex);
		}
		
		return ok;
	}

	public boolean updateReg( ExpEmpleado emp ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXP_EMPLEADO SET FECHA = SYSDATE, USUARIO = ?, ARCHIVO = ?, NOMBRE = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'99')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
				emp.getUsuario(), emp.getArchivo(), emp.getNombre(), emp.getCodigoPersonal(), emp.getDocumentoId(), emp.getHoja()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|updateReg|:"+ex);
		}		
		return ok;
	}	

	public boolean deleteReg( String codigoPersonal, String documentoId, String hoja ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.EXP_EMPLEADO WHERE CODIGO_PERSONAL = ? AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|deleteReg|:"+ex);
		}
		return ok;
	}

	public ExpEmpleado mapeaRegId(String codigoPersonal, String documentoId, String hoja) {
		ExpEmpleado emp = new ExpEmpleado();			
		try{
			String comando = "SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, TO_CHAR(FECHA, 'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO, ARCHIVO, NOMBRE"
					+ " FROM ENOC.EXP_EMPLEADO "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND DOCUMENTO_ID = TO_NUMBER(?,'999')"
					+ " AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			emp = enocJdbc.queryForObject(comando, new ExpEmpleadoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|mapeaRegId|:"+ex);
		}
		
		return emp;
	}

	public boolean existeReg(String codigoPersonal, String documentoId, String hoja) {
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.EXP_EMPLEADO WHERE CODIGO_PERSONAL = ? AND DOCUMENTO_ID = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, documentoId, hoja};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<ExpEmpleado> lisPorEmpleado( String codigoPersonal, String orden ) {
		List<ExpEmpleado> lista 	= new ArrayList<ExpEmpleado>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO, NOMBRE"
					+ " FROM ENOC.EXP_EMPLEADO"
					+ " WHERE CODIGO_PERSONAL = ? "+ orden;
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ExpEmpleadoMapperCorto(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|lisAlumno|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String, ExpEmpleado> mapaPorEmpleado(String codigoPersonal){
		HashMap<String, ExpEmpleado> mapa = new HashMap<String, ExpEmpleado>();
		List<ExpEmpleado> lista 		 = new ArrayList<ExpEmpleado>();
		String comando		= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, DOCUMENTO_ID, HOJA, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO, NOMBRE FROM ENOC.EXP_EMPLEADO WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new ExpEmpleadoMapperCorto(), parametros);
			for(ExpEmpleado m : lista){
				mapa.put(m.getDocumentoId()+m.getHoja(), m);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|mapArchDocAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaDocEmpleados(){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DISTINCT(DOCUMENTO_ID) AS LLAVE, DOCUMENTO_ID AS VALOR FROM ENOC.EXP_EMPLEADO";
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|mapaDocEmpleados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaDocDelEmpleado(){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||DOCUMENTO_ID AS LLAVE, COUNT(HOJA) AS VALOR FROM ENOC.EXP_EMPLEADO GROUP BY CODIGO_PERSONAL,DOCUMENTO_ID";
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpEmpleadoDao|mapaDocDelEmpleado|:"+ex);
		}		
		return mapa;
	}
}