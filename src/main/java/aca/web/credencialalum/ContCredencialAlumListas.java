package aca.web.credencialalum;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContCredencialAlumListas {
	
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
	
	public void enviarConArchivo(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conArchivo", archivo.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/credencial_alum/listas/filtro")
	public String credencialAlumListasFiltro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCredencialAlumListas|credencialAlumListasFiltro:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/listas/filtro";
	}	
	
	@RequestMapping("/credencial_alum/listas/lista")
	public String credencialAlumListasLista(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCredencialAlumListas|credencialAlumListasLista:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/listas/lista";
	}	
	
	@RequestMapping("/credencial_alum/listas/traspaso")
	public String credencialAlumListasTraspaso(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCredencialAlumListas|credencialAlumListasTraspaso:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/listas/traspaso";
	}	
		
}

	