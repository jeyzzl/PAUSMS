package aca.web.residencia;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContResidenciaCandado {
	
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
	
	@RequestMapping("/residencia/candado/buscar")
	public String residenciaCandadoBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContResidenciaCandado|Buscar:");
		return "residencia/candado/buscar";
	}		

	@RequestMapping("/residencia/candado/candado")
	public String residenciaCandadoCandado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContResidenciaCandado|Candado:");
		return "residencia/candado/candado";
	}		

}