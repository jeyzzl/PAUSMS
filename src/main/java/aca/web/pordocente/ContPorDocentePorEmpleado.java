package aca.web.pordocente;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPorDocentePorEmpleado {
	
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
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/porDocente/porempleado/datos")
	public String porDocentePorEmpleadoDatos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|Datos:");
		return "porDocente/porempleado/datos";
	}		

	@RequestMapping("/porDocente/porempleado/imagen")
	public String porDocentePorEmpleadoImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|Imagen:");
		return "porDocente/porempleado/imagen";
	}		
	
	@RequestMapping("/porDocente/porempleado/listaEmp")
	public String porDocentePorEmpleadoListaEmp(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|ListaEmp:");
		return "porDocente/porempleado/listaEmp";
	}		
	
	@RequestMapping("/porDocente/porempleado/portafolio")
	public String porDocentePorEmpleadoPortafolio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|Portafolio:");
		return "porDocente/porempleado/portafolio";
	}		

	@RequestMapping("/porDocente/porempleado/requisitos")
	public String porDocentePorEmpleadoRequisitos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|Requisitos:");
		return "porDocente/porempleado/requisitos";
	}		
	
	@RequestMapping("/porDocente/porempleado/verFrame")
	public String porDocentePorEmpleadoVerFrame(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|VerFrame:");
		return "porDocente/porempleado/verFrame";
	}		

	@RequestMapping("/porDocente/porempleado/verImg")
	public String porDocentePorEmpleadoVerImg(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|VerImg:");
		return "porDocente/porempleado/verImg";
	}		
	
	@RequestMapping("/porDocente/porempleado/verTxt")
	public String porDocentePorEmpleadoVerTxt(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePorEmpleado|VerTxt:");
		return "porDocente/porempleado/verTxt";
	}		

}