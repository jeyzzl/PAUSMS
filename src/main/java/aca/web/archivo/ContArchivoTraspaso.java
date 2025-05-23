package aca.web.archivo;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContArchivoTraspaso {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/archivo/traspaso/menu")
	public String archivoTraspasoMenu(HttpServletRequest request){		
		return "archivo/traspaso/menu";
	}
	
	@RequestMapping("/archivo/traspaso/admAlumnos")
	public String archivoTraspasoAdmAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoAdmAlumnos");
		return "archivo/traspaso/admAlumnos";
	}
	
	@RequestMapping("/archivo/traspaso/admArchivos")
	public String archivoTraspasoAdmArchivos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoAdmArchivos");
		return "archivo/traspaso/admArchivos";
	}
	
	@RequestMapping("/archivo/traspaso/archivosProfesor")
	public String archivoTraspasoArchivosProfesor(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoArchivosProfesor");
		return "archivo/traspaso/archivosProfesor";
	}
	
	@RequestMapping("/archivo/traspaso/archivosAlumno")
	public String archivoTraspasoArchivosAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoArchivosAlumno");
		return "archivo/traspaso/archivosAlumno";
	}
	
	@RequestMapping("/archivo/traspaso/traspaso")
	public String archivoTraspasoTraspaso(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoTraspaso");
		return "archivo/traspaso/traspaso";
	}
	
	@RequestMapping("/archivo/traspaso/traspasoGeneral")
	public String archivoTraspasoTraspasoGeneral(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoTraspaso|archivoTraspasoTraspasoGeneral");
		return "archivo/traspaso/traspasoGeneral";
	}
	
}
