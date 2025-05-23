package aca.web.admlinea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmIngreso;
import aca.admision.spring.AdmIngresoDao;
import aca.admision.spring.AdmIngresoPlan;
import aca.admision.spring.AdmIngresoPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContAmdlineaPlan {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	 AdmIngresoDao admIngresoDao;
	
	@Autowired	
	 AdmIngresoPlanDao admIngresoPlanDao;
	
	@Autowired	
	 CatModalidadDao catModalidadDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;	
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	
	@RequestMapping("/admlinea/plan/ingreso")
	public String admLineaPlanIngreso(HttpServletRequest request, Model modelo){
	
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		
		List<AdmIngreso> lisPeriodos 		= admIngresoDao.lisTodos(" ORDER BY SALOMON.ADM_INGRESO.FECHA ");
		List<CatModalidad> lisModalidades 	= catModalidadDao.getListAll(" ORDER BY ENLINEA, MODALIDAD_ID");
		if (periodoId.equals("0") && lisPeriodos.size()>= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		HashMap<String,String> mapaPlanes 	= admIngresoPlanDao.mapaPlanes(periodoId);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisModalidades", lisModalidades);		
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "admlinea/plan/ingreso";
	}
	
	@RequestMapping("/admlinea/plan/planes")
	public String admLineaPlanPlanes(HttpServletRequest request, Model modelo){
	
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String modalidadId 		= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String nomPeriodo 		= "";
		String nomModalidad		= "";
		String nomFacultad		= "";
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll(" ORDER BY NOMBRE_FACULTAD");
		List<MapaPlan> lisPlanes 					= new ArrayList<MapaPlan>(); 
		if (facultadId.equals("0")) {
			lisPlanes 	= mapaPlanDao.listPlanes("'A','V','I'", " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, PLAN_ID");
		}else{
			lisPlanes 	= mapaPlanDao.getListPlanFac(facultadId, " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, PLAN_ID");
		}
		HashMap<String,String> mapaPlanes 			= admIngresoPlanDao.mapaPlanes(periodoId,modalidadId);
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");
		
		nomPeriodo	 = admIngresoDao.getNombre(periodoId);
		nomModalidad =	catModalidadDao.getNombreModalidad(modalidadId);
		nomFacultad	 = catFacultadDao.getNombreFacultad(facultadId);
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("nomPeriodo", nomPeriodo);
		modelo.addAttribute("nomModalidad", nomModalidad);
		modelo.addAttribute("nomFacultad", nomFacultad);
		
		return "admlinea/plan/planes";
	}
	@RequestMapping("admlinea/plan/grabar")
	public String admlineaPlanGrabar(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String modalidadId 	= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");		
		String mensaje 		= "-";		
		
		List<MapaPlan> lisPlanes 					= new ArrayList<MapaPlan>(); 
		if (facultadId.equals("0")) {
			lisPlanes 	= mapaPlanDao.listPlanes("'A','V','I'", " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, PLAN_ID");
		}else{
			lisPlanes 	= mapaPlanDao.getListPlanFac(facultadId, " ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, PLAN_ID");
		}		
		int grabar = 0;
		int borrar = 0;
		
		for(MapaPlan plan : lisPlanes) {
			
			String planId	= request.getParameter("PlanId"+plan.getPlanId())==null?"0":request.getParameter("PlanId"+plan.getPlanId());
			if(!planId.equals("0")){
				AdmIngresoPlan ingresoPlan = new AdmIngresoPlan();
	    		if(!admIngresoPlanDao.existeReg(periodoId, modalidadId, planId)) {
	    			ingresoPlan.setPeriodoId(periodoId);
			        ingresoPlan.setModalidadId(modalidadId);
			        ingresoPlan.setPlanId(planId);
			        if(admIngresoPlanDao.insertReg(ingresoPlan)) {
			         grabar++; 
			        }			            
	    		}
			}else{
				if(admIngresoPlanDao.existeReg(periodoId, modalidadId, plan.getPlanId())){		            
		            if(admIngresoPlanDao.deleteReg(periodoId, modalidadId, plan.getPlanId())){
		                borrar++; 
		            }		            
				}
			}
		}
		mensaje = "Saved: "+grabar+" Deleted:"+borrar;
		
		return "redirect:/admlinea/plan/planes?PeriodoId="+periodoId+"&ModalidadId="+modalidadId+"&FacultadId="+facultadId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/admlinea/plan/periodos")
	public String admLineaPlanPeriodos(HttpServletRequest request, Model modelo){
		
		List<AdmIngreso> lisPeriodos 	   = admIngresoDao.lisTodos(" ORDER BY SALOMON.ADM_INGRESO.FECHA ");
		HashMap<String,String> mapCursos   = admIngresoPlanDao.mapaCursosPorPeriodo();
		
		modelo.addAttribute("mapCursos", mapCursos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		
		return "admlinea/plan/periodos";
	}
	@RequestMapping("/admlinea/plan/editaPeriodos")
	public String admLineaPlanEditaPeriodos(HttpServletRequest request, Model modelo){
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= "-";
		
		AdmIngreso periodo = new AdmIngreso();
		  
		if (admIngresoDao.existeReg(periodoId)) {
			periodo = admIngresoDao.mapeaRegId(periodoId);
		}	
		
		modelo.addAttribute("periodo", periodo);	
		modelo.addAttribute("mensaje", mensaje);
		
		return "admlinea/plan/editaPeriodos";

	}
	@PostMapping("/admlinea/plan/grabarPeriodo")	
	public String mapaFederalGrabarCurso(HttpServletRequest request, Model modelo){
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String periodoNombre	= request.getParameter("PeriodoNombre")==null?"0": request.getParameter("PeriodoNombre");
		String estado			= request.getParameter("Estado")==null?"0": request.getParameter("Estado");
		String fecha			= request.getParameter("Fecha")==null?"-": request.getParameter("Fecha");
		String mensaje		= "-";
		
		AdmIngreso periodo = new AdmIngreso();
		
		periodo.setPeriodoId(periodoId);
		periodo.setPeriodoNombre(periodoNombre);
		periodo.setEstado(estado);
		periodo.setFecha(fecha);		
		mensaje =  "Info Updated!";				
		if (admIngresoDao.existeReg(periodoId)) {			
			// Modificar
			if (admIngresoDao.updateReg(periodo)) {
				mensaje = "Info Updated!";
			} else {
				mensaje = "A problem occured upon registering information!";
			}			
		} else {
			// Insertar
			periodoId 	= admIngresoDao.maximoReg();
			periodo.setPeriodoId(periodoId);
			if (admIngresoDao.insertReg(periodo)){
				mensaje = "Info Updated!";
			} else {
				mensaje = "A problem occured upon registering information!";
			}
		}
		
		return "redirect:/admlinea/plan/editaPeriodos?PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	@RequestMapping("/admlinea/plan/deletePeriodo")
	public String mapaFederalDeletePlan(HttpServletRequest request) {
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= "-";		

		
		 if(admIngresoDao.existeReg(periodoId)) {			 
			 if (admIngresoDao.deleteReg(periodoId)){
				mensaje = "3";
			}else {
				mensaje = "4";
			}
		 }		
		return "redirect:/admlinea/plan/periodos?Mensaje="+mensaje;
	}
}