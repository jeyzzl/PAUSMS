package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.catalogo.spring.CatHorarioAcceso;
import aca.catalogo.spring.CatHorarioAccesoMapper;

@Component
public class MaestrosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean existeReg( String codigoPersonal){
		boolean ok =	false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.Maestros|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public Maestros mapeaRegId( String codigoPersonal){
		String comando = "";
		Maestros maestro = new Maestros();
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO " +
					"FROM ENOC.MAESTROS "+
					"WHERE CODIGO_PERSONAL = ?";
				maestro = enocJdbc.queryForObject(comando, new MaestrosMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.Maestros|mapeaRegId|:"+ex);			
		}
		
		return maestro;
	}

	public List<Maestros> getListAll( String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					"TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS "+ orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> getListFilterByCodigoPersonal(String horarioId, String orden){
		
		List<Maestros> lista	= new ArrayList<Maestros>();
		try {
			String comando = "SELECT * FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?, '9999999')) "+orden;
			Object[] parametros = new Object[] {horarioId};
			lista = enocJdbc.query(comando, new MaestrosMapper(), parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|getListFilterByCodigoPersonal|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> lisMaestros( String orden ){
		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS " + orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> lisMaestrosActivos( String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS "
					+ " WHERE ESTADO = 'A' " + orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> getListMaestros( String orden ){
		
		List<Maestros> lista	    	= new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS" +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> lisEnExpediente( String orden ){
		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EXP_EMPLEADO) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> lisExpedientesActivos( String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EXP_EMPLEADO)"
					+ " AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EXP_TIPO WHERE ESTADO = 'A') "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListMaestros|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> lisPorEstado( String estados, String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS"
					+ " WHERE ESTADO IN ("+estados+") "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListMaestros|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> lisEnCumpleaños( String mes, String dia, String orden ){
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "
					+ " FROM ENOC.MAESTROS"
					+ " WHERE ESTADO IN('A','J') AND TO_CHAR(ENOC.MAESTROS.F_NACIMIENTO, 'MM') = ?";
			if (!dia.equals("00")) {
				comando += " AND TO_CHAR(ENOC.MAESTROS.F_NACIMIENTO, 'DD') = '"+dia+"'";
			}
			comando += " "+orden;	 
			lista = enocJdbc.query(comando, new MaestrosMapper(), mes);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisEnCumpleaños|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> listMaestrosHoras( String orden ){
		
		List<Maestros> lista	    	= new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS" +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_HORAS) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|listMaestrosHoras|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> lisDirectores( String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT  CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_FACULTAD) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisDirectores|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> lisCoordinadores( String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_CARRERA) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisDirectores|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> listMaestrosHoras( String estado, String tipos, String cargas, String orden ){		
		List<Maestros> lista	    	= new ArrayList<Maestros>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO," +
					" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL," +
					" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS" +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_HORAS WHERE ESTADO = ? AND TIPO IN ("+tipos+") AND CARGA_ID IN ("+cargas+")) "+ orden;
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new MaestrosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|listMaestrosHoras|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> ListaEnCargasyModalidades( String cargas, String modalidades, String orden ){
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN"
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|ListaEnCargasyModalidades|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> getListMaestros( String nombre, String paterno, String materno, String orden ){
		
		List<Maestros> lista	    		= new ArrayList<Maestros>();

		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
					"TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
					"TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS " +
					"WHERE UPPER(NOMBRE) LIKE UPPER('"+nombre+"%') "+
					"AND UPPER(APELLIDO_PATERNO) LIKE UPPER('%"+paterno+"%') "+
					"AND UPPER(APELLIDO_MATERNO) LIKE UPPER('%"+materno+"%')" + orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|getListMaestros|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> listConDocumentos( String orden ){
		List<Maestros> lista	    	= new ArrayList<Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_DOCEMP) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|lisDirectores|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> listSinAccesoAlPlan( String planId, String orden ){
		List<Maestros> lista	   = new ArrayList<Maestros>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM MAESTROS "
					+ " WHERE CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MAPA_FED_PLAN_ACCESO WHERE PLAN_ID = ?) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|listSinAccesoAlPlan|:"+ex);
		}		
		return lista;
	}
	
	public List<Maestros> listSinAccesoAlCurso( String cursoId, String orden ){
		List<Maestros> lista	   = new ArrayList<Maestros>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM MAESTROS "
					+ " WHERE CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MAPA_FED_CURSO_ACCESO WHERE CURSO_ID = ?) "+ orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper(), cursoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestroDao|listSinAccesoAlCurso|:"+ex);
		}		
		return lista;
	}
	
	public TreeMap<String, Maestros> getTreeAll( String orden){
		
		TreeMap<String,Maestros> treeEmp	= new TreeMap<String, Maestros>();
		List<Maestros> lista	    		= new ArrayList<Maestros>();
		String llave						= "";
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, " +
				" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, " +
				" TELEFONO, EMAIL, ESTADO FROM ENOC.MAESTROS "+ orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
			for (Maestros maestro: lista){
		
				llave = maestro.getCodigoPersonal();
				treeEmp.put(llave, maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|getTreeAll|:"+ex);
		}
		
		return treeEmp;
	}
	
	public String getNombreMaestro( String codigoPersonal, String opcion){
 		
 		String nombre			= "";
 		String comando          = "";
 		
 		try{
 			comando = "SELECT COUNT(*) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
	 			if (opcion.equals("NOMBRE")){
	 				comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
	 			}else if (opcion.equals("APELLIDO")){
	 				comando = "SELECT APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
	 			}else{
	 				comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
	 			}
	 			
	 			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
 			} 	
 			
 			
 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.MaestrosDao|getNombreMaestro|:"+ex);
 		}
 		
 		return nombre;
 	}
	
	public String getEstado( String codigoPersonal){
 		String estado	= "X"; 		 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				comando = "SELECT ESTADO FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ?";
 				estado = enocJdbc.queryForObject(comando, String.class, parametros);
 			}	 				
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.MaestrosDao|getEstado|:"+ex);
 		} 		
 		return estado;
 	}	
	
	public String getNombreCorto( String codigoPersonal, String opcion){ 		
 		String nombre			= "";
 		String comando			= ""; 		
 		try{
 			comando = "SELECT COUNT(*) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				if (opcion.equals("NOMBRE")){
 	 				comando = "SELECT SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 	 					"WHERE CODIGO_PERSONAL = ? ";
 	 			}else if (opcion.equals("APELLIDO")){
 	 				comando = "SELECT APELLIDO_PATERNO||' '||SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END) AS NOMBRE FROM ENOC.MAESTROS "+
 	 					"WHERE CODIGO_PERSONAL = ? ";
 	 			}else{
 	 				comando = "SELECT SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.MAESTROS "+
 	 					"WHERE CODIGO_PERSONAL = ? ";
 	 			}	 			
 	 			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.MaestrosDao|getNombreMaestro|:"+ex);
 		}
 		
 		return nombre;
 		
 	}
	
	public String getEquipo(String codigoPersonal){
		
 		String equipo			= "0"; 		
 		try{ 			
 			String comando = "SELECT COUNT(EQUIPO_ID) FROM POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if ( enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				comando = "SELECT COALESCE(EQUIPO_ID, 0) AS EQUIPO FROM POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
 				equipo = enocJdbc.queryForObject(comando, String.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.MaestrosDao|getEquipo|:"+ex);
 		}
 		
 		return equipo;
 	}
	
	public HashMap<String, String> mapaMaestrosAlumno( String codigoAlumno ) {
		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(MAESTRO) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestrosAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaDirectores() {
		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_FACULTAD)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
    			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaDirectores|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaDirectoresCorto() {
		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_FACULTAD)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaDirectoresCorto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCoordinadores() {
		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS VALOR"
					+ " FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_CARRERA)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaCoordinadores|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCoordinadoresCorto() {
		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS" + 
					" WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAT_CARRERA)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaCoordinadoresCorto|:"+ex);
		}
		
		return mapa;
	}
	
	public String getGenero( String codigoPersonal){
		 		
 		String genero			= "-";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				comando = "SELECT GENERO FROM MAESTROS WHERE CODIGO_PERSONAL = ?"; 			
 	 			genero = enocJdbc.queryForObject(comando,String.class,parametros);
 			}
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.vista.spring.MaestrosDao|getGenero|:"+ex);
 		}
 		
 		return genero;
	
	}
	
	public List<Maestros> getListEmp( String nombre, String paterno, String materno, String orden ){
		
		List<Maestros> lista	= new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+
				"FROM ENOC.MAESTROS "+
				"WHERE CODIGO_PERSONAL LIKE '9%' "+
				"AND NOMBRE LIKE UPPER('%"+nombre+"%') "+
				"AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
				"AND (APELLIDO_MATERNO LIKE UPPER('"+materno+"%') OR APELLIDO_MATERNO IS NULL) "+orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|getListEmp|:"+ex);
		}
		
		return lista;
	}
	
