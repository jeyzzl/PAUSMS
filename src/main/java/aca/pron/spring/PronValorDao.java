package aca.pron.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronValorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronValor pronValor) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_VALOR(CURSO_CARGA_ID, AMOR, LEALTAD, CONFIANZA, REVERENCIA, OBEDIENCIA, ARMONIA, RESPETO, PUREZA, "
					+ " HONESTIDAD, VERACIDAD, CONTENTAMIENTO, SERVICIO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			Object[] parametros = new Object[] {
				pronValor.getCursoCargaId(),pronValor.getAmor(),pronValor.getLealtad(),pronValor.getConfianza(),pronValor.getReverencia(),pronValor.getObediencia(),
				pronValor.getArmonia(),pronValor.getRespeto(),pronValor.getPureza(),pronValor.getHonestidad(),pronValor.getVeracidad(),pronValor.getContentamiento(),
				pronValor.getServicio()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.pronValorDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronValor pronValor) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_VALOR SET AMOR = ?, LEALTAD = ?, CONFIANZA = ?, REVERENCIA = ?, OBEDIENCIA = ?, ARMONIA = ?, RESPETO = ?," +			
				" PUREZA = ?, HONESTIDAD = ?, VERACIDAD = ?, CONTENTAMIENTO = ?, SERVICIO = ? WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
				pronValor.getAmor(),pronValor.getLealtad(),pronValor.getConfianza(),pronValor.getReverencia(),pronValor.getObediencia(),pronValor.getArmonia(),
				pronValor.getRespeto(),pronValor.getPureza(),pronValor.getHonestidad(),pronValor.getVeracidad(),pronValor.getContentamiento(),
				pronValor.getServicio(),pronValor.getCursoCargaId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.pronValorDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_VALOR WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.pronValorDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronValor mapeaRegId(String cursoCargaId) {
		PronValor pronValor = new PronValor();
		try{
			String comando = "SELECT CURSO_CARGA_ID, AMOR, LEALTAD, CONFIANZA, REVERENCIA, OBEDIENCIA, ARMONIA, RESPETO, PUREZA, HONESTIDAD, VERACIDAD,"
					+ " CONTENTAMIENTO, SERVICIO FROM ENOC.PRON_VALOR WHERE CURSO_CARGA_ID = ?";
			
				Object[] parametros = new Object[] {cursoCargaId};
				pronValor = enocJdbc.queryForObject(comando, new PronValorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.pronValorDao|mapeaRegId|:"+ex);
		}
		
		return pronValor;		
	}	
	
	public boolean existeReg(String cursoCargaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_VALOR WHERE CURSO_CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.pronValorDao|existeReg|:"+ex);
		}
		return ok;
	}

}
