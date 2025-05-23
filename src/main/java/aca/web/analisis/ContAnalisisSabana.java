package aca.web.analisis;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
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
import aca.financiero.spring.FesCcobroDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContAnalisisSabana {
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private FesCcobroDao fesCcobroDao;	
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	
	@RequestMapping("/analisis/sabana/carreras")
	public String analisisSabanaCarreras(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ContAnalisisSabana|analisisSabanaCarreras:");
		String codigoPersonal	= "0";		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String periodoSesion 	= "0";
		String cargaSesion 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal		= (String)sesion.getAttribute("codigoPersonal");			
			periodoSesion 		= (String)sesion.getAttribute("periodo");
        	cargaSesion 		= (String)sesion.getAttribute("cargaId");
		}
		
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			if(periodoSesion != null) {
				periodoId = periodoSesion;
			}else {
				periodoId = lisPeriodos.get(0).getPeriodoId();
			}
		}else if(!periodoId.equals("0")){
			sesion.setAttribute("periodo", periodoId);
		}		
		
		List<Carga> lisCargas 	= cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY PERIODO,CARGA_ID");
		if (cargaId.equals("0")) cargaId = cargaSesion;
		boolean existeCarga 	= false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size() > 0) {
			cargaId = lisCargas.get(0).getCargaId();
			sesion.setAttribute("cargaId", cargaId);
		}else if(existeCarga) {
			sesion.setAttribute("cargaId", cargaId);
		}
		
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		
		List<MapaPlan> lisPlanes 						= mapaPlanDao.getListAll(" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, PLAN_ID");
		HashMap<String,CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("cargaId", cargaId);		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "analisis/sabana/carreras";
	}	

	@RequestMapping("/analisis/sabana/listado")
	public String analisisSabanaListado(HttpServletRequest request, Model modelo){
		
		String cargaId		= "0";
		String planId 		= request.getParameter("planid");
		String todos 		= request.getParameter("todos")==null?"N":request.getParameter("todos");
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			cargaId 	= (String)sesion.getAttribute("cargaId");
		}
		
		List<String> listAlumnos 	= null;
		
		if(!todos.equals("S")){
			listAlumnos	= alumnoCursoDao.getListaAlumPlan(cargaId, planId, " ORDER BY 2");
		}else{		
			listAlumnos	= alumnoCursoDao.getListaAlumPlan(planId, " ORDER BY 2");
		}	
		
		HashMap<String, HashMap<String, String>> mapaAlumnoCurso 	= new HashMap<String, HashMap<String, String>>(); 
		HashMap<String, String> mapaCalificaciones 	= new HashMap<String, String>(); 

		List<MapaCurso> lisMapaCurso = mapaCursoDao.getMateriasPlanDeEstudio(planId,"1,2,5,7,8"," ORDER BY CICLO, CURSO_ID");
		
		for(String matricula : listAlumnos) {
			mapaCalificaciones = alumnoCursoDao.mapaNotaMateriasAlumno(matricula, planId);
			mapaAlumnoCurso.put(matricula, mapaCalificaciones);
		}
		
		HashMap<String, CatModalidad> mapModalidad	= catModalidadDao.getMapAll("");
		HashMap<String, AlumPlan> mapAlumPlan 		= alumPlanDao.mapAlumEnPlan(planId, "'0','1'");
		HashMap<String, String> mapaNombres 		= alumPersonalDao.mapaNombreAlumnoPlanId(planId);
		HashMap<String, String> mapaModaTodos		= alumAcademicoDao.mapaModalidades(planId);
		HashMap<String, String> mapaModaCarga		= fesCcobroDao.mapaModalidades(cargaId,planId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("listAlumnos", listAlumnos);
		modelo.addAttribute("mapModalidad", mapModalidad);
		modelo.addAttribute("mapAlumPlan", mapAlumPlan);
		modelo.addAttribute("lisMapaCurso", lisMapaCurso);
		modelo.addAttribute("mapaAlumnoCurso", mapaAlumnoCurso);
		modelo.addAttribute("mapaNombres", mapaNombres);
		modelo.addAttribute("mapaModaTodos", mapaModaTodos);
		modelo.addAttribute("mapaModaCarga", mapaModaCarga);
		
		return "analisis/sabana/listado";
	}	

}
