package aca.federacion.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FedCandidatoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FedCandidato fedCandidato){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.FED_CANDIDATO " + 
				"(EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN) " +
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), ?, ?, ? )";			
			
			Object[] parametros = new Object[] {
				fedCandidato.getEventoId(),fedCandidato.getCandidatoId(),fedCandidato.getCandidatoNombre(),fedCandidato.getCandidatos(),fedCandidato.getOrden()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedCandidatoDao|insertReg|:"+ex);			
		}

		return ok;
	}	
	
	public boolean updateReg(FedCandidato fedCandidato){
		boolean ok = false;
		
		try{
			String comando = " UPDATE ENOC.FED_CANDIDATO SET " 
									 + " CANDIDATO_NOMBRE 	= ?  " 
									 + " CANDIDATOS 		= ?, " 
									 + " ORDEN			 	= ?, " 
									 + " WHERE CANDIDATO_ID = TO_NUMBER(?,'99')";
		
			Object[] parametros = new Object[] {
				fedCandidato.getCandidatoNombre(),fedCandidato.getCandidatos(),fedCandidato.getOrden(),fedCandidato.getCandidatoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedCandidatoDao|updateReg|:"+ex);			
		}

		return ok;
	}	
	
	public boolean deleteReg(String candidatoId ){
		boolean ok = false;
		
		try{
			String comando = " DELETE FROM ENOC.FED_CANDIDATO "
									 + " WHERE CANDIDATO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					candidatoId
				};
				if (enocJdbc.update(comando, parametros)==1){
					ok = true;
				}
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.spring.FedCandidatoDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public FedCandidato mapeaRegId(String candidatoId){
		FedCandidato objeto = new FedCandidato();
 		 
 		try{
	 		String comando = " SELECT EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN "+	 			
	 								   " FROM ENOC.FED_CANDIDATO WHERE CANDIDATO_ID = ?"; 
	 		
	 		Object[] parametros = new Object[] {
	 			candidatoId
	 		};
	 		objeto = enocJdbc.queryForObject(comando, new FedCandidatoMapper(), parametros);
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedCandidatoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

 		return objeto;
 	} 	
	
	public List<FedCandidato> getListAll(){
		List<FedCandidato> lista	= new ArrayList<FedCandidato>();
		
		try{
			String comando = " SELECT EVENTO_ID, CANDIDATO_ID, CANDIDATO_NOMBRE, CANDIDATOS, ORDEN FROM ENOC.FED_CANDIDATO "; 
			
			lista = enocJdbc.query(comando, new FedCandidatoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedCandidatoDao|getListAll|:"+ex);
		}

		return lista;
	}

}
