package aca.web.cultural;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.cultural.spring.CompAsistencia;
import aca.cultural.spring.CompAsistenciaAlumno;
import aca.cultural.spring.CompAsistenciaAlumnoDao;
import aca.cultural.spring.CompAsistenciaDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContCulturalReportes {
	
	@Autowired
	private CompAsistenciaDao compAsistenciaDao;
	
	@Autowired
	private CompAsistenciaAlumnoDao compAsistenciaAlumnoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@RequestMapping("/cultural/reporte/listado")
	public String culturalReporteListado(HttpServletRequest request, Model modelo){
		
		String eventoId			= request.getParameter("EventoId")==null?"0":request.getParameter("EventoId");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		
		List<CompAsistencia> lisEventos 	= compAsistenciaDao.lisTodos(" ORDER BY FECHA");		
		if(eventoId.equals("0") && lisEventos.size() >= 1) {
			eventoId = lisEventos.get(0).getEventoId();
		}
		
		List<CompAsistenciaAlumno> lisAlumnos 			= compAsistenciaAlumnoDao.lisPorEventoyRegistro(eventoId, tipo, " ORDER BY PLAN_ID");
		
		HashMap<String, AlumPersonal> mapaAlumnos 		= alumPersonalDao.mapaAlumnosEnCultural();
		HashMap<String, String> mapaInscritos 			= inscritosDao.getMapaInscritos();
		HashMap<String, String> mapCarreraCultural 		= mapaPlanDao.mapCarreraCultural();
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");			
		
		modelo.addAttribute("eventoId", eventoId);
		modelo.addAttribute("tipo", tipo);
		modelo.addAttribute("lisEventos", lisEventos);	
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaInscritos", mapaInscritos);
		modelo.addAttribute("mapCarreraCultural", mapCarreraCultural);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "cultural/reporte/listado";
	}	

}