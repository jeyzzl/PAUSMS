package aca.web.portales;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.AccesoDao;

@Controller
public class ContPortalesPortalAlumno {
	
	@Autowired
	AccesoDao accesoDao;
	
	@RequestMapping("/portales/portalAlumno/portal")
	public String portalesPortalAlumnoPortal(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "0";
		String alumnoMovil		= "N";
		
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		if (sesion != null) {			
			codigoPersonal = (String)sesion.getAttribute("codigoPersonal");			
			alumnoMovil = accesoDao.mapeaRegId(codigoPersonal).getAlumnoMovil();			
		}
		 
		modelo.addAttribute("alumnoMovil", alumnoMovil);
		
		return "portales/portalAlumno/portal";
	}
	
}