package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;

@Controller
public class ContCatalogosTipoCal {
	
	@Autowired
	private CatTipoCalDao catTipoCalDao;
	
	@RequestMapping("/catalogos/tipocal/tipocal")
	public String catalogoTipoCalTipoCal(HttpServletRequest request, Model model){
		
		List<CatTipoCal> lisTipos = (List<CatTipoCal>)catTipoCalDao.getListAll(" ORDER BY NOMBRE_TIPOCAL");
		HashMap<String,String> mapaCatUsadas 	= (HashMap<String,String>) catTipoCalDao.mapaCalUsadas();
		
		model.addAttribute("lisTipos", lisTipos);
		model.addAttribute("mapaCatUsadas", mapaCatUsadas);
		
		return "catalogos/tipocal/tipocal";
	}
	
	@RequestMapping("/catalogos/tipocal/editarTipo")
	public String catalogoTipoCalEditarTipo(HttpServletRequest request, Model model){		
		
		String tipoCalId 			= request.getParameter("TipoCalId")==null?"0":request.getParameter("TipoCalId");
		
		CatTipoCal tipo 		= new CatTipoCal();
		
		if (catTipoCalDao.existeReg(tipoCalId)) {
			tipo 		= catTipoCalDao.mapeaRegId(tipoCalId);
		}
//			else {
//			tipo.setTipoCalId(catTipoCalDao.maximoReg());
//		}
		
		model.addAttribute("tipo", tipo);
		
		return "catalogos/tipocal/editarTipo";
	}
	
	@RequestMapping("/catalogos/tipocal/grabarTipo")
	public String catalogoTipoCalGrabarTipo(HttpServletRequest request, Model model){
		
		String tipoCalId 			= request.getParameter("TipoCalId")==null?"0":request.getParameter("TipoCalId");
		String nombreTipoCal		= request.getParameter("NombreTipoCal")==null?"-":request.getParameter("NombreTipoCal");
		String nombreCorto			= request.getParameter("NombreCorto")==null?"-":request.getParameter("NombreCorto");
		String mensaje 				= "0";
		
		CatTipoCal tipo 		= new CatTipoCal();
		tipo.setTipoCalId(tipoCalId);
		tipo.setNombreTipoCal(nombreTipoCal);
		tipo.setNombreCorto(nombreCorto);		
		
		if (catTipoCalDao.existeReg(tipoCalId)){
			if (catTipoCalDao.updateReg(tipo)) {			
				mensaje = "Updated";
			}else{				
				mensaje = "Error updating";
			}
		}else{
			if (catTipoCalDao.insertReg(tipo) ) {			
				mensaje = "Saved";
			}else{				
				mensaje = "Error saving";
			}
		}
		
		return "redirect:/catalogos/tipocal/editarTipo?TipoCalId="+tipo.getTipoCalId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/tipocal/borrarTipo")
	public String catalogoTipoCalBorrarTipo(HttpServletRequest request, Model model){
		
		String tipoCalId 			= request.getParameter("TipoCalId")==null?"0":request.getParameter("TipoCalId");
		
		if (catTipoCalDao.existeReg(tipoCalId)) {
			catTipoCalDao.deleteReg(tipoCalId);
		}
		
		return "redirect:/catalogos/tipocal/tipocal";
	}	
}