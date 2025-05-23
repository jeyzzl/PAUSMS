package aca.web.aptitudfisica;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContAptitudFisicaAntro {

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
	
	@RequestMapping("/aptitud_fisica/antro/accionAlumno")
	public String aptitudFisicaAntroAccionAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAptitudFisicaAntro|aptitudFisicAntroAccionAlumno:");
		return "aptitud_fisica/antro/accionAlumno";
	}
	
	@RequestMapping("/aptitud_fisica/antro/borrarAlumno")
	public String aptitudFisicaAntroBorrarAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAptitudFisicaAntro|aptitudFisicAntroBorrarAlumno:");
		return "aptitud_fisica/antro/borrarAlumno";
	}
	
	@RequestMapping("/aptitud_fisica/antro/listado")
	public String aptitudFisicaAntroListado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAptitudFisicaAntro|aptitudFisicAntroListado:");
		return "aptitud_fisica/antro/listado";
	}
	
}
