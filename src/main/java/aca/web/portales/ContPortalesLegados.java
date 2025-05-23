package aca.web.portales;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPortalesLegados {
	
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
	
	@RequestMapping("/portales/legados/pagina/amigos")
	public String portalesLegadosPaginaAmigo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesLegados|portalesLegadosPaginaAmigos");
		return "portales/legados/pagina/amigos";
	}
	
	@RequestMapping("/portales/legados/pagina/informacion")
	public String portalesLegadosPaginaInformacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesLegados|portalesLegadosPaginaInformacion");
		return "portales/legados/pagina/informacion";
	}
	
	@RequestMapping("/portales/legados/pagina/principal")
	public String portalesLegadosPaginaPrincipal(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContPortalesLegados|portalesLegadosPaginaPrincipal");
		return "portales/legados/pagina/principal";
	}
	
}