package aca.web.cargagrupo;

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
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCargaGrupoUnid {	
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;	
	
	
	@RequestMapping("/carga_grupo/unid/facultad")
	public String mapaMateriaFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		return "carga_grupo/unid/facultad";
	}
	
	@RequestMapping("/carga_grupo/unid/listado")
	public String mapaMateriaListado(HttpServletRequest request, Model modelo){
		
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
		HashMap<String,String> mapaCursosPorPlan	= mapaCursoDao.mapaCursosPorPlan();
		HashMap<String,String> mapaCursosEnLinea	= mapaCursoDao.mapaCursosEnLinea();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		modelo.addAttribute("mapaCursosEnLinea", mapaCursosEnLinea);
		
		return "carga_grupo/unid/listado";
	}
	
	@RequestMapping("/carga_grupo/unid/show_plan")
	public String cargaGrupoUnidShowPlan(HttpServletRequest request, Model modelo){
		
		String facultad			= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
	  	String carreraId		= mapaPlanDao.getCarreraId(planId);
	  	String carreraNombre	= catCarreraDao.getNombreCarrera(carreraId);
	  	
	  	List<MapaCurso> lisCursos = mapaCursoDao.getLista(planId, "ORDER BY CICLO, NOMBRE_CURSO");
	  	
	  	modelo.addAttribute("facultad", facultad);
	  	modelo.addAttribute("planId", planId);
	  	modelo.addAttribute("carreraNombre", carreraNombre);
	  	modelo.addAttribute("lisCursos", lisCursos);
		
		return "carga_grupo/unid/show_plan";
	}	
	
	@RequestMapping("/carga_grupo/unid/update")
	public String cargaGrupoUnidUpdate(HttpServletRequest request){
		
		String facultad			= request.getParameter("facultad")==null?"":request.getParameter("facultad");
		String planId			= request.getParameter("planId")==null?"":request.getParameter("planId");
		String s_contador		= request.getParameter("f_contador")==null?"":request.getParameter("f_contador");
		String s_plan_id		= request.getParameter("f_plan_id")==null?"":request.getParameter("f_plan_id");
		String s_nombreCurso	= request.getParameter("f_nombreCurso")==null?"":request.getParameter("f_nombreCurso");
		String s_tipoCurso		= request.getParameter("f_tipoCurso")==null?"":request.getParameter("f_tipoCurso");
		String s_ciclo			= request.getParameter("f_ciclo")==null?"":request.getParameter("f_ciclo");
		
		int contador				= Integer.parseInt(s_contador);
		String s_chekonline			= "";
		String s_curso_id			= "";
		int    updates				= 0;
		
		for(int i=1; i<=contador; i++){
			//esta linea me permite verificar si el valor recibido ha sido chekado por el alumno
			s_chekonline	= request.getParameter("f_chekonline"+i); 
				if(s_chekonline==null){ 
					s_chekonline="N"; 
				}
			s_curso_id		= request.getParameter("f_curso_id"+i);
			
			//System.out.println("Datos:"+s_chekonline+":"+s_curso_id);
			MapaCurso mapa = mapaCursoDao.mapeaRegId(s_curso_id);
			mapa.setOnLine(s_chekonline);
	        if(mapaCursoDao.existeReg(s_curso_id)){
	        	if(mapaCursoDao.updateReg(mapa)){
	        		//System.out.println("Modificado: "+s_curso_id);
	        	}else{
	        		//System.out.println("No Cambio: "+s_curso_id);
	        	}
	        }else{
	        	//System.out.println("No existe: "+s_curso_id);
	        }             
	        
			updates++;		
		}
		
		return "redirect:/carga_grupo/unid/show_plan?facultad="+facultad+"&planId="+planId;
	}
	
}