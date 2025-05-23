package aca.web.mapa;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoDao;
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
public class ContMapaAnalisis {
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;	
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private MapaCreditoDao mapaCreditoDao;
	
	@Autowired	
	private MapaAvanceDao mapaAvanceDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private CargaGrupoDao cargaGrupoDao;
	
	@Autowired	
	private CatTipoCursoDao catTipoCursoDao;
	
	
	@RequestMapping("/mapa/analisis/facultad")
	public String mapaCicloFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "mapa/analisis/facultad";
	}
	
	@RequestMapping("/mapa/analisis/listado")
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
		HashMap<String,String> mapaPracticasCarreras	= mapaCursoDao.mapaPracticasEnCarreras();
		HashMap<String,String> mapaCursosPorPlan		= mapaCursoDao.mapaCursosPorPlan();

		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("mapaPracticasCarreras", mapaPracticasCarreras);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCreditos", mapaCreditos);
		modelo.addAttribute("mapaCiclosRegistrados", mapaCiclosRegistrados);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		
		return "mapa/analisis/listado";
	}
	
	@RequestMapping("/mapa/analisis/materias")
	public String mapaAnalisisMaterias(HttpServletRequest request, Model modelo){	
		
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String facultad			= request.getParameter("facultad")==null?"0":request.getParameter("facultad");	
		String cargaId 			= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
		
		List<MapaCurso> lisCursos					= mapaCursoDao.getLista(planId, " ORDER BY 4,3");
		List<Carga> lisCargas 						= cargaDao.getListPlan(planId, "ORDER BY 1 DESC");
		HashMap<String, MapaCurso> mapaCursos		= mapaCursoDao.getMapaCursos(planId, "");
		HashMap<String, CatTipoCurso> mapaTipos		= catTipoCursoDao.getMapAll("");		
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			
			if(cargaId.equals("0")){
				cargaId = (String)sesion.getAttribute("cargaAnalisisPlan")==null?"0":(String)sesion.getAttribute("cargaAnalisisPlan");
			}	
			if(cargaId.equals("0")){				
				boolean encontro = false;
				for(Carga carga : lisCargas){
					if(carga.getCargaId().equals(cargaId)){
						encontro = true;
					}
				}
				if(encontro == false && lisCargas.size() > 0){
					cargaId = lisCargas.get(0).getCargaId();
					sesion.setAttribute("cargaAnalisisPlan", cargaId);
				}			
			}			
		}
		
		HashMap<String,String> mapaMaestros			= maestrosDao.mapaMaestroEnCarga(cargaId, "NOMBRE");
		List<String> lisMaestros  					= cargaGrupoDao.getListMaestros(cargaId, planId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaestros", lisMaestros);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		
		return "mapa/analisis/materias";
	}
	
}