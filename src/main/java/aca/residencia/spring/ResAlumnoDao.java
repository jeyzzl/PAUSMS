package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResAlumno objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.RES_ALUMNO (MATRICULA, FOLIO, FECHA_INICIO, FECHA_FINAL, CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, "
					+ " TUT_APELLIDOS, RAZON, USUARIO, FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO) "
					+ " VALUES(?, TO_NUMBER(?,'999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, TO_NUMBER(?,'99'), ?, "
					+ " NOW(), ?, TO_NUMBER(?,'99999'), ?, ?, TO_NUMBER(?,'9'))";
			Object[] parametros = new Object[] {
				objeto.getMatricula(),objeto.getFolio(), objeto.getFechaInicio(), objeto.getFechaFinal(), objeto.getCalle(),objeto.getColonia(),objeto.getMunicipio(),
				objeto.getTelArea(),objeto.getTelNum(),objeto.getTutNombre(),objeto.getTutApellidos(),objeto.getRazon(),objeto.getUsuario(),objeto.getNumero(),
				objeto.getPeriodoId(),objeto.getEsPermanente(),objeto.getResidenciaId(),objeto.getDormitorio()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ResAlumno objeto ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.RES_ALUMNO" 
				+ " SET CALLE = ?,"
				+ " COLONIA = ?,"
				+ " MUNICIPIO = ?,"
				+ " TEL_AREA = ?,"
				+ " TEL_NUMERO = ?,"
				+ " TUT_NOMBRE = ?,"
				+ " TUT_APELLIDOS = ?,"
				+ " RAZON = TO_NUMBER(?,'99'),"
				+ " USUARIO = ?,"
				+ " FECHA = NOW(),"
				+ " NUMERO = ?,"				
				+ " ESPERMANENTE = ?,"			
				+ " RESIDENCIA_ID = ?,"				
				+ " DORMITORIO = TO_NUMBER(?,'9'),"
				+ " PERIODO_ID = TO_NUMBER(?,'99999'),"
				+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
				+ " FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY')"
				+ " WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {
				objeto.getCalle(),objeto.getColonia(),objeto.getMunicipio(),objeto.getTelArea(),objeto.getTelNum(),objeto.getTutNombre(),objeto.getTutApellidos(),
				objeto.getRazon(),objeto.getUsuario(),objeto.getNumero(),objeto.getEsPermanente(),objeto.getResidenciaId(),objeto.getDormitorio(),
				objeto.getPeriodoId(), objeto.getFechaInicio(), objeto.getFechaFinal(), objeto.getMatricula(), objeto.getFolio()
			};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(String matricula, String folio){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, folio};
			if (enocJdbc.update(comando, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public ResAlumno mapeaRegId(String matricula, String folio) {
		ResAlumno objeto = new ResAlumno();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula,folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
						+ " CALLE, COLONIA, MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO,"
						+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO"
						+ " FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
				objeto = enocJdbc.queryForObject(comando, new ResAlumnoMapper(), parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.ResAlumnoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String matricula, String folio){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String matricula) {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.RES_ALUMNO WHERE MATRICULA = ?";			
			maximo = enocJdbc.queryForObject(comando, String.class, new Object[] {matricula});
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getRazon(String matricula, String folio){
		String razon = "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {matricula, folio};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT RAZON FROM ENOC.RES_ALUMNO WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')";
				razon = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|getRazon|:"+ex);
		}
		
		return razon;
	}
	
	public List<ResAlumno> lisTodos(String orden ){
		
		List<ResAlumno> lista = new ArrayList<ResAlumno>();	
		try{
			String comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, CALLE, COLONIA, "
					+ " MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO"
					+ " FROM ENOC.RES_ALUMNO "+orden; 
			lista = enocJdbc.query(comando, new ResAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResAlumno> lisPorAlumno(String codigoAlumno, String orden ){
		
		List<ResAlumno> lista = new ArrayList<ResAlumno>();	
		try{
			String comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, CALLE, COLONIA, "
					+ " MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO"
					+ " FROM ENOC.RES_ALUMNO "
					+ " WHERE MATRICULA = ? "+orden; 
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new ResAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|lisPorAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResAlumno> lisPorPeriodo(String periodoId, String orden ){
		
		List<ResAlumno> lista = new ArrayList<ResAlumno>();	
		try{
			String comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, CALLE, COLONIA, "
					+ " MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO"
					+ " FROM ENOC.RES_ALUMNO "
					+ " WHERE PERIODO_ID = ? "+orden; 
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new ResAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|lisPorPeriodo|:"+ex);
		}
		
		return lista;
	}

	public List<ResAlumno> lisPorPeriodoRazon(String periodoId, String razonId){
		
		List<ResAlumno> lista = new ArrayList<ResAlumno>();	
		try{
			String comando = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, CALLE, COLONIA, "
					+ " MUNICIPIO, TEL_AREA, TEL_NUMERO, TUT_NOMBRE, TUT_APELLIDOS, RAZON, USUARIO, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, NUMERO, PERIODO_ID, ESPERMANENTE, RESIDENCIA_ID, DORMITORIO"
					+ " FROM ENOC.RES_ALUMNO "
					+ " WHERE PERIODO_ID = ? AND RAZON = ?"; 
			Object[] parametros = new Object[] {periodoId,razonId};
			lista = enocJdbc.query(comando, new ResAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|lisPorPeriodoRazon|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaPorPeriodo(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.RES_ALUMNO GROUP BY PERIODO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|mapaPorPeriodo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaActualizados(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(*) AS VALOR FROM RES_ALUMNO WHERE MATRICULA||RESIDENCIA_ID IN (SELECT CODIGO_PERSONAL||RESIDENCIA_ID FROM ALUM_ACADEMICO) GROUP BY PERIODO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|mapaActualizadosPorPeriodo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaActualizados(String periodoId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, RESIDENCIA_ID AS VALOR FROM ENOC.RES_ALUMNO"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'99999')"
					+ " AND MATRICULA||RESIDENCIA_ID IN (SELECT CODIGO_PERSONAL||RESIDENCIA_ID FROM ENOC.ALUM_ACADEMICO)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.ResAlumnoDao|mapaActualizados|:"+ex);
		}
		
		return mapa;
	}
	
	
}