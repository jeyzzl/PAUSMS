package aca.federacion.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FedVotosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(FedVotos fedVotos){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.FED_VOTOS " + 
				"(EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, FECHA, VOTO) " +
				"VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), ? )";			

			Object[] parametros = new Object[] {
				fedVotos.getEventoId(),fedVotos.getCandidatoId(),fedVotos.getCodigoPersonal(),fedVotos.getFecha(),fedVotos.getVoto()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedVotosDao|insertReg|:"+ex);			
		}

		return ok;
	}	
	
	public boolean updateReg(FedVotos fedVotos){
		boolean ok = false;
		
		try{
			String comando = " UPDATE ENOC.FED_VOTOS SET " 
									 + " FECHA 	= ?  " 
									 + " VOTO 	= ?, " 
									 + " WHERE EVENTO_ID = TO_NUMBER(?,'999') "
									 + " AND CANDIDATO_ID = TO_NUMBER(?,'999') " 
									 + " AND CODIGO_PERSONAL = ?";

			Object[] parametros = new Object[] {
				 fedVotos.getFecha(),fedVotos.getVoto(),fedVotos.getEventoId(),fedVotos.getCandidatoId(),fedVotos.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedVotosDao|updateReg|:"+ex);			
		}

		return ok;
	}	
	
	public boolean deleteReg(String eventoId, String candidatoId, String codigoPersonal ){
		boolean ok = false;
		
		try{
			String comando = " DELETE FROM ENOC.FED_VOTOS "
									 + " WHERE EVENTO_ID = TO_NUMBER(?,'999') " 
									 + " AND CANDIDATO_ID = TO_NUMBER(?,'999') " 
									 + " AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {
				eventoId,candidatoId,codigoPersonal
			};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error  - aca.federacion.spring.FedVotosDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public FedVotos mapeaRegId(String eventoId, String candidatoId, String codigoPersonal){
		FedVotos objeto = new FedVotos();
 		 
 		try{
	 		String comando = " SELECT EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD,MM,YYYY') AS FECHA, VOTO "+	 			
	 						 " FROM ENOC.FED_VOTOS WHERE EVENTO_ID = ?, CANDIDATO_ID = ?, CODIGO_PERSONAL = ? "; 
	 			 		
	 		Object[] parametros = new Object[] {
	 			eventoId,candidatoId,codigoPersonal
	 		};
	 		objeto = enocJdbc.queryForObject(comando, new FedVotosMapper(), parametros);
 		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedVotosDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}

 		return objeto;
 	} 	
	
	public List<FedVotos> getListAll(String orden ){
		List<FedVotos> lista = new ArrayList<FedVotos>();
		
		try{
			String comando = " SELECT EVENTO_ID, CANDIDATO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VOTO "
					+ " FROM ENOC.FED_VOTOS "+orden; 
			
			lista = enocJdbc.query(comando, new FedVotosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.federacion.spring.FedVotosDao|getListAll|:"+ex);
		}

		return lista;
	}

}
