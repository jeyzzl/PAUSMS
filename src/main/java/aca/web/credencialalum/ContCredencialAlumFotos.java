package aca.web.credencialalum;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContCredencialAlumFotos {
	
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
	
	@RequestMapping("/credencial_alum/fotos/bajar")
	public String credencialAlumFotosBajar(HttpServletRequest request){		
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/fotos/bajar";
	}	
	
	@RequestMapping("/credencial_alum/fotos/marcarFotos")
	public String credencialAlumFotosMarcarFotos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCredencialAlumFotos|credencialAlumFotosMarcarFotos:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/fotos/marcarFotos";
	}	
	
	@RequestMapping("/credencial_alum/fotos/subir")
	public String credencialAlumFotosSubir(HttpServletRequest request){		
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUpload:");		
		return "credencial_alum/fotos/subir";
	}		
}

	