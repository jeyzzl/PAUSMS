package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAsesor;
import aca.alumno.spring.AlumAsesorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosAlumnoAsesor {
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;

	@Autowired
	MapaPlanDao mapaPlanDao;	
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	AlumAsesorDao alumAsesorDao;
	
	
	@RequestMapping("/datos_alumno/asesor/planes")
	public String datosAlumnoAsesorPlanes(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String matricula 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
        	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
        }
        
        Acceso acceso					= accesoDao.mapeaRegId(codigoPersonal); 
        AlumPersonal alumPersonal		= alumPersonalDao.mapeaRegId(matricula);
        
        // Lista de planes activos del alumno
    	List<AlumPlan> lisPlanes 					= alumPlanDao.lisPlanesAlumno(matricula," ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
        
        HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
        HashMap<String, String> mapaAsesores 		= alumAsesorDao.mapaAsesores(matricula);
        HashMap<String, Maestros> mapaMaestros 		= maestrosDao.mapaMaestros();       
        
        modelo.addAttribute("acceso",acceso);
        modelo.addAttribute("alumPersonal",alumPersonal);
        
        modelo.addAttribute("lisPlanes",lisPlanes);
        modelo.addAttribute("mapaPlanes",mapaPlanes);
        modelo.addAttribute("mapaAsesores",mapaAsesores);
        modelo.addAttribute("mapaMaestros",mapaMaestros);
        
		return "datos_alumno/asesor/planes";
	}
	
	@RequestMapping("/datos_alumno/asesor/editar")
	public String datosAlumnoAsesorEditar(HttpServletRequest request, Model modelo){	
		
		String matricula 		= "0";
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){        	
        	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
        }
        
        AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
        AlumAsesor alumAsesor 		= new AlumAsesor();        
        if (alumAsesorDao.existeReg(matricula, planId)) {
        	alumAsesor			= alumAsesorDao.mapeaRegId(matricula,planId);
        }
        
        // Lista de planes activos del alumno    	        
        List<Maestros> lisMaestros 		= maestrosDao.getListAll(" ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");        
        
        modelo.addAttribute("alumPersonal",alumPersonal);
        modelo.addAttribute("alumAsesor",alumAsesor);
        modelo.addAttribute("lisMaestros",lisMaestros);
        
		return "datos_alumno/asesor/editar";
	}
	
	@RequestMapping("/datos_alumno/asesor/grabar")
	public String datosAlumnoAsesorGrabar(HttpServletRequest request, Model modelo){	
		
		String matricula 		= "0";
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String maestro 			= request.getParameter("Maestro")==null?"0":request.getParameter("Maestro");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if ( sesion!=null ){        	
        	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
        }
        
        AlumAsesor alumAsesor 	= new AlumAsesor();
        alumAsesor.setCodigoPersonal(matricula);
        alumAsesor.setPlanId(planId);
        alumAsesor.setAsesorId(maestro);
        if(!maestro.equals("0")) {
        	if (alumAsesorDao.existeReg(matricula, planId)) {
            	alumAsesorDao.updateReg(alumAsesor);
            }else {
            	alumAsesorDao.insertReg(alumAsesor);
            }
        }       
        
		return "redirect:/datos_alumno/asesor/editar?PlanId="+planId;
	}
	
}