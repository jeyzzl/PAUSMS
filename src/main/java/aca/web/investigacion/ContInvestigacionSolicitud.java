package aca.web.investigacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.emp.spring.Empleado;
import aca.emp.spring.EmpleadoDao;
import aca.investiga.spring.InvArchivoDao;
import aca.investiga.spring.InvComentarioDao;
import aca.investiga.spring.InvProyecto;
import aca.investiga.spring.InvProyectoDao;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContInvestigacionSolicitud {
	
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
	
	@RequestMapping("/investigacion/solicitud/accion")
	public String investigacionSolicitudAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionSolicitud|investigacionSolicitudAccion:");
		return "investigacion/solicitud/accion";
	}
	
	@RequestMapping("/investigacion/solicitud/evento")
	public String investigacionSolicitudEvento(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionSolicitud|investigacionSolicitudEvento:");
		return "investigacion/solicitud/evento";
	}
	
	@RequestMapping("/investigacion/solicitud/alumno")
	public String investigacionSolicitudAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInvestigacionSolicitud|investigacionSolicitudAlumno:");
		return "investigacion/solicitud/alumno";
	}
	
}