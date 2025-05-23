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
public class ContTempAlumno {
	
	@Controller
	public class ContInscritosInscritosSe {
		
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

		
		@RequestMapping("/temp/alumno/buscar")
		public String tempAlumnoBuscar(HttpServletRequest request){
			//enviarConEnoc(request,"Error-aca.ContTempAlumnoBuscar|tempAlumnoBuscar:");
			//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
			return "temp/alumno/buscar";
			}

	}
}