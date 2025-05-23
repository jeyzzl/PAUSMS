package aca.web.residencia;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContResidenciaColonia {
	
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
	
	@RequestMapping("/residencia/colonia/accion")
	public String residenciaColoniaAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContResidenciaColonia|Accion:");
		return "residencia/colonia/accion";
	}		

	@RequestMapping("/residencia/colonia/colonia")
	public String residenciaColoniaColonia(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContResidenciaColonia|Colonia:");
		return "residencia/colonia/colonia";
	}		

}