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
public class ContAdministrarDiferida {
	
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
	
	@RequestMapping("/administrar/diferida/notas")
	public String administrarDiferidaNotas(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContAdministrarDiferida|administrarDiferidaNotas");
		return "administrar/diferida/notas";
	}
	
	@RequestMapping("/administrar/diferida/notasAccion")
	public String administrarDiferidaNotasAccion(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContAdministrarDiferida|administrarDiferidaNotasAccion");
		return "administrar/diferida/notasAccion";
	}
	
	
}