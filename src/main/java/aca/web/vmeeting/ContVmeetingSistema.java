package aca.web.vmeeting;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContVmeetingSistema {
	
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
	
	@RequestMapping("/vmeeting/sistema/listaAccion")
	public String vmeetingSistemaListaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|vmeetingSistemaListaAccion");
		return "vmeeting/sistema/listaAccion";
	}
	
	@RequestMapping("/vmeeting/listaAccion")
	public String vmeetingListaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|vmeetingListaAccion");
		return "vmeeting/listaAccion";
	}
	
}