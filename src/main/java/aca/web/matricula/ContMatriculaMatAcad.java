package aca.web.matricula;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContMatriculaMatAcad {
	
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
	
	@RequestMapping("/matricula/mat_acad/matricula")
	public String matriculaMat_acadMatricula(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerMatricula|matriculaMat_acadMatricula");
		return "matricula/mat_acad/matricula";
	}
	
	@RequestMapping("/matricula/mat_acad/accionMaterias")
	public String matriculaMat_acadAccionmaterias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerMatricula|matriculaMat_acadAccionmaterias");
		return "matricula/mat_acad/accionMaterias";
	}
	
	@RequestMapping("/matricula/mat_acad/calculoCobro")
	public String matriculaMat_acadCalculocobro(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerMatricula|matriculaMat_acadCalculocobro");
		return "matricula/mat_acad/calculoCobro";
	}
	
	@RequestMapping("/matricula/mat_acad/confirmar")
	public String matriculaMat_acadConfirmar(HttpServletRequest request){		
		//enviarConEnoc(request,"Error-aca.ControllerMatricula|matriculaMat_acadConfirmar");
		return "matricula/mat_acad/confirmar";
	}
	
	@RequestMapping("/matricula/mat_acad/radiografia")
	public String matriculaMat_acadRadiografia(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerMatricula|matriculaMat_acadRadiografia");
		return "matricula/mat_acad/radiografia";
	}
	
}