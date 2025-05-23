package aca.vista.spring;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuariosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public Usuarios mapeaRegId( String codigoPersonal){
		
		Usuarios usuario = new Usuarios();
		 		 		
 		try{
 			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," 
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO," 
					+ " USUARIO, CLAVE, ESTADO"
					+ " FROM ENOC.USUARIOS"
					+ " WHERE CODIGO_PERSONAL = ?"; 					
 			Object[] parametros = new Object[] {codigoPersonal}; 			
 			usuario = enocJdbc.queryForObject(comando, new UsuariosMapper(), parametros);
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|mapeaRegId|:"+ex);
 		}
 		
 		return usuario;
 	}

	public List<Usuarios> getListAll( String orden ){
		
		List<Usuarios> lista	    	= new ArrayList<Usuarios>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
					"USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+ orden;
			lista = enocJdbc.query(comando, new UsuariosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public TreeMap<String,Usuarios> getTreeAll( String orden ){
		
		TreeMap<String,Usuarios> treeUsuario	= new TreeMap<String, Usuarios>();
		List<Usuarios> lista	    	= new ArrayList<Usuarios>();
		String llave						= "";		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+ orden;
			lista = enocJdbc.query(comando, new UsuariosMapper());			
			for (Usuarios usuario: lista){
				llave = usuario.getCodigoPersonal();
				treeUsuario.put(llave, usuario);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getTreeAll|:"+ex);
		}		
		return treeUsuario;
	}
	
	public String getNombreUsuario( String codigoPersonal, String opcion){		
 		String nombre			= "x"; 		 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
 				if (opcion.equals("NOMBRE")){
 		 			 comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 		 					"WHERE CODIGO_PERSONAL = ? ";
 		 		}else if (opcion.equals("APELLIDO")){
 		 			comando = "SELECT APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.USUARIOS "+
 		 					"WHERE CODIGO_PERSONAL = ? ";
 		 		}else{
 		 			comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 		 					"WHERE CODIGO_PERSONAL = ? ";
 		 		} 		 			
 				nombre = enocJdbc.queryForObject(comando,String.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|getNombreUsuario|:"+ex);
 		} 		
 		return nombre;
 	}
	
	public String getGenero(  String codigoPersonal ){
 		
 		String codigoSe			= ""; 		
 		try{
 			String comando = "SELECT GENERO FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ?"; 
 			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				codigoSe = enocJdbc.queryForObject(comando,String.class,parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.UsuarioUtil|getGenero|:"+ex);
 		} 		
 		return codigoSe;
 	}
	
	public String getNombreUsuarioCorto( String codigoPersonal){
 		String nombre		= "X";
 		if (codigoPersonal==null) codigoPersonal = "9800000";
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
 				comando = " SELECT TRIM(SUBSTR(NOMBRE,1, (CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)  ))||' '||TRIM(APELLIDO_PATERNO) AS NOMBRE_CORTO FROM ENOC.USUARIOS"
 						+ " WHERE CODIGO_PERSONAL = ? AND ROWNUM = 1";
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|getNombreUsuarioCorto|:"+ex);
 		} 		
 		return nombre;
 	}
	
	public boolean existeReg( String codigoPersonal){ 		
		boolean existe 		= false; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
 				existe = true;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|existeReg|:"+ex);
 		}
 		return existe;
 	}
	
	public String getNombreAPaternoUsuario( String codigoPersonal){ 		
 		String nombre			= "x"; 		
 		try{ 			
 			String comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.USUARIOS "+
 					"WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			nombre = enocJdbc.queryForObject(comando, String.class, parametros); 						
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.UsuariosDao|getNombreUsuario|:"+ex);
 		} 		
 		return nombre;
 	}
	
	public boolean borrarPriv(String codigo) {
		boolean ok 	= false;		
		try{
			String comando = "UPDATE MODULO_OPCION SET USUARIOS =  REPLACE(USUARIOS,'-"+codigo+"-', '-')";
			if (enocJdbc.update(comando) >= 1){
 				ok = true;
 			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|borrarPriv|:"+ex);
		}
		return ok;
	}
	
	public List<Usuarios> getLista( String nombre, String paterno, String materno, String orden ){
		
		List<Usuarios> lista	= new ArrayList<Usuarios>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS "+
				"WHERE NOMBRE LIKE UPPER('%"+nombre+"%') "+
				"AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%') "+
				"AND ( APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') OR APELLIDO_MATERNO IS NULL) "+orden;
			lista = enocJdbc.query(comando, new UsuariosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<Usuarios> listaEnOpcion( String opcionId, String orden){		
		List<Usuarios> lista	= new ArrayList<Usuarios>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " GENERO, USUARIO, CLAVE, ESTADO "
					+ " FROM ENOC.USUARIOS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?) "+orden;
			lista = enocJdbc.query(comando, new UsuariosMapper(), opcionId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|listaEnOpcion|:"+ex);
		}		
		return lista;
	}
	
	public List<Usuarios> lisPorPrefijo(String prefijo, String orden ){
		
		List<Usuarios> lista	    	= new ArrayList<Usuarios>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, USUARIO, CLAVE, ESTADO"
					+ " FROM ENOC.USUARIOS"
					+ " WHERE CODIGO_PERSONAL LIKE '"+prefijo+"%' "+ orden;		
			lista = enocJdbc.query(comando, new UsuariosMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|lisPorPrefijo|:"+ex);
		}		
		return lista;
	}
	
	/*
	 * FILTRA A LOS ALUMNOS Y EMPLEADOS EN LA BUSQUEDA.  Valores de opcion = 'Alumno','Empleado'.
	 * */
	public List<Usuarios> getLista( String nombre, String paterno, String materno, String opcion, String orden ){
		
		List<Usuarios> lista	= new ArrayList<Usuarios>();
		String filtro 		= "";		
		try{			
			if (opcion.equals("Alumno"))
				filtro = " AND SUBSTR(CODIGO_PERSONAL,1,1) IN ('0','1','2') ";
			else
				filtro = " AND SUBSTR(CODIGO_PERSONAL,1,1) = ('9') ";
			
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
				" USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS"+
				" WHERE NOMBRE LIKE UPPER('%"+nombre+"%')"+
				" AND APELLIDO_PATERNO LIKE UPPER('%"+paterno+"%')"+
				" AND ( APELLIDO_MATERNO LIKE UPPER('%"+materno+"%') OR APELLIDO_MATERNO IS NULL)"+
				filtro+" "+orden;
			lista = enocJdbc.query(comando, new UsuariosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<Usuarios> getListAllFltro( String parametroBuscado, String opcion, String orden ){
		
		List<Usuarios> lista	    	= new ArrayList<Usuarios>();
		String filtro 							= "";		
		try{
			if (opcion.equals("Alumno"))
				filtro += " AND SUBSTR(CODIGO_PERSONAL,1,1) IN ('0','1','2') ";
			else
				filtro += " AND SUBSTR(CODIGO_PERSONAL,1,1) = ('9') ";
			
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, " +
					"USUARIO, CLAVE, ESTADO FROM ENOC.USUARIOS " +
					"WHERE (UPPER(NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO) LIKE '%'||UPPER('"+parametroBuscado+"')||'%') " +
					 filtro+" "+orden;
			lista = enocJdbc.query(comando, new UsuariosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getListAllFltro|:"+ex);
		}		
		return lista;
	}
	
	
	public HashMap<String,String> getMapAlumnosNombre( String orden ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '|| APELLIDO_PATERNO||' '|| APELLIDO_MATERNO AS VALOR " +
				" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ROT_PROGRAMACION) "+ orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|getMapAlumnosNombre|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapCandidatos( String codigos) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN ("+codigos+")";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapCandidatos|:"+ex);
		}		
		return mapa;
	}
	
	/* METODO MUY LENTO */
	public HashMap<String, String> mapaAsesores( ) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '|| APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN (SELECT CODIGO FROM SALOMON.ADM_ASESOR)";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaAsesores|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuarios() {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||COALESCE(REPLACE(APELLIDO_MATERNO, '-', ' '), '')||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaUsuarios|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuarios(String codigos) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN ("+codigos+")";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaUsuarios|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAccesoBecas( ) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM ENOC.BEC_ACCESO)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaAccesoBecas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuariosConAuto( ){
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM ENOC.VIG_AUTO)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaAccesoBecas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuariosEnComentarios( ){
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INV_COMENTARIO)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaUsuariosEnComentarios|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, String> mapaUsuariosEnBitacora( ){
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.USUARIOS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM ENOC.INV_BITACORA)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaUsuariosEnBitacora|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuariosEnResidencia( String matricula ){
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.USUARIOS \r\n"
					+ "WHERE CODIGO_PERSONAL IN (SELECT USUARIO FROM ENOC.LOG_OPERACION WHERE TABLA = 'RES_DATOS' AND DATOS LIKE '%"+matricula+"%')";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.UsuariosDao|mapaUsuariosEnResidencia|:"+ex);
		}		
		return mapa;
	}
}