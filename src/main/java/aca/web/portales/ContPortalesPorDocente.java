package aca.web.portales;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPortalesPorDocente {
	
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
	
	@RequestMapping("/portales/porDocente/bajarArchivo")
	public String portalesPorDocenteBajarArchivo(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerPortales|portalesPadreHorario");
		return "portales/porDocente/bajarArchivo";
	}
	
	@RequestMapping("/portales/porDocente/borrarArchivo")
	public String portalesPorDocenteBorrarArchivo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteBorrarArchivo");
		return "portales/porDocente/borrarArchivo";
	}
	
	@RequestMapping("/portales/porDocente/borrarImagen")
	public String portalesPorDocenteBorrarImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteBorrarImagen");
		return "portales/porDocente/borrarImagen";
	}
	
	@RequestMapping("/portales/porDocente/guardarArchivo")
	public String portalesPorDocenteGuardarArchivo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteGuardarArchivo");
		return "portales/porDocente/guardarArchivo";
	}
	
	@RequestMapping("/portales/porDocente/guardarImagen")
	public String portalesPorDocenteGuardarImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteGuardarImagen");
		return "portales/porDocente/guardarImagen";
	}
	
	@RequestMapping("/portales/porDocente/imagen")
	public String portalesPorDocenteImagen(HttpServletRequest request){
		return "portales/porDocente/imagen";
	}
	
	@RequestMapping("/portales/porDocente/portafolio")
	public String portalesPorDocentePortafolio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocentePortafolio");
		return "portales/porDocente/portafolio";
	}
	
	@RequestMapping("/portales/porDocente/requisitos")
	public String portalesPorDocenteRequisitos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteRequisitos");
		return "portales/porDocente/requisitos";
	}
	
	@RequestMapping("/portales/porDocente/seccion")
	public String portalesPorDocenteSeccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteSeccion");
		return "portales/porDocente/seccion";
	}
	
	@RequestMapping("/portales/porDocente/seccionAccion")
	public String portalesPorDocenteSeccionAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteSeccionAccion");
		return "portales/porDocente/seccionAccion";
	}	
	
	@RequestMapping("/portales/porDocente/traspasoPortafolio")
	public String portalesPorDocenteTraspasoPortafolio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteTraspasoPortafolio");
		return "portales/porDocente/traspasoPortafolio";
	}
	
	@RequestMapping("/portales/porDocente/verFrame")
	public String portalesPorDocenteVerFrame(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteVerFrame");
		return "portales/porDocente/verFrame";
	}
	
	@RequestMapping("/portales/porDocente/verImagen")
	public String portalesPorDocenteVerImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesPorDocente|portalesPorDocenteVerImagen");
		return "portales/porDocente/verImagen";
	}
	
}