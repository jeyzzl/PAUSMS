package aca.leg.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LegCondicionesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LegCondiciones legCondiciones){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.LEG_CONDICIONES(GRUPO, IDDOCUMENTO, VALIDA_FECHA) VALUES(TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), ?)";
				
			Object[] parametros = new Object[] {
				legCondiciones.grupo,legCondiciones.getIdDocumento(),legCondiciones.getValidaFecha()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(LegCondiciones legCondiciones){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.LEG_CONDICIONES"+ 
				" SET VALIDA_FECHA = ?" +
				" WHERE GRUPO = TO_NUMBER(?,'99')" +
				" AND IDDOCUMENTO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
					legCondiciones.getValidaFecha(),legCondiciones.grupo,legCondiciones.getIdDocumento()
			}; 	
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String grupo, String idDocumento ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.LEG_CONDICIONES "+ 
				"WHERE GRUPO = TO_NUMBER(?,'99') AND IDDOCUMENTO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {grupo,idDocumento};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public LegCondiciones mapeaRegId(String grupo, String idDocumento) {
		LegCondiciones legCondiciones = new LegCondiciones();

		try{
			String comando = "SELECT GRUPO, IDDOCUMENTO, VALIDA_FECHA "
					+ " FROM ENOC.LEG_CONDICIONES " 
					+ " WHERE GRUPO = TO_NUMBER(?,'99')"
					+ " AND IDDOCUMENTO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {grupo, idDocumento};
			legCondiciones = enocJdbc.queryForObject(comando, new LegCondicionesMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|mapeaRegId|:"+ex);
		}

		return legCondiciones;
	}
	
	public boolean existeReg(String grupo, String idDocumento) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.LEG_CONDICIONES WHERE GRUPO = TO_NUMBER(?,'99') "
					+ " AND IDDOCUMENTO= TO_NUMBER(?,'99') ";

			Object[] parametros = new Object[] {grupo,idDocumento};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<String> lisGrupoId(){
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT GRUPO FROM LEG_CONDICIONES";

			lista = enocJdbc.queryForList(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|lisGrupoId|:"+ex);
		}
		
		return lista;
	}

	public List<String> lisDocsGrupoId(String grupoId){
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT IDDOCUMENTO FROM LEG_CONDICIONES WHERE GRUPO = ?";
			
			Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.leg.spring.LegCondicionesDao|lisDocsGrupoId|:"+ex);
		}
		
		return lista;
	}

}
