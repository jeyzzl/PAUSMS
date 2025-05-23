package aca.web.musica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMusicaInscripcion {
	
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
	
	@RequestMapping("/musica/inscripcion/buscar")
	public String musicaInscripcionBuscar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaInscripcion|Buscar:");
		return "musica/inscripcion/buscar";
	}		

	@RequestMapping("/musica/inscripcion/calculo")
	public String musicaInscripcionCalculo(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaInscripcion|Calculo:");
		return "musica/inscripcion/calculo";
	}	
	
	@RequestMapping("/musica/inscripcion/formato")
	public String musicaInscripcionFormato(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaInscripcion|Formato:");
		return "musica/inscripcion/formato";
	}		

}