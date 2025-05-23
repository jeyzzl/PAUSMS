// Clase para la vista ALUMNO
package  aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class AlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public Alumno mapeaRegId(String codigoPersonal) {
		
		Alumno objeto = new Alumno();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
						"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
						"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID,CORREO, TELEFONO "+
						"FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = ? ";			
				objeto = enocJdbc.queryForObject(comando, new AlumnoMapper(), parametros);				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|mapeaRegId|:"+ex);			
		}
		return objeto;
	}
	
	public List<Alumno> getListAll( String orden ){
			
		List<Alumno> lista	= new ArrayList<Alumno>();
	
		try{
			String comando = "SELECT "+
			"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+ orden;
			lista = enocJdbc.query(comando, new AlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getListAll|:"+ex);
		
		}
		
		return lista;
	}
	
	public List<Alumno> getLista( String nombre, String paterno, String materno, String orden ){
		
		List<Alumno> lista	= new ArrayList<Alumno>();
		
		try{
			String comando = "SELECT "+
			"CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+
			"WHERE NOMBRE LIKE UPPER('"+nombre+"%') "+
			"AND APELLIDO_PATERNO LIKE UPPER('"+paterno+"%') "+
			"AND APELLIDO_MATERNO LIKE UPPER('"+materno+"%') "+ orden;
			lista = enocJdbc.query(comando, new AlumnoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<Alumno> getListCumple( String mes, String dia, String orden ){
		
		List<Alumno> lista	= new ArrayList<Alumno>();
		
		try{
			
			String comando = "SELECT "+
			"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,PLAN_ID,CARRERA_ID "+
			"FROM ENOC.ALUMNO "+
				"WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= ? ";					
			if (!dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '"+dia+"' ";		
				lista = enocJdbc.query(comando, new AlumnoMapper(),mes);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getListCumple|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, Alumno> getMapaAll( String condiciones){
		HashMap<String, Alumno> mapa	= new HashMap<String, Alumno>();
		List<Alumno> lista	= new ArrayList<Alumno>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUMNO "+condiciones;
			lista = enocJdbc.query(comando, new AlumnoMapper());
			for(Alumno alumno : lista){				
				mapa.put(alumno.getCodigoPersonal(), alumno);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getMapaAll|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, Alumno> getMapaAlumnosEnlinea( String condiciones){
		HashMap<String, Alumno> mapa	= new HashMap<String, Alumno>();
		List<Alumno> lista	= new ArrayList<Alumno>();
		
		try{
			
			String comando = "SELECT B.CODIGO_PERSONAL, B.NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.NOMBRE_LEGAL, B.COTEJADO,"+
					" TO_CHAR(B.F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
					" B.SEXO, B.ESTADO_CIVIL, B.RELIGION_ID, B.PAIS_ID, B.ESTADO_ID, B.CIUDAD_ID, B.NACIONALIDAD,"+
					" B.CURP, B.MODALIDAD_ID, B.CLAS_FIN, B.RESIDENCIA_ID, B.PLAN_ID, B.CARRERA_ID "+
					" FROM ENOC.ALUM_ENLINEA A INNER JOIN ENOC.ALUMNO B" +
					" ON A.CODIGO_PERSONAL=B.CODIGO_PERSONAL "+condiciones;
			lista = enocJdbc.query(comando, new AlumnoMapper());
			for(Alumno alumno : lista){				
				mapa.put(alumno.getCodigoPersonal(), alumno);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getMapaAlumnosEnlinea|:"+ex);
		}
		return mapa;
	}
	
	public String getNombre( String codigoPersonal, String orden){
		
		String nombre		= "x";
		String comando      = "";
		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				if ( orden.equals("NOMBRE")){
					comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "+
						" FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
				}else{
					comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
						" FROM ENOC.ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
				}	
				nombre = enocJdbc.queryForObject(comando,String.class,codigoPersonal); ///
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|getNombre|:"+ex);		
		}
		
		return nombre;
	}	
	
	public HashMap<String, String> mapaNombreAlumno() {
		
		HashMap<String, String> map			= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ALUM_PERSONAL";
			
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa edad : list ) {
				map.put(edad.getLlave(), edad.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoDao|mapaNombreAlumno|:"+ex);
		}		
		return map;
	}
}