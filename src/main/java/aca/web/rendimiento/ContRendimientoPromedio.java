package aca.web.rendimiento;

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

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContRendimientoPromedio {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;

	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@RequestMapping("/rendimiento/promedio/facultad")
	public String rendimientoPromedioFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		HashMap<String,String> mapaPromedios		= alumPlanDao.mapaPromediosFacultades();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		modelo.addAttribute("mapaPromedios", mapaPromedios);
		
		return "rendimiento/promedio/facultad";
	}
	
	@RequestMapping("/rendimiento/promedio/listado")
	public String rendimientoPromedioListado(HttpServletRequest request, Model modelo){
		
		String facultad 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}
		}
		
		List<CatCarrera> lisCarreras				= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes					= mapaPlanDao.listPlanFac(facultad,"'A','I','V'","ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores	= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaAlumnosEnPlan	= alumPlanDao.mapaAlumnosEnPlan(); 
		HashMap<String,String> mapaPromediosCarr	= alumPlanDao.mapaPromediosCarreras();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaAlumnosEnPlan", mapaAlumnosEnPlan);
		modelo.addAttribute("mapaPromediosCarr", mapaPromediosCarr);
		
		return "rendimiento/promedio/listado";
	}
	
	@RequestMapping("/rendimiento/promedio/alumnos")
	public String rendimientoPromedioMateria(HttpServletRequest request, Model modelo){		
		
		String planId 				= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String grado 				= request.getParameter("Grado")==null?"0":request.getParameter("Grado");

		String gradoQuery = "";
		if(!grado.equals("0")){
			gradoQuery = " AND GRADO = '"+grado+"'";
		}
		
		List<AlumPlan> lisAlumnosPromedios				= alumPlanDao.lisAlumnosPromedios(planId, gradoQuery+" ORDER BY ENOC.ALUM_PLAN.PRIMER_MATRICULA");
		HashMap<String,String> mapaAlumnoPlanPromedio	= alumPersonalDao.mapaAlumnosPlanPromedio(planId);	
		HashMap<String,String> mapaMateriasPorAlumno	= alumnoCursoDao.mapaMateriasPorAlumno(planId, "'1'");
		HashMap<String, String> mapaInscritos 			= inscritosDao.getMapaInscritos();
		HashMap<String,String> mapaSumCreditosPorGPA 	= alumnoCursoDao.mapaSumCreditosPorGPA(planId, "'1'");
		HashMap<String,String> mapaSumCreditos 			= alumnoCursoDao.mapaSumCreditosPorPlan(planId);

		modelo.addAttribute("mapaAlumnoPlanPromedio", mapaAlumnoPlanPromedio);
		modelo.addAttribute("lisAlumnosPromedios", lisAlumnosPromedios);
		modelo.addAttribute("mapaMateriasPorAlumno", mapaMateriasPorAlumno);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapaSumCreditosPorGPA", mapaSumCreditosPorGPA);
		modelo.addAttribute("mapaSumCreditos", mapaSumCreditos);
		modelo.addAttribute("grado", grado);
		
		return "rendimiento/promedio/alumnos";
	}
	
}