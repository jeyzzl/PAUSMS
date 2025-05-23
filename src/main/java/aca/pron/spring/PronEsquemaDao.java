package aca.pron.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PronEsquemaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(PronEsquema pronEsquema) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.PRON_ESQUEMA(CURSO_CARGA_ID, ESTRATEGIA_ID, ESTRATEGIA_NOMBRE, VALOR, TIPO, ORDEN)"
					+ " VALUES(?,TO_NUMBER(?,'999'),?,TO_NUMBER(?,'999.99'),?,TO_NUMBER(?,'9999.99'))";

			Object[] parametros = new Object[] {
				pronEsquema.getCursoCargaId(),pronEsquema.getEstrategiaId(),pronEsquema.getEstrategiaNombre(),pronEsquema.getValor(),pronEsquema.getTipo(),
				pronEsquema.getOrden()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(PronEsquema pronEsquema) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.PRON_ESQUEMA SET ESTRATEGIA_ID = TO_NUMBER(?,'999'), ESTRATEGIA_NOMBRE = ?, VALOR = TO_NUMBER(?,'999.99'), TIPO = ?,"
					+ " ORDEN = TO_NUMBER(?,'9999.99') WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
				pronEsquema.getEstrategiaId(),pronEsquema.getEstrategiaNombre(),pronEsquema.getValor(),pronEsquema.getTipo(),
				pronEsquema.getOrden(),pronEsquema.getCursoCargaId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoCargaId, String estrategiaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.PRON_ESQUEMA WHERE CURSO_CARGA_ID = ? AND ESTRATEGIA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId, estrategiaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PronEsquema mapeaRegId(String cursoCargaId, String estrategiaId) {
		PronEsquema pronEsquema = new PronEsquema();
		try{
			String comando = "SELECT CURSO_CARGA_ID, ESTRATEGIA_ID, ESTRATEGIA_NOMBRE, VALOR, TIPO, ORDEN"
					+ " FROM ENOC.PRON_ESQUEMA WHERE CURSO_CARGA_ID = ? AND ESTRATEGIA_ID = ?";
			
				Object[] parametros = new Object[] {cursoCargaId, estrategiaId};
				pronEsquema = enocJdbc.queryForObject(comando, new PronEsquemaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|mapeaRegId|:"+ex);
		}
		
		return pronEsquema;		
	}	
	
	public boolean existeReg(String cursoCargaId, String estrategiaId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.PRON_ESQUEMA WHERE CURSO_CARGA_ID = ? AND ESTRATEGIA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId, estrategiaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<PronEsquema> listaEsquema(String cursoCargaId) {
		List<PronEsquema> lista = new ArrayList<PronEsquema>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, ESTRATEGIA_ID, ESTRATEGIA_NOMBRE, VALOR, TIPO, ORDEN"
					+ " FROM ENOC.PRON_ESQUEMA WHERE CURSO_CARGA_ID = ? ORDER BY ORDEN";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new PronEsquemaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|listaEsquema|:"+ex);
		}
		return lista;
	}
	
	public String getEsquemaId(String cursoCargaId) {
		String id = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ESTRATEGIA_ID)+1,1) ID FROM ENOC.PRON_ESQUEMA WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				id = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.pron.spring.PronEsquemaDao|getEsquemaId|:"+ex);
		}
		return id;
	}

}
