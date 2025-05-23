package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchGrupo;
import aca.archivo.spring.ArchGrupoDao;
import aca.archivo.spring.ArchGrupoDocumentoDao;
import aca.archivo.spring.ArchGrupoPlan;
import aca.archivo.spring.ArchGrupoPlanDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContArchivoPlan {
	
	@Autowired
	ArchGrupoPlanDao archGrupoPlanDao;
	
	@Autowired
	ArchGrupoDocumentoDao archGrupoDocumentoDao;
	
	@Autowired
	ArchGrupoDao archGrupoDao;

	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@RequestMapping("/archivo/plan/listado")
	public String archivoPlanListado(HttpServletRequest request, Model modelo){
		
		List<ArchGrupo> lisArchGrupo	= archGrupoDao.listTodos(" ORDER BY GRUPO_NOMBRE");
		HashMap<String,ArchGrupo> mapaArchGrupo = archGrupoDao.mapaArchGrupo();
		
		HashMap<String,String> mapaPlanesPorGrupo 		= archGrupoPlanDao.mapaPlanesPorGrupo();
		HashMap<String,String> mapaDocumentoPorGrupo 	= archGrupoDocumentoDao.mapaArchGrupoDocumentoPorGrupo();
		
		modelo.addAttribute("lisArchGrupo", lisArchGrupo);		
		modelo.addAttribute("mapaArchGrupo", mapaArchGrupo);		
		modelo.addAttribute("mapaPlanesPorGrupo", mapaPlanesPorGrupo);		
		modelo.addAttribute("mapaDocumentoPorGrupo", mapaDocumentoPorGrupo);		
		
		return "archivo/plan/listado";
	}

	@RequestMapping("/archivo/plan/grupo")
	public String archivoPlanGrupo(HttpServletRequest request, Model modelo){
		String grupoId	= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		
		List<ArchGrupoPlan> lisArchGrupoPlan	= archGrupoPlanDao.lisPorGrupoId(grupoId, "");
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll(" ORDER BY NOMBRE_FACULTAD");
		
		HashMap<String,String> mapaPlanesFacultad = archGrupoPlanDao.mapaPlanesFacultad(grupoId);
		
		String nombreGrupo = archGrupoDao.mapeaRegId(grupoId).getGrupoNombre();
		
		int totalPlanes = lisArchGrupoPlan.size();
		
		modelo.addAttribute("lisArchGrupoPlan", lisArchGrupoPlan);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaPlanesFacultad", mapaPlanesFacultad);
		modelo.addAttribute("nombreGrupo", nombreGrupo);
		modelo.addAttribute("totalPlanes", totalPlanes);
		modelo.addAttribute("grupoId", grupoId);
		
		return "archivo/plan/grupo";
	}

	@RequestMapping("/archivo/plan/facultad")
	public String archivoPlanFacultad(HttpServletRequest request, Model modelo){
		String grupoId		= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String facultadId	= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String mensaje		= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String nombreGrupo 	= archGrupoDao.mapeaRegId(grupoId).getGrupoNombre();
		
		CatFacultad facultad 		= catFacultadDao.mapeaRegId(facultadId);
		List<MapaPlan> lisPlanes 	= mapaPlanDao.getListPlanFac(facultadId, "ORDER BY NOMBRE_PLAN");
		HashMap<String,String> mapaPlanesGrupo = archGrupoPlanDao.mapaPlanesGrupo(grupoId);
		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("grupoId", grupoId);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("nombreGrupo", nombreGrupo);
		modelo.addAttribute("mapaPlanesGrupo", mapaPlanesGrupo);
		
		return "archivo/plan/facultad";
	}

	@RequestMapping("/archivo/plan/grabar")
	public String archivoGrupoGrabar(HttpServletRequest request, Model modelo){
		String grupoId 			= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
		String facultadId		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String mensaje 			= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String codigoPersonal	= "";
		String planId			= "";

		ArchGrupoPlan grupoPlan = new ArchGrupoPlan();
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<MapaPlan> lisPlanes = mapaPlanDao.getListPlanFac(facultadId, "");
		
		for(MapaPlan plan : lisPlanes) {
			archGrupoPlanDao.deleteReg(grupoId, plan.getPlanId());
		}
		
		for(MapaPlan plan : lisPlanes) {
			if(request.getParameter(plan.getPlanId()) != null) {
				planId = plan.getPlanId();
				grupoPlan.setGrupoId(grupoId);
				grupoPlan.setPlanId(planId);
				grupoPlan.setUsuario(codigoPersonal);
				
				if(archGrupoPlanDao.insertReg(grupoPlan)) {
					mensaje = "1";	
				}else {
					mensaje = "2";	
				}
			}
		}
		
		return "redirect:/archivo/plan/facultad?GrupoId="+grupoId+"&Mensaje="+mensaje+"&FacultadId="+facultadId;
	}


}
