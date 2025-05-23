package aca.pron.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronEjesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronEjes pronEjes) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_EJES(CURSO_CARGA_ID, FE, PENSAMIENTO, AMBIENTE, LIDERAZGO, EMPRENDIMIENTO, SUSTENTABILIDAD, SERVICIO, INVESTIGACION)"
					+ " VALUES(?,?,?,?,?,?,?,?,?)";

			Object[] parametros = new Object[] {
				pronEjes.getCursoCargaId(),pronEjes.getFe(),pronEjes.getPensamiento(),pronEjes.getAmbiente(),pronEjes.getLiderazgo(),
				pronEjes.getEmprendimiento(),pronEjes.getSustentabilidad(),pronEjes.getServicio(),pronEjes.getInvestigacion()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEjesDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronEjes pronEjes) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_EJES SET FE = ?, PENSAMIENTO = ?, AMBIENTE = ?, LIDERAZGO = ?, EMPRENDIMIENTO = ?, SUSTENTABILIDAD = ?,"
					+ " SERVICIO = ?, INVESTIGACION = ? WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
				pronEjes.getFe(),pronEjes.getPensamiento(),pronEjes.getAmbiente(),pronEjes.getLiderazgo(),pronEjes.getEmprendimiento(),
				pronEjes.getSustentabilidad(),pronEjes.getServicio(),pronEjes.getInvestigacion(),pronEjes.getCursoCargaId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEjesDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_EJES WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEjesDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronEjes mapeaRegId(String cursoCargaId) {
		PronEjes pronEjes = new PronEjes();
		try{
			String comando = "SELECT CURSO_CARGA_ID, FE, PENSAMIENTO, AMBIENTE, LIDERAZGO, EMPRENDIMIENTO, SUSTENTABILIDAD, SERVICIO, INVESTIGACION"
					+ " FROM ENOC.PRON_EJES WHERE CURSO_CARGA_ID = ?";
			
				Object[] parametros = new Object[] {cursoCargaId};
				pronEjes = enocJdbc.queryForObject(comando, new PronEjesMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEjesDao|mapeaRegId|:"+ex);
		}
		
		return pronEjes;		
	}	
	
	public boolean existeReg(String cursoCargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_EJES WHERE CURSO_CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEjesDao|existeReg|:"+ex);
		}
		return ok;
	}

}
