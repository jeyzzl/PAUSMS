package aca.web.alerta;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.covid.spring.Covid;
import aca.covid.spring.CovidDao;
import aca.covid.spring.CovidPeriodoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAlertaCovid {
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CovidPeriodoDao covidPeriodoDao;
	
	@Autowired
	private CovidDao covidDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	

	@RequestMapping("/alerta/covid/reporte")
	public String alertaCovidReporte(HttpServletRequest request){
		
		return "alerta/covid/reporte";
	}

	@RequestMapping("/alerta/covid/empleados")
	public String alertaCovidEmpleados(HttpServletRequest request, Model modelo){
		
		HashMap<String, Maestros> mapa = maestrosDao.mapaMaestros();
		
		List<Covid> lista = covidDao.getLista("'9'","");
		
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("lista", lista);
		
		return "alerta/covid/empleados";
	}

	@RequestMapping("/alerta/covid/alumnos")
	public String alertaCovidAlumnos(HttpServletRequest request, Model modelo){
		
		String periodoActual 	= String.valueOf(covidPeriodoDao.getActual());
		
		List<Covid> lista = covidDao.getLista("'0','1','2'"," ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
		HashMap<String,AlumAcademico> mapaAcademico     = alumAcademicoDao.mapAcademicoCovid();
		HashMap<String, AlumPersonal> mapaAlumnos		= alumPersonalDao.mapaCovid();		
		HashMap<String,String> mapaPlanesAlumnos		= alumPlanDao.mapPlanActualEnCovid(periodoActual);
		HashMap<String,MapaPlan> mapaPlanes				= mapaPlanDao.mapPlanes("'A','I','V'");
		HashMap<String,CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlanesAlumnos", mapaPlanesAlumnos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAcademico", mapaAcademico);
		
		return "alerta/covid/alumnos";
	}
}
