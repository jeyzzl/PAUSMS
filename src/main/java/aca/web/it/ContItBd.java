package aca.web.it;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContItBd {
	
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
	
	@RequestMapping("/it/bd/er")
	public String itBdEr(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerIt|itBdEr");
		return "it/bd/er";
	}
	
	@RequestMapping("/it/bd/erAccion")
	public String itBdErAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerIt|itBdErAccion");
		return "it/bd/erAccion";
	}
		
}
