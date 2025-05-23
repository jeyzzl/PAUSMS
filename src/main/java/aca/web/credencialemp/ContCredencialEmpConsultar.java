package aca.web.credencialemp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ContCredencialEmpConsultar {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
		
	@Autowired	ServletContext context;
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
	
	@RequestMapping("/credencial_emp/consultar/datoDep")
	public String credencialEmpConsultarDatoDep(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpConsultarDatoDep:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/consultar/datoDep";
	}
	
	@RequestMapping("/credencial_emp/consultar/empleado")
	public String credencialEmpConsultarEmpleado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpConsultarEmpleado:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatosEmpUploadDep:");		
		return "credencial_emp/consultar/empleado";
	}
}
		