// Clase para la tabla de MODULO_OPCION
package aca.menu.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OpcionDao1{
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( Opcion opcion ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MODULO_OPCION"
					+ " (MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, ICONO, ORDEN, USUARIOS, CARPETA)"
					+ " VALUES(?,?,?,?,?,TO_NUMBER(?,'99'),?, ?)";			
			
			Object[] parametros = new Object[] {
					opcion.getModuloId(),opcion.getOpcionId(),opcion.getNombreOpcion(),
					opcion.getUrl(),opcion.getIcono(),opcion.getOrden(),opcion.getUsuarios(),
					opcion.getCarpeta()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( Opcion opcion ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MODULO_OPCION"
					+ " SET NOMBRE_OPCION = ?, URL= ?, ICONO= ?,"
					+ " ORDEN = TO_NUMBER(?,'99'), USUARIOS = ?,"
					+ " CARPETA = ?"
					+ " WHERE MODULO_ID = ? AND OPCION_ID = ?";	
					
			Object[] parametros = new Object[] {opcion.getNombreOpcion(),opcion.getUrl(),
					opcion.getIcono(),opcion.getOrden(),opcion.getUsuarios(),opcion.getCarpeta(),
					opcion.getModuloId(),opcion.getOpcionId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateOpcion( String usuarios, String opcionId  ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MODULO_OPCION "+ 
				"SET USUARIOS = ? "+
				"WHERE  OPCION_ID = ?";
			
			Object[] parametros = new Object[] {usuarios,opcionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|updateOpcion|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String moduloId, String opcionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ?"; 
			
			Object[] parametros = new Object[] {moduloId,opcionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|deleteReg|:"+ex);
		}
		return ok;
	}
		
	public Opcion mapeaRegId( String moduloId, String opcionId) {
		Opcion	opcion = new Opcion();
		
		try{ 
			String comando = "SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, "+
							"ICONO, ORDEN, USUARIOS, CARPETA FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ? "; 
			
			Object[] parametros = new Object[] {moduloId,opcionId};
			opcion = enocJdbc.queryForObject(comando, new OpcionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|mapeaRegId|:"+ex);
		}
		return opcion;
	}
	
	public Opcion mapeaRegId( String opcionId) {
		Opcion opcion = new Opcion();
		
		try{ 
			String comando = "SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, "+
							"ICONO, ORDEN, USUARIOS, CARPETA FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ? "; 
						
			Object[] parametros = new Object[] {opcionId};
			opcion = enocJdbc.queryForObject(comando, new OpcionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|mapeaRegId|:"+ex);
		}
		return opcion;
	}	
	
	public boolean existeReg( String moduloId, String opcionId) {
		boolean ok 			= false;		
		
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_OPCION WHERE MODULO_ID = ? AND OPCION_ID = ?"; 
			
			Object[] parametros = new Object[] {moduloId,opcionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|existeReg|:"+ex);
		}
		return ok;		
	}
	
	public String usuariosOpcion( String opcionId) {		
		String usuarios		= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?";
			Object[] parametros = new Object[] {opcionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT COALESCE(USUARIOS,TO_CLOB('X')) FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?";
				usuarios =  enocJdbc.queryForObject(comando,String.class,parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|usuariosOpcion|:"+ex);
		}	
		return usuarios;
	}
	
	public String opcionNombre( String opcionId) {
		
		String nombre		= "X";
		
		try{
			String comando = "SELECT NOMBRE_OPCION||'@@'||MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?";
			Object[] parametros = new Object[] {opcionId};
			nombre =  enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|opcionNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public boolean tienePermiso(String ruta, String usuario){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM MODULO_OPCION WHERE CARPETA = ? AND INSTR(USUARIOS,?) > 0";			
			Object[] parametros = new Object[]{ruta, usuario};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;				
			}
			
		}catch( Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|tienePermiso|:"+ex);
		}
		
		return ok;		
	}	
	
	// Regresa un listor con todos los elementos de la tabla Modulo_opcion 
	public List<Opcion> getListAll( String orden ) {
		
		List<Opcion> lista	= new ArrayList<Opcion>();		
		String comando		= "";
		
		try{
			comando = "SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, ICONO, ORDEN, USUARIOS, CARPETA FROM ENOC.MODULO_OPCION "+ orden; 
			lista = enocJdbc.query(comando, new OpcionMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	// Regresa un listor del Objeto "Opcion" con las opciones a las que tiene acceso el usuario
	public List<Opcion> getListUser( String codigoPersonal, String opciones ) {		
		List<Opcion> lista 	= new ArrayList<Opcion>();				
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA"
					+ " FROM ENOC.MODULO_OPCION"
					+ " WHERE ICONO = 'A'"
					+ " AND USUARIOS LIKE '%-"+codigoPersonal+"-%'"
					+ " OR OPCION_ID IN ("+opciones+")"
					+ " ORDER BY 1,3";
			lista = enocJdbc.query(comando, new OpcionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|getListUser|:"+ex);
		}		
		return lista;
	}
	
	// Regresa un String que contiene las opciones a que tiene acceso el usuario.
	public String getOpcUser( String codigoPersonal ) {
		
		List<String> lista 	= new ArrayList<String>();	
		String comando		= "";
		String opcion		= "";
		
		try{
			comando = "SELECT OPCION_ID FROM ENOC.MODULO_OPCION"+ 
					" WHERE ICONO = 'A' AND USUARIOS LIKE '%-"+codigoPersonal+"-%'";
			lista = enocJdbc.queryForList(comando, String.class);			
			for ( String op : lista){				
				opcion = opcion +op+" ";				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|getOpcUser|:"+ex);
		}
		
		return opcion;
	}
	
	
	public HashMap<String, String> mapModuloOpciones(Connection conn) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{
			comando = "SELECT MODULO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MODULO_OPCION GROUP BY MODULO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for( aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|mapModuloOpciones|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCarpetas( String codigoPersonal) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		String comando		= "";
		
		try{
			comando = "SELECT CARPETA FROM ENOC.MODULO_OPCION WHERE USUARIOS LIKE '%"+codigoPersonal+"%'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for( aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.OpcionDao|mapCarpetas|:"+ex);
		}
		
		return mapa;
	}
}