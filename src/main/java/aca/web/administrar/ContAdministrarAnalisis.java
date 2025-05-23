package aca.web.administrar;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoActividad;
import aca.carga.spring.CargaGrupoActividadDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaGrupoEvaluacion;
import aca.carga.spring.CargaGrupoEvaluacionDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.kardex.spring.KrdxAlumnoActivDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.kardex.spring.KrdxMaximoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.AlumnoEficienciaDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAdministrarAnalisis {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	KrdxMaximoDao krdxMaximoDao; 
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AlumnoEficienciaDao alumnoEficienciaDao;
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	CargaGrupoEvaluacionDao cargaGrupoEvaluacionDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	CargaGrupoActividadDao cargaGrupoActividadDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	KrdxAlumnoActivDao krdxAlumnoActivDao;

	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/administrar/analisis/alumnos")
	public String administrarAnalisisAlumnos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= "0";
		String carreraId		= request.getParameter("CarreraId")==null?"00000":request.getParameter("CarreraId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			cargaId 			= (String) sesion.getAttribute("cargaId");
		}
		String carreraNombre 	= catCarreraDao.getNombreCarrera(carreraId); 
		//long inicio = System.currentTimeMillis();
		List<AlumnoCurso> lisMaterias					= alumnoCursoDao.getListCargayCarrera(cargaId, carreraId, " ORDER BY CODIGO_PERSONAL, CURSO_ID");
		//System.out.println("Paso 1:"+(System.currentTimeMillis() - inicio));
		HashMap<String, String> mapPuntosEvaluados		= alumnoEficienciaDao.mapaPuntosEvaluados(cargaId, carreraId);
		//System.out.println("Paso 2:"+(System.currentTimeMillis() - inicio));
		HashMap<String, String> mapPuntosAlumno			= alumnoEficienciaDao.mapaPuntosAlumno(cargaId, carreraId);
		//System.out.println("Paso 3:"+(System.currentTimeMillis() - inicio));
		HashMap<String, AlumPersonal> mapAlumnos		= alumPersonalDao.mapaAlumnosEnCarga(cargaId);
		//System.out.println("Paso 4:"+(System.currentTimeMillis() - inicio));
		HashMap<String, String> mapMaestros				= maestrosDao.mapMaestroNombre("NOMBRE");
		
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapPuntosEvaluados", mapPuntosEvaluados);
		modelo.addAttribute("mapPuntosAlumno", mapPuntosAlumno);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapMaestros", mapMaestros);
		
		return "administrar/analisis/alumnos";
	}
	
	@RequestMapping("/administrar/analisis/carga_maestros")
	public String administrarAnalisisCargaMaestros(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdministrarAnalisis|administrarAnalisisCargaMaestros");
		return "administrar/analisis/carga_maestros";
	}
	
	@RequestMapping("/administrar/analisis/avanceEvaluacion")
	public String administrarAnalisisAvanceEvaluacion(HttpServletRequest request, Model modelo){
		
		String cursoCargaId 	= request.getParameter("CursoCargaId") == null ? "0" : request.getParameter("CursoCargaId");
		String cursoId			= cargaGrupoCursoDao.cursoIdOrigen(cursoCargaId);
		String materiaNombre 	= mapaCursoDao.getNombreCurso(cursoId);
		String codigoPersonal 	= "0";
		String maestroNombre	= "-";
		int numAlumnos 			= krdxCursoActDao.numAlumnosMateria(cursoCargaId, "'I','1','2','3','4','5','6'");
		String avanceMateria	= cargaGrupoEvaluacionDao.getAvanceEvaluacion(cursoCargaId);		
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
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
		
		return "administrar/analisis/avanceEvaluacion";
	}
	
	@RequestMapping("/administrar/analisis/carga")
	public String administrarAnalisisCarga(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdministrarAnalisis|administrarAnalisisCarga");
		return "administrar/analisis/carga";
	}
	
	@RequestMapping("/administrar/analisis/cargas")
	public String administrarAnalisisCargas(HttpServletRequest request, Model modelo){		
		
		String periodoId 									= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		/* Lista que trae todos los periodos */
		List <CatPeriodo> lisPeriodos		= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size()>=1) periodoId = lisPeriodos.get(0).getPeriodoId();
		/* Lista que trae todas las cargas de un periodo */
		List <Carga> lisCargas				= cargaDao.getListCargaPeriodo(periodoId, "ORDER BY CARGA_ID");
		
		// Mapa para los maestros por carga
		HashMap<String, String> mapMaestros					= cargaGrupoDao.mapMaestrosCarga(periodoId);
		// Mapa para las materias por carga
		HashMap<String, String> mapMaterias					= cargaGrupoDao.mapMateriasCarga(periodoId);	
		// Mapa para las evaluaciones por carga
		HashMap<String, String> mapEsquema					= cargaGrupoDao.mapMateriasEsquemaCompleto(periodoId);
		// Mapa para las actividades por carga
		HashMap<String, String> mapAgendadas				= cargaGrupoDao.mapAgendadasCargas( periodoId);		
		HashMap<String, String> mapaMesesPorCargas			= cargaGrupoActividadDao.mapaMesesPorCargas();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaMaestros", mapMaestros);
		modelo.addAttribute("mapaMaterias", mapMaterias);
		modelo.addAttribute("mapaEsquemas", mapEsquema);
		modelo.addAttribute("mapaAgendadas", mapAgendadas);
		modelo.addAttribute("mapaMesesPorCargas", mapaMesesPorCargas);
		
		return "administrar/analisis/cargas";
	}
	
	@RequestMapping("/administrar/analisis/falta")
	public String administrarAnalisisFalta(HttpServletRequest request, Model modelo){
		
		String cargaId 								= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		List<CargaAcademica> lisMaterias 			= cargaAcademicaDao.listMateriasFaltantes(cargaId, " ORDER BY FACULTAD(CARRERA_ID), CARRERA_ID");
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");		
		HashMap<String,String> mapaEsquemas			= cargaGrupoDao.mapaSumaEsquemaPorMateria(cargaId);
		HashMap<String,String> mapaAlumnos			= cargaGrupoDao.mapaAlumnosPorMateria(cargaId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaEsquemas", mapaEsquemas);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "administrar/analisis/falta";
	}
	
	@RequestMapping("/administrar/analisis/carrera")
	public String administrarAnalisisCarrera(HttpServletRequest request, Model modelo){
		
		String cargaSesion		= "0";		
		String codigoUsuario	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){        	
        	cargaSesion 	= (String) sesion.getAttribute("cargaId");
        	codigoUsuario 	= (String) sesion.getAttribute("codigoPersonal");
        }	
        String cargaId 			= request.getParameter("CargaId")==null?cargaSesion:request.getParameter("CargaId");
        
		List<Carga> lisCargas 			= cargaDao.getListAll("ORDER BY CARGA_ID DESC");	
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListCarga( cargaId, "ORDER BY FACULTAD_ID, CARRERA_ID");
		
		Acceso acceso = new Acceso();
		if(accesoDao.existeReg(codigoUsuario)){
			acceso = accesoDao.mapeaRegId(codigoUsuario);
		}
		
		HashMap<String,CatFacultad> mapaFacultades  = catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaInscritos 		= alumEstadoDao.mapaInscritosPorCarrera(cargaId);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		
		return "administrar/analisis/carrera";
	}
	
	@RequestMapping("/administrar/analisis/detalle")
	public String administrarAnalisisDetalle(HttpServletRequest request, Model modelo){
		
		String cargaSesion 		= "0";	
		String codigoSesion		= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){        	
        	cargaSesion 		= (String) sesion.getAttribute("cargaId");
        	codigoSesion 		= (String) sesion.getAttribute("codigoEmpleado");        	
        }	
        String cargaId 			= request.getParameter("CargaId")==null?cargaSesion:request.getParameter("CargaId");
        String codigoPersonal	= request.getParameter("CodigoPersonal")==null?codigoSesion:request.getParameter("CodigoPersonal");
        
        String maestroNombre 	= maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
        
		List<CargaAcademica> lisMaterias					= cargaAcademicaDao.getMateriasPorCarga(codigoPersonal, "'"+cargaId+"'", "ORDER BY CARGA_ID");
		HashMap<String, String> mapaAlumnos					= cargaGrupoDao.mapaAlumnosPorMateria(cargaId, codigoPersonal);
		
		// Mapa para las evaluaciones por carga
		HashMap<String, String> mapEvaluaciones				= cargaGrupoDao.mapEvaluacionesMaestro(codigoPersonal);
		// Mapa para las actividades por carga
		HashMap<String, String> mapActividades				= cargaGrupoDao.mapActividadesMaestro(codigoPersonal);
		// Mapa para las modalidades
		HashMap<String,CatModalidad> mapModalidades			= catModalidadDao.getMapAll("");
		// Mapa para evalucion de esquema
		HashMap<String, String> mapEsquemaEvaluacion		= cargaGrupoEvaluacionDao.mapSumaEsquema(cargaId);
		// Mapa para actovidad agendada
		HashMap<String, String> mapActividadAgendada		= cargaGrupoActividadDao.mapActividadesAgendadas(cargaId, "S");
		// Mapa para actovidades evaluadas
		HashMap<String, String> mapActividadEvaluada		= krdxAlumnoActivDao.mapaNumActividadesEvaluadas(cargaId);

		HashMap<String, String> mapaAvanceMaestro			= cargaGrupoEvaluacionDao.mapaAvanceMaestro(codigoPersonal, cargaId);
		HashMap<String,String> mapaEvalPendientes	 		= cargaGrupoEvaluacionDao.mapaEvalPendientes(codigoPersonal, cargaId);
		HashMap<String,String> mapaActPendientes	 		= cargaGrupoEvaluacionDao.mapaActPendientes(codigoPersonal, cargaId);		
		
		modelo.addAttribute("maestroNombre", maestroNombre);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapModalidades", mapModalidades);
		modelo.addAttribute("mapEvaluaciones", mapEvaluaciones);
		modelo.addAttribute("mapActividades", mapActividades);		
		modelo.addAttribute("mapEsquemaEvaluacion", mapEsquemaEvaluacion);
		modelo.addAttribute("mapActividadAgendada", mapActividadAgendada);
		modelo.addAttribute("mapActividadEvaluada", mapActividadEvaluada);
		modelo.addAttribute("mapaAvanceMaestro", mapaAvanceMaestro);
		modelo.addAttribute("mapaEvalPendientes", mapaEvalPendientes);
		modelo.addAttribute("mapaActPendientes", mapaActPendientes);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "administrar/analisis/detalle";
	}
	
	@RequestMapping("/administrar/analisis/estrategias_fac")
	public String administrarAnalisisEstrategiasFac(HttpServletRequest request, Model modelo){				
		
		String cargaId 			= "0";
		String codigoPersonal	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");       	
        	cargaId = (String) sesion.getAttribute("cargaId");        	
        }
        String carreraId 		= request.getParameter("carreraId")==null?"0":request.getParameter("carreraId");
        String carreraNombre 	= catCarreraDao.getNombreCarrera(carreraId);
        
		Acceso acceso =  accesoDao.mapeaRegId(codigoPersonal);
		
		List<CargaAcademica> lisMaterias			= cargaAcademicaDao.getListaCargaCarrera(cargaId, carreraId, "ORDER BY CARRERA_ID, NOMBRE,CURSO_ID, MODALIDAD_ID, CICLO, NOMBRE_CURSO");
		List<String> lisMeses						= cargaGrupoEvaluacionDao.lisMesesPorCargayCarrera(cargaId, carreraId, " ORDER BY 1");
		HashMap<String,String> mapaEstrategias		= cargaGrupoEvaluacionDao.mapaEstrategiasPorMateria(cargaId, carreraId);
		HashMap<String, String> mapaActividades 	= cargaGrupoActividadDao.mapaActividadesPorMateria(cargaId, carreraId);
		HashMap<String, String> mapaValores		 	= cargaGrupoEvaluacionDao.mapaSumaPorMateria(cargaId, carreraId);
		
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("acceso", acceso);
		
		modelo.addAttribute("lisMeses", lisMeses);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaEstrategias", mapaEstrategias);
		modelo.addAttribute("mapaActividades", mapaActividades);
		modelo.addAttribute("mapaValores", mapaValores);
		
		return "administrar/analisis/estrategias_fac";
	}
	
	@RequestMapping("/administrar/analisis/estrategias")
	public String administrarAnalisisEstrategias(HttpServletRequest request, Model modelo){	
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoPersonal	= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	codigoPersonal = (String)sesion.getAttribute("codigoPersonal");
        	if (cargaId.equals("0")) {
        		cargaId = (String) sesion.getAttribute("cargaId");
        	}else {
        		sesion.setAttribute("cargaId",cargaId);
        	}
        }
        
		Acceso acceso =  accesoDao.mapeaRegId(codigoPersonal);
		
		List<Carga> lisCargas 							= cargaDao.getListAllActivas(" ORDER BY CARGA_ID");
		List<CatCarrera> lisCarreras			 		= catCarreraDao.lisEnCarga(cargaId, "ORDER BY FACULTAD_ID, CARRERA_ID");		
		List<String> lisMeses 							= cargaGrupoEvaluacionDao.lisMesesPorCarga(cargaId, "ORDER BY 1");
		
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaMatPorFacultades		= cargaGrupoDao.mapaMateriasPorFacultad(cargaId);
		HashMap<String,String> mapaMatPorCarreras		= cargaGrupoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String,String> mapaSumaPorFacultades	= cargaGrupoDao.mapaSumaPorFacultades(cargaId);	
		HashMap<String,String> mapaSumaPorCarreras		= cargaGrupoDao.mapaSumaPorCarreras(cargaId);
		HashMap<String,String> mapaEstPorFacultades		= cargaGrupoEvaluacionDao.mapaEstrategiasPorFacultad(cargaId);
		HashMap<String,String> mapaEstPorCarreras		= cargaGrupoEvaluacionDao.mapaEstrategiasPorCarrera(cargaId);
		HashMap<String,String> mapaEstPorMesyFacultades	= cargaGrupoEvaluacionDao.mapaEstrategiasPorMesyFacultad(cargaId);
		HashMap<String,String> mapaEstPorMesyCarreras	= cargaGrupoEvaluacionDao.mapaEstrategiasPorMesyCarrera(cargaId);		
		
		modelo.addAttribute("cargaId", cargaId);		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisMeses", lisMeses);
		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaMatPorFacultades", mapaMatPorFacultades);
		modelo.addAttribute("mapaMatPorCarreras", mapaMatPorCarreras);
		modelo.addAttribute("mapaEstPorFacultades", mapaEstPorFacultades);
		modelo.addAttribute("mapaEstPorCarreras", mapaEstPorCarreras);
		modelo.addAttribute("mapaEstPorMesyFacultades", mapaEstPorMesyFacultades);
		modelo.addAttribute("mapaEstPorMesyCarreras", mapaEstPorMesyCarreras);
		modelo.addAttribute("mapaSumaPorFacultades", mapaSumaPorFacultades);
		modelo.addAttribute("mapaSumaPorCarreras", mapaSumaPorCarreras);
		
		return "administrar/analisis/estrategias";
	}
	
	@RequestMapping("/administrar/analisis/maestros")
	public String administrarAnalisisMaestros(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if (cargaId.equals("0")) {
        		cargaId = (String) sesion.getAttribute("cargaId");
        	}else {
        		sesion.setAttribute("cargaId",cargaId);
        	}
        }
        
        List<Carga> lisCargas 			= cargaDao.getListAll("ORDER BY CARGA_ID DESC");		
		
		List<aca.Mapa> lisMaestros							= cargaGrupoDao.lisMaestrosEnCarga(cargaId, "ORDER BY 2");		
		
		HashMap<String, String> mapMaterias					= cargaGrupoDao.mapMateriasProfesor(cargaId);		
		// Mapa para las evaluaciones por profesor
		HashMap<String, String> mapEvaluaciones				= cargaGrupoDao.mapEvaluacionProfesor(cargaId);
		HashMap<String, String> mapEvalRegistradas			= cargaGrupoDao.mapEvaluacionesRegistradas(cargaId);
		HashMap<String, String> mapEvalTotales				= cargaGrupoDao.mapEvaluacionesTotales(cargaId);
		
		// Mapa para las actividades por profesor
		HashMap<String, String> mapActividades				= cargaGrupoDao.mapActividadProfesor(cargaId);
		HashMap<String, String> mapActivRegistradas			= cargaGrupoDao.mapActividadesRegistradas(cargaId);		
		HashMap<String, String> mapActivTotales				= cargaGrupoDao.mapActividadesTotales(cargaId);
		HashMap<String, String> mapEsquema					= cargaGrupoDao.mapaMateriasConEsquema(cargaId);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaestros", lisMaestros);		
		modelo.addAttribute("mapMaterias", mapMaterias);
		modelo.addAttribute("mapEsquema", mapEsquema);
		
		modelo.addAttribute("mapEvaluaciones", mapEvaluaciones);		
		modelo.addAttribute("mapEvalRegistradas", mapEvalRegistradas);
		modelo.addAttribute("mapEvalTotales", mapEvalTotales);
		
		modelo.addAttribute("mapActividades", mapActividades);
		modelo.addAttribute("mapActivRegistradas", mapActivRegistradas);
		modelo.addAttribute("mapActivTotales", mapActivTotales);
		
		return "administrar/analisis/maestros";
	}
	
	@RequestMapping("/administrar/analisis/maxCarga")
	public String administrarAnalisisMaxCarga(HttpServletRequest request, Model modelo){

		List<Carga> lisCargas 						= cargaDao.getListCargasActivas("ORDER BY CARGA_ID");
		HashMap<String,String> mapaCargaFail		= krdxMaximoDao.mapaPorCarga(0, 69);
		HashMap<String,String> mapaCargaPanso		= krdxMaximoDao.mapaPorCarga(70, 79);
		HashMap<String,String> mapaCargaLaLibras	= krdxMaximoDao.mapaPorCarga(80, 89);
		HashMap<String,String> mapaCargaMatadito	= krdxMaximoDao.mapaPorCarga(90, 100);

		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("mapaCargaFail", mapaCargaFail);
		modelo.addAttribute("mapaCargaPanso", mapaCargaPanso);
		modelo.addAttribute("mapaCargaLaLibras", mapaCargaLaLibras);
		modelo.addAttribute("mapaCargaMatadito", mapaCargaMatadito);
		
		return "administrar/analisis/maxCarga";
	}
	
	@RequestMapping("/administrar/analisis/maxFacultades")
	public String administrarAnalisisFacultades(HttpServletRequest request, Model modelo){
	
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
        if (sesion!=null){
        	if (cargaId.equals("0")) {
        		cargaId = (String) sesion.getAttribute("cargaId");
        	}else {
        		sesion.setAttribute("cargaId",cargaId);
        	}
        }
        
        List<CatFacultad> listaFacultades   	= catFacultadDao.lisPorCarga(cargaId, "");
        HashMap<String,String> mapaNotaBaja 	= krdxMaximoDao.mapaPorFacultades(0, 69);
        HashMap<String,String> mapaNotaMedia 	= krdxMaximoDao.mapaPorFacultades(70, 79);
        HashMap<String,String> mapaNotaBuena 	= krdxMaximoDao.mapaPorFacultades(80, 89);
        HashMap<String,String> mapaNotaAlta 	= krdxMaximoDao.mapaPorFacultades(90, 100);
        
        modelo.addAttribute("cargaId", cargaId);
        modelo.addAttribute("listaFacultades", listaFacultades);
        modelo.addAttribute("mapaNotaBaja", mapaNotaBaja);
        modelo.addAttribute("mapaNotaMedia", mapaNotaMedia);
        modelo.addAttribute("mapaNotaBuena", mapaNotaBuena);
        modelo.addAttribute("mapaNotaAlta", mapaNotaAlta);
		
		return "administrar/analisis/maxFacultades";
	}
	
	@RequestMapping("/administrar/analisis/maxCarreras")
	public String administrarAnalisisCarreras(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			if (cargaId.equals("0")) {
				cargaId = (String) sesion.getAttribute("cargaId");
			}
		}
		
		CatFacultad facultad = new CatFacultad();
		
		if(catFacultadDao.existeReg(facultadId)) {
			facultad = catFacultadDao.mapeaRegId(facultadId);
		}
		
		List<CatCarrera> listaCarreras   		= catCarreraDao.lisCarrerasEnFacultad(cargaId, facultadId, "");
		HashMap<String,String> mapaNotaBaja 	= krdxMaximoDao.mapaPorCarreras(0, 69);
		HashMap<String,String> mapaNotaMedia 	= krdxMaximoDao.mapaPorCarreras(70, 79);
		HashMap<String,String> mapaNotaBuena 	= krdxMaximoDao.mapaPorCarreras(80, 89);
		HashMap<String,String> mapaNotaAlta 	= krdxMaximoDao.mapaPorCarreras(90, 100);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("listaCarreras", listaCarreras);
		modelo.addAttribute("mapaNotaBaja", mapaNotaBaja);
		modelo.addAttribute("mapaNotaMedia", mapaNotaMedia);
		modelo.addAttribute("mapaNotaBuena", mapaNotaBuena);
		modelo.addAttribute("mapaNotaAlta", mapaNotaAlta);
		
		return "administrar/analisis/maxCarreras";
	}
	
	@RequestMapping("/administrar/analisis/maxAlumnos")
	public String administrarAnalisisMaxAlumnos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			if (cargaId.equals("0")) {
				cargaId = (String) sesion.getAttribute("cargaId");
			}else {
				sesion.setAttribute("cargaId",cargaId);
			}
		}
		
		CatCarrera carrera = new CatCarrera();
		
		if(catCarreraDao.existeReg(carreraId)) {
			carrera = catCarreraDao.mapeaRegId(carreraId);
		}
		
		List<aca.Mapa> listaAlumnos   			= alumEstadoDao.listaPorCargaIdYcarreraId(cargaId, carreraId, "");
		HashMap<String,String> mapaNotaBaja 	= krdxMaximoDao.mapaPorAlumno(0, 69);
		HashMap<String,String> mapaNotaMedia 	= krdxMaximoDao.mapaPorAlumno(70, 79);
		HashMap<String,String> mapaNotaBuena 	= krdxMaximoDao.mapaPorAlumno(80, 89);
		HashMap<String,String> mapaNotaAlta 	= krdxMaximoDao.mapaPorAlumno(90, 100);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("listaAlumnos", listaAlumnos);
		modelo.addAttribute("mapaNotaBaja", mapaNotaBaja);
		modelo.addAttribute("mapaNotaMedia", mapaNotaMedia);
		modelo.addAttribute("mapaNotaBuena", mapaNotaBuena);
		modelo.addAttribute("mapaNotaAlta", mapaNotaAlta);
		
		return "administrar/analisis/maxAlumnos";
	}	
	
	@RequestMapping("/administrar/analisis/menu")
	public String administrarAnalisisMenu(HttpServletRequest request){
				
		return "administrar/analisis/menu";
	}
	
	@RequestMapping("/administrar/analisis/uso")
	public String administrarAnalisisUso(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdministrarAnalisis|administrarAnalisisUso");
		return "administrar/analisis/uso";
	}
	
	@RequestMapping("/administrar/analisis/portal")
	public String portalesPreceptorResumen(HttpServletRequest request){
		
		String codigoAlumno = request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			sesion.setAttribute("codigoAlumno", codigoAlumno);
		}		
		return "redirect:/portales/alumno/resumen";
	}
	
}