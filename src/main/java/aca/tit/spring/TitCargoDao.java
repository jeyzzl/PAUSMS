package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitCargoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitCargo cargo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_CARGO (CARGO_ID, CARGO_NOMBRE) VALUES( TO_NUMBER(?,'99'), ? ) ";
			
			Object[] parametros = new Object[] {cargo.getCargoId(),cargo.getCargoNombre()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCargoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitCargo cargo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_CARGO SET CARGO_NOMBRE = ? WHERE ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cargo.getCargoNombre(),cargo.getCargoId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCargoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String cargoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_CARGO WHERE CARGO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cargoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCargoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitCargo mapeaRegId(  String cargoId) {
		TitCargo cargo = new TitCargo();
		 
		try{
			String comando = "SELECT CARGO_ID, CARGO_NOMBRE FROM ENOC.TIT_CARGO WHERE CARGO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cargoId};
			cargo = enocJdbc.queryForObject(comando, new TitCargoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCargoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return cargo;
		
	}	
	
	public boolean existeReg(String CargoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_CARGO WHERE CARGO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {CargoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitCargoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitCargo> listAll( String orden) {
		List<TitCargo> lista		= new ArrayList<TitCargo>();
		String comando		= "";
		
		try{
			comando = " SELECT CARGO_ID, CARGO_NOMBRE FROM ENOC.TIT_CARGO"+orden;	 
			
			lista = enocJdbc.query(comando, new TitCargoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.TitCargoDao|listAll|:"+ex);
		}
		return lista;
	}
	
}