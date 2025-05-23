package aca.web.datosalumno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumPersonal;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContDatosAlumnoCumple {
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	
	
	@RequestMapping("/datos_alumno/cumple/form")
	public String datosAlumnoCursosMaterias(HttpServletRequest request, Model modelo){
		String accion 				= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
		String mes					= request.getParameter("Mes")== null?aca.util.Fecha.getHoy().substring(3,5):request.getParameter("Mes");
		String dia					= request.getParameter("Dia")== null?aca.util.Fecha.getHoy().substring(0,2):request.getParameter("Dia");
		Acceso acceso				= new Acceso();
		
		String codigoPersonal 		= "0";
		HttpSession sesion			= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 			= (String) sesion.getAttribute("codigoPersonal");
        	acceso 					= accesoDao.mapeaRegId(codigoPersonal);
        }
        
        List<Inscritos> lisAlumnos 					= inscritosDao.getListCumple(mes, dia, " ORDER BY F_NACIMIENTO, CARRERA_ID, APELLIDO_PATERNO");
        HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
        
        modelo.addAttribute("lisAlumnos", lisAlumnos);
        modelo.addAttribute("acceso", acceso);
        modelo.addAttribute("mapaCarreras", mapaCarreras);
        modelo.addAttribute("mes", mes);
        modelo.addAttribute("dia", dia);
		
		return "datos_alumno/cumple/form";
	}
	
}