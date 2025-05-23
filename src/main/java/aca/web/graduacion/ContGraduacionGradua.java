package aca.web.graduacion;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumGradua;
import aca.alumno.spring.AlumGraduaDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEventoDao;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContGraduacionGradua {	
	
	@Autowired
	private AlumGraduaDao alumGraduaDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumEventoDao alumEventoDao;
	
	@Autowired
	private AlumEgresoDao alumEgresoDao;
	

	@RequestMapping("/graduacion/gradua/candidato")
	public String graduacionGraduacioAlumnos(HttpServletRequest request, Model modelo){
		
		String year				= aca.util.Fecha.getHoy().substring(6,10);
		String fechaInicio 			= request.getParameter("fechaInicio")==null?"01/01/"+year:request.getParameter("fechaInicio");
		String fechaFinal  			= request.getParameter("fechaFinal")==null?"31/12/"+year:request.getParameter("fechaFinal");		
		//String queryFiltro = " WHERE FECHA>TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND FECHA<TO_DATE('"+fechaFinal+"', 'DD/MM/YYYY') ";
		
		List<AlumGradua> lisGraduandos 					= alumGraduaDao.lisPorFechas(fechaInicio, fechaFinal, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)),FECHA");
		HashMap<String,String> mapaFacDelPlan			= mapaPlanDao.mapaFacultadDelPlan();	
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,AlumPersonal> mapaAlumnos		= alumPersonalDao.mapaAlumnosGraduados();
		HashMap<String,String> mapaEventoAlumno			= alumEgresoDao.mapaEventoAlumno();
		HashMap<String,String> mapaEventos				= alumEventoDao.mapNombreEventos();
		
		modelo.addAttribute("lisGraduandos", lisGraduandos);
		modelo.addAttribute("mapaFacDelPlan", mapaFacDelPlan);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaEventoAlumno", mapaEventoAlumno);
		modelo.addAttribute("mapaEventos", mapaEventos);
		
		return "graduacion/gradua/candidato";
	}	
}