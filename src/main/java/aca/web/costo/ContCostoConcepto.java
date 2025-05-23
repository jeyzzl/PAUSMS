package aca.web.costo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.calcula.spring.CalConcepto;
import aca.calcula.spring.CalConceptoDao;
import aca.calcula.spring.CalCostoDao;

@Controller
public class ContCostoConcepto {	
	
	@Autowired
	CalConceptoDao calConceptoDao;
	
	@Autowired
	CalCostoDao calCostoDao;
	
	@RequestMapping("/costo/concepto/lista")
	public String costoConceptoLista(HttpServletRequest request, Model modelo){
		
		List<CalConcepto> lista = calConceptoDao.lisTodos(" ORDER BY CONCEPTO_ID");
		HashMap<String,String> mapaConceptos = calCostoDao.mapaConceptos();
		
		modelo.addAttribute("lista", lista);
		modelo.addAttribute("mapaConceptos", mapaConceptos);
		
		return "costo/concepto/lista";
	}
	
	@RequestMapping("/costo/concepto/editar")
	public String costoConceptoEditar(HttpServletRequest request, Model modelo){		
		String conceptoId	= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		String mensaje		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		CalConcepto objeto	= new CalConcepto();
		
		if (calConceptoDao.existeReg(conceptoId)) {
			objeto = calConceptoDao.mapeaRegId(conceptoId);				
		}
		
	    modelo.addAttribute("concepto", objeto);
	    modelo.addAttribute("mensaje", mensaje);
	    
	    return "costo/concepto/editar";
	}
	
	@RequestMapping("/costo/concepto/grabar")
	public String costoConceptoGrabar(HttpServletRequest request){
		String conceptoId		= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		String conceptoNombre	= request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String tipo				= request.getParameter("Tipo")==null?"-":request.getParameter("Tipo");
		String mensaje			= "0";
		
		CalConcepto objeto	 	= new CalConcepto();
	
		if (calConceptoDao.existeReg(conceptoId)) {
			objeto = calConceptoDao.mapeaRegId(conceptoId);				
			objeto.setConceptoNombre(conceptoNombre);
			objeto.setTipo(tipo);
			if(calConceptoDao.updateReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}else {
			conceptoId = calConceptoDao.maximoReg();
			objeto.setConceptoId(conceptoId);
			objeto.setConceptoNombre(conceptoNombre);
			objeto.setTipo(tipo);
			if(calConceptoDao.insertReg(objeto)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}	
		}
		
		return "redirect:/costo/concepto/editar?ConceptoId="+conceptoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/costo/concepto/borrar")
	public String costoConceptoBorrar(HttpServletRequest request){		
		String conceptoId		= request.getParameter("ConceptoId")==null?"0":request.getParameter("ConceptoId");
		
		if (calConceptoDao.existeReg(conceptoId)) {
			calConceptoDao.deleteReg(conceptoId);
		}
		
	    return "redirect:/costo/concepto/lista";
	}
}