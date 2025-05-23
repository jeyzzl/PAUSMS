package aca.web.disciplina;

import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.disciplina.spring.CondJuez;
import aca.disciplina.spring.CondJuezDao;

@Controller
public class ContDisciplinaElogiosRep {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
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

	@RequestMapping("/disciplina/elogios_rep/elogios_rep")
	public String disciplinaElogiosRepElogiosRep(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContElogiosRep|disciplinaElogiosRepElogiosRep:");				
		return "disciplina/elogios_rep/elogios_rep";
	}
	
}