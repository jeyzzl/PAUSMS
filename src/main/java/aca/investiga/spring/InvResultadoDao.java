package aca.investiga.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvResultadoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( InvResultado res) {
		boolean ok = false;		
		
		try{
			String comando = "INSERT INTO ENOC.INV_RESULTADO(PROYECTO_ID, INFRAESTRUCTURA, BIBLIOGRAFIA)"
					+ " VALUES (?, ?, ?)";
			Object[] parametros = new Object[] { res.getProyectoId(), res.getInfraestructura(), res .getBibliografia() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvResultado|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( InvResultado res) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.INV_RESULTADO "
					+ " SET INFRAESTRUCTURA = ?,"
					+ " BIBLIOGRAFIA = ?"
					+ " WHERE PROYECTO_ID = ?";
			Object[] parametros = new Object[] { res.getInfraestructura(), res .getBibliografia(), res.getProyectoId() };						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.investiga.spring.InvResultado|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String proyectoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_RESULTADO WHERE PROYECTO_ID = ?";
			Object[] parametros = new Object[] { proyectoId };						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvResultado|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public InvResultado mapeaRegId( String proyectoId) {
		
		InvResultado res = new InvResultado();		
		try{
			String comando = "SELECT PROYECTO_ID, INFRAESTRUCTURA, BIBLIOGRAFIA"
					+ " FROM ENOC.INV_RESULTADO"
					+ " WHERE PROYECTO_ID = ?";
			Object[] parametros = new Object[] { proyectoId };
			res = enocJdbc.queryForObject(comando, new InvResultadoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvResultado|mapeaRegId|:"+ex);
		}
		
		return res;
	}
	
	public boolean existeReg( String proyectoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_RESULTADO WHERE PROYECTO_ID = ?";
			Object[] parametros = new Object[] { proyectoId };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = false;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvResultado|existeReg|:"+ex);
		}
		
		return ok;
	}
	
}
