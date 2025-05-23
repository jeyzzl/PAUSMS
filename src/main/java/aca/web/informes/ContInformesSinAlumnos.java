package aca.web.informes;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContInformesSinAlumnos {
	
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
	
	@RequestMapping("/informes/sinalumnos/borrarAll")
	public String informesBecasSinAlumnosBorrarAll(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInformesSinAlumnos|BorrarAll:");
		return "informes/sinalumnos/borrarAll";
	}		

	@RequestMapping("/informes/sinalumnos/borrar")
	public String informesBecasSinAlumnosBorrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInformesSinAlumnos|Borrar:");
		return "informes/sinalumnos/borrar";
	}		
	
	@RequestMapping("/informes/sinalumnos/carga")
	public String informesBecasSinAlumnosCarga(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInformesSinAlumnos|Carga:");
		return "informes/sinalumnos/carga";
	}		
	
	@RequestMapping("/informes/sinalumnos/sinalumnos")
	public String informesBecasSinAlumnosSinAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInformesSinAlumnos|SinALumnos:");
		return "informes/sinalumnos/sinalumnos";
	}		

}