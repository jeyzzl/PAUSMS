package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;

@Controller
public class ContCatalogosModalidad {
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@RequestMapping("/catalogos/modalidad/modalidad")
	public String catalogoModalidadModalidad(HttpServletRequest request, Model model){
		
		List<CatModalidad> lista 				= (List<CatModalidad>)catModalidadDao.getListAll(" ORDER BY NOMBRE_MODALIDAD");
		HashMap<String,String> mapaModalidades 	= (HashMap<String,String>) catModalidadDao.mapaUsados();
		
		model.addAttribute("lista", lista);
		model.addAttribute("mapaModalidades", mapaModalidades);
		
		return "catalogos/modalidad/modalidad";
	}	
	
	@RequestMapping("/catalogos/modalidad/accion")
	public String catalogoModalidadAccion(HttpServletRequest request, Model model){
		
		String modalidadId 			= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");		
		
		CatModalidad modalidad 		= new CatModalidad();		
		if (catModalidadDao.existeReg(modalidadId)) {
			modalidad 		= catModalidadDao.mapeaRegId(modalidadId);
		}else {
			modalidad.setModalidadId(catModalidadDao.maximoReg());
		}
		
		model.addAttribute("modalidad", modalidad);		
		
		return "catalogos/modalidad/accion";
	}
	
	@RequestMapping("/catalogos/modalidad/grabar")
	public String catalogoModalidadGrabar(HttpServletRequest request, Model model){
		
		String modalidadId 			= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		String nombreModalidad		= request.getParameter("NombreModalidad")==null?"-":request.getParameter("NombreModalidad");
		String enLinea 				= request.getParameter("EnLinea")==null?"0":request.getParameter("EnLinea");
		String admisible 			= request.getParameter("Admisible")==null?"0":request.getParameter("Admisible");
		String mensaje 				= "0";
		
		CatModalidad modalidad 		= new CatModalidad();		
		modalidad.setNombreModalidad(nombreModalidad);
		modalidad.setEnLinea(enLinea);
		modalidad.setAdmisible(admisible);
		
		if (catModalidadDao.existeReg(modalidadId)) {
			// Update 
			modalidad.setModalidadId(modalidadId);
			if (catModalidadDao.updateReg(modalidad)) {
				mensaje = "3";
			}else{				
				mensaje = "4";
			}			
		}else {
			// Insert			 
			modalidad.setModalidadId(catModalidadDao.maximoReg() );
			if (catModalidadDao.insertReg(modalidad)){
				mensaje = "1";
			}else{				
				mensaje = "2";
			}									
		}
		
		return "redirect:/catalogos/modalidad/accion?ModalidadId="+modalidad.getModalidadId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/catalogos/modalidad/borrar")
	public String catalogoModalidadBorrar(HttpServletRequest request, Model model){
		
		String modalidadId 			= request.getParameter("ModalidadId")==null?"0":request.getParameter("ModalidadId");
		String mensaje 				= "0";
		
		try {
			catModalidadDao.deleteReg(modalidadId);
			mensaje = "5";
		}catch(Exception e){		
			mensaje = "6";
		}	
		return "redirect:/catalogos/modalidad/modalidad?Mensaje="+mensaje;
	}
	
	
}