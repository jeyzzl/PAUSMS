// Bean del Catalogo de Paises
package  aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatPaisDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatPais pais) {
		boolean ok = false;
		
		try{			 
			String comando = "INSERT INTO ENOC.CAT_PAIS (PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO)"
					+ " VALUES( ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {pais.getPaisId(),pais.getNombrePais(),pais.getNacionalidad(),pais.getInteramerica(),pais.getDivisionId(),pais.getSemaforo()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatPais pais ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_PAIS SET NOMBRE_PAIS = ?, NACIONALIDAD = ?,"
					+ " INTERAMERICA = ?, DIVISION_ID = ?, SEMAFORO = ? WHERE PAIS_ID = ?";
			Object[] parametros = new Object[] {pais.getNombrePais(),pais.getNacionalidad(),pais.getInteramerica(),pais.getDivisionId(),pais.getSemaforo(),pais.getPaisId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String paisId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_PAIS WHERE PAIS_ID = ?";
			Object[] parametros = new Object[] {paisId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg(String paisId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PAIS WHERE PAIS_ID = ? ";
			Object[] parametros = new Object[] {paisId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(PAIS_ID)+1,1) FROM ENOC.CAT_PAIS";
			maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public CatPais mapeaRegId(String paisId) {
		
		CatPais pais = new CatPais();
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO "+
				"FROM ENOC.CAT_PAIS WHERE PAIS_ID = ? ";
			Object[] parametros = new Object[] {paisId};		
			pais = enocJdbc.queryForObject(comando, new CatPaisMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return pais;
	}
	
	public HashMap<String,CatPais> getMapAll( ) {
		
		HashMap<String,CatPais> mapa 	= new HashMap<String,CatPais>();
		List<CatPais> lista				= new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO FROM ENOC.CAT_PAIS";
			lista = enocJdbc.query(comando, new CatPaisMapper());
			for (CatPais pais : lista){
				mapa.put(pais.getPaisId(), pais);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public List<aca.Mapa> mapaEstadosPorPais() {		
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT PAIS_ID AS LLAVE, COUNT(ESTADO_ID) AS VALOR FROM ENOC.CAT_ESTADO GROUP BY PAIS_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|mapaEstadosPorPais|:"+ex);
		}
		
		return lista;
	}
	
	public  String getNombrePais(String paisId) {
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {paisId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|getNombrePais|:"+ex);
		}
		return nombre;
	}
	
	public String getNacionalidad(String paisId){
		String nombre		= "-";	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NACIONALIDAD FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|getNacionalidad|:"+ex);
		}		
		return nombre;
	}

	public String getSepPais(String paisId){
		String nombre		= "-";	
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT SEP_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|getSepPais|:"+ex);
		}		
		return nombre;
	}
	
	public List<CatPais> getListAll(String orden ) {
		
		List<CatPais> lista = new ArrayList<CatPais>();
		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO FROM ENOC.CAT_PAIS "+ orden;
			lista = enocJdbc.query(comando, new CatPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatPais> listExtranjerosEnCarga(String cargaId, String orden ) {
		
		List<CatPais> lista = new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO FROM ENOC.CAT_PAIS"
					+ " WHERE PAIS_ID IN (SELECT DISTINCT(NACIONALIDAD) FROM ENOC.ESTADISTICA WHERE CARGA_ID = ? AND NACIONALIDAD != 91) "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CatPaisMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|listExtranjerosEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatPais> lisPaisesExtranjeros(String orden ) {
		
		List<CatPais> lista = new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO FROM ENOC.CAT_PAIS"
					+ " WHERE PAIS_ID IN (SELECT DISTINCT(NACIONALIDAD) FROM ENOC.INSCRITOS WHERE NACIONALIDAD != 91) "+ orden;	
			lista = enocJdbc.query(comando, new CatPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|lisPaisesExtranjeros|:"+ex);
		}
		
		return lista;
	}
	
	
	public List<CatPais> lisPaisesOrigen( String modalidades, String orden ) {
		
		List<CatPais> lista = new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO FROM ENOC.CAT_PAIS"
					+ " WHERE PAIS_ID IN (SELECT DISTINCT(PAIS_ID) FROM ENOC.INSCRITOS WHERE PAIS_ID != 91 AND MODALIDAD_ID IN ("+modalidades+")) "+ orden;
			lista = enocJdbc.query(comando, new CatPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|lisPaisesOrigen|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatPais> lisGraduandos( String eventoId, String orden ) {		
		List<CatPais> lista = new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO"
					+ " FROM ENOC.CAT_PAIS"
					+ " WHERE PAIS_ID IN (SELECT DISTINCT(ENOC.ALUM_PAIS(CODIGO_PERSONAL)) FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999')) "+ orden;
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new CatPaisMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|lisPaisesOrigen|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatPais> lisInscritos( String orden ) {		
		List<CatPais> lista = new ArrayList<CatPais>();		
		try{
			String comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID, SEMAFORO"
					+ " FROM ENOC.CAT_PAIS"
					+ " WHERE PAIS_ID IN (SELECT DISTINCT(PAIS_ID) FROM ENOC.INSCRITOS) "+ orden;			
			lista = enocJdbc.query(comando, new CatPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPaisDao|lisPaisesOrigen|:"+ex);
		}
		
		return lista;
	}
}