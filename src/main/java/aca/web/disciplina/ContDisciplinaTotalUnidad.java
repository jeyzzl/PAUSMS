package aca.web.disciplina;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContDisciplinaTotalUnidad {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ServletContext context;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/disciplina/total_unidad/total_unidad")
	public String disciplinaTotalUnidad(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContDisciplinaTotalUnidad|disciplinaTotalUnidad:");				
		return "disciplina/total_unidad/total_unidad";
	}
	
}