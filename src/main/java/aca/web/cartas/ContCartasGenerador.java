package aca.web.cartas;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContCartasGenerador {
	
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
	
	@RequestMapping("/cartas/generador/accion")
	public String cartasGeneradorAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasGenerador|cartasGeneradorAccion:");
		return "cartas/generador/accion";
	}
	
	@RequestMapping("/cartas/generador/constancias")
	public String cartasGeneradorConstancias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasGenerador|cartasGeneradorConstancias:");
		return "cartas/generador/constancias";
	}
	
	@RequestMapping("/cartas/generador/vistaPrevia")
	public String cartasGeneradorVistaPrevia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCartasGenerador|cartasGeneradorVistaPrevia:");
		return "cartas/generador/vistaPrevia";
	}	
}