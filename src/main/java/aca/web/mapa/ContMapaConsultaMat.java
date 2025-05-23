package aca.web.mapa;

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

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCalDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoPre;
import aca.plan.spring.MapaCursoPreDao;
import aca.plan.spring.MapaNuevoCurso;
import aca.plan.spring.MapaNuevoCursoDao;
import aca.plan.spring.MapaNuevoPlan;
import aca.plan.spring.MapaNuevoPlanDao;
import aca.plan.spring.MapaNuevoUnidad;
import aca.plan.spring.MapaNuevoUnidadDao;
import aca.plan.spring.MapaPlan;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMapaConsultaMat {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;	
	
	@Autowired	
	private aca.plan.spring.MapaPlanDao mapaPlanDao;	
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private MapaCursoPreDao mapaCursoPreDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaNuevoPlanDao mapaNuevoPlanDao;
	
	@Autowired	
	private MapaNuevoCursoDao mapaNuevoCursoDao;
	
	@Autowired	
	private CatTipoCursoDao catTipoCursoDao;
	
	@Autowired
	private SunPlusFuncionDao sunPlusFuncionDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;	
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	MapaNuevoUnidadDao mapaNuevoUnidadDao;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/mapa/consulta_mat/facultad")
	public String mapaconsultaMatFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores	= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		return "mapa/consulta_mat/facultad";
	}
	
	@RequestMapping("/mapa/consulta_mat/listado")
	public String mapaconsultaMatListado(HttpServletRequest request, Model modelo){
		String facultad 		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		
		List<CatCarrera> lisCarreras					= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes						= mapaPlanDao.getListPlanFac(facultad,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores		= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaFunciones			= sunPlusFuncionDao.mapaFunciones();
		HashMap<String,String> mapaPracticas			= mapaCursoDao.mapaPlanEnPracticas();
		HashMap<String,String> mapaPracticasEnCarreras	= mapaCursoDao.mapaPracticasEnCarreras();
		HashMap<String,String> mapaCursosPorPlan		= mapaCursoDao.mapaCursosPorPlan();
		
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaFunciones", mapaFunciones);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("mapaPracticasEnCarreras", mapaPracticasEnCarreras);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		
		return "mapa/consulta_mat/listado";
	}
	
	@RequestMapping("/mapa/consulta_mat/materia")
	public String mapaconsultaMatMateria(HttpServletRequest request, Model modelo){
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");				
		String versionPlan		= mapaNuevoPlanDao.getMaxVersionPlan(planId);
		String planOrigen 		= mapaPlanDao.getPlanDeOrigen(planId); 
		
		List<MapaCurso> lisCursos							= mapaCursoDao.getLista(planId," ORDER BY 4,3");
		List<MapaCursoPre> lisPrerrequisitos				= mapaCursoPreDao.getLista(planId," ORDER BY 1,2");
		
		HashMap<String, MapaCurso> mapaCursos				= mapaCursoDao.getMapaCursos(planId, "");
		HashMap<String, MapaNuevoPlan> mapaNuevosPlanes		= mapaNuevoPlanDao.mapaPlan();
		HashMap<String, MapaNuevoCurso> mapaNuevosCursos	= mapaNuevoCursoDao.mapaCursosNuevos(planId, planOrigen);
		HashMap<String, CatTipoCurso> mapaTipos				= catTipoCursoDao.getMapAll("");
		HashMap<String,String> mapaReprobados				= alumnoCursoDao.mapaReprobadosPorMateria(planId);		
		HashMap<String, String> mapaRegistrados				= krdxCursoActDao.mapaAlumnosPorMateria(planId);
		HashMap<String, String> mapaPromedioPorMateria 		= alumnoCursoDao.mapaPromedioPorMateria(planId);
		
		modelo.addAttribute("planOrigen", planOrigen);
		modelo.addAttribute("versionPlan", versionPlan);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisPrerrequisitos", lisPrerrequisitos);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaNuevosPlanes", mapaNuevosPlanes);
		modelo.addAttribute("mapaNuevosCursos", mapaNuevosCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaReprobados", mapaReprobados);
		modelo.addAttribute("mapaRegistrados", mapaRegistrados);
		modelo.addAttribute("mapaPromedioPorMateria", mapaPromedioPorMateria);
		
		return "mapa/consulta_mat/materia";
	}
	
	@RequestMapping("/mapa/consulta_mat/listaAlumnos")
	public String mapaconsultaMatlistaAlumnos(HttpServletRequest request, Model modelo){
		String cursoId			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String cursoNombre 		= mapaCursoDao.getNombreCurso(cursoId);
		
		List<AlumnoCurso> lisAlumnos			= alumnoCursoDao.lisAlumnosEnMateria(cursoId, " ORDER BY CURSO_CARGA_ID, ALUM_NOMBRE(CODIGO_PERSONAL)");
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnCurso(cursoId);
		HashMap<String,String> mapaTipos		= catTipoCalDao.mapTipoCal();
		HashMap<String,String> mapaMaestros		= maestrosDao.mapaMaestrosEnMateria(cursoId, "NOMBRE");
		
		modelo.addAttribute("cursoNombre", cursoNombre);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "mapa/consulta_mat/listaAlumnos";
	}
	
	@RequestMapping("/mapa/consulta_mat/listaReprobados")
	public String mapaconsultaMatListaReprobados(HttpServletRequest request, Model modelo){
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String facultad		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String cursoId 		= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String cursoNombre	= mapaCursoDao.getNombreCurso(cursoId);
		
		List<AlumnoCurso> lisAlumnos			= alumnoCursoDao.listaReprobadosPorMateria(cursoId, " ORDER BY ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");		
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnCurso(cursoId);
		
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("cursoNombre", cursoNombre);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("facultad", facultad);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "mapa/consulta_mat/listaReprobados";
	}

	@RequestMapping("/mapa/consulta_mat/prerrequisito")
	public String mapaconsultaMatPrerrequisito(HttpServletRequest request, Model modelo){
		
		String planId 			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String cursoId 			= request.getParameter("idCurso")==null?"0":request.getParameter("idCurso");
		String ciclo			= request.getParameter("ciclo")==null?"1":request.getParameter("ciclo");
		String cursoNombre		= mapaCursoDao.getNombreCurso(cursoId);
		
		List<String> lisPrerrequisitos		= mapaCursoPreDao.lisPrerrequisitos(cursoId);
		List<MapaCurso> lisMaterias			= mapaCursoDao.lisCursos(planId, ciclo, " ORDER BY CICLO ASC");
		
		modelo.addAttribute("cursoNombre", cursoNombre);
		modelo.addAttribute("lisPrerrequisitos", lisPrerrequisitos);
		modelo.addAttribute("lisMaterias", lisMaterias);
		
		return "mapa/consulta_mat/prerrequisito";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraMateriaPdf")
	public String mapaconsultaMatMuestraMateriaPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraMateriaPdf");
		return "mapa/consulta_mat/muestraMateriaPdf";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraPlanPdf")
	public String mapaconsultaMatMuestraPlanPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraPlanPdf");
		return "mapa/consulta_mat/muestraPlanPdf";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraPlanPdfNuevo")
	public String mapaconsultaMatMuestraPlanPdfNuevo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraPlanPdfNuevo");
		return "mapa/consulta_mat/muestraPlanPdfNuevo";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraMateriaPdfI")
	public String mapaconsultaMatMuestraMateriaPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraMateriaPdfI");
		return "mapa/consulta_mat/muestraMateriaPdfI";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraPlanPdfI")
	public String mapaconsultaMatMuestraPlanPdfI(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraPlanPdfI");
		return "mapa/consulta_mat/muestraPlanPdfI";
	}
	
	@RequestMapping("/mapa/consulta_mat/muestraMateriaPdfNuevo")
	public String mapaconsultaMatMuestraMateriaPdfNuevo(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContrMapaConsultaMat|muestraMateriaPdfNuevo");
		
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String versionId		= request.getParameter("versionId")==null?"0":request.getParameter("versionId");
		String cursoId			= request.getParameter("cursoId")==null?"0":request.getParameter("cursoId");		
		String institucion 		= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	institucion 		= (String)sesion.getAttribute("institucion");
        }
        
        List<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadDao.getListCurso(cursoId, "ORDER BY ORDEN, UNIDAD_ID");
        
		MapaNuevoPlan mapaNuevoPlan 	= mapaNuevoPlanDao.mapeaRegId(planId, versionId);
		MapaNuevoCurso mapaNuevoCurso	= mapaNuevoCursoDao.mapeaRegId(cursoId, planId, versionId);
		
		modelo.addAttribute("listUnidades", listUnidades);
		
		modelo.addAttribute("mapaNuevoPlan", mapaNuevoPlan);
		modelo.addAttribute("mapaNuevoCurso", mapaNuevoCurso);
		
		return "mapa/consulta_mat/muestraMateriaPdfNuevo";
	}
	
	@RequestMapping("/mapa/consulta_mat/planDatos")
	public String mapaConsultaMatPlanDatos(HttpServletRequest request, Model modelo ) {
		
		String facultad 		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre 	= catFacultadDao.getNombreFacultad(facultad);
		
		List<MapaPlan> lista	= new ArrayList<MapaPlan>();
		if (facultad.equals("0")) {
			lista	= mapaPlanDao.listPlanes("'A','V','I'"," ORDER BY PLAN_ID");
		}else {
			lista	= mapaPlanDao.listPlanFac(facultad, "'A','V','I'"," ORDER BY PLAN_ID");
		}
		
		HashMap<String, aca.catalogo.spring.CatCarrera> mapaCarrera		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		
		return "mapa/consulta_mat/planDatos";
	}


	
}