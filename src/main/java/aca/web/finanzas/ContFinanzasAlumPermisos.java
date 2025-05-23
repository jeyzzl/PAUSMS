package aca.web.finanzas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.financiero.spring.FinPermiso;
import aca.financiero.spring.FinPermisoDao;

@Controller
public class ContFinanzasAlumPermisos {
	
	@Autowired	
	private FinPermisoDao finPermisoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;	
	
	
	@RequestMapping("/finanzas/alum_permisos/permisos")
	public String finanzasAlumPermisosPermisos(HttpServletRequest request, Model modelo){
		
		List<FinPermiso> lisPermisos			= finPermisoDao.getListAll(" ORDER BY FOLIO");
		HashMap<String,String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnPermiso(); 
		
		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "finanzas/alum_permisos/permisos";
	}
	
}