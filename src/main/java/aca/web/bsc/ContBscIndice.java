package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscIndice {
	
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
	
	@RequestMapping("/bsc/indice/desercion")
	public String bscIndiceDesercion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscIndice|Desercion:");
		return "bsc/indice/desercion";
	}	

	@RequestMapping("/bsc/indice/detalles")
	public String bscIndiceDetalles(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscIndice|Detalles:");
		return "bsc/indice/detalles";
	}	
	
}