package aca.web.temp;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.bec.spring.BecAcceso;
import aca.bec.spring.BecAccesoDao;
import aca.financiero.spring.ContCcosto;
import aca.financiero.spring.ContCcostoDao;
import aca.financiero.spring.ContEjercicio;
import aca.financiero.spring.ContEjercicioDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.Usuarios;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContTemp {
		
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
	
	@RequestMapping("/temp/eventos")
	public String tempEventos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTempEventos|tempEventos:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "temp/eventos";
	}
	
	@RequestMapping("/temp/inscritosPdf")
	public String tempInscritosPdf(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTempInscritosPdf|tempInscritosPdf:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "temp/inscritosPdf";
	}
	
	@RequestMapping("/temp/materias")
	public String tempMaterias(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContTempMaterias|tempMaterias:");
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "temp/materias";
	}	
}