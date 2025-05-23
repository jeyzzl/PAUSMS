package aca.web.servicio;

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

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocAsignacion;
import aca.ssoc.spring.SsocAsignacionDao;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.ssoc.spring.SsocInicio;
import aca.ssoc.spring.SsocInicioDao;
import aca.ssoc.spring.SsocSectorDao;
import aca.util.Fecha;
import aca.vista.spring.InscritosDao;

@Controller
public class ContServicioReporte {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	aca.salida.spring.SalSolicitudDao salSolicitudDao;
	
	@Autowired
	aca.salida.spring.SalGrupoDao salGrupoDao;
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	SsocInicioDao ssocInicioDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	SsocSectorDao ssocSectorDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	
	
	@Autowired
	InscritosDao inscritosDao;	
	
	@Autowired
	SsocAsignacionDao ssocAsignacionDao;
	
	
	@RequestMapping("/servicio/reporte/menu")
	public String servicioReporteMenu(HttpServletRequest request, Model modelo){
		
		return "servicio/reporte/menu";
	}
	
	@RequestMapping("/servicio/reporte/finalizado")
	public String servicioReporteFinalizado(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			if (fechaIni.equals("0")) fechaIni = sesion.getAttribute("fechaIni").toString(); else sesion.setAttribute("fechaIni", fechaIni); 
			if (fechaFin.equals("0")) fechaFin = sesion.getAttribute("fechaFin").toString(); else sesion.setAttribute("fechaFin", fechaFin);		
		}
		
		List<SsocInicio> lisAlumnos			= ssocInicioDao.getListAlumConDoc("23", fechaIni, fechaFin, "ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), PLAN_ID, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");
		
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String, AlumPersonal> mapaAlumnos 		= alumPersonalDao.mapaAlumnosEnServicio();
		HashMap<String, String> mapaDocumentos 		= ssocDocAlumDao.mapaFechaDocumento("23");
		HashMap<String, String> mapaAlumnosSector	= ssocInicioDao.mapaAlumnoSector();
		HashMap<String, String> mapaSectores		= ssocSectorDao.mapaSectorNombre();
		
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaDocumentos",mapaDocumentos);
		modelo.addAttribute("mapaAlumnosSector",mapaAlumnosSector);
		modelo.addAttribute("mapaSectores",mapaSectores);
		
		return "servicio/reporte/finalizado";
	}	
	
	@RequestMapping("/servicio/reporte/cancelado")
	public String servicioReporteCancelado(HttpServletRequest request, Model modelo){
		
		List<SsocInicio> lisCancelado	= ssocInicioDao.getListAll("ORDER BY ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");
		
		HashMap<String, String> mapaAlumnos 		= alumPersonalDao.mapaAlumnosServicio();
		HashMap<String, String> mapaDocumentos 		= ssocDocAlumDao.mapaFechaDocumento("23");
		
		modelo.addAttribute("lisCancelado",lisCancelado);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaDocumentos",mapaDocumentos);
		
		return "servicio/reporte/cancelado";
	}	
	
	@RequestMapping("/servicio/reporte/inscritos")
	public String servicioReporteInscritos(HttpServletRequest request, Model modelo){
		
		List<SsocInicio> lisInscrito	 = ssocInicioDao.getListInscrito("ORDER BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), PLAN_ID, ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)");
		
		HashMap<String, String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosServicio();
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaCarreraPlan 		= mapaPlanDao.mapCarreraPlan();	
		HashMap<String, String> mapaNacionalidades 		= inscritosDao.mapaNacionalidad();		
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap<String, String> mapaAvance		 		= ssocInicioDao.mapaAvanceActual();	
		
		modelo.addAttribute("mapaPaises",mapaPaises);
		modelo.addAttribute("lisInscrito",lisInscrito);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaCarreraPlan",mapaCarreraPlan);
		modelo.addAttribute("mapaNacionalidades",mapaNacionalidades);
		modelo.addAttribute("mapaAvance",mapaAvance);
		
		return "servicio/reporte/inscritos";
	}	
	
	@RequestMapping("/servicio/reporte/activos")
	public String servicioReporteActivos(HttpServletRequest request, Model modelo){
		
		String fechaIni		=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
		String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
		
		List<SsocInicio> lisActivos	= ssocInicioDao.getListActivoPorFechaInicio(fechaIni,fechaFin);
		
		HashMap<String, String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosServicio();
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaCarreraPlan 		= mapaPlanDao.mapCarreraPlan();	
		HashMap<String, String> mapaNacionalidades 		= inscritosDao.mapaNacionalidad();		
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap<String, String> mapaAvance		 		= ssocInicioDao.mapaAvanceActual();	
		
		HashMap<String,String> mapaAsignaciones	= ssocAsignacionDao.mapaSectorAsignado(fechaIni,fechaFin);		
		
		modelo.addAttribute("mapaPaises",mapaPaises);
		modelo.addAttribute("lisActivos",lisActivos);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaCarreraPlan",mapaCarreraPlan);
		modelo.addAttribute("mapaNacionalidades",mapaNacionalidades);
		modelo.addAttribute("mapaAvance",mapaAvance);
		modelo.addAttribute("fechaIni",fechaIni);
		modelo.addAttribute("fechaFin",fechaFin);
		modelo.addAttribute("mapaAsignaciones",mapaAsignaciones);
		
		return "servicio/reporte/activos";
	}
}