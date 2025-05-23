package aca.web.admision;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;

@Controller
public class ContAdmisionFotos {	
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;	
	
	@RequestMapping("/admision/fotos/alumnos")
	public String admisionFotosAlumnos(HttpServletRequest request, Model modelo){
		
		String planId 			= 	request.getParameter("PlanId")==null?"ISC1992*":request.getParameter("PlanId");
		
		List<AlumPersonal> lisAlumnos			= alumPersonalDao.lisEnPlan(planId, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);	
		
		return "admision/fotos/alumnos";
	}
	
}