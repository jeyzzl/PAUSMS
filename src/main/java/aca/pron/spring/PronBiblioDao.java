package aca.pron.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronBiblioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronBiblio pronBiblio) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_BIBLIO(CURSO_CARGA_ID, BIBLIO_ID, BIBLIO_NOMBRE, ORDEN)"
					+ " VALUES(?,TO_NUMBER(?,99),?,TO_NUMBER(?,9999.99))";

			Object[] parametros = new Object[] {
				pronBiblio.getCursoCargaId(),pronBiblio.getBiblioId(),pronBiblio.getBiblioNombre(),pronBiblio.getOrden()	
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronBiblio pronBiblio) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_BIBLIO SET BIBLIO_NOMBRE = ?, ORDEN = ? WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = ?";
			
			Object[] parametros = new Object[] {
				pronBiblio.getBiblioNombre(),pronBiblio.getOrden(),pronBiblio.getCursoCargaId(),pronBiblio.getBiblioId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId, String biblioId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId,biblioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronBiblio mapeaRegId(String cursoCargaId, String biblioId) {
		PronBiblio pronBiblio = new PronBiblio();
		try{
			String comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIO_NOMBRE, ORDEN FROM ENOC.PRON_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = ?";
			
				Object[] parametros = new Object[] {cursoCargaId,biblioId};
				pronBiblio = enocJdbc.queryForObject(comando, new PronBiblioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|mapeaRegId|:"+ex);
		}
		
		return pronBiblio;		
	}	
	
	public boolean existeReg(String cursoCargaId, String biblioId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId,biblioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getBiblioId(String cursoCargaId) {
		String id = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(BIBLIO_ID)+1,1) ID FROM ENOC.PRON_BIBLIO WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				id = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|getBiblioId|:"+ex);
		}
		return id;
	}
	
	public List<aca.pron.spring.PronBiblio> listaBiblio(String cursoCargaId) {
		List<aca.pron.spring.PronBiblio> lista = new ArrayList<aca.pron.spring.PronBiblio>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIO_NOMBRE, ORDEN "
					+ " FROM ENOC.PRON_BIBLIO WHERE CURSO_CARGA_ID = ? ORDER BY ORDEN";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new PronBiblioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronBiblioDao|listaBiblio|:"+ex);
		}
		return lista;
	}

}
