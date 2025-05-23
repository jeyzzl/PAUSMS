package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatArea;
import aca.catalogo.spring.CatAreaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.financiero.spring.SunPlusFuncionDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContCatalogosArea {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	@Autowired
	private CatAreaDao catAreaDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private SunPlusFuncionDao sunPlusFuncionDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;	
	
	
	@RequestMapping("/catalogos/area/areas")
	public String catalogoAreaAreas(HttpServletRequest request, Model model){
		
		List<CatArea> lisAreas = (List<CatArea>)catAreaDao.listAll(" ORDER BY NOMBRE_AREA");
		HashMap<String, String> mapaAreasPorFacultad = catFacultadDao.mapaAreasPorFacultad();
		
		model.addAttribute("lisAreas", lisAreas);
		model.addAttribute("mapaAreasPorFacultad", mapaAreasPorFacultad);
		
		return "catalogos/area/areas";
	}
	
	@RequestMapping("/catalogos/area/editarArea")
	public String catalogoEditarArea(HttpServletRequest request, Model model){
		
		String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");		
		CatArea area		= new CatArea();
		
		if (catAreaDao.existeReg(areaId)) {
			area= catAreaDao.mapeaRegId(areaId);
		}
		
		List<Maestros> lisMaestros	= maestrosDao.lisPorEstado("'A','J'"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		model.addAttribute("area", area);		
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "catalogos/area/editarArea";
	}
	
	@RequestMapping("/catalogos/area/grabarArea")
	public String catalogoAreaGrabarArea(HttpServletRequest request, Model model){				
		
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String nombreArea		= request.getParameter("NombreArea")==null?"-":request.getParameter("NombreArea");
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String tipoPromedio		= request.getParameter("TipoPromedio")==null?"0":request.getParameter("TipoPromedio");
		String mensaje 			= "-";
		
		CatArea area		= new CatArea();
		area.setNombreArea(nombreArea);
		area.setCodigoPersonal(codigoPersonal);
		area.setTipoPromedio(tipoPromedio);
		
		if (catAreaDao.existeReg(areaId)){
			area.setAreaId(areaId);
			if (catAreaDao.updateReg(area)){
				mensaje = "Saved!";
			}else {
				mensaje = "Error!";
			}
		}else {
			area.setAreaId(catAreaDao.maximoReg());
			if (catAreaDao.insertReg(area)){
				mensaje = "Saved!";
			}else {
				mensaje = "Error!";
			}
		}		
		return "redirect:/catalogos/area/editarArea?AreaId="+area.getAreaId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/area/borrarArea")
	public String catalogoAreaBorrarArea(HttpServletRequest request, Model model){
		
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		if (catAreaDao.existeReg(areaId)) {
			catAreaDao.deleteReg(areaId);
		}
		
		return "redirect:/catalogos/area/areas";
	}
	
	@RequestMapping("/catalogos/area/facultad")
	public String catalogoAreasFacultad(HttpServletRequest request, Model model){		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String areaId 			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		if (sesion != null){
			if (areaId.equals("0")) {
				areaId = (String)sesion.getAttribute("areaId");
			}else {
				sesion.setAttribute("areaId", areaId);
			}
		}		
		List<CatFacultad> lisFacultades = (List<CatFacultad>)catFacultadDao.lisPorArea(areaId, " ORDER BY NOMBRE_FACULTAD");
		HashMap<String,String> mapaDirectores			= maestrosDao.mapaDirectores();
		HashMap<String, String> mapaCarrerasPorFacultad = catCarreraDao.mapaCarrerasPorFacultad();
		
		//List<CatFacultad> lisFacultades = (List<CatFacultad>)catFacultadRepo.findAll(new Sort("nombreFacultad"));
		
		model.addAttribute("lisFacultades", lisFacultades);
		model.addAttribute("mapaDirectores", mapaDirectores);
		model.addAttribute("mapaCarrerasPorFacultad", mapaCarrerasPorFacultad);
		
		return "catalogos/area/facultad";		
	}
	
	@RequestMapping("/catalogos/area/editarFacultad")
	public String catalogoEditarFacultad(HttpServletRequest request, Model model){
				
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		
		CatFacultad facultad	= new CatFacultad();		
		if (catFacultadDao.existeReg(facultadId)) {
			facultad= catFacultadDao.mapeaRegId(facultadId);
		}
		
		List<Maestros> lisMaestros	= maestrosDao.lisPorEstado("'A','J'"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		model.addAttribute("facultad", facultad);	
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "catalogos/area/editarFacultad";
	}
	
	@RequestMapping("/catalogos/area/grabarFacultad")
	public String catalogoAreaGrabarFacultad(HttpServletRequest request, Model model){
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		
		String areaId 			= "0";
		String facultadId		= request.getParameter("FacultadId")==null?"-":request.getParameter("FacultadId");
		String nombreFacultad	= request.getParameter("NombreFacultad")==null?"-":request.getParameter("NombreFacultad");
		String titulo			= request.getParameter("Titulo")==null?"-":request.getParameter("Titulo");
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String nombreCorto		= request.getParameter("NombreCorto")==null?"0":request.getParameter("NombreCorto");		
		String referente		= request.getParameter("Referente")==null?"0":request.getParameter("Referente");
		String mensaje 			= "-";
		
		CatFacultad facultad		= new CatFacultad();
		if (sesion != null){
			areaId 	= sesion.getAttribute("areaId").toString();			
		}
		
		facultad.setAreaId(areaId);
		facultad.setCodigoPersonal(codigoPersonal);
		facultad.setNombreFacultad(nombreFacultad);
		facultad.setNombreCorto(nombreCorto);
		facultad.setTitulo(titulo);
		facultad.setInvReferente(referente);
		
		if (catFacultadDao.existeReg(facultadId)) {
			facultad.setFacultadId(facultadId);
			// Grabar
			if (catFacultadDao.updateReg(facultad)) {
				mensaje = "¡Saved!";
			}else {
				mensaje = "¡Error!";
			}
		}else {
			// Grabar
			facultad.setFacultadId(catFacultadDao.maximoReg(areaId));
			if (catFacultadDao.insertReg(facultad)) {
				mensaje = "¡Saved!";
			}else {
				mensaje = "Error";
			}
		}
		
		return "redirect:/catalogos/area/editarFacultad?FacultadId="+facultad.getFacultadId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/area/borrarFacultad")
	public String catalogoAreaBorrarFacultad(HttpServletRequest request, Model model){
		
		String facultadId 			= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		if (catFacultadDao.existeReg(facultadId)) {
			catFacultadDao.deleteReg(facultadId);
		}
		
		return "redirect:/catalogos/area/facultad";
	}
	
	@RequestMapping("/catalogos/area/carrera")
	public String catalogoAreaCarrera(HttpServletRequest request, Model model){		
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		if (sesion != null){
			if (facultadId.equals("0")) {
				facultadId = (String)sesion.getAttribute("facultadId");
			}else {
				sesion.setAttribute("facultadId", facultadId);
			}
		}
		
		List<CatCarrera> lisCarreras 	= (List<CatCarrera>)catCarreraDao.getLista(facultadId, " ORDER BY NOMBRE_CARRERA");
		
		HashMap<String,String> mapaCoordinadores		= maestrosDao.mapaCoordinadores();
		HashMap<String, String> mapaFunciones			= sunPlusFuncionDao.mapaFunciones();
		HashMap<String, String> mapaPlanesPorCarrera 	= mapaPlanDao.mapaPlanesPorCarrera();
		model.addAttribute("lisCarreras", lisCarreras);
		model.addAttribute("mapaCoordinadores", mapaCoordinadores);
		model.addAttribute("mapaFunciones", mapaFunciones);
		model.addAttribute("mapaPlanesPorCarrera", mapaPlanesPorCarrera);
		
		return "catalogos/area/carrera";		
	}
	
	@RequestMapping("/catalogos/area/editarCarrera")
	public String catalogoEditarCarrera(HttpServletRequest request, Model model){
				
		String carreraId 				= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		List<CatNivel> lisNiveles 		= (List<CatNivel>)catNivelDao.getListAll(" ORDER BY NIVEL_ID");
		
		CatCarrera carrera	= new CatCarrera();		
		if (catCarreraDao.existeReg(carreraId)) {
			carrera = catCarreraDao.mapeaRegId(carreraId);
		}
		
		List<Maestros> lisMaestros	= maestrosDao.lisPorEstado("'A','J'"," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		model.addAttribute("carrera", carrera);
		model.addAttribute("lisNiveles", lisNiveles);		
		model.addAttribute("lisMaestros", lisMaestros);
		
		return "catalogos/area/editarCarrera";
	}
	
	@RequestMapping("/catalogos/area/grabarCarrera")
	public String catalogoAreaGrabarCarrera(HttpServletRequest request, Model model){	
				
		String facultadId		= "";
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String nivelId 			= request.getParameter("NivelId")==null?"-":request.getParameter("NivelId");
		String titulo			= request.getParameter("Titulo")==null?"-":request.getParameter("Titulo");
		String nombreCarrera	= request.getParameter("NombreCarrera")==null?"-":request.getParameter("NombreCarrera");		
		String nombreCorto		= request.getParameter("NombreCorto")==null?"0":request.getParameter("NombreCorto");
		String depto			= request.getParameter("CcostoId")==null?"0":request.getParameter("CcostoId");
		String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
		String glsetno 			= request.getParameter("GlSetNo")==null?"XXX":request.getParameter("GlSetNo");
		String costcentcd 		= request.getParameter("CostCentCd")==null?"0":request.getParameter("CostCentCd");
		String dscntpct 		= request.getParameter("DscntPct")==null?"0":request.getParameter("DscntPct");		
		String mensaje 			= "-";
		
		CatCarrera carrera	= new CatCarrera();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			facultadId = sesion.getAttribute("facultadId").toString();
		}
		
		HashMap<String,String> mapaCarreras	= catCarreraDao.mapaCarrerasFacultad(facultadId);		
		String carreraNueva = "0";
		if (carreraId.equals("0")) {
			int row=1;
			while (carreraNueva.equals("0") && row < 100) {
				String folio = "";
				if (row<10) folio = "0"+String.valueOf(row); else folio = String.valueOf(row);  
				if (!mapaCarreras.containsKey(facultadId+folio)){
					carreraNueva = facultadId+folio;
				}
				row++;
			}
			carrera.setCarreraId(carreraNueva);
		}else {
			carrera.setCarreraId(carreraId);
		}
		carrera.setFacultadId(facultadId);		
		carrera.setNivelId(nivelId);
		carrera.setCodigoPersonal(codigoPersonal);
		carrera.setNombreCarrera(nombreCarrera);
		carrera.setNombreCorto(nombreCorto);
		carrera.setTitulo(titulo);		
		carrera.setCcostoId(depto);
		carrera.setGlsetno(glsetno);
		carrera.setCostcentcd(costcentcd);
		carrera.setDscntpct(dscntpct);
		
		if (catCarreraDao.existeReg(carreraId)) {
			carrera.setCarreraId(carreraId);
			// Modificar
			if (catCarreraDao.updateReg(carrera)) {
				mensaje = "¡Saved!";
			}else {
				mensaje = "¡Error!";
			}
		}else {
			// Insertar
			if (catCarreraDao.insertReg(carrera)) {
				mensaje = "¡Saved!";
			}else {
				mensaje = "¡Error!";
			}
		}
		
		return "redirect:/catalogos/area/editarCarrera?CarreraId="+carrera.getCarreraId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/area/borrarCarrera")
	public String catalogoAreaBorrarCarrera(HttpServletRequest request, Model model){
		
		String carreraId 			= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		if (catCarreraDao.existeReg(carreraId)) {
			catCarreraDao.deleteReg(carreraId);
		}
		
		return "redirect:/catalogos/area/carrera";
	}
	
}