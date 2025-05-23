package aca.web.archivo;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContArchivoRespaldo {
	
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
	
	@RequestMapping("/archivo/respaldo/admAlumnos")
	public String archivoRespaldoAdmAlumnos(HttpServletRequest request){		
		return "archivo/respaldo/admAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/admArchivos")
	public String archivoRespaldoAdmArchivos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoAdmArchivos");
		return "archivo/respaldo/admArchivos";
	}
	
	@RequestMapping("/archivo/respaldo/menuRespaldos")
	public String archivoRespaldoMenuRespaldos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoMenuRespaldos");
		return "archivo/respaldo/menuRespaldos";
	}
	
	@RequestMapping("/archivo/respaldo/respaldoAlumnos")
	public String archivoRespaldoRespaldoAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoRespaldoAlumnos");
		return "archivo/respaldo/respaldoAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/respaldoDocAlumno")
	public String archivoRespaldoRespaldoDocAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoRespaldoDocAlumnos");
		return "archivo/respaldo/respaldoDocAlumno";
	}	
	
	@RequestMapping("/archivo/respaldo/respaldoGeneral")
	public String archivoRespaldoRespaldoGeneral(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoRespaldoGeneral");
		return "archivo/respaldo/respaldoGeneral";
	}
	
	@RequestMapping("/archivo/respaldo/respaldoMaestros")
	public String archivoRespaldoRespaldoMaestros(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoRespaldoMaestros");
		return "archivo/respaldo/respaldoMaestros";
	}
	
	@RequestMapping("/archivo/respaldo/zipAdmAlumnos")
	public String archivoRespaldoZipAdmAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoZipAdmAlumnos");
		return "archivo/respaldo/zipAdmAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipAdmAlumnos")
	public String archivoRespaldoDeleteZipAdmAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivoRespaldo|archivoRespaldoDeleteZipAdmAlumnos");
		return "archivo/respaldo/deleteZipAdmAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/existeZipAdmAlumnos")
	public String archivoRespaldoExisteZipAdmAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ContArchivo|archivoRespaldoExisteZipAdmAlumnos");
		return "archivo/respaldo/existeZipAdmAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/zipadmarchivos")
	public String archivoRespaldoZipAdmArchivos(HttpServletRequest request){		
		return "archivo/respaldo/zipadmarchivos";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipAdmArchivos")
	public String archivoRespaldoDeleteZipAdmArchivos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoDeleteZipAdmArchivos");
		return "archivo/respaldo/deleteZipAdmArchivos";
	}
	
	@RequestMapping("/archivo/respaldo/existeZipAdmArchivos")
	public String archivoRespaldoExisteZipAdmArchivos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoExisteZipAdmArchivos");
		return "archivo/respaldo/existeZipAdmArchivos";
	}
	
	@RequestMapping("/archivo/respaldo/zipAlumnos")
	public String archivoRespaldoZipAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoZipAlumnos");
		return "archivo/respaldo/zipAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipAlumnos")
	public String archivoRespaldoDeleteZipAlumnos(HttpServletRequest request){		
		return "archivo/respaldo/deleteZipAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/existeZipAlumnos")
	public String archivoRespaldoExisteZipAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoExisteZipAlumnos");
		return "archivo/respaldo/existeZipAlumnos";
	}
	
	@RequestMapping("/archivo/respaldo/zipDocAlumno")
	public String archivoRespaldoZipDocAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoZipDocAlumnos");
		return "archivo/respaldo/zipDocAlumno";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipDocAlumno")
	public String archivoRespaldoDeleteZipDocAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoDeleteZipDocAlumnos");
		return "archivo/respaldo/deleteZipDocAlumno";
	}
	
	@RequestMapping("/archivo/respaldo/existeZipDocAlumno")
	public String archivoRespaldoExisteZipDocAlumnos(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoExisteZipDocAlumnos");
		return "archivo/respaldo/existeZipDocAlumno";
	}
	
	@RequestMapping("/archivo/respaldo/zipGeneral")
	public String archivoRespaldoZipGeneral(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoZipGeneral");
		return "archivo/respaldo/zipGeneral";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipGeneral")
	public String archivoRespaldoDeleteZipGeneral(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoDeleteZipGeneral");
		return "archivo/respaldo/deleteZipGeneral";
	}	
	
	@RequestMapping("/archivo/respaldo/existeZipGeneral")
	public String archivoRespaldoExisteZipGeneral(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoExisteZipGeneral");
		return "archivo/respaldo/existeZipGeneral";
	}
	
	@RequestMapping("/archivo/respaldo/zipMaestros")
	public String archivoRespaldoZipMaestros(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoZipMaestros");
		return "archivo/respaldo/zipMaestros";
	}
	
	@RequestMapping("/archivo/respaldo/deleteZipMaestros")
	public String archivoRespaldoDeleteZipMaestros(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoDeleteZipMaestros");
		return "archivo/respaldo/deleteZipMaestros";
	}
	
	@RequestMapping("/archivo/respaldo/existeZipMaestros")
	public String archivoRespaldoExisteZipMaestros(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerArchivo|archivoRespaldoExisteZipMaestros");
		return "archivo/respaldo/existeZipMaestros";
	}
	
}