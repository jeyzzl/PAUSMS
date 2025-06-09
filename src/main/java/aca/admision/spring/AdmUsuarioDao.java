package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmUsuarioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmUsuario admUsuario) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO SALOMON.ADM_USUARIO"+ 
				"(USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA, " +
				" CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID) "+
				"VALUES( TO_NUMBER(?,'99999'), ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), " +
				"?, ?, TO_NUMBER(?,'99'), ?, ?, TO_NUMBER(?, '999'), ?, TO_NUMBER(?,'999'))";			
			Object[] parametros = new Object[] {
				admUsuario.getUsuarioId(),admUsuario.getNombre(),admUsuario.getApellidoPaterno(),admUsuario.getApellidoMaterno(),admUsuario.getFechaNac(),admUsuario.getEmail(),
				admUsuario.getCuenta(),admUsuario.getClave(),admUsuario.getMatricula(),admUsuario.getTelefono(), admUsuario.getPaisId(),admUsuario.getEstadoId(),admUsuario.getCiudadId(),
				admUsuario.getGenero(),admUsuario.getEstadoCivil(),admUsuario.getReligionId(), admUsuario.getEstado(), admUsuario.getCodigo(), admUsuario.getNacionalidad(), admUsuario.getEmpleado(),
				admUsuario.getInstitucionId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
			}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg(AdmUsuario admUsuario) {
		boolean ok = false;

		try{
			String comando = "UPDATE SALOMON.ADM_USUARIO SET"
					+ " NOMBRE = ?,"
					+ " APELLIDO_PATERNO = ?,"
					+ " APELLIDO_MATERNO = ?,"
					+ " FECHA_NAC = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " EMAIL = ?,"
					+ " CUENTA = ?,"
					+ " CLAVE = ?,"
					+ " MATRICULA = ?,"
					+ " TELEFONO = ?,"
					+ " PAIS_ID = TO_NUMBER(?, '999') ,"
					+ " ESTADO_ID = TO_NUMBER(?, '999') ,"
					+ " CIUDAD_ID = TO_NUMBER(?, '999') ,"
					+ " GENERO = ?,"
					+ " ESTADOCIVIL = ?,"
					+ " RELIGION_ID = TO_NUMBER(?, '99'),"
					+ " ESTADO = ? ,"
					+ " CODIGO = ? ,"
					+ " NACIONALIDAD = TO_NUMBER(?, '999'),"
					+ " EMPLEADO = ?,"
					+ " INSTITUCION_ID = TO_NUMBER(?, '999')"
					+ " WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
			
			Object[] parametros = new Object[] {
					admUsuario.getNombre(),admUsuario.getApellidoPaterno(),admUsuario.getApellidoMaterno(),admUsuario.getFechaNac(),admUsuario.getEmail(),
					admUsuario.getCuenta(),admUsuario.getClave(),admUsuario.getMatricula(),admUsuario.getTelefono(),admUsuario.getPaisId(),admUsuario.getEstadoId(),
					admUsuario.getCiudadId(),admUsuario.getGenero(),admUsuario.getEstadoCivil(),admUsuario.getReligionId(),admUsuario.getEstado(),admUsuario.getCodigo(),admUsuario.getNacionalidad(),
					admUsuario.getEmpleado(), admUsuario.getInstitucionId(),
					admUsuario.getUsuarioId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|updateReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateClaveUsuario(String usuarioId, String clave) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_USUARIO SET CLAVE = ? WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {clave, usuarioId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|updateClaveUsuario|:"+ex);
		}

		return ok;
	}
	
	public boolean updateMatricula(String usuarioId, String matricula) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_USUARIO SET MATRICULA = ? WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {matricula, usuarioId };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|updateMatricula|:"+ex);
		}

		return ok;
	}	
	
	public boolean updateClave(String cuenta, String clave){
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_USUARIO SET CLAVE = ? WHERE CUENTA = ?";
			Object[] parametros = new Object[] { clave, cuenta };
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|updateClave|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String UsuarioId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {UsuarioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmUsuario mapeaRegId(String UsuarioId ) {
		AdmUsuario objeto = new AdmUsuario();		
		try {
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, TO_CHAR(FECHA_NAC,'DD/MM/YYYY') AS FECHA_NAC, EMAIL, CUENTA,"
					+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
					+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
					+ " FROM SALOMON.ADM_USUARIO"
					+ " WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {UsuarioId};
			objeto = enocJdbc.queryForObject(comando, new AdmUsuarioMapper(), parametros);			
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String UsuarioId) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			Object[] parametros = new Object[] {UsuarioId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeUsuario(String cuenta, String clave) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE CUENTA = ? AND CLAVE = ?";		
			Object[] parametros = new Object[] {cuenta,clave};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|existeUsuario|:"+ex);
		}		
		return ok;
	}
	
	public String getUsuarioId(String cuenta, String clave) {
		String usuarioId = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE CUENTA = ? AND CLAVE = ?";			
			if (enocJdbc.queryForObject(comando,Integer.class, cuenta,clave)>=1){
				comando = "SELECT USUARIO_ID FROM SALOMON.ADM_USUARIO WHERE CUENTA = ? AND CLAVE = ?";
				usuarioId = enocJdbc.queryForObject(comando,String.class, cuenta,clave);
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|getUsuarioId|:"+ex);
		}		
		return usuarioId;
	}
	
	public String getNombreCorto(String usuarioId){
		String nombre = "X";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			if (enocJdbc.queryForObject(comando,Integer.class, usuarioId)>=1){
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO AS NOMBRE FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
				nombre = enocJdbc.queryForObject(comando,String.class, usuarioId);
			}						
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|getNombreCorto|:"+ex);
		}		
		return nombre;
	}
	
	public String getMatricula(String usuarioId){
		String matricula = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			if (enocJdbc.queryForObject(comando,Integer.class, usuarioId)>=1){
				comando 	= "SELECT MATRICULA FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
				matricula 	= enocJdbc.queryForObject(comando,String.class, usuarioId);
			}						
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|getMatricula|:"+ex);
		}		
		return matricula;
	}
	
	public String getCuenta(String usuarioId){
		String matricula = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";			
			if (enocJdbc.queryForObject(comando,Integer.class, usuarioId)>=1){
				comando 	= "SELECT CUENTA FROM SALOMON.ADM_USUARIO WHERE USUARIO_ID = TO_NUMBER(?,'99999')";
				matricula 	= enocJdbc.queryForObject(comando,String.class, usuarioId);
			}						
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|getCuenta|:"+ex);
		}		
		return matricula;
	}
	
	public boolean existeCuenta(String Cuenta) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_USUARIO "+ 
					" WHERE CUENTA = ?";			
			Object[] parametros = new Object[] {Cuenta};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|existeCuenta|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg() {
		String usuarioId = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(USUARIO_ID), 0)+1 AS MAXIMO FROM SALOMON.ADM_USUARIO";
			usuarioId = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.spring.AdmUsuarioDao|maximoReg|:"+ex);
		}
		
		return usuarioId;
	}
	
	public List<AdmUsuario> lisTodos(String orden) {
		List<AdmUsuario> lista		= new ArrayList<AdmUsuario>();		
		try{
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
				+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
				+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
				+ " FROM SALOMON.ADM_USUARIO "+ orden;			
			lista = enocJdbc.query(comando, new AdmUsuarioMapper());			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUsuarioDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmUsuario> lisSinSolicitud( String orden ){
		List<AdmUsuario> lista	= new ArrayList<AdmUsuario>();		
		try{
			String comando =  "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
				+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
				+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"		
				+ " FROM SALOMON.ADM_USUARIO "
				+ " WHERE USUARIO_ID NOT IN (SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD)" + orden;
			lista = enocJdbc.query(comando, new AdmUsuarioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|lisSinSolicitud|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmUsuario> listIngreso( String condicion, String orden ){
		List<AdmUsuario> lista	= new ArrayList<AdmUsuario>();		
		try{
			String comando =  "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
				+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
				+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"		
				+ " FROM SALOMON.ADM_USUARIO "+ condicion + orden;
			lista = enocJdbc.query(comando, new AdmUsuarioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|listIngreso|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AdmUsuario> mapaVistoBueno (){
		HashMap<String, AdmUsuario> mapa = new HashMap<String, AdmUsuario>();
		List<AdmUsuario> lista = new ArrayList<AdmUsuario>();		
		try {
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
				+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
				+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
				+ " FROM SALOMON.ADM_USUARIO "
				+ " WHERE USUARIO_ID IN ("
				+ "	  SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PASOS WHERE PASOS = '2' AND ESTADO = '0')"
				+ " )";			
			lista = enocJdbc.query(comando, new AdmUsuarioMapper());
			for (AdmUsuario objeto : lista ){
				mapa.put(objeto.getUsuarioId(), objeto);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|mapaVistoBueno|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String, AdmUsuario> mapaAlumnosSede (String eventoId){
		HashMap<String, AdmUsuario> mapa = new HashMap<String, AdmUsuario>();
		List<AdmUsuario> lista = new ArrayList<AdmUsuario>();		
		try {
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
				+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
				+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
				+ " FROM SALOMON.ADM_USUARIO"
				+ " WHERE USUARIO_ID IN (SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999')))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AdmUsuarioMapper(), parametros);
			for (AdmUsuario objeto : lista ){
				mapa.put(objeto.getUsuarioId(), objeto);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|mapaAlumnosSede|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmUsuario> mapaAlumnosEnTpt(){
		HashMap<String, AdmUsuario> mapa = new HashMap<String, AdmUsuario>();
		List<AdmUsuario> lista = new ArrayList<AdmUsuario>();		
		try {
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
					+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
					+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
					+ " FROM SALOMON.ADM_USUARIO"
					+ " WHERE USUARIO_ID IN (SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD WHERE FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PRUEBA_ALUMNO))";			
			lista = enocJdbc.query(comando, new AdmUsuarioMapper());
			for (AdmUsuario objeto : lista ){
				mapa.put(objeto.getUsuarioId(), objeto);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|mapaAlumnosEnTpt|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmUsuario> mapaUsuariosPorEstado(String estado){
		HashMap<String, AdmUsuario> mapa = new HashMap<String, AdmUsuario>();
		List<AdmUsuario> lista = new ArrayList<AdmUsuario>();		
		try {
			String comando = "SELECT USUARIO_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NAC, EMAIL, CUENTA,"
					+ " CLAVE, MATRICULA, TELEFONO, PAIS_ID, ESTADO_ID, CIUDAD_ID, GENERO, ESTADOCIVIL, RELIGION_ID,"
					+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTADO, CODIGO, NACIONALIDAD, EMPLEADO, INSTITUCION_ID"
					+ " FROM SALOMON.ADM_USUARIO"
					+ " WHERE USUARIO_ID IN (SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD WHERE ESTADO = ?)";
			lista = enocJdbc.query(comando, new AdmUsuarioMapper(), estado);
			for (AdmUsuario objeto : lista ){
				mapa.put(objeto.getUsuarioId(), objeto);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmUsuarioDao|mapaUsuariosPorEstado|:"+ex);
		}
		return mapa;
	}
}