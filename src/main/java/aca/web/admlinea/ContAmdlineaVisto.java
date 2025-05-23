package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.admision.spring.AdmAcademico;
import aca.admision.spring.AdmAcademicoDao;
import aca.admision.spring.AdmPasos;
import aca.admision.spring.AdmPasosDao;
import aca.admision.spring.AdmSolicitud;
import aca.admision.spring.AdmSolicitudDao;
import aca.admision.spring.AdmUsuario;
import aca.admision.spring.AdmUsuarioDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.emp.spring.EmpleadoDao;

@Controller
public class ContAmdlineaVisto {
	
	@Autowired
	private AdmAcademicoDao admAcademicoDao;
	
	@Autowired
	private AdmSolicitudDao admSolicitudDao;
	
	@Autowired
	private AdmUsuarioDao admUsuarioDao;
	
	@Autowired
	private AdmPasosDao admPasosDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@RequestMapping("/admlinea/visto/pendientes")
	public String admLineaVistoPendientes(HttpServletRequest request, Model modelo) {
		
		List<AdmSolicitud> lisPendientes 				= admSolicitudDao.listIngreso(" ORDER BY SPAS.FECHA DESC");
		
		HashMap<String, AdmUsuario> mapaUsuarios	 	= admUsuarioDao.mapaVistoBueno();
		HashMap<String, AdmAcademico> mapaAcademico 	= admAcademicoDao.mapaAlumnos(" WHERE FOLIO IN(SELECT FOLIO FROM SALOMON.ADM_PASOS WHERE PASOS = '2' AND ESTADO = '0')");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaEmpleados 			= empleadoDao.mapEmpleadoCorto();
		HashMap<String,AdmPasos> mapaPasos 				= admPasosDao.mapaPasos();
		
		modelo.addAttribute("lisPendientes",lisPendientes);
		modelo.addAttribute("mapaUsuarios",mapaUsuarios);
		modelo.addAttribute("mapaAcademico",mapaAcademico);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaEmpleados",mapaEmpleados);
		modelo.addAttribute("mapaPasos",mapaPasos);
		
		return "admlinea/visto/pendientes";
	}

	@RequestMapping("/admlinea/visto/quitarPendiente")
	public String admLineaVistoQuitarPendiente(HttpServletRequest request, Model modelo) {
		
		String folio = request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		
		admPasosDao.quitarPendiente(folio);
		
		return "redirect:/admlinea/visto/pendientes";
	}

}
