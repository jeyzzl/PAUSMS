package aca.web.portales;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPortalesPadre {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/padre/horario")
	public String portalesPadreHorario(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerPortales|portalesPadreHorario");
		return "portales/padre/horario";
	}
	
	@RequestMapping("/portales/padre/horario3")
	public String portalesPadreHorario3(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreHorario3");
		return "portales/padre/horario3";
	}
	
	@RequestMapping("/portales/padre/datos")
	public String portalesPadreDatos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreDatos");
		return "portales/padre/datos";
	}

	@RequestMapping("/portales/padre/detallecal")
	public String portalesPadreDetalleCal(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreDetalleCal");
		return "portales/padre/detallecal";
	}

	@RequestMapping("/portales/padre/hijos")
	public String portalesPadreHijos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreHijos");
		return "portales/padre/hijos";
	}

	@RequestMapping("/portales/padre/materias")
	public String portalesPadreMaterias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreMaterias");
		return "portales/padre/materias";
	}

	@RequestMapping("/portales/padre/referencias")
	public String portalesPadreReferencias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPadre|portalesPadreReferencias");
		return "portales/padre/referencias";
	}
	
}