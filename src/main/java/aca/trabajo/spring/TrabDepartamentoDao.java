package aca.trabajo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;

@Component
public class TrabDepartamentoDao {
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg( TrabDepartamento departamento) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TRAB_DEPT(DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL)"
					+ " VALUES( ?, ?, ?, ?, ? )";
			Object[] parametros = new Object[] {departamento.getDeptId(),departamento.getNombre(), departamento.getDetalles(), departamento.getEstado(), departamento.getCodigo_personal()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( TrabDepartamento departamento ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_DEPT SET NOMBRE = ?, DETALLES = ?, ESTADO = ?, CODIGO_PERSONAL = ? WHERE DEPT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {departamento.getNombre(),departamento.getDetalles(),departamento.getEstado(),departamento.getCodigo_personal(),departamento.getDeptId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String deptId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {deptId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public TrabDepartamento mapeaRegId(  String deptId) {
		
		TrabDepartamento departamento 	= new TrabDepartamento();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?,'999')";
				departamento = enocJdbc.queryForObject(comando, new TrabDepartamentoMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return departamento;
	}
	
	public boolean existeReg( String deptId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(DEPT_ID) FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(DEPT_ID)+1,1) AS MAXIMO FROM ENOC.TRAB_DEPT";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);				
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}

	public String getDirector( String deptId ) {
		String nombre			= "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?,'999')";	
			Object[] parametros = new Object[] {deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CODIGO_PERSONAL FROM ENOC.TRAB_DEPT WHERE DEPT_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNombrePlan|:"+ex);
		}
		return nombre;
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<TrabDepartamento> lisTodos( String orden ) {
		
		List<TrabDepartamento> lista = new ArrayList<TrabDepartamento>();		
		try{
			String comando = "SELECT DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL FROM ENOC.TRAB_DEPT"+ orden;				
			lista = enocJdbc.query(comando, new TrabDepartamentoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<TrabDepartamento> lisActivos( String orden ) {
		
		List<TrabDepartamento> lista = new ArrayList<TrabDepartamento>();		
		try{
			String comando = "SELECT DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL FROM ENOC.TRAB_DEPT WHERE ESTADO = 'A'"+ orden;				
			lista = enocJdbc.query(comando, new TrabDepartamentoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<TrabDepartamento> lisPorSupervisor(String codigoPersonal, String orden ) {
			
			List<TrabDepartamento> lista = new ArrayList<TrabDepartamento>();		
			try{
				String comando = "SELECT DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL FROM ENOC.TRAB_DEPT WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A'"+ orden;	
				Object[] parametros = new Object[] {codigoPersonal};
				lista = enocJdbc.query(comando, new TrabDepartamentoMapper(), parametros);
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.spring.TrabDepartamentoDao|lisPorSupervisor|:"+ex);
			}		
			return lista;
		}
	
	public HashMap<String, String> mapaDeptNombre(String orden) {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT TRAB_DEPT.DEPT_ID AS LLAVE, TRAB_DEPT.NOMBRE AS VALOR FROM TRAB_DEPT";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabDepartamentoDao|mapaDeptNombre|:" + ex);
		}
		return map;
	}
	
	public HashMap<String, TrabDepartamento> mapaDept() {

		HashMap<String, TrabDepartamento> map = new HashMap<String, TrabDepartamento>();
		List<TrabDepartamento> list = new ArrayList<TrabDepartamento>();
		try {
			String comando = "SELECT DEPT_ID, NOMBRE, DETALLES, ESTADO, CODIGO_PERSONAL FROM ENOC.TRAB_DEPT";
			list = enocJdbc.query(comando, new TrabDepartamentoMapper());
			for (TrabDepartamento dept : list) {
				map.put(dept.getDeptId(), dept);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabDepartamentoDao|mapaDeptNombre|:" + ex);
		}
		return map;
	}
}
