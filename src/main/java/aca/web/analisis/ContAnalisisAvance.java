package aca.web.analisis;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.plan.spring.MapaAvanceDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import adm.fecha.Fecha;

@Controller
public class ContAnalisisAvance {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private MapaAvanceDao mapaAvanceDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumEgresoDao alumEgresoDao;
	
	
	@RequestMapping("/analisis/avance/alumnos")
	public String analisisAvanceAlumnos(HttpServletRequest request, Model modelo){
		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String creditosPlan 	= mapaAvanceDao.getCreditosPlan(planId);
		
		/* Lista de planes de estudio*/
		List<AlumPlan> lisAlumnos 		= alumPlanDao.getListaAlumnosPlan(planId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		/* HashMap de los nombres de los alumnos */
		HashMap<String,String> mapAlumnos 	= alumPersonalDao.mapAlumnosPlan(planId);
		
		/* HashMap de los alumnos graduados */
		HashMap<String,String> mapGraduados	= alumEgresoDao.mapaGraduados();

		
		/* HashMap de los créditos de los alumnos en el plan */
		HashMap<String,String> mapCreditos 	= alumPlanDao.mapAlumPlanCreditos(planId);
		
		/* HashMap de la última fecha de inscripción de los alumnos */
		HashMap<String,String> mapUltimaFecha	= alumPlanDao.mapAlumUltimaInsc( planId);
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("mapGraduados", mapGraduados);
		modelo.addAttribute("creditosPlan", creditosPlan);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapCreditos", mapCreditos);
		modelo.addAttribute("mapUltimaFecha", mapUltimaFecha);
		
		return "analisis/avance/alumnos";
	}	
	
	@RequestMapping("/analisis/avance/planes")
	public String analisisAvancePlanes(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			codigoPersonal = sesion.getAttribute("codigoPersonal") == null?"0":sesion.getAttribute("codigoPersonal").toString();			
		}
		
		Acceso  acceso = accesoDao.mapeaRegId(codigoPersonal);
		
		/* Lista de carreras */
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		
		/* Lista de planes de estudio*/
		List<MapaPlan> lisPlanes 	= mapaPlanDao.getListAll("ORDER BY ENOC.FACULTAD(CARRERA_ID), ENOC.NIVEL_PLAN(PLAN_ID), CARRERA_ID, PLAN_ID");
		
		/* HashMap de los nombres de las facultades */
		HashMap<String,CatFacultad> mapFacultad = catFacultadDao.getMapFacultad("");		
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapFacultad", mapFacultad);		
		
		return "analisis/avance/planes";
	}	
	
	@RequestMapping("/analisis/avance/alumnosPorYear")
	public String analisisAvanceAlumnosPorYear(HttpServletRequest request, Model modelo){
		
		String year				= request.getParameter("Year")==null?"0":request.getParameter("Year");
		String planId			= request.getParameter("PlanId")==null?"LAO2000*":request.getParameter("PlanId");
		String creditosPlan 	= mapaAvanceDao.getCreditosPlan(planId);
		String yearActual 		= aca.util.Fecha.getHoy().substring(6, 10);
		
		if(year.equals("0")) {
			year = yearActual;
		}
		
		/* Lista de planes de estudio*/
		List<AlumPlan> lisAlumnos 		= alumPlanDao.getListaAlumnosYear(year, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		/* HashMap de los nombres de los alumnos */
		HashMap<String,String> mapAlumnos = alumPlanDao.mapAlumnosPlanYear(year);
		
		/* HashMap de los créditos de los alumnos en el plan */
		HashMap<String,String> mapCreditos = alumPlanDao.mapAlumPlanCreditosYear(year);
		
		/* HashMap de la última fecha de inscripción de los alumnos */
		HashMap<String,String> mapUltimaFecha = alumPlanDao.mapAlumUltimaInscYear(year);
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("creditosPlan", creditosPlan);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapCreditos", mapCreditos);
		modelo.addAttribute("mapUltimaFecha", mapUltimaFecha);
		modelo.addAttribute("yearActual", yearActual);
		modelo.addAttribute("year", year);
		
		return "analisis/avance/alumnosPorYear";
	}	
	
}
