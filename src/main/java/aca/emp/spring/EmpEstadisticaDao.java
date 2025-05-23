package aca.emp.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpEstadisticaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EmpEstadistica empEstadistica ) {
		boolean ok = false;
		
		try{
			
			String comando = "INSERT INTO ENOC.EMP_ESTADISTICA"
					+ "(CODIGO_PERSONAL, CARGAS, MODALIDADES)"
					+ "VALUES( ?, ?, ? )";
						
			Object[] parametros = new Object[] {empEstadistica.getCodigoPersonal(),empEstadistica.getCargas(),empEstadistica.getModalidades()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|insertReg|:"+ex);			
		}
		return ok;
	}	

	public boolean updateReg( EmpEstadistica empEstadistica ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EMP_ESTADISTICA"
					+ "SET "
					+ "CARGAS = ?,"
					+ "MODALIDADES = ? "
					+ "WHERE CODIGO_PERSONAL = ?";
	
			Object[] parametros = new Object[] {empEstadistica.getCargas(),empEstadistica.getModalidades(),empEstadistica.getCodigoPersonal()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EMP_ESTADISTICA"
					+ "WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EmpEstadistica mapeaRegId( String codigoPersonal ) {
		EmpEstadistica empEstadistica = new EmpEstadistica();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES"
					+ "FROM ENOC.EMP_ESTADISTICA WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			empEstadistica = enocJdbc.queryForObject(comando, new EmpEstadisticaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|mapeaRegId|:"+ex);
		}
		return empEstadistica;
	}
	
	public boolean existeReg( String codigoPersonal ) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EMP_ESTADISTICA "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<EmpEstadistica> getListAll( String orden ) {
		List<EmpEstadistica> lista	= new ArrayList<EmpEstadistica>();
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGAS, MODALIDADES FROM ENOC.EMP_ESTADISTICA "+ orden; 
			
			lista = enocJdbc.query(comando, new EmpEstadisticaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.emp.spring.EmpEstadisticaDao|getListAll|:"+ex);
		}		
		return lista;
	}
}