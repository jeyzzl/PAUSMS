package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadCriterioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaUnidadCriterio criterio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD_CRITERIO(CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE ) "+
				"VALUES( ?, ?, ? ) ";
			

			Object[] parametros = new Object[] {criterio.getCursoCargaId(),criterio.getCriterioId(),criterio.getCriterioNombre()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaUnidadCriterio criterio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD_CRITERIO "+ 
				" SET CRITERIO_NOMBRE = ? " +
				" WHERE CRITERIO_ID = ? " +
				" AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {criterio.getCriterioNombre(),criterio.getCriterioId(),criterio.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaId, String criterioId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD_CRITERIO "+ 
				" WHERE CRITERIO_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {cursoCargaId,criterioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaUnidadCriterio mapeaRegId(  String cursoCargaId, String criterioId) {
		CargaUnidadCriterio criterio = new CargaUnidadCriterio();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE "+
				"FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = ? AND CRITERIO_ID = ?";	
			
			Object[] parametros = new Object[] {cursoCargaId,criterioId};
			criterio = enocJdbc.queryForObject(comando, new CargaUnidadCriterioMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|mapeaRegId|:"+ex);
		}		
		return criterio;
	}
	
	public boolean existeReg( String criterioId, String cursoCargaId) {
		boolean 		  ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CRITERIO_ID = ?  AND CURSO_CARGA_ID = ? "; 

			Object[] parametros = new Object[] {criterioId,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean tieneInstrumentos( String cursoCargaId, String criterioId ) {
		boolean ok           = false;
		try{
			String comando = " SELECT * FROM ENOC.CARGA_UNIDAD_INSTRUMENTO WHERE CURSO_CARGA_ID = ? AND SUBSTR(INSTRUMENTO_ID,1,8) = ? ";			
			Object[] parametros = new Object[] {cursoCargaId,criterioId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|tieneInstrumentos|:"+ex);
		}
		return ok;
   }
	
	public String getNombreCriterio( String cursoCargaId, String criterioId ) {		
		String comando	    = "";
		String nombre		= "";		
		try{
			comando = " SELECT CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = ? AND CRITERIO_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId,criterioId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|getNombreCriterio|:"+ex);
		}
		return nombre;
   }
	
	public List<CargaUnidadCriterio> getListAll( String orden ) {
		
		List<CargaUnidadCriterio> lista  = new ArrayList<CargaUnidadCriterio>();
		
	
		String comando								= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO "+ orden; 
			
			lista = enocJdbc.query(comando, new CargaUnidadCriterioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaUnidadCriterio> getListCriterio(String cursoCargaId, String orden ) {
			List<CargaUnidadCriterio> lista  = new ArrayList<CargaUnidadCriterio>();		
			String comando								= "";
			
			try{
				comando = "SELECT CURSO_CARGA_ID, CRITERIO_ID, CRITERIO_NOMBRE FROM ENOC.CARGA_UNIDAD_CRITERIO " + 
						" WHERE CURSO_CARGA_ID = ? " + orden;				
				lista = enocJdbc.query(comando, new CargaUnidadCriterioMapper(), cursoCargaId);				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.spring.CargaUnidadCriterioDao|getListAll|:"+ex);
			}
			return lista;
		}
	

}