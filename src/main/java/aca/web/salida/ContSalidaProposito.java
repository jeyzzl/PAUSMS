package aca.web.salida;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.salida.spring.SalProposito;
import aca.salida.spring.SalPropositoDao;
import aca.salida.spring.SalSolicitudDao;

@Controller
public class ContSalidaProposito {
	
	@Autowired
	SalPropositoDao salPropositoDao;
	
	@Autowired
	SalSolicitudDao salSolicitudDao;
	
	@RequestMapping("/salida/proposito/listado")
	public String salidaPropositoListado(HttpServletRequest request, Model modelo){		
		
		List<SalProposito> lisPropositos 		= salPropositoDao.lisTodos("ORDER BY PROPOSITO_NOMBRE");
		HashMap<String,String> mapaPropositos 	= salSolicitudDao.mapaPropositos(); 
		
		modelo.addAttribute("lisPropositos", lisPropositos);
		modelo.addAttribute("mapaPropositos", mapaPropositos);
		
		return "salida/proposito/listado";
	}
	
	@RequestMapping("/salida/proposito/editar")
	public String salidaPropositoEditar(HttpServletRequest request, Model model){
		
		String propositoId 		= request.getParameter("PropositoId")==null?"0":request.getParameter("PropositoId");		
		SalProposito proposito	= new SalProposito();
		
		if (salPropositoDao.existeReg(propositoId)) {
			proposito = salPropositoDao.mapeaRegId(propositoId);
		}
		
		model.addAttribute("proposito", proposito);		
		
		return "salida/proposito/editar";
	}
	
	@RequestMapping("/salida/proposito/grabar")
	public String salidaPropositoGrabar(HttpServletRequest request, Model model){				
		
		String propositoId 			= request.getParameter("PropositoId")==null?"0":request.getParameter("PropositoId");
		String propositoNombre		= request.getParameter("PropositoNombre")==null?"-":request.getParameter("PropositoNombre");
		String mensaje 				= "-";
		
		SalProposito proposito		= new SalProposito();
		proposito.setPropositoNombre(propositoNombre);
		
		if (salPropositoDao.existeReg(propositoId)){
			proposito.setPropositoId(propositoId);
			if (salPropositoDao.updateReg(proposito)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			propositoId = salPropositoDao.maximoReg();
			proposito.setPropositoId(propositoId);
			if (salPropositoDao.insertReg(proposito)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/salida/proposito/editar?PropositoId="+proposito.getPropositoId()+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/salida/proposito/borrar")
	public String salidaPropositoBorrar(HttpServletRequest request, Model model){
		
		String propositoId 			= request.getParameter("PropositoId")==null?"0":request.getParameter("PropositoId");
		if (salPropositoDao.existeReg(propositoId)) {
			salPropositoDao.deleteReg(propositoId);
		}
		
		return "redirect:/salida/proposito/listado";
	}
	
}