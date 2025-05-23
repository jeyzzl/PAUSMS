package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadTemaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaUnidadTema cargaUnidadTema){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD_TEMA(CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, FECHA, ORDEN )"
					+ " VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99') )";
			Object[] parametros = new Object[] {cargaUnidadTema.getCursoCargaId(),cargaUnidadTema.getTemaId(),cargaUnidadTema.getTemaNombre(),
					cargaUnidadTema.getFecha(), cargaUnidadTema.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|insertReg|:"+ex);			
		
		}
		
		return ok;
	}	
	
	public boolean updateReg(CargaUnidadTema cargaUnidadTema ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD_TEMA "+ 
				" SET TEMA_NOMBRE = ?, " +
				" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" ORDEN = TO_NUMBER(?,'99')" +
				" WHERE TEMA_ID = ? " +
				" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaUnidadTema.getTemaNombre(),cargaUnidadTema.getFecha(),cargaUnidadTema.getOrden(),
					cargaUnidadTema.getTemaId(), cargaUnidadTema.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|updateReg|:"+ex);		 
		
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String temaId, String cursoCargaId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD_TEMA "+ 
				" WHERE TEMA_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {temaId,cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public CargaUnidadTema mapeaRegId( String cursoCargaId, String temaId){
		CargaUnidadTema cargaUnidadTema = new CargaUnidadTema();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, " +
					" ORDEN FROM ENOC.CARGA_UNIDAD_TEMA WHERE CURSO_CARGA_ID = ? AND TEMA_ID = ?";	
			Object[] parametros = new Object[] {cursoCargaId,temaId};
			cargaUnidadTema = enocJdbc.queryForObject(comando, new CargaUnidadTemaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|mapeaRegId|:"+ex);
		
		}
		
		return cargaUnidadTema;
	}
	
	public boolean existeReg(String temaId, String cursoCargaId){
		boolean 		  ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_UNIDAD_TEMA WHERE TEMA_ID = ?  AND CURSO_CARGA_ID = ? "; 
			Object[] parametros = new Object[] {temaId,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public String getNombreTema(String cursoCargaId, String temaId){
		String nombre			= "";				
		
		try{
			String comando = " SELECT TEMA_NOMBRE FROM ENOC.CARGA_UNIDAD_TEMA " + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND TEMA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, temaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|getNombreTema|:"+ex);
		
		}
		
		return nombre;
	}
	
   public String getMaximo(String cursoCargaId, String unidadId ){
	    String maximo 			= "01";
		
		try{
			String comando = " SELECT COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(TEMA_ID,3,2),'99'))+1,'00')),'01') AS MAXIMO" +
			" FROM ENOC.CARGA_UNIDAD_TEMA " + 
			" WHERE CURSO_CARGA_ID = ?" +
		    " AND SUBSTR(TEMA_ID,1,2) = ? ";
			Object[] parametros = new Object[] {cursoCargaId, unidadId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|getNombreReligion|:"+ex);
		
		}
		
		return maximo;
   }
   
   public boolean tieneActividades(String cursoCargaId, String temaId ){
		
		boolean ok           = false;
		try{
			String comando = " SELECT * FROM ENOC.CARGA_UNIDAD_ACTIVIDAD WHERE CURSO_CARGA_ID = ? " + 
					  " AND SUBSTR(ACTIVIDAD_ID,1,4) = ? ";
			if (enocJdbc.queryForObject(comando,Integer.class, cursoCargaId, temaId)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|tieneActividades|:"+ex);		
		}
		
		return ok;
	}
   
   	public List<CargaUnidadTema> getListAll(String orden ){
   
   		List<CargaUnidadTema> lista = new ArrayList<CargaUnidadTema>();
   		
		try{
			String comando = "SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, FECHA, ORDEN FROM ENOC.CARGA_UNIDAD_TEMA "+ orden; 
			lista = enocJdbc.query(comando, new CargaUnidadTemaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|getListAll|:"+ex);
		
		}
		
		return lista;
	}

	public List<CargaUnidadTema> getListTema(String cursoCargaId, String orden ){
	
		List<CargaUnidadTema> lista = new ArrayList<CargaUnidadTema>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ORDEN FROM ENOC.CARGA_UNIDAD_TEMA " + 
					" WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new CargaUnidadTemaMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadTemaDao|getListCompetencias|:"+ex);		
		}	
		return lista;
	}
	
}