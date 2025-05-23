package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentPerfilDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentPerfil perfil) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO"+
				" ENOC.MENT_PERFIL(CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
				" FOLIO, FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO) "+
				"VALUES(?, ?, ?," +
				" TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?," +
				" ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {perfil.getCodigoPersonal(),perfil.getCargaId(),perfil.getMentorId(),perfil.getFolio(),perfil.getFecha(),perfil.getRelAsdb(),perfil.getRelAsd(),perfil.getRelNasd(),perfil.getResInt(),perfil.getResExt(),perfil.getResTipo(),perfil.getComComedor(),perfil.getComSnack(),perfil.getComAsistencia(),perfil.getComCasa(),perfil.getComOtro(),perfil.getCom3(),perfil.getCom2(),perfil.getCom1(),perfil.getDevDiaria(),perfil.getDevSemana(),perfil.getDevMenos(),perfil.getIglUm(),perfil.getIglOtra(),perfil.getIglNinguna(),perfil.getProFamiliar(),perfil.getProFinanciero(),perfil.getProMateria(),perfil.getProPendiente(),perfil.getProPendiente(),perfil.getLidEspiritual(),perfil.getLidPosicion(),perfil.getTraNada(),perfil.getTraMedio(),perfil.getTraCompleto(),perfil.getEstDesafios(),perfil.getEstRelaciones(),perfil.getEstProgreso(),perfil.getEstRegresa(),perfil.getRiesgoPersonal(),perfil.getRiesgoIntegracion(),perfil.getRiesgoRelaciones(),perfil.getRiesgoAcademico(),perfil.getRiesgoFinanciero(),perfil.getUsuario()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(MentPerfil perfil ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MENT_PERFIL"+ 
				" SET MENTOR_ID = ?," +
				" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" REL_ASDB = ?," +
				" REL_ASD = ?," +
				" REL_NASD = ?," +
				" RES_INT = ?," +
				" RES_EXT = ?," +
				" RES_TIPO = ?," +
				" COM_COMEDOR = ?," +
				" COM_SNACK = ?," +
				" COM_ASISTENCIA = ?," +
				" COM_CASA = ?," +
				" COM_OTRO = ?," +
				" COM_3 = ?," +
				" COM_2 = ?," +
				" COM_1 = ?," +
				" DEV_DIARIA = ?," +
				" DEV_SEMANA = ?," +
				" DEV_MENOS = ?," +
				" IGL_UM = ?," +
				" IGL_OTRA = ?," +
				" IGL_NINGUNA = ?," +
				" PRO_FAMILIAR = ?," +
				" PRO_FINANCIERO = ?," +
				" PRO_MATERIA = ?," +
				" PRO_PENDIENTE = ?," +
				" LID_ESPIRITUAL = ?," +
				" LID_POSICION = ?," +
				" TRA_NADA = ?," +
				" TRA_MEDIO = ?," +
				" TRA_COMPLETO = ?," +
				" EST_DESAFIOS = ?," +
				" EST_RELACIONES = ?," +
				" EST_PROGRESO = ?," +
				" EST_REGRESA = ?," +
				" RIESGO_PERSONAL = ?," +
				" RIESGO_INTEGRACION = ?," +
				" RIESGO_RELACIONES = ?," +
				" RIESGO_ACADEMICO = ?," +
				" RIESGO_FINANCIERO = ?," +
				" USUARIO = ?" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '99')" +
				" AND CARGA_ID = ?";
				
			Object[] parametros = new Object[] {perfil.getMentorId(),perfil.getFecha(),perfil.getRelAsdb(),perfil.getRelAsd(),perfil.getRelNasd(),perfil.getResInt(),perfil.getResExt(),perfil.getResTipo(),perfil.getComComedor(),perfil.getComSnack(),perfil.getComAsistencia(),perfil.getComCasa(),perfil.getComOtro(),perfil.getCom3(),perfil.getCom2(),perfil.getCom1(),perfil.getDevDiaria(),perfil.getDevSemana(),perfil.getDevMenos(),perfil.getIglUm(),perfil.getIglOtra(),perfil.getIglNinguna(),perfil.getProFamiliar(),perfil.getProFinanciero(),perfil.getProMateria(),perfil.getProPendiente(),perfil.getLidEspiritual(),perfil.getLidPosicion(),perfil.getTraNada(),perfil.getTraMedio(),perfil.getTraCompleto(),perfil.getEstDesafios(),perfil.getEstRelaciones(),perfil.getEstProgreso(),perfil.getEstRegresa(),perfil.getRiesgoPersonal(),perfil.getRiesgoIntegracion(),perfil.getRiesgoRelaciones(),perfil.getRiesgoAcademico(),perfil.getRiesgoFinanciero(),perfil.getUsuario(),perfil.getCodigoPersonal(),perfil.getFolio(),perfil.getCargaId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	
	public boolean deleteReg(String codigoPersonal, String folio, String cargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_PERFIL"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '99')" +
				" AND CARGA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,folio,cargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	
	
	public MentPerfil mapeaRegId(  String codigoPersonal, String cargaId, String folio) {
		MentPerfil perfil = new MentPerfil();
		
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
					" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
					" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
					" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
					" COM_OTRO, COM_3, COM_2, COM_1," +
					" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
					" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
					" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
					" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
					" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
					" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO"+
					" FROM ENOC.MENT_PERFIL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND FOLIO = TO_NUMBER(?, '9')";
					
			Object[] parametros = new Object[] {codigoPersonal,cargaId,folio};
			perfil = enocJdbc.queryForObject(comando, new MentPerfilMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|mapeaRegId|:"+ex);
		}
		return perfil;
	}
	
	public boolean existeReg(String codigoPersonal, String folio, String cargaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_PERFIL" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '9')" +
				" AND CARGA_ID = ?";
						
			Object[] parametros = new Object[] {codigoPersonal,folio,cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean esAlumnoMentoriable(  String codigoPersonal ) {
		boolean esMentoriable	= false;
		
		try{
			String comando = "SELECT COALESCE(CODIGO_PERSONAL,'X') AS CODIGO_PERSONAL " +
					" FROM ENOC.INSCRITOS " +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND MODALIDAD_ID IN ('1','4') " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6)" + 
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				esMentoriable = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentorPerfil|esAlumnoMentoriable|:"+ex);
		}
		return esMentoriable;
	}
	
	public int alumMenFac(  String facultadId ) {
		int numAlumnos			= 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUMNOS " +
					" FROM ENOC.INSCRITOS " +
					" WHERE MODALIDAD_ID IN (1,4) " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6))" + 
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'" +
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND ENOC.FACULTAD(CARRERA_ID) = ?";
			
			Object[] parametros = new Object[] {facultadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numAlumnos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|alumMenFac|:"+ex);
		}
		return numAlumnos;
	}
	
	public int alumMenCarr( String carreraId) {
		
		
		int numAlumnos			= 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUMNOS " +
					" FROM ENOC.INSCRITOS " +
					" WHERE MODALIDAD_ID IN ('1','4') " +
					" AND CODIGO_PERSONAL NOT IN " +
					"	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ACADEMICO WHERE TIPO_ALUMNO IN (5,6))" + 
					" AND ENOC.FACULTAD(CARRERA_ID) != '110'" +
					" AND SUBSTR(CARGA_ID,6,1) NOT IN ('F','G')"+
					" AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numAlumnos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|alumMenCarr|:"+ex);
		}
		return numAlumnos;
	}
	
	public String getPerfilIngreso( String codigoPersonal, String cargaId ) {
		String perfil		= "";
		
		try{
			String comando = "SELECT COUNT(FOLIO) AS PERFIL_INGRESO FROM ENOC.MENT_PERFIL WHERE CODIGO_PERSONAL = ? " + 
					"AND CARGA_ID = ?";
					
			Object[] parametros = new Object[] {codigoPersonal};
	 		if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				perfil  = enocJdbc.queryForObject(comando,String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|getPerfilIngreso|:"+ex);
		}
		return perfil;
	}
	
	public String getPerfilFolio( String codigoPersonal, String cargaId, String mentorId, String folio ) {
		String pFolio			= "0" ;
			
			try{
				String comando = "SELECT FOLIO FROM ENOC.MENT_PERFIL WHERE CODIGO_PERSONAL = ? " + 
						"AND CARGA_ID = ? AND MENTOR_ID = ? AND FOLIO = ? ";
				
				Object[] parametros = new Object[] {codigoPersonal};
		 		if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
					pFolio  = enocJdbc.queryForObject(comando,String.class, parametros);
		 		}			
			}catch(Exception ex){
				System.out.println("Error - aca.mentores.spring.MentPerfilDao|getPerfilIngreso|:"+ex);
			}			
			return pFolio;
		}
	
	public List<MentPerfil> getListAll( String orden ) {
		List<MentPerfil> lista		= new ArrayList<MentPerfil>();
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
				" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO"+
				" FROM ENOC.MENT_PERFIL "+ orden; 
			
			lista = enocJdbc.query(comando, new MentPerfilMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public TreeMap<String,MentPerfil> getMapMentPerfil( String periodoId, String orden ) {
		TreeMap<String,MentPerfil> mapa	= new TreeMap<String,MentPerfil>();
		List<MentPerfil> lista	= new ArrayList<MentPerfil>();	
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
			" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
			" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
			" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
			" COM_OTRO, COM_3, COM_2, COM_1," +
			" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
			" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
			" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
			" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
			" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
			" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO"+
			" FROM ENOC.MENT_PERFIL "+ orden; 
			
			lista = enocJdbc.query(comando, new MentPerfilMapper());
			
			for (MentPerfil estado : lista){			
				mapa.put(estado.getCodigoPersonal(), estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPerfilDao|getListAll|:"+ex);
		}
		return mapa;
	}
	public HashMap<String, MentPerfil> getMapMentPerfil( String periodo) {
		HashMap<String, MentPerfil> mapa		= new HashMap<String,MentPerfil>();
		List<MentPerfil> lista	= new ArrayList<MentPerfil>();	
		String comando							= "";		
		try{
			comando = "SELECT * FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? AND STATUS='A'";			
			lista = enocJdbc.query(comando, new MentPerfilMapper(), periodo);			
			for (MentPerfil estado : lista){			
				mapa.put(estado.getCodigoPersonal(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfilesUtil|getMapMentPerfil|:"+ex);
		}
		return mapa;
	}
	
}