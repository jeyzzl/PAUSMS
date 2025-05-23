package aca.web.mapa;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.cert.spring.CertPlanDao;
import aca.plan.spring.MapaArchivo;
import aca.plan.spring.MapaArchivoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaCursoPreDao;
import aca.plan.spring.MapaMayorMenor;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaNuevoPlan;
import aca.plan.spring.MapaNuevoPlanDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.plan.spring.MapaVersion;
import aca.plan.spring.MapaVersionDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMapaPlan {	
	
	@Autowired	
	private MapaArchivoDao mapaArchivoDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private MapaCursoPreDao mapaCursoPreDao;
	
	@Autowired	
	private MapaVersionDao mapaVersionDao;	
	
	@Autowired	
	private MapaCursoDao mapaCursoDao;
	
	@Autowired	
	private MapaNuevoPlanDao mapaNuevoPlanDao;
	
	@Autowired	
	private CertPlanDao certPlanDao;

	@Autowired
	private MapaMayorMenorDao mapaMayorMenorDao;
	
	
	@RequestMapping("/mapa/plan/facultad")
	public String mapaPFacultad(HttpServletRequest request, Model modelo){	
		
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll("ORDER BY 1");
		HashMap<String,String> mapaDirectores	= maestrosDao.mapaDirectores();
		HashMap<String,String> mapaPlanes		= mapaPlanDao.mapaPlanesPorFacultad();
		
		modelo.addAttribute("lisFacultades", lisFacultades);		
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "mapa/plan/facultad";
	}
	
	@RequestMapping("/mapa/plan/plan")
	public String mapaPlanPlan(HttpServletRequest request, Model modelo){	
		
		String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");		
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultad);
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}
		}
		if (filtroEstado.equals("T")) filtroEstado = "'A','V','I'"; else filtroEstado = "'"+filtroEstado+"'";
		List<MapaPlan> lisPlanes						= mapaPlanDao.lisPorFacultadyEstado(facultad, filtroEstado, "ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID),2,1");
		HashMap<String, CatCarrera> mapCarrera			= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaArchivo				= mapaArchivoDao.mapaArchivos();
		HashMap<String, String> mapaPrerrequisitos		= mapaCursoPreDao.mapaPrePorPlan();
		HashMap<String, String> mapaVersiones			= mapaVersionDao.mapaVersiones();
		HashMap<String,String> mapaCursosPorPlan		= mapaCursoDao.mapaCursosPorPlan();
		HashMap<String, MapaNuevoPlan> mapaNuevoPlan 	= mapaNuevoPlanDao.mapaPlan();
		HashMap<String,String> mapaContenido			= mapaCursoDao.mapCursosContenido();
		HashMap<String,String> mapaPlanesCert			= certPlanDao.mapaPlanes();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapCarrera", mapCarrera);		
		modelo.addAttribute("mapaArchivo", mapaArchivo);
		modelo.addAttribute("mapaPrerrequisitos", mapaPrerrequisitos);
		modelo.addAttribute("mapaVersiones", mapaVersiones);
		modelo.addAttribute("mapaCursosPorPlan", mapaCursosPorPlan);
		modelo.addAttribute("mapaNuevoPlan", mapaNuevoPlan);
		modelo.addAttribute("mapaContenido", mapaContenido);
		modelo.addAttribute("mapaPlanesCert", mapaPlanesCert);
		
		return "mapa/plan/plan";
	}
	
	@RequestMapping("/mapa/plan/accion")
	public String mapaPlanAccion(HttpServletRequest request, Model modelo){
		
		String facultad			= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");		
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");		
		int numCursos			= 0;
		if(planId != "0"){
			numCursos			= mapaPlanDao.getNumCursos(planId);
		}
		
		MapaPlan plan = new MapaPlan();
		if (mapaPlanDao.existeReg(planId)) {
			plan = mapaPlanDao.mapeaRegId(planId);
		} else {
			
		}
		String facultadNombre = catFacultadDao.getNombreFacultad(facultad);
		
		List<CatCarrera> lisCarreras 		= catCarreraDao.getLista(facultad, "ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		List<MapaVersion> lisVersiones		= mapaVersionDao.LisTodos(" ORDER BY 2");
		
		modelo.addAttribute("facultad", facultad);
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("numCursos", numCursos);
		modelo.addAttribute("plan", plan);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("lisVersiones", lisVersiones);
		
		return "mapa/plan/accion";
	}
	
	@RequestMapping("/mapa/plan/grabarPlan")
	public String catalogoAreaGrabarPlan(HttpServletRequest request, Model model){				
		
		String facultad			= request.getParameter("Facultad")==null?"000":request.getParameter("Facultad");
		String filtroEstado		= request.getParameter("FiltroEstado")==null?"T":request.getParameter("FiltroEstado");
		String planId			= request.getParameter("PlanId")==null?"-":request.getParameter("PlanId");
		String carreraId		= request.getParameter("CarreraId")==null?"-":request.getParameter("CarreraId");
		String nombrePlan		= request.getParameter("NombrePlan")==null?"-":request.getParameter("NombrePlan");
		String nombrePlanMujer	= request.getParameter("NombrePlanMujer")==null?"-":request.getParameter("NombrePlanMujer");
		String carreraSe		= request.getParameter("CarreraSe")==null?"-":request.getParameter("CarreraSe");
		String planSe			= request.getParameter("PlanSE")==null?"-":request.getParameter("PlanSE");
		String claveProfesiones	= request.getParameter("ClaveProfesiones")==null?"-":request.getParameter("ClaveProfesiones");
		String minimo			= request.getParameter("Minimo")==null?"0":request.getParameter("Minimo");
		String maximo			= request.getParameter("Maximo")==null?"0":request.getParameter("Maximo");
		String notaExtra		= request.getParameter("NotaExtra")==null?"0":request.getParameter("NotaExtra");
		String rvoeInicial		= request.getParameter("RvoeInicial")==null?"-":request.getParameter("RvoeInicial");
		String rvoe				= request.getParameter("Rvoe")==null?"-":request.getParameter("Rvoe");
		String ciclos			= request.getParameter("Ciclos")==null?"0":request.getParameter("Ciclos");
		String oficial			= request.getParameter("Oficial")==null?"-":request.getParameter("Oficial");
		String enLinea			= request.getParameter("EnLinea")==null?"-":request.getParameter("EnLinea");
		String estado			= request.getParameter("Estado")==null?"-":request.getParameter("Estado");
		String general			= request.getParameter("General")==null?"N":request.getParameter("General");
		String fActualiza		= request.getParameter("FActualiza")==null?"01/01/2023":request.getParameter("FActualiza");
		String fInicio			= request.getParameter("FInicio")==null?"01/01/2023":request.getParameter("FInicio");
		String fFinal			= request.getParameter("FFinal")==null?"01/01/2023":request.getParameter("FFinal");
		String precio			= request.getParameter("Precio")==null?"0":request.getParameter("Precio");
		String versionId		= request.getParameter("VersionId")==null?"0":request.getParameter("VersionId");
		String semsys			= request.getParameter("Semsys")==null?"-":request.getParameter("Semsys"); 
		String expediente		= request.getParameter("Expediente")==null?"-":request.getParameter("Expediente");
		String creditos			= request.getParameter("Creditos")==null?"0":request.getParameter("Creditos");
		String planOrigen		= request.getParameter("PlanOrigen")==null?"-":request.getParameter("PlanOrigen");
		String descuento		= request.getParameter("Descuento")==null?"-":request.getParameter("Descuento");
		String mensaje 			= "-";
		
		MapaPlan plan = new MapaPlan();
		plan.setCarreraId(carreraId);
		plan.setNombrePlan(nombrePlan);
		plan.setNombrePlanMujer(nombrePlanMujer);
		plan.setCarreraSe(carreraSe);
		plan.setPlanSE(planSe);
		plan.setClaveProfesiones(claveProfesiones);
		plan.setMinimo(minimo);
		plan.setMaximo(maximo);
		plan.setNotaExtra(notaExtra);
		plan.setRvoeInicial(rvoeInicial);
		plan.setRvoe(rvoe);
		plan.setCiclos(ciclos);
		plan.setOficial(oficial);
		plan.setEnLinea(enLinea);
		plan.setEstado(estado);
		plan.setGeneral(general);
		plan.setFActualiza(fActualiza);
		plan.setFInicio(fInicio);
		plan.setFFinal(fFinal);
		plan.setPrecio(precio);
		plan.setVersionId(versionId);
		plan.setSemsys(semsys);
		plan.setExpediente(expediente);
		plan.setCreditos(creditos);
		plan.setPlanOrigen(planOrigen);
		plan.setDescuento(descuento);
		
		if (mapaPlanDao.existeReg(planId)){
			plan.setPlanId(planId);
			if (mapaPlanDao.updateReg(plan)){
				mensaje = "1";
			}
		}else {
			plan.setPlanId(planId);
			if (mapaPlanDao.insertReg(plan)){
				mensaje = "1";
			}
		}		
		return "redirect:/mapa/plan/accion?Facultad="+facultad+"&PlanId="+planId+"&FiltroEstado="+filtroEstado+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/mapa/plan/borrarPlan")
	public String catalogoAreaBorrarArea(HttpServletRequest request, Model model){
		
		String facultad	= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
		String planId 	= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		if (mapaPlanDao.existeReg(planId)) {
			mapaPlanDao.deleteReg(planId);
		}
		
		return "redirect:/mapa/plan/plan?Facultad="+facultad;
	}
	
	@RequestMapping("/mapa/plan/mapaArchivo")
	public String mapaPlanMapaArchivo(HttpServletRequest request, Model modelo){
		
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		boolean tieneArchivo	= false;
		String carreraId 		= mapaPlanDao.getCarreraId(planId);
		String facultadId		= catCarreraDao.getFacultadId(carreraId);
		MapaArchivo archivo 	= new MapaArchivo();
		
		if (mapaArchivoDao.existeReg(planId, folio)){
			tieneArchivo = true;
			archivo = mapaArchivoDao.mapeaRegId(planId, folio);
		}
		modelo.addAttribute("tieneArchivo", tieneArchivo);
		modelo.addAttribute("archivo", archivo);
		modelo.addAttribute("facultadId", facultadId);
		
		return "mapa/plan/mapaArchivo";
	}
	
	@PostMapping("/mapa/plan/saveFile")
	public String mapaPlanSaveFile(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		
		aca.plan.spring.MapaArchivo archivo = new aca.plan.spring.MapaArchivo();
		
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		
		try {
			archivo.setPlanId(planId);
			archivo.setFolio(folio);		
			archivo.setNombre(file.getOriginalFilename());
			archivo.setArchivo(file.getBytes());	
			if(mapaArchivoDao.existeReg(planId, folio)){				
				mapaArchivoDao.updateReg(archivo);	
			}else{				
				mapaArchivoDao.insertReg(archivo);	
			}			
		}catch(Exception ex){			
		}		
		
		return "redirect:/mapa/plan/mapaArchivo?PlanId="+planId+"&Folio="+folio;
	}
	
	@RequestMapping("/mapa/plan/bajarArchivo")
	public void mapaPlanBajarArchivo(HttpServletRequest request, HttpServletResponse response ) {
		
		aca.plan.spring.MapaArchivo archivo = new aca.plan.spring.MapaArchivo();
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId"); 
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		try {						
			if(mapaArchivoDao.existeReg(planId, folio)){
				archivo = mapaArchivoDao.mapeaRegId(planId, folio);			
				
				OutputStream out = response.getOutputStream();
				response.setHeader("Content-Disposition","attachment; filename=\""+ archivo.getNombre()+ "\"");
				out.write(archivo.getArchivo());				
			}
		}catch(Exception ex){
			System.out.println("Error:mapa/plan/bajarArchivo:"+ex);
		}		
	}
	
	@RequestMapping("/mapa/plan/borrarArchivo")
	public String mapaPlanBorrarArchivo(HttpServletRequest request ) {		
		
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId"); 
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
								
		if(mapaArchivoDao.existeReg(planId, folio)){							
			mapaArchivoDao.deleteReg(planId, folio);
		}
		
		return "redirect:/mapa/plan/mapaArchivo?PlanId="+planId+"&Folio="+folio;
	}
	
}