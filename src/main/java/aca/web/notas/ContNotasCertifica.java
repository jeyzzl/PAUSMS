package aca.web.notas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContNotasCertifica {	
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/notas/certificado/notas")
	public String mapaNotasCertificaNotas(HttpServletRequest request, Model modelo){
		
		String planId 	= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String codigoAlumno = "0";
		String nombreAlumno = "-";
		HttpSession sesion = request.getSession();
		if (sesion != null) {		
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
			nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}
		
		List<MapaPlan> lisPlanes = mapaPlanDao.lisPlanesConMaterias(codigoAlumno, "ORDER BY NOMBRE_PLAN");
		if (lisPlanes.size()>=1 && planId.equals("0")) planId = lisPlanes.get(0).getPlanId();
		
		List<AlumnoCurso> lisNotas = alumnoCursoDao.lisPorAlumnoAndPlan( codigoAlumno, planId, "ORDER BY ALUMNO_CURSO.F_EVALUACION, CICLO");
		HashMap<String,String> mapaGradePoint	= alumnoCursoDao.mapaGradePoint(codigoAlumno);
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisNotas", lisNotas);
		modelo.addAttribute("mapaGradePoint", mapaGradePoint);		
		
		return "notas/certificado/notas";
	}	
}
