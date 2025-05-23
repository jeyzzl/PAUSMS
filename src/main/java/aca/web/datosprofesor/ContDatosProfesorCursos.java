package aca.web.datosprofesor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.archivos.spring.ArchivosAlumnoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupo;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.carga.spring.CargaPronDao;
import aca.catalogo.CatGradePoint;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatCiudadDao;
import aca.catalogo.spring.CatEdificio;
import aca.catalogo.spring.CatEdificioDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatEstrategia;
import aca.catalogo.spring.CatEstrategiaDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatGradePointDao;
import aca.catalogo.spring.CatHorario;
import aca.catalogo.spring.CatHorarioDao;
import aca.catalogo.spring.CatHorarioFacultad;
import aca.catalogo.spring.CatHorarioFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatSalon;
import aca.catalogo.spring.CatSalonDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoAlumnoResp;
import aca.edo.spring.EdoAlumnoRespDao;
import aca.kardex.spring.KrdxAlumnoEval;
import aca.kardex.spring.KrdxAlumnoEvalDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxCursoCal;
import aca.kardex.spring.KrdxCursoCalDao;
import aca.log.spring.LogOperacionDao;
import aca.objeto.spring.Hora;
import aca.objeto.spring.HoraDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.AlumnoEficiencia;
import aca.vista.spring.AlumnoEficienciaDao;
import aca.vista.spring.AlumnoEvaluacionDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.CargaHorario;
import aca.vista.spring.CargaHorarioDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContDatosProfesorCursos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	@Autowired
	CatCiudadDao catCiudadDao;
	
	@Autowired
	CatReligionDao catReligionDao;
	
	@Autowired
	CatEstrategiaDao catEstrategiaDao;	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	LogOperacionDao logOperacionDao;
	
	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	@Autowired
	AlumnoEvaluacionDao alumnoEvaluacionDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;

	@Autowired	
	private CargaPronDao cargaPronDao;	
	
	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;

	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	EdoAlumnoPregDao edoAlumnoPregDao;
	
	@Autowired
	EdoAlumnoRespDao edoAlumnoRespDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private KrdxCursoCalDao krdxCursoCalDao;
	
	@Autowired
	private CargaBloqueDao cargaBloqueDao;

	@Autowired
	private CatHorarioFacultadDao catHorarioFacultadDao;
	
	@Autowired
	private HoraDao horaDao;
	
	@Autowired
	private CatEdificioDao catEdificioDao;
	
	@Autowired
	private CatSalonDao catSalonDao;
	
	@Autowired
	private CatHorarioDao catHorarioDao;
	
	@Autowired
	private CargaHorarioDao cargaHorarioDao;	
	
	@Autowired
	AlumnoEficienciaDao alumnoEficienciaDao;
	
	@Autowired
	ArchivosAlumnoDao archivosAlumnoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CargaAlumnoDao cargaAlumnoDao;

	@Autowired
	private ParametrosDao parametrosDao;

	@Autowired
	private CatGradePointDao catGradePointDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_profesor/cursos/cursos")
	public String datosProfesorCursosCursos(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String maestroNombre	= "-";
		String equipoPor		= "0";		 
		String periodoSesion	= "0";
		String cargaSesion		= "0";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoEmpleado");
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
			equipoPor		 	= maestrosDao.getEquipo(codigoPersonal);
			periodoSesion		= (String) sesion.getAttribute("periodo");
			cargaSesion			= (String) sesion.getAttribute("cargaId");
		}
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListPeriodosMaestro(codigoPersonal, " ORDER BY PERIODO_ID DESC");
		
		boolean existePeriodoSesion = false;
		for(CatPeriodo per :lisPeriodos) {
			if (per.getPeriodoId().equals(periodoSesion)) {
				existePeriodoSesion = true;
				break;
			}
		}
		if (periodoId.equals("0") && existePeriodoSesion) {
			periodoId = periodoSesion;
		}else if (periodoId.equals("0") && lisPeriodos.size()>=1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}else if (!periodoId.equals("0") && sesion!=null){
			sesion.setAttribute("periodo", periodoId);
		}	 
		
		List<Carga> lisCargas 						= cargaDao.getListMaestroPeriodo(codigoPersonal, periodoId);
		
		boolean existeCargaSesion = false;
		for (Carga car : lisCargas) {
			if (car.getCargaId().equals(cargaSesion)) {
				existeCargaSesion = true;
				break;
			}
		}
		
		if (cargaId.equals("0") && existeCargaSesion) {
			cargaId = cargaSesion;
		}else if (cargaId.equals("0") && lisCargas.size()>= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}else if (!cargaId.equals("0") && sesion!=null) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		List<CargaAcademica> lisCursos 					= cargaAcademicaDao.getListaMaestro(cargaId, codigoPersonal, " ORDER BY NOMBRE_CURSO");
		
		HashMap<String,String> mapaPron 				= cargaPronDao.mapaTodos();
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaEdo				 	= edoAlumnoRespDao.mapaEdoDeMateria(cargaId);
		HashMap<String,String> mapaHoras			 	= cargaGrupoHoraDao.mapaHorasPorMateria(cargaId);
		HashMap<String,String> mapaPromedioMaterias	 	= edoAlumnoRespDao.mapaPromedioMaterias(cargaId, codigoPersonal);
		HashMap<String,String> mapaContestaron		 	= edoAlumnoRespDao.mapaContestaron(cargaId);
		HashMap<String,String> mapaTotAlumnos		 	= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','1','2','3','5','6'");		
		HashMap<String,String> mapaPendientes		 	= krdxCursoActDao.mapaTotalAlumnos(cargaId, "'I','5','6'");
		HashMap<String,String> mapaDiferidas		 	= krdxCursoCalDao.mapaCambiosNotas(codigoPersonal, "D");
		HashMap<String,String> mapaCorrecciones		 	= krdxCursoCalDao.mapaCambiosNotas(codigoPersonal, "C");
		HashMap<String,String> mapaAvance			 	= cargaGrupoEvaluacionDao.mapaAvanceMaestro(codigoPersonal, cargaId);
		HashMap<String,String> mapaEvalPendientes	 	= cargaGrupoEvaluacionDao.mapaEvalPendientes(codigoPersonal, cargaId);
		HashMap<String,String> mapaActPendientes	 	= cargaGrupoEvaluacionDao.mapaActPendientes(codigoPersonal, cargaId);
		HashMap<String,String> mapaGraduandos		 	= krdxCursoActDao.mapaGruposConGraduandos(codigoPersonal);
		HashMap<String,String> mapaPuntos 				= cargaGrupoEvaluacionDao.mapaSumaPorMateria(codigoPersonal);
		HashMap<String, String> mapaLetrasDeNotas		= catGradePointDao.mapaLetrasDeNotas(" ORDER BY INICIO");

		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("equipoPor", equipoPor);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCursos", lisCursos);
		
		modelo.addAttribute("mapaPron", mapaPron);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaEdo", mapaEdo);
		modelo.addAttribute("mapaHoras", mapaHoras);
		modelo.addAttribute("mapaPromedioMaterias", mapaPromedioMaterias);
		modelo.addAttribute("mapaContestaron", mapaContestaron);
		modelo.addAttribute("mapaTotAlumnos", mapaTotAlumnos);
		modelo.addAttribute("mapaPendientes", mapaPendientes);
		modelo.addAttribute("mapaDiferidas", mapaDiferidas);
		modelo.addAttribute("mapaCorrecciones", mapaCorrecciones);
		modelo.addAttribute("mapaAvance", mapaAvance);
		modelo.addAttribute("mapaEvalPendientes", mapaEvalPendientes);
		modelo.addAttribute("mapaActPendientes", mapaActPendientes);
		modelo.addAttribute("mapaGraduandos", mapaGraduandos);
		modelo.addAttribute("mapaPuntos", mapaPuntos);
		modelo.addAttribute("mapaLetrasDeNotas", mapaLetrasDeNotas);
		
		return "datos_profesor/cursos/cursos";
	}
	
	@RequestMapping("/datos_profesor/cursos/evaluacion")
	public String datosProfesorCursosEvaluacion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 	= "0";
		String maestroNombre	= "-";
		int numAlumnos 			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'I','1','2','3','4','5','6'");
		String avanceMateria	= cargaGrupoEvaluacionDao.getAvanceEvaluacion(cursoCargaId);		
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){			
			codigoPersonal		= cargaGrupoDao.getCodigoPersonal(cursoCargaId);
			maestroNombre 		= maestrosDao.getNombreMaestro(codigoPersonal,"NOMBRE");
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 		= cargaGrupoEvaluacionDao.getLista(cursoCargaId," ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA");
		List<CargaGrupoActividad> lisActividades 		= cargaGrupoActividadDao.getListCurso(cursoCargaId," ORDER BY EVALUACION_ID, ENOC.CARGA_GRUPO_ACTIVIDAD.FECHA");
		
		HashMap<String, String> mapaNotasEvaluaciones 			= cargaGrupoEvaluacionDao.mapaNotasEnEvaluaciones(cursoCargaId);
		HashMap<String, String> mapaNotasActividadesPorEval		= cargaGrupoEvaluacionDao.mapaNotasActividadesPorEvaluacion(cursoCargaId);
		HashMap<String, String> mapaAvanceEvaluaciones 			= cargaGrupoEvaluacionDao.mapaAvancePorEvaluacion(cursoCargaId);
		HashMap<String, String> mapaNotasActividades 			= cargaGrupoEvaluacionDao.mapaNotasEnActividades(cursoCargaId);
		HashMap<String, String> mapaAvanceActividades 			= cargaGrupoEvaluacionDao.mapaAvancePorActividades(cursoCargaId);
		HashMap<String, String> mapaActividadesPorEvaluacion	= cargaGrupoActividadDao.mapaActividadesPorEvaluacion(cursoCargaId);
		
		modelo.addAttribute("numAlumnos", numAlumnos);
		modelo.addAttribute("avanceMateria", avanceMateria);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisActividades", lisActividades);
		modelo.addAttribute("mapaNotasEvaluaciones", mapaNotasEvaluaciones);
		modelo.addAttribute("mapaNotasActividadesPorEval", mapaNotasActividadesPorEval);
		modelo.addAttribute("mapaAvanceEvaluaciones", mapaAvanceEvaluaciones);
		modelo.addAttribute("mapaNotasActividades", mapaNotasActividades);
		modelo.addAttribute("mapaAvanceActividades", mapaAvanceActividades);
		modelo.addAttribute("mapaActividadesPorEvaluacion", mapaActividadesPorEvaluacion);
			
		return "datos_profesor/cursos/evaluacion";
	}
	
	@RequestMapping("/datos_profesor/cursos/notas")
	public String datosProfesorCursosNotas(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		String codigoEmpleado	= "0";
		String maestroNombre 	= "";
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		int diferida			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'6'");
		CargaGrupo cargaGrupo	= new CargaGrupo();
		MapaCurso mapaCurso		= mapaCursoDao.mapeaRegId(cursoId);
		
		int numEstrategias  	= cargaGrupoEvaluacionDao.getNumEstrategias(cursoCargaId);
		int numEvaluadas		= cargaGrupoEvaluacionDao.getNumEstrategiasEvaluadas(cursoCargaId);
		int numBajas 			= cargaGrupoEvaluacionDao.getNumAlumnosBaja(cursoCargaId);
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
			if (cargaGrupoDao.getCodigoPersonal(cursoCargaId).equals(codigoEmpleado) || cargaGrupoDao.getCodigoOtro(cursoCargaId).equals(codigoEmpleado)) {
				//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroEvaluar");				
				// Modificar estado a ordinario				
				if (cargaGrupoDao.existeReg(cursoCargaId)){
					cargaGrupo = cargaGrupoDao.mapeaRegId(cursoCargaId);
					if (cargaGrupo.getEstado().equals("1") && krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'M','I','1','2','3','4','5','6'") >= 1){
						cargaGrupo.setEstado("2");
						cargaGrupoDao.updateReg(cargaGrupo);
					}
				}				
				maestroNombre = maestrosDao.getNombreMaestro(cargaGrupo.getCodigoPersonal(), "NOMBRE");	
			}else{
				System.out.println(codigoEmpleado+" is not a lecturer for the subject: "+cursoCargaId);
			}		
		}	
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 		= cargaGrupoEvaluacionDao.getLista(cursoCargaId, " ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY-MM-DD'), EVALUACION_ID");
		List<KrdxCursoAct> lisAlumnos 					= krdxCursoActDao.lisAlumnosEnMateria(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaEstrategias			= catEstrategiaDao.getMapNombre("");
		HashMap<String,String> mapaAlumnos				= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		HashMap<String,String> mapaNotas				= krdxAlumnoEvalDao.getMapAlumEval(cursoCargaId);
		HashMap<String,AlumnoEficiencia> mapaEficiencia	= alumnoEficienciaDao.mapaMateria(cursoCargaId);
		HashMap<String,String> mapaTipoCal				= catTipoCalDao.mapTipoCal();
		HashMap<String,String> mapaPuntos				= krdxCursoActDao.mapAlumnoPuntos(cursoCargaId);
		HashMap<String,String> mapaExtras				= krdxCursoActDao.mapAlumnoExtras(cursoCargaId);
		HashMap<String,String> mapaEvaluados			= alumnoEficienciaDao.mapPuntosEvaluadosMateria(cursoCargaId);
		HashMap<String,String> mapaLimiteExtras			= krdxCursoActDao.mapLimiteExtra(cursoCargaId);
		HashMap<String,MapaCurso> mapaCursoMateria		= mapaCursoDao.mapaCursosEnMateria(cursoCargaId);
		HashMap<String,String> mapaActividades			= cargaGrupoActividadDao.mapaActividadesPorEvaluacion(cursoCargaId);
		HashMap<String,String> mapaPromedios			= new HashMap<String,String>();
		HashMap<String,String> mapaArchivosEval			= archivosAlumnoDao.mapaArchivosPorEvaluacion(cursoCargaId);
		HashMap<String,String> mapaArchivosAlum			= archivosAlumnoDao.mapaArchivosPorAlumno(cursoCargaId);
		HashMap<String, String> mapaLetrasDeNotas		= catGradePointDao.mapaLetrasDeNotas(" ORDER BY INICIO");

		// Obtiene los promedios de los alumnos
		if (cargaGrupo.getEstado().equals("2")) {
			for (KrdxCursoAct actual :lisAlumnos){
				String promedio = krdxAlumnoEvalDao.getAlumnoPromedio(cursoCargaId, actual.getCodigoPersonal());
				mapaPromedios.put(actual.getCodigoPersonal(), promedio);
			}
		}
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("numEstrategias", numEstrategias);
		modelo.addAttribute("numEvaluadas", numEvaluadas);
		modelo.addAttribute("numBajas", numBajas);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("diferida", diferida);
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaPuntos", mapaPuntos);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaEvaluados", mapaEvaluados);
		modelo.addAttribute("mapaLimiteExtras", mapaLimiteExtras);		
		modelo.addAttribute("mapaCursoMateria", mapaCursoMateria);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		modelo.addAttribute("mapaArchivosEval", mapaArchivosEval);
		modelo.addAttribute("mapaArchivosAlum", mapaArchivosAlum);
		modelo.addAttribute("mapaEficiencia", mapaEficiencia);
		modelo.addAttribute("mapaLetrasDeNotas", mapaLetrasDeNotas);
		
		return "datos_profesor/cursos/notas";
	}
	
	@RequestMapping("/datos_profesor/cursos/listado")
	public String datosProfesorCursosListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoEmpleado");
		}	
		String cursoCargaId	= request.getParameter("CursoCargaId");	
		String cargaId		= request.getParameter("CargaId");	
		String bloqueId		= request.getParameter("BloqueId");	
		
		List<AlumnoCurso> lisAlumnos 					= alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String, CatModalidad> mapModalidad 		= catModalidadDao.getMapAll("");
		HashMap<String, AlumPersonal> mapPersonal 		= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap<String, String> mapaModoCargaAlumnos 	= cargaAlumnoDao.mapaModoCargaAlumnos(cargaId, bloqueId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("mapPersonal", mapPersonal);
		modelo.addAttribute("mapaModoCargaAlumnos", mapaModoCargaAlumnos);
		
		return "datos_profesor/cursos/listado";
	}
	
	@RequestMapping("/datos_profesor/cursos/tarjeta")
	public String datosProfesorCursosTarjeta(HttpServletRequest request, Model modelo){
		String cursoCargaId			= request.getParameter("CursoCargaId");
		
		List <AlumnoCurso> lisAlumnos 					= alumnoCursoDao.getListCurso(cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap <String, CatTipoCal> mapaCat 			= catTipoCalDao.getMapAll("");
		HashMap <String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap <String, CatEstado> mapaEstados 		= catEstadoDao.getMapAll();
		HashMap <String, CatCiudad> mapaCiudades 		= catCiudadDao.getMapAll("");
		HashMap <String, CatReligion> mapaReligiones 	= catReligionDao.getMapAll("");
		HashMap <String, AlumPersonal> mapaPersonal		= alumPersonalDao.mapAlumnosEnMateria(cursoCargaId);
		HashMap <String, AlumAcademico> mapaAcademico	= alumAcademicoDao.mapAlumnosEnMateria(cursoCargaId);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaCat", mapaCat);
		modelo.addAttribute("mapaPersonal", mapaPersonal);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaCiudades", mapaCiudades);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		
		return "datos_profesor/cursos/tarjeta";
	}	
	
	@RequestMapping("/datos_profesor/cursos/cactames")
	public String datosProfesorCursosCactaMes(HttpServletRequest request){
		
		return "datos_profesor/cursos/cactames";
	}
	
	@RequestMapping("/datos_profesor/cursos/actames")
	public String datosProfesorCursosActaMes(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroActaMes");
		
		String cursoCargaId		= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		String estado			= "X";
		String maestroNombre	= "-";
		String facultadNombre	= "-";
		String carreraNombre	= "-";
		String coordinador		= "-";
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		MapaCurso mapaCurso		= new MapaCurso();
		if (cargaGrupoDao.existeReg(cursoCargaId)){			
			cargaGrupo 			= cargaGrupoDao.mapeaRegId(cursoCargaId);
			estado 				= cargaGrupo.getEstado();
			mapaCurso 			= mapaCursoDao.mapeaRegId(cargaGrupoDao.getCursoId(cursoCargaId));
			maestroNombre		= usuariosDao.getNombreUsuario(cargaGrupo.getCodigoPersonal(), "NOMBRE");
			facultadNombre 		= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(cargaGrupo.getCarreraId()));
			carreraNombre 		= catCarreraDao.getNombreCarrera(cargaGrupo.getCarreraId());
			coordinador			= usuariosDao.getNombreUsuario(catCarreraDao.getCoordinador(cargaGrupo.getCarreraId()), "NOMBRE");
		}
		
		Parametros parametros = parametrosDao.mapeaRegId("1");

		List<CargaGrupoEvaluacion> lisEvaluaciones 			= cargaGrupoEvaluacionDao.getLista( cursoCargaId, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
		List<AlumnoCurso> lisAlumnos 						= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<String> lisMeses								= alumnoEvaluacionDao.getActaMeses(cursoCargaId);
		
		HashMap<String,CatEstrategia> mapaEstrategias 		= catEstrategiaDao.getMapAll("");
		HashMap<String,KrdxAlumnoEval> mapaEvaluaciones	 	= krdxAlumnoEvalDao.mapAlumEval(cursoCargaId);
		HashMap<String,KrdxCursoAct> mapaNotas	 			= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String,CatTipoCal> mapaTipoCal	 			= catTipoCalDao.getMapAll("");
		HashMap<String,AlumPlan> mapaPlanes					= alumPlanDao.mapaPorMateria(cursoCargaId);
		HashMap<String,String> mapaEvaluadas				= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaExtras					= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnoPuntos				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaAlumnoExtras				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnos					= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		HashMap<String,String> mapaPromediosPorMes			= alumnoEvaluacionDao.mapaPromedioPorMes(cursoCargaId);
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("coordinador", coordinador);
		modelo.addAttribute("parametros", parametros);
				
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisMeses", lisMeses);
		
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaEvaluadas", mapaEvaluadas);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaAlumnoPuntos", mapaAlumnoPuntos);
		modelo.addAttribute("mapaAlumnoExtras", mapaAlumnoExtras);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPromediosPorMes", mapaPromediosPorMes);
		
		return "datos_profesor/cursos/actames";
	}	
	
	@RequestMapping("/datos_profesor/cursos/cacta")
	public String datosProfesorCursosCacta(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContPortalesMaestro|portalesMaestroCacta");
		return "datos_profesor/cursos/cacta";
	}	
	
	@RequestMapping("/datos_profesor/cursos/acta")
	public String datosProfesorCursosActa(HttpServletRequest request, Model modelo){
		
		String cursoCargaId		= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		String estado			= "X";
		String maestroNombre	= "-";
		String facultadNombre	= "-";
		String carreraNombre	= "-";
		String coordinador		= "-";
		CargaGrupo cargaGrupo 	= new CargaGrupo();
		MapaCurso mapaCurso		= new MapaCurso();
		if (cargaGrupoDao.existeReg(cursoCargaId)){			
			cargaGrupo 			= cargaGrupoDao.mapeaRegId(cursoCargaId);
			estado 				= cargaGrupo.getEstado();
			mapaCurso 			= mapaCursoDao.mapeaRegId(cargaGrupoDao.getCursoId(cursoCargaId));
			maestroNombre		= usuariosDao.getNombreUsuario(cargaGrupo.getCodigoPersonal(), "NOMBRE");
			facultadNombre 		= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(cargaGrupo.getCarreraId()));
			carreraNombre 		= catCarreraDao.getNombreCarrera(cargaGrupo.getCarreraId());
			coordinador			= usuariosDao.getNombreUsuario(catCarreraDao.getCoordinador(cargaGrupo.getCarreraId()), "NOMBRE");
		}
		
		List<CargaGrupoEvaluacion> lisEvaluaciones 			= cargaGrupoEvaluacionDao.getLista( cursoCargaId, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
		List<AlumnoCurso> lisAlumnos 						= alumnoCursoDao.getListCurso(cursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String,CatEstrategia> mapaEstrategias 		= catEstrategiaDao.getMapAll("");
		HashMap<String,KrdxAlumnoEval> mapaEvaluaciones	 	= krdxAlumnoEvalDao.mapAlumEval(cursoCargaId);
		HashMap<String,KrdxCursoAct> mapaNotas	 			= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String,CatTipoCal> mapaTipoCal	 			= catTipoCalDao.getMapAll("");
		HashMap<String,String> mapaEvaluadas				= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaExtras					= alumnoEvaluacionDao.mapaSumaValor(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnoPuntos				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'%','P'");
		HashMap<String,String> mapaAlumnoExtras				= alumnoEvaluacionDao.mapaPuntosAlumno(cursoCargaId, "'E'");
		HashMap<String,String> mapaAlumnos					= alumPersonalDao.mapAlumnosMateria(cursoCargaId);

		Parametros parametros = parametrosDao.mapeaRegId("1");
		
		modelo.addAttribute("cargaGrupo", cargaGrupo);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("estado", estado);
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("coordinador", coordinador);
		modelo.addAttribute("parametros", parametros);
				
		modelo.addAttribute("lisEvaluaciones", lisEvaluaciones);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		modelo.addAttribute("mapaNotas", mapaNotas);
		modelo.addAttribute("mapaEvaluadas", mapaEvaluadas);
		modelo.addAttribute("mapaExtras", mapaExtras);
		modelo.addAttribute("mapaAlumnoPuntos", mapaAlumnoPuntos);
		modelo.addAttribute("mapaAlumnoExtras", mapaAlumnoExtras);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "datos_profesor/cursos/acta";
	}
	
	@RequestMapping("/datos_profesor/cursos/opinion_materia")
	public String portalesMaestroOpinionMateria(HttpServletRequest request, Model modelo){
		String codigoPersonal	= "";
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}		
		
		String cursoCargaId			= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		String cursoId				= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String edoId				= edoAlumnoRespDao.getEdoId(cursoCargaId);
		String nombreMaestro		= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");		
		String nombreCurso			= mapaCursoDao.getNombreCurso(cursoId);
		String participaron			= edoAlumnoRespDao.getAlumEvalMateria(cursoCargaId);
		String faltaron				= edoAlumnoRespDao.getAlumFaltantesMateria(cursoCargaId);
		List<EdoAlumnoPreg> lisPreg	= edoAlumnoPregDao.getListEdo(edoId, "AND TIPO = 'O' AND SECCION = 'B' ORDER BY ORDEN");
		
		HashMap <String, String> mapaPromedioRespuestas = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaPromedioRespuestas.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getPromedioPreguntaPorMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}
		
		HashMap <String, String> mapaMinimo = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaMinimo.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getMinPreguntaMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}
		
		HashMap <String, String> mapaMaximo = new HashMap <String, String>();
		for (EdoAlumnoPreg pregunta : lisPreg) {
			mapaMaximo.put(pregunta.getPreguntaId(), edoAlumnoRespDao.getMaxPreguntaMateria(edoId, pregunta.getPreguntaId(), cursoCargaId));
		}		
		
		modelo.addAttribute("nombreCurso",nombreCurso);
		modelo.addAttribute("participaron",participaron);
		modelo.addAttribute("faltaron",faltaron);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisPreg", lisPreg);
		modelo.addAttribute("mapaPromedioRespuestas", mapaPromedioRespuestas);
		modelo.addAttribute("mapaMinimo", mapaMinimo);
		modelo.addAttribute("mapaMaximo", mapaMaximo);
		
		
		return "datos_profesor/cursos/opinion_materia";
	}
	
	@RequestMapping("/datos_profesor/cursos/comentarios")
	public String portalesMaestroComentarios(HttpServletRequest request, Model modelo){		
		
		String codigoPersonal		= "";
		String nombreMaestro 		= ""; 
		HttpSession sesion			= request.getSession();
		if (sesion!=null){
			codigoPersonal			= (String) sesion.getAttribute("codigoPersonal");
			nombreMaestro			= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE"); 
		}	
		String cursoCargaId			= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
		
		List<EdoAlumnoPreg> lisPreguntas 		= edoAlumnoPregDao.getListComentarios(cursoCargaId,"D", "ORDER BY 1");
		List<EdoAlumnoResp> lisRespuestas 		= edoAlumnoRespDao.lisRespuestas(cursoCargaId,"ORDER BY 1");
		
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("lisPreguntas", lisPreguntas);
		modelo.addAttribute("lisRespuestas", lisRespuestas);
		
		return "datos_profesor/cursos/comentarios";
	}
	
	@RequestMapping("/datos_profesor/cursos/nuevoHorario")
	public String datosProfesorCursosNuevoHorario(HttpServletRequest request, Model modelo){
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String horarioId		= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
		String codigoEmpleado 	= "";			
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
		}	
		
		List<CargaBloque> lisBloques = cargaBloqueDao.lisBloquesDelMaestro(codigoEmpleado, cargaId, " ORDER BY BLOQUE_ID");
		boolean existe = false;
		for(CargaBloque bloque: lisBloques){
			if (bloque.getBloqueId().equals(bloqueId)) {
				existe = true;
				break;
			}
		}
		if (!existe && lisBloques.size()>=1) bloqueId = lisBloques.get(0).getBloqueId();
		
		List<CatHorario> lisHorarios = catHorarioDao.lisHorariosPorMaestro(codigoEmpleado, bloqueId, cargaId, "");
		boolean existeHora = false;
		for(CatHorario hora: lisHorarios){
			if (hora.getHorarioId().equals(horarioId)) {
				existeHora = true;
				break;
			}
		}
		if (!existeHora && lisHorarios.size() >= 1){
			horarioId = lisHorarios.get(0).getHorarioId();
		}
		
		List<String> turno = catHorarioFacultadDao.getTurno(horarioId, "ORDER BY TURNO");
		List<CatHorarioFacultad> listHorario = new ArrayList<CatHorarioFacultad>();
		HashMap<String, List<CatHorarioFacultad>> mapaListaHorario = new HashMap<String, List<CatHorarioFacultad>>();

		for(String tur : turno){
			listHorario = catHorarioFacultadDao.getListaTurno(horarioId, tur, " ORDER BY TURNO, PERIODO");
			mapaListaHorario.put(horarioId+tur, listHorario);
		}
		
		Maestros maestro = maestrosDao.mapeaRegId(codigoEmpleado);
		
		HashMap<String, String> mapaFacultadPorHorario 		= catFacultadDao.mapaFacultadPorHorario();
		HashMap<String, Hora> mapHoras 						= horaDao.mapaHorasDelMaestro(codigoEmpleado, cargaId, bloqueId);
		HashMap<String, List<Hora>> mapHorasPortalMaestro	= horaDao.mapaHorasDelMaestroPortalMaestro(codigoEmpleado, cargaId, bloqueId);
		HashMap<String, String> mapCursos 					= horaDao.mapaCursosDelMaestro(codigoEmpleado, cargaId);
		HashMap<String, CargaGrupo> mapGrupos				= cargaGrupoDao.mapaGruposDelMaestro(codigoEmpleado, cargaId);
		HashMap<String,String> mapaSalones 					= catSalonDao.mapaNombresSalones();
		
		modelo.addAttribute("maestro", maestro);
		modelo.addAttribute("bloqueId", bloqueId);
		modelo.addAttribute("horarioId", horarioId);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("turno", turno);
		modelo.addAttribute("mapaFacultadPorHorario", mapaFacultadPorHorario);
		modelo.addAttribute("mapHoras", mapHoras);
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("mapGrupos", mapGrupos);
		modelo.addAttribute("mapaSalones", mapaSalones);
		modelo.addAttribute("mapaListaHorario", mapaListaHorario);
		modelo.addAttribute("mapHorasPortalMaestro", mapHorasPortalMaestro);
		modelo.addAttribute("lisHorarios", lisHorarios);
		
		return "datos_profesor/cursos/nuevoHorario";
	}
	
	@RequestMapping("/datos_profesor/cursos/horario3")
	public String datosProfesorCursosHorario3(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesor|datosProfesorCursosHorario3");
		return "datos_profesor/cursos/horario3";
	}

	@RequestMapping("/datos_profesor/cursos/correccionPDF")
	public String datosProfesorCursosCorrecionPDF(HttpServletRequest request, Model modelo){
		
		String institucion 		= "";
		String cursoCargaId 	= request.getParameter("CursoCargaId");
		String cursoId			= request.getParameter("CursoId");
		String materia			= request.getParameter("Materia");
		String maestro			= request.getParameter("Maestro");
		String opcion 			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion"); 
		
		aca.util.Fecha fecha 	= new aca.util.Fecha();
		
		HttpSession sesion		= request.getSession();
		if (sesion!=null){
			institucion 		= (String) sesion.getAttribute("institucion");
		}
		
		HashMap<String, String> cursos 				= krdxCursoActDao.mapaAlumnoCurso(cursoCargaId);
		HashMap<String,KrdxCursoAct> mapaNotas	 	= krdxCursoActDao.mapaNotas(cursoCargaId);
		HashMap<String, String> mapAlumnosMateria 	= alumPersonalDao.mapAlumnosMateria(cursoCargaId);
		
		String carreraOrigen	= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String carreraId		= mapaPlanDao.getCarreraId(carreraOrigen.substring(0,8));
		String strFecha			= aca.util.Fecha.getHoy();
		String yearName			= aca.util.NumberToLetter.convertirYear(Integer.parseInt(fecha.getYear(strFecha))).trim();
		
		List<KrdxCursoCal> lisDiferida = null;
		if (opcion.equals("0")){
			lisDiferida	= krdxCursoCalDao.getListHoy(cursoCargaId,"C", " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");	
		}else{
			lisDiferida	= krdxCursoCalDao.getListDiferidas(cursoCargaId,"C", " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		}
		
		String nombreFacultad 	= catFacultadDao.getNombreFacultad(catCarreraDao.getFacultadId(carreraId));
	    String nombreCarrera  	= catCarreraDao.getNombreCarrera(carreraId);
	    String nombreModalidad 	= catModalidadDao.getNombreModalidad(cargaGrupoDao.getModalidad(cursoCargaId));
	    
	    int ciclo = 0;
	    
	    if(mapaCursoDao.existeReg(cursoId)) {
	    	ciclo = mapaCursoDao.getCiclo(cursoId);
	    }
	    
	    String getCiclo = cargaDao.getCiclo(cursoCargaId.substring(0,6));
		
		modelo.addAttribute("institucion",institucion);
		modelo.addAttribute("cursoCargaId",cursoCargaId);
		modelo.addAttribute("cursoId",cursoId);
		modelo.addAttribute("materia",materia);
		modelo.addAttribute("maestro",maestro);
		modelo.addAttribute("cursos",cursos);
		modelo.addAttribute("carreraId",carreraId);
		modelo.addAttribute("yearName",yearName);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("mapaNotas",mapaNotas);
		modelo.addAttribute("nombreFacultad",nombreFacultad);
		modelo.addAttribute("nombreCarrera",nombreCarrera);
		modelo.addAttribute("nombreModalidad",nombreModalidad);
		modelo.addAttribute("ciclo",ciclo);	
		modelo.addAttribute("getCiclo", getCiclo);
		modelo.addAttribute("mapAlumnosMateria", mapAlumnosMateria);
		modelo.addAttribute("lisDiferida", lisDiferida);
		
		return "datos_profesor/cursos/correccionPDF";
	}
	
	@RequestMapping("/datos_profesor/cursos/borrarNotas")
	public String datosProfesorCursosBorrarNotas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesor|BorrarNotas");
		return "datos_profesor/cursos/borrarNotas";
	}
	
	@RequestMapping("/datos_profesor/cursos/diferidaPDF")
	public String datosProfesorCursosDiferidaPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDatosProfesor|DiferidaPDF");
		return "datos_profesor/cursos/diferidaPDF";
	}
	
	@RequestMapping("/datos_profesor/cursos/nuevoHorarioMateria")
	public String datosProfesorCursosNuevoHorarioMateria(HttpServletRequest request, Model modelo){
		String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		List<CargaHorario> lisHorarios 				= cargaHorarioDao.getLista(cursoCargaId, " ORDER BY DIA, HORA_INICIO, MINUTO_INICIO");		
//		HashMap<String,CatEdificio> mapaEdificios 	= catEdificioDao.getMapAll("");
		HashMap<String,CatSalon> mapaSalones 		= catSalonDao.getMapAll("");
		
		modelo.addAttribute("lisHorarios",lisHorarios);
//		modelo.addAttribute("mapaEdificios",mapaEdificios);
		modelo.addAttribute("mapaSalones",mapaSalones);
		
		return "datos_profesor/cursos/nuevoHorarioMateria";
	}
	
}