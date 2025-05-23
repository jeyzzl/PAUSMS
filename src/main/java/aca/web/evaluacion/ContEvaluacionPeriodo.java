package aca.web.evaluacion;


import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoArea;
import aca.edo.spring.EdoAreaDao;
import aca.edo.spring.EdoDao;
import aca.edo.spring.EdoPeriodo;
import aca.edo.spring.EdoPeriodoDao;

@Controller
public class ContEvaluacionPeriodo {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	EdoDao edoDao;	
	
	@Autowired	
	EdoAlumnoPregDao edoAlumnoPregDao;	
	
	@Autowired	
	EdoAreaDao edoAreaDao;	
	
	@Autowired	
	EdoPeriodoDao edoPeriodoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
		
	@RequestMapping("/evaluacion/periodo/accion")
	public String evaluacionPeriodoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionPeriodo|evaluacionPeriodoAccion:");
		return "evaluacion/periodo/accion";
	}

	@RequestMapping("/evaluacion/periodo/periodo")
	public String evaluacionPeriodoPeriodo(HttpServletRequest request, Model modelo){
		
		List<EdoPeriodo> lisPeriodos	 		= edoPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		HashMap<String,String> mapaEvaluaciones = edoDao.mapaEvaluacionesPorPeriodo();
		
		modelo.addAttribute("lisPeriodos", lisPeriodos);		
		modelo.addAttribute("mapaEvaluaciones", mapaEvaluaciones);
		
		return "evaluacion/periodo/periodo";
	}
}
