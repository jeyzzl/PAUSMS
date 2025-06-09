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
public class TrabAsesorDao {
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg( TrabAsesor asesor) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TRAB_ASESOR(CODIGO_PERSONAL, DEPT_ID, FECHA, STATUS)"
					+ " VALUES( ?, TO_NUMBER(?,'999'), TO_DATE(?, 'DD-MM-YYYY hh24:mi:ss'), ?)";
			Object[] parametros = new Object[] {asesor.getCodigoPersonal(), asesor.getDeptId(), asesor.getFecha(), asesor.getStatus()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( TrabAsesor asesor ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_ASESOR SET DEPT_ID = TO_NUMBER(?,'999'), FECHA = TO_DATE(?, 'DD-MM-YYYY hh24:mi:ss'), STATUS = ? WHERE CODIGO_PERSONAL = ? AND DEPT_ID = ?";
			Object[] parametros = new Object[] {asesor.getDeptId(), asesor.getFecha(), asesor.getStatus(), asesor.getCodigoPersonal(), asesor.getDeptId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal, String deptId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ? AND DEPT_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, deptId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public TrabAsesor mapeaRegId(  String codigoPersonal, String deptId ) {
		
		TrabAsesor asesor 	= new TrabAsesor();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ? AND DEPT_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL, DEPT_ID, FECHA, STATUS FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ? AND DEPT_ID = ?";
				asesor = enocJdbc.queryForObject(comando, new TrabAsesorMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return asesor;
	}
	
	public boolean existeReg( String codigoPersonal, String deptId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ? AND DEPT_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean esAsesor( String codigoPersonal, String deptId) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ? AND DEPT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {codigoPersonal, deptId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|esAsesor|:"+ex);
		}
		
		return ok;
	}

	public boolean esAsesor( String codigoPersonal) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.TRAB_ASESOR WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|esAsesor|:"+ex);
		}
		
		return ok;
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<TrabAsesor> lisTodos( String orden ) {
		
		List<TrabAsesor> lista = new ArrayList<TrabAsesor>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, DEPT_ID, FECHA, STATUS FROM ENOC.TRAB_ASESOR"+ orden;				
			lista = enocJdbc.query(comando, new TrabAsesorMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|lisTodos|:"+ex);
		}		
		return lista;
	}

	// Llena el listor con todos los elementos de la tabla	
	public List<TrabAsesor> lisPorDepartamento( String deptId, String orden ) {
		
		List<TrabAsesor> lista = new ArrayList<TrabAsesor>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, DEPT_ID, FECHA, STATUS FROM ENOC.TRAB_ASESOR WHERE DEPT_ID = TO_NUMBER(?,'999')"+ orden;				
			lista = enocJdbc.query(comando, new TrabAsesorMapper(), deptId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|lisPorDepartamento|:"+ex);
		}		
		return lista;
	}
	
	public List<TrabAsesor> lisActivos( String orden ) {
		
		List<TrabAsesor> lista = new ArrayList<TrabAsesor>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, MAX(DEPT_ID) AS DEPT_ID, MAX(FECHA) AS FECHA, STATUS FROM ENOC.TRAB_ASESOR WHERE STATUS = 'A' GROUP BY CODIGO_PERSONAL, STATUS"+ orden;				
			lista = enocJdbc.query(comando, new TrabAsesorMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabAsesorDao|lisActivos|:"+ex);
		}		
		return lista;
	}
	
}
