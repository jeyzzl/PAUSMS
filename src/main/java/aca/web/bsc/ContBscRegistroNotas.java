package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscRegistroNotas {
	
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
	
	@RequestMapping("/bsc/registronotas/rcarga")
	public String bscRegistroNotasRcarga(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscRegistroNotas|rcarga:");
		return "bsc/registronotas/rcarga";
	}	

	@RequestMapping("/bsc/registronotas/rcarga_n2")
	public String bscRegistroNotasRcargaN2(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscRegistroNotas|rcargan2:");
		return "bsc/registronotas/rcarga_n2";
	}	
	
	@RequestMapping("/bsc/registronotas/rcarga_n3")
	public String bscRegistroNotasRcargaN3(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscRegistroNotas|rcargan3:");
		return "bsc/registronotas/rcarga_n3";
	}	
}