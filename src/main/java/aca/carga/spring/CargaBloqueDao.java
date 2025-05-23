package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaBloqueDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaBloque bloque ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_BLOQUE(CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, F_INICIO, F_CIERRE, F_FINAL, INICIO_CLASES )"
				+ " VALUES( ?, "
				+ " TO_NUMBER(?,'99'),"
				+ " ?, "
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_DATE(?,'DD/MM/YYYY'))";		
			Object[] parametros = new Object[] {bloque.getCargaId(),bloque.getBloqueId(),bloque.getNombreBloque(),
				bloque.getFInicio(),bloque.getFCierre(),bloque.getFFinal(), bloque.getInicioClases()
			}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaBloque bloque ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_BLOQUE " 
				+ " SET NOMBRE_BLOQUE = ?, "
				+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'), "
				+ " F_CIERRE = TO_DATE(?,'DD/MM/YYYY'), "
				+ " F_FINAL  = TO_DATE(?,'DD/MM/YYYY'),"
				+ " INICIO_CLASES  = TO_DATE(?,'DD/MM/YYYY')"								
				+ " WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";				
				Object[] parametros = new Object[] { bloque.getNombreBloque(),bloque.getFInicio(), bloque.getFCierre(),
					bloque.getFFinal(), bloque.getInicioClases(), bloque.getCargaId(), bloque.getBloqueId()
				};		 			
	 			if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String cargaId, String bloqueId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cargaId,bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaBloque mapeaRegId(  String cargaId, String bloqueId ) {
		CargaBloque cargaBloque = new CargaBloque();		
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
				+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY')  AS F_CIERRE,"
				+ " TO_CHAR(INICIO_CLASES,'DD/MM/YYYY') AS INICIO_CLASES"
				+ " FROM ENOC.CARGA_BLOQUE "
				+ " WHERE CARGA_ID = ?"
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {cargaId,bloqueId};
			cargaBloque = enocJdbc.queryForObject(comando, new CargaBloqueMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|mapeaRegId|:"+ex);
		}
		return cargaBloque;
	}
	
	public boolean existeReg(String cargaId, String bloqueId) {
		boolean 			ok 	= false;
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean CargaActiva( String cargaId) {
		boolean ok 				= false;	
	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE";
			
			Object[] parametros = new Object[] {cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|cargaActiva|:"+ex);
		}
		return ok;
	}
	
	public String getBloqueActivo(String cargaId) {
		String bloque			= "0";
	
		try{
			String comando = "SELECT BLOQUE_ID FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = ? AND TO_DATE(now(),'dd-mm-yy') BETWEEN F_INICIO AND F_CIERRE";

			Object[] parametros = new Object[] {cargaId};
			bloque = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getBloqueActivo|:"+ex);
		}		
		return bloque;
	}
	
	public String maximoReg(String cargaId) {
		String maximo 			= "1";				
		try{
			String comando = "SELECT COALESCE(MAX(BLOQUE_ID)+1,1) MAXIMO FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ?";			
			Object[] parametros = new Object[] {cargaId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public String getBloqueActual(String cargaId) {
		String bloque 			= "1";
		
		try{
			String comando = "SELECT BLOQUE_ID FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ?"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL";
			Object[] parametros = new Object[] {cargaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				bloque = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getBloqueActual|:"+ex);
		}		
		return bloque;
	}
	
	public String esBloqueActivo(String cargaId, String bloqueId){
		String esActivo 		= "N";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL";
			
			Object[] parametros = new Object[] {cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				esActivo = "S";
			} 	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|esBloqueActivo|:"+ex);
		}
		return esActivo;
	}
	
	public boolean getFechaAntesBloque(String cargaId, String bloque) {
		boolean docencia 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND now() <= F_CIERRE AND BLOQUE_ID = ?";			
			Object[] parametros = new Object[] {cargaId, bloque};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				docencia = true;
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getFechaAntesBloque|:"+ex);
		}		
		return docencia;
	}	
	
	public List<CargaBloque> getListAll( String orden ) {
		List<CargaBloque> lista	= new ArrayList<CargaBloque>();
		
		try{
			String comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE "+orden;
			lista = enocJdbc.query(comando, new CargaBloqueMapper());				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaBloque> getLista( String cargaId, String orden ) {
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();		
		try{
			String comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ? "+ orden;			
			Object[] parametros = new Object[] {cargaId};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getLista|:"+ex);
		}
		return lisBloque;
	}
	
	public List<CargaBloque> lisBloquesAlumno( String cargaId, String codigoPersonal, String orden ){
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();		
		try{
			String comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID||BLOQUE_ID IN (SELECT CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL= ? AND CARGA_ID = ?) "+ orden;			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getLista|:"+ex);
		}
		return lisBloque;
	}
	
	public List<CargaBloque> lisMaestroCarga( String cargaId,String codigoMaestro, String orden ) {
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();	
		try{
			String comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN (SELECT BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ?) "+ orden;			
			Object[] parametros = new Object[] {cargaId, cargaId, codigoMaestro};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getLista|:"+ex);
		}
		return lisBloque;
	}
	
	public List<CargaBloque> getListaAlumno( String cargaId, String codigoPersonal, String orden ){
		
		List<CargaBloque> lista	= new ArrayList<CargaBloque>();		
		try{
			String comando = " SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"  
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"  
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"  
					+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARGA_ID||BLOQUE_ID IN (SELECT SUBSTR(CURSO_CARGA_ID,1,6)||ENOC.GRUPO_BLOQUE(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {cargaId, codigoPersonal};
			lista = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.BloqueUtil|getListaAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<CargaBloque> getListaActivo( String cargaId, String orden ) {
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE,"
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
				+ " TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE,"
				+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
				+ " FROM ENOC.CARGA_BLOQUE"
				+ " WHERE CARGA_ID = ?"
				+ " AND F_INICIO <= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')"
				+ " AND F_CIERRE >= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') " +orden;
			
			Object[] parametros = new Object[] {cargaId};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getListaActivo|:"+ex);
		}		
		return lisBloque;
	}
	
	public List<CargaBloque> getListBloqueCarga(String cargaId, String orden ) {
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? " +orden;			
			Object[] parametros = new Object[] {cargaId};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getListBloqueCarga|:"+ex);
		}		
		return lisBloque;
	}
	
	public List<CargaBloque> lisBloquesDelMaestro(String codigoPersonal, String cargaId, String orden ) {
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE "
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN (SELECT DISTINCT(BLOQUE_ID) FROM ENOC.CARGA_ACADEMICA WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?) " +orden;			
			Object[] parametros = new Object[] {cargaId, codigoPersonal, cargaId};	
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|lisBloquesDelMaestro|:"+ex);
		}		
		return lisBloque;
	}
	
	public List<CargaBloque> lisBloquesEnInscripcion(String codigoAlumno, String orden ){
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE "
					+ " WHERE SYSDATE BETWEEN F_INICIO AND F_CIERRE"
					+ " AND CARGA_ID||BLOQUE_ID NOT IN (SELECT CARGA_ID||BLOQUE_ID FROM ALUM_ESTADO WHERE CODIGO_PERSONAL = ?)"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM CARGA_ENLINEA WHERE ESTADO = 'A') " +orden;
			Object[] parametros = new Object[] {codigoAlumno};
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|lisBloquesEnInscripcion|:"+ex);
		}		
		return lisBloque;
	}
	
	public List<CargaBloque> lisBloquesEnHorario(String cargaId, String orden ){
		List<CargaBloque> lisBloque	= new ArrayList<CargaBloque>();
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
				+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
				+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
				+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
				+ " FROM ENOC.CARGA_BLOQUE "
				+ " WHERE CARGA_ID = ? AND BLOQUE_ID IN (SELECT BLOQUE_ID FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID != '0') " +orden;
			Object[] parametros = new Object[] {cargaId, cargaId};
			lisBloque = enocJdbc.query(comando, new CargaBloqueMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|lisBloquesEnHorario|:"+ex);
		}		
		return lisBloque;
	}
	
	public String getNombre(String cargaId, String bloqueId) {
		String nombre			= "-";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cargaId,bloqueId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT NOMBRE_BLOQUE FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|getNombre|:"+ex);
		}		
		return nombre;
	}
	
	public HashMap<String, CargaBloque> mapaBloques( ) {
		
		HashMap<String, CargaBloque> mapa = new HashMap<String, CargaBloque>();
		List<CargaBloque> lista	= new ArrayList<CargaBloque>();		
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE";			
			lista = enocJdbc.query(comando,new CargaBloqueMapper());
			for(CargaBloque bloque : lista){
				mapa.put(bloque.getCargaId()+bloque.getBloqueId(), bloque);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|mapaBloques|:"+ex);
		}

		return mapa;
	}	
	
	public HashMap<String, CargaBloque> mapaBloquesActivos( ) {
		
		HashMap<String, CargaBloque> mapa = new HashMap<String, CargaBloque>();
		List<CargaBloque> lista	= new ArrayList<CargaBloque>();		
		try{
			String comando = "SELECT CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " TO_CHAR(INICIO_CLASES, 'DD/MM/YYYY') AS INICIO_CLASES"
					+ " FROM ENOC.CARGA_BLOQUE"
					+ " WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') >= F_INICIO"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') <= F_CIERRE";			
			lista = enocJdbc.query(comando,new CargaBloqueMapper());
			for(CargaBloque bloque : lista){
				mapa.put(bloque.getCargaId()+bloque.getBloqueId(), bloque);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|mapaBloques|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaTotalBloques(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_BLOQUE GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaBloqueDao|mapaBloques|:"+ex);
		}

		return mapa;
	}
}