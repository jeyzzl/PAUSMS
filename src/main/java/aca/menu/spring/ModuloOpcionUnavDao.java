package aca.menu.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ModuloOpcionUnavDao {
/*	
	@Autowired	
	@Qualifier("jdbcUnav")	
	private JdbcTemplate enocJdbc;
	
	public boolean updateUsuarios(String opcionId, String usuarios){
        boolean ok = false;        
        try{
            String comando = "UPDATE ENOC.MODULO_OPCION SET USUARIOS = ? WHERE OPCION_ID = ?";            
            Object[] parametros = new Object[] {usuarios,opcionId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}     
        }catch (Exception ex){
            System.out.println("Error - aca.menu.spring.ModuloOpcionDao|updateAccesos|:" + ex);
        }
        return ok;
    }
	
	public boolean updateNombreIngles(ModuloOpcion moduloOpcion){
		boolean ok = false;        
		try{
			String comando = "UPDATE ENOC.MODULO_OPCION SET NOMBRE_INGLES = ? WHERE OPCION_ID = ?";            
			Object[] parametros = new Object[] {moduloOpcion.getNombreIngles(),moduloOpcion.getOpcionId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;			
			}     
		}catch (Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|updateNombreIngles|:" + ex);
		}
		return ok;
	}
	
	public boolean borrarUsuario(String usuario){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MODULO_OPCION SET USUARIOS = REPLACE(USUARIOS,?,'')  WHERE USUARIOS LIKE '%"+usuario+"%'";
			if (enocJdbc.update(comando) >= 1){
				ok = true;			
			}     
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|borrarUsuario|:"+ex);
		}
		return ok;
	}
	
	public boolean limpiarGuiones(){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MODULO_OPCION SET USUARIOS = REPLACE(USUARIOS,'--','-') WHERE USUARIOS LIKE '%--%'";						
			if (enocJdbc.update(comando) >= 1){
				ok = true;			
			}    
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|limpiarGuiones|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg(String opcionId){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_OPCION WHERE OPCION_ID = ?";
			Object[] parametros = new Object[]{opcionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.menu.spring.ModuloOpcionDao||existeReg :"+ex);
		}
		
		return ok;		
	}
	
	public ModuloOpcion mapeaRegId(String opcionId){
		ModuloOpcion objeto = new ModuloOpcion();
		
		try{			
			String query = "SELECT MODULO_ID,OPCION_ID,NOMBRE_OPCION,URL,ICONO,ORDEN,USUARIOS,MAPA,CARPETA,NOMBRE_INGLES"
					+ " FROM ENOC.MODULO_OPCION"
					+ " WHERE OPCION_ID = ?";
			Object[] parametros = new Object[]{opcionId};
			objeto = enocJdbc.queryForObject(query, new ModuloOpcionMapper(), parametros);
		}catch( Exception ex){
			System.out.println("Error: aca.menu.spring.ModuloOpcionDao||mapeaRegId"+ex);
		}
		
		return objeto;
	}
	
	// Regresa un String que contiene las opciones a que tiene acceso el usuario.
	public String getOpcUser(String codigoPersonal ){
		List<String> lista 	= new ArrayList<String>();
		String opcion		= "";		
		try{
			String comando = "SELECT OPCION_ID FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal);
			for (String op : lista){				
				opcion = opcion +op+" ";				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|getOpcUser|:"+ex);
		}
		
		return opcion;
	}
	
	public HashMap<String, ModuloOpcion> mapaOpciones(){		
		HashMap<String, ModuloOpcion> mapa 	= new HashMap<String, ModuloOpcion>();
		List<ModuloOpcion> lista 			= new ArrayList<ModuloOpcion>();		
		try{
			String comando = "SELECT MODULO_ID,OPCION_ID,NOMBRE_OPCION,URL,ICONO,ORDEN,USUARIOS,MAPA,CARPETA,NOMBRE_INGLES FROM ENOC.MODULO_OPCION";
			lista = enocJdbc.query(comando, new ModuloOpcionMapper());
			for(ModuloOpcion opcion : lista){				
				mapa.put(opcion.getOpcionId(), opcion);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|mapaOpciones|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, String> mapModuloOpciones(){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT MODULO_ID AS LLAVE, COUNT(*) AS VALOR AS OPCIONES FROM ENOC.MODULO_OPCION GROUP BY MODULO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|mapModuloOpciones|:"+ex);
		}
		
		return mapa;
	}
	
	public List<ModuloOpcion> lisTodas( String orden){
		
		List<ModuloOpcion> lista 	= new ArrayList<ModuloOpcion>();
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA, NOMBRE_INGLES"
					+ " FROM ENOC.MODULO_OPCION "+orden;					
			lista = enocJdbc.query(comando, new ModuloOpcionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|lisTodas|:"+ex);
		}
		
		return lista;
	}
	
	public List<ModuloOpcion> lisPorMenu( String menuId, String orden){		
		List<ModuloOpcion> lista 	= new ArrayList<ModuloOpcion>();
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA, NOMBRE_INGLES"
					+ " FROM ENOC.MODULO_OPCION "
					+ " WHERE MODULO_ID IN (SELECT MODULO_ID FROM ENOC.MODULO WHERE MENU_ID = ?) "+orden;
			Object[] parametros = new Object[]{menuId};
			lista = enocJdbc.query(comando, new ModuloOpcionMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|lisPorMenu|:"+ex);
		}
		
		return lista;
	}
	
	public List<ModuloOpcion> listUser(String codigoPersonal, String opciones ){		
		List<ModuloOpcion> lisOpcion 	= new ArrayList<ModuloOpcion>();
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA, NOMBRE_INGLES"
				+ " FROM ENOC.MODULO_OPCION"
				+ " WHERE ICONO IN ('A','R')"
				+ " AND OPCION_ID IN (SELECT OPCION_ID FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?) ORDER BY 1,3";
			lisOpcion = enocJdbc.query(comando, new ModuloOpcionMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|listUser|:"+ex);
		}
		
		return lisOpcion;
	}
	
	public List<ModuloOpcion> lisOpcionesDelUsuario(String codigoPersonal){		
		List<ModuloOpcion> lisOpcion 	= new ArrayList<ModuloOpcion>();
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA, NOMBRE_INGLES"
					+ " FROM ENOC.MODULO_OPCION"
					+ " WHERE ICONO IN ('A','R')"
					+ " AND OPCION_ID IN (SELECT OPCION_ID FROM ACCESO_OPCION WHERE CODIGO_PERSONAL = ?)"					
					+ " ORDER BY 1,3";
			lisOpcion = enocJdbc.query(comando, new ModuloOpcionMapper(), codigoPersonal);		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|lisOpcionesDelUsuario|:"+ex);
		}		
		return lisOpcion;
	}
	
	public List<ModuloOpcion> lisOpcionesDelRol(String rolId, String orden){
		
		List<ModuloOpcion> lista 	= new ArrayList<ModuloOpcion>();
		try{
			String comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, COALESCE(ICONO,' ') AS ICONO, ORDEN, USUARIOS, CARPETA, NOMBRE_INGLES"
					+ " FROM ENOC.MODULO_OPCION"
					+ " WHERE OPCION_ID IN (SELECT OPCION_ID FROM ENOC.ROL_OPCION WHERE ROL_ID = ?) "+orden;
			Object[] parametros = new Object[]{rolId};
			lista = enocJdbc.query(comando, new ModuloOpcionMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcionDao|lisOpcionesDelRol|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapCarpetas( String codigoPersonal){
		HashMap<String, String> map = new HashMap<String, String>();
		List<String> lista			= new ArrayList<String>();
		try{
			String comando = "SELECT CARPETA FROM ENOC.MODULO_OPCION WHERE OPCION_ID IN (SELECT OPCION_ID FROM ACCESO_OPCION WHERE CODIGO_PERSONAL = ?)";
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal);
			for (String carpeta : lista){				
				map.put(carpeta, carpeta);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcion|mapCarpetas|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> mapTotalOpciones(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODULO_ID AS LLAVE, COUNT(OPCION_ID) AS VALOR FROM ENOC.MODULO_OPCION GROUP BY MODULO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcion|mapTotalOpciones|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapOpcionesPorUsuario( String codigoPersonal){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODULO_ID AS LLAVE, COUNT(OPCION_ID) AS VALOR FROM ENOC.MODULO_OPCION"
					+ " WHERE OPCION_ID IN (SELECT OPCION_ID FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?)"
					+ " GROUP BY MODULO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.ModuloOpcion|mapOpcionesPorUsuario|:"+ex);
		}
		
		return mapa;
	}
*/	
}
