package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmSolicitudDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmSolicitud admSolicitud) throws Exception{
		boolean ok = false;
		
		try{
			String comando =  "INSERT INTO SALOMON.ADM_SOLICITUD ("+ 
				" FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD, FECHA_NAC, ESTADOCIVIL, GENERO," +
				" RELIGION_ID, BAUTIZADO, CLAVE, FECHA, MATRICULA, ESTADO, EMAIL, ASESOR_ID, CURP, FECHA_INGRESO, AGENTE, ASESOR_SEC, RED_SOCIAL,"+
				" FELIGRESIA, TELEFONO, CARTA, CODIGO, USUARIO_ID, FECHA_BAUTIZO, LUGAR_BAUTIZO, CULTURAL_ID, REGION_ID, RES_PAIS_ID, RES_ESTADO_ID, RES_CIUDAD_ID, ACOMODO_ID,"+
				" TIPO, NIVEL_ESTUDIO, TIPO_APLICANTE, PERIODO_ID, TIPO_ACOMODO)" +
				" VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
				" TO_NUMBER(?,'99'), ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'9999999'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?))";			
			Object[] parametros = new Object[] {
admSolicitud.getFolio(), admSolicitud.getNombre(), admSolicitud.getApellidoPaterno(), admSolicitud.getApellidoMaterno(), admSolicitud.getPaisId(), admSolicitud.getEstadoId(), admSolicitud.getCiudadId(), admSolicitud.getNacionalidad(),
					admSolicitud.getFechaNac(), admSolicitud.getEstadoCivil(), admSolicitud.getGenero(), admSolicitud.getReligionId(), admSolicitud.getBautizado(), admSolicitud.getClave(), admSolicitud.getMatricula(), admSolicitud.getEstado(),
					admSolicitud.getEmail(), admSolicitud.getAsesorId(), admSolicitud.getCurp(), admSolicitud.getFechaIngreso(), admSolicitud.getAgente(), admSolicitud.getAsesorSec(), admSolicitud.getRedSocial(), admSolicitud.getFeligresia(), admSolicitud.getTelefono(),
					admSolicitud.getCarta(), admSolicitud.getCodigo(), admSolicitud.getUsuarioId(), admSolicitud.getFechaBautizo(), admSolicitud.getLugarBautizo(), admSolicitud.getCulturalId(), admSolicitud.getRegionId(),
					admSolicitud.getResPaisId(), admSolicitud.getResEstadoId(), admSolicitud.getResCiudadId(), admSolicitud.getAcomodoId(), admSolicitud.getTipo(), admSolicitud.getNivelEstudio(), admSolicitud.getTipoAplicante(), admSolicitud.getPeriodoId(), admSolicitud.getTipoAcomodo()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean insertRegistro(AdmSolicitud admSolicitud) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_SOLICITUD" + 
					"(FOLIO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, GENERO, EMAIL, USUARIO, CLAVE, ESTADO, CURP, FECHA_INGRESO, AGENTE, TELEFONO, CODIGO )" +
					" VALUES (TO_NUMBER(?,'99999999'), ?, ?, ?, "
					+ "?, ?, ?, ?, "
					+ "1, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'), ?,? )";			
			Object[] parametros = new Object[] {
				admSolicitud.getFolio(), admSolicitud.getNombre(), admSolicitud.getApellidoPaterno(), admSolicitud.getApellidoMaterno(),
				admSolicitud.getGenero(), admSolicitud.getEmail(), admSolicitud.getUsuario(), admSolicitud.getClave(),
				admSolicitud.getCurp(), admSolicitud.getFechaIngreso(), admSolicitud.getAgente(), admSolicitud.getTelefono(),admSolicitud.getCodigo()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|insertRegistro|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(AdmSolicitud admSolicitud) {
		boolean ok = false;
		
		try{
  		   	String comando = "UPDATE SALOMON.ADM_SOLICITUD " + 
					" SET" +
					" NOMBRE = ?," +
					" APELLIDO_PATERNO = ?," +
					" APELLIDO_MATERNO = ?," +
					" PAIS_ID = TO_NUMBER(?,'999')," +
					" ESTADO_ID = TO_NUMBER(?,'999')," +
					" CIUDAD_ID = TO_NUMBER(?,'999')," +
					" NACIONALIDAD = TO_NUMBER(?,'999')," +
					" FECHA_NAC = TO_DATE(?,'DD/MM/YYYY')," +
					" ESTADOCIVIL = ?," +
					" GENERO = ?," +
					" RELIGION_ID = TO_NUMBER(?,'99')," +
					" BAUTIZADO = ?," +
					" EMAIL = ?," +
					" CLAVE = ?," +
					" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" MATRICULA = ?," +
					" ESTADO = ?, " +
					" ASESOR_ID = ?, " +
					" CURP = ?," +
					" FECHA_INGRESO = ?, " +
					" AGENTE = TO_NUMBER(?,'99'), " +
					" ASESOR_SEC = ?,"+
					" RED_SOCIAL = ?,"+
					" FELIGRESIA = ?,"+
					" TELEFONO = ?,"+
					" CODIGO = ?,"+
					" FECHA_BAUTIZO = ?," +
					" LUGAR_BAUTIZO = ?," +
					" CULTURAL_ID = TO_NUMBER(?,'999')," +
					" REGION_ID = TO_NUMBER(?,'999')," +
					" RES_PAIS_ID = TO_NUMBER(?,'999')," +
					" RES_ESTADO_ID = TO_NUMBER(?,'999')," +
					" RES_CIUDAD_ID = TO_NUMBER(?,'999')," +
					" ACOMODO_ID = ?,"+
					" TIPO = ?,"+
					" NIVEL_ESTUDIO = ?,"+
					" TIPO_APLICANTE = ?,"+
					" PERIODO_ID = TO_NUMBER(?,'999'),"+ 
					" TIPO_ACOMODO = TO_NUMBER(?)"+
					" WHERE FOLIO = TO_NUMBER(?,'9999999')";  		   
	  		 Object[] parametros = new Object[] {
				admSolicitud.getNombre(),admSolicitud.getApellidoPaterno(),admSolicitud.getApellidoMaterno(),admSolicitud.getPaisId(),admSolicitud.getEstadoId(),
				admSolicitud.getCiudadId(),admSolicitud.getNacionalidad(),admSolicitud.getFechaNac(),admSolicitud.getEstadoCivil(),admSolicitud.getGenero(),admSolicitud.getReligionId(),
				admSolicitud.getBautizado(),admSolicitud.getEmail(),admSolicitud.getClave(),admSolicitud.getFecha(),admSolicitud.getMatricula(),
				admSolicitud.getEstado(),admSolicitud.getAsesorId(),admSolicitud.getCurp(),admSolicitud.getFechaIngreso(),admSolicitud.getAgente(),admSolicitud.getAsesorSec(),
				admSolicitud.getRedSocial(),admSolicitud.getFeligresia(),admSolicitud.getTelefono(), admSolicitud.getCodigo(), admSolicitud.getFechaBautizo(), admSolicitud.getLugarBautizo(),
				admSolicitud.getCulturalId(), admSolicitud.getRegionId(), admSolicitud.getResPaisId(), admSolicitud.getResEstadoId(), admSolicitud.getResCiudadId(), admSolicitud.getAcomodoId(),
				admSolicitud.getTipo(), admSolicitud.getNivelEstudio(), admSolicitud.getTipoAplicante(), admSolicitud.getPeriodoId(), admSolicitud.getTipoAcomodo(),
				admSolicitud.getFolio() 
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	   
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateEstadoAndFecha(String folio, String estado) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET ESTADO = ?, FECHA = TO_DATE(TO_CHAR(SYSDATE, 'DD/MON/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') WHERE FOLIO = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {estado, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateEstado|:"+ex);
		}

		return ok;
	}
	
	public boolean updateRegistro(AdmSolicitud admSolicitud) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET"
					+ " NOMBRE = ?,"
					+ " APELLIDO_PATERNO = ?,"
					+ " APELLIDO_MATERNO = ?,"
					+ " GENERO = ?,"
					+ " EMAIL = ?,"
					+ " USUARIO = ?,"
					+ " CLAVE = ?,"
					+ " ESTADO = ?,"
					+ " CURP = ?,"
					+ " FECHA_INGRESO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " AGENTE = TO_NUMBER(?,'99'),"
					+ " TELEFONO = ?,"
					+ " CODIGO = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {
				admSolicitud.getNombre(),admSolicitud.getApellidoPaterno(),admSolicitud.getApellidoMaterno(),admSolicitud.getGenero(),admSolicitud.getEmail(),
				admSolicitud.getUsuario(),admSolicitud.getClave(),admSolicitud.getEstado(),admSolicitud.getCurp(),admSolicitud.getFechaIngreso(),admSolicitud.getAgente(),
				admSolicitud.getTelefono(),admSolicitud.getCodigo(), admSolicitud.getFolio()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateRegistro|:"+ex);
		}

		return ok;
	}
	
	public boolean updateEstado(String folio, String estado) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET ESTADO = ? WHERE FOLIO = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {estado, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateEstado|:"+ex);
		}

		return ok;
	}
	
	public boolean updateMatricula(String folio, String matricula) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET MATRICULA = ? WHERE FOLIO = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {matricula, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateMatricula|:"+ex);
		}

		return ok;
	}
	
	public boolean updateCarta(String folio, String carta) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET CARTA = ? WHERE FOLIO = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {carta, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateCarta|:"+ex);
		}

		return ok;
	}

	public boolean updateFecha(String folio) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET FECHA = NOW() WHERE FOLIO = TO_NUMBER(?,'9999999')";		
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateFecha|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateAsesor(String folio, String asesorId) { 
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_SOLICITUD SET ASESOR_ID = ? WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {asesorId, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|updateAsesor|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio) throws Exception{
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public AdmSolicitud mapeaRegId(String folio ) {
		AdmSolicitud objeto = new AdmSolicitud();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = " SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
						+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
						+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
						+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, SOL.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
						+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, SOL.RED_SOCIAL, SOL.FELIGRESIA, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
						+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
						+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
						+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "					 
						+ " AND SOL.FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmSolicitudMapper(), parametros);				
			}					
		} catch (Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public AdmSolicitud mapeaRegMatricula(String matricula ) {
		AdmSolicitud objeto = new AdmSolicitud();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {matricula};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = " SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
						+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
						+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
						+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
						+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
						+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
						+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
						+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "
						+ " AND US.MATRICULA = ?";			
				objeto = enocJdbc.queryForObject(comando, new AdmSolicitudMapper(), parametros);
			}							
		} catch (Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapeaRegMatricula|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeUsuario(String usuario, String clave) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE USUARIO = ? AND CLAVE = ?";			
			Object[] parametros = new Object[] {usuario,clave};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|existeUsuario1|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeUsuario(String usuario ) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE USUARIO = ?";			
			Object[] parametros = new Object[] {usuario};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|existeUsuario2|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String folio = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO), 0)+1 AS MAXIMO FROM SALOMON.ADM_SOLICITUD";			
			folio = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|maximoReg|:"+ex);
		}		
		return folio;
	}
	
	public String getEdad(String folio) {
		String edad	= "1";		
		try{
			String comando = "SELECT FLOOR(MONTHS_BETWEEN(SYSDATE, FECHA_NAC)/12) AS EDAD FROM SALOMON.ADM_SOLICITUD"
					+ " WHERE FOLIO = TO_NUMBER(?, '99999999')";			
			Object[] parametros = new Object[] {folio};
			edad = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getEdad|:"+ex);
		}		
		return edad;
	}
	
	public String getNombre(String folio){
		String nombre = "";		
		try{
			String comando = "SELECT NOMBRE||' '||APELLIDO_MATERNO||' '||APELLIDO_PATERNO AS NOMBRE" +
					" FROM SALOMON.ADM_SOLICITUD" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999999')";
			Object[] parametros = new Object[] {folio};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto(String folio){
		String nombre = "";		
		try{
			String comando = "SELECT NOMBRE FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?, '99999999')";
			Object[] parametros = new Object[] {folio};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
	public String getUsuario(String folio){
		String nombre = "";		
		try{
			String comando = "SELECT USUARIO FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?, '99999999')";
			Object[] parametros = new Object[] {folio};
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getUsuario|:"+ex);
		}		
		return nombre;
	}
	
	public String getUsuarioId(String folio){
		String usuarioId = "0";
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?, '99999999')";
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){			
				comando = "SELECT USUARIO_ID FROM SALOMON.ADM_SOLICITUD WHERE FOLIO = TO_NUMBER(?, '99999999')";			
				 usuarioId = enocJdbc.queryForObject(comando,String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getUsuarioId|:"+ex);
		}		
		return usuarioId;
	}
	
	public String getFolio(String codigoPersonal) {
		String folio = "";		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT FOLIO FROM SALOMON.ADM_SOLICITUD WHERE MATRICULA = ? AND ROWNUM = 1 ORDER BY FECHA DESC";
				folio = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|getFolio|:"+ex);
		}		
		return folio;
	}
	
	public boolean validarCurp(String curp){
 		if (curp==null) curp = "-";
 		curp=curp.toUpperCase().trim();
 		return curp.matches("[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z,0-9]{2}");
	}
	
	public List<AdmSolicitud> listIngreso( String condicion, String orden ){
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
				+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
				+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
				+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, SOL.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
				+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, SOL.RED_SOCIAL, SOL.FELIGRESIA, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
				+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
				+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
				+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "+ condicion + orden;
			lista = enocJdbc.query(comando, new AdmSolicitudMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|listIngreso|:"+ex);
		}		
		return lista;
	}

	public List<AdmSolicitud> listIngreso( String orden ){
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT ASOL.FOLIO, ASOL.NOMBRE, ASOL.APELLIDO_PATERNO,"
				+ " ASOL.APELLIDO_MATERNO, ASOL.CIUDAD_ID, ASOL.ESTADO_ID,"
				+ " ASOL.PAIS_ID, ASOL.NACIONALIDAD, TO_CHAR(ASOL.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
				+ " ASOL.ESTADOCIVIL, ASOL.GENERO, ASOL.RELIGION_ID, ASOL.BAUTIZADO, ASOL.USUARIO,"
				+ " ASOL.CLAVE, TO_CHAR(ASOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, ASOL.MATRICULA, COALESCE(ASOL.ESTADO,'0') AS ESTADO,"
				+ " ASOL.EMAIL, ASOL.ASESOR_ID, ASOL.CURP, ASOL.FECHA_INGRESO, ASOL.AGENTE, ASOL.ASESOR_SEC, ASOL.RED_SOCIAL, ASOL.FELIGRESIA, ASOL.TELEFONO, ASOL.CARTA,CODIGO,"
				+ " ASOL.USUARIO_ID,"
				+ " ASOL.FECHA_BAUTIZO, ASOL.LUGAR_BAUTIZO, ASOL.CULTURAL_ID, ASOL.REGION_ID, ASOL.RES_PAIS_ID, ASOL.RES_ESTADO_ID, ASOL.RES_CIUDAD_ID, ASOL.ACOMODO_ID, ASOL.TIPO, ASOL.NIVEL_ESTUDIO, ASOL.TIPO_APLICANTE, ASOL.PERIODO_ID, ASOL.TIPO_ACOMODO"
				+ " FROM SALOMON.ADM_SOLICITUD ASOL, SALOMON.ADM_PASOS SPAS"
				+ " WHERE SPAS.FOLIO = ASOL.FOLIO"
				+ " AND SPAS.PASOS = '2' AND SPAS.ESTADO = '0' "+ orden;
			lista = enocJdbc.query(comando, new AdmSolicitudMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|listIngreso|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmSolicitud> listRegMes(String year, String mes ){
		
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, SOL.RED_SOCIAL, SOL.FELIGRESIA, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "			
					+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_REGISTRO IS NOT NULL AND TO_CHAR(F_REGISTRO,'YYYY') = ? AND TO_CHAR(F_REGISTRO,'MM') = ?)"
					+ " ORDER BY US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.NOMBRE";					
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(),new Object[] {year,mes});
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listRegMes|:"+ex);
		}
		
		return lista;
	}
	public List<AdmSolicitud> listSolMes(String year, String mes ){
		
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "				
					+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_SOLICITUD IS NOT NULL AND TO_CHAR(F_SOLICITUD,'YYYY') = ? AND TO_CHAR(F_SOLICITUD,'MM') = ?)"
					+ " ORDER BY US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.NOMBRE"; 
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(),new Object[] {year,mes});
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listSolMes|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmSolicitud> listDocMes(String year, String mes ){
		
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "
					+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_DOCUMENTOS IS NOT NULL AND TO_CHAR(F_DOCUMENTOS,'YYYY') = ?  AND TO_CHAR(F_DOCUMENTOS,'MM') = ?)"
					+ " ORDER BY US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.NOMBRE";
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(),new Object[] {year,mes});
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listDocMes|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmSolicitud> listAdmMes(String year, String mes ){		
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO," 
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US "
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID "			
					+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_ADMISION IS NOT NULL AND TO_CHAR(F_ADMISION,'YYYY') = ? AND TO_CHAR(F_ADMISION,'MM') = ?)"
					+ " ORDER BY US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.NOMBRE";
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(),year,mes);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listAdmMes|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmSolicitud> listCarMes(String year, String mes ){		
		List<AdmSolicitud> lista	= new ArrayList<AdmSolicitud>();		
		try{
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
				+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
				+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO,"
				+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
				+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
				+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
				+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US"
				+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID"
				+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PROCESO WHERE F_CARTA IS NOT NULL AND TO_CHAR(F_CARTA,'YYYY') = ? AND TO_CHAR(F_CARTA,'MM') = ?)"
				+ " ORDER BY US.APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE";			
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(),new Object[] {year,mes});
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listCarMes|:"+ex);
		}	
		return lista;
	}
	
	public List<String> listAsesor(){
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando =  " SELECT DISTINCT(ASESOR_ID) FROM SALOMON.ADM_SOLICITUD";
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|listAsesor|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisFechas(String orden){
		List<String> lisFechas = new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(FECHA_INGRESO)AS FECHA FROM SALOMON.ADM_SOLICITUD WHERE ESTADO>=4 AND" +
					" (FECHA_INGRESO LIKE '%'||(TO_CHAR(now(), 'YYYY'))||'%') OR " +
					" (FECHA_INGRESO LIKE '%'||(TO_CHAR(now(), 'YYYY')+1)||'%') "+ orden;
			lisFechas = enocJdbc.queryForList(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AdmSolicitudDao|lisFechas|:"+ex);
		}
		return lisFechas;
	}
	
	public HashMap<String, String> mapaAlumnos (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR"
					+ " FROM SALOMON.ADM_SOLICITUD";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnos|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaSolicitudesVacias(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT FOLIO AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM SALOMON.ADM_SOLICITUD WHERE"
					+ "	FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_ACADEMICO) AND"
					+ "	FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_ESTUDIO) AND"
					+ " FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_DOCALUM) AND"
					+ " FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_ESTUDIO) AND"
					+ " FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_PADRES) AND"
					+ " FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_RECOMIENDA) AND"
					+ " FOLIO NOT IN (SELECT FOLIO FROM SALOMON.ADM_SALUD)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaSolicitudesVacias|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmSolicitud> mapaAlumnosSede (String eventoId){
		HashMap<String, AdmSolicitud> mapa = new HashMap<String, AdmSolicitud>();
		List<AdmSolicitud> lista = new ArrayList<AdmSolicitud>();		
		try {
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO,"
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US"
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID"				
					+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_RESERVADA WHERE EVENTO_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(), parametros);
			for (AdmSolicitud sol : lista ){
				mapa.put(sol.getFolio(), sol);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnosSede|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, AdmSolicitud> mapaAlumnosEnTpt (){
		HashMap<String, AdmSolicitud> mapa = new HashMap<String, AdmSolicitud>();
		List<AdmSolicitud> lista = new ArrayList<AdmSolicitud>();
		try {
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
				+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
				+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO,"
				+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
				+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
				+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
				+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US"
				+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID"
				+ " AND SOL.FOLIO IN (SELECT FOLIO FROM SALOMON.ADM_PRUEBA_ALUMNO)";
			lista = enocJdbc.query(comando, new AdmSolicitudMapper());
			for (AdmSolicitud sol : lista ){
				mapa.put(sol.getFolio(), sol);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnosEnTpt|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AdmSolicitud> mapaAlumnosPorEstado( String estado ){
		HashMap<String, AdmSolicitud> mapa = new HashMap<String, AdmSolicitud>();
		List<AdmSolicitud> lista = new ArrayList<AdmSolicitud>();		
		try {
			String comando =  "SELECT SOL.FOLIO, US.NOMBRE, US.APELLIDO_PATERNO, US.APELLIDO_MATERNO, US.CIUDAD_ID, US.ESTADO_ID,"
					+ " US.PAIS_ID, US.NACIONALIDAD, TO_CHAR(US.FECHA_NAC, 'DD/MM/YYYY') AS FECHA_NAC,"
					+ " US.ESTADOCIVIL, US.GENERO, US.RELIGION_ID, 'X' AS BAUTIZADO, US.CUENTA AS USUARIO,"
					+ " US.CLAVE, TO_CHAR(SOL.FECHA, 'DD-MM-YYYY HH24:MI') AS FECHA, US.MATRICULA, COALESCE(SOL.ESTADO,'0') AS ESTADO,"
					+ " US.EMAIL, SOL.ASESOR_ID, 'X' AS CURP, SOL.FECHA_INGRESO, SOL.AGENTE, SOL.ASESOR_SEC, US.TELEFONO, SOL.CARTA, US.CODIGO, SOL.USUARIO_ID,"
					+ " SOL.FECHA_BAUTIZO, SOL.LUGAR_BAUTIZO, SOL.CULTURAL_ID, SOL.REGION_ID, SOL.RES_PAIS_ID, SOL.RES_ESTADO_ID, SOL.RES_CIUDAD_ID, SOL.ACOMODO_ID, SOL.TIPO, SOL.NIVEL_ESTUDIO, SOL.TIPO_APLICANTE, SOL.PERIODO_ID, SOL.TIPO_ACOMODO"
					+ " FROM SALOMON.ADM_SOLICITUD SOL, SALOMON.ADM_USUARIO US"
					+ " WHERE US.USUARIO_ID = SOL.USUARIO_ID"					
					+ " AND SOL.ESTADO = ?";
			Object[] parametros = new Object[] {estado};
			lista = enocJdbc.query(comando, new AdmSolicitudMapper(), parametros);
			for (AdmSolicitud sol : lista ){
				mapa.put(sol.getFolio(), sol);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|mapaAlumnosPorEstado|:"+ex);
		}
		return mapa;
	}
	
	public boolean existeAsesor(String asesorId ){
		boolean	ok 		= false;	
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ASESOR WHERE ASESOR_ID = ?";
			Object[] parametros = new Object[] {asesorId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|existeReg:"+ex);
		}
		
		return ok;
	}
}