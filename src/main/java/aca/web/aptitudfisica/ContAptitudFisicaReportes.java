package aca.web.aptitudfisica;

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

import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.apFisica.spring.ApFisicaAlumno;
import aca.apFisica.spring.ApFisicaAlumnoDao;
import aca.apFisica.spring.ApFisicaGrupo;
import aca.apFisica.spring.ApFisicaGrupoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.notifica.spring.NotiCovid;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContAptitudFisicaReportes {

	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc; 
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private ApFisicaAlumnoDao apFisicaAlumnoDao;
	
	@Autowired	
	private ApFisicaGrupoDao apFisicaGrupoDao;
	
	@Autowired	
	private AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private AlumnoCursoDao alumnoCursoDao;  
	
	@Autowired
	CatTipoCalDao catTipoCalDao;	
	
	
	@RequestMapping("/aptitud_fisica/reportes/menu")
	public String aptitudFisicaReportesMenu(HttpServletRequest request){

		return "aptitud_fisica/reportes/menu";
	}
	
	@RequestMapping("/aptitud_fisica/reportes/porMateria")
	public String aptitudFisicaReportesPorMateria(HttpServletRequest request, Model modelo){

		String fecha    		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String clave 			= request.getParameter("clave")==null?"COSM191":request.getParameter("clave");
		
		List<AlumnoCurso> lisAlumnos						= alumnoCursoDao.lisAlumnosPorMateria(fecha, clave,"'I','1','2','3','4','5','6'", " ORDER BY ENOC.FACULTAD(CARRERA_ID), PLAN_ID");
		HashMap<String, MapaPlan> mapaPlanes			 	= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, String> mapaAlumnos		 			= alumPersonalDao.mapaAlumnosEnUnaMateria(clave);
		HashMap<String, ApFisicaAlumno> mapaGrupoAlumnos 	= apFisicaAlumnoDao.mapaGrupoDelAlumno();
		HashMap<String, ApFisicaGrupo> mapaGrupos			= apFisicaGrupoDao.mapaGrupos();
		HashMap<String, CatTipoCal> mapaTipos				= catTipoCalDao.getMapAll("");
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);		
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaGrupoAlumnos", mapaGrupoAlumnos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "aptitud_fisica/reportes/porMateria";
	}
	
	@RequestMapping("/aptitud_fisica/reportes/registrados")
	public String aptitudFisicaReportesRegistrados(HttpServletRequest request, Model modelo){

		String fecha    		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		String clave 			= request.getParameter("clave")==null?"COSM191":request.getParameter("clave");
		
		List<ApFisicaAlumno> lista 					= apFisicaAlumnoDao.alumPorMateria(clave, fecha, " ORDER BY GRUPO_ID");
		HashMap<String, String> mapaAlumCarrera		= apFisicaAlumnoDao.mapaCarrerasDeAlumnos(clave, fecha);
		HashMap<String, MapaPlan> mapaPlanes	 	= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, String> mapaAlumnos		 	= alumPersonalDao.mapaAlumnosEnAptitud();
		HashMap<String, String> mapaGrupos			= apFisicaGrupoDao.mapaNombreGrupo();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaAlumCarrera", mapaAlumCarrera);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		
		return "aptitud_fisica/reportes/registrados";
	}
	
	@RequestMapping("/aptitud_fisica/reportes/porPeriodo")
	public String aptitudFisicaReportesPorPeriodo(HttpServletRequest request, Model modelo){
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();		
		if (sesion != null && cargaId.equals("0")) {
			cargaId 	= (String)sesion.getAttribute("cargaId");
		}
		
		List<Carga> lisCarga 						= cargaDao.getListAll("ORDER BY 4 DESC, 2");
		List<AlumnoCurso> lisAlumnos				= alumnoCursoDao.lisAlumnosEnAptitud(cargaId, "'COSM191','COSM192','COSM293','COSM394','COSM395','COSM396','FGAF133','FGAF134','FGAF233','FGAF234','FGAF333','FGAF334'"," ORDER BY PLAN_ID");
		HashMap<String, AlumPersonal> mapaAlumnos 	= alumPersonalDao.mapaAlumnosEnCarga(cargaId);
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll(" ");
		HashMap<String, ApFisicaAlumno> mapaGrupos  = apFisicaAlumnoDao.mapaGrupoDelAlumno();
		HashMap<String, String> mapaNobresGrupos	= apFisicaGrupoDao.mapaNombreGrupo();
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		modelo.addAttribute("mapaNobresGrupos", mapaNobresGrupos);
		
		return "aptitud_fisica/reportes/porPeriodo";
	}
	
	@RequestMapping("/aptitud_fisica/reportes/sinRegistro")
	public String aptitudFisicaReportesSinRegistro(HttpServletRequest request, Model modelo){

		HttpSession sesion = ((HttpServletRequest)request).getSession();
		
		String clave 			= request.getParameter("clave")==null?"COSM191":request.getParameter("clave");
		String cargaId			= request.getParameter("carga")==null?(String)sesion.getAttribute("cargaId"):request.getParameter("carga");
		
		List<AlumnoCurso> lista 				= alumnoCursoDao.lisAptitudSinGrupo(cargaId, clave, "ORDER BY CODIGO_PERSONAL");
		List<Carga> lisCarga 					= cargaDao.getListAptitud("ORDER BY 4 DESC, 2");
		
		HashMap<String, AlumPersonal> mapaAlumnos	        = alumPersonalDao.mapaAlumnosEnCarga(cargaId);
		HashMap<String, CatCarrera> carreraNombre 			= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "aptitud_fisica/reportes/sinRegistro";
	}
	
}
