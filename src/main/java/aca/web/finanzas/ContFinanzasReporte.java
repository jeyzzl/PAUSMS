package aca.web.finanzas;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpleadoDao;
import aca.financiero.spring.FinPermiso;

@Controller
public class ContFinanzasReporte {
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	EmpleadoDao empleadoDao;
	
	@Autowired
	aca.financiero.spring.FinPermisoDao finPermisoDao;
	
	@RequestMapping("/finanzas/reporte/reporte")
	public String finanzasReporteReporte(HttpServletRequest request, Model modelo){	
		
		String year		= request.getParameter("Year")==null?"2019":request.getParameter("Year");	
		
		List<FinPermiso> lisPermisos 			= finPermisoDao.lisDelYear(year, " ORDER BY ENOC.FIN_PERMISO.F_INICIO");	
		HashMap<String,String> mapaAlumnos 		= alumPersonalDao.mapaAlumnosConPermiso();
		HashMap<String,String> mapaEmpleados	= empleadoDao.mapEmpleadoPermiso();

		modelo.addAttribute("lisPermisos", lisPermisos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaEmpleados", mapaEmpleados);
		
		return "finanzas/reporte/reporte";
	}
}