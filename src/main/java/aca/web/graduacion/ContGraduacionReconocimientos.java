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

@Controller
public class ContGraduacionReconocimientos {

	@Autowired
	private AlumGraduaDao alumGraduaDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@RequestMapping("/graduacion/reconocimientos/listado")
	public String graduacionGraduacioAlumnos(HttpServletRequest request, Model modelo){
		
		String year				= aca.util.Fecha.getHoy().substring(6,10);
		String fechaInicio 			= request.getParameter("fechaInicio")==null?"01/01/"+year:request.getParameter("fechaInicio");
		String fechaFinal  			= request.getParameter("fechaFinal")==null?"31/12/"+year:request.getParameter("fechaFinal");		
		
		List<AlumGradua> lisGraduandos 				= alumGraduaDao.lisPorFechas(fechaInicio, fechaFinal, " ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)),FECHA");
		HashMap<String, AlumPersonal> mapaEgreso	= alumPersonalDao.mapaEgreso();
		
		modelo.addAttribute("lisGraduandos", lisGraduandos);
		modelo.addAttribute("mapaEgreso", mapaEgreso);
		
		return "graduacion/reconocimientos/listado";
	}
	
}
