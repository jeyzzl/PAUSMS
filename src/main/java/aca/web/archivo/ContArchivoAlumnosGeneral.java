package aca.web.archivo;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ContArchivoAlumnosGeneral {
	
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
	
	@RequestMapping("/archivo/alumnos_general/listado")
	public String archivoAlumnosGeneralListado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoAlumnosGeneral|archivoAlumnosGeneralListado");
		return "archivo/alumnos_general/listado";
	}
}
