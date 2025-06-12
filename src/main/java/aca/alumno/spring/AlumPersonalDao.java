// Clase para la tabla de AlumPersonal
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.util.Fecha;


@Component
public class AlumPersonalDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumPersonal alumno ) {
		
 		boolean ok = false;
 		try{ 			
 			String comando = "INSERT INTO ENOC.ALUM_PERSONAL"+ 
 				" (CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"+
 				" F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"+
 				" NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"+
				" RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC)"+
 				" VALUES( ?, ?, ?, ?, ?, "+
 				" TO_DATE(?,'DD/MM/YYYY'), "+
 				" ?, "+
 				" ?, "+
 				" TO_NUMBER(COALESCE(?, '1'),'99'), "+
 				" ?, "+
 				" TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				" TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				" TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				" TO_NUMBER(COALESCE(?, '1'),'999'), "+
 				" ?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), "+
 				" TO_NUMBER(?,'999'),"+
 				" TO_NUMBER(?,'999'),"+
 				" TO_NUMBER(COALESCE(?, '153'),'999'),"+
 				" TO_NUMBER(COALESCE(?, '1'),'999'),"+
 				" TO_NUMBER(COALESCE(?, '1'),'999'),"+
				" ?)";
 			Object[] parametros = new Object[] {
 					alumno.getCodigoPersonal(),alumno.getNombre(),alumno.getApellidoPaterno(),alumno.getApellidoMaterno(),
 		 			alumno.getNombreLegal(),alumno.getFNacimiento(),alumno.getSexo(),alumno.getEstadoCivil(),alumno.getReligionId(),
 		 			alumno.getBautizado(),alumno.getPaisId(),alumno.getEstadoId(),alumno.getCiudadId(),alumno.getNacionalidad(),
 		 			alumno.getEmail(),alumno.getCurp(),alumno.getEstado(),alumno.getCotejado(),alumno.getCodigoSe(),
 		 			alumno.getFcreado(),alumno.getUsAlta(),alumno.getTelefono(), alumno.getNum_oferta(), alumno.getfBautizado(), alumno.getCulturalId(),
 		 			alumno.getRegionId(), alumno.getResPaisId(), alumno.getResEstadoId(), alumno.getResCiudadId(), alumno.getSync()
 		 	};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|insertReg|:"+ex);	
 		}
 		return ok;
 	}	
 	
 	public boolean updateReg( AlumPersonal alumno ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_PERSONAL "+ 
 				" SET "+				
 				" NOMBRE = ?, "+
 				" APELLIDO_PATERNO = ?, "+
 				" APELLIDO_MATERNO = ?, "+
 				" NOMBRE_LEGAL = ?, "+
 				" F_NACIMIENTO = TO_DATE(?,'DD/MM/YYYY'), "+
 				" SEXO = ?, "+
 				" ESTADO_CIVIL = ?, "+
 				" RELIGION_ID = TO_NUMBER(COALESCE(?, '1'),'99'), "+
 				" BAUTIZADO = ?, "+
 				" PAIS_ID = TO_NUMBER(?,'999'), "+
 				" ESTADO_ID = TO_NUMBER(COALESCE(?, '0'),'999'), "+
 				" CIUDAD_ID = TO_NUMBER(COALESCE(?, '0'),'999'), "+
 				" NACIONALIDAD = TO_NUMBER(COALESCE(?, '0'),'999'), " +
 				" EMAIL = ?, "+
 				" CURP = ?, "+
 				" ESTADO = ?, "+
 				" COTEJADO = ?, " +
 				" CODIGO_SE = ?, " + 	
 				" F_CREADO = TO_DATE(?,'DD/MM/YYYY'), "+
 				" US_ALTA = ?, " +
 				" TELEFONO = ?, " +
 				" DIRECCION = ?, " +
 				" F_BAUTIZADO = TO_DATE(?,'DD/MM/YYYY'), " +
 				" CULTURAL_ID = TO_NUMBER(COALESCE(?, '0'),'999'), " +
 				" REGION_ID = TO_NUMBER(COALESCE(?, '0'),'999'),"+
 				" RES_PAIS_ID = TO_NUMBER(COALESCE(?, '1'),'999'),"+
 				" RES_ESTADO_ID = TO_NUMBER(COALESCE(?, '1'),'999'),"+
 				" RES_CIUDAD_ID = TO_NUMBER(COALESCE(?, '1'),'999'),"+
				" SYNC = ?"+
 				" WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {
 					alumno.getNombre(),alumno.getApellidoPaterno(),alumno.getApellidoMaterno(),alumno.getNombreLegal(),
 		 			alumno.getFNacimiento(),alumno.getSexo(),alumno.getEstadoCivil(),alumno.getReligionId(),
 		 			alumno.getBautizado(),alumno.getPaisId(),alumno.getEstadoId(),alumno.getCiudadId(),
 		 			alumno.getNacionalidad(),alumno.getEmail(),alumno.getCurp(),alumno.getEstado(),
 		 			alumno.getCotejado(),alumno.getCodigoSe(), alumno.getFcreado(), alumno.getUsAlta(),alumno.getTelefono(),
 		 			alumno.getDireccion(),alumno.getfBautizado(), alumno.getCulturalId(), alumno.getRegionId(), alumno.getResPaisId(),
					alumno.getResEstadoId(), alumno.getResCiudadId(), alumno.getSync(), alumno.getCodigoPersonal()	
 		 	};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateReg|:"+ex);		
 		} 		
 		return ok;
 	} 	
 	
 	public boolean updateNacionalidad( String codigoPersonal, String nacionalidad ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PERSONAL SET NACIONALIDAD = ? WHERE CODIGO_PERSONAL = ? ";					
			if (enocJdbc.update(comando, nacionalidad, codigoPersonal)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateNacionalidad|:"+ex);
		}
		return ok;
	}
 	
 	public boolean updateCodigoSe( String codigoPersonal, String codigo ) {
		boolean ok = false;
			try{
			String comando = "UPDATE ENOC.ALUM_PERSONAL SET CODIGO_SE = ? WHERE CODIGO_PERSONAL = ? ";
			if (enocJdbc.update(comando, codigo, codigoPersonal)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateCodigoSe|:"+ex);	 
		}
		return ok;
	}
 	
 	public boolean updateCredencial( String codigoPersonal, String codigo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PERSONAL SET CREDENCIAL = ? WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.update(comando, codigo, codigoPersonal)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateCredencial|:"+ex);
		}
		return ok;
	}

	public boolean updateSync( String codigoPersonal, String sync ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PERSONAL SET SYNC = ? WHERE CODIGO_PERSONAL = ? ";					
			if (enocJdbc.update(comando, sync, codigoPersonal)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateSync|:"+ex);
		}
		return ok;
	}
 	
 	public String getCodigoDecimal( String codigoPersonal, String exadecimal) {
 		
 		String codigo			= "-";
 		
 		try{
 			String comando = "SELECT TO_CHAR(TO_NUMBER(SUBSTR('"+exadecimal+"',5,6),'XXXXXXXXXX'),'0000000000') AS RFID FROM DUAL";						
 			codigo =  String.valueOf(enocJdbc.queryForObject(comando, String.class)); 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCodigoDecimal|:"+ex);
 		}
 		return codigo;
 	}
 	
 	public boolean updateCotejado( String codigoPersonal, String cotejado) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.ALUM_PERSONAL SET COTEJADO = ? WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {cotejado, codigoPersonal};
 			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}	
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|updateCotejado|:"+ex);		
 		}
 		return ok;
 	}
 	
 	public boolean deleteReg( String codigoPersonal ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando, parametros)==1){
 				ok = true;
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	} 	
 	
 	public AlumPersonal mapeaRegId(  String codigoPersonal ){
 		
 		AlumPersonal alumno 	= new AlumPersonal(); 		
 		try{
	 		String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
	 		Object[] parametros = new Object[] {codigoPersonal};
	 		if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
	 			comando = " SELECT"
	 					+ " CODIGO_PERSONAL,"
	 					+ " NOMBRE,"
	 					+ " APELLIDO_PATERNO,"
	 					+ " APELLIDO_MATERNO,"
	 					+ " NOMBRE_LEGAL,"
	 					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
	 					+ " COALESCE(SEXO, 'M') SEXO,"
	 					+ " COALESCE(ESTADO_CIVIL, 'S') ESTADO_CIVIL,"
	 					+ " COALESCE(RELIGION_ID, 1) RELIGION_ID,"
	 					+ " COALESCE(BAUTIZADO,'S') BAUTIZADO,"
	 					+ " PAIS_ID,"
	 					+ " ESTADO_ID,"
	 					+ " CIUDAD_ID,"
	 					+ " NACIONALIDAD,"
	 					+ " EMAIL,"
	 					+ " CURP,"
	 					+ " COALESCE(ESTADO,'A') ESTADO,"
	 					+ " COALESCE(COTEJADO,'N') COTEJADO,"
	 					+ " CODIGO_SE,"
	 					+ " F_CREADO,"
	 					+ " US_ALTA,"
	 					+ " TELEFONO,"
	 					+ " DIRECCION,"
	 					+ " CREDENCIAL,"
	 					+ " NUM_OFERTA,"
	 					+ " TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') F_BAUTIZADO,"
	 					+ " CULTURAL_ID,"
	 					+ " REGION_ID,"
						+ "	RES_PAIS_ID,"
						+ " RES_ESTADO_ID,"
						+ " RES_CIUDAD_ID,"
						+ "	SYNC"
	 					+ " FROM ENOC.ALUM_PERSONAL"
	 					+ " WHERE CODIGO_PERSONAL = ?";
	 			alumno = enocJdbc.queryForObject(comando, new AlumPersonalMapper(), parametros);
	 		}	 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapeaRegId|:"+ex);
 		} 		
 		return alumno;
 	}

 	public boolean existeReg( String codigoPersonal) {
 		boolean 		ok 	= false;
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|existeReg|:"+ex);
 		}
 		return ok;
 	}
 	
 	public boolean existeAlumno( String codigoPersonal) {
 		boolean 		ok 	= false; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|existeAlumno|:"+ex);
 		} 		
 		return ok;
 	}
 	
 	public boolean existeCodigoCredencial( String codigoCredencial) {
 		boolean 		ok 	= false; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CREDENCIAL = ?";
 			Object[] parametros = new Object[] {codigoCredencial};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|existeCodigoCredencial|:"+ex);
 		} 		
 		return ok;
 	}
 	
 	public String getCodigoCredencial( String codigoCredencial) {
 		String codigoPersonal 	= "0"; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PERSONAL WHERE CREDENCIAL = ?";
 			Object[] parametros = new Object[] {codigoCredencial};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE CREDENCIAL = ?";
 				codigoPersonal = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|existeReg|:"+ex);
 		}
 		return codigoPersonal;
 	}
 	
 	public boolean getEsHermano( String codigoPersonal, String apellidos) {
 		boolean 		ok 	= false;
 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? AND TRIM(APELLIDO_PATERNO)||' '||TRIM(APELLIDO_MATERNO) = ?";
 			Object[] parametros = new Object[] {codigoPersonal, apellidos};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getEsHermano|:"+ex);
 		}
 		return ok;
 	}
 	
 	public String maximoReg( String Tipo) {
 		
 		String maximo			= "";
 		String anio				= Fecha.getHoy().substring(8,10);
 		String comando 			= "";
 		
 		try{
 			if (Tipo.equals("PAU") || Tipo.equals("FAC") ){
 				comando = "SELECT COALESCE(MAX(CODIGO_PERSONAL)+1, 1"+anio+"0001) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,3) = '1"+anio+"'"; 				
 			}else if (Tipo.equals("SONOMA")){
				comando = "SELECT COALESCE(MAX(CODIGO_PERSONAL)+1, 1"+anio+"5001) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
					"WHERE SUBSTR(CODIGO_PERSONAL,1,4) = '1"+anio+"5'"; 				
			}else if (Tipo.equals("Empleado")){
 				comando = "SELECT MAX(CODIGO_PERSONAL)+1 AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,1) = '9' ";
 			}else if(Tipo.equals("Tabasco")){
 				comando = "SELECT COALESCE(MAX(CODIGO_PERSONAL)+1, 2"+anio+"0001) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 	 					"WHERE SUBSTR(CODIGO_PERSONAL,1,3) = '2"+anio+"'";
 			}else{	
 				comando = "SELECT (MAX(CODIGO_PERSONAL)+1) AS MAXIMO FROM ENOC.ALUM_PERSONAL "+ 
 					"WHERE SUBSTR(CODIGO_PERSONAL,1,1) = 'P' ";
 			}			
 			maximo = enocJdbc.queryForObject(comando, String.class);
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|maximoReg|:"+ex);
 		}
 		return maximo;
 	}
 	
 	public boolean esInscrito(  String codigoPersonal ) { 		
 		boolean ok 				= false;
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? "; 			
 			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|esInscrito|:"+ex);
 		}
 		return ok;
 	}	
 	
 	public int numComidas( String codigoPersonal, String cargaId) { 	
 		int tot					= 0; 		
 		try{
 			String comando = "SELECT COUNT(*) FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?";
 			Object[] parametros = new Object[] {codigoPersonal, cargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT COALESCE(NUM_COMIDAS,0) AS TOT FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?";
 				tot 	= enocJdbc.queryForObject(comando, Integer.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|numComidas|:"+ex);
 		}
 		return tot;
 	}
 	
 	public int getEdad( String codigoPersonal) {
 		
 		int tot	= 0; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12 FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 	 			
 	 			tot = enocJdbc.queryForObject(comando,Integer.class, parametros);
 			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getEdad|:"+ex);
 		}
 		return tot;
 	}
 	
 	public String getFNacimiento( String codigoPersonal) {
 		
 		String fecha 			= ""; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ";
 				fecha = enocJdbc.queryForObject(comando, String.class, parametros);
 			} 						
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getFNacimiento|:"+ex);
 		}
 		return fecha;
 	}
 	
 	public String tipoComidas( String codigoPersonal, String cargaId) {
 		String comidas="";		
 		try{
 			String comando = "SELECT COUNT(*) FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?"; 			
 			Object[] parametros = new Object[] {codigoPersonal,cargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				comando = "SELECT TIPO_COMIDA FROM NOE.COM_AUTORIZACION WHERE MATRICULA = ? AND CARGA_ID = ?";
 				comidas = enocJdbc.queryForObject(comando, String.class, parametros);
 			}				
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|tipoComidas|:"+ex);
 		}
 		return comidas;
 	}
 	
 	public String getNombreAlumno( String codigoPersonal, String opcion) {
  		String nombre			= "-";
 		String comando			= "";
  		
 		try{
 			comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1) {
				if (opcion.equals("NOMBRE")){
	 				comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}else if (opcion.equals("APELLIDO")){
	 				comando = "SELECT APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}else{
	 				comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}	 			
	 			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNombreAlumno|:"+ex);
 		}
 		return nombre;
 	}
 	
 	public String getAlumno( String codigoPersonal, String opcion) {
 		String nombre	= "";
 		String comando 	= "";
 		
 		try{
 			comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1) {
				if (opcion.equals("NOMBRE")){
	 				comando = "SELECT NOMBRE AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}else if (opcion.equals("APELLIDOS")){
	 				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}else{
	 				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE FROM ENOC.ALUM_PERSONAL "+ 
	 					"WHERE CODIGO_PERSONAL = ? ";
	 			}	 			
	 			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getAlumno|:"+ex);
 		}
 		return nombre;
 	}
 	
 	public boolean validarCurp(String curp){
 		if (curp==null) curp = "-";
 		curp = curp.toUpperCase().trim();
 		return curp.matches("[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z,0-9]{2}");
	}
 	
 	public String getFechaIngreso( String codigoPersonal, String planId) {
 		
 		String fecha	 		= "01/01/1900";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = " SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS FECHA FROM ENOC.ALUM_PLAN"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PLAN_ID = ?";			
				fecha = enocJdbc.queryForObject(comando, String.class, parametros);				
			}					 	
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getFechaIngreso|:"+ex);
 		}
 		return fecha;
 	}
 	
 	public String getNombreCorto( String codigoPersonal) {

 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT NOMBRE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
				
				comando = "SELECT APELLIDO_PATERNO||' '||COALESCE(APELLIDO_MATERNO,'.') AS APELLIDOS FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
				apellido = enocJdbc.queryForObject(comando, String.class, parametros);
	 			
	 			if(!nombre.equals("") && !apellido.equals("")){
	 				StringTokenizer token = new StringTokenizer(nombre," ");
	 				temp = token.nextToken();
	 				
	 				if(!temp.equals("")){
	 					nombre = temp;
	 					if(token.countTokens() >= 1){
	 						temp = token.nextToken();
	 						nombre += " " + temp.charAt(0) + ".";
	 					}
	 				}
	 				nombre += " " + apellido;
	 			}				
			}
 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.spring.AlumPersonalDao|getNombreCorto|: "+ex);
 		}
 		return nombre;
 	}
 	
 	public String getNombreMuyCorto( String codigoPersonal) {

 		String nombre	 		= "";
 		String apellido			= "";
 		String temp				= "";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT NOMBRE, APELLIDO_PATERNO, COALESCE(APELLIDO_MATERNO,'.') AS MATERNO"
	 					+ " FROM ENOC.ALUM_PERSONAL"
	 					+ " WHERE CODIGO_PERSONAL = ?"; 			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
				
				comando = " SELECT APELLIDO_PATERNO||' '||COALESCE(APELLIDO_MATERNO,'.') AS APELLIDOS"
	 					+ " FROM ENOC.ALUM_PERSONAL"
	 					+ " WHERE CODIGO_PERSONAL = ?";
				apellido = enocJdbc.queryForObject(comando, String.class, parametros);
				
	 			if(!nombre.equals("") && !apellido.equals("")){
	 				StringTokenizer token = new StringTokenizer(nombre," ");
	 				temp = token.nextToken();
	 				
	 				if(!temp.equals("")){
	 					nombre = temp;
	 					if(token.countTokens() >= 1){
	 						temp = token.nextToken();
	 						nombre += " " + temp.charAt(0) + ".";
	 					}
	 				}
	 				nombre += " " + apellido;
	 			}			
			}
 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.spring.AlumPersonalDao|getNombreMuyCorto|: "+ex);
 		}
 		return nombre;
 	}
 	
 	public String getCotejado( String codigoPersonal) {

 		String cotejado	 		= ""; 		
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT COALESCE(COTEJADO,'N') AS COTEJADO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 			
	 			cotejado = enocJdbc.queryForObject(comando, String.class, parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.spring.AlumPersonalDao|getCotejado|: "+ex);
 		}
 		return cotejado;
 	}
 	
 	public String getCurp( String codigoPersonal) {

 		String curp	 			= ""; 		
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT COALESCE(CURP,'-') AS CURP FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 			
				curp = enocJdbc.queryForObject(comando, String.class, parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error  - aca.alumno.spring.AlumPersonalDao|getCurp|: "+ex);
 		}
 		return curp;
 	}
 	
 	public String getPaisId( String codigoPersonal) {

 		String paisId			= "0";		
 		try{ 			
 			String comando = "SELECT COALESCE(PAIS_ID,0) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 			
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				comando = "SELECT COALESCE(PAIS_ID,0) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 				paisId = enocJdbc.queryForObject(comando, String.class, parametros);
 			}					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getPaisId|:"+ex);
 		}
 		return paisId;
 	}
 	
 	public String getEstadoId( String codigoPersonal) {

 		String estado			= "0";
 		
 		try{
 			String comando = "SELECT COALESCE(ESTADO_ID,0) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 			
 			Object[] parametros = new Object[] {codigoPersonal};
			estado = enocJdbc.queryForObject(comando, String.class, parametros);
					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getEstadoId|:"+ex);
 		}
 		return estado;
 	}
 	
 	public String getNacionalidad( String codigoPersonal) {

 		String paisId			= "153";
 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT COALESCE(NACIONALIDAD,153) AS NACIONALIDAD FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";			
				paisId = enocJdbc.queryForObject(comando, String.class, parametros);
			}				 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNacionalidad|:"+ex);
 		}
 		return paisId;
 	}

 	public String getPromedioFinal( String matricula, String planId) {

 		String promedio			= "X";
 		
 		try{
 			String comando = "SELECT ALUM_PROMEDIO(CODIGO_PERSONAL, ?) AS PROMEDIO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {planId,matricula};
			promedio = enocJdbc.queryForObject(comando, String.class, parametros);
					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getPromedioFinal|:"+ex);
 		}
 		return promedio;
 	}	

 	public String getCargasAlumno( String fecha, String codigoPersonal ) {
 		String cargas 			= "X";
		int row 				= 1;
		List<String> lista 		= new ArrayList<String>();
		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA"
					+ " WHERE TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL"
					+ " AND CARGA_ID IN "
					+ " (SELECT CARGA_ID FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ? AND ESTADO='I')"
					+ " ORDER BY CARGA_ID";
			Object[] parametros = new Object[] {fecha,codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String carga : lista){ 
				if (cargas.equals("X")) cargas="";
				if (row>1) cargas += ",";
				cargas += "'"+carga+"'";
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCargasAlumno|:"+ex);
		}
		return cargas;
	} 	
 	
 	public String getAronPais( String codigoPersonal ) {
 		String rfc 				= "X";
		try{
			String comando = "SELECT NOMBRE FROM ARON.PAIS "
					+ "WHERE ID = (SELECT ID_PAIS FROM ARON.ESTADO "
					+ "WHERE ID = (SELECT C.ID_ESTADO FROM ARON.CIUDAD C "
					+ "WHERE ID = (SELECT ID_CIUDAD FROM ARON.EMPLEADOPERSONALES "
					+ "WHERE ID = (SELECT ID FROM ARON.EMPLEADO WHERE CLAVE = ?))))";
			
			Object[] parametros = new Object[] {codigoPersonal};
			rfc = enocJdbc.queryForObject(comando, String.class, parametros);
							
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getRFC|:"+ex);
		}
		return rfc;
	}
 	
 	public String codigoSe(  String codigoPersonal ) {
 		
 		String codigoSe			= "";
 		
 		try{
 			String comando = "SELECT CODIGO_SE FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ";
 			
 			Object[] parametros = new Object[] {codigoPersonal};
			codigoSe = enocJdbc.queryForObject(comando, String.class, parametros);
					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|codigoSe|:"+ex);
 		}
 		return codigoSe;
 	}
 	
 	public String getGenero(  String codigoPersonal ) {
 		
 		String codigoSe			= "";
 		
 		try{
 			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ) {
				comando = "SELECT SEXO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "; 			
				codigoSe = enocJdbc.queryForObject(comando, String.class, parametros);
			}
								
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getGenero|:"+ex);
 		}
 		return codigoSe;
 	}
 	
 	public String getReligion(  String codigoPersonal ) {
 		
 		String codigoSe			= ""; 		
 		try{
 			String comando = "SELECT RELIGION_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "; 			
 			Object[] parametros = new Object[] {codigoPersonal};
			codigoSe = enocJdbc.queryForObject(comando, String.class, parametros);					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getReligion|:"+ex);
 		}
 		return codigoSe;
 	}
 	
 	public String getCultural(  String codigoPersonal ) {
 		
 		String cultural			= ""; 		
 		try{
 			String comando = "SELECT CULTURAL_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "; 			
 			Object[] parametros = new Object[] {codigoPersonal};
			cultural = enocJdbc.queryForObject(comando, String.class, parametros);					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCultural|:"+ex);
 		}
 		return cultural;
 	}
 	
 	public String getRegion(  String codigoPersonal ) {
 		
 		String cultural			= ""; 		
 		try{
 			String comando = "SELECT REGION_ID FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? "; 			
 			Object[] parametros = new Object[] {codigoPersonal};
			cultural = enocJdbc.queryForObject(comando, String.class, parametros);					
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getRegion|:"+ex);
 		}
 		return cultural;
 	}
 	
 	public String esColportor(  String codigoPersonal ) {
 		
 		String tipo				= "A"; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? ";
 			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 1){
 				comando = "SELECT COALESCE(DISCRIMINATOR_COL,'A') AS COLPORTOR FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 				tipo = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
 			}				
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|esColportor|:"+ex);
 		}
 		return tipo;
 	}
 	
 	public static boolean esVocal(char c){
		return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' ||
				c == 'Á' || c == 'É' || c == 'Í' || c == 'Ó' || c == 'Ú';
    }
 	
 	public static String validaDatosDeCurp( String curp, String nombre, String paterno, String materno, String fecha, String genero, String paisId, String estadoId ) {
 		
 		String resultado	= "";
 		String curpDeDatos 	= ""; 
 		
 		try{
 			// Reemplazar caracteres especiales
 			paterno.replace("Ñ", "X").replace("-", "X").replace(".", "X").replace("/","X").replace("Ü","U");
 			paterno.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			
 			// QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String[] arrayPaterno = paterno.split(" ");
 			if (arrayPaterno.length > 1){
 				paterno = "";
 				for (int j = 0; j < arrayPaterno.length;j++){
 					if (!arrayPaterno[j].equals("DA")&&!arrayPaterno[j].equals("DAS")&&!arrayPaterno[j].equals("DE")&&!arrayPaterno[j].equals("DEL")&&
 						!arrayPaterno[j].equals("DER")&&!arrayPaterno[j].equals("DI")&&!arrayPaterno[j].equals("DIE")&&!arrayPaterno[j].equals("DD")&&
 						!arrayPaterno[j].equals("EL")&&!arrayPaterno[j].equals("LA")&&!arrayPaterno[j].equals("LOS")&&!arrayPaterno[j].equals("LAS")&&
 						!arrayPaterno[j].equals("LE")&&!arrayPaterno[j].equals("LES")&&!arrayPaterno[j].equals("MAC")&&!arrayPaterno[j].equals("MC")&&
 						!arrayPaterno[j].equals("VAN")&&!arrayPaterno[j].equals("VON")&&!arrayPaterno[j].equals("Y")
 						){
 						paterno += arrayPaterno[j];
 					}
 				}
 			}
 			
 			if (paterno.length() > 1){
 				// Primer caracter
 				curpDeDatos += paterno.substring(0,1);
 				// Segundo caracter (vocal interna)
 				for(int i=1; i <= paterno.length()-1; i++){
 	                if( esVocal(paterno.charAt(i)) ){ 
 	                    curpDeDatos += paterno.charAt(i);
 	                    break;
 	                }
 	            }
 				if (curpDeDatos.length()==1){
 					curpDeDatos += "X";
 				}
 			}
 			
 			materno.replace("Ñ", "X").replace("-", "X").replace(".", "X").replace("/","X").replace("Ü","U");
 			materno.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			materno = materno.trim();
 			
 			// QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String[] arrayMaterno = materno.split(" ");
 			if (arrayMaterno.length > 1){
 				materno = "";
 				for (int j = 0; j < arrayMaterno.length;j++){
 					if (!arrayMaterno[j].equals("DA")&&!arrayMaterno[j].equals("DAS")&&!arrayMaterno[j].equals("DE")&&!arrayMaterno[j].equals("DEL")&&
 						!arrayMaterno[j].equals("DER")&&!arrayMaterno[j].equals("DI")&&!arrayMaterno[j].equals("DIE")&&!arrayMaterno[j].equals("DD")&&
 						!arrayMaterno[j].equals("EL")&&!arrayMaterno[j].equals("LA")&&!arrayMaterno[j].equals("LOS")&&!arrayMaterno[j].equals("LAS")&&
 						!arrayMaterno[j].equals("LE")&&!arrayMaterno[j].equals("LES")&&!arrayMaterno[j].equals("MAC")&&!arrayMaterno[j].equals("MC")&&
 						!arrayMaterno[j].equals("VAN")&&!arrayMaterno[j].equals("VON")&&!arrayMaterno[j].equals("Y")
 						){
 						materno += arrayMaterno[j];
 					}
 				}
 			}			
 			
 			// Tercer caracter
 			if (materno.length() > 1){
 				curpDeDatos += materno.substring(0,1);
 			}else{ 				
 				curpDeDatos += "X";
 				materno = "XXXX";
 			}
 			
 			nombre.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U"); 			
 			
 			//QUITAR PALABRAS DE EXEPCION(DA,DAS,DE,DEL,DER,DI,DIE,DD,EL,LA,LOS,LAS,LE,LES,MAC,MC,VAN,VON,Y)
 			String[] arrayNombre = nombre.split(" ");
 			if (arrayNombre.length > 1){
 				nombre = "";
 				for (int j = 0; j < arrayNombre.length;j++){
 					if (!arrayNombre[j].equals("DA")&&!arrayNombre[j].equals("DAS")&&!arrayNombre[j].equals("DE")&&!arrayNombre[j].equals("DEL")&&
 						!arrayNombre[j].equals("DER")&&!arrayNombre[j].equals("DI")&&!arrayNombre[j].equals("DIE")&&!arrayNombre[j].equals("DD")&&
 						!arrayNombre[j].equals("EL")&&!arrayNombre[j].equals("LA")&&!arrayNombre[j].equals("LOS")&&!arrayNombre[j].equals("LAS")&&
 						!arrayNombre[j].equals("LE")&&!arrayNombre[j].equals("LES")&&!arrayNombre[j].equals("MAC")&&!arrayNombre[j].equals("MC")&&
 						!arrayNombre[j].equals("VAN")&&!arrayNombre[j].equals("VON")&&!arrayNombre[j].equals("Y")
 						){
 						nombre += arrayNombre[j]+" ";
 					} 
 				}
 			}
 			
 			nombre = nombre.trim();		 		
 			arrayNombre = nombre.split(" ");
 			if (arrayNombre.length > 1){
 				int pos = nombre.indexOf(" ");
 				if (arrayNombre[0].equals("JOSE") || arrayNombre[0].equals("MARIA")||
 		 			arrayNombre[0].equals("MA") || arrayNombre[0].equals("MA.")||
 		 			arrayNombre[0].equals("J") || arrayNombre[0].equals("J.")
 		 		){						
 		 			nombre = nombre.substring(pos).trim();
 				}				 									
 			}
 			
 			// Cuarto caracter
 			curpDeDatos += nombre.substring(0,1);
 			
 			// Agregar caracteres 5,6 
 			curpDeDatos += fecha.substring(8,10);
 			
 			// Agregar caracteres 7,8 
 			curpDeDatos += fecha.substring(3,5);
 			
 			// Agregar caracteres 9,10 
 			curpDeDatos += fecha.substring(0,2);
 			
 			// Agregar caracter 11 
 			curpDeDatos += genero;
			
 			 			
 			if (!paisId.equals("153")){
 				curpDeDatos += "NE";
 			}else if (!estadoId.equals("0")){
 				int estado = Integer.parseInt(estadoId);
 				switch(estado){
 				case 1: curpDeDatos += "AS"; break;
 				case 2: curpDeDatos += "BC"; break;
 				case 3: curpDeDatos += "BS"; break;
 				case 4: curpDeDatos += "CC"; break;
 				case 5: curpDeDatos += "CL"; break;
 				case 6: curpDeDatos += "CM"; break;
 				case 7: curpDeDatos += "CS"; break;
 				case 8: curpDeDatos += "CH"; break;
 				case 9: curpDeDatos += "DF"; break;
 				case 10: curpDeDatos += "DG"; break;
 				case 11: curpDeDatos += "GT"; break;
 				case 12: curpDeDatos += "GR"; break;
 				case 13: curpDeDatos += "HG"; break;
 				case 14: curpDeDatos += "JC"; break;
 				case 15: curpDeDatos += "MC"; break; 				
 				case 16: curpDeDatos += "MN"; break;
 				case 17: curpDeDatos += "MS"; break;
 				case 18: curpDeDatos += "NT"; break;
 				case 19: curpDeDatos += "NL"; break;
 				case 20: curpDeDatos += "OC"; break;
 				case 21: curpDeDatos += "PL"; break;
 				case 22: curpDeDatos += "QT"; break;
 				case 23: curpDeDatos += "QR"; break; 				
 				case 24: curpDeDatos += "SP"; break;
 				case 25: curpDeDatos += "SL"; break;
 				case 26: curpDeDatos += "SR"; break;
 				case 27: curpDeDatos += "TC"; break;
 				case 28: curpDeDatos += "TS"; break;
 				case 29: curpDeDatos += "TL"; break;
 				case 30: curpDeDatos += "VZ"; break;
 				case 31: curpDeDatos += "YN"; break;
 				case 32: curpDeDatos += "ZS"; break; 				
 				} 				
 			}else {
 				// Estado igual a cero
 				curpDeDatos += "XX";
 			}
 			
 			// Caracter 14 
 			boolean ok = false;
			for(int i=1; i <= paterno.length()-1; i++){
				if( !esVocal(paterno.charAt(i)) ){
					if (paterno.charAt(i)!='Ñ')
						curpDeDatos += paterno.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;	                
	            }
	        }
			if (!ok) curpDeDatos += "X";
			
			// Caracter 15 
			ok = false;
			for(int i=1; i <= materno.length()-1; i++){
				if( !esVocal(materno.charAt(i)) ){
					if (materno.charAt(i)!='Ñ')
						curpDeDatos += materno.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;
	            }				
	        }
			if (!ok) curpDeDatos += "X";
			
			// Caracter 16
			ok = false;
			for(int i=1; i <= nombre.length()-1; i++){
				if( !esVocal(nombre.charAt(i)) ){ 
					if (nombre.charAt(i)!='Ñ')
						curpDeDatos += nombre.charAt(i);
					else
						curpDeDatos += "X";					
					ok = true;
	                break;	               
	            }
	        }
			if (!ok) curpDeDatos += "X";
			
			// Iniciales del nombre
			if (curp.length() >= 4 && curpDeDatos.length() >= 4){
			
				if (curp.substring(0,4).equals(curpDeDatos.substring(0,4))){
					resultado = "1";
				}else{
					resultado = "0";
				}
			}else{
				resultado += "0";
			}
			
			// Fecha nacimiento
			if (curp.length() >= 10 && curpDeDatos.length() >= 10){
	 			 
				if (curp.substring(4,10).equals(curpDeDatos.substring(4,10))){				
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Genero
			if (curp.length() >= 11 && curpDeDatos.length() >= 11){				
				if (curp.substring(10,11).equals(curpDeDatos.substring(10,11))){				
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Estado de origen
			if (curp.length() >= 13 && curpDeDatos.length() >= 13){
			
				if (curp.substring(11,13).equals(curpDeDatos.substring(11,13))){			
					resultado += "1";
				}else{				
					resultado += "0";
				}
			}else{
				resultado += "0";
			}	
			
			// Consonantes internas 
			if (curp.length() >= 16 && curpDeDatos.length() >= 16){
				if (curp.substring(13,16).equals(curpDeDatos.substring(13,16))){
					resultado += "1";
				}else{			
					resultado += "0";
				}
			}else {
				resultado += "0";
			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getColportorId|:"+ex);
 		}

		return resultado;
 	} 
 	
 	public int getNumGenero(String eventoId, String genero ) {
		int numAlumnos = 0;
		
		try{
			String comando = "SELECT COUNT(*) AS GENERO" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE SEXO = ?)"; 
		
			Object[] parametros = new Object[] {eventoId, genero};
			numAlumnos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNumGenero|:"+ex);
		}
		
		return numAlumnos;
	}
 	
	public int getNumMexicanos(String eventoId) {
		int numAlumnos = 0;
		
		try{
			String comando = "SELECT COUNT(*) AS MEXICANOS" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE NACIONALIDAD = 153)"; 
				
			Object[] parametros = new Object[] {eventoId};
			numAlumnos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNumMexicanos|:"+ex);
		}
		
		return numAlumnos;
	}
 	
	public int getNumEdoCivil(String eventoId, String estado){
		int numAlumnos = 0;
		
		try{
			String comando = "SELECT COUNT(*) AS ESTADO" +
					" FROM ENOC.ALUM_EGRESO" + 
					" WHERE EVENTO_ID = TO_NUMBER(?,'99')" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PERSONAL WHERE ESTADO_CIVIL = ?)"; 
				
			Object[] parametros = new Object[] {eventoId, estado};
			numAlumnos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNumEdoCivil|:"+ex);
		}
		
		return numAlumnos;
	}
	
	public List<AlumPersonal> getListAll( String orden ) {
		
		List<AlumPersonal> lisAlumno	= new ArrayList<AlumPersonal>();
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ "NOMBRE_LEGAL, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ "PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"
					+ "CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, DIRECCION, CLAVE_COLPORTOR, STATUS, VERSION, DISCRIMINATOR_COL, CAMPO_ID, CREDENCIAL, NUM_OFERTA, TO_CHAR(F_BAUTIZADO, 'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ "RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC "
					+ "FROM ENOC.ALUM_PERSONAL "+orden; 
			
			lisAlumno = enocJdbc.query(comando, new AlumPersonalMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAll|:"+ex);
		}
		return lisAlumno;
	}
	
	public List<AlumPersonal> getLista( String nombre, String paterno, String materno, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ "F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ "RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ "FROM ENOC.ALUM_PERSONAL"
					+ "WHERE NOMBRE LIKE UPPER('?%')"
					+ "AND APELLIDO_PATERNO LIKE UPPER('%?%')"
					+ "AND APELLIDO_MATERNO LIKE UPPER('%?%') "+orden;
		
			Object[] parametros = new Object[] {nombre,paterno,materno};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getLista|:"+ex);
		}
		return lisPersonal;
	}
	
	public List<AlumPersonal> getListaActas( String grupoId, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ "	F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE INSTR((SELECT ALUMNOS FROM ENOC.CARGA_GRUPO_IMP WHERE GRUPO_ID = ?),'-'||CODIGO_PERSONAL||'-') > 0 "+orden;
		
			Object[] parametros = new Object[] {grupoId};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);	
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListaActas|:"+ex);
		}
		return lisPersonal;
	}
	
	public List<AlumPersonal> getListAlum( String nombre, String paterno, String materno, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ "F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ "RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC "
					+ "FROM ENOC.ALUM_PERSONAL"
					+ "WHERE NOMBRE LIKE UPPER('?%')"
					+ "AND APELLIDO_PATERNO LIKE UPPER('%?%')"
					+ "AND APELLIDO_MATERNO LIKE UPPER('%?%')"
					+ "AND SUBSTR(CODIGO_PERSONAL,1,2) NOT IN ('97','98') "+orden;

			Object[] parametros = new Object[] {nombre,paterno,materno};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlum|:"+ex);
		}
		return lisPersonal;
	}
	
	public List<AlumPersonal> getListAlumTodo( String nombreCompleto, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE UPPER(NOMBRE)||' '||UPPER(APELLIDO_PATERNO)||' '||UPPER(APELLIDO_MATERNO) LIKE '%"+nombreCompleto.toUpperCase()+"%'"
					+ " AND SUBSTR(CODIGO_PERSONAL,1,2) NOT IN ('97','98') "+orden;
		
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper());	

		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlumTodo|:"+ex);
		}
		return lisPersonal;
	}
	
	// Metodo Depreciado se sustituye en la clase InscritosUtil  
	public List<AlumPersonal> getListCumple( String mes, String dia, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";		
		try{			
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC,"
					+ " ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) AS CARRERA_ID"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2) = ?";			
			if (!dia.equals("0")){
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= ? ";				
			}	
			comando = comando + " "+orden;

			Object[] parametros = new Object[] {mes,dia};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);	

		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListCumple|:"+ex);
		}
		return lisPersonal;
	}
	   
	public List<AlumPersonal> listGraduandos( String eventoId, String orden ) {		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";		
		try{			
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999')) "+ orden;			
			Object[] parametros = new Object[] {eventoId};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|listGraduandos|:"+ex);
		}
		return lisPersonal;
	}	
	   
	public List<AlumPersonal> lisAlumnosConDocumentos(String planId, String documentos, String orden ) {
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();				
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE PLAN_ID = ?)"
					+ " AND CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO IN ("+documentos+")) "+ orden;	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisAlumnosConDocumentos|:"+ex);
		}
		return lisPersonal;
	}
	
	// Metodo Depreciado se sustituye en la clase InscritosUtil  
	public List<AlumPersonal> lisNuevosDocumentos( String periodoId, String orden ) {
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
				+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DOCALUM WHERE PERIODO_ID = ? AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_COVID WHERE PERIODO_ID = ?)) "+ orden;			
			Object[] parametros = new Object[] {periodoId, periodoId};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisNuevosDocumentos|:"+ex);
		}
		return lisPersonal;
	}
	
	// Metodo Depreciado se sustituye en la clase InscritosUtil  
	public List<AlumPersonal> lisEnPlan( String planId, String orden ){		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE PLAN_ID = ?) "+ orden;			
			Object[] parametros = new Object[] {planId};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisEnPlan|:"+ex);
		}
		return lisPersonal;
	}
	
	public List<AlumPersonal> lisPorDormitorio( String dormitorioId, String mes, String orden ){		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'YYYY/MM/DD') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?)"
					+ " AND TO_CHAR(F_NACIMIENTO,'MM') = ? "+ orden;
			Object[] parametros = new Object[] {dormitorioId, mes};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisPorDormitorio|:"+ex);
		}
		return lisPersonal;
	}

	public List<AlumPersonal> lisExportar( String orden ){		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'YYYY/MM/DD') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL NOT IN ( SELECT '1'||TRIM(CODE) AS CODIGO_PERSONAL FROM ENOC.ATTACHE_CUSTOMER ) AND CODIGO_PERSONAL NOT IN (SELECT TRIM(CODE) AS CODIGO_PERSONAL FROM ENOC.ATTACHE_CUSTOMER) AND SYNC = 'S'"
					+ orden;
			// Object[] parametros = new Object[] {};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisExportar|:"+ex);
		}
		return lisPersonal;
	}

	public List<AlumPersonal> lisExportados( String orden ){		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'YYYY/MM/DD') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN ( SELECT '1'||TRIM(CODE) AS CODIGO_PERSONAL FROM ENOC.ATTACHE_CUSTOMER ) OR CODIGO_PERSONAL IN (SELECT TRIM(CODE) AS CODIGO_PERSONAL FROM ENOC.ATTACHE_CUSTOMER) AND SYNC = 'S'"
					+ orden;
			// Object[] parametros = new Object[] {};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisExportados|:"+ex);
		}
		return lisPersonal;
	}

	public List<AlumPersonal> lisSync( String sync, String orden ){		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		try{			
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'YYYY/MM/DD') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE SYNC = ?"
					+ orden;
			// Object[] parametros = new Object[] {};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), sync);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|lisSync|:"+ex);
		}
		return lisPersonal;
	}
	
	public String edadEntreFechas( String codigoPersonal, String fechaGra, String fechaNac ) {			
		String meses				= "";			
		try{			
			String comando = " SELECT TO_CHAR(MONTHS_BETWEEN (TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY') ),'9999.99') AS MESES "
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";		
			Object[] parametros = new Object[] {fechaGra,fechaNac,codigoPersonal};
			meses = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|edadEntreFechas|:"+ex);
		}
		return meses;
	}
	
	public String getNombre( String codigoPersonal, String opcion) {		
	
		String nombre		= "x";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
 				if ( opcion.equals("NOMBRE")){
 					comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE "
 							+ "FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 
 				}else{
 					comando = "SELECT APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS NOMBRE "
 							+ "FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"; 
 				}			
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
 			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getNombre|:"+ex);
		}
		return nombre;
	}
	
	public String getPlanActivo( String codigoAlumno){		
		String planId			= "Empty";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigoAlumno};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1 ){
				comando = "SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO = '1'";
				planId = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getPlanActivo|:"+ex);
		}
		return planId;		
	}
	
	public String getCarreraId(String planId){
		String carreraId = "Empty";
		try{
			String comando = "SELECT COUNT(CARRERA_ID) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				carreraId = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCarreraId|:"+ex);
		}
		return carreraId;
		
	}
	
	public String getCarrera( String codigoPersonal) {
		String nombre		= "x";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				parametros = new Object[] {codigoPersonal, codigoPersonal};
				comando = "SELECT ENOC.NOMBRE_CARRERA(ENOC.CARRERA(ENOC.ALUM_PLAN_ID(?))) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";				
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCarrera|:"+ex);
		}
		return nombre;
	}	

	public String getCarrera( String codigoPersonal, String planId) {
		String comando	= "";
		String nombre		= "x";		
		try{			
			comando = "SELECT COUNT(*) FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ENOC.CARRERA(?)";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1 ){
				comando = "SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ENOC.CARRERA(?)";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCarrera|:"+ex);
		}
		return nombre;
	}	
	
	public String getCarreraIdCodigo( String codigoPersonal) {
		String comando		= "";
		String carrera		= "x";		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "
					+ "WHERE A.CODIGO_PERSONAL = ? "
					+ "AND A.ESTADO = '1' "
					+ "AND B.PLAN_ID = A.PLAN_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1 ){
				comando = "SELECT B.CARRERA_ID "
						+ "FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "
						+ "WHERE A.CODIGO_PERSONAL = ? "
						+ "AND A.ESTADO = '1' "
						+ "AND B.PLAN_ID = A.PLAN_ID";
				carrera = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCarreraIdCodigo|:"+ex);
		}
		return carrera;
	}
	
	public List<String> getEmpleadosPorCoodinador(String codigo) {
		
	    List<String> empleados	= new ArrayList<String>();
	    List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
	    
	    try {
	        String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?";
	        Object[] parametros = new Object[] {codigo};
	        if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
	        	comando = "SELECT CLAVE AS LLAVE,NOMBRE+' '+APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
		    			+ "WHERE ID IN "
		    			+ "(SELECT ID FROM ARON.EMPLEADO_PUESTO "
		    			+ "WHERE ID_CCOSTO LIKE CONCAT((SELECT SUBSTR(ID_CCOSTO,1,9) FROM ARON.EMPLEADO_PUESTO WHERE CLAVE=?),'%') "
		    			+ ")"
		    			+ "ORDER BY NOMBRE";
			}else{
		    	comando ="SELECT CLAVE AS LLAVE,NOMBRE||' '||APPATERNO||' '||APMATERNO AS VALOR FROM ARON.EMPLEADO"
		    			+ "WHERE ID IN "
		    			+ "SELECT ID FROM ARON.EMPLEADO_PUESTO "
		    			+ "WHERE ID_CCOSTO IN (SELECT ID_CCOSTO FROM ARON.EMPLEADO_PUESTO WHERE CLAVE=?)"
		    			+ ")"
		    			+ "ORDER BY NOMBRE";
		    }
	        lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
	        for(aca.Mapa map:lista){
    	        empleados.add(map.getLlave());
    	        empleados.add(map.getValor());
    	    }
        } catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getCarreraIdCodigo|:"+ex);
        }
	    
	    return empleados;
	}
	
	public List<AlumPersonal> getListIguales( String nombre, String paterno, String materno) {
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();		
		List<AlumPersonal> lista		= new ArrayList<AlumPersonal>();
		char cNomb			= '0';
		char cPat			= '0';		
		String sNomb		= "";
		String sPat			= "";
		String sMat			= "";
		int iSuma			= 0;
		int iCont			= 0;
		int iTamanio		= 0;
		
		nombre		= nombre.toUpperCase();
		paterno		= paterno.toUpperCase();
		materno		= materno.toUpperCase();
		
		cNomb 		= nombre.charAt(0);
		cPat 		= paterno.charAt(0);
		
		if (cNomb != '0' & cPat != '0')
		{
			try{
				String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE SUBSTR(NOMBRE,1,1) = ? AND SUBSTR(APELLIDO_PATERNO,1,1) = ?";
				lista = enocJdbc.query(comando, new AlumPersonalMapper(), cNomb, cPat);				
				for (AlumPersonal alumno : lista){
					
					sNomb 	= alumno.getNombre();
					sPat 	= alumno.getApellidoPaterno();
					sMat 	= alumno.getApellidoMaterno();
					int i = 0;
					iSuma = 0;
					for(i = 0; i < nombre.length() & i < sNomb.length();i++)
					{
						if(nombre.charAt(i) == sNomb.charAt(i))
						{
							iSuma++;
						}
					}
					if(nombre.length() < sNomb.length())
						iTamanio = sNomb.length();
					else
						iTamanio = nombre.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					iSuma = 0;
					for(i = 0; i < paterno.length() & i < sPat.length();i++)
					{
						if(paterno.charAt(i) == sPat.charAt(i))
						{
							iSuma++;
						}
					}
					if(paterno.length() < sPat.length())
						iTamanio = sPat.length();
					else
						iTamanio = paterno.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					iSuma = 0;
					for(i = 0; i < materno.length() & i < sMat.length();i++)
					{
						if(materno.charAt(i) == sMat.charAt(i))
						{
							iSuma++;
						}
					}
					if(materno.length() < sMat.length())
						iTamanio = sMat.length();
					else
						iTamanio = materno.length();
					if(((100*iSuma)/iTamanio)>=60)
						iCont++;
					
					if(iCont >= 2)
					{						
						lisPersonal.add(alumno);
					}
					iCont = 0;
					
					
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListIguales|:"+ex);
			}
		}
		return lisPersonal;
	}
	
	public String getVencimientoFM3(String codigo) {
	    String fecha="01/01/2050";
	    
	    try {
	        String comando = "SELECT COUNT(*) FROM ENOC.LEG_EXTDOCTOS WHERE CODIGO = ? AND IDDOCUMENTO = 2";
	        Object[] parametros = new Object[] {codigo};
	        if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
	        	comando = "SELECT COALESCE(TO_CHAR(FECHA_VENCE,'DD/MM/YYYY'),'01/01/2050') AS VENCIMIENTO "
		        		+ "FROM ENOC.LEG_EXTDOCTOS "
		        		+ "WHERE CODIGO = ? "
		        		+ "AND IDDOCUMENTO = 2 ";
	        	fecha = enocJdbc.queryForObject(comando, String.class, parametros);
	        }
    	    
        } catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getVencimientoFM3|:"+ex);
            ex.printStackTrace();
        }
	    return fecha;
	}
	
	public boolean esExtranjero(String codigo) { 
	    boolean ext=false;
	    try {
	        String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
	        Object[] parametros = new Object[] {codigo};			
	        if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
	        	comando = "SELECT COALESCE(NACIONALIDAD,0) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";	        
				if (enocJdbc.queryForObject(comando, Integer.class, parametros) != 153){					
					ext = true;
				}
	        }    
	    } catch (Exception ex) {
	    	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|esExtrangero|:"+ex);
            ex.printStackTrace();
        }
	    return ext;
	}
	
	public boolean esExtranjero(String codigo, int paisId) { 
	    boolean ext=false;
	    try {
	        String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
	        Object[] parametros = new Object[] {codigo};			
	        if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
	        	comando = "SELECT COALESCE(NACIONALIDAD,0) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";	        
				if (enocJdbc.queryForObject(comando, Integer.class, parametros) != paisId){					
					ext = true;
				}
	        }    
	    } catch (Exception ex) {
	    	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|esExtrangero|:"+ex);
            ex.printStackTrace();
        }
	    return ext;
	}
	
	public double getSaldo(String codigo) {	
	
	    double saldo=0;
	    try {
	        String comando = "SELECT" +
	        	" COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END ),0) AS SALDO" +
	        	" FROM MATEO.CONT_MOVIMIENTO" +
	        	" WHERE ID_AUXILIARM=?" +
	        	" AND ID_EJERCICIO ='001-'||TO_CHAR(now(),'YYYY')" +
	        	" AND ID_CTAMAYORM IN('1.1.04.01','1.1.04.29','1.1.04.30')" +
	        	" AND TIPO_CUENTA = 'B'";
	        Object[] parametros = new Object[] {codigo};
			
			if (enocJdbc.queryForObject(comando, Double.class, parametros) >= 1){		        
				saldo = enocJdbc.queryForObject(comando, Double.class, parametros);
			}				
        }catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getSaldo|:"+ex);
            ex.printStackTrace();
        }
	    
	    return saldo;
	}
	
	public boolean insertAlumSaldo(String codigo,String ejercicio,double saldo) {
	    
		boolean ok = false; 
	    
	    try {	        
	        String comando = "INSERT INTO ENOC.ALUM_SALDO(CODIGO_PERSONAL, EJERCICIO_ID, SALDO) VALUES(?,?,?)";
	        Object[] parametros = new Object[] {codigo, ejercicio, saldo};
	        if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			} 	    
    	    
        }catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|insertAlumSaldo|:"+ex);
            
        }
	    return ok;
	}
	
	public List<String> getListAlumnos() {   
			
		List<String> lista = new ArrayList<String>();
	    try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.ESTADISTICA";		
			lista = enocJdbc.queryForList(comando, String.class);
			
        }catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlumnos|:"+ex);
        }
	    return lista;
	}
	
	public List<AlumPersonal> getAlumnosPat( String nombre, String paterno, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL WHERE NOMBRE LIKE '"+nombre.charAt(0)+"%' OR APELLIDO_PATERNO LIKE '"+paterno.charAt(0)+"%' "+orden;			
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getAlumnosPat|:"+ex);
		}
		return lisPersonal;
	}
	
	public List<AlumPersonal> alumnosCumpleExaAlumno(String orden) {
		List<AlumPersonal> lista	= new ArrayList<AlumPersonal>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
				"NOMBRE_LEGAL, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, "+
				"PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, "+
				"CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO,F_CREADO,CREDENCIAL,US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID," +
				"RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID"+
				" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN( SELECT MATRICULA FROM ENOC.EXA_CORREO WHERE ELIMINADO != 1 ) "+orden;
			
			lista = enocJdbc.query(comando, new AlumPersonalMapper());
			
		}catch(Exception ex){ 
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|alumnosCumpleExaAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPersonal> getAlumnos( String nombre, String paterno, String materno, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL WHERE NOMBRE LIKE '"+nombre.charAt(0)+"%' OR APELLIDO_PATERNO LIKE '"+paterno.charAt(0)+"%' OR APELLIDO_MATERNO LIKE '"+materno.charAt(0)+"%' "+orden;		 
			
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper());	
							
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getAlumnos|:"+ex);
		}
		
		return lisPersonal;
	}
	
	
	public List<AlumPersonal> BuscaDuplicados( String name, String aPaterno, String aMaterno, int porc) {
		List<AlumPersonal> lisTmp			= new ArrayList<AlumPersonal>();
		try{
			
			List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();	
			
			//Declaracion de Variables					
			String sNomb		= "";
			String sPat			= "";
			String sMat			= "";
			int iSuma			= 0;
			int iCont			= 0;
			int iTamanio		= 0;		
			
			//Asignacion de los parametros
			String nombre		= name.toUpperCase();
			String paterno		= aPaterno.toUpperCase();
			String materno		= aMaterno.toUpperCase();			
			int k=0, i=0/*, row= 0*/; 			
			
			if(materno.length() == 0){
				lisPersonal = getAlumnosPat(nombre, paterno, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
			}
				
			if(materno.length() != 0){
				lisPersonal = getAlumnos(nombre, paterno, materno, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
			}
			//lisPersonal = alumU.getListAll(Conn, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");						
				
			//aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal)lisPersonal.get(row);
										
			for (k=1;k<lisPersonal.size();k++){
				aca.alumno.spring.AlumPersonal alumno2 = lisPersonal.get(k);
				
				sNomb = alumno2.getNombre().toUpperCase();
				sPat = alumno2.getApellidoPaterno().toUpperCase();
				sMat = alumno2.getApellidoMaterno().toUpperCase();						
			
				iSuma = 0;						
				// Verifica la similitud del nombre
				for(i = 0; i < nombre.length() & i < sNomb.length();i++){						
					if(nombre.charAt(i) == sNomb.charAt(i)){
						iSuma++;
					}
				}
				if(nombre.length() < sNomb.length())
					iTamanio = sNomb.length();
				else
					iTamanio = nombre.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;
					
				// Verifica la similitud del apellido paterno
				iSuma = 0;
				for(i = 0; i < paterno.length() & i < sPat.length();i++){
					if(paterno.charAt(i) == sPat.charAt(i)){
						iSuma++;
					}
				}
				if(paterno.length() < sPat.length())
					iTamanio = sPat.length();
				else
					iTamanio = paterno.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;
				
				// Verifica la similitud del apellido materno
				iSuma = 0;
				for(i = 0; i < materno.length() & i < sMat.length();i++){
					if(materno.charAt(i) == sMat.charAt(i)){
						iSuma++;
					}
				}
				if(materno.length() < sMat.length())
					iTamanio = sMat.length();
				else
					iTamanio = materno.length();
				if(((100*iSuma)/iTamanio)>=porc)
					iCont++;	
				
				if(iCont >= 2){
					lisTmp.add(lisPersonal.get(k));
				}
				
				iCont = 0;
			}
		
			lisPersonal.remove(0);
			//System.out.println("Tamano del listor:"+lisPersonal.size());				
		}catch(Exception e){
			System.out.println("Error en busca duplicados: "+e);
		}
		return lisTmp;
	}
	
	public boolean esNuevoIngreso(String codigo, String planId) {
	    boolean ok = false;

	    try {
	        String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT"
	        		+ " WHERE CODIGO_PERSONAL = ?"
	        		+ " AND ENOC.CURSO_PLAN(CURSO_ID)= ?"
	        		+ " AND TIPOCAL_ID IN ('1','2')";	        
	        Object[] parametros = new Object[] {codigo,planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
        } catch (Exception ex) {
        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|esNuevoIngreso|:"+ex);        }
	    return ok;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnCarga( String cargaId) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();

		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapAllCarga|:"+ex);
		}
		return map;
	}
	
	public HashMap<String, String> mapaEdadesAlumnosEnCarga( String cargaId) {
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista 	= enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaEdadesAlumnosEnCarga|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaEdadesAlumnos( String orden) {
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_PERSONAL";
			// Object[] parametros = new Object[] {cargaId};
			lista 	= enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaEdadesAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapMentorContacto( String periodoId) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL "
					+ "WHERE CODIGO_PERSONAL IN "
					+ "(SELECT CODIGO_PERSONAL FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ?)";					
			list = enocJdbc.query(comando, new AlumPersonalMapper(), periodoId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapMentorContacto|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapMentorAconsejado( String periodoId) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();	
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL "
					+ "WHERE CODIGO_PERSONAL IN "
					+ "(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ?)";					
			list = enocJdbc.query(comando, new AlumPersonalMapper(), periodoId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapMentorContacto|:"+ex);
		}
		
		return map;
	}
	
	// Map de alumnos inscritos en cargas
	public HashMap<String, AlumPersonal> mapInscritosEnCargas( String cargas) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL " +
					" WHERE CODIGO_PERSONAL IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO IN ('I','3') AND CARGA_ID IN ("+cargas+"))";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapInscritosEnCargas|:"+ex);
		}
		
		return map;
	}
	
	// Map de alumnos inscritos
	public HashMap<String, AlumPersonal> mapaInscritos() {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaInscritos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnSolicitud() {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_DOCUMENTO)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnSolicitud|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnMaratum() {		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.FIN_MARATUM)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnMaratum|:"+ex);
		}
		
		return map;
	}

	public HashMap<String, AlumPersonal> mapaEgreso() {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaEgreso|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaExalumnos() {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(MATRICULA) FROM ENOC.EXA_EGRESO)";	
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaEgreso|:"+ex);
		}
		
		return map;
	}
	
	// Mapa de alumnos en covid
	public HashMap<String, AlumPersonal> mapaCovid() {			
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION,, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.COVID)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaCovid|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> getMapAllCargaAlumEstado( String cargas) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL "
					+ "WHERE CODIGO_PERSONAL IN "
					+ "(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO IN ('I','3') AND CARGA_ID IN ("+cargas+"))";
			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapAllCargaAlumEstado|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> getMapProced( ) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PAIS_ID, ESTADO_ID"
					+ "FROM ENOC.ALUM_PERSONAL";
			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapProced|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> getMapInscritos( ) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO,CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapInscritos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> getMapInscritos( String cargas, String fechaIni, String fechaFinal) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN"
					+ "	(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND CARGA_ID IN ("+cargas+"))";			
			list = enocJdbc.query(comando, new AlumPersonalMapper(), fechaIni,fechaFinal);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapInscritos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> getMapRotaciones( ) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL)";		
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapRotaciones|:"+ex);
		}		
		return map;
	}
	
	public List<AlumPersonal> getAlumnosCreados( String fechaInicio, String fechaFinal, String orden ) {
		
		List<AlumPersonal> lisPersonal	= new ArrayList<AlumPersonal>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
						+ " NOMBRE_LEGAL, F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
						+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"
						+ " CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, DIRECCION, CREDENCIAL, TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA, TO_CHAR(F_BAUTIZADO, 'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID," 
						+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
						+ " FROM ENOC.ALUM_PERSONAL"
						+ " WHERE F_CREADO >= TO_DATE(?,'DD/MM/YYYY')"
						+ " AND F_CREADO <= TO_DATE(?,'DD/MM/YYYY') "+orden;			
			Object[] parametros = new Object[] {fechaInicio,fechaFinal};	
			lisPersonal = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getAlumnosCreados|:"+ex);
		}		
		return lisPersonal;
	}
	
	public HashMap<String, AlumPersonal> getMapAlumnosIngreso( ) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.VIG_INGRESO)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapAlumnosIngreso|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> getMapEdad( String cargas) {
		
		HashMap<String, String> map			= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(TO_CHAR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12,'99'),'0') AS VALOR " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+"))";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa edad : list ) {
				map.put(edad.getLlave(), edad.getValor().trim());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapEdad|:"+ex);
		}		
		return map;
	}

	public HashMap<String, String> mapaNombreAlumnoPlanId(String planId) {
		
		HashMap<String, String> map			= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE PLAN_ID = ?)";
			
			Object[] parametros = new Object[] {planId};
			list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa edad : list ) {
				map.put(edad.getLlave(), edad.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaNombreAlumnoPlanId|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> mapaEdades( String cargas) {
		
		HashMap<String, String> map			= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12,0) AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+"))";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa edad : list ) {
				map.put(edad.getLlave(), edad.getValor().trim());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getMapEdad|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaAlumnosEnPeriodo( String periodoId, String opcion ) {
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando 					= "";
		try{
			if (opcion.equals("NOMBRE"))
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR ";
			else 
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR ";
			comando += " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE SUBSTR(CARGA_ID,1,4) = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for (aca.Mapa map : list ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnPeriodo|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapAlumnosEnCarga( String cargaId, String opcion) {
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando 					= "";
		try{
			if (opcion.equals("NOMBRE"))
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR ";
			else 
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR ";
			comando += " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : list ) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCarga( String cargaId, String opcion ) {
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando 					= "";
		try{
			if (opcion.equals("NOMBRE")) {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS VALOR ";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_MATERNO||' '||APELLIDO_PATERNO||' '||NOMBRE AS VALOR ";
			}
			comando += " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : list ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnMusica( String cargaId, String opcion ) {
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando 					= "";
		try{
			if (opcion.equals("NOMBRE"))
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR ";
			else 
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR ";
			comando += " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MUSI_AUTORIZA WHERE CARGA_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map : list ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnMusica|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumnosMateria( String cursoCargaId ) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(REPLACE(APELLIDO_MATERNO, '-', ' '), '')||' '||APELLIDO_PATERNO||' '||NOMBRE AS VALOR " +
					" FROM ENOC.ALUM_PERSONAL" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for (aca.Mapa tipo : list ) {
				mapa.put(tipo.getLlave(), tipo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumnosPlan( String planId ) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VAlOR"
					+ " FROM ENOC.ALUM_PERSONAL AP"
					+ " WHERE CODIGO_PERSONAL IN"
					+ " (SELECT CODIGO_PERSONAL FROM ALUM_PLAN WHERE PLAN_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), planId);
			for (aca.Mapa tipo : list ) {
				mapa.put(tipo.getLlave(), tipo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosPlan|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumnosCarga( String cargaId ) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VAlOR"
					+ " FROM ENOC.ALUM_PERSONAL AP"
					+ " WHERE CODIGO_PERSONAL IN"
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?)";
			list = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa tipo : list ) {
				mapa.put(tipo.getLlave(), tipo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosCarga|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapGraduandos( String eventoId) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, DIRECCION, CREDENCIAL,"
					+ " TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID, RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ?)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper(), eventoId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapGraduandos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnMateria( String cursoCargaId) {
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
							+" TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
							+" PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TELEFONO, DIRECCION, CREDENCIAL,"
							+" TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
							+" RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC "
							+" FROM ENOC.ALUM_PERSONAL "
							+" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?)";
			Object[] parametros = new Object[] {cursoCargaId};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnMateria|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnCargas( String cargaId) {

		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ?)";	
			Object[] parametros = new Object[] {cargaId};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnCargas|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosFamilia( String apellidos) {

		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE TRIM(APELLIDO_PATERNO)||' '||TRIM(APELLIDO_MATERNO) = ?";
			Object[] parametros = new Object[] {apellidos};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosFamilia|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnCargasyModalidades( String cargaId, String modalidades) {		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, "
					+ " TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL,"
					+ " TO_CHAR(F_CREADO, 'DD/MM/YYYY') AS F_CREADO, US_ALTA, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargaId+") AND MODALIDAD_ID IN ("+modalidades+"))";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnCargas|:"+ex);
		}		
		return map;
	}
	
	// Map de nombres de alumnos con descuentos 
	public HashMap<String, String> mapAlumDescuento( ) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> map		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"+
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_DESCUENTO)";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa tipo : list ) {
				map.put(tipo.getLlave(), tipo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumDescuento|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> mapAlumComidas(String cargas) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> map		= new HashMap<String,String>();	
		cargas = cargas.replace("''","'");
		try{
			String comando = "SELECT MATRICULA AS LLAVE, COALESCE(NUM_COMIDAS,0) AS VALOR FROM NOE.COM_AUTORIZACION WHERE CARGA_ID IN ("+cargas+")";
			
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa tipo : list ) {
				map.put(tipo.getLlave(), tipo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumComidas|:"+ex);
		}		
		return map;
	}
	
	// Map de nombres de alumnos en Ment_alumno 
	public HashMap<String, String> mapMentAlumno( String periodo){		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ?)";			
			list = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa tipo : list ) {
				map.put(tipo.getLlave(), tipo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapMentAlumno|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapBecadosFecha( String fecha) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN "
					+ "SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN)";
			list = enocJdbc.query(comando, new AlumPersonalMapper(), fecha);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapBecadosFecha|:"+ex);
		}
		
		return map;
	}
	
	// Map de edad de los inscritos
	public HashMap<String, String> mapEdadInscritos( ) {
		
		HashMap<String,String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, TO_CHAR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),F_NACIMIENTO)/12,'99999.99') AS VALOR FROM ENOC.ALUM_PERSONAL "+
					" WHERE CODIGO_PERSONAL IN ( SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS )";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa tipo : list ) {
				map.put(tipo.getLlave(), tipo.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|MapEdadInscritos|:"+ex);
		}
		
		return map;
	}

	public HashMap<String, String> mapEdadCovid() {
		
		HashMap<String,String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_COVID)";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa tipo : list ) {
				map.put(tipo.getLlave(), tipo.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapEdadCovid|:"+ex);
		}
		
		return map;
	}
	
	/*
	 * 
	 **/
	public HashMap<String, AlumPersonal> mapAlumnosDiferidos( ) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String,AlumPersonal> map = new HashMap<String,AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))";
		
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosDiferidos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosBecados( String ejercicioId) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String,AlumPersonal> map = new HashMap<String, AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ?)";
			list = enocJdbc.query(comando, new AlumPersonalMapper(), ejercicioId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosBecados|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnAcuerdos( String ejercicioId) {
		List<AlumPersonal> list 			= new ArrayList<AlumPersonal>();
		HashMap<String,AlumPersonal> map 	= new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ?)";
			list = enocJdbc.query(comando, new AlumPersonalMapper(), ejercicioId);
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnAcuerdos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosInformeBeca( String informeId) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String,AlumPersonal> mapa = new HashMap<String, AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_INFORME_ALUMNO WHERE INFORME_ID = ?)";		
			Object[] parametros = new Object[] {informeId};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosInformeBeca|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosInformes( String ejercicioId) {
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_INFORME_ALUMNO WHERE ID_EJERCICIO = ?)";		
			list = enocJdbc.query(comando, new AlumPersonalMapper(), ejercicioId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosBecados|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosInternos( String dormitorioId) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map 		= new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "	NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"					
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper(), dormitorioId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosInternos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosEnExalumnos( String eventoId ){
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map 		= new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "	NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA,TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"					
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.EXA_ALUMEVENTO WHERE IDEVENTO = ?)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper(), eventoId);
			for (AlumPersonal objeto : list ) {
				map.put(objeto.getCodigoPersonal(), objeto );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosEnExalumnos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosConExtra( String cargaId) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();
		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID IN "
					+ "			(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND NOTA_EXTRA IS NOT NULL AND F_EXTRA IS NOT NULL))";			
			list = enocJdbc.query(comando, new AlumPersonalMapper(), cargaId);
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosConExtra|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosConTramites( ) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
				+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN "
				+ "		(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BIT_TRAMITE_ALUMNO)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConTramites|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosGraduados( ) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA,TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
				+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN "
				+ "		(SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUM_GRADUA)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosGraduados|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnGraduacion( String eventoId ) {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
				+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN "
				+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {eventoId};
			list = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnGraduacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapCandidatos( String codigos) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN ("+codigos+")";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapCandidatos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCandidatos( String eventoId, String candidatoId) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.VOTO_CANDIDATO_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CANDIDATO_ID = TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {eventoId, candidatoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapCandidatos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCandidatosEnEvento( String eventoId) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.VOTO_CANDIDATO_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99999')) ";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapCandidatos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapDeudores(){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ALUM_SALDOS WHERE SALDO < -59999)";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapDeudores|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaConservatorio(String estados){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_UM FROM MUSI_ALUMNO WHERE CODIGO_UM != '0' AND ESTADO IN ("+estados+"))";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaConservatorio|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaUbicacion (String estados){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ALUM_UBICACION"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_UM FROM MUSI_ALUMNO WHERE CODIGO_UM != '0' AND ESTADO IN ("+estados+"))";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaUbicacion|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAcademico (String estados){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_UM FROM MUSI_ALUMNO WHERE CODIGO_UM != '0' AND ESTADO IN ("+estados+"))";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAcademico|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPlan (String estados, String planes){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa	= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_UM FROM MUSI_ALUMNO WHERE CODIGO_UM != '0' AND ESTADO IN ("+estados+"))"
					+ " AND PLAN_ID IN ("+planes+")";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaPlan|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String, String> mapaTramites(){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BIT_TRAMITE_ALUMNO)";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaTramites|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnProceso(String cargaId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6)= ? AND TIPOCAL_ID = 'M')";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnProceso|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConTitulo(String opcion){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		try{
			if (opcion.equals("NOMBRE")) {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.TIT_ALUMNO)";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||', '||NOMBRE AS VALOR"
						+ " FROM ENOC.ALUM_PERSONAL"
						+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.TIT_ALUMNO)";
			}
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConTitulo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConPermiso(){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR" 
					+" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.FIN_PERMISO)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConPermiso|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConAcuerdo(String ejercicioId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BEC_ACUERDO WHERE ID_EJERCICIO = ?)";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), ejercicioId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConAcuerdo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConBeca(String periodoId, String opcion){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		try{
			if (opcion.equals("NOMBRE")) {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PERIODO_ID = ?)";
			}else if (opcion.equals("MUYCORTO")) {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUBSTR(NOMBRE,1, CASE INSTR(NOMBRE,' ') WHEN 0 THEN LENGTH(NOMBRE) ELSE INSTR(NOMBRE,' ')-1 END)||' '||APELLIDO_PATERNO AS VALOR"
						+ " FROM ENOC.ALUM_PERSONAL"
						+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PERIODO_ID = ?)";
			}else {
				comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR"
						+ " FROM ENOC.ALUM_PERSONAL"
						+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.BEC_PUESTO_ALUMNO WHERE PERIODO_ID = ?)";
			}
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConBeca|:"+ex);
		}
		
		return mapa;
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
	
	public HashMap<String,String> mapaAlumnosEntrevistados(String periodoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosAconsejados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAconsejadosPorPeriodo(String periodoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosAconsejados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosAconsejados(String periodoId, String mentorId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? AND MENTOR_ID = ?)";
			Object[] parametros = new Object[] {periodoId, mentorId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosAconsejados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosCovid(String periodoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ALUM_PERSONAL "
				+ " WHERE CODIGO_PERSONAL IN ( "
				+ "		SELECT CODIGO_PERSONAL FROM ENOC.ALUM_COVID WHERE PERIODO_ID = ?"
				+ " )";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosCovid|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosAlerta(String periodoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, "
					+ " NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN ( "
					+ "		SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? "
					+ " )";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnAlerta(String periodoId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, "
					+ " NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN ( "
					+ " 	SELECT USUARIO FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? "
					+ " )";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnSalida(String salidaId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR "
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnSalida|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnDisciplina(String fechaIni, String fechaFin){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.COND_ALUMNO WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnDisciplina|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnEntrevistas(String periodoId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnEntrevistas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnAconsejados(String periodoId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_AlUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAconsejados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnDisciplina(String periodoId ){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnDisciplina|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosServicio(){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.SSOC_INICIO)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosServicio|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnServicio(){
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL, F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"
					+ " CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, DIRECCION, CLAVE_COLPORTOR, STATUS, VERSION, DISCRIMINATOR_COL, CAMPO_ID, CREDENCIAL, NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.SSOC_INICIO)";	
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnServicio|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String, String> mapaAlumnosEnPuestos(String ejercicioId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = ?)";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnPuestos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConCandados(){
			
			List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
			HashMap<String, String> mapa	= new HashMap<String,String>();
			try{
				String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
						+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CAND_ALUMNO WHERE ESTADO = 'A')";
				lista = enocJdbc.query(comando, new aca.MapaMapper());
				for ( aca.Mapa map : lista) {
					mapa.put(map.getLlave(), map.getValor());
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConCandados|:"+ex);
			}		
			return mapa;
		}
	
	public HashMap<String, String> mapaAlumnosPlanPromedio(String planId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL" 
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE PLAN_ID = ? AND PROMEDIO > 0)";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosPlanPromedio|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaNumAlumnosPlanPromedio(){
			
			List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
			HashMap<String, String> mapa	= new HashMap<String,String>();
			try{
				String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PLAN WHERE PROMEDIO > 0 GROUP BY PLAN_ID";
				lista = enocJdbc.query(comando, new aca.MapaMapper());
				for ( aca.Mapa map : lista) {
					mapa.put(map.getLlave(), map.getValor());
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaNumAlumnosPlanPromedio|:"+ex);
			}		
			return mapa;
		}
	
	public HashMap<String, String> mapaAlumnosBaja(String cargaId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL" 
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3' AND CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosBaja|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosBaja(){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3')";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosBaja|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosMatricula(String eventoId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MAT_ALUMNO WHERE EVENTO_ID = ?)";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosMatricula|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosProcesoEnLinea(){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||', '||NOMBRE AS VALOR" 
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A'))";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConPermiso|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnResidencia(String periodoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||', '||NOMBRE AS VALOR" 
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.RES_ALUMNO WHERE PERIODO_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnResidencia|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnAptitud(){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.APFISICA_ALUMNO)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAptitud|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnUnaMateria(String clave){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_ID LIKE '%"+clave+"%')";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnUnaMateria|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnRotaciones(){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ROT_PROGRAMACION)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnRotaciones|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnPermiso(){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.FIN_PERMISO)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAptitud|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnMateria(String clave){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_ID,9,7) = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), clave);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAptitud|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosEnCurso(String cursoId){		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUMNO_CURSO WHERE CURSO_ID = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnAptitud|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnoRevalida() {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
				+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
				+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
				+ " FROM ENOC.ALUM_PERSONAL"
				+ " WHERE CODIGO_PERSONAL IN "
				+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ARCH_REVALIDA)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnoRevalida|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, AlumPersonal> mapaAlumnoExpediente() {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.RES_EXPEDIENTE)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnoExpediente|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosConSolicitu() {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BIT_REQUISITO_ALUMNO)"
					+ " OR CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BIT_SOLICITUD)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConSolicitu|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosConDisciplina(String fechaInicio, String fechaFinal){
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT DISTINCT(MATRICULA) FROM ENOC.COND_ALUMNO WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY'))";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnDisciplina|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnoEnConvalidacion(String fechaIni, String fechaFin) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa 		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ " (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CONV_EVENTO WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY'))";
			list = enocJdbc.query(comando, new aca.MapaMapper(), fechaIni, fechaFin);
			for (aca.Mapa alumno : list ) {
				mapa.put(alumno.getLlave(), alumno.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnoEnConvalidacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConAgenda() {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa 		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VALOR FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUM_AGENDA)";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa alumno : list ) {
				mapa.put(alumno.getLlave(), alumno.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosConAgenda|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, AlumPersonal> mapaAlumnosBloqueados(String periodoId){
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.FIN_BLOQUEO WHERE PERIODO_ID = ?)";
			list = enocJdbc.query(comando, new AlumPersonalMapper(), periodoId);
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosBloqueados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosTpt(){
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> mapa = new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ " NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT FOLIO FROM SALOMON.ADM_PRUEBA_ALUMNO)";
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				mapa.put(alumno.getCodigoPersonal(), alumno );
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosTpt|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosEnCultural(){
		
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.COMP_ASISTENCIA_ALUMNO)";	
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosEnCultural|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapaAlumnosAlumnos(String info){
		HashMap<String, AlumPersonal> map		= new HashMap<String,AlumPersonal>();
		List<AlumPersonal> list = new ArrayList<AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL, F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"
					+ " CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, DIRECCION, CLAVE_COLPORTOR, STATUS, VERSION, DISCRIMINATOR_COL, CAMPO_ID, CREDENCIAL,NUM_OFERTA, F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL "+info;	
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal alumno : list ) {
				map.put(alumno.getCodigoPersonal(), alumno );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaAlumnosAlumnos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, String> mapaDatos(String periodoId){
		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR" 
					+" FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ENC_PERIODO_RES WHERE PERIODO_ID = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaDatos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaEnMatriculas(String matriculas){
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR" 
					+ " FROM ENOC.ALUM_PERSONAL "
					+ " WHERE INSTR(?,CODIGO_PERSONAL) >= 1";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), matriculas);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapaEnMatriculas|:"+ex);
		}
		
		return mapa;
	}

	public List<AlumPersonal> getListAlumnosEnCarga(String cargaId, String orden) {   
			
			List<AlumPersonal> lista = new ArrayList<AlumPersonal>();
		    try {
				String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
						+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)"+orden;	
				Object[] parametros = new Object[] {cargaId};
				lista = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
				
	        }catch (Exception ex) {
	        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlumnosEnCarga|:"+ex);
	        }
		    return lista;
	}

	public List<AlumPersonal> getListAlumnosEnCargaGrado(String cargaId, int grado, String orden) {   
			
		List<AlumPersonal> lista = new ArrayList<AlumPersonal>();
		try {
			String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE GRADO = ? AND ESTADO = 1)"+orden;	
			Object[] parametros = new Object[] {cargaId, grado};
			lista = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
			
		}catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlumnosEnCargaGrado|:"+ex);
		}
		return lista;
	}

	public List<AlumPersonal> getListAlumnosEnPeriodo(String periodoId, String orden) {   
			
			List<AlumPersonal> lista = new ArrayList<AlumPersonal>();
		    try {
				String comando = "SELECT * FROM ENOC.ALUM_PERSONAL"
						+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID IN (SELECT CARGA_ID FROM CARGA WHERE PERIODO = ?))"+orden;	
				Object[] parametros = new Object[] {periodoId};
				lista = enocJdbc.query(comando, new AlumPersonalMapper(), parametros);
				
	        }catch (Exception ex) {
	        	System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAlumnosEnPeriodo|:"+ex);
	        }
		    return lista;
	}

	public List<AlumPersonal> getListAlumnosEnTraspaso( String orden ) {
		
		List<AlumPersonal> lisAlumno	= new ArrayList<AlumPersonal>();
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " NOMBRE_LEGAL, TO_CHAR(F_NACIMIENTO, 'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, EMAIL,"
					+ " CURP, ESTADO, COTEJADO, CODIGO_SE, F_CREADO, US_ALTA, TELEFONO, DIRECCION, CLAVE_COLPORTOR, STATUS, VERSION, DISCRIMINATOR_COL, CAMPO_ID, CREDENCIAL, NUM_OFERTA, TO_CHAR(F_BAUTIZADO, 'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL" 
					+ " WHERE CODIGO_SE IN (SELECT ID_NUMBER FROM KRDX_TRASPASO)"+orden; 
			
			lisAlumno = enocJdbc.query(comando, new AlumPersonalMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|getListAll|:"+ex);
		}
		return lisAlumno;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosInternos() {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map 		= new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "	NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosInternos|:"+ex);
		}
		
		return map;
	}
	
	public HashMap<String, AlumPersonal> mapAlumnosTrabajos() {
		List<AlumPersonal> list 				= new ArrayList<AlumPersonal>();
		HashMap<String, AlumPersonal> map 		= new HashMap<String, AlumPersonal>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID, BAUTIZADO, PAIS_ID, ESTADO_ID, CIUDAD_ID,"
					+ "	NACIONALIDAD, EMAIL, CURP, ESTADO, COTEJADO, CODIGO_SE, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, TELEFONO, DIRECCION, CREDENCIAL, US_ALTA, NUM_OFERTA, TO_CHAR(F_BAUTIZADO,'DD/MM/YYYY') AS F_BAUTIZADO, CULTURAL_ID, REGION_ID,"
					+ " RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, SYNC"
					+ " FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.TRAB_ALUM)";			
			list = enocJdbc.query(comando, new AlumPersonalMapper());
			for (AlumPersonal tipo : list ) {
				map.put(tipo.getCodigoPersonal(), tipo );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPersonalDao|mapAlumnosInternos|:"+ex);
		}
		
		return map;
	}
}
