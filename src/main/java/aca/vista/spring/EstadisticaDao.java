// Clase para la vista CARGA_ACADEMICA
package  aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EstadisticaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean existeReg(String codigoPersonal, String cargaId, String bloqueId, String estado){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CARGA_ID = ?"
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
				+ " AND ESTADO = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId, estado};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public Estadistica mapeaReg(String codigoPersonal, String cargaId, String bloqueId ){
		Estadistica estadistica = new Estadistica(); 
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CARGA_ID = ?"
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT * FROM ENOC.ESTADISTICA"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND CARGA_ID = ?"
						+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";
				estadistica = enocJdbc.queryForObject(comando, new EstadisticaMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapeaReg|:"+ex);
		}
		return estadistica;
	}
	
	public Estadistica mapeaReg(String codigoPersonal, String fecha ){
		Estadistica estadistica = new Estadistica();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND F_INSCRIPCION = ?";			
			Object[] parametros = new Object[] {codigoPersonal, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"  
						+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"  
						+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"  
						+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"  
						+ " CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
						+ " FROM ENOC.ESTADISTICA"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND ENOC.ESTADISTICA.F_INSCRIPCION = TO_DATE(?,'DD/MM/YYYY')"
						+ " AND ROWNUM = 1";
				estadistica = enocJdbc.queryForObject(comando, new EstadisticaMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapeaReg|:"+ex+":"+codigoPersonal);
		}
		return estadistica;
	}
	
	public String getFechaMaxima(String codigoPersonal, String fechaInscripcion){
		String fechaMaxima = "01/01/2000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL = ? AND F_INSCRIPCION != TO_DATE(?,'DD/MM/YYYY')";			
			Object[] parametros = new Object[] {codigoPersonal, fechaInscripcion};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = " SELECT COALESCE(TO_CHAR(MAX(F_INSCRIPCION),'DD/MM/YYYY'),'01/01/2000')"
						+ " FROM ENOC.ESTADISTICA"
						+ " WHERE CODIGO_PERSONAL = ? AND F_INSCRIPCION != TO_DATE(?,'DD/MM/YYYY')";
				fechaMaxima = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getFechaMaxima|:"+ex);
		}
		return fechaMaxima;
	}
	
	public String getFechaMaximaPorPlan(String codigoPersonal, String planId){
		String fechaMaxima = "01/01/2000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = " SELECT COALESCE(TO_CHAR(MAX(F_INSCRIPCION),'DD/MM/YYYY'),'01/01/2000')"
						+ " FROM ENOC.ESTADISTICA"
						+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
				fechaMaxima = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getFechaMaximaPorPlan|:"+ex);
		}
		return fechaMaxima;
	}
	
	public String getPlanEnCarga( String cargaId, String codigoAlumno){		
		String planId ="0";
		List<String> lista = new ArrayList<String>();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {cargaId, codigoAlumno};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT PLAN_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ? ORDER BY F_INSCRIPCION DESC";
				lista 	= enocJdbc.queryForList(comando,String.class,parametros);
				planId 	= lista.get(0); 
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getPlanEnCarga|:"+ex);
		}
		
		return planId;
	}
	
	public String getCarreraDeFecha(String codigoPersonal, String fechaInscripcion){
		String carrera = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ESTADISTICA WHERE CODIGO_PERSONAL = ? AND F_INSCRIPCION = TO_DATE(?,'DD/MM/YYYY')";			
			Object[] parametros = new Object[] {codigoPersonal, fechaInscripcion};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = " SELECT COALESCE(CARRERA_ID,'0')"
						+ " FROM ENOC.ESTADISTICA"
						+ " WHERE CODIGO_PERSONAL = ? AND F_INSCRIPCION = TO_DATE(?,'DD/MM/YYYY')";
				carrera = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getCarreraDeFecha|:"+ex);
		}
		return carrera;
	}
	
	public List<Estadistica> lisInscritosEnCarga( String cargaId, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();		
		try{
			String comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"					
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') AS F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " RESIDENCIA_ID,"					
					+ " SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD, F_INSCRIPCION"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisInscritosEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisEnBoletas( String cargaId, String carreraId, String rangoIni, String rangoFin, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();		
		try{
			String comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"					
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') AS F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " RESIDENCIA_ID,"					
					+ " SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD, F_INSCRIPCION"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARRERA_ID = ?"
					+ " AND CODIGO_PERSONAL BETWEEN ? AND ? "+orden;
			Object[] parametros = new Object[] {cargaId, carreraId, rangoIni, rangoFin};
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisEnBoletas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisPorCargayMateria( String cargaId, String materia, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
				+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, CARRERA_ID, RELIGION_ID, RESIDENCIA_ID, SEXO, "
				+ " PLAN_ID, FACULTAD_ID, MODALIDAD_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD, F_INSCRIPCION"
				+ " FROM ENOC.ESTADISTICA"
				+ " WHERE CARGA_ID = ?"
				+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6)= ? AND SUBSTR(CURSO_ID,9,7) = ? AND TIPOCAL_ID != 'M') "+orden;
			Object[] parametros = new Object[] {cargaId, cargaId, materia};
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisInscritosEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisExtranjerosEnCarga( String cargaId, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();
		try{
			String comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, "
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') AS F_NACIMIENTO, "
					+ " CARRERA_ID, "
					+ " RELIGION_ID,"
					+ " RESIDENCIA_ID, "					
					+ " SEXO, "
					+ " PLAN_ID,"
					+ " FACULTAD_ID, "
					+ " MODALIDAD_ID, "
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD, F_INSCRIPCION "
					+ " FROM ENOC.ESTADISTICA"					
					+ " WHERE CARGA_ID = ?"
					+ " AND NACIONALIDAD != 91 " + orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisExtranjerosEnCarga|: "+ex);
		}
		
		return lista;
	}

	public List<Estadistica> lisInscritosEnCargas(String cargas, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();		
		try{
			String comando= "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, F_NACIMIENTO, F_INSCRIPCION FROM ESTADISTICA" + 
				" WHERE CARGA_ID IN ("+cargas+") AND NACIONALIDAD != 91 " + 
				" AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM LEG_EXTDOCTOS WHERE CODIGO = ESTADISTICA.CODIGO_PERSONAL AND IDDOCUMENTO = 2 AND FECHA_VENCE > TO_DATE('01/01/2020','DD/MM/YYYY')) "+orden;			
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisInscritosEnCarga|:"+ex);
		}
		
		return lista;
	}
	
	public String lisCargasEntreFechas(String fechaIni, String fechaFin){
		List<String> lista	= new ArrayList<String>();
		String cargas 		= "";
		try{
			String comando = " SELECT DISTINCT(CARGA_ID) AS CARGA_ID FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " ORDER BY CARGA_ID";
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String carga : lista) {
				if (cargas.length() > 1) {
					cargas+=",'"+carga+"'";
				}else {
					cargas+="'"+carga+"'";
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|listCargasEntreFechas|:"+ex);
		}
		
		return cargas;
	}
	
	public List<String> listCargasEntreFechas(String fechaIni, String fechaFin){
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CARGA_ID) AS CARGA_ID FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " ORDER BY CARGA_ID";
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			lista = enocJdbc.queryForList(comando, String.class, parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|listCargasEntreFechas|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> listModalidadesEntreFechas(String fechaIni, String fechaFin){
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT CARGA_ID||'$$'||MODALIDAD_ID FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " GROUP BY CARGA_ID, MODALIDAD_ID"
					+ " ORDER BY CARGA_ID, MODALIDAD_ID";
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			lista = enocJdbc.queryForList(comando, String.class, parametros);					
					
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|listModalidadesEntreFechas|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisModalidadesPorFechas(String modalidades, String fechaIni, String fechaFin, String orden){

		List<Estadistica> lista = new ArrayList<Estadistica>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,"
					+ " NOMBRE_LEGAL,COTEJADO,TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper(),fechaIni,fechaFin); ///
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllModalidadesPorFechas|:" + ex);

		}

		return lista;
	}
	
	public List<Estadistica> getListReprobados( String cargaId, String orden ){
		List<Estadistica> lista	= new ArrayList<Estadistica>();
		try{
			String comando = "SELECT"+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO," +
				" NOMBRE_LEGAL,COTEJADO,TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
				" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
				" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
				" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"+
				" FROM ENOC.ESTADISTICA " +
				" WHERE CARGA_ID IN ("+cargaId+")" +
				" AND  ( SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT "+ 
				" 		WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL"+
				" 		AND SUBSTR(CURSO_CARGA_ID,1,6) = "+cargaId+ 
				" 		AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('3','4','6')"+
				" 		AND TIPOCAL_ID='2') > 0 "+ orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getListReprobados|:"+ex);
		}		
		return lista;
	}
	
	public List<Estadistica> lisExtInscModalidad(String modalidades, String orden){		
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+") "+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisExtInscModalidad|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisExtInscModalidad(String modalidades, String fechaIni, String fechaFin, String orden){		
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+")"+
			" AND ENOC.ESTADISTICA.F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper(),fechaIni,fechaFin);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisExtInscModalidad|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisExtInscritosOrigen(String modalidades, String fechaIni, String fechaFin, String orden){		
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE PAIS_ID != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+")"+
			" AND ENOC.ESTADISTICA.F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper(),fechaIni,fechaFin);///
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisExtInscModalidad|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisInscritosSE(String cargaId, String orden ){
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT "+
			"CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			"TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"+
			"SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			"CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			"CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			"FROM ENOC.ESTADISTICA " +			
			"WHERE CARGA_ID IN ("+cargaId+") "+orden;		
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisInscritosSE|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisInscritosSE(String cargaId, String modalidades, String orden ){
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
			+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
			+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
			+ " FROM ENOC.ESTADISTICA"			
			+ " WHERE CARGA_ID IN ("+cargaId+")"
			+ " AND MODALIDAD_ID IN ("+modalidades+") "+orden;		
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisInscritosSE|:"+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> getListExtranjerosInscritosModalidad(String modalidades, String orden){
		
		List<Estadistica> lista	= new ArrayList<Estadistica>();
		
		try{
			String comando = "SELECT "+
			" CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" TO_CHAR(F_NACIMIENTO,'YYYY/MM/DD') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" AND MODALIDAD_ID IN ("+modalidades+")"+
			" AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaUtil|getListExtranjerosInscritos|:"+ex);
		}		
		return lista;
	}
	
	public List<Estadistica> getListaExtranjerosInscritosCargaYModalidad(String cargas, String modalidades, String orden){
		List<Estadistica> lista	= new ArrayList<Estadistica>();		
				
		try{
			String comando = "SELECT "+
			" 	CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"+
			" 	TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" 	SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"+
			" 	CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID," +
			" 	CARRERA_ID, FACULTAD_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "+
			" FROM ENOC.ESTADISTICA "+
			" WHERE NACIONALIDAD != 91 " +
			" 	AND MODALIDAD_ID IN ("+modalidades+")"+
			" 	AND CARGA_ID IN ("+cargas+") " + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaUtil|getListaExtranjerosInscritosCargaYModalidad|:"+ex);
		}
		return lista;
	}
	
	public List<Estadistica> getListInscritosCargaCarrera(String cargaId, String carreraId, String orden){
		List<Estadistica> listaEstadistica	= new ArrayList<Estadistica>();		
				
		try{
			String comando = "SELECT "+
			" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO," +
			" APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, "+
			" TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "+
			" SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, "+
			" CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID, " +
			" CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID, ENOC.ALUMNO_CICLO_HIST(CODIGO_PERSONAL, CARGA_ID, PLAN_ID) "+
			" FROM ENOC.ESTADISTICA " +			
			" WHERE CARGA_ID IN ('"+cargaId+"') AND CARRERA_ID = ? " +orden;
			listaEstadistica = enocJdbc.query(comando, new EstadisticaMapper(),carreraId);///
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstadisticaDao|getListInscritosCargaCarrera|:"+ex);
		}
		return listaEstadistica;
	}
	
	public List<Estadistica> listInscritosCargasModalidadesFechas(String cargas, String modalidades, String fechaIni, String fechaFin, String orden ){
		List<Estadistica> lisEstadistica = new ArrayList<Estadistica>();
		
		try{
			String comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID "
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+") "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+ orden;
			System.out.println(comando);
			lisEstadistica = enocJdbc.query(comando, new EstadisticaMapper(),fechaIni,fechaFin);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|listInscritosCargasModalidadesFechas|:"+ex);
		}
		
		return lisEstadistica;
	}
	
	public List<Estadistica> getListRepetidos(String cargas, String modalidades, String fechaIni, String fechaFin, String orden ){
		List<Estadistica> lista	= new ArrayList<Estadistica>();
		
		try{
			String comando = " SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL, NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,ESTADO,PLAN_ID,"
					+ " CARRERA_ID, FACULTAD_ID, F_INSCRIPCION, CRED_BAJA, CRED_ALTA, TIPOALUMNO_ID"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND CODIGO_PERSONAL IN"
					+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "
					+ " 	WHERE CARGA_ID IN ("+cargas+")"
					+ "		AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ "		GROUP BY CODIGO_PERSONAL" +
			" 	HAVING COUNT(CODIGO_PERSONAL) >= 2) "+ orden;
			
			lista = enocJdbc.query(comando, new EstadisticaMapper(),fechaIni,fechaFin,fechaIni,fechaFin);///
	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getListRepetidos|:"+ex);
		}
		
		return lista;
	}
	
	public List<aca.Mapa> lisAlumnosPorPlan(String cargaId, String orden ){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND CODIGO_PERSONAL IN"
					+ " ( SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL"
					+ " AND PLAN_ID = ESTADISTICA.PLAN_ID"
					+ " AND PRIMER_MATRICULA = ESTADISTICA.F_INSCRIPCION)"
					+ " GROUP BY PLAN_ID "+ orden;			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisAlumnosPorPlan|:"+ex);
		}		
		return lista;
	}	
	
	public List<aca.Mapa> lisAlumnosEnCorte(String cargaId, String orden ){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND CODIGO_PERSONAL IN"
					+ " ( SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ESTADISTICA.CODIGO_PERSONAL"
					+ " AND PLAN_ID = ESTADISTICA.PLAN_ID"
					+ " AND PRIMER_MATRICULA = ESTADISTICA.F_INSCRIPCION) "+ orden;			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisAlumnosEnCorte|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapAlumnosPorCarga(String fechaIni, String fechaFin ){
		
		HashMap<String, String> mapa = new HashMap<String, String>();	
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " GROUP BY CARGA_ID";	
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapAlumnosPorCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumnosModalidadPorCarga(String fechaIni, String fechaFin ){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID||MODALIDAD_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"					
					+ " GROUP BY CARGA_ID,MODALIDAD_ID";
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapAlumnosModalidadPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaModalidadesEnCargas(String cargas){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT MODALIDAD_ID AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN("+cargas+") GROUP BY MODALIDAD_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaModalidadesEnCargas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaFechaInicialCargas(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, MIN(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS VALOR FROM ENOC.ESTADISTICA GROUP BY CARGA_ID";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaFechaInicialCargas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaFechaFinalCargas(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, MAX(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS VALOR FROM ENOC.ESTADISTICA GROUP BY CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaFechaFinalCargas|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaGrupo(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ALUM_GRUPO(CODIGO_PERSONAL, CARGA_ID) AS VALOR"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN(SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(now(), 'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL)";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaGrupo|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaPrimerInscripcion(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, MIN(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS VALOR FROM ENOC.ESTADISTICA GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaCargasInscripcion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaUltimaInscripcion(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	     = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, MAX(TO_CHAR(F_INSCRIPCION,'YYYY/MM/DD')) AS VALOR FROM ENOC.ESTADISTICA GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaUltimaInscripcion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaTotalAlumnosPorCarrera( String fechaIni, String fechaFin ) {
		HashMap<String,String> mapa = new HashMap<String,String>();		
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
	
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ESTADISTICA WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaTotalAlumnosPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalInscritosPorCarga(String cargas, String modalidades, String fechaIni, String fechaFin, String orden){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try {
			String comando  = " SELECT A.CARGA_ID AS LLAVE,"
					+ " (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B"
					+ "  WHERE B.CARGA_ID = A.CARGA_ID AND MODALIDAD_ID IN ("+modalidades+")"
					+ "  AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " ) AS VALOR"
					+ " FROM ENOC.CARGA A"
					+ " WHERE CARGA_ID IN ("+cargas+") "+orden;
			Object[] parametros = new Object[] {fechaIni, fechaFin};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map: lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaTotalInscritosPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapIngresoFac(String cargaId, String modalidades, String fechaIni, String fechaFin, String estado, String tipo){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT FACULTAD_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(? ,'DD/MM/YYYY')";
			
			if (tipo.equals("UM")) 
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'" 
					+ " GROUP BY FACULTAD_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId,fechaIni,fechaFin);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapIngresoFac|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapIngresoCarrera(String cargaId, String modalidades, String fechaIni, String fechaFin, String estado, String tipo){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
			
			if (tipo.equals("UM")) 
				comando = comando + " AND ENOC.ALUM_INGRESO_UM(CODIGO_PERSONAL) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			else
				comando = comando + " AND ENOC.ALUM_INGRESO_PLAN(CODIGO_PERSONAL, PLAN_ID) = TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY')";
			
			comando = comando + " AND ESTADO = '"+estado+"'"
					+ " GROUP BY CARRERA_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId,fechaIni,fechaFin);
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapIngresoCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaInscEstadoyCarrera(String cargaId, String modalidades, String estado){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT PAIS_ID||ESTADO_ID||CARRERA_ID AS LLAVE, COALESCE(COUNT(*),0) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = ?"
					+ " GROUP BY PAIS_ID, ESTADO_ID, CARRERA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId,estado);
			for(aca.Mapa map: lista) {
				mapa.put(map.getLlave(), map.getValor());
			}

			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaInscritosPorEstado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaInscEstado(String cargaId, String modalidades, String estado){
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT PAIS_ID||ESTADO_ID AS LLAVE, COALESCE(COUNT(*),0) AS VALOR FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ESTADO = ?"
					+ " GROUP BY PAIS_ID, ESTADO_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId,estado);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaInscritosPorEstado|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String, String> mapaCarreraCarga(String cargaId){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaCarreraCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosPorCarrera(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ESTADISTICA WHERE CARGA_ID = ? GROUP BY CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaAlumnosPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumnosConNotasCerradas(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT DISTINCT(KCA.CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT KCA, CARGA_GRUPO CG WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ? AND CG.CURSO_CARGA_ID = KCA.CURSO_CARGA_ID AND CG.ESTADO IN ('1','2'))"
					+ " GROUP BY CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, cargaId);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|mapaAlumnosConNotasCerradas|:"+ex);
		}
		
		return mapa;
	}
	
	public List<Estadistica> getList( String cargas, String orden){
		List<Estadistica> lista = new ArrayList<Estadistica>();
		try{
			String comando="SELECT "+
					"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+					
					"CARGA_ID, BLOQUE_ID, "+
					"NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, "+
					"TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO, "+
					"CARRERA_ID, "+
					"RELIGION_ID,"+
					"CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID, " +
					"CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO, "+
					"PLAN_ID,"+
					"FACULTAD_ID, "+
					"MODALIDAD_ID, "+
					"PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD, F_INSCRIPCION "+
					"FROM ENOC.ESTADISTICA "+
					"WHERE CARGA_ID IN("+cargas+") "+
					"ORDER BY CARRERA_ID" + orden;
			lista = enocJdbc.query(comando, new EstadisticaMapperCorto());
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|getList|: "+ex);
		}
		
		return lista;
	}
	
	public List<Estadistica> lisCargaModalidadesFechasYResidencia(String cargaId, String modalidadId, String fechaIni, String fechaFin, String residencia, String nacionalidad, String tipoAlumno, String order) {
		List<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		try{
			String comando="SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID, BLOQUE_ID,"
					+ " NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD-MM-YYYY') F_NACIMIENTO,"
					+ " CARRERA_ID,"
					+ " RELIGION_ID,"
					+ " CASE RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA_ID,"
					+ " CASE SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO,"
					+ " PLAN_ID,"
					+ " FACULTAD_ID,"
					+ " F_INSCRIPCION,"
					+ " MODALIDAD_ID,"
					+ " PAIS_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, TIPOALUMNO_ID, NACIONALIDAD"
					+ " FROM ENOC.ESTADISTICA "
					+ " WHERE CARGA_ID = ?"
					+ " AND MODALIDAD_ID IN ("+modalidadId+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
		//			+ " "+residencia+" "+nacionalidad+" "+tipoAlumno+" "+order;
					+ " "+order;
			
			lisEstadistica = enocJdbc.query(comando, new EstadisticaMapperCorto(),cargaId,fechaIni,fechaFin);///
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.EstadisticaDao|lisCargaModalidadesFechasYResidencia|: "+ex);
		}
		
		return lisEstadistica;
	}
	
	public int[] estadisticaPorCarga (String cargas, String modalidades, String fechaIni, String fechaFin){
		int res[] = new int[9];
		
		String codigo = "x";
		List<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		
		try {
			String comando = " SELECT * FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			lisEstadistica = enocJdbc.query(comando, new EstadisticaMapperCorto());
			for(Estadistica est: lisEstadistica) {
				if(!codigo.equals(est.getCodigoPersonal())) {
					
					codigo = est.getCodigoPersonal();
					
					if (est.getResidenciaId().equals("I")) 		res[0]++; else res[1]++;
					if (est.getSexo().equals("M")) 				res[2]++; else res[3]++;
					if (est.getNacionalidad().equals("153"))	res[4]++; else res[5]++;
					if (est.getClasFin().equals("1")) 			res[6]++; else res[7]++;	
				}
			}
			
			comando = "SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID)) AS CREDITOS FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")";
			int creditos = enocJdbc.queryForObject(comando, Integer.class);
			res[8] = creditos;
			
		}catch(Exception ex) {
			System.out.println("Error - aca.vista.spring.EstadisticaDao|estadisticaPorCarga|: "+ex);
		}
		
		return res;
	}
	
	public int[] estadisticaPorCarga (String cargas, String modalidades, String fechaIni, String fechaFin, String nacionalidad){
		int res[] = new int[9];
		
		String codigo = "x";
		List<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
		
		try {
			String comando = " SELECT * FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
			lisEstadistica = enocJdbc.query(comando, new EstadisticaMapperCorto());
			for(Estadistica est: lisEstadistica) {
				if(!codigo.equals(est.getCodigoPersonal())) {
					
					codigo = est.getCodigoPersonal();
					
					if (est.getResidenciaId().equals("I")) 		res[0]++; else res[1]++;
					if (est.getSexo().equals("M")) 				res[2]++; else res[3]++;
					if (est.getNacionalidad().equals(nacionalidad))	res[4]++; else res[5]++;
					if (est.getClasFin().equals("1")) 			res[6]++; else res[7]++;	
				}
			}
			
			comando = "SELECT SUM(ENOC.ALUM_CRED_CARGA_MOD(CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID)) AS CREDITOS FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")";
			int creditos = enocJdbc.queryForObject(comando, Integer.class);
			res[8] = creditos;
			
		}catch(Exception ex) {
			System.out.println("Error - aca.vista.spring.EstadisticaDao|estadisticaPorCarga|: "+ex);
		}
		
		return res;
	}
	
	public int totalInscritosPorCarga (String cargas, String modalidades, String fechaIni, String fechaFin) {
		int total = 0;
		
		try {
			String comando = " SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
			total = enocJdbc.queryForObject(comando, Integer.class, fechaIni, fechaFin);
		}catch(Exception ex) {
			System.out.println("Error - aca.vista.spring.EstadisticaDao|totalInscritosPorCarga|: "+ex);
		}
		return total;
	}
	
	public int totalCalculoCorbroPorCarga (String cargas, String modalidades, String fechaIni, String fechaFin) {
		int total = 0;
		
		try {
			String comando	= "SELECT COUNT(CODIGO_PERSONAL) AS NUM_COBROS FROM ALUM_ESTADO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND ESTADO = 'M'"
					+ " AND CODIGO_PERSONAL IN ("
					+ "	SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID, 1, 6) IN ("+cargas+") AND TIPOCAL_ID = 'M')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")";
			total = enocJdbc.queryForObject(comando, Integer.class, fechaIni, fechaFin);
		}catch(Exception ex) {
			System.out.println("Error - aca.vista.spring.EstadisticaDao|totalCalculoCorbroPorCarga|: "+ex);
		}
		return total;
	}
	
	public int totalCargaMateriasPorCarga (String cargas, String modalidades, String fechaIni, String fechaFin) {
		int total = 0;
		
		try {
			String 	comando	=	"SELECT"+ 
					" COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_CARGAS" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargas+")" +
					" AND CODIGO_PERSONAL NOT IN "+ 
				  		"(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")) "+
				  	" AND TIPOCAL_ID = 'M'";
			total = enocJdbc.queryForObject(comando, Integer.class, fechaIni, fechaFin);
		}catch(Exception ex) {
			System.out.println("Error - aca.vista.spring.EstadisticaDao|totalCargaMateriasPorCarga|: "+ex);
		}
		return total;
	}
	
}