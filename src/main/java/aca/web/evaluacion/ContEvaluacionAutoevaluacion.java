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
public class ContEvaluacionAutoevaluacion {	
	
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
		
	@RequestMapping("/evaluacion/autoevaluacion/cuestionarios")
	public String evaluacionAutoevaluacionCuestionarios(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionAutoevaluacion|evaluacionAutoevaluacionCuestionarios:");
		return "evaluacion/autoevaluacion/cuestionarios";
	}
	
	@RequestMapping("/evaluacion/autoevaluacion/preguntas")
	public String evaluacionAutoevaluacionPreguntas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionAutoevaluacion|evaluacionAutoevaluacionPreguntas:");
		return "evaluacion/autoevaluacion/preguntas";
	}
	
}
