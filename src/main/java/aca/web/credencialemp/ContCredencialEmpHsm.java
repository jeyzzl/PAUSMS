package aca.web.credencialemp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContCredencialEmpHsm {
	
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
	
	@RequestMapping("/credencial_emp/hsm/clinica")
	public String credencialEmpHsmClinica(HttpServletRequest request){
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/clinica";
	}
	
	@RequestMapping("/credencial_emp/hsm/empleado")
	public String credencialEmpHsmEmpleado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpHsmEmpleado:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/empleado";
	}
	
	@RequestMapping("/credencial_emp/hsm/guardar")
	public String credencialEmpHsmGuardar(HttpServletRequest request){
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/guardar";
	}
	
	@RequestMapping("/credencial_emp/hsm/lista")
	public String credencialEmpHsmLista(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpHsmLista:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/lista";
	}
	
	@RequestMapping("/credencial_emp/hsm/subir")
	public String credencialEmpHsmSubir(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpHsmSubir:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/subir";
	}
	
	@RequestMapping("/credencial_emp/hsm/tomarFoto")
	public String credencialEmpHsmTomarFoto(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCcredencialEmp|credencialEmpHsmTomarFoto:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "credencial_emp/hsm/tomarFoto";
	}
}
	