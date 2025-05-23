// Clase para la tabla de CandTipo
package aca.candado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CandTipoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CandTipo tipo ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.CAND_TIPO "
					+ " (TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA) "
					+ " VALUES(?,?,?,?,?,?,?,?,?)";
			Object[] parametros = new Object[] {
				tipo.getTipoId(),tipo.getNombreTipo(),tipo.getResponsable(),tipo.getLugar(),tipo.getTelefono(),tipo.getEstado(),tipo.getCorreo(),tipo.getCelular(),tipo.getPersona()
			};
		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( CandTipo tipo ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.CAND_TIPO "
					+ " SET NOMBRE_TIPO = ?, RESPONSABLE = ?, LUGAR = ?, TELEFONO = ?, ESTADO = ?, CORREO = ?, CELULAR = ?, PERSONA = ?"
					+ " WHERE TIPO_ID = ? ";
			Object[] parametros = new Object[] {
				tipo.getNombreTipo(),tipo.getResponsable(),tipo.getLugar(),tipo.getTelefono(),tipo.getEstado(),tipo.getCorreo(),tipo.getCelular(),tipo.getPersona(),tipo.getTipoId()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String tipoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CAND_TIPO WHERE TIPO_ID = ?";
			Object[] parametros = new Object[] {tipoId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	
	public CandTipo mapeaRegId( String tipoId) {
		
		CandTipo tipo = new CandTipo();
		try{
			String comando = "SELECT "
					+ "TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA "
					+ "FROM ENOC.CAND_TIPO WHERE TIPO_ID = ? ";
			
			Object[] parametros = new Object[] {tipoId};
			tipo = enocJdbc.queryForObject(comando, new CandTipoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|mapeaRegId|:"+ex);
		}
		return tipo;
	}
	
	public boolean existeReg(String tipoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_TIPO WHERE TRIM(TIPO_ID) = ? ";
			Object[] parametros = new Object[] {tipoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ){		
		String maximo				="1";		
		try{
			String comando = "SELECT COALESCE(MAX(TIPO_ID)+1,1) AS MAXIMO FROM ENOC.CAND_TIPO";			
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}	
	
	public boolean esResponsable( String codigoPersonal) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_TIPO WHERE RESPONSABLE LIKE '%-'||?||'-%'";	
			Object[] parametros = new Object[] {codigoPersonal};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|esResponsable|:"+ex);
		}
		return ok;
	}
	
	public boolean esResponsable( String tipoId, String codigoPersonal) {
		boolean ok 			= false;

		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_TIPO "
					+ "WHERE TIPO_ID = ? "
					+ "AND RESPONSABLE LIKE '%-'||?||'-%'";
			
			Object[] parametros = new Object[] {tipoId,codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|esResponsable|:"+ex);
		}
		return ok;
	}
	
	public String getNombreTipo( String tipoId) {		
		String nombre			= "";
		
		try{
			String comando = "SELECT NOMBRE_TIPO "
					+ "FROM ENOC.CAND_TIPO WHERE TIPO_ID = ? "; 
			Object[] parametros = new Object[] {tipoId};
			nombre = enocJdbc.queryForObject(comando, String.class,parametros);
						
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getNombreTipo|:"+ex);
		}		
		return nombre;
	}
		
	public String getSolucion( String tipoId) {		
		String solucion			= "";

		try{
			String comando = "SELECT LUGAR||' '||TELEFONO AS SOLUCION FROM ENOC.CAND_TIPO WHERE TIPO_ID = ?";			
			Object[] parametros = new Object[] {tipoId};
			solucion = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getSolucion|:"+ex);
		}
		return solucion;
	}
	
	public String getLugar( String tipoId) {		
		String solucion			= "";		
		try{
			String comando = "SELECT LUGAR AS SOLUCION FROM ENOC.CAND_TIPO WHERE TIPO_ID = ?"; 
			Object[] parametros = new Object[] {tipoId};
			solucion = enocJdbc.queryForObject(comando, String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getSolucion|:"+ex);
		}
		return solucion;
	}
	
	public String maximoReg(String tipoId) {
		String maximo 			= "1";
		try{
			String comando = "SELECT MAX(TIPO_ID)+1 MAXIMO FROM ENOC.CAND_TIPO";			
			Object[] parametros = new Object[] {tipoId};
			maximo = enocJdbc.queryForObject(comando, String.class,parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiCuenta|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	
	// Regresa un listor con todos los elementos de la tabla
	public List<CandTipo> getListAll( String orden ) {
		
		List<CandTipo> lisTipo 	= new ArrayList<CandTipo>();		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA FROM ENOC.CAND_TIPO "+orden;			
			lisTipo = enocJdbc.query(comando, new CandTipoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getListAll|:"+ex);
		}		
		return lisTipo;
	}
	
	public List<CandTipo> lisPorAlumno( String codigoPersonal, String orden ) {
		
		List<CandTipo> lisTipo 	= new ArrayList<CandTipo>();		
		try{
			String comando = " SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA "
					+ " FROM ENOC.CAND_TIPO"
					+ " WHERE TIPO_ID IN (SELECT SUBSTR(CANDADO_ID,1,2) FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A') "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lisTipo = enocJdbc.query(comando, new CandTipoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|lisPorAlumno|:"+ex);
		}		
		return lisTipo;
	}
	
	// Regresa un listor con todos los elementos de la tabla
	public List<CandTipo> getLista( String orden ) {
			
		List<CandTipo> lisTipo 	= new ArrayList<CandTipo>();
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA FROM ENOC.CAND_TIPO "+ orden;		
			lisTipo = enocJdbc.query(comando, new CandTipoMapper());						
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getLista|:"+ex);
		}
		return lisTipo;
	}
	
	// Regresa una lista con los modulos de candado a los que un usuario tiene acceso
	public List<CandTipo> getLista( String codigoPersonal, String orden ) {
		
		List<CandTipo> lisTipo 	= new ArrayList<CandTipo>();		
		try{
			String comando = "SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA"
					+ " FROM ENOC.CAND_TIPO"
					+ " WHERE TIPO_ID IN (SELECT TIPO_ID FROM CAND_PERMISO WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal};	
			lisTipo = enocJdbc.query(comando, new CandTipoMapper(),parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|getLista|:"+ex);
		}
		return lisTipo;
	}
	
	
	public HashMap<String,CandTipo> mapTipo() {
		
		HashMap<String,CandTipo> map = new HashMap<String,CandTipo>();
		List<CandTipo> lista = new ArrayList<CandTipo>(); 
		
		try{
			String comando = " SELECT TIPO_ID, NOMBRE_TIPO, RESPONSABLE, LUGAR, TELEFONO, ESTADO, CORREO, CELULAR, PERSONA FROM ENOC.CAND_TIPO";			
			lista = enocJdbc.query(comando, new CandTipoMapper());
			for (CandTipo tipo : lista ) {
				map.put(tipo.getTipoId(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandTipoDao|mapTipo|:"+ex);
		}		
		return map;
	}

	public HashMap<String,String> mapaPermisosPorTipo() {		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT TIPO_ID AS LLAVE, COUNT(*) AS VALOR FROM CAND_PERMISO GROUP BY TIPO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|mapaPermisosPorTipo|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapaCandadosPorTipo() {		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT TIPO_ID AS LLAVE, COUNT(*) AS VALOR FROM CANDADO GROUP BY TIPO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|mapaCandadosPorTipo|:"+ex);
		}		
		return mapa;
	}
	
}