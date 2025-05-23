package aca.web.disciplina;

import java.util.List;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.disciplina.spring.CondAlumno;
import aca.disciplina.spring.CondAlumnoDao;
import aca.disciplina.spring.CondJuez;
import aca.disciplina.spring.CondJuezDao;
import aca.disciplina.spring.CondLugarDao;
import aca.disciplina.spring.CondReporte;
import aca.disciplina.spring.CondReporteDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContDisciplinaReportes {	
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private CondAlumnoDao condAlumnoDao;
	
	@Autowired
	private CondJuezDao condJuezDao;
	
	@Autowired
	private CondReporteDao condReporteDao;
	
	@Autowired
	private CondLugarDao condLugarDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/disciplina/reportes/menu")
	public String disciplinaReportesMenu(HttpServletRequest request, Model modelo){	
		
		return "disciplina/reportes/menu";
	}		
	
	@RequestMapping("/disciplina/reportes/condicionales")
	public String disciplinaReportesCondicionales(HttpServletRequest request, Model modelo){
		String periodoId			= catPeriodoDao.getPeriodo();
		
		List<CondAlumno> lisAlumnos 				= condAlumnoDao.getListReporte( periodoId, "51", " ORDER BY FOLIO ");
		HashMap<String,AlumPlan> mapaPlanAlumno		= alumPlanDao.mapaAlumnosEnDisciplina(periodoId);
		HashMap<String,MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaCantidades		= condAlumnoDao.mapaCantidades(periodoId);
		HashMap<String,String> mapaAlumnos			= alumPersonalDao.mapaAlumnosEnDisciplina(periodoId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaPlanAlumno", mapaPlanAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaCantidades", mapaCantidades);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "disciplina/reportes/condicionales";
	}
	
	@RequestMapping("/disciplina/reportes/registros")
	public String disciplinaReportesUnidades(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}			
		}	
		
		List<CondAlumno> lisRegistros 				= condAlumnoDao.getListFechas(fechaIni, fechaFin, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,2)||SUBSTR(FECHA,1,2), IDREPORTE");
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosEnDisciplina(fechaIni, fechaFin);
		HashMap<String,String> mapaJueces 			= condJuezDao.mapaJuez();
		HashMap<String,CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
		
		modelo.addAttribute("lisRegistros", lisRegistros);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaJueces", mapaJueces);
		modelo.addAttribute("mapaReportes", mapaReportes);
		
		return "disciplina/reportes/registros";
	}
	
	@RequestMapping("/disciplina/reportes/alumno")
	public String disciplinaReportesAlumno(HttpServletRequest request, Model modelo){	
		String periodoId		= catPeriodoDao.getPeriodo();
		String codigoAlumno		= "0";
		String alumnoNombre		= "-";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 	=  (String) sesion.getAttribute("codigoAlumno");
			alumnoNombre 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}	
		
		List<CondAlumno> lisRegistros 				= condAlumnoDao.getList(codigoAlumno, periodoId, " ORDER BY ENOC.COND_ALUMNO.FECHA");		
		HashMap<String,String> mapaJueces 			= condJuezDao.mapaJuez();
		HashMap<String,CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
		HashMap<String,String> mapaLugares 			= condLugarDao.mapaLugar();
		
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("lisRegistros", lisRegistros);		
		modelo.addAttribute("mapaJueces", mapaJueces);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugares", mapaLugares);
		
		return "disciplina/reportes/alumno";
	}
	
	@RequestMapping("/disciplina/reportes/portipo")
	public String disciplinaReportesPorTipo(HttpServletRequest request, Model modelo){
		String periodoId			= catPeriodoDao.getPeriodo();		
		
		List<CondAlumno> lisRegistros 				= condAlumnoDao.getLista(periodoId, " ORDER BY IDREPORTE, ENOC.ALUM_NOMBRE(MATRICULA)");
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosEnDisciplina(periodoId);		
		HashMap<String,String> mapaJueces 			= condJuezDao.mapaJuez();
		HashMap<String,CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
		HashMap<String,String> mapaLugares 			= condLugarDao.mapaLugar();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisRegistros", lisRegistros);		
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);		
		modelo.addAttribute("mapaJueces", mapaJueces);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugares", mapaLugares);
		
		return "disciplina/reportes/portipo";
	}
	
	@RequestMapping("/disciplina/reportes/pordormitorio")
	public String disciplinaReportesPorDormitorio(HttpServletRequest request, Model modelo){
		String periodoId			= catPeriodoDao.getPeriodo();		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}			
		}
		
		List<Inscritos> lisAlumnos 					= inscritosDao.lisUnidadesPorDormitorio(periodoId, fechaIni, fechaFin, " ORDER BY DORMITORIO,ENOC.CARGA_PRIORIDAD(CARGA_ID)");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaCantidades 		= condAlumnoDao.mapaCantidades(periodoId);
			
		modelo.addAttribute("lisAlumnos", lisAlumnos);	
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaCantidades", mapaCantidades);
		
		return "disciplina/reportes/pordormitorio";
	}
	
	@RequestMapping("/disciplina/reportes/porPeriodo")
	public String disciplinaReportesPorPeriodo(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		List<CatPeriodo> lisPeriodos 	= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size() > 0){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		List<CondAlumno> lisAlumnos 				= condAlumnoDao.getLista(periodoId, " ORDER BY FECHA");
		HashMap<String,String> mapaJueces 			= condJuezDao.mapaJuez();
		HashMap<String,CondReporte> mapaReportes 	= condReporteDao.mapaReportes();
		HashMap<String,String> mapaLugares 			= condLugarDao.mapaLugar();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		
		modelo.addAttribute("mapaJueces", mapaJueces);
		modelo.addAttribute("mapaReportes", mapaReportes);
		modelo.addAttribute("mapaLugares", mapaLugares);
						
		return "disciplina/reportes/porPeriodo";
	}	
}