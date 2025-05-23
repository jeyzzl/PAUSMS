// Clase para la tabla de Alum_Academico
package aca.alumno.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlumAcademicoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired 
	AlumPersonalDao alumPersonalDao;
	
	public boolean insertReg( AlumAcademico academico ) {
		boolean ok 				= false;
		
		try{
			if (academico.getResidenciaId()==null || academico.getResidenciaId().equals("")|| academico.getResidenciaId().equals(" ")) academico.setResidenciaId("I");
			
			AlumPersonal alumno 	= new AlumPersonal();
			alumno = alumPersonalDao.mapeaRegId(academico.getCodigoPersonal());
			if(alumno.getReligionId().equals("1")) 
				academico.setClasFin("1"); 
			else  
				academico.setClasFin("2");
			
			String comando = "INSERT INTO ENOC.ALUM_ACADEMICO"+ 
				" (CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				" RESIDENCIA_ID, DORMITORIO, F_SOLICITUD, F_ADMISION, F_ALTA, "+
				" MODALIDAD_ID, EXTENSION_ID, PREPA_LUGAR, ACOMODO_ID) "+
				" VALUES( ?, "+
				" TO_NUMBER(?,'99'), "+
				" TO_NUMBER(?,'99'), "+				
				" ?, "+
				" TO_NUMBER(?,'999'), "+
				" ?, "+
				" ?, "+				
				" TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), "+
				" TO_DATE(?,'DD/MM/YYYY'), "+
				" TO_DATE(?,'DD/MM/YYYY'), "+				
				" TO_NUMBER(?,'99'), "+
				" TO_NUMBER(?,'99'),"+
				" TO_NUMBER(?,'99'),"+
				" TO_NUMBER(?))";					
			Object[] parametros = new Object[] {
					academico.getCodigoPersonal(),academico.getTipoAlumno(),academico.getClasFin(),
					academico.getObrero(),academico.getObreroInstitucion(),academico.getResidenciaId(),
					academico.getDormitorio(),academico.getFSolicitud(),academico.getFAdmision(),
					academico.getFAlta(),academico.getModalidadId(),academico.getExtensionId(),
					academico.getPrepaLugar(),academico.getAcomodoId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AlumAcademico academico ) {
		boolean ok = false;
		
		try{
			
			AlumPersonal alumno = new AlumPersonal();			
			alumno = alumPersonalDao.mapeaRegId(academico.getCodigoPersonal());		
			if(alumno.getReligionId().equals("1"))
				academico.setClasFin("1");
			else
				academico.setClasFin("2");
			
			String comando = "UPDATE ENOC.ALUM_ACADEMICO "+ 
				" SET "+
				" TIPO_ALUMNO = TO_NUMBER(?,'99'), "+
				" CLAS_FIN = TO_NUMBER(?,'99'), "+
				" OBRERO = ?, "+
				" OBRERO_INSTITUCION = TO_NUMBER(?,'999'), "+
				" RESIDENCIA_ID = ?, "+
				" DORMITORIO = ?, "+
				" F_SOLICITUD = TO_DATE(?,'DD/MM/YYYY'), "+
				" F_ADMISION = TO_DATE(?,'DD/MM/YYYY'), "+
				" F_ALTA = TO_DATE(?,'DD/MM/YYYY'), "+
				" MODALIDAD_ID = TO_NUMBER(?,'99'), "+
				" EXTENSION_ID = TO_NUMBER(?,'99'), "+
				" PREPA_LUGAR = TO_NUMBER(?,'99'), "+
				" REQUERIDO = ?, "+
				" NIVEL_INICIO_ID = TO_NUMBER(?, '99'),"+
				" ACOMODO_ID = TO_NUMBER(?)"+
				" WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					academico.getTipoAlumno(),academico.getClasFin(),academico.getObrero(),
					academico.getObreroInstitucion(),academico.getResidenciaId(),academico.getDormitorio(),
					academico.getFSolicitud(),academico.getFAdmision(),academico.getFAlta(),
					academico.getModalidadId(),academico.getExtensionId(),academico.getPrepaLugar(),academico.getRequerido(),academico.getNivelInicioId(),academico.getAcomodoId(),
					academico.getCodigoPersonal()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateClasFin( String codigoPersonal, String clasFin) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ALUM_ACADEMICO SET CLAS_FIN = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {clasFin, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|updateClasFin|:"+ex);		
		}
		
		return ok;
	}	
	
	
	public boolean updateResidencia(  String codigoPersonal, String res) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_ACADEMICO SET RESIDENCIA_ID = ? WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {res, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|updateResidencia|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateResidencia(  String codigoPersonal, String residencia, String dormitorio, String requerido, String acomodo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_ACADEMICO SET RESIDENCIA_ID = ?, DORMITORIO = ?, REQUERIDO = ?, ACOMODO_ID = TO_NUMBER(?) WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {residencia, dormitorio, requerido, acomodo, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|updateResidencia|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public AlumAcademico mapeaRegId(  String codigoPersonal ) {
		AlumAcademico objeto = new AlumAcademico();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1) {				
				comando = "SELECT "+
					"CODIGO_PERSONAL, TIPO_ALUMNO, COALESCE(CLAS_FIN,1) CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
					"COALESCE(RESIDENCIA_ID,'I') RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO, "+
					"COALESCE(TO_CHAR(F_SOLICITUD,'DD/MM/YYYY'),'01/01/1900') AS F_SOLICITUD, "+
					"COALESCE(TO_CHAR(F_ADMISION,'DD/MM/YYYY'),'01/01/1900') AS F_ADMISION, "+
					"COALESCE(TO_CHAR(F_ALTA,'DD/MM/YYYY'),'01/01/1900') F_ALTA, MODALIDAD_ID, "+
					"EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID,ACOMODO_ID "+
					"FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";
				objeto = enocJdbc.queryForObject(comando, new AlumAcademicoMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO "+ 
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean esInscrito( String codigoPersonal ){
 		boolean ok 				= false;
 		
 		try{
 			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? ";				
 			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|esInscrito|:"+ex);
 		}
 		return ok;
 	}
	
	public String getTipoAlumno(String codigoPersonal) {
		String tipo				= "";		
		try{
			String comando = "SELECT B.NOMBRE_TIPO FROM ENOC.ALUM_ACADEMICO A, ENOC.CAT_TIPOALUMNO B"
					+ " WHERE CODIGO_PERSONAL = ? AND A.TIPO_ALUMNO = B.TIPO_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			tipo = enocJdbc.queryForObject(comando,String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getTipoAlumno|:"+ex);
		}
		
		return tipo;
	}
	
	public String getClasFinAlumno(String codigoPersonal) {
		String clasFin = "2";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ? AND RELIGION_ID = 1";
			Object[] parametros = new Object[] {codigoPersonal};				
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >=1 ){
				clasFin = "1";
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getClasFinAlumno|:"+ex);
		}		
		return clasFin;
	}
	
	public String getResidencia( String codigoPersonal) {
		String res = "X";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT RESIDENCIA_ID FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
				res = enocJdbc.queryForObject(comando,String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getResidencia|:"+ex);			
		}		
		return res;
	}
	
	public String getRequerido(String codigoPersonal){		
		String	res	= ""; 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT REQUERIDO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";		
				res = enocJdbc.queryForObject(comando,String.class, parametros);				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getRequerido|:"+ex);
		}
		
		return res;
	}
	
	public String getDormi( String codigoPersonal){
		String res = "";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				comando = "SELECT COALESCE(TRIM(DORMITORIO),'X') AS DORMI FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
				res = enocJdbc.queryForObject(comando,String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getDormi|:"+ex);
		}		
		return res;
	}
	
	public String getModalidad( String codigoPersonal) {
		String modalidad = "x";		
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1) {
				comando = "SELECT ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";				
				modalidad = enocJdbc.queryForObject(comando,String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getModalidad|:"+ex);
		}
				
		return modalidad;
	}
	
	public String getModalidadId( String codigoPersonal) {
		String modalidad = "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT MODALIDAD_ID FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
				modalidad = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getModalidadId|:"+ex);
		}
				
		return modalidad;
	}
	
	public String getTipoAlumnoId( String codigoPersonal) {
		String tipo = "";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";				
				tipo = enocJdbc.queryForObject(comando,String.class, parametros);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getTipoAlumnoId|:"+ex);
		}
		
		return tipo;
	}
	
	public String getCantidadCalculoCobro( String codigoPersonal, String cargaId) {
		String total = "Error. Tendras que inscribirte personalmente o intentar luego";
		
		try{
			String comando = "SELECT SUM(CASE NATURALEZA WHEN 'D' THEN IMPORTE*-1 WHEN 'C' THEN IMPORTE END) AS TOTAL" +
					" FROM MATEO.FES_CC_MOVIMIENTO" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND TIPOMOV = 23";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			total = enocJdbc.queryForObject(comando,String.class, parametros);
			
			if(Float.parseFloat(total) <= 0)//Si sale deuda se pasa a positivo ya que es la cantidad que se va a pagar
				total = String.valueOf(Float.parseFloat(total)*-1);
			else
				total = "Nada";
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getCantidadCalculoCobro|:"+ex);
		}
		
		return total;
	}
	
	public String getSemestre( String codigoPersonal) {
		String semestre = "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(SEMESTRE,0) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";				
				semestre = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getSemestre|:"+ex);
		}
		
		return semestre;
	}
	
	public String getGrado( String codigoPersonal) {
		String grado = "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(GRADO,0) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? ";				
				grado = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getGrado|:"+ex);
		}
		
		return grado;
	}
		
	public List<AlumAcademico> getListAll( String orden ) {
		
		List<AlumAcademico> lista = new ArrayList<AlumAcademico>();
		
		try{
			String comando = "SELECT "+
				" CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
				" OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO, "+
				" F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, " +
				" EXTENSION_ID, PREPA_LUGAR, REQUERIDO, ACOMODO_ID"+
				" FROM ENOC.ALUM_ACADEMICO "+orden;
			lista = enocJdbc.query(comando, new AlumAcademicoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,AlumAcademico> getMapAcademico() {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
				
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"+
				" TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD, TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION, TO_CHAR(F_ALTA, 'DD/MM/YYYY') AS F_ALTA, MODALIDAD_ID," +
				" EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"+
				" FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"; 			
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapAcademico|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumAcademico> getMap() {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
				
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"+
				" TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD, TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION, TO_CHAR(F_ALTA, 'DD/MM/YYYY') AS F_ALTA, MODALIDAD_ID," +
				" EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"+
				" FROM ENOC.ALUM_ACADEMICO"; 			
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapAcademico|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumAcademico> mapAcademicoMatriculasCargaEnLinea(String matriculas) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
		
		try{
			String comando = "SELECT "+
					" CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
					" OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO, "+
					" F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, " +
					" EXTENSION_ID, PREPA_LUGAR, REQUERIDO, ACOMODO_ID"+
					" FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN("+matriculas+") "; 			
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapAcademicoMatriculasCargaEnLinea|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapaAlumnosConCargaEnLinea() {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, EXTENSION_ID, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_ENLINEA WHERE ESTADO = 'A'))"; 			
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapAcademicoMatriculasCargaEnLinea|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapAcademicoCovid() {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
		
		try{
			String comando = "SELECT "+
					" CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, "+
					" OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO, "+
					" F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD, " +
					" EXTENSION_ID, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"+
					" FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.COVID) "; 			
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapAcademicoCovid|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapAcademicoCargasyModalidades( String cargas, String modalidades) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION,"
					+ " OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD,"
					+ " EXTENSION_ID, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+"))";
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapAcademico|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapAlumnosEnMateria( String cursoCargaId) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION,"
					+ " OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD,"
					+ " EXTENSION_ID, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID,ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.ALUMNO_CURSO WHERE CURSO_CARGA_ID = ?)";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando,new AlumAcademicoMapper(), parametros);
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapAcademico|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapAlumnosEnSalida( String salidaId) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista 			= new ArrayList<AlumAcademico>();
				
		try{
			String comando = " SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION,"
					+ " OBRERO_DIVISION, OBRERO_UNION, OBRERO_ASOCIACION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, ENOC.ALUMNO_MODALIDAD(CODIGO_PERSONAL) AS MODALIDAD,"
					+ " EXTENSION_ID, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN "
					+ "		(SELECT CODIGO_PERSONAL FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando,new AlumAcademicoMapper(), parametros);
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapAlumnosEnSalida|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapDormi( String condicion) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();	
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TRIM(DORMITORIO) AS VALOR FROM ENOC.ALUM_ACADEMICO "+condicion;			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapDormi|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapModalidad( String condicion) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();		
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE , MODALIDAD_ID AS VALOR FROM ENOC.ALUM_ACADEMICO "+condicion;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapModalidad|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapTipoAlumno( String condicion) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TIPO_ALUMNO AS VALOR FROM ENOC.ALUM_ACADEMICO "+condicion;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getMapTipoAlumno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getTipoAlumnoSinMentor( String periodo) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TIPO_ALUMNO AS VALOR FROM ENOC.ALUM_ACADEMICO"+
					  " WHERE CODIGO_PERSONAL IN"+ 
					  " (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO"+
					  " WHERE ESTADO = 'I'" +
					  " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL ))"+
					  " AND CODIGO_PERSONAL NOT IN(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? AND STATUS = 'A')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|getTipoAlumnoSinMentor|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapInternos( String dormitorioId) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista	 		= new ArrayList<AlumAcademico>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = "+dormitorioId+")";
			lista = enocJdbc.query(comando,new AlumAcademicoMapper());
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapInternos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapaAlumnosEnGraduacion( String eventoId) {
		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista	 		= new ArrayList<AlumAcademico>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AlumAcademicoMapper(), parametros);
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaAlumnosEnGraduacion|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumAcademico> mapaAlumnosEnProceso( String cargaId) {		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista	 		= new ArrayList<AlumAcademico>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND TIPOCAL_ID = 'M')";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumAcademicoMapper(), parametros);
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaAlumnosEnProceso|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumAcademico> mapaEnCarga( String cargaId) {		
		HashMap<String,AlumAcademico> mapa	= new HashMap<String,AlumAcademico>();
		List<AlumAcademico> lista	 		= new ArrayList<AlumAcademico>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, TIPO_ALUMNO, CLAS_FIN, OBRERO, OBRERO_INSTITUCION, RESIDENCIA_ID, TRIM(DORMITORIO) AS DORMITORIO,"
					+ " F_SOLICITUD, F_ADMISION, F_ALTA, MODALIDAD_ID, EXTENSION_ID, GRADO, SEMESTRE, HO_STATUS, PREPA_LUGAR, REQUERIDO, NIVEL_INICIO_ID, ACOMODO_ID"
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumAcademicoMapper(), parametros);
			for(AlumAcademico objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaEnCarga|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaResidenciaSalidas( String salidaId) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT RESIDENCIA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999'))"
					+ " GROUP BY RESIDENCIA_ID";
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaResidenciaSalidas|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, String> mapaRequeridos() {		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, REQUERIDO AS VALOR FROM ENOC.ALUM_ACADEMICO WHERE REQUERIDO = 'S'";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaResidenciaSalidas|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String, String> mapaModalidades(String planId) {
		
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, MODALIDAD_ID AS VALOR FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE PLAN_ID = ?)";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaModalidades|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, String> mapaResidencia(String periodoId) {		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, RESIDENCIA_ID AS VALOR FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumAcademicoDao|mapaResidencia|:"+ex);
		}		
		return mapa;
	}
}