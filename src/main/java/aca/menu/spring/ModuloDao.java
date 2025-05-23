// Clase para la tabla de Modulo
package aca.menu.spring ;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ModuloDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Modulo modulo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MODULO(MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES) VALUES(?,?,?,?, TO_NUMBER(?,'99'),?)"; 
			
			Object[] parametros = new Object[] {modulo.getModuloId(),modulo.getNombreModulo(),
					modulo.getUrl(),modulo.getIcono(),modulo.getMenuId(),modulo.getNombreIngles()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(Modulo modulo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MODULO SET NOMBRE_MODULO = ?, URL= ?, ICONO= ?, MENU_ID = TO_NUMBER(?,'99'), NOMBRE_INGLES = ? WHERE MODULO_ID = ?";			 
			
			Object[] parametros = new Object[] {modulo.getNombreModulo(),modulo.getUrl(),
					modulo.getIcono(),modulo.getMenuId(),modulo.getNombreIngles(),modulo.getModuloId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String moduloId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MODULO WHERE MODULO_ID = ?"; 
			Object[] parametros = new Object[] {moduloId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public Modulo mapeaRegId( String moduloId) {
		Modulo modulo = new Modulo();
				
		try{ 
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO,MENU_ID,NOMBRE_INGLES FROM ENOC.MODULO WHERE MODULO_ID = ? "; 
			
			Object[] parametros = new Object[] {moduloId};
			modulo = enocJdbc.queryForObject(comando, new ModuloMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|mapeaRegId|:"+ex);
		}
		return modulo;
	}
	
	public boolean existeReg(String moduloId) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO WHERE MODULO_ID = ? "; 
			
			Object[] parametros = new Object[] {moduloId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String moduloNombre( String moduloId) {
		String nombre 		= "-";
		
		try{
			String comando = "SELECT COUNT(NOMBRE_MODULO) FROM ENOC.MODULO WHERE MODULO_ID = ?";
			Object[] parametros = new Object[] {moduloId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT NOMBRE_MODULO FROM ENOC.MODULO WHERE MODULO_ID = ?";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|moduloNombre|:"+ex);
		}
		return nombre;
	}
		
	public List<Modulo> getListAll( String orden ) {
		List<Modulo> lista 	= new ArrayList<Modulo>();
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO "+orden;			
			lista = enocJdbc.query(comando, new ModuloMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<Modulo> lisPorEstados( String estados, String orden ) {
		List<Modulo> lista 	= new ArrayList<Modulo>();
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO WHERE MODULO_ID IN (SELECT DISTINCT(MODULO_ID) FROM ENOC.MODULO_OPCION WHERE ICONO IN ("+estados+")) "+orden;			
			lista = enocJdbc.query(comando, new ModuloMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<Modulo> lisPorMenu( String menuId, String orden ) {
		List<Modulo> lista 	= new ArrayList<Modulo>();
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO WHERE MENU_ID = ? "+orden;
			Object[] parametros = new Object[] {menuId};
			lista = enocJdbc.query(comando, new ModuloMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|lisPorMenu|:"+ex);
		}
		return lista;
	}
		
	public List<Modulo> getListUser( String  codigoPersonal, String opciones ) {
		List<Modulo> lista 	= new ArrayList<Modulo>();		
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO WHERE MODULO_ID IN "
					+ "	(SELECT MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID IN "
					+ "		(SELECT OPCION_ID FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?))"
					+ " ORDER BY MENU_ID, 2";		
			lista = enocJdbc.query(comando, new ModuloMapper(), codigoPersonal);		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getListUser|:"+ex);
		}
		return lista;
	}
	
	public List<Modulo> LisModulosDelUsuario( String  codigoPersonal) {
		List<Modulo> lista 	= new ArrayList<Modulo>();
		
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO"
					+ " WHERE MODULO_ID IN ( SELECT MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID IN (SELECT OPCION_ID FROM ACCESO_OPCION WHERE CODIGO_PERSONAL = ?))"
					+ " ORDER BY MENU_ID, 2";			
			lista = enocJdbc.query(comando, new ModuloMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|LisModulosDelUsuario|:"+ex);
		}
		return lista;
	}
	
	public boolean getEsMaestro( String  codigoPersonal) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getEsMaestro|:"+ex);
		}		
		return ok;
	}
	
	// Valida si el empleado edita contenidos de materias
	public boolean esEditorDeContenidos( String  codigoPersonal) {
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT COUNT(CURSO_ID) FROM ENOC.MAPA_NUEVO_CURSO WHERE CODIGO_MAESTRO LIKE '%"+codigoPersonal+"%'";			
			if (enocJdbc.queryForObject(comando,Integer.class) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|esEditorDeContenidos|:"+ex);
		}		
		return ok;
	}
	
	public boolean getEsEmpleado( String  codigoPersonal) {
		String comando		= "";
		boolean ok 			= false;		
		try{
			comando = "SELECT COUNT(CLAVE) FROM ARON.EMPLEADO WHERE CLAVE = ?";		
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getEsEmpleado|:"+ex);
		}		
		return ok;
	}	
	
	public boolean getEsColaborador( String  codigoPersonal) {
		String comando		= "";
		boolean ok 			= false;		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.EMP_MAESTRO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getEsColaborador|:"+ex);
		}		
		return ok;
	}
	
	public boolean getEsMentor( String  codigoPersonal) {
		String comando		= "";
		boolean ok 			= false;
		
		try{
			comando = "SELECT COUNT(MENTOR_ID) FROM ENOC.MEN_CARRERA "+
			"WHERE MENTOR_ID = ? ";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getEsMentor|:"+ex);
		}
		return ok;
	}
	
	public 	HashMap<String, String> mapaNombreMenu() {
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa			= new HashMap<String, String>();
		String comando					= "";
		
		try{
			comando = "SELECT MODULO_ID AS LLAVE, (SELECT MENU_NOMBRE FROM ENOC.MENU WHERE MENU_ID = A.MENU_ID) AS VALOR FROM ENOC.MODULO A";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getMapAll|:"+ex);
		}
		return mapa;
	}
	
	
	public 	HashMap<String, String> mapaNombreIngles() {
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa			= new HashMap<String, String>();
		String comando					= "";
		
		try{
			comando = "SELECT MODULO_ID AS LLAVE, (SELECT NOMBRE_INGLES FROM ENOC.MENU WHERE MENU_ID = A.MENU_ID) AS VALOR FROM ENOC.MODULO A";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|getMapAll|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapMenuModulos() {
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		HashMap<String, String> map = new HashMap<String, String>();		
		try{
			String comando = "SELECT MENU_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MODULO GROUP BY MENU_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				map.put(m.getLlave(), (String)m.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|mapMenuModulos|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, Modulo> mapModulos() {
		List<Modulo>	lista 		= new ArrayList<Modulo>();
		HashMap<String, Modulo> map = new HashMap<String, Modulo>();
		try{
			String comando = "SELECT MODULO_ID, NOMBRE_MODULO, URL, ICONO, MENU_ID, NOMBRE_INGLES FROM ENOC.MODULO";			
			lista = enocJdbc.query(comando, new ModuloMapper());
			for (Modulo mod : lista){
				map.put(mod.getModuloId(), mod);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloDao|mapModulos|:"+ex);
		}		
		return map;
	}

	
}