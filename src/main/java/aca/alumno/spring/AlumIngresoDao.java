package aca.alumno.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumIngresoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumIngreso alumIngreso ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALUM_INGRESO"+ 
				"(CODIGO_PERSONAL, PLAN_ID, CARGA_ID, CARRERA_ID, NEWUM, NEWPLAN) "+
				"VALUES( ?, ?, ?, ?, ?, ? )";
			
			Object[] parametros = new Object[] {
					alumIngreso.getCodigoPersonal(),alumIngreso.getPlanId(),alumIngreso.getCargaId(),alumIngreso.getCarreraId(), alumIngreso.getNewUm(), alumIngreso.getNewPlan()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|insertReg|:"+ex);			

		}
		return ok;
	}	
	
	public boolean updateReg( AlumIngreso alumIngreso ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.ALUM_INGRESO"+ 
				" SET "+
				" CARGA_ID = ?,"+
				" CARRERA_ID = ?,"+
				" NEWUM = ?,"+
				" NEWPLAN = ?"+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?";	
			Object[] parametros = new Object[] {
					alumIngreso.getCargaId(),alumIngreso.getCarreraId(), alumIngreso.getNewUm(), alumIngreso.getNewPlan(),alumIngreso.getCodigoPersonal(), alumIngreso.getPlanId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|updateReg|:"+ex);		

		}
		return ok;
	}
		
	public boolean deleteReg( String codigoPersonal, String planId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_INGRESO "+ 
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ? ";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public AlumIngreso mapeaRegId(  String codigoPersonal, String planId ){
		AlumIngreso alumIngreso = new AlumIngreso();
		
		try{
			String comando = "SELECT"+
				" CODIGO_PERSONAL, "+
				" PLAN_ID, CARGA_ID, " +
				" CARRERA_ID, NEWUM, NEWPLAN, " +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ? ";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			alumIngreso = enocJdbc.queryForObject(comando, new AlumIngresoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|mapeaRegId|:"+ex);

		}
		return alumIngreso;
	}	
	
	public boolean existeReg( String codigoPersonal){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_INGRESO "+ 
				" WHERE CODIGO_PERSONAL = ? " +
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|existeReg|:"+ex);
		
		}
		return ok;
	}

	public ArrayList<AlumIngreso> getListAll( String orden ){
		
		List<AlumIngreso> lista		= new ArrayList<AlumIngreso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, CARGA_ID, " +
					" CARRERA_ID, NEWUM, NEWPLAN FROM ENOC.ALUM_INGRESO "+ orden; 
			lista = enocJdbc.query(comando, new AlumIngresoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|getListAll|:"+ex);
	
		}
		
		return (ArrayList<AlumIngreso>)lista;
	}
	
	public ArrayList<AlumIngreso> getLista( String codigoPersonal,String planId, String orden ){
		
		List<AlumIngreso> lista	= new ArrayList<AlumIngreso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, CARGA_ID," +
					" CARRERA_ID, NEWUM, NEWPLAN FROM ENOC.ALUM_INGRESO "+ 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumIngresoMapper(), codigoPersonal, planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumIngresoDao|getLista|:"+ex);

		}
		
		return (ArrayList<AlumIngreso>) lista;
	}

}