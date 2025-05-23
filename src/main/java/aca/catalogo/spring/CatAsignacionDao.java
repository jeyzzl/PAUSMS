package aca.catalogo.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatAsignacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CatAsignacion asignacion ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ASIGNACION"+ 
				"(ASIGNACION_ID, ASIGNACION_NOMBRE, DIRECCION, TELEFONO)"+
				" VALUES( ?, ?, ?, ? ) ";
			Object[] parametros = new Object[] {asignacion.getAsignacionId(),asignacion.getAsignacionNombre(),asignacion.getDireccion(),asignacion.getTelefono()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsignacionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatAsignacion asignacion ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ASIGNACION"+ 
				" SET ASIGNACION_NOMBRE = ?," +
				" DIRECCION = ?," +
				" TELEFONO = ?"+
				" WHERE ASIGNACION_ID = ?";
			Object[] parametros = new Object[] {asignacion.getAsignacionNombre(),asignacion.getDireccion(),asignacion.getTelefono(),asignacion.getAsignacionId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsignacionDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String asignacionId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_ASIGNACION WHERE ASIGNACION_ID = ?";
			Object[] parametros = new Object[] {asignacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsignacionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatAsignacion mapeaRegId( String asignacionId ){
		
		CatAsignacion asignacion 	= new CatAsignacion();		
		try{
			String comando = "SELECT ASIGNACION_ID, ASIGNACION_NOMBRE, DIRECCION, TELEFONO FROM ENOC.CAT_ASIGNACION"
					+ " WHERE ASIGNACION_ID = ?";
			Object[] parametros = new Object[] {asignacionId};
			asignacion = enocJdbc.queryForObject(comando, new BeanPropertyRowMapper<CatAsignacion>(CatAsignacion.class), parametros);
			//asignacion = enocJdbc.queryForObject(comando, parametros, new CatAsignacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAsignacionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return asignacion;
	}
	
	public boolean existeReg(String asignacionId ){
		boolean 		ok 	= false;	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ASIGNACION WHERE ASIGNACION_ID = ? ";
			Object[] parametros = new Object[] {asignacionId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){		
			System.out.println("Error - aca.catalogo.spring.CatAsignacionDao|existeReg|:"+ex);
		}
		
		return ok;		
	}
	
}
