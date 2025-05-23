package aca.web.musica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMusicaSalon {
	
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
	
	@RequestMapping("/musica/salon/accion")
	public String musicaSalonAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaSalon|Accion:");
		return "musica/salon/accion";
	}		

	@RequestMapping("/musica/salon/salon")
	public String musicaSalonSalon(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaSalon|Salon:");
		return "musica/salon/salon";
	}		

}