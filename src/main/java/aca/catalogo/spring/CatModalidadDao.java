// Clase Util para la tabla de Cat_Modalidad
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatModalidadDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatModalidad modalidad ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_MODALIDAD(MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE )"
					+ " VALUES( TO_NUMBER(?,'99'), ?, ?, ? )";
			Object[] parametros = new Object[] {modalidad.getModalidadId(),modalidad.getNombreModalidad(),modalidad.getEnLinea(),modalidad.getAdmisible()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatModalidad modalidad ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_MODALIDAD "+ 
				"SET NOMBRE_MODALIDAD = ?," +
				" ENLINEA = ?," +
				" ADMISIBLE = ? " +
				"WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";	
			Object[] parametros = new Object[] {modalidad.getNombreModalidad(),modalidad.getEnLinea(),modalidad.getAdmisible(), modalidad.getModalidadId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String modalidadId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {modalidadId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatModalidad mapeaRegId(  String modalidadId) {
		
		CatModalidad modalidad = new CatModalidad();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')"; 
				modalidad = enocJdbc.queryForObject(comando, new CatModalidadMapper(), parametros);
			}		
		}catch(Exception ex){			
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|mapeaRegId|:"+ex);
			return modalidad;
		}		
		return modalidad;
	}
	
	public boolean existeReg( String modalidadId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|existeReg|:"+ex);
		}
		
		return ok;
	}
				
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(MODALIDAD_ID)+1,1) AS MAXIMO FROM ENOC.CAT_MODALIDAD";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public boolean esLinea( String modalidadId) {		
		boolean ok				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND ENLINEA = 'S'";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|esLinea|:"+ex);
		}
		
		return ok;
	}
	
	public boolean esExtension( String modalidadId) {
		
		boolean ok				= true;		
		try{
			String comando = "SELECT COUNT(ENLINEA) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|esExtension|:"+ex);
		}
		
		return ok;
	}
	
	public String modalidadesEnLinea( ) {
		
		List<String> lista	= new ArrayList<String>();
		String modalidades 	= "";
		int row 			= 0;		
		try{			
			String comando = "SELECT MODALIDAD_ID FROM ENOC.CAT_MODALIDAD WHERE ENLINEA = 'S' ORDER BY MODALIDAD_ID";
			lista = enocJdbc.queryForList(comando,String.class);			
			for (String mod : lista){
				row++;
				if (row>1){
					modalidades+= ","+mod;
				}else{
					modalidades+= mod;
				}				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|modalidadesEnLinea|:"+ex);
		}
		
		return modalidades;
	}
	
	public String getEnLinea( String modalidadId){
		String enLinea 			= "N";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT COALESCE(ENLINEA,'N') FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?";				
				enLinea = enocJdbc.queryForObject(comando, String.class, parametros);
			}									
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getEnLinea|:"+ex);
		}		
		return enLinea;
	}
		
	public List<CatModalidad> getListAll( String orden ) {
		
		List<CatModalidad> lista 		= new ArrayList<CatModalidad>();	
		try{
			String comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+orden;
			lista = enocJdbc.query(comando, new CatModalidadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatModalidad> getListInscritos( String orden ) {		
		List<CatModalidad> lista 		= new ArrayList<CatModalidad>();		
		try{
			String comando = "SELECT A.MODALIDAD_ID AS MODALIDAD_ID," +
					" (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) AS NOMBRE_MODALIDAD," +
					" ENLINEA, ADMISIBLE" +
					" FROM ENOC.CAT_MODALIDAD A" + 
					" WHERE (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) > 0 "+ orden;
			lista = enocJdbc.query(comando, new CatModalidadMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getListInscritos|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatModalidad> getMapAll( String orden ) {
		
		HashMap<String,CatModalidad> mapa 	= new HashMap<String,CatModalidad>();
		List<CatModalidad> lista 			= new ArrayList<CatModalidad>();		
		try{
			String comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+ orden;
			lista = enocJdbc.query(comando, new CatModalidadMapper());
			for(CatModalidad mod : lista){
				mapa.put(mod.getModalidadId(), mod);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapModalidades( String orden ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODALIDAD_ID AS LLAVE, NOMBRE_MODALIDAD AS VALOR FROM ENOC.CAT_MODALIDAD "+ orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|mapModalidades|:"+ex);
		}
		
		return mapa;
	}
	
	public List<CatModalidad> getListInscCargas( String cargas, String modalidades, String orden ) {
		
		List<CatModalidad> lista 		= new ArrayList<CatModalidad>();		
		try{
			String comando = " SELECT A.MODALIDAD_ID AS MODALIDAD_ID, A.NOMBRE_MODALIDAD,"
					+ " (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) AS ENLINEA,"
					+ " ADMISIBLE"
					+ " FROM ENOC.CAT_MODALIDAD A"
					+ " WHERE (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) > 0"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"+ orden;
			lista = enocJdbc.query(comando, new CatModalidadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getListInscCargas|:"+ex);
		}
		
		return lista;
	}	
	
	public String getNombreModalidad( String modalidadId ) {
		String nombre			= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?";
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|getNombreModalidad|:"+ex);
		}
		
		return nombre;
	}
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODALIDAD_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_ACADEMICO GROUP BY MODALIDAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatModalidadDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
}