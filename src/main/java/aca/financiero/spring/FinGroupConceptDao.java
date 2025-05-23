package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.financiero.spring.FinGroupConcept;
import aca.financiero.spring.FinGroupConceptMapper;

@Component
public class FinGroupConceptDao {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( FinGroupConcept financiero ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.FIN_GROUP_CONCEPT "
					+ " (GROUP_ID, CONC_ID, NO_UNITS) "
					+ " VALUES( TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?))";
			Object[] parametros = new Object[] {
						financiero.getGroupId(), financiero.getConcId(), financiero.getNoUnits()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg( FinGroupConcept financiero ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.FIN_GROUP_CONCEPT"
					+ " SET NO_UNITS = TO_NUMBER(?),"					
					+ " WHERE GROUP_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {
				financiero.getNoUnits(), financiero.getGroupId(), financiero.getConcId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String groupId, String concId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.FIN_GROUP_CONCEPT "
					+ " WHERE GROUP_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId, concId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public FinGroup mapeaRegId( String groupId, String concId ) {
		
		FinGroup objeto = new FinGroup();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_GROUP_CONCEPT WHERE GROUP_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId, concId};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT *"
						+ " FROM ENOC.FIN_GROUP_CONCEPT"
						+ " WHERE GROUP_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
				objeto = enocJdbc.queryForObject(comando, new FinGroupMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String groupId, String concId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.FIN_GROUP_CONCEPT"
					+ " WHERE GROUP_ID = TO_NUMBER(?) AND CONC_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {groupId,concId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public List<FinGroupConcept> getListAll( String groupId, String orden ) {
		
		List<FinGroupConcept> lista = new ArrayList<FinGroupConcept>();		
		try{
			String comando = "SELECT *"
					+ " FROM ENOC.FIN_GROUP_CONCEPT WHERE GROUP_ID = TO_NUMBER(?)"+orden;
			lista = enocJdbc.query(comando, new FinGroupConceptMapper(), groupId);			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|getListAll|:"+ex);
		}
		return lista;
	}

	public HashMap<String, String> mapNumConcepts() {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT GROUP_ID AS LLAVE, COUNT(CONC_ID) AS VALOR FROM FIN_GROUP_CONCEPT GROUP BY GROUP_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.financiero.spring.FinGroupConceptDao|mapaAlumNombre|:" + ex);
		}
		return map;
	}

}