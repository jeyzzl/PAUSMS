package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraMapper;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadMapper;
import aca.plan.spring.MapaCursoDao;

@Component
public class CargaGrupoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	public boolean insertReg( CargaGrupo grupo ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO "
					+ " (CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID, CODIGO_PERSONAL, GRUPO, "
					+ " MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA, RESTRICCION, ESTADO, HORARIO, F_EVALUACION, "
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, USUARIO) "
					+ " VALUES( ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, TO_NUMBER(?,'99'),"
					+ " TO_DATE(?,'DD/MM/YYYY'), "
					+ " TO_DATE(?,'DD/MM/YYYY'), "
					+ " TO_DATE(?,'DD/MM/YYYY'), "
					+ " ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?, '999'), TO_NUMBER(?, '999'),?, ?, ?, TO_NUMBER(?,'999999.99'), ?, ?)";			
			Object[] parametros = new Object[] {
				grupo.getCursoCargaId(), grupo.getCargaId(), grupo.getBloqueId(), grupo.getCarreraId(), grupo.getCodigoPersonal(), grupo.getGrupo(),
				grupo.getModalidadId(), grupo.getfInicio(), grupo.getfFinal(), grupo.getfEntrega(), grupo.getRestriccion(), grupo.getEstado(),
				grupo.getHorario(), grupo.getfEvaluacion(), grupo.getValeucas(), grupo.getNumAlum(), grupo.getSemanas(), grupo.getOptativa(), 
				grupo.getComentario(), grupo.getCodigoOtro(), grupo.getPrecio(), grupo.getModo(), grupo.getUsuario()	
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CargaGrupo grupo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO "
					+ " SET CARGA_ID = ?,"
					+ " BLOQUE_ID = TO_NUMBER(?,'99'),"
					+ " CARRERA_ID = ?,"
					+ " CODIGO_PERSONAL = ?,"
					+ " GRUPO = ?,"
					+ " MODALIDAD_ID = TO_NUMBER(?,'99'),"
					+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_FINAL  = TO_DATE(?,'DD/MM/YYYY'), "
					+ " F_ENTREGA= TO_DATE(?,'DD/MM/YYYY'),"
					+ " RESTRICCION = ?,"
					+ " ESTADO = ?,"
					+ " HORARIO = ?,"
					+ " F_EVALUACION = TO_DATE(?,'DD/MM/YYYY'),"
					+ " VALEUCAS = ?,"
					+ " NUM_ALUM = TO_NUMBER(?, '999'),"
					+ " SEMANAS = TO_NUMBER(?, '999'),"
					+ " OPTATIVA = ?,"
					+ " COMENTARIO = ?,"
					+ " CODIGO_OTRO = ?,"
					+ " PRECIO = TO_NUMBER(?,'999999.99'),"
					+ " MODO = ?,"
					+ " USUARIO = ?"
					+ " WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {
				grupo.getCargaId(), grupo.getBloqueId(), grupo.getCarreraId(), grupo.getCodigoPersonal(), grupo.getGrupo(), grupo.getModalidadId(), grupo.getfInicio(),
				grupo.getfFinal(), grupo.getfEntrega(), grupo.getRestriccion(), grupo.getEstado(), grupo.getHorario(), grupo.getfEvaluacion(), grupo.getValeucas(),
				grupo.getNumAlum(), grupo.getSemanas(), grupo.getOptativa(), grupo.getComentario(), grupo.getCodigoOtro(), grupo.getPrecio(), grupo.getModo(), grupo.getUsuario(),
				grupo.getCursoCargaId()	
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateReg|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateMaestro(CargaGrupo grupo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO SET CODIGO_PERSONAL = ? WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {
				grupo.getCodigoPersonal(),grupo.getCursoCargaId()	
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateMaestro|:"+ex);		 
		}		
		return ok;
	}
	
	public boolean updateAuxiliar(CargaGrupo grupo) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO SET CODIGO_OTRO = ? WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {
					grupo.getCodigoOtro(),grupo.getCursoCargaId()	
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateAuxiliar|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateHorario( String cursoCargaId, String horario ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO "
					+ " SET HORARIO = ? "
					+ " WHERE CURSO_CARGA_ID = ? ";				
			Object[] parametros = new Object[] { horario,cursoCargaId };					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateHorario|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateFEvaluacion( String cursoCargaId, String fEvaluacion ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO "
					+ " SET F_EVALUACION = TO_DATE(?,'DD/MM/YYYY') "
					+ " WHERE CURSO_CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {
				fEvaluacion,cursoCargaId
			};
					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateFEvaluacion|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateFechas( String cursoCargaId, String fechaIni, String fechaFin, String grupo, String comentario, String restriccion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO"
					+ " SET F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_FINAL =  TO_DATE(?,'DD/MM/YYYY'),"
					+ " GRUPO = ?, COMENTARIO = ?,"
					+ " RESTRICCION = ? "
					+ " WHERE CURSO_CARGA_ID = ? ";
			
			Object[] parametros = new Object[] {
				fechaIni, fechaFin, grupo, comentario, restriccion, cursoCargaId
			};
					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateFechas|:"+ex); 
		}
		
		return ok;
	}
	
	public boolean updateEstado( String cursoCargaId, String estado ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO SET ESTADO = ? WHERE CURSO_CARGA_ID = ? ";			
			Object[] parametros = new Object[] {
					estado,cursoCargaId
			};					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateEstado|:"+ex);		 
		}
		
		return ok;
	}
	
	public boolean updateBloque( String cursoCargaId, String bloqueId) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO SET BLOQUE_ID = ? WHERE CURSO_CARGA_ID = ? ";			
			Object[] parametros = new Object[] {bloqueId,cursoCargaId };					
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|updateBloque|:"+ex);		 
		}
		
		return ok;
	}

	
	public boolean deleteReg( String cursoCargaId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ? ";			
			Object[] parametros = new Object[] {cursoCargaId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CargaGrupo mapeaRegId( String cursoCargaId ) {
		
		CargaGrupo objeto = new CargaGrupo();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT"
					+ " CURSO_CARGA_ID,"
					+ " CARGA_ID,"
					+ " BLOQUE_ID,"
					+ " CARRERA_ID,"
					+ " COALESCE(CODIGO_PERSONAL,' ') AS CODIGO_PERSONAL,"
					+ " TRIM(GRUPO) AS GRUPO,"
					+ " MODALIDAD_ID,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " RESTRICCION,"
					+ " ESTADO,"
					+ " HORARIO,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, "
					+ " CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID = ? ";
				objeto = enocJdbc.queryForObject(comando, new CargaGrupoMapper(), parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId) {
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean esMaestro( String codigoPersonal) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|esMaestro|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeCargaCarrera( String cargaId, String carreraId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ? ";			
			Object[] parametros = new Object[] {cargaId,carreraId};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|existeCargaCarrera|:"+ex);
		}
		
		return ok;
	}
	
	public void chkFechas( String cursoCargaId ) {
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID = ? "
					+ " AND F_EVALUACION > F_FINAL";
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "UPDATE ENOC.CARGA_GRUPO SET F_EVALUACION = F_FINAL WHERE CURSO_CARGA_ID = ?";
				enocJdbc.update(comando,parametros);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|chkFechas|:"+ex);
		}
	}
	
	public boolean conAlumnos( String cursoCargaId ){
		boolean 	ok 	= false;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID = ? "
					+ " AND TIPOCAL_ID IN ('I','1','2','3','4','5','6')";	
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|conAlumnos|:"+ex);
		}		
		return ok;
	}
	
	public String getCodigoPersonal( String cursoCargaId ) {
		String codigoPersonal 	= "0000000";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";
				codigoPersonal = enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCodigoPersonal|:"+ex);
		}
		
		return codigoPersonal;
	}
	
	public String getCodigoOtro( String cursoCargaId ) {
		String codigoPersonal 	= "0000000";		
		try{
			String comando = "SELECT COUNT(CODIGO_OTRO) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT CODIGO_OTRO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";
				codigoPersonal = enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCodigoOtro|:"+ex);
		}
		
		return codigoPersonal;
	}
	
	public String getEstado( String cursoCargaId ) {
		String estado 	= "0";		
		try{
			String comando = "SELECT COALESCE(ESTADO,'X') AS ESTADO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
			estado = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getEstado|:"+ex);
		}
		
		return estado;
	}
	
	public String getCarreraId( String cursoCargaId ) {
		String carrera 	= "0";		
		
		try{
			String comando = "SELECT COALESCE(CARRERA_ID,'X') AS CARRERA"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			carrera = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCarreraId|:"+ex);
		}
		
		return carrera;
	}
	
	public String getCargaId( String cursoCargaId ) {
		String cargaId 	= "0";		
		
		try{
			String comando = "SELECT SUBSTR(CURSO_CARGA_ID, 1,6) AS CARGA_ID "
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			cargaId = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCargaId|:"+ex);
		}
		
		return cargaId;
	}
	
	public String getFechaInicio( String cursoCargaId ) {
		String fecha 	= "";		
		
		try{
			String comando = "SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO"
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getFechaInicio|:"+ex);
		}
		
		return fecha;
	}
	
	public String getFechaFinal( String cursoCargaId ) {
		String fecha 	= "";		
		
		try{
			String comando = "SELECT TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL "
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getFechaFinal|:"+ex);
		}
		
		return fecha;
	}
	
	
	/**
	 * Esta funcion consulta si la materia tiene cambios de nota debido a un "acta de correccion" ..    	
	 **/
	public boolean getTieneCorreccionNotas( String cursoCargaId ) {
		boolean correccion 		= false;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ? AND TIPO = 'C' ";			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				correccion = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTieneCorreccionNotas|:"+ex);
		}		
		return correccion;
	}
	
	public boolean getTieneCargaDocente( String codigoPersonal, String cargaId ) {
		boolean docencia 		= false;		
		try{
			String comando = "SELECT COUNT(VALEUCAS) FROM ENOC.CARGA_GRUPO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND VALEUCAS='S'";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				docencia = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTieneCargaDocente|:"+ex);
		}		
		return docencia;
	}
	
	public String getOptativa( String cursoCargaId ) {
		String optativa 		= "";		
		
		try{
			String comando = "SELECT OPTATIVA FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaId};
			optativa = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getOptativa|:"+ex);
		}
		
		return optativa;
	}
	
	public String getModalidad( String cursoCargaId ) {
		String modalidad 	= "0";		
		
		try{
			String comando = "SELECT MODALIDAD_ID FROM ENOC.CARGA_GRUPO  WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			modalidad = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getModalidad|:"+ex);
		}
		
		return modalidad;
	}
	
	public String getFacultadGrupo( String cursoCargaId ) {
		String fac 	= "000";		
		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			fac = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getFacultadGrupo|:"+ex);
		}
		
		return fac;
	}
	
	public int verificaHorario( String cursoCargaId) {
		String cursoId 	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId); 
		String fs = mapaCursoDao.getFS(cursoId);
		
		int TH 			= Integer.parseInt(fs);			
		int cont 		= 0;
		
		try{
			String comando = "SELECT HORARIO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ? ";		
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				String horario = enocJdbc.queryForObject(comando,String.class,parametros);
				if(horario.contains("1")){
					if(TH==1) cont++;
					for(int i=0; i<horario.length(); i++){
						if(cont==TH) break;
						if(horario.charAt(i)=='1') cont++;
					}
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|verificaHorario|:"+ex);
		}
		
		return TH-cont;
	}	
	
	public HashMap<String,String> mapaMatSinAlumnos() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CURSO_CARGA_ID NOT IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT)"
					+ " GROUP BY CARGA_ID ";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNumCursosMaestro( String codigoPersonal) {
		String numCursos 	= "0";
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			numCursos = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getNumCursosMaestro|:"+ex);
		}
		
		return numCursos;
	}	
	
	public String getNumCursosMaestro( String codigoPersonal, String cargaId) {
		String numCursos 	= "0";
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			numCursos = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getNumCursosMaestro|:"+ex);
		}
		
		return numCursos;
	}
		
	public List<CargaGrupo> getListAll( String orden ) {
		List<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
				+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
				+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO"
				+ " CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
				+ " FROM ENOC.CARGA_GRUPO "+ orden; 
			
			lisGrupo = enocJdbc.query(comando, new CargaGrupoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListAll|:"+ex);
		}
		
		return lisGrupo;
	}
	
	public List<String> getListBloques( String cargaId, String orden ) {
		List<String> lisBloque	= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(BLOQUE_ID) AS BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? "+ orden;			
			lisBloque = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListBloques|:"+ex);
		}		
		return lisBloque;
	}
	
	public List<CatFacultad> getFacultades( String cargaId) {
		List<CatFacultad> listor	= new ArrayList<CatFacultad>();
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			listor = enocJdbc.query(comando, new CatFacultadMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getFacultades|:"+ex);
		}		
		return listor;
	}
	
	public List<CatCarrera> getCarreras( String cargaId, String facultadId){
		List<CatCarrera> listor	= new ArrayList<CatCarrera>();
		try{
			String comando = "select carrera_id from ENOC.carga_grupo where carga_id=? and ENOC.FACULTAD(carrera_id) = ? group by carrera_id";			
			listor = enocJdbc.query(comando, new CatCarreraMapper(), cargaId, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCarreras|:"+ex);
		}		
		return listor;
	}
	
	public long getTotalCarga( String cargaId) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";			
			total = enocJdbc.queryForObject(comando, Long.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotalCarga|:"+ex);
		}		
		return total;
	}
	
	public long getTotalCarga( String cargaId,String facultadId) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?";			
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotalCarga|:"+ex);
		}		
		return total;
	}
	
	public long getTotalCargaCarrera( String cargaId,String carreraId) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?";			
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotalCargaCarrera|:"+ex);
		}		
		return total;
	}
	
	public long getTotalCargaCarreraMaestro( String cargaId,String carreraId,String codigoPersonal) {
		long total = 0;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ? AND CODIGO_PERSONAL= ?";			
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, carreraId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotalCargaCarreraMaestro|:"+ex);
		}

		return total;
	}
	
	public long getCargaEstado( String cargaId,String estado) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ESTADO = ?";			
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, estado);		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCargaEstado|:"+ex);
		}
		return total;
	}
	
	public long getCargaEstado( String cargaId,String estado,String facultadId) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ESTADO=? AND ENOC.FACULTAD(CARRERA_ID) = ?";
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, estado, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCargaEstado|:"+ex);
		}

		return total;
	}
	
	public long getCargaEstadoCarrera( String cargaId,String estado,String carreraId) {
		long total = 0;

		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ESTADO=? AND CARRERA_ID = ?";
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, estado, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCargaEstadoCarrera|:"+ex);
		}
		return total;
	}
	
	public long getCargaEstadoCarrera( String cargaId,String estado,String carreraId,String codigoPersonal) {
		long total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ESTADO=? AND CARRERA_ID = ? AND CODIGO_PERSONAL=?";
			total = enocJdbc.queryForObject(comando, Long.class, cargaId, estado, carreraId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCargaEstadoCarrera|:"+ex);
		}
		return total;
	}
	
	public List<CargaGrupo> getListaCarga( String cargaId, String orden ) {
		List<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"+
				" FROM ENOC.CARGA_GRUPO "+ 
				" WHERE CARGA_ID = ? "+ orden;
			
			lisGrupo = enocJdbc.query(comando, new CargaGrupoMapper(), cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListaCarga|:"+ex);
		}
		
		return lisGrupo;
	}
	
	public List<CargaGrupo> getListaCarrera( String cargaId, String carreraId, String orden ) {
		List<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"+
				" FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CARGA_ID = ?"+
				" AND CARRERA_ID = ? "+ orden;			
			lisGrupo = enocJdbc.query(comando, new CargaGrupoMapper(), cargaId, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListaCarrera|:"+ex);
		}
		
		return lisGrupo;
	}
	
	public List<String> getListGruposCarga( String cargaId, String orden ) {
		List<String> lisGrupo		= new ArrayList<String>();
		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD,"+
				" CARRERA_ID,"+
				" CURSO_CARGA_ID,"+
				" ESTADO,"+
				" MODALIDAD_ID"+
				" FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CARGA_ID = ? "+ orden;			
			lisGrupo = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListGruposCarga|:"+ex);
		}
		
		return lisGrupo;
	}
	
	public String getNumGrupos( String cargaId, String carreraId ) {
		String total	= "0";		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) AS TOTAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?";			
			total = enocJdbc.queryForObject(comando, String.class, cargaId, carreraId);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getNumGrupos|:"+ex);
		}		
		return total;
	}
	
	public String getNumMaestros( String cargaId, String carreraId ) {
		String total	= "0";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ? "+
				"AND COALESCE(TRIM(CODIGO_PERSONAL), 'X') != 'X' ";			
			total = enocJdbc.queryForObject(comando, String.class, cargaId, carreraId);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getNumMaestros|:"+ex);
		}
		
		return total;
	}
	
	public HashMap<String, CargaGrupo> mapCargaGrupo( String cargaId) {
		HashMap<String, CargaGrupo> mapaCargaGrupo = new HashMap<String, CargaGrupo>();
		List<CargaGrupo> lista	= new ArrayList<CargaGrupo>();		
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
					+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
					+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = ? "; 
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new CargaGrupoMapper(), parametros);
			for(CargaGrupo carga : lista){				
				mapaCargaGrupo.put(carga.getCursoCargaId(), carga);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapCargaGrupo|:"+ex);
		}

		return mapaCargaGrupo;
	}
	
	public HashMap<String, CargaGrupo> getAllMapaCargaGrupo( String orden) {
		HashMap<String, CargaGrupo> mapaCargaGrupo = new HashMap<String, CargaGrupo>();
		List<CargaGrupo> lista	= new ArrayList<CargaGrupo>();	
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
					" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
					" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
					" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"+
					" FROM ENOC.CARGA_GRUPO "+ orden; 
			
			lista = enocJdbc.query(comando,new CargaGrupoMapper());
			for(CargaGrupo carga : lista){				
				mapaCargaGrupo.put(carga.getCursoCargaId(), carga);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getAllMapaCargaGrupo|:"+ex);
		}finally{
			
			
		}
		return mapaCargaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias de una carga academica */
	public HashMap<String, String> getTotGpo( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String valor 	= "0";
		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID";		
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
						
			for(aca.Mapa map : lista){	
				if ((String)map.getValor() == null) {
					valor = "0";
				} else if(map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpo|:"+ex);
		}
		
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias de una facultad en la carga academica */
	public  HashMap<String, String> mapMatPorFacultadEnCarga( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMatPorFacultadEnCarga|:"+ex);
		}

		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias de un maestro en la carga academica */
	public  HashMap<String, String> mapMatPorMaestroEnCarga( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";		
		
		try{
			comando = " SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? GROUP BY CODIGO_PERSONAL";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMatPorMaestroEnCarga|:"+ex);
		}

		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias reprobadas de una facultad en la carga academica */
	public  HashMap<String, String> mapMatRepPorFacultadEnCarga( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE TIPOCAL_ID IN('2','4') AND SUBSTR(CURSO_CARGA_ID,1,6) = ?)"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMatRepPorFacultadEnCarga|:"+ex);
		}

		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias por estado de un maestro en una carga academica */
	public  HashMap<String, String> getTotEdoMaestro( String carga, String codigoMaestro) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID||ESTADO AS LLAVE, COALESCE(COUNT(ESTADO),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID = ?"+
					" AND CODIGO_PERSONAL = ? " +
					" GROUP BY CARGA_ID, ESTADO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carga, codigoMaestro);
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotEdoMaestro|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Cuenta la cantidad de materias por estado carga academica */
	public  HashMap<String, String> getTotGpoEdo( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID || ESTADO AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ESTADO";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpoEdo|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera de una carga academica */
	public  HashMap<String, String> getTotGpoFacEdo( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID || ENOC.FACULTAD(CARRERA_ID) || ESTADO AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ENOC.FACULTAD(CARRERA_ID), ESTADO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpoFacEdo|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera y estado de una carga academica */
	public  HashMap<String, String> getTotGpoFac( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID || ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR " +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ENOC.FACULTAD(CARRERA_ID)";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpoFac|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera de una carga academica */
	public  HashMap<String, String> getTotGpoCarr( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID || CARRERA_ID AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, CARRERA_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpoCarr|:"+ex);
		}

		return mapaGrupo;
	}
	
	public  HashMap<String, String> getTotGpoCarrEdo( String cargas) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID || CARRERA_ID || ESTADO AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, CARRERA_ID, ESTADO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTotGpoCarrEdo|:"+ex);
		}

		return mapaGrupo;
	}

	public  HashMap<String, String> getMapaFacultadIdPorCursoCargaId( String codigoPersonal, String cargaId) {
		HashMap<String, String> mapaFacultadId = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando	= "";	
		try{
			comando = "SELECT CURSO_CARGA_ID AS LLAVE, ENOC.FACULTAD(CARRERA_ID) AS VALOR FROM ENOC.CARGA_GRUPO " +
					"WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL= ? AND CARGA_ID = ?) ";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, cargaId);
			for(aca.Mapa map : lista){				
				mapaFacultadId.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMapaFacultadIdPorCursoCargaId|:"+ex);
		}

		return mapaFacultadId;
	}
	
	public  HashMap<String, String> getMapaFacultadIdPorCursoCargaIdMaestros( String codigoPersonal, String cargaId) {
		HashMap<String, String> mapaFacultadId = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, ENOC.FACULTAD(CARRERA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, cargaId);
			for(aca.Mapa map : lista){				
				mapaFacultadId.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMapaFacultadIdPorCursoCargaIdMaestros|:"+ex);
		}
		return mapaFacultadId;
	}
	
	public  HashMap<String, CargaGrupo> mapaGruposEnCarga( String cargaId) {
		HashMap<String, CargaGrupo> mapa 	= new HashMap<String, CargaGrupo>();
		List<CargaGrupo> lista				= new ArrayList<CargaGrupo>();
		
		try{
			String comando = "SELECT"
					+ " CURSO_CARGA_ID,"
					+ " CARGA_ID,"
					+ " BLOQUE_ID,"
					+ " CARRERA_ID,"
					+ " COALESCE(CODIGO_PERSONAL,' ') AS CODIGO_PERSONAL,"
					+ " GRUPO,"
					+ " TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " RESTRICCION,"
					+ " ESTADO,"
					+ " HORARIO,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, "
					+ " CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaGrupoMapper(), parametros);
			for(CargaGrupo grupo : lista){				
				mapa.put(grupo.getCursoCargaId(), grupo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaGruposEnCarga|:"+ex);
		}

		return mapa;
	}
	
	public String getCarrera( String cursoCargaId ) {
		String comando			= "";
		String carrera			= "";	
		try{
			comando = "SELECT ENOC.NOMBRE_CARRERA(CARRERA_ID) AS CARRERA FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";			
			carrera = enocJdbc.queryForObject(comando, String.class, cursoCargaId);		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCarrera|:"+ex);
		}	
		return carrera;
	}
	
	public String getCursoId( String cursoCargaId ) {
		String comando			= "";
		String curso_id			= "";
		try{
			comando = "SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_Id = ? AND ORIGEN = 'O'";			
			curso_id = enocJdbc.queryForObject(comando, String.class, cursoCargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCursoId|:"+ex);
		}
		return curso_id;
	}
	
	public String getFacultad( String cursoCargaId ) {
		String comando			= "";
		String facultad			= "";	
		try{
			comando = "SELECT ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)) AS FACULTAD FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_Id = ?";			
			facultad = enocJdbc.queryForObject(comando, String.class, cursoCargaId);		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getFacultad|:"+ex);
		}	
		return facultad;
	}	
	
	public String getMaestro( String cursoCargaId ) {		
		String nombre			= "";
		try{
			String comando = "SELECT ENOC.EMP_NOMBRE(CODIGO_PERSONAL) AS NOMBRE FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";	
			nombre = enocJdbc.queryForObject(comando, String.class,cursoCargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMaestro|:"+ex);
		}
		return nombre;
	}
	
	public String getMaestroClave( String cursoCargaId ) {		
		String nombre			= "";
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?";
			nombre = enocJdbc.queryForObject(comando, String.class,cursoCargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMaestroClave|:"+ex);
		}
		return nombre;
	}
	
	public String getCoordinador( String cursoCargaId ) {
		String carrera			= "";	
		try{
			String comando = "SELECT ENOC.COORDINADOR_CARRERA(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ? ";			
			carrera = enocJdbc.queryForObject(comando, String.class, cursoCargaId);	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getCordinador|:"+ex);
		}	
		return carrera;
	}
	
	/* Map que cuenta la cantidad de materias por tipo de curso en una carga academica */
	public  HashMap<String, String> mapMateriasPorTipo( String carga) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT ENOC.TIPOCURSO_ID(CURSO_ID) AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ORIGEN = 'O' " +
					" GROUP BY ENOC.TIPOCURSO_ID(CURSO_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carga);
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasPorTipo|:"+ex);
		}
		
		return mapaGrupo;
	}
	
	/* Cuenta la cantidad de materias en los estados del parametro por carrera en una carga academica */
	public  HashMap<String, String> mapaMateriasPorCarrerayEstado( String cargaId, String estados) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ESTADO IN ("+estados+") GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapaGrupo.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasPorTipo|:"+ex);
		}
		
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public  HashMap<String, String> mapAlumnosPorTipo( String carga) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		String valor 			= "0";		
		try{
			String comando = "SELECT ENOC.TIPOCURSO_ID(CURSO_ID) AS LLAVE, COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS VALOR" +
					" FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" GROUP BY ENOC.TIPOCURSO_ID(CURSO_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carga);
			for(aca.Mapa map : lista){				
				if ((String)map.getValor() == null) {
					valor = "0";
				}else if (map.getValor().equals("")) {
					valor = "0";
				}else {
					valor = (String)map.getValor();
				}
				
				mapaGrupo.put(map.getLlave(), valor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapAlumnosPorTipo|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaAlumnosPorMateria( String cargaId, String codigoPersonal) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, MATERIA_ALUMNOS_ACTIVOS(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {cargaId, codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){		
				mapaGrupo.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapAlumnosPorTipo|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public List<String> getListaCargaTopMaestro( String cargaId ) {
		List<String> lisTopMaterias = new ArrayList<String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL ||'-'|| COUNT(CURSO_CARGA_ID) AS CURSO_CARGA_ID " +
					" FROM ENOC.CARGA_GRUPO " +
					" WHERE CARGA_ID = ?" +
					" GROUP BY CODIGO_PERSONAL" +
					" ORDER BY CURSO_CARGA_ID DESC";			
			lisTopMaterias = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListaCargaTopMaestro|:"+ex);
		}
		
		return lisTopMaterias;
	}	

	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public List<String> getListaCargaTopMaestroAlum( String cargaId ) {
		List<String> lisTopMaterias = new ArrayList<String>();
		
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL ||'-'|| SUM((SELECT COUNT(CURSO_CARGA_ID) AS CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID)) AS ALUMNOS" +
					" FROM ENOC.CARGA_GRUPO " +
					" WHERE CARGA_ID = ?" +
					" GROUP BY CODIGO_PERSONAL" +
					" ORDER BY alumnos DESC";
			
			lisTopMaterias = enocJdbc.queryForList(comando, String.class, cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListaCargaTopMaestroAlum|:"+ex);
		}
		
		return lisTopMaterias;
	}
		
	// SELECT TIPOCURSO_ID(CURSO_ID), COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '12131A' GROUP BY TIPOCURSO_ID(CURSO_ID);
	public HashMap<String, String> getGruposCarga( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID), 0) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO)"
					+ " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaGrupo.put(map.getLlave(), (String)map.getValor());
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getGruposCarga|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> getMaestrosCarga( String cargaId) {
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND COALESCE(TRIM(CODIGO_PERSONAL), 'X') != 'X'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO)"
					+ " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaMaestros.put(map.getLlave(), (String)map.getValor());
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMaestrosCarga|:"+ex);
		}

		return mapaMaestros;
	}
	
	public HashMap<String, String> getTienenHorariosCarga( String cargaId) {
		HashMap<String, String> mapaHorarios = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
	
		try{
			String comando = " SELECT CARRERA_ID AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR"
					  + " FROM ENOC.CARGA_GRUPO CG"
					  + " WHERE CARGA_ID = ?"
					  + " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO)"
					  + " AND (SELECT HORARIO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ENOC.CURSO_ORIGEN(CG.CURSO_CARGA_ID)) = 'S'"
					  + " AND (SELECT COALESCE(SUM(HH),0) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ENOC.CURSO_ORIGEN(CG.CURSO_CARGA_ID)) <= (SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID)"
					  + " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaHorarios.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTienenHorariosCarga|:"+ex);
		}

		return mapaHorarios;
	}
	
	public HashMap<String, String> mapaTieneSalones( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND MODO IN('P','R','H')"					
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO)"
					+ " AND CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?)"
					+ " AND CURSO_CARGA_ID NOT IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID = '0')"
					+ " GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaTieneSalones|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> getTienenHorariosMaestro( String cargaId, String codigoPersonal) {
		HashMap<String, String> mapaHorarios = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARRERA_ID AS LLAVE, COALESCE(COUNT(CURSO_CARGA_ID),0) AS VALOR"+
					  " FROM ENOC.CARGA_GRUPO CG "+
					  " WHERE CARGA_ID = ? "+
					  " AND (SELECT COALESCE(SUM(HT+HP),0) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = CURSO_ORIGEN(CG.CURSO_CARGA_ID)) <= "+ 
					  " (SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) AND CODIGO_PERSONAL = ? "+
					  " GROUP BY CARRERA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, codigoPersonal);
			for(aca.Mapa map : lista) {
				mapaHorarios.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getTienenHorariosMaestro|:"+ex);
		}

		return mapaHorarios;
	}
	
	public List<String> getListMaestros( String cargaId, String planId) {
		List<String> lisTopMaterias = new ArrayList<String>();		
		try{
			String comando = "SELECT "
					+ " CURSO_ID || '@@' || (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ENOC.CARGA_GRUPO_CURSO.CURSO_CARGA_ID)||'@@'||ORIGEN"
					+ " FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CURSO_ID LIKE '"+planId+"%' ORDER BY CURSO_ID";			
			lisTopMaterias = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getListMaestros|:"+ex);
		}		
		return lisTopMaterias;
	}
	
	public HashMap<String, CargaGrupo> mapaGruposDiferidos() {
		HashMap<String, CargaGrupo> mapa = new HashMap<String, CargaGrupo>();
		List<CargaGrupo> lista 		= new ArrayList<CargaGrupo>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"+
				" FROM ENOC.CARGA_GRUPO"+
				" WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))"; 
			
			lista = enocJdbc.query(comando, new CargaGrupoMapper());
			for(CargaGrupo grupo : lista) {
				mapa.put(grupo.getCursoCargaId(), grupo);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaGruposDiferidos|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> getRestriccionMateria( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID AS LLAVE, CARGA_ID, BLOQUE_ID, CARRERA_ID, "+
					 "CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA, "+
					 "RESTRICCION AS VALOR, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					 "VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO "+
					 "FROM ENOC.CARGA_GRUPO "+  
					 "WHERE CARGA_ID = ?";		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getRestriccionMateria|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasCarga( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?) GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasCarga|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaMateriasEnCarga( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando	= "";
		
		try{
			comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasCarga|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaMateriasPorFacultad( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapa.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaMateriasPorFacultad|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapMateriasFacultad( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasFacultad|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasMaestro( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasMaestro|:"+ex);
		}
		return mapaGrupo;
	}	
	
	public HashMap<String, String> mapMaestrosCarga( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?) GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMaestrosCarga|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosFacultad( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMaestrosFacultad|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesCarga( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?) GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesCarga|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesFacultad( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesFacultad|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Mapa de la cantidad de materias agendadas por facultad en una carga academica */
	public HashMap<String, String> mapAgendadasFacultad( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN"
					+ " 	(SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND AGENDADA_E42 = 'S')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapAgendadasFacultad|:"+ex);
		}

		return mapaGrupo;
	}
	
	/* Mapa de la cantidad de materias agendadas por carga academica*/
	public HashMap<String, String> mapAgendadasCargas( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID IN( SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ? )"
					+ " AND CURSO_CARGA_ID IN"
					+ " 	(SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE CARGA_GRUPO.CARGA_ID||'%' AND AGENDADA_E42 = 'S')"
					+ " GROUP BY CARGA_ID";				
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapAgendadasCargas|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesMateria( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesMateria|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesMaestro( String codigoPersonal) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?";		
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesMaestro|:"+ex);
		}

		return mapaGrupo;
	}	
	
	public HashMap<String, String> mapActividadesCarga( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?) GROUP BY CARGA_ID";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesCarga|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesFacultad( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesFacultad|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesMateria( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando			= "";		
		try{
			comando = "SELECT CURSO_CARGA_ID AS LLAVE, GRUPO_NUM_ACTIV(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesMateria|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesMaestro( String codigoPersonal) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando			= ""; 
		try{
			comando = "SELECT CURSO_CARGA_ID AS LLAVE, GRUPO_NUM_ACTIV(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesMaestro|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConEval( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasConEval|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConActi( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando			= "";		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasConActi|:"+ex);
		}
		
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConEsquemaCompleto( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		String comando			= "";		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " AND (SELECT COALESCE(SUM(VALOR),0) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasConEsquemaCompleto|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasEsquemaCompleto( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		
		try{
			comando = " SELECT CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?)"
					+ " AND (SELECT COALESCE(SUM(VALOR),0) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY CARGA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasEsquemaCompleto|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaSumaEsquemaPorMateria( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, ENOC.GRUPO_SUM_EVAL(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaSumaEsquemaPorMateria|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaAlumnosPorMateria( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaAlumnosPorMateria|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasEvaluacion( String periodoId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARGA_ID || GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?)"
					+ " GROUP BY CARGA_ID, GRUPO_NUM_EVAL(CURSO_CARGA_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasEvaluacion|:"+ex);
		}
		return mapaGrupo;
	}
	
	public List<String> getMaestrosCargaPeriodo( String periodoId) {
		List<String> listMaestrosCarga = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO || @@ || USUARIO_APELLIDO(CODIGO_PERSONAL) AS NOMBRE FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN "
					+ "(SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ?) "
					+ "AND TRIM(CODIGO_PERSONAL) != ' ' "
					+ "ORDER BY USUARIO_APELLIDO(CODIGO_PERSONAL)";			
			listMaestrosCarga = enocJdbc.queryForList(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|getMaestrosCargaPeriodo|:"+ex);
		}
		
		return listMaestrosCarga;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public List<String> lisMaestrosCarga( String cargaId, String orden) {
		List<String> listMaestrosCarga = new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			listMaestrosCarga = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|lisMaestrosCarga|:"+ex);
		}
		
		return listMaestrosCarga;
	}
	
	public List<aca.Mapa> lisMaestrosEnCarga( String cargaId, String orden) {
		List<aca.Mapa> listMaestrosCarga = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, ENOC.EMP_NOMBRE(CODIGO_PERSONAL) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = ? "
					+ " AND LENGTH(CODIGO_PERSONAL) = 7 "+ orden;
			Object[] parametros = new Object[] {cargaId};
			listMaestrosCarga = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|lisMaestrosEnCarga|:"+ex);
		}
		
		return listMaestrosCarga;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public List<String> lisMaestrosSinFac( String cargaId, String facultad, String orden) {
		List<String> listMaestros = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? AND CODIGO_PERSONAL != '0'"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE CARGA_ID = ?) "+ orden;			
			listMaestros = enocJdbc.queryForList(comando, String.class, cargaId, facultad, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|lisMaestrosSinFac|:"+ex);
		}		
		return listMaestros;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public List<String> lisMaestrosCargayFac( String cargaId, String facultadId, String orden) {
		List<String> listMaestrosCarga = new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = ? AND CODIGO_PERSONAL != '0' AND ENOC.FACULTAD(CARRERA_ID) = ? "+ orden;			
			listMaestrosCarga = enocJdbc.queryForList(comando, String.class, cargaId, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|lisMaestrosCargayFac|:"+ex);
		}		
		return listMaestrosCarga;
	}
	
	public HashMap<String, String> profesorCarga( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, EMP_NOMBRE(CODIGO_PERSONAL) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|profesorCarga|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasProfesor( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? "
					+ " AND LENGTH(CODIGO_PERSONAL) = 7 GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasProfesor|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaMateriasConEsquema( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR"
					+ " FROM CARGA_GRUPO CG"
					+ " WHERE CARGA_ID = ?"
					+ " AND (SELECT SUM(VALOR) FROM CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaMateriasConEsquema|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionProfesor( String cargaId ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionProfesor|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesRegistradas( String cargaId ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM( (SELECT COUNT(EVALUACION_ID) FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) ) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO CG "
					+ " WHERE CARGA_ID = ?"
					+ "	GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesRegistradas|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesTotales( String cargaId ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(MATERIA_ALUMNOS_ACTIVOS(CURSO_CARGA_ID)*GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? AND LENGTH(CODIGO_PERSONAL) = 7"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvaluacionesTotales|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadProfesor( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadProfesor|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesRegistradas( String cargaId ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM( (SELECT COUNT(ACTIVIDAD_ID) FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) ) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO CG "
					+ " WHERE CARGA_ID = ?"
					+ "	GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesRegistradas|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesTotales( String cargaId ) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(MATERIA_ALUMNOS_ACTIVOS(CURSO_CARGA_ID)*GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ? AND LENGTH(CODIGO_PERSONAL) = 7"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapActividadesTotales|:"+ex);
		}
		return mapaGrupo;
	}
	
	// Map que cuenta el numeros de materias por facultad en una carga
	public HashMap<String, String> mapFacMaterias( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapFacMaterias|:"+ex);
		}

		return mapaGrupo;
	}
	
	// Map de materias evaluadas en e42 o Sistema Academico
	public HashMap<String, String> mapGrupoRegistro( String cargaId, String opcion) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		String comando			= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID AS LLAVE, CURSO_CARGA_ID AS VALOR FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+opcion+")";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapGrupoRegistro|:"+ex);
		}

		return mapaGrupo;
	}
	
	public List<String> lisAllMaestros( String cargaId) {
		List<String> lisAllMaestros = new ArrayList<String>();
		
		String comando	= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD || @@ || CARRERA_ID || @@ || CODIGO_PERSONAL "
					+ "FROM ENOC.CARGA_GRUPO "
					+ "WHERE CARGA_ID = ?"
					+ "GROUP BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, CODIGO_PERSONAL "
					+ "ORDER BY FACULTAD, CARRERA_ID, CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cargaId};
			lisAllMaestros = enocJdbc.queryForList(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|lisAllMaestros|:"+ex);
		}
		
		return lisAllMaestros;
	}
	
	public HashMap<String, String> mapEvalCargaAcademico( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		String comando	= "";
		
		try{
			comando ="SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO "
					+ "WHERE CARGA_ID = ? "
					+ "AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND EVALUACION_E42 = 0)"
					+ "GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvalCargaAcademico|:"+ex);
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvalCargaECuarentaYDos( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND EVALUACION_E42 != 0)"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapEvalCargaECuarentaYDos|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasMaestros( String cargaId) {
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR"
				+ " FROM ENOC.CARGA_GRUPO"
				+ " WHERE CARGA_ID = ?"
				+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaMaestros.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasMaestros|:"+ex);
		}
		return mapaMaestros;
	}
	
	public HashMap<String, String> mapMateriasMaestros() {
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try{
			String comando ="SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO GROUP BY CODIGO_PERSONAL";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa grupo : lista) {
				mapaMaestros.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasMaestros|:"+ex);
		}
		return mapaMaestros;
	}
	
	public HashMap<String, String> mapMateriasAuxiliares(){
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();	
		try{
			String comando ="SELECT CODIGO_OTRO AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO GROUP BY CODIGO_OTRO";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa grupo : lista) {
				mapaMaestros.put(grupo.getLlave(), (String)grupo.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapMateriasAuxiliares|:"+ex);
		}
		return mapaMaestros;
	}
	
	public HashMap<String, String> mapCarreraOrigen( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		String comando	= "";
		
		try{
			comando ="SELECT CURSO_CARGA_ID AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapa.put(grupo.getLlave(), (String)grupo.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapCarreraOrigen|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaMateriaCosto( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();	
		try{
			String comando ="SELECT CURSO_CARGA_ID AS LLAVE, PRECIO AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa grupo : lista) {
				mapa.put(grupo.getLlave(), (String)grupo.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapCarreraOrigen|:"+ex);
		}

		return mapa;
	}
	
	//	
	public HashMap<String, String> mapaHorarioMateria( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID AS LLAVE, (SELECT COALESCE(HORARIO_ID,0) FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = ENOC.FACULTAD(CARRERA_ID)) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapa.put(grupo.getLlave(), (String)grupo.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaHorarioMateria|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String, CargaGrupo> mapCargaGrupoPorCarga(String cargaId) {
		HashMap<String, CargaGrupo> mapaCargaGrupo = new HashMap<String, CargaGrupo>();
		List<CargaGrupo> lista	= new ArrayList<CargaGrupo>();	
		
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
					+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
					+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = ?"; 
			
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaGrupoMapper(), parametros);
			for(CargaGrupo carga : lista){				
				mapaCargaGrupo.put(carga.getCursoCargaId(), carga);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapCargaGrupoPorCargaCarrera|:"+ex);
		}

		return mapaCargaGrupo;
	}	
	
	public HashMap<String, CargaGrupo> mapaGruposDelMaestro(String codigoPersonal, String cargaId){
		HashMap<String, CargaGrupo> mapa 	= new HashMap<String,CargaGrupo>();
		List<CargaGrupo> lista 				= new ArrayList<CargaGrupo>();	
		try{			
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
					+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
					+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			lista = enocJdbc.query(comando, new CargaGrupoMapper(), parametros);			
			for (CargaGrupo objeto : lista) {
				mapa.put(objeto.getCursoCargaId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaGruposDelMaestro|:"+ex);
			ex.printStackTrace();
		}
		return mapa;
	}
	
	public HashMap<String, CargaGrupo> mapaGruposEnGraduacion(String eventoId){
		HashMap<String, CargaGrupo> mapa 	= new HashMap<String,CargaGrupo>();
		List<CargaGrupo> lista 				= new ArrayList<CargaGrupo>();	
		try{			
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
					+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
					+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO, MODO, FECHA, USUARIO"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE ESTADO IN ('1','2') "
					+ " AND CURSO_CARGA_ID IN "
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT "
					+ "			WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ?))";			
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new CargaGrupoMapper(), parametros);			
			for (CargaGrupo objeto : lista) {
				mapa.put(objeto.getCursoCargaId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.Hora|mapaGruposEnGraduacion|:"+ex);
			ex.printStackTrace();
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaGruposModos(String cargaId){
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID||MODO AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY CARRERA_ID||MODO";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaMaestros.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaGruposModos|:"+ex);
		}

		return mapaMaestros;
	}
	
	public HashMap<String, String> mapaSumaPorFacultades( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CC.FACULTAD_ID AS LLAVE, COUNT(*) AS VALOR FROM CARGA_GRUPO CG, CAT_CARRERA CC"
					+ " WHERE CG.CARGA_ID = ? AND CC.CARRERA_ID = CG.CARRERA_ID AND ENOC.SUMA_ESTRA(CG.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY CC.FACULTAD_ID";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaSumaPorFacultades|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaSumaPorCarreras( String cargaId) {
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.SUMA_ESTRA(CURSO_CARGA_ID) >= 100 GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaSumaPorCarreras|:"+ex);
		}

		return mapaGrupo;
	}
	
	public HashMap<String, String> mapaPorCargayBloque( String cargaId){
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT BLOQUE_ID AS LLAVE, COUNT(BLOQUE_ID) AS VALOR FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? GROUP BY BLOQUE_ID";
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa grupo : lista) {
				mapaGrupo.put(grupo.getLlave(), (String)grupo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaPorCargayBloque|:"+ex);
		}
		return mapaGrupo;
	}

}
