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
public class ContDatosAlumnoConstancia {
	
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
	
	@RequestMapping("/datos_alumno/constancia/form")
	public String datosAlumnoConstanciaform(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoConstancia|datosAlumnoConstanciaForm:");        
		return "datos_alumno/constancia_mat/form";
	}
	
	@RequestMapping("/datos_alumno/constancia/show")
	public String datosAlumnoConstanciaShow(HttpServletRequest request, Model modelo){
		return "datos_alumno/constancia_mat/show";
	}
	
	@RequestMapping("/datos_alumno/constancia/view")
	public String datosAlumnoConstanciaView(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoConstanciaMat|datosAlumnoConstanciaView:");        
		return "datos_alumno/constancia/view";
	}

}