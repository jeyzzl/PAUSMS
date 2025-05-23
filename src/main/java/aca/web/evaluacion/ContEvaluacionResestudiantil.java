package aca.web.evaluacion;


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
public class ContEvaluacionResestudiantil {	
	
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
		
	@RequestMapping("/evaluacion/resestudiantil/evaluacion")
	public String evaluacionResestudiantilEvaluacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResestudiantil|evaluacionResestudiantilEvaluacion:");
		return "evaluacion/resestudiantil/evaluacion";
	}
	
	@RequestMapping("/evaluacion/resestudiantil/item")
	public String evaluacionResestudiantilItem(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResestudiantil|evaluacionResestudiantilItem:");
		return "evaluacion/resestudiantil/item";
	}

	@RequestMapping("/evaluacion/resestudiantil/maestroDetalle")
	public String evaluacionResestudiantilMaestroDetalle(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResestudiantil|evaluacionResestudiantilMaestroDetalle:");
		return "evaluacion/resestudiantil/maestroDetalle";
	}
	
	@RequestMapping("/evaluacion/resestudiantil/maestroItem")
	public String evaluacionResestudiantilMaestroItem(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResestudiantil|evaluacionResestudiantilMaestroItem:");
		return "evaluacion/resestudiantil/maestroItem";
	}
	
	@RequestMapping("/evaluacion/resestudiantil/maestro")
	public String evaluacionResestudiantilMaestro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResestudiantil|evaluacionResestudiantilMaestro:");
		return "evaluacion/resestudiantil/maestro";
	}
	
}
