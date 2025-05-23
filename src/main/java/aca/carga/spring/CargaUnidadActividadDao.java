package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaUnidadActividadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaUnidadActividad actividad ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_UNIDAD_ACTIVIDAD(CURSO_CARGA_ID, ACTIVIDAD_ID, " + 
			    " ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN)" +
				" VALUES( ?, ?, ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {actividad.getCursoCargaId(), actividad.getActividadId(),
			actividad.getActividadNombre(), actividad.getComentario(), actividad.getValor(), actividad.getOrden()};
						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaUnidadActividad actividad ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_UNIDAD_ACTIVIDAD "+ 
				" SET ACTIVIDAD_NOMBRE = ?, " +
				" COMENTARIO = ?," +
				" VALOR = TO_NUMBER(?,'999.99'), " +
				" ORDEN = TO_NUMBER(?,'99')" +				
				" WHERE CURSO_CARGA_ID = ? " +
				" AND ACTIVIDAD_ID = ? ";
			Object[] parametros = new Object[] {actividad.getActividadNombre(), actividad.getComentario(),
			actividad.getValor(), actividad.getOrden(), actividad.getCursoCargaId(), actividad.getActividadId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaId, String actividadId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_UNIDAD_ACTIVIDAD "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId, actividadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaUnidadActividad mapeaRegId(  String cursoCargaId, String actividadId) {
		
		CargaUnidadActividad objeto = new CargaUnidadActividad();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, " +
					" ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? AND ACTIVIDAD_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, actividadId};
			objeto = enocJdbc.queryForObject(comando, new CargaUnidadActividadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String actividadId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?  AND ACTIVIDAd_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoCargaId ) {
		String maximo = "01";
		
		try{
			String comando = " SELECT COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(ACTIVIDAD_ID,4,6),'99'))+1,'00')),'01') AS MAXIMO" +
					" FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? ";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean tieneCriterios( String cursoCargaId, String actividadId ) {
		boolean ok = false;			
		try{
			String comando = " SELECT * FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = ? AND SUBSTR(CRITERIO_ID,1,6) = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, cursoCargaId, actividadId)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|tieneCriterios|:"+ex);
		}	
		return ok;
	}
	
	public int getNumActividad( String cursoCargaId) {
		int numActividades = 0;
		
		try{
			String comando = "SELECT COUNT(ACTIVIDAD_ID) AS ACTIVIDAD FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numActividades = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|getSumActividades|:"+ex);
		}
		return numActividades;
	}
	
	public String getNombreActividad( String cursoCargaId, String actividadId) {
		String nombre = "";
		
		try{
			String comando = "SELECT ACTIVIDAD_NOMBRE FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ACTIVIDAD_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,actividadId};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|getNombreActividad|:"+ex);
		}
		return nombre;
	}
	
	public List<CargaUnidadActividad> getListAll( String orden ) {
			
		List<CargaUnidadActividad> lista = new ArrayList<CargaUnidadActividad>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, COMENTARIO, VALOR,ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD "+ orden;
			lista = enocJdbc.query(comando, new CargaUnidadActividadMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaUnidadActividad> getListActividades(String cursoCargaId, String orden ) {
		
		List<CargaUnidadActividad> lista = new ArrayList<CargaUnidadActividad>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new CargaUnidadActividadMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|getListActividades|:"+ex);
		}
		return lista;
	}
	
	public List<String> getDistintasActividades( String cursoCargaId, String orden ) {
		
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT(SUBSTR(ACTIVIDAD_ID,5,2)) AS ACTIVIDAD FROM ENOC.CARGA_UNIDAD_ACTIVIDAD" + 
				" WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.queryForList(comando, String.class, cursoCargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|getDistintasActividades|:"+ex);
		}
		return lista;
	}
	
	public double sumaValorActividad( String cursoCargaId, String actividadId ) {		
		double suma		= 0;		
		try{
			String comando = "SELECT SUM(VALOR) AS SUMA FROM ENOC.CARGA_UNIDAD_ACTIVIDAD WHERE CURSO_CARGA_ID = ? AND SUBSTR(ACTIVIDAD_ID,5,2) = ?";
			suma = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, actividadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaUnidadActividadDao|sumaValorActividad|:"+ex);
		}
		return suma;
	}
	
}