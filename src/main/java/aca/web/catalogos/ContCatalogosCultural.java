package aca.web.catalogos;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.catalogo.spring.CatCultural;
import aca.catalogo.spring.CatCulturalDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatRegion;
import aca.catalogo.spring.CatRegionDao;
import org.json.*;

@Controller
public class ContCatalogosCultural {
	
	@Autowired
	private CatCulturalDao catCulturalDao;
	
	@Autowired
	private CatRegionDao catRegionDao;
	
	@RequestMapping("/catalogos/cultural/grupos")
	public String catalogosCulturalGrupos(HttpServletRequest request, Model model){
		
		List<CatCultural> lisCulturales 				= (List<CatCultural>)catCulturalDao.getListAll(" ORDER BY CULTURAL_ID");
		HashMap<String, String> mapaRegionPorCultural 	= catRegionDao.mapaTotalRegiones();
		
		model.addAttribute("lisCulturales", lisCulturales);
		model.addAttribute("mapaRegionPorCultural", mapaRegionPorCultural);
		
		return "catalogos/cultural/grupos";
	}
	
	@RequestMapping("/catalogos/cultural/editarGrupos")
	public String catalogosCulturalEditarGrupo(HttpServletRequest request, Model model) {
		String culturalId 	= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CatCultural cultural 	= new CatCultural();		
		if (catCulturalDao.existeReg(culturalId)) {
			cultural = catCulturalDao.mapeaRegId(culturalId);
		}else {
			cultural.setCulturalId(catCulturalDao.maximoReg());
		}
		
		model.addAttribute("cultural", cultural);
		model.addAttribute("mensaje", mensaje);
		
		return "catalogos/cultural/editarGrupos";
	}
	
	@RequestMapping("/catalogos/cultural/grabarGrupo")
	public String catalogosCulturalGrabarGrupo(HttpServletRequest request, Model model){
		String culturalId 			= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String nombreCultural 		= request.getParameter("NombreCultural")==null?"-":request.getParameter("NombreCultural");
		String principal 			= request.getParameter("Principal")==null?"-":request.getParameter("Principal");
		String mensaje				= "-";
		
		CatCultural cultural = new CatCultural();
		
		cultural.setCulturalId(culturalId);
		cultural.setNombreCultural(nombreCultural);
		cultural.setPrincipal(principal);
		
		if (catCulturalDao.existeReg(culturalId)) {
			if (catCulturalDao.updateReg(cultural)) {
				mensaje = "2";
			}			
		}else {
			//Insertar
			culturalId = catCulturalDao.maximoReg();
			cultural.setCulturalId(culturalId);
			if (catCulturalDao.insertReg(cultural)) {
				mensaje = "1";
			}			
		}
		
		return "redirect:/catalogos/cultural/editarGrupos?CulturalId="+culturalId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/cultural/deleteGrupo")
	public String catalogosCulturalDeleteGrupo(HttpServletRequest request){
		String culturalId 			= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String mensaje				= "-";		
		if (catCulturalDao.existeReg(culturalId)) {
			if (catCulturalDao.deleteReg(culturalId)){
				mensaje = "Deleted";
			}
		}
		
		return "redirect:/catalogos/cultural/grupos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/cultural/regiones")
	public String catalogosCulturalRegiones(HttpServletRequest request, Model model){
		
		String culturalId 		= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		
		List<CatRegion> lisRegiones = (List<CatRegion>)catRegionDao.getLista(culturalId, " ORDER BY REGION_ID");
		
		model.addAttribute("culturalId", culturalId);
		model.addAttribute("lisRegiones", lisRegiones);
		
		return "catalogos/cultural/regiones";
	}
	
	@RequestMapping("/catalogos/cultural/editarRegiones")
	public String catalogosCulturalEditarRegion(HttpServletRequest request, Model model) {
		String culturalId 	= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String regionId 	= request.getParameter("RegionId")==null?"0":request.getParameter("RegionId");
		String mensaje 	= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CatRegion region = new CatRegion();
		
		if(catRegionDao.existeReg(regionId, culturalId)) {
			region = (CatRegion)catRegionDao.mapeaRegId(regionId, culturalId);
		} else {
			region.setRegionId(catRegionDao.maximoReg(culturalId));
		}
		
		model.addAttribute("culturalId", culturalId);
		model.addAttribute("region", region);
		model.addAttribute("mensaje", mensaje);
		
		return "catalogos/cultural/editarRegiones";
	}
	
	@RequestMapping("/catalogos/cultural/grabarRegion")
	public String catalogosCulturalGrabarRegion(HttpServletRequest request, Model model){
		String culturalId 	= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String regionId 	= request.getParameter("RegionId")==null?"0":request.getParameter("RegionId");
		String nombreRegion = request.getParameter("NombreRegion")==null?"-":request.getParameter("NombreRegion");
		String mensaje		= "-";
		
		CatRegion region = new CatRegion();
		region.setCulturalId(culturalId);
		region.setRegionId(regionId);
		region.setNombreRegion(nombreRegion);
		
		if(catRegionDao.existeReg(regionId, culturalId)) {
			catRegionDao.updateReg(region);
			mensaje = "2";
		} else {
			region.setRegionId(catRegionDao.maximoReg(culturalId));
			catRegionDao.insertReg(region);
			mensaje = "1";
		}
		
		return "redirect:/catalogos/cultural/editarRegiones?CulturalId="+region.getCulturalId()+"&RegionId="+region.getRegionId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/cultural/deleteRegion")
	public String catalogosCulturalDeleteRegion(HttpServletRequest request){
		String culturalId 	= request.getParameter("CulturalId")==null?"0":request.getParameter("CulturalId");
		String regionId 	= request.getParameter("RegionId")==null?"0":request.getParameter("RegionId");	
		
		if(catRegionDao.existeReg(regionId, culturalId)) {
			catRegionDao.deleteReg(regionId, culturalId);
		}
			
		return "redirect:/catalogos/cultural/regiones?CulturalId="+culturalId;
	}
}