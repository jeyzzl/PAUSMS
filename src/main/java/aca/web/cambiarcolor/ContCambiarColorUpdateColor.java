package aca.web.cambiarcolor;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContCambiarColorUpdateColor {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/cambiaColor/updateColor")
	public String cambiaColorUpdateColor(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCambiaColor|cambiaColorUpdateColor");
		return "cambiaColor/updateColor";
	}
	
	@RequestMapping("/cambiaColor/cambiaColor")
	public String cambiaColorCambiaColor(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCambiaColor|cambiaColorCambiaColor");
		return "cambiaColor/cambiaColor";
	}
	
	@RequestMapping("/cambiaColor/cambiaColorMenu")
	public String cambiaColorCambiaColorMenu(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCambiaColor|cambiaColorCambiaColorMenu");
		return "cambiaColor/cambiaColorMenu";
	}
	
	@RequestMapping("/cambiaColor/cambiaColorReloj")
	public String cambiaColorCambiaColorReloj(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCambiaColor|cambiaColorCambiaColorReloj");
		return "cambiaColor/cambiaColorReloj";
	}	
}