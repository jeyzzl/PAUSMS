package aca.web.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumBanco;
import aca.alumno.spring.AlumBancoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;

@Controller
public class ContAdmisionReportes {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;

	@Autowired
	AlumBancoDao alumBancoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/admision/reportes/menu")
	public String admisionReporteReporte(HttpServletRequest request){		
		return "admision/reportes/menu";
	}
	
	@RequestMapping("/admision/reportes/datos")
	public String admisionReportesDatos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmisionReporte|admisionReportesDatos:");
		return "admision/reportes/datos";
	}
	
	@RequestMapping("/admision/reportes/ubicacion")
	public String admisionReportesUbicacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmisionReporte|admisionReportesUbicacion:");
		return "admision/reportes/ubicacion";
	}
	
	@RequestMapping("/admision/reportes/alumnos")
	public String admisionReporteAlumnos(HttpServletRequest request, Model modelo) {
		
		List<AlumPersonal> lista = alumPersonalDao.getListAll(" ORDER BY APELLIDO_PATERNO");
		
		modelo.addAttribute("lista", lista);
		
		return "admision/reportes/alumnos";
	}

	@RequestMapping("/admision/reportes/banco")
	public String admisionReporteBanco(HttpServletRequest request, Model modelo){
		List<AlumPersonal> listaAlumnos = alumPersonalDao.getListAll(" ORDER BY APELLIDO_PATERNO");
		HashMap<String, AlumBanco> mapaBanco = alumBancoDao.mapaAlumBanco();

		modelo.addAttribute("listaAlumnos", listaAlumnos);
		modelo.addAttribute("mapaBanco", mapaBanco);
		
		return "admision/reportes/banco";
	}
}