package aca.web.catalogos;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatInstitucion;
import aca.catalogo.spring.CatInstitucionDao;

@Controller
public class ContCatalogosInstituciones {
		
	@Autowired
	private CatInstitucionDao catInstitucionDao;
	
	@RequestMapping("/catalogos/instituciones/institucion")
	public String catalogosInstitucionesInstitucion(HttpServletRequest request, Model model){
		
		List<CatInstitucion> lisInstituciones = (List<CatInstitucion>)catInstitucionDao.getListAll(" ORDER BY INSTITUCION_ID");		
		model.addAttribute("lisInstituciones", lisInstituciones);
		
		return "catalogos/instituciones/institucion";
	}
	
	@RequestMapping("/catalogos/instituciones/editarInstitucion")
	public String catalogosInstitucionEditarInstitucion(HttpServletRequest request, Model model){		
		
		String institucionId	= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");		
		CatInstitucion institucion = new CatInstitucion();		
		if (catInstitucionDao.existeReg(institucionId)) {
			institucion 		= catInstitucionDao.mapeaRegId(institucionId);
		}else {
			institucion.setInstitucionId(catInstitucionDao.maximoReg());
		}		
		model.addAttribute("institucion", institucion);		
		return "catalogos/instituciones/editarInstitucion";
	}
	
	@RequestMapping("/catalogos/instituciones/grabarInstitucion")
	public String catalogosGrabarInstitucion(HttpServletRequest request, Model model){
		
		String institucionId 		= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		String nombreInstitucion	= request.getParameter("NombreInstitucion")==null?"-":request.getParameter("NombreInstitucion");
		String mensaje 				= "0";
		
		CatInstitucion institucion	= new CatInstitucion();
		institucion.setInstitucionId(institucionId);
		institucion.setNombreInstitucion(nombreInstitucion);
		if (catInstitucionDao.existeReg(institucionId)){
			if (catInstitucionDao.updateReg(institucion)){
				mensaje = "Updated";
			}else{				
				mensaje = "Error Updating";
			}
		}else{
			institucion.setInstitucionId(catInstitucionDao.maximoReg());
			if (catInstitucionDao.insertReg(institucion)){
				mensaje = "Saved";
			}else{				
				mensaje = "Error Saving";
			}
		}		
		return "redirect:/catalogos/instituciones/editarInstitucion?InstitucionId="+institucion.getInstitucionId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/instituciones/borrarInstitucion")
	public String catalogoInstitucionBorrarInstitucion(HttpServletRequest request, Model model){
		
		String institucionId	= request.getParameter("InstitucionId")==null?"0":request.getParameter("InstitucionId");
		
		if (catInstitucionDao.existeReg(institucionId)) {
			catInstitucionDao.deleteReg(institucionId);
		}
		
		return "redirect:/catalogos/instituciones/institucion";
	}

}