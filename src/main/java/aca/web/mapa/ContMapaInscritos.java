package aca.web.mapa;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCredito;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoPre;
import aca.plan.spring.MapaCursoPreDao;
import aca.plan.spring.MapaNuevoPlanDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.PlanCicloDao;

@Controller
public class ContMapaInscritos {
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;	
	
	@Autowired	
	private PlanCicloDao planCicloDao;
	
	@Autowired	
	private MapaCreditoDao mapaCreditoDao;
	
	@Autowired	
	private MapaNuevoPlanDao mapaNuevoPlanDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private MapaCursoPreDao mapaCursoPreDao;
	
	@Autowired	
	private CatTipoCursoDao catTipoCursoDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired	
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	
	@RequestMapping("/mapa/inscritos/facultad")
	public String mapaCicloFacultad(HttpServletRequest request, Model modelo){	
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String periodoSesion	= "0";
		String cargaSesion 		= "0";
		boolean existeCarga		= false;
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			periodoSesion 	= sesion.getAttribute("periodo").toString();					
			cargaSesion 	= sesion.getAttribute("cargaId").toString();
		}
		List<CatPeriodo> lisPeriodos				= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && !periodoSesion.equals("0")) {
			periodoId = periodoSesion;
		}else if(periodoId.equals("0") && lisPeriodos.size()>1) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		if (cargaId.equals("0") && !cargaSesion.equals("0")) cargaId = cargaSesion;
		
		List<Carga> lisCargas						= cargaDao.getListCargaPeriodo(periodoId, " ORDER BY CARGA_ID");
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size()>1) cargaId = lisCargas.get(0).getCargaId();		
		
		if (sesion!=null){
			if (!periodoId.equals("0")) {
				sesion.setAttribute("periodo", periodoId);
			}
			if (!cargaId.equals("0")){
				sesion.setAttribute("cargaId",cargaId);
			}			
		}	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListCarga(cargaId, "ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();		
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "mapa/inscritos/facultad";
	}
	
	@RequestMapping("/mapa/inscritos/listado")
	public String mapaCicloListado(HttpServletRequest request, Model modelo){
		
		String cargaId 					= "0";
		String facultad 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			cargaId = sesion.getAttribute("cargaId").toString();
			
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}			
		}
		String nombreCarga							= cargaDao.getNombreCarga(cargaId);
		
		List<CatCarrera> lisCarreras				= catCarreraDao.lisFacultadyCarga(facultad,cargaId," ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes					= mapaPlanDao.lisPlanesEnCarga(facultad, cargaId,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores	= maestrosDao.mapaCoordinadores();
		//HashMap<String,String> mapaCreditos			= mapaCreditoDao.mapaCreditos();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("cargaId", cargaId);
		
		return "mapa/inscritos/listado";
	}
	
	@RequestMapping("/mapa/inscritos/materia")
	public String mapaCicloMateria(HttpServletRequest request, Model modelo){
		
		String cargaId 					= "0";	
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){			
			cargaId = sesion.getAttribute("cargaId").toString();
		}		
		
		String planId 					= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String versionPlan				= mapaNuevoPlanDao.getMaxVersionPlan(planId);
		String nombreCarga				= cargaDao.getNombreCarga(cargaId);
		
		List<MapaCurso> lisCursos				= mapaCursoDao.lisPorPlanyCarga(planId,cargaId, " ORDER BY 4,3");	
		HashMap<String,CatTipoCurso> mapaTipos	= catTipoCursoDao.getMapAll("");
		
		modelo.addAttribute("versionPlan", versionPlan);
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisCursos", lisCursos);		
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "mapa/inscritos/materia";
	}
	
	@RequestMapping("/mapa/inscritos/lista")
	public String mapaCicloLista(HttpServletRequest request, Model modelo){
		
		String idCurso 			= (String)request.getParameter("idCurso");
		String planId 			= (String)request.getParameter("planId");
		String facultad 		= (String)request.getParameter("facultad");
		String ciclo  			= (String)request.getParameter("ciclo");
		String cursoClave  		= (String)request.getParameter("cursoClave");
		
		String facultadNombre		= catFacultadDao.getNombreFacultad(facultad);
		String materiaNombre		= mapaCursoDao.getNombreCurso(idCurso);
		List<MapaCurso> lisCursos 	= mapaCursoDao.getListMaterias(cursoClave);
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("lisCursos", lisCursos);		
		
		return "mapa/inscritos/lista";
	}
	
	@RequestMapping("/mapa/inscritos/alumnos")
	public String mapaCicloAlumnos(HttpServletRequest request, Model modelo){
		
		String cargaId 		= "0";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){		
			cargaId 		= sesion.getAttribute("cargaId").toString();
		}
		
		String idCurso 			= request.getParameter("idCurso");	
		String planId 			= request.getParameter("planId");
		String facultad 		= request.getParameter("facultad");
		String ciclo  			= request.getParameter("ciclo");
		String cursoClave  		= request.getParameter("cursoClave");
		String materiaNombre	= mapaCursoDao.getNombreCurso(idCurso);
		
		HashMap<String,CatFacultad> mapaFacultades	 	= catFacultadDao.getMapFacultad( "");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String, AlumAcademico> mapaResidencia	= alumAcademicoDao.getMapAcademico();
		HashMap<String,String> mapaInscritosPorMateria 	= krdxCursoActDao.mapInscritosPorMateria(cursoClave);
		List<Estadistica> lisInscritos 					= estadisticaDao.lisPorCargayMateria(cargaId, cursoClave, "ORDER BY FACULTAD_ID,NOMBRE_CARRERA(CARRERA_ID), PLAN_ID, NOMBRE");		
		
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);		
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaResidencia", mapaResidencia);
		modelo.addAttribute("mapaInscritosPorMateria", mapaInscritosPorMateria);
		
		return "mapa/inscritos/alumnos";
	}
}