package aca.web.informes;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacion;
import aca.alumno.spring.AlumUbicacionDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContInformesVacunados {
	
	@Autowired
	AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	
	@RequestMapping("/informes/vacunados/lista")
	public String informes911Estadistica(HttpServletRequest request, Model modelo){
		
		List <AlumUbicacion> lisVacunados 			= alumUbicacionDao.lisVacunados(" ORDER BY CODIGO_PERSONAL");
		HashMap<String,AlumPersonal> mapaAlumnos 	= alumPersonalDao.getMapInscritos();
		HashMap<String,String> mapaPlanAlumno		= alumPlanDao.mapaPlanesActivos();
		HashMap<String,MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		
		modelo.addAttribute("lisVacunados", lisVacunados);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaPlanAlumno", mapaPlanAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "informes/vacunados/lista";
	}

}