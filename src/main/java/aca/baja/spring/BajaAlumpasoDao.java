package aca.baja.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BajaAlumpasoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BajaAlumpaso bajaAlumP ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BAJA_ALUMPASO"+ 
				"(BAJA_ID, PASO_ID, FECHA, ESTADO)"+
				" VALUES(TO_NUMBER(?, '9999999')," +
				" TO_NUMBER(?, '99')," +
				" TO_DATE(?, 'DD/MM/YYYY')," +
				" ?)";
							
			Object[] parametros = new Object[] {bajaAlumP.getBajaId(),bajaAlumP.getPasoId(),bajaAlumP.getFecha(),bajaAlumP.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( BajaAlumpaso bajaAlumP ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BAJA_ALUMPASO"+ 
				" SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"+
				" ESTADO = ?"+				
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {bajaAlumP.getFecha(),bajaAlumP.getEstado(),bajaAlumP.getBajaId(),bajaAlumP.getPasoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg( String bajaId, String pasoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {bajaId,pasoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public BajaAlumpaso mapeaRegId( String bajaId, String pasoId) {
		BajaAlumpaso bajaAlumP = new BajaAlumpaso();
		
		try{
			String comando = "SELECT BAJA_ID, PASO_ID," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO" +
					" FROM ENOC.BAJA_ALUMPASO" + 
					" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
					" AND PASO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {bajaId};
			bajaAlumP = enocJdbc.queryForObject(comando, new BajaAlumpasoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|mapeaRegId|:"+ex);
		}
		return bajaAlumP;
	}
	
	public boolean existeReg( String bajaId, String pasoId) {
		boolean 			ok 	= false;
				
		try{
			String comando = "SELECT COUNT(*) BAJA_ID FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {bajaId,pasoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean realizoPaso( String bajaId, String pasoId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT ESTADO FROM ENOC.BAJA_ALUMPASO"+ 
				" WHERE BAJA_ID = TO_NUMBER(?, '9999999')" +
				" AND PASO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {bajaId,pasoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.spring.BajaAlumpasoDao|pasoPorMentoria|:"+ex);
		}
		return ok;
	}
	
	public List<BajaAlumpaso> getListPorBaja( String bajaId, String orden) {
		List<BajaAlumpaso> lista 	= new ArrayList<BajaAlumpaso>();
		
		try{
			String comando = "SELECT BAJA_ID, PASO_ID, FECHA, ESTADO" +
					" FROM ENOC.BAJA_ALUMPASO A" + 
					" WHERE BAJA_ID = ? "+orden;
			
			lista = enocJdbc.query(comando, new BajaAlumpasoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.baja.BajaAlumnoUtil|getListPorBaja|:"+ex);
		}
		return lista;
	}
}