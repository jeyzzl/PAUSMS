package aca.web.mapa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.area.spring.Area;
import aca.area.spring.AreaDao;
import aca.carga.spring.CargaGrupoCursoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatGradePoint;
import aca.catalogo.spring.CatGradePointDao;
import aca.catalogo.spring.CatTipoCurso;
import aca.catalogo.spring.CatTipoCursoDao;
import aca.plan.MapaCursoElec;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoElectiva;
import aca.plan.spring.MapaCursoElectivaDao;
import aca.plan.spring.MapaCursoPre;
import aca.plan.spring.MapaCursoPreDao;
import aca.plan.spring.MapaNuevoCurso;
import aca.plan.spring.MapaNuevoCursoDao;
import aca.plan.spring.MapaNuevoPlan;
import aca.plan.spring.MapaNuevoPlanDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMapaMateria {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private AreaDao areaDao;
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;

	@Autowired	
	private MapaNuevoCursoDao mapaNuevoCursoDao;
	
	@Autowired	
	private MapaCursoPreDao mapaCursoPreDao;
	
	@Autowired	
	private MapaNuevoPlanDao mapaNuevoPlanDao;
	
	@Autowired	
	private CatTipoCursoDao catTipoCursoDao;
	
	@Autowired	
	private CargaGrupoCursoDao cargaGrupoCursoDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaCursoElectivaDao mapaCursoElectivaDao;	
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/mapa/materia/facultad")
	public String mapaMateriaFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		HashMap<String,String> mapaInscritas		= mapaCursoDao.mapaInscritasPorFacultad();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		modelo.addAttribute("mapaInscritas", mapaInscritas);
		
		return "mapa/materia/facultad";
	}
	
	@RequestMapping("/mapa/materia/listado")
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
		
		List<CatCarrera> lisCarreras					= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes						= mapaPlanDao.getListPlanFac(facultad,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores		= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaCursosPorPlan		= mapaCursoDao.mapaCursosPorPlan();
		HashMap<String,String> mapaPracticas			= mapaCursoDao.mapaPlanEnPracticas();
		HashMap<String,String> mapaPracticasCarreras	= mapaCursoDao.mapaPracticasEnCarreras();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		modelo.addAttribute("mapaPracticas", mapaPracticas);
		modelo.addAttribute("mapaPracticasCarreras", mapaPracticasCarreras);
		
		return "mapa/materia/listado";
	}
	
	@RequestMapping("/mapa/materia/materia")
	public String mapaMateriaMateria(HttpServletRequest request, Model modelo){		
		
		String planId 				= request.getParameter("planId")==null?"0":request.getParameter("planId");	
		String mensaje 				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String planOrigen 			= mapaPlanDao.getPlanDeOrigen(planId);
		
		String nombrePlan 			= mapaPlanDao.getNombrePlan(planId);
		
		List<MapaCurso> lisCursos					= mapaCursoDao.getLista(planId,"ORDER BY CICLO, ORDEN, NOMBRE_CURSO");
		List<MapaCursoPre> lisPrerrequisitos		= mapaCursoPreDao.getLista(planId," ORDER BY 1,2");		
				
		HashMap<String, MapaCurso> mapaCursos			= mapaCursoDao.getMapaCursos(planId, "");
		HashMap<String,CatTipoCurso> mapaTipos			= catTipoCursoDao.getMapAll("");
		HashMap<String,String> mapaUsados				= cargaGrupoCursoDao.mapCursosUsados(planId);
		HashMap<String,String> mapaCursosPorPlan		= mapaCursoDao.mapaCursosPorPlan();	
		
		modelo.addAttribute("nombrePlan", nombrePlan);
		modelo.addAttribute("planOrigen", planOrigen);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisPrerrequisitos", lisPrerrequisitos);
		modelo.addAttribute("mapaCursos", mapaCursos);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("mapaUsados", mapaUsados);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		modelo.addAttribute("tieneMaterias", mapaCursoDao.tieneMaterias(planId));		
		modelo.addAttribute("mensaje", mensaje);		
		
		return "mapa/materia/materia";
	}
	
	@RequestMapping("/mapa/materia/agregarCursoNuevo")
	public String mapaMateriaAgregarCursoNuevo(HttpServletRequest request, Model modelo){		
		
		String cursoId 		= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String planId 		= request.getParameter("planId")==null?"0":request.getParameter("planId");
		String cursoNuevo 	= request.getParameter("CursoNuevo")==null?"0":request.getParameter("CursoNuevo");		
		String planOrigen 			= "-";
		
		if (mapaPlanDao.existeReg(planId)) {
			planOrigen = mapaPlanDao.mapeaRegId(planId).getPlanOrigen();
		}
		
		MapaCurso mapaCurso = new MapaCurso();
		
		if(mapaCursoDao.existeReg(cursoId)) {
			mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		}

		if(!cursoNuevo.equals("0")) {
			mapaCurso.setCursoNuevo(cursoNuevo);
			mapaCursoDao.updateReg(mapaCurso);
		}
		
		List<MapaNuevoCurso> listaSinCursoNuevo = mapaNuevoCursoDao.listaSinCursoNuevo(planId, planOrigen, "ORDER BY NOMBRE");		
		
		modelo.addAttribute("planOrigen", planOrigen);
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("listaSinCursoNuevo", listaSinCursoNuevo);
		
		return "mapa/materia/agregarCursoNuevo";
	}
	
	@RequestMapping("/mapa/materia/eliminarCursoNuevo")
	public String mapaMateriaEliminarCursoNuevo(HttpServletRequest request, Model modelo){		
		String cursoId 		= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String planId 		= request.getParameter("planId")==null?"0":request.getParameter("planId");
		
		MapaCurso mapaCurso = new MapaCurso();
		
		if(mapaCursoDao.existeReg(cursoId)) {
			mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
			mapaCurso.setCursoNuevo("0");
			mapaCursoDao.updateReg(mapaCurso);
		}
		
		return "redirect:/mapa/materia/materia?planId="+planId;
	}

	@RequestMapping("/mapa/materia/traspasar")
	public String mapaMateriaTraspasar(HttpServletRequest request, Model modelo){
		String planId = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		mapaCursoDao.traspasarMaterias(planId);		
		return "redirect:/mapa/materia/materia?planId="+planId;
	}
	
	@RequestMapping("/mapa/materia/accion")
	public String mapaMateriaAccion(HttpServletRequest request, Model modelo){
		
		String planId 			= request.getParameter("planId")==null?"0":request.getParameter("planId");	
		String cursoId 			= request.getParameter("cursoId")==null?"0":request.getParameter("cursoId");
		String planNombre 		= mapaPlanDao.getNombrePlan(planId);
		boolean existe 			= false;		
		MapaCurso mapaCurso		= new MapaCurso();
		
		if (mapaCursoDao.existeReg(cursoId)) {
			existe 		= true;
			mapaCurso 	= mapaCursoDao.mapeaRegId(cursoId);
		}		
		
		List<Area> listaArea 					= areaDao.lisAll();
		List<CatTipoCurso> listaCurso	 		= catTipoCursoDao.getListAll("ORDER BY TIPOCURSO_ID");		
		
		// Mapa de los planes
		HashMap<String, MapaNuevoPlan> mapaPlan = mapaNuevoPlanDao.mapaPlan();
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("existe", existe);
		modelo.addAttribute("planNombre", planNombre);
						
		modelo.addAttribute("listaArea", listaArea);
		modelo.addAttribute("listaCurso", listaCurso);		
		modelo.addAttribute("mapaCurso", mapaCurso);
		modelo.addAttribute("mapaPlan", mapaPlan);
		
		return "mapa/materia/accion";
	}	
	
	@RequestMapping("/mapa/materia/grabarAccion")
	public String mapaMateriaGrabarAccion(HttpServletRequest request){
		
		String planId 			= request.getParameter("planId")==null?"-":request.getParameter("planId");	
		String cursoId 			= request.getParameter("cursoId")==null?"-":request.getParameter("cursoId");
		String enLinea			= request.getParameter("EnLinea")==null?"N":request.getParameter("EnLinea");
		String clave 			= request.getParameter("Clave")==null?"-":request.getParameter("Clave");
		String nombre			= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String ciclo			= request.getParameter("Ciclo")==null?"-":request.getParameter("Ciclo");
		String creditos			= request.getParameter("Creditos")==null?"-":request.getParameter("Creditos");
		String hT				= request.getParameter("HT")==null?"-":request.getParameter("HT");
		String hP				= request.getParameter("HP")==null?"-":request.getParameter("HP");
		String hI				= request.getParameter("HI")==null?"-":request.getParameter("HI");
		String notaAprobatoria	= request.getParameter("NotaAprobatoria")==null?"-":request.getParameter("NotaAprobatoria");
		String estado			= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String tipoCursoId		= request.getParameter("TipoCursoId")==null?"-":request.getParameter("TipoCursoId");
		String obligatorio		= request.getParameter("obligatorio")==null?"-":request.getParameter("obligatorio");
		String completo			= request.getParameter("completo")==null?"-":request.getParameter("completo");
		String hH				= request.getParameter("HH")==null?"-":request.getParameter("HH");
		String hFD				= request.getParameter("HFD")==null?"-":request.getParameter("HFD");
		String hSS				= request.getParameter("HSS")==null?"-":request.getParameter("HSS");
		String hAS				= request.getParameter("HAS")==null?"-":request.getParameter("HAS");
		String horario			= request.getParameter("Horario")==null?"S":request.getParameter("Horario");
		String salon			= request.getParameter("Salon")==null?"S":request.getParameter("Salon");
		String area				= request.getParameter("Area")==null?"-":request.getParameter("Area");
		String laboratorio		= request.getParameter("Laboratorio")==null?"N":request.getParameter("Laboratorio");
		String orden			= request.getParameter("Orden")==null?"-":request.getParameter("Orden");
		String resultado		= "-";
		
		MapaCurso mapaCurso		= new MapaCurso();
		if (mapaCursoDao.existeReg(cursoId)) {
			mapaCurso = mapaCursoDao.mapeaRegId(cursoId);
		}
		
		mapaCurso.setPlanId(planId);
		mapaCurso.setCursoId(cursoId);
		mapaCurso.setNombreCurso(nombre);
		mapaCurso.setCiclo(ciclo);
		mapaCurso.setCreditos(creditos);
		mapaCurso.setHt(hT);
		mapaCurso.setHp(hP);
		mapaCurso.setHi(hI);
		mapaCurso.setNotaAprobatoria(notaAprobatoria);
		mapaCurso.setEstado(estado);
		mapaCurso.setTipoCursoId(tipoCursoId);
		mapaCurso.setOnLine(enLinea);
		mapaCurso.setUnid(enLinea);		
		mapaCurso.setCursoClave(clave);
		mapaCurso.setObligatorio(obligatorio);
		mapaCurso.setCompleto(completo);
		mapaCurso.setHh(hH);
		mapaCurso.setHfd(hFD);
		mapaCurso.setHss(hSS);
		mapaCurso.setHas(hAS);
		mapaCurso.setHorario(horario);
		mapaCurso.setSalon(salon);
		mapaCurso.setAreaId(area);
		mapaCurso.setLaboratorio(laboratorio);
		mapaCurso.setOrden(orden);

		if(!mapaCursoDao.existeReg(cursoId)){	
			cursoId = mapaCursoDao.maximoReg(planId);
			mapaCurso.setCursoId(cursoId);		
				
			if(mapaCursoDao.insertReg(mapaCurso)){
				resultado = "Saved: "+mapaCurso.getCursoClave();					
			}else{
				resultado = "Error saving: "+mapaCurso.getCursoClave();
			}			
		}else{
			if(mapaCursoDao.updateReg(mapaCurso)){
				resultado = "Updated: "+mapaCurso.getCursoClave();				
			}
			else{
				resultado = "Error updating: "+mapaCurso.getCursoClave();
			}
		}		
		return "redirect:/mapa/materia/accion?planId="+planId+"&cursoId="+cursoId+"&Mensaje="+resultado;	
	}
	
	@RequestMapping("/mapa/materia/borrarCurso")
	public String mapaMateriaBorrarCurso(HttpServletRequest request){
		
		String planId 			= request.getParameter("planId")==null?"0":request.getParameter("planId");	
		String cursoId 			= request.getParameter("cursoId")==null?"0":request.getParameter("cursoId");		
		
		if (mapaCursoDao.existeReg(cursoId)) {	
			mapaCursoDao.deleteReg(cursoId);
		}		
		return "redirect:/mapa/materia/materia?planId="+planId;
	}	
	
	@RequestMapping("/mapa/materia/grabarDatos")
	public String mapaMateriaGrabarDatos(HttpServletRequest request, Model modelo){		
		
		String planId 				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		List<MapaCurso> lisCursos	= mapaCursoDao.getLista(planId,"ORDER BY 4,3");
		String horario 				= ""; 
		String salon 				= "";		
		String mensaje				= "0";
		for(MapaCurso curso : lisCursos) {
			if (request.getParameter("Ciclo-"+curso.getCursoId()) != null) {
				String ciclo = request.getParameter("Ciclo-"+curso.getCursoId());
				mapaCursoDao.updateCiclo(curso.getCursoId(), ciclo);
			}
					
			if (request.getParameter("Salon"+curso.getCursoId()) == null) {
				salon = "N"; 
			}else {
				salon = "S";
			}
			mapaCursoDao.updateSalon(curso.getCursoId(), salon);		

			if (request.getParameter("Hora"+curso.getCursoId()) == null) {
				horario = "N"; 
			}else {
				horario = "S";
			}

			if(salon.equals("S")) {
				if(horario.equals("N")) {
					mensaje = "2";
					mapaCursoDao.updateSalon(curso.getCursoId(), "N");		
					mapaCursoDao.updateHorario(curso.getCursoId(), "N");		
				}else {
					mapaCursoDao.updateHorario(curso.getCursoId(), horario);		
				}
			}else {
				mapaCursoDao.updateHorario(curso.getCursoId(), horario);		
			}
		}
		
		return "redirect:/mapa/materia/materia?planId="+planId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/materia/accion_op")
	public String mapaDiamanteActividadAccionOp(HttpServletRequest request, Model modelo){
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String sem				= request.getParameter("Semestre")==null?"0":request.getParameter("Semestre");
		
		String materiaNombre	= mapaCursoDao.getNombreCurso(cursoId);
		
		MapaCursoElectiva optativa = new MapaCursoElectiva();
		if(mapaCursoElectivaDao.existeReg2(cursoId, folio)) {
			optativa = mapaCursoElectivaDao.mapeaRegId(cursoId, folio);
		}else {
			optativa.setFolio(mapaCursoElectivaDao.maximoReg(cursoId));
		}
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("sem", sem);
		modelo.addAttribute("materiaNombre", materiaNombre);
		modelo.addAttribute("optativa", optativa);
		
		return "mapa/materia/accion_op";
	}
	
	@RequestMapping("/mapa/materia/grabarOptativa")
	public String mapaMateriaGrabarOptativa(HttpServletRequest request, Model modelo) {
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String sem				= request.getParameter("Semestre")==null?"0":request.getParameter("Semestre");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String cursoNombre		= request.getParameter("CursoNombre")==null?"0":request.getParameter("CursoNombre");
		String mensaje			= "";
		
		MapaCursoElectiva optativa = new MapaCursoElectiva();
		
		if(mapaCursoElectivaDao.existeReg2(cursoId, folio)) {
			if(mapaCursoElectivaDao.updateReg(cursoNombre, cursoId, folio)) {
				mensaje = "1";
			}else {
				mensaje = "0";
			}
		}else {
			optativa.setCursoId(cursoId);
			optativa.setFolio(folio);
			optativa.setCursoElec("0");
			optativa.setCursoNombre(cursoNombre);
			
			if(mapaCursoElectivaDao.insertReg(optativa)) {
				mensaje = "2";
			}else {
				mensaje = "0";
			}
		}
		
		return "redirect:/mapa/materia/optativa?Plan="+planId+"&Semestre="+sem+"&Curso="+cursoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/materia/borrarOptativa")
	public String mapaMateriaBorrarOptativa(HttpServletRequest request, Model modelo) {
		String cursoId 			= request.getParameter("CursoId")==null?"0":request.getParameter("CursoId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String sem				= request.getParameter("Semestre")==null?"0":request.getParameter("Semestre");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		String mensaje 			= "";
		
		MapaCursoElec optativa = new MapaCursoElec();
		
		optativa.setCursoId(cursoId);
		optativa.setFolio(folio);
		
		if(mapaCursoElectivaDao.existeReg2(cursoId, folio)) {
			if(mapaCursoElectivaDao.deleteReg(cursoId, folio)) {
				mensaje = "1";
			}else {
				mensaje = "0";
			}
		}
		
		return "redirect:/mapa/materia/optativa?Plan="+planId+"&Semestre="+sem+"&Curso="+cursoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/materia/agregar")
	public String mapaMateriaAgregar(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContMapaMateria|mapaMateriaAgregar");
		String cursoId 			= request.getParameter("Curso")==null?"0":request.getParameter("Curso");
		String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId			= request.getParameter("Plan")==null?"0":request.getParameter("Plan");
		String sem				= request.getParameter("Semestre")==null?"0":request.getParameter("Semestre");
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("sem", sem);
		
		return "mapa/materia/agregar";
	}
	
	@RequestMapping("/mapa/materia/optativa")
	public String mapaMateriaOptativa(HttpServletRequest request, Model modelo){
		String cursoId 			= request.getParameter("Curso")==null?"0":request.getParameter("Curso");
		String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId			= request.getParameter("Plan")==null?"0":request.getParameter("Plan");
		String sem				= request.getParameter("Semestre")==null?"0":request.getParameter("Semestre");
		
		String materiaNombre	= mapaCursoDao.getNombreCurso(cursoId);

		List<MapaCursoElectiva> lisCursoElec = mapaCursoElectivaDao.getLista2(cursoId, " ORDER BY FOLIO");
		
		HashMap<String,MapaCurso> mapaCursosPlan = mapaCursoDao.mapaCursosPlan(planId);
		HashMap<String,CatTipoCurso> mapaTipos	 = catTipoCursoDao.getMapAll("");
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("cursoId", cursoId);
		modelo.addAttribute("sem", sem);
		modelo.addAttribute("materiaNombre", materiaNombre);
		
		modelo.addAttribute("mapaCursosPlan", mapaCursosPlan);
		modelo.addAttribute("mapaTipos", mapaTipos);
		modelo.addAttribute("lisCursoElec", lisCursoElec);
		
		return "mapa/materia/optativa";
	}
	
	@RequestMapping("/mapa/materia/prerrequisito")
	public String mapaMateriaPrerrequisito(HttpServletRequest request, Model modelo){		
		String idCurso 		= request.getParameter("idCurso")==null?"0":request.getParameter("idCurso");
		String planId 		= request.getParameter("planId")==null?"0":request.getParameter("planId");				
		String grabar 		= request.getParameter("grabar")==null?"N":request.getParameter("grabar");		
		String ciclo 		= request.getParameter("ciclo")==null?"0":request.getParameter("ciclo");
		String cursoNombre 	= mapaCursoDao.getNombreCurso(idCurso);
		int grabados 		= 0;
		
		List<MapaCurso> lisCursos 			= mapaCursoDao.lisCursos(planId,ciclo," ORDER BY CICLO, ORDEN, NOMBRE_CURSO");
		List<String> lisPrerrequisitos 		= new ArrayList<String>();
		
		if(grabar.equals("S")){			
			mapaCursoPreDao.deletePrerrequisitos(idCurso);
			for (MapaCurso curso : lisCursos) {
				if (request.getParameter(curso.getCursoId())!=null) {
					//Grabar
					MapaCursoPre pre = new MapaCursoPre();
					pre.setCursoId(idCurso);
					pre.setCursoIdPre(curso.getCursoId());
					if (mapaCursoPreDao.existeReg(idCurso, curso.getCursoId())== false) {
						if (mapaCursoPreDao.insertReg(pre)) grabados++;
					}
				}
			}					
		}
		mapaCursoDao.lisPrerrequisitos(idCurso, lisPrerrequisitos);
		
		//System.out.println("Datos:"+ciclo+":"+planId+":"+lisCursos.size()+":"+lisPrerrequisitos.size());
		modelo.addAttribute("cursoNombre", cursoNombre);
		modelo.addAttribute("grabados", grabados);
		modelo.addAttribute("lisCursos", lisCursos);
		modelo.addAttribute("lisPrerrequisitos", lisPrerrequisitos);
		
		return "mapa/materia/prerrequisito";
	}
	
}