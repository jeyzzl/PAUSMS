package aca.web.administrar;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.portafolio.spring.PorCompromiso;
import aca.portafolio.spring.PorCompromisoDao;
import aca.sep.spring.SepAlumnoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAdministrarIdp {
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	PorCompromisoDao porCompromisoDao;
	
	
	@RequestMapping("/administrar/idp/compromiso")
	public String administrarIdpCompromiso(HttpServletRequest request, Model modelo){
		
		String periodoId 		= aca.util.Fecha.getHoy().substring(6, 10);
		
		List<Maestros> lisMaestros						= maestrosDao.lisMaestrosEnCompromiso(periodoId," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String,PorCompromiso> mapaCompromiso	= porCompromisoDao.mapaCompromiso(periodoId);
		HashMap<String,String> mapaEducar	  		 	= porCompromisoDao.mapaEducar("Educar");
		HashMap<String,String> mapaModelar	 	 		= porCompromisoDao.mapaEducar("Modelar");
		HashMap<String,String> mapaInvestigar 			= porCompromisoDao.mapaEducar("Investigar");
		HashMap<String,String> mapaServir	   			= porCompromisoDao.mapaEducar("Servir");
		HashMap<String,String> mapaProclamar   			= porCompromisoDao.mapaEducar("Proclamar");
		HashMap<String,String> mapaEsperanza   			= porCompromisoDao.mapaEducar("Esperanza");
		
		modelo.addAttribute("lisMaestros", lisMaestros);		
		modelo.addAttribute("mapaEducar", mapaEducar);
		modelo.addAttribute("mapaModelar", mapaModelar);
		modelo.addAttribute("mapaInvestigar", mapaInvestigar);
		modelo.addAttribute("mapaServir", mapaServir);
		modelo.addAttribute("mapaProclamar", mapaProclamar);
		modelo.addAttribute("mapaEsperanza", mapaEsperanza);
		modelo.addAttribute("mapaCompromiso", mapaCompromiso);
		
		
		return "administrar/idp/compromiso";
	}
	
	@RequestMapping("/administrar/idp/enviarEstado")
	public String enviarEstado(HttpServletRequest request, Model modelo){
	
		String periodoId 		= request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal") == null ? "X" :request.getParameter("CodigoPersonal");

		if(porCompromisoDao.updateEstado(codigoPersonal, periodoId, "A")){
			
		}
		
		return "redirect:/administrar/idp/compromiso?";
	}

}
