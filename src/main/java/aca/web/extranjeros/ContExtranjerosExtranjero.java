package aca.web.extranjeros;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatPaisDao;
import aca.leg.spring.LegExtranjero;
import aca.leg.spring.LegExtranjeroDao;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContExtranjerosExtranjero{
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	LegExtranjeroDao legExtranjeroDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@RequestMapping("/extranjeros/extranjero/extranjero")
	public String extranjerosExtranjeroExtranjero(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= "0";
		String paisNombre 			= "-";
		boolean inscrito			= false;
		String planActual			= "-";
		String carreraNombre		= "";
		String carreraId			= "";
		AlumPersonal alumPersonal 	= new AlumPersonal();
		LegExtranjero legExtranjero	= new LegExtranjero();
		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
			alumPersonal			= alumPersonalDao.mapeaRegId(codigoAlumno);			
			paisNombre 				= catPaisDao.getNombrePais(alumPersonal.getPaisId());
			inscrito 				= inscritosDao.existeReg(codigoAlumno);
			planActual 				= alumPlanDao.getPlanActual(codigoAlumno);
			carreraNombre 			= catCarreraDao.getNombreCarrera(mapaPlanDao.getCarreraId(planActual));
			carreraId				= mapaPlanDao.getCarreraId(planActual);
			if (legExtranjeroDao.existeReg(codigoAlumno)) {
				legExtranjero 		= legExtranjeroDao.mapeaRegId(codigoAlumno);
			}
		}
		
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListAll(" ORDER BY NOMBRE_CARRERA");
		
		modelo.addAttribute("legExtranjero", legExtranjero);
		modelo.addAttribute("alumPersonal", alumPersonal);
		modelo.addAttribute("paisNombre", paisNombre);
		modelo.addAttribute("inscrito", inscrito);
		modelo.addAttribute("planActual", planActual);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("lisCarreras", lisCarreras);
		
		return "extranjeros/extranjero/extranjero";
	}	
	
	@RequestMapping("/extranjeros/extranjero/grabar")
	public String extranjerosExtranjeroGrabar(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 		= "0";
		String rne		 			= request.getParameter("Rne")==null?"-":request.getParameter("Rne");
		String telefono 			= request.getParameter("Telefono")==null?"-":request.getParameter("Telefono"); 
		String comentario 			= request.getParameter("Comentario")==null?"-":request.getParameter("Comentario");
		String carrera	 			= request.getParameter("Carrera")==null?"-":request.getParameter("Carrera");
		String mensaje 				= "-";
		LegExtranjero legExtranjero	= new LegExtranjero();
		HttpSession sesion			= ((HttpServletRequest)request).getSession();		
		if (sesion!=null){
			codigoAlumno			= (String)sesion.getAttribute("codigoAlumno");
			legExtranjero.setCodigo(codigoAlumno);
			legExtranjero.setRne(rne);
			legExtranjero.setTelefono(telefono);
			legExtranjero.setCarrera(carrera);
			legExtranjero.setComentario(comentario);
			if (legExtranjeroDao.existeReg(codigoAlumno)){				
				if (legExtranjeroDao.updateReg(legExtranjero)) {
					mensaje = "¡Grabado!";
				}else {
					mensaje = "¡Error al grabar!";
				}			
			}else {
				if (legExtranjeroDao.insertReg(legExtranjero)) {
					mensaje = "¡Grabado!";
				}else {
					mensaje = "¡Error al grabar!";
				}
			}
		}		
		return "redirect:/extranjeros/extranjero/extranjero?Mensaje="+mensaje;
	}	
}