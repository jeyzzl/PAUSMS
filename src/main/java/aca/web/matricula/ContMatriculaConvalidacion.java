package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ContMatriculaConvalidacion {	
	
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
	
	@RequestMapping("/matricula/enlinea/proceso")
	public String matriculaConvalidacionProceso(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMatriculaEnLinea|matriculaEnLineaProceso:");
		return "matricula/enlinea/proceso";
	}
	
}