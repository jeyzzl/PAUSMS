package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscDeserta {
	
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
	
	@RequestMapping("/bsc/deserta/inscritos")
	public String bscDesertaInscritos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscDeserta|inscritos:");
		return "bsc/deserta/inscritos";
	}	

	@RequestMapping("/bsc/deserta/tarjeta")
	public String bscDesertaTarjeta(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscDeserta|tarjeta:");
		return "bsc/deserta/tarjeta";
	}	

}