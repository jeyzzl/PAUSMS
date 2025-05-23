package aca.web.encuesta;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatPeriodo;
import aca.encuesta.spring.EncPeriodo;
import aca.encuesta.spring.EncPeriodoDao;

@Controller
public class ContEncuestaPeriodo {
	
	@Autowired
	private EncPeriodoDao encPeriodoDao;
	
	@RequestMapping("/encuesta/periodo/lista")
	public String encuestaPeriodoLista(HttpServletRequest request, Model model){
		
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		List<EncPeriodo> lisPeriodo 		= (List<EncPeriodo>)encPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		
		
		model.addAttribute("PeriodoId", periodoId);
		model.addAttribute("lisPeriodo", lisPeriodo);
		
	    return "encuesta/periodo/lista";
	}
	
	@RequestMapping("/encuesta/periodo/editarPeriodo")
	public String encuestaEditarEditarPeriodo(HttpServletRequest request, Model model){
				
		String periodoId 					= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		EncPeriodo periodo					= new EncPeriodo();
		
		if(encPeriodoDao.existeReg(periodoId)){
			periodo = encPeriodoDao.mapeaRegId(periodoId);
		}	
		
		model.addAttribute("periodo", periodo);
		
		return "encuesta/periodo/editarPeriodo";
	}
	
	@RequestMapping("/encuesta/periodo/grabarPeriodo")
	public String encuestaEditarGrabarPeriodo(HttpServletRequest request, Model model){
				
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String periodoNombre	= request.getParameter("PeriodoNombre")==null?"0":request.getParameter("PeriodoNombre");
		String fechaIni 		= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin 		= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String mensaje 			= "-";
		
		EncPeriodo periodo = new EncPeriodo();
		periodo.setPeriodoId(periodoId);
		periodo.setPeriodoNombre(periodoNombre);
		periodo.setFechaIni(fechaIni);
		periodo.setFechaFin(fechaFin);
		periodo.setEstado(estado);
		
		if(encPeriodoDao.existeReg(periodoId)){
			if (encPeriodoDao.updateReg(periodo)) {
				mensaje = "Actualizado";			
			}else{
				mensaje = "Error";
			}
		}else {
			periodoId = encPeriodoDao.maximoReg();
			periodo.setPeriodoId(periodoId);
			if(encPeriodoDao.insertReg(periodo)){
				mensaje = "Se insertó";	
			}else{
				mensaje = "No se guardó";
			}
		}	
		
		return "redirect:/encuesta/periodo/editarPeriodo?PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}

	
}
