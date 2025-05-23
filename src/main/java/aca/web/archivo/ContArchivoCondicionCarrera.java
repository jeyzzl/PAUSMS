package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchGrupos;
import aca.archivo.spring.ArchGruposCarrera;
import aca.archivo.spring.ArchGruposCarreraDao;
import aca.archivo.spring.ArchGruposDao;
import aca.archivo.spring.ArchStatusDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContArchivoCondicionCarrera {	
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	ArchGruposDao archGruposDao;
	
	@Autowired
	ArchDocumentosDao archDocumentosDao;
	
	@Autowired
	ArchStatusDao archStatusDao;	
	
	@Autowired
	ArchGruposCarreraDao archGruposCarreraDao;
	
	
	@RequestMapping("/archivo/condicion_carrera/facultad")
	public String archivoCondicionCarreraFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores	= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);		
		
		return "archivo/condicion_carrera/facultad";
	}
	
	@RequestMapping("/archivo/condicion_carrera/carrera")
	public String archivoCondicionCarreraCarrera(HttpServletRequest request, Model modelo){
		
		String facultadId			= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String nombreFacultad 		= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CatCarrera> lisCarrera					= catCarreraDao.getLista(facultadId, "ORDER BY NOMBRE_CARRERA");
		List<MapaPlan> lisPlan						= mapaPlanDao.getListPlanFac(facultadId, "ORDER BY PLAN_ID");
		HashMap<String, String>	mapaCoordinadores 	= maestrosDao.mapaCoordinadores();
		HashMap<String, String>	mapaCondiciones 	= archGruposCarreraDao.mapaGrupos();
		
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("nombreFacultad", nombreFacultad);
		modelo.addAttribute("lisCarrera", lisCarrera);
		modelo.addAttribute("lisPlan", lisPlan);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCondiciones", mapaCondiciones);
		
		return "archivo/condicion_carrera/carrera";
	}
	
	@RequestMapping("/archivo/condicion_carrera/grupos")
	public String archivoCondicionCarreraGrupos(HttpServletRequest request, Model modelo){
		
		String carreraId		= request.getParameter("carrera")==null?"0":request.getParameter("carrera");
		CatCarrera carrera		= catCarreraDao.mapeaRegId(carreraId);
		
		List<ArchGrupos> lisGrupos					= archGruposDao.getListGrupoCarrera(carreraId, "ORDER BY GRUPO, IDDOCUMENTO");
		
		HashMap<String, String>	mapaDocumentos 		= archDocumentosDao.mapaTodos();
		HashMap<String, String>	mapaStatus 			= archStatusDao.mapaStatus();
	    
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/condicion_carrera/grupos";
	}
	
	@RequestMapping("/archivo/condicion_carrera/eligeGrupo")
	public String archivoCondicionCarreraEligeGrupo(HttpServletRequest request, Model modelo){			
		
		String carreraId		= request.getParameter("carrera")==null?"0":request.getParameter("carrera");
		String gruposCarrera	= archGruposCarreraDao.getGruposCarrera(carreraId);
		
		List<ArchGrupos> lisGrupos					= archGruposDao.getListAll(" ORDER BY GRUPO, NOMBRE_DOCUMENTO(IDDOCUMENTO)");
		HashMap<String, String>	mapaDocumentos 		= archDocumentosDao.mapaTodos();
		HashMap<String, String>	mapaStatus 			= archStatusDao.mapaStatus();	
		
		modelo.addAttribute("gruposCarrera", gruposCarrera);
		modelo.addAttribute("lisGrupos", lisGrupos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaStatus", mapaStatus);
		
		return "archivo/condicion_carrera/eligeGrupo";
	}	
	
	@RequestMapping("/archivo/condicion_carrera/grabarGrupos")
	public String archivoCondicionCarreraGrabarGrupos(HttpServletRequest request, Model modelo){			
		
		String carreraId				= request.getParameter("carrera")==null?"0":request.getParameter("carrera");		
		List<ArchGrupos> lisGrupos		= archGruposDao.getListAll(" ORDER BY GRUPO, NOMBRE_DOCUMENTO(IDDOCUMENTO)");
		String gruposCarrera			= "";
		String grupoTemp				= "X";
		String mensaje 					= "-";
		
		for (ArchGrupos grupo : lisGrupos) {			
			if (request.getParameter("Grupo"+grupo.getGrupo())!=null) {
				if (!grupoTemp.equals(grupo.getGrupo())) {
					grupoTemp = grupo.getGrupo();
					gruposCarrera += " "+grupo.getGrupo();
				}				  
			}			
		}		
		gruposCarrera += " ";		
		if (gruposCarrera.equals(" ")) gruposCarrera = "0";
		
		ArchGruposCarrera condiciones = new ArchGruposCarrera();
		condiciones.setCarrera(carreraId);
		condiciones.setGrupos(gruposCarrera);
		if (archGruposCarreraDao.existeReg(carreraId)) {
			if (archGruposCarreraDao.updateReg(condiciones)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}else {
			if (archGruposCarreraDao.insertReg(condiciones)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving.";
			}	
		}		
		return "redirect:/archivo/condicion_carrera/grupos?carrera="+carreraId+"&Mensaje="+mensaje;
	}
	
}
