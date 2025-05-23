package aca.web.archivo;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchUbicacion;
import aca.archivo.spring.ArchUbicacionDao;

@Controller
public class ContArchivoUbicacion {
	
	@Autowired
	ArchUbicacionDao archUbicacionDao;	
		
	@RequestMapping("/archivo/ubicacion/lista")
	public String archivoUbicacionLista(HttpServletRequest request, Model modelo){
		
		List<ArchUbicacion> lisUbicacion 	= archUbicacionDao.getListAll("ORDER BY UBICACION_ID");
		HashMap<String,String> mapaUsados 	= archUbicacionDao.mapaUsados();
		
		modelo.addAttribute("lisUbicacion", lisUbicacion);
		modelo.addAttribute("mapaUsados", mapaUsados);
		
		return "archivo/ubicacion/lista";
	}
	
	@RequestMapping("/archivo/ubicacion/editar")
	public String archivoUbicacionEditar(HttpServletRequest request, Model modelo){
		
		String ubicacionId 		= request.getParameter("UbicacionId")==null?"0":request.getParameter("UbicacionId");
		
		ArchUbicacion ubicacion = new ArchUbicacion();		
		if (archUbicacionDao.existeReg(ubicacionId)) {
			ubicacion = archUbicacionDao.mapeaRegId(ubicacionId);
		}
		
		modelo.addAttribute("ubicacion", ubicacion);
		
		return "archivo/ubicacion/editar";
	}
	
	@RequestMapping("/archivo/ubicacion/grabar")
	public String archivoUbicacionGrabar(HttpServletRequest request, Model modelo){
		
		String ubicacionId 			= request.getParameter("UbicacionId")==null?"0":request.getParameter("UbicacionId");
		String ubicacionNombre 		= request.getParameter("UbicacionNombre")==null?"-":request.getParameter("UbicacionNombre");
		
		ArchUbicacion ubicacion = new ArchUbicacion();
		ubicacion.setUbicacionNombre(ubicacionNombre);
		if (archUbicacionDao.existeReg(ubicacionId)) {
			ubicacion.setUbicacionId(ubicacionId);			
			archUbicacionDao.updateReg(ubicacion);
		}else {
			ubicacion.setUbicacionId(archUbicacionDao.maximoReg());
			archUbicacionDao.insertReg(ubicacion);
		}	
		
		return "redirect:/archivo/ubicacion/lista";
	}
	
	@RequestMapping("/archivo/ubicacion/borrar")
	public String archivoUbicacionBorrar(HttpServletRequest request, Model modelo){
		
		String ubicacionId 			= request.getParameter("UbicacionId")==null?"0":request.getParameter("UbicacionId");
		
		if (archUbicacionDao.existeReg(ubicacionId)) {						
			archUbicacionDao.deleteReg(ubicacionId);
		}
		
		return "redirect:/archivo/ubicacion/lista";
	}	
}