package aca.web.datosalumno;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContDatosAlumnoImportado {
	
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
	
	@RequestMapping("/datos_alumno/importado/accion")
	public String datosAlumnoImportadoAccion(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoImportado|datosAlumnoImportadoAccion:");        
		return "datos_alumno/importado/accion";
	}
	
	@RequestMapping("/datos_alumno/importado/cursos")
	public String datosAlumnoImportadoCursos(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoImportado|datosAlumnoImportadoCursos:");        
		return "datos_alumno/importado/cursos";
	}
	
	@RequestMapping("/datos_alumno/importado/curso_update")
	public String datosAlumnoImportadoCursoUpdate(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoImportado|datosAlumnoImportadoCursoUpdate:");        
		return "datos_alumno/importado/curso_update";
	}

}