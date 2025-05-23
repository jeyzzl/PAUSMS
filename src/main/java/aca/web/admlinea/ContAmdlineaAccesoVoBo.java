package aca.web.admlinea;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import aca.admision.spring.AdmAccesoVobo;
import aca.admision.spring.AdmAccesoVoboDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.emp.spring.EmpleadoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAmdlineaAccesoVoBo {
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private AdmAccesoVoboDao admAccesoVoboDao;
	
	@Autowired
	private MaestrosDao maestrosDao;	
	
	
	@GetMapping("/admlinea/accesoVobo/listado")
	public String admLineaAccesoVoboListado(HttpServletRequest request, Model modelo) {
		
		List<CatCarrera> listaCarreras 			= catCarreraDao.getListAll(" ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
		
		HashMap<String, CatFacultad> mapaFacultad = catFacultadDao.getMapFacultad("");
		HashMap<String, String> mapAcceCarrera = admAccesoVoboDao.mapAcceCarrera();
		
		modelo.addAttribute("listaCarreras",listaCarreras);
		modelo.addAttribute("mapaFacultad",mapaFacultad);
		modelo.addAttribute("mapAcceCarrera",mapAcceCarrera);
		
		return "admlinea/accesoVobo/listado";
	}

	@RequestMapping(value="/admlinea/accesoVobo/accesosVobo", method = RequestMethod.GET)
	public String admLineaAccesoVoboAccesosVobo(HttpServletRequest request, Model modelo) {
		
		String carreraId 		= request.getParameter("CarreraId") == null ? "0" : request.getParameter("CarreraId");		
		CatCarrera carrera 		= new CatCarrera();
		
		String codigoEmpleado 	= "0";
		String empleadoNombre 	= "-";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");
			empleadoNombre 		= maestrosDao.getNombreMaestro(codigoEmpleado, "NOMBRE");
			if (!carreraId.equals("0")) {
				sesion.setAttribute("carreraId", carreraId);
			}
			if(catCarreraDao.existeReg(carreraId)) {
				carrera 		= catCarreraDao.mapeaRegId(carreraId);
			}else {
				carreraId 		= (String)sesion.getAttribute("carreraId");
				carrera 		= catCarreraDao.mapeaRegId(carreraId);
			}
		}
		
		List<AdmAccesoVobo> lista 		= admAccesoVoboDao.lista(carreraId);
		HashMap<String,String> mapa 	= empleadoDao.mapEmpleado();	
		
		modelo.addAttribute("carrera",carrera);
		modelo.addAttribute("empleadoNombre",empleadoNombre);
		modelo.addAttribute("lista",lista);		
		modelo.addAttribute("mapa",mapa);
		
		return "admlinea/accesoVobo/accesosVobo";
	}
	
	@RequestMapping("/admlinea/accesoVobo/grabarAcceso")
	public String admLineaAccesoVoboGrabarAcceso(HttpServletRequest request) {	
		
		String carreraId 		= "0";		
		String codigoEmpleado 	= "0";		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		
		if (sesion!=null){
			codigoEmpleado		= (String) sesion.getAttribute("codigoEmpleado");						
			carreraId			= (String) sesion.getAttribute("carreraId");
		}
		
		AdmAccesoVobo objeto = new AdmAccesoVobo();
		
		if(!admAccesoVoboDao.existeReg(codigoEmpleado, carreraId)) {
			objeto.setCarreraId(carreraId);
			objeto.setCodigoPersonal(codigoEmpleado);
			admAccesoVoboDao.insertReg(objeto);
		}
		
		return "redirect:/admlinea/accesoVobo/accesosVobo?CarreraId="+carreraId;
	}
	
	@RequestMapping("/admlinea/accesoVobo/borrarAcceso")
	public String admLineaAccesoVoboBorrarAcceso(HttpServletRequest request) {
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		String carreraId 		= request.getParameter("CarreraId") == null ? "0" : request.getParameter("CarreraId");
		
		if(admAccesoVoboDao.existeReg(codigoPersonal, carreraId)) {
			admAccesoVoboDao.deleteReg(codigoPersonal, carreraId);
		}
		
		return "redirect:/admlinea/accesoVobo/accesosVobo?CarreraId="+carreraId;
	}
}
