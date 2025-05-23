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

import aca.catalogo.spring.CatAreaDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoArea;
import aca.edo.spring.EdoAreaDao;
import aca.edo.spring.EdoDao;
import aca.edo.spring.EdoPeriodo;
import aca.edo.spring.EdoPeriodoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContEvaluacionResultados {	
	
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
	
	@Autowired	
	CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
		
	@RequestMapping("/evaluacion/resultados/facultad")
	public String evaluacionResultadosFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad>lisFacultades				= catFacultadDao.getListAll(" ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades",lisFacultades);				
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "evaluacion/resultados/facultad";
	}
	
	@RequestMapping("/evaluacion/resultados/maestro")
	public String evaluacionResultadosMaestro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContEvaluacionResultados|evaluacionResultadosMaestro:");
		return "evaluacion/resultados/maestro";
	}
}
