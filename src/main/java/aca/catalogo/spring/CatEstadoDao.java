// Clase Util para la tabla de Cat_Carrera
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatEstadoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEstado estado ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_ESTADO(PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO)"
					+ " VALUES( TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {estado.getPaisId(),estado.getEstadoId(),estado.getNombreEstado(),estado.getCorto(), estado.getSepId(), estado.getSepCorto(), estado.getSemaforo()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( CatEstado estado ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ESTADO"
					+ " SET NOMBRE_ESTADO = ?, CORTO = ?, SEP_ID = ?, SEP_CORTO = ?, SEMAFORO = ?"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {estado.getNombreEstado(),estado.getCorto(),estado.getSepId(),estado.getSepCorto(),estado.getSemaforo(),estado.getPaisId(),estado.getEstadoId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|updateReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg( String paisId, String estadoId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public CatEstado mapeaRegId(  String paisId, String estadoId ) {
		
		CatEstado estado 		= new CatEstado();
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO"
					+ " FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {paisId,estadoId};
			estado = enocJdbc.queryForObject(comando, new CatEstadoMapper(), parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return estado;
	}
	
	public boolean existeReg( String paisId, String estadoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESTADO"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId,estadoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String paisId) {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(ESTADO_ID)+1 AS MAXIMO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreEstado( String paisId, String estadoId) {		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getNombreEstado|:"+ex);
		}
		
		return nombre;
	}

	public String getNombreCorto( String paisId, String estadoId) {		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CORTO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
		
	public List<CatEstado> getLista( String paisId, String orden ) {
		
		List<CatEstado> lista	= new ArrayList<CatEstado>();	
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER(?,'999') "+ orden;
			Object[] parametros = new Object[] {paisId};
			lista = enocJdbc.query(comando, new CatEstadoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getLista|:"+ex);
		}		
		
		return lista;
	}
	
	public List<CatEstado> lisEstadosGraduandos( String paisId, String eventoId, String orden ) {
		
		List<CatEstado> lista	= new ArrayList<CatEstado>();		
		try{
			String comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO "
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND PAIS_ID||ESTADO_ID IN (SELECT ENOC.ALUM_PAIS(CODIGO_PERSONAL)||ENOC.ALUM_ESTADO_ID(CODIGO_PERSONAL) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))"
					+ " "+ orden;
			Object[] parametros = new Object[] {paisId, eventoId};
			lista = enocJdbc.query(comando, new CatEstadoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getLista|:"+ex);
		}		
		
		return lista;
	}
	
	public List<CatEstado> getListAll( String orden ) {
	
		List<CatEstado> lista	= new ArrayList<CatEstado>();
		
		try{
			String comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO "+ orden;
			lista = enocJdbc.query(comando, new CatEstadoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatEstado> getMapAll() {
		
		HashMap<String,CatEstado> mapa	= new HashMap<String,CatEstado>();
		List<CatEstado> lista			= new ArrayList<CatEstado>();		
		try{
			String comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO ";
			lista = enocJdbc.query(comando, new CatEstadoMapper());	
			for ( CatEstado estado : lista){
				 mapa.put(estado.getPaisId()+estado.getEstadoId(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,CatEstado> getMapPaisEstado( String orden ) {
		
		HashMap<String,CatEstado> mapa = new HashMap<String,CatEstado>();
		List<CatEstado> lista			= new ArrayList<CatEstado>();		
		try{
			String comando = " SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO "+ orden;
			lista = enocJdbc.query(comando, new CatEstadoMapper());	
			for ( CatEstado estado : lista){
				 mapa.put(estado.getPaisId()+estado.getEstadoId(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getMapPaisEstado|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String,String> getMapEstado( ) {		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<CatEstado> lista			= new ArrayList<CatEstado>();		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO, SEP_ID, SEP_CORTO, SEMAFORO FROM ENOC.CAT_ESTADO";
			lista = enocJdbc.query(comando, new CatEstadoMapper());	
			for ( CatEstado estado : lista){
				 mapa.put(estado.getPaisId()+estado.getEstadoId(), estado.getNombreEstado());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getEstado|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaTotalEstados(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PAIS_ID AS LLAVE, COUNT(ESTADO_ID) AS VALOR FROM ENOC.CAT_ESTADO GROUP BY PAIS_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|mapaTotalEstados|:"+ex);
		}		
		return mapa;
	}
	
	public String getEstado( String paisId, String estadoId) {
		
		String nombre		= "No encontro";
		
		try{
			String comando = "SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE ESTADO_ID = TO_NUMBER(?,'999') AND PAIS_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { estadoId, paisId };
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstadoDao|getEstado|:"+ex);
		}
		
		return nombre;
	}		
}