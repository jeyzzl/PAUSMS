package aca.web.musica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMusicaCursos {
	
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
	
	@RequestMapping("/musica/cursos/accion")
	public String musicaCursosAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaCursos|Accion:");
		return "musica/cursos/accion";
	}		

	@RequestMapping("/musica/cursos/buscar")
	public String musicaCursosBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaCursos|Buscar:");
		return "musica/cursos/buscar";
	}		

	@RequestMapping("/musica/cursos/cursos")
	public String musicaCursosCursos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaCursos|Cursos:");
		return "musica/cursos/cursos";
	}		

}