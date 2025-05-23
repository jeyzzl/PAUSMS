package aca.web.admision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.alumno.spring.AlumPersonal;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;

@Controller
public class ContAdmisionVue {
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatPaisDao catPaisDao;
	
	
	@RequestMapping("/admision/vue/uno")
	public String admisionVueUno(HttpServletRequest request, Model modelo) {		
		return "admision/vue/uno";
	}
	
	@RequestMapping("/admision/vue/dos")
	public String admisionVueDos(HttpServletRequest request, Model modelo) {		
		return "admision/vue/dos";
	}
	
	@RequestMapping("/admision/vue/tres")
	public String admisionVueTres(HttpServletRequest request, Model modelo) {		
		return "admision/vue/tres";
	}
	
	@RequestMapping("/admision/vue/cuatro")
	public String admisionVueCuatro(HttpServletRequest request, Model modelo) {		
		return "admision/vue/cuatro";
	}
	
	@RequestMapping("/admision/vue/cinco")
	public String admisionVueCinco(HttpServletRequest request, Model modelo) {		
		return "admision/vue/cinco";
	}
	
	@RequestMapping("/admision/vue/seis")
	public String admisionVueSeis(HttpServletRequest request, Model modelo) {
		return "admision/vue/seis";
	}
	
	@ResponseBody
	@RequestMapping("/admision/vue/alumnos")
	public List<CatPais> admisionVueAlumnos(HttpServletRequest request, Model modelo) {
		List<CatPais> lista = catPaisDao.getListAll(" ORDER BY 2");
		return lista;
	}
	
}