package aca.web.it;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContItBrowser {
	
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
	
	@RequestMapping("/it/browser/browser")
	public String itBrowserBrowser(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerIt|itBrowserBrowser");
		return "it/browser/browser";
	}
	
	@RequestMapping("/it/browser/browserAccion")
	public String itBrowserBrowserAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerIt|itBrowserBrowserAccion");
		return "it/browser/browserAccion";
	}
}