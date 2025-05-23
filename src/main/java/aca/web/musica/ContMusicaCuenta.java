package aca.web.musica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMusicaCuenta {
	
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
	
	@RequestMapping("/musica/cuenta/accion")
	public String musicaCuentaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaCuenta|Accion:");
		return "musica/cuenta/accion";
	}		

	@RequestMapping("/musica/cuenta/cuenta")
	public String musicaCuentaCuenta(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaCuenta|Cuenta:");
		return "musica/cuenta/cuenta";
	}		

}