	public List<Maestros> getListMaestroEval( String cargas, String orden){
		
		List<Maestros> lista = new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO "+
				"FROM ENOC.MAESTROS "+				
				"WHERE CODIGO_PERSONAL IN " +
				"	(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+") ) "+orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|getListMaestroEval|:"+ex);
		}			
		
		return lista;
	}
	
	public List<Maestros> lisMaestrosEnCompromiso( String periodoId, String orden){
		
		List<Maestros> lista = new ArrayList<Maestros>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM DANIEL.POR_COMPROMISO WHERE PERIODO_ID = ?) "+orden; 
			lista = enocJdbc.query(comando, new MaestrosMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|lisMaestrosEnCompromiso|:"+ex);
		}		
		
		return lista;
	}
	
	public List<Maestros> lisMaestrosHorarioMusica( String cargaId, String orden){
		
		List<Maestros> lista = new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(MAESTRO_ID) FROM ENOC.MUSI_HORARIO WHERE CARGA_ID = ?) "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new MaestrosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|lisMaestrosHorarioMusica|:"+ex);
		}			
		
		return lista;
	}
	
	/* Lista de Maestros evaluados en una determinada evaluacixn docente*/
	public List<Maestros> getListMaestroEvalDocente( String edoId, String orden){
		
		List<Maestros> lista = new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"+
				" F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"+
				" FROM ENOC.MAESTROS"+				
				" WHERE CODIGO_PERSONAL IN" +
				" (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = "+edoId+")) "+orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|getListMaestroEval|:"+ex);
		}			
		
		return lista;
	}
	
	/* Lista de Maestros con documentos digitalizados*/
	public List<Maestros> listMaestroConDocumentos( String opcion, String orden){
		
		List<Maestros> lista = new ArrayList<Maestros>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL,"
					+ " TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS ";
			
					if (!opcion.equals("T")) 
						comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_DOCEMP WHERE IMAGEN IS NOT NULL) ";
					
					comando += orden;
			lista = enocJdbc.query(comando, new MaestrosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|listMaestroConDocumentos|:"+ex);
		}			
		
		return lista;
	}
	
	public HashMap<String,String> mapMaestrosHorarioMusica(String cargaId){
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(MAESTRO_ID) FROM ENOC.MUSI_HORARIO WHERE CARGA_ID = ?)";
			
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestrosHorarioMusica|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> getMaestroEdad(){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ENOC.EMP_EDAD(CODIGO_PERSONAL) AS EDAD AS VALOR FROM ENOC.MAESTROS"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|getMaestroEdad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEstadosMaestros(){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR FROM ENOC.MAESTROS"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaEstadosMaestros|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapMaestroNombre( String opcion ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||', '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||' '||APELLIDO_PATERNO||', '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||', '||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestroNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapMaestrosDeCodigo( String maestros, String opcion ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN ("+maestros+")";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestroNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroCorto( String opcion ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, TRIM(SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)) ||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||TRIM(SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)) AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, TRIM(SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END))||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroCorto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroEstado( ){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR FROM ENOC.MAESTROS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroCorto|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroEnCarga( String cargaId, String opcion ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||' '||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END)||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM CARGA_GRUPO WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroCorto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapMaestroNombre( String cargaId, String opcion ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM CARGA_GRUPO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestroNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapMaestroAnalisis( String cargas, String opcion){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM EST_CCOSTO WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+") )";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestroNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroComentaorio( String opcion){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM FIN_COMENTARIO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapMaestroNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroContrato( String opcion){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_CONTRATO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){		
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroContrato|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroEnHoras( String year, String opcion){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EMP_HORAS WHERE TO_CHAR(FECHA_INI,'YYYY') = ?)";
			Object[] parametros = new Object[] {year}; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroContrato|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestrosEnMateria( String cursoId, String opcion){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	 = "";		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_ACADEMICA WHERE CURSO_ID = ?)";
			Object[] parametros = new Object[] {cursoId}; 
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestrosEnMateria|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaestroEnBase( String opcion){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();				
		try{
			String comando = "";
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) ||','|| APELLIDO_PATERNO AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.HCA_MAESTRO)";			 
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestroEnBase|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEmpleadosReferentes( String opcion){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ACCESO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaEmpleadosReferentes|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaEnEdificios( String opcion){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";		
		try{
			if (opcion.equals("NOMBRE")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS VALOR FROM ENOC.MAESTROS";
			}else if (opcion.equals("APELLIDOS")){
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS VALOR FROM ENOC.MAESTROS";
			}else {
				comando = " SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||','||SUBSTR(NOMBRE,1,CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ') END) AS VALOR"
						+ " FROM ENOC.MAESTROS";
			}
			comando += " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CAT_EDIFICIO_USUARIO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaEnEdificios|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAccesoBecas( ) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM BEC_ACCESO)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaAccesoBecas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Maestros> mapaMaestros() {
		List<Maestros> lista 				= new ArrayList<Maestros>();		
		HashMap<String, Maestros> mapa		= new HashMap<String,Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS";			
			lista = enocJdbc.query(comando, new MaestrosMapper());
			for (Maestros map : lista) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestros|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Maestros> mapaMaestrosEnOpcion(String opcionId){
		List<Maestros> lista 				= new ArrayList<Maestros>();		
		HashMap<String, Maestros> mapa		= new HashMap<String,Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";			
			lista = enocJdbc.query(comando, new MaestrosMapper(), opcionId);
			for (Maestros map : lista) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestrosEnOpcion|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Maestros> mapaMaestrosEnNotas(String codigoAlumno){
		List<Maestros> lista 				= new ArrayList<Maestros>();		
		HashMap<String, Maestros> mapa		= new HashMap<String,Maestros>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, GENERO, ESTADOCIVIL, TELEFONO, EMAIL, ESTADO"
					+ " FROM ENOC.MAESTROS"
					+ " WHERE CODIGO_PERSONAL IN (SELECT GRUPO_MAESTRO(CURSO_CARGA_ID) FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?)";			
			lista = enocJdbc.query(comando, new MaestrosMapper(), codigoAlumno);
			for (Maestros map : lista) {
				mapa.put(map.getCodigoPersonal(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaMaestrosEnNotas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaUsuariosMaterias( String cargaId) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ "	FROM ENOC.MAESTROS WHERE CODIGO_PERSONAL IN (SELECT USUARIO FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.MaestrosDao|mapaUsuariosMaterias|:"+ex);
		}
		
		return mapa;
	}	
}