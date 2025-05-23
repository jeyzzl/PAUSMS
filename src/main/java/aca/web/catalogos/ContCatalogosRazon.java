package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.ResRazones;
import aca.catalogo.spring.ResRazonesDao;

@Controller
public class ContCatalogosRazon {
	
	@Autowired
	private ResRazonesDao resRazonesDao;
	
	@RequestMapping("/catalogos/razon/razon")
	public String catalogoRazonRazon(HttpServletRequest request, Model model){
		
		List<ResRazones> lisRazones = (List<ResRazones>)resRazonesDao.getListAll("");
		HashMap<String,String> mapaRazonesUsadas 	= (HashMap<String,String>) resRazonesDao.mapaRazonesUsadas();
		
		model.addAttribute("lisRazones", lisRazones);
		model.addAttribute("mapaRazonesUsadas", mapaRazonesUsadas);
		
		return "catalogos/razon/razon";
	}
	
	@RequestMapping("/catalogos/razon/editarRazon")
	public String catalogoRazonEditarRazon(HttpServletRequest request, Model model){		
		
		String razon	= request.getParameter("Razon")==null?"0":request.getParameter("Razon");
		
		ResRazones resRazon = new ResRazones();
		
		if (resRazonesDao.existeReg(razon)) {
			resRazon = resRazonesDao.mapeaRegId(razon);
		}else {
			resRazon.setRazon(Integer.parseInt(resRazonesDao.maximoReg()));
		}
		
		model.addAttribute("resRazon", resRazon);
		
		return "catalogos/razon/editarRazon";
	}
	
	@RequestMapping("/catalogos/razon/grabarRazon")
	public String catalogoRazonGrabarRazon(HttpServletRequest request, Model model){
		
		String razon 		= request.getParameter("Razon")==null?"0":request.getParameter("Razon");
		String descripcion	= request.getParameter("Descripcion")==null?"-":request.getParameter("Descripcion");
		String mensaje 		= "0";
		
		ResRazones resRazon	= new ResRazones();
		resRazon.setRazon(Integer.valueOf(razon));
		resRazon.setDescripcion(descripcion);
		
		if (resRazonesDao.existeReg(razon)) {
			if (resRazonesDao.updateReg(resRazon)) {
				mensaje = "Updated";
			}else {
				mensaje = "Error updating";
			}
		}else {
			if (resRazonesDao.insertReg(resRazon)) {
				mensaje = "Saved";
			}else {
				mensaje = "Error saving";
			}
		}									
				
		return "redirect:/catalogos/razon/editarRazon?Razon="+resRazon.getRazon()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/razon/borrarRazon")
	public String catalogoRazonBorrarRazon(HttpServletRequest request, Model model){
		
		String razon	= request.getParameter("Razon")==null?"0":request.getParameter("Razon");
		
		if (resRazonesDao.existeReg(razon)) {
			resRazonesDao.deleteReg(razon);
		}
		
		return "redirect:/catalogos/razon/razon";
	}
	
}