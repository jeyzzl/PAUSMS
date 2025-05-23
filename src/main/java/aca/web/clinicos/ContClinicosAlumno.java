package aca.web.clinicos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.rotaciones.spring.RotEspecialidad;
import aca.rotaciones.spring.RotEspecialidadDao;
import aca.rotaciones.spring.RotHospitalDao;
import aca.rotaciones.spring.RotProgramacion;
import aca.rotaciones.spring.RotProgramacionDao;

@Controller
public class ContClinicosAlumno {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	RotProgramacionDao rotProgramacionDao;	
	
	@Autowired
	RotEspecialidadDao rotEspecialidadDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	RotHospitalDao rotHospitalDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/clinicos/alumno/existeAlumno")
	public String clinicosAlumnoExisteAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|clinicosAlumnoExisteAlumno");
		return "clinicos/alumno/existeAlumno";
	}
	
	@RequestMapping("/clinicos/alumno/setAlumno")
	public String clinicosAlumnoSetAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|clinicosAlumnoSetAlumno");
		return "clinicos/alumno/setAlumno";
	}
	
	@RequestMapping("/clinicos/alumno/existePago")
	public String clinicosAlumnoExistePago(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|clinicosAlumnoExistePago");
		return "clinicos/alumno/existePago";
	}
	
	@RequestMapping("/clinicos/alumno/eliminarProgramacion")
	public String clinicosAlumnoEliminarProgramacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|clinicosAlumnoEliminarProgramacion");
		return "clinicos/alumno/eliminarProgramacion";
	}
	
	@RequestMapping("/clinicos/alumno/eliminarAlumno")
	public String clinicosAlumnoEliminarAlumno(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ControllerClinicos|clinicosAlumnoEliminarAlumno");
		return "clinicos/alumno/eliminarAlumno";
	}

	@RequestMapping("/clinicos/alumno/programacion")
	public String clinicosAlumnoProgramacion(HttpServletRequest request, Model modelo){
		//long time = System.currentTimeMillis();
		List<RotProgramacion> lisProgramaciones = rotProgramacionDao.getListHospSinPago("ORDER BY 1 desc");
		//System.out.println("Time 1:"+(System.currentTimeMillis()-time));
		HashMap<String,String> mapaAlumnos 					=  alumPersonalDao.mapaAlumnosEnRotaciones();		
		HashMap<String,RotEspecialidad> mapaEspecialidades 	=  rotEspecialidadDao.getMapAll("");		
		HashMap<String,String> mapaHospitales	 			=  rotHospitalDao.mapaHospitales();		
		
		modelo.addAttribute("lisProgramaciones", lisProgramaciones);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaEspecialidades", mapaEspecialidades);
		modelo.addAttribute("mapaHospitales", mapaHospitales);		
		
		return "clinicos/alumno/programacion";
	}
	
}