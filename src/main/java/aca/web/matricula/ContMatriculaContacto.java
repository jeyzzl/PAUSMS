package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpContacto;
import aca.emp.spring.EmpContactoDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContMatriculaContacto {
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	private EmpContactoDao empContactoDao;
	
	
	@RequestMapping("/matricula/contacto/listado")
	public String matriculaCierreListado(HttpServletRequest request, Model modelo){
		
		List<Maestros> lisDirectores				= maestrosDao.lisDirectores(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<Maestros> lisCoordinadores				= maestrosDao.lisCoordinadores(" ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String,EmpContacto> mapaContactos 	= empContactoDao.mapaContactos();
		
		modelo.addAttribute("lisDirectores", lisDirectores);
		modelo.addAttribute("lisCoordinadores", lisCoordinadores);
		modelo.addAttribute("mapaContactos", mapaContactos);
		
		return "matricula/contacto/listado";
	}
	
}