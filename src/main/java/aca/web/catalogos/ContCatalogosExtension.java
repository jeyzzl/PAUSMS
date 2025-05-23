package aca.web.catalogos;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatExtension;
import aca.catalogo.spring.CatExtensionDao;
import aca.catalogo.spring.CatDivisionDao;


@Controller
public class ContCatalogosExtension {
	
	@Autowired
	private CatExtensionDao catExtensionDao;
	
	@RequestMapping("/catalogos/extension/extension")
	public String catalogoRazonRazon(HttpServletRequest request, Model model){
		
		List<CatExtension> lisExtension = (List<CatExtension>)catExtensionDao.getListAll(" ORDER BY NOMBRE_EXTENSION");
		model.addAttribute("lisExtension", lisExtension);
		
		return "catalogos/extension/extension";
	}
	
	@RequestMapping("/catalogos/extension/editarExtension")
	public String catalogoExtensionEditarExtension(HttpServletRequest request, Model model){
		
		String extensionId			= request.getParameter("ExtensionId")==null?"0":request.getParameter("ExtensionId");
		CatExtension extension 		= new CatExtension();
		
		if (catExtensionDao.existeReg(extensionId)){
			extension 		= catExtensionDao.mapeaRegId(extensionId);
		}else {
			extension.setExtensionId(catExtensionDao.maximoReg());
		}
	
		
		model.addAttribute("extension", extension);
		
		return "catalogos/extension/editarExtension";
	}
	
	@RequestMapping("/catalogos/extension/grabarExtension")
	public String catalogosGrabarExtension(HttpServletRequest request, Model model){
		
		String extensionId 			= request.getParameter("ExtensionId")==null?"0":request.getParameter("ExtensionId");
		String nombreExtension		= request.getParameter("NombreExtension")==null?"-":request.getParameter("NombreExtension");
		String referente			= request.getParameter("Referente")==null?"-":request.getParameter("Referente");
		String direccion			= request.getParameter("Direccion")==null?"-":request.getParameter("Direccion");
		String colonia				= request.getParameter("Colonia")==null?"-":request.getParameter("Colonia");
		String codigoPostal			= request.getParameter("CodPostal")==null?"-":request.getParameter("CodPostal");
		String mensaje 				= "0";
		
		CatExtension extension	= new CatExtension();
		extension.setExtensionId(extensionId);
		extension.setNombreExtension(nombreExtension);
		extension.setExtensionId(extensionId);
		extension.setNombreExtension(nombreExtension);
		extension.setReferente(referente);
		extension.setDireccion(direccion);
		extension.setColonia(colonia);
		extension.setCodPostal(codigoPostal);
		if (catExtensionDao.existeReg(extensionId)){
			if (catExtensionDao.updateReg(extension)) {
				mensaje = "Updated";
			}else{				
				mensaje = "Error updating";
			}
		}else{
			if (catExtensionDao.insertReg(extension)) {
				mensaje = "Saved";
			}else{				
				mensaje = "Error saving";
			}
		}												
				
		return "redirect:/catalogos/extension/editarExtension?ExtensionId="+extension.getExtensionId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/extension/borrarExtension")
	public String catalogoExtensionBorrarExtension(HttpServletRequest request, Model model){
		
		String extensionId	= request.getParameter("ExtensionId")==null?"0":request.getParameter("ExtensionId");
		
		if (catExtensionDao.existeReg(extensionId)) {
			catExtensionDao.deleteReg(extensionId);
		}
		
		return "redirect:/catalogos/extension/extension";
	}
	
}