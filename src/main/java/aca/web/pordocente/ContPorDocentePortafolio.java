package aca.web.pordocente;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPorDocentePortafolio {
	
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
	
	@RequestMapping("/porDocente/portafolio/accionPor")
	public String porDocentePortafolioAccionPor(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|AccionPor:");
		return "porDocente/portafolio/accionPor";
	}		

	@RequestMapping("/porDocente/portafolio/editarSeccion")
	public String porDocentePortafolioEditarSeccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|EditarSeccion:");
		return "porDocente/portafolio/editarSeccion";
	}		
	
	@RequestMapping("/porDocente/portafolio/grabarSeccion")
	public String porDocentePortafolioGrabarSeccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|GrabarSeccion:");
		return "porDocente/portafolio/grabarSeccion";
	}		
	
	@RequestMapping("/porDocente/portafolio/porSeccion")
	public String porDocentePortafolioPorSeccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|PorSeccion:");
		return "porDocente/portafolio/porSeccion";
	}		
	
	@RequestMapping("/porDocente/portafolio/porSeccionOld")
	public String porDocentePortafolioPorSeccionOld(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|PorSeccionOld:");
		return "porDocente/portafolio/porSeccionOld";
	}		

	@RequestMapping("/porDocente/portafolio/portafolio")
	public String porDocentePortafolioPortafolio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|Portafolio:");
		return "porDocente/portafolio/portafolio";
	}		

	@RequestMapping("/porDocente/portafolio/traspasoPortafolio")
	public String porDocentePortafolioTraspasoPortafolio(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPorDocentePortafolio|TraspasoPortafolio:");
		return "porDocente/portafolio/traspasoPortafolio";
	}		

}