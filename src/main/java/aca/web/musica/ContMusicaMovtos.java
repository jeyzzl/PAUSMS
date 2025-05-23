package aca.web.musica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMusicaMovtos {
	
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
	
	@RequestMapping("/musica/movtos/buscar")
	public String musicaMovtosBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaMovtos|Buscar:");
		return "musica/movtos/accion";
	}		

	@RequestMapping("/musica/movtos/captura")
	public String musicaMovtosCaptura(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaMovtos|Captura:");
		return "musica/movtos/captura";
	}		

}