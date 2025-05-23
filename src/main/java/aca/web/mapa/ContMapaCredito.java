package aca.web.mapa;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.plan.spring.MapaAvance;
import aca.plan.spring.MapaAvanceDao;
import aca.plan.spring.MapaCredito;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.PlanCicloDao;

@Controller
public class ContMapaCredito {
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;	
	
	@Autowired	
	private PlanCicloDao planCicloDao;
	
	@Autowired 
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private MapaCreditoDao mapaCreditoDao;
	
	@Autowired	
	private MapaAvanceDao mapaAvanceDao;
	
	@Autowired	
	private CatTipoCursoDao catTipoCursoDao;
	
	
	@RequestMapping("/mapa/creditos/facultad")
	public String mapaCicloFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "mapa/creditos/facultad";
	}
	
	@RequestMapping("/mapa/creditos/listado")
	public String mapaCicloListado(HttpServletRequest request, Model modelo){
		String facultad 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}
		}
		
		List<CatCarrera> lisCarreras					= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes						= mapaPlanDao.getListPlanFac(facultad,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores		= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaCreditos				= mapaCreditoDao.mapaCreditos();
		HashMap<String,String> mapaCiclosRegistrados	= mapaAvanceDao.mapaCiclosRegistrados();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("mapaCiclosRegistrados", mapaCiclosRegistrados);
		
		return "mapa/creditos/listado";
	}	
	
	@RequestMapping("/mapa/creditos/avance")
	public String mapaCreditosAvance(HttpServletRequest request, Model modelo){
		String facultad 				= request.getParameter("facultad"); 
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		String planId                   = request.getParameter("planId");
	
		List<MapaAvance> lista						= mapaAvanceDao.getListPlan(planId,"ORDER BY CICLO"); 		
		HashMap<String,CatTipoCurso> mapaTipos		= catTipoCursoDao.getMapAll("");
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "mapa/creditos/avance";
	}	
	
	@RequestMapping("/mapa/creditos/add")
	public String mapaCreditosAdd(HttpServletRequest request, Model modelo){
		String facultad 			= request.getParameter("facultad"); 
		String planId               = request.getParameter("planId");
		String ciclo                = request.getParameter("ciclo")==null?"1":request.getParameter("ciclo");
		String tipo      	        = request.getParameter("tipo");
		
		MapaAvance mapaAvance			= new MapaAvance();
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		
		if (mapaAvanceDao.existeReg(planId, ciclo, tipo)) {
			mapaAvance = mapaAvanceDao.mapeaRegId(planId, ciclo, tipo);
		}else {
			mapaAvance.setPlanId(planId);
			mapaAvance.setCiclo(ciclo);
		}
		
		List<CatTipoCurso> lisTipos 	= catTipoCursoDao.getListAll("ORDER BY 1");
		List<MapaCredito> lisCiclos 	= mapaCreditoDao.getLista(planId, "ORDER BY CICLO");
		List<MapaCurso> lisMaterias		= mapaCursoDao.getListSemestre(planId, ciclo," ORDER BY CICLO");
		HashMap<String, String> mapaCreditos = mapaCursoDao.mapaCreditosEnCurso(planId, ciclo);
		
		modelo.addAttribute("mapaAvance", mapaAvance);
		modelo.addAttribute("lisCiclos", lisCiclos);
		modelo.addAttribute("lisTipos", lisTipos);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("facultadNombre", facultadNombre);

		
		return "mapa/creditos/add";
	}
	
	@RequestMapping("/mapa/creditos/grabar")
	public String mapaCreditosGrabar(HttpServletRequest request){
		String facultad 			= request.getParameter("facultad"); 
		String planId               = request.getParameter("planId");
		String ciclo                = request.getParameter("ciclo");
		String tipo      	        = request.getParameter("tipo");
		String creditos    	        = request.getParameter("creditos");
		MapaAvance mapaAvance		= new MapaAvance();
		String mensaje 				= "-";
		
		if (mapaAvanceDao.existeReg(planId, ciclo, tipo)) {
			mapaAvance = mapaAvanceDao.mapeaRegId(planId, ciclo, tipo);
			mapaAvance.setCreditos(creditos);
			if (mapaAvanceDao.updateReg(mapaAvance)){
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}else {
			mapaAvance.setPlanId(planId);
			mapaAvance.setCiclo(ciclo);
			mapaAvance.setTipocursoId(tipo);
			mapaAvance.setCreditos(creditos);
			if (mapaAvanceDao.insertReg(mapaAvance)){
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}		
		return "redirect:/mapa/creditos/avance?facultad="+facultad+"&planId="+planId+"&mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/creditos/borrar")
	public String mapaCreditosBorrar(HttpServletRequest request){
		String facultad 			= request.getParameter("facultad"); 
		String planId               = request.getParameter("planId");
		String ciclo                = request.getParameter("ciclo");
		String tipo      	        = request.getParameter("tipo");
		String mensaje 				= "-";
		
		if (mapaAvanceDao.existeReg(planId, ciclo, tipo)) {
			if (mapaAvanceDao.deleteReg(planId, ciclo, tipo)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting";
			}
		}
		
		return "redirect:/mapa/creditos/avance?facultad="+facultad+"&planId="+planId+"&mensaje="+mensaje;
	}
}