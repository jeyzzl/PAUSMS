package aca.web.titulacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import aca.plan.spring.MapaPlan;
import aca.tit.spring.TitAlumno;
import aca.tit.spring.TitAntecedente;
import aca.tit.spring.TitCarrera;
import aca.tit.spring.TitExpedicion;
import aca.tit.spring.TitProfesional;

@Controller
public class ContTitulacionReportes {	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired	
	private aca.tit.spring.TitTramiteDocDao titTramiteDocDao;
	
	@Autowired	
	private aca.tit.spring.TitAlumnoDao titAlumnoDao;
	
	@Autowired	
	private aca.tit.spring.TitCarreraDao titCarreraDao;
	
	@Autowired	
	private aca.tit.spring.TitProfesionalDao titProfesionalDao;
	
	@Autowired	
	private aca.tit.spring.TitAntecedenteDao titAntecedenteDao;
	
	@Autowired	
	private aca.tit.spring.TitAutorizacionDao titAutorizacionDao;
	
	@Autowired	
	private aca.tit.spring.TitServicioDao titServicioDao;
	
	@Autowired	
	private aca.tit.spring.TitFirmaDao titFirmaDao;
	
	@Autowired	
	private aca.tit.spring.TitExpedicionDao titExpedicionDao;
	
	@Autowired	
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired	
	private aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	
	@RequestMapping("/titulacion/reportes/menu")
	public String titulacionReportesMenu(HttpServletRequest request, Model modelo){	
		
		return "titulacion/reportes/menu";
	}	
	
	@RequestMapping("/titulacion/reportes/alumnos")
	public String titulacionReportesAlumnos(HttpServletRequest request, Model modelo){
		
		String opcion = request.getParameter("Opcion")==null?"0":request.getParameter("Opcion"); 
		
		List<TitAlumno> lisTitulos	= new ArrayList<TitAlumno>();
		if (opcion.equals("0")) {
			lisTitulos = titAlumnoDao.lisAll("ORDER BY FECHA"); 
		}else if (opcion.equals("1")) {
			// Lista sin tramites
			lisTitulos = titAlumnoDao.lisSinTramites("ORDER BY FECHA"); 
		}else if(opcion.equals("2")) {
			// Lista en tramites
			lisTitulos = titAlumnoDao.lisEnTramites("ORDER BY FECHA");
		
		}else if (opcion.equals("3")) {
			lisTitulos = titAlumnoDao.lisTerminado("ORDER BY FECHA");
		}
		
		HashMap<String,aca.tit.spring.TitCarrera> mapaTitCarrera 			= titCarreraDao.mapaAll();
		HashMap<String,aca.tit.spring.TitProfesional> mapaTitProfesional 	= titProfesionalDao.mapaAll();
		HashMap<String,aca.tit.spring.TitExpedicion> mapaTitExpedicion 		= titExpedicionDao.mapaAll();
		HashMap<String,aca.tit.spring.TitAntecedente> mapaTitAntecedente 	= titAntecedenteDao.mapaAll();
		HashMap<String, String> mapaTitulados								= titAlumnoDao.mapTitulados();
		HashMap<String,String> mapaTramites									= titTramiteDocDao.mapaTramites();
		HashMap<String,String> mapaTerminados								= titAlumnoDao.mapaTerminados();
		
		modelo.addAttribute("lisTitulos", lisTitulos);
		modelo.addAttribute("mapaTitCarrera", mapaTitCarrera);
		modelo.addAttribute("mapaTitProfesional", mapaTitProfesional);
		modelo.addAttribute("mapaTitExpedicion", mapaTitExpedicion);
		modelo.addAttribute("mapaTitAntecedente", mapaTitAntecedente);
		modelo.addAttribute("mapaTitulados", mapaTitulados);
		modelo.addAttribute("mapaTramites", mapaTramites);
		modelo.addAttribute("mapaTerminados", mapaTerminados);
		
		return "titulacion/reportes/alumnos";
	}
	
	@RequestMapping("/titulacion/reportes/carrera")
	public String titulacionReportesCarrera(HttpServletRequest request, Model modelo){		
		
		List<TitCarrera> lista		= titCarreraDao.lisAll("ORDER BY CVECARRERA");
		
		HashMap<String, String> mapaTitulados			= titAlumnoDao.mapTitulados();
		HashMap<String, String> mapaMatriculas			= titAlumnoDao.mapaMatriculas();	
		HashMap<String, String> mapaAutorizacion		= titAutorizacionDao.mapaAutorizacion();
		HashMap<String, String> mapaPlanes				= titAlumnoDao.mapaPlanes();
		HashMap<String, MapaPlan> mapaPlanRvoe			= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,String> mapaTramites				= titTramiteDocDao.mapaTramites();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaTitulados", mapaTitulados);
		modelo.addAttribute("mapaMatriculas", mapaMatriculas);
		modelo.addAttribute("mapaAutorizacion", mapaAutorizacion);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaPlanRvoe", mapaPlanRvoe);
		modelo.addAttribute("mapaTramites", mapaTramites);

		return "titulacion/reportes/carrera";
	}
	
	@RequestMapping("/titulacion/reportes/profesional")
	public String titulacionReportesProfesional(HttpServletRequest request, Model modelo){	
		
		List<TitProfesional> lisProfesional			= titProfesionalDao.listAll(" ORDER BY PRIMERAPELLIDO, SEGUNDOAPELLIDO, NOMBRE");
		//HashMap<String, String> mapaTitulados		= titAlumnoDao.mapTitulados();
		HashMap<String, String> mapaMatriculas		= titAlumnoDao.mapaMatriculas();
		
		modelo.addAttribute("lisProfesional", lisProfesional);
		//modelo.addAttribute("mapaTitulados", mapaTitulados);
		modelo.addAttribute("mapaMatriculas", mapaMatriculas);
		
		return "titulacion/reportes/profesional";
	}
	
	@RequestMapping("/titulacion/reportes/expedicion")
	public String titulacionReportesExpedicion(HttpServletRequest request, Model modelo){	
		
		List<TitExpedicion> lisExpedicion			= titExpedicionDao.lisAll(" ORDER BY FECHAEXPEDICION");
		HashMap<String, String> mapaTitulados		= titAlumnoDao.mapTitulados();
		HashMap<String, String> mapaMatriculas		= titAlumnoDao.mapaMatriculas();
		HashMap<String, String> mapaServicios		= titServicioDao.mapaServicios();
		
		modelo.addAttribute("lisExpedicion", lisExpedicion);
		modelo.addAttribute("mapaTitulados", mapaTitulados);
		modelo.addAttribute("mapaMatriculas", mapaMatriculas);
		modelo.addAttribute("mapaServicios", mapaServicios);
		
		return "titulacion/reportes/expedicion";
	}
	
	@RequestMapping("/titulacion/reportes/antecedentes")
	public String titulacionReportesAntecedentes(HttpServletRequest request, Model modelo){	
		
		List<TitAntecedente> lisAntecedentes		= titAntecedenteDao.listAll(" ORDER BY FECHAINICIO");
		HashMap<String, String> mapaTitulados		= titAlumnoDao.mapTitulados();
		HashMap<String, String> mapaMatriculas		= titAlumnoDao.mapaMatriculas();	
		
		modelo.addAttribute("lisAntecedentes", lisAntecedentes);
		modelo.addAttribute("mapaTitulados", mapaTitulados);
		modelo.addAttribute("mapaMatriculas", mapaMatriculas);
		
		return "titulacion/reportes/antecedentes";
	}
}