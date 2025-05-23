package aca.bec.spring;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecCompetenciaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecCompetencia becCompetencia) {
		
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_COMPETENCIA (COMPETENCIA_ID, COMPETENCIA_NOMBRE)"
					+ " VALUES( ?, ? )";
			Object[] parametros = new Object[] {becCompetencia.getCompetenciaId(), becCompetencia.getCompetenciaNombre()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( BecCompetencia becCompetencia) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_COMPETENCIA SET COMPETENCIA_NOMBRE"
					+ " WHERE COMPETENCIA_ID = ?";
			Object[] parametros = new Object[] {becCompetencia.getCompetenciaNombre(), becCompetencia.getCompetenciaId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String competenciaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_COMPETENCIA WHERE COMPETENCIA_ID = ? ";
			Object[] parametros = new Object[] {competenciaId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public BecCompetencia mapeaRegId( String competenciaId) {
		BecCompetencia becCompetencia = new BecCompetencia();
		
		try{
			String comando = "SELECT  * FROM ENOC.BEC_COMPETENCIA WHERE COMPETENCIA_ID = ? ";	
			Object[] parametros = new Object[] {competenciaId};
			becCompetencia = enocJdbc.queryForObject(comando, new BecCompetenciaMapper(),parametros );
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|mapeaRegId|:"+ex);
		}
		
		return becCompetencia;
	}
	
	public String maximo(Connection conn) {		
		String  maximo 		= "";
		
		try{
			String comando = "SELECT COALESCE(MAX(COMPETENCIA_ID+1), 1) AS MAXIMO FROM ENOC.BEC_COMPETENCIA";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|maximo|:"+ex);
		}
		
		return maximo;
	}
	
	public ArrayList<BecCompetencia> getListAll( String orden) {
			
		List<BecCompetencia> lista 		= new ArrayList<BecCompetencia>();	
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_COMPETENCIA "+orden;			
			lista = enocJdbc.query(comando, new BecCompetenciaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCompetenciaDao|getListAll|:"+ex);
		}
		
		return (ArrayList<BecCompetencia>)lista;
	}
	
}
