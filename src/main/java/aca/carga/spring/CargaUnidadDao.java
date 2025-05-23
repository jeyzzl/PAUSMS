package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaUnidad unidad ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD(CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE,ORDEN ) "+
				" VALUES( ?, ?, ?, TO_NUMBER(?,'99') ) ";
			Object[] parametros = new Object[] {unidad.getCursoCargaId(),unidad.getUnidadId(),unidad.getUnidadNombre(), unidad.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|insertReg|:"+ex);			
		
		}
		
		return ok;
	}	
	
	public boolean updateReg(CargaUnidad unidad ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD "+ 
				" SET UNIDAD_NOMBRE = ?, " +
				" ORDEN = TO_NUMBER(?,'99')" +
				" WHERE UNIDAD_ID = ? " +
				" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {unidad.getUnidadNombre(),unidad.getOrden(),unidad.getUnidadId(), unidad.getCursoCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|updateReg|:"+ex);		 
		
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String unidadId, String cursoCargaId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD"+ 
				" WHERE UNIDAD_ID = ?" +
				" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {unidadId,cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public CargaUnidad mapeaRegId( String cursoCargaId, String unidadId){
		
		CargaUnidad unidad = new CargaUnidad();
		String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN "+
			"FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?";
		
		Object[] parametros = new Object[] {cursoCargaId,unidadId};
		unidad = enocJdbc.queryForObject(comando, new CargaUnidadMapper(), parametros);
		
		return unidad;
	}
	
	public boolean existeReg(String unidadId, String cursoCargaId){
		
		boolean 		ok 	= false;
		
		try{			
			String comando = "SELECT COUNT (*) FROM ENOC.CARGA_UNIDAD WHERE UNIDAD_ID = ?  AND CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {unidadId,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public String maximoReg(String cursoCargaId){
		String maximo 			= "01";
		
		try{
			String comando = "SELECT" +
					" COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(UNIDAD_ID,'99'))+1,'00')), '01') AS MAXIMO " +
					" FROM ENOC.CARGA_UNIDAD " + 
					" WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|maximoReg|:"+ex);
		
		}
		
		return maximo;
	}
	
	public int numUnidades(String cursoCargaId ){
		
		int numUnidades		= 0;		
		try{
			String comando = " SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMUNIDADES FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = ?";	
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				numUnidades = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|numUnidades|:"+ex);		
		}		
		return numUnidades;
	}
	
	public boolean tieneTemas(String cursoCargaId, String unidadId ){
		
		boolean ok           = false;		
		try{
			String comando = " SELECT * FROM ENOC.CARGA_UNIDAD_TEMA WHERE CURSO_CARGA_ID = ? AND SUBSTR(TEMA_ID,1,2) = ? ";
			Object[] parametros = new Object[] {cursoCargaId,unidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|getNombreReligion|:"+ex);
	
		}
		
		return ok;
	}

	public boolean tieneCompetencias(String cursoCargaId, String unidadId ){
		boolean ok 				= false;
		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_UNIDAD_COMP" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND UNIDAD_ID = ?" ;
			Object[] parametros = new Object[] {cursoCargaId,unidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|tieneCompetencias|:"+ex);
		
		}
		
		return ok;
	}
	
	public String getUnidadNombre(String cursoCargaId, String unidadId ){
		
		String nombre 			= "";
		
		try{
			String comando = "SELECT UNIDAD_NOMBRE FROM ENOC.CARGA_UNIDAD" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND UNIDAD_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, unidadId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDao|getUnidadNombre|:"+ex);
		
		}
		
		return nombre;
	}
	
	public List<CargaUnidad> getListAll(String orden ){
		
		List<CargaUnidad> lista = new ArrayList<CargaUnidad>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN FROM ENOC.CARGA_UNIDAD "+ orden; 
			lista = enocJdbc.query(comando, new CargaUnidadMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargUnidadUtil|getListAll|:"+ex);
		
		}
		
		return lista;
	}
	
	public List<CargaUnidad> getListUnidad(String cursoCargaId, String orden ){
			
		List<CargaUnidad> lista = new ArrayList<CargaUnidad>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new CargaUnidadMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadDaoUtil|getListAll|:"+ex);		
		}		
		return lista;
	}
}