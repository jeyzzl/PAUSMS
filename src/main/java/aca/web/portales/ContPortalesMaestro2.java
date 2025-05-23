package aca.web.portales;




import aca.voto.spring.*;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContPortalesMaestro2 {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.cita.spring.CitaEventoDao citaEventoDao;
	
	@Autowired
	aca.cita.spring.CitaPeriodoDao citaPeriodoDao;
	
	@Autowired
	aca.cita.spring.CitaReservadaDao citaReservadaDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.voto.spring.VotoEventoDao votoEventoDao;	
	
	@Autowired
	VotoCandidatoDao votoCandidatoDao;
	
	@Autowired
	aca.voto.spring.VotoAlumnoDao votoAlumnoDao;
	
	@Autowired
	aca.por.spring.PorHoraDao porHoraDao;
	
	@Autowired
	aca.por.spring.PorRegistroDao porRegistroDao;
	
	@Autowired
	aca.plan.spring.MapaArchivoDao mapaArchivoDao;

	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/maestro2/alumnoAccion")
	public String portalesMaestro2AlumnoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2AlumnoAccion");
		return "portales/maestro2/alumnoAccion";
	}
	
	@RequestMapping("/portales/maestro2/bajarArchivoAlumno")
	public String portalesMaestro2BajarArchivoAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2BajarArchivoAlumno");
		return "portales/maestro2/bajarArchivoAlumno";
	}
	
	@RequestMapping("/portales/maestro2/copiar_metodoAccion")
	public String portalesMaestro2CopiarMetodoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2CopiarMetodoAccion");
		return "portales/maestro2/copiar_metodoAccion";
	}
	
	@RequestMapping("/portales/maestro2/correccionPDF")
	public String portalesMaestro2CorreccionPDF(HttpServletRequest request){
		//enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2CorreccionPDF");
		return "portales/maestro2/correccionPDF";
	}

	@RequestMapping("/portales/maestro2/evalua_metodoAccion")
	public String portalesMaestro2EvaluaMetodoAccion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2EvaluaMetodoAccion");
		return "portales/maestro2/evalua_metodoAccion";
	}

	@RequestMapping("/portales/maestro2/horario3")
	public String portalesMaestro2Horario3(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerPortales|portalesMaestro2Horario3");
		return "portales/maestro2/horario3";
	}
}