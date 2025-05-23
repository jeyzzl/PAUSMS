package aca.web.bsc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.graduacion.spring.AlumEgresoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContBscEficienciaTerminal {
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private CargaDao cargaDao;
		
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumEgresoDao alumEgresoDao;
	
	@Autowired
	AlumEgresoDao alumEventoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/bsc/eficienciaTerminal/eficiencia")
	public String bscEficienciaTerminalEficiencia(HttpServletRequest request, Model modelo){
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		List<Carga> lisCargas 			= cargaDao.getListAll(" ORDER BY PERIODO DESC, PRIORIDAD, CARGA_ID"); 
		if (cargaId.equals("0") && lisCargas.size()>= 1) cargaId = lisCargas.get(0).getCargaId();
		List<aca.Mapa> lisPlanes 		= estadisticaDao.lisAlumnosPorPlan(cargaId, " ORDER BY FACULTAD(CARRERA(PLAN_ID)), CARRERA_NIVEL(CARRERA(PLAN_ID)), NOMBRE_CARRERA(CARRERA(PLAN_ID))");
		List<aca.Mapa> lisAlumnos 		= estadisticaDao.lisAlumnosEnCorte(cargaId, " ORDER BY FACULTAD(CARRERA(PLAN_ID)), 2");
		
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,String> mapaGraduados 		= alumEgresoDao.mapaGraduados();
		HashMap<String,String> mapaEventos 			= alumEventoDao.mapaFechas();
		
		HashMap<String,Integer> mapaGradPorPlan		= new HashMap<String,Integer>();
		HashMap<String,Integer> mapaGradEnTiempo	= new HashMap<String,Integer>();
		int total			= 0;
		int totalEnTiempo	= 0;
		String ciclos 		= "0";
		String fechaInicio 	= cargaDao.mapeaRegId(cargaId).getFFinal();
		java.util.Date dateInicio = new java.util.Date();		 
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		try {
			dateInicio = sdf.parse(fechaInicio);
		}catch(Exception ex) {
			System.out.println("Error al convertir la fecha 1");
		}		
		for (aca.Mapa m : lisAlumnos) {			
			if (mapaGraduados.containsKey(m.getLlave()+m.getValor())) {
				
				// Fecha del evento en que graduó el alumno
				String eventoId 	= mapaGraduados.get(m.getLlave()+m.getValor());
				String fechaEvento 	= mapaEventos.get(eventoId);
				java.util.Date dateEvento 	= new java.util.Date();
				try {
					dateEvento 	= sdf.parse(fechaEvento);
				}catch(Exception ex) {
					System.out.println("Error al convertir la fecha 2");
				}
				
				// Obtiene la fecha limite para terminar la carrera
				ciclos = mapaPlanes.get(m.getValor()).getCiclos();
				int dias = Integer.parseInt(ciclos)*30*6+30;
				java.util.Date dateFinal = aca.util.Fecha.sumarDiasAFecha(dateInicio, dias);
				if (m.getValor().equals("ISC2010*") || m.getValor().equals("MEDI2010")){
					//System.out.println("Datos:"+m.getValor()+":"+m.getLlave()+":"+ciclos+":"+sdf.format(dateInicio)+":"+sdf.format(dateFinal)+":"+sdf.format(dateEvento));
				}
				
				if (mapaGradPorPlan.containsKey(m.getValor()) ) {
					total = mapaGradPorPlan.get(m.getValor());
					total++;
					mapaGradPorPlan.put(m.getValor(),total);
				}else {
					mapaGradPorPlan.put(m.getValor(),1);
				}
				
				// Si graduó antes de la fecha limite 
				if (dateEvento.before(dateFinal)){
					if (mapaGradEnTiempo.containsKey(m.getValor()) ) {
						totalEnTiempo = mapaGradEnTiempo.get(m.getValor());
						totalEnTiempo++;
						mapaGradEnTiempo.put(m.getValor(),totalEnTiempo);
					}else {
						mapaGradEnTiempo.put(m.getValor(),1);
					}
				}
			}	
		}		
		
		modelo.addAttribute("cargaId", cargaId);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisPlanes", lisPlanes);		
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaGradPorPlan", mapaGradPorPlan);
		modelo.addAttribute("mapaGradEnTiempo", mapaGradEnTiempo);
				
		return "bsc/eficienciaTerminal/eficiencia";
	}		
	
	@RequestMapping("/bsc/eficienciaTerminal/detallado")
	public String bscEficienciaTerminalDetallado(HttpServletRequest request, Model modelo){
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String cargaNombre 	= cargaDao.getNombreCarga(cargaId);
		
		List<aca.Mapa> lisAlumnos 		= estadisticaDao.lisAlumnosEnCorte(cargaId, " ORDER BY FACULTAD(CARRERA(PLAN_ID)),2");
		
		HashMap<String,String> mapaAlumnos 			= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,String> mapaGraduados 		= alumEgresoDao.mapaGraduados();
		HashMap<String,String> mapaEventos 			= alumEventoDao.mapaFechas();
		
		String fechaInicio 	= cargaDao.mapeaRegId(cargaId).getFFinal();
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("fechaInicio", fechaInicio);
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);		
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaGraduados", mapaGraduados);
		modelo.addAttribute("mapaEventos", mapaEventos);
		
		return "bsc/eficienciaTerminal/detallado";
	}

}