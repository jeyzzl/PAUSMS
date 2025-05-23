package aca.web.bsc;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContBscAprovechamiento {
	
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
	
	@RequestMapping("/bsc/aprovechamiento/alumnosPorMaestro")
	public String bscAprovechamientoAlumnosPorMaestro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|alumnosPorMaestro:");
		return "bsc/aprovechamiento/alumnosPorMaestro";
	}	

	@RequestMapping("/bsc/aprovechamiento/promedioPorMaestros")
	public String bscAprovechamientoPromedioPorMaestros(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|promedioPorMaestros:");
		return "bsc/aprovechamiento/promedioPorMaestros";
	}	

	@RequestMapping("/bsc/aprovechamiento/promediosDetalle")
	public String bscAprovechamientoPromediosDetalle(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|promediosDetalle:");
		return "bsc/aprovechamiento/promediosDetalle";
	}	

	@RequestMapping("/bsc/aprovechamiento/promedioPorAlumnos")
	public String bscAprovechamientoPromedioPorAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|promedioPorAlumnos:");
		return "bsc/aprovechamiento/promedioPorAlumnos";
	}	
	
	@RequestMapping("/bsc/aprovechamiento/materiasPorAlumno")
	public String bscAprovechamientoMateriasPorAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|materiasPorAlumno:");
		return "bsc/aprovechamiento/materiasPorAlumno";
	}	
	
	@RequestMapping("/bsc/aprovechamiento/promedios")
	public String bscAprovechamientoPromedios(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscAprovechamiento|promedios:");
		return "bsc/aprovechamiento/promedios";
	}	
	
}