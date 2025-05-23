package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmModCarrera;
import aca.admision.spring.AdmModCarreraDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

import org.springframework.ui.Model;

@Controller
public class ContAmdlineaCarrera {
	
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;

	@Autowired	
	private AdmModCarreraDao admModCarreraDao;	
	
	
	@RequestMapping("/admlinea/carrera/facultad")
	public String admlineaCarreraFacultad(HttpServletRequest request, Model modelo){
		
		List<CatFacultad> lisFacultades 		= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String, String> mapaDirectores 	= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "admlinea/carrera/facultad";
	}
	
	@RequestMapping("/admlinea/carrera/modalidades")
	public String admlineaCarreraModalidades(HttpServletRequest request, Model modelo){	
		
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String modalidad 		= request.getParameter("Modalidad")==null?"0":request.getParameter("Modalidad");		
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CatModalidad> lisModalidades 		= catModalidadDao.getListAll("WHERE ADMISIBLE='S' ORDER BY MODALIDAD_ID");
		if (modalidad.equals("0") && lisModalidades.size() >= 1) {
			modalidad = lisModalidades.get(0).getModalidadId();
		}
		List<CatCarrera> lisCarreras 			= catCarreraDao.getLista(facultadId, " ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		HashMap<String, String> mapModCarrera = admModCarreraDao.mapaModCarrera(); 
		
		List<MapaPlan> lisPlanes					= mapaPlanDao.getListPlanFacAll(facultadId, "ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID),2,1");
				
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("modalidad", modalidad);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("mapModCarrera", mapModCarrera);
		
		return "admlinea/carrera/modalidades";
	}
	 
	@RequestMapping("/admlinea/carrera/grabar")
	public String admlineaCarreraGrabar(HttpServletRequest request, Model modelo){	
		String facultadId 			= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String modalidad 			= request.getParameter("Modalidad")==null?"0":request.getParameter("Modalidad");
		
		List<CatCarrera> lisCarreras 			= catCarreraDao.getLista(facultadId, " ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		
 		for(CatCarrera carrera : lisCarreras){ 			
 			if(request.getParameter("chk"+carrera.getCarreraId()) != null){
 				AdmModCarrera modCarrera	= new AdmModCarrera(); 				
 				modCarrera.setModalidadId(modalidad);
 				modCarrera.setCarreraId(carrera.getCarreraId());
 				if(admModCarreraDao.existeReg(modalidad, carrera.getCarreraId())==false){
 					admModCarreraDao.insertReg(modCarrera);
 				} 					
 			}else{
 				if(admModCarreraDao.existeReg(modalidad, carrera.getCarreraId())) {
 					admModCarreraDao.deleteReg(modalidad, carrera.getCarreraId());
 				}
 			}
 		}
		
		return "redirect:/admlinea/carrera/modalidades?FacultadId="+facultadId+"&Modalidad="+modalidad;
	}
	
}