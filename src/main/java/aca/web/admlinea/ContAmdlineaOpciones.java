package aca.web.admlinea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmOpcionPlan;
import aca.admision.spring.AdmOpcionPlanDao;
import aca.admision.spring.AdmOpciones;
import aca.admision.spring.AdmOpcionesDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller
public class ContAmdlineaOpciones {	
	
	@Autowired
	AdmOpcionesDao admOpcionesDao;
	
	@Autowired
	AdmOpcionPlanDao admOpcionPlanDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@RequestMapping("/admlinea/opciones/lista")
	public String admlineaOpcionesLista(HttpServletRequest request, Model modelo){
		String mensaje = request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		
		int planesEnAdmision	= mapaPlanDao.getNumPlanesPorEstado("A");
		
		List<AdmOpciones> listaOpciones 		= admOpcionesDao.getAll("");
		HashMap<String,String> mapaTotales		= admOpcionPlanDao.mapaTotalPlanes();
		
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("planesEnAdmision", planesEnAdmision);	
		modelo.addAttribute("listaOpciones", listaOpciones);
		modelo.addAttribute("mapaTotales", mapaTotales);
				
		return "admlinea/opciones/lista";
	}
	
	@RequestMapping("/admlinea/opciones/editar")
	public String admlineaOpcionesEditar(HttpServletRequest request, Model modelo){
		String opcionId = request.getParameter("OpcionId") == null ? "0" : request.getParameter("OpcionId");
		
		AdmOpciones opcion = new AdmOpciones();		
		if(admOpcionesDao.existeReg(opcionId)) {
			opcion = admOpcionesDao.mapeaRegId(opcionId);
		}		
		modelo.addAttribute("opcion", opcion);
		
		return "admlinea/opciones/editar";
	}
	
	@RequestMapping("/admlinea/opciones/grabar")
	public String admlineaOpcionesGrabar(HttpServletRequest request, Model modelo){
		String opcionId = request.getParameter("OpcionId") == null ? "0" : request.getParameter("OpcionId");	
		String nombre	= request.getParameter("Nombre") == null ? "0" : request.getParameter("Nombre");	
		String mensaje	= "0";
		
		AdmOpciones opcion = new AdmOpciones();
		
		if(admOpcionesDao.existeReg(opcionId)) {
			opcion = admOpcionesDao.mapeaRegId(opcionId);
			opcion.setNombre(nombre);
			
			if(admOpcionesDao.updateReg(opcion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			opcion.setOpcionId(admOpcionesDao.maxReg());
			opcion.setNombre(nombre);
			
			if(admOpcionesDao.insertReg(opcion)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/admlinea/opciones/lista?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/opciones/borrarOpcion")
	public String mapaFederalBorrarOpcion(HttpServletRequest request) {
		String periodoId		= request.getParameter("OpcionId")==null?"0":request.getParameter("OpcionId");
		String mensaje			= "-";		

		
		 if(admOpcionesDao.existeReg(periodoId)) {			 
			 if (admOpcionesDao.deleteReg(periodoId)){
				mensaje = "3";
			}else {
				mensaje = "4";
			}
		 }		
		return "redirect:/admlinea/opciones/lista?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/opciones/editarPlanes")
	public String admlineaOpcionesEditarPlanes(HttpServletRequest request, Model modelo){
		String opcionId 	= request.getParameter("OpcionId") == null ? "0" : request.getParameter("OpcionId");	
		String facultadId 	= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");	
		String mensaje		= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");	
		String planes 		= "";			
		
		List<AdmOpcionPlan> lisOpciones = admOpcionPlanDao.listaPorOpcionId(opcionId, "");		
		for(AdmOpcionPlan opcion : lisOpciones) {
			if(planes.equals("")){
				planes = "'"+opcion.getPlanId()+"'";
			}else{
				planes += ",'"+opcion.getPlanId()+"'";						
			}
		}
		
		AdmOpciones opcion = new AdmOpciones();		
		if(admOpcionesDao.existeReg(opcionId)) {
			opcion = admOpcionesDao.mapeaRegId(opcionId);
		}
		
		List<CatFacultad> lisFacultades 	= catFacultadDao.getListAll("ORDER BY NOMBRE_FACULTAD");		
		List<MapaPlan> lisPlanes	 		= new ArrayList<MapaPlan>();
		if (facultadId.equals("0")) {
			lisPlanes = mapaPlanDao.listPlanes("'A'", "ORDER BY (ENOC.FACULTAD(CARRERA_ID)), ENOC.CARRERA_NIVEL(CARRERA_ID), PLAN_ID");
		}else {
			lisPlanes = mapaPlanDao.getListPlanFacAdmision(facultadId, "ORDER BY (ENOC.FACULTAD(CARRERA_ID)), ENOC.CARRERA_NIVEL(CARRERA_ID), PLAN_ID");
		}
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("opcion", opcion);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("planes", planes);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		
		return "admlinea/opciones/editarPlanes";
	}
	
	@RequestMapping("/admlinea/opciones/grabarPlanes")
	public String admlineaOpcionesGrabarPlanes(HttpServletRequest request, Model modelo){
		String opcionId 	= request.getParameter("OpcionId") == null ? "0" : request.getParameter("OpcionId");	
		String facultadId 	= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String mensaje		= "0";
		
		List<MapaPlan> lisPlanes	 		= new ArrayList<MapaPlan>();
		if (facultadId.equals("0")) {
			lisPlanes = mapaPlanDao.listPlanes("'A','V'", "ORDER BY (FACULTAD(CARRERA_ID))");
		}else {
			lisPlanes = mapaPlanDao.getListPlanFac(facultadId, "ORDER BY (FACULTAD(CARRERA_ID))");
		}
		
		for(MapaPlan plan : lisPlanes) {
			AdmOpcionPlan opcionPlan = new AdmOpcionPlan();
			opcionPlan.setOpcionId(opcionId);
			String planTemp = request.getParameter(plan.getPlanId())==null?"X":request.getParameter(plan.getPlanId());
			if(planTemp.equals(plan.getPlanId())){
				if (admOpcionPlanDao.existeReg(opcionId,plan.getPlanId())==false){		
					opcionPlan.setPlanId(request.getParameter(plan.getPlanId()));
					if(admOpcionPlanDao.insertReg(opcionPlan)) {
						mensaje = "1";
					}else {
						mensaje = "2";
					}
				}	
			}else {
				if (admOpcionPlanDao.existeReg(opcionId,plan.getPlanId())==true){
					if(admOpcionPlanDao.deleteReg(opcionId, plan.getPlanId())){
						mensaje = "1";
					}else {
						mensaje = "2";
					}
				}
			}
		}
		
		return "redirect:/admlinea/opciones/editarPlanes?OpcionId="+opcionId+"&Mensaje="+mensaje+"&FacultadId="+facultadId;
	}
	
}
