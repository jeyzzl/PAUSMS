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
import aca.plan.spring.MapaCredito;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;
import aca.vista.spring.PlanCicloDao;

@Controller
public class ContMapaCiclo {
	
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
	private MapaCreditoDao mapaCreditoDao; 
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@RequestMapping("/mapa/ciclo/facultad")
	public String mapaCicloFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		HashMap<String,String> mapaCiclosReg		= mapaCursoDao.mapaCiclosRegPorFacultad();
		HashMap<String,String> mapaCiclosPen		= mapaCursoDao.mapaCiclosPenPorFacultad();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		modelo.addAttribute("mapaCiclosReg", mapaCiclosReg);
		modelo.addAttribute("mapaCiclosPen", mapaCiclosPen);
		
		return "mapa/ciclo/facultad";
	}
	
	@RequestMapping("/mapa/ciclo/listado")
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
		
		List<CatCarrera> lisCarreras				= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes					= mapaPlanDao.getListPlanFac(facultad,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores	= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaCreditos			= mapaCreditoDao.mapaCreditos();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		
		return "mapa/ciclo/listado";
	}
	
	@RequestMapping("/mapa/ciclo/credito")
	public String mapaCicloCredito(HttpServletRequest request, Model modelo){	
		
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		int numCiclos 			=  planCicloDao.getNumCiclos(planId);
		
		String carreraId		= mapaPlanDao.getCarreraId(planId);
		String carreraNombre	= catCarreraDao.getNombreCarrera(carreraId);
		
		HashMap<String,MapaCredito> mapaCreditos = mapaCreditoDao.mapaCredito(planId);
		HashMap<String,String> mapaCursos		 = mapaCursoDao.mapaCursosPorCiclo(planId);
				
		modelo.addAttribute("numCiclos", numCiclos);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("mapaCursos", mapaCursos);
		
		return "mapa/ciclo/credito";
	}
	
	@RequestMapping("/mapa/ciclo/grabar")
	public String mapaCicloGrabar(HttpServletRequest request, Model modelo){	
		String planId				= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String facultad				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String mensaje 				= "-";
		int numCiclos 				=  planCicloDao.getNumCiclos(planId);
		MapaCredito mapaCredito		= new MapaCredito();
		
		for(int i=1; i<=numCiclos; i++) {
			String titulo = request.getParameter("titulo"+String.valueOf(i))==null?"-":request.getParameter("titulo"+String.valueOf(i));
			String estado = request.getParameter("Estado"+String.valueOf(i))==null?"-":request.getParameter("Estado"+String.valueOf(i));
			if (!titulo.equals("-")&& titulo.length()>0){
				if (mapaCreditoDao.existeReg(planId, String.valueOf(i))) {
					mapaCredito = mapaCreditoDao.mapeaRegId(planId, String.valueOf(i));
					mapaCredito.setTitulo(titulo);
					mapaCredito.setEstado(estado);
					if (mapaCreditoDao.updateReg(mapaCredito)){
						mensaje = "Saved";
					}else{
						mensaje = "Error saving!";
					}
					
				}else{
					mapaCredito.setPlanId(planId);
					mapaCredito.setCiclo(String.valueOf(i));
					mapaCredito.setCreditos("0");
					mapaCredito.setOptativos("0");
					mapaCredito.setTitulo(titulo);
					mapaCredito.setEstado(estado);
					if (mapaCreditoDao.insertReg(mapaCredito)){
						mensaje = "Saved!";
					}else{
						mensaje = "Error saving!";
					}					
				}				
			}
		}
		return "redirect:/mapa/ciclo/credito?planId="+planId+"&facultad="+facultad+"&mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/ciclo/borrar")
	public String mapaCicloBorrar(HttpServletRequest request, Model modelo){	
		
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String ciclo				= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");
		String facultad				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String mensaje 				= "-";
		
		if (mapaCreditoDao.existeReg(planId, ciclo)) {			
			if (mapaCreditoDao.deleteReg(planId, ciclo)) {
				mensaje = "Deleted";
			}else {
				mensaje = "Error deleting!";
			}
		}	
		
		return "redirect:/mapa/ciclo/credito?planId="+planId+"&facultad="+facultad;
	}
	
}