package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoBiblioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaGrupoBiblio biblio){
		boolean ok = false;
		
		try{
			String comando = " INSERT INTO ENOC.CARGA_GRUPO_BIBLIO(CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN) "+
				" VALUES( ?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {biblio.getCursoCargaId(),biblio.getBiblioId(),biblio.getBibliografia(),biblio.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|insertReg|:"+ex);			
		
		}
		
		return ok;
	}
	
	public boolean updateReg(CargaGrupoBiblio biblio){
		boolean ok = false;
	
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_BIBLIO "+ 
				" SET BIBLIOGRAFIA = ?," +
				" ORDEN = TO_NUMBER(?,'99') " +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND BIBLIO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {biblio.getBibliografia(),biblio.getOrden(),biblio.getCursoCargaId(),biblio.getBiblioId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|updateReg|:"+ex);		 
	
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String cursoCargaId, String biblioId ){
		boolean ok = false;
	
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_BIBLIO "+ 
				" WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99')" ;
			Object[] parametros = new Object[] {cursoCargaId,biblioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public CargaGrupoBiblio mapeaRegId( String cursoCargaId, String biblioId ){
		
		CargaGrupoBiblio biblio = new CargaGrupoBiblio();
		try{
			String comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN "+
				"FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cursoCargaId,biblioId};
			biblio = enocJdbc.queryForObject(comando, new CargaGrupoBiblioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|mapeaRegId|:"+ex);
	
		}	
		
		return biblio;
	}
	
	public boolean existeReg(String cursoCargaId, String biblioId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? AND BIBLIO_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {cursoCargaId,biblioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|existeReg|:"+ex);
			
		}
		
		return ok;
	}
	
	public boolean existeBibliografia(String cursoCargaId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public boolean existeBibliografia(String cursoCargaId, String biblioId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ?  AND BIBLIO_ID= TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId, biblioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|existeReg|:"+ex);
	
		}
		
		return ok;
	}
	
	public String maximoReg(String cursoCargaId){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(BIBLIO_ID)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID= ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
 		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|maximoReg|:"+ex);
		
		}
		
		return maximo;
	}
	
	
	public ArrayList<CargaGrupoBiblio> getListAll(String orden ){
			
			List<CargaGrupoBiblio> lista = new ArrayList<CargaGrupoBiblio>();
			
			try{
				String comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN FROM ENOC.CARGA_GRUPO_BIBLIO "+ orden; 		
				lista = enocJdbc.query(comando, new CargaGrupoBiblioMapper());	
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.spring.CargaGrupoBiblioDao|getListAll|:"+ex);
			
			}
			
			return (ArrayList<CargaGrupoBiblio>) lista;
		}
	
	public ArrayList<CargaGrupoBiblio> getListBiblio(String cursoCargaId, String orden){
		
		List<CargaGrupoBiblio> lista = new ArrayList<CargaGrupoBiblio>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, BIBLIO_ID, BIBLIOGRAFIA, ORDEN FROM ENOC.CARGA_GRUPO_BIBLIO WHERE CURSO_CARGA_ID = ? "+ orden; 
			lista = enocJdbc.query(comando, new CargaGrupoBiblioMapper(), cursoCargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.aca.carga.spring.CargaGrupoBiblioDao|getListAll|:"+ex);		
		}		
		return (ArrayList<CargaGrupoBiblio>) lista;
	}
}