package aca.web.cargagrupo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.carga.spring.CargaGrupoDao;
import aca.carga.spring.CargaPermiso;
import aca.carga.spring.CargaPermisoDao;
import aca.carga.spring.CargaPermisoPlan;
import aca.carga.spring.CargaPermisoPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContCargaGrupoPermiso {
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CargaDao cargaDao;		
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;	
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CargaGrupoDao cargaGrupoDao;
	
	@Autowired
	CargaGrupoCursoDao cargaGrupoCursoDao;	
	
	@Autowired
	CargaPermisoPlanDao cargaPermisoPlanDao;	
	
	
	@RequestMapping("/carga_grupo/permiso/permiso")
	public String cargaGrupoPermisoPermiso(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String cargaNombre		= cargaDao.getNombreCarga(cargaId);
		String facultadNombre	= catFacultadDao.getNombreCorto(facultadId);
		
		List<MapaPlan> lisPlanes 						= mapaPlanDao.lisPorFacultadyEstado(facultadId, "'A','V'"," ORDER BY ENOC.NIVEL_PLAN(PLAN_ID), CARRERA_SE, PLAN_ID");		
		HashMap<String, CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad(" ORDER BY FACULTAD_ID");
		HashMap<String, CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaPermisos 			= cargaPermisoPlanDao.mapaPermisos(cargaId);
		HashMap<String, String> mapaMaterias		    = cargaGrupoCursoDao.mapaMateriasPorPlan(cargaId);
		
		modelo.addAttribute("cargaNombre", cargaNombre);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisPlanes", lisPlanes);
		
		modelo.addAttribute("mapaPermisos", mapaPermisos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaMaterias", mapaMaterias);		
		
		return "carga_grupo/permiso/permiso";
	}
	
	@RequestMapping("/carga_grupo/permiso/grabar")
	public String cargaGrupoPermisoGrabar(HttpServletRequest request){
		
		String usuario		= "0";
		String facultadId 	= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String mensaje 		= "-";
		int grabados		= 0;
		int borrados 		= 0;
		
		HashMap<String, String> mapaMaterias		    = cargaGrupoCursoDao.mapaMateriasPorPlan(cargaId);
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {			
			usuario 	= (String)sesion.getAttribute("codigoPersonal");
		}	
		
		List<MapaPlan> lisPlanes 						= mapaPlanDao.lisPorFacultadyEstado(facultadId, "'A','V'"," ORDER BY ENOC.NIVEL_PLAN(PLAN_ID), CARRERA_SE, PLAN_ID");
		for (MapaPlan plan : lisPlanes) {			
			if (request.getParameter(plan.getPlanId())==null) {
				// Quitar carrera 
				if (cargaPermisoPlanDao.existeReg(cargaId, plan.getPlanId()) && mapaMaterias.containsKey(plan.getPlanId()) == false ){
					if (cargaPermisoPlanDao.deleteReg(cargaId, plan.getPlanId()) ){
						borrados++;
					}
				}else if (mapaMaterias.containsKey(plan.getPlanId())) {
					mensaje = "Unable to delete plans with assigned courses!"; 
				}
			}else {
				// Agregar carrera
				if (cargaPermisoPlanDao.existeReg(cargaId, plan.getPlanId())==false){
					CargaPermisoPlan cargaPermiso = new CargaPermisoPlan();
					cargaPermiso.setCargaId(cargaId);
					cargaPermiso.setPlanId(plan.getPlanId());
					cargaPermiso.setCarreraId(plan.getCarreraId());
					cargaPermiso.setUsuario(usuario);
					cargaPermiso.setRecuperacion("N");					
					if (cargaPermisoPlanDao.insertReg(cargaPermiso)) {
						grabados++;
					}
				}
			}				
		}
		mensaje = mensaje + " Saved: "+grabados+" Deleted: "+borrados;
		
		return "redirect:/carga_grupo/permiso/permiso?CargaId="+cargaId+"&FacultadId="+facultadId+"&Mensaje="+mensaje;
	}	
	
	@RequestMapping("/carga_grupo/permiso/facultad")
	public String cargaGrupoPermisoFacultad(HttpServletRequest request, Model model){
		
		List<CatFacultad> lisFacultades 	= (List<CatFacultad>)catFacultadDao.getListAll("ORDER BY NOMBRE_FACULTAD");
		List<CatPeriodo> lisPeriodos 		= catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");

		if(periodoId.equals("0")) {
			HttpSession sesion = ((HttpServletRequest)request).getSession();
			periodoId 	= (String)sesion.getAttribute("periodo");
		}
		

		
		List<Carga> lisCargas = cargaDao.listPeriodoAndEstado(periodoId, "1", " ORDER BY CARGA_ID");
		
		String cargaId 		= request.getParameter("CargaId") == null ? lisCargas.get(0).getCargaId() : request.getParameter("CargaId");
		
		HashMap<String, String> mapaCarrerasConPermico 	= catCarreraDao.mapaCarrerasConPermico(cargaId);
		HashMap<String, String> mapaPlanesPorCarrera	= cargaPermisoPlanDao.mapaPlanesPorCarrera(cargaId);
		HashMap<String, String> mapaMaterias 			= cargaGrupoDao.mapaMateriasPorFacultad(cargaId);
		
		model.addAttribute("periodoId", periodoId);
		model.addAttribute("cargaId", cargaId);
		model.addAttribute("lisPeriodos", lisPeriodos);
		model.addAttribute("lisCargas", lisCargas);
		model.addAttribute("lisFacultades", lisFacultades);
		model.addAttribute("mapaCarrerasConPermico", mapaCarrerasConPermico);
		model.addAttribute("mapaPlanesPorCarrera", mapaPlanesPorCarrera);
		model.addAttribute("mapaMaterias", mapaMaterias);
		
		return "carga_grupo/permiso/facultad";
	}
	
}