package aca.web.administrar;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContAdministrarTitulo {
	
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
	
	@RequestMapping("/administrar/titulo/evaluar")
	public String administrarTituloEvaluar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdministrarTitulo|administrarTituloEvaluar");
		return "administrar/titulo/evaluar";
	}
	
	@RequestMapping("/administrar/titulo/forma")
	public String administrarTituloForma(HttpServletRequest request){
		return "administrar/titulo/forma";
	}
}